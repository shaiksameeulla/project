package com.ff.ud.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.BookingDetailsBean;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.service.BookingDetailsService;
import com.ff.ud.utils.DateUtils;


@Service
@Controller
public class BookingDetailsController {

	static Logger logger = Logger.getLogger(BookingDetailsController.class.getName());
	
	

	@Autowired private BookingDetailsService bookingDetailsService;
	
	
	@RequestMapping(value = "/generateBookingDetailsDBF",method=RequestMethod.GET)
	public String generateBookingDetailsDBFPage(ModelMap map,HttpServletResponse response) {
		String view="bookingDetails";
		
		List<RegionDO> regionList=bookingDetailsService.getRegion(); 
		List<CityDO> cityList = bookingDetailsService.getCity();
		//hub.generateManifestHODBFDTO(regionAlpahabate, product, dbGenDate);
		map.put("regionList", regionList);
		map.put("cityList", cityList);
		
		map.put("booking",new BookingDetailsBean());
		
		return view;
		
	}
	
	@RequestMapping(value = "/generateBookingDetailsDBF",method=RequestMethod.POST)
	public String generateBookingDetailsDBF(@ModelAttribute("booking") BookingDetailsBean booking,ModelMap map,HttpServletResponse response) {
		String filename=null;
		//String dispatchDate=DateUtils.formatDate( branchToHubFormBean.getDispatchDate(),"MM/dd/yyyy","dd/MM/yyyy");
		//String dispatchDate=DateUtils.formatDate( branchToHubFormBean.getDispatchDate(),"MM/dd/yyyy","yyyy-MM-dd");
		String displayMessage=null;
		String dbfFileName = null;
		Date startDate = null;
		Date endDate = null;
		
	    System.out.println(booking.toString());
		
      try {
			
			String startDate1 = DateUtils.formatDate(booking.getStartDate(),"MM/dd/yyyy","yyyy-MM-dd");
		    String endDate1 = DateUtils.formatDate(booking.getEndDate(),"MM/dd/yyyy","yyyy-MM-dd");
			
		    startDate = DateUtils.getDateFromString(startDate1, DateUtils.JAVA_SQL_DATE_FORMAT);
			endDate = DateUtils.getDateFromString(endDate1, DateUtils.JAVA_SQL_DATE_FORMAT);
			
			
			//dbfFileName = bookingDetailsScheduler.generateDBF(booking,startDate,endDate);
			
			displayMessage="DBF is generated sucessfully for City "+ booking.getCityId();
      } catch (Exception e) {
			displayMessage="Error accur while generating DBF";
			e.printStackTrace();
		}
		List<RegionDO> regionList=bookingDetailsService.getRegion(); 
		List<CityDO> cityList = bookingDetailsService.getCity();
		map.put("regionList", regionList);
		map.put("cityList", cityList);
		map.put("booking",new BookingDetailsBean());
		return "bookingDetails";
	   
			
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getOfficesByCity")
	public List<OfficeDO> getOfficesByCityId(@RequestParam(value="cityId") Integer cityId){
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		List<OfficeDO> offices=null;
		try{
			offices=bookingDetailsService.getOfficesByCityId(cityId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(" getOfficesByCityId : not able to find the offices for [city : "+cityId+"]");
		}
		
		logger.info(" getOfficesByCityId : fetching offices for city "+cityId);
		
		return offices;
	}

	
	@ResponseBody
	@RequestMapping(value="/getCitiesByRegCodes")
	public List<CityDTO> getCitiesByRegCode(@RequestParam(value="regCode") String regCode){
		return bookingDetailsService.getCityByRegCode(regCode);
	}

		
}

	
	

