package com.ff.sap.integration.material.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ff.sap.integration.material.bs.MaterialMasterSAPIntegrationService;
import com.ff.sap.integration.to.SAPMaterialTO;

@WebService(endpointInterface="com.ff.sap.integration.material.webservice.MaterialMasterSAPIntegrationWebservice" )
public class MaterialMasterSAPIntegrationWebserviceImpl implements MaterialMasterSAPIntegrationWebservice {
	Logger logger = Logger.getLogger(MaterialMasterSAPIntegrationWebserviceImpl.class);
	private MaterialMasterSAPIntegrationService integrationService;
	
	public boolean saveMaterialDetails(@WebParam(name="materials")List<SAPMaterialTO> materials) {
		logger.debug("ITEM :: MaterialMasterSAPIntegrationWebserviceImpl :: saveMaterialDetails :: Start");
		boolean isSaved = false;
		boolean iterateOneByOne = false;
		try {
			isSaved = integrationService.saveMaterialsDetails(materials,iterateOneByOne);
		} catch (Exception ex) {
			logger.error("MaterialMasterSAPIntegrationWebserviceImpl :: saveMaterialDetails :: error::",ex);
		}
		logger.debug("ITEM :: MaterialMasterSAPIntegrationWebserviceImpl :: saveMaterialDetails :: End");
		return isSaved;
	}

	public MaterialMasterSAPIntegrationService getIntegrationService() {
		return integrationService;
	}

	public void setIntegrationService(
			MaterialMasterSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}
	
	
}
