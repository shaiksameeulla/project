/**
 * 
 */
package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author narmdr
 *
 */
public class FlightTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer flightId;
	private String flightNumber;
	private String airlineCode;
	private String airlineName;

	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
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
}
