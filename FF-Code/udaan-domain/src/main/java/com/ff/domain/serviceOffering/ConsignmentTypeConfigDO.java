package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class ConsignmentTypeConfigDO extends CGMasterDO{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4663558330819187483L;
	private Integer consignmentTypeConfigId;
	private ConsignmentTypeDO consignmentId;
	private Double declaredValue;
	private String isPaperworkMandatory = "N";
	
	public Integer getConsignmentTypeConfigId() {
		return consignmentTypeConfigId;
	}
	public void setConsignmentTypeConfigId(Integer consignmentTypeConfigId) {
		this.consignmentTypeConfigId = consignmentTypeConfigId;
	}
	
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getIsPaperworkMandatory() {
		return isPaperworkMandatory;
	}
	public void setIsPaperworkMandatory(String isPaperworkMandatory) {
		this.isPaperworkMandatory = isPaperworkMandatory;
	}
	public ConsignmentTypeDO getConsignmentId() {
		return consignmentId;
	}
	public void setConsignmentId(ConsignmentTypeDO consignmentId) {
		this.consignmentId = consignmentId;
	}
	
}
