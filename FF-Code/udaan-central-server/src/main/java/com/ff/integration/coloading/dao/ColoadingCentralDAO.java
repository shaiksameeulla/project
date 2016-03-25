package com.ff.integration.coloading.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;


/**
 * The Interface ColoadingCentralDAO.
 *
 * @author narmdr
 */
public interface ColoadingCentralDAO {

	/**
	 * Checks if is vehicle contract exist.
	 *
	 * @param date the date
	 * @param vehicleRegNumber the vehicle reg number
	 * @return true, if is vehicle contract exist
	 * @throws CGSystemException the cG system exception
	 */
	boolean isVehicleContractExist(String date, String vehicleRegNumber)
			throws CGSystemException;

	/**
	 * Save vehicle service entry do.
	 *
	 * @param coloadingVehicleServiceEntryDO the coloading vehicle service entry do
	 * @throws CGSystemException the cG system exception
	 */
	void saveVehicleServiceEntryDO(
			ColoadingVehicleServiceEntryDO coloadingVehicleServiceEntryDO)
			throws CGSystemException;

}
