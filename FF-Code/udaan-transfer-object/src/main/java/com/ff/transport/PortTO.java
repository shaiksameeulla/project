package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author narmdr
 *
 */
public class PortTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer portId;
	private String portName;
	private String portCode;
	private String portType;
	private String travelTime;
	
	private CityTO cityTO;
	private TransportModeTO transportModeTO;
	
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
	public String getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}
	public CityTO getCityTO() {
		return cityTO;
	}
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	public TransportModeTO getTransportModeTO() {
		return transportModeTO;
	}
	public void setTransportModeTO(TransportModeTO transportModeTO) {
		this.transportModeTO = transportModeTO;
	}
}
