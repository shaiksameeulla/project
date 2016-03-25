package com.ff.domain.umc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author nihsingh
 *
 */
public class UserDO extends CGFactDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2675748154857778058L;
	private Integer userId;
	private String userName;
	private String locked;
	private String active;
	private Integer loginAttempt;
	private Date lastLoginDate;
	private String status;
	private String userCode;
	private String userType;
	

	@JsonBackReference Set<UserRightsDO> userRights = null;
	@JsonBackReference Set<UserOfficeRightsMappingDO> userOfficeRightMappings = null;
	@JsonBackReference Set<EmployeeUserDO> empUserDOs = null;
	@JsonBackReference Set<PasswordDO> passwordDOs = null;

	/**
	 * @desc get UserType
	 * @return userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @desc set UserType
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @desc get UserCode
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @desc set UserCode
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = !StringUtil.isStringEmpty(userCode)?userCode.toUpperCase().trim():userCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return String value
	 */
	public String getLocked() {
		return locked;
	}

	/**
	 * @desc get Active value
	 * @return String value
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @desc set Active value
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @desc  set Locked
	 * @param locked
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}

	/**
	 * @desc get UserName
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @desc setUserName
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = !StringUtil.isStringEmpty(userName)?userName.toUpperCase().trim():userName;
	}

	/**
	 * @return loginAttempt
	 */
	public Integer getLoginAttempt() {
		return loginAttempt;
	}

	/**
	 * @desc set LoginAttempt
	 * @param loginAttempt
	 */
	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	/**
	 * @return lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @desc set LastLoginDate
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @desc set Status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return userRights
	 */
	public Set<UserRightsDO> getUserRights() {
		return userRights;
	}

	/**
	 * @desc set UserRights
	 * @param userRights
	 */
	public void setUserRights(Set<UserRightsDO> userRights) {
		this.userRights = userRights;
	}

	/**
	 * @return userOfficeRightMappings
	 */
	public Set<UserOfficeRightsMappingDO> getUserOfficeRightMappings() {
		return userOfficeRightMappings;
	}

	/**
	 * @desc set UserOfficeRightMappings
	 * @param userOfficeRightMappings
	 */
	public void setUserOfficeRightMappings(
			Set<UserOfficeRightsMappingDO> userOfficeRightMappings) {
		this.userOfficeRightMappings = userOfficeRightMappings;
	}

	/**
	 * @return the empUserDOs
	 */
	public Set<EmployeeUserDO> getEmpUserDOs() {
		return empUserDOs;
	}

	/**
	 * @param empUserDOs the empUserDOs to set
	 */
	public void setEmpUserDOs(Set<EmployeeUserDO> empUserDOs) {
		this.empUserDOs = empUserDOs;
	}

	/**
	 * @return the passwordDOs
	 */
	public Set<PasswordDO> getPasswordDOs() {
		return passwordDOs;
	}

	/**
	 * @param passwordDOs the passwordDOs to set
	 */
	public void setPasswordDOs(Set<PasswordDO> passwordDOs) {
		this.passwordDOs = passwordDOs;
	}

	
	
}
