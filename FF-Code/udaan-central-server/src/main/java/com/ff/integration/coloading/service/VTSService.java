package com.ff.integration.coloading.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ColoadingUniversalService.
 * 
 * @author narmdr
 */
public interface VTSService {

	/**
	 * Validate and save vts.
	 *
	 * @param vehicleRegNumber the vehicle reg number
	 * @param date the date
	 * @param ot the ot
	 * @param openingKm the opening km
	 * @param closingKm the closing km
	 * @param officeId the office id
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateAndSaveVTS(String vehicleRegNumber, String date, Integer ot,
			Integer openingKm, Integer closingKm, Integer officeId)
			throws CGBusinessException, CGSystemException;

	
}
