package com.ff.geography;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PincodeServicabilityTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5084591076098580870L;
	// Inputs
	private String pincode;
	private Integer officeId;
	private String consgSeries;

	// Output
	private Integer pincodeId;
	private String isServicedByFFCL;
	private String isServicedByOffice;
	private String isValidPincode;
	private String isValidPriorityPincode;
	private Integer cityId;
	private String cityName;
	private String errorMsg;

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Integer getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public String getIsServicedByFFCL() {
		return isServicedByFFCL;
	}

	public void setIsServicedByFFCL(String isServicedByFFCL) {
		this.isServicedByFFCL = isServicedByFFCL;
	}

	public String getIsServicedByOffice() {
		return isServicedByOffice;
	}

	public void setIsServicedByOffice(String isServicedByOffice) {
		this.isServicedByOffice = isServicedByOffice;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getIsValidPincode() {
		return isValidPincode;
	}

	public void setIsValidPincode(String isValidPincode) {
		this.isValidPincode = isValidPincode;
	}

	public String getConsgSeries() {
		return consgSeries;
	}

	public void setConsgSeries(String consgSeries) {
		this.consgSeries = consgSeries;
	}

	public String getIsValidPriorityPincode() {
		return isValidPriorityPincode;
	}

	public void setIsValidPriorityPincode(String isValidPriorityPincode) {
		this.isValidPriorityPincode = isValidPriorityPincode;
	}

}
