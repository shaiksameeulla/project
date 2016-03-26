package com.ff.rate.configuration.rateConfiguration.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;

/**
 * @author hkansagr
 */

public interface CashRateConfigurationService {
	
	/**
	 * To save or update cash rate product(s) details
	 * 
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO saveOrUpdateCashRateProductDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To save or update cash rate fixed charges details
	 * 
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO saveOrUpdateFixedChrgsDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To save or update cash rate RTO charges details
	 * 
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO saveOrUpdateRTOChrgsDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To search cash rate product(s) details
	 * 
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO searchCashRateProductDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To search fixed charges details
	 * 
	 * @param productMapId
	 * @param productType 
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO searchFixedChrgsDtls(Integer productMapId, String productType) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To search RTO charges details
	 * 
	 * @param productMapId
	 * @param productType 
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO searchRTOChrgsDtls(Integer productMapId, String productType) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To submit cash rate config. details
	 * 
	 * @param cashRateHeaderId
	 * @param fromDateStr
	 * @param toDateStr
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean submitCashRateDtls(Integer cashRateHeaderId, String fromDateStr, String toDateStr) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To get origin sector by region id
	 * @param regionId
	 * @return sectorTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	SectorTO getOriginSectorByRegionId(Integer regionId) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To update cash rate configuration TO DATE after submit for renew 
	 * @param cashRateHeaderId
	 * @param toDate
	 * @return {boolean}
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateCashRateConfigToDate(Integer cashRateHeaderId, String toDate) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To get current period cash configuration
	 * @param to
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderTO getCurrentPeriodCashConfig(CashRateConfigHeaderTO to) 
			throws CGBusinessException,	CGSystemException;
	
}
