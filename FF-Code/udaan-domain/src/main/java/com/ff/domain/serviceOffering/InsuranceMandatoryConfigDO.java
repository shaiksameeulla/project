package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class InsuranceMandatoryConfigDO extends CGFactDO{

	/**
	 * 
	 */
	

	private static final long serialVersionUID = -4663558330819187483L;
	private Integer insuranceMandatoryConfigId;
	
	private double declaredValue;
	private String isPolicyNoMandatory;
	private String insuredBy;
	public Integer getInsuranceMandatoryConfigId() {
		return insuranceMandatoryConfigId;
	}
	public void setInsuranceMandatoryConfigId(Integer insuranceMandatoryConfigId) {
		this.insuranceMandatoryConfigId = insuranceMandatoryConfigId;
	}
	public double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getIsPolicyNoMandatory() {
		return isPolicyNoMandatory;
	}
	public void setIsPolicyNoMandatory(String isPolicyNoMandatory) {
		this.isPolicyNoMandatory = isPolicyNoMandatory;
	}
	public String getInsuredBy() {
		return insuredBy;
	}
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}
}