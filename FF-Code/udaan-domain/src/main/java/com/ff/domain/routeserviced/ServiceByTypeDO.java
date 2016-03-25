package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.transport.TransportModeDO;

/**
 * @author narmdr
 *
 */
public class ServiceByTypeDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	
	private Integer serviceByTypeId;
	private String serviceByTypeCode;
	private String serviceByTypeDesc;
	private TransportModeDO transportModeDO;
	
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
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}
}
