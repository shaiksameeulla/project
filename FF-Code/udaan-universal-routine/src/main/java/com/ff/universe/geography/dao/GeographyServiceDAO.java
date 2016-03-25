package com.ff.universe.geography.dao;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.CountryDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.geography.PincodeProductServiceabilityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.geography.ZoneDO;
import com.ff.domain.organization.BranchPincodeServiceabilityDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;

/**
 * The Interface GeographyServiceDAO.
 */
public interface GeographyServiceDAO {

	/**
	 * Get the City details by Pincode.
	 * 
	 * @param Pincode
	 *            the pincode
	 * @return CityDO object
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam Pincode
	 */
	CityDO getCity(String Pincode) throws CGSystemException;

	/**
	 * get the List of Pincode details by PincodeTO object.
	 * 
	 * @param pincode
	 *            the pincode
	 * @return List<PincodeDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam PincodeTO
	 */
	List<PincodeDO> validatePincode(PincodeTO pincode) throws CGSystemException;

	/**
	 * get the Pincode serviceability details by pinCode and office.
	 * 
	 * @param pinCode
	 *            the pin code
	 * @param officeId
	 *            the office id
	 * @return PincodeDO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam pinCode String
	 * @inputparam officeId String
	 */
	PincodeDO validatePincodeServiceability(String pinCode, Integer officeId)
			throws CGSystemException;

	/**
	 * get the Pincode Product serviceability details by pinCode, city and
	 * consgSeries.
	 * 
	 * @param pinCode
	 *            the pin code
	 * @param cityId
	 *            the city id
	 * @param consgSeries
	 *            the consg series
	 * @return PincodeProductServiceabilityDO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam pinCode String
	 * @inputparam cityId String
	 * @inputparam consgSeries String
	 */
	List<PincodeProductServiceabilityDO> getPincodeDlvTimeMaps(String pinCode,
			Integer cityId, String consgSeries) throws CGSystemException;

	/**
	 * Get Region list This method will return Regions List.
	 * 
	 * @return List<RegionDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam
	 */
	List<RegionDO> getAllRegions() throws CGSystemException;

	/*
	 * public List<CityDO> getCitiesByRegion(RegionTO regionTO) throws
	 * CGSystemException;
	 */

	/**
	 * Get Transshipment Cities List By Region details This method will return
	 * List of Transshipment Cities.
	 * 
	 * @param regionTO
	 *            the region to
	 * @return List<CityDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam RegionTO object
	 */
	public List<CityDO> getTranshipmentCitiesByRegion(RegionTO regionTO)
			throws CGSystemException;

	/**
	 * Get City Details By City This method will return City Object.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return CityDO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam CityTO object
	 */
	public CityDO getCity(CityTO cityTO) throws CGSystemException;

	/**
	 * check whether pincode Serviceability available for given pincode details
	 * This method will return True or false.
	 * 
	 * @param pincodeTO
	 *            the pincode to
	 * @return boolean (true/false)
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam PincodeTO object
	 */
	public boolean pincodeServiceabilityByCity(PincodeTO pincodeTO)
			throws CGSystemException;

	/**
	 * Get City list This method will return City List.
	 * 
	 * @return List<CityDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam
	 */
	public List<CityDO> getAllCities() throws CGSystemException;

	/**
	 * Get List of City Details By City This method will return List of City
	 * Objects.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return List<CityDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam CityTO object
	 */
	public List<CityDO> getCitiesByCity(CityTO cityTO) throws CGSystemException;

	/**
	 * Get List of Cities By List of City Ids This method will return List of
	 * City Objects.
	 * 
	 * @param cityTOList
	 *            the city to list
	 * @return List<CityDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam CityTO List
	 */
	public List<CityDO> getAllCitiesByCityIds(List<CityTO> cityTOList)
			throws CGSystemException;

	/**
	 * check whether the Region exist or not by the region code This method will
	 * return either Region is exist ot not.
	 * 
	 * @param regionCode
	 *            the region code
	 * @return boolean (True/False)
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam regionCode String
	 */
	public boolean isRegionExists(String regionCode) throws CGSystemException;

	/**
	 * check the pincode product serviceability.
	 * 
	 * @param pincodeProdServicebility
	 *            the pincode prod servicebility
	 * @return boolean (True/False)
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException 
	 * @inputparam PincodeServicabilityTO object
	 */
	public boolean isPincodeServicedByProduct(
			PincodeServicabilityTO pincodeProdServicebility)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get List of City Details By List of Offices This method will return List
	 * of City Objects.
	 * 
	 * @param officeTOList
	 *            the office to list
	 * @return List<CityDO>
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam OfficeTO List
	 */
	public List<CityDO> getCitiesByOffices(List<OfficeTO> officeTOList)
			throws CGSystemException;

	/**
	 * Gets the pincode by city id.
	 * 
	 * @param cityId
	 *            the city id
	 * @return the pincode by city id
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	PincodeDO getPincodeByCityId(Integer cityId) throws CGSystemException;

	/**
	 * Get Region By id or name This method will return RegionTO.
	 * 
	 * @param regionCode
	 *            the region code
	 * @param regionId
	 *            the region id
	 * @return RegionTO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam String regionCode,Integer regionId
	 */
	public RegionDO getRegionByIdOrName(String regionCode, Integer regionId)
			throws CGSystemException;

	/**
	 * Get State by StateId.
	 * 
	 * @param cityId
	 *            the city id
	 * @return StateTO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @inputparam Integer cityId
	 */
	StateDO getState(Integer cityId) throws CGSystemException;

	/**
	 * Gets the serviced city by transshipment city.
	 * 
	 * @param transCityId
	 *            the trans city id
	 * @return the serviced city by transshipment city
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException;

	/**
	 * gets the Pincode on the basis of PincodeId
	 * 
	 * @param pincodeId
	 * @return
	 * @throws CGSystemException
	 */
	PincodeDO getPincode(Integer pincodeId) throws CGSystemException;

	/**
	 * @param pincodeId
	 * @return
	 * @throws CGSystemException
	 */
	CityDO getCityByPincodeId(Integer pincodeId) throws CGSystemException;

	/**
	 * @param cityName
	 * @return
	 * @throws CGSystemException
	 */
	CityDO getCityByName(String cityName) throws CGSystemException;

	/**
	 * @param departmentName
	 * @return
	 * @throws CGSystemException
	 */
	DepartmentDO getDepartmentByName(String departmentName)
			throws CGSystemException;

	/**
	 * @param regionCode
	 * @return
	 * @throws CGSystemException
	 */
	RegionDO getRegionByReportingRHOCode(String regionCode)
			throws CGSystemException;

	/**
	 * @param zoneId
	 * @return
	 * @throws CGSystemException
	 */
	List<ZoneDO> getZoneByZoneId(Integer zoneId) throws CGSystemException;

	/**
	 * @param rhoOfficeId
	 * @return
	 * @throws CGSystemException
	 */
	List<CityDO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGSystemException
	 */
	List<CityDO> getCityListOfAssignedOffices(Integer userId)
			throws CGSystemException;

	/**
	 * @param stateCode
	 * @return
	 * @throws CGSystemException
	 */
	public StateDO getStateByCode(String stateCode) throws CGSystemException;

	/**
	 * @param string
	 * @return
	 * @throws CGSystemException
	 */
	CountryDO getCountryByCode(String string) throws CGSystemException;

	public Integer getOfficeIdByPincode(Integer pincodeId)
			throws CGSystemException;

	List<CityDO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGSystemException;

	/**
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 */
	CityDO getCityByOfficeId(Integer officeId) throws CGSystemException;

	ZoneDO getZoneCodeByCityId(Integer cityId) throws CGSystemException;

	public PincodeProductServiceabilityDO getPincodeProdServiceabilityDtls(
			Integer pincodeProductMapId) throws CGSystemException,
			CGBusinessException;

	List<BranchPincodeServiceabilityDO> getServicableOfficeIdByPincode(
			Integer pincodeId) throws CGSystemException;

	List<OfficeDO> getAllOfficeByOfficeIds(List<OfficeTO> officeTO)
			throws CGSystemException;

	/**
	 * Gets the pincode product serviceability.
	 *
	 * @param pincodeId the pincode id
	 * @param productSeries the product series
	 * @return the pincode product serviceability
	 * @throws CGSystemException the cG system exception
	 */
	List<Integer> isValidPincodeProductServiceability(
			Integer pincodeId, Integer productId) throws CGSystemException;

	/**
	 * Gets the branch dtls for pincode service by pincode.
	 *
	 * @param pincodeId the pincode id
	 * @return the branch dtls for pincode service by pincode
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeDO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId)
			throws CGSystemException;

	List<?> getAllPincodeAsMap() throws CGSystemException;

	List<CityDO> getCitiesByRegion(Integer regionId) throws CGSystemException;

	List<StateDO> getStatesList() throws CGSystemException;

	List<CityDO> getCityListByStateId(Integer stateId) throws CGSystemException;

	/**
	 * Gets the all branches.
	 *
	 * @return the all branches
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getAllBranchesAsMap() throws CGSystemException;
	
	/**
	 * Gets the all rho list as map.
	 *
	 * @return the all rho list as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getAllRHOListAsMap() throws CGSystemException;

	/**
	 * Gets the all rho and co list as map.
	 *
	 * @return the all rho and co list as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getAllRHOAndCOListAsMap() throws CGSystemException;

	/**
	 * Gets the all cities by rho office as map.
	 *
	 * @param rhoOfficeid the rho officeid
	 * @return the all cities by rho office as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getAllCitiesByRHOOfficeAsMap(Integer rhoOfficeid)
			throws CGSystemException;

	/**
	 * Gets all the sectors
	 * 
	 * @return
	 * @throws CGSystemException
	 */
	List<SectorDO> getSectors() throws CGSystemException;
	
	/**
	 * Gets all the pincodes as a list
	 * 
	 * @return all pincodes as list
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<?> getAllPincodeAsMapWithoutStatus() throws CGSystemException;
	
	/**
	 * Returns the list containing a description of all the required products
	 * 
	 * @param pincodeId
	 * @return List<String>
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<String> getProductsDescMappedToPincode(Integer pincodeId) throws CGSystemException, CGBusinessException;
	
	/**
	 * Returns a list of pincodes in the form pincode~city_name
	 * 
	 * @return List<String>
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<String> getPincodeAndCityListForAutoComplete() throws CGSystemException, CGBusinessException;

}
