package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;


/**
 * The Class BcunRouteDO.
 *
 * @author narmdr
 */
public class BcunRouteDO extends CGMasterDO {

	private static final long serialVersionUID = -5692723326095059524L;

	private Integer routeId;
	private Double distance;
	private Integer originCityId;
	private Integer destCityId;
	
	/**
	 * @return the originCityId
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}
	/**
	 * @param originCityId the originCityId to set
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}
	/**
	 * @return the destCityId
	 */
	public Integer getDestCityId() {
		return destCityId;
	}
	/**
	 * @param destCityId the destCityId to set
	 */
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	/**
	 * @return the routeId
	 */
	public Integer getRouteId() {
		return routeId;
	}
	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
