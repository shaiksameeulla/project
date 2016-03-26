package com.ff.web.booking.dao;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.booking.BookingDO;

/**
 * The Class CashBookingDAOImpl.
 */
public class CashBookingDAOImpl extends CGBaseDAO implements CashBookingDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingDAOImpl.class);

	public List<BookingDO> getDiscountBookings() throws CGSystemException {

		List<BookingDO> discountBookings = null;
		try {
			String fromDateStr = DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(getDate(Calendar.MONDAY));
			String toDateStr = DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(getDate(Calendar.THURSDAY));
			Date fromDate = DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(fromDateStr);
			Date toDate = DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(toDateStr);
			String[] params = { "fromDate", "toDate" };
			Object[] values = { DateUtil.trimTimeFromDate(fromDate),
					DateUtil.appendLastHourToDate(toDate) };
			discountBookings = (List<BookingDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getDiscountBookings",
							params, values);
		} catch (ParseException e) {
			LOGGER.error("Exception Occured in::CashBookingDAOImpl::getDiscountBookings() :: "
					,e);
		}
		return discountBookings;
	}

	private Date getDate(int day) {

		Date date = null;
		GregorianCalendar fromCal = new GregorianCalendar();
		fromCal.set(Calendar.DAY_OF_WEEK, day);
		date = fromCal.getTime();
		return date;
	}
}
