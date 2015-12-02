package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.expense.MiscExpenseDO;


// TODO: Auto-generated Javadoc
/**
 * The Interface FranchiseeBookingIntlMDBDAO.
 */
public interface FranchiseeBookingIntlMDBDAO {
	
	/**
	 * Insert franchisee booking details.
	 *
	 * @param bookingList the booking list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	public boolean insertFranchiseeBookingDetails(
			List<FranchiseeBookingDO> bookingList) throws CGBusinessException;
	
	/**
	 * Save misc exp.
	 *
	 * @param miscExpenseDoList the misc expense do list
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveMiscExp(List<MiscExpenseDO> miscExpenseDoList)
	throws CGSystemException;
	
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
	 * Gets the booking item list.
	 *
	 * @param paperWorkCN the paper work cn
	 * @return the booking item list
	 * @throws CGSystemException the cG system exception
	 */
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)
	throws CGSystemException;


}
