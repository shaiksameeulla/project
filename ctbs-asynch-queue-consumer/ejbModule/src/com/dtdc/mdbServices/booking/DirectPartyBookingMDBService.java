/*
 * @author soagarwa
 */
package src.com.dtdc.mdbServices.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.dpbooking.DirectPartyBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DirectPartyBookingService.
 */
public interface DirectPartyBookingMDBService {

	/**
	 * Save dp booking details.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveDPBookingDetails(CGBaseTO bookingTO)throws CGBusinessException;

	/**
	 * Save dp booking details.
	 *
	 * @param bookingTOList the booking to list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	boolean saveDPBookingDetails(List<DirectPartyBookingTO> bookingTOList)throws CGBusinessException;


}
