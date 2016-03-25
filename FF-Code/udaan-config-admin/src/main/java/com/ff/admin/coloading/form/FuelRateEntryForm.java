package com.ff.admin.coloading.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.coloading.FuelRateEntryTO;

public class FuelRateEntryForm extends CGBaseForm {

	
	private static final long serialVersionUID = 8350391868090719968L;

	public FuelRateEntryForm(){
		setTo( new FuelRateEntryTO());
	}
}
