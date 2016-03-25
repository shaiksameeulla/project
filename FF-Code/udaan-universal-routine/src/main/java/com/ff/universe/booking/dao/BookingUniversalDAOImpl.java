package com.ff.universe.booking.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.universe.booking.constant.UniversalBookingConstants;

public class BookingUniversalDAOImpl extends CGBaseDAO implements
		BookingUniversalDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingUniversalDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getBookings(List<String> consgNumbers,
			String consgType) throws CGBusinessException {
		List<BookingDO> bookings = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(BookingDO.class);
			criteria.add(Restrictions.in(
					UniversalBookingConstants.QRY_PARAM_CONSG, consgNumbers));
			if (StringUtils.isNotEmpty(consgType)) {
				criteria.createAlias("consgTypeId", "consgTypeId");
				criteria.add(Restrictions.eq("consgTypeId.consignmentCode",
						consgType));
			}
			bookings = (List<BookingDO>) criteria.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookings()..:"
					+ e.getMessage());
		} finally {
			closeSession(session);
		}
		return bookings;
	}

	@Override
	public BookingValidationTO updateBookings(List<BookingDO> bookings)
			throws CGBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method : isConsgBooked
	 * @param : String consgNumber
	 * @Desc : Check for whether consignment is booked not
	 * @return : boolean
	 */
	@Override
	public BookingValidationTO isConsignmentsBooked(
			BookingValidationTO bookingValidationInputs)
			throws CGBusinessException {
		long count = 0;
		Map<String, String> consgTransStatus = new HashMap<>();
		BookingValidationTO bookValidation = new BookingValidationTO();
		try {
			for (String consgNumber : bookingValidationInputs.getConsgNumbers()) {
				count = (Long) getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								UniversalBookingConstants.QRY_IS_CN_BOOKED,
								UniversalBookingConstants.QRY_PARAM_CONSG,
								consgNumber).get(0);
				if (count > 0)
					consgTransStatus.put(consgNumber, CommonConstants.YES);
				else
					consgTransStatus.put(consgNumber, CommonConstants.NO);
			}
			bookValidation.setConsgTransStatus(consgTransStatus);
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: isConsignmentsBooked()..:"
					+ e.getMessage());
		}
		return bookValidation;
	}

	/**
	 * @Method : isConsgBooked
	 * @param : String consgNumber
	 * @Desc : Check for whether atleast one consg is booked not
	 * @return : boolean
	 */
	@Override
	public Boolean isAtleastOneConsignmentBooked(List<String> consgList)
			throws CGBusinessException {
		List<Long> result = null;
		boolean isBooked = false;
		for (String consg : consgList) {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UniversalBookingConstants.QRY_IS_CN_BOOKED,
					UniversalBookingConstants.QRY_PARAM_CONSG, consg);
			if (!CGCollectionUtils.isEmpty(result) && result.get(0) > 0) {
				isBooked = true;
				break;
			}
		}

		return isBooked;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException {
		List<String> result = null;
		try {
			result = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_GET_BOOKING_TYPE_BY_CONSG_NUMBER,
							UniversalBookingConstants.QRY_PARAM_CONSG,
							consignmentTO.getConsgNo());
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingTypeByConsgNumber()..:"
					+ e.getMessage());
		}

		return !StringUtil.isEmptyColletion(result) ? result.get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.dao.BookingCommonDAO#getBookingType(java.lang.String)
	 */
	public BookingTypeDO getBookingType(String bookingType)
			throws CGBusinessException {
		BookingTypeDO bookingTypeDO = null;
		Session session = null;
		session = createSession();
		try {
			bookingTypeDO = (BookingTypeDO) session
					.createCriteria(BookingTypeDO.class)
					.add(Restrictions.eq(
							UniversalBookingConstants.PARAM_BOOKING_TYPE,
							bookingType)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingType()..:"
					+ e.getMessage());
		} finally {
			closeSession(session);
		}
		return bookingTypeDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.dao.EBBookingDAO#getBookingPrefDetails()
	 */
	@Override
	public List<BookingPreferenceDetailsDO> getBookingPrefDetails()
			throws CGSystemException {
		// TODO Auto-generated method stub
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		try {
			bookingPrefDOs = getHibernateTemplate().findByNamedQuery(
					"getBookingPrefDetails");

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingPrefDetails()..:"
					,e);
			throw new CGSystemException(e);
		}
		return bookingPrefDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGSystemException {

		List<BookingDO> bookingDOList = null;
		BookingDO bookingDO = null;
		try {
			bookingDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getBookingDtlsByConsgNo",
							"consgNumber", consgNumber);
			if (!StringUtil.isEmptyColletion(bookingDOList)) {
				bookingDO = bookingDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingDtlsByConsgNo()..:"
					,e);
			throw new CGSystemException(e);
		}
		return bookingDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getFocBookingDetailsByDate(String currentDate,
			String previousDate) throws CGBusinessException, CGSystemException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date eDate = null;
		Date sDate = null;
		List<BookingDO> bookingDO = null;
		try {
			eDate = dateFormat.parse(currentDate);
			sDate = dateFormat.parse(previousDate);

			String[] paramNames = { UniversalBookingConstants.PARAM_START_DATE,
					UniversalBookingConstants.PARAM_END_DATE };
			Object[] values = { sDate, eDate };

			bookingDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UniversalBookingConstants.QRY_EMAIL_FOC_BOOKING_DETAILS_BY_DATE,
							paramNames, values);
			if(CGCollectionUtils.isEmpty(bookingDO)){
				LOGGER.trace("BookingUniversalDAOImpl::getFocBookingDetailsByDate()::No Data Available",currentDate);
			}

		} catch (ParseException e) {
			LOGGER.error("Error Occured in::BookingUniversalDAOImpl::getFocBookingDetailsByDate()", e);
		}
		return bookingDO;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public CNPricingDetailsDO getCnPriceByConsgID(Integer consgId)
	 * throws CGSystemException { List<CNPricingDetailsDO> cnPriceDOList = null;
	 * CNPricingDetailsDO cnPriceDO = null; try{ cnPriceDOList =
	 * getHibernateTemplate()
	 * .findByNamedQueryAndNamedParam("getCnPriceDtlsByConsgID", "consgId",
	 * consgId); if(!StringUtil.isEmptyColletion(cnPriceDOList)){ cnPriceDO =
	 * new CGSystemException(e); } return cnPriceDO; }
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getBookingDtlsByConsgNoWithDateFilter(String consgNumber)
			throws CGSystemException {

		List<BookingDO> bookingDOList = null;
		BookingDO bookingDO = null;
		try {
			bookingDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getBookingDtlsByConsgNoWithDateFilter",
							"consgNumber", consgNumber);
			if (!StringUtil.isEmptyColletion(bookingDOList)) {
				bookingDO = bookingDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingDtlsByConsgNoWithDateFilter()..:"
					,e);
			throw new CGSystemException(e);
		}
		return bookingDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Date getBookingDateByConsgNo(String consgNumber)
			throws CGSystemException {

		List<Date> bookingDoList = null;
		Date bookingDate = null;
		try {
			bookingDoList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getBookingDateByConsgNo",
							"consgNumber", consgNumber);
			if (!StringUtil.isEmptyColletion(bookingDoList)) {
				bookingDate = bookingDoList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingUniversalDAOImpl :: getBookingDtlsByConsgNoWithDateFilter()..:"
					,e);
			throw new CGSystemException(e);
		}
		return bookingDate;
	}
	
	
	
}