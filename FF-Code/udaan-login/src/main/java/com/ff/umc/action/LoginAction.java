package com.ff.umc.action;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.SpringContext;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.domain.umc.MenuCompositeNodeDO;
import com.ff.domain.umc.MenuNodeDO;
import com.ff.umc.PasswordTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.LoginForm;
import com.ff.umc.service.LoginService;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.util.UdaanContextService;

public class LoginAction extends CGBaseAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);
	LoginService loginService = null;
	GlobalUniversalService globalUniversalService = null;
	private UdaanContextService udaanContextService;
	// private OrganizationCommonService organizationCommonService;
	

	public ActionForward showForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		LOGGER.debug("LoginAction::showForm ..Start");
		HttpSession session = null;
		String forwardName = UmcConstants.WELCOME;
		session = (HttpSession) request.getSession(false);
		try{
		if (!StringUtil.isNull(session.getAttribute(UmcConstants.LOGEIN_USER))) {
			forwardName = UmcConstants.SUCCESS;
		}
		loginService = (LoginService) getBean("loginService");
		globalUniversalService = (GlobalUniversalService) getBean("globalUniversalService");
		if(CGCollectionUtils.isEmpty(configurableParams)){
			configurableParams = globalUniversalService.getConfigParams();//put logger inside
		}
		}
		catch(Exception e){
			
			LOGGER.error("LoginAction::showForm :: Exception ::Error loading Config params ######",e);
			prepareActionMessage(request, "E0031");
		}
		session.setAttribute("UDAAN_BUILD", loginService.getjdbcOfficeBuild());
		return mapping.findForward(forwardName);
	}

	public ActionForward submitLoginForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		long startMilliseconds=System.currentTimeMillis();
		String returnpg = "success";
		LOGGER.debug("LoginAction::submitLoginForm::Start");
		LOGGER.debug("LoginAction::submitLoginForm::Initiating Login Process:: start Time ########## : " + startMilliseconds);
		HttpSession session = (HttpSession) request.getSession(false);

		String appName = null;
		String moduleName =	null;
		String consgNumeber = null;
		StringBuffer url = null;
		String trackingUrl = null;
		String crmUserName = null;
		String crmPassword = null;
		Boolean isRequestFromCRM = false;
		RequestDispatcher dispatcher = null;
		//CRM- Tracking Integration
		if (!StringUtil.isNull(request.getParameter("consgNumber"))){
			appName = (String) request.getParameter("appName");
			moduleName = (String) request.getParameter("moduleName");
			consgNumeber = (String) request.getParameter("consgNumber");
			crmUserName = (String) request.getParameter("userName");
			crmPassword = (String) request.getParameter("password");
			url = new StringBuffer();
			url.append("/consignmentTrackingHeader.do?submitName=viewConsignmentTracking&consgNumber=");
			url.append(consgNumeber);
			trackingUrl = url.toString();
			LOGGER.debug("LoginAction::submitLoginForm:: CRM Login: Consignment No :: " + consgNumeber + " User Name :: " + crmUserName +" Password :: " + crmPassword);
			if (!StringUtil.isNull(appName) && appName.equalsIgnoreCase("CRM")
					&& moduleName.equalsIgnoreCase("tracking")) {
				dispatcher = request.getRequestDispatcher(trackingUrl);
				isRequestFromCRM = true;
			}
		}
		// if the session is already available then just use this ,need not to create User session creation
		if (isSessionAlive(request)) {
			request.setAttribute("warning", "W0001");
			LOGGER.debug("LoginAction::submitLoginForm::Session Already Valid");
			if (isRequestFromCRM) {
				try {
					dispatcher.forward(request, response);
				} catch (ServletException | IOException e) {
					LOGGER.error("Error occured in LoginAction :: submitLoginForm() ::", e);
					prepareActionMessage(request,LoginErrorCodeConstants.UNABLE_TO_LOGIN);
					if (session != null) {
						session.invalidate();
					}
					return mapping.findForward(UmcConstants.FAILURE);
				}
			}
			return mapping.findForward(returnpg);
		}

		loginService = (LoginService) getBean("loginService");
		udaanContextService = (UdaanContextService) getBean("udaanContextService");
		globalUniversalService = (GlobalUniversalService) getBean("globalUniversalService");

		if (SpringContext.getSpringApplicationContext() == null) {
			SpringContext.setSpringApplicationContext(springApplicationContext);
		}

		// Added by Narasimha.. for setting config params into session
		try {
			LOGGER.debug("LoginAction::submitLoginForm::Getting User Credentials for Processing");
			LoginForm loginform = (LoginForm) form;
			UserTO to = (UserTO) loginform.getTo();
			PasswordTO pwdto = loginform.getPwdto();
			to.setPassword(pwdto.getPassword());
			if (isRequestFromCRM) {
				to.setUserName(crmUserName);
				to.setPassword(crmPassword);
			}

			LOGGER.debug("LoginAction::submitLoginForm::Checking Credential's Validity::");
			if (StringUtil.isStringEmpty(to.getUserName())
					|| StringUtil.isStringEmpty(to.getPassword())) {
				throw new CGBusinessException(
						LoginErrorCodeConstants.BLANK_USERNAME_PASSWORD);
			}

			LOGGER.debug("LoginAction::submitLoginForm:: Got Credentials " + to.getUserName() + "-->"
					+ pwdto.getPassword());

			if (!StringUtil.isEmptyMap(configurableParams)) {
				prepareTerminalUrls(configurableParams, request);
			} else {
				configurableParams = globalUniversalService.getConfigParams();
			}

			// Gives the value of Context Path like 'udaan-web'
			String contextPath = request.getContextPath();

			// Sets User Name in session
			session.setAttribute(UmcConstants.USER_NAME, to.getUserName());
			long authenticationStartTime=System.currentTimeMillis();
			LOGGER.debug("LoginAction::submitLoginForm::Validating User's Authencity:: starts time ##### " + authenticationStartTime);
			// Validates User's Authenticity
			UserInfoTO userInfoTO = validateUser(to.getUserName(),
					to.getPassword(), contextPath, configurableParams);
			long authenticationEndTime=System.currentTimeMillis();
			long difference=authenticationEndTime-authenticationStartTime;
			LOGGER.debug("LoginAction::submitLoginForm::Validating User's Authencity:: END  ######## Time[" + authenticationEndTime +" ]#########Difference :"+difference);
			session.setAttribute(UmcConstants.USER_NAME, userInfoTO.getUserto()
					.getUserName());

			udaanContextService.setUserInfoTO(userInfoTO);

			if (session != null) {
				userInfoTO.getUserto().setSessionTimeout(
						session.getMaxInactiveInterval());
				session.setAttribute(UmcConstants.USER_INFO, userInfoTO);
				session.setAttribute(UmcConstants.WELCOME_USERNAME,
						userInfoTO.getWelcomeUserName());
				session.setAttribute(UmcConstants.USER_NAME, userInfoTO
						.getUserto().getUserName());
			}

			// Code for page access
			ServletContext ctx = request.getSession().getServletContext();
			String contextName = ctx.getServletContextName();
			LOGGER.debug("contextName in LoginAction :: submitLoginForm() ::"
					+ contextName);

			// added by narmdr to for tree menu
			// Generates Menu Map
			LinkedHashMap<String, MenuNodeDO> itemsMap = loginService
					.getAccessScreensForUser(
							userInfoTO.getUserto().getUserId(), userInfoTO
							.getUserto().getUserRoles(), contextName);
			session.setAttribute("udaanTreeMenus", itemsMap);
			//CRM
			if(isRequestFromCRM){
				dispatcher.forward(request, response);
				//mapping.findForward(trackingUrl);
			}

		} catch (CGBusinessException businessException) {
			LOGGER.error("Error occured in LoginAction :: submitLoginForm() ::"
					+ businessException.getMessage());

			if (businessException.getMessage().equals("changepassword")) {
				return mapping.findForward(UmcConstants.CHANGEPASSWORD);
			}
			getBusinessError(request, businessException);
			if (session != null) {
				session.invalidate();
			}
			return mapping.findForward(UmcConstants.FAILURE);
		} catch (CGSystemException systemException) {
			LOGGER.error(
					"Error occured in LoginAction :: submitLoginForm() ::", systemException);
			getSystemException(request, systemException);
			if (session != null) {
				session.invalidate();
			}
			return mapping.findForward(UmcConstants.FAILURE);
		} catch (Exception exception) {
			LOGGER.error(
					"Error occured in LoginAction :: submitLoginForm() ::", exception);
			prepareActionMessage(request,
					LoginErrorCodeConstants.UNABLE_TO_LOGIN);
			if (session != null) {
				session.invalidate();
			}
			return mapping.findForward(UmcConstants.FAILURE);
		}
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug("LoginAction::submitLoginForm ..END login process ################### End time :["
				+ endMilliSeconds +"] Difference :"+diff);
		return mapping.findForward(returnpg);
	}

	private void prepareTerminalUrls(Map<String, String> configurableParams,
			HttpServletRequest request) {
		
		LOGGER.debug("LoginAction::prepareTerminalUrls::STARTS----->");
		
		String requestIpAddress = request.getRemoteHost();
		LOGGER.debug("LoginAction::prepareTerminalUrls::Remote Host IP : " + requestIpAddress);
		
		// To check ip address for IPv6 0:0:0:0:0:0:0:1
		if (requestIpAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			requestIpAddress = "127.0.0.1";// localhost
		}
		
		// Prepare Weight Reader URL
		String terminalCtxName = configurableParams.get("TERMINAL_CONTEXT_NAME");
		String terminalCtxPort = configurableParams.get("TERMINAL_CONTEXT_PORT");
		String terminalWeightReaderUrl = configurableParams.get("TERMINAL_WEIGHT_READER_URL");
		String terminalMacReaderURL = configurableParams.get("TERMINAL_MAC_ADDR_READER_URL");
		
		StringBuilder weightReaderUrl = new StringBuilder("http://");
		weightReaderUrl.append(requestIpAddress);
		weightReaderUrl.append(":");
		weightReaderUrl.append(terminalCtxPort);
		weightReaderUrl.append("/");
		weightReaderUrl.append(terminalCtxName);
		weightReaderUrl.append("/");
		weightReaderUrl.append(terminalWeightReaderUrl);
		
		LOGGER.debug("LoginAction::prepareTerminalUrls::Weight Reader URL : " + weightReaderUrl);

		request.getSession().setAttribute(
				UdaanCommonConstants.WEIGHT_READER_URL,
				weightReaderUrl.toString());
		
		// Prepare MAC Reader URL
		StringBuilder macReaderUrl = new StringBuilder("http://");
		macReaderUrl.append(requestIpAddress);
		macReaderUrl.append(":");
		macReaderUrl.append(terminalCtxPort);
		macReaderUrl.append("/");
		macReaderUrl.append(terminalCtxName);
		macReaderUrl.append("/");
		macReaderUrl.append(terminalMacReaderURL);

		request.getSession().setAttribute(UdaanCommonConstants.MAC_ADDRESS_URL,
				macReaderUrl.toString());
		
		// Prepare Print URL Added By Himal JDynamiTe 2.0
		String terminalPrintJobUrl = configurableParams.get("TERMINAL_PRINT_JOB_URL"); // printJob
		StringBuilder printJobUrl = new StringBuilder("http://");
		printJobUrl.append(requestIpAddress);
		printJobUrl.append(":");
		printJobUrl.append(terminalCtxPort);
		printJobUrl.append("/");
		printJobUrl.append(terminalCtxName);
		printJobUrl.append("/");
		printJobUrl.append(terminalPrintJobUrl);
		request.getSession().setAttribute(UdaanCommonConstants.PRINT_JOB_URL, printJobUrl.toString());
		
		// Prepare URL for bulk printing Added by Tejas
		String terminalBulkBillPrintJobUrl = configurableParams.get("TERMINAL_BULK_BILL_PRINT_JOB_URL"); // bulkBillPrint
		/** Line added for testing purpose **/
		terminalBulkBillPrintJobUrl = "bulkBillPrint";  
		StringBuilder bulkBillPrintJobUrlBuilder = new StringBuilder("http://");
		bulkBillPrintJobUrlBuilder.append(requestIpAddress);
		bulkBillPrintJobUrlBuilder.append(":");
		bulkBillPrintJobUrlBuilder.append(terminalCtxPort);
		bulkBillPrintJobUrlBuilder.append("/");
		bulkBillPrintJobUrlBuilder.append(terminalCtxName);
		bulkBillPrintJobUrlBuilder.append("/");
		bulkBillPrintJobUrlBuilder.append(terminalBulkBillPrintJobUrl);
		request.getSession().setAttribute(UdaanCommonConstants.BULK_BILL_PRINT_JOB_URL, bulkBillPrintJobUrlBuilder.toString());
	}

	public ActionForward redirectHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String returnpg = "success";
		return mapping.findForward(returnpg);
	}

	/**
	 * Authenticate user.
	 * 
	 * @param to
	 *            the to
	 * @return the user to
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*private UserTO authenticateUser(UserTO to, String contextPath)
			throws CGBusinessException, CGSystemException {
		LoginService loginService = (LoginService) getBean("loginService");
		LOGGER.debug("LoginAction::authenticateUser ..Start");
		return loginService.authenticateUser(to, contextPath);
	}*/
	
	
	private UserInfoTO validateUser(String username,String password, String contextPath,Map<String,String> configParam)
			throws CGBusinessException, CGSystemException {
		LoginService loginService = (LoginService) getBean("loginService");
		LOGGER.debug("LoginAction::validateUser::--->>");
		return  loginService.validateUser(username,password, contextPath,configParam);
	}

	/*
	 * private boolean isPasswordExpired() throws CGBusinessException {
	 * 
	 * LoginService loginService =(LoginService) getBean("loginService");
	 * 
	 * //return loginService. }
	 */

	private List<Integer> getUserRoles(UserTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("LoginAction::getUserRoles ..Start");
		loginService = (LoginService) getBean("loginService");
		return loginService.getUserRoles(to);
	}
	private List<String> getUserRolesList(UserTO to) throws CGBusinessException,
	CGSystemException {
LOGGER.debug("LoginAction::getUserRoles ..Start");
loginService = (LoginService) getBean("loginService");
return loginService.getUserRolesList(to);
}

	// Added by Narasimha for Silent Login
	public ActionForward silentLoginUdaan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("LoginAction::silentLoginUdaan ..Start"+System.currentTimeMillis());
		String navigateTo = null;
		Map<String, String> configurableParams = null;
		// String macAddress = "";
		UserTO userTO = null;
		UserInfoTO userInfoTO = null;
		String welcomeUserName = "";
		String appName = "";
		String userName = "";
		String screenCode = "";
		String moduleName = "";
		try {
			
			invalidateSession(request);
			userName = request.getParameter("userName") != null ? request
					.getParameter("userName") : null;
			appName = request.getParameter("appName") != null ? request
					.getParameter("appName") : null;
			screenCode = request.getParameter("screenCode") != null ? request
					.getParameter("screenCode") : null;
			moduleName = request.getParameter("moduleName") != null ? request
					.getParameter("moduleName") : null;
			LoginService loginService = (LoginService) getBean("loginService");
			UserTO to = new UserTO();
			to.setUserName(userName);
			userTO = loginService.getUserByUser(to);
			if (StringUtil.isNull(userTO))
				throw new CGBusinessException(
						LoginErrorCodeConstants.INVALID_USERID);
			HttpSession session = (HttpSession) request.getSession(true);
			globalUniversalService = (GlobalUniversalService) getBean("globalUniversalService");
			configurableParams = globalUniversalService.getConfigParams();
			udaanContextService = (UdaanContextService) getBean("udaanContextService");
			if (!StringUtil.isEmptyMap(configurableParams)) {
				prepareTerminalUrls(configurableParams, request);
			}
			// commented by niharika with anwars confirmation
			// taking time to get the mac request url from client
			
			/*
			String url = (String) request.getSession().getAttribute(
					UdaanCommonConstants.MAC_ADDRESS_URL);
			if (url != null) {
				LOGGER.debug("Reading from terminal :" + url);
				macAddress = TerminalUtil.getTerminalMacAddress(url);
			}
			
			to.setMacAddressLogInUser(macAddress);
			*/
			boolean isPwdExpired = loginService.isPasswordExpired(userTO
					.getUserId());
			
			// To Check if password is expired
			if ((isPwdExpired)) {
				loginService.updatePwdChangeRequiredFlag(userTO.getUserId());
				session.setAttribute(UmcConstants.USER_NAME,
						userTO.getUserName());
				navigateTo = UmcConstants.CHANGEPASSWORD;
			} else {
				userInfoTO = new UserInfoTO();
				String userType = userTO.getUserType();
				if (!StringUtil.isStringEmpty(userType)
						&& userType.trim().equalsIgnoreCase(
								UmcConstants.SRC_ACCESS_TO_FFCL)) {
					userInfoTO.setEmpUserTo(loginService.getEmpUserInfo(userTO
							.getUserId()));
					userInfoTO.setOfficeTo(loginService
							.getOfficeByempId(userInfoTO.getEmpUserTo()
									.getEmpTO().getEmployeeId()));
					welcomeUserName = userInfoTO.getEmpUserTo().getEmpTO()
							.getFirstName()

							+ " "
							+ userInfoTO.getEmpUserTo().getEmpTO()
									.getLastName();
				} else if (!StringUtil.isStringEmpty(userType)
						&& userType.trim().equalsIgnoreCase(
								UmcConstants.SRC_ACCESS_TO_CUSTOMER)) {
					userInfoTO.setCustUserTo(loginService
							.getCustUserInfo(userTO.getUserId()));
					
					userInfoTO.setOfficeTo(loginService.getOfficeByCustId(userInfoTO.getCustUserTo().getCustTO().getCustomerId()));

					if (userInfoTO.getCustUserTo().getCustTO().getStatus()
							.equalsIgnoreCase("I")) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.CUSTOMER_INACTIVE);
					}
					
					welcomeUserName = userInfoTO.getCustUserTo().getCustTO()
							.getBusinessName();
				}
				if (!StringUtil.isEmptyMap(configurableParams)) {
					userInfoTO.setConfigurableParams(configurableParams);
				}
				List<Integer> roleList = getUserRoles(userTO);
				userTO.setUserRoles(roleList);
				userTO.setUserRoleList(getUserRolesList(userTO));//Newly Added method ,to get current user role list
				userInfoTO.setUserto(userTO);
				udaanContextService.setUserInfoTO(userInfoTO);
				if (session != null) {
					userTO.setSessionTimeout(session.getMaxInactiveInterval());
					session.setAttribute(UmcConstants.USER_INFO, userInfoTO);
					session.setAttribute(UmcConstants.WELCOME_USERNAME,
							welcomeUserName);
					session.setAttribute(UmcConstants.USER_NAME,
							userTO.getUserName());
				}
				LOGGER.debug("Application Name in LoginAction :: silentLoginUdaan() ::"
						+ appName);
				// added by narmdr to for tree menu
				LinkedHashMap<String, MenuNodeDO> itemsMap = loginService
						.getAccessScreensForUser(userTO.getUserId(),
								userTO.getUserRoles(), appName);
				if (itemsMap != null) {
					session.setAttribute("udaanTreeMenus", itemsMap);
					navigateTo = getMenuUrl(itemsMap, moduleName, screenCode,appName);

				} else {
					throw new CGBusinessException(
							LoginErrorCodeConstants.MENUS_NOT_CONFIGURED);
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LoginAction :: silentLoginUdaan() ::"
					,e);
			getBusinessError(request, e);
			return mapping.findForward(UmcConstants.FAILURE);
		} catch (Exception e) {
			LOGGER.error("Error occured in LoginAction :: silentLoginUdaan() ::"
					,e);
			prepareActionMessage(request, new ActionMessage(
					LoginErrorCodeConstants.SERVER_ERROR));
			return mapping.findForward(UmcConstants.FAILURE);
		}
		// RequestDispatcher rd = request.getRequestDispatcher(navigateTo);
		// rd.forward(request, response);
		response.sendRedirect(navigateTo);
		LOGGER.debug("LoginAction::silentLoginUdaan ..END"+System.currentTimeMillis());
		return null;
	}

	public ActionForward silentLoginToApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoginAction::silentLoginToApp ..START"+System.currentTimeMillis());
		Map<String, String> configurableParams = null;
		UserTO user = null;
		StringBuilder fwdAction = new StringBuilder();
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		String configAdminUrl = "";
		String configAdminPort = "";
		String userName = "";
		String appName = "";
		String configAdminIpAddress = "";
		String screenCode = "";
		String moduleName;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			if(!isSessionAlive(request)){
				/**
				 * if the session is not available then redirect to login page to login
				 */
				return mapping.findForward(UmcConstants.WELCOME);
			}
			user = userInfoTO.getUserto();
			configurableParams = userInfoTO.getConfigurableParams();
			screenCode = request.getParameter("screenCode");
			moduleName = request.getParameter("moduleName");
			appName = request.getParameter("appName");
			if (StringUtils.isNotEmpty(screenCode)) {
				configurableParams = userInfoTO.getConfigurableParams();
				if (configurableParams != null && !configurableParams.isEmpty()) {
					if (StringUtils.equalsIgnoreCase(
							UmcConstants.APP_NAME_CONFIG_ADMIN, appName)) {
						configAdminUrl = configurableParams
								.get(UmcConstants.UDAAN_CONFIG_ADMIN_SILENT_LOGIN_URL);
						configAdminPort = configurableParams
								.get(UmcConstants.UDAAN_CONFIG_ADMIN_PORT);
						configAdminIpAddress = configurableParams
								.get(UmcConstants.UDAAN_CONSIG_ADMIN_IP_ADDRESS);
					} else if (StringUtils.equalsIgnoreCase(
							UmcConstants.APP_NAME_WEB, appName)) {
						configAdminUrl = configurableParams
								.get(UmcConstants.UDAAN_WEB_SILENT_LOGIN_URL);
						configAdminPort = configurableParams
								.get(UmcConstants.UDAAN_WEB_PORT);
						configAdminIpAddress = configurableParams
								.get(UmcConstants.UDAAN_WEB_IP_ADDRESS);
					} // added by kamal for silent login to udaan-report
					else if (StringUtils.equalsIgnoreCase(
							UmcConstants.APP_NAME_UDAAN_REPORT, appName)) {
						configAdminUrl = configurableParams
								.get(UmcConstants.UDAAN_REPORT_SILENT_LOGIN_URL);
						configAdminPort = configurableParams
								.get(UmcConstants.UDAAN_REPORT_PORT);
						configAdminIpAddress = configurableParams
								.get(UmcConstants.UDAAN_REPORT_IP_ADDRESS);

					}else if (StringUtils.equalsIgnoreCase(
							UmcConstants.APP_NAME_UDAAN_CENTRAL, appName)) {
						/*configAdminUrl = "/udaan-central-server/manualDownloadAuthenticServlet.ff?";
						configAdminPort = "8380";
						configAdminIpAddress = "localhost";*/
						
						configAdminUrl = configurableParams
								.get(UmcConstants.UDAAN_CENTRAL_SERVER_DOWNLOAD_URL);
						configAdminPort = configurableParams
								.get(UmcConstants.UDAAN_CENTRAL_SERVER_PORT);
						configAdminIpAddress = configurableParams
								.get(UmcConstants.UDAAN_CENTRAL_SERVER_ID_ADDRESS);
						
						//Cookie cook = new Cookie("UDAAN","UDAAN"); 
						//cook.setPath("/");  
						//response.addCookie(cook); 
						response.addHeader("UDAAN", "UDAAN");
					}
				}
				userName = user.getUserName();
				if (StringUtils.isNotEmpty(configAdminIpAddress)
						&& StringUtils.isNotEmpty(configAdminPort)
						&& StringUtils.isNotEmpty(userName)) {
					fwdAction.append(UmcConstants.HTTP);
					fwdAction.append(configAdminIpAddress + ":");
					fwdAction.append(configAdminPort);
					fwdAction.append(configAdminUrl);
					fwdAction.append("&userName=" + userName);
					fwdAction.append("&screenCode=" + screenCode);
					fwdAction.append("&moduleName=" + moduleName);
					fwdAction.append("&appName=" + appName);
				}
			}
			
			response.sendRedirect(fwdAction.toString());
		} catch (Exception ex) {
			LOGGER.error("Exception Occurred : silentLoginToApp :"
					, ex);
		}
		LOGGER.debug("LoginAction::silentLoginToApp ..END"+System.currentTimeMillis());
		return null;

	}

	private String getMenuUrl(LinkedHashMap<String, MenuNodeDO> itemsMap,
			String moduleName, String screenCode, String appName) {
		LOGGER.debug("LoginAction : getMenuUrl :START"+System.currentTimeMillis() );
		String menuUrl = "";

		for (String key : itemsMap.keySet()) {
			if (StringUtils.equalsIgnoreCase(key, moduleName)) {
				MenuNodeDO menuNodeTO = (MenuNodeDO) itemsMap.get(key);
				if (!StringUtil.isNull(menuNodeTO)) {
					Set<MenuCompositeNodeDO> menus = (Set<MenuCompositeNodeDO>) menuNodeTO.getMenuNodeDOs();
					for (MenuNodeDO menu : menus) {
						// Level - 1
						if (!StringUtil.isEmptyColletion(menu.getMenuNodeDOs())) {
							for (MenuNodeDO subMenu1 : menu.getMenuNodeDOs()) {
								// Level - 2
								if (!StringUtil.isEmptyColletion(subMenu1
										.getMenuNodeDOs())) {
									for (MenuNodeDO subMenu2 : subMenu1
											.getMenuNodeDOs()) {
										// Level - 3
										if (!StringUtil
												.isEmptyColletion(subMenu2
														.getMenuNodeDOs())) {
											for (MenuNodeDO subMenu3 : subMenu2
													.getMenuNodeDOs()) {
												menuUrl = generateUrl(
														screenCode, appName,
														menuUrl, subMenu3);
											}
										} else {
											menuUrl = generateUrl(screenCode,
													appName, menuUrl, subMenu2);
										}
									}
								} else {
									menuUrl = generateUrl(screenCode, appName,
											menuUrl, subMenu1);
								}
							}
						} else {
							menuUrl = generateUrl(screenCode, appName, menuUrl,
									menu);
						}
					}
				}
			}
		}
		LOGGER.debug("LoginAction : getMenuUrl :END"+System.currentTimeMillis() );
		return menuUrl;
	}

	private String generateUrl(String screenCode, String appName,
			String menuUrl, MenuNodeDO menu) {
		if (!StringUtil.isNull(menu.getApplScreenDO())) {
			if (StringUtils.equalsIgnoreCase(menu.getApplScreenDO()
					.getScreenCode(), screenCode)) {
				menuUrl = "/" + appName + "/"
						+ menu.getApplScreenDO().getUrlName();
			}
		}
		return menuUrl;
	}

	public UserInfoTO getLoginUserInfoTO(HttpServletRequest request) {
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		return userInfo;
	}

	/**
	 * isSessionAlive :: check whether session is exist or not
	 * 
	 * @param request
	 * @return boolean
	 */

	public boolean isSessionAlive(HttpServletRequest request) {
		UserTO userInfo = getLoginUserTO(request);
		if (userInfo != null) {
			return true;
		}
		return false;
	}

	public void removeSession(HttpServletRequest request) {
		UserTO userInfo = getLoginUserTO(request);
		if (userInfo != null) {
			HttpSession session = request.getSession(Boolean.FALSE);
			session.removeAttribute(UmcConstants.USER_INFO);
		}
	}

	public void invalidateSession(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		UserTO userInfo = getLoginUserTO(request);
		if (userInfo != null) {
			HttpSession session = request.getSession(Boolean.FALSE);
			if (StringUtil.isStringEmpty(userName)
					|| StringUtil.isStringEmpty(userInfo.getUserName())
					|| !userInfo.getUserName().equalsIgnoreCase(userName)) {
				LOGGER.trace("LoginAction::invalidateSession ..Session invalidating due to login mismatch for Silent login");
				session.invalidate();
			}
		}
	}

	/**
	 * Name : getLoginUserTO purpose : to get UserTO from Session Input :
	 * HttpServletRequest request return : UserTO.
	 * 
	 * @param request
	 *            the request
	 * @return the login user to
	 */
	public UserTO getLoginUserTO(HttpServletRequest request) {
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if (userInfo != null) {
			return userInfo.getUserto();
		}
		return null;
	}
}