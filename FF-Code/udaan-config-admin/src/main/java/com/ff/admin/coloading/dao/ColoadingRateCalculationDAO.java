/**
 * 
 */
package com.ff.admin.coloading.dao;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author isawarka
 * 
 */
public interface ColoadingRateCalculationDAO {

	//Rate Calculation
	void calculateAndSaveRateForAir() throws CGBusinessException,CGSystemException;
	void calculateAndSaveRateForTrain() throws CGBusinessException,CGSystemException;
	void calculateAndSaveRateForSurface() throws CGBusinessException,CGSystemException;
	void calculateAndSaveRateForVehicle() throws CGBusinessException,CGSystemException;
	void calculateAndSaveRateForVehicleMonthly() throws CGBusinessException,CGSystemException;
}
