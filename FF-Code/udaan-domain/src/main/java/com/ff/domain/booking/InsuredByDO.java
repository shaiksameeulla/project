package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class InsuredByDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8995135283859143476L;
	
	private Integer insuredById;
	private double declaredValue;
	private String isPolicyNoMandatory;
	
	public Integer getInsuredById() {
		return insuredById;
	}
	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
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
	
}
