package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;


public class RateQuotationProductCategoryHeaderDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448455027561884023L;
	private Integer rateQuotationProductCategoryHeaderId;
	private String extendedFromPreviousQuotation;
	private RateProductCategoryDO rateProductCategory;
	private RateMinChargeableWeightDO minimumChargeableWeightDO;
	private RateQuotationDO rateQuotationDO;
	private Set<RateQuotationWeightSlabDO> rateQuotationWeightSlabDO;
	private Integer vobSlab;
	private String flatRate;
	
	/**
	 * @return the rateQuotationWeightSlabDO
	 */
	public Set<RateQuotationWeightSlabDO> getRateQuotationWeightSlabDO() {
		return rateQuotationWeightSlabDO;
	}
	/**
	 * @param rateQuotationWeightSlabDO the rateQuotationWeightSlabDO to set
	 */
	public void setRateQuotationWeightSlabDO(
			Set<RateQuotationWeightSlabDO> rateQuotationWeightSlabDO) {
		this.rateQuotationWeightSlabDO = rateQuotationWeightSlabDO;
	}
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
	 * @return the rateProductCategory
	 */
	public RateProductCategoryDO getRateProductCategory() {
		return rateProductCategory;
	}
	/**
	 * @param rateProductCategory the rateProductCategory to set
	 */
	public void setRateProductCategory(RateProductCategoryDO rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
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
	public RateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	public void setRateQuotationDO(RateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
	/**
	 * @return the minimumChargeableWeightDO
	 */
	public RateMinChargeableWeightDO getMinimumChargeableWeightDO() {
		return minimumChargeableWeightDO;
	}
	/**
	 * @param minimumChargeableWeightDO the minimumChargeableWeightDO to set
	 */
	public void setMinimumChargeableWeightDO(
			RateMinChargeableWeightDO minimumChargeableWeightDO) {
		this.minimumChargeableWeightDO = minimumChargeableWeightDO;
	}
	public String getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}
	
}
