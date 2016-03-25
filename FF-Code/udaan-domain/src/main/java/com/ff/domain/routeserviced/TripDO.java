package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.transport.PortDO;
import com.ff.domain.transport.TransportDO;

/**
 * @author narmdr
 *
 */
public class TripDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer tripId;
	private String arrivalTime;
	private String departureTime;	
	private RouteDO routeDO;
	private TransportDO transportDO;
	private PortDO originPortDO;
	private PortDO destPortDO;
	private String active;
	
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
	public RouteDO getRouteDO() {
		return routeDO;
	}
	public void setRouteDO(RouteDO routeDO) {
		this.routeDO = routeDO;
	}
	public TransportDO getTransportDO() {
		return transportDO;
	}
	public void setTransportDO(TransportDO transportDO) {
		this.transportDO = transportDO;
	}
	public PortDO getOriginPortDO() {
		return originPortDO;
	}
	public void setOriginPortDO(PortDO originPortDO) {
		this.originPortDO = originPortDO;
	}
	public PortDO getDestPortDO() {
		return destPortDO;
	}
	public void setDestPortDO(PortDO destPortDO) {
		this.destPortDO = destPortDO;
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
}
