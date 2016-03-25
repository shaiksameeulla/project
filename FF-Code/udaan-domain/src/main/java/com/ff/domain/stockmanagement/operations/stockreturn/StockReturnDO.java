/**
 * 
 */
package com.ff.domain.stockmanagement.operations.stockreturn;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;
import com.ff.domain.umc.UserDO;

/**
 * The Class StockReturnDO.
 *
 * @author cbhure
 */
public class StockReturnDO extends StockCommonBaseDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2921333548125523220L;
	
	/** The stock return id. */
	private Long stockReturnId;
	
	/** The issue number. */
	private String issueNumber;
	
	/** The return number. */
	private String returnNumber;
	
	/** The acknowledgement number. */
	private String acknowledgementNumber;
	
	/** The issued date. */
	private Date issuedDate;
	
	/** The returning office do. */
	private OfficeDO returningOfficeDO;
	
	/** The issued office do. */
	private OfficeDO issuedOfficeDO;
	
	/** The created by user do. */
	private UserDO createdByUserDO;
	
	/** The updated by user do. */
	private UserDO updatedByUserDO;
	
	/** The return date. */
	private Date returnDate;
	
	/** The issue from type. */
	private String issueFromType;

	/** The return item dtls. */
	@JsonManagedReference
	Set<StockReturnItemDtlsDO> returnItemDtls;
	
	/** Non-persistent variable for Return Against Receipt/issue. */
	public String transactionFromType;

	/**
	 * Gets the stock return id.
	 *
	 * @return the stockReturnId
	 */
	public Long getStockReturnId() {
		return stockReturnId;
	}

	/**
	 * Sets the stock return id.
	 *
	 * @param stockReturnId the stockReturnId to set
	 */
	public void setStockReturnId(Long stockReturnId) {
		this.stockReturnId = stockReturnId;
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
	 * Gets the return number.
	 *
	 * @return the returnNumber
	 */
	public String getReturnNumber() {
		return returnNumber;
	}

	/**
	 * Sets the return number.
	 *
	 * @param returnNumber the returnNumber to set
	 */
	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
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
	 * Gets the returning office do.
	 *
	 * @return the returningOfficeDO
	 */
	public OfficeDO getReturningOfficeDO() {
		return returningOfficeDO;
	}

	/**
	 * Sets the returning office do.
	 *
	 * @param returningOfficeDO the returningOfficeDO to set
	 */
	public void setReturningOfficeDO(OfficeDO returningOfficeDO) {
		this.returningOfficeDO = returningOfficeDO;
	}

	

	/**
	 * Gets the issued office do.
	 *
	 * @return the issuedOfficeDO
	 */
	public OfficeDO getIssuedOfficeDO() {
		return issuedOfficeDO;
	}

	/**
	 * Sets the issued office do.
	 *
	 * @param issuedOfficeDO the issuedOfficeDO to set
	 */
	public void setIssuedOfficeDO(OfficeDO issuedOfficeDO) {
		this.issuedOfficeDO = issuedOfficeDO;
	}

	/**
	 * Gets the return date.
	 *
	 * @return the returnDate
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * Sets the return date.
	 *
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	
	
	/**
	 * Gets the created by user do.
	 *
	 * @return the createdByUserDO
	 */
	public UserDO getCreatedByUserDO() {
		return createdByUserDO;
	}

	/**
	 * Sets the created by user do.
	 *
	 * @param createdByUserDO the createdByUserDO to set
	 */
	public void setCreatedByUserDO(UserDO createdByUserDO) {
		this.createdByUserDO = createdByUserDO;
	}

	/**
	 * Gets the updated by user do.
	 *
	 * @return the updatedByUserDO
	 */
	public UserDO getUpdatedByUserDO() {
		return updatedByUserDO;
	}

	/**
	 * Sets the updated by user do.
	 *
	 * @param updatedByUserDO the updatedByUserDO to set
	 */
	public void setUpdatedByUserDO(UserDO updatedByUserDO) {
		this.updatedByUserDO = updatedByUserDO;
	}

	/**
	 * Gets the return item dtls.
	 *
	 * @return the returnItemDtls
	 */
	public Set<StockReturnItemDtlsDO> getReturnItemDtls() {
		return returnItemDtls;
	}

	/**
	 * Sets the return item dtls.
	 *
	 * @param returnItemDtls the returnItemDtls to set
	 */
	public void setReturnItemDtls(Set<StockReturnItemDtlsDO> returnItemDtls) {
		this.returnItemDtls = returnItemDtls;
	}

	/**
	 * Gets the issue from type.
	 *
	 * @return the issueFromType
	 */
	public String getIssueFromType() {
		return issueFromType;
	}

	/**
	 * Sets the issue from type.
	 *
	 * @param issueFromType the issueFromType to set
	 */
	public void setIssueFromType(String issueFromType) {
		this.issueFromType = issueFromType;
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
	 * @return the transactionFromType
	 */
	public String getTransactionFromType() {
		return transactionFromType;
	}

	/**
	 * @param transactionFromType the transactionFromType to set
	 */
	public void setTransactionFromType(String transactionFromType) {
		this.transactionFromType = transactionFromType;
	}

	
	
	

}
