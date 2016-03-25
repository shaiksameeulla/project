package com.ff.to.rate;

public class CreditCustomerRateCalculationInputTO extends RateCalculationInputTO {
	
	private String customerCode;

	//Non persistence field
	private Integer quotationId; 
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
	
}
