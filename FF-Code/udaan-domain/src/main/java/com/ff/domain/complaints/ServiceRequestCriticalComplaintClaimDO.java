/**
 * 
 */
package com.ff.domain.complaints;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 * 
 */
public class ServiceRequestCriticalComplaintClaimDO extends CGFactDO {

	private static final long serialVersionUID = 4136463456099115189L;

	private Integer serviceReqClaimId;
	private ServiceRequestComplaintDO serviceRequestComplaintDO;
	private String actualClaim;
	private Double actualClaimAmt;
	private String negotiableClaim;
	private Double negotiableClaimAmt;
	private String isCof;
	private Double cofAmt;
	private String settlement;
	private String isSettled;
	private String paperWork;
	private String accountability;
	private String clientPolicy;
	private String missingCertificate;
	private String remarks;
	private String salesManagerFeedback;
	private Date salesManagerFeedbackDate;
	private String csManagerFeedback;
	private Date csManagerFeedbackDate;
	private String agmFeedback;
	private Date agmFeedbackDate;
	private String vpFeedback;
	private Date vpFeedBackDate;
	private String corporate;
	private Date corporateDate;

	/** The claim complaint status. */
	private String claimComplaintStatus;

	
	
	/**
	 * @return the isCof
	 */
	public String getIsCof() {
		return isCof;
	}

	/**
	 * @param isCof the isCof to set
	 */
	public void setIsCof(String isCof) {
		this.isCof = isCof;
	}

	/**
	 * @return the cofAmt
	 */
	public Double getCofAmt() {
		return cofAmt;
	}

	/**
	 * @param cofAmt the cofAmt to set
	 */
	public void setCofAmt(Double cofAmt) {
		this.cofAmt = cofAmt;
	}

	/**
	 * @return the corporate
	 */
	public String getCorporate() {
		return corporate;
	}

	/**
	 * @param corporate
	 *            the corporate to set
	 */
	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	/**
	 * @return the corporateDate
	 */
	public Date getCorporateDate() {
		return corporateDate;
	}

	/**
	 * @param corporateDate
	 *            the corporateDate to set
	 */
	public void setCorporateDate(Date corporateDate) {
		this.corporateDate = corporateDate;
	}

	/**
	 * @return the isSettled
	 */
	public String getIsSettled() {
		return isSettled;
	}

	/**
	 * @param isSettled
	 *            the isSettled to set
	 */
	public void setIsSettled(String isSettled) {
		this.isSettled = isSettled;
	}

	/**
	 * @return the claimComplaintStatus
	 */
	public String getClaimComplaintStatus() {
		return claimComplaintStatus;
	}

	/**
	 * @param claimComplaintStatus
	 *            the claimComplaintStatus to set
	 */
	public void setClaimComplaintStatus(String claimComplaintStatus) {
		this.claimComplaintStatus = claimComplaintStatus;
	}

	/**
	 * @return the serviceRequestComplaintDO
	 */
	public ServiceRequestComplaintDO getServiceRequestComplaintDO() {
		return serviceRequestComplaintDO;
	}

	/**
	 * @param serviceRequestComplaintDO
	 *            the serviceRequestComplaintDO to set
	 */
	public void setServiceRequestComplaintDO(
			ServiceRequestComplaintDO serviceRequestComplaintDO) {
		this.serviceRequestComplaintDO = serviceRequestComplaintDO;
	}

	/**
	 * @return the serviceReqClaimId
	 */
	public Integer getServiceReqClaimId() {
		return serviceReqClaimId;
	}

	/**
	 * @param serviceReqClaimId
	 *            the serviceReqClaimId to set
	 */
	public void setServiceReqClaimId(Integer serviceReqClaimId) {
		this.serviceReqClaimId = serviceReqClaimId;
	}

	/**
	 * @return the serviceReqComplaintDO
	 */
	/*
	 * public ServiceRequestComplaintDO getServiceReqComplaintDO() { return
	 * serviceReqComplaintDO; }
	 *//**
	 * @param serviceReqComplaintDO
	 *            the serviceReqComplaintDO to set
	 */
	/*
	 * public void setServiceReqComplaintDO( ServiceRequestComplaintDO
	 * serviceReqComplaintDO) { this.serviceReqComplaintDO =
	 * serviceReqComplaintDO; }
	 */
	/**
	 * @return the actualClaim
	 */
	public String getActualClaim() {
		return actualClaim;
	}

	/**
	 * @param actualClaim
	 *            the actualClaim to set
	 */
	public void setActualClaim(String actualClaim) {
		this.actualClaim = actualClaim;
	}

	/**
	 * @return the actualClaimAmt
	 */
	public Double getActualClaimAmt() {
		return actualClaimAmt;
	}

	/**
	 * @param actualClaimAmt
	 *            the actualClaimAmt to set
	 */
	public void setActualClaimAmt(Double actualClaimAmt) {
		this.actualClaimAmt = actualClaimAmt;
	}

	/**
	 * @return the negotiableClaim
	 */
	public String getNegotiableClaim() {
		return negotiableClaim;
	}

	/**
	 * @param negotiableClaim
	 *            the negotiableClaim to set
	 */
	public void setNegotiableClaim(String negotiableClaim) {
		this.negotiableClaim = negotiableClaim;
	}

	/**
	 * @return the negotiableClaimAmt
	 */
	public Double getNegotiableClaimAmt() {
		return negotiableClaimAmt;
	}

	/**
	 * @param negotiableClaimAmt
	 *            the negotiableClaimAmt to set
	 */
	public void setNegotiableClaimAmt(Double negotiableClaimAmt) {
		this.negotiableClaimAmt = negotiableClaimAmt;
	}

	/**
	 * @return the settlement
	 */
	public String getSettlement() {
		return settlement;
	}

	/**
	 * @param settlement
	 *            the settlement to set
	 */
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	/**
	 * @return the paperWork
	 */
	public String getPaperWork() {
		return paperWork;
	}

	/**
	 * @param paperWork
	 *            the paperWork to set
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
	}

	/**
	 * @return the accountability
	 */
	public String getAccountability() {
		return accountability;
	}

	/**
	 * @param accountability
	 *            the accountability to set
	 */
	public void setAccountability(String accountability) {
		this.accountability = accountability;
	}

	/**
	 * @return the clientPolicy
	 */
	public String getClientPolicy() {
		return clientPolicy;
	}

	/**
	 * @param clientPolicy
	 *            the clientPolicy to set
	 */
	public void setClientPolicy(String clientPolicy) {
		this.clientPolicy = clientPolicy;
	}

	/**
	 * @return the missingCertificate
	 */
	public String getMissingCertificate() {
		return missingCertificate;
	}

	/**
	 * @param missingCertificate
	 *            the missingCertificate to set
	 */
	public void setMissingCertificate(String missingCertificate) {
		this.missingCertificate = missingCertificate;
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
	 * @return the salesManagerFeedback
	 */
	public String getSalesManagerFeedback() {
		return salesManagerFeedback;
	}

	/**
	 * @param salesManagerFeedback
	 *            the salesManagerFeedback to set
	 */
	public void setSalesManagerFeedback(String salesManagerFeedback) {
		this.salesManagerFeedback = salesManagerFeedback;
	}

	/**
	 * @return the salesManagerFeedbackDate
	 */
	public Date getSalesManagerFeedbackDate() {
		return salesManagerFeedbackDate;
	}

	/**
	 * @param salesManagerFeedbackDate
	 *            the salesManagerFeedbackDate to set
	 */
	public void setSalesManagerFeedbackDate(Date salesManagerFeedbackDate) {
		this.salesManagerFeedbackDate = salesManagerFeedbackDate;
	}

	/**
	 * @return the csManagerFeedback
	 */
	public String getCsManagerFeedback() {
		return csManagerFeedback;
	}

	/**
	 * @param csManagerFeedback
	 *            the csManagerFeedback to set
	 */
	public void setCsManagerFeedback(String csManagerFeedback) {
		this.csManagerFeedback = csManagerFeedback;
	}

	/**
	 * @return the csManagerFeedbackDate
	 */
	public Date getCsManagerFeedbackDate() {
		return csManagerFeedbackDate;
	}

	/**
	 * @param csManagerFeedbackDate
	 *            the csManagerFeedbackDate to set
	 */
	public void setCsManagerFeedbackDate(Date csManagerFeedbackDate) {
		this.csManagerFeedbackDate = csManagerFeedbackDate;
	}

	/**
	 * @return the agmFeedback
	 */
	public String getAgmFeedback() {
		return agmFeedback;
	}

	/**
	 * @param agmFeedback
	 *            the agmFeedback to set
	 */
	public void setAgmFeedback(String agmFeedback) {
		this.agmFeedback = agmFeedback;
	}

	/**
	 * @return the agmFeedbackDate
	 */
	public Date getAgmFeedbackDate() {
		return agmFeedbackDate;
	}

	/**
	 * @param agmFeedbackDate
	 *            the agmFeedbackDate to set
	 */
	public void setAgmFeedbackDate(Date agmFeedbackDate) {
		this.agmFeedbackDate = agmFeedbackDate;
	}

	/**
	 * @return the vpFeedback
	 */
	public String getVpFeedback() {
		return vpFeedback;
	}

	/**
	 * @param vpFeedback
	 *            the vpFeedback to set
	 */
	public void setVpFeedback(String vpFeedback) {
		this.vpFeedback = vpFeedback;
	}

	/**
	 * @return the vpFeedBackDate
	 */
	public Date getVpFeedBackDate() {
		return vpFeedBackDate;
	}

	/**
	 * @param vpFeedBackDate
	 *            the vpFeedBackDate to set
	 */
	public void setVpFeedBackDate(Date vpFeedBackDate) {
		this.vpFeedBackDate = vpFeedBackDate;
	}

}
