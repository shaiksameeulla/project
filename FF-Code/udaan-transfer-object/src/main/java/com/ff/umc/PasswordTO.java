package com.ff.umc;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nihsingh
 *
 */
public class PasswordTO extends CGBaseTO {

	
	private static final long serialVersionUID = 2667708935774237410L;
	private Integer passwordId;
	private String passwordCode;
	private String password;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String username;
	private String emailId;
	
	/**
	 * @desc get Username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	
	/**
	 * @desc set Username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/**
	 * @desc get EmailId
	 * @return emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	
	/**
	 * @desc set EmailId
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	/**
	 * @desc get ChangeRequired
	 * @return String value
	 */
	public String getChangeRequired() {
		return changeRequired;
	}
	
	
	/**
	 * @desc set ChangeRequired
	 * @param changeRequired
	 */
	public void setChangeRequired(String changeRequired) {
		this.changeRequired = changeRequired;
	}
	
	
	private String changeRequired;
	
	/**
	 * @desc get OldPassword
	 * @return String value
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	
	
	/**
	 * @desc set OldPassword
	 * @param oldPassword
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	
	/**
	 * @desc get NewPassword
	 * @return newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	
	/**
	 * @desc get UserId
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	
	
	/**
	 * @desc set UserId
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	/**
	 * @desc set NewPassword 
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	/**
	 * @desc get ConfirmPassword
	 * @return String value
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	
	/**
	 * @desc set ConfirmPassword
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	private Integer userId ;
	private Date lastModifiedDate;
	
	
	/**
	 * @desc get PasswordId
	 * @return passwordId
	 */
	public Integer getPasswordId() {
		return passwordId;
	}
	
	
	/**
	 * @desc set PasswordId
	 * @param passwordId
	 */
	public void setPasswordId(Integer passwordId) {
		this.passwordId = passwordId;
	}
	
	
	/**
	 * @desc get PasswordCode
	 * @return passwordCode
	 */
	public String getPasswordCode() {
		return passwordCode;
	}
	
	
	/**
	 * @desc get LastModifiedDate
	 * @return lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	
	/**
	 * @desc set LastModifiedDate
	 * @param lastModifiedDate
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	/**
	 * @desc set PasswordCode
	 * @param passwordCode
	 */
	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}
	
	
	/**
	 * @desc get Password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	
	/**
	 * @desc set Password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
