package com.ff.to.billing;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class BillingConsignmentTO.
 * 
 * @author narmdr
 */
public class ConsignmentAliasTO extends CGBaseTO {

    /**
     * 
     */
    private static final long serialVersionUID = 7589777534124161243L;

    private Integer billingConsignmentId;
    private Integer consgId;
    private String consgNo;
    private String salesOfficeName;
    private String businessName;
    private String invoiceNumber;
    private String bookingDate;
    private String consignmentNumber;
    private String consignmentType;
    private String originCityName;
    private String originOfficeName;
    private String customerReferenceNumber;
    private String destinationCityName;
    private Double finalWeight;
    private Double lcAmount;
    private Double codAmount;
    private Double finalSlabRate;
    private Double lcCharge;
    private Double codCharge;
    private Double rtoCharge;
    private Double riskSurCharge;
    private Double documentHandlingCharge;
    private Double parcelHandlingCharge;
    private Double airportHandlingCharge;
    private Double otherCharges;
    private Double totalCharges;
    private String vendorCode;
    private String deliveryDate;
    private char weightModified;
    private char newRateAdded;
    private char deltaTransfer;
    private String rowNumber;
    private String pageNumber;
    private char rtoMarked;
    /**
     * @return the billingConsignmentId
     */
    public Integer getBillingConsignmentId() {
        return billingConsignmentId;
    }
    /**
     * @param billingConsignmentId the billingConsignmentId to set
     */
    public void setBillingConsignmentId(Integer billingConsignmentId) {
        this.billingConsignmentId = billingConsignmentId;
    }
    /**
     * @return the consgId
     */
    public Integer getConsgId() {
        return consgId;
    }
    /**
     * @param consgId the consgId to set
     */
    public void setConsgId(Integer consgId) {
        this.consgId = consgId;
    }
    /**
     * @return the consgNo
     */
    public String getConsgNo() {
        return consgNo;
    }
    /**
     * @param consgNo the consgNo to set
     */
    public void setConsgNo(String consgNo) {
        this.consgNo = consgNo;
    }
    /**
     * @return the salesOfficeName
     */
    public String getSalesOfficeName() {
        return salesOfficeName;
    }
    /**
     * @param salesOfficeName the salesOfficeName to set
     */
    public void setSalesOfficeName(String salesOfficeName) {
        this.salesOfficeName = salesOfficeName;
    }
    /**
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }
    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    /**
     * @return the invoiceNuber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    /**
     * @param invoiceNuber the invoiceNuber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    /**
     * @return the bookingDate
     */
    public String getBookingDate() {
        return bookingDate;
    }
    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    /**
     * @return the consignmentNumber
     */
    public String getConsignmentNumber() {
        return consignmentNumber;
    }
    /**
     * @param consignmentNumber the consignmentNumber to set
     */
    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }
    /**
     * @return the consignmentType
     */
    public String getConsignmentType() {
        return consignmentType;
    }
    /**
     * @param consignmentType the consignmentType to set
     */
    public void setConsignmentType(String consignmentType) {
        this.consignmentType = consignmentType;
    }
    /**
     * @return the originCityName
     */
    public String getOriginCityName() {
        return originCityName;
    }
    /**
     * @param originCityName the originCityName to set
     */
    public void setOriginCityName(String originCityName) {
        this.originCityName = originCityName;
    }
    /**
     * @return the originOfficeName
     */
    public String getOriginOfficeName() {
        return originOfficeName;
    }
    /**
     * @param originOfficeName the originOfficeName to set
     */
    public void setOriginOfficeName(String originOfficeName) {
        this.originOfficeName = originOfficeName;
    }
    /**
     * @return the customerReferenceNumber
     */
    public String getCustomerReferenceNumber() {
        return customerReferenceNumber;
    }
    /**
     * @param customerReferenceNumber the customerReferenceNumber to set
     */
    public void setCustomerReferenceNumber(String customerReferenceNumber) {
        this.customerReferenceNumber = customerReferenceNumber;
    }
    /**
     * @return the destinationCityName
     */
    public String getDestinationCityName() {
        return destinationCityName;
    }
    /**
     * @param destinationCityName the destinationCityName to set
     */
    public void setDestinationCityName(String destinationCityName) {
        this.destinationCityName = destinationCityName;
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
     * @return the codAmount
     */
    public Double getCodAmount() {
        return codAmount;
    }
    /**
     * @param codAmount the codAmount to set
     */
    public void setCodAmount(Double codAmount) {
        this.codAmount = codAmount;
    }
    /**
     * @return the finalSlabRate
     */
    public Double getFinalSlabRate() {
        return finalSlabRate;
    }
    /**
     * @param finalSlabRate the finalSlabRate to set
     */
    public void setFinalSlabRate(Double finalSlabRate) {
        this.finalSlabRate = finalSlabRate;
    }
    /**
     * @return the lcCharge
     */
    public Double getLcCharge() {
        return lcCharge;
    }
    /**
     * @param lcCharge the lcCharge to set
     */
    public void setLcCharge(Double lcCharge) {
        this.lcCharge = lcCharge;
    }
    /**
     * @return the codCharge
     */
    public Double getCodCharge() {
        return codCharge;
    }
    /**
     * @param codCharge the codCharge to set
     */
    public void setCodCharge(Double codCharge) {
        this.codCharge = codCharge;
    }
    /**
     * @return the rtoCharge
     */
    public Double getRtoCharge() {
        return rtoCharge;
    }
    /**
     * @param rtoCharge the rtoCharge to set
     */
    public void setRtoCharge(Double rtoCharge) {
        this.rtoCharge = rtoCharge;
    }
    /**
     * @return the riskSurCharge
     */
    public Double getRiskSurCharge() {
        return riskSurCharge;
    }
    /**
     * @param riskSurCharge the riskSurCharge to set
     */
    public void setRiskSurCharge(Double riskSurCharge) {
        this.riskSurCharge = riskSurCharge;
    }
    /**
     * @return the documentHandlingCharge
     */
    public Double getDocumentHandlingCharge() {
        return documentHandlingCharge;
    }
    /**
     * @param documentHandlingCharge the documentHandlingCharge to set
     */
    public void setDocumentHandlingCharge(Double documentHandlingCharge) {
        this.documentHandlingCharge = documentHandlingCharge;
    }
    /**
     * @return the parcelHandlingCharge
     */
    public Double getParcelHandlingCharge() {
        return parcelHandlingCharge;
    }
    /**
     * @param parcelHandlingCharge the parcelHandlingCharge to set
     */
    public void setParcelHandlingCharge(Double parcelHandlingCharge) {
        this.parcelHandlingCharge = parcelHandlingCharge;
    }
    /**
     * @return the airportHandlingCharge
     */
    public Double getAirportHandlingCharge() {
        return airportHandlingCharge;
    }
    /**
     * @param airportHandlingCharge the airportHandlingCharge to set
     */
    public void setAirportHandlingCharge(Double airportHandlingCharge) {
        this.airportHandlingCharge = airportHandlingCharge;
    }
    /**
     * @return the otherCharges
     */
    public Double getOtherCharges() {
        return otherCharges;
    }
    /**
     * @param otherCharges the otherCharges to set
     */
    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }
    /**
     * @return the totalCharges
     */
    public Double getTotalCharges() {
        return totalCharges;
    }
    /**
     * @param totalCharges the totalCharges to set
     */
    public void setTotalCharges(Double totalCharges) {
        this.totalCharges = totalCharges;
    }
    /**
     * @return the vendorCode
     */
    public String getVendorCode() {
        return vendorCode;
    }
    /**
     * @param vendorCode the vendorCode to set
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
    /**
     * @return the deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }
    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    /**
     * @return the weightModified
     */
    public char getWeightModified() {
        return weightModified;
    }
    /**
     * @param weightModified the weightModified to set
     */
    public void setWeightModified(char weightModified) {
        this.weightModified = weightModified;
    }
    /**
     * @return the newRateAdded
     */
    public char getNewRateAdded() {
        return newRateAdded;
    }
    /**
     * @param newRateAdded the newRateAdded to set
     */
    public void setNewRateAdded(char newRateAdded) {
        this.newRateAdded = newRateAdded;
    }
    /**
     * @return the deltaTransfer
     */
    public char getDeltaTransfer() {
        return deltaTransfer;
    }
    /**
     * @param deltaTransfer the deltaTransfer to set
     */
    public void setDeltaTransfer(char deltaTransfer) {
        this.deltaTransfer = deltaTransfer;
    }
	/**
	 * @return the rowNumber
	 */
	public String getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the pageNumber
	 */
	public String getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public char getRtoMarked() {
		return rtoMarked;
	}
	public void setRtoMarked(char rtoMarked) {
		this.rtoMarked = rtoMarked;
	}
	


    
}
