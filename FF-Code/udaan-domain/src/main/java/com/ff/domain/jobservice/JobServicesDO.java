package com.ff.domain.jobservice;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class JobServicesDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2934964261564716488L;
	
	private Integer jobId;
	private String jobNumber;
	private String processCode;
	private Integer officeId;
	private Date fileSubmissionDate;
	private Integer priority;
	private String jobStatus;
	private String remarks;
	private Integer creatdBy;
	private Date createdDate;
	private Integer updateBy;
	private Date updateDate;
	private Integer percentageCompleted;
	private byte[] failureFile; // changed variable name from responseFile to  failureFile
	private byte[] successFile;
	private String fileNameSuccess;
	private String fileNameFailure;
	
	
	
	
	/**
	 * @return the successFile
	 */
	public byte[] getSuccessFile() {
		return successFile;
	}
	/**
	 * @param successFile the successFile to set
	 */
	public void setSuccessFile(byte[] successFile) {
		this.successFile = successFile;
	}
	/**
	 * @return the jobId
	 */
	public Integer getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the jobNumber
	 */
	public String getJobNumber() {
		return jobNumber;
	}
	/**
	 * @param jobNumber the jobNumber to set
	 */
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	/**
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}
	/**
	 * @param processCode the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	
	/**
	 * @return the fileSubmissionDate
	 */
	public Date getFileSubmissionDate() {
		return fileSubmissionDate;
	}
	/**
	 * @param fileSubmissionDate the fileSubmissionDate to set
	 */
	public void setFileSubmissionDate(Date fileSubmissionDate) {
		this.fileSubmissionDate = fileSubmissionDate;
	}
	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
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
	 * @return the creatdBy
	 */
	public Integer getCreatdBy() {
		return creatdBy;
	}
	/**
	 * @param creatdBy the creatdBy to set
	 */
	public void setCreatdBy(Integer creatdBy) {
		this.creatdBy = creatdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updateBy
	 */
	public Integer getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the percentageCompleted
	 */
	public Integer getPercentageCompleted() {
		return percentageCompleted;
	}
	/**
	 * @param percentageCompleted the percentageCompleted to set
	 */
	public void setPercentageCompleted(Integer percentageCompleted) {
		this.percentageCompleted = percentageCompleted;
	}
	/**
	 * @return the failureFile
	 */
	public byte[] getFailureFile() {
		return failureFile; // changed variable name from responseFile to  failureFile, changed method name from getResponseFile to getFailureFile
	}
	/**
	 * @param responseFile the failureFile to set
	 */
	public void setFailureFile(byte[] failureFile) { // changed variable name from responseFile to  failureFile, changed method name from setResponseFile to setFailureFile
		this.failureFile = failureFile; // changed variable name from responseFile to  failureFile
	}
	/**
	 * @return the fileNameSuccess
	 */
	public String getFileNameSuccess() {
		return fileNameSuccess;
	}
	/**
	 * @param fileNameSuccess the fileNameSuccess to set
	 */
	public void setFileNameSuccess(String fileNameSuccess) {
		this.fileNameSuccess = fileNameSuccess;
	}
	/**
	 * @return the fileNameFailure
	 */
	public String getFileNameFailure() {
		return fileNameFailure;
	}
	/**
	 * @param fileNameFailure the fileNameFailure to set
	 */
	public void setFileNameFailure(String fileNameFailure) {
		this.fileNameFailure = fileNameFailure;
	}

}
