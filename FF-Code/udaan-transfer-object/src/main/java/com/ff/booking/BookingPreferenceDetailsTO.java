/**
 * 
 */
package com.ff.booking;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BookingPreferenceDetailsTO.
 *
 * @author uchauhan
 */
public class BookingPreferenceDetailsTO extends CGBaseTO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8602297746646439860L;
	
	/** The preference id. */
	private Integer preferenceId;
	
	/** The preference code. */
	private String preferenceCode;
	
	/** The preference name. */
	private String preferenceName;
	
	/** The description. */
	private String description;
	
	/** The status. */
	private String status;
	
	/**
	 * Gets the preference id.
	 *
	 * @return the preferenceId
	 */
	public Integer getPreferenceId() {
		return preferenceId;
	}
	
	/**
	 * Sets the preference id.
	 *
	 * @param preferenceId the preferenceId to set
	 */
	public void setPreferenceId(Integer preferenceId) {
		this.preferenceId = preferenceId;
	}
	
	/**
	 * Gets the preference code.
	 *
	 * @return the preferenceCode
	 */
	public String getPreferenceCode() {
		return preferenceCode;
	}
	
	/**
	 * Sets the preference code.
	 *
	 * @param preferenceCode the preferenceCode to set
	 */
	public void setPreferenceCode(String preferenceCode) {
		this.preferenceCode = preferenceCode;
	}
	
	/**
	 * Gets the preference name.
	 *
	 * @return the preferenceName
	 */
	public String getPreferenceName() {
		return preferenceName;
	}
	
	/**
	 * Sets the preference name.
	 *
	 * @param preferenceName the preferenceName to set
	 */
	public void setPreferenceName(String preferenceName) {
		this.preferenceName = preferenceName;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
