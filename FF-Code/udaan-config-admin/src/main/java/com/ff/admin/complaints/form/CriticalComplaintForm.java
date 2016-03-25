package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.CriticalComplaintTO;

/**
 * @author hkansagr
 */
public class CriticalComplaintForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public CriticalComplaintForm() {
		setTo(new CriticalComplaintTO());
	}

}
