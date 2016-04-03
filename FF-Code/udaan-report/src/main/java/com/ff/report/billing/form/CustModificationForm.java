package com.ff.report.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.CustModificationTO;

public class CustModificationForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustModificationForm() {

		CustModificationTO custModificationTO = new CustModificationTO();
		setTo(custModificationTO);
	}

}
