package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.transport.BcunTransportDO;
import com.ff.domain.transport.PortDO;

/**
 * The Class BcunTripDO.
 *
 * @author narmdr
 */
public class BcunTripDO extends CGFactDO {

	private static final long serialVersionUID = 5490498753241109591L;
	private Integer tripId;
	private String arrivalTime;
	private String departureTime;	
	private BcunRouteDO routeDO;
	private BcunTransportDO transportDO;
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
	/**
	 * @return the routeDO
	 */
	public BcunRouteDO getRouteDO() {
		return routeDO;
	}
	/**
	 * @param routeDO the routeDO to set
	 */
	public void setRouteDO(BcunRouteDO routeDO) {
		this.routeDO = routeDO;
	}
	/**
	 * @return the transportDO
	 */
	public BcunTransportDO getTransportDO() {
		return transportDO;
	}
	/**
	 * @param transportDO the transportDO to set
	 */
	public void setTransportDO(BcunTransportDO transportDO) {
		this.transportDO = transportDO;
	}
}
