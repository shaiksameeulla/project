package com.ff.domain.bcun.reconcillation;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;

/**
 * @author bmodala
 *
 */
public class BcunReconcillationDO extends CGBaseDO {	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559319983181012986L;

	private Long dataReconcillationId;
	private Integer transactionOfficeId;
	private Date transactionDate;
	private String processName;
	private BigInteger branchCNCount;
	private BigInteger centralCNCount;
	private Date centralLastProcessedDate=Calendar.getInstance().getTime();
	private String isCountMatched="N";
	private Date branchLastRequestInDate;
	
	public Long getDataReconcillationId() {
		return dataReconcillationId;
	}
	public void setDataReconcillationId(Long dataReconcillationId) {
		this.dataReconcillationId = dataReconcillationId;
	}
	
	public Integer getTransactionOfficeId() {
		return transactionOfficeId;
	}
	public void setTransactionOfficeId(Integer transactionOfficeId) {
		this.transactionOfficeId = transactionOfficeId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		Calendar date=null;
		if(transactionDate!=null){
			date=Calendar.getInstance();
			date.setTime(transactionDate);
			this.transactionDate = date.getTime();
		}
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public BigInteger getBranchCNCount() {
		return branchCNCount;
	}
	public void setBranchCNCount(BigInteger branchCNCount) {
		this.branchCNCount = branchCNCount;
	}
	public BigInteger getCentralCNCount() {
		return centralCNCount;
	}
	public void setCentralCNCount(BigInteger centralCNCount) {
		this.centralCNCount = centralCNCount;
	}
	public Date getCentralLastProcessedDate() {
		return centralLastProcessedDate;
	}
	public void setCentralLastProcessedDate(Date centralLastProcessedDate) {
		this.centralLastProcessedDate = centralLastProcessedDate;
	}
	public String getIsCountMatched() {
		return isCountMatched;
	}
	public void setIsCountMatched(String isCountMatched) {
		this.isCountMatched = isCountMatched;
	}
	public Date getBranchLastRequestInDate() {
		return branchLastRequestInDate;
	}
	public void setBranchLastRequestInDate(Date branchLastRequestInDate) {
		this.branchLastRequestInDate = branchLastRequestInDate;
	}	
}
