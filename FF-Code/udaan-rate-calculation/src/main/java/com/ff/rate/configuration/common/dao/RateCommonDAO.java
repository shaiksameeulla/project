package com.ff.rate.configuration.common.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateCustomerProductCatMapDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RateVobSlabsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.to.ratemanagement.masters.RateContractTO;

public interface RateCommonDAO {

	
	/** 
     * Get RateIndustryCategory List
     * This method will return  RateIndustryCategory List 
     * @inputparam 
     * @return  	List<RateIndustryCategoryDO>
     * @throws CGSystemException
	 */
	
	List<RateIndustryCategoryDO> getRateIndustryCategoryList() throws CGSystemException;
	
	/** 
     * Get RateProductCategory List
     * This method will return  RateProductCategoryDO List 
     * @inputparam 
     * @return  	List<RateProductCategoryDO>
     * @throws CGSystemException
	 */
	
	List<RateProductCategoryDO> getRateProductCategoryList() throws CGSystemException;
	
	/** 
     * Get RateCustomerCategory List
     * This method will return  RateCustomerCategoryDO List 
     * @inputparam 
     * @return  	List<RateCustomerCategoryDO>
     * @throws CGSystemException
	 */
	
	List<RateCustomerCategoryDO> getRateCustomerCategoryList() throws CGSystemException;
	
	/** 
     * Get RateVobSlabs List
     * This method will return  RateVobSlabs List 
     * @inputparam 
     * @return  	List<RateVobSlabsDO>
     * @throws CGSystemException
	 */
	
	List<RateVobSlabsDO> getRateVobSlabsList() throws CGSystemException;
	
	/** 
     * Get RateWeightSlabsDO List
     * This method will return  RateWeightSlabsDO List 
     * @inputparam 
     * @return  	List<RateWeightSlabsDO>
     * @throws CGSystemException
	 */
	
	List<RateWeightSlabsDO> getRateWeightSlabsList() throws CGSystemException;
	
	/** 
     * Get RateSectorsDO List
     * This method will return  RateSectorsDO List 
     * @inputparam 
     * @return  	List<RateSectorsDO>
     * @throws CGSystemException
	 */
	
	List<RateSectorsDO> getRateSectorsList() throws CGSystemException;
	
	/** 
     * Get RateMinChargeableWeightDO List
     * This method will return  RateMinChargeableWeightDO List 
     * @inputparam 
     * @return  	List<RateMinChargeableWeightDO>
     * @throws CGSystemException
	 */
	
	List<RateMinChargeableWeightDO> getRateMinChrgWtList() throws CGSystemException;
	
	/** 
     * Get RateCustomerProductCatMapDO List
     * This method will return  RateCustomerProductCatMapDO List 
     * @inputparam 
     * @return  	List<RateCustomerProductCatMapDO>
     * @throws CGSystemException
	 */
	
	List<RateCustomerProductCatMapDO> getRateCustomerProductCatMapList() throws CGSystemException;
	
	/**
	 * To block or unblock customer
	 * 
	 * @param customerId
	 * @param status
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean blockOrUnblockCustomer(Integer customerId, String status) 
			throws CGSystemException;
	
	/**
	 * To update Customer Pan No.
	 * 
	 * @param panNo
	 * @param customerId
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean updateCustPanNo(String panNo, Integer customerId)
			throws CGSystemException;
	
	/**
	 * To update Customer Tan No.
	 * 
	 * @param tanNo
	 * @param customerId
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean updateCustTanNo(String tanNo, Integer customerId)
			throws CGSystemException;
	
	/**
	 * To update contract status
	 * 
	 * @param rateContractTO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean updateContractStatus(RateContractTO rateContractTO)
			throws CGSystemException;
	
	/**
	 * To Get Rate Sector List
	 * 
	 * @param rateProductCategory
	 * @param rateCustomerCategory
	 * @return rateSectorsDOs
	 * @throws CGSystemException
	 */
	public List<RateSectorsDO> getRateSectorList(String rateProductCategory, 
			String rateCustomerCategory) throws CGSystemException;
	
	/**
	 * To get rate weight slab list
	 * 
	 * @param rateProductCategory
	 * @param rateCustomerCategory
	 * @return rateWeightSlabsDOs
	 * @throws CGSystemException
	 */
	public List<RateWeightSlabsDO> getRateWeightSlabList(String rateProductCategory, 
			String rateCustomerCategory) throws CGSystemException;

	List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate, String taxGroup) throws CGSystemException;

	List<RateTaxComponentDO> getTaxComponentsForRateConfiguration(
			Integer stateId, Date currentDateWithoutTime)throws CGSystemException;
	/**
	 * To get origin sector by region Id
	 * @param regionId
	 * @return sectorDO
	 * @throws CGSystemException
	 */
	SectorDO getSectorByRegionId(Integer regionId) 
			throws CGSystemException;
	
	public RateContractDO getRateContractDetails(Integer rateContractId) throws CGSystemException;

	EmployeeDO getRegionalApprovalDetails(Integer regionId, Integer indCatId) throws CGSystemException;
	
	List<CodChargeDO> getDeclaredValueCodCharge(String configuredType)
			throws CGSystemException;
	
	public List<CodChargeDO> getDeclaredValueCodChargeForBA(String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException;
}
