/**
 * 
 */
package com.capgemini.lbs.framework.domain;

import java.util.Date;


/**
 * @author mohammes
 *Use :Each Fact( Transaction ) Entity(class) must extend CGMasterDO class
 */
public  class CGFactDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9022364492709287655L;
	
	public Integer userId;
	private Integer updatedBy;
	private Integer originOfficeId;
	private Integer destOfficeId;
	private Integer createdBy;
	
	private Date transactionCreateDate ;
	private Date transactionModifiedDate;
	private Date processDateTime;
	private Date createdDate;
	private Date updatedDate;
	
	/**
	 * 
	 * N = New (By default status will be set as New (N))
	 * C= Complete (When data successfully sent to SAP System it will be updated as C) 
	 * */
	
	private String sapStatus = "N"; 
	
	/**
	 * 
	 * By default status will be set as Null
	 * S = SAP  (When data will be inserted into CSD database from SAP) 
	 * */
	private String sapStatusInBound;
	
	/**
	 * to stamp sap transaction date and time
	 * */
	private Date sapTimestamp;
	
	
	
	private String nodeId;
	private String processNumber;
	
	private String centralInboudStatus;
	private String centralOutboudStatus;
	//for Data Transfer status to OPSMAAN
	private String legacyStatus;
	



	
	public String getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getProcessNumber() {
		return processNumber;
	}
	
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	
	
	
	
	/**
	 * @return
	 */
	public String getCentralInboudStatus() {
		return centralInboudStatus;
	}
	/**
	 * @param centralInboudStatus
	 */
	public void setCentralInboudStatus(String centralInboudStatus) {
		this.centralInboudStatus = centralInboudStatus;
	}
	/**
	 * @return
	 */
	public String getCentralOutboudStatus() {
		return centralOutboudStatus;
	}
	/**
	 * @param centralOutboudStatus
	 */
	public void setCentralOutboudStatus(String centralOutboudStatus) {
		this.centralOutboudStatus = centralOutboudStatus;
	}
	
	public String getLegacyStatus() {
		return legacyStatus;
	}
	
	public void setLegacyStatus(String legacyStatus) {
		this.legacyStatus = legacyStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getOriginOfficeId() {
		return originOfficeId;
	}

	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
	}

	public Integer getDestOfficeId() {
		return destOfficeId;
	}

	public void setDestOfficeId(Integer destOfficeId) {
		this.destOfficeId = destOfficeId;
	}


	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}

	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

	public Date getTransactionModifiedDate() {
		return transactionModifiedDate;
	}

	public void setTransactionModifiedDate(Date transactionModifiedDate) {
		this.transactionModifiedDate = transactionModifiedDate;
	}

	public Date getProcessDateTime() {
		return processDateTime;
	}

	public void setProcessDateTime(Date processDateTime) {
		this.processDateTime = processDateTime;
	}

	public String getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	public String getSapStatusInBound() {
		return sapStatusInBound;
	}

	public void setSapStatusInBound(String sapStatusInBound) {
		this.sapStatusInBound = sapStatusInBound;
	}

	public Date getSapTimestamp() {
		return sapTimestamp;
	}

	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	


}
