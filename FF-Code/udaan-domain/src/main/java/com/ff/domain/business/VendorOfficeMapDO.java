package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.OfficeDO;
public class VendorOfficeMapDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	private Integer vendorOfficeMappingId;
	private Integer vendorOfficeRegionId;
	private OfficeDO officeDO;
	private String status;
	
	public Integer getVendorOfficeMappingId() {
		return vendorOfficeMappingId;
	}
	public void setVendorOfficeMappingId(Integer vendorOfficeMappingId) {
		this.vendorOfficeMappingId = vendorOfficeMappingId;
	}
	public Integer getVendorOfficeRegionId() {
		return vendorOfficeRegionId;
	}
	public void setVendorOfficeRegionId(Integer vendorOfficeRegionId) {
		this.vendorOfficeRegionId = vendorOfficeRegionId;
	}
	public OfficeDO getOfficeDO() {
		return officeDO;
	}
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
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
