/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.wrappers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * The Class CGSession.
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class CGSession implements HttpSession {

	/** The real session. */
	private HttpSession realSession;

	/** The sub session key. */
	private String subSessionKey;

	/**
	 * Instantiates a new cTBS session.
	 * 
	 * @param session
	 *            the session
	 */
	public CGSession(HttpSession session) {
		this.realSession = session;
	}

	/**
	 * Sets the current sub session key.
	 * 
	 * @param subSessionKey
	 *            the new current sub session key
	 */
	public void setCurrentSubSessionKey(String subSessionKey) {
		this.subSessionKey = subSessionKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String key) {
		return getSubSession().get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	public void setAttribute(String key, Object value) {
		getSubSession().put(key, value);
		realSession.setAttribute(subSessionKey, getSubSession());
		// trigger replication...is this expensive?
	}

	/**
	 * Gets the sub session.
	 * 
	 * @return the sub session
	 */
	private Map<String, Object> getSubSession() {

		Map<String, Object> sub = (Map<String, Object>) realSession
				.getAttribute(subSessionKey);
		if (sub == null) {
			sub = new HashMap<String, Object>();
			realSession.setAttribute(subSessionKey, sub);
		}
		return sub;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {

		return realSession.getAttributeNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		return realSession.getCreationTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		return realSession.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		return realSession.getLastAccessedTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return realSession.getMaxInactiveInterval();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		return realSession.getServletContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public HttpSessionContext getSessionContext() {
		return realSession.getSessionContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String arg0) {
		return realSession.getValue(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		return realSession.getValueNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {
		realSession.invalidate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		return realSession.isNew();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 * java.lang.Object)
	 */
	public void putValue(String arg0, Object arg1) {
		realSession.putValue(arg0, arg1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String arg0) {
		realSession.removeAttribute(arg0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String arg0) {
		realSession.removeValue(arg0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int arg0) {
		realSession.setMaxInactiveInterval(arg0);

	}
}
