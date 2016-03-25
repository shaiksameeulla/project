package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateProductCategoryTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateProductCategoryId;
	private String rateProductCategoryCode;
	private String rateProductCategoryName;
	private String productCategoryType;
	
	public Integer getRateProductCategoryId() {
		return rateProductCategoryId;
	}
	public void setRateProductCategoryId(Integer rateProductCategoryId) {
		this.rateProductCategoryId = rateProductCategoryId;
	}
	public String getRateProductCategoryName() {
		return rateProductCategoryName;
	}
	public void setRateProductCategoryName(String rateProductCategoryName) {
		this.rateProductCategoryName = rateProductCategoryName;
	}
	public String getProductCategoryType() {
		return productCategoryType;
	}
	public void setProductCategoryType(String productCategoryType) {
		this.productCategoryType = productCategoryType;
	}
	public String getRateProductCategoryCode() {
		return rateProductCategoryCode;
	}
	public void setRateProductCategoryCode(String rateProductCategoryCode) {
		this.rateProductCategoryCode = rateProductCategoryCode;
	}

}
