package com.ff.to.stockmanagement.stocktransfer;

import java.util.Map;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author hkansagr
 * 
 */

public class StockTransferTO extends StockHeaderTO {
	
	private static final long serialVersionUID = 1235354656L;

	private Long stockTransferId;
	private String stockTransferNumber;
	
	private String transferDateStr;
	
	private Integer transferFromPersonId;
	private Integer transferTOPersonId;
	
	/**  Stock Transfer From type e.x :BA/Customer/Employee/Franchisee		 */
	private String transferFromType;
	
	/**  Stock Transfer TO type e.x :BA/Customer/Employee/Franchisee		 */
	private String transferTOType;
	
	private String startSerialNumber;
	private String endSerialNumber;
	
	/** office code in serial number	*/
	public String officeProductCodeInSeries;
	
	/**	excluding office code remaining numbers in starting serial number	*/
	public Long startLeaf;
	
	/** excluding office code remaining numbers in ending serial number	*/
	public Long endLeaf;
	
	/**  Stock Issue number		 */
	private String stockIssueNumber;
	
	/**  Stock transfer Quantity */
	private Integer transferQuantity;
	
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	private Integer  loggedInOfficeId;
	private Integer  transferOfficeId;
	
	private String loggedInOfficeName;
	private String canUpdate;
	
	Map<Integer,String> transferFromPartyMap;
	Map<Integer,String> transferTOPartyMap;
	private Integer itemId;
	
	
	public Long getStockTransferId() {
		return stockTransferId;
	}
	public void setStockTransferId(Long stockTransferId) {
		this.stockTransferId = stockTransferId;
	}
	public String getStockTransferNumber() {
		return stockTransferNumber;
	}
	public void setStockTransferNumber(String stockTransferNumber) {
		this.stockTransferNumber = stockTransferNumber;
	}
	public String getTransferDateStr() {
		return transferDateStr;
	}
	public void setTransferDateStr(String transferDateStr) {
		this.transferDateStr = transferDateStr;
	}
	public Integer getTransferFromPersonId() {
		return transferFromPersonId;
	}
	public void setTransferFromPersonId(Integer transferFromPersonId) {
		this.transferFromPersonId = transferFromPersonId;
	}
	public Integer getTransferTOPersonId() {
		return transferTOPersonId;
	}
	public void setTransferTOPersonId(Integer transferTOPersonId) {
		this.transferTOPersonId = transferTOPersonId;
	}
	public String getTransferFromType() {
		return transferFromType;
	}
	public void setTransferFromType(String transferFromType) {
		this.transferFromType = transferFromType;
	}
	public String getTransferTOType() {
		return transferTOType;
	}
	public void setTransferTOType(String transferTOType) {
		this.transferTOType = transferTOType;
	}
	
	public String getStockIssueNumber() {
		return stockIssueNumber;
	}
	public void setStockIssueNumber(String stockIssueNumber) {
		this.stockIssueNumber = stockIssueNumber;
	}
	public Integer getTransferQuantity() {
		return transferQuantity;
	}
	public void setTransferQuantity(Integer transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	public Integer getCreatedByUserId() {
		return createdByUserId;
	}
	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}
	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	public Integer getTransferOfficeId() {
		return transferOfficeId;
	}
	public void setTransferOfficeId(Integer transferOfficeId) {
		this.transferOfficeId = transferOfficeId;
	}
	
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}
	public String getCanUpdate() {
		return canUpdate;
	}
	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}
	/**
	 * @return the transferFromPartyMap
	 */
	public Map<Integer, String> getTransferFromPartyMap() {
		return transferFromPartyMap;
	}
	/**
	 * @param transferFromPartyMap the transferFromPartyMap to set
	 */
	public void setTransferFromPartyMap(Map<Integer, String> transferFromPartyMap) {
		this.transferFromPartyMap = transferFromPartyMap;
	}
	/**
	 * @return the transferTOPartyMap
	 */
	public Map<Integer, String> getTransferTOPartyMap() {
		return transferTOPartyMap;
	}
	/**
	 * @param transferTOPartyMap the transferTOPartyMap to set
	 */
	public void setTransferTOPartyMap(Map<Integer, String> transferTOPartyMap) {
		this.transferTOPartyMap = transferTOPartyMap;
	}
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @return the startSerialNumber
	 */
	public String getStartSerialNumber() {
		return startSerialNumber;
	}
	/**
	 * @param startSerialNumber the startSerialNumber to set
	 */
	public void setStartSerialNumber(String startSerialNumber) {
		this.startSerialNumber = startSerialNumber;
	}
	/**
	 * @return the endSerialNumber
	 */
	public String getEndSerialNumber() {
		return endSerialNumber;
	}
	/**
	 * @param endSerialNumber the endSerialNumber to set
	 */
	public void setEndSerialNumber(String endSerialNumber) {
		this.endSerialNumber = endSerialNumber;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	
	
}
