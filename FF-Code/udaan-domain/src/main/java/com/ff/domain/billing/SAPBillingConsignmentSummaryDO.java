/**
 * 
 */
package com.ff.domain.billing;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */
public class SAPBillingConsignmentSummaryDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2073708210565064982L;
	
	private Long Id;
	private Date bookingDate;
	private String bookingOffice;
	private String custSoldTo;
	private String custShipTo;
	private String distributionChannel;
	private Integer transactionNumber;
	private String destinationBranch;
	private String productCode;
	private String bookingType;
	private Integer quantity;
	private String consgStatus;
	private Double weight;
	private Double freight;
	private Double fuelSurcharge;
	private Double riskSurcharge;
	private Double parcelHandlingCharges;
	private Double airportHandlingCharges;
	private Double documentHandlingCharges;
	private Double valueOfMaterial;
	private Double codCharges;
	private Double toPayCharges;
	private Double lcCharges;
	private Double codAmt;
	private Double lcAmt;
	private Double specialCharges;
	private Double others;
	private Double serviceTax;
	private Double educationCess;
	private Double secHighEduCess;
	private Double stateTax;
	private Double surchargeOnStateTax;
	private Double grandTotal;
	private String summaryCategory;
	private String destinationOfc;
	private String exception;
	
	
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the bookingOffice
	 */
	public String getBookingOffice() {
		return bookingOffice;
	}
	/**
	 * @param bookingOffice the bookingOffice to set
	 */
	public void setBookingOffice(String bookingOffice) {
		this.bookingOffice = bookingOffice;
	}
	/**
	 * @return the custSoldTo
	 */
	public String getCustSoldTo() {
		return custSoldTo;
	}
	/**
	 * @param custSoldTo the custSoldTo to set
	 */
	public void setCustSoldTo(String custSoldTo) {
		this.custSoldTo = custSoldTo;
	}
	/**
	 * @return the custShipTo
	 */
	public String getCustShipTo() {
		return custShipTo;
	}
	/**
	 * @param custShipTo the custShipTo to set
	 */
	public void setCustShipTo(String custShipTo) {
		this.custShipTo = custShipTo;
	}
	/**
	 * @return the distributionChannel
	 */
	public String getDistributionChannel() {
		return distributionChannel;
	}
	/**
	 * @param distributionChannel the distributionChannel to set
	 */
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	
	/**
	 * @return the transactionNumber
	 */
	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the destinationBranch
	 */
	public String getDestinationBranch() {
		return destinationBranch;
	}
	/**
	 * @param destinationBranch the destinationBranch to set
	 */
	public void setDestinationBranch(String destinationBranch) {
		this.destinationBranch = destinationBranch;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the consgStatus
	 */
	public String getConsgStatus() {
		return consgStatus;
	}
	/**
	 * @param consgStatus the consgStatus to set
	 */
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * @return the freight
	 */
	public Double getFreight() {
		return freight;
	}
	/**
	 * @param freight the freight to set
	 */
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	/**
	 * @return the fuelSurcharge
	 */
	public Double getFuelSurcharge() {
		return fuelSurcharge;
	}
	/**
	 * @param fuelSurcharge the fuelSurcharge to set
	 */
	public void setFuelSurcharge(Double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	/**
	 * @return the riskSurcharge
	 */
	public Double getRiskSurcharge() {
		return riskSurcharge;
	}
	/**
	 * @param riskSurcharge the riskSurcharge to set
	 */
	public void setRiskSurcharge(Double riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	/**
	 * @return the parcelHandlingCharges
	 */
	public Double getParcelHandlingCharges() {
		return parcelHandlingCharges;
	}
	/**
	 * @param parcelHandlingCharges the parcelHandlingCharges to set
	 */
	public void setParcelHandlingCharges(Double parcelHandlingCharges) {
		this.parcelHandlingCharges = parcelHandlingCharges;
	}
	/**
	 * @return the airportHandlingCharges
	 */
	public Double getAirportHandlingCharges() {
		return airportHandlingCharges;
	}
	/**
	 * @param airportHandlingCharges the airportHandlingCharges to set
	 */
	public void setAirportHandlingCharges(Double airportHandlingCharges) {
		this.airportHandlingCharges = airportHandlingCharges;
	}
	/**
	 * @return the documentHandlingCharges
	 */
	public Double getDocumentHandlingCharges() {
		return documentHandlingCharges;
	}
	/**
	 * @param documentHandlingCharges the documentHandlingCharges to set
	 */
	public void setDocumentHandlingCharges(Double documentHandlingCharges) {
		this.documentHandlingCharges = documentHandlingCharges;
	}
	/**
	 * @return the valueOfMaterial
	 */
	public Double getValueOfMaterial() {
		return valueOfMaterial;
	}
	/**
	 * @param valueOfMaterial the valueOfMaterial to set
	 */
	public void setValueOfMaterial(Double valueOfMaterial) {
		this.valueOfMaterial = valueOfMaterial;
	}
	/**
	 * @return the codCharges
	 */
	public Double getCodCharges() {
		return codCharges;
	}
	/**
	 * @param codCharges the codCharges to set
	 */
	public void setCodCharges(Double codCharges) {
		this.codCharges = codCharges;
	}
	/**
	 * @return the toPayCharges
	 */
	public Double getToPayCharges() {
		return toPayCharges;
	}
	/**
	 * @param toPayCharges the toPayCharges to set
	 */
	public void setToPayCharges(Double toPayCharges) {
		this.toPayCharges = toPayCharges;
	}
	/**
	 * @return the lcCharges
	 */
	public Double getLcCharges() {
		return lcCharges;
	}
	/**
	 * @param lcCharges the lcCharges to set
	 */
	public void setLcCharges(Double lcCharges) {
		this.lcCharges = lcCharges;
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
	 * @return the lcAmt
	 */
	public Double getLcAmt() {
		return lcAmt;
	}
	/**
	 * @param lcAmt the lcAmt to set
	 */
	public void setLcAmt(Double lcAmt) {
		this.lcAmt = lcAmt;
	}
	/**
	 * @return the specialCharges
	 */
	public Double getSpecialCharges() {
		return specialCharges;
	}
	/**
	 * @param specialCharges the specialCharges to set
	 */
	public void setSpecialCharges(Double specialCharges) {
		this.specialCharges = specialCharges;
	}
	/**
	 * @return the others
	 */
	public Double getOthers() {
		return others;
	}
	/**
	 * @param others the others to set
	 */
	public void setOthers(Double others) {
		this.others = others;
	}
	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the educationCess
	 */
	public Double getEducationCess() {
		return educationCess;
	}
	/**
	 * @param educationCess the educationCess to set
	 */
	public void setEducationCess(Double educationCess) {
		this.educationCess = educationCess;
	}
	/**
	 * @return the secHighEduCess
	 */
	public Double getSecHighEduCess() {
		return secHighEduCess;
	}
	/**
	 * @param secHighEduCess the secHighEduCess to set
	 */
	public void setSecHighEduCess(Double secHighEduCess) {
		this.secHighEduCess = secHighEduCess;
	}
	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}
	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return the surchargeOnStateTax
	 */
	public Double getSurchargeOnStateTax() {
		return surchargeOnStateTax;
	}
	/**
	 * @param surchargeOnStateTax the surchargeOnStateTax to set
	 */
	public void setSurchargeOnStateTax(Double surchargeOnStateTax) {
		this.surchargeOnStateTax = surchargeOnStateTax;
	}
	/**
	 * @return the grandTotal
	 */
	public Double getGrandTotal() {
		return grandTotal;
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	/**
	 * @return the summaryCategory
	 */
	public String getSummaryCategory() {
		return summaryCategory;
	}
	/**
	 * @param summaryCategory the summaryCategory to set
	 */
	public void setSummaryCategory(String summaryCategory) {
		this.summaryCategory = summaryCategory;
	}
	/**
	 * @return the destinationOfc
	 */
	public String getDestinationOfc() {
		return destinationOfc;
	}
	/**
	 * @param destinationOfc the destinationOfc to set
	 */
	public void setDestinationOfc(String destinationOfc) {
		this.destinationOfc = destinationOfc;
	}
	
	
	
}
