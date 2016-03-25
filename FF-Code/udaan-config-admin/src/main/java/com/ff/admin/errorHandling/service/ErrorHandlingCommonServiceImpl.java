package com.ff.admin.errorHandling.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.errorHandling.action.ErrorHandlingAction;
import com.ff.admin.errorHandling.dao.ErrorHandlingCommonDAO;
import com.ff.domain.sap.error.conf.SAPInterfaceErrorConfigDO;
import com.ff.errorHandling.ErrorHandlingTo;

public class ErrorHandlingCommonServiceImpl implements ErrorHandlingCommonService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingCommonServiceImpl.class);

	List<ErrorHandlingTo> successfulList = null;
	List<ErrorHandlingTo> errorList = null;
	
	List<CGBaseTO> SAPCustomerSuccessTOs = null;
	List<CGBaseTO> SAPCustomerErrorTOs = null;
	
	private ErrorHandlingCommonDAO errorHandlingCommonDAO = null;
	
	public void setErrorHandlingCommonDAO(ErrorHandlingCommonDAO errorHandlingCommonDAO) {
		this.errorHandlingCommonDAO = errorHandlingCommonDAO;
	}
	
	@Override
	public List<ErrorHandlingTo> getSAPCustomerData(String effectiveFromDate, String effectiveToDate, String interfaceName) throws CGSystemException {
//		String successStatus = "C";
//		String errorStatus = "E";
		
		List<SAPInterfaceErrorConfigDO> sapInterfaceErrorConfigDOs = errorHandlingCommonDAO.getSAPInterfaceConfigData(interfaceName);
		LOGGER.debug("sapInterfaceErrorConfigDOs: " + sapInterfaceErrorConfigDOs);
		
		List<ErrorHandlingTo> errorHandlingToList = new ArrayList<ErrorHandlingTo>();
		
		for(SAPInterfaceErrorConfigDO SAPInterfaceErrorConfigDoObj : sapInterfaceErrorConfigDOs) {
			
			ErrorHandlingTo errorHandlingTo = new ErrorHandlingTo();
			
				//List<com.ff.errorHandling.SAPCustomerTO> SAPCustomerSuccessTOs = new ArrayList<com.ff.errorHandling.SAPCustomerTO>();	
			    SAPCustomerSuccessTOs = new ArrayList<CGBaseTO>();	
//				List<CGBaseDO> successSAPCustomerDOs = errorHandlingCommonDAO.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName, successStatus, SAPInterfaceErrorConfigDoObj);
				List<CGBaseDO> successSAPCustomerDOs = errorHandlingCommonDAO.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName, SAPInterfaceErrorConfigDoObj.getSuccessStatus(), SAPInterfaceErrorConfigDoObj);
		
				for (CGBaseDO successDo : successSAPCustomerDOs) {
					com.ff.errorHandling.SAPCustomerTO successTo = new com.ff.errorHandling.SAPCustomerTO();
					successTo.setCustomerNo(successDo.getCustomerNo());
					successTo.setTansException("NA");
					SAPCustomerSuccessTOs.add(successTo);
				}
				
				//List<com.ff.errorHandling.SAPCustomerTO> SAPCustomerErrorTOs = new ArrayList<com.ff.errorHandling.SAPCustomerTO>();
				SAPCustomerErrorTOs = new ArrayList<CGBaseTO>();
//				List<CGBaseDO> errorSAPCustomerDOs = errorHandlingCommonDAO.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName, errorStatus, SAPInterfaceErrorConfigDoObj);
				List<CGBaseDO> errorSAPCustomerDOs = errorHandlingCommonDAO.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName, SAPInterfaceErrorConfigDoObj.getFailureStatus(), SAPInterfaceErrorConfigDoObj);

				for (CGBaseDO errorDo : errorSAPCustomerDOs) {
					com.ff.errorHandling.SAPCustomerTO errorTo = new com.ff.errorHandling.SAPCustomerTO();
					errorTo.setCustomerNo(errorDo.getCustomerNo());
					errorTo.setTansException(errorDo.getException());
					SAPCustomerErrorTOs.add(errorTo);
				}
				
		
		/*		if (StringUtil.isEmptyColletion(SAPCustomerDOs)) {
					throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_LIST);
				}*/
				
				errorHandlingTo.setInterfaceName(SAPInterfaceErrorConfigDoObj.getInterfaceName());
				errorHandlingTo.setSuccessList(SAPCustomerSuccessTOs);
				errorHandlingTo.setErrorList(SAPCustomerErrorTOs);
				
				errorHandlingToList.add(errorHandlingTo);
		}
		
		return errorHandlingToList;
	}	


}
