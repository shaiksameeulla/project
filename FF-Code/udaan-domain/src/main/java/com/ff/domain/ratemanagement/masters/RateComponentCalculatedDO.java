package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.capgemini.lbs.framework.utils.StringUtil;

public class RateComponentCalculatedDO extends CGMasterDO implements Comparable<RateComponentCalculatedDO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer rateComponentCalculatedtId;
	private RateComponentDO rateComponentCalculated;
	private RateComponentDO rateComponentCalculatedOn;
	private String operationUsedInCalc;
	private Integer evalOrder;	
	//Non persistence field.
	private double rateValue;
	
	
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
	public RateComponentDO getRateComponentCalculated() {
		return rateComponentCalculated;
	}
	/**
	 * @param rateComponentCalculated the rateComponentCalculated to set
	 */
	public void setRateComponentCalculated(RateComponentDO rateComponentCalculated) {
		this.rateComponentCalculated = rateComponentCalculated;
	}
	/**
	 * @return the rateComponentCalculatedOn
	 */
	public RateComponentDO getRateComponentCalculatedOn() {
		return rateComponentCalculatedOn;
	}
	/**
	 * @param rateComponentCalculatedOn the rateComponentCalculatedOn to set
	 */
	public void setRateComponentCalculatedOn(
			RateComponentDO rateComponentCalculatedOn) {
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
	public double getRateValue() {
		return rateValue;
	}
	public void setRateValue(double rateValue) {
		this.rateValue = rateValue;
	}
	
	
	@Override
	public int compareTo(RateComponentCalculatedDO obj) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(evalOrder) 
				&& !StringUtil.isEmptyInteger(obj.getEvalOrder())){
			//result = this.evalOrder.compareTo(obj.evalOrder);
			int curent = this.rateComponentCalculated.getCalculationSequence();
			int compareTo = obj.rateComponentCalculated.getCalculationSequence();
			if(curent == compareTo)
				result = 0;
			else if(curent > compareTo)
				result = 1;
			else
				result = -1;
		}
		return result;
}
}
