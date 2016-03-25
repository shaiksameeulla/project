/**
 * 
 */
package com.ff.complaints;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.RegionTO;

/**
 * @author prmeher
 *
 */
public class ServiceRequestFollowupTO extends CGBaseTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3581866098725548607L;
	private Integer followUpId;
	private String followUpDate;
	private String callFrom;
	private String caller;
	private String customerName;
	private String email;
	private String followupNote;
	private ServiceRequestTO serviceRequestTO;
	private String status;
	private Integer complaintId;
	private Integer regionId;
	private Integer cityId;
	private Integer officeId;
	private Integer employeeId;
	private List<RegionTO> regionTOs;
	
	// common Attribute
	private Integer loginUserId;
	
	/** Followup Details Attributes */
	private String followupDetails;
	private String consigNo;
	private String followupTime;
	private String empDetails;
	
	
	
	
	/**
	 * @return the regionTOs
	 */
	public List<RegionTO> getRegionTOs() {
		return regionTOs;
	}
	/**
	 * @param regionTOs the regionTOs to set
	 */
	public void setRegionTOs(List<RegionTO> regionTOs) {
		this.regionTOs = regionTOs;
	}
	/**
	 * @return the empDetails
	 */
	public String getEmpDetails() {
		return empDetails;
	}
	/**
	 * @param empDetails the empDetails to set
	 */
	public void setEmpDetails(String empDetails) {
		this.empDetails = empDetails;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
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
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the followupDetails
	 */
	public String getFollowupDetails() {
		return followupDetails;
	}
	/**
	 * @param followupDetails the followupDetails to set
	 */
	public void setFollowupDetails(String followupDetails) {
		this.followupDetails = followupDetails;
	}
	/**
	 * @return the consigNo
	 */
	public String getConsigNo() {
		return consigNo;
	}
	/**
	 * @param consigNo the consigNo to set
	 */
	public void setConsigNo(String consigNo) {
		this.consigNo = consigNo;
	}
	/**
	 * @return the followupTime
	 */
	public String getFollowupTime() {
		return followupTime;
	}
	/**
	 * @param followupTime the followupTime to set
	 */
	public void setFollowupTime(String followupTime) {
		this.followupTime = followupTime;
	}
	/**
	 * @return the complaintId
	 */
	public Integer getComplaintId() {
		return complaintId;
	}
	/**
	 * @param complaintId the complaintId to set
	 */
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	/**
	 * @return the loginUserId
	 */
	public Integer getLoginUserId() {
		return loginUserId;
	}
	/**
	 * @param loginUserId the loginUserId to set
	 */
	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	public String getFollowUpDate() {
		return followUpDate;
	}
	/**
	 * @param followUpDate the followUpDate to set
	 */
	public void setFollowUpDate(String followUpDate) {
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
	 * @return the serviceRequestTO
	 */
	public ServiceRequestTO getServiceRequestTO() {
		return serviceRequestTO;
	}
	/**
	 * @param serviceRequestTO the serviceRequestTO to set
	 */
	public void setServiceRequestTO(ServiceRequestTO serviceRequestTO) {
		this.serviceRequestTO = serviceRequestTO;
	}
	
}
