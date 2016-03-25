package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.transport.TransportModeDO;

/**
 * @author mrohini
 *
 */
public class RouteModeMappingDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	private Integer routeModeMappingId;
	
	private RouteDO routeDO;
	private TransportModeDO transportModeDO;
	
	public Integer getRouteModeMappingId() {
		return routeModeMappingId;
	}
	public void setRouteModeMappingId(Integer routeModeMappingId) {
		this.routeModeMappingId = routeModeMappingId;
	}
	public RouteDO getRouteDO() {
		return routeDO;
	}
	public void setRouteDO(RouteDO routeDO) {
		this.routeDO = routeDO;
	}
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}
	
	
	
}
