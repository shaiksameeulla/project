package com.ff.domain.stockmanagement.operations.receipt;

import java.util.Date;
import java.util.Set;

import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;

/**
 * The Class StockReceiptDO.
 *
 * @author hkansagr
 */

public class BcunStockReceiptDO extends StockCommonBaseDO 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key for the stock receipt table. */
	private Long stockReceiptId;
	
	/** unique stock requisition number EX: PR + OfficeCode(4) + RunningSerialNumber(5). */
	private String requisitionNumber;
	
	/** unique stock issue number EX: SI + OfficeCode(4) + RunningSerialNumber(5). */
	private String issueNumber;
	
	/** unique stock receipt number EX: SA + OfficeCode(4) + RunningSerialNumber(5). */
	private String acknowledgementNumber;
	
	/** issued date. */
	private Date issuedDate;
	
	/** logged in office of receipt (Many-to-one relation ship in hbm). */
	private Integer receiptOfficeId;
	
	/** (Many-to-one relation ship in hbm). */
	private Integer issuedOfficeId;
	
	/** receipt date. */
	private Date receivedDate;
	
	/** record created user (common attribute) (Many-to-one relation ship in hbm). */
	private Integer createUserId;
	
	/** record updated user (common attribute) (Many-to-one relation ship in hbm). */
	private Integer updateUserId;
	
	/** one-to-many relation ship in hbm. */
	Set<BcunStockReceiptItemDtlsDO> stockReceiptItemDtls;

	/** Non-persistent variable for Receipt Against Requisition/Issue. */
	public String transactionFromType;
	
	/**
	 * Gets the stock receipt id.
	 *
	 * @return the stockReceiptId
	 */
	public Long getStockReceiptId() {
		return stockReceiptId;
	}
	
	/**
	 * Sets the stock receipt id.
	 *
	 * @param stockReceiptId the stockReceiptId to set
	 */
	public void setStockReceiptId(Long stockReceiptId) {
		this.stockReceiptId = stockReceiptId;
	}
	
	/**
	 * Gets the requisition number.
	 *
	 * @return the requisitionNumber
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	
	/**
	 * Sets the requisition number.
	 *
	 * @param requisitionNumber the requisitionNumber to set
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	
	/**
	 * Gets the issue number.
	 *
	 * @return the issueNumber
	 */
	public String getIssueNumber() {
		return issueNumber;
	}
	
	/**
	 * Sets the issue number.
	 *
	 * @param issueNumber the issueNumber to set
	 */
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	
	/**
	 * Gets the issued date.
	 *
	 * @return the issuedDate
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}
	
	/**
	 * Sets the issued date.
	 *
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	
	public Integer getReceiptOfficeId() {
		return receiptOfficeId;
	}

	public void setReceiptOfficeId(Integer receiptOfficeId) {
		this.receiptOfficeId = receiptOfficeId;
	}

	public Integer getIssuedOfficeId() {
		return issuedOfficeId;
	}

	public void setIssuedOfficeId(Integer issuedOfficeId) {
		this.issuedOfficeId = issuedOfficeId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * Gets the received date.
	 *
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	/**
	 * Sets the received date.
	 *
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
		
	public Set<BcunStockReceiptItemDtlsDO> getStockReceiptItemDtls() {
		return stockReceiptItemDtls;
	}

	public void setStockReceiptItemDtls(
			Set<BcunStockReceiptItemDtlsDO> stockReceiptItemDtls) {
		this.stockReceiptItemDtls = stockReceiptItemDtls;
	}

	/**
	 * Gets the acknowledgement number.
	 *
	 * @return the acknowledgement number
	 */
	public String getAcknowledgementNumber() {
		return acknowledgementNumber;
	}
	
	/**
	 * Sets the acknowledgement number.
	 *
	 * @param acknowledgementNumber the new acknowledgement number
	 */
	public void setAcknowledgementNumber(String acknowledgementNumber) {
		this.acknowledgementNumber = acknowledgementNumber;
	}
	
	/**
	 * Gets the transaction from type.
	 *
	 * @return the transactionFromType
	 */
	public String getTransactionFromType() {
		return transactionFromType;
	}
	
	/**
	 * Sets the transaction from type.
	 *
	 * @param transactionFromType the transactionFromType to set
	 */
	public void setTransactionFromType(String transactionFromType) {
		this.transactionFromType = transactionFromType;
	}
	
}
