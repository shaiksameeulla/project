package com.ff.web.drs.common.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.drs.DeliveryTO;


public class DeliveryDrsForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DeliveryTO to=null;
	
	public DeliveryDrsForm() {
		super();
		to = new DeliveryTO();
		setTo(to);
	}

	
}
