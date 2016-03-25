package com.ff.to.billing;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTypeTO;
import com.ff.serviceOfferring.ProductTO;

public class FinancialProductCustTypeMapTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305357349358659791L;
	private Integer financialProductMapId;
	private FinancialProductTO financialProductTO;
	private ProductTO productTO;
	private CustomerTypeTO customerTypeTO;
	private String status;
	
	public Integer getFinancialProductMapId() {
		return financialProductMapId;
	}
	public void setFinancialProductMapId(Integer financialProductMapId) {
		this.financialProductMapId = financialProductMapId;
	}
	public FinancialProductTO getFinancialProductTO() {
		return financialProductTO;
	}
	public void setFinancialProductTO(FinancialProductTO financialProductTO) {
		this.financialProductTO = financialProductTO;
	}
	public ProductTO getProductTO() {
		return productTO;
	}
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}
	public CustomerTypeTO getCustomerTypeTO() {
		return customerTypeTO;
	}
	public void setCustomerTypeTO(CustomerTypeTO customerTypeTO) {
		this.customerTypeTO = customerTypeTO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
