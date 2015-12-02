/*
 * @author soagarwa
 */
package src.com.dtdc.mdbDao.booking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.EmailUtility;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class CashBookingDAOImpl.
 */
public class CashBookingMDBDAOImpl extends HibernateDaoSupport implements
CashBookingMDBDAO {

	/** The logger. */
	private Logger logger = LoggerFactory
	.getLogger(CashBookingMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.dtdc.ng.dao.booking.cashbooking.CashBookingDAO#saveCashBookingDetails(java.util.List)
	 */
	@Override
	public boolean saveAndDeleteCashBookingDetails(List<CashBookingDO> cashBookingDoList) throws CGBusinessException{
		boolean bookingStatus = false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder errorMessages=new StringBuilder();
		StringBuilder errorCNNumbers=new StringBuilder();
		try {
			/*
			String queryForConsignmentBooked = "getDetailsForConsignmentNO";
			List<CashBookingDO> result = null;
			Session session = getSessionFactory().openSession();
			String[] stringArray = {BookingConstants.CONSG_NUM,BookingConstants.MANIFEST_NUM};
			Object[] valuesArray = {cashBookingDoList.get(0).getConsignmentNumber(),null};
			result = hibernateTemplate.findByNamedQueryAndNamedParam(
					queryForConsignmentBooked, stringArray,valuesArray);
			if (result != null && result.size() > 0) {
				insertInLogTable(result);
				String query = "delete from dtdc_ctbs_plus.dtdc_f_booking where CONSG_NUMBER ='"
						+ cashBookingDoList.get(0).getConsignmentNumber()
						+ "'"
						+ " or PARENT_CONSG_NO = '"
						+ cashBookingDoList.get(0).getConsignmentNumber() + "'";
				session.createSQLQuery(query).executeUpdate();
			}
			Iterator<CashBookingDO> itr = cashBookingDoList.iterator();
			while (itr.hasNext()) {
				CashBookingDO bookingDO = itr.next();
				session.save(bookingDO);
			}
			bookingStatus = true;
			 */
			logger.debug("CashBookingMDBDAOImpl:saveAndDeleteCashBookingDetails:size -- "+ cashBookingDoList.size());			

			if(cashBookingDoList == null || cashBookingDoList.isEmpty()) {
				return false;
			}
			Iterator<CashBookingDO> itr = cashBookingDoList.iterator();
			while (itr.hasNext()) {
				CashBookingDO bookingDO = itr.next();
				try{
					logger.debug("CashBookingMDBDAOImpl:saveAndDeleteCashBookingDetails: ConsignmentNumber----"+ bookingDO.getConsignmentNumber());
					hibernateTemplate.saveOrUpdate(bookingDO);
				}catch(Exception e){
					logger.error("CashBookingMDBDAOImpl::saveAndDeleteCashBookingDetails::Exception occured:"
							+e.getMessage());
					errorMessages.append(e.getMessage());
					errorMessages.append("\n");
					errorCNNumbers.append(bookingDO.getConsignmentNumber()+",");
				}
			}
			bookingStatus = true;
		} catch (Exception obj) {
			logger.error("CashBookingMDBDAOImpl::saveAndDeleteCashBookingDetails::Exception occured:"
					+obj.getMessage());
			EmailUtility.sendEmail(
					new String[] { "ctbsaqc@dtdc.com" },"Unable Book in Central DB",
					"Unable Book in Central DB"+
					"CNNo:"+ errorCNNumbers +
					"ErrorLogs"+errorMessages); 

			throw new CGBusinessException(obj);
		}
		finally{
			hibernateTemplate.flush();
		}
		return bookingStatus;
	}

	/**
	 * Insert in log table.
	 *
	 * @param alreadybookedlist the alreadybookedlist
	 * @throws CGSystemException the cG system exception
	 */
	private void insertInLogTable(List<CashBookingDO> alreadybookedlist)
	throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<BookingLogDO> bookingLoglist = new ArrayList<BookingLogDO>();
		try {
			Iterator<CashBookingDO> itrBooking = alreadybookedlist.iterator();

			while(itrBooking.hasNext()){
				BookingLogDO logDo = new BookingLogDO();
				CashBookingDO bookingDo = itrBooking.next();
				PropertyUtils.copyProperties(logDo, bookingDo);
				bookingLoglist.add(logDo);
			}

			Iterator<BookingLogDO> itr = bookingLoglist.iterator();
			while (itr.hasNext()) {
				BookingLogDO bookinglogDO = itr.next();
				hibernateTemplate.save(bookinglogDO);
			}
		} catch (Exception e) {
			logger.error("CashBookingMDBDAOImpl::insertInLogTable::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}finally{
			hibernateTemplate.flush();
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.CashBookingMDBDAO#getIdForExpressService()
	 */
	@Override
	public Integer getIdForExpressService() throws CGBusinessException {
		String query="getIdForExpressService";
		List<Integer> ids=null;
		Integer serviceId=null;
		try{
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,BookingConstants.SERVICE_CODE, BookingConstants.EXPRESS_SERVICE_CODE);
		}catch(Exception e){
			logger.error("CashBookingMDBDAOImpl::getIdForExpressService::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException("Exception occured while retrieving id for Express Service");
		}
		serviceId=(ids!=null)?ids.get(0):null;
		return serviceId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.CashBookingMDBDAO#getIdForDoxType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getIdForDoxType() throws CGBusinessException {
		String query="getIdForDoxType";
		List<Integer> ids=null;
		Integer doxId=null;
		try{
			ids = getHibernateTemplate().findByNamedQueryAndNamedParam(query,BookingConstants.DOX_TYPE, BookingConstants.DOX);
		}catch(Exception e){
			logger.error("CashBookingMDBDAOImpl::getIdForDoxType::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException("Exception occured while retrieving id for DoxType DOX ");
		}
		doxId=(ids!=null)?ids.get(0):null;
		return doxId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.CashBookingMDBDAO#getBookingItemList(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN) {
		String query = "getBookingItemList";
		List<BookingItemListDO> bookingItems = null;

		try {
			bookingItems = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(query,
					"paperWorkCN", paperWorkCN);


		} catch (Exception e) {
			logger.error("CashBookingMDBDAOImpl::getBookingItemList::Exception occured:"
					+e.getMessage());
		}
		return bookingItems;
	}


	/* (non-Javadoc)
	 * @see com.dtdc.ng.dao.booking.cashbooking.CashBookingDAO#getCashBookingEnquiryDetails(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingDO> getCashBookingEnquiryDetails (
			String consingnmentNO) throws CGBusinessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		String query = "getConsignmentDetailsForCashBooking";
		// String query =
		// "from com.dtdc.domain.booking.cashbooking.CashBookingDO bookingDO where bookingDO.consignmentNumber = :consingnmentNO";
		List<BookingDO> cashBookingDO = null;
		try {
			cashBookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(
					query, BookingConstants.CONSIGNMENT_NO, consingnmentNO);
		} catch (Exception e) {
			logger.error("CashBookingMDBDAOImpl::getCashBookingEnquiryDetails::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		return cashBookingDO;
	}

}
