/**
 * 
 */
package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 *
 */
public class AirlineTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer airlineId;
	private String airlineCode;
	private String airlineName;
	private String recordStatus;
	
	/**
	 * @return the airlineId
	 */
	public Integer getAirlineId() {
		return airlineId;
	}
	/**
	 * @param airlineId the airlineId to set
	 */
	public void setAirlineId(Integer airlineId) {
		this.airlineId = airlineId;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	
	
	
}
