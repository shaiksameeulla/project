package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateCustomerCategoryDO extends CGMasterDO{
	
	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateCustomerCategoryId;
	private String rateCustomerCategoryCode;
	private String rateCustomerCategoryName;
	private RateIndustryCategoryDO rateIndustryCategoryDO;
	private String customerSubCategoryCode;
	private String customerSubCategoryName;
	
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
	public RateIndustryCategoryDO getRateIndustryCategoryDO() {
		return rateIndustryCategoryDO;
	}
	public void setRateIndustryCategoryDO(
			RateIndustryCategoryDO rateIndustryCategoryDO) {
		this.rateIndustryCategoryDO = rateIndustryCategoryDO;
	}
	
}
