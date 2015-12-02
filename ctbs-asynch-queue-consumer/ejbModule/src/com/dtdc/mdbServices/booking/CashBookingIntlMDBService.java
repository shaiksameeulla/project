package src.com.dtdc.mdbServices.booking;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.to.internationalbooking.cashbooking.CashBookingInternationalTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface CashBookingIntlMDBService.
 */
public interface CashBookingIntlMDBService {
	
	/**
	 * Save cash booking details.
	 *
	 * @param cashBookingIntlTO the cash booking intl to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveCashBookingDetails(CashBookingInternationalTO cashBookingIntlTO) throws CGBusinessException;

}
