package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateCustomerCategoryTO extends CGBaseTO{
	
	private static final long serialVersionUID = 1L;
	
	private Integer rateCustomerCategoryId;
	private String rateCustomerCategoryCode;
	private String rateCustomerCategoryName;
	private Integer rateIndustryCategory;
	private RateIndustryCategoryTO rateIndustryCategoryDO;
	private String customerSubCategoryCode;
	private String customerSubCategoryName;
	
	public String getRateCustomerCategoryCode() {
		return rateCustomerCategoryCode;
	}
	public void setRateCustomerCategoryCode(String rateCustomerCategoryCode) {
		this.rateCustomerCategoryCode = rateCustomerCategoryCode;
	}
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
	public String getRateCustomerCategoryName() {
		return rateCustomerCategoryName;
	}
	public void setRateCustomerCategoryName(String rateCustomerCategoryName) {
		this.rateCustomerCategoryName = rateCustomerCategoryName;
	}
	public Integer getRateIndustryCategory() {
		return rateIndustryCategory;
	}
	public void setRateIndustryCategory(Integer rateIndustryCategory) {
		this.rateIndustryCategory = rateIndustryCategory;
	}
	/**
	 * @return the rateIndustryCategoryDO
	 */
	public RateIndustryCategoryTO getRateIndustryCategoryDO() {
		return rateIndustryCategoryDO;
	}
	/**
	 * @param rateIndustryCategoryDO the rateIndustryCategoryDO to set
	 */
	public void setRateIndustryCategoryDO(
			RateIndustryCategoryTO rateIndustryCategoryDO) {
		this.rateIndustryCategoryDO = rateIndustryCategoryDO;
	}
	/**
	 * @return the customerSubCategoryCode
	 */
	public String getCustomerSubCategoryCode() {
		return customerSubCategoryCode;
	}
	/**
	 * @param customerSubCategoryCode the customerSubCategoryCode to set
	 */
	public void setCustomerSubCategoryCode(String customerSubCategoryCode) {
		this.customerSubCategoryCode = customerSubCategoryCode;
	}
	/**
	 * @return the customerSubCategoryName
	 */
	public String getCustomerSubCategoryName() {
		return customerSubCategoryName;
	}
	/**
	 * @param customerSubCategoryName the customerSubCategoryName to set
	 */
	public void setCustomerSubCategoryName(String customerSubCategoryName) {
		this.customerSubCategoryName = customerSubCategoryName;
	}
}
