/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.geography.CityDO;

import src.com.dtdc.constants.ManifestConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class PaperWorkManifestMDBDAOImpl.
 *
 * @author nisahoo
 */
public class PaperWorkManifestMDBDAOImpl extends CGBaseDAO implements PaperWorkManifestMDBDAO {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaperWorkManifestMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#isManifestInBag(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isManifestInBag(String manifestNo) {
		LOGGER.info("PaperWorkManifestMDBDAOImpl: isManifestInBag(): START");
		List<ManifestDO> manifestList = null;
		boolean updatePossible = true;

		HibernateTemplate hibernateTemplate = getHibernateTemplate();

		manifestList = hibernateTemplate.findByNamedQueryAndNamedParam(
				ManifestConstant.VALIDATE_MANIFEST_NUMBER_IN_BAG_QUERY,
				ManifestConstant.VALIDATE_MANIFEST_NUMBER_IN_BAG_QUERY_PARAM,
				manifestNo);
		if (manifestList != null && manifestList.size() > 0) {
			LOGGER.info("Size of Manifest list=" + manifestList.size());
			updatePossible = false;
		}
		LOGGER.info("Is the Manifest=[" + manifestNo + " ] in Bag ="
				+ updatePossible);
		LOGGER.info("PaperWorkManifestMDBDAOImpl: isManifestInBag(): END");
		return updatePossible;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#savePaperWorkManifest(ManifestDO)
	 */
	@Override
	public void savePaperWorkManifest(ManifestDO manifestDO) {
		LOGGER.info("PaperWorkManifestMDBDAOImpl: savePaperWorkManifest(): START");

		getHibernateTemplate().saveOrUpdate(manifestDO);

		LOGGER.info("PaperWorkManifestMDBDAOImpl: savePaperWorkManifest(): END");
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#updateBookingWeight(BookingDO)
	 */
	@Override
	public void updateBookingWeight(BookingDO bookingDO) {
		LOGGER.info("PaperWorkManifestMDBDAOImpl: updateBookingWeight(): START");
		
		getHibernateTemplate().update(bookingDO);

		LOGGER.info("PaperWorkManifestMDBDAOImpl: updateBookingWeight(): END");
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#getBookedConsignmentByCnNumber(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getBookedConsignmentByCnNumber(String cnNumber) {
		LOGGER.info("PaperWorkManifestMDBDAOImpl: getConsignmentByConsignmentNumber(): START");
		
		BookingDO bookingDO = null;
		
		String[] params = { ManifestConstant.CONSIGNMENT_NUMBER_QUERY_PARAM,
				ManifestConstant.PAPERWORK_MANIFEST_TYPE_QUERY_PARAM };
		Object[] values = { cnNumber,
				ManifestConstant.PAPERWORK_BOOKING_MANIFEST_TYPE };

		List<BookingDO> bookedcnList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				ManifestConstant.GET_PAPERWORK_CONSIGNMENT_QUERY, params,values);

		if (bookedcnList != null && bookedcnList.size() > 0) {
			bookingDO = bookedcnList.get(0);	
		} 
		LOGGER.info("PaperWorkManifestMDBDAOImpl: getConsignmentByConsignmentNumber(): END");
		return bookingDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#getDestCityByCityName(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CityDO getDestCityByCityName(String destCityName) {
		LOGGER.info("PaperWorkManifestMDBDAOImpl: getDestCityByCityName(): START");
		CityDO cityDO = null;
		List<CityDO> destCityList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				ManifestConstant.GET_CITY_BY_CITY_NAME_QUERY,
				ManifestConstant.GET_CITY_BY_CITY_NAME_QUERY_PARAM,
				destCityName);
		LOGGER.info("No of Cities matching the City Name: ["+destCityName+"] = "+destCityList.size());
		if(destCityList != null && destCityList.size() > 0){
			cityDO = destCityList.get(0);
		}
		LOGGER.info("PaperWorkManifestMDBDAOImpl: getDestCityByCityName(): END");
		return cityDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#getBookingDetailsByParentCnNo(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getBookingDetailsByParentCnNo(String parentCnNumber) {
		LOGGER.info("PaperWorkManifestDAOImpl: getBookingDetailsByParentCnNo(): START");
		String query = "getDetailsForConsignmentNO";
		BookingDO bookingDO = null;

		List<BookingDO> bookedcnList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				query, "cnNo",parentCnNumber);

		if (bookedcnList != null && bookedcnList.size() > 0) {
			bookingDO = bookedcnList.get(0);	
		} 
		LOGGER.info("PaperWorkManifestDAOImpl: getBookingDetailsByParentCnNo(): END");
		return bookingDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO#getManifestDetailsByCompositeID(String, String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsByCompositeID(String manifestNo, String cnNo, String manifestType) {
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestDetailsByCompositeID(): START");
		ManifestDO manifestDO = null;
		
		String query = "getManifestDetailsByCompositeId";
		String[] params = {"manifestNo","cnNo","manifestTypeCode"};
		Object[] values = {manifestNo,cnNo,manifestType};
			
		List<ManifestDO> manifestList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);
		
		if (manifestList != null && manifestList.size() > 0) {
			manifestDO = manifestList.get(0);
		}
		
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestDetailsByCompositeID(): END");
		return manifestDO;
	}

}
