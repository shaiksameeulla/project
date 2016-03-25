package com.ff.complaints;

public class ServiceRequestFilters {
	private Integer userId;
	private String consignmentNo;
	private String reqStatus;
	private String reqDesc;
	private String callerPhone;
	private String callerEmail;
	private String callerMobile;
	private String referenceNo;
	private String serviceRequestNumber;
	private String bookingReferenceNumber;
	private Boolean isProjectionRequired;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}	
	public String getReqDesc() {
		return reqDesc;
	}
	public void setReqDesc(String reqDesc) {
		this.reqDesc = reqDesc;
	}
	public String getCallerPhone() {
		return callerPhone;
	}
	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}
	public String getCallerEmail() {
		return callerEmail;
	}
	public void setCallerEmail(String callerEmail) {
		this.callerEmail = callerEmail;
	}
	public String getCallerMobile() {
		return callerMobile;
	}
	public void setCallerMobile(String callerMobile) {
		this.callerMobile = callerMobile;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getConsignmentNo() {
		return consignmentNo;
	}
	/**
	 * @return the serviceRequestNumber
	 */
	public String getServiceRequestNumber() {
		return serviceRequestNumber;
	}
	/**
	 * @return the bookingReferenceNumber
	 */
	public String getBookingReferenceNumber() {
		return bookingReferenceNumber;
	}
	/**
	 * @param serviceRequestNumber the serviceRequestNumber to set
	 */
	public void setServiceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
	}
	/**
	 * @param bookingReferenceNumber the bookingReferenceNumber to set
	 */
	public void setBookingReferenceNumber(String bookingReferenceNumber) {
		this.bookingReferenceNumber = bookingReferenceNumber;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}
	/**
	 * @return the isProjectionRequired
	 */
	public Boolean getIsProjectionRequired() {
		return isProjectionRequired;
	}
	/**
	 * @param isProjectionRequired the isProjectionRequired to set
	 */
	public void setIsProjectionRequired(Boolean isProjectionRequired) {
		this.isProjectionRequired = isProjectionRequired;
	}
}
