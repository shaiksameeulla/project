package com.ff.admin.mec.expense.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.to.mec.expense.ExpenseTO;

/**
 * @author hkansagr
 */

public interface ExpenseEntryDAO {

	/**
	 * To save or update expense details
	 * 
	 * @param domain
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateExpenseDtls(ExpenseDO domain) throws CGSystemException;

	/**
	 * To search expense details from database
	 * 
	 * @param expenseTO
	 * @return domain
	 * @throws CGSystemException
	 */
	ExpenseDO searchExpenseDtls(ExpenseTO expenseTO) throws CGSystemException;

	/**
	 * To update expense status by its (old) expense Id
	 * 
	 * @param expenseId
	 * @param expenseStatus
	 * @param sapStatus
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean updateExpenseStatus(Long expenseId, String expenseStatus,
			String sapStatus) throws CGSystemException;

	/**
	 * To get Product Details
	 * 
	 * @param productId
	 * @return productDO
	 * @throws CGSystemException
	 */
	ProductDO getProductDtls(Integer productId) throws CGSystemException;

}
