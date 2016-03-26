/**
 * 
 */
package com.ff.web.booking.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;

/**
 * The Class BackDatedBookingValidator.
 * 
 * @author Narasimha Rao kattunga
 */
public class BulkBookingValidator {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkBookingValidator.class);

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/**
	 * @param bookingValidator
	 *            the bookingValidator to set
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
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
	 * Validate backdated bookings.
	 * 
	 * @param consignmentDOs
	 * 
	 * @param bookings
	 *            the bookings
	 * @param cnNoList
	 * @param cnValidation
	 * @return the backdated booking to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BulkReturnObject validateBulkBookings(List<BookingDO> bookingDOs,
			List<ConsignmentDO> consignmentDOs, List<String> cnNoList,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BulkBookingValidator::validateBulkBookings----------->START for all the consignments"
				+ DateUtil.getCurrentTimeInMilliSeconds());

		List<BookingDO> validBookings = new ArrayList<BookingDO>();
		List<ConsignmentDO> validConsignments = new ArrayList<ConsignmentDO>();

		List<BookingDO> invalidBookings = new ArrayList<BookingDO>();
		List<ConsignmentDO> invalidConsignments = new ArrayList<ConsignmentDO>();

		BulkReturnObject bulkReturnObject = new BulkReturnObject();

		int count = 0;
		for (BookingDO bookingDO : bookingDOs) {
			ConsignmentDO consignmentDO = new ConsignmentDO();
			LOGGER.debug(
					"BulkBookingValidator::validateBulkBookings----------->START for indivisual consignments:"
							+ DateUtil.getCurrentTimeInMilliSeconds(),
							bookingDO.getConsgNumber());
			BookingValidationTO bookingvalidateTO = prepareInputs(bookingDO,
					cnValidation);
			bookingvalidateTO.setDeclaredValue(bookingDO.getDeclaredValue());
			if (!StringUtil.isStringEmpty(bookingvalidateTO.getConsgNumber())) {
				String consgSeries = bookingDO.getConsgNumber().substring(4, 5);
				bookingvalidateTO.setConsgSeries(StringUtils
						.isNumeric(consgSeries) ? "N" : consgSeries);
			}
			consignmentDO = consignmentDOs.get(count);
			// bookingvalidateTO.setCityId(bookingDO.getCityId());
			// Applying Booking business rules

			bookingvalidateTO = callBookingBusinessRulesProcess(
					bookingvalidateTO, cnNoList, bookingDO);
			if (!StringUtil.isEmptyInteger(bookingvalidateTO.getPincodeId())) {
				PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(bookingvalidateTO.getPincodeId());
				bookingDO.setPincodeId(pincodeDO);
				consignmentDO.setDestPincodeId(pincodeDO);
			}
			List<String> errorCodes = bookingvalidateTO.getErrorCodes();

			// Validating mandatory fields
			bookingvalidateTO = setUpMandatoryFields(bookingDO, consignmentDO,
					errorCodes, bookingvalidateTO);
			if (StringUtil.isEmptyList(bookingvalidateTO.getErrorCodes())) {
				validBookings.add(bookingDO);
				validConsignments.add(consignmentDO);
			} else {
				bookingDO.setErrorCodes(errorCodes);
				invalidBookings.add(bookingDO);
				invalidConsignments.add(consignmentDO);
			}
			LOGGER.debug("BulkBookingValidator::validateBulkBookings----------->END for indivisual consignments:"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			count++;
		}

		bulkReturnObject.setValidBookings(validBookings);
		bulkReturnObject.setInvalidBookings(invalidBookings);
		bulkReturnObject.setValidConsignments(validConsignments);
		bulkReturnObject.setInvalidConsignments(invalidConsignments);

		LOGGER.debug("BulkBookingValidator::validateBulkBookings----------->END::::::: for all the consignments"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bulkReturnObject;
	}

	/**
	 * Sets the up mandatory fields.
	 * 
	 * @param bookingDO
	 *            the booking to
	 * @param consignmentDO
	 * @param errorCodes
	 *            the error codes
	 * @param bookingvalidateTO
	 * @return the backdated booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingValidationTO setUpMandatoryFields(BookingDO bookingDO,
			ConsignmentDO consignmentDO, List<String> errorCodes,
			BookingValidationTO bookingvalidateTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BulkBookingValidator::setUpMandatoryFields----------->START:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.CONSIGNMENT_TYPE_PARCEL, bookingDO
				.getConsgTypeId().getConsignmentCode())) {
			if (!StringUtil.isNull(consignmentDO.getCnContentId())
					&& StringUtils.isNotEmpty(consignmentDO.getCnContentId()
							.getCnContentName())) {
				CNContentDO cnDo = new CNContentDO();
				LOGGER.debug("bookingCommonService::getCNContentByName----------->START:::::::"
						+ DateUtil.getCurrentTimeInMilliSeconds());
				CNContentTO cnContent = bookingCommonService
						.getCNContentByName(consignmentDO.getCnContentId()
								.getCnContentName());
				LOGGER.debug("bookingCommonService::getCNContentByName----------->END:::::::"
						+ DateUtil.getCurrentTimeInMilliSeconds());
				if (StringUtil.isNull(cnContent)) {			
					bookingDO.setOtherCNContent(consignmentDO.getCnContentId()
							.getCnContentName());
					if(bookingDO.getOtherCNContent().trim().length()>=100){
						String erroCode = "BLK005";
						errorCodes.add(erroCode);
					}
					consignmentDO.setOtherCNContent(consignmentDO
							.getCnContentId().getCnContentName());
					CNContentTO cnContentTO = bookingCommonService
							.getCNContentByName("Others");
					cnDo.setCnContentId(cnContentTO.getCnContentId());
					cnDo.setCnContentCode(cnContentTO.getCnContentCode());
					cnDo.setCnContentName(cnContentTO.getCnContentName());
					bookingDO.setCnContentId(cnDo);
					consignmentDO.setCnContentId(cnDo);
				} else {
					cnDo.setCnContentId(cnContent.getCnContentId());
					cnDo.setCnContentCode(cnContent.getCnContentCode());
					cnDo.setCnContentName(cnContent.getCnContentName());
					bookingDO.setCnContentId(cnDo);
					consignmentDO.setCnContentId(cnDo);
				}
			} else {
				errorCodes.add(BookingErrorCodesConstants.INVALID_CONTENT);
			}

			if (StringUtil.isEmptyDouble(bookingDO.getDeclaredValue())
					|| bookingDO.getDeclaredValue() == -1) {
				errorCodes
				.add(BookingErrorCodesConstants.INVALID_DECLARED_VALUE);
			}

			if (StringUtil.isEmptyInteger(bookingDO.getNoOfPieces()) || bookingDO.getNoOfPieces() == -1) {
				errorCodes.add(BookingErrorCodesConstants.INVALID_NO_OF_PIECES);
			}

			if (!StringUtil.isNull(bookingDO.getInsuredBy())
					&& StringUtil.isEmptyInteger(bookingDO.getInsuredBy()
							.getInsuredById())) {
				errorCodes.add(BookingErrorCodesConstants.INVALID_INSURED_BY);
			}

			if (!StringUtil.isNull(bookingDO.getInsuredBy())
					&& !StringUtil.isStringEmpty(bookingDO.getInsuredBy()
							.getInsuredByCode())
							&& bookingDO.getInsuredBy().getInsuredByCode()
							.equalsIgnoreCase("C")) {
				if (StringUtil.isStringEmpty(bookingDO.getInsurencePolicyNo())) {
					errorCodes
					.add(BookingErrorCodesConstants.INVALID_POLICY_NO);
				}
			}
		}
		// Validation for LC Amunt
		String consgSeries = bookingvalidateTO.getConsgSeries();
		if (!StringUtil.isStringEmpty(consgSeries)) {
			if (StringUtil.isDigit(consgSeries.charAt(0))) {
				consgSeries = "N";
			}
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT,
					consgSeries)) {
				if (StringUtil.isEmptyDouble(consignmentDO.getLcAmount())
						|| consignmentDO.getLcAmount() == -1) {
					errorCodes.add(BookingErrorCodesConstants.INVALID_LC_AMT);
				}
				if (StringUtil.isStringEmpty(consignmentDO.getLcBankName())) {
					errorCodes
					.add(BookingErrorCodesConstants.INVALID_LC_BANK_NAME);
				}
			} else if (!StringUtils.equalsIgnoreCase(
					CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT,
					consgSeries)) {
				if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
					errorCodes
					.add(BookingErrorCodesConstants.LC_AMT_NOT_REQUIRED);
				}
				if (!StringUtil.isStringEmpty(consignmentDO.getLcBankName())) {
					errorCodes
					.add(BookingErrorCodesConstants.LC_BANK_NAME_NOT_REQUIRED);
				}
			}

			if (!StringUtils.equalsIgnoreCase(
					CommonConstants.PRODUCT_SERIES_CASH_COD, consgSeries)) {
				if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
					errorCodes
					.add(BookingErrorCodesConstants.COD_AMT_NOT_REQUIRED);
				}
			} else if (StringUtils.equalsIgnoreCase(
					CommonConstants.PRODUCT_SERIES_CASH_COD, consgSeries)) {
				if (StringUtil.isEmptyDouble(consignmentDO.getCodAmt())
						|| consignmentDO.getCodAmt() == -1) {
					errorCodes.add(BookingErrorCodesConstants.INVALID_COD_AMT);
				}
			}
		}

		if(bookingDO.getUpdatedProcess().getProcessCode().equalsIgnoreCase("UPPU")){
			LOGGER.error(
					"BulkBookingValidator :: setUpMandatoryFields() :: Updated process is set as of pickup."
							+ bookingDO.getConsgNumber());
			errorCodes.add(BookingErrorCodesConstants.UPDATED_PROCESS_SET_AS_PICKUP);

		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BulkBookingValidator::setUpMandatoryFields----------->END:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	/**
	 * Call booking business rules process.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @param cnNoList
	 * @param bookingDO
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingValidationTO callBookingBusinessRulesProcess(
			BookingValidationTO bookingvalidateTO, List<String> cnNoList,
			BookingDO bookingDO) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkBookingValidator::callBookingBusinessRulesProcess----------->START:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Check for mandatory validations
		validateMandatoryFields(bookingvalidateTO);
		// Check for Consignment validations
		if (StringUtil.isNull(cnNoList)) {
			bookingvalidateTO.setBookingType(BookingConstants.CCC_BOOKING);
			bookingValidator.isValidConsignmentForBulk(bookingvalidateTO,
					bookingDO);
			bookingvalidateTO.setBookingType(BookingConstants.BULK_BOOKING);
		} else {
			bookingValidator.isConsgBookedForBulk(bookingvalidateTO, bookingDO);
		}
		// Check for Pincode validations
		if (!StringUtil.isStringEmpty(bookingvalidateTO.getPincode())) {
			bookingValidator.isValidPincode(bookingvalidateTO);
		}
		// Check for Alternate Pincode validations
		if (!StringUtil.isStringEmpty(bookingvalidateTO.getAltPincode())) {
			bookingValidator.isValidAlternatePincode(bookingvalidateTO);
		}
		// Check for Validate mobile validations
		bookingValidator.validateMobileOrPhoneNoForBulk(bookingvalidateTO);
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.CONSIGNMENT_TYPE_PARCEL,
				bookingvalidateTO.getConsgType())) {
			// Check for Validate declared validations
			bookingValidator.validateDeclaredValue(bookingvalidateTO);
		}
		LOGGER.debug("BulkBookingValidator::callBookingBusinessRulesProcess----------->END:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	/**
	 * Validate mandatory fields.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingValidationTO validateMandatoryFields(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BulkBookingValidator::validateMandatoryFields----------->START:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errors = bookingvalidateTO.getErrorCodes();
		bookingValidator.validConsgFormat(bookingvalidateTO,
				bookingvalidateTO.getConsgNumber());

		if (StringUtil.isStringEmpty(bookingvalidateTO.getConsigneeFirstName()))
			errors.add(BookingErrorCodesConstants.INVALID_CONSIGNEE_NAME);
		if (StringUtil.isStringEmpty(bookingvalidateTO.getConsigneeAddr()))
			errors.add(BookingErrorCodesConstants.INVALID_CONSIGNEE_ADDR);

		if (StringUtil.isStringEmpty(bookingvalidateTO.getPincode()))
			errors.add(BookingErrorCodesConstants.INVALID_PINCODE);

		//+++++++++++++++++++++++++++++++
		if (StringUtil.isEmptyDouble(bookingvalidateTO.getConsgWeight()) || bookingvalidateTO.getConsgWeight() == -1)
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_WEIGHT);
		//********************************

		List<String> bookingTypes = new ArrayList<String>(1);

		bookingTypes.add(BookingConstants.BULK_BOOKING);
		if (StringUtils.isEmpty(bookingvalidateTO.getBookingType()))
			errors.add(BookingErrorCodesConstants.INVALID_BOOKING_TYPE);
		else if (!CGCollectionUtils.isExistsInCollection(bookingTypes,
				bookingvalidateTO.getBookingType().toUpperCase()))
			errors.add(BookingErrorCodesConstants.INVALID_BOOKING_TYPE);

		List<String> docTypes = new ArrayList<String>(2);

		docTypes.add(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		docTypes.add(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		if (StringUtils.isEmpty(bookingvalidateTO.getConsgType()))
			errors.add(BookingErrorCodesConstants.INVALID_DOCUMENT);
		else if (!CGCollectionUtils.isExistsInCollection(docTypes,
				bookingvalidateTO.getConsgType().toUpperCase()))
			errors.add(BookingErrorCodesConstants.INVALID_DOCUMENT);

		bookingvalidateTO.setErrorCodes(errors);
		LOGGER.debug("BulkBookingValidator::validateMandatoryFields----------->END:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	/**
	 * Prepare inputs.
	 * 
	 * @param bookingDO
	 *            the booking to
	 * @param cnValidation
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingValidationTO prepareInputs(BookingDO bookingDO,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {

		BookingValidationTO bookingvalidateTO = new BookingValidationTO();
		bookingvalidateTO.setProcessCode(CommonConstants.PROCESS_BOOKING);
		bookingvalidateTO.setConsgNumber(bookingDO.getConsgNumber());
		if (!StringUtil.isNull(bookingDO.getBookingType())
				&& !StringUtil.isNull(bookingDO.getBookingType()
						.getBookingType()))
			bookingvalidateTO.setBookingType(bookingDO.getBookingType()
					.getBookingType());
		if (!StringUtil.isNull(bookingDO.getPincodeId())
				&& !StringUtil.isNull(bookingDO.getPincodeId().getPincode()))
			bookingvalidateTO.setPincode(bookingDO.getPincodeId().getPincode());

		if (!StringUtil.isNull(bookingDO.getBookingOfficeId()))
			bookingvalidateTO
			.setBookingOfficeId(bookingDO.getBookingOfficeId());

		if (!StringUtil.isNull(bookingDO.getConsigneeId())) {
			if (!StringUtil.isNull(bookingDO.getConsigneeId().getMobile()))
				bookingvalidateTO.setConsigneeMobile(bookingDO.getConsigneeId()
						.getMobile());
			if (!StringUtil.isNull(bookingDO.getConsigneeId().getPhone()))
				bookingvalidateTO.setConsigneePhn(bookingDO.getConsigneeId()
						.getPhone());

			if (!StringUtil.isNull(bookingDO.getConsigneeId().getFirstName()))
				bookingvalidateTO.setConsigneeFirstName(bookingDO
						.getConsigneeId().getFirstName());
		}
		if (!StringUtil.isNull(bookingDO.getConsignorId())) {
			if (!StringUtil.isNull(bookingDO.getConsignorId().getPhone()))
				bookingvalidateTO.setConsignorPhn(bookingDO.getConsignorId()
						.getPhone());
			if (!StringUtil.isNull(bookingDO.getConsignorId().getMobile()))
				bookingvalidateTO.setConsignorMobile(bookingDO.getConsignorId()
						.getMobile());
		}

		if (!StringUtil.isNull(bookingDO.getConsigneeAddr()))
			bookingvalidateTO.setConsigneeAddr(bookingDO.getConsigneeAddr());

		bookingvalidateTO.setIsBusinessExceptionReq(CommonConstants.NO);

		if (!StringUtil.isNull(bookingDO.getActualWeight()))
			bookingvalidateTO.setConsgWeight(bookingDO.getActualWeight());

		if (!StringUtil.isNull(bookingDO.getConsgTypeId())
				&& !StringUtil.isNull(bookingDO.getConsgTypeId()
						.getConsignmentCode()))
			bookingvalidateTO.setConsgType(bookingDO.getConsgTypeId()
					.getConsignmentCode());

		if (!StringUtil.isNull(bookingDO.getDeclaredValue()))
			bookingvalidateTO.setDeclaredValue(bookingDO.getDeclaredValue());

		if (!StringUtil.isNull(cnValidation.getOfficeCode()))
			cnValidation.setOfficeCode(cnValidation.getOfficeCode());

		bookingvalidateTO.setConsgWeight(bookingDO.getFianlWeight());
		bookingvalidateTO.setProductTOList(cnValidation.getProductTOList());
		bookingvalidateTO.setAltPincode(bookingDO.getAltPincode());
		bookingvalidateTO.setOfficeCode(cnValidation.getOfficeCode());
		bookingvalidateTO.setIssuedTOPartyId1(cnValidation
				.getIssuedTOPartyId1());
		bookingvalidateTO.setIssuedTOPartyId(cnValidation.getIssuedTOPartyId());

		if (!StringUtil.isNull(bookingDO.getOriginCityId()))
			bookingvalidateTO.setCityId(bookingDO.getOriginCityId());
		return bookingvalidateTO;
	}

	/**
	 * Gets the cN rate.
	 * 
	 * @param rateCalculationInputTO
	 *            the rate calculation input to
	 * @return the cN rate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*
	 * private CNPricingDetailsTO getCNRate( RateCalculationInputTO
	 * rateCalculationInputTO) throws CGBusinessException, CGSystemException {
	 * CNPricingDetailsTO cnRateDtls = new CNPricingDetailsTO();
	 * cnRateDtls.setFreightChg(12.000); cnRateDtls.setFuelChg(10.000);
	 * cnRateDtls.setRiskSurChg(13.000); cnRateDtls.setTopayChg(11.000);
	 * cnRateDtls.setAirportHandlingChg(15.999);
	 * cnRateDtls.setServiceTax(20.000); cnRateDtls.setEduCessChg(21.000);
	 * cnRateDtls.setHigherEduCessChg(22.000);
	 * cnRateDtls.setFinalPrice(100.000); return cnRateDtls; }
	 */

	public BookingValidationTO validateConsignmentForBulkPrinted(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		return bookingValidator
				.validateConsignmentForBulkPrinted(bookingvalidateTO);
	}

}
