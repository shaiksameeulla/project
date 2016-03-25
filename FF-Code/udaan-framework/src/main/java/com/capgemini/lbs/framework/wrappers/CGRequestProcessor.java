/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.wrappers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.DateUtil;

/**
 * The Class CGRequestProcessor.
 */
public class CGRequestProcessor extends TilesRequestProcessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CGRequestProcessor.class);

	/* (non-Javadoc)
	 * @see org.apache.struts.action.RequestProcessor#processActionPerform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.Action, org.apache.struts.action.ActionForm, org.apache.struts.action.ActionMapping)
	 */
	protected ActionForward processActionPerform(
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, Action action,
			ActionForm form, ActionMapping mapping) {
		ActionForward forward = null;
		try {
			forward = super.processActionPerform(request, response, action,
					form, mapping);
			if (mapping.getScope().equalsIgnoreCase("session")) {
				request.getSession().setAttribute(mapping.getName() != null ? mapping.getName() :"_NULL", form);
			}

		} catch (IOException e) {
			LOGGER.error("CGRequestProcessor::processActionPerform::IOException " ,e);
		} catch (ServletException e) {
			LOGGER.error("CGRequestProcessor::processActionPerform::ServletException " ,e);
		}

		return forward;
	}

}