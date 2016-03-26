/**
 * 
 */
package com.ff.web.booking.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.geography.CityTO;
import com.ff.jobservices.JobServicesTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.jobservice.service.JobServicesUniversalService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.constants.BookingExcelConstants;
import com.ff.web.booking.converter.BulkBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.BulkBookingValidator;
import com.ff.web.booking.validator.BulkReturnObject;
import com.ff.web.manifest.constants.OutManifestConstants;

/**
 * The Class BulkBookingServiceImpl.
 * 
 * @author uchauhan
 */
public class BulkBookingServiceImpl implements BulkBookingService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkBookingServiceImpl.class);
	/** The bulk booking validator. */
	private BulkBookingValidator bulkBookingValidator;

	/** The credit customer booking service. */
	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;
	private BookingCommonService bookingCommonService;
	private ConsignmentCommonDAO consignmentCommonDAO;
	private JobServicesUniversalService jobService;
	private Properties errorBundle;
	private Properties rateErrorBundle;

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

	public void setJobService(JobServicesUniversalService jobService) {
		this.jobService = jobService;
	}

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
	 * @param bookingCommonDAO
	 *            the bookingCommonDAO to set
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/**
	 * @param bookingCommonService
	 *            the bookingCommonService to set
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/**
	 * @param bulkBookingValidator
	 *            the bulkBookingValidator to set
	 */
	public void setBulkBookingValidator(
			BulkBookingValidator bulkBookingValidator) {
		this.bulkBookingValidator = bulkBookingValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BulkBookingService#saveOrUpdateBookings(java
	 * .util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BulkReturnObject handleBulkBooking(List<Object> list,
			List<String> cnNoList, BookingValidationTO cnValidation,
			String jobNumber) throws CGBusinessException, CGSystemException {
		List<BookingDO> bookingDOs = null;
		List<ConsignmentDO> consignmentDOs = null;
		BulkReturnObject bulkReturnObject = null;
		try {
			if (!StringUtil.isNull(list.get(0))) {
				bookingDOs = (List<BookingDO>) list.get(0);
			}
			if (!StringUtil.isNull(list.get(0))) {
				consignmentDOs = (List<ConsignmentDO>) list.get(1);
			}
			bulkReturnObject = callBulkBookingProcess(bookingDOs,
					consignmentDOs, cnNoList, cnValidation, jobNumber);

		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : BulkBookingServiceImpl.saveOrUpdateBookings()", e);
			throw new CGSystemException(e);

		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : BulkBookingServiceImpl.saveOrUpdateBookings()", e);
			ExceptionUtil.prepareBusinessException(e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return bulkReturnObject;
	}

	/**
	 * Call backdateded bookings.
	 * 
	 * @param ccBookings
	 *            the cc bookings
	 * @param cnValidation
	 * @return the credit customer booking parcel to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BulkReturnObject callBulkBookingProcess(List<BookingDO> bookingDOs,
			List<ConsignmentDO> consignmentDOs, List<String> cnNoList,
			BookingValidationTO cnValidation, String jobNumber)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BulkBookingServiceImpl::callBulkBookingProcess::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		BulkReturnObject bulkBooking = bulkBookingValidator
				.validateBulkBookings(bookingDOs, consignmentDOs, cnNoList,
						cnValidation);
		resetJobStatus("Booking business rules validation completed",
				jobNumber, null, 50, "I", null, null);

		List<BookingDO> bookings = bulkBooking.getValidBookings();
		List<ConsignmentDO> consignments = bulkBooking.getValidConsignments();
		int successCount = bookings.size();

		List<BookingDO> invalidBookings = bulkBooking.getInvalidBookings();
		List<ConsignmentDO> invalidConsignments = bulkBooking
				.getInvalidConsignments();
		List<String> workingCNList = null;
		List<BookingDO> bookingDOFailedList=new ArrayList<BookingDO>();
		List<ConsignmentDO> consgDOFailedList=new ArrayList<ConsignmentDO>();
		if (cnNoList != null && !cnNoList.isEmpty()) {
			workingCNList = new ArrayList<String>(cnNoList);
		}

		if (!StringUtil.isEmptyList(bookings)
				&& !StringUtil.isEmptyList(consignments)) {
			calculateRateForValidConsignment(consignments, bookings,
					invalidConsignments, invalidBookings);
			
			boolean isCnUsed = true;
			String cnNumber = null;
		
			for (int i = 0; i < bookings.size(); i++) {
				BookingDO booking = bookings.get(i);
				ConsignmentDO consignment = consignments.get(i);
				if (cnNoList != null && !cnNoList.isEmpty()) {
					if (isCnUsed)
						cnNumber = cnNoList.remove(0);
					booking.setConsgNumber(cnNumber);
					consignment.setConsgNo(cnNumber);
				}
				try {
					// Individual save
					if(!StringUtil.isNull(booking) && !StringUtil.isEmptyInteger(booking.getBookingId())){
						BookingTO ccBooking = bookingCommonService
								.getBookingDtls(booking.getConsgNumber());
						if(!StringUtil.isNull(ccBooking)){
							booking.setBookingId(ccBooking.getBookingId());
							booking.setPickRunsheetNo(ccBooking.getPickupRunsheetNo());
						}
					}
					bookingCommonService.saveBookingAndConsignment(booking,
							consignment);
					isCnUsed = true;
				} catch (Exception iex) {
					LOGGER.error(
							"BulkBookingServiceImpl::callBulkBookingProcess::individual save...failed for consignment=>"
									+ consignment.getConsgNo(), iex);
					List<String> errorList = new ArrayList<String>(1);
					String excpDetails = ExceptionUtil.getExceptionDetails(iex);
					String erroCode = "BLK004";
					if (GlobalErrorCodeConstants.DUPLICATE_KEY_VIOLATION
							.equals(excpDetails)) {
						erroCode = "BLK003";
					}else{
						isCnUsed = false;
					}
					errorList.add(erroCode);
					booking.setErrorCodes(errorList);
					invalidBookings.add(booking);
					invalidConsignments.add(consignment);
					bookingDOFailedList.add(booking);
					consgDOFailedList.add(consignment);
					successCount = successCount - 1;
				}
			}
		}

		for(int i=0;i<bookingDOFailedList.size();i++){
			bookings.remove(bookingDOFailedList.get(i));
			consignments.remove(consgDOFailedList.get(i));

		}
		cnNoList = workingCNList;
		resetJobStatus("Checking for error list", jobNumber, null, 80, "I",
				null, null);

		if (!StringUtil.isEmptyList(invalidBookings)) {
			bulkBooking.setFailedBookingCount(bulkBooking.getInvalidBookings()
					.size());
			bulkBooking.setSuccessBookingCount(successCount);
			List<List> errList = new ArrayList<>();
			List<String> headerList = BookingUtils.getBulkHeaderList();
			headerList.add(BookingExcelConstants.ERROR_DESCRIPTION);
			errList.add(headerList);
			errList = getBookingErrorList(errList, invalidBookings,
					invalidConsignments, cnNoList);
			bulkBooking.setErrList(errList);

		}

		resetJobStatus("Error list check completed", jobNumber, null, 85, "I",
				null, null);

		// PREPARING SUCCESS LIST

		resetJobStatus("Checking for success list", jobNumber, null, 86, "I",
				null, null);

		if (!CGCollectionUtils.isEmpty(bookings)) {
			bulkBooking.setSuccessBookingCount(bulkBooking.getValidBookings()
					.size());

			List<List> successList = new ArrayList<>();

			List<String> headerList = BookingUtils.getBulkHeaderList();
			headerList.add(BookingExcelConstants.SUCCESS_DESCRIPTION);
			successList.add(headerList);

			successList = getBookingSuccessList(successList, bookings,
					consignments, cnNoList);

			bulkBooking.setSuccessList(successList);

		}

		// Code Added Ends

		resetJobStatus("Success list check completed", jobNumber, null, 90,
				"I", null, null);
		LOGGER.debug("BulkBookingServiceImpl::callBulkBookingProcess::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bulkBooking;
	}

	// Code Added
	/*
	 * Method : getBookingSuccessList
	 */

	/**
	 * @param successList
	 * @param validBookings
	 * @param validConsignments
	 * @param cnNoList
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<List> getBookingSuccessList(List<List> successList,
			List<BookingDO> validBookings,
			List<ConsignmentDO> validConsignments, List<String> cnNoList) {

		LOGGER.debug("BulkBookingServiceImpl::getBookingSuccessList::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());

		int cnNoCount = 0;

		for (BookingDO validBookingDO : validBookings) {
			try {

				String[] addrArr = null;
				String phone = null;
				String mobile = null;
				String firstName = null;
				String address = null;
				String city = null;
				String pincode = null;
				String state = null;
				String country = null;
				String transcNo = null;

				// For Vendor Details
				String vendrName = null;
				String pickUpLoc = null;
				String vendrContctNo = null;
				String paymntMethod = null;

				// For Alternate Address
				String pincodeAlt = null;
				String countryAlt = null;
				String stateAlt = null;
				String cityAlt = null;

				ConsigneeConsignorDO consigneeDO = null;
				ConsigneeConsignorDO altConsigneeDO = null;
				ConsignmentDO consignmentDO = validConsignments.get(cnNoCount);

				List l1 = new ArrayList<>();

				l1.add(!StringUtil.isNull(validBookingDO.getBookingDate())
						&& !StringUtil.isNull(validBookingDO.getBookingDate()) ? DateUtil
						.getDDMMYYYYDateString(validBookingDO.getBookingDate())
						: CommonConstants.EMPTY_STRING);

				l1.add(!StringUtil.isStringEmpty(validBookingDO
						.getConsgNumber()) ? validBookingDO.getConsgNumber()
						.toString() : CommonConstants.EMPTY_STRING);

				/*
				 * l1.add(cnNoList == null ? validBookingDO.getConsgNumber() :
				 * cnNoList.get(cnNoCount));
				 */

				// l1.add(cnNoList == null ? validBookingDO.getConsgNumber() :
				// cnNoList.get(cnNoCount));

				cnNoCount++;

				consigneeDO = new ConsigneeConsignorDO();
				if (!StringUtil.isNull(validBookingDO.getConsigneeId())) {
					consigneeDO = validBookingDO.getConsigneeId();
				}

				if (!StringUtil.isNull(consigneeDO)) {
					if (!StringUtil.isStringEmpty(consigneeDO.getAddress())) {
						addrArr = consigneeDO.getAddress().split(
								CommonConstants.COMMA);

						phone = consigneeDO.getPhone();
						mobile = consigneeDO.getMobile();
						firstName = consigneeDO.getFirstName();
						address = validBookingDO.getConsigneeAddr();
					}
				}

				l1.add(!StringUtil.isNull(validBookingDO.getRefNo())
						&& !StringUtil.isNull(validBookingDO.getRefNo()) ? validBookingDO
						.getRefNo().toString() : CommonConstants.EMPTY_STRING);

				// Consignee Name (First Name)
				l1.add(StringUtils.isNotEmpty(firstName) ? firstName
						: CommonConstants.EMPTY_STRING);

				// Content Name
				String contentName = "";
				CNContentDO cnContentDO = consignmentDO.getCnContentId();

				if (!StringUtil.isNull(cnContentDO)) {
					if (!StringUtil.isStringEmpty(cnContentDO
							.getCnContentName())) {
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

				// Address
				l1.add(StringUtils.isNotEmpty(address) ? address
						: CommonConstants.EMPTY_STRING);

				if (!StringUtil.isEmpty(addrArr)) {

					pincode = addrArr[addrArr.length - 1];
					country = addrArr[addrArr.length - 2];
					state = addrArr[addrArr.length - 3];
					city = addrArr[addrArr.length - 4];
				}

				// City
				l1.add(StringUtils.isNotEmpty(city) && !city.equals("~") ? city
						: CommonConstants.EMPTY_STRING);

				// Pincode
				l1.add(StringUtils.isNotEmpty(pincode) && !pincode.equals("~") ? pincode
						: CommonConstants.EMPTY_STRING);

				// State
				l1.add(StringUtils.isNotEmpty(state) && !state.equals("~") ? state
						: CommonConstants.EMPTY_STRING);

				// Country
				l1.add(StringUtils.isNotEmpty(country) && !country.equals("~") ? country
						: CommonConstants.EMPTY_STRING);

				// Final Weight
				l1.add(!StringUtil.isEmptyDouble(validBookingDO
						.getFianlWeight()) ? validBookingDO.getFianlWeight()
						.toString() : validBookingDO.getActWtStr());

				// Phone No.
				l1.add(StringUtils.isNotEmpty(phone) ? phone
						: CommonConstants.EMPTY_STRING);

				// Mobile No.
				l1.add(StringUtils.isNotEmpty(mobile) ? mobile
						: CommonConstants.EMPTY_STRING);

				BulkBookingVendorDtlsDO bulkBookingVendorDtlsDO = validBookingDO
						.getBulkBookingVendorDtls();

				// Adding Vendor related information to the list
				if (!StringUtil.isNull(bulkBookingVendorDtlsDO)) {

					transcNo = bulkBookingVendorDtlsDO.getTransactionNo();
					vendrName = bulkBookingVendorDtlsDO.getVendorName();
					pickUpLoc = bulkBookingVendorDtlsDO.getVendorPickupLoc();
					vendrContctNo = bulkBookingVendorDtlsDO
							.getVendorContactNo();
					paymntMethod = bulkBookingVendorDtlsDO
							.getPaymentMethodType();
				}

				// Transaction No.
				l1.add(StringUtils.isNotEmpty(transcNo) ? transcNo
						: CommonConstants.EMPTY_STRING);

				// COD Amount
				l1.add(!StringUtil.isStringEmpty(consignmentDO.getCodAmtStr()) ? consignmentDO
						.getCodAmtStr() : consignmentDO.getCodAmtStr());

				// No. of Pieces
				l1.add(!StringUtil.isStringEmpty(validBookingDO
						.getNoOfPcsStr()) ? validBookingDO.getNoOfPcsStr() : CommonConstants.EMPTY_STRING);

				// Vendor Name
				l1.add(StringUtils.isNotEmpty(vendrName) ? vendrName
						: CommonConstants.EMPTY_STRING);

				// Vendor PickUp Location
				l1.add(StringUtils.isNotEmpty(pickUpLoc) ? pickUpLoc
						: CommonConstants.EMPTY_STRING);

				// Vendor Contact No.
				l1.add(StringUtils.isNotEmpty(vendrContctNo) ? vendrContctNo
						: CommonConstants.EMPTY_STRING);

				// Payment Method Type
				l1.add(StringUtils.isNotEmpty(paymntMethod) ? paymntMethod
						: CommonConstants.EMPTY_STRING);

				// Order Date
				l1.add(validBookingDO.getOrderDate());

				// LC Amount
				l1.add(!StringUtil.isStringEmpty(consignmentDO.getLcAmtStr()) ? consignmentDO
						.getLcAmtStr() : consignmentDO.getLcAmtStr());

				// LC Bank
				l1.add(StringUtils.isNotEmpty(consignmentDO.getLcBankName()) ? consignmentDO
						.getLcBankName() : CommonConstants.EMPTY_STRING);

				// Declared value
				l1.add(!StringUtil.isStringEmpty(validBookingDO.getDecValStr()) ? validBookingDO
						.getDecValStr() : validBookingDO.getDecValStr());

				// Insured by
				String Insured = null;
				InsuredByDO insuredByDO = validBookingDO.getInsuredBy();
				if (!StringUtil.isNull(insuredByDO)) {
					Insured = validBookingDO.getInsuredBy().getInsuredByDesc();
				}
				l1.add(StringUtils.isNotEmpty(Insured) ? Insured
						: CommonConstants.EMPTY_STRING);

				// Policy No
				l1.add(!StringUtil.isNull(validBookingDO)
						&& !StringUtil.isStringEmpty(validBookingDO
								.getInsurencePolicyNo()) ? validBookingDO
						.getInsurencePolicyNo() : CommonConstants.EMPTY_STRING);

				// Alternate Address
				String[] addrArrAlt = null;
				if (!StringUtil.isNull(consignmentDO.getAltConsigneeAddr())) {
					altConsigneeDO = consignmentDO.getAltConsigneeAddr();
				}

				if (!StringUtil.isNull(altConsigneeDO)) {
					if (!StringUtil.isStringEmpty(altConsigneeDO.getAddress())) {
						addrArrAlt = altConsigneeDO.getAddress().split(
								CommonConstants.COMMA);
					}
				}

				l1.add(!StringUtil.isStringEmpty(validBookingDO
						.getAltConsigneeAddr()) ? validBookingDO
						.getAltConsigneeAddr() : CommonConstants.EMPTY_STRING);

				if (!StringUtil.isEmpty(addrArrAlt)) {
					pincodeAlt = addrArrAlt[addrArrAlt.length - 1];
					countryAlt = addrArrAlt[addrArrAlt.length - 2];
					stateAlt = addrArrAlt[addrArrAlt.length - 3];
					cityAlt = addrArrAlt[addrArrAlt.length - 4];
				}

				// Alternate City
				l1.add(StringUtils.isNotEmpty(cityAlt) && !cityAlt.equals("~") ? cityAlt
						: CommonConstants.EMPTY_STRING);

				// Alternate Pincode
				l1.add(StringUtils.isNotEmpty(pincodeAlt)
						&& !pincodeAlt.equals("~") ? pincodeAlt
						: CommonConstants.EMPTY_STRING);

				// Alternate State
				l1.add(StringUtils.isNotEmpty(stateAlt)
						&& !stateAlt.equals("~") ? stateAlt
						: CommonConstants.EMPTY_STRING);

				// Alternate Country
				l1.add(StringUtils.isNotEmpty(countryAlt)
						&& !countryAlt.equals("~") ? countryAlt
						: CommonConstants.EMPTY_STRING);

				// Success Message
				l1.add(getSuccessMessage());

				successList.add(l1);

			} catch (Exception ex) {
				LOGGER.error("BulkBookingServiceImpl::getBookingSuccessList::error::",ex);
			}
		}

		LOGGER.debug("BulkBookingServiceImpl::getBookingSuccessList::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());

		return successList;
	}

	private String getSuccessMessage() {
		String successMsg = BookingExcelConstants.SUCCESS_MSG;
		return successMsg;
	}

	// Code Added Ends

	private void calculateRateForValidConsignment(
			List<ConsignmentDO> consignments, List<BookingDO> bookings,
			List<ConsignmentDO> invalidConsignments,
			List<BookingDO> invalidBookings) {
		/** Calculate Rate For CN */
		ConsignmentDO consgDO = null;
		ConsignmentTO consignmentTO = null;
		Set<ConsignmentBillingRateDO> consgBillingRateDOs = null;
		ConsignmentBillingRateDO consgBillingRateDO = null;
		int size = consignments.size();
		List<String> invalidConsignmentNos = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			/** Prepare Consignment Rate Input TO */
			consgDO = consignments.get(i);
			try {
				consignmentTO = BulkBookingConverter.convertConsignmentDOToTO(
						consgDO, bookingCommonService);

				if (!StringUtil.isEmptyInteger(consgDO.getCustomer())) {
					CustomerTO customer = bookingCommonService
							.getCustomerByIdOrCode(consgDO.getCustomer(),
									CommonConstants.EMPTY_STRING);
					if (StringUtils.equalsIgnoreCase(
							CommonConstants.CUSTOMER_CODE_ACC, customer
									.getCustomerTypeTO().getCustomerTypeCode())) {
						consgDO.setRateType(RateCommonConstants.RATE_TYPE_CASH);
						consignmentTO.getConsgPriceDtls().setRateType(
								RateCommonConstants.RATE_TYPE_CASH);
					} else {
						consgDO.setRateType(RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
						consignmentTO.getConsgPriceDtls().setRateType(
								RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
					}

				} else {
					consgDO.setRateType(RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
					consignmentTO.getConsgPriceDtls().setRateType(
							RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
				}
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
				}else{
					LOGGER.error("Error occured in BulkBookingService :: calculateRateForValidConsignment() ::RateComponent doen not exist(throwing business exception) ");
					throw new CGBusinessException(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
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
					
					StringBuffer rateDetails=new StringBuffer("ConsignmentRate Dtsl :for  Consg No :[");
					rateDetails.append(consgDO.getConsgNo());
					rateDetails.append("] Cod Amount :[");
					rateDetails.append(consgBillingRateDO.getCodAmount());
					rateDetails.append("] LC Amount :[");
					rateDetails.append(consgBillingRateDO.getLcAmount());
					LOGGER.info("BulkBookingService :: calculateRateForValidConsignment() ::Consg rate details "+rateDetails);
					
				}

			} catch (CGBusinessException | CGSystemException e) {
				LOGGER.error("Error occured in BulkBookingService :: calculateRateForValidConsignment() ::CGbusiness/System exception for consg no "+consgDO.getConsgNo(),e);
				consgDO.setErrorCode(e.getMessage());
				invalidConsignments.add(consgDO);
				invalidConsignmentNos.add(consgDO.getConsgNo());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("Error occured in BulkBookingService :: calculateRateForValidConsignment() ::Exception for consg no "+consgDO.getConsgNo(),e);
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
	}

	/**
	 * Gets the booking error list.
	 * 
	 * @param errList
	 *            the err list
	 * @param list
	 *            the cc bookings
	 * @param consg
	 * @return the booking error list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<List> getBookingErrorList(List<List> errList,
			List<BookingDO> list, List<ConsignmentDO> consg,
			List<String> cnNoList) {
		LOGGER.debug("BulkBookingServiceImpl::getBookingErrorList::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		int cnNount = 0;
		for (BookingDO invalidBooking : list) {
			try {
				ConsigneeConsignorDO consigneeTO = null;
				ConsigneeConsignorDO altConsigneeTO = null;
				ConsignmentDO consignmentDO = consg.get(cnNount);
				List l1 = new ArrayList<>();

				l1.add(!StringUtil.isNull(invalidBooking.getBookingDate())
						&& !StringUtil.isNull(invalidBooking.getBookingDate()) ? DateUtil
						.getDDMMYYYYDateString(invalidBooking.getBookingDate())
						: CommonConstants.EMPTY_STRING);

				/*
				 * l1.add(!StringUtil.isStringEmpty(invalidBooking.getConsgNumber
				 * ()) ? invalidBooking .getConsgNumber().toString() :
				 * CommonConstants.EMPTY_STRING);
				 */

				l1.add(cnNoList == null ? invalidBooking.getConsgNumber()
						: CommonConstants.EMPTY_STRING);
				// l1.add();
				cnNount++;

				String[] addrArr = null;
				String phone = null;
				String mobile = null;
				String firstName = null;
				String address = null;
				consigneeTO = new ConsigneeConsignorDO();
				if (!StringUtil.isNull(invalidBooking.getConsigneeId())) {
					consigneeTO = invalidBooking.getConsigneeId();
				}
				if (!StringUtil.isNull(consigneeTO)) {
					if (!StringUtil.isStringEmpty(consigneeTO.getAddress())) {
						addrArr = consigneeTO.getAddress().split(
								CommonConstants.COMMA);
						phone = (consigneeTO.getPhone());
						mobile = (consigneeTO.getMobile());
						firstName = consigneeTO.getFirstName();
						address = invalidBooking.getConsigneeAddr();
					}
				}
				l1.add(!StringUtil.isNull(invalidBooking.getRefNo())
						&& !StringUtil.isNull(invalidBooking.getRefNo()) ? invalidBooking
						.getRefNo().toString() : CommonConstants.EMPTY_STRING);

				l1.add(StringUtils.isNotEmpty(firstName) ? firstName
						: CommonConstants.EMPTY_STRING);

				String contentName = "";
				CNContentDO cnContentDO = consignmentDO.getCnContentId();
				if (!StringUtil.isNull(cnContentDO)) {
					if (!StringUtil.isStringEmpty(cnContentDO
							.getCnContentName())) {
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

				l1.add(StringUtils.isNotEmpty(address) ? address
						: CommonConstants.EMPTY_STRING);

				// Getting pincode
				String pincode = null;
				// Getting Country
				String country = null;
				// Getting state
				String state = null;
				// Getting city
				String city = null;

				if (!StringUtil.isEmpty(addrArr)) {
					pincode = addrArr[addrArr.length - 1];
					country = addrArr[addrArr.length - 2];
					state = addrArr[addrArr.length - 3];
					city = addrArr[addrArr.length - 4];
				}
				l1.add(StringUtils.isNotEmpty(city) && !city.equals("~") ? city
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(pincode) && !pincode.equals("~") ? pincode
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(state) && !state.equals("~") ? state
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(country) && !country.equals("~") ? country
						: CommonConstants.EMPTY_STRING);

				l1.add((!StringUtil.isEmptyDouble(invalidBooking.getFianlWeight()) && invalidBooking.getFianlWeight() > 0.0 ) ? invalidBooking.getFianlWeight().toString() : invalidBooking.getActWtStr());

				l1.add(StringUtils.isNotEmpty(phone) ? phone
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(mobile) ? mobile
						: CommonConstants.EMPTY_STRING);
				String transcNo = null;
				String vendrName = null;
				String pickUpLoc = null;
				String vendrContctNo = null;
				String paymntMethod = null;

				BulkBookingVendorDtlsDO dtlsDO = invalidBooking
						.getBulkBookingVendorDtls();
				if (!StringUtil.isNull(dtlsDO)) {
					transcNo = dtlsDO.getTransactionNo();
					vendrName = dtlsDO.getVendorName();
					pickUpLoc = dtlsDO.getVendorPickupLoc();
					vendrContctNo = dtlsDO.getVendorContactNo();
					paymntMethod = dtlsDO.getPaymentMethodType();
				}

				l1.add(StringUtils.isNotEmpty(transcNo) ? transcNo
						: CommonConstants.EMPTY_STRING);
				l1.add(!StringUtil.isStringEmpty(consignmentDO.getCodAmtStr()) ? consignmentDO
						.getCodAmtStr() : consignmentDO.getCodAmtStr());
				l1.add(!StringUtil.isStringEmpty(invalidBooking
						.getNoOfPcsStr()) ? invalidBooking.getNoOfPcsStr() : CommonConstants.EMPTY_STRING);

				l1.add(StringUtils.isNotEmpty(vendrName) ? vendrName
						: CommonConstants.EMPTY_STRING);

				l1.add(StringUtils.isNotEmpty(pickUpLoc) ? pickUpLoc
						: CommonConstants.EMPTY_STRING);

				l1.add(StringUtils.isNotEmpty(vendrContctNo) ? vendrContctNo
						: CommonConstants.EMPTY_STRING);

				l1.add(StringUtils.isNotEmpty(paymntMethod) ? paymntMethod
						: CommonConstants.EMPTY_STRING);

				l1.add(invalidBooking.getOrderDate());

				l1.add(!StringUtil.isStringEmpty(consignmentDO.getLcAmtStr()) ? consignmentDO
						.getLcAmtStr().toString() : consignmentDO.getLcAmtStr());

				l1.add(StringUtils.isNotEmpty(consignmentDO.getLcBankName()) ? consignmentDO
						.getLcBankName() : CommonConstants.EMPTY_STRING);
				// Declared value
				l1.add(!StringUtil.isStringEmpty(invalidBooking.getDecValStr()) ? invalidBooking
						.getDecValStr() : invalidBooking.getDecValStr());
				// Insured by
				String Insured = null;
				InsuredByDO insuredByDO = invalidBooking.getInsuredBy();
				if (!StringUtil.isNull(insuredByDO)) {
					Insured = invalidBooking.getInsuredBy().getInsuredByDesc();
				}
				l1.add(StringUtils.isNotEmpty(Insured) ? Insured
						: CommonConstants.EMPTY_STRING);

				// Policy No
				l1.add(!StringUtil.isNull(invalidBooking)
						&& !StringUtil.isStringEmpty(invalidBooking
								.getInsurencePolicyNo()) ? invalidBooking
						.getInsurencePolicyNo() : CommonConstants.EMPTY_STRING);

				String[] addrArrAlt = null;
				if (!StringUtil.isNull(consignmentDO.getAltConsigneeAddr())) {
					altConsigneeTO = consignmentDO.getAltConsigneeAddr();
				}
				if (!StringUtil.isNull(altConsigneeTO)) {
					if (!StringUtil.isStringEmpty(altConsigneeTO.getAddress())) {
						addrArrAlt = altConsigneeTO.getAddress().split(
								CommonConstants.COMMA);
					}
				}
				// Alternate address
				l1.add(!StringUtil.isNull(invalidBooking.getAltConsigneeAddr()) ? invalidBooking
						.getAltConsigneeAddr() : CommonConstants.EMPTY_STRING);
				// Getting pincode
				String pincodeAlt = null;
				// Getting Country
				String countryAlt = null;
				// Getting state
				String stateAlt = null;
				// Getting city
				String cityAlt = null;
				if (!StringUtil.isEmpty(addrArrAlt)) {
					pincodeAlt = addrArrAlt[addrArrAlt.length - 1];
					countryAlt = addrArrAlt[addrArrAlt.length - 2];
					stateAlt = addrArrAlt[addrArrAlt.length - 3];
					cityAlt = addrArrAlt[addrArrAlt.length - 4];
				}

				l1.add(StringUtils.isNotEmpty(cityAlt) && !cityAlt.equals("~") ? cityAlt
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(pincodeAlt)
						&& !pincodeAlt.equals("~") ? pincodeAlt
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(stateAlt)
						&& !stateAlt.equals("~") ? stateAlt
						: CommonConstants.EMPTY_STRING);
				l1.add(StringUtils.isNotEmpty(countryAlt)
						&& !countryAlt.equals("~") ? countryAlt
						: CommonConstants.EMPTY_STRING);

				// Error message
				l1.add(getErrorMessages(invalidBooking.getErrorCodes()));
				errList.add(l1);

			} catch (Exception ex) {
				LOGGER.error("BulkBookingServiceImpl::getBookingErrorList::error::",ex);
			}

		}
		LOGGER.debug("BulkBookingServiceImpl::getBookingErrorList::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
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
		return errorMsgs.toString();
	}

	// /########################### Background Processing
	// ########################

	@Async
	public void proceedBulkBooking(CreditCustomerBookingParcelTO bulkBookingTO,
			OfficeTO loggedInOffice, final String filePath, String jobNumber) throws CGBusinessException, CGSystemException {

		
		LOGGER.trace("BulkBookingServiceImpl::proceedBulkBooking:::::----------> START" +  DateUtil.getCurrentTimeInMilliSeconds());
		List<String> seriesList = null;

		// String jobNumber = getJobNumber();
		try {
			if ((StringUtil.equals("P", bulkBookingTO.getConsgStickerType()) || StringUtil
					.equals("S", bulkBookingTO.getConsgStickerType()))
					&& !StringUtil.isEmptyInteger(bulkBookingTO.getCnCount())) {
				seriesList = seriesConverter(seriesList,
						bulkBookingTO.getStartConsgNo(),
						bulkBookingTO.getCnCount());
			}
			BookingValidationTO cnValidation = new BookingValidationTO();
			cnValidation.setOfficeCode(bulkBookingTO.getBookingOffCode());
			cnValidation
					.setIssuedTOPartyId1(bulkBookingTO.getBookingOfficeId());
			cnValidation.setIssuedTOPartyId(bulkBookingTO.getCustomerId());

			getProductList(cnValidation);

			List<Object> list = setUpBulkBookingTos(bulkBookingTO, seriesList,
					loggedInOffice, cnValidation, filePath);
			List dataList = (List) list.get(0);
			int totalCount = dataList.size();
			resetJobStatus("Reading data from excel completed", jobNumber,
					null, 20, "I", null, bulkBookingTO.getConsgStickerType());

			BulkReturnObject bulkBookingObj = handleBulkBooking(list,
					seriesList, cnValidation, jobNumber);

			// For ErrorListExcel Generation
			List<List> errorList = bulkBookingObj.getErrList();
			ByteArrayOutputStream errorFileByteStream = null;
			if (errorList != null && !errorList.isEmpty()) {
				XSSFWorkbook xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errorList);
				ByteArrayOutputStream workbookByteArrayStream= new ByteArrayOutputStream();
				xssfWorkbook.write(workbookByteArrayStream);
				
				errorFileByteStream = new ByteArrayOutputStream();
				ZipOutputStream errorZipstream=new ZipOutputStream(errorFileByteStream);
				
				ZipEntry zipEntry = new ZipEntry("Bulk_Error.xls");
				zipEntry.setSize(workbookByteArrayStream.size());
				
				errorZipstream.putNextEntry(zipEntry);
				errorZipstream.write(workbookByteArrayStream.toByteArray());
				errorZipstream.closeEntry();          
				errorZipstream.close();
				
				workbookByteArrayStream.close();
				LOGGER.debug("byte array size====>" + errorFileByteStream.size());
			}

			// For SuccessListExcel Generation
			List<List> successList = bulkBookingObj.getSuccessList();

			ByteArrayOutputStream successFileByteStream = null;
			if (successList != null && !successList.isEmpty()) {
				XSSFWorkbook xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(successList);
				ByteArrayOutputStream workbookByteArrayStream= new ByteArrayOutputStream();
				xssfWorkbook.write(workbookByteArrayStream);
				
				successFileByteStream = new ByteArrayOutputStream();
				ZipOutputStream successZipstream=new ZipOutputStream(successFileByteStream);
				
				ZipEntry zipEntry = new ZipEntry("Bulk_Success.xls");
				zipEntry.setSize(successFileByteStream.size());
				
				successZipstream.putNextEntry(zipEntry);
				successZipstream.write(workbookByteArrayStream.toByteArray());
				successZipstream.closeEntry();  
				successZipstream.close();
				
				workbookByteArrayStream.close();
				LOGGER.debug("byte array size====> " + successFileByteStream.size());
			}

			// Message Generation
			String message = null;
			if (errorList == null || errorList.isEmpty()) {
				message = "All consignments booked successfully";
			} else {
				int invalidCount = bulkBookingObj.getFailedBookingCount();
				message = invalidCount + " consignments failed out of "
						+ totalCount;// createFailureMessage(errorList);
			}

			resetJobStatus(message, jobNumber, errorFileByteStream, 100, "S",
					successFileByteStream, bulkBookingTO.getConsgStickerType());

		} catch (CGBusinessException e) {
			String message = errorBundle.getProperty(e.getMessage());
			LOGGER.error("BulkBookingServiceImpl::proceedBulkBooking::resetStatus::error",e);
			resetJobStatus(message, jobNumber, null, 100, "F", null, bulkBookingTO.getConsgStickerType());
		} catch (CGSystemException e) {
			String message = errorBundle.getProperty(e.getMessage());
			LOGGER.error("BulkBookingServiceImpl::proceedBulkBooking::resetStatus::error",e);
			resetJobStatus(message, jobNumber, null, 100, "F", null, bulkBookingTO.getConsgStickerType());
		} catch (IOException e) {
			String message = "Unable to perform bulk operation";// ExceptionUtil.getMessageFromException(e);
			LOGGER.error("BulkBookingServiceImpl::proceedBulkBooking::resetStatus::error",e);
			resetJobStatus(message, jobNumber, null, 100, "F", null, bulkBookingTO.getConsgStickerType());

		}
		LOGGER.trace("BulkBookingServiceImpl::proceedBulkBooking:::::----------> END" +  DateUtil.getCurrentTimeInMilliSeconds());
	}

	private void resetJobStatus(String message, String jobNumber,
			ByteArrayOutputStream errorFileStream, int percentedCompleted,
			String status, ByteArrayOutputStream successStream, String consgStickerType) throws CGBusinessException, CGSystemException {
		try {
			JobServicesTO jobTO = new JobServicesTO();
			jobTO.setProcessCode("BULK");
			jobTO.setJobNumber(jobNumber);
			jobTO.setJobStatus(status);
			jobTO.setPercentageCompleted(percentedCompleted);
			jobTO.setRemarks(message);
			jobTO.setFailureFile(errorFileStream == null ? null : errorFileStream
					.toByteArray());
			jobTO.setFileNameFailure(errorFileStream == null ? null
					: "Bulk_Upload_Error_"+consgStickerType+".xls");
	
			// Added code for successFile
			jobTO.setSuccessFile(successStream == null ? null : successStream
					.toByteArray());
			jobTO.setFileNameSuccess(successStream == null ? null
					: "Bulk_Upload_Success_"+consgStickerType+".xls");
			// Added code for successFile Ends
	
			jobTO.setUpdateDate(new Date());
		
			jobTO = jobService.saveOrUpdateJobService(jobTO);
			jobNumber = jobTO.getJobNumber();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("Error occured in BulkBookingServiceImpl :: resetJobStatus()..:"
					, e);
			resetJobStatus("Error while saving success/error files.", jobNumber, null, 100, "F", null, consgStickerType);
			throw e;
		} finally {
			try {
				if(errorFileStream != null)
					errorFileStream.close();
				if(successStream != null)
					successStream.close();
			} catch (IOException e) {
				LOGGER.error("stream is already closed or null");
			}
		}
	}

	public String createFailureMessage(List<List> rows)
			throws CGBusinessException {
		LOGGER.debug("BulkBookingServiceImpl::createFailureMessage::START------------>:::::::");
		StringBuilder errorMessage = new StringBuilder();
		try {
			for (int i = 1; i < rows.size(); i++) {
				List<String> row = rows.get(i);
				errorMessage.append("Consignment: " + row.get(1) + " Error: "
						+ row.get(31) + "\n");
			}
		} catch (Exception e) {
			LOGGER.error("CGExcelUploadUtil::CreateExcelFile::ERROR :: ------------>:::::::",e);
		}
		LOGGER.debug("BulkBookingServiceImpl::createFailureMessage::START------------>:::::::");
		return errorMessage.toString();
	}

	/*
	 * public byte[] createByteArray(FileOutputStream file) throws IOException {
	 * LOGGER
	 * .debug("AbstractBcunDatasyncServiceImpl::createZipFile::start====>");
	 * InputStream in = null; ByteArrayOutputStream baos = null; try { baos =
	 * new ByteArrayOutputStream(); in = new FileInputStream(file.); final
	 * byte[] buffer = new byte[500]; int read = -1; while ((read =
	 * in.read(buffer)) > 0) { baos.write(buffer, 0, read); }
	 * LOGGER.debug("AbstractBcunDatasyncServiceImpl::createZipFile::end====>");
	 * } catch (Exception ex) { LOGGER.debug(
	 * "AbstractBcunDatasyncServiceImpl::createZipFile::error in creating zip====>"
	 * ); } finally { in.close(); } return baos.toByteArray(); }
	 */

	private List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException {
		try {
			LOGGER.debug("BulkBookingAction::StockSeriesGenerator::START------------>:::::::");
			seriesList = StockSeriesGenerator.globalSeriesCalculater(
					consgNumber, quantity);
		} catch (Exception e) {
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		LOGGER.debug("BulkBookingAction::StockSeriesGenerator::End------------>:::::::");
		return seriesList;
	}

	private void getProductList(BookingValidationTO cnValidation)
			throws CGBusinessException, CGSystemException {
		List<ProductTO> products = bookingCommonService
				.getAllProducts(BookingConstants.BULK_BOOKING);
		cnValidation.setProductTOList(products);

	}

	private List<Object> setUpBulkBookingTos(
			CreditCustomerBookingParcelTO bulkBookingTO,
			List<String> consgnumbers, OfficeTO loggedInOffice,
			BookingValidationTO cnValidation, String filePath)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BulkBookingAction::setUpBulkBookingTos::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Excel parsing
		final FormFile myFile = bulkBookingTO.getBulkBookingExcel();
		final String fileName = myFile.getFileName();
		final String fileUrl = filePath + fileName;
		Boolean isValidHeader = Boolean.FALSE;
		List<Object> list = null;
		LOGGER.debug("BulkBookingAction::getAllRowsValues::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<List> bookingDetails = CGExcelUploadUtil.getAllRowsValues(fileUrl,
				myFile);

		if (!StringUtil.isEmptyInteger(bulkBookingTO.getCnCount())) {
			if (bookingDetails.size() - 1 != bulkBookingTO.getCnCount()) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_BULK_BOOKINGS);
			}
		}

		LOGGER.debug("BulkBookingAction::getAllRowsValues::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());

		List<String> excelHeaderList = bookingDetails.get(0);
		isValidHeader = BookingUtils
				.validateBulkFileUploadHeader(excelHeaderList);

		if (isValidHeader.equals(Boolean.TRUE)) {
			Integer consgTypeId = 0;
			String consgType = null;

			if (StringUtils.isNotEmpty(bulkBookingTO.getConsgTypeName())) {
				consgTypeId = Integer.parseInt(bulkBookingTO.getConsgTypeName()
						.split("#")[0]);
				consgType = bulkBookingTO.getConsgTypeName().split("#")[1];
				bulkBookingTO.setConsgTypeId(consgTypeId);
				bulkBookingTO.setConsgTypeName(consgType);
			}
			BookingTypeTO bookingType = bookingCommonService
					.getBookingType(BookingConstants.BULK_BOOKING);
			ProcessTO process = getProcess();
			bulkBookingTO.setProcessTO(process);
			bulkBookingTO.setBookingTypeId(bookingType.getBookingTypeId());
			bulkBookingTO.setBookingType(bookingType.getBookingType());
			bulkBookingTO.setProcessNumber(getProcessNumber(
					CommonConstants.PROCESS_BOOKING,
					loggedInOffice.getOfficeCode()));
			String office = loggedInOffice.getOfficeId() + "#"
					+ loggedInOffice.getOfficeName() + " "
					+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
			bulkBookingTO.setBookingOffCode(office);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			bulkBookingTO.setOriginCity(city.getCityName());
			bulkBookingTO.setCityId(city.getCityId());

			list = BulkBookingConverter.bulkBookingTOConvertor(bookingDetails,
					consgnumbers, bulkBookingTO, cnValidation,
					bookingCommonService);
		} else {
			throw new CGBusinessException("PU009");
			// throw new CGBusinessException("Invalid Header");
		}
		LOGGER.debug("BulkBookingAction::setUpBulkBookingTos::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return list;

	}

	private ProcessTO getProcess() throws CGSystemException,
			CGBusinessException {
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		/*
		 * bookingCommonService = (BookingCommonService)
		 * springApplicationContext
		 * .getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		 */
		process = bookingCommonService.getProcess(process);
		return process;
	}

	private String getProcessNumber(String processCode, String officeCode)
			throws CGSystemException, CGBusinessException {
		return bookingCommonService.getProcessNumber(processCode, officeCode);
	}

	public void setErrorBundle(Properties errorBundle) {
		this.errorBundle = errorBundle;
	}

	@Override
	public BookingValidationTO validateConsignmentForBulkPrinted(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BulkBookingServiceImpl::validateConsignmentForBulkPrinted::START------------>:::::::");
		bookingvalidateTO = bulkBookingValidator
				.validateConsignmentForBulkPrinted(bookingvalidateTO);
		LOGGER.trace("BulkBookingServiceImpl::validateConsignmentForBulkPrinted::END------------>:::::::");
		return bookingvalidateTO;
	}

}
