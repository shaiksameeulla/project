package com.ff.sap.integration.material.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ff.sap.integration.material.bs.MaterialMasterSAPIntegrationService;
import com.ff.sap.integration.to.SAPColoaderTO;

@WebService(endpointInterface="com.ff.sap.integration.material.webservice.ColoaderInvoicNoUpdateSAPIntegrationWebservice" )
public class ColoaderInvoicNoUpdateSAPIntegrationWebserviceImpl implements ColoaderInvoicNoUpdateSAPIntegrationWebservice {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoaderInvoicNoUpdateSAPIntegrationWebserviceImpl.class);
	
	private MaterialMasterSAPIntegrationService integrationService;
	
	public boolean updateColoaderInvoiceNo(@WebParam(name="coloaderInvoiceNumber")List<SAPColoaderTO> coloaderInvoiceNumber) {
		boolean isSaved = false;
		try {
			isSaved = integrationService.updateColoaderInvoiceNo(coloaderInvoiceNumber);
		} catch (Exception ex) {
			LOGGER.error("ColoaderInvoicNoUpdateSAPIntegrationWebserviceImpl :: updateColoaderInvoiceNo :: error::",ex);
		}
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
