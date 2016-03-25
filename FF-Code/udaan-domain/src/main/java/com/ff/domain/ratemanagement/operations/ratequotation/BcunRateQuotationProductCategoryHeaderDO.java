package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;


public class BcunRateQuotationProductCategoryHeaderDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448455027561884023L;
	private Integer rateQuotationProductCategoryHeaderId;
	private String extendedFromPreviousQuotation;
	private Integer rateProductCategory;
	private Integer minimumChargeableWeightId;
	private Integer rateQuotationId;
	private Set<BcunRateQuotationWeightSlabDO> rateQuotationWeightSlabDO;
	private Integer vobSlab;
	private String flatRate;
	
	/**
	 * @return the extendedFromPreviousQuotation
	 */
	public String getExtendedFromPreviousQuotation() {
		return extendedFromPreviousQuotation;
	}
	/**
	 * @param extendedFromPreviousQuotation the extendedFromPreviousQuotation to set
	 */
	public void setExtendedFromPreviousQuotation(
			String extendedFromPreviousQuotation) {
		this.extendedFromPreviousQuotation = extendedFromPreviousQuotation;
	}
	/**
	 * @return the vobSlab
	 */
	public Integer getVobSlab() {
		return vobSlab;
	}
	/**
	 * @param vobSlab the vobSlab to set
	 */
	public void setVobSlab(Integer vobSlab) {
		this.vobSlab = vobSlab;
	}
	public Integer getRateQuotationProductCategoryHeaderId() {
		return rateQuotationProductCategoryHeaderId;
	}
	public void setRateQuotationProductCategoryHeaderId(
			Integer rateQuotationProductCategoryHeaderId) {
		this.rateQuotationProductCategoryHeaderId = rateQuotationProductCategoryHeaderId;
	}
	/**
	 * @return the rateProductCategory
	 */
	public Integer getRateProductCategory() {
		return rateProductCategory;
	}
	/**
	 * @param rateProductCategory the rateProductCategory to set
	 */
	public void setRateProductCategory(Integer rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}
	/**
	 * @return the minimumChargeableWeightId
	 */
	public Integer getMinimumChargeableWeightId() {
		return minimumChargeableWeightId;
	}
	/**
	 * @param minimumChargeableWeightId the minimumChargeableWeightId to set
	 */
	public void setMinimumChargeableWeightId(Integer minimumChargeableWeightId) {
		this.minimumChargeableWeightId = minimumChargeableWeightId;
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
	/**
	 * @return the rateQuotationWeightSlabDO
	 */
	public Set<BcunRateQuotationWeightSlabDO> getRateQuotationWeightSlabDO() {
		return rateQuotationWeightSlabDO;
	}
	/**
	 * @param rateQuotationWeightSlabDO the rateQuotationWeightSlabDO to set
	 */
	public void setRateQuotationWeightSlabDO(
			Set<BcunRateQuotationWeightSlabDO> rateQuotationWeightSlabDO) {
		this.rateQuotationWeightSlabDO = rateQuotationWeightSlabDO;
	}
	public String getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}
	
}
