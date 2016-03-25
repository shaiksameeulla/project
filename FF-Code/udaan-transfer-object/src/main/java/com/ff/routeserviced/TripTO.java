package com.ff.routeserviced;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.transport.PortTO;
import com.ff.transport.TransportTO;

/**
 * @author narmdr
 *
 */
public class TripTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer tripId;
	private RouteTO routeTO;
	private TransportTO transportTO;
	private PortTO originPortTO;
	private PortTO destPortTO;
	private String arrivalTime;
	private String departureTime;
	private String active;
	private Integer regionId;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public RouteTO getRouteTO() {
		return routeTO;
	}
	public void setRouteTO(RouteTO routeTO) {
		this.routeTO = routeTO;
	}
	public TransportTO getTransportTO() {
		return transportTO;
	}
	public void setTransportTO(TransportTO transportTO) {
		this.transportTO = transportTO;
	}
	public PortTO getOriginPortTO() {
		return originPortTO;
	}
	public void setOriginPortTO(PortTO originPortTO) {
		this.originPortTO = originPortTO;
	}
	public PortTO getDestPortTO() {
		return destPortTO;
	}
	public void setDestPortTO(PortTO destPortTO) {
		this.destPortTO = destPortTO;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
}
