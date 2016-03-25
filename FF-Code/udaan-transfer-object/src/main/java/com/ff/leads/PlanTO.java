package com.ff.leads;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author sdalli
 *
 */
public class PlanTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer planId;
	private String date;
	private String purposeOfVisit;
	private String contactPerson;
	private String dateTime;
	private List<StockStandardTypeTO> feedBackStandardTypeTO;
	private String feedbackCode;
	private Integer planFeedbackId;
	private String remarks;
	private String alertMsg;
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public List<StockStandardTypeTO> getFeedBackStandardTypeTO() {
		return feedBackStandardTypeTO;
	}
	public void setFeedBackStandardTypeTO(
			List<StockStandardTypeTO> feedBackStandardTypeTO) {
		this.feedBackStandardTypeTO = feedBackStandardTypeTO;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFeedbackCode() {
		return feedbackCode;
	}
	public void setFeedbackCode(String feedbackCode) {
		this.feedbackCode = feedbackCode;
	}
	public Integer getPlanFeedbackId() {
		return planFeedbackId;
	}
	public void setPlanFeedbackId(Integer planFeedbackId) {
		this.planFeedbackId = planFeedbackId;
	}
	/**
	 * @return the alertMsg
	 */
	public String getAlertMsg() {
		return alertMsg;
	}
	/**
	 * @param alertMsg the alertMsg to set
	 */
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
}
