/**
 * 
 */
package com.ff.sap.integration.vendor.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ff.sap.integration.to.SAPVendorTO;
import com.ff.sap.integration.vendor.bs.VendorMasterSAPIntegrationService;

/**
 * @author cbhure
 *
 */

@WebService(endpointInterface="com.ff.sap.integration.vendor.webservice.VendorMasterSAPIntegrationWebservice" )
public class VendorMasterSAPIntegrationWebserviceImpl implements VendorMasterSAPIntegrationWebservice{
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VendorMasterSAPIntegrationWebserviceImpl.class);
	
private VendorMasterSAPIntegrationService integrationService;
	
	public boolean saveVendorDetails(@WebParam(name="vendors")List<SAPVendorTO> vendors) {
		boolean isSaved = false;
		boolean iterateOneByOne = false;
		try {
			isSaved = integrationService.saveVendorDetails(vendors,iterateOneByOne);
		} catch (Exception ex) {
			LOGGER.error("VendorMasterSAPIntegrationService :: saveVendorDetails :: error",ex);
		}finally{
			//If Batch Insert/Update fails then try to insert/update records one by one 
			// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
			//for error records
			/*try{
				iterateOneByOne = true;	
				if(!isSaved)
					isSaved = integrationService.saveVendorDetails(vendors,iterateOneByOne);
			}catch(Exception e){
			}*/
		}
		return isSaved;
	}

	public VendorMasterSAPIntegrationService getIntegrationService() {
		return integrationService;
	}

	public void setIntegrationService(
			VendorMasterSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}

}
