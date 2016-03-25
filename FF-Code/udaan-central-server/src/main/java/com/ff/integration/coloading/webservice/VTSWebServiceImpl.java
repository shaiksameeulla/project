package com.ff.integration.coloading.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ff.integration.coloading.service.VTSService;
import com.ff.integration.coloading.service.VTSServiceImpl;


@WebService(endpointInterface="com.ff.integration.coloading.webservice.VTSWebService" )
public class VTSWebServiceImpl implements VTSWebService {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VTSServiceImpl.class);

	private VTSService vtsService;
	
	/**
	 * @param vtsService the vtsService to set
	 */
	public void setVtsService(VTSService vtsService) {
		this.vtsService = vtsService;
	}

	@Override
	public String saveVTS(
			@WebParam(name = "vehicleRegNumber") String vehicleRegNumber,
			@WebParam(name = "date") String date,
			@WebParam(name = "ot") Integer ot,
			@WebParam(name = "openingKm") Integer openingKm,
			@WebParam(name = "closingKm") Integer closingKm,
			@WebParam(name = "officeId") Integer officeId) {
		
		String message = null;
		try {
			message = vtsService.validateAndSaveVTS(vehicleRegNumber, date, ot, openingKm, closingKm, officeId);
		}/* catch (CGSystemException e) {
			LOGGER.error("Exception happened in saveVTS of VTSWebServiceImpl..."
					, e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in saveVTS of VTSWebServiceImpl..."
					, e);
		} */catch (Exception e) {
			LOGGER.error("Exception happened in saveVTS of VTSWebServiceImpl..."
					, e);
			message = "Details cannot be processed, Please contact IT Department";			
		}
		return message;
	}
}
