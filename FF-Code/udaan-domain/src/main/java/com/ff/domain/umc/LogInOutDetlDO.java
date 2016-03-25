package com.ff.domain.umc;

import java.io.Serializable;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.OfficeDO;

public class LogInOutDetlDO extends CGFactDO implements Serializable{
	
	
	private Integer logInOutId;
	private Integer userId;
	private Date logInDate;
	private Date logOutDate;
	private String macAddress;
	private Integer officeId;
	private String jSessionId;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @desc get LogInOutId
	 * @return logInOutId
	 */
	public Integer getLogInOutId() {
		return logInOutId;
	}
	
	
	/**
	 * @desc set LogInOutId
	 * @param logInOutId
	 */
	public void setLogInOutId(Integer logInOutId) {
		this.logInOutId = logInOutId;
	}
	
	/** 
	 * @desc get MacAddress
	 * @return macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	
	
	/**
	 * @desc set MacAddress
	 * @param macAddress
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	/**
	 * @desc get LogInDate
	 * @return logInDate
	 */
	public Date getLogInDate() {
		return logInDate;
	}
	
	
	/**
	 * @desc set LogInDate
	 * @param logInDate
	 */
	public void setLogInDate(Date logInDate) {
		this.logInDate = logInDate;
	}
	
	/**
	 * @desc get LogOutDate
	 * @return logOutDate
	 */
	public Date getLogOutDate() {
		return logOutDate;
	}
	
	/**
	 * @desc set LogOutDate
	 * @param logOutDate
	 */
	public void setLogOutDate(Date logOutDate) {
		this.logOutDate = logOutDate;
	}
	
	/**
	 * @desc get OfficeId
	 * @return officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	
	/**
	 * @desc set OfficeId
	 * @param officeId
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return jSessionId
	 */
	public String getjSessionId() {
		return jSessionId;
	}
	
	/**
	 * @param jSessionId
	 */
	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}
	
}
