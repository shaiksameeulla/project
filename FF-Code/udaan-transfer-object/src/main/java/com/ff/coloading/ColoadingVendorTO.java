package com.ff.coloading;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author Indrajeet Sawarkar
 * 
 */
public class ColoadingVendorTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer vendorId;
	private String vendorCode;
	private String businessName;

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	

}
