package com.ample.mis.hibernate.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ample.mis.exception.persistence.DataNotFoundException;
import com.ample.mis.hibernate.dao.BaseHibernateDAO;
import com.ample.mis.hibernate.util.HibernateUtil;

@Repository("baseHibernateDaoImpl")
public class BaseHibernateDaoImpl implements BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(BaseHibernateDaoImpl.class);
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	//Get Session from Session Factory
	public Session getSession() throws Exception {
		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
			session = (sessionFactory != null) ? sessionFactory.openSession(): null;
			threadLocal.set(session);
		}
		return session;
	}

	//Close Session from Session Factory
	public void closeSession() throws Exception {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}

	public List<?> fetchAllData(String userQuery) throws DataNotFoundException
	{
		
		List<?> list = new ArrayList();
			try{
				Session session = getSession();
				/*Session session2 = HibernateUtil.getSession();
				list =  session2.createQuery(userQuery).list();*/
				
				list =  session.createQuery(userQuery).list();
				closeSession();
		if (list == null) 
			list = new ArrayList();
	}
		 catch (HibernateException e) {
				throw new DataNotFoundException("Database problem",e);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataNotFoundException("Unknown exception",e);
			}
		
		return list;
	}

	public int save(Object transientInstance) throws Exception{
		log.debug("saving " + transientInstance.getClass() + " instance");
		Session session = null;
		Transaction tx = null;
		Integer saved = 0;
		try {
			session = getSession();
			tx = session.beginTransaction();
			saved = (Integer) session.save(transientInstance);
			tx.commit();
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception occured during save..." + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.debug("save successful");
		return saved;
	}

	public int delete(Integer id, Object persistentInstance) throws Exception {
		log.debug("deleting " + persistentInstance.getClass() + " instance");
		int deleted = 0;
		Session session = getSession();
		Transaction tx = null;
		Object object = findById(id, persistentInstance);
		if (object != null) {
			tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
			deleted = 1;
		}
		closeSession();
		log.debug("delete successful");
		return deleted;
	}

	public void delete(List<?> persistentInstanceList) throws Exception {
		log.debug("deleting objects instance");
		Session session=null;
		Transaction tx=null;
		try {
			 session = getSession();
			 tx=session.beginTransaction();
			for (Object object : persistentInstanceList) {
				session.delete(object);
			}
			tx.commit();
			closeSession();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<?> findByExample(Object instance) throws Exception {
		log.debug("finding " + instance.getClass().getName()
				+ " instance by example");
		try {
			List<?> results = getSession().createCriteria(instance.getClass())
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (Exception re) {
			re.printStackTrace();
			log.error("find by example failed", re);
			throw re;
		}
	}

	public int save(List<?> list) throws Exception {
		log.debug("saving list of " + list.getClass().getTypeParameters()
				+ " instance");
		int saved = 0;
		Transaction tx = null;
		Session session = null;
		try {

			for (Object object : list) {
				session = getSession();
				tx = session.beginTransaction();
				Integer serialId = (Integer) session.save(object);
				saved += (serialId != null ? serialId : 0);
				tx.commit();
				session.flush();
				session.close();

			}
			log.debug("save successful");
		} catch (Exception e) {
			log.error("save failed  " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return saved;
	}

	public Object merge(Object detachedInstance) throws Exception {
		log.debug("merging " + detachedInstance.getClass().getName()
				+ " instance");
		try {
			Object result = getSession().merge(detachedInstance);
			log.debug("merge successful");
			closeSession();
			return result;
		} catch (Exception re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public List<?> findAll(Object instance) throws Exception {
		log.debug("finding all " + instance.getClass().getName()
				+ " instances");
		try {
			String queryString = "from " + instance.getClass().getSimpleName()
					+ " bo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("find all failed", ex);
			throw ex;
		}
	}

	public Object findById(java.lang.Integer id, Object object)
			throws Exception {
		log.debug("getting " + object.getClass().getSimpleName()
				+ " instance with id: " + id);
		try {
			Object instance = HibernateUtil.getSession().get(object.getClass(), id);
			return instance;
		} catch (Exception re) {
			re.printStackTrace();
			log.error("get failed", re);
			throw re;
		}
	}

	public List<?> findByProperty(String propertyName, Object value,
			Object className) throws Exception {
		log.debug("finding " + value.getClass().getName()
				+ " instance with property: " + propertyName + ", value: "
				+ value);
		try {
			String queryString = "from " + className.getClass().getSimpleName()
					+ " as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int saveOrUpdate(Object transientInstance) {

		Session session = null;
		Transaction transaction = null;
		try {

			session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(transientInstance);
			transaction.commit();
			session.flush();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				if (transaction != null) {
					transaction.rollback();
				}
			}

		} finally {
			session.close();
		}
		return 0;

	}

	public int update(Object object) throws Exception {
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(object);
			tx.commit();
			result = 1;
			session.flush();
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				closeSession();
			}
		}

		return result;
	}

	public int updateByQuery(String quString) throws Exception {
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = getSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(quString);
			query.executeUpdate();
			tx.commit();
			result = 1;
			session.flush();
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				closeSession();
			}
		}

		return result;
	}

	public int updateList(List<?> obList) throws Exception {
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = getSession();
			tx = session.beginTransaction();
			for (Object object : obList) {
				session.saveOrUpdate(object);
				result += 1;
			}
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				closeSession();
			}
		}

		return result;
	}

	public boolean isDuplicatedName(Integer id, String name, Class className)
			throws Exception {
		Session session = null;
		boolean duplicate = true;

		/*
		 * session = getSession(); Criteria crit =
		 * session.createCriteria(className); crit.add(Restrictions.eq("name",
		 * name)); List<?> list = crit.list();
		 * 
		 * if (list.size() > 0) { for (Iterator<?> iterator = list.iterator();
		 * iterator.hasNext();) { Object object = iterator.next(); if (object
		 * instanceof CategoryMasterBO) { CategoryMasterBO bo =
		 * (CategoryMasterBO) object; if (id != 0 && id != bo.getId()) { if
		 * (name.equalsIgnoreCase(bo.getName())) throw new DuplicateException();
		 * } else if (bo.getName().equalsIgnoreCase(name)) throw new
		 * DuplicateException(); } else if (object instanceof DepartmentBO) {
		 * DepartmentBO bo = (DepartmentBO) object; if (id != 0 && id !=
		 * bo.getId()) { if (name.equalsIgnoreCase(bo.getName())) throw new
		 * DuplicateException(); } else if (bo.getName().equalsIgnoreCase(name))
		 * throw new DuplicateException(); } else if (object instanceof
		 * ServiceDataFormBO) { ServiceDataFormBO bo = (ServiceDataFormBO)
		 * object; if ((id != null || id != 0) && id != bo.getId()) { if
		 * (name.equalsIgnoreCase(bo.getName())) throw new DuplicateException();
		 * } else if (bo.getName().equalsIgnoreCase(name)) throw new
		 * DuplicateException(); }
		 * 
		 * }
		 * 
		 * } else { duplicate = false; }
		 * 
		 * session.flush();
		 */
		// session.close();
		return duplicate;
	}

	public int fetchMaxPrimaryKey() {
		throw new RuntimeException();
	}

	public synchronized Object getMaxValueOfTable(Class<?> className,
			String propertyName) throws Exception {
		Session session = HibernateUtil.getSession();
		Object maxResult = session.createCriteria(className)
				.addOrder(Order.desc(propertyName)).setMaxResults(1)
				.uniqueResult();
		session.flush();
		session.close();
		return maxResult;

	}
	
	public Object findByValue(String query) throws Exception
	{
		Session session = HibernateUtil.getSession();
		return session.createQuery(query).uniqueResult();
		
	}
   public  void callProcedure(String sql) throws Exception{
	   Session session = null;
		Transaction tx = null;
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			tx.begin();
			Connection connection =session.connection();
		 try {
			CallableStatement cs= connection.prepareCall(sql);
			 cs.execute();
			 tx.commit();
			 session.flush();
				session.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
   }

	public List<Object> fetchLpValues(Object instance, int sortTypeId) {
		List<?> lpValue = new ArrayList();
		
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Criteria crit = session.createCriteria((Class) instance);
			if(sortTypeId ==1){
			crit.addOrder(Order.asc("name")).list();
			}
			if(sortTypeId ==2){
				crit.addOrder(Order.asc("id")).list();
				}
			if(sortTypeId ==3){
				crit.addOrder(Order.desc("id")).list();
				}
			lpValue = crit.list();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
		return (List<Object>) lpValue;
	}

}