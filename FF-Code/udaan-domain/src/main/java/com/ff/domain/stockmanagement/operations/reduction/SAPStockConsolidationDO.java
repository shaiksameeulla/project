package com.ff.domain.stockmanagement.operations.reduction;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;

/**
 * @author hkansagr
 * 
 */
public class SAPStockConsolidationDO extends CGBaseDO {

	private static final long serialVersionUID = 1L;

	/** The transaction id - primary key. */
	private Long transactionId;

	/** The transaction creation date. */
	private Date transactionDate;

	/** The transaction created office. */
	private OfficeDO transactionCreatedOfficeDO;

	/** The item/material id. */
	private ItemDO itemDO;

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
	 * @return the transactionCreatedOfficeDO
	 */
	public OfficeDO getTransactionCreatedOfficeDO() {
		return transactionCreatedOfficeDO;
	}

	/**
	 * @param transactionCreatedOfficeDO
	 *            the transactionCreatedOfficeDO to set
	 */
	public void setTransactionCreatedOfficeDO(
			OfficeDO transactionCreatedOfficeDO) {
		this.transactionCreatedOfficeDO = transactionCreatedOfficeDO;
	}

	/**
	 * @return the itemDO
	 */
	public ItemDO getItemDO() {
		return itemDO;
	}

	/**
	 * @param itemDO
	 *            the itemDO to set
	 */
	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
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
