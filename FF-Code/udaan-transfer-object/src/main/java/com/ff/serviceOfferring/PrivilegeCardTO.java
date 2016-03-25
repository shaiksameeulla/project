package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PrivilegeCardTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3665096465363270843L;
	private Integer privilegeCardId;
	private Integer customerId;
	private Double balance;
	private String status;
	private String privilegeCardNo;
	public Integer getPrivilegeCardId() {
		return privilegeCardId;
	}
	public void setPrivilegeCardId(Integer privilegeCardId) {
		this.privilegeCardId = privilegeCardId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrivilegeCardNo() {
		return privilegeCardNo;
	}
	public void setPrivilegeCardNo(String privilegeCardNo) {
		this.privilegeCardNo = privilegeCardNo;
	}
	
	
}
