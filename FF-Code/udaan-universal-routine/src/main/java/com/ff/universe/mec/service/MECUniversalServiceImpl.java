package com.ff.universe.mec.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.BankDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.BillTO;
import com.ff.to.mec.BankTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.universe.billing.service.BillingUniversalService;
import com.ff.universe.booking.dao.BookingUniversalDAO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.mec.dao.MECUniversalDAO;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.ratemanagement.service.RateUniversalService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.util.StockUtility;

public class MECUniversalServiceImpl implements MECUniversalService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MECUniversalServiceImpl.class);

	/** The mecUniversalDAO */
	private MECUniversalDAO mecUniversalDAO;

	/** The serviceOfferingCommonService. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The organizationCommonService. */
	private OrganizationCommonService organizationCommonService;

	/** The rateUniversalService */
	private RateUniversalService rateUniversalService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The bussiness common service. */
	private BusinessCommonService businessCommonService;

	/** The Billing Universal Service. */
	private BillingUniversalService billingUniversalService;

	/** The booking universal dao. */
	private BookingUniversalDAO bookingUniversalDAO;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The sequence generator service. */
	private SequenceGeneratorService sequenceGeneratorService;

	/** The global universal service. */
	private GlobalUniversalService globalUniversalService;

	/**
	 * @param globalUniversalService
	 *            the globalUniversalService to set
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	/**
	 * @param sequenceGeneratorService
	 *            the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * @param bookingUniversalDAO
	 *            the bookingUniversalDAO to set
	 */
	public void setBookingUniversalDAO(BookingUniversalDAO bookingUniversalDAO) {
		this.bookingUniversalDAO = bookingUniversalDAO;
	}

	/**
	 * @param mecUniversalDAO
	 *            the mecUniversalDAO to set
	 */
	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param rateUniversalService
	 *            the rateUniversalService to set
	 */
	public void setRateUniversalService(
			RateUniversalService rateUniversalService) {
		this.rateUniversalService = rateUniversalService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param businessCommonService
	 *            the businessCommonService to set
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	/**
	 * @param billingUniversalService
	 *            the billingUniversalService to set
	 */
	public void setBillingUniversalService(
			BillingUniversalService billingUniversalService) {
		this.billingUniversalService = billingUniversalService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankTO> getAllBankDtls() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("MECUniversalServiceImpl::getAllBankDtls()::START");
		List<BankDO> bankDOs = null;
		List<BankTO> bankTOs = null;
		try {
			bankDOs = mecUniversalDAO.getAllBankDtls();
			bankTOs = (List<BankTO>) CGObjectConverter
					.createTOListFromDomainList(bankDOs, BankTO.class);
		} catch (Exception e) {
			LOGGER.error("Exception Occurs in MECUniversalServiceImpl::getAllBankDtls()::"
					+ e.getMessage());
		}
		LOGGER.debug("MECUniversalServiceImpl::getAllBankDtls()::END");
		return bankTOs;
	}

	@Override
	public List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException {
		List<PaymentModeTO> paymentModeTOs = null;
		LOGGER.debug("MECUniversalServiceImpl::getPaymentModeDtls()::START");
		try {
			paymentModeTOs = serviceOfferingCommonService
					.getPaymentModeDtls(processCode);
		} catch (Exception e) {
			LOGGER.debug(
					"Exception Occurs in MECUniversalServiceImpl::getPaymentModeDtls()::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("MECUniversalServiceImpl::getPaymentModeDtls()::END");
		return paymentModeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLMasterTO> getGLDtlsByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("MECUniversalServiceImpl::getGLDtlsByRegionId()::START");
		List<GLMasterDO> glDOs = null;
		List<GLMasterTO> glTOs = null;
		try {
			glDOs = mecUniversalDAO.getGLDtlsByRegionId(regionId);
			glTOs = (List<GLMasterTO>) CGObjectConverter
					.createTOListFromDomainList(glDOs, GLMasterTO.class);
		} catch (Exception e) {
			LOGGER.debug(
					"Exception Occurs in MECUniversalServiceImpl::getGLDtlsByRegionId()::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("MECUniversalServiceImpl::getGLDtlsByRegionId()::END");
		return glTOs;
	}

	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getEmployeesOfOffice(officeTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLMasterTO> getAllBankGLDtls(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("MECUniversalServiceImpl::getAllBankGLDtls()::START");
		List<GLMasterDO> bankGlDOs = null;
		List<GLMasterTO> bankGlTOs = null;
		try {
			bankGlDOs = mecUniversalDAO.getAllBankGLDtls(regionId);
			bankGlTOs = (List<GLMasterTO>) CGObjectConverter
					.createTOListFromDomainList(bankGlDOs, GLMasterTO.class);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occurs in MECUniversalServiceImpl::getAllBankGLDtls()::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("MECUniversalServiceImpl::getAllBankGLDtls()::END");
		return bankGlTOs;
	}

	@Override
	public boolean saveOtherCollectionDtls(
			List<CNCollectionDtlsTO> cnCollectionTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("MECUniversalServiceImpl::saveOtherCollectionDtls()::START");
		boolean result = Boolean.FALSE;
		List<CollectionDtlsDO> collectionDtlsDOs = new ArrayList<CollectionDtlsDO>();
		for (CNCollectionDtlsTO to : cnCollectionTOs) {
			CollectionDtlsDO domain = new CollectionDtlsDO();
			/* BILL_AMOUNT */
			if (!StringUtil.isEmptyDouble(to.getAmount())) {
				domain.setBillAmount(to.getAmount());
			}
			/* RCVD_AMOUNT */
			if (!StringUtil.isEmptyDouble(to.getAmount())) {
				domain.setRecvAmount(to.getAmount());
			}
			/* TOTAL_AMOUNT */
			if (!StringUtil.isEmptyDouble(to.getAmount())) {
				domain.setTotalBillAmount(to.getAmount());
			}
			/* CONSIGNMENT_ID */
			if (!StringUtil.isEmptyInteger(to.getConsgId())) {
				ConsignmentDO consgDO = new ConsignmentDO();
				consgDO.setConsgId(to.getConsgId());
				domain.setConsgDO(consgDO);
			}
			/* COLLECTION_TYPE i.e. LC, COD, TOPAY, EXPENSE etc. */
			if (!StringUtil.isStringEmpty(to.getCollectionType())) {
				domain.setCollectionType(to.getCollectionType());
			}
			/* COLLECTION AGAINST */
			if (!StringUtil.isStringEmpty(to.getCollectionAgainst())) {
				domain.setCollectionAgainst(to.getCollectionAgainst());
			}
			collectionDtlsDOs.add(domain);
		}
		try {
			result = mecUniversalDAO.saveOtherCollectionDtls(collectionDtlsDOs);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in MECUniversalServiceImpl::saveOtherCollectionDtls()::"
					+ e.getMessage());
		}
		LOGGER.debug("MECUniversalServiceImpl::saveOtherCollectionDtls()::END");
		return result;
	}

	@Override
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException {
		return rateUniversalService
				.getCustomersByPickupDeliveryLocation(officeId);
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	@Override
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException {
		return businessCommonService.getAllCustomers();
	}

	@Override
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCity(cityId);
	}

	@Override
	public ConsignmentDO getConsignmentDtls(String consgNo)
			throws CGSystemException {
		return mecUniversalDAO.getConsignmentDtls(consgNo);
	}

	@Override
	@Deprecated
	public List<CustomerDO> getLiabilityCustomers(Integer regionId)
			throws CGSystemException {
		return mecUniversalDAO.getLiabilityCustomers(regionId);
	}
	
	@Override
	public List<CustomerTO> getLiabilityCustomersForLiability(Integer regionId) throws CGSystemException,
			CGBusinessException {
		List<StockCustomerWrapperDO> customerWrapperDoList = null;
		customerWrapperDoList = mecUniversalDAO
				.getLiabilityCustomersForLiability(regionId);
		List<CustomerTO> customerList = null;
		if (!StringUtil.isEmptyList(customerWrapperDoList)) {
			customerList = new ArrayList<>(customerWrapperDoList.size());
			for (StockCustomerWrapperDO customerWrapper : customerWrapperDoList) {
				CustomerDO customer = customerWrapper.getCustomerDO();
				if (!StringUtil.isNull(customer) && !StringUtil.isStringEmpty(customer.getCustomerCode())) {
					CustomerTO customerResultTO = new CustomerTO();
					customerResultTO.setCustomerId(customer.getCustomerId());
					customerResultTO
							.setCustomerCode(customer.getCustomerCode());
					customerResultTO
							.setBusinessName(customer.getBusinessName());
					
					customerList.add(customerResultTO);
				}

			}
		}

		return customerList;
	}

	@Override
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getReasonsByReasonType(reasonTO);
	}

	@Override
	public List<BillTO> getPaymentBillsDataByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGBusinessException, CGSystemException {
		return billingUniversalService.getPaymentBillsDataByCustomerId(
				customerId, locationOperationType, officeId);
	}

	@Override
	public List<BillTO> getSAPBillsDataByCustomerId(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		return billingUniversalService.getSAPBillsDataByCustomerId(customerId,
				officeId);
	}

	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		return serviceOfferingCommonService
				.getProductByConsgSeries(consgSeries);
	}

	@Override
	public List<BookingDO> getBookings(List<String> consgNumbers,
			String consgType) throws CGSystemException, CGBusinessException {
		return bookingUniversalDAO.getBookings(consgNumbers, consgType);
	}

	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		return geographyCommonService.getCity(cityTO);
	}

	@Override
	public RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalDAO.getRateCustCategoryByCustId(customerId);
	}

	@Override
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException {
		consignmentCommonService.updateBillingFlagsInConsignment(consignmentDO,
				updatedIn);
	}

	@Override
	public RegionDO getRegionByOffice(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalDAO.getRegionByOffice(officeId);
	}

	@Override
	public List<CustomerTO> getCustomersForBillCollection(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECUniversalServiceImpl :: getCustomersForBillCollection() :: START");
		List<CustomerDO> customerDOs = mecUniversalDAO
				.getCustomersForBillCollection(officeId);
		List<CustomerTO> customerTOs = new ArrayList<CustomerTO>();
		for (CustomerDO customerDO : customerDOs) {
			CustomerTO customerTO = new CustomerTO();
			customerTO = (CustomerTO) CGObjectConverter.createToFromDomain(
					customerDO, customerTO);
			customerTOs.add(customerTO);
		}
		LOGGER.trace("MECUniversalServiceImpl :: getCustomersForBillCollection() :: END");
		return customerTOs;
	}

	@Override
	public void validateExpenseConsgDtls(List<DeliveryDetailsDO> drsDtlsDOs,
			Map<String, Integer> paymentTypeMap) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MECUniversalServiceImpl :: validateExpenseConsgDtls() :: START");
		try {
			List<CollectionDO> collectionDOs = null;
			List<CollectionDtlsDO> collDtlsDOs = null;
			List<Integer> consgIds = parseConsgIdsList(drsDtlsDOs);
			if (!CGCollectionUtils.isEmpty(consgIds)) {
				collDtlsDOs = mecUniversalDAO
						.getCollectionDtlsFromDeliveryDtls(consgIds);
			}
			if (!CGCollectionUtils.isEmpty(collDtlsDOs)) {
				collectionDOs = new ArrayList<CollectionDO>(collDtlsDOs.size());
				for (DeliveryDetailsDO drsDtlsDO : drsDtlsDOs) {
					for (CollectionDtlsDO collDtlsDO : collDtlsDOs) {
						if (drsDtlsDO
								.getConsignmentDO()
								.getConsgNo()
								.equalsIgnoreCase(
										collDtlsDO.getConsgDO().getConsgNo())) {
							CollectionDO collectionDO = new CollectionDO();

							// Setting collection office
							collectionDO.setCollectionOfficeDO(drsDtlsDO
									.getDeliveryDO().getCreatedOfficeDO());
							String officeCode = drsDtlsDO.getDeliveryDO()
									.getCreatedOfficeDO().getOfficeCode();

							// Setting collection date
							collectionDO.setCollectionDate(DateUtil
									.getCurrentDate());

							// Setting Transaction No.
							List<String> txnNos = generateSequenceNo(1,
									CommonConstants.GEN_MISC_CONSG_COLL_TXN_NO);
							String txNo = CommonConstants.EMPTY_STRING;
							if (!CGCollectionUtils.isEmpty(txnNos)) {
								txNo = MECUniversalConstants.TX_CODE_CC
										+ officeCode + txnNos.get(0);
							}
							collectionDO.setTxnNo(txNo);

							// Collection payment mode details
							PaymentModeDO paymentDO = StockUtility
									.preparePaymentModeForCollection(
											paymentTypeMap,
											drsDtlsDO.getModeOfPayment());
							collectionDO.setPaymentModeDO(paymentDO);
							collectionDO
									.setChqDate(drsDtlsDO.getChequeDDDate());
							collectionDO
									.setChqNo(drsDtlsDO.getChequeDDNumber());
							collectionDO.setBankName(drsDtlsDO
									.getBankNameBranch());

							collectionDO
									.setCollectionCategory(MECUniversalConstants.CN_COLLECTION_TYPE);
							collectionDO
									.setStatus(MECUniversalConstants.STATUS_OPENED);

							// Setting total grid amount object to header object
							collectionDO.setTotalAmount(collDtlsDO
									.getTotalBillAmount());

							// Setting Created Date & Update Date.
							collectionDO.setCreatedDate(DateUtil
									.getCurrentDate());
							collectionDO.setUpdatedDate(DateUtil
									.getCurrentDate());

							// Setting delivery date to collection details
							collDtlsDO.setConsgDeliveryDate(drsDtlsDO
									.getDeliveryDate());

							collDtlsDO.setCollectionDO(collectionDO);
							// Setting Created Date & Update Date.
							collDtlsDO
									.setCreatedDate(DateUtil.getCurrentDate());
							collDtlsDO
									.setUpdatedDate(DateUtil.getCurrentDate());

							Set<CollectionDtlsDO> collectionDtlsList = new HashSet<CollectionDtlsDO>();
							collectionDtlsList.add(collDtlsDO);
							collectionDO.setCollectionDtls(collectionDtlsList);

							collectionDOs.add(collectionDO);
							break;
						}
					}
				}
			}
			if (!CGCollectionUtils.isEmpty(collectionDOs)) {
				mecUniversalDAO.saveExpConsgCollectionDtls(collectionDOs);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECUniversalServiceImpl :: validateExpenseConsgDtls() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("MECUniversalServiceImpl :: validateExpenseConsgDtls() :: END");
	}

	/**
	 * To parse consignment(s) Ids List from DRS Details
	 * 
	 * @param drsDtlsDOs
	 * @return consgIds
	 */
	private List<Integer> parseConsgIdsList(List<DeliveryDetailsDO> drsDtlsDOs) {
		LOGGER.trace("MECUniversalServiceImpl :: parseConsgIdsList() :: START");
		List<Integer> consgIds = new ArrayList<Integer>();
		for (DeliveryDetailsDO dlvDtlsDO : drsDtlsDOs) {
			if (dlvDtlsDO.getConsignmentDO() != null) {
				consgIds.add(dlvDtlsDO.getConsignmentDO().getConsgId());
			}
		}
		LOGGER.trace("MECUniversalServiceImpl :: parseConsgIdsList() :: END");
		return consgIds;
	}

	@Override
	public List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECUniversalServiceImpl::generateSequenceNo()::START");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(process);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSeq);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("MECUniversalServiceImpl::generateSequenceNo()::END");
		return sequenceNumber;
	}
	@Override
	public List<String> generateSequenceNo(SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECUniversalServiceImpl::generateSequenceNo()::START");
		List<String> sequenceNumber = null;
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getCollectionSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("MECUniversalServiceImpl::generateSequenceNo()::END");
		return sequenceNumber;
	}

	@Override
	public List<DeliveryDetailsDO> getDrsDtlsForExpenseTypeCollectoin()
			throws CGSystemException, CGBusinessException {
		return mecUniversalDAO.getDrsDtlsForExpenseTypeCollectoin();
	}

	@Override
	public Map<String, Integer> getPaymentModeTypeForCollection()
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECUniversalServiceImpl :: getPaymentModeTypeForCollection() :: START");
		Map<String, Integer> paymentTypeMap = null;
		List<PaymentModeTO> paymentTypeList = getPaymentModeDtls(CommonConstants.PROCESS_MEC);
		if (!CGCollectionUtils.isEmpty(paymentTypeList)) {
			paymentTypeMap = new HashMap<>(paymentTypeList.size());
			for (PaymentModeTO paymentTypeTO : paymentTypeList) {
				paymentTypeMap.put(paymentTypeTO.getPaymentCode(),
						paymentTypeTO.getPaymentId());
			}
		}
		LOGGER.trace("MECUniversalServiceImpl :: getPaymentModeTypeForCollection() :: END");
		return paymentTypeMap;
	}

	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getChildConsgIdByConsgNo(consgNumber);
	}

	@Override
	public BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return bookingUniversalDAO.getBookingDtlsByConsgNo(consgNumber);
	}

	@Override
	public List<OfficeTO> getAllOfficesByType(String officeType)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByType(officeType);
	}

	@Override
	public String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException {
		return globalUniversalService.getConfigParamValueByName(paramName);
	}

	@Override
	public String getNumberInWords(Long enteredNo) throws CGBusinessException {
		return globalUniversalService.getNumberInWords(enteredNo);
	}
	@Override
	public Date getBookingDateByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return bookingUniversalDAO.getBookingDateByConsgNo(consgNumber);
	}

}
