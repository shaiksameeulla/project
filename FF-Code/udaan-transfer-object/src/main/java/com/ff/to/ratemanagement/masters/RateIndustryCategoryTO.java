package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateIndustryCategoryTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateIndustryCategoryId;
	private String rateIndustryCategoryCode;
	private String rateIndustryCategoryName;
	private Integer rateCustomerCategoryId;
	
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
