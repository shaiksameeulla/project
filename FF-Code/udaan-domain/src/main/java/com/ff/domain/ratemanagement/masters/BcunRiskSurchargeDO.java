package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRiskSurchargeDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer riskSurchargeId;
	private Integer rateCustomerCategoryId;
	private Double minimumValue;
	private Double maximumValue;
	private String considerValue;
	private String chargeApplicable;
	private String dataFrom;
	private Integer insuredBy;
	private String rateComponentCode;
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
	 * @return the rateCustomerCategoryId
	 */
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	/**
	 * @param rateCustomerCategoryId the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
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
	
	
}
