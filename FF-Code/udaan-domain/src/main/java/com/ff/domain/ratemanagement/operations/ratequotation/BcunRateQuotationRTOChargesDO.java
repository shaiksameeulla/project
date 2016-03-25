package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRateQuotationRTOChargesDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9090999703745389480L;
	
	private Integer rateQuotationRTOChargesId;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private String rateComponentCode;
	private Integer rateQuotationId;
	private Double discountOnSlab;
	
	
	public Integer getRateQuotationRTOChargesId() {
		return rateQuotationRTOChargesId;
	}
	public void setRateQuotationRTOChargesId(Integer rateQuotationRTOChargesId) {
		this.rateQuotationRTOChargesId = rateQuotationRTOChargesId;
	}
	public String getRtoChargeApplicable() {
		return rtoChargeApplicable;
	}
	public void setRtoChargeApplicable(String rtoChargeApplicable) {
		this.rtoChargeApplicable = rtoChargeApplicable;
	}
	public String getSameAsSlabRate() {
		return sameAsSlabRate;
	}
	public void setSameAsSlabRate(String sameAsSlabRate) {
		this.sameAsSlabRate = sameAsSlabRate;
	}
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
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
	 * @return the rateQuotationId
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @param rateQuotationId the rateQuotationId to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
	}
	
}
