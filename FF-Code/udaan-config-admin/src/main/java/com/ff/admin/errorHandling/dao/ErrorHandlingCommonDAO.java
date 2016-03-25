package com.ff.admin.errorHandling.dao;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.sap.error.conf.SAPInterfaceErrorConfigDO;

public interface ErrorHandlingCommonDAO {
	
	List<CGBaseDO> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName, String status, 
			SAPInterfaceErrorConfigDO SAPInterfaceErrorConfigDoObj) throws CGSystemException;
	
	
	List<SAPInterfaceErrorConfigDO> getSAPInterfaceConfigData(String interfaceName) throws CGSystemException;
	

}
