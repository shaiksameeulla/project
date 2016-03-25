package com.ff.domain.complaints;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class ServicesDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5506056459545040894L;
	
	private Integer serviceId;
	
	private String serviceCode;
	
	private String serviceDescription;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	
	
	
}
