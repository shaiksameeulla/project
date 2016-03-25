package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mrohini
 *
 */
public class BcunRouteModeMappingDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	private Integer routeModeMappingId;
	
	private Integer routeId;
	private Integer transportModeId;
	
	public Integer getRouteModeMappingId() {
		return routeModeMappingId;
	}
	public void setRouteModeMappingId(Integer routeModeMappingId) {
		this.routeModeMappingId = routeModeMappingId;
	}
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Integer getTransportModeId() {
		return transportModeId;
	}
	public void setTransportModeId(Integer transportModeId) {
		this.transportModeId = transportModeId;
	}
	
	
}
