/**
 * 
 */
package com.ff.rate.configuration.rateConfiguration.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BACODChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;

/**
 * @author prmeher
 * 
 */
public interface BARateConfigurationService {

	/**
	 * Returns Stock Standard Types
	 * 
	 * @param typeName
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGBusinessException, CGSystemException;

	public List<RateSectorsTO> getRateSectorListForBARateConfiguration(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException;

	public List<RateWeightSlabsTO> getRateWeightSlabsList(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException;

	public SectorDO getSectorByCityCode(String cityCode)
			throws CGBusinessException, CGSystemException;

	public List<CustomerTypeDO> getBATypeList(String customerCodeBa)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO saveOrUpdateBARateConfiguration(
			BARateHeaderTO baRateHeader) throws CGBusinessException,
			CGSystemException;

	public BARateHeaderTO searchBARateConfiguration(Date fromDate, Date toDate,
			Integer cityId, Integer baTypeId, Integer courierProdductId,
			Integer priorityProductId) throws CGBusinessException,
			CGSystemException;

	public BARateHeaderTO getFixedChargesForCourier(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public List<InsuredByDO> getInsuredByDetails() throws CGBusinessException,
			CGSystemException;

	public BARateHeaderTO getRTOChargesForCourier(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO searchBARateConfigurationForPriorityProduct(
			Date stringToDDMMYYYYFormat, Date stringToDDMMYYYYFormat2,
			Integer cityId, Integer baType,
			Integer productCategoryIdForPriority, Integer headerId)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO getRTOChargesForPriority(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO getFixedChargesForPriority(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public String submitBaRateConfiguration(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public String isExistsBaRateConfiguration(Integer cityId, Integer baTypeId)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO searchRenewedBARateConfiguration(Integer cityId,
			Integer baTypeId, Integer courierProdductId,
			Integer priorityProdductId, Date toDate, Integer headerId, Map<String, Integer> prodCateMap, HttpSession session)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO searchBARateConfigurationByHeaderId(Integer headerId,
			Integer cityId, Integer baTypeId, Integer courierProductId,
			Integer priorityProductId) throws CGBusinessException,
			CGSystemException;

	public BARateHeaderTO saveOrUpdateFixedCharges(
			BARateHeaderTO baRateHeaderTO, List<BACODChargesTO> codList)
			throws CGBusinessException, CGSystemException;

	public BARateHeaderTO saveOrUpdateRTOCharges(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException;

	public RateCustomerCategoryTO getRateCustCategoryByCode(
			String rateCustomerCategoryCode) throws CGBusinessException,
			CGSystemException;

	public List<CodChargeTO> getDeclaredValueCodChargeForBA(
			String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException, CGBusinessException;

	/**
	 * To save or update BA rate configuration details
	 * 
	 * @param baRateHeader
	 * @param prodCateMap
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BARateHeaderTO _saveOrUpdateBARatesDtls(BARateHeaderTO baRateHeader,
			Map<String, Integer> prodCateMap, HttpSession session) throws CGBusinessException,
			CGSystemException;

	/**
	 * To search BA rates configuration details
	 * 
	 * @param headerId
	 * @param cityId
	 * @param baTypeId
	 * @param productId
	 * @param servicedOn
	 * @param prodCateMap
	 * @param session 
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BARateHeaderTO _searchBARatesDtls(Integer headerId, Integer cityId,
			Integer baTypeId, Integer productId, String servicedOn,
			Map<String, Integer> prodCateMap, HttpSession session) throws CGBusinessException,
			CGSystemException;

	/**
	 * To search BA rates configuration details
	 * 
	 * @param cityId
	 * @param baTypeId
	 * @param productId
	 * @param servicedOn
	 * @param prodCateMap
	 * @param session 
	 * @return to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BARateHeaderTO _searchBARatesDtls(Integer cityId, Integer baTypeId,
			Integer productId, String servicedOn,
			Map<String, Integer> prodCateMap, HttpSession session) throws CGBusinessException,
			CGSystemException;
	
	
	/**
	 * Returns information about the logged in user
	 * @param request
	 * @return UserInfoTO
	 */
	public UserInfoTO getLoggedInUserDetails(HttpServletRequest request);
	
	/**
	 * Returns user id of logged in user
	 * @param request
	 * @return Integer
	 */
	public Integer getLoggedInUserIdToSaveInDatabase(HttpServletRequest request);

}
