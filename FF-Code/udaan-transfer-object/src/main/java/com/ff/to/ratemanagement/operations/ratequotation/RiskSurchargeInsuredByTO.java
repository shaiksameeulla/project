package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
/**
 * @author preegupt
 *
 */
public class RiskSurchargeInsuredByTO  extends CGBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer riskSurchargeInsuredById;
	private String riskSurchargeInsuredByName;
	private String riskSurchargeInsuredByCode;
	private Float insuredValue;
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
	 * @return the riskSurchargeInsuredByName
	 */
	public String getRiskSurchargeInsuredByName() {
		return riskSurchargeInsuredByName;
	}
	/**
	 * @param riskSurchargeInsuredByName the riskSurchargeInsuredByName to set
	 */
	public void setRiskSurchargeInsuredByName(String riskSurchargeInsuredByName) {
		this.riskSurchargeInsuredByName = riskSurchargeInsuredByName;
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
	/**
	 * @return the insuredValue
	 */
	public Float getInsuredValue() {
		return insuredValue;
	}
	/**
	 * @param insuredValue the insuredValue to set
	 */
	public void setInsuredValue(Float insuredValue) {
		this.insuredValue = insuredValue;
	}
	
}
