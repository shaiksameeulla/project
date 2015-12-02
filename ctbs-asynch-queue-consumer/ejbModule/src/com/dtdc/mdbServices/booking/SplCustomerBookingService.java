package src.com.dtdc.mdbServices.booking;

/*
 * @author rajmanda
 */

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.booking.specialcustomer.SplCustomerBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface SplCustomerBookingService.
 */
/**
 * @author nkollaba
 *
 */

public interface SplCustomerBookingService {



	
	/**
	 * save spl custome profile.
	 *
	 * @param bookingTO the booking to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveSplCustBookingDetails(CGBaseTO bookingTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save spl cust booking details.
	 *
	 * @param splCustomerBookingTOList the spl customer booking to list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	boolean saveSplCustBookingDetails(
			List<SplCustomerBookingTO> splCustomerBookingTOList)
			throws CGSystemException, CGBusinessException;

	
	

}
