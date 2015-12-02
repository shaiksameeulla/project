package src.com.dtdc.mdbServices.booking;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.to.internationalbooking.directpartybooking.DirectPartyBookingIntlTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface DirectPartyBookingIntlMDBService.
 */
public interface DirectPartyBookingIntlMDBService {
	
	/**
	 * Save dp booking details.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	boolean saveDPBookingDetails(DirectPartyBookingIntlTO bookingTO)throws CGSystemException ,CGBusinessException;

}
