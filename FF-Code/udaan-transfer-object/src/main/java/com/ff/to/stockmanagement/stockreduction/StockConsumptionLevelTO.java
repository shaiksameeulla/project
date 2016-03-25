package com.ff.to.stockmanagement.stockreduction;

import java.util.Date;

import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.ItemTO;

/**
 * @author hkansagr
 * 
 */
public class StockConsumptionLevelTO {

	/** The transaction id - primary key */
	private Long transactionId;

	/** The consignment/manifest number for which consolidation is required. */
	private String consignmentManifestNo;

	/** The txn. date. */
	private Date transactionDate;

	/** The txn. created office */
	private OfficeTO transationCreatedOfficeTO;

	/** The item/material id. */
	private ItemTO itemTO;

	/** The stock office id. */
	private Integer stockOfficeId;

	/** The stock franchisee id. */
	private Integer stockFranchiseeId;

	/** The stock employee id. */
	private Integer stockEmployeeId;

	/** The stock customer id. */
	private Integer stockCustomerId;

	/** The stock BA id. */
	private Integer stockBaId;

	/** The generated txn. no. */
	private String generatedTransactionNo;

	/**
	 * The isConsolidated - Y-already Consolidated and inserted in stock
	 * consumption staging table with Transaction table, N-not consolidated
	 */
	private String isConsolidated;

	/** The CSD stock reduction status - R- Stock Reduced,N-Not Reduced */
	private String csdStockReductionStatus;

	
	/**
	 * @return the transationCreatedOfficeTO
	 */
	public OfficeTO getTransationCreatedOfficeTO() {
		return transationCreatedOfficeTO;
	}

	/**
	 * @param transationCreatedOfficeTO
	 *            the transationCreatedOfficeTO to set
	 */
	public void setTransationCreatedOfficeTO(OfficeTO transationCreatedOfficeTO) {
		this.transationCreatedOfficeTO = transationCreatedOfficeTO;
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
	 * @return the consignmentManifestNo
	 */
	public String getConsignmentManifestNo() {
		return consignmentManifestNo;
	}

	/**
	 * @param consignmentManifestNo
	 *            the consignmentManifestNo to set
	 */
	public void setConsignmentManifestNo(String consignmentManifestNo) {
		this.consignmentManifestNo = consignmentManifestNo;
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
	 * @return the stockOfficeId
	 */
	public Integer getStockOfficeId() {
		return stockOfficeId;
	}

	/**
	 * @param stockOfficeId
	 *            the stockOfficeId to set
	 */
	public void setStockOfficeId(Integer stockOfficeId) {
		this.stockOfficeId = stockOfficeId;
	}

	/**
	 * @return the stockFranchiseeId
	 */
	public Integer getStockFranchiseeId() {
		return stockFranchiseeId;
	}

	/**
	 * @param stockFranchiseeId
	 *            the stockFranchiseeId to set
	 */
	public void setStockFranchiseeId(Integer stockFranchiseeId) {
		this.stockFranchiseeId = stockFranchiseeId;
	}

	/**
	 * @return the stockEmployeeId
	 */
	public Integer getStockEmployeeId() {
		return stockEmployeeId;
	}

	/**
	 * @param stockEmployeeId
	 *            the stockEmployeeId to set
	 */
	public void setStockEmployeeId(Integer stockEmployeeId) {
		this.stockEmployeeId = stockEmployeeId;
	}

	/**
	 * @return the stockCustomerId
	 */
	public Integer getStockCustomerId() {
		return stockCustomerId;
	}

	/**
	 * @param stockCustomerId
	 *            the stockCustomerId to set
	 */
	public void setStockCustomerId(Integer stockCustomerId) {
		this.stockCustomerId = stockCustomerId;
	}

	/**
	 * @return the stockBaId
	 */
	public Integer getStockBaId() {
		return stockBaId;
	}

	/**
	 * @param stockBaId
	 *            the stockBaId to set
	 */
	public void setStockBaId(Integer stockBaId) {
		this.stockBaId = stockBaId;
	}

	/**
	 * @return the generatedTransactionNo
	 */
	public String getGeneratedTransactionNo() {
		return generatedTransactionNo;
	}

	/**
	 * @param generatedTransactionNo
	 *            the generatedTransactionNo to set
	 */
	public void setGeneratedTransactionNo(String generatedTransactionNo) {
		this.generatedTransactionNo = generatedTransactionNo;
	}

	/**
	 * @return the isConsolidated
	 */
	public String getIsConsolidated() {
		return isConsolidated;
	}

	/**
	 * @param isConsolidated
	 *            the isConsolidated to set
	 */
	public void setIsConsolidated(String isConsolidated) {
		this.isConsolidated = isConsolidated;
	}

	/**
	 * @return the csdStockReductionStatus
	 */
	public String getCsdStockReductionStatus() {
		return csdStockReductionStatus;
	}

	/**
	 * @param csdStockReductionStatus
	 *            the csdStockReductionStatus to set
	 */
	public void setCsdStockReductionStatus(String csdStockReductionStatus) {
		this.csdStockReductionStatus = csdStockReductionStatus;
	}

}
