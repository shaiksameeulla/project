package com.ff.report.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerBillingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.report.billing.service.BillingCommonService;
import com.ff.report.common.dao.BillPrintingDAO;
import com.ff.report.common.util.BillingConstants;
import com.ff.to.billing.BillAliasTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.ConsignmentAliasTO;
import com.ff.to.billing.ReBillGDRAliasTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.billing.service.BillingUniversalService;

// TODO: Auto-generated Javadoc
/**
 * The Class BillPrintingServiceImpl.
 */
public class BillPrintingServiceImpl implements BillPrintingService {

	/** The bill printing dao. */
	private BillPrintingDAO billPrintingDAO;

	private transient BillingCommonService billingCommonService;

	/** The billing universal service. */
	private BillingUniversalService billingUniversalService;

	/**
	 * Sets the billing universal service.
	 * 
	 * @param billingUniversalService
	 *            the billingUniversalService to set
	 */
	public void setBillingUniversalService(
			BillingUniversalService billingUniversalService) {
		this.billingUniversalService = billingUniversalService;
	}


	public void setBillingCommonService(BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillPrintingServiceImpl.class);


	/**
	 * @see com.ff.admin.billing.service.BillPrintingService#getBillsForBillNumbers(java.util.List,
	 *      java.lang.String, java.lang.String, int) Nov 21, 2013
	 * @param invoiceNumbers
	 * @param startDate
	 * @param endDate
	 * @param productId
	 * @return
	 * @throws CGBusinessException
	 *             getBillsForBillNumbers BillPrintingService kgajare
	 */
	@Override
	public List<BillAliasTO> getBillsForBillNumbers(
			List<Integer> invoiceNumbers, String startDate, String endDate, Integer financialProductId, 
			List<Integer> billingOfficeIds) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillPrintingServiceImpl::getBillsForBillNumbers::START----->");
		StringBuffer sb = new StringBuffer();
		sb.append("invoiceNumbers = " + invoiceNumbers).append(CommonConstants.COMMA).append("startDate = " + startDate).append(CommonConstants.COMMA)
		.append("endDate" + endDate).append(CommonConstants.COMMA).append("financialProductId = " + financialProductId).append(CommonConstants.COMMA)
		.append("billingOfficeIds = " + billingOfficeIds);
		LOGGER.info("BillPrintingServiceImpl :: getBillsForBillNumbers :: Input parameters\n" +  sb.toString());
		List<BillAliasTO> billAliasTOs = null;
		billAliasTOs = 
				billPrintingDAO.getBillsForBillNumbers(invoiceNumbers, startDate, endDate, financialProductId, billingOfficeIds);
		if (CGCollectionUtils.isEmpty(billAliasTOs)) {
			throw new CGBusinessException(BillingConstants.NO_BILLS_FOUND);
		}
		LOGGER.debug("BillPrintingServiceImpl::getBillsForBillNumbers::END----->");
		return billAliasTOs;
	}

	/**
	 * @see com.ff.admin.billing.service.BillPrintingService#getBillsForCustomers(java.util.List,
	 *      java.lang.String, java.lang.String, int) Nov 21, 2013
	 * @param custIds
	 * @param startDate
	 * @param endDate
	 * @param productId
	 * @return
	 * @throws CGBusinessException
	 *             getBillsForCustomers BillPrintingService kgajare
	 */
	@Override
	public List<BillAliasTO> getBillsForCustomers(List<Integer> custIds,
			String startDate, String endDate, Integer financialProductId, 
			List<Integer> billingOfficeIds)
					throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillPrintingServiceImpl::getBillsForCustomers::START----->");
		StringBuffer sb = new StringBuffer();
		sb.append("custIds = " + custIds).append(CommonConstants.COMMA).append("startDate = " + startDate).append(CommonConstants.COMMA)
		.append("endDate" + endDate).append(CommonConstants.COMMA).append("financialProductId = " + financialProductId).append(CommonConstants.COMMA)
		.append("billingOfficeIds = " + billingOfficeIds);
		LOGGER.debug("BillPrintingServiceImpl :: getBillsForCustomers() :: Input parameters\n" + sb.toString());
		List<BillAliasTO> billAliasTOs = null;
		billAliasTOs = billPrintingDAO.getBillsForCustomers(custIds, startDate,
				endDate, financialProductId, billingOfficeIds);
		if (CGCollectionUtils.isEmpty(billAliasTOs)) {
			throw new CGBusinessException(BillingConstants.NO_BILLS_FOUND);
		}
		LOGGER.debug("BillPrintingServiceImpl::getBillsForCustomers::END----->");
		return billAliasTOs;
	}

	/**
	 * @see com.ff.admin.billing.service.BillPrintingService#getConsignmentsForPrinting(java.lang.Integer)
	 *      Nov 21, 2013
	 * @param invoiceId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getConsignmentsForPrinting BillPrintingService kgajare
	 */
	@Override
	public List<ConsignmentAliasTO> getConsignmentsForPrinting(Integer invoiceId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForPrinting::START----->");
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForPrinting::Getting consignments for invoice number : " + invoiceId);
		List<ConsignmentAliasTO> consignmentAliasTOs = null;
		consignmentAliasTOs = billPrintingDAO.getConsignmentsForPrinting(invoiceId);
		if (CGCollectionUtils.isEmpty(consignmentAliasTOs)) {
			throw new CGBusinessException(BillingConstants.CONSIGNMENT_NOT_FOUND);
		}
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForPrinting::END----->");
		return consignmentAliasTOs;
	}
	
	public BillPrintingDAO getBillPrintingDAO() {
		return billPrintingDAO;
	}

	public void setBillPrintingDAO(BillPrintingDAO billPrintingDAO) {
		this.billPrintingDAO = billPrintingDAO;
	}

	public List<ReBillGDRAliasTO> getRebillingGDRDetails(String  reBillId)throws CGBusinessException, CGSystemException {
		LOGGER.debug("ReBillingGDRServiceImpl::getRebillingGDRDetails::START----->");
		List<ReBillGDRAliasTO> reBillGdrAliasTOs = null;
		Integer rebill=Integer.parseInt(reBillId);
		reBillGdrAliasTOs = billPrintingDAO
				.getRebillingGDRDetails(rebill);
		if (CGCollectionUtils.isEmpty(reBillGdrAliasTOs)) {
			throw new CGBusinessException(
					BillingConstants.NO_REBILLGDR_DATA);
		}
		LOGGER.debug("ReBillingGDRServiceImpl::getRebillingGDRDetails::END----->");
		return reBillGdrAliasTOs;

	}

	//code added from config admin 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillPrintingService#getStation(java.lang
	 * .Integer)
	 */
	public CityTO getStation(final Integer cityId) throws CGBusinessException,
	CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getStation::START----->");
		CityTO cityTO1 = null;
		try {
			CityDO cityDO = billPrintingDAO.getCity(cityId);
			if (cityDO != null) {
				cityTO1 = new CityTO();
				cityTO1 = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO1);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : BillPrintingServiceImpl::getStation", ex);
			throw new CGBusinessException(AdminErrorConstants.NO_STATION_FOUND,
					ex);
		}
		LOGGER.debug("BillPrintingServiceImpl::getStation::END----->");
		return cityTO1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillPrintingService#getCustomersByBranch
	 * (java.util.List)
	 */
	public List<CustomerTO> getCustomersByBranch(List<Integer> BranchId)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getCustomersByBranch::START----->");
		List<CustomerTO> custList = new ArrayList<CustomerTO>();

		if (!StringUtil.isEmptyColletion(BranchId)) {
			List<CustomerDO> customerDOList = billPrintingDAO
					.getCustomersByBranch(BranchId);

			if (!StringUtil.isEmptyColletion(customerDOList)) {
				custList = (List<CustomerTO>) CGObjectConverter
						.createTOListFromDomainList(customerDOList,
								CustomerTO.class);
			} else {
				throw new CGBusinessException(
						AdminErrorConstants.NO_CUSTOMER_FOUND);
			}
		}
		/*
		 * } catch (Exception ex) { LOGGER.error(
		 * "ERROR : BillPrintingServiceImpl::getCustomersByBranch", ex); throw
		 * new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND, ex); }
		 */
		LOGGER.debug("BillPrintingServiceImpl::getCustomersByBranch::END----->");
		return custList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillPrintingService#getStationaryTypes(java
	 * .lang.String)
	 */
	@Override
	public List<StockStandardTypeTO> getStationaryTypes(final String typeName)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getStationaryTypes::START----->");
		List<StockStandardTypeTO> stationeryNames = null;

		stationeryNames = billingCommonService
				.getStandardTypesByTypeName(typeName);
		if (CGCollectionUtils.isEmpty(stationeryNames)) {
			throw new CGBusinessException(AdminErrorConstants.NO_BILLS_FOUND);
		}

		/*
		 * catch (Exception ex) { LOGGER.error(
		 * "ERROR : BillPrintingServiceImpl::getStationaryTypes", ex); throw new
		 * CGBusinessException(AdminErrorConstants.NO_STATIONARY_AVAILABLE); }
		 */
		LOGGER.debug("BillPrintingServiceImpl::getStationaryTypes::END----->");
		return stationeryNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.billing.service.BillPrintingService#getBills(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillTO> getBills(List<Integer> custIds, String startDate,
			String endDate, Integer productId,List<Integer> billingBrachs) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getBills::START----->");
		List<BillTO> billList = new ArrayList<BillTO>();

		if (!StringUtil.isNull(custIds)) {

			billList = billingUniversalService.getBillsData(custIds, startDate,
					endDate, productId,billingBrachs);
			if (CGCollectionUtils.isEmpty(billList)) {
				throw new CGBusinessException(
						AdminErrorConstants.NO_BILLS_FOUND);
			}

			Collections.sort(billList);
		}

		/*
		 * }catch (Exception ex) { LOGGER.error(
		 * "ERROR :BillPrintingServiceImpl::getBills",ex); throw new
		 * CGBusinessException(AdminErrorConstants.NO_BILLS_FOUND, ex); }
		 */
		LOGGER.debug("BillPrintingServiceImpl::getBills::END----->");
		return billList;
	}


	/**
	 * @see com.ff.admin.billing.service.BillPrintingService#getCustomersByBillingBranch(java.util.List)
	 *      Dec 3, 2013
	 * @param BranchId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getCustomersByBillingBranch BillPrintingService kgajare
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CustomerTO> getCustomersByBillingBranch(List<Integer> BranchId)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getCustomersByBillingBranch::START----->");
		List<CustomerTO> custList = new ArrayList<CustomerTO>();

		if (!StringUtil.isEmptyColletion(BranchId)) {
			List<CustomerBillingDO> customerDOList = billPrintingDAO.getCustomersByBillingBranch(BranchId);


			if (!StringUtil.isEmptyColletion(customerDOList)) {

				for (CustomerBillingDO customerBillingDO : customerDOList) {
					CustomerTO custTo = new CustomerTO();
					custTo.setCustomerCode(customerBillingDO.getCustomerCode());
					custTo.setContractNo(customerBillingDO.getContractNo());
					custTo.setCustomerId(customerBillingDO.getCustomerId());
					custTo.setBusinessName(customerBillingDO.getBusinessName());
					custTo.setShippedToCode(customerBillingDO.getShippedToCode());
					custList.add(custTo);
				}
				/* if (!StringUtil.isEmptyColletion(customerDOList)) {
		custList = (List<CustomerTO>) CGObjectConverter
			.createTOListFromDomainList(customerDOList,
				CustomerTO.class);*/
			} else {
				throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
			}
		}
		LOGGER.debug("BillPrintingServiceImpl::getCustomersByBillingBranch::END----->");
		return custList;

	}


	public List<CustomerTO>  getCustsByBillingBranchAndFinancialProduct(List<Integer> branchId,Integer financialProdId)  throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::START----->");
		List<CustomerTO> custList = new ArrayList<CustomerTO>();

		if (!StringUtil.isEmptyColletion(branchId) && !StringUtil.isEmptyInteger(financialProdId)) {
			List<CustomerBillingDO> customerDOList = billPrintingDAO.getCustsByBillingBranchAndFinancialProduct(branchId, financialProdId);

			if (!StringUtil.isEmptyColletion(customerDOList)) {

				for (CustomerBillingDO customerBillingDO : customerDOList) {
					CustomerTO custTo = new CustomerTO();
					custTo.setCustomerCode(customerBillingDO.getCustomerCode());
					custTo.setContractNo(customerBillingDO.getContractNo());
					custTo.setCustomerId(customerBillingDO.getCustomerId());
					custTo.setBusinessName(customerBillingDO.getBusinessName());
					custTo.setShippedToCode(customerBillingDO.getShippedToCode());
					custList.add(custTo);
				}
				/*custList = (List<CustomerTO>) CGObjectConverter
    			.createTOListFromDomainList(customerDOList,
    				CustomerTO.class);*/
			} else {
				throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
			}
		}
		LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::END----->");
		return custList;
	}


	public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BillPrintingServiceImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = billPrintingDAO.getBranchesUnderReportingHub(officeId);
		/**
		 * Comment START
		 * Commented By - kgajare
		 * Commented Date - 19 May 2014
		 * Commented Reason - An issue was reported for HUB login in BIll Printing where 
		 * user of Standalone HUB cannot print bills for its own customers. The reason was below business exception.
		 * As confirmed with BA Team we have to remove the business exception even if HUB did not have any branches 
		 * under it and let the user use Bill Printing with only HUB itself in Branch List.
		 */
		/*if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(AdminErrorConstants.ERROR_IN_GETTING_BRANCH_LIST_OF_HUB);
		}*/
		/**
		 * Comment END
		 */
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("BillPrintingServiceImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeTOList;
	}
	
	@Override
	public List<ConsignmentAliasTO> getConsignmentsForBulkBillPrinting(Integer invoiceId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForBulkBillPrinting::START----->");
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForBulkBillPrinting::Getting consignments for invoice number : " + invoiceId);
		List<ConsignmentAliasTO> consignmentAliasTOs = null;
		consignmentAliasTOs = billPrintingDAO.getConsignmentsForBulkBillPrinting(invoiceId);
		if (CGCollectionUtils.isEmpty(consignmentAliasTOs)) {
			throw new CGBusinessException(BillingConstants.CONSIGNMENT_NOT_FOUND);
		}
		LOGGER.debug("BillPrintingServiceImpl::getConsignmentsForBulkBillPrinting::END----->");
		return consignmentAliasTOs;
	}


	public List<CustomerTO>  getCustsByBillingBranchAndFinancialProductForReport(List<Integer> branchId,List<Integer> productIds)  throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::START----->");
		List<CustomerTO> custList = new ArrayList<CustomerTO>();

		if (!StringUtil.isEmptyColletion(branchId) && !StringUtil.isEmptyColletion(productIds)) {
			List<CustomerBillingDO> customerDOList = billPrintingDAO.getCustsByBillingBranchAndFinancialProductForReport(branchId, productIds);

			if (!StringUtil.isEmptyColletion(customerDOList)) {

				for (CustomerBillingDO customerBillingDO : customerDOList) {
					CustomerTO custTo = new CustomerTO();
					custTo.setCustomerCode(customerBillingDO.getCustomerCode());
					custTo.setContractNo(customerBillingDO.getContractNo());
					custTo.setCustomerId(customerBillingDO.getCustomerId());
					custTo.setBusinessName(customerBillingDO.getBusinessName());
					custTo.setShippedToCode(customerBillingDO.getShippedToCode());
					custList.add(custTo);
				}
				/*custList = (List<CustomerTO>) CGObjectConverter
    			.createTOListFromDomainList(customerDOList,
    				CustomerTO.class);*/
			} else {
				throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
			}
		}
		LOGGER.debug("BillPrintingServiceImpl::getCustsByBillingBranchAndFinancialProduct::END----->");
		return custList;
	}

	//Created By Shaheed For All customers
	@Override
	public List<BillAliasTO> getBillsForAllCustomers(String startDate, String endDate, Integer financialProductId, 
			List<Integer> billingOfficeIds)
					throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillPrintingServiceImpl::getBillsForCustomers::START----->");
		StringBuffer sb = new StringBuffer();
		sb.append("startDate = " + startDate).append(CommonConstants.COMMA)
		.append("endDate" + endDate).append(CommonConstants.COMMA).append("financialProductId = " + financialProductId).append(CommonConstants.COMMA)
		.append("billingOfficeIds = " + billingOfficeIds);
		LOGGER.debug("BillPrintingServiceImpl :: getBillsForCustomers() :: Input parameters\n" + sb.toString());
		List<BillAliasTO> billAliasTOs = null;
		billAliasTOs = billPrintingDAO.getBillsForAllCustomers(startDate,
				endDate, financialProductId, billingOfficeIds);
		if (CGCollectionUtils.isEmpty(billAliasTOs)) {
			throw new CGBusinessException(BillingConstants.NO_BILLS_FOUND);
		}
		LOGGER.debug("BillPrintingServiceImpl::getBillsForCustomers::END----->");
		return billAliasTOs;
	}
	
	
	
}
