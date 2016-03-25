package com.ff.to.mec.expense;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class ValidateExpenseDetailTO extends CGBaseTO{
	
	private static final long serialVersionUID = 1L;
	
	private Long expenseId;/* hidden */
	private String txDate;
	private String txNumber;
	private String expenseFor;/* hidden */
	private String expenseForDesc;
	private Integer expenseTypeId;/* hidden */
	private String expenseType;
	private Double amount;
	private String expenseStatus;/* hidden i.e S/V */
	private String status;/* description : SUBMITTED/VALIDATED */
	private Integer expenseOfficeId;/* hidden */
	private Integer expenseRegionId;/* hidden */
	
		
	/**
	 * @return the expenseRegionId
	 */
	public Integer getExpenseRegionId() {
		return expenseRegionId;
	}
	/**
	 * @param expenseRegionId the expenseRegionId to set
	 */
	public void setExpenseRegionId(Integer expenseRegionId) {
		this.expenseRegionId = expenseRegionId;
	}
	/**
	 * @return the expenseOfficeId
	 */
	public Integer getExpenseOfficeId() {
		return expenseOfficeId;
	}
	/**
	 * @param expenseOfficeId the expenseOfficeId to set
	 */
	public void setExpenseOfficeId(Integer expenseOfficeId) {
		this.expenseOfficeId = expenseOfficeId;
	}
	/**
	 * @return the expenseForDesc
	 */
	public String getExpenseForDesc() {
		return expenseForDesc;
	}
	/**
	 * @param expenseForDesc the expenseForDesc to set
	 */
	public void setExpenseForDesc(String expenseForDesc) {
		this.expenseForDesc = expenseForDesc;
	}
	/**
	 * @return the expenseId
	 */
	public Long getExpenseId() {
		return expenseId;
	}
	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	/**
	 * @return the txDate
	 */
	public String getTxDate() {
		return txDate;
	}
	/**
	 * @param txDate the txDate to set
	 */
	public void setTxDate(String txDate) {
		this.txDate = txDate;
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
	 * @return the expenseFor
	 */
	public String getExpenseFor() {
		return expenseFor;
	}
	/**
	 * @param expenseFor the expenseFor to set
	 */
	public void setExpenseFor(String expenseFor) {
		this.expenseFor = expenseFor;
	}
	/**
	 * @return the expenseTypeId
	 */
	public Integer getExpenseTypeId() {
		return expenseTypeId;
	}
	/**
	 * @param expenseTypeId the expenseTypeId to set
	 */
	public void setExpenseTypeId(Integer expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	/**
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}
	/**
	 * @param expenseType the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
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
	 * @return the expenseStatus
	 */
	public String getExpenseStatus() {
		return expenseStatus;
	}
	/**
	 * @param expenseStatus the expenseStatus to set
	 */
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
