package com.ff.admin.mec.expense.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.to.mec.expense.ExpenseTO;

/**
 * @author hkansagr
 */

public class ExpenseEntryForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public ExpenseEntryForm(){
		setTo(new ExpenseTO());
	}
	
}
