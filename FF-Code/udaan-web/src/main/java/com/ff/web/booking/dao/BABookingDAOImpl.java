package com.ff.web.booking.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.web.booking.constants.BookingConstants;

/**
 * The Class BABookingDAOImpl.
 */
public class BABookingDAOImpl extends CGBaseDAO implements BABookingDAO {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BABookingDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.web.booking.dao.BABookingDAO#getBABookings(java.util.Date, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBABookings(Date bookingDate,
			Integer businessAssociateId) throws CGSystemException {
		LOGGER.debug("BABookingDAOImpl::getBABookings::START------------>:::::::");
		List<BookingDO> baBookings = null;
		Session session = null;
		session = createSession();
		try {
			baBookings = (List<BookingDO>) session
					.createCriteria(BookingDO.class)
					.createAlias("bookingType", "bookType")
					.createAlias("customerId", "customer")
					.add(Restrictions.between("bookingDate",
							DateUtil.trimTimeFromDate(bookingDate),
							DateUtil.appendLastHourToDate(bookingDate)))
					.add(Restrictions.eq("customer.customerId",
							businessAssociateId))
					.add(Restrictions.eq("bookType.bookingType",
							BookingConstants.BA_BOOKING)).list();
		} catch (Exception e) {
			LOGGER.error("Error occured in BABookingDAOImpl :: getBABookings()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BABookingDAOImpl::getBABookings::END------------>:::::::");
		return baBookings;
	}
}
