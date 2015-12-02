package src.com.dtdc.mdbServices.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.BookingDBSyncTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BookingMDBService.
 */
public interface BookingMDBService {

	/**
	 * Save or update bookings.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveOrUpdateBookings(CGBaseTO bookingTO)
			throws CGBusinessException;

	/**
	 * Save or update bookings.
	 *
	 * @param cashBookingTO the cash booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveOrUpdateBookings(List<BookingDBSyncTO> cashBookingTO)
			throws CGBusinessException;

}
