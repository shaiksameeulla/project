package com.ff.sap.integration.employee.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.sap.integration.to.SAPEmployeeTO;

public interface EmployeeMasterSAPTransactionService {

	public List<CSDSAPEmployeeDO> getUpdateEmployeeDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;
	
	public boolean updateDetails(List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException;

	public List<CSDSAPEmployeeDO> getDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException;

	public boolean saveDetailsOneByOneForEmployees(List<CSDSAPEmployeeDO> updateEmployeeDOs);

	public boolean saveDetailsForEmployee(List<CSDSAPEmployeeDO> employeeDOs) throws CGSystemException;

	
	
}
