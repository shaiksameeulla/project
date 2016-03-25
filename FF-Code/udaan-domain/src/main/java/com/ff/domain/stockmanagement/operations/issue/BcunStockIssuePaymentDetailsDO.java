package com.ff.domain.stockmanagement.operations.issue;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The Class StockIssuePaymentDetailsDO.
 *
 * @author hkansagr
 */

public class BcunStockIssuePaymentDetailsDO extends CGFactDO
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/* Primary Key */
	/** The stock payment id. */
	private Long stockPaymentId;
	
	/* Issue Number of Transaction */
	/** The issue number. */
	private String issueNumber;
	
	/* Date of issue */
	/** The issued date. */
	private Date issuedDate;
	
	/* ex: cash/DD etc */
	/** The payment mode. */
	private String paymentMode;
	
	/* name of the Branch/office(User entry free text) */
	/** The branch. */
	private String branch;
	
	/* Name of Bank */
	/** The bank name. */
	private String bankName;
	
	/* payment amount */
	private Double amountReceived;
	
	/* payment date */
	/** The payment date. */
	private Date paymentDate;
	
	/* Cheque /dd number */
	/** The cheque number. */
	private String chequeNumber;

	
	/** The is processed. */
	private String isProcessed="N";
	
	/** The service tax. */
	private Double serviceTax;
	
	/** The service tax amount. */
	private Double serviceTaxAmount;
	
	/** The edu cess tax. */
	private Double eduCessTax;
	
	/** The edu cess tax amount. */
	private Double eduCessTaxAmount;
	
	/** The hedu cess tax. */
	private Double heduCessTax;
	
	/** The hedu cess tax amount. */
	private Double heduCessTaxAmount;
	
	/** The state tax. StTax applies only For Jammu &Kashmir */
	private Double stateTax; 
	/** The state tax. surcharge on StTax applies only For Jammu &Kashmir */
	private Double surChrgeStateTax;
	/** The state tax. StTax applies only For Jammu &Kashmir */
	private Double stateTaxAmount;
	/** The state tax. surcharge on StTax Amount applies only For Jammu &Kashmir */
	private Double surChrgeStateTaxAmount;
	
	private Double totalTaxPerQuantity;

	private Double totalToPayAmount;//Total Amount to be paid
	
	@JsonBackReference 
	private BcunStockIssueDO stockIssueDO;

	/**
	 * @return the stockPaymentId
	 */
	public Long getStockPaymentId() {
		return stockPaymentId;
	}

	/**
	 * @return the issueNumber
	 */
	public String getIssueNumber() {
		return issueNumber;
	}

	/**
	 * @return the issuedDate
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return the amountReceived
	 */
	public Double getAmountReceived() {
		return amountReceived;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @return the isProcessed
	 */
	public String getIsProcessed() {
		return isProcessed;
	}

	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @return the serviceTaxAmount
	 */
	public Double getServiceTaxAmount() {
		return serviceTaxAmount;
	}

	/**
	 * @return the eduCessTax
	 */
	public Double getEduCessTax() {
		return eduCessTax;
	}

	/**
	 * @return the eduCessTaxAmount
	 */
	public Double getEduCessTaxAmount() {
		return eduCessTaxAmount;
	}

	/**
	 * @return the heduCessTax
	 */
	public Double getHeduCessTax() {
		return heduCessTax;
	}

	/**
	 * @return the heduCessTaxAmount
	 */
	public Double getHeduCessTaxAmount() {
		return heduCessTaxAmount;
	}

	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}

	/**
	 * @return the surChrgeStateTax
	 */
	public Double getSurChrgeStateTax() {
		return surChrgeStateTax;
	}

	/**
	 * @return the stateTaxAmount
	 */
	public Double getStateTaxAmount() {
		return stateTaxAmount;
	}

	/**
	 * @return the surChrgeStateTaxAmount
	 */
	public Double getSurChrgeStateTaxAmount() {
		return surChrgeStateTaxAmount;
	}

	/**
	 * @return the totalTaxPerQuantity
	 */
	public Double getTotalTaxPerQuantity() {
		return totalTaxPerQuantity;
	}

	/**
	 * @return the totalToPayAmount
	 */
	public Double getTotalToPayAmount() {
		return totalToPayAmount;
	}

	/**
	 * @return the stockIssueDO
	 */
	public BcunStockIssueDO getStockIssueDO() {
		return stockIssueDO;
	}

	/**
	 * @param stockPaymentId the stockPaymentId to set
	 */
	public void setStockPaymentId(Long stockPaymentId) {
		this.stockPaymentId = stockPaymentId;
	}

	/**
	 * @param issueNumber the issueNumber to set
	 */
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @param amountReceived the amountReceived to set
	 */
	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @param chequeNumber the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @param isProcessed the isProcessed to set
	 */
	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @param serviceTaxAmount the serviceTaxAmount to set
	 */
	public void setServiceTaxAmount(Double serviceTaxAmount) {
		this.serviceTaxAmount = serviceTaxAmount;
	}

	/**
	 * @param eduCessTax the eduCessTax to set
	 */
	public void setEduCessTax(Double eduCessTax) {
		this.eduCessTax = eduCessTax;
	}

	/**
	 * @param eduCessTaxAmount the eduCessTaxAmount to set
	 */
	public void setEduCessTaxAmount(Double eduCessTaxAmount) {
		this.eduCessTaxAmount = eduCessTaxAmount;
	}

	/**
	 * @param heduCessTax the heduCessTax to set
	 */
	public void setHeduCessTax(Double heduCessTax) {
		this.heduCessTax = heduCessTax;
	}

	/**
	 * @param heduCessTaxAmount the heduCessTaxAmount to set
	 */
	public void setHeduCessTaxAmount(Double heduCessTaxAmount) {
		this.heduCessTaxAmount = heduCessTaxAmount;
	}

	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}

	/**
	 * @param surChrgeStateTax the surChrgeStateTax to set
	 */
	public void setSurChrgeStateTax(Double surChrgeStateTax) {
		this.surChrgeStateTax = surChrgeStateTax;
	}

	/**
	 * @param stateTaxAmount the stateTaxAmount to set
	 */
	public void setStateTaxAmount(Double stateTaxAmount) {
		this.stateTaxAmount = stateTaxAmount;
	}

	/**
	 * @param surChrgeStateTaxAmount the surChrgeStateTaxAmount to set
	 */
	public void setSurChrgeStateTaxAmount(Double surChrgeStateTaxAmount) {
		this.surChrgeStateTaxAmount = surChrgeStateTaxAmount;
	}

	/**
	 * @param totalTaxPerQuantity the totalTaxPerQuantity to set
	 */
	public void setTotalTaxPerQuantity(Double totalTaxPerQuantity) {
		this.totalTaxPerQuantity = totalTaxPerQuantity;
	}

	/**
	 * @param totalToPayAmount the totalToPayAmount to set
	 */
	public void setTotalToPayAmount(Double totalToPayAmount) {
		this.totalToPayAmount = totalToPayAmount;
	}

	/**
	 * @param stockIssueDO the stockIssueDO to set
	 */
	public void setStockIssueDO(BcunStockIssueDO stockIssueDO) {
		this.stockIssueDO = stockIssueDO;
	}

	

}
