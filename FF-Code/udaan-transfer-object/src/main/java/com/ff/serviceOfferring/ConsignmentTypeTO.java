package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ConsignmentTypeTO extends CGBaseTO{
	
	private static final long serialVersionUID = 229846116482474282L;
	
	private String consignmentCode;
	private String consignmentName;
	private Integer consignmentId;
	/**
	 * @return the consignmentCode
	 */
	public String getConsignmentCode() {
		return consignmentCode;
	}
	/**
	 * @param consignmentCode the consignmentCode to set
	 */
	public void setConsignmentCode(String consignmentCode) {
		this.consignmentCode = consignmentCode;
	}
	public Integer getConsignmentId() {
		return consignmentId;
	}
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}
	/**
	 * @return the consignmentName
	 */
	public String getConsignmentName() {
		return consignmentName;
	}
	/**
	 * @param consignmentName the consignmentName to set
	 */
	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}
	

}
