package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class RateCalculationWeightSlabDO extends CGFactDO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5322823439885549207L;
	

	private Integer rateQuotationWeightSlabId;
	private WeightSlabDO startWeight;
	private WeightSlabDO endWeight;
	private Integer order;
	private String additional;
	
	/**
	 * @return the additional
	 */
	public String getAdditional() {
		return additional;
	}
	/**
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	/**
	 * @return the rateQuotationWeightSlabId
	 */
	public Integer getRateQuotationWeightSlabId() {
		return rateQuotationWeightSlabId;
	}
	/**
	 * @param rateQuotationWeightSlabId the rateQuotationWeightSlabId to set
	 */
	public void setRateQuotationWeightSlabId(Integer rateQuotationWeightSlabId) {
		this.rateQuotationWeightSlabId = rateQuotationWeightSlabId;
	}
	/**
	 * @return the startWeight
	 */
	public WeightSlabDO getStartWeight() {
		return startWeight;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeight(WeightSlabDO startWeight) {
		this.startWeight = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public WeightSlabDO getEndWeight() {
		return endWeight;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeight(WeightSlabDO endWeight) {
		this.endWeight = endWeight;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	
}
