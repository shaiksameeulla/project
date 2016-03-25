package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.CriticalClaimComplaintTO;

/**
 * @author cbhure
 *
 */

public class CriticalClaimComplaintForm extends CGBaseForm {

	
	private static final long serialVersionUID = -4735656656196056880L;

	public CriticalClaimComplaintForm() {
		setTo(new CriticalClaimComplaintTO());
	}

}
