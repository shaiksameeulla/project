package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * @author cbhure
 *
 */
public class CSDSAPVendorRegionMapDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	private Integer vendorRegionMappingId;
	public Integer regionId;
	private String status;
	@JsonBackReference public CSDSAPLoadMovementVendorDO vendorDO;
	
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
	public CSDSAPLoadMovementVendorDO getVendorDO() {
		return vendorDO;
	}
	/**
	 * @param vendorDO the vendorDO to set
	 */
	public void setVendorDO(CSDSAPLoadMovementVendorDO vendorDO) {
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
