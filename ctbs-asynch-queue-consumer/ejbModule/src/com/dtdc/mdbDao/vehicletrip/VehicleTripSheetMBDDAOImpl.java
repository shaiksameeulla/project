/**
 * 
 */
package src.com.dtdc.mdbDao.vehicletrip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.dtdc.domain.transaction.vehicletrip.VehicleTripSheetDO;

// TODO: Auto-generated Javadoc
/**
 * The Class VehicleTripSheetMBDDAOImpl.
 *
 * @author joaugust
 */
public class VehicleTripSheetMBDDAOImpl extends HibernateDaoSupport implements
		VehicleTripSheetMDBDAO {

	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(VehicleTripSheetMBDDAOImpl.class);

	/*
	 * (non-Javadoc) saves the new info or updates the existing trip sheet
	 * 
	 * @see
	 * com.dtdc.ng.dao.vehicletrip.VehicleTripSheetDAO#saveVehicleTripSheet()
	 */
	public Boolean saveVehicleTripSheet(VehicleTripSheetDO tripSheetDO)
			throws Exception {

		boolean vehicleTripStatus = false;

		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			hibernateTemplate.saveOrUpdate(tripSheetDO);
			hibernateTemplate.flush();
			vehicleTripStatus = true;
		} catch (Exception obj) {
			logger.error("VehicleTripSheetMBDDAOImpl::saveVehicleTripSheet::Exception occured:"
					+obj.getMessage());
			throw new CGBusinessException("Error occurred while saving the data");
		}
		return vehicleTripStatus;
	}

}
