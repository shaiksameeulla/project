package com.ff.admin.coloading.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.coloading.ColoadingVehicleContractTO;

public class VehiclesContractColoadingForm extends CGBaseForm {
	
	private static final long serialVersionUID = 8350391868090719968L;

	public VehiclesContractColoadingForm(){
		setTo(new ColoadingVehicleContractTO());
	}
	
}
