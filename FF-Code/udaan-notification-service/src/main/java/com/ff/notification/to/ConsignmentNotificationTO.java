package com.ff.notification.to;

import java.util.List;

public class ConsignmentNotificationTO {
	private List<Integer> customersId;
	private String notifyType;
	public List<Integer> getCustomersId() {
		return customersId;
	}
	public void setCustomersId(List<Integer> customersId) {
		this.customersId = customersId;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
	
}
