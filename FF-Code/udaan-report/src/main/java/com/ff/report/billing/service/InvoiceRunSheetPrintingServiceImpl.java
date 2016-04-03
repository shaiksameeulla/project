/**
 * 
 */
package com.ff.report.billing.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.converter.InvoiceRunSheetPrintingConverter;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.business.CustomerTO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.organization.EmployeeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.InvoiceRunSheetTO;


// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetPrintingServiceImpl.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetPrintingServiceImpl implements InvoiceRunSheetPrintingService {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceRunSheetPrintingServiceImpl.class);
	
	/** The billing common service. */
	private BillingCommonService billingCommonService;
	private BillPrintingService billPrintingService;

	/**
	 * Sets the billing common service.
	 *
	 * @param billingCommonService the billingCommonService to set
	 */
	public void setBillingCommonService(BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	

	public void setBillPrintingService(BillPrintingService billPrintingService) {
		this.billPrintingService = billPrintingService;
	}



	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#getPickupBoys(java.lang.Integer)
	 */
	@Override
	public List<EmployeeTO> getPickupBoys(Integer officeId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getPickupBoys::START----->");
		List<EmployeeTO> employeeTOs = null;
		employeeTOs = billingCommonService.getPickupBoys(officeId);
		if (CGCollectionUtils.isEmpty(employeeTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_PICKUPBOY);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getPickupBoys::END----->");
		return employeeTOs;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#generateInvoiceRunsheetNumber(java.lang.String)
	 */
	@Override
	public String generateInvoiceRunsheetNumber(String officeCode)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::generateInvoiceRunsheetNumber::START/END----->");
		return billingCommonService.generateInvoiceRunsheetNumber(officeCode);
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#saveInvoiceRunSheet(com.ff.to.billing.InvoiceRunSheetTO)
	 */
	@Override
	public InvoiceRunSheetTO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::saveInvoiceRunSheet::START----->");
		InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO = new InvoiceRunSheetHeaderDO();
		invoiceRunSheetHeaderDO = billingCommonService.saveInvoiceRunSheet(invoiceRunSheetTO);
		invoiceRunSheetTO = InvoiceRunSheetPrintingConverter.invoiceRunsheetTOConverter(invoiceRunSheetHeaderDO);
		/*if(!isInvoiceRunSheetSaved.equalsIgnoreCase("Success")){
			throw new CGBusinessException(AdminErrorConstants.BillINVOICE_DATA_NOT_SAVED);
		}*/
		if(StringUtil.isNull(invoiceRunSheetTO)){
			throw new CGBusinessException(AdminErrorConstants.BillINVOICE_DATA_NOT_SAVED);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::saveInvoiceRunSheet::END----->");
		return invoiceRunSheetTO;
}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#getCustomers(java.lang.Integer)
	 */
	@Override
	public List<CustomerTO> getCustomers(Integer pickupBoyId,List<Integer> branchId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getCustomers::START----->");
		List<CustomerTO> customerTOs = new ArrayList<CustomerTO>();
		List<CustomerTO> billingCustomerTOs = new ArrayList<CustomerTO>();
		customerTOs = billingCommonService.getCustomers(pickupBoyId);
		billingCustomerTOs=billPrintingService.getCustomersByBillingBranch(branchId);
		//taking intersection 
		if(!CGCollectionUtils.isEmpty(customerTOs) && !CGCollectionUtils.isEmpty(billingCustomerTOs)){
		   billingCustomerTOs.retainAll(customerTOs);
	    }
	   else{
		   throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
	    }
		  
		if (CGCollectionUtils.isEmpty(billingCustomerTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getCustomers::END----->");
		return billingCustomerTOs;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#getInvoiceRunSheet(java.lang.String)
	 */
	@Override
	public List<InvoiceRunSheetTO> getInvoiceRunSheet(
			String invoiceRunSheetNumber) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getInvoiceRunSheet::START----->");
		List<InvoiceRunSheetTO> invoiceRunSheetTOs = billingCommonService.getInvoiceRunSheet(invoiceRunSheetNumber);
		if (CGCollectionUtils.isEmpty(invoiceRunSheetTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_INVOICERUNSHEET_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getInvoiceRunSheet::END----->");
		return invoiceRunSheetTOs;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#getShippedToCodesByCustomerId(java.lang.Integer)
	 */
	@Override
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getShippedToCodesByCustomerId::START----->");
		List<String> shipToCode = billingCommonService.getShippedToCodesByCustomerId(customerId);
		if (CGCollectionUtils.isEmpty(shipToCode)) {
			throw new CGBusinessException(AdminErrorConstants.NO_SHIPTOCODE_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getShippedToCodesByCustomerId::END----->");
		return shipToCode;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetPrintingService#getBillsData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillTO> getBillsData(String shipToCode, String startDate,
			String endDate) throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getBillsData::START----->");
		List<BillTO> billTOs = billingCommonService.getBillsData(shipToCode, startDate, endDate);
		if (CGCollectionUtils.isEmpty(billTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_BILLS_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetPrintingServiceImpl::getBillsData::END----->");
		return billTOs;
	}
	
	public List<BillTO> getBillsByCustomerId(Integer custId,String startDate,
			String endDate)throws CGBusinessException, CGSystemException {
		List<BillTO> billTOs =billingCommonService.getBillsByCustomerId(custId,startDate,endDate);
		if(!CGCollectionUtils.isEmpty(billTOs)){
		  return billTOs;
		}
		else{
			return null;
		}
	}
	

}
