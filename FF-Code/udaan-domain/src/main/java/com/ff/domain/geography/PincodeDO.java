package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//  @ Project : FirstFlight
//  @ File Name : PincodeDO.java
//  @ Date : 10/4/2012
//  @ Author : 

@JsonIgnoreProperties(ignoreUnknown=true)
public class PincodeDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1375440198760407511L;
	private Integer pincodeId;
	private String pincode;
	private Integer cityId;
	private String location;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}
