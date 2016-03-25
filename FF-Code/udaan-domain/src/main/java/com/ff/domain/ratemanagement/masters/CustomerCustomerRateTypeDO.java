/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

/**
 * @author prmeher
 * 
 */
public class CustomerCustomerRateTypeDO {

	/** Customer Code */
	private String customerCode;

	/** Customer Rate Category */
	private String rateCustomerCategoryCode;

	/**
	 * @param customerCode
	 * @param rateCustomerCategoryCode
	 */
	public CustomerCustomerRateTypeDO(String customerCode,
			String rateCustomerCategoryCode) {
		this.customerCode = customerCode;
		this.rateCustomerCategoryCode = rateCustomerCategoryCode;
	}
	
	/**
	 *  Default Constructor
	 */
	public CustomerCustomerRateTypeDO(){
		
	}
	
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the rateCustomerCategoryCode
	 */
	public String getRateCustomerCategoryCode() {
		return rateCustomerCategoryCode;
	}

	/**
	 * @param rateCustomerCategoryCode
	 *            the rateCustomerCategoryCode to set
	 */
	public void setRateCustomerCategoryCode(String rateCustomerCategoryCode) {
		this.rateCustomerCategoryCode = rateCustomerCategoryCode;
	}

}
