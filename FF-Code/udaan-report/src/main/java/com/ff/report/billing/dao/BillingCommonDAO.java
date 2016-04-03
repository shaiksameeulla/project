package com.ff.report.billing.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.FinancialProductDO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;


// TODO: Auto-generated Javadoc
/**
 * The Interface BillingCommonDAO.
 *
 * @author narmdr
 */
public interface BillingCommonDAO {
	
	/**
	 * Save invoice run sheet.
	 *
	 * @param invoiceRunSheetHeaderDO the invoice run sheet header do
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InvoiceRunSheetHeaderDO saveInvoiceRunSheet(InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO)throws CGBusinessException, CGSystemException ;
	
	/**
	 * Gets the invoice run sheet.
	 *
	 * @param invoiceRunSheetNumber the invoice run sheet number
	 * @return the invoice run sheet
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<InvoiceRunSheetHeaderDO> getInvoiceRunSheet(final String invoiceRunSheetNumber) throws CGBusinessException,
	CGSystemException;

	/**
	 * Gets the consignment for rate.
	 *
	 * @return the consignment for rate
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<ConsignmentBilling> getConsignmentForRate(Long limit)throws CGBusinessException,
	CGSystemException;
	
	public ProductDO getProduct(Integer productId)throws CGBusinessException,
	CGSystemException;
	
	public OfficeDO getOffice(Integer officeId)throws CGBusinessException,
	CGSystemException;
	
	public CityDO getCityByOffice(Integer cityId)throws CGBusinessException,
	CGSystemException;
	
	
	public BookingDO getCustomerFromTypeBooking(String consgNo)throws CGBusinessException,
	CGSystemException;
	
	public boolean UpdateConsignmentBillingStatus(String consgNo)throws CGBusinessException,
	CGSystemException;
	
	public boolean billing_consolidation_Proc()throws CGBusinessException,
	CGSystemException;
	
	public ConsignmentBillingRateDO saveOrUpdateConsgRate(ConsignmentBillingRateDO consignmentBillingRateDO,String consgNo)throws CGBusinessException,
	CGSystemException;
	
	public ConsignmentBillingRateDO getAlreadyExistConsgRate(ConsignmentDO consingnment,String rateFor)throws CGBusinessException,
	CGSystemException;
	
	public Long getLimitOfRecordProcessedForBilling()throws CGSystemException;
	
	public Long getTotalCNForBillingJob()throws CGSystemException;
	
	public boolean billing_Stock_consolidation_Proc()throws CGBusinessException,
	CGSystemException;
	
	public List<FinancialProductDO> getAllFinancialProducts() throws CGSystemException;
}
