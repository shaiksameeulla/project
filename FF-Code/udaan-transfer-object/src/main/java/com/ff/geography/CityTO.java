package com.ff.geography;

import com.capgemini.lbs.framework.to.CGBaseTO;


public class CityTO extends CGBaseTO {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 8334792619360169119L;
	private Integer cityId;
	  private String cityCode;
	  private String cityName;
	  private Integer region;
	  private Integer state;
	  
	  @Deprecated
	  private long timeTaken;
	  
	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
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

}
