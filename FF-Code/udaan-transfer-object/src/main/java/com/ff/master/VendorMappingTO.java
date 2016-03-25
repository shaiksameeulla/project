package com.ff.master;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class VendorMappingTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    Integer regionTo;
    Integer stationTo;
    String vendorName;
    String vendorCode;
    String address;
    String errorMessage;
    String successMessage;
	public Integer getRegionTo() {
		return regionTo;
	}
	public void setRegionTo(Integer regionTo) {
		this.regionTo = regionTo;
	}
	public Integer getStationTo() {
		return stationTo;
	}
	public void setStationTo(Integer stationTo) {
		this.stationTo = stationTo;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
    
	
}
