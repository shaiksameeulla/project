package com.capgemini.lbs.framework.to;

/**
 * The Class SmsSenderTO.
 * 
 * @author narmdr
 */
public class SmsSenderTO extends CGBaseTO {

	private static final long serialVersionUID = 4009461753175953075L;
	
	private String moduleName;
	private Integer userId;
	private String contactNumber;
	private String message;
	private String statusCode;
	private String isSmsSent; //Y-Yes, N-No
	private boolean isImmediateSend = Boolean.TRUE;//isImmediateSend indicates whether SMS need to send immediately.
	private String cnNumber;
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
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	/**
	 * @return the isImmediateSend
	 */
	public boolean isImmediateSend() {
		return isImmediateSend;
	}
	/**
	 * @param isImmediateSend the isImmediateSend to set
	 */
	public void setImmediateSend(boolean isImmediateSend) {
		this.isImmediateSend = isImmediateSend;
	}
	public String getCnNumber() {
		return cnNumber;
	}
	public void setCnNumber(String cnNumber) {
		this.cnNumber = cnNumber;
	}
	
}
