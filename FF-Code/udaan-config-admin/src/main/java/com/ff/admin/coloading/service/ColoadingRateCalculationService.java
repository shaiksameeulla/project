/**
 * 
 */
package com.ff.admin.coloading.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;


/**
 * @author isawarka
 * 
 */
public interface ColoadingRateCalculationService {
	
	void calculateAndSaveRateForAir() throws CGBusinessException,CGSystemException, HttpException, ClassNotFoundException, IOException;
	void calculateAndSaveRateForTrain() throws CGBusinessException,CGSystemException, HttpException, ClassNotFoundException, IOException;
	void calculateAndSaveRateForSurface() throws CGBusinessException,CGSystemException, HttpException, ClassNotFoundException, IOException;
	void calculateAndSaveRateForVehicle() throws CGBusinessException,CGSystemException, HttpException, ClassNotFoundException, IOException;
	void calculateAndSaveRateForVehicleMonthly() throws CGBusinessException,CGSystemException, 
						HttpException, ClassNotFoundException, IOException;
}
