package com.ff.domain.routeserviced;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.transport.TransportModeDO;

/**
 * @author narmdr
 *
 */
public class TripServicedByDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer tripServicedById;
	private ServicedByDO servicedByDO;
	private TripDO tripDO;
	private TransportModeDO transportModeDO;
	private String operationDays;
	private String allDays;
	private Date effectiveFrom;
	private Date effectiveTo;
	private String active;

	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getOperationDays() {
		return operationDays;
	}
	public void setOperationDays(String operationDays) {
		this.operationDays = operationDays;
	}
	public String getAllDays() {
		return allDays;
	}
	public void setAllDays(String allDays) {
		this.allDays = allDays;
	}
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public Date getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public Integer getTripServicedById() {
		return tripServicedById;
	}
	public void setTripServicedById(Integer tripServicedById) {
		this.tripServicedById = tripServicedById;
	}
	public ServicedByDO getServicedByDO() {
		return servicedByDO;
	}
	public void setServicedByDO(ServicedByDO servicedByDO) {
		this.servicedByDO = servicedByDO;
	}
	public TripDO getTripDO() {
		return tripDO;
	}
	public void setTripDO(TripDO tripDO) {
		this.tripDO = tripDO;
	}
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}	
}

