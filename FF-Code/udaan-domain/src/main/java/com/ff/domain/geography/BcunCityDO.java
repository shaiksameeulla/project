package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunCityDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8813581532459102682L;
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
	 * @param cityId the cityId to set
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
	 * @param cityCode the cityCode to set
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
	 * @param cityName the cityName to set
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
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the region
	 */
	public Integer getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(Integer region) {
		this.region = region;
	}
	
	
}
