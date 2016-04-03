/**
 * 
 */
package com.ff.report.billing.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.report.billing.converter.InvoiceRunSheetPrintingConverter;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetUpdateServiceImpl.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetUpdateServiceImpl implements InvoiceRunSheetUpdateService{
	
/** The Constant LOGGER. */
private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceRunSheetUpdateServiceImpl.class);
	
	/** The billing common service. */
	private BillingCommonService billingCommonService;
	
	/**
	 * Sets the billing common service.
	 *
	 * @param billingCommonService the billingCommonService to set
	 */
	public void setBillingCommonService(BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetUpdateService#getInvoiceRunSheet(java.lang.String)
	 */
	@Override
	public List<InvoiceRunSheetTO> getInvoiceRunSheet(
			String invoiceRunSheetNumber) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::getInvoiceRunSheet::START----->");
		List<InvoiceRunSheetTO> invoiceRunSheetTOs = billingCommonService.getInvoiceRunSheet(invoiceRunSheetNumber);
		if (CGCollectionUtils.isEmpty(invoiceRunSheetTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_INVOICERUNSHEET_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::getInvoiceRunSheet::END----->");
		return invoiceRunSheetTOs;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetUpdateService#saveInvoiceRunSheet(com.ff.to.billing.InvoiceRunSheetTO)
	 */
	@Override
	public InvoiceRunSheetTO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::saveInvoiceRunSheet::START----->");
		InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO = new InvoiceRunSheetHeaderDO();
		invoiceRunSheetHeaderDO = billingCommonService.saveInvoiceRunSheet(invoiceRunSheetTO);
		invoiceRunSheetTO = InvoiceRunSheetPrintingConverter.invoiceRunsheetTOConverter(invoiceRunSheetHeaderDO);
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::saveInvoiceRunSheet::END----->");
		return invoiceRunSheetTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.InvoiceRunSheetUpdateService#getRunSheetStatus()
	 */
	@Override
	public List<StockStandardTypeTO> getRunSheetStatus()
			throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::getRunSheetStatus::START/END----->");
		return billingCommonService.getRunSheetStatus();
	}
	
	
	
	

}
