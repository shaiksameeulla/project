/**
 * 
 */
package com.ff.to.stockmanagement;

import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class StockDetailTO.
 *
 * @author mohammes
 */
public abstract class StockDetailTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 153456453453L;
	
	/** Material type information many-to-one relationship. */
	public Integer itemTypeId;
	
	/** Material Code information many-to-one relationship. */
	public Integer itemId;
	
	/** rowNumber : sI no in the screen. */
	public  Integer rowNumber;
	
	/** Unit of measure information. */
	public  String uom;
	
	/** remarks at the time of transaction. */
	public String remarks;
	
	/** description of the item. */
	public String description;
	
	/** remarks at the time of approval. */
	public String approveRemarks;
	
	/** final requested quantity to be approved. */
	public  Integer requestedQuantity;
	
	/** final approved quantity to be issued. */
	public Integer approvedQuantity;
	
	
	
	/** final issued quantity which had issued/issuing. */
	public Integer issuedQuantity;
	
	/** final received quantity. */
	public Integer receivedQuantity;
	
	/** returning quantity. */
	public Integer returningQuantity;
	
	/** start/end serial number. */
	public String startSerialNumber;
	
	/** The end serial number. */
	public String endSerialNumber;
	
	/** office code in serial number. */
	public String officeProductCodeInSeries;
	
	/** excluding office code remaining numbers in starting serial number. */
	public Long startLeaf;
	
	/** excluding office code remaining numbers in ending serial number. */
	public Long endLeaf;
	
	
	
	/** The transaction create date str. */
	public String transactionCreateDateStr;
	
	/** The series. */
	public String series;
	
	/** The series length. */
	public Integer seriesLength;
	
	/** The is item has series. */
	public String isItemHasSeries;
	
	/**   it holds text which represents whether series of which type like CNote/stickers..ets */
	public String seriesType;
	
	
	
	/** Non-persistent property  It hold the FK of the table(s) Stock issue/Stock receipt/Stock requisition it's for partial receipt/issue. */
	public Long stockItemDtlsId;

	/** The current stock quantity. */
	public Integer currentStockQuantity;
	
	/** for Drop down population in the Screen for ItemType rather than populating all itemTypes from DB. */
	public Map<Integer,String> itemType;
	
	/** for Drop down population in the Screen for Item(Material) rather than populating all item dtls from DB. */
	public Map<Integer,String> item;
	
	/** The balance quantity. */
	public Integer balanceQuantity;//it holds information for balance Quantity irrespective of the table
	
	
	/** The requisition created office id. Office at which Actual Requisition created.  and persistable */
	private Integer requisitionCreatedOfficeId; 
	
	private String seriesStartsWith;// for  SAP(Acknowledgement at RHO), Series validation  and  Persistable
	/**
	 * @return the requisitionCreatedOfficeId
	 */
	public Integer getRequisitionCreatedOfficeId() {
		return requisitionCreatedOfficeId;
	}

	/**
	 * @return the seriesStartsWith
	 */
	public String getSeriesStartsWith() {
		return seriesStartsWith;
	}

	/**
	 * @param requisitionCreatedOfficeId the requisitionCreatedOfficeId to set
	 */
	public void setRequisitionCreatedOfficeId(Integer requisitionCreatedOfficeId) {
		this.requisitionCreatedOfficeId = requisitionCreatedOfficeId;
	}

	/**
	 * @param seriesStartsWith the seriesStartsWith to set
	 */
	public void setSeriesStartsWith(String seriesStartsWith) {
		this.seriesStartsWith = seriesStartsWith;
	}

	
	

	/**
	 * Gets the received quantity.
	 *
	 * @return the receivedQuantity
	 */
	public Integer getReceivedQuantity() {
		return receivedQuantity;
	}

	/**
	 * Sets the received quantity.
	 *
	 * @param receivedQuantity the receivedQuantity to set
	 */
	public void setReceivedQuantity(Integer receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	/**
	 * Gets the returning quantity.
	 *
	 * @return the returningQuantity
	 */
	public Integer getReturningQuantity() {
		return returningQuantity;
	}

	/**
	 * Sets the returning quantity.
	 *
	 * @param returningQuantity the returningQuantity to set
	 */
	public void setReturningQuantity(Integer returningQuantity) {
		this.returningQuantity = returningQuantity;
	}

	/**
	 * Gets the item type id.
	 *
	 * @return the item type id
	 */
	public Integer getItemTypeId() {
		return itemTypeId;
	}

	/**
	 * Sets the item type id.
	 *
	 * @param itemTypeId the new item type id
	 */
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * Gets the row number.
	 *
	 * @return the row number
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * Sets the row number.
	 *
	 * @param rowNumber the new row number
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * Gets the uom.
	 *
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * Sets the uom.
	 *
	 * @param uom the new uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Gets the approve remarks.
	 *
	 * @return the approve remarks
	 */
	public String getApproveRemarks() {
		return approveRemarks;
	}

	/**
	 * Sets the approve remarks.
	 *
	 * @param approveRemarks the new approve remarks
	 */
	public void setApproveRemarks(String approveRemarks) {
		this.approveRemarks = approveRemarks;
	}

	/**
	 * Gets the requested quantity.
	 *
	 * @return the requested quantity
	 */
	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	/**
	 * Sets the requested quantity.
	 *
	 * @param requestedQuantity the new requested quantity
	 */
	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	/**
	 * Gets the approved quantity.
	 *
	 * @return the approved quantity
	 */
	public Integer getApprovedQuantity() {
		return approvedQuantity;
	}

	/**
	 * Sets the approved quantity.
	 *
	 * @param approvedQuantity the new approved quantity
	 */
	public void setApprovedQuantity(Integer approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}

	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the transaction create date str.
	 *
	 * @return the transaction create date str
	 */
	public String getTransactionCreateDateStr() {
		return transactionCreateDateStr;
	}

	/**
	 * Sets the transaction create date str.
	 *
	 * @param transactionCreateDateStr the new transaction create date str
	 */
	public void setTransactionCreateDateStr(String transactionCreateDateStr) {
		this.transactionCreateDateStr = transactionCreateDateStr;
	}

	/**
	 * Gets the start serial number.
	 *
	 * @return the start serial number
	 */
	public String getStartSerialNumber() {
		return startSerialNumber;
	}

	/**
	 * Sets the start serial number.
	 *
	 * @param startSerialNumber the new start serial number
	 */
	public void setStartSerialNumber(String startSerialNumber) {
		this.startSerialNumber = startSerialNumber;
	}

	/**
	 * Gets the end serial number.
	 *
	 * @return the end serial number
	 */
	public String getEndSerialNumber() {
		return endSerialNumber;
	}

	/**
	 * Sets the end serial number.
	 *
	 * @param endSerialNumber the new end serial number
	 */
	public void setEndSerialNumber(String endSerialNumber) {
		this.endSerialNumber = endSerialNumber;
	}

	/**
	 * Gets the issued quantity.
	 *
	 * @return the issued quantity
	 */
	public Integer getIssuedQuantity() {
		return issuedQuantity;
	}

	/**
	 * Sets the issued quantity.
	 *
	 * @param issuedQuantity the new issued quantity
	 */
	public void setIssuedQuantity(Integer issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * Sets the series.
	 *
	 * @param series the new series
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	

	/**
	 * Gets the checks if is item has series.
	 *
	 * @return the checks if is item has series
	 */
	public String getIsItemHasSeries() {
		return isItemHasSeries;
	}

	/**
	 * Sets the checks if is item has series.
	 *
	 * @param isItemHasSeries the new checks if is item has series
	 */
	public void setIsItemHasSeries(String isItemHasSeries) {
		this.isItemHasSeries = isItemHasSeries;
	}

	/**
	 * Gets the series length.
	 *
	 * @return the series length
	 */
	public Integer getSeriesLength() {
		return seriesLength;
	}

	/**
	 * Sets the series length.
	 *
	 * @param seriesLength the new series length
	 */
	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}

	/**
	 * Gets the stock item dtls id.
	 *
	 * @return the stock item dtls id
	 */
	public Long getStockItemDtlsId() {
		return stockItemDtlsId;
	}

	/**
	 * Sets the stock item dtls id.
	 *
	 * @param stockItemDtlsId the new stock item dtls id
	 */
	public void setStockItemDtlsId(Long stockItemDtlsId) {
		this.stockItemDtlsId = stockItemDtlsId;
	}

	/**
	 * Gets the current stock quantity.
	 *
	 * @return the current stock quantity
	 */
	public Integer getCurrentStockQuantity() {
		return currentStockQuantity;
	}

	/**
	 * Sets the current stock quantity.
	 *
	 * @param currentStockQuantity the new current stock quantity
	 */
	public void setCurrentStockQuantity(Integer currentStockQuantity) {
		this.currentStockQuantity = currentStockQuantity;
	}

	/**
	 * Gets the item type.
	 *
	 * @return the itemType
	 */
	public Map<Integer, String> getItemType() {
		return itemType;
	}

	/**
	 * Sets the item type.
	 *
	 * @param itemType the itemType to set
	 */
	public void setItemType(Map<Integer, String> itemType) {
		this.itemType = itemType;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public Map<Integer, String> getItem() {
		return item;
	}

	/**
	 * Sets the item.
	 *
	 * @param item the item to set
	 */
	public void setItem(Map<Integer, String> item) {
		this.item = item;
	}

	/**
	 * Gets the series type.
	 *
	 * @return the seriesType
	 */
	public String getSeriesType() {
		return seriesType;
	}

	/**
	 * Sets the series type.
	 *
	 * @param seriesType the seriesType to set
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	/**
	 * Gets the balance quantity.
	 *
	 * @return the balanceQuantity
	 */
	public Integer getBalanceQuantity() {
		return balanceQuantity;
	}

	/**
	 * Sets the balance quantity.
	 *
	 * @param balanceQuantity the balanceQuantity to set
	 */
	public void setBalanceQuantity(Integer balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}

	/**
	 * Gets the office product code in series.
	 *
	 * @return the officeProductCodeInSeries
	 */
	public String getOfficeProductCodeInSeries() {
		return officeProductCodeInSeries;
	}

	/**
	 * Gets the start leaf.
	 *
	 * @return the startLeaf
	 */
	public Long getStartLeaf() {
		return startLeaf;
	}

	/**
	 * Gets the end leaf.
	 *
	 * @return the endLeaf
	 */
	public Long getEndLeaf() {
		return endLeaf;
	}

	/**
	 * Sets the office product code in series.
	 *
	 * @param officeProductCodeInSeries the officeProductCodeInSeries to set
	 */
	public void setOfficeProductCodeInSeries(String officeProductCodeInSeries) {
		this.officeProductCodeInSeries = officeProductCodeInSeries;
	}

	/**
	 * Sets the start leaf.
	 *
	 * @param startLeaf the startLeaf to set
	 */
	public void setStartLeaf(Long startLeaf) {
		this.startLeaf = startLeaf;
	}

	/**
	 * Sets the end leaf.
	 *
	 * @param endLeaf the endLeaf to set
	 */
	public void setEndLeaf(Long endLeaf) {
		this.endLeaf = endLeaf;
	}

}
