package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BulkBookingTO;

/**
 * The Class BulkBookingForm.
 */
public class BulkBookingForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9165042435751266676L;

	/**
	 * Instantiates a new bulk booking form.
	 */
	public BulkBookingForm() {
		setTo(new BulkBookingTO());
	}
}
