package com.ff.to.stockmanagement.stockissue;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 *	@author hkansagr 
 */

public class StockIssueEmployeeTO extends StockHeaderTO {
	private static final long serialVersionUID = 1L;
	
	private Long stockIssueId;
	
	private String createdIssueDate;
	private String createdIssueTime;
	
	private Integer itemId;		
	private Integer recipientId;	//OR Customer List
	private String recipientCode;		//OR Customer Code
	private String recipientName;	
	private Integer issuedQuantity;
	private String startSerialNumber;	
	private String endSerialNumber;
	private String stockIssueNumber;
	
	private Integer loggedInOfficeId;
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	
	private String issuedToType;
	
	private String itemSeries;
	private Integer seriesLength;
	private String isItemHasSeries;
	private Integer currentStockQuantity;
	private String seriesType;
	private Long stockIssueItemDtlsId;
	private String officeProductCodeInSeries;
	private Long startLeaf;
	private Long endLeaf;
	
	
	
	
	
	/**
	 * @return the itemSeries
	 */
	public String getItemSeries() {
		return itemSeries;
	}
	/**
	 * @param itemSeries the itemSeries to set
	 */
	public void setItemSeries(String itemSeries) {
		this.itemSeries = itemSeries;
	}
	/**
	 * @return the seriesLength
	 */
	public Integer getSeriesLength() {
		return seriesLength;
	}
	/**
	 * @param seriesLength the seriesLength to set
	 */
	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}
	/**
	 * @return the isItemHasSeries
	 */
	public String getIsItemHasSeries() {
		return isItemHasSeries;
	}
	/**
	 * @param isItemHasSeries the isItemHasSeries to set
	 */
	public void setIsItemHasSeries(String isItemHasSeries) {
		this.isItemHasSeries = isItemHasSeries;
	}
	/**
	 * @return the currentStockQuantity
	 */
	public Integer getCurrentStockQuantity() {
		return currentStockQuantity;
	}
	/**
	 * @param currentStockQuantity the currentStockQuantity to set
	 */
	public void setCurrentStockQuantity(Integer currentStockQuantity) {
		this.currentStockQuantity = currentStockQuantity;
	}
	public String getCreatedIssueTime() {
		return createdIssueTime;
	}
	/**
	 * @param createdIssueTime the createdIssueTime to set
	 */
	public void setCreatedIssueTime(String createdIssueTime) {
		this.createdIssueTime = createdIssueTime;
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
	 * @return the issuedQuantity
	 */
	public Integer getIssuedQuantity() {
		return issuedQuantity;
	}
	/**
	 * @param issuedQuantity the issuedQuantity to set
	 */
	public void setIssuedQuantity(Integer issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}
	/**
	 * @return the stockIssueNumber
	 */
	public String getStockIssueNumber() {
		return stockIssueNumber;
	}
	/**
	 * @param stockIssueNumber the stockIssueNumber to set
	 */
	public void setStockIssueNumber(String stockIssueNumber) {
		this.stockIssueNumber = stockIssueNumber;
	}
	/**
	 * @return the stockIssueId
	 */
	public Long getStockIssueId() {
		return stockIssueId;
	}
	/**
	 * @param stockIssueId the stockIssueId to set
	 */
	public void setStockIssueId(Long stockIssueId) {
		this.stockIssueId = stockIssueId;
	}
	/**
	 * @return the createdIssueDate
	 */
	public String getCreatedIssueDate() {
		return createdIssueDate;
	}
	/**
	 * @param createdIssueDate the createdIssueDate to set
	 */
	public void setCreatedIssueDate(String createdIssueDate) {
		this.createdIssueDate = createdIssueDate;
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
	 * @return the recipientId
	 */
	public Integer getRecipientId() {
		return recipientId;
	}
	/**
	 * @return the recipientCode
	 */
	public String getRecipientCode() {
		return recipientCode;
	}
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
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
	 * @param recipientId the recipientId to set
	 */
	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}
	/**
	 * @param recipientCode the recipientCode to set
	 */
	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}
	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
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
	/**
	 * @return the seriesType
	 */
	public String getSeriesType() {
		return seriesType;
	}
	/**
	 * @param seriesType the seriesType to set
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}
	/**
	 * @return the stockIssueItemDtlsId
	 */
	public Long getStockIssueItemDtlsId() {
		return stockIssueItemDtlsId;
	}
	/**
	 * @param stockIssueItemDtlsId the stockIssueItemDtlsId to set
	 */
	public void setStockIssueItemDtlsId(Long stockIssueItemDtlsId) {
		this.stockIssueItemDtlsId = stockIssueItemDtlsId;
	}
	/**
	 * @return the issuedTOType
	 */
	public String getIssuedToType() {
		return issuedToType;
	}
	/**
	 * @param issuedTOType the issuedTOType to set
	 */
	public void setIssuedToType(String issuedToType) {
		this.issuedToType = issuedToType;
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
	 * @return the officeProductCodeInSeries
	 */
	public String getOfficeProductCodeInSeries() {
		return officeProductCodeInSeries;
	}
	/**
	 * @param officeProductCodeInSeries the officeProductCodeInSeries to set
	 */
	public void setOfficeProductCodeInSeries(String officeProductCodeInSeries) {
		this.officeProductCodeInSeries = officeProductCodeInSeries;
	}
}
