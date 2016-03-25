package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;

public class RateCalculationCustomerDO extends CGFactDO {

    /**
     * 
     */
    private static final long serialVersionUID = 375899922121291808L;
    private Integer customerId;
    private String customerCode;
    private String status;
    private String city;
	private RateCustomerCategoryDO customerCategoryDO;
	private CustomerTypeDO customerType;
	private String contractNo;
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the customerCategoryDO
	 */
	public RateCustomerCategoryDO getCustomerCategoryDO() {
		return customerCategoryDO;
	}
	/**
	 * @param customerCategoryDO the customerCategoryDO to set
	 */
	public void setCustomerCategoryDO(RateCustomerCategoryDO customerCategoryDO) {
		this.customerCategoryDO = customerCategoryDO;
	}
	/**
	 * @return the customerType
	 */
	public CustomerTypeDO getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(CustomerTypeDO customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
    
}
