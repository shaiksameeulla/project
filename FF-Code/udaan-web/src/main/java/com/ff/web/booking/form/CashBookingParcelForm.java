package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;

/**
 * The Class CashBookingParcelForm.
 */
public class CashBookingParcelForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -750264608254380325L;

	/**
	 * Instantiates a new cash booking parcel form.
	 */
	public CashBookingParcelForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		CNContentTO cnContent = new CNContentTO();
		CNPaperWorksTO cnPaperWork = new CNPaperWorksTO();
		BookingPaymentTO bookPayment = new BookingPaymentTO();
		CashBookingParcelTO cashBookingParcelTO = new CashBookingParcelTO();
		cashBookingParcelTO.setConsignee(consigneeId);
		cashBookingParcelTO.setConsignor(consignorId);
		cashBookingParcelTO.setCnPricingDtls(cnPricingDtls);
		cashBookingParcelTO.setCnContents(cnContent);
		cashBookingParcelTO.setBookingPayment(bookPayment);
		cashBookingParcelTO.setCnPaperWorks(cnPaperWork);
		setTo(cashBookingParcelTO);
	}
}
