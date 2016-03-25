package com.ff.to.billing;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ReBillConsgAliasTO extends CGBaseTO {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 7875287674721644294L;
	private Integer consgId; 
	private String consgNo;
	private Character consgStatus;
	private Integer operaOffice;
	private Double finalWt;
	private Integer customer;
	private String consgCode;
	private String destPincode;
	private String productCode;
	private Character insuredCode;
	private Double discount;
	private Double topayAmt;
	private Double splChg;
	private Double codAmt;
	private Double lcAmt;
	private String serviceOn;
	private Double declareValue;
	private String ebPrefCode;
	private String rateType;
	private Date eventDate;
	private Date bookDate;
	private Integer billingConsignmentId;
    private String bill_generated;
    private String booking_Rate;
    private String rto_Rate;
  
	public Integer getConsgId() {
		return consgId;
	}
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public Character getConsgStatus() {
		return consgStatus;
	}
	public void setConsgStatus(Character consgStatus) {
		this.consgStatus = consgStatus;
	}
	public Integer getOperaOffice() {
		return operaOffice;
	}
	public void setOperaOffice(Integer operaOffice) {
		this.operaOffice = operaOffice;
	}
	public Double getFinalWt() {
		return finalWt;
	}
	public void setFinalWt(Double finalWt) {
		this.finalWt = finalWt;
	}
	public Integer getCustomer() {
		return customer;
	}
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	public String getConsgCode() {
		return consgCode;
	}
	public void setConsgCode(String consgCode) {
		this.consgCode = consgCode;
	}
	public String getDestPincode() {
		return destPincode;
	}
	public void setDestPincode(String destPincode) {
		this.destPincode = destPincode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Character getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(Character insuredCode) {
		this.insuredCode = insuredCode;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getTopayAmt() {
		return topayAmt;
	}
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}
	public Double getSplChg() {
		return splChg;
	}
	public void setSplChg(Double splChg) {
		this.splChg = splChg;
	}
	public Double getCodAmt() {
		return codAmt;
	}
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	public Double getLcAmt() {
		return lcAmt;
	}
	public void setLcAmt(Double lcAmt) {
		this.lcAmt = lcAmt;
	}
	public String getServiceOn() {
		return serviceOn;
	}
	public void setServiceOn(String serviceOn) {
		this.serviceOn = serviceOn;
	}
	public Double getDeclareValue() {
		return declareValue;
	}
	public void setDeclareValue(Double declareValue) {
		this.declareValue = declareValue;
	}
	public String getEbPrefCode() {
		return ebPrefCode;
	}
	public void setEbPrefCode(String ebPrefCode) {
		this.ebPrefCode = ebPrefCode;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	
	public Integer getBillingConsignmentId() {
		return billingConsignmentId;
	}
	public void setBillingConsignmentId(Integer billingConsignmentId) {
		this.billingConsignmentId = billingConsignmentId;
	}
	public String getBill_generated() {
		return bill_generated;
	}
	public void setBill_generated(String bill_generated) {
		this.bill_generated = bill_generated;
	}
	public String getBooking_Rate() {
		return booking_Rate;
	}
	public void setBooking_Rate(String booking_Rate) {
		this.booking_Rate = booking_Rate;
	}
	public String getRto_Rate() {
		return rto_Rate;
	}
	public void setRto_Rate(String rto_Rate) {
		this.rto_Rate = rto_Rate;
	}
	  
	

}
