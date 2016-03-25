package com.ff.domain.ratemanagement.operations.ratecalculation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class CashRateRTOChargesDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9090999703745389480L;
	
	private Integer rateCashRTOChargesId;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private String rateComponentCode;
	private Integer cashProductHeaderId;
	private Double discountOnSlab;
	/** The priorityInd. i.e. Y= Priority, N= Non-Priority */
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
	 * @return the rateCashRTOChargesId
	 */
	public Integer getRateCashRTOChargesId() {
		return rateCashRTOChargesId;
	}
	/**
	 * @param rateCashRTOChargesId the rateCashRTOChargesId to set
	 */
	public void setRateCashRTOChargesId(Integer rateCashRTOChargesId) {
		this.rateCashRTOChargesId = rateCashRTOChargesId;
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
	 * @return the cashProductHeaderId
	 */
	public Integer getCashProductHeaderId() {
		return cashProductHeaderId;
	}
	/**
	 * @param cashProductHeaderId the cashProductHeaderId to set
	 */
	public void setCashProductHeaderId(Integer cashProductHeaderId) {
		this.cashProductHeaderId = cashProductHeaderId;
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
	
	
}
