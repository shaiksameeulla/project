package com.ff.domain.billing;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;

/**
 * The Class BillingConsignmentSummaryDO.
 * 
 * @author narmdr
 */
public class BillingConsignmentSummaryDO extends CGFactDO {

    private static final long serialVersionUID = 6332365462062575111L;

    private Integer billingConsignmentSummaryId;
    private String transactionIdentifier;
    private String salesOrder;
    private Date bookingDate;
    private String transferStatus;// Y/N
    private Date createdDate;
    private Date updatedDate;
    private Integer createdBy;
    private Integer updatedBy;
    private Date sapTimestamp;
    private String shipToCode;
    private Double unitOfMeasurement;
    private String summaryType;
    private String distributionChannel;
    private Integer noOfPickups;
    private Integer invoiceId;

    /*
     * private ContractPaymentBillingLocationDO
     * contractPaymentBillingLocationDO;
     */
    /* private ProductDO productDO; */
    private OfficeDO pickupOfficeDO;
    /* private ConsignmentTypeDO consignmentTypeDO; */
    /* private RegionDO destinationRegionDO; */
    /*private BillDO billDO;*/
    private Set<BillingConsignmentDO> billingConsignmentDOs;
    private String productCode;
    private String summaryCategory;
    private String destinationOffice;
    private Integer version;

    /**
     * @return the billingConsignmentSummaryId
     */
    public Integer getBillingConsignmentSummaryId() {
	return billingConsignmentSummaryId;
    }

    /**
     * @param billingConsignmentSummaryId
     *            the billingConsignmentSummaryId to set
     */
    public void setBillingConsignmentSummaryId(
	    Integer billingConsignmentSummaryId) {
	this.billingConsignmentSummaryId = billingConsignmentSummaryId;
    }

    /**
     * @return the transactionIdentifier
     */
    public String getTransactionIdentifier() {
	return transactionIdentifier;
    }

    /**
     * @param transactionIdentifier
     *            the transactionIdentifier to set
     */
    public void setTransactionIdentifier(String transactionIdentifier) {
	this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * @return the salesOrder
     */
    public String getSalesOrder() {
	return salesOrder;
    }

    /**
     * @param salesOrder
     *            the salesOrder to set
     */
    public void setSalesOrder(String salesOrder) {
	this.salesOrder = salesOrder;
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
	return bookingDate;
    }

    /**
     * @param bookingDate
     *            the bookingDate to set
     */
    public void setBookingDate(Date bookingDate) {
	this.bookingDate = bookingDate;
    }

    /**
     * @return the transferStatus
     */
    public String getTransferStatus() {
	return transferStatus;
    }

    /**
     * @param transferStatus
     *            the transferStatus to set
     */
    public void setTransferStatus(String transferStatus) {
	this.transferStatus = transferStatus;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
	return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
	return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

    /**
     * @return the createdBy
     */
    public Integer getCreatedBy() {
	return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(Integer createdBy) {
	this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public Integer getUpdatedBy() {
	return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(Integer updatedBy) {
	this.updatedBy = updatedBy;
    }

    /**
     * @return the sapTimestamp
     */
    public Date getSapTimestamp() {
	return sapTimestamp;
    }

    /**
     * @param sapTimestamp
     *            the sapTimestamp to set
     */
    public void setSapTimestamp(Date sapTimestamp) {
	this.sapTimestamp = sapTimestamp;
    }

    /**
     * @return the shipToCode
     */
    public String getShipToCode() {
	return shipToCode;
    }

    /**
     * @param shipToCode
     *            the shipToCode to set
     */
    public void setShipToCode(String shipToCode) {
	this.shipToCode = shipToCode;
    }

    /**
     * @return the unitOfMeasurement
     */
    public Double getUnitOfMeasurement() {
	return unitOfMeasurement;
    }

    /**
     * @param unitOfMeasurement
     *            the unitOfMeasurement to set
     */
    public void setUnitOfMeasurement(Double unitOfMeasurement) {
	this.unitOfMeasurement = unitOfMeasurement;
    }

    /**
     * @return the summaryType
     */
    public String getSummaryType() {
	return summaryType;
    }

    /**
     * @param summaryType
     *            the summaryType to set
     */
    public void setSummaryType(String summaryType) {
	this.summaryType = summaryType;
    }

    /**
     * @return the distributionChannel
     */
    public String getDistributionChannel() {
	return distributionChannel;
    }

    /**
     * @param distributionChannel
     *            the distributionChannel to set
     */
    public void setDistributionChannel(String distributionChannel) {
	this.distributionChannel = distributionChannel;
    }

    /**
     * @return the noOfPickups
     */
    public Integer getNoOfPickups() {
	return noOfPickups;
    }

    /**
     * @param noOfPickups
     *            the noOfPickups to set
     */
    public void setNoOfPickups(Integer noOfPickups) {
	this.noOfPickups = noOfPickups;
    }

    /**
     * @return the pickupOfficeDO
     */
    public OfficeDO getPickupOfficeDO() {
	return pickupOfficeDO;
    }

    /**
     * @param pickupOfficeDO
     *            the pickupOfficeDO to set
     */
    public void setPickupOfficeDO(OfficeDO pickupOfficeDO) {
	this.pickupOfficeDO = pickupOfficeDO;
    }

    /**
     * @return the billDO
     *//*
    public BillDO getBillDO() {
	return billDO;
    }

    *//**
     * @param billDO
     *            the billDO to set
     *//*
    public void setBillDO(BillDO billDO) {
	this.billDO = billDO;
    }*/

    /**
     * @return the invoiceId
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the billingConsignmentDOs
     */
    public Set<BillingConsignmentDO> getBillingConsignmentDOs() {
	return billingConsignmentDOs;
    }

    /**
     * @param billingConsignmentDOs
     *            the billingConsignmentDOs to set
     */
    public void setBillingConsignmentDOs(
	    Set<BillingConsignmentDO> billingConsignmentDOs) {
	this.billingConsignmentDOs = billingConsignmentDOs;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
	return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    /**
     * @return the summaryCategory
     */
    public String getSummaryCategory() {
	return summaryCategory;
    }

    /**
     * @param summaryCategory
     *            the summaryCategory to set
     */
    public void setSummaryCategory(String summaryCategory) {
	this.summaryCategory = summaryCategory;
    }

    /**
     * @return the destinationOffice
     */
    public String getDestinationOffice() {
	return destinationOffice;
    }

    /**
     * @param destinationOffice
     *            the destinationOffice to set
     */
    public void setDestinationOffice(String destinationOffice) {
	this.destinationOffice = destinationOffice;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(Integer version) {
	this.version = version;
    }

}
