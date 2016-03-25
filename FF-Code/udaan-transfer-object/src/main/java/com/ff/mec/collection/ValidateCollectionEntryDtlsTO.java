package com.ff.mec.collection;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class ValidateCollectionEntryDtlsTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private String transactionDate;
	private String transactionNo;
	private String custName;
	private String collectionAgainst;
	private String paymentMode;
	private Double amount;
	private String status;
	private String collectionType;
	private Integer collectionOfficeId;

	/**
	 * @return the collectionOfficeId
	 */
	public Integer getCollectionOfficeId() {
		return collectionOfficeId;
	}

	/**
	 * @param collectionOfficeId
	 *            the collectionOfficeId to set
	 */
	public void setCollectionOfficeId(Integer collectionOfficeId) {
		this.collectionOfficeId = collectionOfficeId;
	}

	/**
	 * @return the collectionType
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType
	 *            the collectionType to set
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * @param transactionNo
	 *            the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the collectionAgainst
	 */
	public String getCollectionAgainst() {
		return collectionAgainst;
	}

	/**
	 * @param collectionAgainst
	 *            the collectionAgainst to set
	 */
	public void setCollectionAgainst(String collectionAgainst) {
		this.collectionAgainst = collectionAgainst;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
