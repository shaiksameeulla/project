package com.ff.web.loadmanagement.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadReceiveLocalTO;

/**
 * The Class LoadReceiveLocalForm.
 *
 * @author narmdr
 */
public class LoadReceiveLocalForm extends CGBaseForm {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7238519131428218401L;

	/**
	 * Instantiates a new load receive local form.
	 */
	public LoadReceiveLocalForm() {
		setTo(new LoadReceiveLocalTO());
	}
}
