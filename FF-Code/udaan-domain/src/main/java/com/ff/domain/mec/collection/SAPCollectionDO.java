/**
 * 
 */
package com.ff.domain.mec.collection;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author CBHURE
 *
 */

public class SAPCollectionDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6099762538290589176L;

	/**
	 * 
	 */

	private Long Id;
	
	private String custCode;
	private String txNumber;
	private Date collectionDate;
	private String modeOfPayment;
	private String bankGLCode;
	private Date chequeDate;
	private String chequeNo;
	private String bankName;
	private String consgNo;
	private String collectionAgainst;
	private String billNo;
	private Double receivedAmt;
	private Double tdsAmt;
	private Double billedAmt;
	private String remarks;
	private String officeCode;
	private Double deduction;
	private String reasonCode;
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
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	/**
	 * @param modeOfPayment the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
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
	 * @return the collectionAgainst
	 */
	public String getCollectionAgainst() {
		return collectionAgainst;
	}
	/**
	 * @param collectionAgainst the collectionAgainst to set
	 */
	public void setCollectionAgainst(String collectionAgainst) {
		this.collectionAgainst = collectionAgainst;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the receivedAmt
	 */
	public Double getReceivedAmt() {
		return receivedAmt;
	}
	/**
	 * @param receivedAmt the receivedAmt to set
	 */
	public void setReceivedAmt(Double receivedAmt) {
		this.receivedAmt = receivedAmt;
	}
	/**
	 * @return the tdsAmt
	 */
	public Double getTdsAmt() {
		return tdsAmt;
	}
	/**
	 * @param tdsAmt the tdsAmt to set
	 */
	public void setTdsAmt(Double tdsAmt) {
		this.tdsAmt = tdsAmt;
	}
	/**
	 * @return the billedAmt
	 */
	public Double getBilledAmt() {
		return billedAmt;
	}
	/**
	 * @param billedAmt the billedAmt to set
	 */
	public void setBilledAmt(Double billedAmt) {
		this.billedAmt = billedAmt;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the collectionDate
	 */
	public Date getCollectionDate() {
		return collectionDate;
	}
	/**
	 * @param collectionDate the collectionDate to set
	 */
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
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
	 * @return the deduction
	 */
	public Double getDeduction() {
		return deduction;
	}
	/**
	 * @param deduction the deduction to set
	 */
	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}
	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
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
