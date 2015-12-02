package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;


// TODO: Auto-generated Javadoc
/**
 * The Interface CashBookingIntlMDBDAO.
 */
public interface CashBookingIntlMDBDAO {
	
	/**
	 * Save cash booking details.
	 *
	 * @param cashBookingIntlDoList the cash booking intl do list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveCashBookingDetails(List<CashBookingDO> cashBookingIntlDoList) throws CGBusinessException;

}
