/**
 * 
 */
package com.ff.domain.stockmanagement.operations.issue;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */

public class SAPStockIssueDO extends CGFactDO { 
	
	private static final long serialVersionUID = -6449648984299517298L;
	private Long Id;
	private Long stockIssueItemDtlsId;
	private Integer rowNumber;
	private Date issueDate;
	private String requisitionNumber;
	private String itemCode;
	private String itemTypeCode;
	private String description;
	private String uom;
	private Integer issuedQty;
	private String issueNumber;
	private String issuedToofficeCode;
	private String issuedOfficeCode;
	private String custCode;
	private String exception;
	
	
	
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
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}
	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
	 * @return the issuedQty
	 */
	public Integer getIssuedQty() {
		return issuedQty;
	}
	/**
	 * @param issuedQty the issuedQty to set
	 */
	public void setIssuedQty(Integer issuedQty) {
		this.issuedQty = issuedQty;
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
	 * @return the issuedToofficeCode
	 */
	public String getIssuedToofficeCode() {
		return issuedToofficeCode;
	}
	/**
	 * @param issuedToofficeCode the issuedToofficeCode to set
	 */
	public void setIssuedToofficeCode(String issuedToofficeCode) {
		this.issuedToofficeCode = issuedToofficeCode;
	}
	/**
	 * @return the issuedOfficeCode
	 */
	public String getIssuedOfficeCode() {
		return issuedOfficeCode;
	}
	/**
	 * @param issuedOfficeCode the issuedOfficeCode to set
	 */
	public void setIssuedOfficeCode(String issuedOfficeCode) {
		this.issuedOfficeCode = issuedOfficeCode;
	}
	/**
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}
	/**
	 * @param custCode the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	
}
