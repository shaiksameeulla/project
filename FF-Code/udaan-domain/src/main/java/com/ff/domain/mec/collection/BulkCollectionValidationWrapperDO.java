package com.ff.domain.mec.collection;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;

public class BulkCollectionValidationWrapperDO extends CGBaseDO{
	
	private static final long serialVersionUID = 1L;
	private Integer consignmentId;
	private String consignmentNo;
	private Integer collectionId;
	private String transactionNo;
	private Date collectionDate;
	private Date bookingDate;
	private Integer collectionOfficeId;
	private Character collectionCategory;
	private Character collectionStatus;
	private String paymentType;
	private Double totalCollectionAmount;
	private Double lcAmount;
	private Double toPayAmount;
	private Double codAmount;
	
	public BulkCollectionValidationWrapperDO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getConsignmentId() {
		return consignmentId;
	}

	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Integer getCollectionOfficeId() {
		return collectionOfficeId;
	}

	public void setCollectionOfficeId(Integer collectionOfficeId) {
		this.collectionOfficeId = collectionOfficeId;
	}

	public Character getCollectionCategory() {
		return collectionCategory;
	}

	public void setCollectionCategory(Character collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	public Character getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(Character collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getTotalCollectionAmount() {
		return totalCollectionAmount;
	}

	public void setTotalCollectionAmount(Double totalCollectionAmount) {
		this.totalCollectionAmount = totalCollectionAmount;
	}

	public Double getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	public Double getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public Double getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}

}
