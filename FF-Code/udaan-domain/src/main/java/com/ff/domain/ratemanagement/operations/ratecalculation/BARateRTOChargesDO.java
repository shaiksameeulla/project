package com.ff.domain.ratemanagement.operations.ratecalculation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BARateRTOChargesDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9090999703745389480L;
	
	private Integer rateBARTOChargesId;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private String rateComponentCode;
	private Integer baRateHeaderId;
	private Double discountOnSlab;
	private String priorityInd;
	/**
	 * @return the rateBARTOChargesId
	 */
	public Integer getRateBARTOChargesId() {
		return rateBARTOChargesId;
	}
	/**
	 * @param rateBARTOChargesId the rateBARTOChargesId to set
	 */
	public void setRateBARTOChargesId(Integer rateBARTOChargesId) {
		this.rateBARTOChargesId = rateBARTOChargesId;
	}
	/**
	 * @return the rtoChargeApplicable
	 */
	public String getRtoChargeApplicable() {
		return rtoChargeApplicable;
	}
	/**
	 * @param rtoChargeApplicable the rtoChargeApplicable to set
	 */
	public void setRtoChargeApplicable(String rtoChargeApplicable) {
		this.rtoChargeApplicable = rtoChargeApplicable;
	}
	/**
	 * @return the sameAsSlabRate
	 */
	public String getSameAsSlabRate() {
		return sameAsSlabRate;
	}
	/**
	 * @param sameAsSlabRate the sameAsSlabRate to set
	 */
	public void setSameAsSlabRate(String sameAsSlabRate) {
		this.sameAsSlabRate = sameAsSlabRate;
	}
	/**
	 * @return the rateComponentCode
	 */
	public String getRateComponentCode() {
		return rateComponentCode;
	}
	/**
	 * @param rateComponentCode the rateComponentCode to set
	 */
	public void setRateComponentCode(String rateComponentCode) {
		this.rateComponentCode = rateComponentCode;
	}
	/**
	 * @return the baRateHeaderId
	 */
	public Integer getBaRateHeaderId() {
		return baRateHeaderId;
	}
	/**
	 * @param baRateHeaderId the baRateHeaderId to set
	 */
	public void setBaRateHeaderId(Integer baRateHeaderId) {
		this.baRateHeaderId = baRateHeaderId;
	}
	/**
	 * @return the discountOnSlab
	 */
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
	/**
	 * @param discountOnSlab the discountOnSlab to set
	 */
	public void setDiscountOnSlab(Double discountOnSlab) {
		this.discountOnSlab = discountOnSlab;
	}
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
	
	
}
