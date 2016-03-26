package com.ff.web.pickup.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.pickup.PickupRunsheetTO;


/**
 * The Interface UpdateRunsheetService.
 */
public interface UpdateRunsheetService {
	// Update Pickup Run sheet
	/**
	 * Update pickup runsheet.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public PickupRunsheetTO updatePickupRunsheet(PickupRunsheetTO pickupRunsheetTO)
			throws CGBusinessException, CGSystemException;

	public List<PickupRunsheetTO> getPickupRunsheetDetails(String runsheetNo)
			throws CGBusinessException, CGSystemException;

	public List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException,
			CGSystemException;

	public BookingValidationTO validateConsignment(
			BookingValidationTO cnValidationTO, String pickupRunsheetNo,
			String consgNumber, Integer quantity) throws CGBusinessException,
			CGSystemException;
}
