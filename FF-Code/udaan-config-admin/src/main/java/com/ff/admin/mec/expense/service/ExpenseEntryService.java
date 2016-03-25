package com.ff.admin.mec.expense.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.mec.expense.ConsignmentExpenseDetailTO;
import com.ff.to.mec.expense.ExpenseTO;

/**
 * @author hkansagr
 */

public interface ExpenseEntryService {

	/**
	 * To save or update expense details
	 * 
	 * @param to
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateExpenseDtls(ExpenseTO to) throws CGBusinessException,
			CGSystemException;

	/**
	 * To search expense details from database
	 * 
	 * @param to
	 * @return expenseTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ExpenseTO searchExpenseDtls(ExpenseTO to) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get consignment details by consg no.
	 * 
	 * @param consgNo
	 * @return consgTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ConsignmentTO getConsignmentDtls(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get calculate octroi rate components
	 * 
	 * @param consgTO
	 * @return ConsignmentExpenseDetailTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ConsignmentExpenseDetailTO calculateOCTROI(ConsignmentTO consgTO) 
			throws CGBusinessException,	CGSystemException;

}
