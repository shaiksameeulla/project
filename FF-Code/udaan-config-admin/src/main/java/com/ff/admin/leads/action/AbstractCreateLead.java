package com.ff.admin.leads.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;

public abstract class AbstractCreateLead extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractCreateLead.class);
	/**
	 * getMessageFromErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @return message
	 */
	public String getMessageFromErrorBundle(HttpServletRequest request,
			String key) {
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(key);
		}
		return msg;
	}

	/**
	 * getErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @return error messages
	 */
	private MessageResources getErrorBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}
}
