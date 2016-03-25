package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author preegupt
 *
 */
public class BcunStateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 533488046509484330L;
	private Integer stateId;
	private Integer zone;
	private String stateName;
	private String stateCode;
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

	/**
	 * @return the zone
	 */
	public Integer getZone() {
		return zone;
	}
	/**
	 * @param zone the zone to set
	 */
	public void setZone(Integer zone) {
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
	
	
	
}
