/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.master.customer.CustomerDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface DirectPartyBookingDAO.
 */
/**
 * @author nkollaba
 *
 */
public interface DirectPartyBookingMDBDAO {

	/**
	 * Insert franchisee booking details.
	 *
	 * @param bookingList the booking list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveOrUpdateDPBookings(
			List<DirectPartyBookingDO> bookingList) throws CGBusinessException;
	
	
	
	/**
	 * Gets the booking item list.
	 *
	 * @param paperWorkCN the paper work cn
	 * @return the booking item list
	 */
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN);
	
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
	public Integer getIdForDoxType() throws CGBusinessException;
	
	/**
	 * Gets the customer details.
	 *
	 * @param customerCode the customer code
	 * @return the customer details
	 * @throws CGBusinessException the cG business exception
	 */
	CustomerDO getCustomerDetails(String customerCode) throws CGBusinessException;
	
	/**
	 * Gets the dP booking details.
	 *
	 * @param cnNo the cn no
	 * @return the dP booking details
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DirectPartyBookingDO> getDPBookingDetails(String cnNo) throws CGBusinessException;
	
	/**
	 * Insert in booking duplicate table.
	 *
	 * @param duplicateBookingDo the duplicate booking do
	 * @throws CGSystemException the cG system exception
	 */
	public void insertInBookingDuplicateTable(BookingDuplicateDO duplicateBookingDo)throws CGSystemException ;

}
