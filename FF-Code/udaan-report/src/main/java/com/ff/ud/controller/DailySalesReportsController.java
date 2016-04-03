package com.ff.ud.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.BookingDetailsBean;
import com.ff.ud.dto.BranchToHubFormBean;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.dto.RegionRightsDTO;
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.ud.utils.DateUtils;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;


@Controller
public class DailySalesReportsController {


	static Logger logger = Logger.getLogger(DailySalesReportsController.class.getName());
	
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;
	

	@RequestMapping(value ="/dailySalesReports",method=RequestMethod.GET)
	public String getdailySalesReports(ModelMap map,HttpServletResponse response,HttpServletRequest request,HttpSession session) {
    String view="dailySalesReports";

	
    List<RegionDO> regionList=null;
    List<RegionDO> region=null;
    List<RegionDO> branchCode=null;
    String brachName =null;
    List<CityDO> cityList =null;
    String cityName=null;
    
    List<RegionRightsDTO> regionName=null;
    List<OfficeDO> offices=null;
    List<CityDTO> cityDTO=null;
    
    
    UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
    
  if(userInfoTO != null){
	                  cityName =userInfoTO.getOfficeTo().getOfficeName();
                       }
  
    session.getAttributeNames().toString();
    
    String officeCode= userInfoTO.getOfficeTo().getOfficeCode();
    Integer userId=userInfoTO.getUserto().getUserId();
    String officeName=userInfoTO.getOfficeTo().getOfficeName();
    
	region=branchToHubServices.getOfficeType(officeCode);
	 String officeId=null;
	 for(RegionDO officeTyeId :region){
		 officeId=officeTyeId.getRegionCode();
	 }
	 
	     /**
		 * RHO login Details	
		 */
	  if(officeId.equals("2") && ! cityName.equalsIgnoreCase("Corporate Office")){
		 
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
		
		  offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
		  cityDTO=branchToHubServices.getCityByRegCode(cityCode1);
		  
		    map.put("cityDTO", cityDTO);
		    map.put("offices", offices);
		  	map.put("regionList", regionList);
		  	map.put("branchDO", new BranchToHubFormBean());
		  	
		
			
	  }
	     /**
		  * Corporate office Login details
		  */
	  else if (cityName.equalsIgnoreCase("Corporate Office")){
		  regionList=branchToHubServices.getRegion();
		  cityList = branchToHubServices.getCity();
		  
		    map.put("regionList", regionList);
			map.put("cityList", cityList);
			
			map.put("branchDO", new BranchToHubFormBean());
	  }

		/**
		 * Hub And Branch login Details	
		 */
	  else{
		  
		  region=branchToHubServices.getCityNames(officeCode);
			 String cityCode=null;
			 for(RegionDO region1 :region){
				  cityCode=region1.getRegionCode();
			 }
		  	 regionList=branchToHubServices.getRegionid(cityCode);
		  	 String cityid=null;
			 for(RegionDO region1 :region){
				  cityid=region1.getCityId();
			 }
		  	cityList = branchToHubServices.getCityNameForBranch(cityid);
		  	brachName = cityName;
		  	branchCode = branchToHubServices.getBranchCode(cityName);
		  	String banchCodeValue=null;
		  	for(RegionDO regionDO : branchCode){
		  		banchCodeValue=regionDO.getRegionCode();
		  	}
		  	
		  	offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
		  	cityDTO=branchToHubServices.getCityByRegCode(banchCodeValue);
		  	
		  	
		  	map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("brachName", brachName);
			map.put("branchCode", banchCodeValue);
			map.put("officeId", officeId);
			map.put("officeName", officeName);
			map.put("officeCode", officeCode);
			map.put("offices", offices);
			map.put("cityDTO", cityDTO);
			
			map.put("branchDO", new BranchToHubFormBean());
			
			
	  }
	
  
	
	return view;
	
}



}
