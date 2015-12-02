/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centraldao.booking;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralBookingDAOImpl.
 */
@SuppressWarnings("unchecked")
public class CentralBookingDAOImpl extends HibernateDaoSupport implements
CentralBookingDAO {

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.CentralBookingDAO#isCnAlreadyBooked(java.lang.String)
	 */
	@Override
	public boolean isCnAlreadyBooked(String consignmentNumber)
	throws CGSystemException {
		String queryForConsignmentBooked = "checkIfConsignmentIsBooked";
		List<Long> result = null;
		Boolean isCNBooked = true;

		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryForConsignmentBooked, BookingConstants.CONSG_NUM,
				consignmentNumber);
		long count = result.get(0).longValue();
		isCNBooked = count > 0 ? true : false;
		return isCNBooked;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.booking.CentralBookingDAO#getCNstatus(String)
	 */
	@Override
	public String getCNstatus(String ConNo) throws CGSystemException{
		String queryForGetConDeliveryStatus = "getConDeliveryStatus";
		List<Long> result = null;
		String status = null;

		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryForGetConDeliveryStatus, BookingConstants.CONSG_NUM,
				ConNo);
		if(result != null) {
			status = result.get(0).toString();
		}
		return status;
	}
}
