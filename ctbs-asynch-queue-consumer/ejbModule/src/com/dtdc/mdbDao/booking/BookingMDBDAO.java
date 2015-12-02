package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.BookingDBSyncDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface BookingMDBDAO.
 */
public interface BookingMDBDAO {
	
	/**
	 * Save or update bookings.
	 *
	 * @param bookings the bookings
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveOrUpdateBookings(List<BookingDBSyncDO> bookings)
			throws CGBusinessException;

	/**
	 * Gets the booking id.
	 *
	 * @param cnNumber the cn number
	 * @return the booking id
	 * @throws CGBusinessException the cG business exception
	 */
	public List<Object[]> getBookingId(String cnNumber)
			throws CGBusinessException;

	/**
	 * Gets the cne address id.
	 *
	 * @param cneId the cne id
	 * @return the cne address id
	 * @throws CGBusinessException the cG business exception
	 */
	public Integer getCneAddressId(Integer cneId) throws CGBusinessException;

	/**
	 * Gets the cnr address id.
	 *
	 * @param cnrId the cnr id
	 * @return the cnr address id
	 * @throws CGBusinessException the cG business exception
	 */
	public Integer getCnrAddressId(Integer cnrId) throws CGBusinessException;

}
