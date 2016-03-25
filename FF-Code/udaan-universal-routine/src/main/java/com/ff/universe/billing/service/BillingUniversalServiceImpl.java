package com.ff.universe.billing.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.BillDO;
import com.ff.to.billing.BillTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.universe.billing.converter.BillingUniversalConverter;
import com.ff.universe.billing.dao.BillingUniversalDAO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.ratemanagement.service.RateUniversalService;

// TODO: Auto-generated Javadoc
/**
 * The Class BillingUniversalServiceImpl.
 * 
 * @author narmdr
 */
public class BillingUniversalServiceImpl implements BillingUniversalService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillingUniversalServiceImpl.class);

	/** The billing universal dao. */
	private transient BillingUniversalDAO billingUniversalDAO;

	/** The rate universal service. */
	private transient RateUniversalService rateUniversalService;

	/** The consignment common service. */
	private transient ConsignmentCommonService consignmentCommonService;

	/**
	 * Sets the billing universal dao.
	 * 
	 * @param billingUniversalDAO
	 *            the billingUniversalDAO to set
	 */
	public void setBillingUniversalDAO(BillingUniversalDAO billingUniversalDAO) {
		this.billingUniversalDAO = billingUniversalDAO;
	}

	/**
	 * Sets the rate universal service.
	 * 
	 * @param rateUniversalService
	 *            the rateUniversalService to set
	 */
	public void setRateUniversalService(
			RateUniversalService rateUniversalService) {
		this.rateUniversalService = rateUniversalService;
	}

	/**
	 * Sets the consignment common service.
	 * 
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getBillsDataByCustomerId(java.lang.Integer)
	 */
	@Override
	public List<BillTO> getBillsDataByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getBillsDataByCustomerId() :: Start --------> ::::::");

		List<BillDO> billDOs = billingUniversalDAO
				.getBillsByCustomerId(customerId);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getBillsDataByCustomerId() :: End --------> ::::::");
		return billTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.service.BillingUniversalService#getBillsData(
	 * java.util.List)
	 */
	@Override
	public List<BillTO> getBillsData(List<String> invoiceNumbers)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: Start --------> ::::::");

		List<BillDO> billDOs = billingUniversalDAO
				.getBillsByInvoiceNos(invoiceNumbers);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: End --------> ::::::");
		return billTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.service.BillingUniversalService#getBillsData(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillTO> getBillsData(String shippedToCode, String startDateStr,
			String endDateStr) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: Start --------> ::::::");
		Date startDate = DateUtil.stringToDDMMYYYYFormat(startDateStr);
		Date endDate = DateUtil.stringToDDMMYYYYFormat(endDateStr);

		List<BillDO> billDOs = billingUniversalDAO
				.getBillsByShippedToCodeAndStartEndDate(shippedToCode,
						startDate, endDate);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: End --------> ::::::");
		return billTOs;
	}

	public List<BillTO> getBillsByCustomerId(Integer custId, String startDt,
			String endDt) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getBillsByCustomerId() :: Start --------> ::::::");
		Date startDate = DateUtil.stringToDDMMYYYYFormat(startDt);
		Date endDate = DateUtil.stringToDDMMYYYYFormat(endDt);
		List<BillDO> billDOs = billingUniversalDAO.getBillsByCustomerId(custId,
				startDate, endDate);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: End --------> ::::::");
		return billTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.billing.service.BillingUniversalService#getBillsData(
	 * java.util.List, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<BillTO> getBillsData(List<Integer> customerIds,
			String startDateStr, String endDateStr, final Integer productId,
			List<Integer> billingBrachs) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: Start --------> ::::::");
		/*
		 * Date startDate = DateUtil.stringToDDMMYYYYFormat(startDateStr); Date
		 * endDate = DateUtil.stringToDDMMYYYYFormat(endDateStr);
		 */

		List<BillDO> billDOs = billingUniversalDAO
				.getBillsByCustomerIdsAndStartEndDate(customerIds,
						startDateStr, endDateStr, productId, billingBrachs);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getBillsData() :: End --------> ::::::");
		return billTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getShippedToCodesByCustomerId(java.lang.Integer)
	 */
	@Override
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl::getShippedToCodesByCustomerId::START/END----->");
		return rateUniversalService.getShippedToCodesByCustomerId(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getRateContractsByCustomerIds(java.util.List)
	 */
	@Override
	public List<RateContractTO> getRateContractsByCustomerIds(
			List<Integer> customerIds) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl::getRateContractsByCustomerIds::END----->");
		return rateUniversalService.getRateContractsByCustomerIds(customerIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getBookedConsignmentsByCustIdDateRange(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, String startDateStr, String endDateStr)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl::getBookedConsignmentsByCustIdDateRange::START----->");
		Date startDate = DateUtil.stringToDDMMYYYYFormat(startDateStr);
		Date endDate = DateUtil.stringToDDMMYYYYFormat(endDateStr);
		LOGGER.debug("BillingUniversalServiceImpl::getBookedConsignmentsByCustIdDateRange::END----->");
		return consignmentCommonService.getBookedConsignmentsByCustIdDateRange(
				customerId, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getBookedTransferredConsignmentsByCustIdDateRange(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<ConsignmentTO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, String startDateStr, String endDateStr)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl::getBookedTransferredConsignmentsByCustIdDateRange::START----->");
		Date startDate = DateUtil.stringToDDMMYYYYFormat(startDateStr);
		Date endDate = DateUtil.stringToDDMMYYYYFormat(endDateStr);
		LOGGER.debug("BillingUniversalServiceImpl::getBookedTransferredConsignmentsByCustIdDateRange::END----->");
		return consignmentCommonService
				.getBookedTransferredConsignmentsByCustIdDateRange(customerId,
						startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getPaymentBillsDataByCustomerId(java.lang.Integer, java.lang.String[])
	 */
	@Override
	public List<BillTO> getPaymentBillsDataByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getPaymentBillsDataByCustomerId() :: Start --------> ::::::");

		List<BillDO> billDOs = billingUniversalDAO.getPaymentBillsByCustomerId(
				customerId, locationOperationType, officeId);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getPaymentBillsDataByCustomerId() :: End --------> ::::::");
		return billTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.billing.service.BillingUniversalService#
	 * getSAPBillsDataByCustomerId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<BillTO> getSAPBillsDataByCustomerId(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingUniversalServiceImpl :: getSAPBillsDataByCustomerId() :: Start --------> ::::::");

		List<BillDO> billDOs = billingUniversalDAO.getSAPBillsByCustomerId(
				customerId, officeId);
		List<BillTO> billTOs = BillingUniversalConverter
				.convertBillDOsToBillTOs(billDOs);

		LOGGER.debug("BillingUniversalServiceImpl :: getSAPBillsDataByCustomerId() :: End --------> ::::::");
		return billTOs;
	}

}
