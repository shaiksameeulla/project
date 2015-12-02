package src.com.dtdc.mdbDao.booking;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.specialcustomer.SplCustomerBookingDO;

// TODO: Auto-generated Javadoc
/*
 * @author JayDutta
 */



/**
 * The Interface SplCustomerBookingDAO.
 */
public interface SplCustomerBookingDAO {
	
	/**
	 * Save special cust profile by cust.
	 *
	 * @param splCustomerBookingDOList the spl customer booking do list
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveSplCustBookingDetails(
			List<SplCustomerBookingDO> splCustomerBookingDOList)
			throws CGSystemException;
	
	
}
