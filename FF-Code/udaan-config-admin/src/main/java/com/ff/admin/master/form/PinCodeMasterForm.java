package com.ff.admin.master.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.master.PinCodeMasterTO;
import com.ff.to.billing.BillPrintingTO;

public class PinCodeMasterForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276371201261660444L;

	public PinCodeMasterForm() {
		PinCodeMasterTO pinCodeMasterTO = new PinCodeMasterTO();
		setTo(pinCodeMasterTO);

	}
}
