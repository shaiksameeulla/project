package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BaAdditionalChargesDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -815207970429462197L;
	private Integer additionalChrgId;
	private Double componentValue;
	private String componentCode;
	private Integer baRateheaderId;
	private String priorityInd;
	
	
	/**
	 * @return the priorityInd
	 */
	public String getPriorityInd() {
		return priorityInd;
	}
	/**
	 * @param priorityInd the priorityInd to set
	 */
	public void setPriorityInd(String priorityInd) {
		this.priorityInd = priorityInd;
	}
	/**
	 * @return the additionalChrgId
	 */
	public Integer getAdditionalChrgId() {
		return additionalChrgId;
	}
	/**
	 * @param additionalChrgId the additionalChrgId to set
	 */
	public void setAdditionalChrgId(Integer additionalChrgId) {
		this.additionalChrgId = additionalChrgId;
	}
	/**
	 * @return the componentValue
	 */
	public Double getComponentValue() {
		return componentValue;
	}
	/**
	 * @param componentValue the componentValue to set
	 */
	public void setComponentValue(Double componentValue) {
		this.componentValue = componentValue;
	}
	/**
	 * @return the componentCode
	 */
	public String getComponentCode() {
		return componentCode;
	}
	/**
	 * @param componentCode the componentCode to set
	 */
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	/**
	 * @return the baRateheaderId
	 */
	public Integer getBaRateheaderId() {
		return baRateheaderId;
	}
	/**
	 * @param baRateheaderId the baRateheaderId to set
	 */
	public void setBaRateheaderId(Integer baRateheaderId) {
		this.baRateheaderId = baRateheaderId;
	}
	
	
}
