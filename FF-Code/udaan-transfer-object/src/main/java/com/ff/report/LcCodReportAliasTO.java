package com.ff.report;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class LcCodReportAliasTO extends CGBaseTO implements Comparable<LcCodReportAliasTO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer customerId;
	String customerName;
	String custCode;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	@Override
	public int compareTo(LcCodReportAliasTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.customerName)) {
			if (StringUtils.isNotEmpty(obj1.customerName)) {
				String compStr1 = this.customerName.toUpperCase();
				String compStr2 = obj1.customerName.toUpperCase();
				returnVal = compStr1.compareTo(compStr2);
			}else{
				returnVal = -1;
			}
		}
		return returnVal;
	}
}
