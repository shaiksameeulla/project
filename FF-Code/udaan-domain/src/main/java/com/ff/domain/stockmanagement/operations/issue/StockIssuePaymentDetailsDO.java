package com.ff.domain.stockmanagement.operations.issue;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;

/**
 * The Class StockIssuePaymentDetailsDO.
 *
 * @author hkansagr
 */

public class StockIssuePaymentDetailsDO extends StockCommonBaseDO
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
	
	/** (many-to-one relationship in hbm). */
	
	@JsonBackReference 
	private StockIssueDO stockIssueDO;
	
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
	
	
	/**
	 * Gets the stock issue do.
	 *
	 * @return the stock issue do
	 */
	public StockIssueDO getStockIssueDO() {
		return stockIssueDO;
	}

	/**
	 * Sets the stock issue do.
	 *
	 * @param stockIssueDO the new stock issue do
	 */
	public void setStockIssueDO(StockIssueDO stockIssueDO) {
		this.stockIssueDO = stockIssueDO;
	}
	
	

	/**
	 * Gets the stock payment id.
	 *
	 * @return the stock payment id
	 */
	public Long getStockPaymentId() {
		return stockPaymentId;
	}

	/**
	 * Sets the stock payment id.
	 *
	 * @param stockPaymentId the new stock payment id
	 */
	public void setStockPaymentId(Long stockPaymentId) {
		this.stockPaymentId = stockPaymentId;
	}

	/**
	 * Gets the issue number.
	 *
	 * @return the issue number
	 */
	public String getIssueNumber() {
		return issueNumber;
	}

	/**
	 * Sets the issue number.
	 *
	 * @param issueNumber the new issue number
	 */
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	/**
	 * Gets the issued date.
	 *
	 * @return the issued date
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * Sets the issued date.
	 *
	 * @param issuedDate the new issued date
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	

	/**
	 * Gets the payment mode.
	 *
	 * @return the payment mode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Sets the payment mode.
	 *
	 * @param paymentMode the new payment mode
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the new branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	/**
	 * @return the amountReceived
	 */
	public Double getAmountReceived() {
		return amountReceived;
	}

	/**
	 * @param amountReceived the amountReceived to set
	 */
	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}

	/**
	 * Gets the payment date.
	 *
	 * @return the payment date
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Sets the payment date.
	 *
	 * @param paymentDate the new payment date
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the cheque number.
	 *
	 * @return the cheque number
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * Sets the cheque number.
	 *
	 * @param chequeNumber the new cheque number
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the isProcessed
	 */
	public String getIsProcessed() {
		return isProcessed;
	}

	/**
	 * @param isProcessed the isProcessed to set
	 */
	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
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
	 * @return the totalToPayAmount
	 */
	public Double getTotalToPayAmount() {
		return totalToPayAmount;
	}

	

	/**
	 * @param totalToPayAmount the totalToPayAmount to set
	 */
	public void setTotalToPayAmount(Double totalToPayAmount) {
		this.totalToPayAmount = totalToPayAmount;
	}


	/**
	 * @return the totalTaxPerQuantity
	 */
	public Double getTotalTaxPerQuantity() {
		return totalTaxPerQuantity;
	}

	/**
	 * @param totalTaxPerQuantity the totalTaxPerQuantity to set
	 */
	public void setTotalTaxPerQuantity(Double totalTaxPerQuantity) {
		this.totalTaxPerQuantity = totalTaxPerQuantity;
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

}
