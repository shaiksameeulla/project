package com.ff.web.booking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.web.booking.validator.BulkReturnObject;

/**
 * The Interface BackdatedBookingService.
 */
public interface BackdatedBookingService {

	/**
	 * Gets the all regions.
	 * 
	 * @return the all regions
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all offices.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the all offices
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTO> getAllOffices(Integer regionId)
			throws CGBusinessException, CGSystemException;

	public BulkReturnObject handleBackDatedBooking(List<Object> list, BookingValidationTO cnValidation)
			throws CGBusinessException, CGSystemException;

}
