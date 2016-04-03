package com.ff.ud.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ff.ud.dao.CustomerDAO;
import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.OfficeDTO;
import com.ff.ud.dto.RegionDTO;
import com.ff.ud.dto.RegionRightsDTO;



@Component
@Service
public class HubToHubServiceImpl implements HubToHubService {


	@Autowired
	private MasterDAO masterDAO;
	@Autowired
	private CustomerDAO customerDAO;
	
	

	static Logger logger = Logger.getLogger(HubToHubServiceImpl.class);

	

	public List<String> getCityCodesOpsman() {
		return masterDAO.getCityCodesOpsman();
	}

	

	@Override
	public String getOriginCityFromRegion(String regionAlpahabate) {
		return masterDAO.getOriginCityFromRegion(regionAlpahabate);
	}

	


	@Override
	public List<CustomerDTO> getCustomerData(String region, String originCity,
			String destinatioCity, String product) {
		return customerDAO.getCustomerData(region, originCity, destinatioCity,
				product);
	}

	
	
	
	
	

	@Override
	public List<RegionDO> getRegion() {

		List<RegionDTO> regionlist = masterDAO.getRegionAlphabet();
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (RegionDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setRegionName(regionDto.getRegionName());
			regionDolist.add(regionDo);
		}

		return regionDolist;
	}
	
	
	@Override
	public List<RegionDO> getRegionCode(String cityName) {
		System.out.println("cityName 1"+cityName);

		List<OfficeDTO> regionlist = masterDAO.getRegionNames(cityName);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setCityId(String.valueOf(regionDto.getCityId()));
			regionDolist.add(regionDo);
		}

		return regionDolist;
	}
	

	@Override
	public List<CityDO> getCity() {

		List<CityDO> cityList = new ArrayList<CityDO>();
		CityDO cityDo = null;
		List<CityDTO> cityToList = masterDAO.getCityByRegion();
		for(CityDTO cityTO  : cityToList) {
			cityDo = new CityDO();
			cityDo.setCityCode(cityTO.getCityCode());
			cityDo.setCityName(cityTO.getCityName());
			cityDo.setRegCode(cityTO.getRegCode());
			cityDo.setCityId(cityTO.getCityId());
			
			cityList.add(cityDo);

		}
		
		return cityList;
	}

	@Override
	public List<CityDTO> getCityByRegCode(String regCode) {
		
		return masterDAO.getCityByRegCode(regCode) ;
	}




	public List<RegionDO> getRegionid(String region) {

		List<RegionDTO> regionlist = masterDAO.getRegionAlphabetid(region);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (RegionDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setRegionName(regionDto.getRegionName());
			regionDolist.add(regionDo);
		}

		return regionDolist;
	}



	
	public List<CityDO> getCityIdForRHO(String cityName) {
		
		List<CityDO> regionlist = masterDAO.getRHOName(cityName);
		List<CityDO> regionDolist = new ArrayList<CityDO>();
		for (CityDO regionDto : regionlist) {
			CityDO cityDO = new CityDO();
			cityDO.setCityName(regionDto.getCityName());
			cityDO.setCityId(regionDto.getCityId());
			System.out.println("office name :"+cityDO.getCityName());
			regionDolist.add(cityDO);
		}

		return regionDolist;
	}



	@Override
	public List<RegionDO> getOfficeType(String officeType) {
		System.out.println("getOfficeType HUU :"+officeType);
		List<OfficeDO> regionlist = masterDAO.getOfficeType(officeType);;
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDO regionDto : regionlist) {
			RegionDO cityDO = new RegionDO();
			cityDO.setRegionCode(String.valueOf(regionDto.getOfficeType()));
			System.out.println("getOfficeType HUU1 "+cityDO.getRegionCode());
			regionDolist.add(cityDO);
		}
		return regionDolist;
	}



	@Override
	public List<CityDO> getCityNameForBranch(String CityNameOffice) {
		List<CityDO> regionlist = masterDAO.getCityNameForBranch(CityNameOffice);
		List<CityDO> regionDolist = new ArrayList<CityDO>();
		for (CityDO regionDto : regionlist) {
			CityDO cityDO = new CityDO();
			cityDO.setCityName(regionDto.getCityName());
			cityDO.setCityId(regionDto.getCityId());
			System.out.println("office name :"+cityDO.getCityName());
			regionDolist.add(cityDO);
		}

		return regionDolist;
	}



	@Override
	public List<RegionDO> getBranchCode(String branchName) {
		List<OfficeDO> regionlist = masterDAO.getBranchCode(branchName);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDO regionDto : regionlist) {
			RegionDO regionDO = new RegionDO();
			regionDO.setRegionCode(regionDto.getOfficeCode());
			System.out.println("office name :"+regionDO.getRegionCode());
			regionDolist.add(regionDO);
		}

		return regionDolist;
	}



	@Override
	public List<CityDO> getCityIdForRHOList(String cityCode, String officeId) {
		List<CityDO> regionlist = masterDAO.getCityIdForRHOList(cityCode);
		Set<CityDO> regionDolist=new HashSet<CityDO>();
		for (CityDO regionDto : regionlist) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(regionDto.getCityId());
			cityDO.setCityName(regionDto.getCityName());
			regionDolist.add(cityDO);
		}
		List<CityDO> regionDolist1 = new ArrayList<CityDO>(regionDolist);
		return regionDolist1;
	}

		
	//this is for region manager

	public List<RegionDO> getRegionidForRegion(List<RegionRightsDTO> region){

		List<RegionDTO> regionlist = masterDAO.getRegionAlphabetidForRegion(region);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (RegionDTO regionDto : regionlist) {
			
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(String.valueOf(regionDto.getRegionCode()));
			regionDo.setRegionName(regionDto.getRegionName());
			regionDolist.add(regionDo);
		}

		return regionDolist;
	}
	
	
}

/*
 * @Override public void update_DtToDBF(List<String> consignmentNos,String
 * regionAlpahabate) { customerDAO.update_DtToDBF(consignmentNos,
 * regionAlpahabate);
 * 
 * }
 */

