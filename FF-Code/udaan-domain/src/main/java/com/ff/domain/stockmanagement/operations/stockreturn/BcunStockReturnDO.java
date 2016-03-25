/**
 * 
 */
package com.ff.domain.stockmanagement.operations.stockreturn;

import java.util.Date;
import java.util.Set;

import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;

/**
 * The Class StockReturnDO.
 *
 * @author cbhure
 */
public class BcunStockReturnDO extends StockCommonBaseDO{

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
	private Integer returningOfficeId;
	
	/** The issued office do. */
	private Integer issuedOfficeId;
	
	/** The created by user do. */
	private Integer createUserId;
	
	/** The updated by user do. */
	private Integer updateUserId;
	
	/** The return date. */
	private Date returnDate;
	
	/** The issue from type. */
	private String issueFromType;

	/** The return item dtls. */
	Set<BcunStockReturnItemDtlsDO> returnItemDtls;

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

	public Integer getReturningOfficeId() {
		return returningOfficeId;
	}

	public void setReturningOfficeId(Integer returningOfficeId) {
		this.returningOfficeId = returningOfficeId;
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

	public Set<BcunStockReturnItemDtlsDO> getReturnItemDtls() {
		return returnItemDtls;
	}

	public void setReturnItemDtls(Set<BcunStockReturnItemDtlsDO> returnItemDtls) {
		this.returnItemDtls = returnItemDtls;
	}

	
	
	

}
