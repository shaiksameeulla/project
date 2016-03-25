package com.ff.routeserviced;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author narmdr
 *
 */
public class RouteTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	private Integer routeId;
	private Double distance;

	private CityTO originCityTO;
	private CityTO destCityTO;
	
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
	public CityTO getOriginCityTO() {
		return originCityTO;
	}
	public void setOriginCityTO(CityTO originCityTO) {
		this.originCityTO = originCityTO;
	}
	public CityTO getDestCityTO() {
		return destCityTO;
	}
	public void setDestCityTO(CityTO destCityTO) {
		this.destCityTO = destCityTO;
	}
		
}
