/**
 * 
 */
package src.com.dtdc.mdbDao.vehicletrip;

import com.dtdc.domain.transaction.vehicletrip.VehicleTripSheetDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface VehicleTripSheetMDBDAO.
 *
 * @author joaugust
 */
public interface VehicleTripSheetMDBDAO {
	
	/**
	 * Save vehicle trip sheet.
	 *
	 * @param tripSheetDO the trip sheet do
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean saveVehicleTripSheet(VehicleTripSheetDO tripSheetDO)
			throws Exception;;
}
