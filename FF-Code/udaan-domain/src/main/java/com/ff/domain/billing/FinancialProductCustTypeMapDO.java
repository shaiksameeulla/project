package com.ff.domain.billing;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.serviceOffering.ProductDO;

public class FinancialProductCustTypeMapDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6370414390353248866L;
	private Integer financialProductMapId;
	private FinancialProductDO financialProductDO;
	private ProductDO productDO;
	private CustomerTypeDO customerTypeDO;
	private String status;
	
	public Integer getFinancialProductMapId() {
		return financialProductMapId;
	}
	public void setFinancialProductMapId(Integer financialProductMapId) {
		this.financialProductMapId = financialProductMapId;
	}
	public FinancialProductDO getFinancialProductDO() {
		return financialProductDO;
	}
	public void setFinancialProductDO(FinancialProductDO financialProductDO) {
		this.financialProductDO = financialProductDO;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public CustomerTypeDO getCustomerTypeDO() {
		return customerTypeDO;
	}
	public void setCustomerTypeDO(CustomerTypeDO customerTypeDO) {
		this.customerTypeDO = customerTypeDO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
