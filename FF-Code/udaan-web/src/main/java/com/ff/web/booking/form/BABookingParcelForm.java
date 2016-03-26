package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.BABookingParcelTO;

/**
 * The Class BABookingParcelForm.
 */
public class BABookingParcelForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new bA booking parcel form.
	 */
	public BABookingParcelForm(){
		setTo(new BABookingParcelTO());
		
	}
}