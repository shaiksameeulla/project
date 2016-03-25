package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRegionDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1726016184791888013L;
	private Integer regionId;               
	private String regionCode;          
	private String regionName;          
	private String regionDisplayName;          
	private Integer zone;
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the regionDisplayName
	 */
	public String getRegionDisplayName() {
		return regionDisplayName;
	}
	/**
	 * @param regionDisplayName the regionDisplayName to set
	 */
	public void setRegionDisplayName(String regionDisplayName) {
		this.regionDisplayName = regionDisplayName;
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
	
	
}
