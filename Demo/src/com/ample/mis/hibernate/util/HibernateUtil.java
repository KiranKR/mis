package com.ample.mis.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public final class HibernateUtil {

	private final static String CLASS_NAME = "HibernateUtil";
	private static String CONFIG_FILE_LOCATION = "com/ample/mis/hibernate/ifcResources/hibernate_configuration/hibernate.cfg.xml";	
	//private static String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	private static final ThreadLocal threadLocal = new ThreadLocal();
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {

			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateUtil() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		final String METHOD_NAME = ".getSession()";
		//applicationLogger.start(METHOD_NAME);
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		final String METHOD_NAME = ".rebuildSessionFactory()";
	//	applicationLogger.start(METHOD_NAME);
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		final String METHOD_NAME = ".closeSession()";
	//	applicationLogger.start(METHOD_NAME);
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 * 
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		final String METHOD_NAME = ".getSessionFactory()";
	//	applicationLogger.start(METHOD_NAME);

		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		final String METHOD_NAME = ".getConfiguration()";
	//	applicationLogger.start(METHOD_NAME);
		HibernateUtil.configFile = configFile;
		sessionFactory = null;
	//	applicationLogger.end(METHOD_NAME);
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		final String METHOD_NAME = ".getConfiguration()";
	//	applicationLogger.start(METHOD_NAME);

		return configuration;
	}

	/*public static void main(String[] args) {
		
		 * Logger log=Logger.getLogger(HibernateUtil.class); Statistics stats =
		 * HibernateUtil.sessionFactory.getStatistics();
		 * 
		 * double queryCacheHitCount = stats.getQueryCacheHitCount();
		 * 
		 * double queryCacheMissCount = stats.getQueryCacheMissCount();
		 * 
		 * double queryCacheHitRatio = queryCacheHitCount / (queryCacheHitCount
		 * + queryCacheMissCount);
		 * 
		 * log.info("Query Hit ratio:" + queryCacheHitRatio);
		 * System.out.println("Query Hit ratio:" + queryCacheHitRatio);
		 * 
		 * EntityStatistics entityStats = stats.getEntityStatistics(
		 * UserBO.class.getName() );
		 * 
		 * long changes = entityStats.getInsertCount() +
		 * entityStats.getUpdateCount() + entityStats.getDeleteCount();
		 * 
		 * log.info(UserBO.class.getName() + " changed " + changes);
		 * System.out.println(UserBO.class.getName() + " changed " + changes);
		 

		Session session = HibernateUtil.getSession();
		WorksBO worksBO = new WorksBO();

		Transaction tx = session.beginTransaction();
		tx.begin();
		session.saveOrUpdate(worksBO);
		tx.commit();
		session.close();
		// session.clear();
		
		 * UserBO bo=(UserBO)session.get(UserBO.class, new Integer(2));
		 * 
		 * System.out.println(DateFormat.getDateTimeInstance(DateFormat.FULL,
		 * DateFormat.SHORT).format(bo.getCreatedDate()));
		 
		// CacheManager manager=CacheManager.getInstance().getCache(name)
	}*/
}