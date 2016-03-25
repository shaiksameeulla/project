package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunSystemAuthService;
import com.cg.lbs.bcun.utility.BcunPopulateSysUserCodesOnStartup;
import com.ff.domain.bcun.SystemAuthDO;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServlet.
 */
@SuppressWarnings("serial")
public class LoginServlet extends CGBaseServlet {
	
	/** The Constant SERVICE_BCUN_SYSTEM_AUTH. */
	public static final String SERVICE_BCUN_SYSTEM_AUTH = "bcunSystemAuth";
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("LoginServlet::doGet:: start=======>");
		// Retrieve user info from request
		String systemUserCode = (String) request.getParameter(SplitModelConstant.USER_NAME);
		String branchCode =null;
		LOGGER.debug("LoginServlet::doGet:: request recieved for System User Code [ " + systemUserCode + " ]");
		
		// Retrieve Authorized Branch Code from map populated at server start up with key
		// as system user Code and value as Authorized Branch Code
		String authorizedBranchCodeDB = BcunPopulateSysUserCodesOnStartup.getSystemUserCodesMap().get(systemUserCode);
		LOGGER.debug("LoginServlet::doGet:: Authorized Branch Code from DB [ " + authorizedBranchCodeDB + " ]");
		
		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			try{
				if(authorizedBranchCodeDB == null){
					LOGGER.debug("LoginServlet::retrieving any authorized system user code and system user info added at runtime");
					WebApplicationContext springApplicationContext = getSpringApplicationContext();
					
					BcunSystemAuthService bcunSystmeAuthService= (BcunSystemAuthService)springApplicationContext.getBean(SERVICE_BCUN_SYSTEM_AUTH);
					branchCode = bcunSystmeAuthService.getBranchCode();
					LOGGER.debug("LoginServlet::doGet:: request recieved for Branch Code [ " + branchCode + " ]");
					
					List<SystemAuthDO> sysUserCodesList = bcunSystmeAuthService.retrieveAllSysUserCodes();
					
					if(sysUserCodesList!= null && sysUserCodesList.size()>0){
						Iterator<SystemAuthDO> itr = sysUserCodesList.iterator();
						
						while(itr.hasNext()){
							SystemAuthDO systemAuthDo = itr.next();
							BcunPopulateSysUserCodesOnStartup.getSystemUserCodesMap().put(systemAuthDo.getSystemUserCode(), systemAuthDo.getAuthBranchCode());
						}
					}
					
					authorizedBranchCodeDB = BcunPopulateSysUserCodesOnStartup.getSystemUserCodesMap().get(systemUserCode);
					/*Boolean isAuthorizedSysUser = bcunSystmeAuthService.isAuthSystemUser(systemUserCode, branchCode);
					
					if(isAuthorizedSysUser){
						LOGGER.debug("LoginServlet::doGet:: System User Authorized : System User Code  [ " + systemUserCode + " ] and System Branch Code  [ " + branchCode + " ]");
						authorizedBranchCodeDB=branchCode;
						BcunPopulateSysUserCodesOnStartup.getSystemUserCodesMap().put(systemUserCode,branchCode);
					}*/
				}
	
				if (!StringUtil.isStringEmpty(branchCode) && branchCode.equals(authorizedBranchCodeDB.trim())) {
					Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,SplitModelConstant.AUTHORISED_USER);
					response.addCookie(userCookie);
					LOGGER.debug("LoginServlet::doGet::central server authentication success.. created cookie");
				} else {
					Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,SplitModelConstant.UN_AUTHORISED_USER);
					response.addCookie(userCookie);
				}
			} catch (Exception e) {
				LOGGER.error("LoginServlet::doGet::Exception ########",e);
			}
		}
		
		/*HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			
			try {
				WebApplicationContext springApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
				
				BcunSystmeAuthService bcunSystmeAuthService= (BcunSystmeAuthService)springApplicationContext.getBean(SERVICE_BCUN_SYSTEM_AUTH);
				String branchCode = bcunSystmeAuthService.getBranchCode();
				LOGGER.debug("LoginServlet::doGet:: request recieved for Branch Code [ " + branchCode + " ]");
				Boolean isAuthorizedSysUser = bcunSystmeAuthService.isAuthSystemUser(userCode, branchCode);

				if(isAuthorizedSysUser){
					Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,SplitModelConstant.USER_LOGGED_IN);
					response.addCookie(userCookie);
					LOGGER.debug("LoginServlet::doGet::Central Server Authentication Success.. Created cookie");
				}else{
					Cookie userCookie = new Cookie(SplitModelConstant.USER_STATUS,SplitModelConstant.UN_AUTHORISED_USER);
					response.addCookie(userCookie);
					LOGGER.debug("LoginServlet::doGet::Central Server Un-Authorized User..");
				}
			} catch (Exception e) {
				LOGGER.debug("LoginServlet::doGet::Exception happened : ",e);
			}
		}*/
		LOGGER.debug("LoginServlet::doGet:: end=======>");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("LoginServlet::doPost:: start=======>");
		// Retrieve user info from request
		String systemUserCode = (String) request.getParameter(SplitModelConstant.USER_INFORMATION);
		String branchCode = (String) request.getParameter(SplitModelConstant.USER_BRANCH_CODE);
		
		LOGGER.debug("LoginServlet::doPost:: request recieved for System User Code [ " + systemUserCode + " ] and Branch Code ["+branchCode+"]");
		BcunSystemAuthService bcunSystmeAuthService= (BcunSystemAuthService)getSpringApplicationContext().getBean(SERVICE_BCUN_SYSTEM_AUTH);
		Boolean isAuthorizedSysUser =false;
		 try {
			isAuthorizedSysUser = bcunSystmeAuthService.isAuthSystemUser(systemUserCode, branchCode);
		} catch (Exception e) {
			LOGGER.error("LoginServlet::doPost:: System User Authorized : System User Code ",e);
		}
		
		if(isAuthorizedSysUser){
			LOGGER.debug("LoginServlet::doPost:: System User Authorized : System User Code  [ " + systemUserCode + " ] and System Branch Code  [ " + branchCode + " ]");
			response.addHeader(SplitModelConstant.RESPONSE_FROM_SERVER,FrameworkConstants.SUCCESS_FLAG);
		}else{
			LOGGER.debug("LoginServlet::doPost::  user configuration is not available for the user System User Code  [ " + systemUserCode + " ] and System Branch Code  [ " + branchCode + " ]");
		}
		
		
		LOGGER.debug("LoginServlet::doGet:: end=======>");
	}
}