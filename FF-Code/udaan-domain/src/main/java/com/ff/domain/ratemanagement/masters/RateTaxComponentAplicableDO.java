package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateTaxComponentAplicableDO extends CGMasterDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7770056488525177877L;
	private Integer rateTaxComponentAplicableId;
	private RateTaxComponentDO rateTaxComponentDO;
	private RateIndustryTypeDO rateIndustryTypeDO;
	/**
	 * @return the rateTaxComponentAplicableId
	 */
	public Integer getRateTaxComponentAplicableId() {
		return rateTaxComponentAplicableId;
	}
	/**
	 * @param rateTaxComponentAplicableId the rateTaxComponentAplicableId to set
	 */
	public void setRateTaxComponentAplicableId(Integer rateTaxComponentAplicableId) {
		this.rateTaxComponentAplicableId = rateTaxComponentAplicableId;
	}
	/**
	 * @return the rateTaxComponentDO
	 */
	public RateTaxComponentDO getRateTaxComponentDO() {
		return rateTaxComponentDO;
	}
	/**
	 * @param rateTaxComponentDO the rateTaxComponentDO to set
	 */
	public void setRateTaxComponentDO(RateTaxComponentDO rateTaxComponentDO) {
		this.rateTaxComponentDO = rateTaxComponentDO;
	}
	/**
	 * @return the rateIndustryTypeDO
	 */
	public RateIndustryTypeDO getRateIndustryTypeDO() {
		return rateIndustryTypeDO;
	}
	/**
	 * @param rateIndustryTypeDO the rateIndustryTypeDO to set
	 */
	public void setRateIndustryTypeDO(RateIndustryTypeDO rateIndustryTypeDO) {
		this.rateIndustryTypeDO = rateIndustryTypeDO;
	}
	
	
	
}