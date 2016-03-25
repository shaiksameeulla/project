/**
 * 
 */
package com.ff.complaints;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author cbhure
 * 
 */
public class CriticalClaimComplaintTO extends CGBaseTO {

	private static final long serialVersionUID = 6265976536829445099L;

	private Integer serviceReqClaimId;
	private Integer serviceRequestComplaintId;
	private String actualClaim;
	private String actualClaimAmt;
	private String negotiableClaim;
	private String negotiableClaimAmt;
	private String settlement;
	private String paperWork;
	private String accountability;
	private String clientPolicy;
	private String missingCertificate;
	private String remark;
	private String salesManagerFeedback;
	private String salesManagerFeedbackDate;
	private String csManagerFeedback;
	private String csManagerFeedbackDate;
	private String agmFeedback;
	private String agmFeedbackDate;
	private String vpFeedback;
	private String vpFeedBackDate;
	private String corporate;
	private String corporateDate;
	private String isActualClaim;
	private String isNegotiableClaim;
	private String isSettlement;
	private String isSettled;
	private String creationDateStr;
	private String updateDateStr;
	private String complaintNumber;
	private String consgNo;
	private Integer complaintID;
	private String transMsg;
	private String todaysDate;
	private String isCof;
	private String cofAmt;
	
	/** Common Attribute - created and updated user id. */
	private Integer createdBy;
	private Integer updatedBy;

	private String complaintNo;
	private Integer complaintId;
	private String consignmentNumber;

	/** The claim complaint status. */
	private String claimComplaintStatus;

	/** The claimComplaintStatusList. */
	private List<StockStandardTypeTO> claimComplaintStatusList;

	
	
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
	public String getCofAmt() {
		return cofAmt;
	}

	/**
	 * @param cofAmt the cofAmt to set
	 */
	public void setCofAmt(String cofAmt) {
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
	public String getCorporateDate() {
		return corporateDate;
	}

	/**
	 * @param corporateDate
	 *            the corporateDate to set
	 */
	public void setCorporateDate(String corporateDate) {
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
	 * @return the claimComplaintStatusList
	 */
	public List<StockStandardTypeTO> getClaimComplaintStatusList() {
		return claimComplaintStatusList;
	}

	/**
	 * @param claimComplaintStatusList
	 *            the claimComplaintStatusList to set
	 */
	public void setClaimComplaintStatusList(
			List<StockStandardTypeTO> claimComplaintStatusList) {
		this.claimComplaintStatusList = claimComplaintStatusList;
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
	 * @return the todaysDate
	 */
	public String getTodaysDate() {
		return todaysDate;
	}

	/**
	 * @param todaysDate
	 *            the todaysDate to set
	 */
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the creationDateStr
	 */
	public String getCreationDateStr() {
		return creationDateStr;
	}

	/**
	 * @param creationDateStr
	 *            the creationDateStr to set
	 */
	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	/**
	 * @return the updateDateStr
	 */
	public String getUpdateDateStr() {
		return updateDateStr;
	}

	/**
	 * @param updateDateStr
	 *            the updateDateStr to set
	 */
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
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
	public String getActualClaimAmt() {
		return actualClaimAmt;
	}

	/**
	 * @param actualClaimAmt
	 *            the actualClaimAmt to set
	 */
	public void setActualClaimAmt(String actualClaimAmt) {
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
	public String getNegotiableClaimAmt() {
		return negotiableClaimAmt;
	}

	/**
	 * @param negotiableClaimAmt
	 *            the negotiableClaimAmt to set
	 */
	public void setNegotiableClaimAmt(String negotiableClaimAmt) {
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getSalesManagerFeedbackDate() {
		return salesManagerFeedbackDate;
	}

	/**
	 * @param salesManagerFeedbackDate
	 *            the salesManagerFeedbackDate to set
	 */
	public void setSalesManagerFeedbackDate(String salesManagerFeedbackDate) {
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
	public String getCsManagerFeedbackDate() {
		return csManagerFeedbackDate;
	}

	/**
	 * @param csManagerFeedbackDate
	 *            the csManagerFeedbackDate to set
	 */
	public void setCsManagerFeedbackDate(String csManagerFeedbackDate) {
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
	public String getAgmFeedbackDate() {
		return agmFeedbackDate;
	}

	/**
	 * @param agmFeedbackDate
	 *            the agmFeedbackDate to set
	 */
	public void setAgmFeedbackDate(String agmFeedbackDate) {
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
	public String getVpFeedBackDate() {
		return vpFeedBackDate;
	}

	/**
	 * @param vpFeedBackDate
	 *            the vpFeedBackDate to set
	 */
	public void setVpFeedBackDate(String vpFeedBackDate) {
		this.vpFeedBackDate = vpFeedBackDate;
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
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the isActualClaim
	 */
	public String getIsActualClaim() {
		return isActualClaim;
	}

	/**
	 * @param isActualClaim
	 *            the isActualClaim to set
	 */
	public void setIsActualClaim(String isActualClaim) {
		this.isActualClaim = isActualClaim;
	}

	/**
	 * @return the isNegotiableClaim
	 */
	public String getIsNegotiableClaim() {
		return isNegotiableClaim;
	}

	/**
	 * @param isNegotiableClaim
	 *            the isNegotiableClaim to set
	 */
	public void setIsNegotiableClaim(String isNegotiableClaim) {
		this.isNegotiableClaim = isNegotiableClaim;
	}

	/**
	 * @return the isSettlement
	 */
	public String getIsSettlement() {
		return isSettlement;
	}

	/**
	 * @param isSettlement
	 *            the isSettlement to set
	 */
	public void setIsSettlement(String isSettlement) {
		this.isSettlement = isSettlement;
	}

	/**
	 * @return the complaintNumber
	 */
	public String getComplaintNumber() {
		return complaintNumber;
	}

	/**
	 * @param complaintNumber
	 *            the complaintNumber to set
	 */
	public void setComplaintNumber(String complaintNumber) {
		this.complaintNumber = complaintNumber;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo
	 *            the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the complaintID
	 */
	public Integer getComplaintID() {
		return complaintID;
	}

	/**
	 * @param complaintID
	 *            the complaintID to set
	 */
	public void setComplaintID(Integer complaintID) {
		this.complaintID = complaintID;
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
	 * @return the complaintId
	 */
	public Integer getComplaintId() {
		return complaintId;
	}

	/**
	 * @param complaintId
	 *            the complaintId to set
	 */
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

}
