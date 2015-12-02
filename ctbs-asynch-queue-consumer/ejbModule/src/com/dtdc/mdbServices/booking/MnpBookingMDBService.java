/*
 * @author soagarwa
 */
package src.com.dtdc.mdbServices.booking;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.mnpbooking.MnpBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MnpBookingService.
 */
public interface MnpBookingMDBService {

	/**
	 * Save mnp bookin details.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveMnpBookinDetails(CGBaseTO bookingTO)
			throws CGBusinessException;

	/**
	 * Book.
	 *
	 * @param to the to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveMnpBookinDetails(MnpBookingTO to)
			throws CGBusinessException;

}
