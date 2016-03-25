package com.ff.domain.leads;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author sdalli
 *
 */
public class PlanFeedbackDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer planFeedbackId;
	private LeadDO leadDO;
	private String feedbackCode;
	private Date visitDate;
	private String purposeOfVisit;
	private String contactPerson;
	private String time;
	private String remarks;
	private String leadStatus;
	private EmployeeDO createdByEmployeeDO;
	private Date createdDate;
	private EmployeeDO updatedByEmployeeDO;
	private Date updatedDate;

	public Integer getPlanFeedbackId() {
		return planFeedbackId;
	}
	public void setPlanFeedbackId(Integer planFeedbackId) {
		this.planFeedbackId = planFeedbackId;
	}
	public LeadDO getLeadDO() {
		return leadDO;
	}
	public void setLeadDO(LeadDO leadDO) {
		this.leadDO = leadDO;
	}
	public String getFeedbackCode() {
		return feedbackCode;
	}
	public void setFeedbackCode(String feedbackCode) {
		this.feedbackCode = feedbackCode;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}
	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	/**
	 * @return the createdByEmployeeDO
	 */
	public EmployeeDO getCreatedByEmployeeDO() {
		return createdByEmployeeDO;
	}
	/**
	 * @param createdByEmployeeDO the createdByEmployeeDO to set
	 */
	public void setCreatedByEmployeeDO(EmployeeDO createdByEmployeeDO) {
		this.createdByEmployeeDO = createdByEmployeeDO;
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
	 * @return the updatedByEmployeeDO
	 */
	public EmployeeDO getUpdatedByEmployeeDO() {
		return updatedByEmployeeDO;
	}
	/**
	 * @param updatedByEmployeeDO the updatedByEmployeeDO to set
	 */
	public void setUpdatedByEmployeeDO(EmployeeDO updatedByEmployeeDO) {
		this.updatedByEmployeeDO = updatedByEmployeeDO;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
