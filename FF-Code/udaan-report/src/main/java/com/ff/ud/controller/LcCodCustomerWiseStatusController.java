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
public class LcCodCustomerWiseStatusController {
	
static Logger logger=Logger.getLogger(LcCodCustomerWiseStatusController.class.getName());
	
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;
	
	@RequestMapping(value="/LcCodCustomerWiseStatus",method=RequestMethod.GET)
	public String getLcCodCustomerWiseStatusDetails(ModelMap map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String view="LcCodCustomerWiseStatus";	
		

       	List<CityDO> cityList=null;
		List<CityDO> StrationList=null;
		List<RegionDO> regionList=null;
		List<RegionDO> destList=null;
		List<RegionDO> branchCode=null;
		List<RegionDO> region=null;
		String cityName=null;
		String brachName =null;
		
		  List<RegionRightsDTO> regionName=null;
	      List<OfficeDO> offices=null;
	      List<CityDTO> cityDTO=null;
	      List<CustomerDTO> customerOffice=null;
		
		 UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
	      
    	 cityName =userInfoTO.getOfficeTo().getOfficeName();
    	 String officeCode= userInfoTO.getOfficeTo().getOfficeCode();
    	 Integer userId=userInfoTO.getUserto().getUserId();
    	 String officeName=userInfoTO.getOfficeTo().getOfficeName();
    	 
	region=dispatchAndReceiveReportService.getOfficeType(officeCode);
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
			logger.info("LcCodCustomerWiseStatusController :: Corporate User Login Details --------->start");
			regionList=dispatchAndReceiveReportService.getRegion(); 
			cityList= dispatchAndReceiveReportService.getCity();
		    customerOffice=dispatchAndReceiveReportService.getCustomerNameFromOFficeCode(officeCode);
		    destList=dispatchAndReceiveReportService.getRegion();
		    //customerOffice =dispatchAndReceiveReportService.getCustomerNameFromOFficeCodeForAll();
			
			map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("customerOffice", customerOffice);
			map.put("cityName", cityName);
			map.put("destList", destList);
			
			
			map.put("branchDO", new BranchToHubFormBean());
			
			logger.info("LcCodCustomerWiseStatusController :: Corporate User Login Details --------->End");
		/**
		 * RHO login Details	
		 */
		}else if(officeId.equalsIgnoreCase("2") && !cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("LcCodCustomerWiseStatusController :: RHO Login Details --------->start");
			
			
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
			  
			 
			  customerOffice=dispatchAndReceiveReportService.getCustomerNameFromOFficeCode(officeCode);
			  offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
			  cityDTO=branchToHubServices.getCityByRegCode(cityCode1);
			  destList=dispatchAndReceiveReportService.getRegion();
			  
			    map.put("cityDTO", cityDTO);
			    map.put("offices", offices);
			  	map.put("regionList", regionList);
			  	map.put("customerOffice", customerOffice);
			  	map.put("destList", destList);
			  	
	
			
			
			logger.info("LcCodCustomerWiseStatusController :: RHO Login Details --------->End");
			
			/**
			 * Hub And Branch login Details	
			 */
		}else{
			logger.info("LcCodCustomerWiseStatusController :: User Login Details --------->Start");
			region=dispatchAndReceiveReportService.getCityNames(officeCode);
			 String cityCode=null;
			 String banchCodeValue=null;
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
			// String banchCodeValue=null;
			  	for(RegionDO regionDO : branchCode){
			  		banchCodeValue=regionDO.getRegionCode();
			  	   }
	
			 
			  	customerOffice=dispatchAndReceiveReportService.getCustomerNameFromOFficeCode(officeCode);
			  	offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
			  	cityDTO=branchToHubServices.getCityByRegCode(banchCodeValue);
			  	 destList=dispatchAndReceiveReportService.getRegion();
			   
			  	map.put("regionList", regionList);
				map.put("cityList", cityList);
				map.put("brachName", brachName);
				map.put("branchCode", banchCodeValue);
				map.put("officeId", officeId);
				map.put("officeName", officeName);
				map.put("officeCode", officeCode);
				map.put("offices", offices);
				map.put("cityDTO", cityDTO);
				map.put("customerOffice", customerOffice);
				map.put("destList", destList);
			  	
			  	
					
			logger.info("LcCodCustomerWiseStatusController :: User Login Details --------->End");
			
		  }
	    
		 map.put("branchDO", new BranchToHubFormBean());
		 
		 logger.info("LcCodCustomerWiseStatusController :: --------->End");
		
	    }
	
           
	return view;
		
		
		
	}
	

}
