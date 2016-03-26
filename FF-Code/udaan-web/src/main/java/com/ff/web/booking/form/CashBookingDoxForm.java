package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.business.ConsignorConsigneeTO;

/**
 * The Class CashBookingDoxForm.
 */
public class CashBookingDoxForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3057205588451553522L;

	/**
	 * Instantiates a new cash booking dox form.
	 */
	public CashBookingDoxForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		BookingPaymentTO bookingPayment = new BookingPaymentTO();
		CashBookingDoxTO cashBookingTO = new CashBookingDoxTO();
		cashBookingTO.setConsignee(consigneeId);
		cashBookingTO.setConsignor(consignorId);
		cashBookingTO.setCnPricingDtls(cnPricingDtls);
		cashBookingTO.setBookingPayment(bookingPayment);
		setTo(cashBookingTO);
	}
}
