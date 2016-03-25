package com.ff.domain.stockmanagement.operations.receipt;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */

public class SAPStockReceiptDO extends CGFactDO {
	
	private static final long serialVersionUID = 895872635331891429L;
	private Long Id;
	private Integer rowNumber;
	private Date receivedDate;
	private String requisitionNumber;
	private String itemCode;
	private String itemTypeCode;
	private String description;
	private String uom;
	private Integer receivedQty;
	private String ackNumber;
	private String issueNumber;
	private String exception;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the requisitionNumber
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	/**
	 * @param requisitionNumber the requisitionNumber to set
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the itemTypeCode
	 */
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	/**
	 * @param itemTypeCode the itemTypeCode to set
	 */
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
	/**
	 * @return the receivedQty
	 */
	public Integer getReceivedQty() {
		return receivedQty;
	}
	/**
	 * @param receivedQty the receivedQty to set
	 */
	public void setReceivedQty(Integer receivedQty) {
		this.receivedQty = receivedQty;
	}
	/**
	 * @return the ackNumber
	 */
	public String getAckNumber() {
		return ackNumber;
	}
	/**
	 * @param ackNumber the ackNumber to set
	 */
	public void setAckNumber(String ackNumber) {
		this.ackNumber = ackNumber;
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
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	
	
	
}
