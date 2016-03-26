package com.ff.sap.integration.employee.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.sap.integration.to.SAPEmployeeTO;

public interface EmployeeSAPIntegrationService {
	public boolean saveEmployeesDetails(List<SAPEmployeeTO> employees,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;
}
