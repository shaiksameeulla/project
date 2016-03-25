
package com.capgemini.lbs.framework.to;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author mohammes
 *
 */
public class UserTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5783914437207093207L;
	
	private Integer userId;
	private String userName;
	private String userCode;
	private String userType;
	private String password;
	
	private Boolean boolSuccess;
	private Integer loginAttempt;
	private Integer maxAllowedLoginAttempt;
	private Integer maxLogoutDays;
	private String locked;
	private Integer loginlogoutId;
	private List<String> screenList = null;
	private List<String> userRoles;
	private Map<String, String> configurableParams = new HashMap<String, String>();
	private String changePwd;
	//following attribute added for validating multiple system login
	private String currentSessionId;
	private String previousSessionId;
	private Integer previousLoginlogoutId;
	private String enableFrmBillingDate;
	private String macAddressLogInUser;
	private Boolean retailAccessUser = false;
	
	//max in active interval for session timeout pop up
	private Integer sessionTimeout;
	
	// error code on exception
	private String errorCode;
	private String isExternalUser;
	
	
	/**
	 * @return the userId
	 */
	
	
	/**
	 * @return
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @return
	 */
	public Integer getMaxAllowedLoginAttempt() {
		return maxAllowedLoginAttempt;
	}
	/**
	 * @param maxAllowedLoginAttempt
	 */
	public void setMaxAllowedLoginAttempt(Integer maxAllowedLoginAttempt) {
		this.maxAllowedLoginAttempt = maxAllowedLoginAttempt;
	}
	/**
	 * @return
	 */
	public Integer getMaxLogoutDays() {
		return maxLogoutDays;
	}
	/**
	 * @param maxLogoutDays
	 */
	public void setMaxLogoutDays(Integer maxLogoutDays) {
		this.maxLogoutDays = maxLogoutDays;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return
	 */
	public Boolean getBoolSuccess() {
		return boolSuccess;
	}
	/**
	 * @param boolSuccess
	 */
	public void setBoolSuccess(Boolean boolSuccess) {
		this.boolSuccess = boolSuccess;
	}
	/**
	 * @return
	 */
	public String getLocked() {
		return locked;
	}

	/**
	 * @param locked
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}

	/**
	 * @return
	 */
	public Integer getLoginAttempt() {
		return loginAttempt;
	}

	/**
	 * @param loginAttempt
	 */
	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}
	/**
	 * @return
	 */
	public Integer getLoginlogoutId() {
		return loginlogoutId;
	}
	/**
	 * @param loginlogoutId
	 */
	public void setLoginlogoutId(Integer loginlogoutId) {
		this.loginlogoutId = loginlogoutId;
	}
	/**
	 * @return
	 */
	public List<String> getScreenList() {
		return screenList;
	}
	/**
	 * @param screenList
	 */
	public void setScreenList(List<String> screenList) {
		this.screenList = screenList;
	}
	
	/**
	 * @return
	 */
	public String getIsExternalUser() {
		return isExternalUser;
	}
	/**
	 * @param isExternalUser
	 */
	public void setIsExternalUser(String isExternalUser) {
		this.isExternalUser = isExternalUser;
	}
	/**
	 * @return
	 */
	public List<String> getUserRoles() {
		return userRoles;
	}
	/**
	 * @param userRoles
	 */
	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}
	/**
	 * @return
	 */
	public Map<String, String> getConfigurableParams() {
		return configurableParams;
	}
	/**
	 * @param configurableParams
	 */
	public void setConfigurableParams(Map<String, String> configurableParams) {
		this.configurableParams = configurableParams;
	}

	/**
	 * @return
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return
	 */
	public String getChangePwd() {
		return changePwd;
	}
	/**
	 * @param changePwd
	 */
	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}
	/**
	 * @return the currentSessionId
	 */
	public String getCurrentSessionId() {
		return currentSessionId;
	}
	/**
	 * @param currentSessionId the currentSessionId to set
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
	 * @param previousSessionId the previousSessionId to set
	 */
	public void setPreviousSessionId(String previousSessionId) {
		this.previousSessionId = previousSessionId;
	}
	/**
	 * @return
	 */
	public String getEnableFrmBillingDate() {
		return enableFrmBillingDate;
	}
	/**
	 * @param enableFrmBillingDate
	 */
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
	 * @param sessionTimeout the sessionTimeout to set
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
	 * @param previousLoginlogoutId the previousLoginlogoutId to set
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
	 * @param errorCode the errorCode to set
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
	 * @param macAddressLogInUser the macAddressLogInUser to set
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
	 * @param retailAccessUser the retailAccessUser to set
	 */
	public void setRetailAccessUser(Boolean retailAccessUser) {
		this.retailAccessUser = retailAccessUser;
	}
	/**
	 * @return
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}