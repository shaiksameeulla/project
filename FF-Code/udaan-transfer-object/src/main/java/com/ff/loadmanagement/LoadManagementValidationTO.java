package com.ff.loadmanagement;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class LoadManagementValidationTO.
 *
 * @author narmdr
 */
public class LoadManagementValidationTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5794804433311103539L;
	
	/** The regional office id. */
	private Integer regionalOfficeId;
	
	/** The logged in office id. */
	private Integer loggedInOfficeId;
	
	/** The gate pass number. */
	private String gatePassNumber;
		
	/** Outputs. */
	private String errorMsg;
	
	/**
	 * Gets the regional office id.
	 *
	 * @return the regional office id
	 */
	public Integer getRegionalOfficeId() {
		return regionalOfficeId;
	}
	
	/**
	 * Sets the regional office id.
	 *
	 * @param regionalOfficeId the new regional office id
	 */
	public void setRegionalOfficeId(Integer regionalOfficeId) {
		this.regionalOfficeId = regionalOfficeId;
	}
	
	/**
	 * Gets the gate pass number.
	 *
	 * @return the gate pass number
	 */
	public String getGatePassNumber() {
		return gatePassNumber;
	}
	
	/**
	 * Sets the gate pass number.
	 *
	 * @param gatePassNumber the new gate pass number
	 */
	public void setGatePassNumber(String gatePassNumber) {
		this.gatePassNumber = gatePassNumber;
	}
	
	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * Gets the logged in office id.
	 *
	 * @return the logged in office id
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	
	/**
	 * Sets the logged in office id.
	 *
	 * @param loggedInOfficeId the new logged in office id
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
}
