/**
 * 
 */
package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author uchauhan
 * 
 */
public class InsuranceConfigTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5812058452023717641L;

	private Integer insuranceConfigId;
	private Double minDeclaredValue;
	private Double maxDeclaredValue;
	private String isPolicyNoMandatory;
	private Double declaredValue;
	private Integer insuredById;
	private String isDeclaredValuesExceeded;
	private String trasnStatus;

	public Integer getInsuranceConfigId() {
		return insuranceConfigId;
	}

	public void setInsuranceConfigId(Integer insuranceConfigId) {
		this.insuranceConfigId = insuranceConfigId;
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

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Integer getInsuredById() {
		return insuredById;
	}

	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
	}

	public String getIsDeclaredValuesExceeded() {
		return isDeclaredValuesExceeded;
	}

	public void setIsDeclaredValuesExceeded(String isDeclaredValuesExceeded) {
		this.isDeclaredValuesExceeded = isDeclaredValuesExceeded;
	}

	/**
	 * @return the trasnStatus
	 */
	public String getTrasnStatus() {
		return trasnStatus;
	}

	/**
	 * @param trasnStatus the trasnStatus to set
	 */
	public void setTrasnStatus(String trasnStatus) {
		this.trasnStatus = trasnStatus;
	}

}
