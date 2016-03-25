package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class CashRateConfigRTOChargesTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateCashRTOChargesId;//The rateCashRTOChargesId
	private String rtoChargeApplicable;//The rtoChargeApplicable
	private String sameAsSlabRate;//The sameAsSlabRate
	private Double discountOnSlab;//The discountOnSlab
	private String rateComponentCode;//The rateComponentCode
	private Integer productMapId;//The productMapId
	private String priorityInd;//The priorityInd. i.e. Y= Priority, N= Non-Priority
	
	/** If checked, then its ON. */
	private String rtoChargeApplicableChk;
	private String sameAsSlabRateChk;
	
	/** Product.s - Ids */
	private Integer coRTOChargesId;
	private Integer arRTOChargesId;
	private Integer trRTOChargesId;
	private Integer prRTOChargesId;
	private Integer commonBasedRTOChargesId;
	
	
	/**
	 * @return the commonBasedRTOChargesId
	 */
	public Integer getCommonBasedRTOChargesId() {
		return commonBasedRTOChargesId;
	}
	/**
	 * @param commonBasedRTOChargesId the commonBasedRTOChargesId to set
	 */
	public void setCommonBasedRTOChargesId(Integer commonBasedRTOChargesId) {
		this.commonBasedRTOChargesId = commonBasedRTOChargesId;
	}
	/**
	 * @return the coRTOChargesId
	 */
	public Integer getCoRTOChargesId() {
		return coRTOChargesId;
	}
	/**
	 * @param coRTOChargesId the coRTOChargesId to set
	 */
	public void setCoRTOChargesId(Integer coRTOChargesId) {
		this.coRTOChargesId = coRTOChargesId;
	}
	/**
	 * @return the arRTOChargesId
	 */
	public Integer getArRTOChargesId() {
		return arRTOChargesId;
	}
	/**
	 * @param arRTOChargesId the arRTOChargesId to set
	 */
	public void setArRTOChargesId(Integer arRTOChargesId) {
		this.arRTOChargesId = arRTOChargesId;
	}
	/**
	 * @return the trRTOChargesId
	 */
	public Integer getTrRTOChargesId() {
		return trRTOChargesId;
	}
	/**
	 * @param trRTOChargesId the trRTOChargesId to set
	 */
	public void setTrRTOChargesId(Integer trRTOChargesId) {
		this.trRTOChargesId = trRTOChargesId;
	}
	/**
	 * @return the prRTOChargesId
	 */
	public Integer getPrRTOChargesId() {
		return prRTOChargesId;
	}
	/**
	 * @param prRTOChargesId the prRTOChargesId to set
	 */
	public void setPrRTOChargesId(Integer prRTOChargesId) {
		this.prRTOChargesId = prRTOChargesId;
	}
	/**
	 * @return the rtoChargeApplicableChk
	 */
	public String getRtoChargeApplicableChk() {
		return rtoChargeApplicableChk;
	}
	/**
	 * @param rtoChargeApplicableChk the rtoChargeApplicableChk to set
	 */
	public void setRtoChargeApplicableChk(String rtoChargeApplicableChk) {
		this.rtoChargeApplicableChk = rtoChargeApplicableChk;
	}
	/**
	 * @return the sameAsSlabRateChk
	 */
	public String getSameAsSlabRateChk() {
		return sameAsSlabRateChk;
	}
	/**
	 * @param sameAsSlabRateChk the sameAsSlabRateChk to set
	 */
	public void setSameAsSlabRateChk(String sameAsSlabRateChk) {
		this.sameAsSlabRateChk = sameAsSlabRateChk;
	}
	/**
	 * @return the rateCashRTOChargesId
	 */
	public Integer getRateCashRTOChargesId() {
		return rateCashRTOChargesId;
	}
	/**
	 * @param rateCashRTOChargesId the rateCashRTOChargesId to set
	 */
	public void setRateCashRTOChargesId(Integer rateCashRTOChargesId) {
		this.rateCashRTOChargesId = rateCashRTOChargesId;
	}
	/**
	 * @return the rtoChargeApplicable
	 */
	public String getRtoChargeApplicable() {
		return rtoChargeApplicable;
	}
	/**
	 * @param rtoChargeApplicable the rtoChargeApplicable to set
	 */
	public void setRtoChargeApplicable(String rtoChargeApplicable) {
		this.rtoChargeApplicable = rtoChargeApplicable;
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
	 * @return the productMapId
	 */
	public Integer getProductMapId() {
		return productMapId;
	}
	/**
	 * @param productMapId the productMapId to set
	 */
	public void setProductMapId(Integer productMapId) {
		this.productMapId = productMapId;
	}
	/**
	 * @return the priorityInd
	 */
	public String getPriorityInd() {
		return priorityInd;
	}
	/**
	 * @param priorityInd the priorityInd to set
	 */
	public void setPriorityInd(String priorityInd) {
		this.priorityInd = priorityInd;
	}
	
}
