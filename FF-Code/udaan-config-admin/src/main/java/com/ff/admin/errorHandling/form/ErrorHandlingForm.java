package com.ff.admin.errorHandling.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.errorHandling.ErrorHandlingTo;

public class ErrorHandlingForm extends CGBaseForm{

	private static final long serialVersionUID = 1L;
	
	public ErrorHandlingForm() {		
		ErrorHandlingTo errorHandlingTo = new ErrorHandlingTo();
		setTo(errorHandlingTo);
	}

}
