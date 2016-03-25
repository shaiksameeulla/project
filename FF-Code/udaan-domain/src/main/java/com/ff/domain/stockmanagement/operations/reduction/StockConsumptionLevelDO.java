package com.ff.domain.stockmanagement.operations.reduction;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.StockBookingDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.StockManifestDO;

/**
 * @author hkansagr
 * 
 */
public class StockConsumptionLevelDO extends CGBaseDO {

	private static final long serialVersionUID = 1L;

	/** The transaction id - primary key */
	private Long transactionId;

	/** The consignment/manifest number for which consolidation is required. */
	private String consignmentManifestNo;

	/** The TXN. date. */
	private Date transactionDate;

	/** The TXN. date without time. (Non - Persistence) */
	private Date transactionDateWithoutTime;

	/** The TXN. created office. */
	private Integer transactionCreatedOfficeId;

	/** The item/material id. */
	private Integer itemId;

	/** The stock office. */
	private Integer stockOfficeId;

	/** The stock franchise id. */
	private Integer stockFranchiseeId;

	/** The stock employee id. */
	private Integer stockEmployeeId;

	/** The stock customer id. */
	private Integer stockCustomerId;

	/** The stock BA id. */
	private Integer stockBaId;

	/** The generated TXN. no. */
	private String generatedTransactionNo;

	/**
	 * The isConsolidated - Y-already Consolidated and inserted in stock
	 * consumption staging table with Transaction table, N-not consolidated
	 */
	private String isConsolidated;

	/** The CSD stock reduction status - R- Stock Reduced,N-Not Reduced */
	private String csdStockReductionStatus;
	
	private String TransactionOfficeCode;
	
	//non persistance
	private StockManifestDO stockManifestDO;
	private StockBookingDO stockBookingDO;
	private ComailDO stockComailDO;

	
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
		this.consignmentManifestNo = !StringUtil.isStringEmpty(consignmentManifestNo)?consignmentManifestNo.trim().toUpperCase():consignmentManifestNo;
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
	 * @return the transactionCreatedOfficeId
	 */
	public Integer getTransactionCreatedOfficeId() {
		return transactionCreatedOfficeId;
	}

	/**
	 * @param transactionCreatedOfficeId
	 *            the transactionCreatedOfficeId to set
	 */
	public void setTransactionCreatedOfficeId(Integer transactionCreatedOfficeId) {
		this.transactionCreatedOfficeId = transactionCreatedOfficeId;
	}

	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	 * @return the transactionDateWithoutTime
	 */
	public Date getTransactionDateWithoutTime() {
		return transactionDateWithoutTime;
	}

	/**
	 * @param transactionDateWithoutTime
	 *            the transactionDateWithoutTime to set
	 */
	public void setTransactionDateWithoutTime(Date transactionDateWithoutTime) {
		this.transactionDateWithoutTime = transactionDateWithoutTime;
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

	/**
	 * @return the stockManifestDO
	 */
	public StockManifestDO getStockManifestDO() {
		return stockManifestDO;
	}

	/**
	 * @return the stockBookingDO
	 */
	public StockBookingDO getStockBookingDO() {
		return stockBookingDO;
	}

	/**
	 * @param stockBookingDO the stockBookingDO to set
	 */
	public void setStockBookingDO(StockBookingDO stockBookingDO) {
		this.stockBookingDO = stockBookingDO;
	}

	/**
	 * @param stockManifestDO the stockManifestDO to set
	 */
	public void setStockManifestDO(StockManifestDO stockManifestDO) {
		this.stockManifestDO = stockManifestDO;
	}

	/**
	 * @return the stockComailDO
	 */
	public ComailDO getStockComailDO() {
		return stockComailDO;
	}

	/**
	 * @param stockComailDO the stockComailDO to set
	 */
	public void setStockComailDO(ComailDO stockComailDO) {
		this.stockComailDO = stockComailDO;
	}

	/**
	 * @return the transactionOfficeCode
	 */
	public String getTransactionOfficeCode() {
		return TransactionOfficeCode;
	}

	/**
	 * @param transactionOfficeCode the transactionOfficeCode to set
	 */
	public void setTransactionOfficeCode(String transactionOfficeCode) {
		TransactionOfficeCode = transactionOfficeCode;
	}

}
