package com.ff.admin.routeservice.common.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.admin.routeservice.form.PureRouteForm;
import com.ff.admin.routeservice.service.RouteServicedService;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.routeserviced.RouteTO;
import com.ff.to.routeservice.PureRouteTO;
import com.ff.transport.AirlineTO;
import com.ff.transport.TransportModeTO;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;


public class AbstractRouteServiceAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractRouteServiceAction.class);
	
	public RouteServicedService routeServicedService;
	public RouteServicedCommonService routeServicedCommonService;
	
	public transient JSONSerializer serializer;	
	
	public void setRouteServicedService(RouteServicedService routeServicedService) {
		this.routeServicedService = routeServicedService;
	}	

	public void setRouteServicedCommonService(RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}
	
	/** 
     * Load the trip details into TO object
     * @inputparam   request object it contains originStation, destination, transportmode
     * @return  Load the trip details into TO object 
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("static-access")
	public void getRouteByOriginStationAndDestinationStation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("AbstractRouteServiceAction::getRouteByOriginStationAndDestinationStation::START------------>:::::::");
		String searchDetails = null;
		PrintWriter out = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		Integer regionId = null;
		try {	
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		PureRouteTO pureRouteTO = null;
		RouteTO routeTO = new RouteTO();
		CityTO originCityTO = new CityTO();
		CityTO destCityTO = new CityTO();
		if(!StringUtil.isStringEmpty(request.getParameter(RouteServiceCommonConstants.PARAM_ORIGIN_CITY_ID))){
			originCityTO.setCityId(Integer.parseInt(request.getParameter(RouteServiceCommonConstants.PARAM_ORIGIN_CITY_ID)));
			routeTO.setOriginCityTO(originCityTO);
		
			if(!StringUtil.isStringEmpty(request.getParameter(RouteServiceCommonConstants.PARAM_DEST_CITY_ID))){
				destCityTO.setCityId(Integer.parseInt(request.getParameter(RouteServiceCommonConstants.PARAM_DEST_CITY_ID)));
				routeTO.setDestCityTO(destCityTO);
			
				regionId = Integer.parseInt(request.getParameter("originRegionId")); 
				TransportModeTO transportModeTO = new TransportModeTO();
				if(!StringUtil.isStringEmpty(request.getParameter(RouteServiceCommonConstants.QRY_TRANSPORT_MODE))){
					transportModeTO.setTransportModeId(Integer.parseInt(request.getParameter(RouteServiceCommonConstants.QRY_TRANSPORT_MODE)));
			
					transportModeTO.setTransportModeCode(request.getParameter("transportModeCode"));
					PureRouteForm pureRouteForm = (PureRouteForm) form;
					pureRouteTO = (PureRouteTO) pureRouteForm.getTo();
					routeServicedService = getRouteServicedService();
					pureRouteTO = routeServicedService.getTripDetailsByRoute(routeTO,transportModeTO, regionId);
					if(!StringUtil.isNull(pureRouteTO)){
						searchDetails = serializer.toJSON(pureRouteTO).toString();
					}else{
						searchDetails = null;
					}
					
					jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,searchDetails);
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR,new String[]{RouteServiceCommonConstants.TRNSPRT_MODE}));
			}
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR,new String[]{RouteServiceCommonConstants.ORIGIN_CITY}));
			}
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR,new String[]{RouteServiceCommonConstants.DEST_CITY}));
			}
		}catch (CGSystemException e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRouteServiceAction::getRouteByOriginStationAndDestinationStation::END------------>:::::::");
	}
	

	
	
	/** 
     * Load the Citi list by Region
     * @inputparam  request object it contains Region object
     * @return  Load the CITI LIST into String object 
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("static-access")
	public void getStationsByRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("AbstractRouteServiceAction::getStationsByRegion::START------------>:::::::");		
		String jsonResult = null;
		PrintWriter out = null;
		String regionId = FrameworkConstants.EMPTY_STRING;		
		try {
		serializer = CGJasonConverter.getJsonObject();
		regionId = request.getParameter(RouteServiceCommonConstants.PARAM_REGION_ID);
		out = response.getWriter();
		
		RegionTO regionTO = new RegionTO();
		if(!StringUtil.isStringEmpty(regionId)){
			regionTO.setRegionId(StringUtil.parseInteger(regionId));
			
			routeServicedService = getRouteServicedService();
			List<CityTO> cityTOList = routeServicedService.getStationsByRegion(regionTO);
			
			if(!CGCollectionUtils.isEmpty(cityTOList)){
				jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG , serializer.toJSON(cityTOList).toString());
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE, new String[]{RouteServiceCommonConstants.STATION}));
			}
		}else{
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR,new String[]{RouteServiceCommonConstants.REGION_DATA}));
		}
		}catch (CGSystemException e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getBusinessErrorFromWrapper(request,e));
		}catch (Exception e) {
			LOGGER.error("Exception happened in getRouteByOriginStationAndDestinationStation of RouteServicedAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRouteServiceAction::getStationsByRegion::END------------>:::::::");
	}
	
	
	/** 
     * Get Route service Object
     * This method will return  Route service Object 
     * @inputparam 
     * @return  	RouteServicedService Object
     * @author      Rohini  Maladi  
     */
	
	public RouteServicedService getRouteServicedService()
	{
		if(StringUtil.isNull(routeServicedService)) {
			routeServicedService = (RouteServicedService)getBean(AdminSpringConstants.ROUTE_SERVICED_SERVICE);
		}
		return routeServicedService;
	}
	
	public RouteServicedCommonService getRouteServicedCommonService() {
		if(StringUtil.isNull(routeServicedCommonService)) {
			routeServicedCommonService = (RouteServicedCommonService)getBean(AdminSpringConstants.ROUTE_SERVICED_COMMON_SERVICE);
		}
		return routeServicedCommonService;
	}
	
	/** 
     * Get TransportMode list
     * This method will return  TransportMode List 
     * @inputparam 
     * @return  	TransportMode List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("unchecked")
	public List<LabelValueBean> getAllTransportModeList(HttpServletRequest request) throws CGBusinessException, CGSystemException{
		LOGGER.debug("AbstractRouteServiceAction::getAllTransportModeList::START------------>:::::::");
		HttpSession session=request.getSession(false);
		List<LabelValueBean> transportModeList = null;
			transportModeList = (List<LabelValueBean> )session.getAttribute(RouteServiceCommonConstants.TRANSPORT_MODE);

			if(CGCollectionUtils.isEmpty(transportModeList)) {
				routeServicedService = getRouteServicedService();
				transportModeList = routeServicedService.getAllTransportModeList();
				session.setAttribute(RouteServiceCommonConstants.TRANSPORT_MODE, transportModeList);
			}
		if(CGCollectionUtils.isEmpty(transportModeList)){
			ActionMessage actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.TRNSPRT_MODE);
			prepareActionMessage(request, actionMessage);
			LOGGER.error("Exception happened in getAllRegions of AbstractRouteServicedAction...");
		}
		LOGGER.debug("AbstractRouteServiceAction::getAllTransportModeList::END------------>:::::::");
		
		return transportModeList;

		
	}

	/** 
     * Get Region list
     * This method will return  Region List 
     * @inputparam 
     * @return  	Region List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
	@SuppressWarnings("unchecked")
	public List<LabelValueBean> getAllRegions(HttpServletRequest request) throws CGBusinessException, CGSystemException{
		LOGGER.debug("AbstractRouteServiceAction::getAllRegions::START------------>:::::::");		
		HttpSession session=request.getSession(false);		
		List<LabelValueBean> regList = null;
		regList = (List<LabelValueBean>)session.getAttribute(RouteServiceCommonConstants.REGION);

		if(CGCollectionUtils.isEmpty(regList)) {
			regList = new ArrayList<LabelValueBean>();
			routeServicedService = getRouteServicedService();
			List<RegionTO> regionList = routeServicedService.getAllRegions();
			for(RegionTO regionTO:regionList){
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(regionTO.getRegionName());
				lvb.setValue(regionTO.getRegionId()+"");
				regList.add(lvb);			
			}
			session.setAttribute(RouteServiceCommonConstants.REGION, regList);
		}
		if(CGCollectionUtils.isEmpty(regList)){
			ActionMessage actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.ROUTE_PURE);
			prepareActionMessage(request, actionMessage);
			LOGGER.error("Exception happened in getAllRegions of AbstractRouteServicedAction...");
		}
		LOGGER.debug("AbstractRouteServiceAction::getAllRegions::END------------>:::::::");
		return regList;
	}
	/**
	 * ajaxGetAllAirlinesMap : to retrieve all airlines details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void ajaxGetAllAirlinesMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out=null;
		Map<String,String> airlineSessionHolder = null;
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			airlineSessionHolder=getAirlineDetailsFromSession(request);

		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRouteServiceAction:: ajaxGetAllAirlinesMap", e);
		}catch (CGSystemException e) {
			LOGGER.error("AbstractRouteServiceAction:: ajaxGetAllAirlinesMap", e);
		}catch (Exception e) {
			LOGGER.error("AbstractRouteServiceAction:: ajaxGetAllAirlinesMap", e);
		}

		finally {
			out.print(CollectionUtils.isEmpty(airlineSessionHolder)?null:airlineSessionHolder.toString());
			out.flush();
			out.close();
		}

	}

	public Map<String, String> getAirlineDetailsFromSession(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		Map<String, String> airlineSessionHolder;
		HttpSession session=request.getSession(false);	
		airlineSessionHolder = (Map<String,String>)session.getAttribute(RouteServiceCommonConstants.AIRLINE_SESSION);

		if(CGCollectionUtils.isEmpty(airlineSessionHolder)) {
			routeServicedService = getRouteServicedService();
			List<AirlineTO> airlineList = routeServicedService.getAllAirlineDetails();
			if(!CGCollectionUtils.isEmpty(airlineList)){
				airlineSessionHolder=new HashMap<String, String>(airlineList.size());
				for(AirlineTO regionTO:airlineList){
					airlineSessionHolder.put(regionTO.getAirlineCode(),regionTO.getAirlineName());
				}
				session.setAttribute(RouteServiceCommonConstants.AIRLINE_SESSION, airlineSessionHolder);
			}
			
		}
		return airlineSessionHolder;
	}
	}
