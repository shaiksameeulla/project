package com.ff.domain.geography;

import java.util.Set;

import com.ff.domain.geography.ZoneDO;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class StateDO extends CGFactDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 533679957313508680L;
	private Integer stateId;
	private ZoneDO zone;
	private String stateName;
	private String stateCode;
	//private Set<CityDO> citys;
	
	
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
	
		
	public ZoneDO getZone() {
		return zone;
	}

	public void setZone(ZoneDO zone) {
		this.zone = zone;
	}

	
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	/**
	 * @return the citys
	 */	
//	public Set<CityDO> getCitys() {
//		return citys;
//	}

	/**
	 * @param citys the citys to set
	 */
//	public void setCitys(Set<CityDO> citys) {
//		this.citys = citys;
//	}

}
