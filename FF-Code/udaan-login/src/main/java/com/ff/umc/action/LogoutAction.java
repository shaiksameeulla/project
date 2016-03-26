package com.ff.umc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.service.LoginService;


public class LogoutAction extends CGBaseAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);
	LoginService loginService = null;
	
	/**
	 * @Desc set the logout time
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to login view
	 */
	public ActionForward logoutUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("LoginAction::logoutUser...Start");
		
		HttpSession session = request.getSession();

		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);	//get userId from current session
		UserTO userTo=userInfoTO.getUserto();
		loginService = (LoginService) getBean("loginService");
		try {
			boolean islogoutSuccess = loginService.insertLogoutTime(userTo);
			if(islogoutSuccess){
				session.setAttribute("logOut","logOut");
				prepareActionMessage(request, LoginErrorCodeConstants.LOGOUT_SUCCESSFULLY);
			}
			
		}
		catch (CGSystemException e) {
			LOGGER.error("Error occured in LogoutAction :: logoutUser() ::" + e.getMessage());
			getSystemException(request, e);
		}
		
		return mapping.findForward(UmcConstants.WELCOME);
	}
	
}
