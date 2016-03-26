package com.ff.rate.configuration.rateConfiguration.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateConfigDO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;

/**
 * @author hkansagr
 */

public interface BAMaterialRateConfigDAO {

	/**
	 * To save BA material rate details 
	 * @param domain
	 * @return {boolean}
	 * @throws CGSystemException
	 */
	boolean saveBAMaterialRateDtls(BAMaterialRateConfigDO domain) 
			throws CGSystemException;
	
	/**
	 * To search BA material rate details of current tariff 
	 * @param to
	 * @return baMtrlRateConfigDOs
	 * @throws CGSystemException
	 */
	BAMaterialRateConfigDO searchBAMaterialRateDtls(BAMaterialRateConfigTO to)
			throws CGSystemException;

	/**
	 * To update valid to date for previous tariff
	 * @param to
	 * @return {boolean}
	 * @throws CGSystemException
	 */
	boolean updateValidToDate(BAMaterialRateConfigTO to)
			throws CGSystemException;
	
	/**
	 * To search already renewal BA material rate details 
	 * @param to
	 * @return baMtrlRateConfigDOs
	 * @throws CGSystemException
	 */
	BAMaterialRateConfigDO searchRenewedBAMaterialRateDtls(BAMaterialRateConfigTO to)
			throws CGSystemException;

}
