package com.ff.web.booking.service;

import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingResultTO;
import com.ff.booking.FOCBookingDoxTO;
import com.ff.booking.FOCBookingParcelTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

/**
 * The Interface FOCBookingService.
 */
public interface FOCBookingService {

	/**
	 * Save or update foc booking dox.
	 * 
	 * @param focBookingDoxTO
	 *            the foc booking dox to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO saveOrUpdateBookingDox(FOCBookingDoxTO focBookingDoxTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update foc booking parcel.
	 * 
	 * @param focBookingParcelTO
	 *            the foc booking parcel to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO saveOrUpdateBookingParcel(
			FOCBookingParcelTO focBookingParcelTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException;

}
