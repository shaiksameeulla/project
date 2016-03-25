package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RiskSurchargeInsuredByDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1752299215664410678L;
	
	private Integer riskSurchargeInsuredById;
	private String riskSurchargeInsuredByDesc;
	private String riskSurchargeInsuredByCode = "NA";
	private Double riskSurchargeInsuredValue;
	private Double percentile;
	
	
	
	/**
	 * @return the percentile
	 */
	public Double getPercentile() {
		return percentile;
	}
	/**
	 * @param percentile the percentile to set
	 */
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
	/**
	 * @return the riskSurchargeInsuredValue
	 */
	public Double getRiskSurchargeInsuredValue() {
		return riskSurchargeInsuredValue;
	}
	/**
	 * @param riskSurchargeInsuredValue the riskSurchargeInsuredValue to set
	 */
	public void setRiskSurchargeInsuredValue(Double riskSurchargeInsuredValue) {
		this.riskSurchargeInsuredValue = riskSurchargeInsuredValue;
	}
	/**
	 * @return the riskSurchargeInsuredById
	 */
	public Integer getRiskSurchargeInsuredById() {
		return riskSurchargeInsuredById;
	}
	/**
	 * @param riskSurchargeInsuredById the riskSurchargeInsuredById to set
	 */
	public void setRiskSurchargeInsuredById(Integer riskSurchargeInsuredById) {
		this.riskSurchargeInsuredById = riskSurchargeInsuredById;
	}
	/**
	 * @return the riskSurchargeInsuredByDesc
	 */
	public String getRiskSurchargeInsuredByDesc() {
		return riskSurchargeInsuredByDesc;
	}
	/**
	 * @param riskSurchargeInsuredByDesc the riskSurchargeInsuredByDesc to set
	 */
	public void setRiskSurchargeInsuredByDesc(String riskSurchargeInsuredByDesc) {
		this.riskSurchargeInsuredByDesc = riskSurchargeInsuredByDesc;
	}
	/**
	 * @return the riskSurchargeInsuredByCode
	 */
	public String getRiskSurchargeInsuredByCode() {
		return riskSurchargeInsuredByCode;
	}
	/**
	 * @param riskSurchargeInsuredByCode the riskSurchargeInsuredByCode to set
	 */
	public void setRiskSurchargeInsuredByCode(String riskSurchargeInsuredByCode) {
		this.riskSurchargeInsuredByCode = riskSurchargeInsuredByCode;
	}
	


}
