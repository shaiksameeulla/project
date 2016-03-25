package com.ff.universe.geography.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.geography.CountryTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.masters.SectorTO;

public interface GeographyCommonService {

	/**
	 * Get the City details by Pincode
	 * 
	 * @inputparam Pincode
	 * @return CityTO object
	 * @throws CGSystemException
	 */
	public CityTO getCity(String Pincode) throws CGBusinessException,
			CGSystemException;

	/**
	 * get the List of Pincode details by PincodeTO object
	 * 
	 * @inputparam PincodeTO
	 * @return List<PincodetO>
	 * @throws CGSystemException
	 */
	public PincodeTO validatePincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * get the Pincode serviceability details by pinCode and office
	 * 
	 * @inputparam pinCode String
	 * @inputparam officeId String
	 * @return PincodeTO
	 */
	public PincodeTO validatePincodeServiceability(String pinCode,
			Integer officeId) throws CGSystemException, CGBusinessException;

	/**
	 * get the Pincode Product serviceability details by pinCode, city and
	 * consgSeries
	 * 
	 * @inputparam pinCode String
	 * @inputparam cityId String
	 * @inputparam consgSeries String
	 * @return PincodeProductServiceabilityTO
	 */
	public List<PincodeProductServiceabilityTO> getPincodeDlvTimeMaps(
			String pinCode, Integer cityId, String consgSeries)
			throws CGSystemException;

	/**
	 * Get Region list This method will return Regions List
	 * 
	 * @inputparam
	 * @return List<RegionTO>
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/*
	 * public List<CityTO> getCitiesByRegion(RegionTO regionTO) throws
	 * CGBusinessException, CGSystemException;
	 */

	/**
	 * Get Transshipment Cities List By Region details This method will return
	 * List of Transshipment Cities
	 * 
	 * @inputparam RegionTO object
	 * @return List<CityTO>
	 */
	public List<CityTO> getTranshipmentCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get City Details By City This method will return City Object
	 * 
	 * @inputparam CityTO object
	 * @return CityTO
	 */
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * check whether pincode Serviceability available for given pincode details
	 * This method will return True or false
	 * 
	 * @inputparam PincodeTO object
	 * @return boolean (true/false)
	 */
	public boolean pincodeServiceabilityByCity(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get City list This method will return City List
	 * 
	 * @inputparam
	 * @return List<CityTO>
	 */
	public List<CityTO> getAllCities() throws CGBusinessException,
			CGSystemException;

	/**
	 * Get List of City Details By City This method will return List of City
	 * Objects
	 * 
	 * @inputparam CityTO object
	 * @return List<CityTO>
	 */
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get List of Cities By List of City Ids This method will return List of
	 * City Objects
	 * 
	 * @inputparam CityTO List
	 * @return List<CityTO>
	 */
	public List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException;

	/**
	 * check whether the Region exist or not by the region code This method will
	 * return either Region is exist ot not
	 * 
	 * @inputparam regionCode String
	 * @return boolean (True/False)
	 */
	public boolean isRegionExists(String regionCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * check the pincode product serviceability
	 * 
	 * @inputparam PincodeServicabilityTO object
	 * @return boolean (True/False)
	 */
	public boolean isPincodeServicedByProduct(
			PincodeServicabilityTO pincodeProdServicebility)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get List of City Details By List of Offices This method will return List
	 * of City Objects
	 * 
	 * @inputparam OfficeTO List
	 * @return List<CityDO>
	 */
	List<CityTO> getCitiesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException;

	public PincodeTO getPincodeByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get Region By id or name This method will return RegionTO
	 * 
	 * @inputparam String regionCode,Integer regionId
	 * @return RegionTO
	 */
	public RegionTO getRegionByIdOrName(String regionCode, Integer regionId)
			throws CGSystemException;

	/**
	 * Gets the state for given cityId
	 * 
	 * @param cityId
	 * @return
	 */
	public StateTO getState(Integer cityId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param transCityId
	 * @return
	 * @throws CGSystemException
	 * @throws CGSystemException
	 */
	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException, CGSystemException;

	/**
	 * @param pincodeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PincodeTO getPincode(Integer pincodeId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param pincodeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CityTO getCityByPincodeId(Integer pincodeId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param cityName
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CityTO getCityByName(String cityName) throws CGBusinessException,
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
	 * @param rhoOfficeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CityTO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param stateCode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public StateTO getStateByCode(String stateCode) throws CGBusinessException,
			CGSystemException;

	public CountryTO getCountryByCode(String string)
			throws CGBusinessException, CGSystemException;

	public Integer getOfficeIdByPincode(Integer pincodeId)
			throws CGSystemException;

	public List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CityTO getCityByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public PincodeProductServiceabilityTO getPincodeProdServiceabilityDtls(
			Integer pincodeProductMapId) throws CGSystemException,
			CGBusinessException;

	public List<PincodeServicabilityTO> getServicableOfficeIdByPincode(
			Integer pincodeId) throws CGSystemException, CGBusinessException;

	public List<OfficeTO> getAllOfficeByOfficeIds(List<OfficeTO> officeTOs)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the pincode details by pincode.
	 *
	 * @param pincode the pincode
	 * @return the pincode details by pincode
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<PincodeTO> getPincodeDetailsByPincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is pincode serviceable by product series.
	 *
	 * @param pincodeId the pincode id
	 * @param productSeries the product series
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean isPincodeServiceableByProductSeries(Integer pincodeId,
			Integer productId) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the branch dtls for pincode service by pincode.
	 *
	 * @param pincodeId the pincode id
	 * @return the branch dtls for pincode service by pincode
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<OfficeTO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the all pincodes as map.
	 *
	 * @return the all pincodes as map
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<Integer, String> getAllPincodesAsMap() throws CGSystemException,
			CGBusinessException;

	public List<StateTO> getStatesList() throws CGSystemException, CGBusinessException;

	public List<CityTO> getCityListByStateId(Integer stateId) throws CGSystemException, CGBusinessException;
	
		/**
	 * @param regionId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCitiesByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException;

		/**
		 * Gets the all branches as map.
		 *
		 * @return the all branches as map
		 * @throws CGSystemException the cG system exception
		 * @throws CGBusinessException the cG business exception
		 */
		Map<Integer, String> getAllBranchesAsMap() throws CGSystemException,
				CGBusinessException;
		/**
		 * 
		 * @return
		 * @throws CGSystemException
		 * @throws CGBusinessException
		 */
		public List<SectorTO> getSectors() throws CGSystemException, CGBusinessException;

		/**
		 * Gets the all rho offices as map.
		 *
		 * @return the all rho offices as map
		 * @throws CGSystemException the cG system exception
		 * @throws CGBusinessException the cG business exception
		 */
		Map<Integer, String> getAllRHOOfficesAsMap() throws CGSystemException,
				CGBusinessException;

		/**
		 * Gets the all rho and co offices as map.
		 *
		 * @return the all rho and co offices as map
		 * @throws CGSystemException the cG system exception
		 * @throws CGBusinessException the cG business exception
		 */
		Map<Integer, String> getAllRHOAndCOOfficesAsMap()
				throws CGSystemException, CGBusinessException;

		/**
		 * Gets the all cities by rho office as map.
		 *
		 * @param rhoOfficeid the rho officeid
		 * @return the all cities by rho office as map
		 * @throws CGSystemException the cG system exception
		 * @throws CGBusinessException the cG business exception
		 */
		Map<Integer, String> getAllCitiesByRhoOfficeAsMap(Integer rhoOfficeid)
				throws CGSystemException, CGBusinessException;
		
		
		/**
		 * Gets the all pincodes as a map irrespective of status
		 *
		 * @return the all pincodes as map
		 * @throws CGSystemException the cG system exception
		 * @throws CGBusinessException the cG business exception
		 */
		public Map<Integer, String> getAllPincodesAsMapForLocationSearch() throws CGSystemException ,CGBusinessException;
		
		/**
		 * Returns a description of all the products mapped to a pincode
		 * 
		 * @return String
		 * @throws CGSystemException
		 * @throws CGBusinessException
		 */
		public String getProductsDescMappedToPincode(Integer pincodeId) throws CGSystemException ,CGBusinessException;
		
		/**
		 * Returns a list of pincodes in the form pincode~city name
		 * 
		 * @return List<String>
		 * @throws CGSystemException
		 * @throws CGBusinessException
		 */
		public List<String> getPincodeAndCityListForAutoComplete() throws CGSystemException ,CGBusinessException;
}
