package com.ff.web.booking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.booking.CreditCustomerBookingDoxTO;

/**
 * The Class CreditCustomerBookingDoxForm.
 */
public class CreditCustomerBookingDoxForm extends CGBaseForm  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7181057486084209872L;

	/**
	 * Instantiates a new credit customer booking dox form.
	 */
	public CreditCustomerBookingDoxForm() {
		CreditCustomerBookingDoxTO creditCustBookingDoxTO =new CreditCustomerBookingDoxTO();
		//creditCustBookingDoxTO.setCustomerTO(customerTO);
		setTo(creditCustBookingDoxTO);
	}

}
