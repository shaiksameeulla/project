package com.ff.admin.mec.expense.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.to.mec.expense.ValidateExpenseTO;

public interface ValidateExpenseDAO {

	/**
	 * To search Validate Expense Details
	 * 
	 * @param to
	 * @return expenseDOs
	 * @throws CGSystemException
	 */
	List<ExpenseDO> searchValidateExpenseDtls(ValidateExpenseTO to)
		throws CGSystemException;
	
}
