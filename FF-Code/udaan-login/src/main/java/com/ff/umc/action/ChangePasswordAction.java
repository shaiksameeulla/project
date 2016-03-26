package com.ff.umc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.umc.PasswordTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.ChangePasswordForm;
import com.ff.umc.service.LoginService;
import com.ff.umc.service.PasswordUtil;

/**
 * @author nihsingh
 *
 */
public class ChangePasswordAction extends CGBaseAction {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginAction.class);
	
	/**
	 * @Desc prepares change password view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return change password view
	 * @throws CGBusinessException
	 */
	public ActionForward showChangePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException {
		try{
		ChangePasswordForm changeform = (ChangePasswordForm) form;
		PasswordTO changePaswdTO = (PasswordTO) changeform.getTo();
		
		String loginId=request.getSession().getAttribute(UmcConstants.USER_NAME).toString();
		
		if(loginId==null)
		{
			throw new CGBusinessException(LoginErrorCodeConstants.SESSION_NULL);
		}else
		{
			changePaswdTO.setUsername(loginId);
		}
		}//end of try
		catch(CGBusinessException e){
			LOGGER.error("ChangePasswordAction::showChangePassword"+e.getMessage());
			getBusinessError(request, e);
		}
		return mapping.findForward(UmcConstants.CHANGEPASSWORD);
	}
	
	/**
	 * @Desc returns to the login page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return login view
	 */
	public ActionForward showLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ChangePasswordAction::showLogin::START------------>:::::::");	
		try
			{
			ChangePasswordForm changeform = (ChangePasswordForm) form;
			PasswordTO changePaswdTO = (PasswordTO) changeform.getTo();
			LoginService loginService = (LoginService) getBean("loginService");
			//get the context path
			String contextPath = request.getContextPath();
			
			String username=request.getSession().getAttribute(UmcConstants.USER_NAME).toString();
			String newEncryptdPaswd=PasswordUtil.getSHAEncryptedPassword(changePaswdTO.getNewPassword());
			changePaswdTO.setUserId(loginService.getUserIdByUsername(username));
			
			//validates the current password
			boolean validateCurrPaswd=loginService.validateCurrentPaswd(changePaswdTO);
			if(validateCurrPaswd){
				 boolean validatePaswd=loginService.validatePassword(changePaswdTO.getUserId(),newEncryptdPaswd);
		       if(validatePaswd){
		    	   //updates the password
		    	boolean updatePaswd=loginService.updatePassword(changePaswdTO,contextPath);
				if(updatePaswd){			
					prepareActionMessage(request, new ActionMessage(LoginErrorCodeConstants.PASSWORD_CHANGE_SUCCESSFULLY));
				}			
			  }else {
				  	prepareActionMessage(request, new ActionMessage(LoginErrorCodeConstants.SAME_LAST_TWO_PASSWORD));
				  	changePaswdTO.setUsername(request.getSession().getAttribute(UmcConstants.USER_NAME).toString());
				  	return mapping.findForward(UmcConstants.CHANGEPASSWORD);
				
			     }
		    }
        else {
        		prepareActionMessage(request, new ActionMessage(LoginErrorCodeConstants.CURRENT_PASWD_INVALID));
				changePaswdTO.setUsername(request.getSession().getAttribute(UmcConstants.USER_NAME).toString());
				return mapping.findForward(UmcConstants.CHANGEPASSWORD);
			}
			
			}//end of try block
			catch (CGBusinessException e) {
				LOGGER.error("Authentication failed or Changing the Password failed",e);
				getBusinessError(request, e);
				return mapping.findForward(UmcConstants.FAILURE);
			}
			catch (CGSystemException e) {
				LOGGER.error("Authentication failed or Changing the Password failed",e);
				prepareActionMessage(request, new ActionMessage(LoginErrorCodeConstants.DATABASE_ERROR));
				return mapping.findForward(UmcConstants.FAILURE);
			}
			catch (Exception e) {
				LOGGER.error("Authentication failed or Changing the Password failed",e);
				prepareActionMessage(request, new ActionMessage(LoginErrorCodeConstants.SERVER_ERROR));
				return mapping.findForward(UmcConstants.FAILURE);
			}
		LOGGER.trace("ChangePasswordAction::showLogin::END------------>:::::::");	
		return mapping.findForward(UmcConstants.WELCOME);
	}
	

}