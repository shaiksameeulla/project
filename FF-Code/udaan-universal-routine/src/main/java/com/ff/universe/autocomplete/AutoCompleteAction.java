package com.ff.universe.autocomplete;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.universe.umc.service.UserManagementCommonService;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoCompleteAction.
 */
public class AutoCompleteAction extends CGBaseAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AutoCompleteAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The umc common service. */
	UserManagementCommonService umcCommonService = null;

	/**
	 * Gets the login ids autocomplete.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the login ids autocomplete
	 */
	public void getLoginIdsAutocomplete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * Gets the users autocomplete.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the users autocomplete
	 */
	public void getUsersAutocomplete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userType = "";
		userType = request.getParameter("userType");
		Map<Integer, String> users = null;
		try {
			umcCommonService = (UserManagementCommonService) getBean("umcCommonService");
			users = umcCommonService.getUsersByType(userType);
			java.io.PrintWriter out = response.getWriter();
			out.print(users);
			out.flush();
		} catch (Exception e) {
			LOGGER.error("AutoCompleteAction::getUsersAutocomplete", e);
		}
	}

}
