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
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.BookingDetailsBean;
import com.ff.ud.dto.BranchToHubFormBean;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.ud.utils.DateUtils;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;


@Controller
public class DestinationHubDispAndDestBrRecController {
	
	static Logger logger = Logger.getLogger(DeliveryRunsheetReportController.class.getName());
    
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	@Autowired private BranchToHubServices branchToHubServices ;
	
	@RequestMapping(value = "/destinationHubDispAndDestBrRecived",method=RequestMethod.GET)
	public String getDestinationHubToBranch(ModelMap map,HttpServletResponse response,HttpServletRequest request,HttpSession session){
		String view="destinationHubDispAndDestBrRecived";
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
		//cityName="HYD Hafeezpet HUB";
		region=dispatchAndReceiveReportService.getOfficeType(officeCode);
		String officeId=null;
		 for(RegionDO officeTyeId :region){
			 officeId=officeTyeId.getRegionCode();
		 }
		/*String officeId=null;
		officeId="3";*/
		 /**
		  * Corporate office Login details
		  */
		if(cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("DestinationHubToDispatchBranchController :: Corporate User Login Details --------->start");
			regionList=dispatchAndReceiveReportService.getRegion(); 
			cityList= dispatchAndReceiveReportService.getCity();
			productList= dispatchAndReceiveReportService.getProduct();
			
			map.put("regionList", regionList);
			map.put("cityList", cityList);
			map.put("productList", productList);
			map.put("cityName", cityName);
			
			map.put("branchDO", new BranchToHubFormBean());
			
			logger.info("DestinationHubToDispatchBranchController :: Corporate User Login Details --------->End");
		/**
		 * RHO login Details	
		 */
		}else if(officeId.equalsIgnoreCase("2") && !cityName.equalsIgnoreCase("Corporate Office")){
			logger.info("DestinationHubToDispatchBranchController :: RHO Login Details --------->start");
			
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
			
			
			logger.info("DestinationHubToDispatchBranchController :: RHO Login Details --------->End");
			
			/**
			 * Hub And Branch login Details	
			 */
		}else{
			logger.info("DestinationHubToDispatchBranchController :: User Login Details --------->Start");
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
				
					
			logger.info("DestinationHubToDispatchBranchController :: User Login Details --------->End");
			
		}
		 destRegionList=dispatchAndReceiveReportService.getRegion(); 
		 DestcityList= dispatchAndReceiveReportService.getCity();
		 
		 map.put("destRegionList", destRegionList);
		map.put("DestcityList", DestcityList);
			
		 map.put("branchDO", new BranchToHubFormBean());
		 
		 logger.info("DestinationHubToDispatchBranchController :: --------->End");
		
		return view;
		
	}
	
	@RequestMapping(value = "/destinationHubDispAndDestBrRecived",method=RequestMethod.POST)
	public String getDestinationHubToBranchDetails(@ModelAttribute("branchDO") BranchToHubFormBean branchToHubFormBean,ModelMap map,HttpServletResponse response) {
		String filename=null;
		String displayMessage=null;
		String dbfFileName = null;
		Date startDate = null;
		Date endDate = null;
		
	    System.out.println(branchToHubFormBean.toString());
		
      try {
			
			String startDate1 = DateUtils.formatDate(branchToHubFormBean.getStartDate(),"MM/dd/yyyy","yyyy-MM-dd");
		    String endDate1 = DateUtils.formatDate(branchToHubFormBean.getEndDate(),"MM/dd/yyyy","yyyy-MM-dd");
			
		    startDate = DateUtils.getDateFromString(startDate1, DateUtils.JAVA_SQL_DATE_FORMAT);
			endDate = DateUtils.getDateFromString(endDate1, DateUtils.JAVA_SQL_DATE_FORMAT);
			
			
			//dbfFileName = bookingDetailsScheduler.generateDBF(booking,startDate,endDate);
			
			displayMessage="DBF is generated sucessfully for City "+ branchToHubFormBean.getCityId();
      } catch (Exception e) {
			displayMessage="Error accur while generating DBF";
			e.printStackTrace();
		}
		List<RegionDO> regionList=dispatchAndReceiveReportService.getRegion(); 
		List<CityDO> cityList = dispatchAndReceiveReportService.getCity();
		map.put("regionList", regionList);
		map.put("cityList", cityList);
		map.put("branchDO",new BookingDetailsBean());
		return "destinationHubDispAndDestBrRecived";
	   
			
	}
	
	
	
	
}
