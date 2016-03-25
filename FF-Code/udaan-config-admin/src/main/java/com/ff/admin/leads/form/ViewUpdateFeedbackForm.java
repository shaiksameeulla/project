package com.ff.admin.leads.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.leads.ViewUpdateFeedbackTO;

public class ViewUpdateFeedbackForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ViewUpdateFeedbackForm(){
		setTo(new ViewUpdateFeedbackTO());
	}

}
