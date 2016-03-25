package com.ff.domain.umc;

import java.io.Serializable;
import java.util.Date;

import com.capgemini.lbs.framework.domain.*;

/**
 * @author nihsingh
 *
 */
/**
 * @author nihsingh
 *
 */
public class PasswordDO extends CGFactDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4656569411314538991L;
	private Integer passwordId;
	private Integer userId ;
	private String password;
	private String isActivePassword;
	private Date lastModifiedDate;
	private String changeRequired;
	
	

	
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * @return password
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
	 * @return isActivePassword
	 */
	public String getIsActivePassword() {
		return isActivePassword;
	}
	
	/**
	 * @return lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	/**
	 * @param lastModifiedDate
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * @param isActivePassword
	 */
	public void setIsActivePassword(String isActivePassword) {
		this.isActivePassword = isActivePassword;
	}
	
	/**
	 * @return String value
	 */
	public String getChangeRequired() {
		return changeRequired;
	}
	public void setChangeRequired(String changeRequired) {
		this.changeRequired = changeRequired;
	}

}
