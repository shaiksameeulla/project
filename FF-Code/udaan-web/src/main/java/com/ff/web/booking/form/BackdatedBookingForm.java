package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BackdatedBookingTO;


/**
 * The Class BackdatedBookingForm.
 */
public class BackdatedBookingForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2035579651660292749L;

	/**
	 * Instantiates a new backdated booking form.
	 */
	public BackdatedBookingForm() {
		setTo(new BackdatedBookingTO());
	}
}