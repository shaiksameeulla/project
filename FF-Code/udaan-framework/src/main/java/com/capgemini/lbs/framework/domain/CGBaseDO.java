/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class CGBaseDO.
 */
@SuppressWarnings("serial")
public abstract class CGBaseDO implements Serializable {
	
	private String dtToBranch="N";
	private String dtToCentral="N";
	private String dtUpdateToCentral="N";
	private String dtFromOpsman="N";
	private String dtToOpsman="N";
	@JsonIgnore
	private String mandatoryFlag="N";
	
	@JsonIgnore
	private boolean notificationRequired;
	
	private String transactionStatus="A";
	
	private String customerNo;
	private String exception;

	/**
	 * @return the dtToBranch
	 */
	public String getDtToBranch() {
		return dtToBranch;
	}
	/**
	 * @param dtToBranch the dtToBranch to set
	 */
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	/**
	 * @return the dtToCentral
	 */
	public String getDtToCentral() {
		return dtToCentral;
	}
	/**
	 * @param dtToCentral the dtToCentral to set
	 */
	public void setDtToCentral(String dtToCentral) {
		this.dtToCentral = dtToCentral;
	}
	/**
	 * @return the dtUpdateToCentral
	 */
	public String getDtUpdateToCentral() {
		return dtUpdateToCentral;
	}
	/**
	 * @param dtUpdateToCentral the dtUpdateToCentral to set
	 */
	public void setDtUpdateToCentral(String dtUpdateToCentral) {
		this.dtUpdateToCentral = dtUpdateToCentral;
	}
	/**
	 * @return the dtFromOpsman
	 */
	public String getDtFromOpsman() {
		return dtFromOpsman;
	}
	/**
	 * @param dtFromOpsman the dtFromOpsman to set
	 */
	public void setDtFromOpsman(String dtFromOpsman) {
		this.dtFromOpsman = dtFromOpsman;
	}
	/**
	 * @return the dtToOpsman
	 */
	public String getDtToOpsman() {
		return dtToOpsman;
	}
	/**
	 * @param dtToOpsman the dtToOpsman to set
	 */
	public void setDtToOpsman(String dtToOpsman) {
		this.dtToOpsman = dtToOpsman;
	}
	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return the mandatoryFlag
	 */
	public String getMandatoryFlag() {
		return mandatoryFlag;
	}
	/**
	 * @param mandatoryFlag the mandatoryFlag to set
	 */
	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
	public boolean isNotificationRequired() {
		return notificationRequired;
	}
	public void setNotificationRequired(boolean notificationRequired) {
		this.notificationRequired = notificationRequired;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}	


}
