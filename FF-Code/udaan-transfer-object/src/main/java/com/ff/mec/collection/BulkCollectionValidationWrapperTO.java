package com.ff.mec.collection;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class BulkCollectionValidationWrapperTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer consignmentId;
	private String consignmentNo;
	private Integer collectionId;
	private String transactionNo;
	private String collectionDate;
	private String bookingDate;
	private Integer collectionOfficeId;
	private Character collectionCategory;
	private String collectionStatus;
	private String paymentType;
	private String totalCollectionAmount;
	private String bookingAmount;

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

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
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

	public String getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTotalCollectionAmount() {
		return totalCollectionAmount;
	}

	public void setTotalCollectionAmount(String totalCollectionAmount) {
		this.totalCollectionAmount = totalCollectionAmount;
	}

	public String getBookingAmount() {
		return bookingAmount;
	}

	public void setBookingAmount(String bookingAmount) {
		this.bookingAmount = bookingAmount;
	}

}
