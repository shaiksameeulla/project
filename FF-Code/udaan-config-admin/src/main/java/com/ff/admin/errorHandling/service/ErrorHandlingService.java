package com.ff.admin.errorHandling.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.errorHandling.ErrorHandlingTo;

public interface ErrorHandlingService {

	List<ErrorHandlingTo> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName) throws CGBusinessException, CGSystemException;

}
