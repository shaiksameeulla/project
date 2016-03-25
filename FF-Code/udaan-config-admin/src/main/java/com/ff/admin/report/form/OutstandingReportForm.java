package com.ff.admin.report.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.admin.report.to.OutstandingReportTO;

/**
 * @author khassan
 *
 */
public class OutstandingReportForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6309888647292994186L;
	
	private OutstandingReportTO to;
	
	public OutstandingReportForm(){
		super();
		to=new OutstandingReportTO();
		setTo(to);
	}

}
