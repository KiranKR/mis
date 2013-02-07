package com.ample.mis.hibernate.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Data access interface for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public interface BaseHibernateDAO {
	public Session getSession() throws Exception;

	public Object findByValue(String query) throws Exception;
	public void closeSession() throws Exception;

	public int update(Object object) throws Exception;

	public int updateByQuery(String quString) throws Exception;

	public boolean isDuplicatedName(Integer id, String name, Class<?> className)
			throws Exception;

	public int save(Object transientInstance) throws Exception;

	public int saveOrUpdate(Object transientInstance) throws Exception;

	public List<?> fetchAllData(String userQuery) throws HibernateException,
			Exception;

	public int delete(Integer id, Object persistentInstance) throws Exception;

	public void delete(List<?> persistentInstanceList) throws Exception;

	public List<?> findByExample(Object instance) throws Exception;

	public int save(List<?> list) throws Exception;

	public int updateList(List<?> obList) throws Exception;

	public Object merge(Object detachedInstance) throws Exception;

	public List<?> findAll(Object instance) throws Exception;

	public Object findById(java.lang.Integer id, Object object)
			throws Exception;

	public List<?> findByProperty(String propertyName, Object value,
			Object className) throws Exception;

	public int fetchMaxPrimaryKey();

	public Object getMaxValueOfTable(Class<?> className, String propertyName)
			throws Exception;
	public  void callProcedure(String sql) throws Exception;
	public List<Object> fetchLpValues(Object instance, int sortTypeId)throws Exception;
}