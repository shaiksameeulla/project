package com.ff.domain.consignment;

import java.io.Serializable;

import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.InsuredByDO;


/***
 * BR:1. If consignment is updated by consignment modification functionality then those changes will not be overridden
 * 2. To identify whether cn is updated by cn modification check if the consignment created by user id is EMPDADMIN ie 1
 * 3.  if cn is updated by cn modification then preserve all these changes in ConsignmentBcunHelperDO 
 *  and then finally inject all these values to Actual consignment
 * ********************************************
 * 
 * if any new field is added in consignment modification screen then add same property in this Domain object.
 * and make sure this class property and consignmentDo property should be same in all the manner
 * ******************************************
 * * */
@SuppressWarnings("serial")
public class ConsignmentBcunHelperDO implements Serializable {

	
	
	private Double actualWeight;
	private Double baAmt;
	// for Billing
	private String billingStatus;
	private String changedAfterBillingWtDest;
	private String changedAfterNewRateCmpnt;
	
	private Double codAmt;
	private ConsigneeConsignorDO consignor;
	private Integer customer;
	private Double declaredValue;
	private Double finalWeight;
	
	private InsuredByDO insuredBy;
	private Integer operatingOffice;
	private Integer orgOffId;
	private Double price;
	private String rateType;
	
	private Double splChg;
	private PincodeDO destPincodeId;
	private Double lcAmount;
	private String lcBankName;
	private Double topayAmt;
	private Integer createdBy;
	private String isExcessConsg;
	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @return the baAmt
	 */
	public Double getBaAmt() {
		return baAmt;
	}
	/**
	 * @param baAmt the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}
	/**
	 * @return the billingStatus
	 */
	public String getBillingStatus() {
		return billingStatus;
	}
	/**
	 * @param billingStatus the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	/**
	 * @return the changedAfterBillingWtDest
	 */
	public String getChangedAfterBillingWtDest() {
		return changedAfterBillingWtDest;
	}
	/**
	 * @param changedAfterBillingWtDest the changedAfterBillingWtDest to set
	 */
	public void setChangedAfterBillingWtDest(String changedAfterBillingWtDest) {
		this.changedAfterBillingWtDest = changedAfterBillingWtDest;
	}
	/**
	 * @return the changedAfterNewRateCmpnt
	 */
	public String getChangedAfterNewRateCmpnt() {
		return changedAfterNewRateCmpnt;
	}
	/**
	 * @param changedAfterNewRateCmpnt the changedAfterNewRateCmpnt to set
	 */
	public void setChangedAfterNewRateCmpnt(String changedAfterNewRateCmpnt) {
		this.changedAfterNewRateCmpnt = changedAfterNewRateCmpnt;
	}
	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}
	/**
	 * @param codAmt the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	/**
	 * @return the consignor
	 */
	public ConsigneeConsignorDO getConsignor() {
		return consignor;
	}
	/**
	 * @param consignor the consignor to set
	 */
	public void setConsignor(ConsigneeConsignorDO consignor) {
		this.consignor = consignor;
	}
	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the finalWeight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}
	/**
	 * @param finalWeight the finalWeight to set
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}
	/**
	 * @return the insuredBy
	 */
	public InsuredByDO getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(InsuredByDO insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}
	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}
	/**
	 * @return the orgOffId
	 */
	public Integer getOrgOffId() {
		return orgOffId;
	}
	/**
	 * @param orgOffId the orgOffId to set
	 */
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	/**
	 * @return the splChg
	 */
	public Double getSplChg() {
		return splChg;
	}
	/**
	 * @param splChg the splChg to set
	 */
	public void setSplChg(Double splChg) {
		this.splChg = splChg;
	}
	/**
	 * @return the destPincodeId
	 */
	public PincodeDO getDestPincodeId() {
		return destPincodeId;
	}
	/**
	 * @param destPincodeId the destPincodeId to set
	 */
	public void setDestPincodeId(PincodeDO destPincodeId) {
		this.destPincodeId = destPincodeId;
	}
	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}
	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}
	/**
	 * @return the topayAmt
	 */
	public Double getTopayAmt() {
		return topayAmt;
	}
	/**
	 * @param topayAmt the topayAmt to set
	 */
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the isExcessConsg
	 */
	public String getIsExcessConsg() {
		return isExcessConsg;
	}
	/**
	 * @param isExcessConsg the isExcessConsg to set
	 */
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}
	
	
	
	
	
}
