package com.capgemini.lbs.framework.domain;

public class SmsDO extends CGFactDO {

	private static final long serialVersionUID = 8064851406198874492L;
	private Integer smsId;
	private String moduleName;
	//private Integer userId;
	private String contactNumber;
	private String message;
	private String statusCode;
	private String isSmsSent; //Y-Yes, N-No
	/**
	 * @return the smsId
	 */
	public Integer getSmsId() {
		return smsId;
	}
	/**
	 * @param smsId the smsId to set
	 */
	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
	}
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the isSmsSent
	 */
	public String getIsSmsSent() {
		return isSmsSent;
	}
	/**
	 * @param isSmsSent the isSmsSent to set
	 */
	public void setIsSmsSent(String isSmsSent) {
		this.isSmsSent = isSmsSent;
	}
	
}
