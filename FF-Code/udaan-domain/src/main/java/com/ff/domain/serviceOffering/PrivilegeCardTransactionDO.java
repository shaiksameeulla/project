package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.booking.BookingTypeDO;

public class PrivilegeCardTransactionDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -4663558330819187483L;
	private Integer privilegeCardTransactionId;
	private PrivilegeCardDO privilegeCard;
	private BookingTypeDO bookingType;
	private Integer customerId;
	private Double amount;
	private String consgNumber;

	public Integer getPrivilegeCardTransactionId() {
		return privilegeCardTransactionId;
	}

	public void setPrivilegeCardTransactionId(Integer privilegeCardTransactionId) {
		this.privilegeCardTransactionId = privilegeCardTransactionId;
	}

	public PrivilegeCardDO getPrivilegeCard() {
		return privilegeCard;
	}

	public void setPrivilegeCard(PrivilegeCardDO privilegeCard) {
		this.privilegeCard = privilegeCard;
	}

	public BookingTypeDO getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingTypeDO bookingType) {
		this.bookingType = bookingType;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	

}