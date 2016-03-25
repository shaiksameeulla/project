package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;


public class RateCustomerTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateCustomerId;
	private CustomerTO customerTO;
	private String customerType;
	private RateCustomerCategoryTO rateCustomerCategoryTO;
	
	public Integer getRateCustomerId() {
		return rateCustomerId;
	}
	public void setRateCustomerId(Integer rateCustomerId) {
		this.rateCustomerId = rateCustomerId;
	}
	public CustomerTO getCustomerTO() {
		return customerTO;
	}
	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public RateCustomerCategoryTO getRateCustomerCategoryTO() {
		return rateCustomerCategoryTO;
	}
	public void setRateCustomerCategoryTO(
			RateCustomerCategoryTO rateCustomerCategoryTO) {
		this.rateCustomerCategoryTO = rateCustomerCategoryTO;
	}
	
}
