package com.ff.domain.complaints;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ServiceRequestLegalComplaintDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer serviceRequestComplaintLegal;

	private Integer serviceRequestComplaintId;

	private String investigationFeedback;

	private String forwardedToFfclLawyer;

	private Date forwardedToFfclLawyerDate;
	
	private Date advocateNoticeFileDate;
	
	private Date hearing1_date;
	
	private Date hearing2_date;
	
	private Date hearing3_date;
	
	private Date hearing4_date;
	
	private Date hearing5_date;
	
	private Date hearing6_date;

	private Double lawyerFees;

	private String remarks;

	private String hearing1;

	private String hearing2;

	private String hearing3;

	private String hearing4;

	private String hearing5;
	
	private String hearing6;

	/** The advocate Notice paper. */
	private ServiceRequestPapersDO advocateNoticePaperDO;

	/** The legal complaint status. */
	private String legalComplaintStatus;

	
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
	 * @return the advocateNoticePaperDO
	 */
	public ServiceRequestPapersDO getAdvocateNoticePaperDO() {
		return advocateNoticePaperDO;
	}

	/**
	 * @param advocateNoticePaperDO
	 *            the advocateNoticePaperDO to set
	 */
	public void setAdvocateNoticePaperDO(
			ServiceRequestPapersDO advocateNoticePaperDO) {
		this.advocateNoticePaperDO = advocateNoticePaperDO;
	}

	/**
	 * @return the serviceRequestComplaintLegal
	 */
	public Integer getServiceRequestComplaintLegal() {
		return serviceRequestComplaintLegal;
	}

	/**
	 * @param serviceRequestComplaintLegal
	 *            the serviceRequestComplaintLegal to set
	 */
	public void setServiceRequestComplaintLegal(
			Integer serviceRequestComplaintLegal) {
		this.serviceRequestComplaintLegal = serviceRequestComplaintLegal;
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
	 * @return the investigationFeedback
	 */
	public String getInvestigationFeedback() {
		return investigationFeedback;
	}

	/**
	 * @param investigationFeedback
	 *            the investigationFeedback to set
	 */
	public void setInvestigationFeedback(String investigationFeedback) {
		this.investigationFeedback = investigationFeedback;
	}

	/**
	 * @return the forwardedToFfclLawyer
	 */
	public String getForwardedToFfclLawyer() {
		return forwardedToFfclLawyer;
	}

	/**
	 * @param forwardedToFfclLawyer
	 *            the forwardedToFfclLawyer to set
	 */
	public void setForwardedToFfclLawyer(String forwardedToFfclLawyer) {
		this.forwardedToFfclLawyer = forwardedToFfclLawyer;
	}

	/**
	 * @return the forwardedToFfclLawyerDate
	 */
	public Date getForwardedToFfclLawyerDate() {
		return forwardedToFfclLawyerDate;
	}

	/**
	 * @param forwardedToFfclLawyerDate
	 *            the forwardedToFfclLawyerDate to set
	 */
	public void setForwardedToFfclLawyerDate(Date forwardedToFfclLawyerDate) {
		this.forwardedToFfclLawyerDate = forwardedToFfclLawyerDate;
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
	public Date getHearing1_date() {
		return hearing1_date;
	}

	/**
	 * @param hearing1_date the hearing1_date to set
	 */
	public void setHearing1_date(Date hearing1_date) {
		this.hearing1_date = hearing1_date;
	}

	/**
	 * @return the hearing2_date
	 */
	public Date getHearing2_date() {
		return hearing2_date;
	}

	/**
	 * @param hearing2_date the hearing2_date to set
	 */
	public void setHearing2_date(Date hearing2_date) {
		this.hearing2_date = hearing2_date;
	}

	/**
	 * @return the hearing3_date
	 */
	public Date getHearing3_date() {
		return hearing3_date;
	}

	/**
	 * @param hearing3_date the hearing3_date to set
	 */
	public void setHearing3_date(Date hearing3_date) {
		this.hearing3_date = hearing3_date;
	}

	/**
	 * @return the hearing4_date
	 */
	public Date getHearing4_date() {
		return hearing4_date;
	}

	/**
	 * @param hearing4_date the hearing4_date to set
	 */
	public void setHearing4_date(Date hearing4_date) {
		this.hearing4_date = hearing4_date;
	}

	/**
	 * @return the hearing5_date
	 */
	public Date getHearing5_date() {
		return hearing5_date;
	}

	/**
	 * @param hearing5_date the hearing5_date to set
	 */
	public void setHearing5_date(Date hearing5_date) {
		this.hearing5_date = hearing5_date;
	}

	/**
	 * @return the hearing6_date
	 */
	public Date getHearing6_date() {
		return hearing6_date;
	}

	/**
	 * @param hearing6_date the hearing6_date to set
	 */
	public void setHearing6_date(Date hearing6_date) {
		this.hearing6_date = hearing6_date;
	}

	/**
	 * @return the advocateNoticeFileDate
	 */
	public Date getAdvocateNoticeFileDate() {
		return advocateNoticeFileDate;
	}

	/**
	 * @param advocateNoticeFileDate the advocateNoticeFileDate to set
	 */
	public void setAdvocateNoticeFileDate(Date advocateNoticeFileDate) {
		this.advocateNoticeFileDate = advocateNoticeFileDate;
	}

	
	
}
