package com.ff.domain.stockmanagement.operations;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class StockCommonDetailsDO.
 *
 * @author mohammes
 */
public class BcunStockCommonDetailsDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3257017059437815523L;

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
	
	/** remarks at the time of approval. */
	public String approveRemarks;
	
	/** final requested quantity to be approved. */
	public  Integer requestedQuantity;
	
	/** final approved quantity to be issued. */
	public Integer approvedQuantity;

	/** final issued quantity. */
	public Integer issuedQuantity;
	
	/** final received quantity. */
	public Integer receivedQuantity;
	
	/** returning quantity. */
	public Integer returningQuantity;
	
	/** description of the item. */
	public String description;
	
	/** office code in serial number. */
	public String officeProductCodeInSeries;
	
	/** excluding office code remaining numbers in starting serial number. */
	public Long startLeaf;
	
	/** excluding office code remaining numbers in ending serial number. */
	public Long endLeaf;
	
	/**	  starting series number Ex. B999L00000001	*/
	public String startSerialNumber;
	
	/**	  ending series number Ex. B999L00000009	*/
	public String endSerialNumber;
	
	
	/** Non-persistent property  It hold the FK of the table(s) Stock issue/Stock receipt/Stock requisition it's for partial receipt/issue. */
	public Long stockItemDtlsId;
	
	
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
	 * Gets the issued quantity.
	 *
	 * @return the issuedQuantity
	 */
	public Integer getIssuedQuantity() {
		return issuedQuantity;
	}

	/**
	 * Sets the issued quantity.
	 *
	 * @param issuedQuantity the issuedQuantity to set
	 */
	public void setIssuedQuantity(Integer issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Sets the office product code in series.
	 *
	 * @param officeProductCodeInSeries the officeProductCodeInSeries to set
	 */
	public void setOfficeProductCodeInSeries(String officeProductCodeInSeries) {
		this.officeProductCodeInSeries = officeProductCodeInSeries;
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
	 * Sets the start leaf.
	 *
	 * @param startLeaf the startLeaf to set
	 */
	public void setStartLeaf(Long startLeaf) {
		this.startLeaf = startLeaf;
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
	 * Sets the end leaf.
	 *
	 * @param endLeaf the endLeaf to set
	 */
	public void setEndLeaf(Long endLeaf) {
		this.endLeaf = endLeaf;
	}

	/**
	 * Gets the start serial number.
	 *
	 * @return the startSerialNumber
	 */
	public String getStartSerialNumber() {
		return startSerialNumber;
	}

	/**
	 * Sets the start serial number.
	 *
	 * @param startSerialNumber the startSerialNumber to set
	 */
	public void setStartSerialNumber(String startSerialNumber) {
		this.startSerialNumber = startSerialNumber;
	}

	/**
	 * Gets the end serial number.
	 *
	 * @return the endSerialNumber
	 */
	public String getEndSerialNumber() {
		return endSerialNumber;
	}

	/**
	 * Sets the end serial number.
	 *
	 * @param endSerialNumber the endSerialNumber to set
	 */
	public void setEndSerialNumber(String endSerialNumber) {
		this.endSerialNumber = endSerialNumber;
	}

	/**
	 * Gets the item type do.
	 *
	 * @return the item type do
	 */
	

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



	public Integer getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
}
