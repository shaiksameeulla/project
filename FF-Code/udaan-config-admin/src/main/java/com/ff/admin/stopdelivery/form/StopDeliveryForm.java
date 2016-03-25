package com.ff.admin.stopdelivery.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.utilities.StopDeliveryTO;

public class StopDeliveryForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 632057966791684141L;

	public StopDeliveryForm(){
		StopDeliveryTO stopDeliveryTO = new StopDeliveryTO();
		setTo(stopDeliveryTO);
	}
}
