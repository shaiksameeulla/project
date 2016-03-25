package com.ff.geography;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RegionTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3227798413592090479L;
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
