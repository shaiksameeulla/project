package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.FOCBookingDoxTO;
import com.ff.business.ConsignorConsigneeTO;

/**
 * The Class FOCBookingDoxForm.
 */
public class FOCBookingDoxForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4725382041063880440L;

	/**
	 * Instantiates a new fOC booking dox form.
	 */
	public FOCBookingDoxForm() {
		ConsignorConsigneeTO consigneeId = new ConsignorConsigneeTO();
		ConsignorConsigneeTO consignorId = new ConsignorConsigneeTO();
		FOCBookingDoxTO focBookingDoxTO = new FOCBookingDoxTO();
		focBookingDoxTO.setConsignee(consigneeId);
		focBookingDoxTO.setConsignor(consignorId);
		setTo(focBookingDoxTO);
	}
}
