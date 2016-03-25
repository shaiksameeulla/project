/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestComplaintTypeTO extends CGBaseTO{
	
private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestComplaintTypeId;
	private String complaintTypeCode;
	private String complaintTypeName;
	private String complaintTypeDescription;
	private String isCriticalComplaint;
	
	public Integer getServiceRequestComplaintTypeId() {
		return serviceRequestComplaintTypeId;
	}
	public void setServiceRequestComplaintTypeId(
			Integer serviceRequestComplaintTypeId) {
		this.serviceRequestComplaintTypeId = serviceRequestComplaintTypeId;
	}
	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}
	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}
	public String getComplaintTypeName() {
		return complaintTypeName;
	}
	public void setComplaintTypeName(String complaintTypeName) {
		this.complaintTypeName = complaintTypeName;
	}
	public String getComplaintTypeDescription() {
		return complaintTypeDescription;
	}
	public void setComplaintTypeDescription(String complaintTypeDescription) {
		this.complaintTypeDescription = complaintTypeDescription;
	}
	/**
	 * @return the isCriticalComplaint
	 */
	public String getIsCriticalComplaint() {
		return isCriticalComplaint;
	}
	/**
	 * @param isCriticalComplaint the isCriticalComplaint to set
	 */
	public void setIsCriticalComplaint(String isCriticalComplaint) {
		this.isCriticalComplaint = isCriticalComplaint;
	}
	
	

}
