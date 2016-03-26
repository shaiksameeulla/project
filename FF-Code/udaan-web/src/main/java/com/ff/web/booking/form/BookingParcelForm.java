package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BookingParcelTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;

/**
 * The Class BookingParcelForm.
 */
public class BookingParcelForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8731367545646171511L;

	/**
	 * Instantiates a new booking parcel form.
	 */
	public BookingParcelForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		CashBookingParcelTO cashbookingParcelTO=new CashBookingParcelTO();
		BookingParcelTO bookingParcelTO = new BookingParcelTO();
		bookingParcelTO.setConsignee(consigneeId);
		bookingParcelTO.setConsignee(consignorId);
		bookingParcelTO.setCnPricingDtls(cnPricingDtls);

		setTo(cashbookingParcelTO);
	}

}
