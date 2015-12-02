/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.master.customer.CustomerDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface MnpBookingDAO.
 */
public interface MnpBookingMDBDAO {

	/**
	 * Save mnp bookin details.
	 *
	 * @param mnpBookingDOList the mnp booking do list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean saveAndDeleteMnpBookinDetails(List<BookingDO> mnpBookingDOList)
	throws CGBusinessException;
	
	/**
	 * Gets the booking item list.
	 *
	 * @param paperWorkCN the paper work cn
	 * @return the booking item list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)
	throws CGBusinessException;
	
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
	public CustomerDO getCustomerDetails(String customerCode) throws CGBusinessException;

	/**
	 * Gets the mnp booking enquiry details.
	 *
	 * @param consignmentNo the consignment no
	 * @return the mnp booking enquiry details
	 * @throws CGBusinessException the cG business exception
	 */
	public List<BookingDO> getMnpBookingEnquiryDetails(String consignmentNo)throws CGBusinessException;

}
