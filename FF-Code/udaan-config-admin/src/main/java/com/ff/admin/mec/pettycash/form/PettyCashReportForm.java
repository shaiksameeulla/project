package com.ff.admin.mec.pettycash.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.mec.pettycash.PettyCashReportTO;

/**
 * @author hkansagr
 */

public class PettyCashReportForm extends CGBaseForm {
	
	private static final long serialVersionUID = 1L;

	public PettyCashReportForm(){
		setTo(new PettyCashReportTO());
	}
}
