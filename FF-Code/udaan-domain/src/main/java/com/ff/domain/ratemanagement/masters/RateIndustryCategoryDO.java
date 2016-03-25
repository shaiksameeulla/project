package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateIndustryCategoryDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateIndustryCategoryId;
	private String rateIndustryCategoryCode;
	private String rateIndustryCategoryName;
	private Integer rateCustomerCategoryId;
	private String rateBenchMarkApplicable;
	private String rateQuotationApplicable;
	
	/**
	 * @return the rateBenchMarkApplicable
	 */
	public String getRateBenchMarkApplicable() {
		return rateBenchMarkApplicable;
	}
	/**
	 * @param rateBenchMarkApplicable the rateBenchMarkApplicable to set
	 */
	public void setRateBenchMarkApplicable(String rateBenchMarkApplicable) {
		this.rateBenchMarkApplicable = rateBenchMarkApplicable;
	}
	/**
	 * @return the rateQuotationApplicable
	 */
	public String getRateQuotationApplicable() {
		return rateQuotationApplicable;
	}
	/**
	 * @param rateQuotationApplicable the rateQuotationApplicable to set
	 */
	public void setRateQuotationApplicable(String rateQuotationApplicable) {
		this.rateQuotationApplicable = rateQuotationApplicable;
	}
	public String getRateIndustryCategoryCode() {
		return rateIndustryCategoryCode;
	}
	public void setRateIndustryCategoryCode(String rateIndustryCategoryCode) {
		this.rateIndustryCategoryCode = rateIndustryCategoryCode;
	}
	public Integer getRateIndustryCategoryId() {
		return rateIndustryCategoryId;
	}
	public void setRateIndustryCategoryId(Integer rateIndustryCategoryId) {
		this.rateIndustryCategoryId = rateIndustryCategoryId;
	}
	public String getRateIndustryCategoryName() {
		return rateIndustryCategoryName;
	}
	public void setRateIndustryCategoryName(String rateIndustryCategoryName) {
		this.rateIndustryCategoryName = rateIndustryCategoryName;
	}
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
}
