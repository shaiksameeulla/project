package com.ff.domain.billing;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class FinancialProductDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer financialProductId;
	private String financialProductCode;
	private String financialProductName;
	private String financialProductDesc;
	private String status;
	
	public Integer getFinancialProductId() {
		return financialProductId;
	}
	public void setFinancialProductId(Integer financialProductId) {
		this.financialProductId = financialProductId;
	}
	public String getFinancialProductCode() {
		return financialProductCode;
	}
	public void setFinancialProductCode(String financialProductCode) {
		this.financialProductCode = financialProductCode;
	}
	public String getFinancialProductName() {
		return financialProductName;
	}
	public void setFinancialProductName(String financialProductName) {
		this.financialProductName = financialProductName;
	}
	public String getFinancialProductDesc() {
		return financialProductDesc;
	}
	public void setFinancialProductDesc(String financialProductDesc) {
		this.financialProductDesc = financialProductDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
