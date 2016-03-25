/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 *
 */
public class ServiceTransferTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4090814076315398514L;
	private String serviceReqNo;
	private Integer serviceRequestId;
	private String empName;
	private String empEmail;
	private String empPhoneNumber;
	/**
	 * @return the serviceReqNo
	 */
	public String getServiceReqNo() {
		return serviceReqNo;
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @return the empEmail
	 */
	public String getEmpEmail() {
		return empEmail;
	}
	/**
	 * @return the empPhoneNumber
	 */
	public String getEmpPhoneNumber() {
		return empPhoneNumber;
	}
	/**
	 * @param serviceReqNo the serviceReqNo to set
	 */
	public void setServiceReqNo(String serviceReqNo) {
		this.serviceReqNo = serviceReqNo;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @param empEmail the empEmail to set
	 */
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	/**
	 * @param empPhoneNumber the empPhoneNumber to set
	 */
	public void setEmpPhoneNumber(String empPhoneNumber) {
		this.empPhoneNumber = empPhoneNumber;
	}
	/**
	 * @return the serviceRequestId
	 */
	public Integer getServiceRequestId() {
		return serviceRequestId;
	}
	/**
	 * @param serviceRequestId the serviceRequestId to set
	 */
	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	
	
	

}
