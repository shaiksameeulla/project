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
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

@Controller
public class BASalesSummaryReportController {
	
	
static Logger logger=Logger.getLogger(BASalesSummaryReportController.class.getName());
	
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;

	@RequestMapping(value="baSalesSummaryReport",method=RequestMethod.GET)
	public String getBASalesSummary(ModelMap  map,HttpServletRequest request,HttpServletResponse response,HttpSession session)
	{
		logger.info("BASalesSummaryReportController :: getBASalesSummary --------->start");
		 String view="baSalesSummaryReport";	
			
			
	       	List<CityDO> cityList=null;
			List<CityDO> StrationList=null;
			List<RegionDO> regionList=null;
			List<RegionDO> branchCode=null;
			List<RegionDO> region=null;
			String cityName=null;
			String brachName =null;
			
			 UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		      
	    	 cityName =userInfoTO.getOfficeTo().getOfficeName();
		String officeCode=userInfoTO.getOfficeTo().getOfficeCode();
		region=dispatchAndReceiveReportService.getOfficeType(cityName);
		String officeId=null;
		 for(RegionDO officeTyeId :region){
			 officeId=officeTyeId.getRegionCode();   

			
		 /*   cityName="Corporate Office";
			String officeId=null;
			officeId="1";*/
			 /**
			  * Corporate office Login details
			  */
			if(cityName.equalsIgnoreCase("Corporate Office")){
				logger.info("BASalesSummaryReportController :: Corporate User Login Details --------->start");
				regionList=dispatchAndReceiveReportService.getRegion(); 
				cityList= dispatchAndReceiveReportService.getCity();
		
				
				map.put("regionList", regionList);
				map.put("cityList", cityList);
		
				map.put("cityName", cityName);
				
				map.put("branchDO", new BranchToHubFormBean());
				
				logger.info("BASalesSummaryReportController :: Corporate User Login Details --------->End");
			/**
			 * RHO login Details	
			 */
			}else if(officeId.equalsIgnoreCase("2") && !cityName.equalsIgnoreCase("Corporate Office")){
				logger.info("BASalesSummaryReportController :: RHO Login Details --------->start");
				
				region=dispatchAndReceiveReportService.getCityNames(officeCode);
				 String cityCode=null;
				 for(RegionDO region1 :region){
					  cityCode=region1.getRegionCode();
				 }
				 regionList=dispatchAndReceiveReportService.getRegionid(cityCode);
				 cityList = dispatchAndReceiveReportService.getCityIdForRHOList(cityCode);
				 

				map.put("regionList", regionList);
				map.put("cityList", cityList);
				map.put("officeId", officeId);
				
				
				logger.info("BASalesSummaryReportController :: RHO Login Details --------->End");
				
				/**
				 * Hub And Branch login Details	
				 */
			}else{
				logger.info("BASalesSummaryReportController :: User Login Details --------->Start");
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
		
				 
				    map.put("regionList", regionList);
					map.put("cityList", cityList);
					map.put("branchCode", banchCodeValue);
					map.put("brachName", brachName);
					map.put("cityid", cityid);
					map.put("officeId", officeId);
					
						
				logger.info("BASalesSummaryReportController :: User Login Details --------->End");
				
			  }
		    
			 map.put("branchDO", new BranchToHubFormBean());
			 
			 logger.info("BASalesSummaryReportController :: --------->End");
			
		    }
		
	           
		return view;
		
	}

}
