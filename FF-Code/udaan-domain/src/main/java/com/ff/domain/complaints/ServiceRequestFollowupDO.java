/**
 * 
 */
package com.ff.domain.complaints;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author prmeher
 *
 */
public class ServiceRequestFollowupDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357003711026658092L;
	
	
	private Integer followUpId;
	private Date followUpDate;
	private String callFrom;
	private String caller;
	private String customerName;
	private String email;
	private String followupNote;
	private ServiceRequestDO serviceRequestDO;
	private EmployeeDO employeeDO;
	
	
	/**
	 * @return the employeeDO
	 */
	public EmployeeDO getEmployeeDO() {
		return employeeDO;
	}
	/**
	 * @param employeeDO the employeeDO to set
	 */
	public void setEmployeeDO(EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}
	/**
	 * @return the followUpId
	 */
	public Integer getFollowUpId() {
		return followUpId;
	}
	/**
	 * @param followUpId the followUpId to set
	 */
	public void setFollowUpId(Integer followUpId) {
		this.followUpId = followUpId;
	}
	/**
	 * @return the followUpDate
	 */
	public Date getFollowUpDate() {
		return followUpDate;
	}
	/**
	 * @param followUpDate the followUpDate to set
	 */
	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}
	/**
	 * @return the callFrom
	 */
	public String getCallFrom() {
		return callFrom;
	}
	/**
	 * @param callFrom the callFrom to set
	 */
	public void setCallFrom(String callFrom) {
		this.callFrom = callFrom;
	}
	/**
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}
	/**
	 * @param caller the caller to set
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the followupNote
	 */
	public String getFollowupNote() {
		return followupNote;
	}
	/**
	 * @param followupNote the followupNote to set
	 */
	public void setFollowupNote(String followupNote) {
		this.followupNote = followupNote;
	}
	/**
	 * @return the serviceRequestDO
	 */
	public ServiceRequestDO getServiceRequestDO() {
		return serviceRequestDO;
	}
	/**
	 * @param serviceRequestDO the serviceRequestDO to set
	 */
	public void setServiceRequestDO(ServiceRequestDO serviceRequestDO) {
		this.serviceRequestDO = serviceRequestDO;
	}
}
