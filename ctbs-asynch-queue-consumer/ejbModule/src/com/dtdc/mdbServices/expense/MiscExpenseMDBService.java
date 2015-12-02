package src.com.dtdc.mdbServices.expense;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.expense.CnMiscExpenseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MiscExpenseMDBService.
 */
public interface MiscExpenseMDBService {
	
	
	
	/**
	 * Save misc expenses.
	 *
	 * @param miscExpenseTo the misc expense to
	 */
	void saveMiscExpenses (final CnMiscExpenseTO miscExpenseTo);
	
	/**
	 * Save misc expenses.
	 *
	 * @param miscExpenseTo the misc expense to
	 */
	void saveMiscExpenses (final CGBaseTO miscExpenseTo);
	
	/**
	 * Update expenses.
	 *
	 * @param miscExpenseTo the misc expense to
	 */
	void updateExpenses (final CnMiscExpenseTO miscExpenseTo);
	
	/**
	 * Update expenses.
	 *
	 * @param miscExpenseTo the misc expense to
	 */
	void updateExpenses(final CGBaseTO miscExpenseTo);
}
