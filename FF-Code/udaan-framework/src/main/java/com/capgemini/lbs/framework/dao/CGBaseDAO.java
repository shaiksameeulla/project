/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The Class CGBaseDAO.
 */
public abstract class CGBaseDAO extends HibernateDaoSupport {

	/**
	 * @param session
	 * @throws HibernateException
	 */
	public void closeSession(Session session) throws HibernateException {
		if (session != null && session.isOpen()) {
			//releaseSession(session);
			session.close();
		}
	}

	/**
	 * @return
	 * @throws HibernateException
	 */
	public Session createSession() throws HibernateException {
		return getHibernateTemplate().getSessionFactory().openSession();
		//return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	/**
	 * @return
	 * @throws HibernateException
	 */
	public Session getCreatedSession() throws HibernateException {
		
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	/**
	 * Open transactional session.:: Use this method to get Session and its for Declarative tx
	 *
	 * @return the session
	 * @throws HibernateException the hibernate exception
	 */
	public Session openTransactionalSession() throws HibernateException {
		return getSession(false);
	}
	
	/**
	 * Close transactional session.:: To close the session if the Application uses Declarative tx
	 *
	 * @param session the session
	 * @throws HibernateException the hibernate exception
	 */
	public void closeTransactionalSession(Session session) throws HibernateException {
		if (session != null && session.isOpen()) {
			if (!SessionFactoryUtils.isSessionTransactional(session, getHibernateTemplate().getSessionFactory())) {
				session.close();
			}
		}
	}
	
	/**
	 * Checks if is transactional session.
	 *
	 * @param session the session
	 * @return the boolean
	 * @throws HibernateException the hibernate exception
	 */
	public Boolean isTransactionalSession(Session session) throws HibernateException {
		boolean isTransactional=false;
		if (session != null && session.isOpen()) {
			if (SessionFactoryUtils.isSessionTransactional(session, getHibernateTemplate().getSessionFactory())) {
				isTransactional= true;
			}
		}
		return isTransactional;
	}
	
	

	
}
