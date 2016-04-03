/**
 * 
 */
package com.ff.report.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InvoiceRunSheetUpdateService.
 *
 * @author abarudwa
 */
public interface InvoiceRunSheetUpdateService {
	
	/**
	 * Gets the invoice run sheet.
	 *
	 * @param invoiceRunSheetNumber the invoice run sheet number
	 * @return the invoice run sheet
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<InvoiceRunSheetTO> getInvoiceRunSheet(String invoiceRunSheetNumber) 
			throws CGBusinessException,CGSystemException;
	
	/**
	 * Save invoice run sheet.
	 *
	 * @param invoiceRunSheetTO the invoice run sheet to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InvoiceRunSheetTO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO) 
			throws CGBusinessException,CGSystemException ;
	
	/**
	 * Gets the run sheet status.
	 *
	 * @return the run sheet status
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<StockStandardTypeTO> getRunSheetStatus()
			throws CGSystemException, CGBusinessException;

}
