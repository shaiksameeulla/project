package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;

public class RateCalculationProductCategoryHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448455027561884023L;
	private Integer rateQuotationProductCategoryHeaderId;
	private RateProductCategoryDO rateProductCategory;
	private RateMinChargeableWeightDO minimumChargeableWeightDO;
	private RateCalculationQuotationDO rateQuotationDO;
	private String calculationMethod;
	
	
	/**
	 * @return the calculationMethod
	 */
	public String getCalculationMethod() {
		return calculationMethod;
	}
	/**
	 * @param calculationMethod the calculationMethod to set
	 */
	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	/**
	 * @return the rateQuotationProductCategoryHeaderId
	 */
	public Integer getRateQuotationProductCategoryHeaderId() {
		return rateQuotationProductCategoryHeaderId;
	}
	/**
	 * @param rateQuotationProductCategoryHeaderId the rateQuotationProductCategoryHeaderId to set
	 */
	public void setRateQuotationProductCategoryHeaderId(
			Integer rateQuotationProductCategoryHeaderId) {
		this.rateQuotationProductCategoryHeaderId = rateQuotationProductCategoryHeaderId;
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
	/**
	 * @return the rateQuotationDO
	 */
	public RateCalculationQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	/**
	 * @param rateQuotationDO the rateQuotationDO to set
	 */
	public void setRateQuotationDO(RateCalculationQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
	
}
