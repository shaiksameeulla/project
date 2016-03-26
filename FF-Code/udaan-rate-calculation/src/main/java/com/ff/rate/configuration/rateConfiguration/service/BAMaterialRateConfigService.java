package com.ff.rate.configuration.rateConfiguration.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;

/**
 * @author hkansagr
 */

public interface BAMaterialRateConfigService {

	/**
	 * To save BA material rate details
	 * @param to
	 * @return {boolean}
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveBAMaterialRateDtls(BAMaterialRateConfigTO to) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To search BA material rate details of current tariff
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	BAMaterialRateConfigTO searchBAMaterialRateDtls(BAMaterialRateConfigTO to)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To search already renewal BA material rate details of current tariff
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	BAMaterialRateConfigTO searchRenewedBAMaterialRateDtls(BAMaterialRateConfigTO to)
			throws CGBusinessException, CGSystemException;
	
}
