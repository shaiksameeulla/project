/**
 * 
 */
package com.ff.domain.stockmanagement.operations.transfer;

import java.util.Date;

import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.StockCommonDetailsDO;

/**
 * The Class StockTransferDO.
 *
 * @author mohammes
 */
public class StockTransferDO extends StockCommonDetailsDO {

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
	
	/** The from shipped to code.  Non Persistant Property*/
	private String fromShippedToCode;
	
	/** Stock transfer From Customer DO. */
	private CustomerDO transferFromCustomerDO;
	
	/** Stock transfer created office DO. */
	private OfficeDO createdOfficeDO;
	
	/** Stock transfer From BA DO. */
	private CustomerDO transferFromBaDO;
	
	/** Stock transfer From EMPLOYEE DO. */
	private EmployeeDO transferFromEmpDO;
	
	
	
	/** Stock transfer TO Customer DO. */
	private CustomerDO transferTOCustomerDO;
	
	/** Stock transfer TO Office DO. */
	private OfficeDO transferTOOfficeDO;
	
	/** Stock transfer TO BA DO. */
	private CustomerDO transferTOBaDO;
	
	/** Stock transfer TO EMPLOYEE DO. */
	private EmployeeDO transferTOEmpDO;
	
	

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
	 * @return the fromShippedToCode
	 */
	public String getFromShippedToCode() {
		return fromShippedToCode;
	}

	/**
	 * @param fromShippedToCode the fromShippedToCode to set
	 */
	public void setFromShippedToCode(String fromShippedToCode) {
		this.fromShippedToCode = fromShippedToCode;
	}

	/**
	 * Sets the transfer date.
	 *
	 * @param transferDate the new transfer date
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * Gets the transfer from customer do.
	 *
	 * @return the transfer from customer do
	 */
	public CustomerDO getTransferFromCustomerDO() {
		return transferFromCustomerDO;
	}

	/**
	 * Sets the transfer from customer do.
	 *
	 * @param transferFromCustomerDO the new transfer from customer do
	 */
	public void setTransferFromCustomerDO(CustomerDO transferFromCustomerDO) {
		this.transferFromCustomerDO = transferFromCustomerDO;
	}

	

	

	/**
	 * Gets the transfer from emp do.
	 *
	 * @return the transfer from emp do
	 */
	public EmployeeDO getTransferFromEmpDO() {
		return transferFromEmpDO;
	}

	/**
	 * Sets the transfer from emp do.
	 *
	 * @param transferFromEmpDO the new transfer from emp do
	 */
	public void setTransferFromEmpDO(EmployeeDO transferFromEmpDO) {
		this.transferFromEmpDO = transferFromEmpDO;
	}

	/**
	 * Gets the transfer to customer do.
	 *
	 * @return the transfer to customer do
	 */
	public CustomerDO getTransferTOCustomerDO() {
		return transferTOCustomerDO;
	}

	/**
	 * Sets the transfer to customer do.
	 *
	 * @param transferTOCustomerDO the new transfer to customer do
	 */
	public void setTransferTOCustomerDO(CustomerDO transferTOCustomerDO) {
		this.transferTOCustomerDO = transferTOCustomerDO;
	}

	/**
	 * Gets the transfer to office do.
	 *
	 * @return the transfer to office do
	 */
	public OfficeDO getTransferTOOfficeDO() {
		return transferTOOfficeDO;
	}

	/**
	 * Sets the transfer to office do.
	 *
	 * @param transferTOOfficeDO the new transfer to office do
	 */
	public void setTransferTOOfficeDO(OfficeDO transferTOOfficeDO) {
		this.transferTOOfficeDO = transferTOOfficeDO;
	}

	

	

	/**
	 * Gets the transfer to emp do.
	 *
	 * @return the transfer to emp do
	 */
	public EmployeeDO getTransferTOEmpDO() {
		return transferTOEmpDO;
	}

	/**
	 * Sets the transfer to emp do.
	 *
	 * @param transferTOEmpDO the new transfer to emp do
	 */
	public void setTransferTOEmpDO(EmployeeDO transferTOEmpDO) {
		this.transferTOEmpDO = transferTOEmpDO;
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

	/**
	 * Gets the created office do.
	 *
	 * @return the created office do
	 */
	public OfficeDO getCreatedOfficeDO() {
		return createdOfficeDO;
	}

	/**
	 * Sets the created office do.
	 *
	 * @param createdOfficeDO the new created office do
	 */
	public void setCreatedOfficeDO(OfficeDO createdOfficeDO) {
		this.createdOfficeDO = createdOfficeDO;
	}

	/**
	 * @return the transferFromBaDO
	 */
	public CustomerDO getTransferFromBaDO() {
		return transferFromBaDO;
	}

	/**
	 * @return the transferTOBaDO
	 */
	public CustomerDO getTransferTOBaDO() {
		return transferTOBaDO;
	}

	/**
	 * @param transferFromBaDO the transferFromBaDO to set
	 */
	public void setTransferFromBaDO(CustomerDO transferFromBaDO) {
		this.transferFromBaDO = transferFromBaDO;
	}

	/**
	 * @param transferTOBaDO the transferTOBaDO to set
	 */
	public void setTransferTOBaDO(CustomerDO transferTOBaDO) {
		this.transferTOBaDO = transferTOBaDO;
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

	
	
	
	
	
	

	
}
