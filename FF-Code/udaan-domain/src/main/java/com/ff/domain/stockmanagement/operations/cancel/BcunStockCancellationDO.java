/**
 * 
 */
package com.ff.domain.stockmanagement.operations.cancel;

import java.util.Date;

import com.ff.domain.stockmanagement.operations.BcunStockCommonDetailsDO;

/**
 * The Class StockCancellationDO.
 *
 * @author cbhure
 */
public class BcunStockCancellationDO extends BcunStockCommonDetailsDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7361644014239338279L;

	/** The stock cancelled id. */
	
	private Long stockCancelledId;
	
	/** The cancellation number. */
	private String cancellationNumber;
	
	/** The reason. */
	private String reason;
	
	/** The cancelled date. */
	private Date cancelledDate;
	
	/** The issue number. */
	private String issueNumber;
	
	/** The quantity. */
	private Integer quantity;
	
	/** The cancellation office do. */
	private Integer   cancellationOfficId;
	
	
	
	/**
	 * Gets the cancellation number.
	 *
	 * @return the cancellationNumber
	 */
	public String getCancellationNumber() {
		return cancellationNumber;
	}
	
	/**
	 * Sets the cancellation number.
	 *
	 * @param cancellationNumber the cancellationNumber to set
	 */
	public void setCancellationNumber(String cancellationNumber) {
		this.cancellationNumber = cancellationNumber;
	}
	
	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * Sets the reason.
	 *
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**
	 * Gets the cancelled date.
	 *
	 * @return the cancelledDate
	 */
	public Date getCancelledDate() {
		return cancelledDate;
	}
	
	/**
	 * Sets the cancelled date.
	 *
	 * @param cancelledDate the cancelledDate to set
	 */
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
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
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity.
	 *
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Gets the cancellation office do.
	 *
	 * @return the cancellationOfficeDO
	 */

	/**
	 * Gets the stock cancelled id.
	 *
	 * @return the stock cancelled id
	 */
	public Long getStockCancelledId() {
		return stockCancelledId;
	}
	
	/**
	 * Sets the stock cancelled id.
	 *
	 * @param stockCancelledId the new stock cancelled id
	 */
	public void setStockCancelledId(Long stockCancelledId) {
		this.stockCancelledId = stockCancelledId;
	}

	public Integer getCancellationOfficId() {
		return cancellationOfficId;
	}

	public void setCancellationOfficId(Integer cancellationOfficId) {
		this.cancellationOfficId = cancellationOfficId;
	}

	
}
	