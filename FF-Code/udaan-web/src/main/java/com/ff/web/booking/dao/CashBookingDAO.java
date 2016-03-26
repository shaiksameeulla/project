package com.ff.web.booking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;

/**
 * The Interface CashBookingDAO.
 */
public interface CashBookingDAO {
	public List<BookingDO> getDiscountBookings() throws CGSystemException;
}
