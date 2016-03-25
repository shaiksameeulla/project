package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunPrivilegeCardDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3576938106062014401L;

	private Integer privilegeCardId;
	private Integer customerId;
	private Double balance;
	private String status = "A";
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
