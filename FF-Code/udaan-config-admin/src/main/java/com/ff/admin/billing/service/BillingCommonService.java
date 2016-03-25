package com.ff.admin.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.domain.booking.BookingDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.FinancialProductTO;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface BillingCommonService.
 *
 * @author narmdr
 */
public interface BillingCommonService {

	// getEmployeesByOfficeId
	/**
	 * Gets the pickup boys(Get Employees By OfficeId).
	 *
	 * @param officeId the office id
	 * @return the pickup boys
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<EmployeeTO> getPickupBoys(final Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the regions.
	 *
	 * @return the regions
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the products.
	 *
	 * @return the products
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<FinancialProductTO> getProducts() throws CGBusinessException, CGSystemException;

	// getStations
	/**
	 * Gets the cities by region id.
	 *
	 * @param regionId the region id
	 * @return the cities by region id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException;

	// getBranches
	/**
	 * Gets the offices by city id.
	 *
	 * @param cityId the city id
	 * @return the offices by city id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getOfficesByCityId(final Integer cityId)
			throws CGBusinessException, CGSystemException;

	// getCustomers
	/**
	 * Gets the customers by office id.
	 *
	 * @param officeId the office id
	 * @return the customers by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CustomerTO> getCustomersByOfficeId(final Integer officeId)
			throws CGBusinessException, CGSystemException;

	// getStationaryTypes
	/**
	 * Gets the standard types by type name.
	 *
	 * @param typeName the type name
	 * @return the standard types by type name
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<StockStandardTypeTO> getStandardTypesByTypeName(final String typeName)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Generate invoice runsheet number.
	 *
	 * @param officeCode the office code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String generateInvoiceRunsheetNumber(final String officeCode)
			throws CGBusinessException, CGSystemException;
	

	/////getShipToCodes
	/**
	 * Gets the shipped to codes by customer id.
	 * Gets the ship to codes from contract.
	 *
	 * @param customerId the customer id
	 * @return the ship to codes
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getShippedToCodesByCustomerId(final Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the customers from Pickup Runsheet.
	 *
	 * @param pickupBoyId the pickup boy id or EmpId
	 * @return the customers
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CustomerTO> getCustomers(final Integer pickupBoyId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the bills(List of Bill no. or invoice no.).
	 *
	 * @param shipToCode the ship to code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the bills
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getBills(final String shipToCode, final String startDate,
			final String endDate) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data.
	 *
	 * @param invoiceNos the invoice nos
	 * @return the bills data
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<BillTO> getBillsData(final List<String> invoiceNos)
			throws CGBusinessException, CGSystemException;

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
	List<BillTO> getBillsData(final String shipToCode, final String startDate,
			final String endDate) throws CGBusinessException, CGSystemException;
	
	/**
	 * Save invoice run sheet.
	 *
	 * @param invoiceRunSheetTO the invoice run sheet to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InvoiceRunSheetHeaderDO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO) throws CGBusinessException,
	CGSystemException ;
	
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
	 * Gets the run sheet status.
	 *
	 * @return the run sheet status
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<StockStandardTypeTO> getRunSheetStatus()
			throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the consignments for rate.
	 *
	 * @return the consignments for rate
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void getConsignmentsForRate()throws CGBusinessException,CGSystemException,InterruptedException;
	
	/**
	 * To get Product by its Id
	 * 
	 * @param productId
	 * @return product
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ProductTO getProduct(Integer productId) 
			throws CGBusinessException, CGSystemException;
	
	public OfficeTO getOffice(Integer officeId)throws CGBusinessException,
	CGSystemException;
	
	public CityTO getCityByOffice(Integer cityId)throws CGBusinessException,
	CGSystemException;
	
	
	public void UpdateConsignmentBillingStatus(String consgNo)throws CGBusinessException,
	CGSystemException;
	
	
	public boolean billing_consolidation_Proc()throws CGBusinessException,
	CGSystemException;
	
	public BookingDO getConsgBookingDetails(String consgNo)throws CGBusinessException,
	CGSystemException;
	
	public void calcRateForCNInConsgRate(Long limit)throws CGBusinessException,CGSystemException,InterruptedException;
	
	public boolean billing_Stock_consolidation_Proc()throws CGBusinessException,
	CGSystemException;
	
	public List<BillTO> getBillsByCustomerId(Integer custId,String startDate,String endDate)throws CGBusinessException, CGSystemException;
}
