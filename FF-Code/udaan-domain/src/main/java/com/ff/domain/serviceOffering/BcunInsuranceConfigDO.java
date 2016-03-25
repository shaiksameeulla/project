package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunInsuranceConfigDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -4663558330819187483L;
	private Integer insuranceConfigId;
	private Integer insuredById;
	private Double minDeclaredValue;
	private Double maxDeclaredValue;
	private String isPolicyNoMandatory = "N";
	private Integer bookingType;

	public Integer getInsuranceConfigId() {
		return insuranceConfigId;
	}

	public void setInsuranceConfigId(Integer insuranceConfigId) {
		this.insuranceConfigId = insuranceConfigId;
	}

	public Integer getInsuredById() {
		return insuredById;
	}

	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
	}

	public Double getMinDeclaredValue() {
		return minDeclaredValue;
	}

	public void setMinDeclaredValue(Double minDeclaredValue) {
		this.minDeclaredValue = minDeclaredValue;
	}

	public Double getMaxDeclaredValue() {
		return maxDeclaredValue;
	}

	public void setMaxDeclaredValue(Double maxDeclaredValue) {
		this.maxDeclaredValue = maxDeclaredValue;
	}

	public String getIsPolicyNoMandatory() {
		return isPolicyNoMandatory;
	}

	public void setIsPolicyNoMandatory(String isPolicyNoMandatory) {
		this.isPolicyNoMandatory = isPolicyNoMandatory;
	}

	/**
	 * @return the bookingType
	 */
	public Integer getBookingType() {
		return bookingType;
	}

	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(Integer bookingType) {
		this.bookingType = bookingType;
	}


}