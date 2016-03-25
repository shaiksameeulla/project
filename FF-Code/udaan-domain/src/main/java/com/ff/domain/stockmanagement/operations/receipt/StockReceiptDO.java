package com.ff.domain.stockmanagement.operations.receipt;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;
import com.ff.domain.umc.UserDO;

/**
 * The Class StockReceiptDO.
 *
 * @author hkansagr
 */

public class StockReceiptDO extends StockCommonBaseDO 
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
	private OfficeDO receiptOfficeId;
	
	/** (Many-to-one relation ship in hbm). */
	private OfficeDO issuedOfficeId;
	
	/** receipt date. */
	private Date receivedDate;
	
	/** record created user (common attribute) (Many-to-one relation ship in hbm). */
	private UserDO createdByUser;
	
	/** record updated user (common attribute) (Many-to-one relation ship in hbm). */
	private UserDO updatedByUser;
	
	/** one-to-many relation ship in hbm. */
	@JsonManagedReference
	Set<StockReceiptItemDtlsDO> stockReceiptItemDtls;

	/** Non-persistent variable for Receipt Against Requisition/Issue. */
	public String transactionFromType;
	/** Non-persistent variable for Receipt Against Excel Upload */
	private int nextNumber;
	
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
	
	/**
	 * Gets the receipt office id.
	 *
	 * @return the receiptOfficeId
	 */
	public OfficeDO getReceiptOfficeId() {
		return receiptOfficeId;
	}
	
	/**
	 * Sets the receipt office id.
	 *
	 * @param receiptOfficeId the receiptOfficeId to set
	 */
	public void setReceiptOfficeId(OfficeDO receiptOfficeId) {
		this.receiptOfficeId = receiptOfficeId;
	}
	
	/**
	 * Gets the issued office id.
	 *
	 * @return the issuedOfficeId
	 */
	public OfficeDO getIssuedOfficeId() {
		return issuedOfficeId;
	}
	
	/**
	 * Sets the issued office id.
	 *
	 * @param issuedOfficeId the issuedOfficeId to set
	 */
	public void setIssuedOfficeId(OfficeDO issuedOfficeId) {
		this.issuedOfficeId = issuedOfficeId;
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
	
	/**
	 * Gets the created by user.
	 *
	 * @return the createdByUser
	 */
	public UserDO getCreatedByUser() {
		return createdByUser;
	}
	
	/**
	 * Sets the created by user.
	 *
	 * @param createdByUser the createdByUser to set
	 */
	public void setCreatedByUser(UserDO createdByUser) {
		this.createdByUser = createdByUser;
	}
	
	/**
	 * Gets the updated by user.
	 *
	 * @return the updatedByUser
	 */
	public UserDO getUpdatedByUser() {
		return updatedByUser;
	}
	
	/**
	 * Sets the updated by user.
	 *
	 * @param updatedByUser the updatedByUser to set
	 */
	public void setUpdatedByUser(UserDO updatedByUser) {
		this.updatedByUser = updatedByUser;
	}
	
	/**
	 * Gets the stock receipt item dtls.
	 *
	 * @return the stockReceiptItemDtls
	 */
	public Set<StockReceiptItemDtlsDO> getStockReceiptItemDtls() {
		return stockReceiptItemDtls;
	}
	
	/**
	 * Sets the stock receipt item dtls.
	 *
	 * @param stockReceiptItemDtls the stockReceiptItemDtls to set
	 */
	public void setStockReceiptItemDtls(
			Set<StockReceiptItemDtlsDO> stockReceiptItemDtls) {
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

	/**
	 * @return the nextNumber
	 */
	public int getNextNumber() {
		return nextNumber;
	}

	/**
	 * @param nextNumber the nextNumber to set
	 */
	public void setNextNumber(int nextNumber) {
		this.nextNumber = nextNumber;
	}
	
}
