package src.com.dtdc.mdbDao.expense;

/**
 * 
 */

import com.dtdc.domain.expense.MiscExpenseDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MiscExpenseMDBDAO.
 *
 * @author mohammal
 */
public interface MiscExpenseMDBDAO {
	
	/**
	 * Save expenses.
	 *
	 * @param expenseDao the expense dao
	 */
	public void saveExpenses(MiscExpenseDO expenseDao);
	
	/**
	 * Update expenses.
	 *
	 * @param expenseDao the expense dao
	 */
	public void updateExpenses(final MiscExpenseDO expenseDao);
}
