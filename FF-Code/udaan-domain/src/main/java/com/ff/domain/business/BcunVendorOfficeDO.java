package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.OfficeDO;

public class BcunVendorOfficeDO extends CGMasterDO {
	
	private static final long serialVersionUID = 1L;
	
	private Integer vendorOfficeMappingId;
	private Integer vendorOfficeRegionId;
	private OfficeDO officeDO;
	
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
	
	
}
