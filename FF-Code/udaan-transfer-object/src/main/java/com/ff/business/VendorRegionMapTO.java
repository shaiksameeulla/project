package com.ff.business;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.RegionTO;

public class VendorRegionMapTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vendorRegionMappingId;
	public RegionTO regionTO;
	public Integer getVendorRegionMappingId() {
		return vendorRegionMappingId;
	}
	public void setVendorRegionMappingId(Integer vendorRegionMappingId) {
		this.vendorRegionMappingId = vendorRegionMappingId;
	}
	public RegionTO getRegionTO() {
		return regionTO;
	}
	public void setRegionTO(RegionTO regionTO) {
		this.regionTO = regionTO;
	}

	
	
}
