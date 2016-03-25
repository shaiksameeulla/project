package com.ff.domain.mec.expense;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunExpenseDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/* primary key */
	private Long expenseId;

	/* The transaction number */
	private String txNumber;

	/* The posting/current date */
	private Date postingDate;

	/* The document date */
	private Date documentDate;

	/* The expense for i.e O=Office, E=EMPLOYEE, C=CONSIGNMENT */
	private String expenseFor;

	/* The type of expense */
	private Integer typeOfExpense;

	/* The mode of expense i.e. CASH or CHAQUE or BOTH */
	private Integer modeOfExpense;

	/* The cheque no. */
	private String chequeNo;

	/* The cheque date */
	private Date chequeDate;

	/* FK with GLMasterDO i.e. ff_d_gl_master */
	private Integer bankDO;

	/* The bank branch name */
	private String branchName;

	/* Total expense */
	private Double totalExpense;

	/* The expense office Id */
	private Integer expenseOfficeId;

	/* The expenseOfficeRho. */
	private Integer expenseOfficeRho;

	/* Status for tx number */
	private String status;

	/* The Remarks for header - expense type - office */
	private String remarks;

	Set<BcunExpenseEntriesDO> expenseEntries;

	/**
	 * @return the expenseId
	 */
	public Long getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId
	 *            the expenseId to set
	 */
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the txNumber
	 */
	public String getTxNumber() {
		return txNumber;
	}

	/**
	 * @param txNumber
	 *            the txNumber to set
	 */
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	/**
	 * @return the postingDate
	 */
	public Date getPostingDate() {
		return postingDate;
	}

	/**
	 * @param postingDate
	 *            the postingDate to set
	 */
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	/**
	 * @return the documentDate
	 */
	public Date getDocumentDate() {
		return documentDate;
	}

	/**
	 * @param documentDate
	 *            the documentDate to set
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	/**
	 * @return the expenseFor
	 */
	public String getExpenseFor() {
		return expenseFor;
	}

	/**
	 * @param expenseFor
	 *            the expenseFor to set
	 */
	public void setExpenseFor(String expenseFor) {
		this.expenseFor = expenseFor;
	}

	/**
	 * @return the typeOfExpense
	 */
	public Integer getTypeOfExpense() {
		return typeOfExpense;
	}

	/**
	 * @param typeOfExpense
	 *            the typeOfExpense to set
	 */
	public void setTypeOfExpense(Integer typeOfExpense) {
		this.typeOfExpense = typeOfExpense;
	}

	/**
	 * @return the modeOfExpense
	 */
	public Integer getModeOfExpense() {
		return modeOfExpense;
	}

	/**
	 * @param modeOfExpense
	 *            the modeOfExpense to set
	 */
	public void setModeOfExpense(Integer modeOfExpense) {
		this.modeOfExpense = modeOfExpense;
	}

	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}

	/**
	 * @param chequeNo
	 *            the chequeNo to set
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	/**
	 * @return the chequeDate
	 */
	public Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate
	 *            the chequeDate to set
	 */
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the bankDO
	 */
	public Integer getBankDO() {
		return bankDO;
	}

	/**
	 * @param bankDO
	 *            the bankDO to set
	 */
	public void setBankDO(Integer bankDO) {
		this.bankDO = bankDO;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the totalExpense
	 */
	public Double getTotalExpense() {
		return totalExpense;
	}

	/**
	 * @param totalExpense
	 *            the totalExpense to set
	 */
	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	/**
	 * @return the expenseOfficeId
	 */
	public Integer getExpenseOfficeId() {
		return expenseOfficeId;
	}

	/**
	 * @param expenseOfficeId
	 *            the expenseOfficeId to set
	 */
	public void setExpenseOfficeId(Integer expenseOfficeId) {
		this.expenseOfficeId = expenseOfficeId;
	}

	/**
	 * @return the expenseOfficeRho
	 */
	public Integer getExpenseOfficeRho() {
		return expenseOfficeRho;
	}

	/**
	 * @param expenseOfficeRho
	 *            the expenseOfficeRho to set
	 */
	public void setExpenseOfficeRho(Integer expenseOfficeRho) {
		this.expenseOfficeRho = expenseOfficeRho;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the expenseEntries
	 */
	public Set<BcunExpenseEntriesDO> getExpenseEntries() {
		return expenseEntries;
	}

	/**
	 * @param expenseEntries
	 *            the expenseEntries to set
	 */
	public void setExpenseEntries(Set<BcunExpenseEntriesDO> expenseEntries) {
		this.expenseEntries = expenseEntries;
	}

}
