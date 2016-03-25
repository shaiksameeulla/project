package com.ff.to.stockmanagement.stockcancel;

import com.ff.to.stockmanagement.StockHeaderTO;

public class StockCancellationTO extends StockHeaderTO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6261059914668374981L;
	
	private Long stockCancelledId;
	private String cancelledDateStr;
	private String reason;
	private String cancellationNo;
	private Integer quantity;
	
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	private Integer loggedInOfficeId;
	private String loggedInOfficeName;
	private Integer cancellationOfficeId;
	private Integer itemId;
	private String issueNumber;
	
	/** office code in serial number	*/
	public String officeProductCodeInSeries;
	
	/**	excluding office code remaining numbers in starting serial number	*/
	public Long startLeaf;
	
	/** excluding office code remaining numbers in ending serial number	*/
	public Long endLeaf;
	
	/**	  starting series number Ex. B999L00000001	*/
	public String startSerialNumber;
	
	/**	  ending series number Ex. B999L00000009	*/
	public String endSerialNumber;
	/**
	 * @return the stockCancelledId
	 */
	
	
	public Long getStockCancelledId() {
		return stockCancelledId;
	}
	/**
	 * @param stockCancelledId the stockCancelledId to set
	 */
	public void setStockCancelledId(Long stockCancelledId) {
		this.stockCancelledId = stockCancelledId;
	}
	
	
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the cancellationNo
	 */
	public String getCancellationNo() {
		return cancellationNo;
	}
	/**
	 * @param cancellationNo the cancellationNo to set
	 */
	public void setCancellationNo(String cancellationNo) {
		this.cancellationNo = cancellationNo;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the loggedInUserId
	 */
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}
	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	/**
	 * @return the createdByUserId
	 */
	public Integer getCreatedByUserId() {
		return createdByUserId;
	}
	/**
	 * @param createdByUserId the createdByUserId to set
	 */
	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	/**
	 * @return the updatedByUserId
	 */
	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}
	/**
	 * @param updatedByUserId the updatedByUserId to set
	 */
	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	/**
	 * @return the loggedInOfficeName
	 */
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}
	/**
	 * @param loggedInOfficeName the loggedInOfficeName to set
	 */
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}
		/**
	 * @return the cancellationOfficeId
	 */
	public Integer getCancellationOfficeId() {
		return cancellationOfficeId;
	}
	/**
	 * @param cancellationOfficeId the cancellationOfficeId to set
	 */
	public void setCancellationOfficeId(Integer cancellationOfficeId) {
		this.cancellationOfficeId = cancellationOfficeId;
	}
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the issueNumber
	 */
	public String getIssueNumber() {
		return issueNumber;
	}
	/**
	 * @param issueNumber the issueNumber to set
	 */
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	/**
	 * @return the cancelledDateStr
	 */
	public String getCancelledDateStr() {
		return cancelledDateStr;
	}
	/**
	 * @return the officeProductCodeInSeries
	 */
	public String getOfficeProductCodeInSeries() {
		return officeProductCodeInSeries;
	}
	/**
	 * @return the startLeaf
	 */
	public Long getStartLeaf() {
		return startLeaf;
	}
	/**
	 * @return the endLeaf
	 */
	public Long getEndLeaf() {
		return endLeaf;
	}
	/**
	 * @return the startSerialNumber
	 */
	public String getStartSerialNumber() {
		return startSerialNumber;
	}
	/**
	 * @return the endSerialNumber
	 */
	public String getEndSerialNumber() {
		return endSerialNumber;
	}
	/**
	 * @param cancelledDateStr the cancelledDateStr to set
	 */
	public void setCancelledDateStr(String cancelledDateStr) {
		this.cancelledDateStr = cancelledDateStr;
	}
	/**
	 * @param officeProductCodeInSeries the officeProductCodeInSeries to set
	 */
	public void setOfficeProductCodeInSeries(String officeProductCodeInSeries) {
		this.officeProductCodeInSeries = officeProductCodeInSeries;
	}
	/**
	 * @param startLeaf the startLeaf to set
	 */
	public void setStartLeaf(Long startLeaf) {
		this.startLeaf = startLeaf;
	}
	/**
	 * @param endLeaf the endLeaf to set
	 */
	public void setEndLeaf(Long endLeaf) {
		this.endLeaf = endLeaf;
	}
	/**
	 * @param startSerialNumber the startSerialNumber to set
	 */
	public void setStartSerialNumber(String startSerialNumber) {
		this.startSerialNumber = startSerialNumber;
	}
	/**
	 * @param endSerialNumber the endSerialNumber to set
	 */
	public void setEndSerialNumber(String endSerialNumber) {
		this.endSerialNumber = endSerialNumber;
	}
	
	
	
}
