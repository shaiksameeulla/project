package com.ff.ud.service;

import java.util.List;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.RegionRightsDTO;


/**
 * 
 * @author "Kir@N"
 * This is the service class which deals with all operations in <b>branch to hub</b> process.
 */
    public interface BranchToHubServices {

	public String getOriginCityFromRegion(String regionAlpahabate);

	public List<String> getCityCodesOpsman();

	

/**
 * This method returns the list of the region
 * @return list of region objects
 */
	public List<RegionDO> getRegion();
	
	/**
	 * This methods returns the city
	 * @return list of cities
	 */
	public List<CityDO> getCity();
	/**
	 * This methods return the list of the cities whos regCode is given
	 * @param regCode : aphabate of the reason
	 * @return list of city of the given regCode
	 */
	public List<CityDTO> getCityByRegCode(String regCode);

	public List<OfficeDO> getOfficesByCityId(Integer cityId);

	/**
	 * This methods returns the script code of branch
	 * @param originBranchCode : udaan branch code
	 * @return 3 alphabetic  script code
	 */
	public String getScriptCodeByOfficeCode(String originBranchCode);

	public List<RegionDO> getCityNames(String cityName);
	


	public List<RegionDO> getRegionid(String region);

	public List<RegionDO> getOfficeType(String officeType);
	public List<CityDO> getCityIdForRHO(String cityNames);

	public List<CityDO>getCityNameForBranch(String cityOfficeName);
	public List<RegionDO> getBranchCode (String branchName);

	public List<CityDO> getCityIdForRHOList(String cityCode, String officeId);
	
	
	/*
	 * This method for Region allacation for manager
	 */
	public List<RegionRightsDTO> getRegionAllocationForManager(Integer regionId);

	/*
	 * This is for Region manager 
	 */
	public List<RegionDO> getRegionidForRegion(List<RegionRightsDTO> region);
	
	
	
}
