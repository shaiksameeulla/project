package src.com.dtdc.mdbServices.booking;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.to.internationalbooking.franchiseebooking.FranchiseeBookingIntlTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface FranchiseeBookingIntlMDBService.
 */
public interface FranchiseeBookingIntlMDBService {
	
	/**
	 * Save fr booking details.
	 *
	 * @param franchiseeBookingIntlTO the franchisee booking intl to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveFRBookingDetails(
			FranchiseeBookingIntlTO franchiseeBookingIntlTO)
			throws CGSystemException, CGBusinessException;


}
