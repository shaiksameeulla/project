package com.ff.to.billing;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ReBillGDRAliasTO extends CGBaseTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 328523659133690704L;
	private Integer reBillingID;
	private String invoiceNumber;
	private String businessName;
	private String officeName;
	private String  bookingDate;
	private String consignmentNumber;
	private String consignmentType;
	private String cityName;
	private Double finalWeight;
	private Character contractFor;
	private Character rateCalculatedFor;
	private Double freightCharges;
	private Double riskSurcharge;
	private Double documentHandlingCharge;
	private Double parcelHandlingCharge;
	private Double airportHandlingCharge;
	private Double otherCharges;
	private Double totalCharges;
	
	public Integer getReBillingID() {
		return reBillingID;
	}
	public void setReBillingID(Integer reBillingID) {
		this.reBillingID = reBillingID;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getConsignmentType() {
		return consignmentType;
	}
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Double getFinalWeight() {
		return finalWeight;
	}
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}
	public Character getContractFor() {
		return contractFor;
	}
	public void setContractFor(Character contractFor) {
		this.contractFor = contractFor;
	}
	public Character getRateCalculatedFor() {
		return rateCalculatedFor;
	}
	public void setRateCalculatedFor(Character rateCalculatedFor) {
		this.rateCalculatedFor = rateCalculatedFor;
	}
	public Double getFreightCharges() {
		return freightCharges;
	}
	public void setFreightCharges(Double freightCharges) {
		this.freightCharges = freightCharges;
	}
	public Double getRiskSurcharge() {
		return riskSurcharge;
	}
	public void setRiskSurcharge(Double riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	public Double getDocumentHandlingCharge() {
		return documentHandlingCharge;
	}
	public void setDocumentHandlingCharge(Double documentHandlingCharge) {
		this.documentHandlingCharge = documentHandlingCharge;
	}
	public Double getParcelHandlingCharge() {
		return parcelHandlingCharge;
	}
	public void setParcelHandlingCharge(Double parcelHandlingCharge) {
		this.parcelHandlingCharge = parcelHandlingCharge;
	}
	public Double getAirportHandlingCharge() {
		return airportHandlingCharge;
	}
	public void setAirportHandlingCharge(Double airportHandlingCharge) {
		this.airportHandlingCharge = airportHandlingCharge;
	}
	public Double getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	public Double getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(Double totalCharges) {
		this.totalCharges = totalCharges;
	}

	
	
}
