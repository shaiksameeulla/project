package src.com.dtdc.mdbDao.booking;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.booking.BookingDBSyncDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.master.product.VasProductChargesDO;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingMDBDAOImpl.
 */
public class BookingMDBDAOImpl extends HibernateDaoSupport implements
BookingMDBDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(BookingMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.BookingMDBDAO#saveOrUpdateBookings(List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveOrUpdateBookings(List<BookingDBSyncDO> bookings)
	throws CGBusinessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder unSavedCNs = new StringBuilder();
		try {
			LOGGER.debug("BookingMDBDAOImpl : saveOrUpdateBookings : Start..");
			if (bookings != null && bookings.size() > 0) {
				Iterator<BookingDBSyncDO> itr = bookings.iterator();
				while (itr.hasNext()) {
					BookingDBSyncDO bookingDO = itr.next();
					try {
						Set<VasProductChargesDO> vasCharges = bookingDO
						.getVasCharges();
						Set<BookingItemListDO> bkItems = bookingDO
						.getNonDoxPaperWrkItems();
						hibernateTemplate.saveOrUpdate(bookingDO);
						Integer bookingId = bookingDO.getBookingId();
						if (bookingId > 0) {
							if (vasCharges != null && vasCharges.size() > 0) {
								hibernateTemplate.saveOrUpdate(vasCharges);
							}
							if (bkItems != null && bkItems.size() > 0) {
								hibernateTemplate.saveOrUpdate(bkItems);
							}
						}
						hibernateTemplate.flush();
						unSavedCNs.append("SUCESS");
					} catch (DataAccessException obj) {
						logger.error("BookingMDBDAOImpl::saveOrUpdateBookings::Exception occured:"
								+obj.getMessage());
						unSavedCNs.append("ERROR");
						unSavedCNs.append("~");
						unSavedCNs.append(bookingDO.getConsignmentNumber());
						unSavedCNs.append("~");
						LOGGER.error(bookingDO.getConsignmentNumber()
								+ "Not saved sucessfully.");
						logger.error("BookingMDBDAOImpl::saveOrUpdateBookings::Exception occured:"
								+obj.getMessage());
					}
				}

			}
			unSavedCNs.append("SUCEESS");
			logger.debug("Bookings happend sucessfully.");
			LOGGER.debug("BookingMDBDAOImpl : saveOrUpdateBookings : End..");
		} catch (DataAccessException obj) {
			logger.error("BookingMDBDAOImpl::saveOrUpdateBookings::Exception occured:"
					+obj.getMessage());
			/*
			 * Iterator<BookingDBSyncDO> itr = (Iterator<BookingDBSyncDO>)
			 * bookings .iterator(); while (itr.hasNext()) { BookingDBSyncDO
			 * bookingDO = itr.next(); try { if (bookingDO != null &&
			 * bookingDO.getBookingId() != null && bookingDO.getBookingId() > 0)
			 * { hibernateTemplate.update(bookingDO); } else {
			 * hibernateTemplate.save(bookingDO); }
			 * logger.debug(bookingDO.getConsignmentNumber() +
			 * "Booked Successfully Saved."); } catch (Exception e) {
			 * unSavedCNs.append("ERROR"); unSavedCNs.append("~");
			 * unSavedCNs.append(bookingDO.getConsignmentNumber());
			 * unSavedCNs.append("~");
			 * LOGGER.error(bookingDO.getConsignmentNumber() +
			 * "Not saved sucessfully." + e); } }
			 */
		}
		return unSavedCNs.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.BookingMDBDAO#getBookingId(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getBookingId(String cnNumber)
	throws CGBusinessException {
		List<Object[]> results = null;
		results = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getUniqueBookingIds", "cnNo", cnNumber);
		return results;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.BookingMDBDAO#getCneAddressId(Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Integer getCneAddressId(Integer cneId) throws CGBusinessException {
		Integer cneAddId = 0;
		List<Integer> results = null;
		results = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getConsineeAddressId", "cneId", cneId);
		if (results != null && results.size() > 0) {
			cneAddId = results.get(0);
		}
		return cneAddId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.BookingMDBDAO#getCnrAddressId(Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Integer getCnrAddressId(Integer cnrId) throws CGBusinessException {
		Integer cnrAddId = 0;
		List<Integer> results = null;
		results = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getConsinorAddressId", "cnrId", cnrId);
		if (results != null && results.size() > 0) {
			cnrAddId = results.get(0);
		}
		return cnrAddId;
	}
}
