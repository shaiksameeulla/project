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
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.BranchToHubFormBean;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.RegionRightsDTO;
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

@Controller
public class TatPerformancereportController {
	
static Logger logger = Logger.getLogger(TatPerformancereportController.class.getName());
    
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;
	
	@RequestMapping(value = "/tatPerformanceReport",method=RequestMethod.GET)
	public String getTatPerformanceReport(ModelMap map,HttpServletResponse response,HttpServletRequest request,HttpSession session){
		String view="tatPerformanceReport";
		List<CityDO> cityList=null;
		List<RegionDO> regionList=null;
		List<RegionDO> branchCode=null;
		List<CustomerDTO> customerList=null;		
		List<RegionDO> region=null;
		String cityName=null;
		String brachName =null;
		
		
		 List<RegionRightsDTO> regionName=null;
	      List<OfficeDO> offices=null;
	      List<CityDTO> cityDTO=null;
	        
	       UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
	      
			 Integer userId=userInfoTO.getUserto().getUserId();
	    	 cityName =userInfoTO.getOfficeTo().getOfficeName();
	    	 String officeCode=userInfoTO.getOfficeTo().getOfficeCode();
	    	 String officeName=userInfoTO.getOfficeTo().getOfficeName();
		//cityName="Mumbai RHO";
		region=dispatchAndReceiveReportService.getOfficeType(officeCode);
		String officeId=null;
		 for(RegionDO officeTyeId :region){
			 officeId=officeTyeId.getRegionCode();
		 }
		//String officeId=null;
		//officeId="2";
		 /**
		  * Corporate office Login details
		  */
		if(cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("TatPerformancereportController :: Corporate User Login Details --------->start");
			regionList=dispatchAndReceiveReportService.getRegion(); 
			cityList= dispatchAndReceiveReportService.getCity();
 			customerList= dispatchAndReceiveReportService.getCustomerCodeByNames();
			
			map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("customerList", customerList);
			map.put("cityName", cityName);
			
			map.put("branchDO", new BranchToHubFormBean());
			
			logger.info("TatPerformancereportController :: Corporate User Login Details --------->End");
		/**
		 * RHO login Details	
		 */
		}else if(officeId.equalsIgnoreCase("2") && !cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("TatPerformancereportController :: RHO Login Details --------->start");
			
			String cityCode1=null;
			  regionName=branchToHubServices.getRegionAllocationForManager(userId);
			 
         
			  if(regionName.size()>1){

				  
						 regionList=branchToHubServices.getRegionidForRegion(regionName);
				   }
			
			  else{
				  region=branchToHubServices.getCityNames(officeCode);
					// String cityCode1=null;
					 for(RegionDO region1 :region){
						  cityCode1=region1.getRegionCode();
					 }
				
					 regionList=branchToHubServices.getRegionid(cityCode1);
			     }
			
			  customerList= dispatchAndReceiveReportService.getCustomerCodeByNames();
			  
			  offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
			  cityDTO=branchToHubServices.getCityByRegCode(cityCode1);
			  
			    map.put("cityDTO", cityDTO);
			    map.put("offices", offices);
			  	map.put("regionList", regionList);
			  	map.put("customerList", customerList);
			
			
			logger.info("TatPerformancereportController :: RHO Login Details --------->End");
			
			/**
			 * Hub And Branch login Details	
			 */
		}else{
			logger.info("TatPerformancereportController :: User Login Details --------->Start");
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
			  	
			  	customerList= dispatchAndReceiveReportService.getCustomerCodeByNames();
			  	offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
			  	cityDTO=branchToHubServices.getCityByRegCode(banchCodeValue);
			 
			    map.put("regionList", regionList);
				map.put("cityList", cityList);
				map.put("branchCode", banchCodeValue);
				map.put("customerList", customerList);
				map.put("brachName", brachName);
				map.put("officeId", officeId);
				map.put("officeName", officeName);
				map.put("officeCode", officeCode);
				map.put("cityDTO", cityDTO);
				map.put("offices", offices);
			
				

					
			logger.info("TatPerformancereportController :: User Login Details --------->End");
			
		}
		
		 map.put("branchDO", new BranchToHubFormBean());
		 
		 logger.info("TatPerformancereportController :: --------->End");
		
		return view;
		
	}
	

}
