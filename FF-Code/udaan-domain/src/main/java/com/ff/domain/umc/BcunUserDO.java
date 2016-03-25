package com.ff.domain.umc;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author nihsingh
 *
 */
public class BcunUserDO extends CGFactDO {

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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Integer getLoginAttempt() {
		return loginAttempt;
	}
	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
