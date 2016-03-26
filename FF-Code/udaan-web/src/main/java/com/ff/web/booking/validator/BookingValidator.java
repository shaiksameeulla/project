package com.ff.web.booking.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.domain.booking.BookingDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.StockUserTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.CreditCustomerBookingService;

/**
 * The Class BookingValidator.
 */
public class BookingValidator {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingValidator.class);

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/** The stock universal service. */
	private StockUniversalService stockUniversalService;

	/** The credit customer booking service. */
	private CreditCustomerBookingService creditCustomerBookingService;

	/**
	 * Gets the geography common service.
	 * 
	 * @return the geography common service
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * Gets the booking common service.
	 * 
	 * @return the booking common service
	 */
	public BookingCommonService getBookingCommonService() {
		return bookingCommonService;
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
	 * Gets the stock universal service.
	 * 
	 * @return the stock universal service
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * Sets the stock universal service.
	 * 
	 * @param stockUniversalService
	 *            the new stock universal service
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/**
	 * Gets the credit customer booking service.
	 * 
	 * @return the credit customer booking service
	 */
	public CreditCustomerBookingService getCreditCustomerBookingService() {
		return creditCustomerBookingService;
	}

	/**
	 * Sets the credit customer booking service.
	 * 
	 * @param creditCustomerBookingService
	 *            the new credit customer booking service
	 */
	public void setCreditCustomerBookingService(
			CreditCustomerBookingService creditCustomerBookingService) {
		this.creditCustomerBookingService = creditCustomerBookingService;
	}

	/**
	 * Validate consignment.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateConsignment(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingValidator::validateConsignment::START------------>:::::::");
		bookingvalidateTO = isValidConsignment(bookingvalidateTO);
		LOGGER.debug("BookingValidator::validateConsignment::END------------>:::::::");
		return bookingvalidateTO;
	}

	/**
	 * Checks if is valid pincode.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingValidationTO isValidPincode(
			BookingValidationTO bookingvalidateTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingValidator::isValidPincode----------->START:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		PincodeTO pincodeTO = null;
		PincodeTO pincodeTO1 = new PincodeTO();
		pincodeTO1.setPincode(bookingvalidateTO.getPincode());
		pincodeTO = geographyCommonService.validatePincode(pincodeTO1);
		if (StringUtil.isNull(pincodeTO)) {
			errorCodes.add(BookingErrorCodesConstants.INVALID_PINCODE);
			bookingvalidateTO.setIsValidPincode(CommonConstants.NO);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_PINCODE);
		} else {
			bookingvalidateTO.setPincodeId(pincodeTO.getPincodeId());
			bookingvalidateTO.setPincode(pincodeTO.getPincode());

			// For PriorityProduct
			/*
			 * if (StringUtils.equalsIgnoreCase(
			 * BookingConstants.BOOKING_PRIORITY_PRODUCT,
			 * bookingvalidateTO.getConsgSeries()) ||
			 * StringUtils.equalsIgnoreCase(
			 * BookingConstants.BOOKING_EB_PRODUCT,
			 * bookingvalidateTO.getConsgSeries())) {
			 */
			// bookingvalidateTO.setCityId(pincodeTO.getCityId());
			bookingvalidateTO = validatePincodeProductServiceability(bookingvalidateTO);

			CityTO city = geographyCommonService.getCity(bookingvalidateTO
					.getPincode());
			if (city != null) {
				bookingvalidateTO.setCityId(city.getCityId());
				bookingvalidateTO.setCityName(city.getCityName());
			}
			// }

		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::isValidPincode----------->end:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	/**
	 * Checks if is valid consignment.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO isValidConsignment(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingValidator::isValidConsignment::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// Check for if alredy exists the consignment or not
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsgNumber())) {
			// consignment booked or not validation has been taken care in
			// pickup process. So avoiding the condition here.
			if (!StringUtils.equalsIgnoreCase(CommonConstants.PROCESS_PICKUP,
					bookingvalidateTO.getProcessCode())) {
				isConsgBooked(bookingvalidateTO);
			}
			if (StringUtils.equalsIgnoreCase(CommonConstants.NO,
					bookingvalidateTO.getIsPkupCN())) {
				// Booking Type - consignment series validations
				bookingProductValidation(bookingvalidateTO, errorCodes);
				// Goods Issue validations TODO
				StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
				String msg = "";
				stockValiationTO = issueValidation(bookingvalidateTO);
				StockUserTO stockUser = stockValiationTO.getIssuedTO();
				Boolean isCNIssuesd = stockValiationTO.getIsIssuedTOParty();
				if (!isCNIssuesd) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					errorCodes
							.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
				} else if (!StringUtil.isStringEmpty(stockValiationTO
						.getIssuedToPickupBoy())
						&& stockValiationTO.getIssuedToPickupBoy().equals(
								CommonConstants.NO)) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					errorCodes
							.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

				} else {
					// String businessAssociate = "BA001 - Likhitha Enterprise";
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.CCC_BOOKING,
							bookingvalidateTO.getBookingType())) {
						if (!StringUtil.isNull(stockUser)) {
							msg = stockUser.getStockUserCode() + " - "
									+ stockUser.getStockUserName();
							bookingvalidateTO.setIssuedTOPartyId(stockUser
									.getStockUserId());
							bookingvalidateTO.setConsgIssuedTO(msg);
							bookingvalidateTO.setCustID(stockUser
									.getStockUserId());
							bookingvalidateTO.setCustCode(msg);
						}
					}
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.BA_BOOKING,
							bookingvalidateTO.getBookingType())) {
						msg = stockUser.getStockUserCode() + " - "
								+ stockUser.getStockUserName();
						bookingvalidateTO.setIssuedTOPartyId(stockUser
								.getStockUserId());
						bookingvalidateTO.setBusinessAssociateId(stockUser
								.getStockUserId());
						bookingvalidateTO.setBusinessAssociate(msg);
					}

				}
			}
		} else {
			bookingvalidateTO.setIsValidCN(CommonConstants.NO);
			errorCodes.add(BookingErrorCodesConstants.INVALID_CN);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_CN);
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::isValidConsignment1::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	public BookingValidationTO validateConsignmentForBulkPrinted(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingValidator::validateConsignmentForBulkPrinted::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// Check for if alredy exists the consignment or not
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsgNumber())) {
			isConsgBooked(bookingvalidateTO);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsPkupCN())) {
				if (!StringUtil.isEmptyInteger(bookingvalidateTO
						.getIssuedTOPartyId())
						&& !StringUtil.isEmptyInteger(bookingvalidateTO
								.getCustID())
						&& !bookingvalidateTO.getIssuedTOPartyId().equals(
								bookingvalidateTO.getCustID())) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					errorCodes
							.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

				}
			} else if (StringUtils.equalsIgnoreCase(CommonConstants.NO,
					bookingvalidateTO.getIsPkupCN())) {
				// Booking Type - consignment series validations
				bookingProductValidation(bookingvalidateTO, errorCodes);
				// Goods Issue validations TODO
				StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
				String msg = "";
				stockValiationTO = issueValidation(bookingvalidateTO);
				StockUserTO stockUser = stockValiationTO.getIssuedTO();
				Boolean isCNIssuesd = stockValiationTO.getIsIssuedTOParty();
				// isCNIssuesd = Boolean.TRUE;
				if (!isCNIssuesd) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					errorCodes
							.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
				} else if (!StringUtil.isStringEmpty(stockValiationTO
						.getIssuedToPickupBoy())
						&& stockValiationTO.getIssuedToPickupBoy().equals(
								CommonConstants.NO)) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					errorCodes
							.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

				} else {
					// String businessAssociate = "BA001 - Likhitha Enterprise";
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.CCC_BOOKING,
							bookingvalidateTO.getBookingType())) {
						if (!StringUtil.isNull(stockUser)) {
							msg = stockUser.getStockUserCode() + " - "
									+ stockUser.getStockUserName();
							bookingvalidateTO.setIssuedTOPartyId(stockUser
									.getStockUserId());
							bookingvalidateTO.setConsgIssuedTO(msg);
							bookingvalidateTO.setCustID(stockUser
									.getStockUserId());
							bookingvalidateTO.setCustCode(msg);
						}
					}
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.BA_BOOKING,
							bookingvalidateTO.getBookingType())) {
						msg = stockUser.getStockUserCode() + " - "
								+ stockUser.getStockUserName();
						bookingvalidateTO.setIssuedTOPartyId(stockUser
								.getStockUserId());
						bookingvalidateTO.setBusinessAssociateId(stockUser
								.getStockUserId());
						bookingvalidateTO.setBusinessAssociate(msg);
					}

				}
			}
		} else {
			bookingvalidateTO.setIsValidCN(CommonConstants.NO);
			errorCodes.add(BookingErrorCodesConstants.INVALID_CN);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_CN);
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::validateConsignmentForBulkPrinted::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	public BookingValidationTO isValidConsignmentForBackDated(
			BookingValidationTO bookingvalidateTO, BookingDO bookingDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingValidator::isValidConsignmentForBackDated::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// Check for if alredy exists the consignment or not
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsgNumber())) {
			isConsgBooked(bookingvalidateTO);

			if (!StringUtil.isEmptyInteger(bookingvalidateTO.getBookingId())) {
				bookingDO.setBookingId(bookingvalidateTO.getBookingId());
			}
			if (!StringUtil.isStringEmpty(bookingvalidateTO
					.getPickupRunsheetNo())) {
				bookingDO.setPickRunsheetNo(bookingvalidateTO
						.getPickupRunsheetNo());
			}
			if (bookingvalidateTO.getIsConsgExists().equals(CommonConstants.NO)) {

				if (StringUtils.isNotEmpty(bookingvalidateTO.getBookingType())) {
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsPkupCN())) {
						if (!StringUtil.isEmptyInteger(bookingvalidateTO
								.getIssuedTOPartyId())
								&& !StringUtil.isEmptyInteger(bookingvalidateTO
										.getCustID())
								&& !bookingvalidateTO.getIssuedTOPartyId()
										.equals(bookingvalidateTO.getCustID())) {
							bookingvalidateTO
									.setIsConsgExists(CommonConstants.YES);
							errorCodes
									.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.YES, bookingvalidateTO
											.getIsBusinessExceptionReq()))
								throw new CGBusinessException(
										BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

						}

					} else {
						// Booking Type - consignment series validations
						List<ProductTO> prodList = bookingvalidateTO
								.getProductTOList();
						String consgSeries = bookingvalidateTO.getConsgSeries();
						if (StringUtil.isDigit(consgSeries.charAt(0))) {
							consgSeries = "N";
						}
						Boolean isProduct = false;
						for (ProductTO prod : prodList) {
							if (prod.getConsgSeries().equalsIgnoreCase(
									consgSeries))
								isProduct = true;
						}

						if (!isProduct) {
							bookingvalidateTO
									.setIsConsgExists(CommonConstants.YES);
							errorCodes
									.add(BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.YES, bookingvalidateTO
											.getIsBusinessExceptionReq()))
								throw new CGBusinessException(
										BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
						}

						// Goods Issue validations TODO
						StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
						String msg = "";
						stockValiationTO = issueValidationForBackdated(bookingvalidateTO);
						StockUserTO stockUser = stockValiationTO.getIssuedTO();
						Boolean isCNIssuesd = stockValiationTO
								.getIsIssuedTOParty();
						// isCNIssuesd = Boolean.TRUE;
						if (!isCNIssuesd) {
							bookingvalidateTO
									.setIsConsgExists(CommonConstants.YES);
							errorCodes
									.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.YES, bookingvalidateTO
											.getIsBusinessExceptionReq()))
								throw new CGBusinessException(
										BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
						} else {
							// String businessAssociate =
							// "BA001 - Likhitha Enterprise";
							if (StringUtils.equalsIgnoreCase(
									BookingConstants.CCC_BOOKING,
									bookingvalidateTO.getBookingType())) {
								if (!StringUtil.isNull(stockUser)) {
									msg = stockUser.getStockUserCode() + " - "
											+ stockUser.getStockUserName();
									bookingvalidateTO
											.setIssuedTOPartyId(stockUser
													.getStockUserId());
									bookingvalidateTO.setConsgIssuedTO(msg);
									bookingvalidateTO.setCustID(stockUser
											.getStockUserId());
									bookingvalidateTO.setCustCode(msg);
								}
							}
							if (StringUtils.equalsIgnoreCase(
									BookingConstants.BA_BOOKING,
									bookingvalidateTO.getBookingType())) {
								msg = stockUser.getStockUserCode() + " - "
										+ stockUser.getStockUserName();
								bookingvalidateTO.setIssuedTOPartyId(stockUser
										.getStockUserId());
								bookingvalidateTO
										.setBusinessAssociateId(stockUser
												.getStockUserId());
								bookingvalidateTO.setBusinessAssociate(msg);
							}

						}
					}
				}
			}
		} else {
			bookingvalidateTO.setIsValidCN(CommonConstants.NO);
			errorCodes.add(BookingErrorCodesConstants.INVALID_CN);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_CN);
		}

		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::isValidConsignmentForBackDated::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	/**
	 * @param bookingvalidateTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/**
	 * @param bookingvalidateTO
	 * @param bookingDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BookingValidationTO isValidConsignmentForBulk(
			BookingValidationTO bookingvalidateTO, BookingDO bookingDO)
			throws CGBusinessException, CGSystemException {
		
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("BookingValidator :: isValidConsignmentForBulk :: Start  ::");
		if (!StringUtil.isNull(bookingvalidateTO)) {
			logger.append(" ::Consignment No"
					+ bookingvalidateTO.getConsgNumber());
			logger.append(" ::Customer Code::-->"
					+ bookingvalidateTO.getCustCode());
		}
		logger.append("::Start Time::" + startTimeInMilis);
		LOGGER.debug(logger.toString());
		
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// Check for if alredy exists the consignment or not
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsgNumber())) {
			isConsgBooked(bookingvalidateTO);
			if (!StringUtil.isEmptyInteger(bookingvalidateTO.getBookingId())) {
				bookingDO.setBookingId(bookingvalidateTO.getBookingId());
			}
			if (!StringUtil.isStringEmpty(bookingvalidateTO
					.getPickupRunsheetNo())) {
				bookingDO.setPickRunsheetNo(bookingvalidateTO
						.getPickupRunsheetNo());
			}

			if (bookingvalidateTO.getIsConsgExists().equals(CommonConstants.NO)) {
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsPkupCN())) {
					if (!StringUtil.isEmptyInteger(bookingvalidateTO
							.getIssuedTOPartyId())
							&& !StringUtil.isEmptyInteger(bookingvalidateTO
									.getCustID())
							&& !bookingvalidateTO.getIssuedTOPartyId().equals(
									bookingvalidateTO.getCustID())) {
						bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
						errorCodes
								.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

					}
				} else if (StringUtils.equalsIgnoreCase(CommonConstants.NO,
						bookingvalidateTO.getIsPkupCN())) {
					// Booking Type - consignment series validations

					List<ProductTO> prodList = bookingvalidateTO
							.getProductTOList();
					String consgSeries = bookingvalidateTO.getConsgSeries();
					if (StringUtil.isDigit(consgSeries.charAt(0))) {
						consgSeries = "N";
					}
					Boolean isProduct = false;
					for (ProductTO prod : prodList) {
						if (prod.getConsgSeries().equalsIgnoreCase(consgSeries))
							isProduct = true;
					}

					if (!isProduct) {
						bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
						errorCodes
								.add(BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
					}

					// bookingProductValidation(bookingvalidateTO, errorCodes);
					// Goods Stock Issue validations
					LOGGER.debug("isValidConsignmentStock::Start"
							+ DateUtil.getCurrentTimeInMilliSeconds());
					StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
					stockValiationTO.setStockItemNumber(bookingvalidateTO
							.getConsgNumber());
					if (StringUtil.isDigit(bookingvalidateTO.getConsgSeries()
							.charAt(0))) {
						stockValiationTO.setRhoCode(bookingvalidateTO
								.getRhoCode());
					} else {
						stockValiationTO.setOfficeCode(bookingvalidateTO
								.getOfficeCode());
					}

					stockValiationTO
							.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
					stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
							.getIssuedTOPartyId());
					stockValiationTO
							.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);

					stockValiationTO = stockUniversalService
							.validateStock(stockValiationTO);

					if (!stockValiationTO.getIsIssuedTOParty()) {
						if ((StringUtil.isEmptyInteger(bookingvalidateTO
								.getCustID()))
								|| (!StringUtil
										.isEmptyInteger(bookingvalidateTO
												.getCustID())
										&& !StringUtil
												.isEmptyInteger(bookingvalidateTO
														.getIssuedTOPartyId()) && !bookingvalidateTO
										.getCustID().equals(
												bookingvalidateTO
														.getIssuedTOPartyId()))) {
							bookingvalidateTO
									.setIsConsgExists(CommonConstants.YES);
							errorCodes
									.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.YES, bookingvalidateTO
											.getIsBusinessExceptionReq()))
								throw new CGBusinessException(
										BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED_PICK_UP_BOY);

						}
					}

					// Extra validation for Credit customer booking
					if (StringUtils.equalsIgnoreCase(
							BookingConstants.CCC_BOOKING,
							bookingvalidateTO.getBookingType())) {
						stockValiationTO = stockIssueValidationCCBooking(
								bookingvalidateTO, errorCodes, stockValiationTO);
					}

					StockUserTO stockUser = stockValiationTO.getIssuedTO();
					Boolean isCNIssuesd = stockValiationTO.getIsIssuedTOParty();

					if (!isCNIssuesd) {
						bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
						errorCodes
								.add(BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
					} else {
						String msg = "";
						if (!StringUtil.isNull(stockUser)) {
							msg = stockUser.getStockUserCode() + " - "
									+ stockUser.getStockUserName();
							bookingvalidateTO.setIssuedTOPartyId(stockUser
									.getStockUserId());
							bookingvalidateTO.setConsgIssuedTO(msg);
							bookingvalidateTO.setCustID(stockUser
									.getStockUserId());
							bookingvalidateTO.setCustCode(msg);
						}

					}
				}
			}
			LOGGER.debug("isValidConsignmentStock::END"
					+ DateUtil.getCurrentTimeInMilliSeconds());
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("BookingValidator::isValidConsignmentForBulk::END------------>:::::" +
				"Consignment No::-->"+ bookingvalidateTO.getConsgNumber()+
				"  End Time "
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return bookingvalidateTO;

	}

	private void bookingProductValidation(
			BookingValidationTO bookingvalidateTO, List<String> errorCodes)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingValidator::bookingProductValidation::START------------>:::::::");
		String consgSeries = bookingvalidateTO.getConsgNumber().substring(4, 5);
		if (StringUtil.isDigit(consgSeries.charAt(0))) {
			boolean isProductServiced = bookingCommonService
					.isNormalProductServicedByBooking(
							bookingvalidateTO.getBookingType(),
							CommonConstants.NORMAL_CREDIT);
			if (!isProductServiced) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				errorCodes
						.add(BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
			} else {
				bookingvalidateTO.setIsProductServiced(Boolean.TRUE);
			}

		} else {
			boolean isProductServiced = bookingCommonService
					.isProductServicedByBooking(
							bookingvalidateTO.getBookingType(),
							bookingvalidateTO.getConsgNumber().substring(4, 5));
			if (!isProductServiced) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				errorCodes
						.add(BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
			} else {
				bookingvalidateTO.setIsProductServiced(Boolean.TRUE);
			}
		}
		LOGGER.debug("BookingValidator::bookingProductValidation::END------------>:::::::");
	}

	private void isConsgBooked(BookingValidationTO bookingvalidateTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingValidator::isConsgBooked::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		boolean isConsgBooked = Boolean.FALSE;
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		String bookingType = bookingvalidateTO.getBookingType();
		if ((StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingType))
				|| (StringUtils.equalsIgnoreCase(BookingConstants.BULK_BOOKING,
						bookingType))
				&& StringUtils.equalsIgnoreCase(
						CommonConstants.PROCESS_BOOKING,
						bookingvalidateTO.getProcessCode())) {
			CreditCustomerBookingDoxTO ccBooking = creditCustomerBookingService
					.getBookingByProcess(bookingvalidateTO.getConsgNumber(),
							CommonConstants.PROCESS_PICKUP);
			if (!StringUtil.isNull(ccBooking)) {
				/*
				 * isConsgBooked = bookingCommonService
				 * .isChildConsgBooked(bookingvalidateTO.getConsgNumber());
				 */

				isConsgBooked = bookingCommonService
						.isConsgBookedForPickup(bookingvalidateTO
								.getConsgNumber());

				bookingvalidateTO.setBookingId(ccBooking.getBookingId());
				bookingvalidateTO.setBookingOfficeId(ccBooking
						.getBookingOfficeId());
				bookingvalidateTO.setCustCode(ccBooking.getCustomer()
						.getCustomerCode()
						+ " - "
						+ ccBooking.getCustomer().getBusinessName());
				bookingvalidateTO.setCustID(ccBooking.getCustomer()
						.getCustomerId());
				bookingvalidateTO.setPickupRunsheetNo(ccBooking
						.getPickupRunsheetNo());
				bookingvalidateTO.setIsPkupCN(CommonConstants.YES);
			} else {
				isConsgBooked = bookingCommonService
						.isConsgBooked(bookingvalidateTO.getConsgNumber());
			}
		} else if ((StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
				bookingType))
				|| (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
						bookingType))
				|| (StringUtils.equalsIgnoreCase(BookingConstants.FOC_BOOKING,
						bookingType))
				&& StringUtils.equalsIgnoreCase(
						CommonConstants.PROCESS_BOOKING,
						bookingvalidateTO.getProcessCode())) {
			CreditCustomerBookingDoxTO ccBooking = creditCustomerBookingService
					.getBookingByProcess(bookingvalidateTO.getConsgNumber(),
							CommonConstants.PROCESS_PICKUP);
			if (!StringUtil.isNull(ccBooking)) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				errorCodes.add(BookingErrorCodesConstants.INVALID_PICKUP_CONSG);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PICKUP_CONSG);
			} else {
				isConsgBooked = bookingCommonService
						.isConsgBooked(bookingvalidateTO.getConsgNumber());
			}
		}
		if (isConsgBooked) {
			bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
			errorCodes.add(BookingErrorCodesConstants.CONSG_BOOKED);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.CONSG_BOOKED);
		}
		LOGGER.debug("BookingValidator::isConsgBooked::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
	}

	public void isConsgBookedForBulk(BookingValidationTO bookingvalidateTO,
			BookingDO bookingDO) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingValidator::isConsgBooked::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		boolean isConsgBooked = Boolean.FALSE;
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		if ((StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType()))
				|| (StringUtils.equalsIgnoreCase(BookingConstants.BULK_BOOKING,
						bookingvalidateTO.getBookingType()))
				&& StringUtils.equalsIgnoreCase(
						CommonConstants.PROCESS_BOOKING,
						bookingvalidateTO.getProcessCode())) {
			CreditCustomerBookingDoxTO ccBooking = creditCustomerBookingService
					.getBookingByProcess(bookingvalidateTO.getConsgNumber(),
							CommonConstants.PROCESS_PICKUP);
			if (!StringUtil.isNull(ccBooking)) {
				bookingvalidateTO.setBookingId(ccBooking.getBookingId());
				bookingvalidateTO.setBookingOfficeId(ccBooking
						.getBookingOfficeId());
				bookingvalidateTO.setCustCode(ccBooking.getCustomer()
						.getCustomerCode()
						+ " - "
						+ ccBooking.getCustomer().getBusinessName());
				bookingvalidateTO.setCustID(ccBooking.getCustomer()
						.getCustomerId());
				bookingvalidateTO.setPickupRunsheetNo(ccBooking
						.getPickupRunsheetNo());
				bookingvalidateTO.setIsPkupCN(CommonConstants.YES);
				bookingDO.setBookingId(ccBooking.getBookingId());
				bookingDO.setPickRunsheetNo(ccBooking.getPickupRunsheetNo());

			} else {
				isConsgBooked = bookingCommonService
						.isConsgBooked(bookingvalidateTO.getConsgNumber());
			}
		} else {

			isConsgBooked = bookingCommonService
					.isConsgBooked(bookingvalidateTO.getConsgNumber());
		}
		if (isConsgBooked) {
			bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
			errorCodes.add(BookingErrorCodesConstants.CONSG_BOOKED);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.CONSG_BOOKED);
		}
		LOGGER.debug("BookingValidator::isConsgBooked::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
	}

	/**
	 * Validate pincode product serviceability.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validatePincodeProductServiceability(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingValidator::validatePincodeProductServiceability::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Check for pincode serviceability for Priority Products
		boolean isPincodeServicedByProduct = Boolean.FALSE;
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// List<PincodeProductServiceabilityTO> pincodeDlvTimeMaps = null;
		PincodeServicabilityTO pincodeProdServicebility = new PincodeServicabilityTO();
		pincodeProdServicebility.setPincode(bookingvalidateTO.getPincode());

		if (!StringUtil.isNull(bookingvalidateTO.getConsgSeries())) {
			pincodeProdServicebility.setConsgSeries(bookingvalidateTO
					.getConsgSeries());
			if (StringUtil
					.isDigit(bookingvalidateTO.getConsgSeries().charAt(0))) {
				pincodeProdServicebility
						.setConsgSeries(CommonConstants.PRODUCT_SERIES_NORMALCREDIT);
			}
			pincodeProdServicebility.setCityId(bookingvalidateTO.getCityId());
			try {
				isPincodeServicedByProduct = geographyCommonService
						.isPincodeServicedByProduct(pincodeProdServicebility);
			} catch (CGBusinessException e) {
				isPincodeServicedByProduct = Boolean.TRUE;
				errorCodes
						.add(BookingErrorCodesConstants.ORIGIN_NOT_DEFINED_FOR_PRODUCT);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw e;
			}
		}

		if (!isPincodeServicedByProduct) {
			bookingvalidateTO.setIsValidPriorityPincode(CommonConstants.NO);
			if (StringUtils.equalsIgnoreCase(
					BookingConstants.BOOKING_PRIORITY_PRODUCT,
					bookingvalidateTO.getConsgSeries())) {
				errorCodes
						.add(BookingErrorCodesConstants.INVALID_PRIORITY_PINCODE_SEVERCEABILITY);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PRIORITY_PINCODE_SEVERCEABILITY);
			} else {
				errorCodes
						.add(BookingErrorCodesConstants.INVALID_PINCODE_NOT_SERVICED_BY_PRODUCT);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PINCODE_NOT_SERVICED_BY_PRODUCT);
			}

		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.trace("BookingValidator::validatePincodeProductServiceability::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	/**
	 * Issue validation.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the stock issue validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private StockIssueValidationTO issueValidation(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {

		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("BookingValidator :: issueValidation :: Start  ::");
		if (!StringUtil.isNull(bookingvalidateTO)) {
			logger.append(" ::Consignment No::-->"
					+ bookingvalidateTO.getConsgNumber());
			logger.append(" ::Customer Code::-->"
					+ bookingvalidateTO.getCustCode());
		}
		logger.append("::Start Time::" + startTimeInMilis);
		LOGGER.debug(logger.toString());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
		stockValiationTO.setLoggedInCityId(bookingvalidateTO.getLoggedInCityId());
		if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingvalidateTO.getBookingType())
				|| StringUtils.equalsIgnoreCase(BookingConstants.FOC_BOOKING,
						bookingvalidateTO.getBookingType())) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId()))
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId());
			stockValiationTO.setStockItemNumber(bookingvalidateTO
					.getConsgNumber());
			String consgSeries = bookingvalidateTO.getConsgNumber().substring(
					4, 5);
			if (StringUtil.isDigit(consgSeries.charAt(0))) {
				stockValiationTO.setRhoCode(bookingvalidateTO.getRhoCode());
			} else {
				stockValiationTO.setOfficeCode(bookingvalidateTO
						.getOfficeCode());
			}
			stockValiationTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockValiationTO.setBusinessException(null);
			stockValiationTO.setIssuingOfficeId(null);
			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);

			if (!stockValiationTO.getIsIssuedTOParty()) {
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
				stockValiationTO.setBusinessException(null);
				stockValiationTO.setIssuedTOPartyId(null);
				stockValiationTO.setIssuingOfficeId(bookingvalidateTO
						.getIssuedTOPartyId());
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}

		}

		else if (StringUtils.equalsIgnoreCase(BookingConstants.BULK_BOOKING,
				bookingvalidateTO.getBookingType())) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId()))
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId());
			stockValiationTO.setStockItemNumber(bookingvalidateTO
					.getConsgNumber());
			String consgSeries = bookingvalidateTO.getConsgNumber().substring(
					4, 5);
			if (StringUtil.isDigit(consgSeries.charAt(0))) {
				stockValiationTO.setRhoCode(bookingvalidateTO.getRhoCode());
			} else {
				stockValiationTO.setOfficeCode(bookingvalidateTO
						.getOfficeCode());
			}
			stockValiationTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);

			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
			stockValiationTO.setBusinessException(null);
			stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
					.getIssuedTOPartyId());
			stockValiationTO.setIssuingOfficeId(null);
			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);

			if (!stockValiationTO.getIsIssuedTOParty()) {
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
				stockValiationTO.setBusinessException(null);
				stockValiationTO.setIssuedTOPartyId(null);
				stockValiationTO.setIssuingOfficeId(bookingvalidateTO
						.getIssuedTOPartyId1());
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
				if (stockValiationTO.getIsIssuedTOParty()) {
					if ((StringUtil.isEmptyInteger(bookingvalidateTO
							.getCustID()))
							|| (!StringUtil.isEmptyInteger(bookingvalidateTO
									.getCustID())
									&& !StringUtil
											.isEmptyInteger(bookingvalidateTO
													.getIssuedTOPartyId()) && !bookingvalidateTO
									.getCustID().equals(
											bookingvalidateTO
													.getIssuedTOPartyId()))) {
						stockValiationTO
								.setIssuedToPickupBoy(CommonConstants.NO);
					}
				}
			}

			if (!stockValiationTO.getIsIssuedTOParty()) {
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
				stockValiationTO.setBusinessException(null);
				stockValiationTO.setIssuingOfficeId(null);
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}
		}

		else {
			stockValiationTO = prepareInputsForIssueValidations(bookingvalidateTO);

			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);
		}
		// Extra validation for Credit customer booking
		if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType())) {
			// CC BOOKING
			stockValiationTO = stockIssueValidationCCBooking(bookingvalidateTO,
					errorCodes, stockValiationTO);
		}

		if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingvalidateTO.getBookingType())
				|| StringUtils.equalsIgnoreCase(BookingConstants.FOC_BOOKING,
						bookingvalidateTO.getBookingType())) {
			if (!stockValiationTO.getIsIssuedTOParty()) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
			}
		} else if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
				bookingvalidateTO.getBookingType())) {
			if (!stockValiationTO.getIsIssuedTOParty()) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_NOT_ISSUED_TO_BA);
			}
		} else if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType())) {
			if (!stockValiationTO.getIsIssuedTOParty()) {
				bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_NOT_ISSUED_TO_CUSTOMER_UPDATED_FOR_CUSTOMER);
			}
		}

		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		LOGGER.debug("BookingValidator::issueValidation::END------------>:::::" +
				"Consignment No::-->"+ bookingvalidateTO.getConsgNumber()+
				"  End Time "
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return stockValiationTO;
	}

	private StockIssueValidationTO issueValidationForBackdated(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {

		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("BookingValidator::issueValidationForBackdated::START------------>");
		if (!StringUtil.isNull(bookingvalidateTO)) {
			logger.append(" ::Consignment No::-->"
					+ bookingvalidateTO.getConsgNumber());
			logger.append(" ::Customer Code::-->"
					+ bookingvalidateTO.getCustCode());
		}
		logger.append("::Start Time::" + startTimeInMilis);
		LOGGER.debug(logger.toString());

		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();

		if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingvalidateTO.getBookingType())
				|| StringUtils.equalsIgnoreCase(BookingConstants.FOC_BOOKING,
						bookingvalidateTO.getBookingType())) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId1()))
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId1());
			stockValiationTO.setStockItemNumber(bookingvalidateTO
					.getConsgNumber());
			String consgSeries = bookingvalidateTO.getConsgNumber().substring(
					4, 5);
			if (StringUtil.isDigit(consgSeries.charAt(0))) {
				stockValiationTO.setRhoCode(bookingvalidateTO.getRhoCode());
			} else {
				stockValiationTO.setOfficeCode(bookingvalidateTO
						.getOfficeCode());
			}
			stockValiationTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
			if (bookingvalidateTO.getCustCode() == null){
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			} else {
				stockValiationTO
				.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
			}
			stockValiationTO.setBusinessException(null);
			stockValiationTO.setIssuingOfficeId(null);
			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);

			if (!stockValiationTO.getIsIssuedTOParty()) {
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
				stockValiationTO.setBusinessException(null);
				stockValiationTO.setIssuedTOPartyId(null);
				stockValiationTO.setIssuingOfficeId(bookingvalidateTO
						.getIssuedTOPartyId1());
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}

		} else {
			stockValiationTO = prepareInputsForIssueValidationsForBackdated(bookingvalidateTO);

			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);
		}
		// Extra validation for Credit customer booking
		if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType())) {
			// CC BOOKING
			stockValiationTO = stockIssueValidationCCBooking(bookingvalidateTO,
					errorCodes, stockValiationTO);
		} else {
			// For CASH/FOC Booking
			if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
					bookingvalidateTO.getBookingType())
					|| StringUtils.equalsIgnoreCase(
							BookingConstants.FOC_BOOKING,
							bookingvalidateTO.getBookingType())) {
				if (!stockValiationTO.getIsIssuedTOParty()) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVLID_CN_NOT_ISSUED);
				}
			} else if (StringUtils.equalsIgnoreCase(
					BookingConstants.BA_BOOKING,
					bookingvalidateTO.getBookingType())) {
				if (!stockValiationTO.getIsIssuedTOParty()) {
					bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_NOT_ISSUED_TO_BA);
				}
			}
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("BookingValidator::issueValidationForBackdated::END------------>:::::" +
				"Consignment No::-->"+ bookingvalidateTO.getConsgNumber()+
				"  End Time "
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return stockValiationTO;
	}

	private StockIssueValidationTO prepareInputsForIssueValidationsForBackdated(
			BookingValidationTO bookingvalidateTO) {
		LOGGER.debug("BookingValidator::prepareInputsForIssueValidations::START------------>:::::::");
		StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
		stockValiationTO.setStockItemNumber(bookingvalidateTO.getConsgNumber());
		String consgSeries = bookingvalidateTO.getConsgNumber().substring(4, 5);
		if (StringUtil.isDigit(consgSeries.charAt(0))) {
			stockValiationTO.setRhoCode(bookingvalidateTO.getRhoCode());
		} else {
			stockValiationTO.setOfficeCode(bookingvalidateTO.getOfficeCode());
		}

		if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
				bookingvalidateTO.getBookingType())) {
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BA);
			stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
					.getIssuedTOPartyId());
		} else if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType())) {
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
			stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
					.getIssuedTOPartyId());
		} else if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingvalidateTO.getBookingType())
				|| StringUtils.equalsIgnoreCase(BookingConstants.FOC_BOOKING,
						bookingvalidateTO.getBookingType())) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId1()))
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId1());
		}
		stockValiationTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
		
		//added by shaheed
		stockValiationTO.setLoggedInCityId(bookingvalidateTO.getLoggedInCityId());
		//code ends here
		LOGGER.debug("BookingValidator::prepareInputsForIssueValidations::END------------>:::::::");
		return stockValiationTO;
	}

	private StockIssueValidationTO prepareInputsForIssueValidations(
			BookingValidationTO bookingvalidateTO) {
		LOGGER.debug("BookingValidator::prepareInputsForIssueValidations::START------------>:::::::");
		StockIssueValidationTO stockValiationTO = new StockIssueValidationTO();
		stockValiationTO.setStockItemNumber(bookingvalidateTO.getConsgNumber());
		stockValiationTO.setLoggedInCityId(bookingvalidateTO.getLoggedInCityId());
		String consgSeries = bookingvalidateTO.getConsgNumber().substring(4, 5);
		if (StringUtil.isDigit(consgSeries.charAt(0))) {
			stockValiationTO.setRhoCode(bookingvalidateTO.getRhoCode());
		} else {
			stockValiationTO.setOfficeCode(bookingvalidateTO.getOfficeCode());
		}

		if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
				bookingvalidateTO.getBookingType()))
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BA);
		else if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingvalidateTO.getBookingType()))
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
		if (!StringUtil.isEmptyInteger(bookingvalidateTO.getIssuedTOPartyId()))
			stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
					.getIssuedTOPartyId());
		stockValiationTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
		LOGGER.debug("BookingValidator::prepareInputsForIssueValidations::END------------>:::::::");
		return stockValiationTO;
	}

	private StockIssueValidationTO stockIssueValidationCCBooking(
			BookingValidationTO bookingvalidateTO, List<String> errorCodes,
			StockIssueValidationTO stockValiationTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingValidator::stockIssueValidationCCBooking::START------------>:::::::");
		if (StringUtils.equalsIgnoreCase(CommonConstants.PROCESS_PICKUP,
				bookingvalidateTO.getProcessCode())) {
			// Branch validation
			stockValiationTO = issueValidationForPickupBooking(
					bookingvalidateTO, stockValiationTO);
		} else {
			stockValiationTO = issueValidationForCCBooking(bookingvalidateTO,
					stockValiationTO);
		}
		// }
		LOGGER.debug("BookingValidator::stockIssueValidationCCBooking::END------------>:::::::");
		return stockValiationTO;
	}

	private StockIssueValidationTO issueValidationForCCBooking(
			BookingValidationTO bookingvalidateTO,
			StockIssueValidationTO stockValiationTO) throws CGSystemException,
			CGBusinessException {
		
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("BookingValidator::issueValidationForCCBooking::START------------>");
		if (!StringUtil.isNull(bookingvalidateTO)) {
			logger.append(" ::Consignment No::-->"
					+ bookingvalidateTO.getConsgNumber());
			logger.append(" ::Customer Code::-->"
					+ bookingvalidateTO.getCustCode());
		}
		logger.append("::Start Time::" + startTimeInMilis);
		LOGGER.debug(logger.toString());
		
		if (!stockValiationTO.getIsIssuedTOParty()) {
			stockValiationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_FR);
			stockValiationTO.setBusinessException(null);
			stockValiationTO = stockUniversalService
					.validateStock(stockValiationTO);
		} else if (!stockValiationTO.getIsIssuedTOParty()) {
			bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_NOT_ISSUED_TO_CUSTOMER);
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("BookingValidator::issueValidationForCCBooking::END------------>:::::" +
				"Consignment No::-->"+ bookingvalidateTO.getConsgNumber()+
				"  End Time "
				+ endTimeInMilis
				+" ::Time Diff in miliseconds ::"+(diff)
				+" ::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return stockValiationTO;
	}

	private StockIssueValidationTO issueValidationForPickupBooking(
			BookingValidationTO bookingvalidateTO,
			StockIssueValidationTO stockValiationTO) throws CGSystemException,
			CGBusinessException {
		
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("BookingValidator::issueValidationForPickupBooking::START------------>");
		if (!StringUtil.isNull(bookingvalidateTO)) {
			logger.append(" ::Consignment No::-->"
					+ bookingvalidateTO.getConsgNumber());
			logger.append(" ::Customer Code::-->"
					+ bookingvalidateTO.getCustCode());
		}
		logger.append("::Start Time::" + startTimeInMilis);
		LOGGER.debug(logger.toString());
		
		if (!stockValiationTO.getIsIssuedTOParty()) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId1())) {
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId1());
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
				stockValiationTO.setBusinessException(null);
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}
		}
		if (!stockValiationTO.getIsIssuedTOParty()) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId2())) {
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId2());
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
				stockValiationTO.setBusinessException(null);
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}
		}
		if (!stockValiationTO.getIsIssuedTOParty()) {
			if (!StringUtil.isEmptyInteger(bookingvalidateTO
					.getIssuedTOPartyId())) {
				stockValiationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_FR);
				stockValiationTO.setIssuedTOPartyId(bookingvalidateTO
						.getIssuedTOPartyId());
				stockValiationTO.setBusinessException(null);
				stockValiationTO = stockUniversalService
						.validateStock(stockValiationTO);
			}
		}
		if (!stockValiationTO.getIsIssuedTOParty()) {
			bookingvalidateTO.setIsConsgExists(CommonConstants.YES);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_NOT_ISSUED_TO_CUSTOMER_PICKUP_BOY_OR_BRANCH);
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;

		LOGGER.debug("BookingValidator::issueValidationForPickupBooking::END------------>:::::" +
				"Consignment No::-->"+ bookingvalidateTO.getConsgNumber()+
				"  End Time "
				+ endTimeInMilis
				+" ::Time Diff in miliseconds ::"+(diff)
				+" ::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return stockValiationTO;
	}

	/**
	 * Validate mobile or phone no.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingValidationTO validateMobileOrPhoneNo(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException {
		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		if (!StringUtil.isNull(bookingvalidateTO.getConsignee())) {

			if (StringUtil.isStringEmpty(bookingvalidateTO.getConsignee()
					.getPhone())
					&& StringUtil.isStringEmpty(bookingvalidateTO
							.getConsignee().getMobile())) {
				errorCodes
						.add(BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);

			}
			if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignee()
					.getPhone())) {
				if (bookingvalidateTO.getConsignee().getPhone().length() > 11
						|| !StringUtils.isNumeric(bookingvalidateTO
								.getConsignee().getPhone())) {
					errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_PHONE_NO);
				}
			}
			if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignee()
					.getMobile())) {
				if (bookingvalidateTO.getConsignee().getMobile().length() > 10
						|| !StringUtils.isNumeric(bookingvalidateTO
								.getConsignee().getMobile())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_MOBILE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_MOBILE_NO);
				}
			}
		}
		if (!StringUtil.isNull(bookingvalidateTO.getConsignor())) {
			if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignor()
					.getPhone())) {
				if (bookingvalidateTO.getConsignor().getPhone().length() > 11
						|| !StringUtils.isNumeric(bookingvalidateTO
								.getConsignor().getPhone())) {
					errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_PHONE_NO);
				}
			}
			if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignor()
					.getMobile())) {
				if (bookingvalidateTO.getConsignor().getMobile().length() > 10
						|| !StringUtils.isNumeric(bookingvalidateTO
								.getConsignor().getMobile())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_MOBILE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_MOBILE_NO);
				}
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	public BookingValidationTO validateMobileOrPhoneNoForBulk(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException {
		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();

		if (StringUtil.isStringEmpty(bookingvalidateTO.getConsigneePhn())
				&& StringUtil.isStringEmpty(bookingvalidateTO
						.getConsigneeMobile())) {
			errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);

		}
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsigneePhn())) {
			if (bookingvalidateTO.getConsigneePhn().length() > 11
					|| !StringUtils.isNumeric(bookingvalidateTO
							.getConsigneePhn())) {
				errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_NO);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PHONE_NO);
			}
		}
		if (StringUtils.isNotEmpty(bookingvalidateTO.getConsigneeMobile())) {
			if (bookingvalidateTO.getConsigneeMobile().length() > 10
					|| !StringUtils.isNumeric(bookingvalidateTO
							.getConsigneeMobile())) {
				errorCodes.add(BookingErrorCodesConstants.INVALID_MOBILE_NO);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_MOBILE_NO);
			}
		}

		/*
		 * if (StringUtil.isStringEmpty(bookingvalidateTO.getConsignorPhn()) &&
		 * StringUtil.isStringEmpty(bookingvalidateTO .getConsignorMobile())) {
		 * errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);
		 * if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
		 * bookingvalidateTO.getIsBusinessExceptionReq())) throw new
		 * CGBusinessException(
		 * BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);
		 * 
		 * }
		 * 
		 * if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignorPhn())) { if
		 * (bookingvalidateTO.getConsignorMobile().length() > 11 ||
		 * !StringUtils.isNumeric(bookingvalidateTO .getConsignorPhn())) {
		 * errorCodes.add(BookingErrorCodesConstants.INVALID_PHONE_NO); if
		 * (StringUtils.equalsIgnoreCase(CommonConstants.YES,
		 * bookingvalidateTO.getIsBusinessExceptionReq())) throw new
		 * CGBusinessException( BookingErrorCodesConstants.INVALID_PHONE_NO); }
		 * } if (StringUtils.isNotEmpty(bookingvalidateTO.getConsignorMobile()))
		 * { if (bookingvalidateTO.getConsignorMobile().length() > 10 ||
		 * !StringUtils.isNumeric(bookingvalidateTO .getConsignorMobile())) {
		 * errorCodes.add(BookingErrorCodesConstants.INVALID_MOBILE_NO); if
		 * (StringUtils.equalsIgnoreCase(CommonConstants.YES,
		 * bookingvalidateTO.getIsBusinessExceptionReq())) throw new
		 * CGBusinessException( BookingErrorCodesConstants.INVALID_MOBILE_NO); }
		 * }
		 */

		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	/**
	 * Validate consignee consignor.
	 * 
	 * @param bookingvalidateTO
	 * 
	 * @param bookingDO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingValidationTO validateConsigneeConsignor(
			BookingValidationTO bookingvalidateTO, BookingDO bookingDO)
			throws CGBusinessException {
		LOGGER.debug("BookingValidator::validateConsigneeConsignor::START------------>:::::::");
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		String consgSeries = null;
		if (!StringUtil.isStringEmpty(bookingDO.getConsgNumber())) {
			consgSeries = bookingDO.getConsgNumber().substring(4, 5);
		}
		if (!StringUtil.isNull(consgSeries)) {
			if (StringUtils.equalsIgnoreCase(
					BookingConstants.BOOKING_PRIORITY_PRODUCT, consgSeries)
					|| StringUtils.equalsIgnoreCase(
							BookingConstants.BOOKING_COD_PRODUCT, consgSeries)) {
				if (StringUtil.isNull(bookingDO.getConsigneeId())
						|| StringUtil.isNull(bookingDO.getConsignorId())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_CONSIGNEE_CONSIGNOR);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_CONSIGNEE_CONSIGNOR);
				}
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::validateConsigneeConsignor::END------------>:::::::");
		return bookingvalidateTO;
	}

	/**
	 * Validate declared value.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BookingValidator::validateDeclaredValue::START------------>:::::::");
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		BookingTypeConfigTO bookingTypeConfig = bookingCommonService
				.getBookingTypeConfig(bookingvalidateTO.getBookingType());
		if (!StringUtil.isNull(bookingTypeConfig)
				&& !StringUtil.isEmptyDouble(bookingvalidateTO
						.getDeclaredValue())) {
			if (bookingvalidateTO.getDeclaredValue() > bookingTypeConfig
					.getMaxDeclaredValAllowed()) {
				errorCodes
						.add(BookingErrorCodesConstants.DECLARED_VALUE_EXCEEDED);
				bookingvalidateTO.setIsValidDeclaredVal(CommonConstants.NO);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.DECLARED_VALUE_EXCEEDED);
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.trace("BookingValidator::validateDeclaredValue::END------------>:::::::");
		return bookingvalidateTO;
	}

	public BookingValidationTO isValidAlternatePincode(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("BookingValidator::isValidAlternatePincode----------->START:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		PincodeTO pincodeTO = null;
		PincodeTO pincodeTO1 = new PincodeTO();
		pincodeTO1.setPincode(bookingvalidateTO.getAltPincode());
		pincodeTO = geographyCommonService.validatePincode(pincodeTO1);
		if (StringUtil.isNull(pincodeTO)) {
			errorCodes
					.add(BookingErrorCodesConstants.INVALID_ALTERNATE_PINCODE);
			bookingvalidateTO.setIsValidPincode(CommonConstants.NO);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingvalidateTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_PINCODE);
		} else {
			bookingvalidateTO.setAltPincodeId(pincodeTO.getPincodeId());
			bookingvalidateTO.setAltPincode(pincodeTO.getPincode());

			bookingvalidateTO = validateAlternatePincodeProductServiceability(bookingvalidateTO);
			CityTO city = geographyCommonService.getCity(bookingvalidateTO
					.getPincode());
			if (city != null) {
				bookingvalidateTO.setAltCityId(city.getCityId());
				bookingvalidateTO.setAltCityName(city.getCityName());
			}

		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::isValidAlternatePincode----------->end:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;

	}

	private BookingValidationTO validateAlternatePincodeProductServiceability(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BookingValidator::validatePincodeProductServiceability::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Check for pincode serviceability for Priority Products
		boolean isPincodeServicedByProduct = Boolean.FALSE;
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		// List<PincodeProductServiceabilityTO> pincodeDlvTimeMaps = null;
		PincodeServicabilityTO pincodeProdServicebility = new PincodeServicabilityTO();
		pincodeProdServicebility.setPincode(bookingvalidateTO.getAltPincode());
		if (!StringUtil.isStringEmpty(bookingvalidateTO.getConsgSeries())) {
			pincodeProdServicebility.setConsgSeries(bookingvalidateTO
					.getConsgSeries());
			if (StringUtil
					.isDigit(bookingvalidateTO.getConsgSeries().charAt(0))) {
				pincodeProdServicebility
						.setConsgSeries(CommonConstants.PRODUCT_SERIES_NORMALCREDIT);
			}
			pincodeProdServicebility.setCityId(bookingvalidateTO.getCityId());
			LOGGER.trace("BookingValidator::validatPincodeProductServiceability:start------------>:::::::"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			try {
				isPincodeServicedByProduct = geographyCommonService
						.isPincodeServicedByProduct(pincodeProdServicebility);
			} catch (CGBusinessException e) {
				isPincodeServicedByProduct = Boolean.TRUE;
				errorCodes
						.add(BookingErrorCodesConstants.ORIGIN_NOT_DEFINED_FOR_PRODUCT_ALTERNATE_PINCODE);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw e;
			}
			LOGGER.trace("BookingValidator::validatPincodeProductServiceability:end------------>:::::::"
					+ DateUtil.getCurrentTimeInMilliSeconds());
		}
		if (!isPincodeServicedByProduct) {
			bookingvalidateTO.setIsValidPriorityPincode(CommonConstants.NO);
			if (StringUtils.equalsIgnoreCase(
					BookingConstants.BOOKING_PRIORITY_PRODUCT,
					bookingvalidateTO.getConsgSeries())) {
				errorCodes
						.add(BookingErrorCodesConstants.INVALID_PRIORITY_PINCODE_SEVERCEABILITY);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_PRIORITY_PINCODE_SEVERCEABILITY);
			} else {
				errorCodes
						.add(BookingErrorCodesConstants.INVALID_ALT_PINCODE_NOT_SERVICED_BY_PRODUCT);
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						bookingvalidateTO.getIsBusinessExceptionReq()))
					throw new CGBusinessException(
							BookingErrorCodesConstants.INVALID_ALT_PINCODE_NOT_SERVICED_BY_PRODUCT);
			}

		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.trace("BookingValidator::validatePincodeProductServiceability::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookingvalidateTO;
	}

	public void validateMobileOrPhoneNoForBackdated(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException {

		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> errorCodes = bookingvalidateTO.getErrorCodes();
		String bookingType = bookingvalidateTO.getBookingType();

		if (!StringUtil.isStringEmpty(bookingType)) {

			if ((bookingType.equalsIgnoreCase(BookingConstants.CASH_BOOKING))
					|| (bookingType
							.equalsIgnoreCase(BookingConstants.FOC_BOOKING))
					|| (bookingType
							.equalsIgnoreCase(BookingConstants.BA_BOOKING)
							&& !StringUtil.isNull(bookingvalidateTO
									.getConsgSeries()) && bookingvalidateTO
							.getConsgSeries().equalsIgnoreCase("P"))
					|| (bookingType
							.equalsIgnoreCase(BookingConstants.CCC_BOOKING)
							&& !StringUtil.isNull(bookingvalidateTO
									.getConsgSeries()) && bookingvalidateTO
							.getConsgSeries().equalsIgnoreCase("T"))) {

				if (StringUtil.isStringEmpty(bookingvalidateTO
						.getConsigneeFirstName())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_CONSIGNEE_FIRST_NAME);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_CONSIGNEE_FIRST_NAME);

				}

				if (StringUtil.isStringEmpty(bookingvalidateTO
						.getConsigneePhn())
						&& StringUtil.isStringEmpty(bookingvalidateTO
								.getConsigneeMobile())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_PHONE_MOBILE_NO_CONSIGNEE);

				}
				if (StringUtils.isNotEmpty(bookingvalidateTO.getConsigneePhn())) {
					if (bookingvalidateTO.getConsigneePhn().length() > 11
							|| !StringUtils.isNumeric(bookingvalidateTO
									.getConsigneePhn())) {
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_PHONE_NO_CONSIGNEE);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.INVALID_PHONE_NO_CONSIGNEE);
					}
				}
				if (StringUtils.isNotEmpty(bookingvalidateTO
						.getConsigneeMobile())) {
					if (bookingvalidateTO.getConsigneeMobile().length() > 10
							|| !StringUtils.isNumeric(bookingvalidateTO
									.getConsigneeMobile())) {
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_MOBILE_NO_CONSIGNEE);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.INVALID_MOBILE_NO_CONSIGNEE);
					}
				}

				if (StringUtil.isStringEmpty(bookingvalidateTO
						.getConsignorMobile())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_CONSIGNOR_MOBILE_NO);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_CONSIGNOR_MOBILE_NO);

				}

				if (StringUtil.isStringEmpty(bookingvalidateTO
						.getConsignorFirstName())) {
					errorCodes
							.add(BookingErrorCodesConstants.INVALID_CONSIGNOR_FIRST_NAME);
					if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
							bookingvalidateTO.getIsBusinessExceptionReq()))
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_CONSIGNOR_FIRST_NAME);

				}

				if (StringUtils.isNotEmpty(bookingvalidateTO
						.getConsignorMobile())) {
					if (bookingvalidateTO.getConsignorMobile().length() > 10
							|| !StringUtils.isNumeric(bookingvalidateTO
									.getConsignorMobile())) {
						errorCodes
								.add(BookingErrorCodesConstants.INVALID_MOBILE_NO_CONSIGNOR);
						if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
								bookingvalidateTO.getIsBusinessExceptionReq()))
							throw new CGBusinessException(
									BookingErrorCodesConstants.INVALID_MOBILE_NO_CONSIGNOR);
					}
				}
			}
		}
		bookingvalidateTO.setErrorCodes(errorCodes);
		LOGGER.debug("BookingValidator::validateMobileOrPhoneNo::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());

	}

	public void validConsgFormat(BookingValidationTO bookingvalidateTO,
			String consgNumber) {
		List<String> errors = bookingvalidateTO.getErrorCodes();
		if (StringUtil.isStringEmpty(consgNumber))
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_DOES_NOT_EXISTS);
		else if (consgNumber.length() != 12)
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_NO_FORMAT);
		else if (!StringUtils.isAlpha(consgNumber.substring(0, 1))
				|| !StringUtils.isAlphanumeric(consgNumber.substring(4, 5))
				|| !StringUtils.isNumeric(consgNumber.substring(1, 4))
				|| !StringUtils.isNumeric(consgNumber.substring(5, 12))) {
			errors.add(BookingErrorCodesConstants.INVALID_CONSG_NO_FORMAT);

		}

	}
}
