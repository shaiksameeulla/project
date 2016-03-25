package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RegionDO extends CGMasterDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6507752402987833512L;
	private Integer regionId;               
	private String regionCode;          
	private String regionName;          
	private String regionDisplayName;          
	private Integer zone;
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionDisplayName() {
		return regionDisplayName;
	}
	public void setRegionDisplayName(String regionDisplayName) {
		this.regionDisplayName = regionDisplayName;
	}
	public Integer getZone() {
		return zone;
	}
	public void setZone(Integer zone) {
		this.zone = zone;
	}	
}
