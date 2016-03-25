package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;



public class ServiceRequestTransfertoTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5506056459545040894L;
	
	private Integer serviceRequestTransfertoId;
	
	private String transfertoCode;
	
	private String transfertoDesc;
	
	private String transfertoName;

	/**
	 * @return the serviceRequestTransfertoId
	 */
	public Integer getServiceRequestTransfertoId() {
		return serviceRequestTransfertoId;
	}

	/**
	 * @param serviceRequestTransfertoId the serviceRequestTransfertoId to set
	 */
	public void setServiceRequestTransfertoId(Integer serviceRequestTransfertoId) {
		this.serviceRequestTransfertoId = serviceRequestTransfertoId;
	}

	/**
	 * @return the transfertoCode
	 */
	public String getTransfertoCode() {
		return transfertoCode;
	}

	/**
	 * @param transfertoCode the transfertoCode to set
	 */
	public void setTransfertoCode(String transfertoCode) {
		this.transfertoCode = transfertoCode;
	}

	/**
	 * @return the transfertoDesc
	 */
	public String getTransfertoDesc() {
		return transfertoDesc;
	}

	/**
	 * @param transfertoDesc the transfertoDesc to set
	 */
	public void setTransfertoDesc(String transfertoDesc) {
		this.transfertoDesc = transfertoDesc;
	}

	/**
	 * @return the transfertoName
	 */
	public String getTransfertoName() {
		return transfertoName;
	}

	/**
	 * @param transfertoName the transfertoName to set
	 */
	public void setTransfertoName(String transfertoName) {
		this.transfertoName = transfertoName;
	}
	
	
}