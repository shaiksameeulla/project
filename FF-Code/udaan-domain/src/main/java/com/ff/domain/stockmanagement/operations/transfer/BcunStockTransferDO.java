/**
 * 
 */
package com.ff.domain.stockmanagement.operations.transfer;

import java.util.Date;

import com.ff.domain.stockmanagement.operations.BcunStockCommonDetailsDO;

/**
 * The Class StockTransferDO.
 *
 * @author mohammes
 */
public class BcunStockTransferDO extends BcunStockCommonDetailsDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2092696548510074286L;
	
	/** primary key of stock transfer. */
	private Long stockTransferId;
	
	/** Unique Stock Transfer number. */
	private String stockTransferNumber;
	
	/** Stock Issue number. */
	private String stockIssueNumber;
	
	/**  Stock Transfer From type e.x :BA/Customer/Employee/Franchisee		 */
	private String transferFromType;
	
	/**  Stock Transfer TO type e.x :Branch/BA/Customer/Employee/Franchisee		 */
	private String transferTOType;
	
	/** Stock transfer Transaction Date. */
	private Date transferDate;
	
	/** Stock transfer From Customer DO. */
	private Integer transferFromCustomerId;
	
	/** Stock transfer created office DO. */
	private Integer createdOfficeId;
	
	/** Stock transfer From BA DO. */
	private Integer transferFromBaId;
	
	/** Stock transfer From EMPLOYEE DO. */
	private Integer transferFromEmpId;
	
	/** Stock transfer TO Customer DO. */
	private Integer transferTOCustomerId;
	
	/** Stock transfer TO Office DO. */
	private Integer transferTOOfficeId;
	
	/** Stock transfer TO BA DO. */
	private Integer transferTOBaId;
	
	/** Stock transfer TO EMPLOYEE DO. */
	private Integer transferTOEmpId;
	
	

	/** Stock transfer Quantity. */
	private Integer transferQuantity;
	
	private String shippedToCode;
	/**
	 * Gets the stock transfer id.
	 *
	 * @return the stock transfer id
	 */
	public Long getStockTransferId() {
		return stockTransferId;
	}

	/**
	 * Sets the stock transfer id.
	 *
	 * @param stockTransferId the new stock transfer id
	 */
	public void setStockTransferId(Long stockTransferId) {
		this.stockTransferId = stockTransferId;
	}

	/**
	 * Gets the stock transfer number.
	 *
	 * @return the stock transfer number
	 */
	public String getStockTransferNumber() {
		return stockTransferNumber;
	}

	/**
	 * Sets the stock transfer number.
	 *
	 * @param stockTransferNumber the new stock transfer number
	 */
	public void setStockTransferNumber(String stockTransferNumber) {
		this.stockTransferNumber = stockTransferNumber;
	}

	/**
	 * Gets the stock issue number.
	 *
	 * @return the stock issue number
	 */
	public String getStockIssueNumber() {
		return stockIssueNumber;
	}

	/**
	 * Sets the stock issue number.
	 *
	 * @param stockIssueNumber the new stock issue number
	 */
	public void setStockIssueNumber(String stockIssueNumber) {
		this.stockIssueNumber = stockIssueNumber;
	}

	/**
	 * Gets the transfer from type.
	 *
	 * @return the transfer from type
	 */
	public String getTransferFromType() {
		return transferFromType;
	}

	/**
	 * Sets the transfer from type.
	 *
	 * @param transferFromType the new transfer from type
	 */
	public void setTransferFromType(String transferFromType) {
		this.transferFromType = transferFromType;
	}

	

	/**
	 * Gets the transfer date.
	 *
	 * @return the transfer date
	 */
	public Date getTransferDate() {
		return transferDate;
	}

	/**
	 * Sets the transfer date.
	 *
	 * @param transferDate the new transfer date
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	



	
	public Integer getTransferFromEmpId() {
		return transferFromEmpId;
	}

	public void setTransferFromEmpId(Integer transferFromEmpId) {
		this.transferFromEmpId = transferFromEmpId;
	}

	/**
	 * Gets the transfer quantity.
	 *
	 * @return the transfer quantity
	 */
	public Integer getTransferQuantity() {
		return transferQuantity;
	}

	/**
	 * Sets the transfer quantity.
	 *
	 * @param transferQuantity the new transfer quantity
	 */
	public void setTransferQuantity(Integer transferQuantity) {
		this.transferQuantity = transferQuantity;
	}

	/**
	 * Gets the transfer to type.
	 *
	 * @return the transfer to type
	 */
	public String getTransferTOType() {
		return transferTOType;
	}

	/**
	 * Sets the transfer to type.
	 *
	 * @param transferTOType the new transfer to type
	 */
	public void setTransferTOType(String transferTOType) {
		this.transferTOType = transferTOType;
	}

	public Integer getTransferFromCustomerId() {
		return transferFromCustomerId;
	}

	public void setTransferFromCustomerId(Integer transferFromCustomerId) {
		this.transferFromCustomerId = transferFromCustomerId;
	}

	public Integer getCreatedOfficeId() {
		return createdOfficeId;
	}

	public void setCreatedOfficeId(Integer createdOfficeId) {
		this.createdOfficeId = createdOfficeId;
	}

	public Integer getTransferFromBaId() {
		return transferFromBaId;
	}

	public void setTransferFromBaId(Integer transferFromBaId) {
		this.transferFromBaId = transferFromBaId;
	}

	public Integer getTransferTOCustomerId() {
		return transferTOCustomerId;
	}

	public void setTransferTOCustomerId(Integer transferTOCustomerId) {
		this.transferTOCustomerId = transferTOCustomerId;
	}

	public Integer getTransferTOOfficeId() {
		return transferTOOfficeId;
	}

	public void setTransferTOOfficeId(Integer transferTOOfficeId) {
		this.transferTOOfficeId = transferTOOfficeId;
	}

	public Integer getTransferTOBaId() {
		return transferTOBaId;
	}

	public void setTransferTOBaId(Integer transferTOBaId) {
		this.transferTOBaId = transferTOBaId;
	}

	public Integer getTransferTOEmpId() {
		return transferTOEmpId;
	}

	/**
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	public void setTransferTOEmpId(Integer transferTOEmpId) {
		this.transferTOEmpId = transferTOEmpId;
	}

		
}
