package com.ff.to.stockmanagement.stockreduction;

import java.util.Date;

import com.ff.to.stockmanagement.masters.ItemTO;

/**
 * @author hkansagr
 * 
 */
public class SAPStockConsolidationTO {

	/** The transaction id - primary key. */
	private Long transactionId;

	/** The transaction creation date. */
	private Date transactionDate;

	/** The transaction created office. */
	private Integer transactionCreatedOffice;

	/** The item/material id. */
	private ItemTO itemTO;

	/** The consumed stock quantity. */
	private Long consumedStockQuantity;

	/**
	 * The generated transaction number - it will populated to stock consumption
	 * level table
	 */
	private String generatedTransactionNumber;

	/** The SAP transfer status - N- NOT TRANSFER,T-TRANSFERRED */
	private String sapTransferStatus;

	
	/**
	 * @return the itemTO
	 */
	public ItemTO getItemTO() {
		return itemTO;
	}

	/**
	 * @param itemTO
	 *            the itemTO to set
	 */
	public void setItemTO(ItemTO itemTO) {
		this.itemTO = itemTO;
	}

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionCreatedOffice
	 */
	public Integer getTransactionCreatedOffice() {
		return transactionCreatedOffice;
	}

	/**
	 * @param transactionCreatedOffice
	 *            the transactionCreatedOffice to set
	 */
	public void setTransactionCreatedOffice(Integer transactionCreatedOffice) {
		this.transactionCreatedOffice = transactionCreatedOffice;
	}

	/**
	 * @return the consumedStockQuantity
	 */
	public Long getConsumedStockQuantity() {
		return consumedStockQuantity;
	}

	/**
	 * @param consumedStockQuantity
	 *            the consumedStockQuantity to set
	 */
	public void setConsumedStockQuantity(Long consumedStockQuantity) {
		this.consumedStockQuantity = consumedStockQuantity;
	}

	/**
	 * @return the generatedTransactionNumber
	 */
	public String getGeneratedTransactionNumber() {
		return generatedTransactionNumber;
	}

	/**
	 * @param generatedTransactionNumber
	 *            the generatedTransactionNumber to set
	 */
	public void setGeneratedTransactionNumber(String generatedTransactionNumber) {
		this.generatedTransactionNumber = generatedTransactionNumber;
	}

	/**
	 * @return the sapTransferStatus
	 */
	public String getSapTransferStatus() {
		return sapTransferStatus;
	}

	/**
	 * @param sapTransferStatus
	 *            the sapTransferStatus to set
	 */
	public void setSapTransferStatus(String sapTransferStatus) {
		this.sapTransferStatus = sapTransferStatus;
	}

}
