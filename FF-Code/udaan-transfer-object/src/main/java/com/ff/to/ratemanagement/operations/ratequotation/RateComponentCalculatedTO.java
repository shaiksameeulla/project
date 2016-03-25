package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.rate.RateComponentTO;
/**
 * @author preegupt
 *
 */
public class RateComponentCalculatedTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5927620806113610146L;
	private Integer rateComponentCalculatedtId;
	private RateComponentTO rateComponentCalculated;
	private RateComponentTO rateComponentCalculatedOn;
	private String operationUsedInCalc;
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
	public RateComponentTO getRateComponentCalculated() {
		return rateComponentCalculated;
	}
	/**
	 * @param rateComponentCalculated the rateComponentCalculated to set
	 */
	public void setRateComponentCalculated(RateComponentTO rateComponentCalculated) {
		this.rateComponentCalculated = rateComponentCalculated;
	}
	/**
	 * @return the rateComponentCalculatedOn
	 */
	public RateComponentTO getRateComponentCalculatedOn() {
		return rateComponentCalculatedOn;
	}
	/**
	 * @param rateComponentCalculatedOn the rateComponentCalculatedOn to set
	 */
	public void setRateComponentCalculatedOn(
			RateComponentTO rateComponentCalculatedOn) {
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
}
