package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BABookingDoxTO;

/**
 * The Class BABookingDoxForm.
 */
public class BABookingDoxForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4638205736137972127L;

	/**
	 * Instantiates a new Business Associate booking dox form.
	 */
	public BABookingDoxForm(){
			
		setTo(new BABookingDoxTO());
		
	}
}
