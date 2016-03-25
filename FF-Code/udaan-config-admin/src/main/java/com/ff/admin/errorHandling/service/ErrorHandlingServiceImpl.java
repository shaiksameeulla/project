package com.ff.admin.errorHandling.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.errorHandling.ErrorHandlingTo;

public class ErrorHandlingServiceImpl implements ErrorHandlingService {
	
	private final static Logger LOGGER= LoggerFactory.getLogger(ErrorHandlingServiceImpl.class);
	
	private ErrorHandlingCommonService errorHandlingCommonService;
	
	public void setErrorHandlingCommonService(ErrorHandlingCommonService errorHandlingCommonService) {
		this.errorHandlingCommonService = errorHandlingCommonService;
	}

	@Override
	public List<ErrorHandlingTo> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName) throws CGBusinessException, CGSystemException {
		List<ErrorHandlingTo> errorHandlingTo = errorHandlingCommonService.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName);
/*		if(StringUtil.isEmptyList(sapCustomerDataList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST); // to be changed
		}*/
		return errorHandlingTo;
	}


}
