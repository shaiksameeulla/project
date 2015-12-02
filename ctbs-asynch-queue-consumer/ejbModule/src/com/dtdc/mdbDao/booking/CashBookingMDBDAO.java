/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CashBookingDAO.
 */
public interface CashBookingMDBDAO {

	/**
	 * Save cash booking details.
	 *
	 * @param cashBookingDo the cash booking do
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveAndDeleteCashBookingDetails(List<CashBookingDO> cashBookingDo)throws CGBusinessException;
	
	/**
	 * Gets the id for express service.
	 *
	 * @return the id for express service
	 * @throws CGBusinessException the cG business exception
	 */
	public Integer getIdForExpressService() throws CGBusinessException;
	
	/**
	 * Gets the id for dox type.
	 *
	 * @return the id for dox type
	 * @throws CGBusinessException the cG business exception
	 */
	public Integer getIdForDoxType()throws CGBusinessException ;
	
	/**
	 * Gets the booking item list.
	 *
	 * @param paperWorkCN the paper work cn
	 * @return the booking item list
	 */
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN);
	
	/**
	 * Gets the cash booking enquiry details.
	 *
	 * @param consingnmentNO the consingnment no
	 * @return the cash booking enquiry details
	 * @throws CGBusinessException the cG business exception
	 */
	public List<BookingDO> getCashBookingEnquiryDetails (
			String consingnmentNO) throws CGBusinessException;

}
