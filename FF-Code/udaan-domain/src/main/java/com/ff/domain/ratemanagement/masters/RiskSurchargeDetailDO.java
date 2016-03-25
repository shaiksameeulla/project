package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RiskSurchargeDetailDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576890493434921229L;
	
	private Integer riskSurchargeDetailId;
	private Float minimumThreshold;
	private Float maximumThreshold;
	private String minimumOnly;
	private Double riskSurchagePercentile;
	private RiskSurchargeInsuredByDO insuredBy;
	private RiskSurchargeDO riskSurcharge;
	/**
	 * @return the riskSurchargeDetailId
	 */
	public Integer getRiskSurchargeDetailId() {
		return riskSurchargeDetailId;
	}
	/**
	 * @param riskSurchargeDetailId the riskSurchargeDetailId to set
	 */
	public void setRiskSurchargeDetailId(Integer riskSurchargeDetailId) {
		this.riskSurchargeDetailId = riskSurchargeDetailId;
	}
	/**
	 * @return the minimumThreshold
	 */
	public Float getMinimumThreshold() {
		return minimumThreshold;
	}
	/**
	 * @param minimumThreshold the minimumThreshold to set
	 */
	public void setMinimumThreshold(Float minimumThreshold) {
		this.minimumThreshold = minimumThreshold;
	}
	/**
	 * @return the maximumThreshold
	 */
	public Float getMaximumThreshold() {
		return maximumThreshold;
	}
	/**
	 * @param maximumThreshold the maximumThreshold to set
	 */
	public void setMaximumThreshold(Float maximumThreshold) {
		this.maximumThreshold = maximumThreshold;
	}
	/**
	 * @return the minimumOnly
	 */
	public String getMinimumOnly() {
		return minimumOnly;
	}
	/**
	 * @param minimumOnly the minimumOnly to set
	 */
	public void setMinimumOnly(String minimumOnly) {
		this.minimumOnly = minimumOnly;
	}
	/**
	 * @return the riskSurchagePercentile
	 */
	public Double getRiskSurchagePercentile() {
		return riskSurchagePercentile;
	}
	/**
	 * @param riskSurchagePercentile the riskSurchagePercentile to set
	 */
	public void setRiskSurchagePercentile(Double riskSurchagePercentile) {
		this.riskSurchagePercentile = riskSurchagePercentile;
	}
	/**
	 * @return the insuredBy
	 */
	public RiskSurchargeInsuredByDO getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(RiskSurchargeInsuredByDO insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the riskSurcharge
	 */
	public RiskSurchargeDO getRiskSurcharge() {
		return riskSurcharge;
	}
	/**
	 * @param riskSurcharge the riskSurcharge to set
	 */
	public void setRiskSurcharge(RiskSurchargeDO riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
				

}
