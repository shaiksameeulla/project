/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;

/**
 * @author mohammes
 *
 */
public interface StockExcelUploadDAO {

	/**
	 * Save stock receipt list.
	 *
	 * @param receiptDoList the receipt do list
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveStockReceiptList(List<StockReceiptDO> receiptDoList)
			throws CGBusinessException, CGSystemException;

}
