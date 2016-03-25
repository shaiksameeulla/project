package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;

public class RateCustomerDO extends CGFactDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateCustomerId;
	private CustomerDO customerDO;
	//private FranchiseeDO franchiseeDO;
	private String customerType;
	public Integer getRateCustomerId() {
		return rateCustomerId;
	}
	public void setRateCustomerId(Integer rateCustomerId) {
		this.rateCustomerId = rateCustomerId;
	}
	public CustomerDO getCustomerDO() {
		return customerDO;
	}
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}
	/*public FranchiseeDO getFranchiseeDO() {
		return franchiseeDO;
	}
	public void setFranchiseeDO(FranchiseeDO franchiseeDO) {
		this.franchiseeDO = franchiseeDO;
	}*/
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public RateCustomerCategoryDO getRateCustomerCategoryDO() {
		return rateCustomerCategoryDO;
	}
	public void setRateCustomerCategoryDO(
			RateCustomerCategoryDO rateCustomerCategoryDO) {
		this.rateCustomerCategoryDO = rateCustomerCategoryDO;
	}
	private RateCustomerCategoryDO rateCustomerCategoryDO;
}
