package com.ff.ud.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.OfficeNameDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.RegionRightsDTO;



@Component
@Service
public class BranchToHubServiceImpl implements BranchToHubServices {
	
	static Logger logger = Logger.getLogger(BranchToHubServiceImpl.class.getName());
	
	@Autowired private MasterDAO masterDAO;

	
	@Autowired private HubToHubService hubToHubService;
	

	@Override
	public String getOriginCityFromRegion(String regionAlpahabate) {
		return masterDAO.getOriginCityFromRegion(regionAlpahabate);
	}

	@Override
	public List<String> getCityCodesOpsman() {
		return masterDAO.getCityCodesOpsman();
	}

	



	/*@Override
	public ManifestBODTO getDataForOriginBranchToOriginHub(String regionAlphabate,String originBranchCode,String despDate) throws HibernateException, SQLException{
		*//**
		 * Instatiating the ManifestBODTO, Holding the all the data from Branch to HUB
		 *//*
		ManifestBODTO manifestBODTO=new ManifestBODTO();
		
		*//**
		 * Fetching consignment details.
		 *//*
		List<Outgo> outGOList=outGoDAO.getOutgoDetails(regionAlphabate, originBranchCode, despDate);
		
		*//**
		 * Add Consignment details in the ogm list
		 *//*
		manifestBODTO.setOgmList(outGOList);
		
		*//**
		 * Set to store the unique packet nos
		 *//*
		Set<String> setOfPacket=new HashSet<String>();
		
		for(Outgo outgo:outGOList){
			setOfPacket.add(outgo.getBR_PKT_NO());
		}
		
		//outGoDAO.updateDtToDBF(setOfPacket, regionAlphabate);
		
		*//**
		 * Fetching packet list form the packet table
		 *//*
		String packetNosCommanSeprated=setOfPacket.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
		List<BoPktDTO> packetList=packetDAO.getBranchPacketData(regionAlphabate, packetNosCommanSeprated);
		manifestBODTO.setPacketList(packetList);
		
		
		*//**
		 * Set to store the unique MBL's
		 *//*
		Set<String> setOfMBLs=new HashSet<String>();
		
		for(BoPktDTO branchPacket:packetList){
			setOfMBLs.add(branchPacket.getMblNo());
		}
		
		String mblNosCommanSeprated=setOfMBLs.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
		System.out.println("mblNosCommanSeprated ---> "+mblNosCommanSeprated);
		*//**
		 * Fetching branch gatepass details.
		 *//*
		List<BoMblDTO> gatePassList=mblDAO.getBranchMblData(regionAlphabate, mblNosCommanSeprated);
		manifestBODTO.setMblList(gatePassList);
		
		
		return manifestBODTO;
	}*/
	
	
		@Override
		public List<RegionDO> getRegion() {
			return hubToHubService.getRegion();
		}

		@Override
		public List<CityDO> getCity() {
			return hubToHubService.getCity();
		}

		
		@Override
		public List<OfficeDO> getOfficesByCityId(Integer cityId) {
			return masterDAO.getOfficesByCityId(cityId);
		}

		@Override
		public String getScriptCodeByOfficeCode(String originBranchCode) {
			return masterDAO.getScriptCodeByOfficeCode(originBranchCode);
		}

		@Override
		public List<CityDTO> getCityByRegCode(String regCode) {
			System.out.println("regCode 0"+regCode);
			return hubToHubService.getCityByRegCode(regCode);
		
		}

		@Override
		public List<RegionDO> getCityNames(String cityName) {
			System.out.println("cityName 0"+cityName);
			return hubToHubService.getRegionCode(cityName);
		}

	

		@Override
		public List<RegionDO> getRegionid(String region) {
			return hubToHubService.getRegionid(region);
		}

		@Override
		public List<RegionDO> getOfficeType(String officeType) {
			return hubToHubService.getOfficeType(officeType);
			//return masterDAO.getOfficeType(officeType);
		}

		@Override
		public List<CityDO> getCityIdForRHO(String cityNames) {
			return hubToHubService.getCityIdForRHO(cityNames);
		}

		@Override
		public List<CityDO> getCityNameForBranch(String cityOfficeName) {
			return hubToHubService.getCityNameForBranch(cityOfficeName);
		}

		@Override
		public List<RegionDO> getBranchCode(String branchName) {
			return hubToHubService.getBranchCode(branchName);
		}

		@Override
		public List<CityDO> getCityIdForRHOList(String cityCode, String officeId) {
			return hubToHubService.getCityIdForRHOList(cityCode,officeId);
		}

	


		/*
		 * This method for region allacation for manager
		 */
		public List<RegionRightsDTO> getRegionAllocationForManager(Integer regionId){
			
			logger.info("BranchToHubServiceImpl :: getRegionAllocationForManager :: -------->Start");
			List<RegionRightsDTO> regionList =masterDAO.getRegionAllocationForManager(regionId);
			List<RegionRightsDTO> regionRightList=new ArrayList<RegionRightsDTO>();
			
			for(RegionRightsDTO region:regionList)
			{
				RegionRightsDTO rigionDTO=new RegionRightsDTO();
				rigionDTO.setRegionCode(region.getRegionCode());
				rigionDTO.setRegionName(region.getRegionName());
				regionRightList.add(rigionDTO);
			}
			logger.info("BranchToHubServiceImpl :: getRegionAllocationForManager :: -------->End");
			return regionRightList;
			
		}
	
            /*
            * This is for region manager
            */
		
		public List<RegionDO> getRegionidForRegion(List<RegionRightsDTO> region) {
			return hubToHubService.getRegionidForRegion(region);
		}
		
	
	}


