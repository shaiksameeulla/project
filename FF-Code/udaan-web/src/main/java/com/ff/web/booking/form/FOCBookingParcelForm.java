package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.FOCBookingParcelTO;

import com.ff.business.ConsignorConsigneeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;

/**
 * The Class FOCBookingParcelForm.
 */
public class FOCBookingParcelForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4111135969360625196L;
	
	/**
	 * Instantiates a new fOC booking parcel form.
	 */
	public FOCBookingParcelForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		CNPricingDetailsTO cnPricingDtls = new CNPricingDetailsTO();
		CNContentTO cnContent = new CNContentTO();
		CNPaperWorksTO cnPaperWork = new CNPaperWorksTO();
		FOCBookingParcelTO focBookingParcelTO = new FOCBookingParcelTO();
		focBookingParcelTO.setConsignee(consigneeId);
		focBookingParcelTO.setConsignor(consignorId);
		focBookingParcelTO.setCnPricingDtls(cnPricingDtls);
		focBookingParcelTO.setCnContents(cnContent);
		focBookingParcelTO.setCnPaperWorks(cnPaperWork);
		setTo(focBookingParcelTO);
	}
}
