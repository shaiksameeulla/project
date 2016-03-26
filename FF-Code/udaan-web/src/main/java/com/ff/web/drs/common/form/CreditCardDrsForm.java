package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.CreditCardDrsTO;

/**
 * @author nihsingh
 *
 */
public class CreditCardDrsForm extends CGBaseForm{

	private static final long serialVersionUID = 1L;
	
	/** The drs to. */
	private CreditCardDrsTO to = null;

	/**
	 * Instantiates a new prepare CC and Q series dox drs form.
	 */
	public CreditCardDrsForm() {
		super();
		to = new CreditCardDrsTO();
		setTo(to);
	}
}
