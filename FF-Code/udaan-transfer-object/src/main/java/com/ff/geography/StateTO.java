/**
 * 
 */
package com.ff.geography;

import com.capgemini.lbs.framework.to.CGBaseTO;


/**
 * @author uchauhan
 *
 */
public class StateTO extends CGBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2406620270453690874L;
	private Integer stateId;
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
