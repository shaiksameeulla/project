package com.ff.ud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.AllFfMastDO;
import com.ff.ud.domain.CustomerDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.ChequeDetailsDTO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.MonthWiseDateDTO;
import com.ff.ud.dto.PinckUpDeliveryDTO;
import com.ff.ud.dto.RegionRightsDTO;
import com.ff.ud.dto.VendorCodeDTO;
import com.ff.ud.dto.vendorDTO;
import com.ff.ud.service.BranchToHubServices;
import com.ff.ud.service.DispatchAndReceiveReportService;
import com.ff.ud.utils.StringUtils;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

@Controller
public class CommenRegionCityListController {
	
	static Logger logger = Logger.getLogger(CommenRegionCityListController.class.getName());
	
	@Autowired private BranchToHubServices branchToHubServices;
	@Autowired private DispatchAndReceiveReportService dispatchAndReceiveReportService;
	

	@ResponseBody
	@RequestMapping(value="/getCitiesByRegCodes1")
	public List<CityDTO> getCitiesByRegCode(@RequestParam(value="regCode") String regCode,HttpSession session){
		logger.info(" getCitiesByRegCode : fetching city "+regCode);
		
            String cityName=null;
            List<RegionRightsDTO>regionName=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 	System.out.println("User name==============>"+userId);
		 	System.out.println("Ofice Id==============>"+officeTypeId);
		List<CityDTO> cityDTO=null;
		//cityDTO=dispatchAndReceiveReportService.getBranchAllocationForManager(userId);
		if(!"Corporate Office".equalsIgnoreCase(cityName)){
			
			 regionName=branchToHubServices.getRegionAllocationForManager(userId);

			 if(regionName.size()>1)
			 {
				 cityDTO=branchToHubServices.getCityByRegCode(regCode);
			 }
			 
			 else{
				 cityDTO=dispatchAndReceiveReportService.getBranchAllocationForManager(userId);
			 }
		}
		if(StringUtils.isEmptyColletion(cityDTO)){
		if("1000".equalsIgnoreCase(regCode)){
			cityDTO=dispatchAndReceiveReportService.getCityCodeFromAllRegion();
		}else{
			cityDTO=branchToHubServices.getCityByRegCode(regCode);
		}
		
		}
		return cityDTO;
	}
	/**
	 * manger
	 * @param cityId
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/getOfficesByCityId")
	public List<OfficeDO> getOfficesByCityId(@RequestParam(value="cityId") Integer cityId,HttpSession session){
	
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		
		Integer cityName=cityId;
	    String cityName1=null;
	    List<RegionRightsDTO> regionName=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName1 =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 String officeCode= userInfoTO.getOfficeTo().getOfficeCode();
		 	System.out.println("User name==============>"+userId);
		 
	
		 

		List<OfficeDO> offices=new ArrayList<OfficeDO>();
		String value=String.valueOf(cityId);
		
		int length=value.length();
		logger.info(" getOfficesByCityIdForHub : Values "+length);
		String allVales=null;
		Integer allForval=null;
		if("5".equalsIgnoreCase(String.valueOf(length))){
			allVales=value.substring(0, 4);
			 allForval=Integer.valueOf(value.substring(3, 5));
		}else if("6".equalsIgnoreCase(String.valueOf(length))){
			allVales=value.substring(0, 4);
			 allForval=Integer.valueOf(value.substring(3, 6));
		}
		
		
		
		logger.info(" getOfficesByCityId : Values "+value);
		try{
			
			if(!"Corporate Office".equalsIgnoreCase(cityName1)){
				
				regionName=branchToHubServices.getRegionAllocationForManager(userId);
				 
				  if(regionName.size()>1)
				  {
					  offices=branchToHubServices.getOfficesByCityId(cityId) ;
				  }
				  else{
			      offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
				  }
			}
			if(StringUtils.isEmptyColletion(offices)){
			   MasterDAO masterDAO=new MasterDAO();
			   if("1000".equalsIgnoreCase(allVales)){
			List<AllFfMastDO> allOfficeNames=dispatchAndReceiveReportService.getOfficesByAll(allForval);
				
				try{
				  for(AllFfMastDO officeName :allOfficeNames){
					OfficeDO officeNames=new OfficeDO();
					officeNames.setOfficeCode(officeName.getOfficeCode());
			    	officeNames.setOfficeName(officeName.getOfficeName());
					offices.add(officeNames);
					
				}
				
				}catch(Exception e){e.printStackTrace();}}
			   
			   if("8".equalsIgnoreCase(String.valueOf(length))){
				   List<AllFfMastDO> allOfficeNames=dispatchAndReceiveReportService.getAllRegionAllOffice();
					
					try{
					  for(AllFfMastDO officeName :allOfficeNames){
						OfficeDO officeNames=new OfficeDO();
						officeNames.setOfficeCode(officeName.getOfficeCode());
				    	officeNames.setOfficeName(officeName.getOfficeName());
						offices.add(officeNames);
						
					}
					
					}catch(Exception e){e.printStackTrace();}
			   }
			   
			   
				logger.info(" getOfficesByAll : Values222 "+offices.size());

				   if(offices.size()==0){
					if(offices.size()==0 && "3".equalsIgnoreCase(String.valueOf(officeTypeId)) || 
							"5".equalsIgnoreCase(String.valueOf(officeTypeId))){
						
						OfficeDO officeNames=new OfficeDO();
						officeNames.setOfficeCode(officeCode);
						officeNames.setOfficeName(cityName1);
						offices.add(officeNames);
					}else{
						offices=branchToHubServices.getOfficesByCityId(cityId);
					 }
			  }
			}
			
			}	catch(Exception e){
			e.printStackTrace();
			logger.error(" getOfficesByCityId : not able to find the offices for [city : "+cityId+"]");
		}
		
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		
		
		return offices;
	}
	
	
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/getOfficesByCityIdForHub")
	public List<OfficeDO> getOfficesByCityIdForHub(@RequestParam(value="cityId") Integer cityId,HttpSession session){
		logger.info(" getOfficesByCityIdForHub : fetching offices for city "+cityId);
		
		Integer cityName=cityId;
		 String cityName1=null;
		 List<RegionRightsDTO> regionName=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName1 =userInfoTO.getOfficeTo().getOfficeName();
		String officeCode= userInfoTO.getOfficeTo().getOfficeCode();
		
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		System.out.println("User name==============>"+userId);
		 
		//List<OfficeDO> offices=null;
		List<OfficeDO> offices=new ArrayList<OfficeDO>();
		String value=String.valueOf(cityId);
		
		int length=value.length();
		logger.info(" getOfficesByCityIdForHub : Values "+length);
		String allVales=null;
		Integer allForval=null;
		if("5".equalsIgnoreCase(String.valueOf(length))){
			allVales=value.substring(0, 4);
			 allForval=Integer.valueOf(value.substring(3, 5));
		}else if("6".equalsIgnoreCase(String.valueOf(length))){
			allVales=value.substring(0, 4);
			 allForval=Integer.valueOf(value.substring(3, 6));
		}
		
		logger.info(" getOfficesByCityIdForHub : Values "+allVales);
		logger.info(" getOfficesByCityIdForHub : Values "+allForval);
		try{
			
			if(!"Corporate Office".equalsIgnoreCase(cityName1)){
				
				regionName=branchToHubServices.getRegionAllocationForManager(userId);
				 
				  if(regionName.size()>1)
				  {
					  offices=branchToHubServices.getOfficesByCityId(cityId) ;
				  }
				  else{
			      offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
				  }
				// offices=dispatchAndReceiveReportService.getOfficeNamesForManager(userId);
			}
			
			if(StringUtils.isEmptyColletion(offices) || offices.size()==0){
				if("1000".equalsIgnoreCase(allVales)){
				     List<AllFfMastDO> allOfficeNames=dispatchAndReceiveReportService.getOfficesByAllHUb(allForval);
				
				try{
				for(AllFfMastDO officeName :allOfficeNames){
					OfficeDO officeNames=new OfficeDO();
					officeNames.setOfficeCode(officeName.getOfficeCode());
					officeNames.setOfficeName(officeName.getOfficeName());
					offices.add(officeNames);
					
				}
				}catch(Exception e){e.printStackTrace();}
				}
				logger.info(" getOfficesByAll : Values222 "+offices.size());
				if(offices.size()==0){
					if(offices.size()==0 && "3".equalsIgnoreCase(String.valueOf(officeTypeId)) || 
							"5".equalsIgnoreCase(String.valueOf(officeTypeId))){
						
						OfficeDO officeNames=new OfficeDO();
						officeNames.setOfficeCode(officeCode);
						officeNames.setOfficeName(cityName1);
						offices.add(officeNames);
					}else{
					     offices=dispatchAndReceiveReportService.getOfficesByAllHUbByCityCode(cityId);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(" getOfficesByCityIdForHub : not able to find the offices for [city : "+cityId+"]");
		}
		
		logger.info(" getOfficesByCityIdForHub : fetching offices for city "+cityId);
		
		return offices;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getRegionForDestnationCity")
	public List<CityDTO> getCitiesForDestnationCity(@RequestParam(value="regCode") String regCode){
		return branchToHubServices.getCityByRegCode(regCode);
	}
	
	@ResponseBody
	@RequestMapping(value="/getCitiesByRegCodeFindOriginSta")
	public List<CityDTO> getCitiesByRegCodeFindOriginSta(@RequestParam(value="regCode") String regCode,HttpSession session){
		logger.info(" getCitiesByRegCodeFindOriginSta : fetching city "+regCode);
		String cityName=null;
		List<RegionRightsDTO>regionName=null;
		List<CityDTO> cityDTO=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 String officeCode=userInfoTO.getOfficeTo().getOfficeCode();
		 System.out.println("UserId ===============>"+userId);
		//cityName="HYD Hafeezpet HUB";
		 int length=regCode.length();
			
		 regionName=branchToHubServices.getRegionAllocationForManager(userId);
		 
		 if(regionName.size()>0){
			 cityDTO=branchToHubServices.getCityByRegCode(regCode);
		       }
		  else{
			   cityDTO=dispatchAndReceiveReportService.getBranchAllocationForManager(userId);
		      }
			String allVales=null;
			String allForval=null;
			if("5".equalsIgnoreCase(String.valueOf(length))){
				allVales=regCode.substring(0, 4);
				 allForval=regCode.substring(3, 5);
			}else if("6".equalsIgnoreCase(String.valueOf(length))){
				allVales=regCode.substring(0, 4);
				 allForval=regCode.substring(3, 6);
			}
		//List<CityDTO> cityDTO=null;
		if(allForval==null){
			cityDTO=dispatchAndReceiveReportService.getCityCodeByStation(regCode,officeCode);
		}else{
			cityDTO=dispatchAndReceiveReportService.getCityCodeByStation(allForval,cityName);
		}
		
		return cityDTO;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getCustomerNamesForLCCOD")
	public List<CustomerDTO> getCustomerNamesForLCCOD(@RequestParam(value="officeCode") String officeCode){
		logger.info(" getCustomerNamesForLCCOD : fetching officeCode "+officeCode);
		String cityName=null;
		List<CustomerDTO> customerDTO=null;
	    int officeCodeLength=officeCode.length();
	    
		logger.info(" getOfficesByCityIdForHub : Values "+officeCodeLength);
		String allValesForRegion=null;
		//Integer allForval=null;
		String allValue=null;
		if("7".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 7);		 
		}else if("5".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 5);
			
		}else if("6".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 6);
		}else if("9".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValesForRegion=officeCode.substring(8, 9);
		}else if("10".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValesForRegion=officeCode.substring(8, 10);
		}
		
	    
	    if(officeCodeLength ==4){
		    customerDTO=dispatchAndReceiveReportService.getLCCODForBranch(officeCode);
	     }/*else if(officeCodeLength==9 || officeCodeLength==10){
	    	 customerDTO=dispatchAndReceiveReportService.getCustomerNameByRegion(allValesForRegion);
	     }*/else {
	    	 customerDTO=dispatchAndReceiveReportService.getCustomerNameForAllOffice(officeCode);
	     }
	    if("100010001000".equalsIgnoreCase(officeCode)){
	    	customerDTO=dispatchAndReceiveReportService.getForAllLCCODCustomer();
	    	
	    }
	    
	    
		logger.info(" getCustomerNamesForLCCOD : fetching Customer Bussiness Names ");
		
		return customerDTO;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getCustomerNameFromOfficeCode")
	public List<CustomerDTO> getCustomerCodeFromOfficeCode(@RequestParam(value="officeCode") String officeCode){
		logger.info(" getCustomerCodeFromOfficeCode : fetching officeCode "+officeCode);
		String cityName=null;
		List<CustomerDTO> customerDTO=null;
	    int officeCodeLength=officeCode.length();
	    
		logger.info(" getOfficesByCityIdForHub : Values "+officeCodeLength);
		String allValesForRegion=null;
		//Integer allForval=null;
		String allValue=null;
		if("7".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 7);		 
		}else if("5".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 5);
			
		}else if("6".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValue=officeCode.substring(4, 6);
		}else if("9".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValesForRegion=officeCode.substring(8, 9);
		}else if("10".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			allValesForRegion=officeCode.substring(8, 10);
		}
		
		
	    if(officeCodeLength ==4){
		    customerDTO=dispatchAndReceiveReportService.getCustomerNameFromOFficeCode(officeCode);
	     }else if(officeCodeLength==9 || officeCodeLength==10){
	    	 customerDTO=dispatchAndReceiveReportService.getCustomerNameByRegion(allValesForRegion);
	     }else if(officeCodeLength >=10 ){
	    	 customerDTO=dispatchAndReceiveReportService.getCustomerNameFromOFficeCodeForAll(officeCode);
	     }else{
	    	 customerDTO=dispatchAndReceiveReportService.getCustomerNameFromOFficeCodeForAll(allValue);
	     }
	    	 
		logger.info(" getCustomerCodeFromOfficeCode : fetching Customer Bussiness Names ");
		
		return customerDTO;
	}
	
	@ResponseBody
	@RequestMapping(value="/getOfficesForALLOriginBranchs")
	public List<AllFfMastDO> getOfficeCodeAllOriginBranch(@RequestParam(value="officeCode") String officeCode,HttpSession session){
		logger.info(" getOfficeCodeAllOriginBranch : fetching officeCode "+officeCode);
		String cityName=null;
		//Integer regionId=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		cityName =userInfoTO.getOfficeTo().getOfficeName();
		System.out.println("-------> userInfoTO is <------- : "+userInfoTO);
		
		// regionId=userInfoTO.getUserto().getUserCode();
		 
		Integer regionCodeId=null;
		regionCodeId=dispatchAndReceiveReportService.getRegionIdForRegionCode(cityName);
		List<AllFfMastDO> regionId=new ArrayList<AllFfMastDO>();
		
		
		
		
		/*for(RegionDO officeTyeId :regionId1){
			regionCodeId=Integer.valueOf(officeTyeId.getRegionCode());
		 }
		*/
		
		System.out.println("-------> regionId is <------- : "+regionId);
		
		List<AllFfMastDO> officeDTO=null;
		officeDTO=dispatchAndReceiveReportService.getOfficeCodeForAllOriginOffice(regionCodeId);
		logger.info(" getOfficeCodeAllOriginBranch : fetching officeCode "+regionId);
		logger.info(" getOfficeCodeAllOriginBranch : fetching Customer Bussiness Names ");
		
		return officeDTO;
	}
	/*This method is use for getting only hub from station
	 * 
	 */
	
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/getOfficesByCityIdHub")
	public List<OfficeDO> getOfficesByCityIdHub(@RequestParam(value="cityId") Integer cityId,HttpSession session){
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		
		Integer cityName=cityId;
		/* String cityName=null;
		
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();getOfficesByCityId
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		System.out.println("User name==============>"+userId);*/
		 
		//List<OfficeDO> offices=null;
		List<OfficeDO> offices=new ArrayList<OfficeDO>();
		String value=String.valueOf(cityId);
		logger.info(" getOfficesByCityId : Values "+value);
		try{
			
				List<OfficeDO> allOfficeNames=dispatchAndReceiveReportService.getOfficesByAllHubs(cityId);
				
				try{
				for(OfficeDO officeName :allOfficeNames){
					OfficeDO officeNames=new OfficeDO();
					officeNames.setOfficeCode(officeName.getOfficeCode());
					officeNames.setOfficeName(officeName.getOfficeName());
					offices.add(officeNames);
					
				   }
				   }catch(Exception e){e.printStackTrace();}
				
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(" getOfficesByCityId : not able to find the offices for [city : "+cityId+"]");
		}
		
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		
		return offices;
	}
	
	public List<OfficeDO> getCustomerCodeForUps(@RequestParam(value="customerId") String customerId,HttpSession session){
		logger.info(" getCustomerCodeForUps : fetching Ups officeCodes for customerId "+customerId);
		List<OfficeDO> officeCode=new ArrayList<OfficeDO>();
		
		officeCode=dispatchAndReceiveReportService.getOfficeNamesByCustomerId(customerId);
		
		return null;
		
	}
	/*
	 * This is use for BA customer   getVendorNameByVendorId
	 */
	
	@ResponseBody
	@RequestMapping(value="/getBACustomerNameFromOfficeCode")
	public List<CustomerDTO> getBACustomerCodeFromOfficeCode(@RequestParam(value="officeCode") String officeCode){
		logger.info(" getBACustomerNameFromOfficeCode : fetching officeCode "+officeCode);
		String cityName=null;
		List<CustomerDTO> customerDTO=null;
		customerDTO=dispatchAndReceiveReportService.getBACustomerNameFromOFficeCode(officeCode);
		
		logger.info(" getCustomerCodeFromOfficeCode : fetching Customer Bussiness Names ");
		
		return customerDTO;
	}
	
	
	/*
	 * getVendorNameByVendorId
	 */
	
	@ResponseBody
	@RequestMapping(value="/getVendorNameByVendorId")
	public List<VendorCodeDTO> getVendorNameCodeByVendorId(@RequestParam(value="vendorId") String vendorId,
			@RequestParam(value="officeCode") String officeCode, HttpSession session){
		logger.info(" getVendorNameCodeByVendorId : fetching officeCode "+vendorId);
		logger.info(" getVendorNameCodeByVendorId : fetching officeCode "+officeCode);
		String cityName=null;
		List<VendorCodeDTO> vendorList=null;
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 String officeCodes= userInfoTO.getOfficeTo().getOfficeCode();
		 String allValue=null;
		 int officeCodeLength=officeCode.length();
		 if("7".equalsIgnoreCase(String.valueOf(officeCodeLength))){
				allValue=officeCode.substring(4, 7);		 
			}
		 
		 if("00000".equalsIgnoreCase(vendorId)){
			 vendorList=dispatchAndReceiveReportService.getEmployeeNameByFiledStaff(vendorId,officeCode);
		 }else if("4".equalsIgnoreCase(String.valueOf(officeCodeLength))){
			 vendorList=dispatchAndReceiveReportService.getVendorCodeDetailsByVendorId(vendorId,officeCode);
		 }else{
			 vendorList=dispatchAndReceiveReportService.getAllBranchForVendor(vendorId,officeCode);
		 }
		 
		logger.info(" getVendorNameCodeByVendorId ==============>End");
		
		return vendorList;
	}
	
	/*
	 * get customer from product id and office code
	 */
	@ResponseBody
	@RequestMapping(value="/getCustomerByProductId")
	public List<CustomerDTO> getCustomerByProductId(@RequestParam(value="productId") String productId,
			@RequestParam(value="officeCode") String officeCode, HttpSession session){
		System.out.println("------------------------------------------------------------------");
		logger.info(" getCustomerByProductId : fetching officeCode "+productId);
		logger.info(" getCustomerByProductId : fetching officeCode "+officeCode);
		String cityName=null;
		List<CustomerDTO> customerList=null;
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 String officeCodes= userInfoTO.getOfficeTo().getOfficeCode();
		 
		 customerList=dispatchAndReceiveReportService.getCustomerDetailsByProductId(productId,officeCode);
		 
		logger.info(" getCustomerByProductId ==============>End");
		
		return customerList;
	}
	@ResponseBody
	@RequestMapping(value="/getMonthWiseDate")
	public List<MonthWiseDateDTO> getMonthWiseDate(@RequestParam(value="values") String values, HttpSession session){
		
		logger.info(" getCustomerByProductId : fetching officeCode "+values);
		logger.info(" getCustomerByProductId : fetching officeCode "+values);
		String cityName=null;
		List<MonthWiseDateDTO> dateList=null;
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 String officeCodes= userInfoTO.getOfficeTo().getOfficeCode();
		 
		 int dateLength=values.length();
		 logger.info("Length of the Values   "+dateLength);
		 if("2".equalsIgnoreCase(String.valueOf(dateLength)) || "1".equalsIgnoreCase(String.valueOf(dateLength))){
			 dateList=dispatchAndReceiveReportService.getMonthWiseDateFormate(values); 
		 }else{
			 dateList=dispatchAndReceiveReportService.getMonthWiseDateEndDay(values);
		 }
		 
		 
		logger.info(" getCustomerByProductId ==============>End");
		logger.info(" getCustomerByProductId ==============> " +dateList.size());
		
		return dateList;
	}
	
	@ResponseBody
	@RequestMapping(value="/getVendorTypes")
	public List<VendorCodeDTO> getVendorTypes(@RequestParam(value="values") String values, HttpSession session){
		
		logger.info(" getVendorTypes : fetching officeCode "+values);
	
		String cityName=null;
		List<VendorCodeDTO> dateList=null;
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		 cityName =userInfoTO.getOfficeTo().getOfficeName();
		 Integer userId=userInfoTO.getUserto().getUserId();
		 Integer officeTypeId=userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeId();
		 String officeCodes= userInfoTO.getOfficeTo().getOfficeCode();
		 
	
			 dateList=dispatchAndReceiveReportService.getVendorCustomerType();
		
		 
		 
		logger.info("getVendorTypes ==============>End");

		
		return dateList;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getCustomerListByRegionId")
	public List<CustomerDTO> getCustomerListByRegionId(@RequestParam(value="regionId") String regionId){
		logger.info(" getCustomerListByRegionId : fetching regionId "+regionId);
		String cityName=null;
		List<CustomerDTO> customerList=null;
	   
		customerList=dispatchAndReceiveReportService.getCustomerListByRegionId(regionId);
	    return customerList;
	}
	@ResponseBody
	@RequestMapping(value="/getChequeNumbersByCustomerId")
	public List<ChequeDetailsDTO> getChequeNumbers(@RequestParam(value="customerId") String customerId ,
			@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate){
		logger.info(" getChequeNumbers : fetching regionId "+customerId);
		String cityName=null;
		List<ChequeDetailsDTO> ChequeDetailsDTOList=null;
	   
 		ChequeDetailsDTOList=dispatchAndReceiveReportService.getChequeNumbersByCustomerId(customerId,fromDate,toDate);
	    return ChequeDetailsDTOList;
	}
	


@ResponseBody
@RequestMapping(value="/getCustomerNamesForLCCODToPayFromRegion")
public List<CustomerDTO> getCustomerNamesForLCCODToPayFromRegion(@RequestParam(value="region") String region){
	logger.info(" getCustomerNamesForLCCODToPayFromRegion : fetching region "+region);
	String cityName=null;
	List<CustomerDTO> customerDTO=null;
    int regionLength=region.length();
    
	logger.info(" getOfficesByCityIdForHub : Values "+regionLength);

    customerDTO=dispatchAndReceiveReportService.getCustomerNamesForLCCODToPayFromRegion(region);
 

	
	return customerDTO;
}

}
