/*
 * @author soagarwa
 */
package src.com.dtdc.mdbServices.booking;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.cashbooking.CashBookingTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface CashBookingService.
 */
public interface CashBookingMDBService {

	/**
	 * Save cash booking details.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveCashBookingDetails(CGBaseTO bookingTO)throws CGBusinessException;
	
	/**
	 * Save cash booking details.
	 *
	 * @param cashBookingTO the cash booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveCashBookingDetails(CashBookingTO cashBookingTO)throws CGBusinessException;

}
