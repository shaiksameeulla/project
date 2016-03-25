package com.ff.umc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author ami
 * 
 */
public class UserTO extends CGBaseTO {

	private static final long serialVersionUID = 5783914437207093207L;

	private Integer userId;
	private String userName;
	private String userCode;
	private String userType;
	private String password;
	private PasswordTO pwdTO;

	private Boolean boolSuccess;
	private Integer loginAttempt;
	private Integer maxAllowedLoginAttempt;
	private Integer maxLogoutDays;
	private String locked;
	private Integer loginlogoutId;
	private List<Integer> screenList;
	private List<Integer> userRoles;
	private List<String> userRoleList;
	private Map<String, String> configurableParams = new HashMap<String, String>();
	// private String changePwd;
	// following attribute added for validating multiple system login
	private String currentSessionId;
	private String previousSessionId;
	private Integer previousLoginlogoutId;
	private String enableFrmBillingDate;
	private String macAddressLogInUser;
	private Boolean retailAccessUser = false;

	// max in active interval for session timeout pop up
	private Integer sessionTimeout;

	// error code on exception
	private String errorCode;
	private String isExternalUser;
	private String active;
	private Date lastLoginDate;
	private String city;
	private String status;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the userId
	 */

	public PasswordTO getPwdTO() {
		return pwdTO;
	}

	public void setPwdTO(PasswordTO pwdTO) {
		this.pwdTO = pwdTO;
	}

	public List<Integer> getScreenList() {
		return screenList;
	}

	public void setScreenList(List<Integer> screenList) {
		this.screenList = screenList;
	}

	public List<Integer> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Integer> userRoles) {
		this.userRoles = userRoles;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getMaxAllowedLoginAttempt() {
		return maxAllowedLoginAttempt;
	}

	public void setMaxAllowedLoginAttempt(Integer maxAllowedLoginAttempt) {
		this.maxAllowedLoginAttempt = maxAllowedLoginAttempt;
	}

	public Integer getMaxLogoutDays() {
		return maxLogoutDays;
	}

	public void setMaxLogoutDays(Integer maxLogoutDays) {
		this.maxLogoutDays = maxLogoutDays;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = !StringUtil.isStringEmpty(userName)?userName.toUpperCase().trim():userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getBoolSuccess() {
		return boolSuccess;
	}

	public void setBoolSuccess(Boolean boolSuccess) {
		this.boolSuccess = boolSuccess;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public Integer getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public Integer getLoginlogoutId() {
		return loginlogoutId;
	}

	public void setLoginlogoutId(Integer loginlogoutId) {
		this.loginlogoutId = loginlogoutId;
	}

	public String getIsExternalUser() {
		return isExternalUser;
	}

	public void setIsExternalUser(String isExternalUser) {
		this.isExternalUser = isExternalUser;
	}

	public Map<String, String> getConfigurableParams() {
		return configurableParams;
	}

	public void setConfigurableParams(Map<String, String> configurableParams) {
		this.configurableParams = configurableParams;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = !StringUtil.isStringEmpty(userCode)?userCode.toUpperCase().trim():userCode;
	}

	/*
	 * public String getChangePwd() { return changePwd; } public void
	 * setChangePwd(String changePwd) { this.changePwd = changePwd; }
	 */
	/**
	 * @return the currentSessionId
	 */
	public String getCurrentSessionId() {
		return currentSessionId;
	}

	/**
	 * @param currentSessionId
	 *            the currentSessionId to set
	 */
	public void setCurrentSessionId(String currentSessionId) {
		this.currentSessionId = currentSessionId;
	}

	/**
	 * @return the previousSessionId
	 */
	public String getPreviousSessionId() {
		return previousSessionId;
	}

	/**
	 * @param previousSessionId
	 *            the previousSessionId to set
	 */
	public void setPreviousSessionId(String previousSessionId) {
		this.previousSessionId = previousSessionId;
	}

	public String getEnableFrmBillingDate() {
		return enableFrmBillingDate;
	}

	public void setEnableFrmBillingDate(String enableFrmBillingDate) {
		this.enableFrmBillingDate = enableFrmBillingDate;
	}

	/**
	 * @return the sessionTimeout
	 */
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param sessionTimeout
	 *            the sessionTimeout to set
	 */
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * @return the previousLoginlogoutId
	 */
	public Integer getPreviousLoginlogoutId() {
		return previousLoginlogoutId;
	}

	/**
	 * @param previousLoginlogoutId
	 *            the previousLoginlogoutId to set
	 */
	public void setPreviousLoginlogoutId(Integer previousLoginlogoutId) {
		this.previousLoginlogoutId = previousLoginlogoutId;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the macAddressLogInUser
	 */
	public String getMacAddressLogInUser() {
		return macAddressLogInUser;
	}

	/**
	 * @param macAddressLogInUser
	 *            the macAddressLogInUser to set
	 */
	public void setMacAddressLogInUser(String macAddressLogInUser) {
		this.macAddressLogInUser = macAddressLogInUser;
	}

	/**
	 * @return the retailAccessUser
	 */
	public Boolean getRetailAccessUser() {
		return retailAccessUser;
	}

	/**
	 * @param retailAccessUser
	 *            the retailAccessUser to set
	 */
	public void setRetailAccessUser(Boolean retailAccessUser) {
		this.retailAccessUser = retailAccessUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userRoleList
	 */
	public List<String> getUserRoleList() {
		return userRoleList;
	}

	/**
	 * @param userRoleList the userRoleList to set
	 */
	public void setUserRoleList(List<String> userRoleList) {
		this.userRoleList = userRoleList;
	}

}