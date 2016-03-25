package com.ff.domain.stockmanagement.operations.issue;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;
import com.ff.domain.umc.UserDO;

/**
 * The Class StockIssueDO.
 *
 * @author hkansagr
 */

public class StockIssueDO extends StockCommonBaseDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key for stock issue table. */
	private Long stockIssueId;
	
	/** unique stock requisition number EX: PR + OfficeCODE(4) + RunningSerialNumber(5). */
	private String requisitionNumber;
	
	/** unique issue transaction number. */
	private String stockIssueNumber;
	
	/** date. */
	private Date stockIssueDate;
	
	
	
	/** logged in office of issue. */
	private OfficeDO issueOfficeDO;
	
	/** type of receivers like EMP/BA/FR. */
	private String issuedToType;
	
	/** office  of receiver if receiver is Branch. */
	private OfficeDO issuedToOffice;
	
	/** BA Id of Receiver if receiver is BA (Many-to-one relation ship in hbm). */
	private CustomerDO issuedToBA;
	
	/** FR id if receiver is FR (Many-to-one relation ship in hbm). */
	private CustomerDO issuedToFranchisee;
	
	/** Pick boy/Employee if receiver is EMP (Many-to-one relation ship in hbm). */
	private EmployeeDO issuedToPickupBoy;
	
	/** Customer id if receiver is Customer (Many-to-one relation ship in hbm). */
	private CustomerDO issuedToCustomer;
	
	/** record created user (common attribute) (Many-to-one relation ship in hbm). */
	private UserDO createdByUser;
	
	/** record updated user (common attribute) (Many-to-one relation ship in hbm). */
	private UserDO updatedByUser;
	
	/** Non-persistent variable for Issue Against Requisition. */
	private String transactionFromType;
	
	private Double totalAmountBeforeTax;
	
	private String shippedToCode;
	
	
	/** (one-to-many relation ship in hbm). */
	@JsonManagedReference
	Set<StockIssueItemDtlsDO> issueItemDtlsDO;
	
	/** (one-to-one relation ship in hbm). */
	@JsonManagedReference
	private StockIssuePaymentDetailsDO issuePaymentDtlsDO;

	/**
	 * Gets the created by user.
	 *
	 * @return the created by user
	 */
	public UserDO getCreatedByUser() {
		return createdByUser;
	}
	
	/**
	 * Sets the created by user.
	 *
	 * @param createdByUser the new created by user
	 */
	public void setCreatedByUser(UserDO createdByUser) {
		this.createdByUser = createdByUser;
	}
	
	/**
	 * Gets the updated by user.
	 *
	 * @return the updated by user
	 */
	public UserDO getUpdatedByUser() {
		return updatedByUser;
	}
	
	/**
	 * Sets the updated by user.
	 *
	 * @param updatedByUser the new updated by user
	 */
	public void setUpdatedByUser(UserDO updatedByUser) {
		this.updatedByUser = updatedByUser;
	}
	
	/**
	 * Gets the issued to type.
	 *
	 * @return the issued to type
	 */
	public String getIssuedToType() {
		return issuedToType;
	}
	
	/**
	 * Sets the issued to type.
	 *
	 * @param issuedToType the new issued to type
	 */
	public void setIssuedToType(String issuedToType) {
		this.issuedToType = issuedToType;
	}
	
	/**
	 * Gets the issued to office.
	 *
	 * @return the issued to office
	 */
	public OfficeDO getIssuedToOffice() {
		return issuedToOffice;
	}
	
	/**
	 * Sets the issued to office.
	 *
	 * @param issuedToOffice the new issued to office
	 */
	public void setIssuedToOffice(OfficeDO issuedToOffice) {
		this.issuedToOffice = issuedToOffice;
	}
	
	
	
	
	

	
	/**
	 * Gets the issued to pickup boy.
	 *
	 * @return the issued to pickup boy
	 */
	public EmployeeDO getIssuedToPickupBoy() {
		return issuedToPickupBoy;
	}
	
	/**
	 * Sets the issued to pickup boy.
	 *
	 * @param issuedToPickupBoy the new issued to pickup boy
	 */
	public void setIssuedToPickupBoy(EmployeeDO issuedToPickupBoy) {
		this.issuedToPickupBoy = issuedToPickupBoy;
	}
	
	/**
	 * Gets the issued to customer.
	 *
	 * @return the issued to customer
	 */
	public CustomerDO getIssuedToCustomer() {
		return issuedToCustomer;
	}
	
	/**
	 * Sets the issued to customer.
	 *
	 * @param issuedToCustomer the new issued to customer
	 */
	public void setIssuedToCustomer(CustomerDO issuedToCustomer) {
		this.issuedToCustomer = issuedToCustomer;
	}
	
	/**
	 * Gets the issue item dtls do.
	 *
	 * @return the issue item dtls do
	 */
	public Set<StockIssueItemDtlsDO> getIssueItemDtlsDO() {
		return issueItemDtlsDO;
	}
	
	/**
	 * Sets the issue item dtls do.
	 *
	 * @param issueItemDtlsDO the new issue item dtls do
	 */
	public void setIssueItemDtlsDO(Set<StockIssueItemDtlsDO> issueItemDtlsDO) {
		this.issueItemDtlsDO = issueItemDtlsDO;
	}
	
	/**
	 * Gets the stock issue id.
	 *
	 * @return the stock issue id
	 */
	public Long getStockIssueId() {
		return stockIssueId;
	}
	
	/**
	 * Sets the stock issue id.
	 *
	 * @param stockIssueId the new stock issue id
	 */
	public void setStockIssueId(Long stockIssueId) {
		this.stockIssueId = stockIssueId;
	}
	
	/**
	 * Gets the requisition number.
	 *
	 * @return the requisition number
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	
	/**
	 * Sets the requisition number.
	 *
	 * @param requisitionNumber the new requisition number
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
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
	 * Gets the stock issue date.
	 *
	 * @return the stock issue date
	 */
	public Date getStockIssueDate() {
		return stockIssueDate;
	}
	
	/**
	 * Sets the stock issue date.
	 *
	 * @param stockIssueDate the new stock issue date
	 */
	public void setStockIssueDate(Date stockIssueDate) {
		this.stockIssueDate = stockIssueDate;
	}
	
	/**
	 * Gets the issue office do.
	 *
	 * @return the issue office do
	 */
	public OfficeDO getIssueOfficeDO() {
		return issueOfficeDO;
	}
	
	/**
	 * Sets the issue office do.
	 *
	 * @param issueOfficeDO the new issue office do
	 */
	public void setIssueOfficeDO(OfficeDO issueOfficeDO) {
		this.issueOfficeDO = issueOfficeDO;
	}
	
	/**
	 * Gets the issue payment dtls do.
	 *
	 * @return the issue payment dtls do
	 */
	public StockIssuePaymentDetailsDO getIssuePaymentDtlsDO() {
		return issuePaymentDtlsDO;
	}
	
	/**
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @return the totalAmountBeforeTax
	 */
	public Double getTotalAmountBeforeTax() {
		return totalAmountBeforeTax;
	}

	/**
	 * @param totalAmountBeforeTax the totalAmountBeforeTax to set
	 */
	public void setTotalAmountBeforeTax(Double totalAmountBeforeTax) {
		this.totalAmountBeforeTax = totalAmountBeforeTax;
	}

	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	/**
	 * Sets the issue payment dtls do.
	 *
	 * @param issuePaymentDtlsDO the new issue payment dtls do
	 */
	public void setIssuePaymentDtlsDO(StockIssuePaymentDetailsDO issuePaymentDtlsDO) {
		this.issuePaymentDtlsDO = issuePaymentDtlsDO;
	}
	
	/**
	 * Gets the transaction from type.
	 *
	 * @return the transactionFromType
	 */
	public String getTransactionFromType() {
		return transactionFromType;
	}
	
	/**
	 * Sets the transaction from type.
	 *
	 * @param transactionFromType the transactionFromType to set
	 */
	public void setTransactionFromType(String transactionFromType) {
		this.transactionFromType = transactionFromType;
	}

	/**
	 * @return the issuedToBA
	 */
	public CustomerDO getIssuedToBA() {
		return issuedToBA;
	}

	/**
	 * @return the issuedToFranchisee
	 */
	public CustomerDO getIssuedToFranchisee() {
		return issuedToFranchisee;
	}

	/**
	 * @param issuedToBA the issuedToBA to set
	 */
	public void setIssuedToBA(CustomerDO issuedToBA) {
		this.issuedToBA = issuedToBA;
	}

	/**
	 * @param issuedToFranchisee the issuedToFranchisee to set
	 */
	public void setIssuedToFranchisee(CustomerDO issuedToFranchisee) {
		this.issuedToFranchisee = issuedToFranchisee;
	}

}
