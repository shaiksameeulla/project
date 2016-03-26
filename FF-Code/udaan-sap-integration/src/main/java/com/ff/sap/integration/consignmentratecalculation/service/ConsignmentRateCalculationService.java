package com.ff.sap.integration.consignmentratecalculation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * @author hkansagr
 * 
 */
public interface ConsignmentRateCalculationService {

	/**
	 * To execute consignment rate calculation job
	 * @param rateCalculatedfor 
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws InterruptedException 
	 */
	public void executeConsignmentRateCalculation(String rateCalculatedfor) throws CGBusinessException,
			CGSystemException, InterruptedException;

}
