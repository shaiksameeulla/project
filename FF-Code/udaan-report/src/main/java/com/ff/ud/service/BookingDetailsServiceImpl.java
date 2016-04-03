package com.ff.ud.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.CityDTO;

//import com.ff.ou.dbf.dao.BookingDAO;


@Component
@Service
public class BookingDetailsServiceImpl  implements BookingDetailsService {
	
	@Autowired private MasterDAO masterDAO;
//	@Autowired private BookingDAO bookingDAO;
	//@Autowired private BookingDetailsService bookingDetailsService;
	
	@Autowired private HubToHubService hubToHubService;
	
	static Logger logger = Logger.getLogger(BookingDetailsServiceImpl.class);

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
	
	/*
	@Override
	public ManifestBODTO getDataForOriginBranchToOriginHub(String regionAlphabate, String originBranchCode,
			Date startDate, Date endDate) throws HibernateException, SQLException{*/
	/*public ManifestBODTO getDataForOriginBranchToOriginHub(String region, String originCity,Date dbfDate, Date endDate) throws HibernateException, SQLException{
		*//**
		 * Instatiating the ManifestBODTO, Holding the all the data from Branch to HUB
		 *//*
		
		List<BookingDetailsDTO> outgoList = new ArrayList<BookingDetailsDTO>();
		
		ManifestBODTO manifestBODTO=new ManifestBODTO();
		
		
		//String  OfficeId= outGoDAO.getOfficeIdFromOfficeCode(originBranchCode);
		
		
		*//**
		 * Fetching consignment details.
		 *//*
          
		
		List<Booking> bookingList=bookingDAO.getBookingDetails(region,originCity,dbfDate,endDate);
		
		 
		
		*//**
		 * Add Consignment details in the ogm list
		 *//*
		manifestBODTO.setBookingList(bookingList);
		
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
		//List<BoPktDTO> packetList=packetDAO.getBranchPacketData(regionAlphabate, packetNosCommanSeprated);
		List<BoPktDTO> packetList=packetDAO.getBranchPacketData(regionAlphabate, packetNosCommanSeprated,originBranchCode);
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
		//List<BoMblDTO> gatePassList=mblDAO.getBranchMblData(regionAlphabate, mblNosCommanSeprated);
		List<BoMblDTO> gatePassList=mblDAO.getBranchMblData(regionAlphabate, mblNosCommanSeprated,originBranchCode);
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
		public List<CityDTO> getCityByRegCode(String regCode) {
			return hubToHubService.getCityByRegCode(regCode);
		}

		@Override
		public List<OfficeDO> getOfficesByCityId(Integer cityId) {
			return masterDAO.getOfficesByCityId(cityId);
		}

		@Override
		public String getScriptCodeByOfficeCode(String originBranchCode) {
			return masterDAO.getScriptCodeByOfficeCode(originBranchCode);
		}
/*
		@Override
		public List<BoPktDTO> getBranchPacketData(String regionAlpahabate,
				String originRegion, String desRegion, String product)
				throws HibernateException, SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update_DtToDBF(List<String> pktNos, String regionAlpahabate) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<BoMblDTO> getBranchMblData(String region,
				String originCity, String destinatioCity, String product)
				throws SQLException, HibernateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update_DtToDBF1(List<String> MblNo, String regionAlpahabate) {
			// TODO Auto-generated method stub
			
		}

*/

	
	
}
