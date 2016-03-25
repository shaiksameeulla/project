package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;


public class RateCalculationApplicableSlabsDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer rateComponentApplicableId;
	private RateCalculationWeightSlabDO rateCalculationWeightSlabDO;
	private Double rate;
	private String rateCalculatedFor;
	/**
	 * @return the rateComponentApplicableId
	 */
	public Integer getRateComponentApplicableId() {
		return rateComponentApplicableId;
	}
	/**
	 * @param rateComponentApplicableId the rateComponentApplicableId to set
	 */
	public void setRateComponentApplicableId(Integer rateComponentApplicableId) {
		this.rateComponentApplicableId = rateComponentApplicableId;
	}
	/**
	 * @return the rateCalculationWeightSlabDO
	 */
	public RateCalculationWeightSlabDO getRateCalculationWeightSlabDO() {
		return rateCalculationWeightSlabDO;
	}
	/**
	 * @param rateCalculationWeightSlabDO the rateCalculationWeightSlabDO to set
	 */
	public void setRateCalculationWeightSlabDO(
			RateCalculationWeightSlabDO rateCalculationWeightSlabDO) {
		this.rateCalculationWeightSlabDO = rateCalculationWeightSlabDO;
	}
	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the rateCalculatedFor
	 */
	public String getRateCalculatedFor() {
		return rateCalculatedFor;
	}
	/**
	 * @param rateCalculatedFor the rateCalculatedFor to set
	 */
	public void setRateCalculatedFor(String rateCalculatedFor) {
		this.rateCalculatedFor = rateCalculatedFor;
	}
}