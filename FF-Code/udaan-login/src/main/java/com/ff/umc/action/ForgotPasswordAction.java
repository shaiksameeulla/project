package com.ff.umc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.umc.PasswordTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.ForgotPasswordForm;
import com.ff.umc.service.LoginService;

/**
 * @author nihsingh
 *
 */
public class ForgotPasswordAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);

	LoginService loginService = null;
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forgot passowrd view
	 */
	public ActionForward showForgotPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		return mapping.findForward(UmcConstants.FORGOTPASSWORD);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return login view
	 */
	public ActionForward showLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ForgotPasswordAction::showLogin::START------------>:::::::");
		loginService = (LoginService) springApplicationContext
				.getBean(UmcConstants.LOGIN_SERVICE);
		 HttpSession session = null;
		 session = (HttpSession) request.getSession(false);
		
		try {
			
			ForgotPasswordForm forgotPaswdForm = (ForgotPasswordForm) form;
			PasswordTO paswdTO = (PasswordTO) forgotPaswdForm.getTo();


			//get the context path
			String contextPath = request.getContextPath();
			String genPaswd = loginService.generatePassword();
			request.setAttribute("password", genPaswd);
			String username=paswdTO.getUsername();
		    request.setAttribute("username", username);
			
		    //validates userName
		    boolean validateUsername=loginService.validateUsername( paswdTO.getUsername());
		    if(validateUsername){
		    //sends email when password changed
		    	boolean sentMail=Boolean.FALSE;
		    	try{
		    	 sentMail=loginService.sendPasswordMail(genPaswd, paswdTO.getUsername(),"Request for Forgot Password","",contextPath);
		    	}catch(Exception ex){
		    		LOGGER.error("Exception happened in showLogin of ForgotPasswordAction..."
							+ ex.getLocalizedMessage());
		    		ex.getMessage();
		    		
		    	}
		    	if (sentMail) {

				ActionMessages msgs = new ActionMessages();
				ActionMessage errMsg = new ActionMessage(LoginErrorCodeConstants.PASSWORD_SENT_TO_MAIL);
				msgs.add("info", errMsg);
				request.setAttribute("info", msgs);
				loginService = (LoginService) getBean("loginService");
				 session.setAttribute("UDAAN_BUILD", loginService.getjdbcOfficeBuild());
				return mapping.findForward(UmcConstants.WELCOME);
			    } else {
				return mapping.findForward(UmcConstants.FORGOTPASSWORD);
			      }  
			
		    }else{
		    	ActionMessages msgs = new ActionMessages();
				ActionMessage errMsg = new ActionMessage(LoginErrorCodeConstants.USERNAME_INVALID);
				msgs.add("warning", errMsg);
				request.setAttribute("warning", msgs);
		    	return mapping.findForward(UmcConstants.FORGOTPASSWORD);
		    }
		} catch (Exception e) {
			LOGGER.error("Exception happened in showLogin of ForgotPasswordAction..."
					,e);
			ActionMessages msgs = new ActionMessages();
			ActionMessage errMsg = new ActionMessage(LoginErrorCodeConstants.FORGOT_PASSWORD_FAIL);
			msgs.add("error", errMsg);
			request.setAttribute("error", msgs);
			loginService = (LoginService) getBean("loginService");
			 session.setAttribute("UDAAN_BUILD", loginService.getjdbcOfficeBuild());
			return mapping.findForward(UmcConstants.FAILURE);
		}
		

	}
}
