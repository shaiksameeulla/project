package com.ff.web.pickup.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.pickup.PickupRunsheetTO;

public class PickupRunsheetForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8011766626579257959L;
	
	public PickupRunsheetForm() {
		OfficeTypeTO officeTypeTO=new OfficeTypeTO();
		OfficeTO loginOfficeTO=new OfficeTO();
		loginOfficeTO.setOfficeTypeTO(officeTypeTO);
		PickupRunsheetTO pickupRunsheetTO = new PickupRunsheetTO();
		pickupRunsheetTO.setLoginOfficeTO(loginOfficeTO);
		setTo(pickupRunsheetTO);
	}
}
