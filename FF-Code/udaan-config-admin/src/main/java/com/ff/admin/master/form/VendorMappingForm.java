package com.ff.admin.master.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.master.VendorMappingTO;

public class VendorMappingForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public VendorMappingForm() {

		VendorMappingTO vendorMappingTO = new VendorMappingTO();
		setTo(vendorMappingTO);
	}

}
