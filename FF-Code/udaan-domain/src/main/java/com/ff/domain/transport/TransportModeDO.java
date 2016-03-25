/**
 * 
 */
package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author narmdr
 *
 */
public class TransportModeDO extends CGMasterDO {

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
