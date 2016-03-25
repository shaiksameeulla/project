package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.business.CustomerDO;

public class PrivilegeCardDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3576938106062014401L;
	
	private Integer privilegeCardId;
	private BookingTypeDO bookingTypeId;
	private CustomerDO customerId;
	private double balance;
	private double availableBal;
	private String status;
	
	public Integer getPrivilegeCardId() {
		return privilegeCardId;
	}
	public void setPrivilegeCardId(Integer privilegeCardId) {
		this.privilegeCardId = privilegeCardId;
	}
	public BookingTypeDO getBookingTypeId() {
		return bookingTypeId;
	}
	public void setBookingTypeId(BookingTypeDO bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	public CustomerDO getCustomerId() {
		return customerId;
	}
	public void setCustomerId(CustomerDO customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAvailableBal() {
		return availableBal;
	}
	public void setAvailableBal(double availableBal) {
		this.availableBal = availableBal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
