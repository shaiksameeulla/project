/*
 * @author soagarwa
 */
package src.com.dtdc.mdbServices.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.franchisebooking.FranchiseeBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface FranchiseeBookingService.
 */
/**
 * @author nkollaba
 * 
 */

public interface FranchiseeBookingMDBService {

	/**
	 * Save fr booking details.
	 *
	 * @param franchiseeBookingTO the franchisee booking to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveFRBookingDetails(CGBaseTO franchiseeBookingTO) throws CGSystemException, CGBusinessException;

	/**
	 * Save cn booking details.
	 *
	 * @param bookingDtlsTOList the booking dtls to list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveFRBookingDetails(
			List<FranchiseeBookingTO> bookingDtlsTOList) throws CGSystemException ,CGBusinessException;

}
