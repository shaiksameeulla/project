/**
 * 
 */
package src.com.dtdc.mdbServices.vehicletrip;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface VehicleTripSheetMDBService.
 */
public interface VehicleTripSheetMDBService {

	/**
	 * Save vehicle trip sheet.
	 *
	 * @param tripSheetTO the trip sheet to
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean saveVehicleTripSheet(CGBaseTO tripSheetTO) throws Exception;

}
