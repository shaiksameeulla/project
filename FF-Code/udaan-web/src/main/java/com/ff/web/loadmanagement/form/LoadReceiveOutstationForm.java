package com.ff.web.loadmanagement.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadReceiveOutstationTO;

/**
 * The Class LoadReceiveOutstationForm.
 */
public class LoadReceiveOutstationForm extends CGBaseForm{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7918915975501210386L;

	/**
	 * Instantiates a new load receive outstation form.
	 */
	public LoadReceiveOutstationForm() {
		setTo(new LoadReceiveOutstationTO());
	}
}
