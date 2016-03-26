package com.ff.sap.integration.plantmaster.bs;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author hkansagr
 */
public interface PlantMasterTransactionService {

	/**
	 * To save office and employee details
	 * 
	 * @param officeDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public SAPErrorTO saveOfficeAndEmpDtls(OfficeDO officeDO)
			throws CGBusinessException, CGSystemException;

}
