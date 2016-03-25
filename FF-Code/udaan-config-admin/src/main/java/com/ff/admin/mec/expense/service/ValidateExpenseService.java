package com.ff.admin.mec.expense.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.mec.expense.ValidateExpenseTO;

public interface ValidateExpenseService {

	/**
	 * To search validate expense details
	 * 
	 * @param to
	 * @return validateExpTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ValidateExpenseTO searchValidateExpenseDtls(ValidateExpenseTO to)
		throws CGBusinessException, CGSystemException;
}
