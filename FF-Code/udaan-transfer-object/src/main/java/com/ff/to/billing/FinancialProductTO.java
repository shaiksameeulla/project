package com.ff.to.billing;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class FinancialProductTO extends CGBaseTO implements
Comparable<FinancialProductTO>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6631166884010622086L;
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
	
	@Override
	public int compareTo(FinancialProductTO financialProduct) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(financialProductName)) {
			returnVal = this.financialProductName.compareTo(financialProduct.getFinancialProductName());
		}
		return returnVal;
	}
}
