package com.capgemini.lbs.framework.queueUtils;

import java.io.Serializable;

public class NotificationQueueContentTO implements Serializable {

	private String consgNo;
	private String currentStatus;
	private String prevStatus;
	
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
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
}
