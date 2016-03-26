/**
 * 
 */
package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.CreditCustomerBookingParcelTO;

/**
 * The Class CreditCustomerBookingParcelForm.
 *
 * @author uchauhan
 */
public class CreditCustomerBookingParcelForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new credit customer booking parcel form.
	 */
	public CreditCustomerBookingParcelForm() {
		setTo(new CreditCustomerBookingParcelTO());

	}

}
