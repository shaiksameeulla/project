package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.booking.BookingTypeTO;

public class PrivilegeCardTransactionTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4942318139218723478L;
	private Integer privilegeCardTransactionId;
	private PrivilegeCardTO privilegeCardTO;
	private BookingTypeTO bookingTypeTO;
	private Integer customerId;
	public Double amount;

	public Integer getPrivilegeCardTransactionId() {
		return privilegeCardTransactionId;
	}

	public void setPrivilegeCardTransactionId(Integer privilegeCardTransactionId) {
		this.privilegeCardTransactionId = privilegeCardTransactionId;
	}

	public PrivilegeCardTO getPrivilegeCardTO() {
		return privilegeCardTO;
	}

	public void setPrivilegeCardTO(PrivilegeCardTO privilegeCardTO) {
		this.privilegeCardTO = privilegeCardTO;
	}

	public BookingTypeTO getBookingTypeTO() {
		return bookingTypeTO;
	}

	public void setBookingTypeTO(BookingTypeTO bookingTypeTO) {
		this.bookingTypeTO = bookingTypeTO;
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

}
