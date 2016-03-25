package com.ff.integration.coloading.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The Interface VTSWebService.
 * 
 * @author narmdr
 */
@WebService
public interface VTSWebService {

	/**
	 * Save vts.
	 *
	 * @param vehicleRegNumber the vehicle reg number
	 * @param date the date
	 * @param ot the ot
	 * @param openingKm the opening km
	 * @param closingKm the closing km
	 * @param officeId the office id
	 * @return the string
	 */
	public String saveVTS(
			@WebParam(name = "vehicleRegNumber") String vehicleRegNumber,
			@WebParam(name = "date") String date,
			@WebParam(name = "ot") Integer ot,
			@WebParam(name = "openingKm") Integer openingKm,
			@WebParam(name = "closingKm") Integer closingKm,
			@WebParam(name = "officeId") Integer officeId);

}
