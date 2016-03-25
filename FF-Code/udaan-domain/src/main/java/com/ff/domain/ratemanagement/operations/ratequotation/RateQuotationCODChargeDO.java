package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;

public class RateQuotationCODChargeDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6870096557152896236L;
	
	private Integer rateQuotationCODChargeId;
	private CodChargeDO codChargeDO;
	private RateQuotationDO rateQuotationDO;
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
	 * @return the codChargeId
	 */
	public CodChargeDO getCodChargeDO() {
		return codChargeDO;
	}
	/**
	 * @param codChargeId the codChargeId to set
	 */
	public void setCodChargeDO(CodChargeDO codChargeDO) {
		this.codChargeDO = codChargeDO;
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
	

	

}
