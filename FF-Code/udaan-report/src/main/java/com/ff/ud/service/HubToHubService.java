package com.ff.ud.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.OfficeNameDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.RegionRightsDTO;



public interface HubToHubService {

	public List<String> getCityCodesOpsman();


	public String getOriginCityFromRegion(String regionAlpahabate);


	public List<CustomerDTO> getCustomerData(String region,String originCity,String destinatioCity, String product) throws HibernateException,SQLException;

	
	public List<RegionDO> getRegion();
	public List<RegionDO> getRegionCode(String cityName);
	
	public List<CityDO> getCity();

	public List<CityDTO> getCityByRegCode(String regCode);


	public List<RegionDO> getRegionid(String region);
	public List<CityDO> getCityIdForRHO(String cityNames);


	public List<RegionDO> getOfficeType(String officeType);
	
	public List<CityDO> getCityNameForBranch(String CityNameOffice);


	public List<RegionDO> getBranchCode(String branchName);


	public List<CityDO> getCityIdForRHOList(String cityCode, String officeId);
	
	//this is for region manager
	public List<RegionDO> getRegionidForRegion(List<RegionRightsDTO> region);
}
