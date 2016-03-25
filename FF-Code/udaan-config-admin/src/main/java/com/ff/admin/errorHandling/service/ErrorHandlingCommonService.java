package com.ff.admin.errorHandling.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.errorHandling.ErrorHandlingTo;

public interface ErrorHandlingCommonService {
	
	List<ErrorHandlingTo> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName) throws CGSystemException;

}
