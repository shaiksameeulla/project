package com.ff.rate.configuration.common.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.business.CustomerTypeTO;
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
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserTO;

public interface RateCommonService {

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
	 * @param custCode
	 * @inputparam
	 * @return List<RateVobSlabsTO>
	 * @throws CGSystemException
	 */

	List<RateVobSlabsTO> getRateVobSlabsList(String type, String custCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateWeightSlabsTO List This method will return RateWeightSlabsTO List
	 * 
	 * @param custCode
	 * @inputparam
	 * @return List<RateWeightSlabsTO>
	 * @throws CGSystemException
	 */

	List<RateWeightSlabsTO> getRateWeightSlabsList(String type, String custCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateSectorsTO List This method will return RateSectorsTO List
	 * 
	 * @param custCode
	 * @inputparam
	 * @return List<RateSectorsTO>
	 * @throws CGSystemException
	 */

	List<RateSectorsTO> getRateSectorsList(String type, String custCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateSectorsTO List This method will return RateSectorsTO List
	 * 
	 * @param custCode
	 * @inputparam
	 * @return List<RateSectorsTO>
	 * @throws CGSystemException
	 */

	List<RateSectorsTO> getRateConfigSectorsList(String type, String custCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get RateMinChargeableWeightTO List This method will return
	 * RateMinChargeableWeightTO List
	 * 
	 * @param custCode
	 * @inputparam
	 * @return List<RateMinChargeableWeightTO>
	 * @throws CGSystemException
	 */

	List<RateMinChargeableWeightTO> getRateMinChrgWtList(String type,
			String custCode) throws CGSystemException, CGBusinessException;

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
	 * @throws CGSystemException
	 */

	EmployeeTO getEmployeeDetails(String empCode) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param loggedInRHO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	OfficeTO getOfficeDetails(Integer loggedInRHO) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param rhoCityTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<CityTO> getCitiesByCity(CityTO rhoCityTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param cityTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	CityTO getCity(CityTO cityTO) throws CGSystemException, CGBusinessException;

	/**
	 * @param string
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<StockStandardTypeTO> getStandardType(String string)
			throws CGSystemException, CGBusinessException;

	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<InsuredByTO> getRiskSurchargeInsuredBy() throws CGSystemException,
			CGBusinessException;

	/**
	 * @param sequenceGeneratorConfigTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	SequenceGeneratorConfigTO getGeneratedSequence(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param loginCityId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param officeTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To block or unblock customer
	 * 
	 * @param customerId
	 * @param status
	 * @return boolean
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	boolean blockOrUnblockCustomer(Integer customerId, String status)
			throws CGSystemException, CGBusinessException;

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
	ZoneTO getZoneByZoneId(Integer zoneId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getRHOOfficesByUserId(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param ofcList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCityListOfReportedOffices(Integer ofcList)
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
	 * To update Customer Pan No.
	 * 
	 * @param panNo
	 * @param customerId
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateCustPanNo(String panNo, Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To update Customer Tan No.
	 * 
	 * @param tanNo
	 * @param customerId
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateCustTanNo(String tanNo, Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To update contract status
	 * 
	 * @param rateContractTO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateContractStatus(RateContractTO rateContractTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To Get Rate Sector List
	 * 
	 * @param rateProductCategory
	 * @param rateCustomerCategory
	 * @return rateSectorsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RateSectorsTO> getRateSectorList(String rateProductCategory,
			String rateCustomerCategory) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get rate weight slab list
	 * 
	 * @param rateProductCategory
	 * @param rateCustomerCategory
	 * @return RateWeightSlabsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RateWeightSlabsTO> getRateWeightSlabList(
			String rateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get insured by details
	 * 
	 * @return insuredByTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<InsuredByTO> getInsuarnceBy() throws CGBusinessException,
			CGSystemException;

	/**
	 * @param string
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	StateTO getStateByCode(String string) throws CGBusinessException,
			CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGBusinessException, CGSystemException;

	/**
	 * @param string
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CountryTO getCountryByCode(String string) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param stateId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateTaxComponentTO> loadDefaultRateTaxComponentValue(Integer stateId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param empId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * Gets the item type as map.
	 *
	 * @return the item type as map
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<Integer,String> getItemTypeAsMap() 
			throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the item by type as map.
	 *
	 * @param itemTypeId the item type id
	 * @return the item by type as map
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<Integer,String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException,CGBusinessException;
	
	/**
	 *
	 * @param itemTypeId the item type id
	 * @param itemId the item id
	 * @return the item by item type and item id
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	ItemTO getItemByItemTypeAndItemId(Integer itemTypeId,Integer itemId) 
			throws CGSystemException, CGBusinessException;

	List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGSystemException, CGBusinessException;

	List<RateTaxComponentTO> loadDefaultRateTaxComponentValueForConfiguration(
			Integer stateId) throws CGSystemException, CGBusinessException;

	Integer getCustomerTypeId(String quotationType, String indCode, String contractType, String businessType) 
			throws CGSystemException, CGBusinessException;

	BookingTypeConfigTO getBookingTypeConfigVWDeno() throws CGSystemException, CGBusinessException;
	/**
	 * To get origin sector by region Id
	 * @param regionId
	 * @return sectorDO
	 * @throws CGSystemException
	 */
	SectorTO getSectorByRegionId(Integer regionId) 
			throws CGSystemException, CGBusinessException;

	List<OfficeTO> getAllRegionalOffices() throws CGSystemException, CGBusinessException;

	OfficeTO getOfficeByUserId(Integer userId) throws CGSystemException, CGBusinessException;

	void sendEmailForBlockContractCustomer(RateContractTO rateTO) throws CGSystemException, CGBusinessException;

	List<StateTO> getStatesList() throws CGSystemException, CGBusinessException;

	List<CityTO> getCityListByStateId(Integer stateId) throws CGSystemException, CGBusinessException;
	
	List<CodChargeTO> getDeclaredValueCodCharge(String configuredType) throws CGBusinessException, CGSystemException;
	
	OfficeTO getOfficeByOfcCode(String ofcCode) throws CGBusinessException, CGSystemException;
	
	EmployeeTO getEmployeeByEmpCode(String empCode) throws CGBusinessException, CGSystemException;
	
	UserTO getUserByUserName(String userName) throws CGBusinessException, CGSystemException;
	
	RateCustomerCategoryTO getRateCustCategoryByCode(String rateCustomerCategoryCode) 
			throws CGBusinessException, CGSystemException;
	
	RateIndustryTypeTO getRateIndustryTypeByCode(String rateIndustryTypeCode) 
			throws CGBusinessException,	CGSystemException;
	
	CustomerGroupTO getCustomerGroupByCode(String customerGroupCode)
			throws CGBusinessException, CGSystemException;
	
	CustomerTypeTO getCustomerTypeByCode(String customerTypeCode)
			throws CGBusinessException, CGSystemException;
	
	RateIndustryCategoryTO getIndustryCategoryByCode(String indCatCode) 
			throws CGBusinessException, CGSystemException;
	
	DepartmentTO getDepartmentByCode(String deptCode)
			throws CGBusinessException, CGSystemException;
	
	List<CodChargeTO> getDeclaredValueCodChargeForBA(
			String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException,CGBusinessException;
	
	/**
	 * To get weight slab details by weight slab category
	 * 
	 * @param wtSlabCate
	 * @return wtSlabTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<WeightSlabTO> getWeightSlabByWtSlabCate(String wtSlabCate)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get weight slab details by weight slab category
	 * 
	 * @param wtSlabCate
	 * @param endWeight
	 * @return wtSlabTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<WeightSlabTO> getWeightSlabByWtSlabCateAndEndWt(String wtSlabCate,
			Double endWeight) throws CGBusinessException, CGSystemException;

	List<OfficeTO> getAllOfficesByCityAndOfcTypeCode(Integer cityId,
			String ofcTypeCode) throws CGBusinessException, CGSystemException;
	

	
}
