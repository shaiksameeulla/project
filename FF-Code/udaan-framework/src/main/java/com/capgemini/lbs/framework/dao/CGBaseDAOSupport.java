/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;


/**
 * The Class CGBaseDAOSupport.
 */
public abstract class CGBaseDAOSupport  {
	private HibernateTemplate hibernateTemplate;

    /**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	/**
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
	/**
	 * 
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * @param session
	 * @throws HibernateException
	 */
	public void closeSession(Session session) throws HibernateException {
		if (session != null && session.isOpen()) {
			if (!SessionFactoryUtils.isSessionTransactional(session, sessionFactory)) {
				session.close();
			}
		}
	}

	/**
	 * @return
	 * @throws HibernateException
	 */
	public Session createSession() throws HibernateException {
		return this.sessionFactory.getCurrentSession();
	}
	/**
	 * @return
	 * @throws HibernateException
	 */
	public Session openSession() throws HibernateException {
		return sessionFactory.openSession();
	}

}
