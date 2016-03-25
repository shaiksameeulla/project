package com.ff.domain.mec.expense;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;



/**
 * @author CBHURE
 *
 */

public class SAPExpenseDO extends CGFactDO {
	
	private static final long serialVersionUID = 1L;
	
	/* primary key */
	private Long Id;
	
	/* The transaction number */
	private String officeCode;
	private String txNumber;
	private String expenseGLCode;
	private String paymentCode;
	private String bankGLCode;
	private Double totalExpense;
	private Date chequeDate;
	private String chequeNo;
	private String bankName;
	private Date postingDate;
	private String reportingRhoCode;
	private String destinationRHO;
	private Double serviceChanrge;
	private Double serviceTaxBasic;
	private Double edOnServiceTax;
	private Double hedOnServiceTax;
	private String consgNo;
	private String empCode;
	private String remark;
	private String exception;
	// Added glIndicator
	private String glIndicator;
	
	
	

	/**
	 * @return the glIndicator
	 */
	public String getGlIndicator() {
		return glIndicator;
	}

	/**
	 * @param glIndicator the glIndicator to set
	 */
	public void setGlIndicator(String glIndicator) {
		this.glIndicator = glIndicator;
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

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @return the txNumber
	 */
	public String getTxNumber() {
		return txNumber;
	}

	/**
	 * @param txNumber the txNumber to set
	 */
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	/**
	 * @return the expenseGLCode
	 */
	public String getExpenseGLCode() {
		return expenseGLCode;
	}

	/**
	 * @param expenseGLCode the expenseGLCode to set
	 */
	public void setExpenseGLCode(String expenseGLCode) {
		this.expenseGLCode = expenseGLCode;
	}

	/**
	 * @return the paymentCode
	 */
	public String getPaymentCode() {
		return paymentCode;
	}

	/**
	 * @param paymentCode the paymentCode to set
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	/**
	 * @return the bankGLCode
	 */
	public String getBankGLCode() {
		return bankGLCode;
	}

	/**
	 * @param bankGLCode the bankGLCode to set
	 */
	public void setBankGLCode(String bankGLCode) {
		this.bankGLCode = bankGLCode;
	}

	/**
	 * @return the totalExpense
	 */
	public Double getTotalExpense() {
		return totalExpense;
	}

	/**
	 * @param totalExpense the totalExpense to set
	 */
	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	/**
	 * @return the chequeDate
	 */
	public Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}

	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the postingDate
	 */
	public Date getPostingDate() {
		return postingDate;
	}

	/**
	 * @param postingDate the postingDate to set
	 */
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the reportingRhoCode
	 */
	public String getReportingRhoCode() {
		return reportingRhoCode;
	}

	/**
	 * @param reportingRhoCode the reportingRhoCode to set
	 */
	public void setReportingRhoCode(String reportingRhoCode) {
		this.reportingRhoCode = reportingRhoCode;
	}

	/**
	 * @return the destinationRHO
	 */
	public String getDestinationRHO() {
		return destinationRHO;
	}

	/**
	 * @param destinationRHO the destinationRHO to set
	 */
	public void setDestinationRHO(String destinationRHO) {
		this.destinationRHO = destinationRHO;
	}

	/**
	 * @return the serviceChanrge
	 */
	public Double getServiceChanrge() {
		return serviceChanrge;
	}

	/**
	 * @param serviceChanrge the serviceChanrge to set
	 */
	public void setServiceChanrge(Double serviceChanrge) {
		this.serviceChanrge = serviceChanrge;
	}

	/**
	 * @return the serviceTaxBasic
	 */
	public Double getServiceTaxBasic() {
		return serviceTaxBasic;
	}

	/**
	 * @param serviceTaxBasic the serviceTaxBasic to set
	 */
	public void setServiceTaxBasic(Double serviceTaxBasic) {
		this.serviceTaxBasic = serviceTaxBasic;
	}

	/**
	 * @return the edOnServiceTax
	 */
	public Double getEdOnServiceTax() {
		return edOnServiceTax;
	}

	/**
	 * @param edOnServiceTax the edOnServiceTax to set
	 */
	public void setEdOnServiceTax(Double edOnServiceTax) {
		this.edOnServiceTax = edOnServiceTax;
	}

	/**
	 * @return the hedOnServiceTax
	 */
	public Double getHedOnServiceTax() {
		return hedOnServiceTax;
	}

	/**
	 * @param hedOnServiceTax the hedOnServiceTax to set
	 */
	public void setHedOnServiceTax(Double hedOnServiceTax) {
		this.hedOnServiceTax = hedOnServiceTax;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	

}
