/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.wrappers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.ApplicatonUtils;

/**
 * The Class CGRequestWrapper.
 */
public class CGRequestWrapper extends HttpServletRequestWrapper {

	/** The my session. */
	public CGSession mySession;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(CGRequestWrapper.class);

	/**
	 * Instantiates a new cTBS request wrapper.
	 * 
	 * @param request
	 *            the request
	 */
	public CGRequestWrapper(HttpServletRequest request) {
		super(request);
		mySession = new CGSession(request.getSession());
		String contextId = request.getParameter("subSessionKey");
		logger.info("============contextId:========" + contextId + "=====:");
		if (contextId != null && !contextId.trim().equals("")
				&& !contextId.equalsIgnoreCase("null")) {
			// contextId = contextId.substring(0, contextId.length() - 1);
			mySession.setCurrentSubSessionKey(request
					.getParameter("subSessionKey"));
			request.setAttribute("subSessionKey", contextId);
			logger.info("22222222222contextId:========" + contextId + "=====:");
		} else {
			contextId = ApplicatonUtils.generateSubSessionId(mySession);
			logger.info("contextId---->" + contextId);
			mySession.setCurrentSubSessionKey(contextId);
			request.setAttribute("subSessionKey", contextId);
			logger.info("3333333 contextId:========" + contextId + "=====:");
		}
		// System.out.println(se);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession()
	 */
	public HttpSession getSession() {
		return mySession;
	}
}
