package com.ff.web.booking.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.constants.BookingExcelConstants;
import com.ff.web.booking.converter.BulkBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.BackDatedBookingValidator;
import com.ff.web.booking.validator.BulkReturnObject;
import com.ff.web.manifest.constants.OutManifestConstants;

/**
 * The Class BackdatedBookingServiceImpl.
 */
public class BackdatedBookingServiceImpl implements BackdatedBookingService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BackdatedBookingServiceImpl.class);

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;
	private ConsignmentCommonDAO consignmentCommonDAO;
	private Properties errorBundle;
	private Properties rateErrorBundle;

	/**
	 * @return the errorBundle
	 */
	public Properties getErrorBundle() {
		return errorBundle;
	}

	/**
	 * @param errorBundle
	 *            the errorBundle to set
	 */
	public void setErrorBundle(Properties errorBundle) {
		this.errorBundle = errorBundle;
	}

	/**
	 * @return the rateErrorBundle
	 */
	public Properties getRateErrorBundle() {
		return rateErrorBundle;
	}

	/**
	 * @param rateErrorBundle
	 *            the rateErrorBundle to set
	 */
	public void setRateErrorBundle(Properties rateErrorBundle) {
		this.rateErrorBundle = rateErrorBundle;
	}

	/** The back dated booking Validator. */
	private BackDatedBookingValidator backDatedBookingValidator;

	/** The booking common DAO. */
	private BookingCommonDAO bookingCommonDAO;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/**
	 * @return the consignmentCommonDAO
	 */
	public ConsignmentCommonDAO getConsignmentCommonDAO() {
		return consignmentCommonDAO;
	}

	/**
	 * @param consignmentCommonDAO
	 *            the consignmentCommonDAO to set
	 */
	public void setConsignmentCommonDAO(
			ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * Gets the geography common service.
	 * 
	 * @return the geography common service
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Gets the organization common service.
	 * 
	 * @return the organization common service
	 */
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	/**
	 * Sets the organization common service.
	 * 
	 * @param organizationCommonService
	 *            the new organization common service
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the back dated booking validator.
	 * 
	 * @param backDatedBookingValidator
	 *            the new back dated booking validator
	 */
	public void setBackDatedBookingValidator(
			BackDatedBookingValidator backDatedBookingValidator) {
		this.backDatedBookingValidator = backDatedBookingValidator;
	}

	/**
	 * Sets the booking common dao.
	 * 
	 * @param bookingCommonDAO
	 *            the new booking common dao
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/**
	 * Sets the booking common service.
	 * 
	 * @param bookingCommonService
	 *            the new booking common service
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BackdatedBookingService#saveOrUpdateBookings
	 * (java.util.List)
	 */
	public BulkReturnObject handleBackDatedBooking(List<Object> list,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BackdatedBookingServiceImpl::callBackdatededBookings::START------------>:::::::");
		List<BookingDO> bookingDOs = null;
		List<ConsignmentDO> consignmentDOs = null;
		BulkReturnObject bulkReturnObject = null;
		if (!StringUtil.isNull(list.get(0))) {
			bookingDOs = (List<BookingDO>) list.get(0);
		}
		if (!StringUtil.isNull(list.get(1))) {
			consignmentDOs = (List<ConsignmentDO>) list.get(1);
		}
		bulkReturnObject = callBackdatededBookings(bookingDOs, consignmentDOs,
				cnValidation);
		LOGGER.debug("BackdatedBookingServiceImpl::callBackdatededBookings::END------------>:::::::");
		return bulkReturnObject;
	}

	private BulkReturnObject callBackdatededBookings(
			List<BookingDO> bookingDOs, List<ConsignmentDO> consignmentDOs,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("BackdatedBookingServiceImpl::callBackdatededBookings::START------------>:::::::");
		// boolean isSaved = Boolean.FALSE;
		BulkReturnObject bulkBooking = new BulkReturnObject();
		bulkBooking = backDatedBookingValidator.validateBackdatedBookings(
				bookingDOs, consignmentDOs, cnValidation);
		List<BookingDO> bookings = bulkBooking.getValidBookings();
		List<ConsignmentDO> consignments = bulkBooking.getValidConsignments();
		// int successCount = bookings.size();
		List<BookingDO> invalidBookings = bulkBooking.getInvalidBookings();
		List<ConsignmentDO> invalidConsignments = bulkBooking
				.getInvalidConsignments();
		// List<String> workingCNList = null;

		if (!StringUtil.isEmptyList(bookings)
				&& !StringUtil.isEmptyList(consignments)) {
			calculateRateForValidConsignment(consignments, bookings,
					invalidConsignments, invalidBookings);

			for (int i = 0; i < bookings.size(); i++) {
				BookingDO booking = bookings.get(i);
				ConsignmentDO consignment = consignments.get(i);

				try {
					// Individual save
					bookingCommonService.saveBookingAndConsignment(booking,
							consignment);

				} catch (Exception iex) {
					invalidBookings.add(booking);
					invalidConsignments.add(consignment);

				}
			}
		}

		if(bookingDOs.size()==bulkBooking.getInvalidBookings().size()){
			bulkBooking.setIsAllInvalidBooking(CommonConstants.YES);
		}
			
		if (!StringUtil.isEmptyList(bulkBooking.getInvalidBookings())) {
			List<List> errList = new ArrayList<>();
			List<String> headerList = BookingUtils.getHeaderList();
			headerList.add(BookingExcelConstants.ERROR_DESCRIPTION);
			errList.add(headerList);
			errList = getBookingErrorList(errList,
					bulkBooking.getInvalidBookings(),
					bulkBooking.getInvalidConsignments());
			bulkBooking.setErrList(errList);
		}

		LOGGER.debug("BackdatedBookingServiceImpl::callBackdatededBookings::END------------>:::::::");
		return bulkBooking;

	}

	private void calculateRateForValidConsignment(
			List<ConsignmentDO> consignments, List<BookingDO> bookings,
			List<ConsignmentDO> invalidConsignments,
			List<BookingDO> invalidBookings) {
		LOGGER.debug("BackdatedBookingServiceImpl::calculateRateForValidConsignment::START------------>:::::::");
		/* Calculate Rate For CN */
		ConsignmentDO consgDO = null;
		BookingDO bookingDO = null;
		ConsignmentTO consignmentTO = null;
		Set<ConsignmentBillingRateDO> consgBillingRateDOs = null;
		ConsignmentBillingRateDO consgBillingRateDO = null;
		int size = consignments.size();
		List<String> invalidConsignmentNos = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			/* Prepare Consignment Rate Input TO */
			consgDO = consignments.get(i);
			bookingDO = bookings.get(i);
			try {
				consignmentTO = BulkBookingConverter.convertConsignmentDOToTO(
						consgDO, bookingCommonService);

				if (!StringUtil.isNull(bookingDO.getFianlWeight())) {
					consignmentTO.setFinalWeight(bookingDO.getFianlWeight());
				}

				consignmentTO.setBookingDate(bookingDO.getBookingDate());

				CNPricingDetailsTO cnPricing = consignmentTO
						.getConsgPriceDtls();

				if (!StringUtil.isNull(bookingDO.getDeclaredValue())) {
					cnPricing.setDeclaredvalue(bookingDO.getDeclaredValue());
				}

				if (!StringUtil.isStringEmpty(bookingDO.getDlvTime())
						&& !StringUtil.isStringEmpty(consgDO.getProductDO()
								.getConsgSeries())) {
					if (StringUtils.equalsIgnoreCase(
							CommonConstants.PRODUCT_SERIES_PRIORITY, consgDO
									.getProductDO().getConsgSeries())) {
						String priorityService = bookingDO.getDlvTime().split(
								CommonConstants.HYPHEN)[0];
						if (!StringUtil.isStringEmpty(priorityService)) {
							cnPricing.setServicesOn(priorityService);
						}
					}

				}

				if (!StringUtil.isNull(bookingDO.getBookingType())
						&& !StringUtil.isNull(bookingDO.getBookingType()
								.getBookingType())) {
					String bookingType = bookingDO.getBookingType()
							.getBookingType();
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.CASH_BOOKING, bookingType) 
							|| StringUtils.equalsIgnoreCase(
									BookingConstants.FOC_BOOKING, bookingType)) {
						cnPricing
								.setRateType(RateCommonConstants.RATE_TYPE_CASH);
						consgDO.setRateType(cnPricing.getRateType());
					} else if (StringUtils.equalsIgnoreCase(
							BookingConstants.CCC_BOOKING, bookingType)) {
						cnPricing
								.setRateType(RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
						consgDO.setRateType(cnPricing.getRateType());
					} else if (StringUtils.equalsIgnoreCase(
							BookingConstants.BA_BOOKING, bookingType)) {
						cnPricing.setRateType(RateCommonConstants.RATE_TYPE_BA);
						consgDO.setRateType(cnPricing.getRateType());
					}

				}
				if (!StringUtil.isNull(bookingDO.getCustomerId())
						&& !StringUtil.isNull(bookingDO.getCustomerId()
								.getCustomerId())) {
					consignmentTO.setCustomer(bookingDO.getCustomerId()
							.getCustomerId());
				}

				if (!StringUtil.isNull(bookingDO.getCustomerId())
						&& !StringUtil.isNull(bookingDO.getCustomerId()
								.getCustomerType())) {
					if (StringUtils.equalsIgnoreCase(
							CommonConstants.CUSTOMER_CODE_ACC, bookingDO
									.getCustomerId().getCustomerType()
									.getCustomerTypeCode())) {
						cnPricing
								.setRateType(RateCommonConstants.RATE_TYPE_CASH);
					}
				}

				consignmentTO.setConsgPriceDtls(cnPricing);
				/** Calculate Rate */
				ConsignmentRateCalculationOutputTO calculatedRate = bookingCommonService
						.calcRateForConsingment(consignmentTO);
				/** Prepare Rate Calculation OutPut DO */
				if (!StringUtil.isNull(calculatedRate)) {
					/* prepare consgBillingRateDO */
					consgBillingRateDO = BulkBookingConverter
							.prepareCNBillingRateDO(calculatedRate);
					consgBillingRateDO
							.setRateCalculatedFor(BookingConstants.BOOKING_NORMAL_PROCESS);
					consgBillingRateDO.setBilled(CommonConstants.NO);
					consgBillingRateDO.setUpdatedBy(consgDO.getUpdatedBy());
				}
				if (!StringUtil.isNull(consgBillingRateDO)) {
					consgBillingRateDOs = new HashSet<ConsignmentBillingRateDO>();
					// Set Topay amount for T-Series
					if (StringUtil.endsWithIgnoreCase(consgDO.getConsgNo()
							.substring(4, 5),
							OutManifestConstants.CONSG_SERIES_T)) {
						consgDO.setTopayAmt(consgBillingRateDO
								.getGrandTotalIncludingTax());
					}
					consgBillingRateDO.setConsignmentDO(consgDO);
					consgBillingRateDO.setCreatedDate(new Date());
					consgBillingRateDO.setCreatedBy(consgDO.getCreatedBy());
					consgBillingRateDO.setUpdatedBy(consgDO.getUpdatedBy());
					consgBillingRateDOs.add(consgBillingRateDO);
					consgDO.setConsgRateDtls(consgBillingRateDOs);
				
				}

			} catch (CGBusinessException | CGSystemException e) {
				consgDO.setErrorCode(e.getMessage());
				invalidConsignments.add(consgDO);
				invalidConsignmentNos.add(consgDO.getConsgNo());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				consgDO.setErrorCode(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				invalidConsignments.add(consgDO);
				invalidConsignmentNos.add(consgDO.getConsgNo());
			}
		}

		ConsignmentDO errorCnDO = null;
		if (!CGCollectionUtils.isEmpty(invalidConsignmentNos)) {
			for (String cnNo : invalidConsignmentNos) {
				for (ConsignmentDO cnDO : consignments) {
					if (cnDO.getConsgNo().equalsIgnoreCase(cnNo)) {
						consignments.remove(cnDO);
						errorCnDO = cnDO;
						break;
					}
				}
				for (BookingDO cnBkDO : bookings) {
					if (cnBkDO.getConsgNumber().equalsIgnoreCase(cnNo)) {
						List<String> errorCodes = new ArrayList<>();
						if (!StringUtil.isNull(errorCnDO)) {
							errorCodes.add(errorCnDO.getErrorCode());
						} else {
							errorCodes
									.add(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
						}
						cnBkDO.setErrorCodes(errorCodes);
						invalidBookings.add(cnBkDO);
						bookings.remove(cnBkDO);
						break;
					}
				}
			}
		}
		LOGGER.debug("BackdatedBookingServiceImpl::calculateRateForValidConsignment::END------------>:::::::");
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.BackdatedBookingService#getAllRegions()
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BackdatedBookingService#getAllOffices(java
	 * .lang.Integer)
	 */
	public List<OfficeTO> getAllOffices(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BackdatedBookingServiceImpl::getAllOffices::START------------>:::::::");
		OfficeTO officeTO = new OfficeTO();
		officeTO.setOfficeName("--Select--");
		List<OfficeTO> officeTOList = new ArrayList<>();
		officeTOList.add(officeTO);
		List<OfficeTO> officeTOList1 = organizationCommonService
				.getAllOffices(regionId);
		officeTOList.addAll(officeTOList1);
		LOGGER.debug("BackdatedBookingServiceImpl::getAllOffices::END------------>:::::::");
		return officeTOList;
	}

	/**
	 * Gets the booking error list.
	 * 
	 * @param errList
	 *            the err list
	 * @param backDatedBooking
	 *            the back dated booking
	 * @return the booking error list
	 */
	private List<List> getBookingErrorList(List<List> errList,
			List<BookingDO> invalidBookings,
			List<ConsignmentDO> invalidConsignments) {
		LOGGER.debug("BackdatedBookingServiceImpl::getBookingErrorList::START------------>:::::::");
		int index = 0;
		for (BookingDO invalidBooking : invalidBookings) {
			ConsignmentDO consignmentDO = invalidConsignments.get(index);
			ConsigneeConsignorDO consigneeTO = null;
			ConsigneeConsignorDO consgTO = null;
			List l1 = new ArrayList<>();
			l1.add(invalidBooking.getConsgNumber());

			String custCode = null;
			CustomerDO customerDO = invalidBooking.getCustomerId();
			if (!StringUtil.isNull(customerDO)) {
				custCode = invalidBooking.getCustomerId().getCustomerCode();
			}

			l1.add(StringUtils.isNotEmpty(custCode) ? custCode
					: CommonConstants.EMPTY_STRING);

			l1.add(invalidBooking.getRefNo());

			String bookinType = null;
			BookingTypeDO bookingTypeDO = invalidBooking.getBookingType();
			if (!StringUtil.isNull(bookingTypeDO)) {
				bookinType = invalidBooking.getBookingType().getBookingType();
			}

			l1.add(StringUtils.isNotEmpty(bookinType) ? bookinType
					: CommonConstants.EMPTY_STRING);

			String consgType = null;
			ConsignmentTypeDO typeDO = invalidBooking.getConsgTypeId();
			if (!StringUtil.isNull(typeDO)) {
				consgType = invalidBooking.getConsgTypeId()
						.getConsignmentCode();
			}

			l1.add(StringUtils.isNotEmpty(consgType) ? consgType
					: CommonConstants.EMPTY_STRING);

			String pincode = null;
			PincodeDO pin = invalidBooking.getPincodeId();
			if (!StringUtil.isNull(pin)) {
				pincode = invalidBooking.getPincodeId().getPincode();
			}
			l1.add(StringUtils.isNotEmpty(pincode) ? pincode
					: CommonConstants.EMPTY_STRING);
			l1.add(!StringUtil.isEmptyDouble(invalidBooking.getActualWeight()) ? invalidBooking
					.getActualWeight().toString() : invalidBooking
					.getActWtStr());
			l1.add(!StringUtil.isEmptyDouble(invalidBooking.getVolWeight()) ? invalidBooking
					.getVolWeight().toString() : invalidBooking.getVolWtStr());

			String name = null;
			String mobile = null;
			String address = null;

			consgTO = invalidBooking.getConsignorId();
			if (!StringUtil.isNull(consgTO)) {
				name = consgTO.getFirstName();
				mobile = consgTO.getMobile();
				address = consgTO.getAddress();

			}
			l1.add(StringUtils.isNotEmpty(name) ? name
					: CommonConstants.EMPTY_STRING);
			l1.add(StringUtils.isNotEmpty(mobile) ? mobile
					: CommonConstants.EMPTY_STRING);
			l1.add(StringUtils.isNotEmpty(address) ? address
					: CommonConstants.EMPTY_STRING);

			String name1 = null;
			String mobile1 = null;
			String address1 = null;
			String phon1 = null;
			consigneeTO = invalidBooking.getConsigneeId();
			if (!StringUtil.isNull(consigneeTO)) {
				name1 = consigneeTO.getFirstName();
				mobile1 = consigneeTO.getMobile();
				address1 = consigneeTO.getAddress();
				phon1 = consigneeTO.getPhone();

			}

			l1.add(StringUtils.isNotEmpty(name1) ? name1
					: CommonConstants.EMPTY_STRING);
			l1.add(StringUtils.isNotEmpty(address1) ? address1
					: CommonConstants.EMPTY_STRING);
			l1.add(StringUtils.isNotEmpty(phon1) ? phon1
					: CommonConstants.EMPTY_STRING);
			l1.add(StringUtils.isNotEmpty(mobile1) ? mobile1
					: CommonConstants.EMPTY_STRING);
			String contentName = "";
			CNContentDO cnContentDO = consignmentDO.getCnContentId();

			if (!StringUtil.isNull(cnContentDO)) {
				if (!StringUtil.isStringEmpty(cnContentDO.getCnContentName())) {
					if (cnContentDO.getCnContentName().equalsIgnoreCase(
							"Others")) {
						contentName = consignmentDO.getOtherCNContent();
					} else {
						contentName = consignmentDO.getCnContentId()
								.getCnContentName();
					}
				}
			}
			l1.add(StringUtils.isNotEmpty(contentName) ? contentName
					: CommonConstants.EMPTY_STRING);

			// l1.add(invalidBooking.getCnContentId().getCnContentName());
			l1.add(!StringUtil.isEmptyDouble(invalidBooking.getDeclaredValue()) ? invalidBooking
					.getDeclaredValue().toString() : invalidBooking
					.getDecValStr());

			String Insured = null;
			InsuredByDO insuredByDO = invalidBooking.getInsuredBy();
			if (!StringUtil.isNull(insuredByDO)) {
				Insured = invalidBooking.getInsuredBy().getInsuredByDesc();
			}
			l1.add(StringUtils.isNotEmpty(Insured) ? Insured
					: CommonConstants.EMPTY_STRING);

			l1.add(invalidBooking.getInsurencePolicyNo());
			String paperWork = null;
			CNPaperWorksDO paperWorksDO = consignmentDO.getCnPaperWorkId();
			if (!StringUtil.isNull(paperWorksDO)) {
				paperWork = consignmentDO.getCnPaperWorkId()
						.getCnPaperWorkName();
			}
			l1.add(StringUtils.isNotEmpty(paperWork) ? paperWork
					: CommonConstants.EMPTY_STRING);
			l1.add(consignmentDO.getPaperWorkRefNo());
			l1.add(!StringUtil.isEmptyDouble(consignmentDO.getCodAmt()) ? consignmentDO
					.getCodAmt().toString() : consignmentDO.getCodAmtStr());

			l1.add(invalidBooking.getDlvTime());
			
			l1.add(!StringUtil.isStringEmpty(consignmentDO.getLcAmtStr()) ? consignmentDO
					.getLcAmtStr().toString() : consignmentDO.getLcAmtStr());

			l1.add(StringUtils.isNotEmpty(consignmentDO.getLcBankName()) ? consignmentDO
					.getLcBankName() : CommonConstants.EMPTY_STRING);
			l1.add(getErrorMessages(invalidBooking.getErrorCodes()));
			// l1.add(getErrorMessages(backDatedBooking.getErrorCodeslst().get(index)));
			index++;
			errList.add(l1);

		}
		LOGGER.debug("BackdatedBookingServiceImpl::getBookingErrorList::END------------>:::::::");
		return errList;

	}

	/**
	 * Gets the error messages.
	 * 
	 * @param errorCodes
	 *            the error codes
	 * @return the error messages
	 */
	private String getErrorMessages(List<String> errorCodes) {
		LOGGER.debug("BackdatedBookingServiceImpl::getErrorMessages::Start------------>:::::::");
		StringBuilder errorMsgs = new StringBuilder();
		if (!CGCollectionUtils.isEmpty(errorCodes)) {
			for (String errorCode : errorCodes) {
				String errorMsg = null;
				errorMsg = errorBundle.getProperty(errorCode);
				if (StringUtil.isStringEmpty(errorMsg)) {
					errorMsg = rateErrorBundle.getProperty(errorCode);
				}
				if (StringUtil.isStringEmpty(errorMsg)) {
					errorMsg = "Error is missing!";
				}
				errorMsgs.append(errorMsg);
				errorMsgs.append(CommonConstants.COMMA);
			}
		} else {
			errorMsgs.append("Error is missing!");
		}
		LOGGER.debug("BackdatedBookingServiceImpl::getErrorMessages::END------------>:::::::");
		return errorMsgs.toString();

	}

}
