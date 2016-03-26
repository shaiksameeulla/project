package com.ff.umc.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.CustomerUserDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.LogInOutDetlDO;
import com.ff.domain.umc.MenuCompositeNodeDO;
import com.ff.domain.umc.MenuNodeDO;
import com.ff.domain.umc.PasswordDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserOfficeRightsMappingDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.MenuNodeTO;
import com.ff.umc.PasswordTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.action.UserJoinBean;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.LoginDAO;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;

public class LoginServiceImpl implements LoginService {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	private LoginDAO loginDAO;

	// private EmailSenderService emailSenderService;

	private EmailSenderUtil emailSenderUtil;

	private String jdbcOffice;

	private OrganizationCommonService organizationCommonService;

	private GlobalUniversalService globalUniversalService;

	private String jdbcOfficeCode;

	private String jdbcBuild;
	
	
	private Properties customerLoginDetailsProperties;
	

	public void setCustomerLoginDetailsProperties(
			Properties customerLoginDetailsProperties) {
		this.customerLoginDetailsProperties = customerLoginDetailsProperties;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param globalUniversalService
	 *            the globalUniversalService to set
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	/**
	 * @param jdbcOffice
	 *            the jdbcOffice to set
	 */
	public void setJdbcOffice(String jdbcOffice) {
		this.jdbcOffice = jdbcOffice;
	}

	/**
	 * @return the jdbcOffice
	 */
	public String getJdbcOffice() {
		return jdbcOffice;
	}

	/**
	 * @return the jdbcOfficeCode
	 */
	public String getJdbcOfficeCode() {
		return jdbcOfficeCode;
	}

	/**
	 * @param jdbcOfficeCode
	 *            the jdbcOfficeCode to set
	 */
	public void setJdbcOfficeCode(String jdbcOfficeCode) {
		this.jdbcOfficeCode = jdbcOfficeCode;
	}

	/*
	 * public void setEmailSenderService(EmailSenderService emailSenderService)
	 * { this.emailSenderService = emailSenderService; }
	 */

	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	/**
	 * @return the jdbcBuild
	 */
	public String getJdbcBuild() {
		return jdbcBuild;
	}

	/**
	 * @param jdbcBuild
	 *            the jdbcBuild to set
	 */
	public void setJdbcBuild(String jdbcBuild) {
		this.jdbcBuild = jdbcBuild;
	}

	private Boolean isUserLocked(UserDO userDO) {
		// Check if the user is locked
		if (userDO.getLocked() != null
				&& userDO.getLocked().equals(UmcConstants.FLAG_Y)) {
			return true;
		}
		return false;
	}

	public String getEncryptedPassword(String password)
			throws CGBusinessException {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance(UmcConstants.SHA);
			md.reset();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(
					"Error occured in LoginServiceImpl :: getEncryptedPassword()..:",
					e);
			throw new CGBusinessException(e);
		}

		try {
			byte[] theTextToDigestAsBytes = password
					.getBytes(UmcConstants.UTF_8);/* encoding */
			md.update(theTextToDigestAsBytes);// md.update( int ) processes only
												// the low order 8-bits.
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Error occured in LoginServiceImpl :: getEncryptedPassword()..:"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}

		// It actually expects an unsigned byte.
		byte[] digest = md.digest();

		// convert the byte to hex format method
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(0xFF & digest[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public Boolean checkLastLogin(UserJoinBean userJoinBean,
			Map<String, String> configParam) throws CGSystemException {

		Date logInDate = userJoinBean.getUser().getLastLoginDate();
		int maxNotLoggedInDays = 0;

		if (logInDate != null) {
			long days = DateUtil.DayDifferenceBetweenTwoDates(logInDate,
					new java.util.Date());

			String param = configParam.get("MAX_NOT_LOGGED_IN_DAYS");
			if (param != null) {
				maxNotLoggedInDays = Integer.parseInt(param);
			}

			if (days > maxNotLoggedInDays) {
				return true;
			} else {
				return false;
			}

		}
		return false;

	}

	public void insertLoginTime(UserTO userTO) throws CGBusinessException,
			CGSystemException {
		LogInOutDetlDO logdo = new LogInOutDetlDO();
		logdo.setUserId(userTO.getUserId());
		Date date = new Date();
		logdo.setLogInDate(date);
		logdo.setjSessionId(userTO.getCurrentSessionId());
		logdo.setMacAddress(userTO.getMacAddressLogInUser());
		loginDAO.insertLoginLogoutTime(logdo);

	}

	public LogInOutDetlDO getLastLogin(UserDO userdo) throws CGSystemException {
		LogInOutDetlDO loggeddomain = loginDAO.getLastLogin(userdo);
		return loggeddomain;
	}

	public PasswordTO getPassword(UserJoinBean userJoinBean)
			throws CGBusinessException, CGSystemException {
		// PasswordDO passworddo = loginDAO.getPassword(userId);
		PasswordDO passworddo = userJoinBean.getPaswd();
		PasswordTO to = new PasswordTO();
		if (passworddo != null) {
			CGObjectConverter.createToFromDomain(passworddo, to);
		} else
			return null;
		return to;
	}

	private void updateLoginAttempt(UserDO userDO) throws CGSystemException {
		// Update the login attempt of this user
		Integer attempt = userDO.getLoginAttempt();
		if (attempt == null) {
			attempt = 0;// set to zero the first time if the user is logged in
			loginDAO.updateLoginAttempt(userDO);
			return;
		}

		attempt++;
		userDO.setLoginAttempt(attempt);
		loginDAO.updateLoginAttempt(userDO);
	}

	private void updateSuccessfulLoginAttempt(UserDO userDO)
			throws CGSystemException {

		loginDAO.updateLoginAttempt(userDO);
	}

	public void lockUser(UserDO userdo) throws CGSystemException {

		loginDAO.lockUser(userdo);

	}

	private void checkLoginAttempts(UserDO userDO,
			Map<String, String> configParam) throws CGBusinessException,
			CGSystemException {
		// Check the no of attempts of login. If it more than 5, lock the
		// password and show the user a message that username is locked
		LOGGER.debug("LoginServiceImpl : checkLoginAttempts"
				+ System.currentTimeMillis());
		String param = configParam.get("MAX_ALLOWED_LOGIN_ATTEMPTS");
		int attempt = param != null ? Integer.parseInt(param)
				: UmcConstants.MAX_LOGIN_ATTEMPTS;

		if ((userDO.getLoginAttempt() != null && userDO.getLoginAttempt() == (attempt - 1))) {
			userDO.setLocked(UmcConstants.FLAG_Y);
			userDO.setActive(UmcConstants.FLAG_N);
			this.lockUser(userDO);
			throw new CGBusinessException(LoginErrorCodeConstants.USER_LOCKED);// Set
																				// the
																				// message
																				// and
																				// throw
																				// exception
		}
		LOGGER.debug("LoginServiceImpl : checkLoginAttempts"
				+ System.currentTimeMillis());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap<String, MenuNodeDO> getAccessScreensForUser(
			Integer userid, List<Integer> userRoles, String appsName)
			throws CGBusinessException {
		LOGGER.debug("LoginServiceImpl : getAccessScreensForUser:::::START"
				+ System.currentTimeMillis());
		List<MenuNodeDO> sortedNods = null;
		LinkedHashMap<String, MenuNodeDO> hashMap = new LinkedHashMap<>();
		try {
			if (userRoles != null && !userRoles.isEmpty()) {
				// Added for silent login

				List<Integer> screenIds = loginDAO
						.getAccessScreensIdsByUserRole(userRoles, appsName);

				if (screenIds != null && screenIds.size() > 0) {

					List<MenuNodeDO> menuLeafNodeDOs = loginDAO
							.getMenuNodesByScreenIds(screenIds, appsName);

					LOGGER.debug("LoginServiceImpl : getAccessScreensForUser::for loop for menu::Start"
							+ System.currentTimeMillis());
					sortedNods = new ArrayList(menuLeafNodeDOs.size());
					for (MenuNodeDO menuLeafNodeDO : menuLeafNodeDOs) {

						MenuNodeDO menuNodeDO = menuLeafNodeDO.getMenuNodeDO() == null ? menuLeafNodeDO
								: menuLeafNodeDO.getMenuNodeDO()
										.getMenuNodeDO() == null ? menuLeafNodeDO
										.getMenuNodeDO()
										: menuLeafNodeDO.getMenuNodeDO()
												.getMenuNodeDO()
												.getMenuNodeDO() == null ? menuLeafNodeDO
												.getMenuNodeDO()
												.getMenuNodeDO()
												: menuLeafNodeDO
														.getMenuNodeDO()
														.getMenuNodeDO()
														.getMenuNodeDO();

						MenuNodeDO filteredMenuNodeDO = filterMenueNode(
								menuNodeDO, screenIds, appsName);

						sortedNods.add(filteredMenuNodeDO);
						/*
						 * hashMap.put(filteredMenuNodeDO.getMenuLabel(),
						 * filteredMenuNodeDO);
						 */

					}

					// Collection<MenuNodeDO> values =hashMap.values();
					Collections.sort(sortedNods);
					for (MenuNodeDO sortedNode : sortedNods) {
						if (sortedNode.getStatus().equalsIgnoreCase("A")) {
							hashMap.put(sortedNode.getMenuLabel(), sortedNode);
						}
					}
					LOGGER.debug("LoginServiceImpl : getAccessScreensForUser::for loop for menu::END"
							+ System.currentTimeMillis());
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in LoginServiceImpl :: ..:getAccessScreensForUser()",
					e);
			throw new CGBusinessException(LoginErrorCodeConstants.DB_ERROR);
		}
		if (CGCollectionUtils.isEmpty(hashMap)) {
			throw new CGBusinessException(
					LoginErrorCodeConstants.MENUS_NOT_CONFIGURED);
		}

		// return menuListItems;
		LOGGER.debug("LoginServiceImpl : getAccessScreensForUser:::::END"
				+ System.currentTimeMillis());

		// return finalHashMap;
		return hashMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MenuNodeDO filterMenueNode(MenuNodeDO menuNodeDO,
			List<Integer> screenIds, String appsName)
			throws CGBusinessException {

		Set<MenuCompositeNodeDO> menuNodeDOs = menuNodeDO.getMenuNodeDOs();

		if (!StringUtil.isEmptyColletion(menuNodeDOs)) {
			/** ############ Level1 Start #################### **/
			List<MenuNodeDO> l1MenuNodeDOs = new ArrayList<>();
			for (MenuNodeDO subMenuNodeDO : menuNodeDOs) {

				if (!isValidScreenForUser(subMenuNodeDO, screenIds)) {
					continue;
				}

				/** ############ Level2 Start #################### **/
				if (!StringUtil
						.isEmptyColletion(subMenuNodeDO.getMenuNodeDOs())) {
					List<MenuNodeDO> l2MenuNodeDOs = new ArrayList<>();
					for (MenuNodeDO subMenuNodeDO2 : subMenuNodeDO
							.getMenuNodeDOs()) {

						if (!isValidScreenForUser(subMenuNodeDO2, screenIds)) {
							continue;
						}

						/** ############ Level3 Start #################### **/
						if (!StringUtil.isEmptyColletion(subMenuNodeDO2
								.getMenuNodeDOs())) {
							List<MenuNodeDO> l3MenuNodeDOs = new ArrayList<>();
							for (MenuNodeDO subMenuNodeDO3 : subMenuNodeDO2
									.getMenuNodeDOs()) {

								if (!isValidScreenForUser(subMenuNodeDO3,
										screenIds)) {
									continue;
								}
								/**
								 * ############ Level4 Start
								 * ####################
								 **/
								if (!StringUtil.isEmptyColletion(subMenuNodeDO3
										.getMenuNodeDOs())) {
									List<MenuNodeDO> l4MenuNodeDOs = new ArrayList<>();
									for (MenuNodeDO subMenuNodeDO4 : subMenuNodeDO3
											.getMenuNodeDOs()) {

										if (!isValidScreenForUser(
												subMenuNodeDO4, screenIds)) {
											continue;
										}
										if (subMenuNodeDO4 != null)
											l4MenuNodeDOs.add(subMenuNodeDO4);
										// TODO add level here
									}
									if (l4MenuNodeDOs.isEmpty()) {
										subMenuNodeDO3 = null;
									} else {
										Collections.sort(l4MenuNodeDOs);
										subMenuNodeDO3
												.setMenuNodeDOs(new TreeSet(
														l4MenuNodeDOs));
									}

								}
								/** ############ Level4 End #################### **/
								if (subMenuNodeDO3 != null)
									l3MenuNodeDOs.add(subMenuNodeDO3);

							}
							if (l3MenuNodeDOs.isEmpty()) {
								subMenuNodeDO2 = null;
							} else {
								Collections.sort(l3MenuNodeDOs);
								subMenuNodeDO2.setMenuNodeDOs(new TreeSet(
										l3MenuNodeDOs));
							}
						}
						/** ############ Level3 End #################### **/
						if (subMenuNodeDO2 != null)
							l2MenuNodeDOs.add(subMenuNodeDO2);
					}
					if (l2MenuNodeDOs.isEmpty()) {
						subMenuNodeDO = null;
					} else {
						Collections.sort(l2MenuNodeDOs);
						subMenuNodeDO
								.setMenuNodeDOs(new TreeSet(l2MenuNodeDOs));
					}

				}
				/** ############ Level2 End #################### **/
				if (subMenuNodeDO != null)
					l1MenuNodeDOs.add(subMenuNodeDO);
			}
			if (l1MenuNodeDOs.isEmpty()) {
				menuNodeDO = null;
			} else {
				Collections.sort(l1MenuNodeDOs);
				menuNodeDO.setMenuNodeDOs(new TreeSet(l1MenuNodeDOs));
			}

			/** ############ Level1 End #################### **/
		}
		// menuNodeDO.getM
		return menuNodeDO;
	}

	@SuppressWarnings("unused")
	private MenuNodeTO convertMenuNodeDOToMenuNodeTO(MenuNodeDO menuNodeDO,
			List<Integer> screenIds, String appsName)
			throws CGBusinessException {

		MenuNodeTO menuNodeTO = new MenuNodeTO();
		CGObjectConverter.createToFromDomain(menuNodeDO, menuNodeTO);

		Set<MenuCompositeNodeDO> menuNodeDOs = menuNodeDO.getMenuNodeDOs();

		if (!StringUtil.isEmptyColletion(menuNodeDOs)) {
			// convertAndSetMenuNodeDOsInMenuNodeTO(menuNodeDOs, menuNodeTO);

			/** ############ Level1 Start #################### **/
			List<MenuNodeTO> menuNodeTOs = new ArrayList<>();
			for (MenuNodeDO subMenuNodeDO : menuNodeDOs) {

				if (!isValidScreenForUser(subMenuNodeDO, screenIds)) {
					continue;
				}

				MenuNodeTO subMenuNodeTO = new MenuNodeTO();
				convertMenuNodeDOToTO(subMenuNodeDO, subMenuNodeTO, appsName);
				menuNodeTOs.add(subMenuNodeTO);

				/** ############ Level2 Start #################### **/
				if (!StringUtil
						.isEmptyColletion(subMenuNodeDO.getMenuNodeDOs())) {
					List<MenuNodeTO> menuNodeTOs2 = new ArrayList<>();
					for (MenuNodeDO subMenuNodeDO2 : subMenuNodeDO
							.getMenuNodeDOs()) {

						if (!isValidScreenForUser(subMenuNodeDO2, screenIds)) {
							continue;
						}

						MenuNodeTO subMenuNodeTO2 = new MenuNodeTO();
						convertMenuNodeDOToTO(subMenuNodeDO2, subMenuNodeTO2,
								appsName);
						menuNodeTOs2.add(subMenuNodeTO2);

						/** ############ Level3 Start #################### **/
						if (!StringUtil.isEmptyColletion(subMenuNodeDO2
								.getMenuNodeDOs())) {
							List<MenuNodeTO> menuNodeTOs3 = new ArrayList<>();
							for (MenuNodeDO subMenuNodeDO3 : subMenuNodeDO2
									.getMenuNodeDOs()) {

								if (!isValidScreenForUser(subMenuNodeDO3,
										screenIds)) {
									continue;
								}

								MenuNodeTO subMenuNodeTO3 = new MenuNodeTO();
								convertMenuNodeDOToTO(subMenuNodeDO3,
										subMenuNodeTO3, appsName);
								menuNodeTOs3.add(subMenuNodeTO3);

								/**
								 * ############ Level4 Start
								 * ####################
								 **/
								if (!StringUtil.isEmptyColletion(subMenuNodeDO3
										.getMenuNodeDOs())) {
									List<MenuNodeTO> menuNodeTOs4 = new ArrayList<>();
									for (MenuNodeDO subMenuNodeDO4 : subMenuNodeDO3
											.getMenuNodeDOs()) {

										if (!isValidScreenForUser(
												subMenuNodeDO4, screenIds)) {
											continue;
										}

										MenuNodeTO subMenuNodeTO4 = new MenuNodeTO();
										convertMenuNodeDOToTO(subMenuNodeDO4,
												subMenuNodeTO4, appsName);
										menuNodeTOs4.add(subMenuNodeTO4);

										// TODO add level here

									}
									Collections.sort(menuNodeTOs4);
									subMenuNodeTO3.setMenuNodeTOs(menuNodeTOs4);
								}
								/** ############ Level4 End #################### **/

							}
							Collections.sort(menuNodeTOs3);
							subMenuNodeTO2.setMenuNodeTOs(menuNodeTOs3);
						}
						/** ############ Level3 End #################### **/

					}
					Collections.sort(menuNodeTOs2);
					subMenuNodeTO.setMenuNodeTOs(menuNodeTOs2);
				}
				/** ############ Level2 End #################### **/

			}
			Collections.sort(menuNodeTOs);
			menuNodeTO.setMenuNodeTOs(menuNodeTOs);
			/** ############ Level1 End #################### **/
		}
		// menuNodeDO.getM
		return menuNodeTO;
	}

	private boolean isValidScreenForUser(MenuNodeDO menuNodeDO,
			List<Integer> screenIds) {

		boolean isValid = Boolean.FALSE;

		if (menuNodeDO.getApplScreenDO() == null) {

			return true;
		}

		if (screenIds.contains(menuNodeDO.getApplScreenDO().getScreenId())) {
			isValid = Boolean.TRUE;

		}

		return isValid;
	}

	@SuppressWarnings("unused")
	private boolean isValidScreenForMenu(MenuCompositeNodeDO menuNodeDO,
			List<Integer> screenIds) {

		boolean isValid = Boolean.FALSE;

		if (menuNodeDO.getApplScreenDO() == null) {
			return true;
		}

		for (Integer screenId : screenIds) {
			if (screenId.equals(menuNodeDO.getApplScreenDO().getScreenId())) {
				isValid = Boolean.TRUE;
				continue;
			}
		}

		return isValid;
	}

	private void convertMenuNodeDOToTO(MenuNodeDO menuNodeDO,
			MenuNodeTO menuNodeTO, String appsName) throws CGBusinessException {
		CGObjectConverter.createToFromDomain(menuNodeDO, menuNodeTO);
		if (menuNodeDO.getApplScreenDO() != null) {
			ApplScreensTO applScreensTO = new ApplScreensTO();
			CGObjectConverter.createToFromDomain(menuNodeDO.getApplScreenDO(),
					applScreensTO);
			// start Added By Narasimha
			if (StringUtils.equalsIgnoreCase(UmcConstants.APP_NAME_CENTRALISED,
					menuNodeDO.getApplScreenDO().getAppName())
					&& StringUtils.equalsIgnoreCase(UmcConstants.APP_NAME_WEB,
							appsName)) {
				menuNodeTO.setIsSilentLogin(CommonConstants.YES);
				// preparing url
				StringBuffer silentLoginUrl = new StringBuffer();
				silentLoginUrl
						.append("/udaan-web/login.do?submitName=silentLoginToApp");
				silentLoginUrl.append("&screenCode=");
				silentLoginUrl.append(menuNodeDO.getApplScreenDO()
						.getScreenCode());
				silentLoginUrl.append("&moduleName=");
				silentLoginUrl.append(menuNodeDO.getApplScreenDO()
						.getModuleName());
				silentLoginUrl.append("&appName=");
				silentLoginUrl.append(UmcConstants.APP_NAME_CONFIG_ADMIN);
				applScreensTO.setUrlName(silentLoginUrl.toString());
			} else {
				if ((!StringUtil.isStringEmpty(menuNodeDO.getApplScreenDO()
						.getAppName()) && StringUtils.equalsIgnoreCase(
						UmcConstants.APP_NAME_UDAAN_REPORT, menuNodeDO
								.getApplScreenDO().getAppName()))
						|| (!StringUtil.isStringEmpty(menuNodeDO
								.getApplScreenDO().getModuleName()) && menuNodeDO
								.getApplScreenDO()
								.getModuleName()
								.toUpperCase()
								.contains(
										UmcConstants.MODULE_UDAAN_REPORT
												.toUpperCase()))
						|| !StringUtil.isStringEmpty(menuNodeDO
								.getApplScreenDO().getModuleName())
						&& menuNodeDO
								.getApplScreenDO()
								.getModuleName()
								.toUpperCase()
								.contains(
										UmcConstants.MODULE_UDAAN_CENTRAL_DOWNLOAD
												.toUpperCase())) {
					menuNodeTO.setIsSilentLogin(CommonConstants.YES);
				}
			}
			// End
			menuNodeTO.setApplScreensTO(applScreensTO);
		}
	}

	@Override
	public EmployeeUserTO getEmpUserInfo(Integer userId)
			throws CGBusinessException, CGSystemException {
		EmployeeUserDO empUserDO = loginDAO.getEmpUserInfo(userId);
		EmployeeUserTO empUserTO = new EmployeeUserTO();
		EmployeeTO empTo = new EmployeeTO();

		if (empUserDO != null && empUserDO.getEmpDO().getOfficeId() != null) {
			empUserTO.setUserId(empUserDO.getUserId());
			CGObjectConverter.createToFromDomain(empUserDO.getEmpDO(), empTo);
			empTo.setCityId(empUserDO.getEmpDO().getCity());
			empTo.setEmpStatus(empUserDO.getEmpDO().getEmpStatus());
			empUserTO.setEmpTO(empTo);
			empUserTO.setEmpUserId(empUserDO.getEmpUserId());
		}
		return empUserTO;
	}

	public EmployeeUserTO getEmpUserInfoFromUserBean(UserJoinBean userJoinBean)
			throws CGBusinessException, CGSystemException {
		// EmployeeUserDO empUserDO = loginDAO.getEmpUserInfo(userId);
		// //EmployeeUserDO empUserDO = userJoinBean.getEmpUser();
		EmployeeDO empDO = userJoinBean.getEmpDO();
		EmployeeUserTO empUserTO = new EmployeeUserTO();
		EmployeeTO empTo = new EmployeeTO();

		if (empDO != null && empDO.getOfficeId() != null) {
			empUserTO.setUserId(userJoinBean.getUser().getUserId());
			CGObjectConverter.createToFromDomain(empDO, empTo);
			empTo.setCityId(empDO.getCity());
			empTo.setEmpStatus(empDO.getEmpStatus());
			empUserTO.setEmpTO(empTo);
			// empUserTO.setEmpUserId(empUserDO.getEmpUserId());
		}
		return empUserTO;
	}

	@Override
	public CustomerUserTO getCustUserInfo(Integer userId)
			throws CGBusinessException, CGSystemException {
		CustomerUserDO custUserDO = loginDAO.getCustUserInfo(userId);
		CustomerUserTO custUserTO = new CustomerUserTO();
		CustomerTO custTO = new CustomerTO();

		if (custUserDO != null) {
			custUserTO.setUserId(custUserDO.getUserId());
			CGObjectConverter
					.createToFromDomain(custUserDO.getCustDO(), custTO);
			custUserTO.setCustTO(custTO);

		}

		return custUserTO;
	}

	@Override
	public List<Integer> getUserRoles(UserTO to) throws CGBusinessException,
			CGSystemException {
		List<UserRightsDO> userRoleList = loginDAO.getUserRoles(to.getUserId());
		List<Integer> roleIdList = new ArrayList<Integer>();
		if (!StringUtil.isEmptyList(userRoleList)) {
			for (UserRightsDO userRight : userRoleList) {
				roleIdList.add(userRight.getRoleDO().getRoleId());
			}
		}
		return roleIdList;
	}

	public List<UserRightsDO> getUserRoleIdsAndNames(UserTO to)
			throws CGBusinessException, CGSystemException {
		List<UserRightsDO> userRoleList = loginDAO.getUserRoles(to.getUserId());
		return userRoleList;
	}

	@Override
	public List<String> getUserRolesList(UserTO to) throws CGBusinessException,
			CGSystemException {
		List<UserRightsDO> userRoleList = loginDAO.getUserRoles(to.getUserId());
		List<String> roleList = null;
		if (!StringUtil.isEmptyList(userRoleList)) {
			roleList = new ArrayList<String>(userRoleList.size());
			for (UserRightsDO userRight : userRoleList) {
				roleList.add(userRight.getRoleDO().getRoleName());
			}
		}
		return roleList;
	}

	@Override
	public List<Integer> getAllowedScreensForUser(Integer userid)
			throws CGBusinessException, CGSystemException {
		List<ApplScreenDO> screenListDO = loginDAO
				.getAllowedScreensForUser(userid);
		List<Integer> screenList = new ArrayList<Integer>();
		if (screenListDO != null && screenListDO.size() > 0) {
			int count = 0;
			while (screenListDO.size() > count) {
				ApplScreenDO domain = screenListDO.get(count);
				screenList.add(domain.getScreenId());
				count++;
			}
		}

		return screenList;
	}

	@Override
	public Boolean validatePassword(Integer loginID, String newPassword)
			throws CGBusinessException, CGSystemException {

		List<PasswordDO> list = loginDAO.validatePassword(loginID, newPassword);
		if (StringUtil.isEmptyList(list)) {
			ExceptionUtil
					.prepareBusinessException(LoginErrorCodeConstants.NO_RECORD_IN_DATABASE);
		}

		// change for bcun soft delete

		List<PasswordDO> listOfRecentThree = new ArrayList<PasswordDO>();
		for (int i = 0; i < list.size(); i++) {
			if (i == 3)
				break;
			listOfRecentThree.add(list.get(i));
		}

		if (listOfRecentThree.size() == 3) {
			if (newPassword.equals(list.get(1).getPassword())
					|| (newPassword.equals(list.get(2).getPassword()))) {
				return false;
			} else {
				return true;
			}
		} else if (listOfRecentThree.size() == 2) {
			if (newPassword.equals(list.get(0).getPassword())
					|| (newPassword.equals(list.get(1).getPassword()))) {
				return false;
			} else {
				return true;
			}

		} else {

			return true;
		}

	}

	@Override
	public Boolean updatePassword(PasswordTO changePwdTO, String contextPath)
			throws CGBusinessException, CGSystemException {

		PasswordDO passDO = new PasswordDO();
		Date date = new Date();
		passDO.setUserId(changePwdTO.getUserId());

		String encrypetPassword = PasswordUtil
				.getSHAEncryptedPassword(changePwdTO.getNewPassword());
		passDO.setPassword(encrypetPassword);
		passDO.setIsActivePassword(UmcConstants.FLAG_Y);
		passDO.setLastModifiedDate(date);
		passDO.setChangeRequired(UmcConstants.FLAG_N);
		// if config
		if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
			passDO.setDtToBranch(UmcConstants.FLAG_N);
		} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
			// if web
			passDO.setDtToCentral(UmcConstants.FLAG_N);
		}

		loginDAO.updatePassword(passDO, contextPath);

		return true;

	}

	@Override
	public Boolean isPasswordExpired(Integer userId)
			throws CGBusinessException, CGSystemException {

		PasswordDO pwdDo = loginDAO.getPassword(userId);
		Date lastModifiedDate = pwdDo.getLastModifiedDate();
		long days = DateUtil.DayDifferenceBetweenTwoDates(lastModifiedDate,
				new java.util.Date());
		int pwdValidDays = 0;
		ConfigurableParamsDO param = loginDAO
				.getConfParamByParamName("PASSWORD_EXPIRY_DAYS");
		if (param != null) {
			pwdValidDays = Integer.parseInt(param.getParamValue());
		}
		if (days > pwdValidDays)
			return true;

		return false;
	}

	public Boolean isPasswordExpiredFromUserJavaBean(UserJoinBean userJoinBean,
			Map<String, String> configParam) throws CGBusinessException,
			CGSystemException {

		// PasswordDO pwdDo = loginDAO.getPassword(userId);
		PasswordDO pwdDo = userJoinBean.getPaswd();
		Date lastModifiedDate = pwdDo.getLastModifiedDate();
		long days = DateUtil.DayDifferenceBetweenTwoDates(lastModifiedDate,
				new java.util.Date());
		int pwdValidDays = 0;
		// ConfigurableParamsDO param =
		// loginDAO.getConfParamByParamName("PASSWORD_EXPIRY_DAYS");
		String param = configParam.get("PASSWORD_EXPIRY_DAYS");
		if (param != null) {
			pwdValidDays = Integer.parseInt(param);
		}
		if (days > pwdValidDays)
			return true;

		return false;
	}

	@Override
	public Boolean updatePwdChangeRequiredFlag(Integer userId)
			throws CGBusinessException, CGSystemException {

		boolean flag = loginDAO.updateChangeRequiredFlag(userId);
		return flag;
	}

	@Override
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException {

		OfficeTO offTo = null;
		RegionTO regionTO = null;
		OfficeDO officeDo = loginDAO.getOfficeByEmpId(empId);
		OfficeTypeTO offTypeTo = null;
		if (officeDo != null) {
			offTo = new OfficeTO();// creating office Object
			CGObjectConverter.createToFromDomain(officeDo, offTo);
			if (officeDo.getOfficeTypeDO() != null) {
				offTypeTo = new OfficeTypeTO();// creating officeType Object
				CGObjectConverter.createToFromDomain(
						officeDo.getOfficeTypeDO(), offTypeTo);
				offTo.setOfficeTypeTO(offTypeTo);// set officeTypeTo
			}
			if (officeDo.getMappedRegionDO() != null) {
				regionTO = new RegionTO();// creating RegionTO Object
				CGObjectConverter.createToFromDomain(
						officeDo.getMappedRegionDO(), regionTO);
				offTo.setRegionTO(regionTO);// set RegionTO
			}

		}
		return offTo;

	}
	
	public OfficeTO getOfficeByCustId(Integer custId)
			throws CGBusinessException, CGSystemException {

		OfficeTO offTo = null;
		RegionTO regionTO = null;
		OfficeDO officeDo = loginDAO.getOfficeByCustId(custId);
		OfficeTypeTO offTypeTo = null;
		if (officeDo != null) {
			offTo = new OfficeTO();// creating office Object
			CGObjectConverter.createToFromDomain(officeDo, offTo);
			if (officeDo.getOfficeTypeDO() != null) {
				offTypeTo = new OfficeTypeTO();// creating officeType Object
				CGObjectConverter.createToFromDomain(
						officeDo.getOfficeTypeDO(), offTypeTo);
				offTo.setOfficeTypeTO(offTypeTo);// set officeTypeTo
			}
			if (officeDo.getMappedRegionDO() != null) {
				regionTO = new RegionTO();// creating RegionTO Object
				CGObjectConverter.createToFromDomain(
						officeDo.getMappedRegionDO(), regionTO);
				offTo.setRegionTO(regionTO);// set RegionTO
			}

		}
		return offTo;

	}

	public OfficeTO getOfficeTOByOfficeDO(OfficeDO officeDo)
			throws CGBusinessException, CGSystemException {

		OfficeTO offTo = null;
		RegionTO regionTO = null;
		// OfficeDO officeDo = loginDAO.getOfficeByEmpId(empId);
		OfficeTypeTO offTypeTo = null;
		if (officeDo != null) {
			offTo = new OfficeTO();// creating office Object
			CGObjectConverter.createToFromDomain(officeDo, offTo);
			if (officeDo.getOfficeTypeDO() != null) {
				offTypeTo = new OfficeTypeTO();// creating officeType Object
				CGObjectConverter.createToFromDomain(
						officeDo.getOfficeTypeDO(), offTypeTo);
				offTo.setOfficeTypeTO(offTypeTo);// set officeTypeTo
			}
			if (officeDo.getMappedRegionDO() != null) {
				regionTO = new RegionTO();// creating RegionTO Object
				CGObjectConverter.createToFromDomain(
						officeDo.getMappedRegionDO(), regionTO);
				offTo.setRegionTO(regionTO);// set RegionTO
			}

		}
		return offTo;

	}

	@Override
	public Integer getUserIdByUsername(String username)
			throws CGBusinessException, CGSystemException {
		UserDO userDO = loginDAO.getUserIdByUserName(username);
		if (userDO == null)
			throw new CGBusinessException(
					LoginErrorCodeConstants.USERID_DOES_NOT_HAVE_PASSWORD);
		return userDO.getUserId();
	}

	/* creates a new pasword */
	public String generatePassword() throws CGBusinessException {
		String paswd = PasswordUtil
				.generatePassword(UmcConstants.PASSWORD_LENGTH);
		return paswd;
	}

	/*
	 * Updates the newly system generated password in the database and sends the
	 * mail both mail and saving is done at a time
	 */
	@Transactional
	public Boolean sendPasswordMail(String newPaswd, String userName,
			String mailSubject, String operatnName, String contextPath)
			throws CGSystemException, CGBusinessException {

		UserDO userDO = null;
		List<EmployeeUserDO> empList = null;
		List<CustomerUserDO> custList = null;
		String body = "";
		boolean isMailSent = Boolean.FALSE;
		LOGGER.info("Generated raw password is " + newPaswd);
		String encryptedPaswd = PasswordUtil.getSHAEncryptedPassword(newPaswd);
		String templateName = "";
		String reset = "reset";

		userDO = loginDAO.getUserIdByUserName(userName);

		PasswordDO passDO = new PasswordDO();
		Date date = new Date();
		passDO.setUserId(userDO.getUserId());
		passDO.setPassword(encryptedPaswd);
		passDO.setIsActivePassword(UmcConstants.FLAG_Y);
		passDO.setLastModifiedDate(date);
		passDO.setChangeRequired(UmcConstants.FLAG_Y);
		// if config
		if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
			passDO.setDtToBranch(UmcConstants.FLAG_N);
		} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
			// if web
			passDO.setDtToCentral(UmcConstants.FLAG_N);
		}

		passDO.setDtUpdateToCentral(UmcConstants.FLAG_N);
		/*
		 * fetches the emailid and sends mail
		 */
		String userType = userDO.getUserType();

		if (!StringUtil.isStringEmpty(userType)
				&& userType.trim().equalsIgnoreCase(UmcConstants.FLAG_C)) {
			custList = loginDAO.getCustEmailID(passDO);
			String businessName = custList.get(0).getCustDO().getBusinessName();
			String str = custList.get(0).getCustDO().getEmail();
			Map<Object, Object> mailTemplate = new HashMap<Object, Object>();
			mailTemplate.put("password", newPaswd);
			mailTemplate.put("username", userName);
			mailTemplate.put("name", businessName);

			if (operatnName.equalsIgnoreCase(reset)) {
				templateName = "ResetPassword.vm";
				body = " Dear "
						+ businessName
						+ " <br/><br/>Welcome to Udaan, we have processed your password reset request, please find below the new password <br/><br/>Login Id:"
						+ userName + "<br/> Password:" + newPaswd;
				body += "<br/><br/> Regards <br/> First Flight UDAAN support team";
			} else {
				templateName = "Password.vm";
				body = " Dear  "
						+ businessName
						+ " <br/><br/>Welcome to Udaan,please find below your login credentials <br/><br/> Login Id:"
						+ userName + "<br/> Password:" + newPaswd;
				body += "<br/><br/> Regards <br/> First Flight UDAAN support team";
			}
			try {

				LOGGER.info(body);
				LOGGER.debug(body);

				String to[] = { str };

				String cc[] = {};
				boolean isSavePaswd = loginDAO
						.savePassword(passDO, contextPath);
				if (isSavePaswd) {
					/*
					 * emailSenderService.sendEmailByPlainText(FrameworkConstants
					 * .CLIENT_USER_FROM_EMAIL_ID, to, cc,
					 * "New password generated", body);
					 */

					if (isSavePaswd) {
						MailSenderTO mailsenderTO = new MailSenderTO();
						mailsenderTO.setTo(to);
						mailsenderTO.setCc(cc);
						mailsenderTO.setPlainMailBody(body);
						mailsenderTO.setMailSubject(mailSubject);
						mailsenderTO.setTemplateName(templateName);
						sendEmail(mailsenderTO);

						/*
						 * emailSenderService.sendEmailByPlainText(
						 * FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID, to, cc,
						 * "New password generated", body);
						 */
						// isMailSent = Boolean.TRUE;
					}

					isMailSent = Boolean.TRUE;
				}
			} catch (Exception e) {
				LOGGER.error(
						"Error occured in LoginServiceImpl :: ..:sendPasswordMail()",
						e);
			}

			/*
			 * boolean isSavePaswd = loginDAO.savePassword(passDO); if
			 * (isSavePaswd) isMailSent = Boolean.TRUE;
			 */

		} else {
			empList = loginDAO.getEmpEmailID(passDO);
			String firstName = empList.get(0).getEmpDO().getFirstName();
			String lastName = empList.get(0).getEmpDO().getLastName();
			String name = firstName + " " + lastName;
			String str = empList.get(0).getEmpDO().getEmailId();
			Map<Object, Object> mailTemplate = new HashMap<Object, Object>();
			mailTemplate.put("password", newPaswd);
			mailTemplate.put("username", userName);
			mailTemplate.put("name", name);

			if (operatnName.equalsIgnoreCase(reset)) {
				templateName = "ResetPassword.vm";
				body = " Dear "
						+ name
						+ " <br/><br/>Welcome to Udaan, we have processed your password reset request, please find below the new password <br/><br/>Login Id:"
						+ userName + "<br/> Password:" + newPaswd;
				body += "<br/><br/> Regards <br/> First Flight UDAAN support team";

			} else {
				templateName = "Password.vm";
				body = " Dear  "
						+ name
						+ " <br/><br/>Welcome to Udaan,please find below your login credentials <br/><br/> Login Id:"
						+ userName + "<br/> Password:" + newPaswd;
				body += "<br/><br/> Regards <br/> First Flight UDAAN support team";
			}
			LOGGER.info(body);
			LOGGER.debug(body);

			String to[] = { str };
			String cc[] = {};
			boolean isSavePaswd = loginDAO.savePassword(passDO, contextPath);
			if (isSavePaswd) {
				MailSenderTO mailsenderTO = new MailSenderTO();
				mailsenderTO.setTo(to);
				mailsenderTO.setCc(cc);
				mailsenderTO.setPlainMailBody(body);
				mailsenderTO.setMailSubject(mailSubject);
				mailsenderTO.setTemplateName(templateName);
				sendEmail(mailsenderTO);

				/*
				 * emailSenderService.sendEmailByPlainText(FrameworkConstants.
				 * CLIENT_USER_FROM_EMAIL_ID, to, cc, "New password generated",
				 * body);
				 */
				isMailSent = Boolean.TRUE;
			}

			/*
			 * boolean isSavePaswd = loginDAO.savePassword(passDO); if
			 * (isSavePaswd) isMailSent = Boolean.TRUE;
			 */

		}
		return isMailSent;
	}

	/* for validating the current paswd for change password */
	public Boolean validateCurrentPaswd(PasswordTO changePaswdTO)
			throws CGBusinessException, CGSystemException {

		List<PasswordDO> list = loginDAO.validateCurrentPaswd(changePaswdTO);
		Boolean flag = Boolean.FALSE;
		if (StringUtil.isEmptyList(list)) {
			ExceptionUtil
					.prepareBusinessException(LoginErrorCodeConstants.NO_RECORD_IN_DATABASE);
		}
		String encryptdOldPaswd = PasswordUtil
				.getSHAEncryptedPassword(changePaswdTO.getOldPassword());

		for (PasswordDO passwdDO : list) {
			if (passwdDO.getIsActivePassword().equalsIgnoreCase(
					UmcConstants.FLAG_Y)) {
				if (passwdDO.getPassword().equals(encryptdOldPaswd)) {
					flag = Boolean.TRUE;
					return Boolean.TRUE;
				}
			}
		}
		return flag;

		/*
		 * if
		 * (list.get(0).getIsActivePassword()getPassword().equals(encryptdOldPaswd
		 * )) return Boolean.TRUE; else return Boolean.FALSE;
		 */

	}

	@Override
	public UserTO getUserDetailsByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {
		UserDO userDO = loginDAO.getUserDetailsByUserId(userId);
		if (!StringUtil.isStringEmpty(userDO.getActive())) {
			userDO.setActive(userDO.getActive().trim());
		}
		UserTO userto = new UserTO();
		CGObjectConverter.createToFromDomain(userDO, userto);
		if (userDO.getActive().equalsIgnoreCase(UmcConstants.FLAG_Y))
			userto.setActive(UmcConstants.USER_ACTIVE);
		else
			userto.setActive(UmcConstants.USER_INACTIVE);
		return userto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = loginDAO.getAllOfficesByType(offType);
		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = loginDAO.getAllOfficesByCity(cityId);
		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}

	public Integer getUserIdByUserNameType(String username, String userType)
			throws CGBusinessException, CGSystemException {
		UserDO userDO = loginDAO.getUserIdByUserNameType(username, userType);
		if (userDO == null)
			throw new CGBusinessException(
					LoginErrorCodeConstants.USERID_DOES_NOT_HAVE_PASSWORD);
		return userDO.getUserId();
	}

	public UserTO getUserById(Integer userId) throws CGBusinessException,
			CGSystemException {
		UserDO userDO = loginDAO.getUserById(userId);
		UserTO userTo = new UserTO();
		CGObjectConverter.createToFromDomain(userDO, userTo);
		return userTo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTO> getAllUsersByEmpId(Integer empId)
			throws CGBusinessException, CGSystemException {

		List<UserTO> userList = null;
		List<UserDO> userDOs = loginDAO.getAllUsersByEmpId(empId);
		userList = (List<UserTO>) CGObjectConverter.createTOListFromDomainList(
				userDOs, UserTO.class);
		return userList;
	}

	@Override
	public Boolean insertLogoutTime(UserTO userTo) throws CGSystemException {
		boolean isLogout = Boolean.FALSE;
		LOGGER.debug("LoginServiceImpl::insertLogoutTime...Start");
		try {

			LogInOutDetlDO logdo = new LogInOutDetlDO();
			// set userid from action ...session
			Date logOutDate = new Date();
			logdo.setLogOutDate(logOutDate);
			logdo.setLogInOutId(userTo.getLoginlogoutId());
			isLogout = loginDAO.updateLogoutDetails(logdo);

		} catch (Exception e) {
			LOGGER.error("Error occured in LoginServiceImpl :: insertLogoutTime() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isLogout;
	}

	/* for validating the username for forgotPassword */
	public Boolean validateUsername(String username) throws CGSystemException {
		UserDO userDO = null;
		userDO = loginDAO.getUserIdByUserName(username);
		if (userDO != null) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public UserTO getUserByUser(UserTO userTO) throws CGBusinessException,
			CGSystemException {

		List<UserTO> userTOList = new ArrayList<UserTO>();

		List<UserDO> userDOList = loginDAO.getUserByUser(userTO);

		if (!CGCollectionUtils.isEmpty(userDOList))
			userTOList = (List<UserTO>) CGObjectConverter
					.createTOListFromDomainList(userDOList, UserTO.class);

		if (CGCollectionUtils.isEmpty(userTOList))
			return null;

		return userTOList.get(0);
	}

	public void sendEmail(MailSenderTO emailTO) {
		emailSenderUtil.sendEmail(emailTO);
	}

	public String getjdbcOfficCode() {
		return getJdbcOfficeCode();
		// return this.jdbcOfficeCode;
	}

	public String getjdbcOfficeBuild() {
		return getJdbcBuild();
		// return this.jdbcOfficeCode;
	}

	@Override
	public UserJoinBean getUserJoinBean(String username, String password)
			throws CGSystemException {
		LOGGER.debug("LoginServiceImpl::getUserJoinBean::Checking User Details::START");
		UserJoinBean userJoinBean = loginDAO
				.getUserJoinBean(username, password);
		LOGGER.debug("LoginServiceImpl::getUserJoinBean::Checking User Details::END");
		return userJoinBean;
	}

	public UserInfoTO validateUser(String username, String password,
			String contextPath, Map<String, String> configParam)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LoginServiceImpl::validateUser::Start::"
				+ System.currentTimeMillis());
		UserTO userto = new UserTO();
		String jdbcBuild;
		String dbBuild;
		String offceCode;
		String welcomeUserName = "";
		UserJoinBean userJoinBean = null;
		UserInfoTO userInfoTO = new UserInfoTO();
		
	
		String filePath = null;
		filePath = customerLoginDetailsProperties.getProperty("IsCustomerLogin");
		
		
			if(filePath.equalsIgnoreCase("N")){

				LOGGER.debug("LoginServiceImpl::validateUser::Checking Build Version::"
						+ System.currentTimeMillis());
				// Check if the build version in JDBC properties matches with the build
				// version in DB
				jdbcBuild = getjdbcOfficeBuild();
				offceCode = getJdbcOffice();

				if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_WEB_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				} else if (contextPath.equalsIgnoreCase("/udaan-report")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_REPORT_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				}
				LOGGER.debug("LoginServiceImpl::validateUser::Checking Build Version Done::"
						+ System.currentTimeMillis());

				LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::"
						+ System.currentTimeMillis());
				
				Integer userId = getUserIdByUsername(username);
				userto = getUserById(userId);
				String isCustUser = userto.getUserType();
				
				if (isCustUser.equals("C")) {     //code for customer(logged in user is customer)
					userJoinBean = getCustomerUserJoinBean(username, password);
					
				}else{
				
				userJoinBean = getUserJoinBean(username, password);

				}
				
				
				

				if (StringUtil.isNull(userJoinBean)) {
					LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::User is not Employee::"
							+ System.currentTimeMillis());
					userJoinBean = getCustomerUserJoinBean(username, password);
					if (StringUtil.isNull(userJoinBean)) {
						LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::User is not Customer::"
								+ System.currentTimeMillis());
						throw new CGBusinessException(
								LoginErrorCodeConstants.INVALID_USERID);
					}
				}

				String userOfficeCode = userJoinBean.getOfficeDO().getOfficeCode();

				// Store all office codes in the list assigned to the user
				Set<UserOfficeRightsMappingDO> userOfficeRightsMapping = userJoinBean
						.getUser().getUserOfficeRightMappings();
				List<String> userOfficCodeList = new ArrayList<String>();
				for (UserOfficeRightsMappingDO userOfficeMappingDO : userOfficeRightsMapping) {
					String officeCode = userOfficeMappingDO.getOffice().getOfficeCode();

					if (userOfficeMappingDO.getStatus().equals("A")) {
						userOfficCodeList.add(officeCode);
					}
				}

				// Check User's Office Code with JDBC Office Code for '/udaan-web'
				jdbcOfficeCode = jdbcOffice;
				if (contextPath.equalsIgnoreCase("/udaan-web")) {

					if (!userOfficCodeList.contains(jdbcOffice)
							&& !userOfficeCode.contains(jdbcOffice)) {

						throw new CGBusinessException(
								LoginErrorCodeConstants.USER_NOT_ASSIGNED_TO_THE_LOGGEDIN_OFFICE);
					}

				}
				// else {
				userto.setUserId(userJoinBean.getUser().getUserId());
				/*
				 * if(isMultipleSystemLoggin(to)){ throw new
				 * CGBusinessException(ErrorCodeConstants
				 * .MULTIPLE_SYS_LOGIN_RESTRICTED); }
				 */

				// Check User's Locked Flag
				if (isUserLocked(userJoinBean.getUser()))
					throw new CGBusinessException(LoginErrorCodeConstants.USER_LOCKED);

				// Check Max Login Days Reached
				Boolean isMaxNotLoggedInDaysReached = checkLastLogin(userJoinBean,
						configParam);

				// Check User's Last Login Date & Time
				if (isMaxNotLoggedInDaysReached) {
					userJoinBean.getUser().setLocked(UmcConstants.FLAG_Y);
					userJoinBean.getUser().setActive(UmcConstants.FLAG_N);
					userJoinBean.getUser().setDtUpdateToCentral(UmcConstants.FLAG_Y);
					userJoinBean.getUser().setDtToCentral(UmcConstants.FLAG_N);
					userJoinBean.getUser().setDtToBranch(UmcConstants.FLAG_N);

					// Lock the user and throw an exception
					lockUser(userJoinBean.getUser());
					throw new CGBusinessException(
							LoginErrorCodeConstants.NOT_LOGIN_MAX_DAYS);
				}

				// Get the DB password for this User Id
				String dbpassword = null;
				PasswordTO passwordto = getPassword(userJoinBean);// put logger
				if (passwordto != null) {
					dbpassword = passwordto.getPassword();
					if (dbpassword == null)
						throw new CGBusinessException(
								LoginErrorCodeConstants.INVALID_PASSWORD);
				}

				// Convert the password into encrypted form and then compare it with
				// the
				// database password
				String encryptedpassword = getEncryptedPassword(password);
				if (encryptedpassword != null && encryptedpassword.equals(dbpassword)) {
					checkLoginAttempts(userJoinBean.getUser(), configParam);

					userto.setBoolSuccess(true);
					userJoinBean.getUser().setLoginAttempt(0);
					Date date = new Date();
					userJoinBean.getUser().setLastLoginDate(date);// reset the login
																	// attempt to zero
					// insertLoginTime(userto);// Insert the login date and time

					// LogInOutDetlDO domain = getLastLogin(userJoinBean.getUser());//
					// Get the last
					// login date
					// and time id

					/*
					 * LogInOutDetlDO domain =userJoinBean.getLoginHistory();
					 * 
					 * if (domain != null) { Integer loginloginId =
					 * domain.getLogInOutId(); userto.setLoginlogoutId(loginloginId); }
					 */
					userto.setPwdTO(passwordto);
					userto.setUserId(userJoinBean.getUser().getUserId());
					userto.setUserType(userJoinBean.getUser().getUserType());
					userto.setUserName(userJoinBean.getUser().getUserName());
					userto.setUserCode(userJoinBean.getUser().getUserCode());
					/***/
					userto.setActive(userJoinBean.getUser().getActive());
					if (userto.getActive().equals(UmcConstants.FLAG_N)) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.LOGIN_ID_DEACTIVE);
					}
					/****/
					// after successful login setlogin attempt bk to zero
					updateSuccessfulLoginAttempt(userJoinBean.getUser());

				} else {
					checkLoginAttempts(userJoinBean.getUser(), configParam);// check the
																			// number of
																			// login
																			// attempts
					// and lock the user id here
					updateLoginAttempt(userJoinBean.getUser());// Update the login
																// attempt whether
					// the user has given correct
					// password or not

					throw new CGBusinessException(
							LoginErrorCodeConstants.INVALID_PASSWORD);
				}

				boolean isPwdExpired = isPasswordExpiredFromUserJavaBean(userJoinBean,
						configParam);
				// chk if pwd is expired
				if ((isPwdExpired)) {
					updatePwdChangeRequiredFlag(userJoinBean.getUser().getUserId());
					// session.setAttribute(UmcConstants.USER_NAME,userto.getUserName());
					throw new CGBusinessException("changepassword");
					// returnpg = UmcConstants.CHANGEPASSWORD;
				}

				// Check the change password flag each time the user logins.Take him
				// to login page if he is login for first time.
				else if (userJoinBean.getPaswd().getChangeRequired() == null
						|| userJoinBean.getPaswd().getChangeRequired().trim()
								.equalsIgnoreCase("Y")) {
					// session.setAttribute(UmcConstants.USER_NAME,userto.getUserName());
					throw new CGBusinessException("changepassword");
					// returnpg = UmcConstants.CHANGEPASSWORD;
				} else {

					
					String userType = userto.getUserType();
					// modified by sami
					if (!StringUtil.isStringEmpty(userType)
							&& userType.trim().equalsIgnoreCase("E")) {

						userInfoTO
								.setEmpUserTo(getEmpUserInfoFromUserBean(userJoinBean));

						// EmployeeTO empTO = getEmpUserInfoFromUserBean(userJoinBean);

						EmployeeTO empTO = userInfoTO.getEmpUserTo().getEmpTO();

						if (!StringUtil.isNull(empTO)
								&& empTO.getEmpStatus().equalsIgnoreCase("I")) {
							throw new CGBusinessException(
									LoginErrorCodeConstants.EMPLOYEE_INACTIVE);
						}

						if (contextPath.equalsIgnoreCase("/udaan-web")) {
							userInfoTO.setOfficeTo(organizationCommonService
									.getOfficeByIdOrCode(null, offceCode));
						} else {
							userInfoTO.setOfficeTo(getOfficeTOByOfficeDO(userJoinBean
									.getOfficeDO()));
						}

						String lastName;
						if (!StringUtil.isNull(empTO)
								&& StringUtil.isNull(empTO.getLastName())) {
							lastName = "";
						} else {

							lastName = empTO.getLastName();
						}
						welcomeUserName = empTO.getFirstName()

						+ " " + lastName;
					} else if (!StringUtil.isStringEmpty(userType)
							&& userType.trim().equalsIgnoreCase("C")) {
						userInfoTO.setCustUserTo(getCustUserInfo(userto.getUserId()));
						
						userInfoTO.setOfficeTo(getOfficeTOByOfficeDO(userJoinBean
								.getOfficeDO()));

						if (userInfoTO.getCustUserTo().getCustTO().getStatus()
								.equalsIgnoreCase("I")) {
							throw new CGBusinessException(
									LoginErrorCodeConstants.CUSTOMER_INACTIVE);
						}
						
						welcomeUserName = userInfoTO.getCustUserTo().getCustTO()
								.getBusinessName();
					}

					// Added by Narasimha

					if (!StringUtil.isEmptyMap(configParam)) {
						userInfoTO.setConfigurableParams(configParam);
					}
					List<UserRightsDO> userRightDoList = getUserRoleIdsAndNames(userto);
					List<Integer> roleIdList = new ArrayList<Integer>();
					if (!StringUtil.isEmptyList(userRightDoList)) {
						for (UserRightsDO userRight : userRightDoList) {
							roleIdList.add(userRight.getRoleDO().getRoleId());
						}
					}
					List<String> roleNameList = null;
					if (!StringUtil.isEmptyList(userRightDoList)) {
						roleNameList = new ArrayList<String>(userRightDoList.size());
						for (UserRightsDO userRight : userRightDoList) {
							roleNameList.add(userRight.getRoleDO().getRoleName());
						}
					}

					// Gets User Role Names
					userto.setUserRoleList(roleNameList);

					// Gets User Role Ids
					userto.setUserRoles(roleIdList);

					userInfoTO.setUserto(userto);
					userInfoTO.setWelcomeUserName(welcomeUserName);
					LOGGER.debug("LoginServiceImpl::validateUser..:END");
					//return userInfoTO;
				}	
		
			}else if(filePath.equalsIgnoreCase("Y")){
				LOGGER.debug("LoginServiceImpl::validateUser::Checking Build Version::"
						+ System.currentTimeMillis());
				// Check if the build version in JDBC properties matches with the build
				// version in DB
				jdbcBuild = getjdbcOfficeBuild();
				offceCode = getJdbcOffice();

				if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_WEB_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				} else if (contextPath.equalsIgnoreCase("/udaan-report")) {
					dbBuild = globalUniversalService
							.getConfigParamValueByName("UDAAN_REPORT_BUILD_NO");
					if (StringUtil.isStringEmpty(jdbcBuild)
							|| StringUtil.isStringEmpty(dbBuild)
							|| !jdbcBuild.trim().equalsIgnoreCase(dbBuild.trim())) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.INCORRCT_BUILD);
					}
				}
				LOGGER.debug("LoginServiceImpl::validateUser::Checking Build Version Done::"
						+ System.currentTimeMillis());

				LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::"
						+ System.currentTimeMillis());
				
				Integer userId = getUserIdByUsername(username);
				userto = getUserById(userId);
				String isCustUser = userto.getUserType();
				
				if (isCustUser.equals("C")) {     //code for customer(logged in user is customer)
					userJoinBean = getCustomerUserJoinBean(username, password);
				
				if (StringUtil.isNull(userJoinBean)) {
					LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::User is not Employee::"
							+ System.currentTimeMillis());
					userJoinBean = getCustomerUserJoinBean(username, password);
					if (StringUtil.isNull(userJoinBean)) {
						LOGGER.debug("LoginServiceImpl::validateUser::Checking User Details::User is not Customer::"
								+ System.currentTimeMillis());
						throw new CGBusinessException(
								LoginErrorCodeConstants.INVALID_USERID);
					}
				}

				String userOfficeCode = userJoinBean.getOfficeDO().getOfficeCode();

				// Store all office codes in the list assigned to the user
				Set<UserOfficeRightsMappingDO> userOfficeRightsMapping = userJoinBean
						.getUser().getUserOfficeRightMappings();
				List<String> userOfficCodeList = new ArrayList<String>();
				for (UserOfficeRightsMappingDO userOfficeMappingDO : userOfficeRightsMapping) {
					String officeCode = userOfficeMappingDO.getOffice().getOfficeCode();

					if (userOfficeMappingDO.getStatus().equals("A")) {
						userOfficCodeList.add(officeCode);
					}
				}

				// Check User's Office Code with JDBC Office Code for '/udaan-web'
				jdbcOfficeCode = jdbcOffice;
				if (contextPath.equalsIgnoreCase("/udaan-web")) {

					if (!userOfficCodeList.contains(jdbcOffice)
							&& !userOfficeCode.contains(jdbcOffice)) {

						throw new CGBusinessException(
								LoginErrorCodeConstants.USER_NOT_ASSIGNED_TO_THE_LOGGEDIN_OFFICE);
					}

				}
				// else {
				userto.setUserId(userJoinBean.getUser().getUserId());
				/*
				 * if(isMultipleSystemLoggin(to)){ throw new
				 * CGBusinessException(ErrorCodeConstants
				 * .MULTIPLE_SYS_LOGIN_RESTRICTED); }
				 */

				// Check User's Locked Flag
				if (isUserLocked(userJoinBean.getUser()))
					throw new CGBusinessException(LoginErrorCodeConstants.USER_LOCKED);

				// Check Max Login Days Reached
				Boolean isMaxNotLoggedInDaysReached = checkLastLogin(userJoinBean,
						configParam);

				// Check User's Last Login Date & Time
				if (isMaxNotLoggedInDaysReached) {
					userJoinBean.getUser().setLocked(UmcConstants.FLAG_Y);
					userJoinBean.getUser().setActive(UmcConstants.FLAG_N);
					userJoinBean.getUser().setDtUpdateToCentral(UmcConstants.FLAG_Y);
					userJoinBean.getUser().setDtToCentral(UmcConstants.FLAG_N);
					userJoinBean.getUser().setDtToBranch(UmcConstants.FLAG_N);

					// Lock the user and throw an exception
					lockUser(userJoinBean.getUser());
					throw new CGBusinessException(
							LoginErrorCodeConstants.NOT_LOGIN_MAX_DAYS);
				}

				// Get the DB password for this User Id
				String dbpassword = null;
				PasswordTO passwordto = getPassword(userJoinBean);// put logger
				if (passwordto != null) {
					dbpassword = passwordto.getPassword();
					if (dbpassword == null)
						throw new CGBusinessException(
								LoginErrorCodeConstants.INVALID_PASSWORD);
				}

				// Convert the password into encrypted form and then compare it with
				// the
				// database password
				String encryptedpassword = getEncryptedPassword(password);
				if (encryptedpassword != null && encryptedpassword.equals(dbpassword)) {
					checkLoginAttempts(userJoinBean.getUser(), configParam);

					userto.setBoolSuccess(true);
					userJoinBean.getUser().setLoginAttempt(0);
					Date date = new Date();
					userJoinBean.getUser().setLastLoginDate(date);// reset the login
																	// attempt to zero
					// insertLoginTime(userto);// Insert the login date and time

					// LogInOutDetlDO domain = getLastLogin(userJoinBean.getUser());//
					// Get the last
					// login date
					// and time id

					/*
					 * LogInOutDetlDO domain =userJoinBean.getLoginHistory();
					 * 
					 * if (domain != null) { Integer loginloginId =
					 * domain.getLogInOutId(); userto.setLoginlogoutId(loginloginId); }
					 */
					userto.setPwdTO(passwordto);
					userto.setUserId(userJoinBean.getUser().getUserId());
					userto.setUserType(userJoinBean.getUser().getUserType());
					userto.setUserName(userJoinBean.getUser().getUserName());
					userto.setUserCode(userJoinBean.getUser().getUserCode());
					/***/
					userto.setActive(userJoinBean.getUser().getActive());
					if (userto.getActive().equals(UmcConstants.FLAG_N)) {
						throw new CGBusinessException(
								LoginErrorCodeConstants.LOGIN_ID_DEACTIVE);
					}
					/****/
					// after successful login setlogin attempt bk to zero
					updateSuccessfulLoginAttempt(userJoinBean.getUser());

				} else {
					checkLoginAttempts(userJoinBean.getUser(), configParam);// check the
																			// number of
																			// login
																			// attempts
					// and lock the user id here
					updateLoginAttempt(userJoinBean.getUser());// Update the login
																// attempt whether
					// the user has given correct
					// password or not

					throw new CGBusinessException(
							LoginErrorCodeConstants.INVALID_PASSWORD);
				}

				boolean isPwdExpired = isPasswordExpiredFromUserJavaBean(userJoinBean,
						configParam);
				// chk if pwd is expired
				if ((isPwdExpired)) {
					updatePwdChangeRequiredFlag(userJoinBean.getUser().getUserId());
					// session.setAttribute(UmcConstants.USER_NAME,userto.getUserName());
					throw new CGBusinessException("changepassword");
					// returnpg = UmcConstants.CHANGEPASSWORD;
				}

				// Check the change password flag each time the user logins.Take him
				// to login page if he is login for first time.
				else if (userJoinBean.getPaswd().getChangeRequired() == null
						|| userJoinBean.getPaswd().getChangeRequired().trim()
								.equalsIgnoreCase("Y")) {
					// session.setAttribute(UmcConstants.USER_NAME,userto.getUserName());
					throw new CGBusinessException("changepassword");
					// returnpg = UmcConstants.CHANGEPASSWORD;
				} else {

					
					String userType = userto.getUserType();
					// modified by sami
					if (!StringUtil.isStringEmpty(userType)
							&& userType.trim().equalsIgnoreCase("E")) {

						userInfoTO
								.setEmpUserTo(getEmpUserInfoFromUserBean(userJoinBean));

						// EmployeeTO empTO = getEmpUserInfoFromUserBean(userJoinBean);

						EmployeeTO empTO = userInfoTO.getEmpUserTo().getEmpTO();

						if (!StringUtil.isNull(empTO)
								&& empTO.getEmpStatus().equalsIgnoreCase("I")) {
							throw new CGBusinessException(
									LoginErrorCodeConstants.EMPLOYEE_INACTIVE);
						}

						if (contextPath.equalsIgnoreCase("/udaan-web")) {
							userInfoTO.setOfficeTo(organizationCommonService
									.getOfficeByIdOrCode(null, offceCode));
						} else {
							userInfoTO.setOfficeTo(getOfficeTOByOfficeDO(userJoinBean
									.getOfficeDO()));
						}

						String lastName;
						if (!StringUtil.isNull(empTO)
								&& StringUtil.isNull(empTO.getLastName())) {
							lastName = "";
						} else {

							lastName = empTO.getLastName();
						}
						welcomeUserName = empTO.getFirstName()

						+ " " + lastName;
					} else if (!StringUtil.isStringEmpty(userType)
							&& userType.trim().equalsIgnoreCase("C")) {
						userInfoTO.setCustUserTo(getCustUserInfo(userto.getUserId()));
						
						userInfoTO.setOfficeTo(getOfficeTOByOfficeDO(userJoinBean
								.getOfficeDO()));

						if (userInfoTO.getCustUserTo().getCustTO().getStatus()
								.equalsIgnoreCase("I")) {
							throw new CGBusinessException(
									LoginErrorCodeConstants.CUSTOMER_INACTIVE);
						}
						
						welcomeUserName = userInfoTO.getCustUserTo().getCustTO()
								.getBusinessName();
					}

					// Added by Narasimha

					if (!StringUtil.isEmptyMap(configParam)) {
						userInfoTO.setConfigurableParams(configParam);
					}
					List<UserRightsDO> userRightDoList = getUserRoleIdsAndNames(userto);
					List<Integer> roleIdList = new ArrayList<Integer>();
					if (!StringUtil.isEmptyList(userRightDoList)) {
						for (UserRightsDO userRight : userRightDoList) {
							roleIdList.add(userRight.getRoleDO().getRoleId());
						}
					}
					List<String> roleNameList = null;
					if (!StringUtil.isEmptyList(userRightDoList)) {
						roleNameList = new ArrayList<String>(userRightDoList.size());
						for (UserRightsDO userRight : userRightDoList) {
							roleNameList.add(userRight.getRoleDO().getRoleName());
						}
					}

					// Gets User Role Names
					userto.setUserRoleList(roleNameList);

					// Gets User Role Ids
					userto.setUserRoles(roleIdList);

					userInfoTO.setUserto(userto);
					userInfoTO.setWelcomeUserName(welcomeUserName);
					LOGGER.debug("LoginServiceImpl::validateUser..:END");
					//return userInfoTO;
				}	
				
				
			}else{
				throw new CGBusinessException(
						LoginErrorCodeConstants.LOGIN_ID_NOT_ALLOWED);
			}

			}
		/*} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//in.close();
		}	*/
		return userInfoTO;
		// }

	}

	@Override
	public UserJoinBean getCustomerUserJoinBean(String username, String password)
			throws CGSystemException {
		LOGGER.debug("LoginServiceImpl::getUserJoinBean..fetching login details:Start");
		return loginDAO.getCustomerUserJoinBean(username, password);
	}
}
