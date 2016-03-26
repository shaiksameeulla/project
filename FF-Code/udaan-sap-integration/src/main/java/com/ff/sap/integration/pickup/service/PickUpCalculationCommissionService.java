package com.ff.sap.integration.pickup.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.SAPPickUpComissionCalculationStagingDO;

public interface PickUpCalculationCommissionService {

	public List<SAPPickUpComissionCalculationStagingDO> findPickUpCommissionCountForSAPIntegration() throws CGSystemException, CGBusinessException;
	public void updatePickUpStagingFlag(String status,List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommisnStagingDOList,String exception);
}
