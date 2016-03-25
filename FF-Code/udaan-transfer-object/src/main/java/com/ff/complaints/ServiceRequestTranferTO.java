/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestTranferTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestTransferToId;
	private String transferToCode;
	private String transferToName;
	private String transferToDescription;
	
	public Integer getServiceRequestTransferToId() {
		return serviceRequestTransferToId;
	}
	public void setServiceRequestTransferToId(Integer serviceRequestTransferToId) {
		this.serviceRequestTransferToId = serviceRequestTransferToId;
	}
	public String getTransferToCode() {
		return transferToCode;
	}
	public void setTransferToCode(String transferToCode) {
		this.transferToCode = transferToCode;
	}
	public String getTransferToName() {
		return transferToName;
	}
	public void setTransferToName(String transferToName) {
		this.transferToName = transferToName;
	}
	public String getTransferToDescription() {
		return transferToDescription;
	}
	public void setTransferToDescription(String transferToDescription) {
		this.transferToDescription = transferToDescription;
	}
	
	
	

}
