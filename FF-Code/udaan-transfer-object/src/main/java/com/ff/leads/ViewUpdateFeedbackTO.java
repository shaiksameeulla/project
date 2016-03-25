package com.ff.leads;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.leads.PlanTO;
import com.ff.organization.EmployeeTO;

/**
 * @author sdalli
 *
 */
public class ViewUpdateFeedbackTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String leadNumber;
	private String contactPerson;
	private String mobileNo;
	private String emailAddress;
	private List<PlanTO> planDetails;
	private Integer leadId;
	private String leadFeedCodeSave; 
	private String leadStatus;
	private EmployeeTO createdBy;
	private EmployeeTO updatedBy;
	private String transMag;
	
	protected int rowCount;
	private String[] visitNos= new String[rowCount];
	private Integer[] planFeedbackIds= new Integer[rowCount];
	private String[] dateStr= new String[rowCount];
	private String[] purposeOfVisitors= new String[rowCount];
	private String[] contactPersons= new String[rowCount];
	private String[] timeStr= new String[rowCount];
	private String[] feedBackCode= new String[rowCount];
	private String[] remarks= new String[rowCount];
	
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLeadNumber() {
		return leadNumber;
	}
	public void setLeadNumber(String leadNumber) {
		this.leadNumber = leadNumber;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<PlanTO> getPlanDetails() {
		return planDetails;
	}
	public void setPlanDetails(List<PlanTO> planDetails) {
		this.planDetails = planDetails;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String[] getVisitNos() {
		return visitNos;
	}
	public void setVisitNos(String[] visitNos) {
		this.visitNos = visitNos;
	}
	
	public String[] getPurposeOfVisitors() {
		return purposeOfVisitors;
	}
	public void setPurposeOfVisitors(String[] purposeOfVisitors) {
		this.purposeOfVisitors = purposeOfVisitors;
	}
	public String[] getContactPersons() {
		return contactPersons;
	}
	public void setContactPersons(String[] contactPersons) {
		this.contactPersons = contactPersons;
	}
	
	public String[] getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String[] timeStr) {
		this.timeStr = timeStr;
	}
	public String[] getDateStr() {
		return dateStr;
	}
	public void setDateStr(String[] dateStr) {
		this.dateStr = dateStr;
	}
	public Integer getLeadId() {
		return leadId;
	}
	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}
	public String[] getFeedBackCode() {
		return feedBackCode;
	}
	public void setFeedBackCode(String[] feedBackCode) {
		this.feedBackCode = feedBackCode;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the leadFeedCodeSave
	 */
	public String getLeadFeedCodeSave() {
		return leadFeedCodeSave;
	}
	/**
	 * @param leadFeedCodeSave the leadFeedCodeSave to set
	 */
	public void setLeadFeedCodeSave(String leadFeedCodeSave) {
		this.leadFeedCodeSave = leadFeedCodeSave;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public Integer[] getPlanFeedbackIds() {
		return planFeedbackIds;
	}
	public void setPlanFeedbackIds(Integer[] planFeedbackIds) {
		this.planFeedbackIds = planFeedbackIds;
	}
	/**
	 * @return the createdBy
	 */
	public EmployeeTO getCreatedBy() {
		if(createdBy == null)
			createdBy = new EmployeeTO();
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeTO createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public EmployeeTO getUpdatedBy() {
		if(updatedBy == null)
			updatedBy = new EmployeeTO();
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(EmployeeTO updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the transMag
	 */
	public String getTransMag() {
		return transMag;
	}
	/**
	 * @param transMag the transMag to set
	 */
	public void setTransMag(String transMag) {
		this.transMag = transMag;
	}
	
}
