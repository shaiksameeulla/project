package com.ff.admin.mec.expense.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.mec.expense.ValidateExpenseTO;

/**
 * @author hkansagr
 */

public class ValidateExpenseForm extends CGBaseForm{

	private static final long serialVersionUID = 1L;

	public ValidateExpenseForm(){
		setTo(new ValidateExpenseTO());
	}
}
