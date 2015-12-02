/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.EmailUtility;
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.master.customer.CustomerDO;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectPartyBookingDAOImpl.
 */
@SuppressWarnings("unchecked")
public class DirectPartyBookingMDBDAOImpl extends HibernateDaoSupport implements
		DirectPartyBookingMDBDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.dpbooking.DirectPartyBookingDAO#
	 * insertFranchiseeBookingDetails(java.util.List)
	 */
	@Override
	public boolean saveOrUpdateDPBookings(
			List<DirectPartyBookingDO> bookingList) throws CGBusinessException{
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder errorMessages=new StringBuilder();
		StringBuilder errorCNNumbers=new StringBuilder();
		boolean bookingStatus = false;		
		try {
			Iterator<DirectPartyBookingDO> itr = bookingList.iterator();
			while (itr.hasNext()) {
				DirectPartyBookingDO bookingDO = itr.next();
				try{	
					hibernateTemplate.saveOrUpdate(bookingDO);
					hibernateTemplate.flush();
				}catch(Exception e){
					logger.error(bookingDO.getConsignmentNumber()+"#################Unable Book in Central DB.####################");
					logger.error("DirectPartyBookingMDBDAOImpl::saveOrUpdateDPBookings::Exception occured:"
							+e.getMessage());
					errorMessages.append(e.getMessage());
					errorMessages.append("\n");
					errorCNNumbers.append(bookingDO.getConsignmentNumber()+",");
	            }
			}
			bookingStatus = true;
		} catch (Exception obj) {
			EmailUtility.sendEmail(
					new String[] { "ctbsaqc@dtdc.com" },"Unable Book in Central DB",
					"Unable Book in Central DB"+
					"CNNo:"+ errorCNNumbers +
					"ErrorLogs"+errorMessages);  
			logger.error("DirectPartyBookingMDBDAOImpl::saveOrUpdateDPBookings::Exception occured:"
					+obj.getMessage());
			
			throw new CGBusinessException(obj);
		}
		return bookingStatus;
	}
	
	/**
	 * Insert in log table.
	 *
	 * @param alreadybookedlist the alreadybookedlist
	 * @throws CGSystemException the cG system exception
	 */
	private void insertInLogTable(List<DirectPartyBookingDO> alreadybookedlist)
			throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<BookingLogDO> bookingLoglist = new ArrayList<BookingLogDO>();
		try {
			Iterator<DirectPartyBookingDO> itrBooking = alreadybookedlist.iterator();
			
			while(itrBooking.hasNext()){
				BookingLogDO logDo = new BookingLogDO();
				DirectPartyBookingDO directBookingDo = itrBooking.next();
				PropertyUtils.copyProperties(logDo, directBookingDo);
				bookingLoglist.add(logDo);
			}
		
			Iterator<BookingLogDO> itr = bookingLoglist.iterator();
			while (itr.hasNext()) {
				BookingLogDO bookinglogDO = itr.next();
				hibernateTemplate.save(bookinglogDO);
			}
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::insertInLogTable::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			hibernateTemplate.flush();
		}
	}

	/**
	 * Gets the booking details formanifest.
	 *
	 * @param manifestNo the manifest no
	 * @return the booking details formanifest
	 * @throws CGSystemException the cG system exception
	 */
	private List<DirectPartyBookingDO> getBookingDetailsFormanifest(
			String manifestNo) throws CGSystemException {
		String query = "getDirectPartyDetailsForManisfestNO";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<DirectPartyBookingDO> bookingDO = null;
		try {
			bookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(query,
					BookingConstants.MANIFEST_NUM, manifestNo);
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getBookingDetailsFormanifest::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingDO;
	}
	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.booking.DirectPartyBookingMDBDAO#getBookingItemList(java.lang.String)
	 */
	@Override
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN) {
		String query = "getBookingItemList";
		List<BookingItemListDO> bookingItems = null;

		try {
			bookingItems = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, "paperWorkCN",
							paperWorkCN);

		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getBookingItemList::Exception occured:"
					+e.getMessage());
		}
		return bookingItems;
	}

	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.booking.DirectPartyBookingMDBDAO#getIdForExpressService()
	 */
	@Override
	public Integer getIdForExpressService() throws CGBusinessException {
		String query = "getIdForExpressService";
		List<Integer> ids = null;
		Integer serviceId = null;
		try {
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,
					BookingConstants.SERVICE_CODE,
					BookingConstants.EXPRESS_SERVICE_CODE);
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getIdForExpressService::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(
					"Exception occured while retrieving id for Express Service");
		}
		serviceId = (ids != null) ? ids.get(0) : null;
		return serviceId;

	}

	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.booking.DirectPartyBookingMDBDAO#getIdForDoxType()
	 */
	@Override
	public Integer getIdForDoxType() throws CGBusinessException {
		String query = "getIdForDoxType";
		List<Integer> ids = null;
		Integer doxId = null;
		try {
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,
					BookingConstants.DOX_TYPE, BookingConstants.DOX);
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getIdForDoxType::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(
					"Exception occured while retrieving id for DoxType DOX ");
		}
		doxId = (ids != null) ? ids.get(0) : null;
		return doxId;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.dao.booking.dpbooking.DirectPartyBookingDAO#getCustomerDetails
	 * (java.lang.String)
	 */
	@Override
	public CustomerDO getCustomerDetails(String customerCode) throws CGBusinessException{
		// TODO Auto-generated method stub
		List<CustomerDO> customerList = null;
		String queryForCustomerDetails = "getCustomerDetails";
		CustomerDO customer = null;
		try {
			customerList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryForCustomerDetails,
							BookingConstants.CUSTOMER_CODE, customerCode);
			if (customerList != null && !customerList.isEmpty()) {
				customer = customerList.get(0);
			}
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getCustomerDetails::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return customer;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.dao.booking.dpbooking.DirectPartyBookingDAO#getDPBookingDetails
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<DirectPartyBookingDO> getDPBookingDetails(String cnNo) throws CGBusinessException{
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		String query = "getDPBookingDetailsForConsignment";
		List<DirectPartyBookingDO> bookingDO = null;
		try {
			bookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(query,
					BookingConstants.CONSG_NUM, cnNo);

		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBDAOImpl::getDPBookingDetails::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return bookingDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.DirectPartyBookingMDBDAO#insertInBookingDuplicateTable(BookingDuplicateDO)
	 */
	public void insertInBookingDuplicateTable(BookingDuplicateDO duplicateBookingDo)
	throws CGSystemException {
	HibernateTemplate hibernateTemplate = getHibernateTemplate();
	try {
			hibernateTemplate.save(duplicateBookingDo);
			EmailUtility.sendEmail(
					new String[] { "ctbsaqc@dtdc.com" },"Duplicate Consignment Found",
					"Duplicate Record found with Matching Criteria"+
					"CNNo:"+ duplicateBookingDo.getConsignmentNumber() +
					"Booking Branch:"+duplicateBookingDo.getBookingBranchDetails()+  
					"Booking Date"+duplicateBookingDo.getBookingDate());
	} catch (Exception e) {
		logger.error("DirectPartyBookingMDBDAOImpl::insertInBookingDuplicateTable::Exception occured:"
				+e.getMessage());
		throw new CGSystemException(e);
	}finally{
		hibernateTemplate.flush();
	}
}

}
