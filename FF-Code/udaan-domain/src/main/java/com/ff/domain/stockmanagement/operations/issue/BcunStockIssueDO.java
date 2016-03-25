package com.ff.domain.stockmanagement.operations.issue;

import java.util.Date;
import java.util.Set;

import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;

/**
 * The Class StockIssueDO.
 *
 * @author hkansagr
 */

public class BcunStockIssueDO extends StockCommonBaseDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key for stock issue table. */
	private Long stockIssueId;
	
	/** unique stock requisition number EX: PR + OfficeCODE(4) + RunningSerialNumber(5). */
	private String requisitionNumber;
	
	/** unique issue transaction number. */
	private String stockIssueNumber;
	
	/** date. */
	private Date stockIssueDate;
	
	/** logged in office of issue. */
	private Integer issueOfficeId;
	
	/** type of receivers like EMP/BA/FR. */
	private String issuedToType;
	
	/** office  of receiver if receiver is Branch. */
	private Integer issuedToOfficeId;
	
	/** BA Id of Receiver if receiver is BA (Many-to-one relation ship in hbm). */
	private Integer issuedToBAId;
	
	/** FR id if receiver is FR (Many-to-one relation ship in hbm). */
	private Integer issuedToFranchiseeId;
	
	/** Pick boy/Employee if receiver is EMP (Many-to-one relation ship in hbm). */
	private Integer issuedToPickupBoyId;
	
	/** Customer id if receiver is Customer (Many-to-one relation ship in hbm). */
	private Integer issuedToCustomerId;
	
	/** record created user (common attribute) (Many-to-one relation ship in hbm). */
	private Integer createUserId;
	
	/** record updated user (common attribute) (Many-to-one relation ship in hbm). */
	private Integer updateUserId;
	
	/** Non-persistent variable for Issue Against Requisition. */
	public String transactionFromType;
	
	private String shippedToCode;
	
	
	/** (one-to-many relation ship in hbm). */
	
	Set<BcunStockIssueItemDtlsDO> issueItemDtlsDO;
	
	

	
	/**
	 * Gets the issued to type.
	 *
	 * @return the issued to type
	 */
	public String getIssuedToType() {
		return issuedToType;
	}
	
	/**
	 * Sets the issued to type.
	 *
	 * @param issuedToType the new issued to type
	 */
	public void setIssuedToType(String issuedToType) {
		this.issuedToType = issuedToType;
	}
	
	
	
	public Integer getIssueOfficeId() {
		return issueOfficeId;
	}

	public void setIssueOfficeId(Integer issueOfficeId) {
		this.issueOfficeId = issueOfficeId;
	}

	public Integer getIssuedToOfficeId() {
		return issuedToOfficeId;
	}

	public void setIssuedToOfficeId(Integer issuedToOfficeId) {
		this.issuedToOfficeId = issuedToOfficeId;
	}

	public Integer getIssuedToBAId() {
		return issuedToBAId;
	}

	public void setIssuedToBAId(Integer issuedToBAId) {
		this.issuedToBAId = issuedToBAId;
	}

	public Integer getIssuedToFranchiseeId() {
		return issuedToFranchiseeId;
	}

	public void setIssuedToFranchiseeId(Integer issuedToFranchiseeId) {
		this.issuedToFranchiseeId = issuedToFranchiseeId;
	}

	public Integer getIssuedToPickupBoyId() {
		return issuedToPickupBoyId;
	}

	public void setIssuedToPickupBoyId(Integer issuedToPickupBoyId) {
		this.issuedToPickupBoyId = issuedToPickupBoyId;
	}

	public Integer getIssuedToCustomerId() {
		return issuedToCustomerId;
	}

	public void setIssuedToCustomerId(Integer issuedToCustomerId) {
		this.issuedToCustomerId = issuedToCustomerId;
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
	 * Gets the stock issue id.
	 *
	 * @return the stock issue id
	 */
	public Long getStockIssueId() {
		return stockIssueId;
	}
	
	/**
	 * Sets the stock issue id.
	 *
	 * @param stockIssueId the new stock issue id
	 */
	public void setStockIssueId(Long stockIssueId) {
		this.stockIssueId = stockIssueId;
	}
	
	/**
	 * Gets the requisition number.
	 *
	 * @return the requisition number
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	
	/**
	 * Sets the requisition number.
	 *
	 * @param requisitionNumber the new requisition number
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	
	/**
	 * Gets the stock issue number.
	 *
	 * @return the stock issue number
	 */
	public String getStockIssueNumber() {
		return stockIssueNumber;
	}
	
	/**
	 * Sets the stock issue number.
	 *
	 * @param stockIssueNumber the new stock issue number
	 */
	public void setStockIssueNumber(String stockIssueNumber) {
		this.stockIssueNumber = stockIssueNumber;
	}
	
	/**
	 * Gets the stock issue date.
	 *
	 * @return the stock issue date
	 */
	public Date getStockIssueDate() {
		return stockIssueDate;
	}
	
	/**
	 * Sets the stock issue date.
	 *
	 * @param stockIssueDate the new stock issue date
	 */
	public void setStockIssueDate(Date stockIssueDate) {
		this.stockIssueDate = stockIssueDate;
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
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	/**
	 * Sets the transaction from type.
	 *
	 * @param transactionFromType the transactionFromType to set
	 */
	public void setTransactionFromType(String transactionFromType) {
		this.transactionFromType = transactionFromType;
	}

	public Set<BcunStockIssueItemDtlsDO> getIssueItemDtlsDO() {
		return issueItemDtlsDO;
	}

	public void setIssueItemDtlsDO(Set<BcunStockIssueItemDtlsDO> issueItemDtlsDO) {
		this.issueItemDtlsDO = issueItemDtlsDO;
	}

	/*public BcunStockIssuePaymentDetailsDO getIssuePaymentDtlsDO() {
		return issuePaymentDtlsDO;
	}

	public void setIssuePaymentDtlsDO(
			BcunStockIssuePaymentDetailsDO issuePaymentDtlsDO) {
		this.issuePaymentDtlsDO = issuePaymentDtlsDO;
	}*/
	
}
