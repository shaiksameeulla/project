package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;


public class RateQuotationFixedChargesDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer rateQuotationFixedChargesId;
	private Double value;
	private RateQuotationDO rateQuotationDO;
	private String rateComponentCode;
	private RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeader;
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
	 * @return the rateQuotationDO
	 */
	public RateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	/**
	 * @param rateQuotationDO the rateQuotationDO to set
	 */
	public void setRateQuotationDO(RateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
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
	 * @return the rateQuotationProductCategoryHeader
	 */
	public RateQuotationProductCategoryHeaderDO getRateQuotationProductCategoryHeader() {
		return rateQuotationProductCategoryHeader;
	}
	/**
	 * @param rateQuotationProductCategoryHeader the rateQuotationProductCategoryHeader to set
	 */
	public void setRateQuotationProductCategoryHeader(
			RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeader) {
		this.rateQuotationProductCategoryHeader = rateQuotationProductCategoryHeader;
	}
	
			
}
