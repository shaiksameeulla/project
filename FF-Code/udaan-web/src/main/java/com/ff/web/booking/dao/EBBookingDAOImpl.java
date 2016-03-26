package com.ff.web.booking.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.service.EmotionalBondBookingServiceImpl;

/**
 * The Class EBBookingDAOImpl.
 */
public class EBBookingDAOImpl extends CGBaseDAO implements EBBookingDAO {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EBBookingDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.dao.EBBookingDAO#getEBBookings(java.util.Date,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getEBBookings(Date bookingDate, String bookingType,
			Integer loginOffceId) throws CGSystemException {
		LOGGER.debug("EBBookingDAOImpl::getEBBookings::START------------>:::::::");
		// TODO Auto-generated method stub
		List<BookingDO> bookingDOs = null;
		Session session = null;
		session = createSession();
		try {
			bookingDOs = (List<BookingDO>) session
					.createCriteria(BookingDO.class)
					.createAlias("bookingType", "bookType")
					.add(Restrictions.between("deliveryDate",
							DateUtil.trimTimeFromDate(bookingDate),
							DateUtil.appendLastHourToDate(bookingDate)))
					.add(Restrictions.eq("bookType.bookingType",
							BookingConstants.EMOTIONAL_BOND_BOOKING))
					// .add(Restrictions.eq("bookingOfficeId", loginOffceId))
					.add(Restrictions.eq("status",
							BookingConstants.BOOKING_NORMAL_PROCESS)).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EBBookingDAOImpl::getEBBookings() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("EBBookingDAOImpl::getEBBookings::END------------>:::::::");
		return bookingDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.dao.EBBookingDAO#getBookingPrefDetails()
	 */
	@Override
	public List<BookingPreferenceDetailsDO> getBookingPrefDetails(Integer stateId)
			throws CGSystemException {
		LOGGER.debug("EBBookingDAOImpl::getBookingPrefDetails::START------------>:::::::");
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		try {
			
			String[] paramNames = { BookingConstants.STATE_ID,
					BookingConstants.CURRENT_DATE};
			Object[] values = {stateId, DateUtil.stringToDDMMYYYYFormat(DateUtil
					.getCurrentDateInYYYYMMDDHHMM())};
			bookingPrefDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(BookingConstants.QRY_GET_BOOKING_PREF_DETAILS,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EBBookingDAOImpl::getBookingPrefDetails() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("EBBookingDAOImpl::getBookingPrefDetails::END------------>:::::::");
		return bookingPrefDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.EBBookingDAO#updateEBBookingsDtls(java.util.List)
	 */
	@Override
	public Boolean updateEBBookingsDtls(List<BookingDO> bookingDOs)
			throws CGSystemException {
		LOGGER.debug("EBBookingDAOImpl::updateEBBookingsDtls::START------------>:::::::");
		Session session = null;
		Transaction tx = null;
		boolean isUpdated = Boolean.FALSE;
		int updatedRows = 0;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query query = session
					.getNamedQuery(BookingConstants.UPDATE_BOOKING_STATUS);
			for (BookingDO booking : bookingDOs) {
				query.setInteger("bookingId", booking.getBookingId());
				query.setString("status", booking.getStatus());
				updatedRows = query.executeUpdate();
			}
			if (updatedRows > 0) {
				isUpdated = Boolean.TRUE;
			}
			tx.commit();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EBBookingDAOImpl::updateEBBookingsDtls() :: "
					+ e.getMessage());
			if (tx != null)
				tx.rollback();
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("EBBookingDAOImpl::updateEBBookingsDtls::END------------>:::::::");
		return isUpdated;

	}

	public List<String> getBookingPrefCodes(List<Integer> preferenceIds)
			throws CGSystemException {
		LOGGER.debug("EBBookingDAOImpl::getBookingPrefCodes::START------------>:::::::");
		List<String> bookingPrefCodes = null;
		try {
			bookingPrefCodes = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getAllPreferenceCodes",
							"preferenceIds", preferenceIds);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EBBookingDAOImpl::getBookingPrefDetails() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("EBBookingDAOImpl::getBookingPrefDetails::END------------>:::::::");
		return bookingPrefCodes;
	}

}
