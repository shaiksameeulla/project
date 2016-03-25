/**
 * 
 */
package com.ff.admin.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.InvoiceRunSheetTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InvoiceRunSheetPrintingService.
 *
 * @author abarudwa
 */
public interface InvoiceRunSheetPrintingService {
	
	/**
	 * Gets the pickup boys.
	 *
	 * @param officeId the office id
	 * @return the pickup boys
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<EmployeeTO> getPickupBoys(Integer officeId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Generate invoice runsheet number.
	 *
	 * @param officeCode the office code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String generateInvoiceRunsheetNumber(String officeCode)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save invoice run sheet.
	 *
	 * @param invoiceRunSheetTO the invoice run sheet to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InvoiceRunSheetTO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO) throws CGBusinessException,
	CGSystemException ;
	
	/**
	 * Gets the customers.
	 *
	 * @param pickupBoyId the pickup boy id
	 * @return the customers
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<CustomerTO> getCustomers(Integer pickupBoyId,List<Integer> branchId)
			throws CGBusinessException, CGSystemException;
	
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
	 * Gets the shipped to codes by customer id.
	 *
	 * @param customerId the customer id
	 * @return the shipped to codes by customer id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException ;
	
	/**
	 * Gets the bills data.
	 *
	 * @param shipToCode the ship to code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the bills data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<BillTO> getBillsData(String shipToCode, String startDate,
			String endDate) throws CGBusinessException, CGSystemException ;
	
	public List<BillTO> getBillsByCustomerId(Integer custId,String startDate,
			String endDate)throws CGBusinessException, CGSystemException ;
}
