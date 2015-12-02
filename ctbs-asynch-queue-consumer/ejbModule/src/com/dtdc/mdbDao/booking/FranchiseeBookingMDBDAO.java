/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface FranchiseeBookingDAO.
 */
/**
 * @author nkollaba
 * 
 */

public interface FranchiseeBookingMDBDAO {

	/**
	 * Insert franchisee booking details.
	 *
	 * @param bookingList the booking list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean insertAndDeleteFranchiseeBookingDetails(
			List<FranchiseeBookingDO> bookingList) throws CGSystemException ;

	/**
	 * Gets the booking item list.
	 *
	 * @param paperWorkCN the paper work cn
	 * @return the booking item list
	 * @throws CGSystemException the cG system exception
	 */
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)throws CGSystemException ;

	/**
	 * Gets the id for express service.
	 *
	 * @return the id for express service
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getIdForExpressService() throws CGSystemException;

	/**
	 * Gets the id for dox type.
	 *
	 * @return the id for dox type
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getIdForDoxType() throws CGSystemException;
	
	/**
	 * Gets the dest branch pincode and city.
	 *
	 * @param pincode the pincode
	 * @return the dest branch pincode and city
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List getDestBranchPincodeAndCity(String pincode) throws CGSystemException,CGBusinessException;
	
	/**
	 * Gets the franchisee booking details.
	 *
	 * @param consignmentNumber the consignment number
	 * @return the franchisee booking details
	 * @throws CGSystemException the cG system exception
	 */
	public List<FranchiseeBookingDO> getFranchiseeBookingDetails(String consignmentNumber
			)throws CGSystemException;
	
	/**
	 * Insert in booking duplicate table.
	 *
	 * @param duplicateBookingDo the duplicate booking do
	 * @throws CGSystemException the cG system exception
	 */
	public void insertInBookingDuplicateTable(BookingDuplicateDO duplicateBookingDo)throws CGSystemException ;

}
