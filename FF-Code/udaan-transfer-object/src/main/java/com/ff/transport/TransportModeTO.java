/**
 * 
 */
package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author narmdr
 *
 */
public class TransportModeTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer transportModeId;
	private String transportModeCode;
	private String transportModeDesc;
	
	public Integer getTransportModeId() {
		return transportModeId;
	}
	public void setTransportModeId(Integer transportModeId) {
		this.transportModeId = transportModeId;
	}
	public String getTransportModeCode() {
		return transportModeCode;
	}
	public void setTransportModeCode(String transportModeCode) {
		this.transportModeCode = transportModeCode;
	}
	public String getTransportModeDesc() {
		return transportModeDesc;
	}
	public void setTransportModeDesc(String transportModeDesc) {
		this.transportModeDesc = transportModeDesc;
	}
	
}
