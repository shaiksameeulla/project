package com.ff.admin.mec.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.dao.MECCommonDAO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.collection.BulkCollectionValidationWrapperDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.pettycash.PettyCashReportWrapperDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.mec.collection.BulkCollectionValidationWrapperTO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.billing.BillTO;
import com.ff.to.mec.BankTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.mec.service.MECUniversalService;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;

/**
 * @author hkansagr
 */

public class MECCommonServiceImpl implements MECCommonService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MECCommonServiceImpl.class);

	/** The MEC common DAO. */
	private MECCommonDAO mecCommonDAO;

	/** The MEC universal service. */
	private MECUniversalService mecUniversalService;

	/** The rateCalculationUniversalService. */
	private RateCalculationUniversalService rateCalculationUniversalService;

	/**
	 * @param rateCalculationUniversalService
	 *            the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	/**
	 * @param mecCommonDAO
	 *            the mecCommonDAO to set
	 */
	public void setMecCommonDAO(MECCommonDAO mecCommonDAO) {
		this.mecCommonDAO = mecCommonDAO;
	}

	/**
	 * @param mecUniversalService
	 *            the mecUniversalService to set
	 */
	public void setMecUniversalService(MECUniversalService mecUniversalService) {
		this.mecUniversalService = mecUniversalService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl::getStockStdType::START");
		List<StockStandardTypeDO> typeDoList = null;
		List<StockStandardTypeTO> typeToList = null;
		typeDoList = mecCommonDAO.getStockStdType(typeName);
		if (!StringUtil.isEmptyColletion(typeDoList)) {
			typeToList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(typeDoList,
							StockStandardTypeTO.class);
		}
		LOGGER.trace("MECCommonServiceImpl::getStockStdType::END");
		return typeToList;
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return mecUniversalService.getAllRegions();
	}

	@Override
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException {
		return mecUniversalService.getAllCustomers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.mec.collection.service.BillCollectionService#getAllBankDtls
	 * ()
	 */
	public List<BankTO> getAllBankDtls() throws CGBusinessException,
			CGSystemException {
		return mecUniversalService.getAllBankDtls();
	}

	@Override
	public List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.generateSequenceNo(noOfSeq, process);
	}
	@Override
	public List<String> generateSequenceNo(SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.generateSequenceNo(sequenceGeneratorConfigTO);
	}
	

	@Override
	public CustomerTO getCustByCustName(String custName)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getCustByCustName()::START");
		CustomerTO custTo = null;
		CustomerDO custDO = mecCommonDAO.getCustDOByCustName(custName);
		if (custDO != null) {
			custTo = new CustomerTO();
			CGObjectConverter.createToFromDomain(custDO, custTo);
		}
		LOGGER.trace("MECCommonServiceImpl::getCustByCustName()::END");
		return custTo;
	}

	/**
	 * Get all the cities comes under the zone.
	 * <p>
	 * <ul>
	 * <li>the list of cities comes under the zone will be returned.
	 * </ul>
	 * <p>
	 * 
	 * @param regionTO
	 *            the region to
	 * @return List<CityTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getCitiesByRegion()::START");
		List<CityTO> cityTOs = null;
		if (!StringUtil.isNull(regionTO)) {
			CityTO cityTO = new CityTO();
			cityTO.setRegion(regionTO.getRegionId());
			cityTOs = mecUniversalService.getCitiesByCity(cityTO);
		}
		LOGGER.trace("MECCommonServiceImpl::getCitiesByRegion()::END");
		return cityTOs;
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getAllOfficesByCity(cityId);
	}

	@Override
	/*
	 * public Integer getGLPaymentId(Integer glId) throws CGSystemException {
	 * return mecCommonDAO.getGLPaymentId(glId); }
	 */
	public GLMasterTO getGLDtlsById(Integer glId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getGLDtlsById()::START");
		GLMasterTO glTO = null;
		GLMasterDO domain = mecCommonDAO.getGLDtlsById(glId);
		if (!StringUtil.isNull(domain)) {
			glTO = new GLMasterTO();
			glTO = (GLMasterTO) CGObjectConverter.createToFromDomain(domain,
					glTO);
			if (!StringUtil.isNull(domain.getPaymentModeDO()))
				glTO.setPaymentModeId(domain.getPaymentModeDO().getPaymentId());
		}
		LOGGER.trace("MECCommonServiceImpl::getGLDtlsById()::END");
		return glTO;
	}

	@Override
	public boolean isConsigBookedNotDelivered(String consgNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::isConsigBookedNotDelivered()::START");
		boolean result = Boolean.FALSE;
		result = mecCommonDAO.isConsignmentBooked(consgNo);
		LOGGER.trace("MECCommonServiceImpl::isConsigBookedNotDelivered()::END");
		return result;
	}

	@Override
	public ConsignmentTO getConsignmentDtls(String consgNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getConsignmentDtls()::START");
		ConsignmentTO consgTO = null;
		ConsignmentDO consgDO = null;
		consgDO = mecUniversalService.getConsignmentDtls(consgNo);
		if (!StringUtil.isNull(consgDO)) {
			consgTO = new ConsignmentTO();
			consgTO = (ConsignmentTO) CGObjectConverter.createToFromDomain(
					consgDO, consgTO);
			consignmentTransferObjConverter(consgTO, consgDO);
		}
		LOGGER.trace("MECCommonServiceImpl::getConsignmentDtls()::END");
		return consgTO;
	}

	/**
	 * To convert consignment domain object to transfer object
	 * 
	 * @param consgTO
	 * @param consgDO
	 */
	private void consignmentTransferObjConverter(ConsignmentTO consgTO,
			ConsignmentDO consgDO) throws CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl::consignmentTransferObjConverter()::START");
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		// Set destination pincode
		if (!StringUtil.isNull(consgDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consgDO.getDestPincodeId(),
					destPin);
			consgTO.setDestPincode(destPin);
		}
		// Set origin office
		if (!StringUtil.isNull(consgDO.getOrgOffId())) {
			consgTO.setOrgOffId(consgDO.getOrgOffId());
		}
		// Set consignment type
		if (!StringUtil.isNull(consgDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter
					.createToFromDomain(consgDO.getConsgType(), typeTO);
			consgTO.setTypeTO(typeTO);
		}
		// Set paper work
		if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
			CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnPaperWorkId(),
					cnPaperworkTO);
			cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperworkTO);
		}
		// Set content
		if (!StringUtil.isNull(consgDO.getCnContentId())) {
			CNContentTO cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
					cnContentTO);
			cnContentTO.setOtherContent(consgDO.getOtherCNContent());
			consgTO.setCnContents(cnContentTO);
		}
		// Set insured by
		if (!StringUtil.isNull(consgDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
					insuredBy);
			consgTO.setInsuredByTO(insuredBy);
		}
		// Set vol. weight
		if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(consgDO.getVolWeight());
			volWeightDtls.setHeight(consgDO.getHeight());
			volWeightDtls.setLength(consgDO.getLength());
			volWeightDtls.setBreadth(consgDO.getBreath());
			consgTO.setVolWightDtls(volWeightDtls);
		}
		// Set consignor
		if (!StringUtil.isNull(consgDO.getConsignor())) {
			ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignor(),
					consignorTO);
			consgTO.setConsignorTO(consignorTO);
		}
		// Set consingee
		if (!StringUtil.isNull(consgDO.getConsignee())) {
			ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignee(),
					consigneeTO);
			consgTO.setConsigneeTO(consigneeTO);
		}
		// CN rate component
		Set<ConsignmentBillingRateDO> consgRate = consgDO.getConsgRateDtls();
		if (!CGCollectionUtils.isEmpty(consgRate)) {
			CNPricingDetailsTO consgPriceDtls = setUpRateCompoments(consgRate);
			if (!StringUtil.isEmptyDouble(consgDO.getDeclaredValue()))
				consgPriceDtls.setDeclaredvalue(consgDO.getDeclaredValue());
			if (!StringUtil.isEmptyDouble(consgDO.getDiscount()))
				consgPriceDtls.setDiscount(consgDO.getDiscount());
			consgPriceDtls.setRateType(consgDO.getRateType());
			consgTO.setConsgPriceDtls(consgPriceDtls);
		}
		LOGGER.trace("MECCommonServiceImpl::consignmentTransferObjConverter()::END");
	}

	/**
	 * To set up rate components to CN pricing details.
	 * 
	 * @param consgRates
	 * @return consgRateDtls
	 */
	private CNPricingDetailsTO setUpRateCompoments(
			Set<ConsignmentBillingRateDO> consgRates) {
		LOGGER.trace("MECCommonServiceImpl::setUpRateCompoments()::START");
		CNPricingDetailsTO consgRateDtls = null;
		for (ConsignmentBillingRateDO rateOutput : consgRates) {
			consgRateDtls = new CNPricingDetailsTO();
			if (StringUtils.equalsIgnoreCase(
					MECCommonConstants.CONSG_BOOKING_TYPE_NORMAL,
					rateOutput.getRateCalculatedFor())) {
				consgRateDtls.setAirportHandlingChg(rateOutput
						.getAirportHandlingCharge());
				consgRateDtls.setFinalPrice(rateOutput
						.getGrandTotalIncludingTax());
				consgRateDtls.setCodAmt(rateOutput.getCodAmount());
				consgRateDtls.setFuelChg(rateOutput.getFuelSurcharge());
				consgRateDtls.setServiceTax(rateOutput.getServiceTax());
				consgRateDtls.setRiskSurChg(rateOutput.getRiskSurcharge());
				consgRateDtls.setTopayChg(rateOutput.gettOPayCharge());
				consgRateDtls.setEduCessChg(rateOutput.getEducationCess());
				consgRateDtls.setHigherEduCessChg(rateOutput
						.getHigherEducationCess());
				consgRateDtls.setFreightChg(rateOutput.getSlabRate());
				consgRateDtls.setSplChg(rateOutput.getOtherOrSpecialCharge());
			}
		}
		LOGGER.trace("MECCommonServiceImpl::setUpRateCompoments()::END");
		return consgRateDtls;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public List<CustomerTO> getLiabilityCustomers(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getLiabilityCustomers()::START");
		List<CustomerDO> customerDOList = mecUniversalService
				.getLiabilityCustomers(regionId);
		List<CustomerTO> customerTOList = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOList, CustomerTO.class);
		LOGGER.trace("MECCommonServiceImpl::getLiabilityCustomers()::END");
		return customerTOList;
	}
	
	@Override
	public List<CustomerTO> getLiabilityCustomersForLiability(Integer regionId) throws CGSystemException,
	CGBusinessException {
		return  mecUniversalService
				.getLiabilityCustomersForLiability(regionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getPaymentModeDtls(String MECProcessCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl::getPaymentModeDtls()::START");
		List<StockStandardTypeDO> typeDoList = mecCommonDAO
				.getLiabilityPaymentMode(MECProcessCode);
		List<StockStandardTypeTO> modes = null;
		if (!StringUtil.isEmptyColletion(typeDoList)) {
			modes = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(typeDoList,
							StockStandardTypeTO.class);
		} else {
			LOGGER.warn("MECCommonServiceImpl::getStockStdType details does not exist for type Name "
					+ MECProcessCode);
		}
		LOGGER.trace("MECCommonServiceImpl::getPaymentModeDtls()::END");
		return modes;
	}

	@Override
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException, CGBusinessException {
		return mecUniversalService.getReasonsByReasonType(reasonTO);
	}

	@Override
	public List<BillTO> getPaymentBillsDataByCustomerId(Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getPaymentBillsDataByCustomerId(customerId,
				locationOperationType, officeId);
	}

	@Override
	public List<BillTO> getSAPBillsDataByCustomerId(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		return mecUniversalService.getSAPBillsDataByCustomerId(customerId,
				officeId);
	}

	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getEmployeesOfOffice(officeTO);
	}

	@Override
	public List<GLMasterTO> getAllBankGLDtls(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getAllBankGLDtls(regionId);
	}

	@Override
	public List<PaymentModeTO> getPaymentModeDetails(String processCode)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getPaymentModeDtls(processCode);
	}

	@Override
	public List<GLMasterTO> getGLDtlsByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getGLDtlsByRegionId(regionId);
	}

	@Override
	public boolean saveOtherCollectionDtls(
			List<CNCollectionDtlsTO> cnCollectionDtlsTOs)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.saveOtherCollectionDtls(cnCollectionDtlsTOs);
	}

	@Override
	public OctroiRateCalculationOutputTO calculateOCTROI(
			ConsignmentTO consignmentTO) throws CGSystemException,
			CGBusinessException {
		return rateCalculationUniversalService.calculateOCTROI(consignmentTO);
	}

	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		return mecUniversalService.getProductByConsgSeries(consgSeries);
	}

	@Override
	public void getBookingDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl :: getBookingDtls() :: START");
		BookingDO bookingDO = null;
		List<String> consgNos = new ArrayList<String>();

		/* Always one consignment is passed as a parameter */
		consgNos.add(consignmentTO.getConsgNo());
		List<BookingDO> bookingDOs = mecUniversalService.getBookings(consgNos,
				CommonConstants.EMPTY_STRING);
		if (!CGCollectionUtils.isEmpty(bookingDOs)) {
			bookingDO = bookingDOs.get(0);
		} else {
			throw new CGBusinessException(
					MECCommonConstants.RATE_NOT_CALC_FOR_CN);
		}

		/** set booking details to consignment for rate calculation */

		// Set Booking date / Event date
		if (!StringUtil.isStringEmpty(consignmentTO.getConsgStatus())
				&& !StringUtil.equals(consignmentTO.getConsgStatus(),
						MECUniversalConstants.CONSIGNMENT_STATUS_RETURNED)) {
			consignmentTO.setBookingDate(bookingDO.getBookingDate());
		}

		LOGGER.trace("MECCommonServiceImpl :: getBookingDtls() :: END");
	}

	/**
	 * To get rate customer category by customer id
	 * 
	 * @param customerId
	 * @return rateCustomerCategoryDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getRateCustCategoryByCustId(customerId);
	}

	@Override
	public String getRateType(String customerType) {
		LOGGER.trace("MECCommonServiceImpl :: getRateType() :: START");
		String rateType = CommonConstants.EMPTY_STRING;
		switch (customerType) {
		case RateUniversalConstants.RATE_CUST_CAT_CRDT:
		case RateUniversalConstants.RATE_CUST_CAT_FR:
			rateType = RateUniversalConstants.RATE_TYPE_CC;
			break;
		case RateUniversalConstants.RATE_CUST_CAT_CASH:
		case RateUniversalConstants.RATE_CUST_CAT_ACC:
			rateType = RateUniversalConstants.RATE_TYPE_CH;
			break;
		case RateUniversalConstants.RATE_CUST_CAT_BA:
			rateType = RateUniversalConstants.RATE_TYPE_BA;
			break;
		}
		LOGGER.trace("MECCommonServiceImpl :: getRateType() :: END");
		return rateType;
	}

	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		return mecUniversalService.getCity(cityTO);
	}

	@Override
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException {
		mecUniversalService.updateBillingFlagsInConsignment(consignmentDO,
				updatedIn);
	}

	@Override
	public RegionDO getRegionByOffice(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getRegionByOffice(officeId);
	}

	@Override
	public List<CustomerTO> getCustomersForBillCollection(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getCustomersForBillCollection(officeId);
	}

	@Override
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException {
		return mecUniversalService
				.getCustomersByPickupDeliveryLocation(officeId);
	}

	@Override
	public List<CollectionDO> getCollectionDtlsForRecalculation()
			throws CGSystemException, CGBusinessException {
		return mecCommonDAO.getCollectionDtlsForRecalculation();
	}

	@Override
	public void updateCollectionRecalcFlag(List<Integer> collectionIds)
			throws CGSystemException, CGBusinessException {
		mecCommonDAO.updateCollectionRecalcFlag(collectionIds);
	}

	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getChildConsgIdByConsgNo(consgNumber);
	}

	@Override
	public BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getBookingDtlsByConsgNo(consgNumber);
	}
	@Override
	public Date getBookingDateByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getBookingDateByConsgNo(consgNumber);
	}
	
	

	@Override
	public List<OfficeTO> getAllOfficesByType(String officeType)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getAllOfficesByType(officeType);
	}

	@Override
	public String decreaseDateByOne(String fromDt) throws CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByOne() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			/* Number of days to reduce */
			c.add(Calendar.DATE, -1);
			/* toDtStr is now the new date, 1 day before */
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			LOGGER.error(
					"Exception occurs in MECCommonServiceImpl :: decreaseDateByOne() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByOne() :: END");
		return toDtStr;
	}

	@Override
	public String decreaseDateByDays(String fromDt, Integer days)
			throws CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByDays() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			/* Number of days to reduce */
			c.add(Calendar.DATE, -days);
			/* toDtStr is now the new date, 1 day before */
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			LOGGER.error(
					"Exception occurs in MECCommonServiceImpl :: decreaseDateByDays() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByDays() :: END");
		return toDtStr;
	}

	@Override
	public String decreaseDateByDays(String fromDt, Integer days,
			String dateFormat) throws CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByDays() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			/* Number of days to reduce */
			c.add(Calendar.DATE, -days);
			// toDtStr is now the new date, 1 day before, as result of given
			// date format
			sdf = new SimpleDateFormat(dateFormat);
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			LOGGER.error(
					"Exception occurs in MECCommonServiceImpl :: decreaseDateByDays(dateFormat) :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("MECCommonServiceImpl :: decreaseDateByDays(dateFormat) :: END");
		return toDtStr;
	}

	@Override
	public String increaseDateByDays(String fromDt, Integer days)
			throws CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl :: increaseDateByDays() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			/* Number of days to reduce */
			c.add(Calendar.DATE, days);
			/* toDtStr is now the new date, 1 day before */
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			LOGGER.error(
					"Exception occurs in MECCommonServiceImpl :: increaseDateByDays() :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("MECCommonServiceImpl :: increaseDateByDays() :: END");
		return toDtStr;
	}

	@Override
	public String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException {
		return mecUniversalService.getConfigParamValueByName(paramName);
	}

	@Override
	public List<CollectionDO> getChequeDepositSlipDtls(Integer reportingRHOId)
			throws CGBusinessException, CGSystemException {
		return mecCommonDAO.getChequeDepositSlipDtls(reportingRHOId);
	}

	@Override
	public String getNumberInWords(Long enteredNo) throws CGBusinessException {
		return mecUniversalService.getNumberInWords(enteredNo);
	}

	@Override
	public List<PettyCashReportWrapperDO> getCollectionIdAndCollectionDateForPettyCash(String currentDateString)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("MECCommonServiceImpl :: getCollectionIdAndCollectionDateForPettyCash :: START");
		List<PettyCashReportWrapperDO> pettyCashReportWrapperDoList= null;
		Date currentDateObject = DateUtil.getDateFromString(currentDateString, FrameworkConstants.DDMMYYYY_SLASH_FORMAT); 
		List<Object[]> collectionDetails = mecCommonDAO.getCollectionIdAndCollectionDateForPettyCash(currentDateObject);
		
		if(!StringUtil.isEmptyColletion(collectionDetails)) {
			pettyCashReportWrapperDoList = new ArrayList<PettyCashReportWrapperDO>();
			for (Object[] objArr : collectionDetails) {
				pettyCashReportWrapperDoList.add(new PettyCashReportWrapperDO((Integer)objArr[0],(Date)objArr[1],(Integer)objArr[2]));
			}
		}
		LOGGER.trace("MECCommonServiceImpl :: getCollectionIdAndCollectionDateForPettyCash :: END");
		return pettyCashReportWrapperDoList;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.mec.common.service.MECCommonService#searchCollectionDetailsForBulkValidation(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Override
	public List<BulkCollectionValidationWrapperTO> searchCollectionDetailsForBulkValidation(
			Integer customerId, Date fromDate, Date toDate, Integer firstResult)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl :: searchCollectionDetailsForBulkValidation :: START");
		List<BulkCollectionValidationWrapperTO> bulkCollectionValidationWrapperToList = null;
		List<BulkCollectionValidationWrapperDO> bulkCollectionValidationWrapperDoList = null;
		bulkCollectionValidationWrapperDoList = mecCommonDAO.getCollectionDetailsForBulkValidation(customerId, fromDate, toDate, firstResult);
		if (!StringUtil.isEmptyList(bulkCollectionValidationWrapperDoList)) {
			bulkCollectionValidationWrapperToList = new ArrayList<>();
			for (BulkCollectionValidationWrapperDO bulkCollectionValidationWrapperDo : bulkCollectionValidationWrapperDoList) {
				BulkCollectionValidationWrapperTO bulkCollectionValidationWrapperTo = 
						convertBulkValidationDOtoTO(bulkCollectionValidationWrapperDo);
				bulkCollectionValidationWrapperToList.add(bulkCollectionValidationWrapperTo);
			}
		}
		
		LOGGER.trace("MECCommonServiceImpl :: searchCollectionDetailsForBulkValidation :: END");
		return bulkCollectionValidationWrapperToList;
	}
	
	private BulkCollectionValidationWrapperTO convertBulkValidationDOtoTO(BulkCollectionValidationWrapperDO bulkCollectionValidationWrapperDo) {
		LOGGER.trace("MECCommonServiceImpl :: convertBulkValidationDOtoTO :: START");
		BulkCollectionValidationWrapperTO bulkCollectionValidationWrapperTo = new BulkCollectionValidationWrapperTO();
		// Booking Date
		bulkCollectionValidationWrapperTo.setBookingDate(DateUtil.getDDMMYYYYDateToString(bulkCollectionValidationWrapperDo.getBookingDate()));
		
		// Collection Category
		bulkCollectionValidationWrapperTo.setCollectionCategory(bulkCollectionValidationWrapperDo.getCollectionCategory());
		
		// Collection Date
		bulkCollectionValidationWrapperTo.setCollectionDate(DateUtil.getDDMMYYYYDateToString(bulkCollectionValidationWrapperDo.getCollectionDate()));
		
		// Collection Id
		bulkCollectionValidationWrapperTo.setCollectionId(bulkCollectionValidationWrapperDo.getCollectionId());
		
		// Collection Office Id
		bulkCollectionValidationWrapperTo.setCollectionOfficeId(bulkCollectionValidationWrapperDo.getCollectionOfficeId());
		
		// Collection Status
		if (MECCommonConstants.SUBMITTED_STATUS.equals(bulkCollectionValidationWrapperDo.getCollectionStatus().toString())){
			bulkCollectionValidationWrapperTo.setCollectionStatus(MECCommonConstants.SUBMITTED);
		}
		
		// Consignment Id
		bulkCollectionValidationWrapperTo.setConsignmentId(bulkCollectionValidationWrapperDo.getConsignmentId());
		
		// Consignment No
		bulkCollectionValidationWrapperTo.setConsignmentNo(bulkCollectionValidationWrapperDo.getConsignmentNo());
		
		// Mode of Payment
		bulkCollectionValidationWrapperTo.setPaymentType(bulkCollectionValidationWrapperDo.getPaymentType());
		
		// Total Collection Amount
		bulkCollectionValidationWrapperTo.setTotalCollectionAmount(bulkCollectionValidationWrapperDo.getTotalCollectionAmount().toString());
		
		// Transaction No
		bulkCollectionValidationWrapperTo.setTransactionNo(bulkCollectionValidationWrapperDo.getTransactionNo());
		
		// Booking Amount is either LC amount, COD amount or TOPAY amount
		if (!StringUtil.isEmptyDouble(bulkCollectionValidationWrapperDo.getCodAmount())) {
			bulkCollectionValidationWrapperTo.setBookingAmount(bulkCollectionValidationWrapperDo.getCodAmount().toString());
		}
		else if (!StringUtil.isEmptyDouble(bulkCollectionValidationWrapperDo.getLcAmount())) {
			bulkCollectionValidationWrapperTo.setBookingAmount(bulkCollectionValidationWrapperDo.getLcAmount().toString());
		}
		else if (!StringUtil.isEmptyDouble(bulkCollectionValidationWrapperDo.getToPayAmount())) {
			bulkCollectionValidationWrapperTo.setBookingAmount(bulkCollectionValidationWrapperDo.getLcAmount().toString());
		}
		
		LOGGER.trace("MECCommonServiceImpl :: convertBulkValidationDOtoTO :: END");
		return bulkCollectionValidationWrapperTo;
	}
	
	
	@Override
	public Integer getTotalNumberOfRecordsForBulkValidation(Integer customerId,
			Date fromDate, Date toDate) throws CGBusinessException, CGSystemException {
		LOGGER.trace("MECCommonServiceImpl :: getTotalNumberOfRecordsForBulkValidation :: START");
		Integer totalNumberOfRecords = mecCommonDAO.getTotalNumberOfRecordsForBulkValidation(customerId, fromDate, toDate);
		LOGGER.trace("MECCommonServiceImpl :: getTotalNumberOfRecordsForBulkValidation :: END");
		return totalNumberOfRecords;
	}
}
