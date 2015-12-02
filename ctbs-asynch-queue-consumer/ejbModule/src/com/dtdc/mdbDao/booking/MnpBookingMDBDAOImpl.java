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
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.master.customer.CustomerDO;

// TODO: Auto-generated Javadoc
/**
 * The Class MnpBookingDAOImpl.
 */
@SuppressWarnings("rawtypes")
public class MnpBookingMDBDAOImpl extends HibernateDaoSupport implements
		MnpBookingMDBDAO {

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.MnpBookingMDBDAO#saveAndDeleteMnpBookinDetails(List)
	 */
	@Override
	public boolean saveAndDeleteMnpBookinDetails(List<BookingDO> mnpBookingDOList)
			throws CGBusinessException {
		boolean bookingStatus = false;
		StringBuilder errorMessages=new StringBuilder();
		StringBuilder errorCNNumbers=new StringBuilder();
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		/*
		 * Delete the records for that consignment no on update after enquiry to
		 * check dublicate records
		 */
		/*String query = "delete from dtdc_ctbs_plus.dtdc_f_booking where CONSG_NUMBER ='"
				+ mnpBookingDOList.get(0).getConsignmentNumber() + "'and CONSGMNT_STATUS != 'CANCELLED'";
		Session session = getSessionFactory().openSession();
		try {
		List<BookingDO> alreadybookedlist = getBookingDetailsFormanifest(mnpBookingDOList
				.get(0).getConsignmentNumber());

		if (alreadybookedlist != null && alreadybookedlist.size() > 0) {
			insertInLogTable(alreadybookedlist);
			session.createSQLQuery(query).executeUpdate();
		}*/
		
		// session.close();
		try {
		Iterator it = mnpBookingDOList.iterator();
		
			while (it.hasNext()) {
				BookingDO dTDCBookingDO = (BookingDO) it.next();
				try{
					if (dTDCBookingDO != null) {
						hibernateTemplate.saveOrUpdate(dTDCBookingDO);
						hibernateTemplate.flush();
					}
					bookingStatus = true;
				}catch(Exception e){
					logger.error(dTDCBookingDO.getConsignmentNumber()+"#################Unable Book in Central DB.####################");
					logger.error("MnpBookingMDBDAOImpl::saveAndDeleteMnpBookinDetails occured:"
							+e.getMessage());
					errorMessages.append(e.getMessage());
					errorMessages.append("\n");
					errorCNNumbers.append(dTDCBookingDO.getConsignmentNumber()+",");
				}

			}
		} catch (Exception e) {
			logger.error("MnpBookingMDBDAOImpl::saveAndDeleteMnpBookinDetails occured:"
					+e.getMessage());
			EmailUtility.sendEmail(
					new String[] { "ctbsaqc@dtdc.com" },"Unable Book in Central DB",
					"Unable Book in Central DB"+
					"CNNo:"+ errorCNNumbers +
					"ErrorLogs"+errorMessages); 
			//session.close();
			throw new CGBusinessException(e);
			//return false;
		}finally{
			//session.flush();
			//session.close();
			hibernateTemplate = null;
		}
		return bookingStatus;

	}
	
	/**
	 * Insert in log table.
	 *
	 * @param alreadybookedlist the alreadybookedlist
	 * @throws CGSystemException the cG system exception
	 */
	private void insertInLogTable(List<BookingDO> alreadybookedlist)
			throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<BookingLogDO> bookingLoglist = new ArrayList<BookingLogDO>();
		try {
			Iterator<BookingDO> itrBooking = alreadybookedlist.iterator();
			
			while(itrBooking.hasNext()){
				BookingLogDO logDo = new BookingLogDO();
				BookingDO bookingDo = itrBooking.next();
				PropertyUtils.copyProperties(logDo, bookingDo);
				bookingLoglist.add(logDo);
			}
		
			Iterator<BookingLogDO> itr = bookingLoglist.iterator();
			while (itr.hasNext()) {
				BookingLogDO bookinglogDO = itr.next();
				hibernateTemplate.save(bookinglogDO);
			}
		} catch (Exception e) {
			logger.error("MnpBookingMDBDAOImpl::insertInLogTable occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			hibernateTemplate.flush();
		}
	}

	/**
	 * Gets the booking details formanifest.
	 *
	 * @param consignmentNo the consignment no
	 * @return the booking details formanifest
	 * @throws CGSystemException the cG system exception
	 */
	private List<BookingDO> getBookingDetailsFormanifest(
			String consignmentNo) throws CGSystemException {
		String query = "getDetailsForConsignmentNO";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<BookingDO> bookingDO = null;
		try {
			bookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(query,
					BookingConstants.CONSG_NUM, consignmentNo);
		} catch (Exception e) {
			logger.error("MnpBookingMDBDAOImpl::getBookingDetailsFormanifest occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.MnpBookingMDBDAO#getBookingItemList(String)
	 */
	@Override
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)
			throws CGBusinessException {
		String query = "getBookingItemList";
		List<BookingItemListDO> bookingItems = null;

		try {
			bookingItems = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, "paperWorkCN",
							paperWorkCN);

		} catch (Exception e) {
			logger.error("MnpBookingMDBDAOImpl::getBookingItemList occured:"
					+e.getMessage());
			throw new CGBusinessException(
			"Exception occured while retrieving bookingList");
		}
		return bookingItems;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.ng.dao.booking.mnpbooking.MnpBookingDAO#getIdForExpressService()
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
			logger.error("MnpBookingMDBDAOImpl::getIdForExpressService occured:"
					+e.getMessage());
			throw new CGBusinessException(
					"Exception occured while retrieving id for Express Service");
		}
		serviceId = (ids != null) ? ids.get(0) : null;
		return serviceId;

	}

	/* (non-Javadoc)
	 * @see com.dtdc.ng.dao.booking.mnpbooking.MnpBookingDAO#getIdForDoxType()
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
			logger.error("MnpBookingMDBDAOImpl::getIdForDoxType occured:"
					+e.getMessage());
			throw new CGBusinessException(
					"Exception occured while retrieving id for DoxType DOX ");
		}
		doxId = (ids != null) ? ids.get(0) : null;
		return doxId;
	}
	
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.MnpBookingMDBDAO#getCustomerDetails(String)
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
			logger.error("MnpBookingMDBDAOImpl::getCustomerDetails occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return customer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.dao.booking.mnpbooking.MnpBookingDAO#getMnpBookingEnquiryDetails
	 * (java.lang.String)
	 */
	@Override
	public List<BookingDO> getMnpBookingEnquiryDetails(String consignmentNo)
			throws CGBusinessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		String query = "getMNPEnquiryDetailsForConsignmentNO";

		List mnpBookingDO = null;
		try {
			mnpBookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(
					query, BookingConstants.CONSG_NUM, consignmentNo);

		} catch (Exception e) {
			logger.error("MnpBookingMDBDAOImpl::getMnpBookingEnquiryDetails occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return mnpBookingDO;
	}
	
}
