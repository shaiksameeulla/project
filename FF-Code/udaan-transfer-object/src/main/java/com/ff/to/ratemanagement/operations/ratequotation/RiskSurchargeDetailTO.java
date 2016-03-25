package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author preegupt
 *
 */
public class RiskSurchargeDetailTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer RiskSurchargeDetailId;
	private Float minimumThreshold;
	private Float maximumThreshold;
	private String minimumOnly;
	private Float riskSurchagePercentile;
	private RiskSurchargeInsuredByTO insuredBy;
	private RiskSurchargeTO riskSurcharge;
	/**
	 * @return the riskSurchargeDetailId
	 */
	public Integer getRiskSurchargeDetailId() {
		return RiskSurchargeDetailId;
	}
	/**
	 * @param riskSurchargeDetailId the riskSurchargeDetailId to set
	 */
	public void setRiskSurchargeDetailId(Integer riskSurchargeDetailId) {
		RiskSurchargeDetailId = riskSurchargeDetailId;
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
	public Float getRiskSurchagePercentile() {
		return riskSurchagePercentile;
	}
	/**
	 * @param riskSurchagePercentile the riskSurchagePercentile to set
	 */
	public void setRiskSurchagePercentile(Float riskSurchagePercentile) {
		this.riskSurchagePercentile = riskSurchagePercentile;
	}
	/**
	 * @return the insuredBy
	 */
	public RiskSurchargeInsuredByTO getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(RiskSurchargeInsuredByTO insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the riskSurcharge
	 */
	public RiskSurchargeTO getRiskSurcharge() {
		return riskSurcharge;
	}
	/**
	 * @param riskSurcharge the riskSurcharge to set
	 */
	public void setRiskSurcharge(RiskSurchargeTO riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	
	
}
