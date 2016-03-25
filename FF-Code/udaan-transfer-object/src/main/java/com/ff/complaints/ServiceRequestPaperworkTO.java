package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;
import org.apache.struts.upload.FormFile;

public class ServiceRequestPaperworkTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer serviceRequestPaperworkId;
	Integer serviceRequestId;
	String complaintFileName;
	String consignorCopyFileName;
	String mailCopyFileName;
	String undertakingFileName;
	String invoiceFileName;
	Integer transferIcc;
	String clientMeet;
	String serviceRequestPaperworkDateStr;
	String feedback;
	String complaintNumber;
	String consignmentNumber;
	Integer createdBy;
	Integer updateBy;	
	
	FormFile complaintFile;
	FormFile consignorCopyFile;
	FormFile mailCopyFile;
	FormFile undertakingFile;
	FormFile invoiceFile;

	Integer complaintFileId;
	Integer consignorCopyFileId;
	Integer mailCopyFileId;
	Integer undertakingFileId;
	Integer invoiceFileId;
	
	
	/**
	 * @return the complaintFileId
	 */
	public Integer getComplaintFileId() {
		return complaintFileId;
	}
	/**
	 * @param complaintFileId the complaintFileId to set
	 */
	public void setComplaintFileId(Integer complaintFileId) {
		this.complaintFileId = complaintFileId;
	}
	/**
	 * @return the consignorCopyFileId
	 */
	public Integer getConsignorCopyFileId() {
		return consignorCopyFileId;
	}
	/**
	 * @param consignorCopyFileId the consignorCopyFileId to set
	 */
	public void setConsignorCopyFileId(Integer consignorCopyFileId) {
		this.consignorCopyFileId = consignorCopyFileId;
	}
	/**
	 * @return the mailCopyFileId
	 */
	public Integer getMailCopyFileId() {
		return mailCopyFileId;
	}
	/**
	 * @param mailCopyFileId the mailCopyFileId to set
	 */
	public void setMailCopyFileId(Integer mailCopyFileId) {
		this.mailCopyFileId = mailCopyFileId;
	}
	/**
	 * @return the undertakingFileId
	 */
	public Integer getUndertakingFileId() {
		return undertakingFileId;
	}
	/**
	 * @param undertakingFileId the undertakingFileId to set
	 */
	public void setUndertakingFileId(Integer undertakingFileId) {
		this.undertakingFileId = undertakingFileId;
	}
	/**
	 * @return the invoiceFileId
	 */
	public Integer getInvoiceFileId() {
		return invoiceFileId;
	}
	/**
	 * @param invoiceFileId the invoiceFileId to set
	 */
	public void setInvoiceFileId(Integer invoiceFileId) {
		this.invoiceFileId = invoiceFileId;
	}
	/**
	 * @return the servicerequestPaperworkId
	 */
	public Integer getServiceRequestPaperworkId() {
		return serviceRequestPaperworkId;
	}
	/**
	 * @param servicerequestPaperworkId the servicerequestPaperworkId to set
	 */
	public void setServiceRequestPaperworkId(Integer serviceRequestPaperworkId) {
		this.serviceRequestPaperworkId = serviceRequestPaperworkId;
	}
	/**
	 * @return the serviceRequestId
	 */
	public Integer getServiceRequestId() {
		return serviceRequestId;
	}
	/**
	 * @param serviceRequestId the serviceRequestId to set
	 */
	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	/**
	 * @return the complaintFileName
	 */
	public String getComplaintFileName() {
		return complaintFileName;
	}
	/**
	 * @param complaintFileName the complaintFileName to set
	 */
	public void setComplaintFileName(String complaintFileName) {
		this.complaintFileName = complaintFileName;
	}
	/**
	 * @return the consignorCopyFileName
	 */
	public String getConsignorCopyFileName() {
		return consignorCopyFileName;
	}
	/**
	 * @param consignorCopyFileName the consignorCopyFileName to set
	 */
	public void setConsignorCopyFileName(String consignorCopyFileName) {
		this.consignorCopyFileName = consignorCopyFileName;
	}
	/**
	 * @return the mailCopyFileName
	 */
	public String getMailCopyFileName() {
		return mailCopyFileName;
	}
	/**
	 * @param mailCopyFileName the mailCopyFileName to set
	 */
	public void setMailCopyFileName(String mailCopyFileName) {
		this.mailCopyFileName = mailCopyFileName;
	}
	/**
	 * @return the undertakingFileName
	 */
	public String getUndertakingFileName() {
		return undertakingFileName;
	}
	/**
	 * @param undertakingFileName the undertakingFileName to set
	 */
	public void setUndertakingFileName(String undertakingFileName) {
		this.undertakingFileName = undertakingFileName;
	}
	/**
	 * @return the invoiceFileName
	 */
	public String getInvoiceFileName() {
		return invoiceFileName;
	}
	/**
	 * @param invoiceFileName the invoiceFileName to set
	 */
	public void setInvoiceFileName(String invoiceFileName) {
		this.invoiceFileName = invoiceFileName;
	}
	/**
	 * @return the transferIcc
	 */
	public Integer getTransferIcc() {
		return transferIcc;
	}
	/**
	 * @param transferIcc the transferIcc to set
	 */
	public void setTransferIcc(Integer transferIcc) {
		this.transferIcc = transferIcc;
	}
	/**
	 * @return the clientMeet
	 */
	public String getClientMeet() {
		return clientMeet;
	}
	/**
	 * @param clientMeet the clientMeet to set
	 */
	public void setClientMeet(String clientMeet) {
		this.clientMeet = clientMeet;
	}
	/**
	 * @return the servicerequestPaperworkDateStr
	 */
	public String getServiceRequestPaperworkDateStr() {
		return serviceRequestPaperworkDateStr;
	}
	/**
	 * @param servicerequestPaperworkDateStr the servicerequestPaperworkDateStr to set
	 */
	public void setServiceRequestPaperworkDateStr(
			String serviceRequestPaperworkDateStr) {
		this.serviceRequestPaperworkDateStr = serviceRequestPaperworkDateStr;
	}
	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}
	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	/**
	 * @return the consignorCopyFile
	 */
	public FormFile getConsignorCopyFile() {
		return consignorCopyFile;
	}
	/**
	 * @param consignorCopyFile the consignorCopyFile to set
	 */
	public void setConsignorCopyFile(FormFile consignorCopyFile) {
		this.consignorCopyFile = consignorCopyFile;
	}
	/**
	 * @return the mailCopyFile
	 */
	public FormFile getMailCopyFile() {
		return mailCopyFile;
	}
	/**
	 * @param mailCopyFile the mailCopyFile to set
	 */
	public void setMailCopyFile(FormFile mailCopyFile) {
		this.mailCopyFile = mailCopyFile;
	}
	/**
	 * @return the complaintFile
	 */
	public FormFile getComplaintFile() {
		return complaintFile;
	}
	/**
	 * @param complaintFile the complaintFile to set
	 */
	public void setComplaintFile(FormFile complaintFile) {
		this.complaintFile = complaintFile;
	}
	/**
	 * @return the undertakingFile
	 */
	public FormFile getUndertakingFile() {
		return undertakingFile;
	}
	/**
	 * @param undertakingFile the undertakingFile to set
	 */
	public void setUndertakingFile(FormFile undertakingFile) {
		this.undertakingFile = undertakingFile;
	}
	/**
	 * @return the invoiceFile
	 */
	public FormFile getInvoiceFile() {
		return invoiceFile;
	}
	/**
	 * @param invoiceFile the invoiceFile to set
	 */
	public void setInvoiceFile(FormFile invoiceFile) {
		this.invoiceFile = invoiceFile;
	}
	/**
	 * @return the complaintNumber
	 */
	public String getComplaintNumber() {
		return complaintNumber;
	}
	/**
	 * @param complaintNumber the complaintNumber to set
	 */
	public void setComplaintNumber(String complaintNumber) {
		this.complaintNumber = complaintNumber;
	}
	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	
	
}
