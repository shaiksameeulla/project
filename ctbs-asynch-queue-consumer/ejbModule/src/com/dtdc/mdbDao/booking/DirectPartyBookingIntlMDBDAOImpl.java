package src.com.dtdc.mdbDao.booking;

import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.expense.MiscExpenseDO;


// TODO: Auto-generated Javadoc
/**
 * The Class DirectPartyBookingIntlMDBDAOImpl.
 */
public class DirectPartyBookingIntlMDBDAOImpl extends HibernateDaoSupport implements DirectPartyBookingIntlMDBDAO{
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.dpbooking.DirectPartyBookingDAO#
	 * insertFranchiseeBookingDetails(java.util.List)
	 */
	@Override
	public boolean insertDirectPartyBookingDetails(
			List<DirectPartyBookingDO> bookingList) throws CGBusinessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		boolean bookingStatus = false;
		try {
			Iterator<DirectPartyBookingDO> itr = bookingList.iterator();
			while (itr.hasNext()) {
				DirectPartyBookingDO bookingDO = itr.next();
				hibernateTemplate.save(bookingDO);
				hibernateTemplate.flush();
			}
			bookingStatus = true;
		} catch (Exception obj) {
			logger.error("DirectPartyBookingIntlMDBDAOImpl::insertDirectPartyBookingDetails::Exception occured:"
					+obj.getMessage());
			// return false;
			throw new CGBusinessException(obj);
		}

		return bookingStatus;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.DirectPartyBookingIntlMDBDAO#getIdForExpressService()
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
			logger.error("DirectPartyBookingIntlMDBDAOImpl::getIdForExpressService::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return serviceId;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.DirectPartyBookingIntlMDBDAO#getIdForDoxType()
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
			logger.error("DirectPartyBookingIntlMDBDAOImpl::getIdForDoxType::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return doxId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.franchisebooking.FranchiseeBookingDAO#
	 * getBookingItemList(java.lang.String)
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
			logger.error("DirectPartyBookingIntlMDBDAOImpl::getBookingItemList::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingItems;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.DirectPartyBookingIntlMDBDAO#saveMiscExp(List)
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
			logger.error("DirectPartyBookingIntlMDBDAOImpl::saveMiscExp::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);
		}
		return message;
	}

}
