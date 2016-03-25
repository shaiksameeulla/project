package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;

//  @ Project : FirstFlight
//  @ File Name : CityDO.java
//  @ Date : 10/4/2012
//  @ Author : 

public class CityDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4292575844008524332L;
	private Integer cityId;
	private String cityCode;
	private String cityName;
	private Integer districtId;
	private Integer state;
	private Integer region;

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
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 *            the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the districtId
	 */
	public Integer getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId
	 *            the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

}
