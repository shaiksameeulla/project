package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGMasterDO;


public class VendorTypeDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	
	private Integer vendorTypeId;
	public String vendorTypeCode;
	public String vendorTypeDescription;
	public String accGroupSAP;
	
	/**
	 * @return the vendorTypeId
	 */
	public Integer getVendorTypeId() {
		return vendorTypeId;
	}
	/**
	 * @param vendorTypeId the vendorTypeId to set
	 */
	public void setVendorTypeId(Integer vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}
	/**
	 * @return the vendorTypeCode
	 */
	public String getVendorTypeCode() {
		return vendorTypeCode;
	}
	/**
	 * @param vendorTypeCode the vendorTypeCode to set
	 */
	public void setVendorTypeCode(String vendorTypeCode) {
		this.vendorTypeCode = vendorTypeCode;
	}
	/**
	 * @return the vendorTypeDescription
	 */
	public String getVendorTypeDescription() {
		return vendorTypeDescription;
	}
	/**
	 * @param vendorTypeDescription the vendorTypeDescription to set
	 */
	public void setVendorTypeDescription(String vendorTypeDescription) {
		this.vendorTypeDescription = vendorTypeDescription;
	}
	/**
	 * @return the accGroupSAP
	 */
	public String getAccGroupSAP() {
		return accGroupSAP;
	}
	/**
	 * @param accGroupSAP the accGroupSAP to set
	 */
	public void setAccGroupSAP(String accGroupSAP) {
		this.accGroupSAP = accGroupSAP;
	}
	
	
	
	
	
}
