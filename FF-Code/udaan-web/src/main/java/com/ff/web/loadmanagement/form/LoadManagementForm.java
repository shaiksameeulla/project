package com.ff.web.loadmanagement.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadMovementTO;

/**
 * The Class LoadManagementForm.
 *
 * @author narmdr
 */
public class LoadManagementForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1163640713775117361L;

	/**
	 * Instantiates a new load management form.
	 */
	public LoadManagementForm() {
		setTo(new LoadMovementTO());
	}
}
