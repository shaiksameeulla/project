package com.capgemini.lbs.framework.queueUtils;

import java.util.List;

public class ConsignmentNotificationQueueTO {
	private String consignmentNo;
	private String currentStatus;
	private String prevStatus;
	private List<String> allowedProducts;
	private List<String> allowedStatus;
	
	public String getConsignmentNo() {
		return consignmentNo;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getPrevStatus() {
		return prevStatus;
	}
	public void setPrevStatus(String prevStatus) {
		this.prevStatus = prevStatus;
	}
	public List<String> getAllowedProducts() {
		return allowedProducts;
	}
	public void setAllowedProducts(List<String> allowedProducts) {
		this.allowedProducts = allowedProducts;
	}
	public List<String> getAllowedStatus() {
		return allowedStatus;
	}
	public void setAllowedStatus(List<String> allowedStatus) {
		this.allowedStatus = allowedStatus;
	}
	
}
