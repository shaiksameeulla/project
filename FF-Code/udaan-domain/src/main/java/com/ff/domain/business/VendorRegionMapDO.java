package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.fasterxml.jackson.annotation.JsonBackReference;


public class VendorRegionMapDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	
	private Integer vendorRegionMappingId;
	public Integer regionId;
	@JsonBackReference public LoadMovementVendorDO vendorDO;
	private String status;
	
	/**
	 * @return the vendorRegionMappingId
	 */
	public Integer getVendorRegionMappingId() {
		return vendorRegionMappingId;
	}
	/**
	 * @param vendorRegionMappingId the vendorRegionMappingId to set
	 */
	public void setVendorRegionMappingId(Integer vendorRegionMappingId) {
		this.vendorRegionMappingId = vendorRegionMappingId;
	}
	
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the vendorDO
	 */
	public LoadMovementVendorDO getVendorDO() {
		return vendorDO;
	}
	/**
	 * @param vendorDO the vendorDO to set
	 */
	public void setVendorDO(LoadMovementVendorDO vendorDO) {
		this.vendorDO = vendorDO;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
