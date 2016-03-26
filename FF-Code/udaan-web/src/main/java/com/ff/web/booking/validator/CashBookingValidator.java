package com.ff.web.booking.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingValidationTO;
import com.ff.serviceOfferring.PrivilegeCardTO;
import com.ff.serviceOfferring.PrivilegeCardTransactionTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;

/**
 * The Class CashBookingValidator.
 */
public class CashBookingValidator extends BookingValidator {

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.validator.BookingValidator#setBookingCommonService
	 * (com.ff.web.booking.service.BookingCommonService)
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/**
	 * Process br rules cash booking.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO processBrRulesCashBooking(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		BookingValidationTO bookingValidationTO = validateConsignment(bookingvalidateTO);
		if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingValidationTO.getBookingType())) {
			if (bookingValidationTO.getMaxDiscount() > 0)
				bookingValidationTO = validateCashDiscount(bookingValidationTO);
		}
		return bookingvalidateTO;
	}

	/**
	 * Validate cash discount.
	 * 
	 * @param bookingValidationTO
	 *            the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateCashDiscount(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException {
		List<String> errorCodes = bookingValidationTO.getErrorCodes();
		BookingTypeConfigTO bookingTypeConfig = bookingCommonService
				.getBookingTypeConfig(bookingValidationTO.getBookingType());
		if (bookingValidationTO.getMaxDiscount() > bookingTypeConfig
				.getMaxDiscountAllowed()) {
			bookingValidationTO.setIsDiscountExceeded(CommonConstants.YES);
			bookingValidationTO.setMaxDiscount(bookingTypeConfig
					.getMaxDiscountAllowed());
			errorCodes.add(BookingErrorCodesConstants.DISCOUNT_EXCEEDED);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingValidationTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.DISCOUNT_EXCEEDED);
		}

		return bookingValidationTO;
	}

	/**
	 * Validate privilege card.
	 * 
	 * @param bookingValidationTO
	 *            the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validatePrivilegeCard(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException {
		PrivilegeCardTO privilegeCardTO = null;
		Double availableBal = 0.00;
		List<String> errorCodes = bookingValidationTO.getErrorCodes();
		List<PrivilegeCardTransactionTO> privgCardTrasnDtls = bookingCommonService
				.getPrivilegeCardTransDtls(bookingValidationTO
						.getPrivilegeCardNo());
		privilegeCardTO = bookingCommonService
				.getPrivilegeCardDtls(bookingValidationTO.getPrivilegeCardNo());
		if (StringUtil.isNull(privilegeCardTO)) {
			bookingValidationTO.setIsValidPrivilegeCard(CommonConstants.NO);
			errorCodes.add(BookingErrorCodesConstants.INVALID_PRIVILEGE_CARD);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingValidationTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_PRIVILEGE_CARD);
		}
		if (StringUtil.isEmptyList(privgCardTrasnDtls)) {
			availableBal = privilegeCardTO.getBalance();
		} else {
			privilegeCardTO = privgCardTrasnDtls.get(0).getPrivilegeCardTO();
			for (PrivilegeCardTransactionTO privgCardTransTO : privgCardTrasnDtls) {
				availableBal = availableBal + privgCardTransTO.getAmount();
			}
			availableBal = privilegeCardTO.getBalance() - availableBal;
		}
		bookingValidationTO.setPrivilegeCardAvalBal(availableBal);
		if (privilegeCardTO != null) {
			bookingValidationTO.setPrivilegeCardId(privilegeCardTO
					.getPrivilegeCardId());
		}
		if (bookingValidationTO.getPrivilegeCardAmt() > availableBal) {
			bookingValidationTO.setIsValidPrivilegeCard(CommonConstants.NO);
			errorCodes
					.add(BookingErrorCodesConstants.PRIVILEGE_CARD_AMT_EXCEEDED);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingValidationTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.PRIVILEGE_CARD_AMT_EXCEEDED);
		}
		if (StringUtils.equalsIgnoreCase(
				BookingConstants.PRIVILEGE_CARD_STATUS_BLOCKED,
				privilegeCardTO.getStatus())) {
			bookingValidationTO.setIsValidPrivilegeCard(CommonConstants.NO);
			errorCodes
					.add(BookingErrorCodesConstants.INVALID_PRIVILEGE_CARD_BLOCKED);
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					bookingValidationTO.getIsBusinessExceptionReq()))
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_PRIVILEGE_CARD_BLOCKED);
		}
		bookingValidationTO.setErrorCodes(errorCodes);
		return bookingValidationTO;
	}
}
