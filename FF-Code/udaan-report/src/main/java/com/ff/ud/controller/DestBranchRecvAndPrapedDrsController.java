package com.ff.ud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.BranchToHubFormBean;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

@Controller
public class DestBranchRecvAndPrapedDrsController {
	
static Logger logger = Logger.getLogger(DestBranchRecvAndPrapedDrsController.class.getName());
    
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;
	
	@RequestMapping(value = "/destBranchRecvAndDrsPrepared",method=RequestMethod.GET)
	public String getDestinationBranchToDrsPrepared(ModelMap map,HttpServletResponse response,HttpServletRequest request,HttpSession session){
		String view="destBranchRecvAndDrsPrepared";
		List<CityDO> cityList=null;
		List<CityDO> StrationList=null;
		List<RegionDO> regionList=null;
		List<RegionDO> destRegionList=null;
		List<CityDO> DestcityList=null;
		List<RegionDO> branchCode=null;
		List<ProductDTO> productList=null;
		List<RegionDO> region=null;
		String cityName=null;
		String brachName =null;
	        
	        UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
	      

	    	 cityName =userInfoTO.getOfficeTo().getOfficeName();
	    	 String officeCode=userInfoTO.getOfficeTo().getOfficeCode();
		//cityName="Mumbai RHO";
		region=dispatchAndReceiveReportService.getOfficeType(officeCode);
		String officeId=null;
		 for(RegionDO officeTyeId :region){
			 officeId=officeTyeId.getRegionCode();
		 }
		/*String officeId=null;
		officeId="2";*/
		 /**
		  * Corporate office Login details
		  */
		if(cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("getDestinationBranchToDrsPrepared :: Corporate User Login Details --------->start");
			regionList=dispatchAndReceiveReportService.getRegion(); 
			cityList= dispatchAndReceiveReportService.getCity();
			productList= dispatchAndReceiveReportService.getProduct();
			
			map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("productList", productList);
			map.put("cityName", cityName);
			
			map.put("branchDO", new BranchToHubFormBean());
			
			logger.info("getDestinationBranchToDrsPrepared :: Corporate User Login Details --------->End");
		/**
		 * RHO login Details	
		 */
		}else if(officeId.equalsIgnoreCase("2") && !cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("getDestinationBranchToDrsPrepared :: RHO Login Details --------->start");
			
			region=dispatchAndReceiveReportService.getCityNames(officeCode);
			 String cityCode=null;
			 for(RegionDO region1 :region){
				  cityCode=region1.getRegionCode();
			 }
			 regionList=dispatchAndReceiveReportService.getRegionid(cityCode);
			 cityList = dispatchAndReceiveReportService.getCityIdForRHOList(cityCode);
			 
			 
			productList= dispatchAndReceiveReportService.getProduct();

			map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("destRegionList", destRegionList);
			map.put("DestcityList", DestcityList);
			map.put("productList", productList);
			map.put("officeId", officeId);
			
			
			logger.info("getDestinationBranchToDrsPrepared :: RHO Login Details --------->End");
			
			/**
			 * Hub And Branch login Details	
			 */
		}else{
			logger.info("getDestinationBranchToDrsPrepared :: User Login Details --------->Start");
			region=dispatchAndReceiveReportService.getCityNames(officeCode);
			 String cityCode=null;
			 for(RegionDO region1 :region){
				  cityCode=region1.getRegionCode();
			 }
			 regionList=dispatchAndReceiveReportService.getRegionid(cityCode);
			 String cityid=null;
			 for(RegionDO region1 :region){
				  cityid=region1.getCityId();
				  System.out.println("City id value :::"+cityid);
			 }
			 cityList = dispatchAndReceiveReportService.getCityNameForBranch(cityid);
			 branchCode = dispatchAndReceiveReportService.getBranchNameWithBranchCode(cityName);
			 brachName=cityName;
			 String banchCodeValue=null;
			  	for(RegionDO regionDO : branchCode){
			  		banchCodeValue=regionDO.getRegionCode();
			  	}
			productList= dispatchAndReceiveReportService.getProduct();
			 
			    map.put("regionList", regionList);
				map.put("cityList", cityList);
				map.put("branchCode", banchCodeValue);
				map.put("productList", productList);
				map.put("brachName", brachName);
				map.put("cityid", cityid);
				map.put("officeId", officeId);
				
					
			logger.info("getDestinationBranchToDrsPrepared :: User Login Details --------->End");
			
		}
		 destRegionList=dispatchAndReceiveReportService.getRegion(); 
		 DestcityList= dispatchAndReceiveReportService.getCity();
		 
		 map.put("destRegionList", destRegionList);
		map.put("DestcityList", DestcityList);
			
		 map.put("branchDO", new BranchToHubFormBean());
		 
		 logger.info("getDestinationBranchToDrsPrepared :: --------->End");
		
		return view;
		
	}

}
