package com.ff.admin.billing.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.billing.BulkCustModificationTO;

public class BulkCustModificationForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BulkCustModificationForm() {

		BulkCustModificationTO bulkcustModificationTO = new BulkCustModificationTO();
		setTo(bulkcustModificationTO);
	}

}
