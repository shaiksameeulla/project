package com.ff.web.loadmanagement.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadReceiveEditBagTO;

/**
 * The Class LoadReceiveEditBagForm.
 */
public class LoadReceiveEditBagForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6336137155143410627L;

	/**
	 * Instantiates a new load receive edit bag form.
	 */
	public LoadReceiveEditBagForm() {
		setTo(new LoadReceiveEditBagTO());
	}
	
	
}
