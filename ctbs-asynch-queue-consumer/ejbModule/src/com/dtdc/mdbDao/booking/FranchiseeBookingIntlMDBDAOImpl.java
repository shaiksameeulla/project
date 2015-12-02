package src.com.dtdc.mdbDao.booking;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.expense.MiscExpenseDO;


// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeBookingIntlMDBDAOImpl.
 */
public class FranchiseeBookingIntlMDBDAOImpl extends HibernateDaoSupport implements FranchiseeBookingIntlMDBDAO{
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO#insertFranchiseeBookingDetails(List)
	 */
	@Override
	public boolean insertFranchiseeBookingDetails(
			List<FranchiseeBookingDO> bookingList) throws CGBusinessException {
		Session session = null;
		boolean bookingStatus = false;
		// org.hibernate.Transaction trn = null;
		try {

			session = getSessionFactory().openSession();
			// trn = session.beginTransaction();
			Iterator<FranchiseeBookingDO> itr = bookingList.iterator();
			while (itr.hasNext()) {
				FranchiseeBookingDO bookingDO = itr.next();
				session.saveOrUpdate(bookingDO);
				session.flush();
			}
			bookingStatus = true;
			// trn.commit();
		} catch (Exception obj) {
			logger.error("FranchiseeBookingIntlMDBDAOImpl::insertFranchiseeBookingDetails::Exception occured:"
					+obj.getMessage());
			// return false;
			throw new CGBusinessException(obj);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return bookingStatus;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO#saveMiscExp(List)
	 */
	@Override
	public String saveMiscExp(List<MiscExpenseDO> miscExpenseDoList)
			throws CGSystemException {
		String message = "FAILURE";
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			if (miscExpenseDoList != null) {
				hibernateTemplate.saveOrUpdateAll(miscExpenseDoList);
				message = "SUCCESS";
			}
		} catch (Exception ex) {
			logger.error("FranchiseeBookingIntlMDBDAOImpl::saveMiscExp::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return message;
	}

	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO#getIdForExpressService()
	 */
	@Override
	public Integer getIdForExpressService() throws CGSystemException {
		String query = "getIdForExpressService";
		List<Integer> ids = null;
		Integer serviceId = null;
		try {
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,
					BookingConstants.SERVICE_CODE,
					BookingConstants.EXPRESS_SERVICE_CODE);
			serviceId = (ids != null) ? ids.get(0) : null;
		} catch (Exception e) {
			logger.error("FranchiseeBookingIntlMDBDAOImpl::getIdForExpressService::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return serviceId;

	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO#getIdForDoxType()
	 */
	@Override
	public Integer getIdForDoxType() throws CGSystemException {
		String query = "getIdForDoxType";
		List<Integer> ids = null;
		Integer doxId = null;
		try {
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,
					BookingConstants.DOX_TYPE, BookingConstants.DOX);
			doxId = (ids != null) ? ids.get(0) : null;
		} catch (Exception e) {
			logger.error("FranchiseeBookingIntlMDBDAOImpl::getIdForDoxType occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return doxId;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO#getBookingItemList(String)
	 */
	@Override
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)
			throws CGSystemException {
		String query = "getBookingItemList";
		List<BookingItemListDO> bookingItems = null;

		try {
			bookingItems = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, "paperWorkCN",
							paperWorkCN);

		} catch (Exception e) {
			logger.error("FranchiseeBookingIntlMDBDAOImpl::getBookingItemList occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingItems;
	}
}
