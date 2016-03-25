package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.CityDO;

/**
 * @author narmdr
 *
 */
public class RouteDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	private Integer routeId;
	private Double distance;

	private CityDO originCityDO;
	private CityDO destCityDO;
	
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public CityDO getOriginCityDO() {
		return originCityDO;
	}
	public void setOriginCityDO(CityDO originCityDO) {
		this.originCityDO = originCityDO;
	}
	public CityDO getDestCityDO() {
		return destCityDO;
	}
	public void setDestCityDO(CityDO destCityDO) {
		this.destCityDO = destCityDO;
	}
	
}
