package com.ff.to.billing;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CustModificationAliasTO extends CGBaseTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String consignmentNo;
	String isConsgDelivered;
	String billingStatus;
	String bookDetails;
	Date bkgDate;
	String isExcessConsg;
	String rateDetails;
	String custCheck;
	String expenseCheck;
	String collectionCheck;
	String liabilityCheck;
	String liabilitySapCheck;
//	String isDelivered;
	Integer custId;
	String custCode;
	String shipToCode;
	String custName;
	String custTypeCode;
	Double totalConsignmentWeight;
	Double declaredvalue;
	Integer officeId;
	Integer cityId;
	
	// Return values
	String isCustEditable = "Y";
	String isWeightEditable = "Y";
	String isCnTypeEditable = "Y";
		
	public String getConsignmentNo() {
		return consignmentNo;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}
	public String getIsConsgDelivered() {
		return isConsgDelivered;
	}
	public void setIsConsgDelivered(String isConsgDelivered) {
		this.isConsgDelivered = isConsgDelivered;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getBookDetails() {
		return bookDetails;
	}
	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}
	public Date getBkgDate() {
		return bkgDate;
	}
	public void setBkgDate(Date bkgDate) {
		this.bkgDate = bkgDate;
	}
	public String getIsExcessConsg() {
		return isExcessConsg;
	}
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}
	public String getRateDetails() {
		return rateDetails;
	}
	public void setRateDetails(String rateDetails) {
		this.rateDetails = rateDetails;
	}
	public String getCustCheck() {
		return custCheck;
	}
	public void setCustCheck(String custCheck) {
		this.custCheck = custCheck;
	}
	public String getExpenseCheck() {
		return expenseCheck;
	}
	public void setExpenseCheck(String expenseCheck) {
		this.expenseCheck = expenseCheck;
	}
	public String getCollectionCheck() {
		return collectionCheck;
	}
	public void setCollectionCheck(String collectionCheck) {
		this.collectionCheck = collectionCheck;
	}
	public String getLiabilityCheck() {
		return liabilityCheck;
	}
	public void setLiabilityCheck(String liabilityCheck) {
		this.liabilityCheck = liabilityCheck;
	}
	public String getLiabilitySapCheck() {
		return liabilitySapCheck;
	}
	public void setLiabilitySapCheck(String liabilitySapCheck) {
		this.liabilitySapCheck = liabilitySapCheck;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getShipToCode() {
		return shipToCode;
	}
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustTypeCode() {
		return custTypeCode;
	}
	public void setCustTypeCode(String custTypeCode) {
		this.custTypeCode = custTypeCode;
	}
	public Double getTotalConsignmentWeight() {
		return totalConsignmentWeight;
	}
	public void setTotalConsignmentWeight(Double totalConsignmentWeight) {
		this.totalConsignmentWeight = totalConsignmentWeight;
	}
	public Double getDeclaredvalue() {
		return declaredvalue;
	}
	public void setDeclaredvalue(Double declaredvalue) {
		this.declaredvalue = declaredvalue;
	}
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getIsCustEditable() {
		return isCustEditable;
	}
	public void setIsCustEditable(String isCustEditable) {
		this.isCustEditable = isCustEditable;
	}
	public String getIsWeightEditable() {
		return isWeightEditable;
	}
	public void setIsWeightEditable(String isWeightEditable) {
		this.isWeightEditable = isWeightEditable;
	}
	public String getIsCnTypeEditable() {
		return isCnTypeEditable;
	}
	public void setIsCnTypeEditable(String isCnTypeEditable) {
		this.isCnTypeEditable = isCnTypeEditable;
	}
}
