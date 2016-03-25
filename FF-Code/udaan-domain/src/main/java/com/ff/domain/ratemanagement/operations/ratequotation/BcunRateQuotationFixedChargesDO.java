package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRateQuotationFixedChargesDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer rateQuotationFixedChargesId;
	private Double value;
	private Integer rateQuotationId;
	private String rateComponentCode;
	
	/**
	 * @return the rateQuotationFixedChargesId
	 */
	public Integer getRateQuotationFixedChargesId() {
		return rateQuotationFixedChargesId;
	}
	/**
	 * @param rateQuotationFixedChargesId the rateQuotationFixedChargesId to set
	 */
	public void setRateQuotationFixedChargesId(Integer rateQuotationFixedChargesId) {
		this.rateQuotationFixedChargesId = rateQuotationFixedChargesId;
	}
	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	/**
	 * @return the rateComponentCode
	 */
	public String getRateComponentCode() {
		return rateComponentCode;
	}
	/**
	 * @param rateComponentCode the rateComponentCode to set
	 */
	public void setRateComponentCode(String rateComponentCode) {
		this.rateComponentCode = rateComponentCode;
	}
	/**
	 * @return the rateQuotationId
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @param rateQuotationId the rateQuotationId to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
	}
	
			
}
