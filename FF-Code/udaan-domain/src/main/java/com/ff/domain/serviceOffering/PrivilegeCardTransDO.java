package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.CustomerDO;

public class PrivilegeCardTransDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -4663558330819187483L;
	private Integer privilegeCardTransId;
	private PrivilegeCardDO privilegeCardId;
	private BookingTypeDO bookinTpyeId;
	private CustomerDO customerId;
	public Double amount;

	public Integer getPrivilegeCardTransId() {
		return privilegeCardTransId;
	}

	public void setPrivilegeCardTransId(Integer privilegeCardTransId) {
		this.privilegeCardTransId = privilegeCardTransId;
	}

	public PrivilegeCardDO getPrivilegeCardId() {
		return privilegeCardId;
	}

	public void setPrivilegeCardId(PrivilegeCardDO privilegeCardId) {
		this.privilegeCardId = privilegeCardId;
	}

	public BookingTypeDO getBookinTpyeId() {
		return bookinTpyeId;
	}

	public void setBookinTpyeId(BookingTypeDO bookinTpyeId) {
		this.bookinTpyeId = bookinTpyeId;
	}

	public CustomerDO getCustomerId() {
		return customerId;
	}

	public void setCustomerId(CustomerDO customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}