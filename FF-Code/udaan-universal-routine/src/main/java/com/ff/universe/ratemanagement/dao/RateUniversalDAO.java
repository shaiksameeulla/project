/**
 * 
 */
package com.ff.universe.ratemanagement.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.ratemanagement.masters.AccountGroupSapDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateCustomerProductCatMapDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RateVobSlabsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ba.BAMaterialRateDetailsDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.to.ratemanagement.operations.StockRateTO;


/**
 * @author rmaladi
 *
 */
public interface RateUniversalDAO {

	/** 
     * Get RateIndustryCategory List
     * This method will return  RateIndustryCategory List 
     * @inputparam 
     * @return  	List<RateIndustryCategoryDO>
     * @throws CGSystemException
	 */
	
	List<RateIndustryCategoryDO> getRateIndustryCategoryList(String applicable) throws CGSystemException;
	
	/** 
     * Get RateProductCategory List
     * This method will return  RateProductCategoryDO List 
     * @inputparam 
     * @return  	List<RateProductCategoryDO>
     * @throws CGSystemException
	 */
	
	List<RateProductCategoryDO> getRateProductCategoryList(String type) throws CGSystemException;
	
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
	 * @param custCatCode 
     * @inputparam 
     * @return  	List<RateVobSlabsDO>
     * @throws CGSystemException
	 */
	
	List<RateVobSlabsDO> getRateVobSlabsList(String type, String custCatCode) throws CGSystemException;
	
	/** 
     * Get RateWeightSlabsDO List
     * This method will return  RateWeightSlabsDO List 
	 * @param custCatCode 
     * @inputparam 
     * @return  	List<RateWeightSlabsDO>
     * @throws CGSystemException
	 */
	
	List<RateWeightSlabsDO> getRateWeightSlabsList(String type, String custCatCode) throws CGSystemException;
	
	/** 
     * Get RateSectorsDO List
     * This method will return  RateSectorsDO List 
	 * @param custCatCode 
     * @inputparam 
     * @return  	List<RateSectorsDO>
     * @throws CGSystemException
	 */
	
	List<RateSectorsDO> getRateSectorsList(String type, String custCatCode) throws CGSystemException;
	
	/** 
     * Get RateSectorsDO List
     * This method will return  RateSectorsDO List 
	 * @param custCatCode 
     * @inputparam 
     * @return  	List<RateSectorsDO>
     * @throws CGSystemException
	 */
	
	List<RateSectorsDO> getRateConfigSectorsList(String type, String custCatCode) throws CGSystemException;
	
	/** 
     * Get RateMinChargeableWeightDO List
     * This method will return  RateMinChargeableWeightDO List 
	 * @param custCatCode 
     * @inputparam 
     * @return  	List<RateMinChargeableWeightDO>
     * @throws CGSystemException
	 */
	
	List<RateMinChargeableWeightDO> getRateMinChrgWtList(String type, String custCatCode) throws CGSystemException;
	
	/** 
     * Get RateCustomerProductCatMapDO List
     * This method will return  RateCustomerProductCatMapDO List 
     * @inputparam 
     * @return  	List<RateCustomerProductCatMapDO>
     * @throws CGSystemException
	 */
	
	List<RateCustomerProductCatMapDO> getRateCustomerProductCatMapList() throws CGSystemException;

	/**
	 * @param typeName
	 * @return
	 * @throws CGSystemException
	 */
	List<StockStandardTypeDO> getStandardType(String typeName) throws CGSystemException;
	
	/**
	 * @param rateIndustryTypeCode
	 * @return
	 * @throws CGSystemException
	 */
	RateIndustryTypeDO getIndustryTypeByCode(String rateIndustryTypeCode) throws CGSystemException;

	/**
	 * @param rateCustomerCategoryCode
	 * @return
	 * @throws CGSystemException
	 */
	RateCustomerCategoryDO getRateCustCategoryGrpByCode(String rateCustomerCategoryCode) throws CGSystemException;

	/**
	 * @param customerGroupCode
	 * @return
	 * @throws CGSystemException
	 */
	CustomerGroupDO getCustomerGroupByCode(String customerGroupCode)throws CGSystemException;

	/**
	 * @param stdTypeCode
	 * @return
	 * @throws CGSystemException
	 */
	StockStandardTypeDO getBillingCycleByStdCode(String stdTypeCode)throws CGSystemException;

	/**
	 * Gets the shipped to codes by customer id.
	 *
	 * @param customerId the customer id
	 * @return the shipped to codes by customer id
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGSystemException;

	/**
	 * Gets the rate contracts by customer ids.
	 *
	 * @param customerIds the customer ids
	 * @return the rate contracts by customer ids
	 * @throws CGSystemException the cG system exception
	 */
	List<RateContractDO> getRateContractsByCustomerIds(List<Integer> customerIds)
			throws CGSystemException;

	/**
	 * @param customerTypeCode
	 * @return
	 * @throws CGSystemException
	 */
	CustomerTypeDO getCustomerTypeByCode(String customerTypeCode)throws CGSystemException;

	/**
	 * @param customerGroup
	 * @return
	 * @throws CGSystemException
	 */
	AccountGroupSapDO getRateCustCategoryByCustGroup(String customerGroup)throws CGSystemException;  
	
	// For BillCollection
	/**
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 */
	List<CustomerDO> getCustomersByPickupDeliveryLocation(Integer officeId)throws CGSystemException;

	RateContractDO getContractDtlsByContractNo(String contractNo) throws CGSystemException;

	List<ContractPaymentBillingLocationDO> getContractPayBillingLocationDtlsByRateContractId(Integer rateContractId) throws CGSystemException;

	/**
	 * Gets the bA material rate details by material.
	 *
	 * @param stockRateTo the stock rate to
	 * @return the bA material rate details by material
	 * @throws CGSystemException the cG system exception
	 */
	List<BAMaterialRateDetailsDO> getBAMaterialRateDetailsByMaterial(
			StockRateTO stockRateTo) throws CGSystemException;

	/**
	 * Gets the tax components.
	 *
	 * @param stateId the state id
	 * @param currentDate the current date
	 * @param taxGroup the tax group
	 * @return the tax components
	 */
	List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate);

	List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContrat(Integer customerId) throws CGSystemException;

	ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(Integer pickupDlvLocId) throws CGSystemException;
	
	/**
	 * To get pickup delivery location by contract id.
	 * 
	 * @param contractId
	 * @return pickupLocationDO
	 * @throws CGSystemException
	 */
	PickupDeliveryLocationDO getPickupDlvLocationByContractId(Integer contractId)
			throws CGSystemException;
	
	List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratByOfficeId(Integer officeId) throws CGSystemException;  

	BookingTypeConfigDO getBookingTypeConfigVWDeno() throws CGSystemException;
	
	/*Kamal for outstanding report*/
	List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratWrapperDOByOfficeId(Integer officeId) throws CGSystemException;
	
	RateIndustryCategoryDO getIndustryCategoryByCode(String indCatCode)throws CGSystemException;
	
	/**
	 * To get weight slab details by weight slab category
	 * 
	 * @param wtSlabCate
	 * @return wtSlabDOs
	 * @throws CGSystemException
	 */
	List<WeightSlabDO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGSystemException;
	
	/**
	 * To get weight slab details by weight slab category and end weight
	 * 
	 * @param wtSlabCate
	 * @return wtSlabDOs
	 * @throws CGSystemException
	 */
	List<WeightSlabDO> getWeightSlabByWtSlabCateAndEndWt(String wtSlabCate,Double endWeight)
			throws CGSystemException;
	
}
