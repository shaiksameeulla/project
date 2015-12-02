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
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.BookingLogDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeBookingDAOImpl.java
 */
@SuppressWarnings("unchecked")
public class FranchiseeBookingMDBDAOImpl extends HibernateDaoSupport implements
FranchiseeBookingMDBDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(FranchiseeBookingMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO#insertAndDeleteFranchiseeBookingDetails(List)
	 */
	@Override
	public boolean insertAndDeleteFranchiseeBookingDetails(
			List<FranchiseeBookingDO> bookingList) throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder errorMessages=new StringBuilder();
		StringBuilder errorCNNumbers=new StringBuilder();
		//		Session session = getSessionFactory().openSession();
		boolean bookingStatus = false;
		try {
			if(bookingList == null || bookingList.isEmpty()) {
				return false;
				/*
				 * Have used sql query as dml operations are not supported by
				 * hibernate template
				 */
				/*	String query = "delete from dtdc_ctbs_plus.dtdc_f_booking where FRANCH_MINF_NUMBER ='"
					+ bookingList.get(0).getFranchiseeManifestNo()
					+ "' or CONSG_NUMBER='"+bookingList.get(0).getConsignmentNumber()
					+ "'and CONSGMNT_STATUS != 'CANCELLED'";

			List<FranchiseeBookingDO> alreadybookedlist = getBookingDetailsFormanifest(
					bookingList.get(0).getFranchiseeManifestNo(),bookingList.get(0).getConsignmentNumber());

			if (alreadybookedlist != null && alreadybookedlist.size() > 0) {
				insertInLogTable(alreadybookedlist);
				session.createSQLQuery(query).executeUpdate();
			}*/
			}



			Iterator<FranchiseeBookingDO> itr = bookingList.iterator();
			while (itr.hasNext()) {
				FranchiseeBookingDO bookingDO = itr.next();
				try{
					hibernateTemplate.saveOrUpdate(bookingDO);
					LOGGER.debug(bookingDO.getConsignmentNumber()+"###########Booked Successfully in Central DB.################");
				}catch(Exception e){
					LOGGER.error(bookingDO.getConsignmentNumber()+"#################Unable Book in Central DB.####################");
					logger.error("FranchiseeBookingMDBDAOImpl::insertAndDeleteFranchiseeBookingDetails occured:"
							+e.getMessage());
					errorMessages.append(e.getMessage());
					errorMessages.append("\n");
					errorCNNumbers.append(bookingDO.getConsignmentNumber()+",");
				}
			}
			bookingStatus = true;
		} catch (Exception obj) {
			logger.error("FranchiseeBookingMDBDAOImpl::insertAndDeleteFranchiseeBookingDetails occured:"
					+obj.getMessage());
			//return false;

			EmailUtility.sendEmail(
					new String[] { "ctbsaqc@dtdc.com" },"Unable Book in Central DB",
					"Unable Book in Central DB"+
					"CNNo:"+ errorCNNumbers +
					"ErrorLogs"+errorMessages);  


			throw new CGSystemException(obj);
		} finally {
			hibernateTemplate = null;
			/*session.flush();
			session.close();*/
		}

		return bookingStatus;
	}

	/**
	 * Insert in log table.
	 *
	 * @param alreadybookedlist the alreadybookedlist
	 * @throws CGSystemException the cG system exception
	 */
	private void insertInLogTable(List<FranchiseeBookingDO> alreadybookedlist)
	throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<BookingLogDO> bookingLoglist = new ArrayList<BookingLogDO>();
		try {
			Iterator<FranchiseeBookingDO> itrBooking = alreadybookedlist.iterator();

			while(itrBooking.hasNext()){
				BookingLogDO logDo = new BookingLogDO();
				FranchiseeBookingDO franchiseeBookingDo = itrBooking.next();
				PropertyUtils.copyProperties(logDo, franchiseeBookingDo);
				bookingLoglist.add(logDo);
			}

			Iterator<BookingLogDO> itr = bookingLoglist.iterator();
			while (itr.hasNext()) {
				BookingLogDO bookinglogDO = itr.next();
				hibernateTemplate.save(bookinglogDO);
			}
		} catch (Exception e) {
			logger.error("FranchiseeBookingMDBDAOImpl::insertInLogTable occured:"
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
	 * @param consignmentNumber the consignment number
	 * @return the booking details formanifest
	 * @throws CGSystemException the cG system exception
	 */
	private List<FranchiseeBookingDO> getBookingDetailsFormanifest(
			String manifestNo,String consignmentNumber) throws CGSystemException {
		String query = "getFranchiseeDetailsForManisfestNOorCnNumber";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<FranchiseeBookingDO> bookingDO = null;
		try {
			String values[] = { manifestNo,consignmentNumber};
			String param[] = {BookingConstants.MANIFEST_NUM,BookingConstants.CONSIGNMENT_NUMBER};

			bookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(query,
					param, values);
		} catch (Exception e) {
			logger.error("FranchiseeBookingMDBDAOImpl::getBookingDetailsFormanifest occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingDO;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.franchisebooking.FranchiseeBookingDAO#
	 * getBookingItemList(java.lang.String)
	 */
	@Override
	public List<BookingItemListDO> getBookingItemList(String paperWorkCN)throws CGSystemException {
		String query = "getBookingItemList";
		List<BookingItemListDO> bookingItems = null;

		try {
			bookingItems = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(query, "paperWorkCN",
					paperWorkCN);

		} catch (Exception e) {
			logger.error("FranchiseeBookingMDBDAOImpl::getBookingItemList occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return bookingItems;
	}

	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO#getIdForExpressService()
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
			logger.error("FranchiseeBookingMDBDAOImpl::getIdForExpressService occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		return serviceId;

	}

	/* (non-Javadoc)
	 * @see src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO#getIdForDoxType()
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
			logger.error("FranchiseeBookingMDBDAOImpl::getIdForDoxType occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return doxId;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO#getDestBranchPincodeAndCity(String)
	 */
	@Override
	public List<Object[]> getDestBranchPincodeAndCity(String pincode)
	throws CGSystemException, CGBusinessException {

		String query = "getDestBranchPincodeAndCity";
		List<Object[]> pincodeAndCity = new ArrayList<Object[]>();

		try {
			pincodeAndCity = getHibernateTemplate().findByNamedQueryAndNamedParam(
					query, BookingConstants.PINCODE, pincode);

		} catch (Exception e) {

			logger.error("FranchiseeBookingMDBDAOImpl::getDestBranchPincodeAndCity occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return pincodeAndCity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.dao.booking.franchisebooking.FranchiseeBookingDAO#
	 * getFranchiseeBookingDetails(java.lang.String)
	 */
	@Override
	public List<FranchiseeBookingDO> getFranchiseeBookingDetails(String consignmentNumber
	) throws CGSystemException {
		//manifestNO=(manifestNO!=null && !manifestNO.equalsIgnoreCase(""))? manifestNO.trim():null;
		consignmentNumber=(consignmentNumber!=null && !consignmentNumber.equalsIgnoreCase(""))?consignmentNumber.trim():null;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		String query = "enquiryFranchiseeDetailsForcnNO";
		List<FranchiseeBookingDO> bookingDO = null;
		try {
			String values[] = {consignmentNumber};
			String param[] = { BookingConstants.CONSIGNMENT_NUMBER
			};

			bookingDO = hibernateTemplate.findByNamedQueryAndNamedParam(query,
					param, values);

		} catch (Exception e) {
			logger.error("FranchiseeBookingMDBDAOImpl::getFranchiseeBookingDetails occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return bookingDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO#insertInBookingDuplicateTable(BookingDuplicateDO)
	 */
	@Override
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
		logger.error("FranchiseeBookingMDBDAOImpl::insertInBookingDuplicateTable occured:"
				+e.getMessage());
		throw new CGSystemException(e);
	}finally{
		hibernateTemplate.flush();
	}
 }
}
