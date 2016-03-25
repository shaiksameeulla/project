/**
 * 
 */
package com.ff.admin.stockmanagement.stockreturn.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;

/**
 * The Interface StockReturnDAO.
 *
 * @author cbhure
 */
public interface StockReturnDAO {

	/**
	 * Save return details.
	 *
	 * @param returnDo the return do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveReturnDetails(StockReturnDO returnDo)throws CGSystemException;  
	
	/**
	 * Find details by return number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock return do
	 * @throws CGSystemException the cG system exception
	 */
	StockReturnDO findDetailsByReturnNumber(StockReturnTO stockReturnTO) throws CGSystemException;

	/**
	 * Find details by issue number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock issue do
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueDO findDetailsByIssueNumber(StockReturnTO stockReturnTO)
			throws CGSystemException;

	/**
	 * Checks if is issue number received for return.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the long
	 * @throws CGSystemException the cG system exception
	 */
	Long isIssueNumberReceivedForReturn(StockReturnTO stockReturnTO)
			throws CGSystemException;

	/**
	 * Gets the received qnty for issue number for return.
	 *
	 * @param stockReturnTO the stock return to
	 * @param itemId the item id
	 * @return the received qnty for issue number for return
	 * @throws CGSystemException the cG system exception
	 */
	Long getReceivedQntyForIssueNumberForReturn(StockReturnTO stockReturnTO,
			Integer itemId) throws CGSystemException;

	/**
	 * Find details by ack number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock receipt do
	 * @throws CGSystemException the cG system exception
	 */
	StockReceiptDO findDetailsByAcknowledgementNumber(StockReturnTO stockReturnTO)
			throws CGSystemException;
	
}
