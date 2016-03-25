package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateComponentCalculatedDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer rateComponentCalculatedtId;
	private Integer rateComponentCalculated;
	private Integer rateComponentCalculatedOn;
	private String operationUsedInCalc;
	private Integer evalOrder;	
	//Non persistence field.
	private double rateValue;
	/**
	 * @return the rateComponentCalculatedtId
	 */
	public Integer getRateComponentCalculatedtId() {
		return rateComponentCalculatedtId;
	}
	/**
	 * @param rateComponentCalculatedtId the rateComponentCalculatedtId to set
	 */
	public void setRateComponentCalculatedtId(Integer rateComponentCalculatedtId) {
		this.rateComponentCalculatedtId = rateComponentCalculatedtId;
	}
	/**
	 * @return the rateComponentCalculated
	 */
	public Integer getRateComponentCalculated() {
		return rateComponentCalculated;
	}
	/**
	 * @param rateComponentCalculated the rateComponentCalculated to set
	 */
	public void setRateComponentCalculated(Integer rateComponentCalculated) {
		this.rateComponentCalculated = rateComponentCalculated;
	}
	/**
	 * @return the rateComponentCalculatedOn
	 */
	public Integer getRateComponentCalculatedOn() {
		return rateComponentCalculatedOn;
	}
	/**
	 * @param rateComponentCalculatedOn the rateComponentCalculatedOn to set
	 */
	public void setRateComponentCalculatedOn(Integer rateComponentCalculatedOn) {
		this.rateComponentCalculatedOn = rateComponentCalculatedOn;
	}
	/**
	 * @return the operationUsedInCalc
	 */
	public String getOperationUsedInCalc() {
		return operationUsedInCalc;
	}
	/**
	 * @param operationUsedInCalc the operationUsedInCalc to set
	 */
	public void setOperationUsedInCalc(String operationUsedInCalc) {
		this.operationUsedInCalc = operationUsedInCalc;
	}
	/**
	 * @return the evalOrder
	 */
	public Integer getEvalOrder() {
		return evalOrder;
	}
	/**
	 * @param evalOrder the evalOrder to set
	 */
	public void setEvalOrder(Integer evalOrder) {
		this.evalOrder = evalOrder;
	}
	/**
	 * @return the rateValue
	 */
	public double getRateValue() {
		return rateValue;
	}
	/**
	 * @param rateValue the rateValue to set
	 */
	public void setRateValue(double rateValue) {
		this.rateValue = rateValue;
	}
	
	
}
