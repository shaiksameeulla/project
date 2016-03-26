package com.ff.rate.configuration.rateConfiguration.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigAdditionalChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigFixedChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigRTOChargesDO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;

/**
 * @author hkansagr
 */

public interface CashRateConfigurationDAO {

	/**
	 * To save or update cash rate product details
	 * 
	 * @param domain
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateCashRateProductDtls(CashRateConfigHeaderDO domain) 
			throws CGSystemException;
	
	/**
	 * To check whether cash rate configuration is already exist or not
	 * @param to
	 * @return {boolean}
	 * @throws CGSystemException
	 */
	boolean isCashRateConfigExist(CashRateConfigHeaderTO to)
			throws CGSystemException;
	
	/**
	 * To save or update cash rate fixed charges details
	 * 
	 * @param fixChrgDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateFixedChrgsDtls(Set<CashRateConfigFixedChargesDO> fixChrgDOs) 
			throws CGSystemException;
	
	/**
	 * To save or update cash rate additional charges details
	 * 
	 * @param additionalChrgDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateAdditionalChrgsDtls(Set<CashRateConfigAdditionalChargesDO> additionalChrgDOs) 
			throws CGSystemException;
	
	/**
	 * To save or update cash rate rto charges details
	 * 
	 * @param rtoChrgDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateRTOChrgsDtls(Set<CashRateConfigRTOChargesDO> rtoChrgDOs) 
			throws CGSystemException;
	
	/**
	 * To search cash rate product(s) details
	 * 
	 * @param domain
	 * @return domain
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderDO searchCashRateProductDtls(CashRateConfigHeaderDO domain) 
			throws CGSystemException;
	
	/**
	 * To search fixed charges details 
	 * 
	 * @param productMapId
	 * @return fixedChrgsDO
	 * @throws CGSystemException
	 */
	CashRateConfigFixedChargesDO searchFixedChrgsDtls(Integer productMapId)
			throws CGSystemException;
	
	/**
	 * To search additional charges details
	 * 
	 * @param productMapId
	 * @return additionalChrgsDOs
	 * @throws CGSystemException
	 */
	List<CashRateConfigAdditionalChargesDO> searchAdditionalChrgsDtls(
			Integer productMapId) throws CGSystemException;
	
	/**
	 * To search RTO charges details 
	 * 
	 * @param productMapId
	 * @return rtoChrgsDO
	 * @throws CGSystemException
	 */
	CashRateConfigRTOChargesDO searchRTOChrgsDtls(Integer productMapId)
			throws CGSystemException;
	
	/**
	 * To submit cash rate configuration details
	 * 
	 * @param cashRateHeaderId
	 * @param fromDateStr
	 * @param toDateStr
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean submitCashRateDtls(Integer cashRateHeaderId,String fromDateStr, String toDateStr) 
			throws CGSystemException;
	
	/**
	 * To delete fixed charges details before save
	 * 
	 * @param productMapIds
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean deleteFixedChrgs(List<Integer> productMapIds)
			throws CGSystemException;
	
	/**
	 * To delete additional charges details before save
	 * 
	 * @param productMapIds
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean deleteAdditionalChrgs(List<Integer> productMapIds)
			throws CGSystemException;
	
	/**
	 * To delete RTO charges details before save
	 * 
	 * @param productMapIds
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean deleteRTOChrgs(List<Integer> productMapIds)
			throws CGSystemException;
	
	/**
	 * To get origin sector by region Id
	 * @param regionId
	 * @return sectorDO
	 * @throws CGSystemException
	 */
	SectorDO getOriginSectorByRegionId(Integer regionId) 
			throws CGSystemException;
	
	/**
	 * To search cash rate product(s) details - for Renew
	 * 
	 * @param domain
	 * @param dt
	 * @return domain
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderDO searchCashRateProductDtlsForRenew(CashRateConfigHeaderDO domain,
			Date dt) throws CGSystemException;
	
	/**
	 * To update cash rate configuration TO DATE after submit of renew
	 * @param cashRateHeaderId
	 * @param toDate
	 * @return {boolean}
	 * @throws CGSystemException
	 */
	boolean updateCashRateConfigToDate(Integer cashRateHeaderId, String toDate) 
			throws CGSystemException;

	/**
	 * To get from date by cash rate header Id
	 * @param to
	 * @return {Date} - fromDate
	 * @throws CGSystemException
	 */
	Date getFromDateByCashRateHeaderId(CashRateConfigHeaderTO to) 
			throws CGSystemException;
	
	/**
	 * To get current period cash rate configuration
	 * @param to
	 * @return cashRateConfigHeaderDO
	 * @throws CGSystemException
	 */
	CashRateConfigHeaderDO getCurrentPeriodCashConfig(CashRateConfigHeaderTO to)
			throws CGSystemException;

	CashRateConfigHeaderDO getCashRateConfigDetails(Integer headerId)
			throws CGSystemException;

	CashRateConfigHeaderDO saveOrUpdateCashRateConfig(CashRateConfigHeaderDO domain)
			throws CGSystemException;
}
