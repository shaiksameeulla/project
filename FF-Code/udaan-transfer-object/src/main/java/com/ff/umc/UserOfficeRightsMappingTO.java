package com.ff.umc;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nihsingh
 *
 */
public class UserOfficeRightsMappingTO  extends CGBaseTO implements Comparable<UserOfficeRightsMappingTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1416638876579149839L;
	private Integer userMappingId;
	private Integer userId;
	private Integer officeId;
	private String officeCode;
	private String officeName;
	private String mappedTo;
	private String status;
	
	
	/**
	 * @desc get UserMappingId
	 * @return userMappingId
	 */
	public Integer getUserMappingId() {
		return userMappingId;
	}
	
	
	/**
	 * @desc set UserMappingId
	 * @param userMappingId
	 */
	public void setUserMappingId(Integer userMappingId) {
		this.userMappingId = userMappingId;
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
	 * @desc getOfficeCode
	 * @return officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	
	
	/**
	 * @desc set OfficeCode
	 * @param officeCode
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	
	/**
	 * @desc get OfficeName
	 * @return officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	
	
	/**
	 * @desc set OfficeName
	 * @param officeName
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	
	/**
	 * @desc get MappedTo
	 * @return mappedTo
	 */
	public String getMappedTo() {
		return mappedTo;
	}
	
	
	/**
	 * @desc set MappedTo
	 * @param mappedTo
	 */
	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}
	
	
	/**
	 * @desc get Status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	
	/**
	 * @desc sets Status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserOfficeRightsMappingTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.officeName)) {
			returnVal = this.officeName.compareTo(obj1.officeName);
		}
		return returnVal;
	}
	
}
