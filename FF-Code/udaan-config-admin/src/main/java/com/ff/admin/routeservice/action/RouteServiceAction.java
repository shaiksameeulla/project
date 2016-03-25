package com.ff.admin.routeservice.action;

import java.io.PrintWriter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.routeservice.common.action.AbstractRouteServiceAction;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.admin.routeservice.form.PureRouteForm;
import com.ff.to.routeservice.PureRouteTO;
import com.ff.transport.VehicleTO;
//import com.ff.umc.UserInfoTO;
//import com.ff.umc.UserTO;
//import com.ff.umc.constants.UmcConstants;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;

/**
 * @author rmaladi
 *
 */
public class RouteServiceAction extends AbstractRouteServiceAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(RouteServiceAction.class);
	
	private RouteServicedCommonService routeServicedCommonService;
	/** 
     * View Form Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */

	public ActionForward viewPureRoute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RouteServiceAction::viewPureRoute::START------------>:::::::");
		PureRouteTO pureRouteTO = new PureRouteTO();
		getDefultUIValues(request, pureRouteTO);	
		((PureRouteForm) form).setTo(pureRouteTO);
		LOGGER.debug("RouteServiceAction::viewPureRoute::END------------>:::::::");	
	
		return mapping.findForward(RouteServiceCommonConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * Load the default values into TO
     * @inputparam   pureRouteTO object
     * @return  Load the values into TO object 
     * @author      Rohini  Maladi  
     */

	
	private void getDefultUIValues(HttpServletRequest request, PureRouteTO pureRouteTO) {
		LOGGER.debug("RouteServiceAction::getDefultUIValues::START------------>:::::::");
		ActionMessage actionMessage = null;
		try{
		
		//HttpSession session =request.getSession(Boolean.FALSE);
		//UserInfoTO userInfoTO =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		//UserTO userTO = userInfoTO.getUserto();
		List<LabelValueBean> transportModeList = getAllTransportModeList(request);		
		if(!CGCollectionUtils.isEmpty(transportModeList)){
			request.setAttribute(RouteServiceCommonConstants.TRANSPORT_LIST, transportModeList);
			pureRouteTO.setTransportModeList(transportModeList);	
		}else{
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.TRNSPRT_MODE);
			LOGGER.error("Exception happened in getDefultUIValues of routeservicedAction...");			
		}
		
		List<LabelValueBean> regList = getAllRegions(request);
		if(!CGCollectionUtils.isEmpty(regList)){
			request.setAttribute(RouteServiceCommonConstants.REGION_LIST, regList);
			pureRouteTO.setOriginRegionList(regList);
			pureRouteTO.setDestinationRegionList(regList);
		}else{
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.REGION_DATA);
			LOGGER.error("Exception happened in getDefultUIValues of routeservicedAction...");			
		}
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefultUIValues of routeservicedAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefultUIValues of routeservicedAction..."+e.getMessage());
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DB_ISSUE);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefultUIValues of routeservicedAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		
		LOGGER.debug("RouteServiceAction::getDefultUIValues::END------------>:::::::");
	}
	
	/** 
     * Save Pure Route
     * This method save the pure route details in database
     * @inputparam  PureRouteForm will be passed with the following details filled in -
     *                                          <ul>
     *                                          <li>PureRouteTO                                          
     *                                          </ul>

     * @return  	
     * @author      Rohini  Maladi  
     */
	
	public void savePureRoute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RouteServicedAction::savePureRoute::START------------>:::::::");
		PureRouteTO pureRouteTO = null;
		String routeStatus = CommonConstants.FAILURE;
		PrintWriter out = null;
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String action = FrameworkConstants.EMPTY_STRING;
		// String unmappedVehicles = "Vehicle(s) not mapped with this region : ";
		String unmappedVehicles = " ";
		boolean check = true;
		try {	
			out = response.getWriter();
			PureRouteForm pureRouteForm = (PureRouteForm) form;
			pureRouteTO = (PureRouteTO) pureRouteForm.getTo();
			action = pureRouteTO.getPageAction(); 
			routeServicedService = getRouteServicedService();
			
			if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equalsIgnoreCase("Road")) {
				
				routeServicedCommonService = getRouteServicedCommonService();
				
				String[] transportNumbers = pureRouteTO.getTransportNumber();
				VehicleTO to = new VehicleTO();
				to.setRegionId(new Integer(pureRouteTO.getOriginRegion()));
				to.setAvailable(check);
				to.setTransportNumbers(transportNumbers);
				
				Set<String> availableVehicleSet = new HashSet<String>();
				List<VehicleTO> vehicleTOs = routeServicedCommonService.getVehicleDetails(to);
				for(VehicleTO availableVehicle : vehicleTOs) {
					availableVehicleSet.add(availableVehicle.getRegNumber().trim());
				}
				
				VehicleTO vehicleTO = new VehicleTO();
				vehicleTO.setRegionId(new Integer(pureRouteTO.getOriginRegion()));
				vehicleTO.setAllVehiclesInRegion(true);
				
	
				List<VehicleTO> vehicleTOList = routeServicedCommonService.getVehicleDetails(vehicleTO);
				Set<String> vehicleSet = new HashSet<String>();
				for(VehicleTO vehicle : vehicleTOList) {
					vehicleSet.add(vehicle.getRegNumber().trim());
				}
				
				for(int i=0; i< transportNumbers.length; i++){
					if((availableVehicleSet.contains(transportNumbers[i].trim())) && (! vehicleSet.contains(transportNumbers[i].trim()))) {
						unmappedVehicles = unmappedVehicles + transportNumbers[i].trim() + " ";
						
						if(i == (transportNumbers.length -1)) {
							throw new CGBusinessException(ColoadingConstants.CL0026);
							// throw new CGBusinessException("Vehicle(s) not mapped with this region : " + unmappedVehicles);
						}
					}
				}
			}			
			
			UserInfoTO userInfoTO =(UserInfoTO)request.getSession().getAttribute(UmcConstants.USER_INFO);
			UserTO userTO = userInfoTO.getUserto();
			pureRouteTO.setAirlinesMap(getAirlineDetailsFromSession(request));
			
			routeStatus = routeServicedService.savePureRoute(pureRouteTO, userTO.getUserId());			
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
			LOGGER.error("Exception happened in savePureRoute of RouteServicedAction..."+e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in savePureRoute of RouteServicedAction..."+e.getLocalizedMessage());
			/*String exception=ExceptionUtil.getExceptionDetails(e);*/
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in savePureRoute of RouteServicedAction..."+e.getLocalizedMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
				out.print(jsonResult);
				out.flush();
				out.close();
		}
		LOGGER.debug("RouteServicedAction::savePureRoute::END------------>:::::::");
			
	}
	/** 
     * Load the transport details into String object
     * @inputparam  request object it contains tranpsortNO (ex: flight number),transport mode
     * @return  Load the transport details into String object 
     * @author      Rohini  Maladi  
     */
	public void getTransportDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RouteServiceAction::getTransportDetails::START------------>:::::::");
		String transportId = null;
		PrintWriter out = null;
		String mode = FrameworkConstants.EMPTY_STRING;
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String transportNumber = null;
		Integer regionId = null;
		try {	

			out = response.getWriter();
		
			mode = request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_MODE);
			transportNumber = request.getParameter(RouteServiceCommonConstants.PARAM_TRANSPORT_NUMBER);
			regionId = 	Integer.parseInt(request.getParameter(RouteServiceCommonConstants.PARAM_REGION_ID));
			routeServicedService = getRouteServicedService();
			transportId = routeServicedService.getTransportDetails(mode, transportNumber, regionId);
			
			jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, transportId);
			
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getTransportDetails of RouteServicedAction..."+e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getTransportDetails of RouteServicedAction..."+e.getLocalizedMessage());
			/*String exception=ExceptionUtil.getExceptionStackTrace(e);*/
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getTransportDetails of RouteServicedAction..."+e.getLocalizedMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RouteServiceAction::getTransportDetails::END------------>:::::::");
	}	
}
