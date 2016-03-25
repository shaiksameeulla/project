package com.cg.lbs.bcun.to;

import java.util.Date;


/**
 * @author bmodala

 * 
 */
public class ReconcillationConfigPropertyTO extends BcunConfigPropertyTO {


	private Date transactionDate;
	private String blobPreparationQuery;
	private String namedQuerydtToCentralUpdate;
	private String blobPreparationDomain;
	private String namedQueryAtbranch;
	private Integer transactionOfficeId;
	private String namedQueryForCentralCnCount;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getBlobPreparationQuery() {
		return blobPreparationQuery;
	}
	public void setBlobPreparationQuery(String blobPreparationQuery) {
		this.blobPreparationQuery = blobPreparationQuery;
	}
	
	
	public String getNamedQuerydtToCentralUpdate() {
		return namedQuerydtToCentralUpdate;
	}
	public void setNamedQuerydtToCentralUpdate(String namedQuerydtToCentralUpdate) {
		this.namedQuerydtToCentralUpdate = namedQuerydtToCentralUpdate;
	}
	public String getBlobPreparationDomain() {
		return blobPreparationDomain;
	}
	public void setBlobPreparationDomain(String blobPreparationDomain) {
		this.blobPreparationDomain = blobPreparationDomain;
	}
	
	public Integer getTransactionOfficeId() {
		return transactionOfficeId;
	}
	public void setTransactionOfficeId(Integer transactionOfficeId) {
		this.transactionOfficeId = transactionOfficeId;
	}
	
	/**
	 * @return the namedQueryAtbranch
	 */
	public String getNamedQueryAtbranch() {
		return namedQueryAtbranch;
	}
	/**
	 * @param namedQueryAtbranch the namedQueryAtbranch to set
	 */
	public void setNamedQueryAtbranch(String namedQueryAtbranch) {
		this.namedQueryAtbranch = namedQueryAtbranch;
	}
	/**
	 * @return the namedQueryForCentralCnCount
	 */
	public String getNamedQueryForCentralCnCount() {
		return namedQueryForCentralCnCount;
	}
	/**
	 * @param namedQueryForCentralCnCount the namedQueryForCentralCnCount to set
	 */
	public void setNamedQueryForCentralCnCount(String namedQueryForCentralCnCount) {
		this.namedQueryForCentralCnCount = namedQueryForCentralCnCount;
	}
}
