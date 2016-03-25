package com.ff.domain.routeserviced;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.transport.TransportModeDO;

/**
 * The Class BcunTripServicedByDO.
 *
 * @author narmdr
 */
public class BcunTripServicedByDO extends CGFactDO {

	private static final long serialVersionUID = -7687795437666540003L;
	private Integer tripServicedById;
	private BcunServicedByDO servicedByDO;
	private BcunTripDO tripDO;
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
	public BcunServicedByDO getServicedByDO() {
		return servicedByDO;
	}
	public void setServicedByDO(BcunServicedByDO servicedByDO) {
		this.servicedByDO = servicedByDO;
	}
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}
	/**
	 * @return the tripDO
	 */
	public BcunTripDO getTripDO() {
		return tripDO;
	}
	/**
	 * @param tripDO the tripDO to set
	 */
	public void setTripDO(BcunTripDO tripDO) {
		this.tripDO = tripDO;
	}	
}

