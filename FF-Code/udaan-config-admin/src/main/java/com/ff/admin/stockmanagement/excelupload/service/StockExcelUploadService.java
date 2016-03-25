/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.StockExcelUploadTO;

/**
 * @author mohammes
 *
 */
public interface StockExcelUploadService {

	/**
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Boolean saveStockExcelUpload(StockExcelUploadTO to)
			throws CGBusinessException, CGSystemException;

}
