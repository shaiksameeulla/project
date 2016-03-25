package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RiskSurchargeDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer riskSurchargeId;
	private RateComponentDO rateComponentDO;
	private RateCustomerCategoryDO rateCustomerCategoryDO;
	private Double minimumValue;
	private Double maximumValue;
	private String considerValue;
	private String chargeApplicable;
	private String dataFrom;
	private Integer insuredBy;
	private String rateComponentCode;
	
	
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
	 * @return the insuredBy
	 */
	public Integer getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(Integer insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the chargeApplicable
	 */
	public String getChargeApplicable() {
		return chargeApplicable;
	}
	/**
	 * @param chargeApplicable the chargeApplicable to set
	 */
	public void setChargeApplicable(String chargeApplicable) {
		this.chargeApplicable = chargeApplicable;
	}
	/**
	 * @return the dataFrom
	 */
	public String getDataFrom() {
		return dataFrom;
	}
	/**
	 * @param dataFrom the dataFrom to set
	 */
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	/**
	 * @return the riskSurchargeId
	 */
	public Integer getRiskSurchargeId() {
		return riskSurchargeId;
	}
	/**
	 * @param riskSurchargeId the riskSurchargeId to set
	 */
	public void setRiskSurchargeId(Integer riskSurchargeId) {
		this.riskSurchargeId = riskSurchargeId;
	}
	/**
	 * @return the rateComponent
	 */
	public RateComponentDO getRateComponentDO() {
		return rateComponentDO;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponentDO(RateComponentDO rateComponentDO) {
		this.rateComponentDO = rateComponentDO;
	}
	/**
	 * @return the rateCustomerCategoryDO
	 */
	public RateCustomerCategoryDO getRateCustomerCategoryDO() {
		return rateCustomerCategoryDO;
	}
	/**
	 * @param rateCustomerCategoryDO the rateCustomerCategoryDO to set
	 */
	public void setRateCustomerCategoryDO(
			RateCustomerCategoryDO rateCustomerCategoryDO) {
		this.rateCustomerCategoryDO = rateCustomerCategoryDO;
	}
	/**
	 * @return the minimumValue
	 */
	public Double getMinimumValue() {
		return minimumValue;
	}
	/**
	 * @param minimumValue the minimumValue to set
	 */
	public void setMinimumValue(Double minimumValue) {
		this.minimumValue = minimumValue;
	}
	/**
	 * @return the maximumValue
	 */
	public Double getMaximumValue() {
		return maximumValue;
	}
	/**
	 * @param maximumValue the maximumValue to set
	 */
	public void setMaximumValue(Double maximumValue) {
		this.maximumValue = maximumValue;
	}
	/**
	 * @return the considerValue
	 */
	public String getConsiderValue() {
		return considerValue;
	}
	/**
	 * @param considerValue the considerValue to set
	 */
	public void setConsiderValue(String considerValue) {
		this.considerValue = considerValue;
	}
	


}
