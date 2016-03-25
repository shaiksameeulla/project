/**
 * 
 */
package com.ff.to.drs;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class DeliveryNumberGeneratorTO.
 *
 * @author mohammes
 */
public class DeliveryNumberGeneratorTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2593236245257128059L;
	
	/** The logged in office id. */
	private Integer loggedInOfficeId;
	
	/** The logged in office code. */
	private Integer loggedInOfficeCode;
	
	/** The is yprs. */
	private Boolean isYprs;
	
	/** The drs length. */
	private int drsLength;
	
	/**
	 * Gets the logged in office id.
	 *
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	
	/**
	 * Gets the logged in office code.
	 *
	 * @return the loggedInOfficeCode
	 */
	public Integer getLoggedInOfficeCode() {
		return loggedInOfficeCode;
	}
	
	/**
	 * Gets the checks if is yprs.
	 *
	 * @return the isYprs
	 */
	public Boolean getIsYprs() {
		return isYprs;
	}
	
	/**
	 * Gets the drs length.
	 *
	 * @return the drsLength
	 */
	public int getDrsLength() {
		return drsLength;
	}
	
	/**
	 * Sets the logged in office id.
	 *
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	
	/**
	 * Sets the logged in office code.
	 *
	 * @param loggedInOfficeCode the loggedInOfficeCode to set
	 */
	public void setLoggedInOfficeCode(Integer loggedInOfficeCode) {
		this.loggedInOfficeCode = loggedInOfficeCode;
	}
	
	/**
	 * Sets the checks if is yprs.
	 *
	 * @param isYprs the isYprs to set
	 */
	public void setIsYprs(Boolean isYprs) {
		this.isYprs = isYprs;
	}
	
	/**
	 * Sets the drs length.
	 *
	 * @param drsLength the drsLength to set
	 */
	public void setDrsLength(int drsLength) {
		this.drsLength = drsLength;
	}

}
