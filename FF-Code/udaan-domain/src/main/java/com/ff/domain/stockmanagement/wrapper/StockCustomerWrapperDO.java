/**
 * 
 */
package com.ff.domain.stockmanagement.wrapper;

import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;

/**
 * @author mohammes
 *
 */
public class StockCustomerWrapperDO {

	/**
	 * @param shippedTOCode
	 * @param customerDO
	 */
	public StockCustomerWrapperDO(String shippedTOCode, CustomerDO customerDO) {
		this.shippedTOCode = shippedTOCode;
		this.customerDO = customerDO;
	}
	
	public StockCustomerWrapperDO(String shippedTOCode, Integer customerId,String customerCode,String businessName,CustomerTypeDO customerType) {
		this.shippedTOCode = shippedTOCode;
		this.customerDO = new CustomerDO();
		this.customerDO.setCustomerId(customerId);
		this.customerDO.setCustomerCode(customerCode);
		this.customerDO.setBusinessName(businessName);
		this.customerDO.setCustomerType(customerType);
	}
	
	public StockCustomerWrapperDO(Integer customerId,String customerCode,String businessName) {
		this.customerDO = new CustomerDO();
		this.customerDO.setCustomerId(customerId);
		this.customerDO.setCustomerCode(customerCode);
		this.customerDO.setBusinessName(businessName);
	}

	private String shippedTOCode;
	
	private CustomerDO customerDO;

	/**
	 * @return the shippedTOCode
	 */
	public String getShippedTOCode() {
		return shippedTOCode;
	}

	/**
	 * @return the customerDO
	 */
	public CustomerDO getCustomerDO() {
		return customerDO;
	}

	/**
	 * @param shippedTOCode the shippedTOCode to set
	 */
	public void setShippedTOCode(String shippedTOCode) {
		this.shippedTOCode = shippedTOCode;
	}

	/**
	 * @param customerDO the customerDO to set
	 */
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}
	
	
}
