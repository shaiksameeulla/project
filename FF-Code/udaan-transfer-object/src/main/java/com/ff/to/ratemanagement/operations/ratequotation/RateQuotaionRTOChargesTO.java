package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateQuotaionRTOChargesTO extends CGBaseTO{
	
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8651421176919129246L;
	private Integer quotaionRTOChargesId;
	private RateQuotationTO rateQuotation;
	private String rtoChargesApplicable;
	private String sameAsSlabRate;
	private Double discountOnSlab;
	private String rateComponent;
	private boolean proposedRates = Boolean.FALSE;
	private boolean basicInfo = Boolean.FALSE;
	private boolean fixedCharges = Boolean.FALSE;
	private String transMsg;
	private boolean isSaved = Boolean.FALSE;
	
	
	
	
	/**
	 * @return the isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved the isSaved to set
	 */
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	public String getTransMsg() {
		return transMsg;
	}
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}
	/**
	 * @return the proposedRates
	 */
	public boolean isProposedRates() {
		return proposedRates;
	}
	/**
	 * @param proposedRates the proposedRates to set
	 */
	public void setProposedRates(boolean proposedRates) {
		this.proposedRates = proposedRates;
	}
	/**
	 * @return the basicInfo
	 */
	public boolean isBasicInfo() {
		return basicInfo;
	}
	/**
	 * @param basicInfo the basicInfo to set
	 */
	public void setBasicInfo(boolean basicInfo) {
		this.basicInfo = basicInfo;
	}
	/**
	 * @return the fixedCharges
	 */
	public boolean isFixedCharges() {
		return fixedCharges;
	}
	/**
	 * @param fixedCharges the fixedCharges to set
	 */
	public void setFixedCharges(boolean fixedCharges) {
		this.fixedCharges = fixedCharges;
	}

	/**
	 * @return the quotaionRTOChargesId
	 */
	public Integer getQuotaionRTOChargesId() {
		return quotaionRTOChargesId;
	}
	/**
	 * @param quotaionRTOChargesId the quotaionRTOChargesId to set
	 */
	public void setQuotaionRTOChargesId(Integer quotaionRTOChargesId) {
		this.quotaionRTOChargesId = quotaionRTOChargesId;
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
	 * @return the rtoChargesApplicable
	 */
	public String getRtoChargesApplicable() {
		return rtoChargesApplicable;
	}
	/**
	 * @param rtoChargesApplicable the rtoChargesApplicable to set
	 */
	public void setRtoChargesApplicable(String rtoChargesApplicable) {
		this.rtoChargesApplicable = rtoChargesApplicable;
	}
	/**
	 * @return the sameAsSlabRate
	 */
	public String getSameAsSlabRate() {
		return sameAsSlabRate;
	}
	/**
	 * @param sameAsSlabRate the sameAsSlabRate to set
	 */
	public void setSameAsSlabRate(String sameAsSlabRate) {
		this.sameAsSlabRate = sameAsSlabRate;
	}
	/**
	 * @return the discountOnSlab
	 */
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
	/**
	 * @param discountOnSlab the discountOnSlab to set
	 */
	public void setDiscountOnSlab(Double discountOnSlab) {
		this.discountOnSlab = discountOnSlab;
	}
	/**
	 * @return the rateComponent
	 */
	public String getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(String rateComponent) {
		this.rateComponent = rateComponent;
	}
	
	
	
}
