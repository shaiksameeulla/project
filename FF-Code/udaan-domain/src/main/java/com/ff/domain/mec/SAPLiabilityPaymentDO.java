package com.ff.domain.mec;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author CBHURE
 *
 */

public class SAPLiabilityPaymentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5117663373112211609L;
	
	/** primary key. */
	private Integer Id;
	private String custCode;
	private String txNumber;
	private Date creationDate;
	private Date chequeDate;
	private String chequeNo;
	private String chequeBankName;
	private String bankGLCode;
	private Double amount;
	private String regionCode;
	private String exception;
	
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	 * @return the chequeBankName
	 */
	public String getChequeBankName() {
		return chequeBankName;
	}
	/**
	 * @param chequeBankName the chequeBankName to set
	 */
	public void setChequeBankName(String chequeBankName) {
		this.chequeBankName = chequeBankName;
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
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}
