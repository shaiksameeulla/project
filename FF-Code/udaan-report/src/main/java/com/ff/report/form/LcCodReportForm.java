package com.ff.report.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.report.LcCodReportTO;

public class LcCodReportForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LcCodReportForm() {

		LcCodReportTO lcCodTO = new LcCodReportTO();
		setTo(lcCodTO);
	}

}
