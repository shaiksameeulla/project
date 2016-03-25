/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestStatusTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestStatusId;
	private String statusCode;
	private String statusName;
	private String statusDescription;
	public Integer getServiceRequestStatusId() {
		return serviceRequestStatusId;
	}
	public void setServiceRequestStatusId(Integer serviceRequestStatusId) {
		this.serviceRequestStatusId = serviceRequestStatusId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	

}
