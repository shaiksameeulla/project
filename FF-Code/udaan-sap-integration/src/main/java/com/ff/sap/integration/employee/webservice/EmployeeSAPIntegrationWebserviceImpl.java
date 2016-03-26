package com.ff.sap.integration.employee.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ff.sap.integration.employee.bs.EmployeeSAPIntegrationService;
import com.ff.sap.integration.to.SAPEmployeeTO;

/**
 * @author cbhure
 *
 */

@WebService(endpointInterface="com.ff.sap.integration.employee.webservice.EmployeeSAPIntegrationWebservice" )
public class EmployeeSAPIntegrationWebserviceImpl implements EmployeeSAPIntegrationWebservice {
	
	Logger logger = Logger.getLogger(EmployeeSAPIntegrationWebserviceImpl.class);
	private EmployeeSAPIntegrationService integrationService;

	public boolean saveEmployeesDetails(@WebParam(name="employees")List<SAPEmployeeTO> employees) {
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationWebserviceImpl :: saveEmployeesDetails :: Start");
		boolean isSaved = false;
		boolean iterateOneByOne = false;
		try {
			isSaved = integrationService.saveEmployeesDetails(employees,iterateOneByOne);
		} catch (Exception ex) {
			logger.error(" EmployeeSAPIntegrationWebserviceImpl :: saveEmployeesDetails :: error",ex);
		}
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationWebserviceImpl :: saveEmployeesDetails :: End");
		return isSaved;
	}

	public void setIntegrationService(
			EmployeeSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}
}
