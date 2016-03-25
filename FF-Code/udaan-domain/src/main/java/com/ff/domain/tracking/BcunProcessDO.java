package com.ff.domain.tracking;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunProcessDO extends CGFactDO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8188636560774364456L;
	
	
	private Integer processId;
	private String processCode;
	private String processName;
	private String processDesc;
	private Integer processOrder;
	private String trackingTxt;
	
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	/**
	 * @return the processOrder
	 */
	public Integer getProcessOrder() {
		return processOrder;
	}
	/**
	 * @param processOrder the processOrder to set
	 */
	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}
	/**
	 * @return the trackingTxt
	 */
	public String getTrackingTxt() {
		return trackingTxt;
	}
	/**
	 * @param trackingTxt the trackingTxt to set
	 */
	public void setTrackingTxt(String trackingTxt) {
		this.trackingTxt = trackingTxt;
	}

}
