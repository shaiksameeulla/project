package com.ff.web.booking.validator;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingValidationTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;

/**
 * The Class BABookingValidator.
 */
public class BABookingValidator extends BookingValidator {
	
	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/* (non-Javadoc)
	 * @see com.ff.web.booking.validator.BookingValidator#getBookingCommonService()
	 */
	public BookingCommonService getBookingCommonService() {
		return bookingCommonService;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.booking.validator.BookingValidator#setBookingCommonService(com.ff.web.booking.service.BookingCommonService)
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/**
	 * Process br rules ba booking.
	 *
	 * @param bookingvalidateTO the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public BookingValidationTO processBrRulesBABooking(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		 validateConsignment(bookingvalidateTO);
		 return bookingvalidateTO;
	}

	/**
	 * Validate insured dtls.
	 *
	 * @param insuranceConfigTO the insurance config to
	 * @return the insurance config to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InsuranceConfigTO validateInsuredDtls(
			InsuranceConfigTO insuranceConfigTO) throws CGBusinessException,
			CGSystemException {
		BookingTypeConfigTO bookingTypeConfig = bookingCommonService
				.getBookingTypeConfig(BookingConstants.BA_BOOKING);
		if (!StringUtil.isNull(bookingTypeConfig)) {
			if (insuranceConfigTO.getDeclaredValue() > bookingTypeConfig
					.getMaxDeclaredValAllowed())
				insuranceConfigTO
						.setIsDeclaredValuesExceeded(CommonConstants.YES);
			throw new CGBusinessException(
					BookingErrorCodesConstants.DECLARED_VALUE_EXCEEDED);
		}

		return insuranceConfigTO;
	}
}
