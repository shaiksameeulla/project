package com.ff.web.loadmanagement.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadDispatchDetailsTO;

/**
 * The Class LoadDispatchForm.
 *
 * @author narmdr
 */
public class LoadDispatchForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new load dispatch form.
	 */
	public LoadDispatchForm() {
		setTo(new LoadDispatchDetailsTO());
	}
}
