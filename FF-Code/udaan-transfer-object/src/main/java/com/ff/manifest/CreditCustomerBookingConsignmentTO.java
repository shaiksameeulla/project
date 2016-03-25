package com.ff.manifest;

import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.consignment.ConsignmentTO;

public class CreditCustomerBookingConsignmentTO {
	private ConsignmentTO consignmentTO;
	private CreditCustomerBookingDoxTO creditCustomerBookingDoxTO;
	private CreditCustomerBookingParcelTO creditCustomerBookingPPXTO;
	
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}
	public CreditCustomerBookingDoxTO getCreditCustomerBookingDoxTO() {
		return creditCustomerBookingDoxTO;
	}
	public void setCreditCustomerBookingDoxTO(
			CreditCustomerBookingDoxTO creditCustomerBookingDoxTO) {
		this.creditCustomerBookingDoxTO = creditCustomerBookingDoxTO;
	}
	public CreditCustomerBookingParcelTO getCreditCustomerBookingPPXTO() {
		return creditCustomerBookingPPXTO;
	}
	public void setCreditCustomerBookingPPXTO(
			CreditCustomerBookingParcelTO creditCustomerBookingPPXTO) {
		this.creditCustomerBookingPPXTO = creditCustomerBookingPPXTO;
	}
	
	
	
}
