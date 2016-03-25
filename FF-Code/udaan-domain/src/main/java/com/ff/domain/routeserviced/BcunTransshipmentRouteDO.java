package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author narmdr
 *
 */
public class BcunTransshipmentRouteDO extends CGMasterDO {

	private static final long serialVersionUID = -6684236292291731586L;
	private Integer transshipmentRouteId;
	private Integer transshipmentCityId;
	private Integer servicedCityId;
	private String active;

	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Integer getTransshipmentRouteId() {
		return transshipmentRouteId;
	}
	public void setTransshipmentRouteId(Integer transshipmentRouteId) {
		this.transshipmentRouteId = transshipmentRouteId;
	}
	public Integer getTransshipmentCityId() {
		return transshipmentCityId;
	}
	public void setTransshipmentCityId(Integer transshipmentCityId) {
		this.transshipmentCityId = transshipmentCityId;
	}
	public Integer getServicedCityId() {
		return servicedCityId;
	}
	public void setServicedCityId(Integer servicedCityId) {
		this.servicedCityId = servicedCityId;
	}
}
