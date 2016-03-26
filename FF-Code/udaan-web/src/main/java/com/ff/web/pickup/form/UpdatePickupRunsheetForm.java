package com.ff.web.pickup.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.pickup.UpdatePickupRunsheetTO;

/**
 * The Class UpdatePickupRunsheetForm.
 */
public class UpdatePickupRunsheetForm extends CGBaseForm {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8011766626579257959L;
	
	/**
	 * Instantiates a new update pickup runsheet form.
	 */
	public UpdatePickupRunsheetForm() {
		setTo(new UpdatePickupRunsheetTO());
	}
}
