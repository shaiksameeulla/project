/**
 * 
 */
package com.ff.universe.ratemanagement.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.geography.CityTO;
import com.ff.geography.CountryTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateCustomerProductCatMapTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserTO;

/**
 * @author rmaladi
 * 
 */
public interface RateUniversalService {

	/**
	 * Get RateIndustryCategory List This method will return
	 * RateIndustryCategory List
	 * 
	 * @inputparam
	 * @return List<RateIndustryCategoryTO>
	 * @throws CGSystemException
	 */

	List<RateIndustryCategoryTO> getRateIndustryCategoryList(String applicable)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateProductCategoryTO List This method will return
	 * RateProductCategoryTO List
	 * 
	 * @inputparam
	 * @return List<RateProductCategoryTO>
	 * @throws CGSystemException
	 */

	List<RateProductCategoryTO> getRateProductCategoryList(String type)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateCustomerCategoryTO List This method will return
	 * RateCustomerCategoryTO List
	 * 
	 * @inputparam
	 * @return List<RateCustomerCategoryTO>
	 * @throws CGSystemException
	 */

	List<RateCustomerCategoryTO> getRateCustomerCategoryList()
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateVobSlabsTO List This method will return RateVobSlabsTO List
	 * 
	 * @param custCatCode
	 * @inputparam
	 * @return List<RateVobSlabsTO>
	 * @throws CGSystemException
	 */

	List<RateVobSlabsTO> getRateVobSlabsList(String type, String custCatCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateWeightSlabsTO List This method will return RateWeightSlabsTO List
	 * 
	 * @param custCatCode
	 * @inputparam
	 * @return List<RateWeightSlabsTO>
	 * @throws CGSystemException
	 */

	List<RateWeightSlabsTO> getRateWeightSlabsList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException;

	/**
	 * Get RateSectorsTO List This method will return RateSectorsTO List
	 * 
	 * @param custCatCode
	 * @inputparam
	 * @return List<RateSectorsTO>
	 * @throws CGSystemException
	 */

	List<RateSectorsTO> getRateSectorsList(String type, String custCatCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateSectorsTO List This method will return RateSectorsTO List
	 * 
	 * @param custCatCode
	 * @inputparam
	 * @return List<RateSectorsTO>
	 * @throws CGSystemException
	 */

	List<RateSectorsTO> getRateConfigSectorsList(String type, String custCatCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateMinChargeableWeightTO List This method will return
	 * RateMinChargeableWeightTO List
	 * 
	 * @param custCatCode
	 * @inputparam
	 * @return List<RateMinChargeableWeightTO>
	 * @throws CGSystemException
	 */

	List<RateMinChargeableWeightTO> getRateMinChrgWtList(String type,
			String custCatCode) throws CGSystemException, CGBusinessException;

	/**
	 * Get RateCustomerProductCatMapTO List This method will return
	 * RateCustomerProductCatMapTO List
	 * 
	 * @inputparam
	 * @return List<RateCustomerProductCatMapTO>
	 * @throws CGSystemException
	 */

	List<RateCustomerProductCatMapTO> getRateCustomerProductCatMapList()
			throws CGSystemException, CGBusinessException;

	/**
	 * Get Employee details by Employee Code This method will return EmployeeTO
	 * object
	 * 
	 * @inputparam EmployeeCode Integer
	 * @return EmployeeTO Object
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	List<EmployeeTO> getEmployeeDetails(EmployeeTO employeeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param pincode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CityTO getCityByPincode(String pincode) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param zoneId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ZoneTO> getZoneByZoneId(Integer zoneId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param typeName
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<StockStandardTypeTO> getStandardType(String typeName)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param loggedInRHO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	OfficeTO getOfficeDetails(Integer loggedInRHO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param rhoCityTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCitiesByCity(CityTO rhoCityTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param cityTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CityTO getCity(CityTO cityTO) throws CGBusinessException, CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<InsuredByTO> getInsuarnceBy() throws CGBusinessException,
			CGSystemException;

	/**
	 * @param sequenceGeneratorConfigTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	SequenceGeneratorConfigTO getGeneratedSequence(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param loginCityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param officeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the shipped to codes by customer id.
	 * 
	 * @param customerId
	 *            the customer id
	 * @return the shipped to codes by customer id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<String> getShippedToCodesByCustomerId(final Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getRHOOfficesByUserId(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param rhoOfficeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the rate contracts by customer ids.
	 * 
	 * @param customerIds
	 *            the customer ids
	 * @return the rate contracts by customer ids
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RateContractTO> getRateContractsByCustomerIds(List<Integer> customerIds)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get pickup branch(s)
	 * 
	 * @param pincode
	 * @return the list of pickup branch(s)
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getPickupBranchsByPincode(String pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate pincode by pincodeId or pincode
	 * 
	 * @param pincode
	 * @return pincode
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	PincodeTO validatePincode(PincodeTO pincode) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets All regions.
	 * 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the cities by region.
	 * 
	 * @param regionTO
	 *            the region to
	 * @return the cities by region
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the State by Code
	 * 
	 * @param stateCode
	 *            the region to
	 * @return state
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public StateTO getStateByCode(String stateCode) throws CGBusinessException,
			CGSystemException;

	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGSystemException, CGBusinessException;

	/**
	 * @param empId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException;

	CountryTO getCountryByCode(String string) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item type as map.
	 * 
	 * @return the item type as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the item by type as map.
	 * 
	 * @param itemTypeId
	 *            the item type id
	 * @return the item by type as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the item by item type and item id.
	 * 
	 * @param itemTypeId
	 *            the item type id
	 * @param itemId
	 *            the item id
	 * @return the item by item type and item id
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException;

	List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the tax components.
	 * 
	 * @param city
	 *            the city
	 * @param currentDate
	 *            the current date
	 * @param taxGroup
	 *            the tax group
	 * @return the tax components
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<String, Double> getTaxComponents(CityTO city, Date currentDate)
			throws CGBusinessException;

	/**
	 * Calculate rate for ba material.
	 * 
	 * @param stockRateTo
	 *            the stock rate to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	void calculateRateForBAMaterial(StockRateTO stockRateTo)
			throws CGBusinessException, CGSystemException;

	CustomerTypeTO getCustomerTypeId(String custTypeCode)
			throws CGBusinessException, CGSystemException;

	BookingTypeConfigTO getBookingTypeConfigVWDeno()
			throws CGBusinessException, CGSystemException;

	// Kamal hassan

	List<PickupDeliveryContractWrapperDO> getPickUpDeliveryContratWrapperDOByOfficeId(
			Integer officeID) throws CGSystemException;

	List<OfficeTO> getAllRegionalOffices() throws CGBusinessException,
			CGSystemException;

	OfficeTO getOfficeByUserId(Integer userId) throws CGBusinessException,
			CGSystemException;

	List<StateTO> getStatesList() throws CGBusinessException, CGSystemException;

	List<CityTO> getCityListByStateId(Integer stateId)
			throws CGBusinessException, CGSystemException;

	OfficeTO getOfficeByOfcCode(String ofcCode) throws CGBusinessException,
			CGSystemException;

	EmployeeTO getEmployeeByEmpCode(String empCode) throws CGBusinessException,
			CGSystemException;

	UserTO getUserByUserName(String userName) throws CGBusinessException,
			CGSystemException;

	RateCustomerCategoryTO getRateCustCategoryByCode(
			String rateCustomerCategoryCode) throws CGBusinessException,
			CGSystemException;

	RateIndustryTypeTO getRateIndustryTypeByCode(String rateIndustryTypeCode)
			throws CGBusinessException, CGSystemException;

	CustomerGroupTO getCustomerGroupByCode(String customerGroupCode)
			throws CGBusinessException, CGSystemException;

	CustomerTypeTO getCustomerTypeByCode(String customerTypeCode)
			throws CGBusinessException, CGSystemException;

	RateIndustryCategoryTO getIndustryCategoryByCode(String indCatCode)
			throws CGBusinessException, CGSystemException;

	DepartmentTO getDepartmentByCode(String deptCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get weight slab details by weight slab category
	 * 
	 * @param wtSlabCate
	 * @return wtSlabCate
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<WeightSlabDO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get weight slab details by weight slab category
	 * 
	 * @param wtSlabCate
	 * @param endWeight
	 * @return wtSlabCate
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<WeightSlabDO> getWeightSlabByWtSlabCateAndEndWt(String wtSlabCate,
			Double endWeight) throws CGBusinessException, CGSystemException;

	List<OfficeTO> getAllOfficesByCityAndOfcTypeCode(Integer cityId,
			String ofcTypeCode) throws CGBusinessException, CGSystemException;
	
}
