/**
 * 
 */
package com.ff.sap.integration.plantmaster.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ff.sap.integration.plantmaster.bs.PlantMasterSAPIntegrationService;
import com.ff.sap.integration.to.SAPOfficeTO;

/**
 * @author cbhure
 *
 */
@WebService(endpointInterface="com.ff.sap.integration.plantmaster.webservice.PlantMasterSAPIntegrationWebService" )
public class PlantMasterSAPIntegrationWebServiceImpl implements PlantMasterSAPIntegrationWebService{
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PlantMasterSAPIntegrationWebServiceImpl.class);

	private PlantMasterSAPIntegrationService integrationService;
	
	@Override
	public boolean saveOfficeDetails(@WebParam(name="office") List<SAPOfficeTO> Office) {
		boolean isSaved = false;
		boolean iterateOneByOne = false;
		try {
			isSaved = integrationService.saveOfficeDetails(Office,iterateOneByOne);
		} catch (Exception ex) {
			LOGGER.error(
					"PlantMasterSAPIntegrationWebServiceImpl::saveOfficeDetails::error::",
					ex);
		}
		return isSaved;
	}

	/**
	 * @param integrationService the integrationService to set
	 */
	public void setIntegrationService(
			PlantMasterSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}

}

