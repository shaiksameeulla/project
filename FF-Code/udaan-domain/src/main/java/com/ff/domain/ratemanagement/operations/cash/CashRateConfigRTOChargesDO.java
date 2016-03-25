package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */

public class CashRateConfigRTOChargesDO extends CGFactDO {
	
	private static final long serialVersionUID = 1L;
	
	/** The rateCashRTOChargesId. */
	private Integer rateCashRTOChargesId;
	
	/** The rtoChargeApplicable. */
	private String rtoChargeApplicable;
	
	/** The sameAsSlabRate. */
	private String sameAsSlabRate;
	
	/** The discountOnSlab. */
	private Double discountOnSlab;
	
	/** The rateComponentCode. */
	private String rateComponentCode;
	
	/** The productMapId. */
	//private Integer productMapId;
	/** The cashRateHeaderProductDO. */
	private CashRateConfigHeaderDO cashRateHeaderDO;
	
	/** The priorityInd. i.e. Y= Priority, N= Non-Priority */
	private String priorityInd;

	
	
	/**
	 * @return the cashRateHeaderProductDO
	 */
	public CashRateConfigHeaderDO getCashRateHeaderDO() {
		return cashRateHeaderDO;
	}
	/**
	 * @param cashRateHeaderProductDO the cashRateHeaderProductDO to set
	 */
	public void setCashRateHeaderDO(
			CashRateConfigHeaderDO cashRateHeaderDO) {
		this.cashRateHeaderDO = cashRateHeaderDO;
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
