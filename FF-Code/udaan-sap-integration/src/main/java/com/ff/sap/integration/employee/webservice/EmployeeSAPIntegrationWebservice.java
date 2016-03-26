package com.ff.sap.integration.employee.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ff.sap.integration.to.SAPEmployeeTO;

/**
 * @author mohammal
 *
 */

@WebService
public interface EmployeeSAPIntegrationWebservice {
	//public boolean saveEmployeeDetails(@WebParam(name="employee")SAPEmployeeTO employee);
	public boolean saveEmployeesDetails(@WebParam(name="employees")List<SAPEmployeeTO> employees);
}
