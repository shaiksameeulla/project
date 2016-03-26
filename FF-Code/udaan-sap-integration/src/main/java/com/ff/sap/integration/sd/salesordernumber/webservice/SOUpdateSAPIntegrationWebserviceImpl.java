/**
 * 
 */
package com.ff.sap.integration.sd.salesordernumber.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ff.sap.integration.sd.salesordernumber.bs.SOUpdateSAPIntegrationService;
import com.ff.sap.integration.to.SAPSalesOrderTO;

/**
 * @author cbhure
 *
 */

@WebService(endpointInterface="com.ff.sap.integration.sd.salesordernumber.webservice.SOUpdateSAPIntegrationWebservice" )
public class SOUpdateSAPIntegrationWebserviceImpl implements SOUpdateSAPIntegrationWebservice{
	
	Logger logger = Logger.getLogger(SOUpdateSAPIntegrationWebserviceImpl.class);
	private SOUpdateSAPIntegrationService integrationService;
	
	public boolean updateSalesOrderNumber(@WebParam(name = "salesOrder") List<SAPSalesOrderTO> salesOrder) {
		logger.warn("SOUpdateSAPIntegrationWebserviceImpl :: updateSalesOrderNumber :: START");
		boolean isSaved = false;
		try {
			isSaved = integrationService.updateSalesOrderNumber(salesOrder);
		} catch (Exception ex) {
			logger.error("SOUpdateSAPIntegrationWebserviceImpl :: updateSalesOrderNumber :: ERROR",ex);
		}
		logger.warn("SOUpdateSAPIntegrationWebserviceImpl :: updateSalesOrderNumber :: END");
		return isSaved;
	}

	/**
	 * @param integrationService the integrationService to set
	 */
	public void setIntegrationService(
			SOUpdateSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}
	
	
	

}
