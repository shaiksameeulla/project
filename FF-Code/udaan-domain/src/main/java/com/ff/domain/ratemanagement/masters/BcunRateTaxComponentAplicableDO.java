package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateTaxComponentAplicableDO extends CGMasterDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7770056488525177877L;
	private Integer rateTaxComponentAplicableId;
	private Integer rateTaxComponent;
	private Integer rateIndustryType;
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
	 * @return the rateTaxComponent
	 */
	public Integer getRateTaxComponent() {
		return rateTaxComponent;
	}
	/**
	 * @param rateTaxComponent the rateTaxComponent to set
	 */
	public void setRateTaxComponent(Integer rateTaxComponent) {
		this.rateTaxComponent = rateTaxComponent;
	}
	/**
	 * @return the rateIndustryType
	 */
	public Integer getRateIndustryType() {
		return rateIndustryType;
	}
	/**
	 * @param rateIndustryType the rateIndustryType to set
	 */
	public void setRateIndustryType(Integer rateIndustryType) {
		this.rateIndustryType = rateIndustryType;
	}
	
	
}