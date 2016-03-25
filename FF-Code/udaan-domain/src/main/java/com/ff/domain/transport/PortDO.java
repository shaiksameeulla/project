package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.CityDO;

/**
 * @author narmdr
 *
 */
public class PortDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	
	private Integer portId;
	private String portName;
	private String portCode;
	private String portType;
	
	private CityDO cityDO;
	private TransportModeDO transportModeDO;
	
	public Integer getPortId() {
		return portId;
	}
	public void setPortId(Integer portId) {
		this.portId = portId;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public String getPortType() {
		return portType;
	}
	public void setPortType(String portType) {
		this.portType = portType;
	}
	public CityDO getCityDO() {
		return cityDO;
	}
	public void setCityDO(CityDO cityDO) {
		this.cityDO = cityDO;
	}
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}
	
}
