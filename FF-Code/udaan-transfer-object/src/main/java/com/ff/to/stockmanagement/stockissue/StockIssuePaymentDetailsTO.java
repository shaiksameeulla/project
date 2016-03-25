package com.ff.to.stockmanagement.stockissue;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class StockIssuePaymentDetailsTO extends CGBaseTO
{
	private static final long serialVersionUID = 1L;
	
	private Long stockPaymentId;
	private String paymentDateStr;			
	private String chequeNumber;
	private String bankName;
	private String branch;
	
	private Double amountReceived;
	private String issueNumber;
	private String issuedDateStr;
	private String paymentMode;
	private Date paymentDate;
	
	/* Date of issue */
	private Date issuedDate;
	
	private String isForPanIndia;
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
	
	private Double totalTaxPerQuantityPerRupe;
	private Double totalToPayAmount;//Total Amount to be paid
	
	private Double totalTaxApplied;//For Print
	private Double totalTaxAmountApplied;//For print
	
	private Double totalAmountBeforeTax;//For Print
	
	private String totalAmountInWords;//For print
	
	public Long getStockPaymentId() {
		return stockPaymentId;
	}
	public void setStockPaymentId(Long stockPaymentId) {
		this.stockPaymentId = stockPaymentId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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
	public String getIssuedDateStr() {
		return issuedDateStr;
	}
	public void setIssuedDateStr(String issuedDateStr) {
		this.issuedDateStr = issuedDateStr;
	}
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Date getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getPaymentDateStr() {
		return paymentDateStr;
	}
	/**
	 * @return the isForPanIndia
	 */
	public String getIsForPanIndia() {
		return isForPanIndia;
	}
	/**
	 * @param isForPanIndia the isForPanIndia to set
	 */
	public void setIsForPanIndia(String isForPanIndia) {
		this.isForPanIndia = isForPanIndia;
	}
	public void setPaymentDateStr(String paymentDateStr) {
		this.paymentDateStr = paymentDateStr;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	/**
	
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
	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}
	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}
	public Double getTotalAmountBeforeTax() {
		return totalAmountBeforeTax;
	}
	public void setTotalAmountBeforeTax(Double totalAmountBeforeTax) {
		this.totalAmountBeforeTax = totalAmountBeforeTax;
	}
	public Double getTotalTaxAmountApplied() {
		return totalTaxAmountApplied;
	}
	public void setTotalTaxAmountApplied(Double totalTaxAmountApplied) {
		this.totalTaxAmountApplied = totalTaxAmountApplied;
	}
	/**
	 * @return the heduCessTax
	 */
	public Double getHeduCessTax() {
		return heduCessTax;
	}
	
	public Double getTotalTaxApplied() {
		return totalTaxApplied;
	}
	public void setTotalTaxApplied(Double totalTaxApplied) {
		this.totalTaxApplied = totalTaxApplied;
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
	 * @return the totalTaxPerQuantityPerRupe
	 */
	public Double getTotalTaxPerQuantityPerRupe() {
		return totalTaxPerQuantityPerRupe;
	}
	/**
	 * @param totalTaxPerQuantityPerRupe the totalTaxPerQuantityPerRupe to set
	 */
	public void setTotalTaxPerQuantityPerRupe(Double totalTaxPerQuantityPerRupe) {
		this.totalTaxPerQuantityPerRupe = totalTaxPerQuantityPerRupe;
	}
	/**
	 * @param surChrgeStateTaxAmount the surChrgeStateTaxAmount to set
	 */
	public void setSurChrgeStateTaxAmount(Double surChrgeStateTaxAmount) {
		this.surChrgeStateTaxAmount = surChrgeStateTaxAmount;
	}
	
	
	
}
