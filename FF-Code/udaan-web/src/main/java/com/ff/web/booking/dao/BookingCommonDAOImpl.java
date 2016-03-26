package com.ff.web.booking.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class BookingCommonDAOImpl.
 * 
 * @author nkattung
 */
public class BookingCommonDAOImpl extends CGBaseDAO implements BookingCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingCommonDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#savePickupBooking(java.util.List)
	 */
	public List<Integer> savePickupBooking(List<BookingDO> bookings)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::savePickupBooking::START------------>:::::::");
		List<Integer> successBookingIds = new ArrayList(bookings.size());
		Session session = null;
		try {
			session = openTransactionalSession();
			for (BookingDO booking : bookings) {
				session.saveOrUpdate(booking);
				successBookingIds.add(booking.getBookingId());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in :: BookingCommonDAOImpl :: savePickupBooking()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			closeTransactionalSession(session);
		}
		LOGGER.debug("BookingCommonDAOImpl::savePickupBooking::END------------>:::::::");
		return successBookingIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#saveOrUpdateBooking(java.util
	 * .List)
	 */
	public List<Integer> saveOrUpdateBooking(List<BookingDO> bookings)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBooking::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		Session session = null;
		Transaction tx = null;
		List<Integer> successBookingIds = new ArrayList(bookings.size());
		boolean is2wayWriteEnabled = TwoWayWriteProcessCall
				.isTwoWayWriteEnabled();
		
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			for (BookingDO booking : bookings) {
				BookingUtils.validateMandatoryData(booking);
				if (is2wayWriteEnabled) {
					booking.setDtToCentral(CommonConstants.YES);
				} else {
					booking.setDtToCentral(CommonConstants.NO);
				}
				
				session.merge(booking);
				successBookingIds.add(booking.getBookingId());
			}
			tx.commit();
			// isBookingAdded = Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in :: BookingCommonDAOImpl :: saveOrUpdateBooking()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			session.close();
		}
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBooking::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return successBookingIds;
	}

	public Boolean createBooking(BookingDO booking) throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::createBooking::START------------>:::::::");
		boolean isBookingAdded = Boolean.FALSE;
		boolean is2wayWriteEnabled = TwoWayWriteProcessCall
				.isTwoWayWriteEnabled();
		try {
			BookingUtils.validateMandatoryData(booking);
			// booking type=5 for bulk booking
			if (is2wayWriteEnabled
					&& booking.getBookingType().getBookingTypeId() != 5) {
				booking.setDtToCentral(CommonConstants.YES);
			} else {
				booking.setDtToCentral(CommonConstants.NO);
			}
			
			getHibernateTemplate().merge(booking);
			isBookingAdded = Boolean.TRUE;
		} catch (Exception e) {
			isBookingAdded = Boolean.FALSE;
			LOGGER.error("Error occured in :: BookingCommonDAOImpl :: createBooking()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::createBooking::END------------>:::::::");
		return isBookingAdded;
	}

	public List<BookingDO> saveOrUpdateBookingList(List<BookingDO> bookings)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBookingList::START------------>:::::::");
		List<BookingDO> bookingList = new ArrayList<BookingDO>();
		boolean is2wayWriteEnabled = TwoWayWriteProcessCall
				.isTwoWayWriteEnabled();
		try {
			for (BookingDO booking : bookings) {
				if (is2wayWriteEnabled) {
					booking.setDtToCentral(CommonConstants.YES);
				} else {
					booking.setDtToCentral(CommonConstants.NO);
				}
				BookingUtils.validateMandatoryData(booking);
				getHibernateTemplate().saveOrUpdate(booking);
				bookingList.add(booking);
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: saveOrUpdateBookingList()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBookingList::END------------>:::::::");
		return bookingList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#saveOrUpdatePrivilegeCardTransDtls
	 * (com.ff.domain.serviceOffering.PrivilegeCardTransactionDO)
	 */
	public boolean saveOrUpdatePrivilegeCardTransDtls(
			PrivilegeCardTransactionDO privgCardTransDtls)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBookingList::START------------>:::::::");
		boolean isPrivgCardTransAdded = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(privgCardTransDtls);
			isPrivgCardTransAdded = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: saveOrUpdatePrivilegeCardTransDtls()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::saveOrUpdateBookingList::END------------>:::::::");
		return isPrivgCardTransAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#updateBooking(com.ff.domain.booking
	 * .BookingDO)
	 */
	public Integer updateBooking(BookingDO bookingDO) throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::updateBooking::START------------>:::::::");
		// String isUpdated = "N";
		Integer bookingId = null;
		boolean is2wayWriteEnabled = TwoWayWriteProcessCall
				.isTwoWayWriteEnabled();
		try {
			BookingUtils.validateMandatoryData(bookingDO);
			if (is2wayWriteEnabled) {
				bookingDO.setDtToCentral(CommonConstants.YES);
				bookingDO.setDtUpdateToCentral(CommonConstants.YES);
			} else {
				bookingDO.setDtToCentral(CommonConstants.NO);
			}
			
			getHibernateTemplate().update(bookingDO);
			// isUpdated = "Y";
			bookingId = bookingDO.getBookingId();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: updateBooking()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::updateBooking::END------------>:::::::");
		return bookingId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#getBookingType(java.lang.String)
	 */
	public BookingTypeDO getBookingType(String bookingType)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::getBookingType::START------------>:::::::");
		BookingTypeDO bookingTypeDO = null;
		Session session = null;
		session = createSession();
		try {
			bookingTypeDO = (BookingTypeDO) session
					.createCriteria(BookingTypeDO.class)
					.add(Restrictions.eq(BookingConstants.BOOKING_TYPE,
							bookingType)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: getBookingType()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BookingCommonDAOImpl::getBookingType::END------------>:::::::");
		return bookingTypeDO;
	}

	/**
	 * Checks if is consg booked.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @return : boolean
	 * @throws CGBaseException
	 *             the cG base exception
	 * @Method : isConsgBooked
	 * @Desc : Check for whether consignment is booked not
	 */
	@Override
	public boolean isConsgBooked(String consgNumber) throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::START------------>:::::::");
		long count = 0;
		boolean isCNBooked = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_IS_CN_BOOKED,
							BookingConstants.QRY_PARAM_CONSG, consgNumber).get(
							0);
			if (count > 0)
				isCNBooked = Boolean.TRUE;
			else {
				count = (Long) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								BookingConstants.QRY_IS_CHILD_CN_BOOKED,
								BookingConstants.QRY_PARAM_CONSG, consgNumber)
						.get(0);
				if (count > 0)
					isCNBooked = Boolean.TRUE;
				else {
					count = (Long) getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
									BookingConstants.QRY_IS_CONSG_BOOKED,
									BookingConstants.QRY_PARAM_CONSG,
									consgNumber).get(0);
				}
				if (count > 0)
					isCNBooked = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isConsgBooked()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::END------------>:::::::");
		return isCNBooked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#getBookingTypeConfig(java.lang
	 * .String)
	 */
	@SuppressWarnings("unchecked")
	public BookingTypeConfigDO getBookingTypeConfig(String bookingType)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::getBookingTypeConfig::START------------>:::::::");
		BookingTypeConfigDO bookingTypeConfigDO = null;
		List<BookingTypeConfigDO> bookingTypeConfigDOs = null;
		try {
			bookingTypeConfigDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_GET_BOOKING_TYPE_CONFIGS,
							BookingConstants.BOOKING_TYPE, bookingType);
			bookingTypeConfigDO = !StringUtil.isEmptyList(bookingTypeConfigDOs) ? bookingTypeConfigDOs
					.get(0) : null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::getBookingTypeConfig :: ",
					e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::getBookingTypeConfig::END------------>:::::::");
		return bookingTypeConfigDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#getBookingByProcess(java.lang
	 * .String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public BookingDO getBookingByProcess(String consgNumber, String processCode)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::getBookingByProcess::START------------>:::::::");
		BookingDO booking = null;
		List<BookingDO> bookings = null;
		try {
			String[] params = { BookingConstants.QRY_PARAM_CONSG,
					BookingConstants.PROCESS_CODE };
			Object[] values = { consgNumber, processCode };
			bookings = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_GET_BOOKING_BY_PROCESS,
							params, values);
			booking = !StringUtil.isEmptyList(bookings) ? bookings.get(0)
					: null;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::getBookingByProcess :: ",
					e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::getBookingByProcess::END------------>:::::::");
		return booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#getBookingDtls(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getBookingDtls(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::getBookingDtls::START------------>:::::::");
		List<Object[]> bookingDtls = null;
		try {
			String[] params = { BookingConstants.QRY_PARAM_CONSG };
			Object[] values = { consgNumber };
			bookingDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(
					BookingConstants.QRY_GET_PICKUP_BOOKING_DTLS, params,
					values);
		} catch (Exception e) {
			LOGGER.error("ERROR : BookingCommonDAOImpl::getBookingDtls :: ",
					e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::getBookingDtls::END------------>:::::::");
		return bookingDtls;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getBookingDtlsByStatus(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::getBookingDtlsByStatus::START------------>:::::::");
		List<Object[]> bookingDtls = null;
		try {
			String[] params = { BookingConstants.QRY_PARAM_CONSG };
			Object[] values = { consgNumber };
			bookingDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getPickupBookingDtlsByStatus", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::getBookingDtlsByStatus :: ",
					e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::getBookingDtlsByStatus::END------------>:::::::");
		return bookingDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#updateBookingCNStatus(java.util
	 * .List, java.lang.String)
	 */
	public boolean updateBookingCNStatus(List<String> cnNumbers, String cnStatus)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::updateBookingCNStatus::START------------>:::::::");
		boolean isUpdated = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(BookingConstants.QRY_UPDATE_BOOKING_CN_STATUS);
			query.setString(BookingConstants.STATUS, cnStatus);
			query.setParameterList(BookingConstants.QRY_PARAM_CONSG, cnNumbers);
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::updateBookingStatus :: ",
					e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BookingCommonDAOImpl::updateBookingCNStatus::START------------>:::::::");
		return isUpdated;

	}

	public BookingWrapperDO batchBookingUpdate(BookingWrapperDO bookingWrapperDO)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::batchBookingUpdate::START------------>:::::::");
		Session session = null;
		Transaction tx = null;
		try {
			if (CGCollectionUtils.isEmpty(bookingWrapperDO
					.getSucessConsignments())
					&& CGCollectionUtils.isEmpty(bookingWrapperDO
							.getConsingments())
					&& bookingWrapperDO.getSucessConsignments().size() != bookingWrapperDO
							.getConsingments().size()) {
				throw new CGSystemException(new Exception(
						"Invalid consignments"));
			}
			//session = getHibernateTemplate().getSessionFactory().openSession();
			//tx = session.beginTransaction();
			LOGGER.debug("BookingCommonDAOImpl::batchBookingUpdate::calling batch save for booking with size------------>:::::::" + bookingWrapperDO.getSucessConsignments().size());
			getHibernateTemplate().saveOrUpdateAll(
					bookingWrapperDO.getSucessConsignments());
			LOGGER.debug("BookingCommonDAOImpl::batchBookingUpdate::calling batch save for consignment with size------------>:::::::" + bookingWrapperDO.getSucessConsignments().size());
			getHibernateTemplate().saveOrUpdateAll(
					bookingWrapperDO.getConsingments());
			bookingWrapperDO.setBulkSave(Boolean.TRUE);
			//tx.commit();
		} catch (Exception e) {
			bookingWrapperDO.setBulkSave(Boolean.FALSE);
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::batchBookingUpdate :: fails due to: ", e);
			//tx.rollback();
			throw new CGSystemException(e);
		} finally {
			if (session != null)
				session.close();
		}
		LOGGER.debug("BookingCommonDAOImpl::batchBookingUpdate::END------------>:::::::");
		return bookingWrapperDO;
	}

	public Integer[] createBookingAndConsignment(BookingDO booking,
			ConsignmentDO consignment) throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::saveBookingAndConsignment::START------------>:::::::");
		// boolean isBookingAdded = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		Integer[] bookingAndCnId = null;
		try {
			if (StringUtil.isNull(booking) || StringUtil.isNull(consignment))
				throw new CGSystemException(new Exception(
						"Invalid consignments"));
			BookingUtils.validateMandatoryData(booking);
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			boolean is2wayWriteEnabled = TwoWayWriteProcessCall
					.isTwoWayWriteEnabled();
			if (is2wayWriteEnabled) {
				booking.setDtToCentral(CommonConstants.YES);
				consignment.setDtToCentral(CommonConstants.YES);
			} else {
				booking.setDtToCentral(CommonConstants.NO);
				consignment.setDtToCentral(CommonConstants.NO);
			}
			
			session.saveOrUpdate(booking);
			LOGGER.debug("BookingCommonDAOImpl::createBookingAndConsignment::booking saved successfully------------>:::::::"
					+ booking.getConsgNumber());
			session.saveOrUpdate(consignment);
			LOGGER.debug("BookingCommonDAOImpl::createBookingAndConsignment::consignment saved successfully------------>:::::::"
					+ consignment.getConsgNo());
			bookingAndCnId = new Integer[2];
			bookingAndCnId[0] = booking.getBookingId();
			bookingAndCnId[1] = consignment.getConsgId();
			// isBookingAdded = Boolean.TRUE;
			tx.commit();
		} catch (Exception e) {
			LOGGER.error("BookingCommonDAOImpl::createBookingAndConsignment::booking/consignment save fails------------>:::::::"
					+ consignment.getConsgNo());
			// isBookingAdded = Boolean.FALSE;
			LOGGER.error(
					"Error occured in :: BookingCommonDAOImpl :: createBookingAndConsignment()..:",
					e);
			tx.rollback();
			throw new CGSystemException(e);
		} finally {
			if (session != null)
				session.close();
		}
		LOGGER.debug("BookingCommonDAOImpl::saveBookingAndConsignment::END------------>:::::::");
		return bookingAndCnId;
	}

	public boolean deleteConsignments(List<String> cnNumbers)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::deleteConsignments::START------------>:::::::");
		boolean isdeleted = Boolean.FALSE;
		Session session = null;
		try {
			session = openTransactionalSession();
			Query query = session.getNamedQuery("deletePickedupBookingCNs");
			query.setParameterList(BookingConstants.QRY_PARAM_CONSG, cnNumbers);
			query.executeUpdate();
			isdeleted = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BookingCommonDAOImpl::deleteConsignments :: ",
					e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("BookingCommonDAOImpl::deleteConsignments::END------------>:::::::");
		return isdeleted;
	}

	public List<BookingTypeDO> getAllBookingType() throws CGSystemException {
		List<BookingTypeDO> bookingTypeDOs = null;
		Session session = null;
		try {
			session = createSession();
			bookingTypeDOs = (List<BookingTypeDO>) session.createCriteria(
					BookingTypeDO.class).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : BookingCommonDAOImpl.getAllBookingType", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return bookingTypeDOs;
	}

	@Override
	public void batchBookingSaveUpdate(List<BookingDO> bookings)
			throws CGSystemException {
		getHibernateTemplate().saveOrUpdateAll(bookings);
	}

	@Override
	public boolean isChildConsgBooked(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::START------------>:::::::");
		long count = 0;
		boolean isCNBooked = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_IS_CHILD_CN_BOOKED,
							BookingConstants.QRY_PARAM_CONSG, consgNumber).get(
							0);
			if (count > 0)
				isCNBooked = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isConsgBooked()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::END------------>:::::::");
		return isCNBooked;
	}

	@Override
	public boolean isConsgBookedForPickup(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::START------------>:::::::");
		long count = 0;
		boolean isCNBooked = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_IS_CONSG_BOOKED,
							BookingConstants.QRY_PARAM_CONSG, consgNumber).get(
							0);
			if (count > 0)
				isCNBooked = Boolean.TRUE;
			else {
				count = (Long) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								BookingConstants.QRY_IS_CHILD_CN_BOOKED,
								BookingConstants.QRY_PARAM_CONSG, consgNumber)
						.get(0);
			}
			if (count > 0)
				isCNBooked = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isConsgBooked()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::END------------>:::::::");
		return isCNBooked;
	}

	@Override
	public boolean isConsgBookedAsChildCn(String consgNumber)
			throws CGSystemException {
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::START------------>:::::::");
		long count = 0;
		boolean isCNBooked = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							BookingConstants.QRY_IS_CHILD_CN_BOOKED,
							BookingConstants.QRY_PARAM_CONSG, consgNumber).get(0);
			if (count > 0)
				isCNBooked = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingCommonDAOImpl :: isConsgBooked()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BookingCommonDAOImpl::isConsgBooked::END------------>:::::::");
		return isCNBooked;
	}
	
}
