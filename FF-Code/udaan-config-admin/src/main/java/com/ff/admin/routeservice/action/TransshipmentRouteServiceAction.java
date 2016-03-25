package com.ff.admin.routeservice.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

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
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.routeservice.common.action.AbstractRouteServiceAction;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.admin.routeservice.form.TransshipmentRouteForm;
import com.ff.routeserviced.TransshipmentRouteTO;
//import com.ff.umc.UserInfoTO;
//import com.ff.umc.UserTO;
//import com.ff.umc.constants.UmcConstants;

/**
 * @author rmaladi
 *
 */
public class TransshipmentRouteServiceAction extends AbstractRouteServiceAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransshipmentRouteServiceAction.class);
	
	
	/** 
     * View Form Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */

	public ActionForward viewTransshipmentRoute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RouteServiced::viewTransshipmentRoute::START------------>:::::::");
		TransshipmentRouteTO transshipmentRouteTO = new TransshipmentRouteTO();
		getDefultUIValues(request, transshipmentRouteTO);	
		((TransshipmentRouteForm) form).setTo(transshipmentRouteTO);
		LOGGER.debug("TransshipmentRouteServiceAction::viewTransshipmentRoute::END------------>:::::::");	
	
		return mapping.findForward(RouteServiceCommonConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * Load the default values into TO
     * @inputparam   pureRouteTO object
     * @return  Load the values into TO object 
     * @author      Rohini  Maladi  
     */

	
	private void getDefultUIValues(HttpServletRequest request, TransshipmentRouteTO transshipmentRouteTO){
		LOGGER.debug("TransshipmentRouteServiceAction::getDefultUIValues::START------------>:::::::");
		ActionMessage actionMessage = null;
		try{
			routeServicedService = getRouteServicedService();
			
			List<LabelValueBean> regList = getAllRegions(request);
			if(!CGCollectionUtils.isEmpty(regList)){
				request.setAttribute(RouteServiceCommonConstants.REGION_LIST, regList);
				transshipmentRouteTO.setTransshipmentRegionList(regList);		
			}else{
				actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DETAILS_NOT_FOUND_DB_ISSUE,RouteServiceCommonConstants.ROUTE_PURE);
				LOGGER.error("Exception happened in getDefultUIValues of TransshipmentRouteServiceAction...");				
			}
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefultUIValues of TransshipmentRouteServiceAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefultUIValues of TransshipmentRouteServiceAction..."+e.getMessage());
			actionMessage =  new ActionMessage(AdminErrorConstants.ROUTE_DB_ISSUE);			
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefultUIValues of TransshipmentRouteServiceAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);			
		}
		LOGGER.debug("TransshipmentRouteServiceAction::getDefultUIValues::END------------>:::::::");
	}
	
	/** 
     * Save Transshipment Route
     * This method save the transshipment route details in database
     * @inputparam  TransshipmentRouteForm will be passed with the following details filled in -
     *                                          <ul>
     *                                          <li>TransshipmentRouteTO                                          
     *                                          </ul>

     * @return  	
     * @author      Rohini  Maladi  
     */
	
	public void saveTransshipmentRoute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("TransshipmentRouteServiceAction::saveTransshipmentRoute::START------------>:::::::");
		TransshipmentRouteTO transshipmentRouteTO = null;
		String transshipmentRouteStatus = CommonConstants.FAILURE;
		PrintWriter out = null;
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String action = FrameworkConstants.EMPTY_STRING;
		try {	
			out = response.getWriter();
			TransshipmentRouteForm transshipmentRouteForm = (TransshipmentRouteForm) form;
			transshipmentRouteTO = (TransshipmentRouteTO) transshipmentRouteForm.getTo();
			action = transshipmentRouteTO.getPageAction(); 
			routeServicedService = getRouteServicedService();
			transshipmentRouteStatus = routeServicedService.saveTransshipmentRoute(transshipmentRouteTO);			
			if(transshipmentRouteStatus.equals(CommonConstants.SUCCESS)){
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
			LOGGER.error("Exception happened in saveTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in saveTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getLocalizedMessage());
			/*String exception=ExceptionUtil.getExceptionStackTrace(e);*/
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in saveTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getLocalizedMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		
		finally{			
				out.print(jsonResult);
				out.flush();
				out.close();
		}
		LOGGER.debug("TransshipmentRouteServiceAction::saveTransshipmentRoute::END------------>:::::::");
			
	}
	
	/** 
     * Load the transshipment route details into TO object
     * @inputparam   request object it contains originStation, destination, transportmode
     * @return  Load the transshipment route details into TO object 
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("static-access")
	public void getTransshipmentRoute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("TransshipmentRouteServiceAction::getTransshipmentRoute::START------------>:::::::");
		String searchDetails = null;
		PrintWriter out = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {	
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		TransshipmentRouteTO transshipmentRouteTO = null;
		TransshipmentRouteForm transshipmentRouteForm = (TransshipmentRouteForm) form;
		transshipmentRouteTO = (TransshipmentRouteTO) transshipmentRouteForm.getTo();
		if(!StringUtil.isStringEmpty(request.getParameter(RouteServiceCommonConstants.PARAM_TRANSSHIPMENT_CITY_ID))){
		transshipmentRouteTO.setTransshipmentCityId((Integer.parseInt(request.getParameter(RouteServiceCommonConstants.PARAM_TRANSSHIPMENT_CITY_ID))));
		List<TransshipmentRouteTO> transshipmentRouteTOList = new ArrayList<TransshipmentRouteTO>();
		routeServicedService = getRouteServicedService();
		transshipmentRouteTOList = routeServicedService.getTransshipmentRouteDetails(transshipmentRouteTO);
		if(!StringUtil.isNull(transshipmentRouteTO))
			searchDetails = serializer.toJSON(transshipmentRouteTOList).toString();
		else
			searchDetails = null;
		
			jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,searchDetails);
		}else{
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getMessageFromErrorBundle(request,AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRANSPMENT_STATION}));
		}
		}catch (CGSystemException e) {
			LOGGER.error("Exception happened in getTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getMessage());
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getTransshipmentRoute of TransshipmentRouteServiceAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("TransshipmentRouteServiceAction::getTransshipmentRoute::END------------>:::::::");
	}
	
}

