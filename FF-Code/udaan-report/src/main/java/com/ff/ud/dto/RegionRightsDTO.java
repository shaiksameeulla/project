package com.ff.ud.dto;

import java.util.List;

public class RegionRightsDTO {

	private Integer regionCode;
	private String regionName;
	
	public  RegionRightsDTO(){	}
	
	public  RegionRightsDTO(Integer regionCode,String regionName)
	{
		this.regionCode=regionCode;
		this.regionName=regionName;
	}

	public Integer getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	
}
