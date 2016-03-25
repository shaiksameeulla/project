package com.ff.domain.geography;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ZoneDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149779149377340388L;
	
	private Integer zoneId;
	private String zoneName;
	private String zoneCode;
	//private Set<StateDO> states;
	
	/**
	 * @return the zoneId
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	
	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	
	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}
	
	/**
	 * @param zoneId the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}
	
	/**
	 * @param zoneId the zoneCode to set
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * @return the states
	 */
//	public Set<StateDO> getStates() {
//		return states;
//	}

	/**
	 * @param states the states to set
	 */
//	public void setStates(Set<StateDO> states) {
//		this.states = states;
//	}	

}
