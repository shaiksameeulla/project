package com.ff.to.billing;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class BulkCustModificationAliasTO extends CGBaseTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String custCheck;
	String expenseCheck;
	String collectionCheck;
	String liabilityCheck;
	String liabilitySapCheck;
	
	public String getCustCheck() {
		return custCheck;
	}
	public void setCustCheck(String custCheck) {
		this.custCheck = custCheck;
	}
	public String getExpenseCheck() {
		return expenseCheck;
	}
	public void setExpenseCheck(String expenseCheck) {
		this.expenseCheck = expenseCheck;
	}
	public String getCollectionCheck() {
		return collectionCheck;
	}
	public void setCollectionCheck(String collectionCheck) {
		this.collectionCheck = collectionCheck;
	}
	public String getLiabilityCheck() {
		return liabilityCheck;
	}
	public void setLiabilityCheck(String liabilityCheck) {
		this.liabilityCheck = liabilityCheck;
	}
	public String getLiabilitySapCheck() {
		return liabilitySapCheck;
	}
	public void setLiabilitySapCheck(String liabilitySapCheck) {
		this.liabilitySapCheck = liabilitySapCheck;
	}
	

}
