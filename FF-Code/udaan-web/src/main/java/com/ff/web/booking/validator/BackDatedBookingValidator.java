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
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;

/**
 * The Class BackDatedBookingValidator.
 * 
 * @author Narasimha Rao kattunga
 */
public class BackDatedBookingValidator {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BackDatedBookingValidator.class);

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/**
	 * Sets the booking validator.
	 * 
	 * @param bookingValidator
	 *            the new booking validator
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
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

	/**
	 * Validate backdated bookings.
	 * 
	 * @param bookings
	 *            the bookings
	 * @return the backdated booking to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BulkReturnObject validateBackdatedBookings(
			List<BookingDO> bookingDOs, List<ConsignmentDO> consignmentDOs,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BackDatedBookingValidator::validateBackdatedBookings::START------------>:::::::");
		List<BookingDO> validBookings = new ArrayList<BookingDO>();
		List<ConsignmentDO> validConsignments = new ArrayList<ConsignmentDO>();
		List<BookingDO> invalidBookings = new ArrayList<BookingDO>();
		List<ConsignmentDO> invalidConsignments = new ArrayList<ConsignmentDO>();
		BulkReturnObject bulkReturnObject = new BulkReturnObject();
		int count = 0;
		for (BookingDO bookingDO : bookingDOs) {
			ConsignmentDO consignmentDO = new ConsignmentDO();
			consignmentDO = consignmentDOs.get(count);

			BookingValidationTO bookingvalidateTO = prepareInputs(bookingDO,
					cnValidation);
			if (!StringUtil.isStringEmpty(bookingvalidateTO.getConsgNumber())) {
				String consgSeries = bookingDO.getConsgNumber().substring(4, 5);
				bookingvalidateTO.setConsgSeries(StringUtils
						.isNumeric(consgSeries) ? "N" : consgSeries);
			}
			
			validateCustomer(bookingDO, bookingvalidateTO);
			validatePriority(bookingDO, bookingvalidateTO);
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId())) {
				CustomerDO customerId = bookingDO.getCustomerId();
				customerId
						.setCustomerId(bookingvalidateTO.getIssuedTOPartyId());
				bookingDO.setCustomerId(customerId);
				//code added by shaheed to stamp customer code in consignment Table
				consignmentDO.setCustomer(bookingvalidateTO.getIssuedTOPartyId());
				//code ends here
			} else {
				bookingDO.setCustomerId(null);
				//consignmentDO.setCustomer(null);
			}
			//code added to allow acc booking
			//bookingvalidateTO.setCustCode(bookingDO.getCustomerId().getCustomerCode());
			//code ends here
			callBookingBusinessRulesProcess(bookingvalidateTO, bookingDO);
			if (!StringUtil.isEmptyInteger(bookingvalidateTO.getPincodeId())) {
				PincodeDO pin = bookingDO.getPincodeId();
				pin.setPincodeId(bookingvalidateTO.getPincodeId());
				bookingDO.setPincodeId(pin);
			}
			List<String> errorCodes = bookingvalidateTO.getErrorCodes();
			setUpMandatoryFields(bookingDO, consignmentDO, errorCodes,
					bookingvalidateTO);
			if (StringUtil.isEmptyList(bookingvalidateTO.getErrorCodes())) {
				validBookings.add(bookingDO);
				validConsignments.add(consignmentDO);
			} else {
				bookingDO.setErrorCodes(errorCodes);
				invalidBookings.add(bookingDO);
				invalidConsignments.add(consignmentDO);
			}

			count++;

		}
		bulkReturnObject.setValidBookings(validBookings);
		bulkReturnObject.setInvalidBookings(invalidBookings);
		bulkReturnObject.setValidConsignments(validConsignments);
		bulkReturnObject.setInvalidConsignments(invalidConsignments);

		LOGGER.debug("BackDatedBookingValidator::validateBackdatedBookings::END------------>:::::::");
		return bulkReturnObject;
	}

	private void validatePriority(BookingDO bookingDO,
			BookingValidationTO bookingvalidateTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::Start------------>:::::::");
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		if (!StringUtil.isNull(bookingDO.getPincodeId())
				&& !StringUtil.isStringEmpty(bookingDO.getPincodeId()
						.getPincode())
				&& !StringUtil.isEmptyInteger(bookingDO.getOriginCityId())) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.PRODUCT_SERIES_PRIORITY,
					bookingvalidateTO.getConsgSeries())) {

				if (!StringUtil.isStringEmpty(bookingDO.getDlvTime())) {
					if (StringUtils.equals(BookingConstants.AFTER_14,
							bookingDO.getDlvTime())
							|| StringUtils.equals(BookingConstants.BEFORE_14,
									bookingDO.getDlvTime())
							|| StringUtils.equalsIgnoreCase(BookingConstants.TILL_48_Hr,
									bookingDO.getDlvTime())) {

						List<PincodeProductServiceabilityTO> pincodeDeliveryTimeMapTOs = null;
						pincodeDeliveryTimeMapTOs = bookingCommonService
								.getPincodeDlvTimeMaps(bookingDO.getPincodeId()
										.getPincode(), bookingDO
										.getOriginCityId(), bookingvalidateTO
										.getConsgSeries());

						if (!CGCollectionUtils
								.isEmpty(pincodeDeliveryTimeMapTOs)) {
							for (PincodeProductServiceabilityTO dlvTimeMap : pincodeDeliveryTimeMapTOs) {
								if (StringUtils.equals(bookingDO.getDlvTime()
										.split(CommonConstants.HYPHEN)[0],
										dlvTimeMap.getDlvTimeQualification())) {
									bookingDO.setPincodeDlvTimeMapId(dlvTimeMap
											.getPincodeDeliveryTimeMapId());
								}
							}
						} else {
							errorCodes
									.add(BookingErrorCodesConstants.INVALID_PRIORITY);
						}

					} else {
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_PRIORITY_SERVICE);
					}
				} else {
					errorCodes
							.add(BookingErrorCodesConstants.PRIORITY_MANDATORY);
				}

			}
		}

		if (!StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY,
				bookingvalidateTO.getConsgSeries())
				&& !StringUtil.isStringEmpty(bookingDO.getDlvTime())) {
			errorCodes
					.add(BookingErrorCodesConstants.PRIORITY_SERVICE_NOT_REQUIRED);
		}
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::END------------>:::::::");
	}

	private void validateCustomer(BookingDO bookingDO,
			BookingValidationTO bookingvalidateTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::Start------------>:::::::");
		List<String> errorCodes = new ArrayList<>();
		// Validate Customer code
		if (!StringUtil.isNull(bookingDO.getBookingType())
				&& !StringUtil.isStringEmpty(bookingDO.getBookingType()
						.getBookingType())) {
			if (!StringUtil.isNull(bookingDO.getCustomerId())
					&& !StringUtil.isStringEmpty(bookingDO.getCustomerId()
							.getCustomerCode())) {
				if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
						bookingDO.getBookingType().getBookingType())) {
					CustomerTO ba = bookingCommonService.getCustomerByIdOrCode(
							CommonConstants.EMPTY_INTEGER, bookingDO
									.getCustomerId().getCustomerCode());
					if (StringUtil.isNull(ba))
						errorCodes.add(BookingErrorCodesConstants.INVALID_BA);
					else {
						if (!StringUtil.isEmptyInteger(ba.getCustomerId()))
							bookingvalidateTO.setIssuedTOPartyId(ba
									.getCustomerId());
						if (!StringUtil.isStringEmpty(ba.getCustomerCode()))
							bookingDO.setShippedToCode(ba.getCustomerCode());
					}
				}
				
				if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
						bookingDO.getBookingType().getBookingType())) {
					CustomerTO customer = bookingCommonService
							.getCustomerByIdOrCode(
									CommonConstants.EMPTY_INTEGER, bookingDO
											.getCustomerId().getCustomerCode());

					if (StringUtil.isNull(customer)) {
						List<CustomerTO> customerTOs = bookingCommonService
								.getCustomerForContractByShippedToCode(bookingDO
										.getCustomerId().getCustomerCode());
						if (!CGCollectionUtils.isEmpty(customerTOs)) {
							customer = customerTOs.get(0);
						}
					}
					if (StringUtil.isNull(customer))
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_CUSTOMER);
					else {
						if (!StringUtil
								.isEmptyInteger(customer.getCustomerId()))
							bookingvalidateTO.setIssuedTOPartyId(customer
									.getCustomerId());
						/*if (!StringUtil.isStringEmpty(customer
								.getCustomerCode()))
							bookingDO.setShippedToCode(customer
									.getCustomerCode());
					}*/
						//changes made by shaheed
						if (!StringUtil.isStringEmpty(customer
								.getCustomerCode()))
							bookingDO.setShippedToCode(bookingDO
									.getCustomerId().getCustomerCode());
						
						if (!StringUtil.isNull(customer.getCustomerTypeTO())){
							//CustomerTypeTO custType = customer.getCustomerTypeTO();
							CustomerDO customerDO = null;
							customerDO = new CustomerDO();
							CustomerTypeDO  custTypeDo = null;
							custTypeDo = new CustomerTypeDO();
							
							customerDO = (CustomerDO) CGObjectConverter.createDomainFromTo(customer,customerDO );
							
							CustomerTypeTO custType = customer.getCustomerTypeTO();
							
							custTypeDo = (CustomerTypeDO) CGObjectConverter.createDomainFromTo(custType,custTypeDo );
							
							customerDO.setCustomerType(custTypeDo);
							
							
							
							bookingDO.setCustomerId(customerDO);							
							
							
							
							
							
						}
					}
					//code ends here

				}
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::END------------>:::::::");
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
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::Start------------>:::::::");
		if (!StringUtil.isNull(bookingDO.getConsgTypeId())
				&& StringUtils.isNotEmpty(bookingDO.getConsgTypeId()
						.getConsignmentCode())) {

			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, bookingDO
							.getConsgTypeId().getConsignmentCode())) {

				if (StringUtil.isEmptyDouble(bookingDO.getDeclaredValue())
						|| bookingDO.getDeclaredValue() == -1) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_DECLARED_VALUE);
				} else if (!StringUtil.isNull(bookingDO.getPincodeId())
						&& !StringUtil.isEmptyInteger(bookingDO.getPincodeId()
								.getPincodeId())) {

					ConsignmentTypeConfigTO consgTypeConfigTO = new ConsignmentTypeConfigTO();
					consgTypeConfigTO.setDeclaredValue(bookingDO
							.getDeclaredValue());
					consgTypeConfigTO.setDocType(bookingDO.getConsgTypeId()
							.getConsignmentCode());
					ConsignmentTypeConfigTO consgTypeConfig = bookingCommonService
							.getConsgTypeConfigDtls(consgTypeConfigTO);
					if (!StringUtil.isNull(consgTypeConfig)) {
						if (bookingDO.getDeclaredValue() > consgTypeConfig
								.getDeclaredValue().doubleValue()) {
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.YES,
									consgTypeConfig.getIsPaperworkMandatory())) {

								if (!StringUtil.isNull(bookingDO
										.getCnPaperWorkId())
										&& StringUtils.isNotEmpty(bookingDO
												.getCnPaperWorkId()
												.getCnPaperWorkName())) {
									CNPaperWorksDO cnPaperWorksDO = bookingDO
											.getCnPaperWorkId();
									CNPaperWorksTO cnPaperWorks = bookingCommonService
											.getPaperWorkByPincode(bookingDO
													.getPincodeId()
													.getPincode(), bookingDO
													.getCnPaperWorkId()
													.getCnPaperWorkName());
									if (StringUtil.isNull(cnPaperWorks))
										errorCodes
												.add(BookingErrorCodesConstants.INVALID_PAPER_WORK);
									else
										cnPaperWorksDO
												.setCnPaperWorkId(cnPaperWorks
														.getCnPaperWorkId());
									bookingDO.setCnPaperWorkId(cnPaperWorksDO);
									consignmentDO
											.setCnPaperWorkId(cnPaperWorksDO);
								} else {
									errorCodes
											.add(BookingErrorCodesConstants.INVALID_PAPER_WORK);
								}

							}
						} else {
							if (!StringUtil
									.isNull(bookingDO.getCnPaperWorkId())
									&& StringUtils.isNotEmpty(bookingDO
											.getCnPaperWorkId()
											.getCnPaperWorkName())) {
								errorCodes
										.add(BookingErrorCodesConstants.PAPER_WORK_NOT_REQUIRED);
							}
						}

					}

				}

				if (!StringUtil.isNull(bookingDO.getCnContentId())
						&& StringUtils.isNotEmpty(bookingDO.getCnContentId()
								.getCnContentName())) {
					CNContentDO cnContentDO = bookingDO.getCnContentId();
					CNContentTO cnContent = bookingCommonService
							.getCNContentByName(bookingDO.getCnContentId()
									.getCnContentName());
					if (StringUtil.isNull(cnContent)) {
						bookingDO.setOtherCNContent(bookingDO.getCnContentId()
								.getCnContentName());
						consignmentDO.setOtherCNContent(consignmentDO
								.getCnContentId().getCnContentName());
						CNContentTO cnContentTO = bookingCommonService
								.getCNContentByName("Others");
						cnContentDO
								.setCnContentId(cnContentTO.getCnContentId());
						cnContentDO.setCnContentCode(cnContentTO
								.getCnContentCode());
						cnContentDO.setCnContentName(cnContentTO
								.getCnContentName());
						bookingDO.setCnContentId(cnContentDO);
						consignmentDO.setCnContentId(cnContentDO);
					} else {
						cnContentDO.setCnContentCode(cnContent
								.getCnContentCode());
						cnContentDO.setCnContentName(cnContent
								.getCnContentName());
						cnContentDO.setCnContentId(cnContent.getCnContentId());
						bookingDO.setCnContentId(cnContentDO);
					}
				} else {
					errorCodes.add(BookingErrorCodesConstants.INVALID_CONTENT);
				}

				if (!StringUtil.isNull(bookingDO.getInsuredBy())
						&& StringUtil.isNull(bookingDO.getInsuredBy()
								.getInsuredById())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_INSURED_BY);
				}
				if (!StringUtil.isNull(bookingDO.getInsuredBy())
						&& !StringUtil.isNull(bookingDO.getInsuredBy()
								.getInsuredByCode())
						&& bookingDO.getInsuredBy().getInsuredByCode()
								.equalsIgnoreCase("C")) {
					if (StringUtil.isNull(bookingDO.getInsurencePolicyNo())) {
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_POLICY_NO);
					}
				}

			}
		}
		// Validation for LC Amunt
		String consgSeries = bookingvalidateTO.getConsgSeries();
		if (!StringUtil.isNull(consgSeries)) {

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
				if (StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
					errorCodes.add(BookingErrorCodesConstants.INVALID_COD_AMT);
				}
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::END------------>:::::::");
		return bookingvalidateTO;
	}

	/**
	 * Call booking business rules process.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @param bookingDO
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private BookingValidationTO callBookingBusinessRulesProcess(
			BookingValidationTO bookingvalidateTO, BookingDO bookingDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BackDatedBookingValidator::callBookingBusinessRulesProcess::START------------>:::::::");
		// Check for mandatory validations
		validateMandatoryFields(bookingvalidateTO);
		// bookingvalidateTO = validateBackBookingDate(bookingvalidateTO);
		// Check for Consignment validations
		if (!StringUtil.isStringEmpty(bookingvalidateTO.getConsgNumber())) {
			bookingValidator.isValidConsignmentForBackDated(bookingvalidateTO,
					bookingDO);
		}
		// Check for Pincode validations
		if (!StringUtil.isStringEmpty(bookingvalidateTO.getPincode())) {
			bookingValidator.isValidPincode(bookingvalidateTO);
		}
		/*
		 * // Check for Consoignee Consignor validations
		 * bookingValidator.validateConsigneeConsignor(bookingvalidateTO,
		 * bookingDO);
		 */
		// Check for Validate mobile validations
		bookingValidator.validateMobileOrPhoneNoForBackdated(bookingvalidateTO);
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.CONSIGNMENT_TYPE_PARCEL,
				bookingvalidateTO.getConsgType())) {
			// Check for Validate declared validations
			if (!StringUtil.isEmptyDouble(bookingvalidateTO.getDeclaredValue()))
				bookingValidator.validateDeclaredValue(bookingvalidateTO);
		}
		LOGGER.debug("BackDatedBookingValidator::callBookingBusinessRulesProcess::END------------>:::::::");
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
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::START------------>:::::::");
		List<String> errors = bookingvalidateTO.getErrorCodes();
		if (StringUtils.isEmpty(bookingvalidateTO.getConsgNumber()))
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_DOES_NOT_EXISTS);
		else if (bookingvalidateTO.getConsgNumber().length() <= 12
				&& bookingvalidateTO.getConsgNumber().length() > 12)
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_NO_FORMAT);
		if (StringUtils.isEmpty(bookingvalidateTO.getPincode()))
			errors.add(BookingErrorCodesConstants.INVALID_PINCODE);
		if (StringUtil.isEmptyDouble(bookingvalidateTO.getConsgWeight()) || bookingvalidateTO.getConsgWeight() == -1)
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_WEIGHT);

		List<String> bookingTypes = new ArrayList<String>(5);

		bookingTypes.add(BookingConstants.CASH_BOOKING);
		bookingTypes.add(BookingConstants.BA_BOOKING);
		bookingTypes.add(BookingConstants.CCC_BOOKING);
		bookingTypes.add(BookingConstants.EMOTIONAL_BOND_BOOKING);
		bookingTypes.add(BookingConstants.FOC_BOOKING);
		if (StringUtils.isEmpty(bookingvalidateTO.getBookingType())) {
			errors.add(BookingErrorCodesConstants.INVALID_BOOKING_TYPE);
			bookingvalidateTO.setBookingType(null);
		} else if (!CGCollectionUtils.isExistsInCollection(bookingTypes,
				bookingvalidateTO.getBookingType().toUpperCase())) {
			errors.add(BookingErrorCodesConstants.INVALID_BOOKING_TYPE);
			bookingvalidateTO.setBookingType(null);
		}
		List<String> docTypes = new ArrayList<String>(2);

		docTypes.add(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		docTypes.add(CommonConstants.CONSIGNMENT_TYPE_PARCEL);

		if (StringUtils.isEmpty(bookingvalidateTO.getConsgType()))
			errors.add(BookingErrorCodesConstants.INVALID_DOCUMENT);
		else if (!CGCollectionUtils.isExistsInCollection(docTypes,
				bookingvalidateTO.getConsgType().toUpperCase()))
			errors.add(BookingErrorCodesConstants.INVALID_DOCUMENT);
		bookingvalidateTO.setErrorCodes(errors);
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::END------------>:::::::");
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
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::Start------------>:::::::");
		BookingValidationTO bookingvalidateTO = new BookingValidationTO();
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

			if (!StringUtil.isNull(bookingDO.getConsignorId().getFirstName()))
				bookingvalidateTO.setConsignorFirstName(bookingDO
						.getConsignorId().getFirstName());
		}

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
			bookingvalidateTO.setOfficeCode(cnValidation.getOfficeCode());

		if (!StringUtil.isNull(cnValidation.getIssuedTOPartyId1()))
			bookingvalidateTO.setIssuedTOPartyId1(cnValidation
					.getIssuedTOPartyId1());

		if (!StringUtil.isNull(cnValidation.getProductTOList()))
			bookingvalidateTO.setProductTOList(cnValidation.getProductTOList());

		if (!StringUtil.isNull(cnValidation.getRhoCode()))
			bookingvalidateTO.setRhoCode(cnValidation.getRhoCode());
		
		if (!StringUtil.isNull(bookingDO.getOriginCityId()))
			bookingvalidateTO.setCityId(bookingDO.getOriginCityId());
		
		//added by shaheed to allow BA booking in backdated
		bookingvalidateTO.setLoggedInCityId(cnValidation.getLoggedInCityId());
		//code ends here
		LOGGER.debug("BackDatedBookingValidator::validateMandatoryFields::END------------>:::::::");
		return bookingvalidateTO;
	}

}
