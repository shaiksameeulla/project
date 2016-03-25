package com.ff.routeserviced;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.transport.TransportModeTO;

/**
 * @author narmdr
 *
 */
public class ServiceByTypeTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer serviceByTypeId;
	private String serviceByTypeCode;
	private String serviceByTypeDesc;
	private TransportModeTO transportModeTO;
	
	public Integer getServiceByTypeId() {
		return serviceByTypeId;
	}
	public void setServiceByTypeId(Integer serviceByTypeId) {
		this.serviceByTypeId = serviceByTypeId;
	}
	public String getServiceByTypeCode() {
		return serviceByTypeCode;
	}
	public void setServiceByTypeCode(String serviceByTypeCode) {
		this.serviceByTypeCode = serviceByTypeCode;
	}
	public String getServiceByTypeDesc() {
		return serviceByTypeDesc;
	}
	public void setServiceByTypeDesc(String serviceByTypeDesc) {
		this.serviceByTypeDesc = serviceByTypeDesc;
	}
	public TransportModeTO getTransportModeTO() {
		return transportModeTO;
	}
	public void setTransportModeTO(TransportModeTO transportModeTO) {
		this.transportModeTO = transportModeTO;
	}
}
