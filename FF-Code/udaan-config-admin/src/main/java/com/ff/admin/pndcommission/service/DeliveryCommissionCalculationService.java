package com.ff.admin.pndcommission.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author hkansagr
 * 
 */
public interface DeliveryCommissionCalculationService {

	/**
	 * To generate or execute delivery commission calculation.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void generateDlvCommission() throws CGBusinessException, CGSystemException;

	/**
	 * To copy the data from delivery commission table to SAP staging table.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void dataCopyToSAPStagingTable() throws CGBusinessException,
			CGSystemException;

}
