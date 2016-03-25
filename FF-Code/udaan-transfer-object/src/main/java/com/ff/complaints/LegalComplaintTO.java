package com.ff.complaints;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

public class LegalComplaintTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private FormFile advocateNoticFromClient;
	private String investigFeedback;
	private String remarks;
	private Double lawyerFees;
	private String hearing1;
	private String hearing2;
	private String hearing3;
	private String hearing4;
	private String hearing5;
	private String hearing6;
	private String forwardedToFFclLawyer;
	private String advocateNoticeFileDate;
	private String date;
	private String hearing1_date;
	private String hearing2_date;
	private String hearing3_date;
	private String hearing4_date;
	private String hearing5_date;
	private String hearing6_date;
	private String createdDate;
	private String updateDate;
	private Integer createdBy;
	private Integer updateby;
	private String fileName;
	private String filePath;
	private FormFile frmfile;
	private Integer serviceRequestComplaintId;
	private String systemDateAndTime;
	private String complaintNo;
	private String consignmentNo;
	private String complaintStatus;

	/** The legal complaint status. */
	private String legalComplaintStatus;

	/** The legalComplaintStatusList. */
	private List<StockStandardTypeTO> legalComplaintStatusList;

	
	/**
	 * @return the legalComplaintStatusList
	 */
	public List<StockStandardTypeTO> getLegalComplaintStatusList() {
		return legalComplaintStatusList;
	}

	/**
	 * @param legalComplaintStatusList
	 *            the legalComplaintStatusList to set
	 */
	public void setLegalComplaintStatusList(
			List<StockStandardTypeTO> legalComplaintStatusList) {
		this.legalComplaintStatusList = legalComplaintStatusList;
	}

	/**
	 * @return the legalComplaintStatus
	 */
	public String getLegalComplaintStatus() {
		return legalComplaintStatus;
	}

	/**
	 * @param legalComplaintStatus
	 *            the legalComplaintStatus to set
	 */
	public void setLegalComplaintStatus(String legalComplaintStatus) {
		this.legalComplaintStatus = legalComplaintStatus;
	}

	/**
	 * @return the advocateNoticFromClient
	 */
	public FormFile getAdvocateNoticFromClient() {
		return advocateNoticFromClient;
	}

	/**
	 * @param advocateNoticFromClient
	 *            the advocateNoticFromClient to set
	 */
	public void setAdvocateNoticFromClient(FormFile advocateNoticFromClient) {
		this.advocateNoticFromClient = advocateNoticFromClient;
	}

	/**
	 * @return the investigFeedback
	 */
	public String getInvestigFeedback() {
		return investigFeedback;
	}

	/**
	 * @param investigFeedback
	 *            the investigFeedback to set
	 */
	public void setInvestigFeedback(String investigFeedback) {
		this.investigFeedback = investigFeedback;
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
	 * @return the hearing1
	 */
	public String getHearing1() {
		return hearing1;
	}

	/**
	 * @param hearing1
	 *            the hearing1 to set
	 */
	public void setHearing1(String hearing1) {
		this.hearing1 = hearing1;
	}

	/**
	 * @return the hearing2
	 */
	public String getHearing2() {
		return hearing2;
	}

	/**
	 * @param hearing2
	 *            the hearing2 to set
	 */
	public void setHearing2(String hearing2) {
		this.hearing2 = hearing2;
	}

	/**
	 * @return the hearing3
	 */
	public String getHearing3() {
		return hearing3;
	}

	/**
	 * @param hearing3
	 *            the hearing3 to set
	 */
	public void setHearing3(String hearing3) {
		this.hearing3 = hearing3;
	}

	/**
	 * @return the hearing4
	 */
	public String getHearing4() {
		return hearing4;
	}

	/**
	 * @param hearing4
	 *            the hearing4 to set
	 */
	public void setHearing4(String hearing4) {
		this.hearing4 = hearing4;
	}

	/**
	 * @return the hearing5
	 */
	public String getHearing5() {
		return hearing5;
	}

	/**
	 * @param hearing5
	 *            the hearing5 to set
	 */
	public void setHearing5(String hearing5) {
		this.hearing5 = hearing5;
	}

	/**
	 * @return the forwardedToFFclLawyer
	 */
	public String getForwardedToFFclLawyer() {
		return forwardedToFFclLawyer;
	}

	/**
	 * @param forwardedToFFclLawyer
	 *            the forwardedToFFclLawyer to set
	 */
	public void setForwardedToFFclLawyer(String forwardedToFFclLawyer) {
		this.forwardedToFFclLawyer = forwardedToFFclLawyer;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the lawyerFees
	 */
	public Double getLawyerFees() {
		return lawyerFees;
	}

	/**
	 * @param lawyerFees
	 *            the lawyerFees to set
	 */
	public void setLawyerFees(Double lawyerFees) {
		this.lawyerFees = lawyerFees;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updateby
	 */
	public Integer getUpdateby() {
		return updateby;
	}

	/**
	 * @param updateby
	 *            the updateby to set
	 */
	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the frmfile
	 */
	public FormFile getFrmfile() {
		return frmfile;
	}

	/**
	 * @param frmfile
	 *            the frmfile to set
	 */
	public void setFrmfile(FormFile frmfile) {
		this.frmfile = frmfile;
	}

	/**
	 * @return the serviceRequestComplaintId
	 */
	public Integer getServiceRequestComplaintId() {
		return serviceRequestComplaintId;
	}

	/**
	 * @param serviceRequestComplaintId
	 *            the serviceRequestComplaintId to set
	 */
	public void setServiceRequestComplaintId(Integer serviceRequestComplaintId) {
		this.serviceRequestComplaintId = serviceRequestComplaintId;
	}

	/**
	 * @return the systemDateAndTime
	 */
	public String getSystemDateAndTime() {
		return systemDateAndTime;
	}

	/**
	 * @param systemDateAndTime
	 *            the systemDateAndTime to set
	 */
	public void setSystemDateAndTime(String systemDateAndTime) {
		this.systemDateAndTime = systemDateAndTime;
	}

	/**
	 * @return the complaintNo
	 */
	public String getComplaintNo() {
		return complaintNo;
	}

	/**
	 * @param complaintNo
	 *            the complaintNo to set
	 */
	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}

	/**
	 * @return the consignmentNo
	 */
	public String getConsignmentNo() {
		return consignmentNo;
	}

	/**
	 * @param consignmentNo
	 *            the consignmentNo to set
	 */
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	/**
	 * @return the complaintStatus
	 */
	public String getComplaintStatus() {
		return complaintStatus;
	}

	/**
	 * @param complaintStatus
	 *            the complaintStatus to set
	 */
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	/**
	 * @return the hearing6
	 */
	public String getHearing6() {
		return hearing6;
	}

	/**
	 * @param hearing6 the hearing6 to set
	 */
	public void setHearing6(String hearing6) {
		this.hearing6 = hearing6;
	}

	/**
	 * @return the hearing1_date
	 */
	public String getHearing1_date() {
		return hearing1_date;
	}

	/**
	 * @param hearing1_date the hearing1_date to set
	 */
	public void setHearing1_date(String hearing1_date) {
		this.hearing1_date = hearing1_date;
	}

	/**
	 * @return the hearing2_date
	 */
	public String getHearing2_date() {
		return hearing2_date;
	}

	/**
	 * @param hearing2_date the hearing2_date to set
	 */
	public void setHearing2_date(String hearing2_date) {
		this.hearing2_date = hearing2_date;
	}

	/**
	 * @return the hearing3_date
	 */
	public String getHearing3_date() {
		return hearing3_date;
	}

	/**
	 * @param hearing3_date the hearing3_date to set
	 */
	public void setHearing3_date(String hearing3_date) {
		this.hearing3_date = hearing3_date;
	}

	/**
	 * @return the hearing4_date
	 */
	public String getHearing4_date() {
		return hearing4_date;
	}

	/**
	 * @param hearing4_date the hearing4_date to set
	 */
	public void setHearing4_date(String hearing4_date) {
		this.hearing4_date = hearing4_date;
	}

	/**
	 * @return the hearing5_date
	 */
	public String getHearing5_date() {
		return hearing5_date;
	}

	/**
	 * @param hearing5_date the hearing5_date to set
	 */
	public void setHearing5_date(String hearing5_date) {
		this.hearing5_date = hearing5_date;
	}

	/**
	 * @return the hearing6_date
	 */
	public String getHearing6_date() {
		return hearing6_date;
	}

	/**
	 * @param hearing6_date the hearing6_date to set
	 */
	public void setHearing6_date(String hearing6_date) {
		this.hearing6_date = hearing6_date;
	}

	/**
	 * @return the advocateNoticeFileDate
	 */
	public String getAdvocateNoticeFileDate() {
		return advocateNoticeFileDate;
	}

	/**
	 * @param advocateNoticeFileDate the advocateNoticeFileDate to set
	 */
	public void setAdvocateNoticeFileDate(String advocateNoticeFileDate) {
		this.advocateNoticeFileDate = advocateNoticeFileDate;
	}

	
	
}
