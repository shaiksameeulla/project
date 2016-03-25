package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateQuotationCODChargeTO  extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9117801342992983908L;

	private Integer quotationCODChargeId;
	private Integer codChargeId;
	private RateQuotationTO rateQuotation;
	private String consideeHigherFixedPercent;
	private String considerFixed;
	private Double fixedChargeValue;
	private Double percentileValue;
	
	
	
	
	/**
	 * @return the codChargeId
	 */
	public Integer getCodChargeId() {
		return codChargeId;
	}
	/**
	 * @param codChargeId the codChargeId to set
	 */
	public void setCodChargeId(Integer codChargeId) {
		this.codChargeId = codChargeId;
	}
	/**
	
	 * @return the quotationCODChargeId
	 */
	public Integer getQuotationCODChargeId() {
		return quotationCODChargeId;
	}
	/**
	 * @param quotationCODChargeId the quotationCODChargeId to set
	 */
	public void setQuotationCODChargeId(Integer quotationCODChargeId) {
		this.quotationCODChargeId = quotationCODChargeId;
	}
	
	/**
	 * @return the rateQuotation
	 */
	public RateQuotationTO getRateQuotation() {
		return rateQuotation;
	}
	/**
	 * @param rateQuotation the rateQuotation to set
	 */
	public void setRateQuotation(RateQuotationTO rateQuotation) {
		this.rateQuotation = rateQuotation;
	}
	
	/**
	 * @return the consideeHigherFixedPercent
	 */
	public String getConsideeHigherFixedPercent() {
		return consideeHigherFixedPercent;
	}
	/**
	 * @param consideeHigherFixedPercent the consideeHigherFixedPercent to set
	 */
	public void setConsideeHigherFixedPercent(String consideeHigherFixedPercent) {
		this.consideeHigherFixedPercent = consideeHigherFixedPercent;
	}
	/**
	 * @return the fixedChargeValue
	 */
	public Double getFixedChargeValue() {
		return fixedChargeValue;
	}
	/**
	 * @param fixedChargeValue the fixedChargeValue to set
	 */
	public void setFixedChargeValue(Double fixedChargeValue) {
		this.fixedChargeValue = fixedChargeValue;
	}
	/**
	 * @return the percentileValue
	 */
	public Double getPercentileValue() {
		return percentileValue;
	}
	/**
	 * @param percentileValue the percentileValue to set
	 */
	public void setPercentileValue(Double percentileValue) {
		this.percentileValue = percentileValue;
	}
	public String getConsiderFixed() {
		return considerFixed;
	}
	public void setConsiderFixed(String considerFixed) {
		this.considerFixed = considerFixed;
	}
	
}
