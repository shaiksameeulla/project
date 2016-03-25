package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RateQuotationRTOChargesDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9090999703745389480L;
	
	private Integer rateQuotationRTOChargesId;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private String rateComponentDO;
	private RateQuotationDO rateQuotationDO;
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
	public String getRateComponentDO() {
		return rateComponentDO;
	}
	public void setRateComponentDO(String rateComponentDO) {
		this.rateComponentDO = rateComponentDO;
	}
	public RateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	public void setRateQuotationDO(RateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
	public void setDiscountOnSlab(Double discountOnSlab) {
		this.discountOnSlab = discountOnSlab;
	}
}
