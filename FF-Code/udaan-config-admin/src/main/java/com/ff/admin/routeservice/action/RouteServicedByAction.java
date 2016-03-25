package com.ff.admin.routeservice.action;

import java.io.PrintWriter; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.routeservice.common.action.AbstractRouteServiceAction;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.admin.routeservice.form.RouteServicedByForm;
import com.ff.business.LoadMovementVendorTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.geography.CityTO;
import com.ff.routeserviced.RouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.TransportTO;
//import com.ff.umc.UserInfoTO;
//import com.ff.umc.UserTO;
//import com.ff.umc.constants.UmcConstants;
import com.ff.transport.VehicleTO;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;

/**
 * @author rmaladi
 *
 */
public class RouteServicedByAction extends AbstractRouteServiceAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(RouteServicedByAction.class);
	
	private RouteServicedCommonService routeServicedCommonService;
	
	/** 
     * View Form Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */

	public ActionForward viewRouteServicedBy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("viewRouteServicedBy::RouteServicedByAction::START------------>:::::::");
		TripServicedByTO tripServicedByTO = new TripServicedByTO();
		getDefultUIValues(request, tripServicedByTO);	
		((RouteServicedByForm) form).setTo(tripServicedByTO);
		LOGGER.debug("viewRouteServicedBy::RouteServicedByAction::END------------>:::::::");	
	
		return mapping.findForward(RouteServiceCommonConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * Load the default values into TO
     * @inputparam   TripServicedByTO object
     * @return  Load the values into TO object 
     * @author      Rohini  Maladi  
     */

	
	private void getDefultUIValues(HttpServletRequest request, TripServicedByTO tripServicedByTO){
		LOGGER.debug("getDefultUIValues::RouteServicedByAction::START------------>:::::::");
		ActionMessage actionMessage = null;
		try{
		
		//HttpSession session =request.getSession(Boolean.FALSE);
		//UserInfoTO userInfoTO =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		//UserTO userTO = userInfoTO.getUserto();
		List<LabelValueBean> transportModeList = getAllTransportModeList(request);		
		if(!CGCollectionUtils.isEmpty(transportModeList)){
			request.setAttribute(RouteServiceCommonConstants.TRANSPORT_LIST, transportModeList);
			tripServicedByTO.setTransportModeList(transportModeList);	
		}else{
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.TRNSPRT_MODE);
			LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction...");			
		}
		
		List<LabelValueBean> regList = getAllRegions(request);
		if(!CGCollectionUtils.isEmpty(regList)){
		request.setAttribute(RouteServiceCommonConstants.REGION_LIST, regList);
		tripServicedByTO.setOriginRegionList(regList);
		tripServicedByTO.setDestinationRegionList(regList);
		}else{
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.REGION_DATA);
			LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction...");			
		}
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction..."+e.getMessage());
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction..."+e.getMessage());
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DB_ISSUE);			
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);			
		}
		LOGGER.debug("getDefultUIValues::RouteServicedByAction::END------------>:::::::");
	}
	
	/** 
     * Save Pure Route
     * This method save the pure route details in database
     * @inputparam  TripServicedByForm will be passed with the following details filled in -
     *                                          <ul>
     *                                          <li>TripServicedByTO                                          
     *                                          </ul>

     * @return  	
     * @author      Rohini  Maladi  
     */
	
	public void saveTripServicedBy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RouteServicedByAction::saveTripServicedBy::START------------>:::::::");
		TripServicedByTO tripServicedByTO = null;
		String routeStatus = CommonConstants.FAILURE;
		PrintWriter out = null;
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String action = FrameworkConstants.EMPTY_STRING;
		try {	
			out = response.getWriter();
			RouteServicedByForm routeServicedByForm = (RouteServicedByForm) form;
			tripServicedByTO = (TripServicedByTO) routeServicedByForm.getTo();
			action = tripServicedByTO.getPageAction();
			
			routeServicedService = getRouteServicedService();
			
			routeStatus = routeServicedService.saveTripServicedBy(tripServicedByTO);			
			
			if(routeStatus.equals(CommonConstants.SUCCESS)){
				if(action.equals(RouteServiceCommonConstants.SAVE))
					jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_INFO_SAVED,null));
				else
					jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_INFO_UPDATED,null));	
			}else{
				if(action.equals(RouteServiceCommonConstants.SAVE))
					jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_INFO_NOT_SAVED,null));
				else
					jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_INFO_UPDATED,null));
			}
					
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in saveTripServicedBy of RouteServicedByAction..."+e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in saveTripServicedBy of RouteServicedByAction..."+e.getLocalizedMessage());
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in saveTripServicedBy of RouteServicedByAction..."+e.getLocalizedMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
				out.print(jsonResult);
				out.flush();
				out.close();
		}
		LOGGER.debug("RouteServicedByAction::saveTripServicedBy::END------------>:::::::");
			
	}
	/** 
     * Load the serviceby type list by transportmode
     * @inputparam  request object it contains transportmode object
     * @return  Load the serviceby type LIST into String object 
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("static-access")
	public void getServiceByType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RouteServicedByAction::getServiceByType::START------------>:::::::");		
		String serviceByTypeList = null;
		PrintWriter out = null;
		Integer transportModeId = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		
		if(!StringUtil.isStringEmpty(request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_MODE_ID))){
		transportModeId = StringUtil.parseInteger(request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_MODE_ID));
		
		routeServicedService = getRouteServicedService();
		List<LabelValueBean> serviceTypeList = routeServicedService.getServiceBytypeByTransportModeId(transportModeId);
		
		if(!CGCollectionUtils.isEmpty(serviceTypeList)){
			serviceByTypeList = serializer.toJSON(serviceTypeList).toString();
			jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG , serviceByTypeList);
		}else{
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE}));
		}
		}else{
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR,new String[]{RouteServiceCommonConstants.TRNSPRT_MODE}));
		}
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getServiceByType of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		}catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getServiceByType of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getBusinessErrorFromWrapper(request,e));
		}catch (Exception e) {
			LOGGER.error("Exception happened in getServiceByType of RouteServicedByAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);

		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RouteServicedByAction::getServiceByType::END------------>:::::::");
	}

	/** 
     * Load the route servicedby list
     * @inputparam  request object it contains 
     									<li> OriginStation
     									<li> DestinationStation
     									<li> TransportModeId
     									<li> ServiceByTypeId     									<li> OriginStation
     									<li> ServiceByTypeCode
     									<li> EffectiveFrom
     									<li> EffectiveTill
     * @return  Load the routeserviceby LIST into String object 
     * @author      Rohini  Maladi  
     */

	
	@SuppressWarnings({ "static-access", "rawtypes",})
	public void getTripServicedByDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedByAction::getTripServicedByDetails::START------------>:::::::");
		PrintWriter out = null;
		String transportMode = null;
		String originCity = null;
		String destinationCity = null;
		String serviceByType = null;
		String serviceByTypeCode = null;
		List tsByList = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		String tripServicedBy = FrameworkConstants.EMPTY_STRING;
		String transportModeCode = null;
		Integer regionId = null;
		String screenName = null;
		
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			
			transportMode = request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_MODE_ID);
			originCity = request.getParameter(RouteServiceCommonConstants.PARAM_ORIGIN_CITY_ID);
			destinationCity = request.getParameter(RouteServiceCommonConstants.PARAM_DEST_CITY_ID);
			serviceByType = request.getParameter(RouteServiceCommonConstants.PARAM_SERVICE_BY_TYPE_ID);
			serviceByTypeCode = request.getParameter(RouteServiceCommonConstants.PARAM_SERVICE_BY_TYPE_CODE);
			String effectiveFrom = request.getParameter(RouteServiceCommonConstants.PARAM_EFFECTIVE_FROM);
			String effectiveTo = request.getParameter(RouteServiceCommonConstants.PARAM_EFFECTIVE_TO);
			transportModeCode = request.getParameter("transportModeCode");
			regionId = Integer.parseInt(request.getParameter("originRegionId"));
			
			screenName = request.getParameter(RouteServiceCommonConstants.SCREEN_NAME);
	
	//		String coLoaderModule = RouteServiceCommonConstants.ROUTE_SERVICED_BY;
			
			routeServicedService = getRouteServicedService();
			
			tsByList = routeServicedService
						.getRouteServicedByDetails(transportMode, originCity,
								destinationCity, serviceByType, serviceByTypeCode,
								effectiveFrom, effectiveTo, regionId, transportModeCode, screenName);	 // , coLoaderModule
			if(tsByList.size() > 0) {
				if(tsByList.size() == 5) {
					if(null != tsByList.get(4)){
						Map<String, Set<String>> vendorToFlightsMap = (Map<String, Set<String>>) tsByList.get(4);
						HttpSession session =request.getSession(Boolean.FALSE);
						session.setAttribute("vendorToFlightsMap", vendorToFlightsMap);
					}
				}
				
				if( (null != screenName && screenName.equalsIgnoreCase(RouteServiceCommonConstants.AIR_CO_LOADER_RATE_ENTRY))
						|| (null != screenName && screenName.equalsIgnoreCase("Train Co-loader Rate Entry")) ) {
					ColoadingVendorTO to = null;
					List<ColoadingVendorTO> coloadingVendorTOs = null;
					
					ArrayList<LoadMovementVendorTO> loadMovementVendorTOs = (ArrayList<LoadMovementVendorTO>) tsByList.get(0);
					if (!CGCollectionUtils.isEmpty(loadMovementVendorTOs)) {
						coloadingVendorTOs = new ArrayList<>(loadMovementVendorTOs.size());
						for(LoadMovementVendorTO loadMovementVendorTO : loadMovementVendorTOs) {
							to = new ColoadingVendorTO();
							to.setVendorId(loadMovementVendorTO.getVendorId());
							to.setVendorCode(loadMovementVendorTO.getVendorCode());
							to.setBusinessName(loadMovementVendorTO.getBusinessName());
							coloadingVendorTOs.add(to);
						}
						
						if (!CGCollectionUtils.isEmpty(coloadingVendorTOs)) {
							request.setAttribute(ColoadingConstants.VENDOR_LIST_BY_ROUTE, coloadingVendorTOs);
							
							jsonResult = serializer.toJSON(coloadingVendorTOs).toString();
						}
					} else {
						jsonResult ="{\"ERROR\":\"ERROR\"}"; 
						// throw new CGBusinessException(ColoadingConstants.CL0001);
					}
				} else {
					tripServicedBy = serializer.toJSON(tsByList).toString();
					if(!StringUtil.isStringEmpty(tripServicedBy)){
						jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG , tripServicedBy);
					}else{
						jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE, new String[]{RouteServiceCommonConstants.TRIP_SERVICED}));
					}
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getTripServicedByDetails of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		}catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getTripServicedByDetails of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getBusinessErrorFromWrapper(request,e));
		}catch (Exception e) {
			LOGGER.error("Exception happened in getTripServicedByDetails of RouteServicedByAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RouteServicedByAction::getTripServicedByDetails::END------------>:::::::");
	}
	
	/** 
     * Load the Trip list
     * @inputparam  request object it contains 
     									<li> OriginStation
     									<li> DestinationStation
     									<li> TransportModeId
     									<li> ServiceByTypeId     									<li> OriginStation
     									<li> ServiceByTypeCode
     									<li> EffectiveFrom
     									<li> EffectiveTill
     * @return  Load the Trip LIST into String object 
     * @author      Rohini  Maladi  
     */

	@SuppressWarnings("static-access")
	public void getTripDetailsByVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedByAction::getTripDetailsByVendor::START------------>:::::::");
		String tripList = null;
		PrintWriter out = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		List<TripTO> tripTOList = null;
		String transportMode = null;
		String originCity = null;
		String destinationCity = null;
		String serviceByType = null;
		String serviceByTypeCode = null;
		String effectiveFrom = null;
		String effectiveTo = null;
		String vendor = null;
		String transportModeCode = null;
		Integer regionId = null;
		
		try {
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		
		transportMode = request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_MODE_ID);
		originCity = request.getParameter(RouteServiceCommonConstants.PARAM_ORIGIN_CITY_ID);
		destinationCity = request.getParameter(RouteServiceCommonConstants.PARAM_DEST_CITY_ID);
		serviceByType = request.getParameter(RouteServiceCommonConstants.PARAM_SERVICE_BY_TYPE_ID);
		serviceByTypeCode = request.getParameter(RouteServiceCommonConstants.PARAM_SERVICE_BY_TYPE_CODE);
		effectiveFrom = request.getParameter(RouteServiceCommonConstants.PARAM_EFFECTIVE_FROM);
		effectiveTo = request.getParameter(RouteServiceCommonConstants.PARAM_EFFECTIVE_TO);
		vendor = request.getParameter(RouteServiceCommonConstants.PARAM_VENDOR);
		transportModeCode = request.getParameter("transportModeCode");
		regionId = Integer.parseInt(request.getParameter("originRegionId"));
		
//		String coLoaderModule = RouteServiceCommonConstants.ROUTE_SERVICED_BY;
		
		routeServicedService = getRouteServicedService();
		tripTOList = routeServicedService.getTripList(transportMode, originCity,
				destinationCity, serviceByType, serviceByTypeCode,
				effectiveFrom, effectiveTo,vendor, transportModeCode, regionId); // , coLoaderModule
		
		if(!CGCollectionUtils.isEmpty(tripTOList)){	
			tripList = serializer.toJSON(tripTOList).toString();		
			jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG , tripList);
		}else{
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getMessageFromErrorBundle(request,AdminErrorConstants.TRIP_DETAILS_NOT_FOUND, null));
		}
		}catch (CGSystemException e) {
			LOGGER.error("Exception happened in getTripDetailsByVendor of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		}catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getTripDetailsByVendor of RouteServicedByAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getBusinessErrorFromWrapper(request,e));
		}catch (Exception e) {
			LOGGER.error("Exception happened in getTripDetailsByVendor of RouteServicedByAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RouteServicedByAction::getTripDetailsByVendor::END------------>:::::::");
	}
	
	

	

}

