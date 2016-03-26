/**
 * 
 */
package com.ff.sap.integration.plantmaster.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPOfficeTO;

/**
 * @author cbhure
 *
 */
@WebService
public interface PlantMasterSAPIntegrationWebService {

	/**
	 * @param Office
	 * @return
	 */
	
	public boolean saveOfficeDetails(@WebParam(name="office")List<SAPOfficeTO> Office);
	
}
