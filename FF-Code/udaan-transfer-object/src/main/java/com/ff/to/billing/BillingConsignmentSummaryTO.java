package com.ff.to.billing;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ProductTO;

/**
 * The Class BillingConsignmentSummaryTO.
 * 
 * @author narmdr
 */
public class BillingConsignmentSummaryTO extends CGBaseTO {

    private static final long serialVersionUID = -2795983260980757082L;
    private Integer billingConsignmentSummaryId;
    private String transactionIdentifier;
    private String salesOrder;
    private Date bookingDate;

    /*
     * private ContractPaymentBillingLocationTO
     * contractPaymentBillingLocationTO;
     */
    /* private ProductTO productTO; */
    private OfficeTO pickupOfficeTO;
    /*
     * private ConsignmentTypeTO consignmentTypeTO; private RegionTO
     * destinationRegionTO;
     */
    private BillTO billTO;

    private List<BillingConsignmentTO> billingConsignmentTOs;
    private String transferStatus;
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
     * @return the pickupOfficeTO
     */
    public OfficeTO getPickupOfficeTO() {
	return pickupOfficeTO;
    }

    /**
     * @param pickupOfficeTO
     *            the pickupOfficeTO to set
     */
    public void setPickupOfficeTO(OfficeTO pickupOfficeTO) {
	this.pickupOfficeTO = pickupOfficeTO;
    }

    /**
     * @return the billTO
     */
    public BillTO getBillTO() {
	return billTO;
    }

    /**
     * @param billTO
     *            the billTO to set
     */
    public void setBillTO(BillTO billTO) {
	this.billTO = billTO;
    }

    /**
     * @return the billingConsignmentTOs
     */
    public List<BillingConsignmentTO> getBillingConsignmentTOs() {
	return billingConsignmentTOs;
    }

    /**
     * @param billingConsignmentTOs
     *            the billingConsignmentTOs to set
     */
    public void setBillingConsignmentTOs(
	    List<BillingConsignmentTO> billingConsignmentTOs) {
	this.billingConsignmentTOs = billingConsignmentTOs;
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
     * @param unitOfMeasurement the unitOfMeasurement to set
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
     * @param destinationOffice the destinationOffice to set
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
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

}
