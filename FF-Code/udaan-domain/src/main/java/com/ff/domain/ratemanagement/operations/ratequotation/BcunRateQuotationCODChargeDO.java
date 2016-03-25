package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRateQuotationCODChargeDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6870096557152896236L;
	
	private Integer rateQuotationCODChargeId;
	private Integer codChargeId;
	private Integer rateQuotationId;
	private String considerHigherFixedOrPercent;
	private Double fixedChargeValue;
	private Double percentileValue;
	private String considerFixed;
	private String calculatedValueFixedOrPercent;
	

	public String getCalculatedValueFixedOrPercent() {
		return calculatedValueFixedOrPercent;
	}
	public void setCalculatedValueFixedOrPercent(
			String calculatedValueFixedOrPercent) {
		this.calculatedValueFixedOrPercent = calculatedValueFixedOrPercent;
	}
	/**
	 * @return the considerFixed
	 */
	public String getConsiderFixed() {
		return considerFixed;
	}
	/**
	 * @param considerFixed the considerFixed to set
	 */
	public void setConsiderFixed(String considerFixed) {
		this.considerFixed = considerFixed;
	}
	
	/**
	 * @return the quotationCODChargeId
	 */
	public Integer getRateQuotationCODChargeId() {
		return rateQuotationCODChargeId;
	}
	/**
	 * @param quotationCODChargeId the quotationCODChargeId to set
	 */
	public void setRateQuotationCODChargeId(Integer rateQuotationCODChargeId) {
		this.rateQuotationCODChargeId = rateQuotationCODChargeId;
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
	/**
	 * @return the considerHigherFixedOrPercent
	 */
	public String getConsiderHigherFixedOrPercent() {
		return considerHigherFixedOrPercent;
	}
	/**
	 * @param considerHigherFixedOrPercent the considerHigherFixedOrPercent to set
	 */
	public void setConsiderHigherFixedOrPercent(String considerHigherFixedOrPercent) {
		this.considerHigherFixedOrPercent = considerHigherFixedOrPercent;
	}
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
	 * @return the rateQuotationID
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @param rateQuotationID the rateQuotationID to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
	}

}
