package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateCustomerCategoryDO extends CGMasterDO{
	
	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateCustomerCategoryId;
	private String rateCustomerCategoryCode;
	private String rateCustomerCategoryName;
	
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
	
}
