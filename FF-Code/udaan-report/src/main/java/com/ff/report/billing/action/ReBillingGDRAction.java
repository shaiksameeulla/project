/**
 * 
 */
package com.ff.report.billing.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.billing.constants.BillingConstants;
import com.ff.report.billing.form.ReBillingGDRForm;
import com.ff.report.billing.service.ReBillingGDRService;
import com.ff.report.constants.AdminSpringConstants;
import com.ff.to.billing.ReBillingGDRTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author abarudwa
 *
 */
public class ReBillingGDRAction extends AbstractBillingAction{
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReBillingGDRAction.class);
	
	private ReBillingGDRService reBillingGDRService;
	
	/**
	 * @return the reBillingService
	 */
	public ReBillingGDRService getReBillingService() {
		if(StringUtil.isNull(reBillingGDRService)){
			reBillingGDRService = (ReBillingGDRService) getBean(AdminSpringConstants.RE_BILLING_GDR_SERVICE);
		}
		return reBillingGDRService;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReBillingGDRAction::preparePage::START------------>:::::::");
		
		ReBillingGDRTO reBillingGDRTO = null;
		ReBillingGDRForm reBillingGDRForm = null;
		ActionMessage actionMessage = null;
		try{
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("reportUrl", composeReportUrl(userInfoTO.getConfigurableParams()));
			reBillingGDRForm = (ReBillingGDRForm) form;
			reBillingGDRTO = (ReBillingGDRTO) reBillingGDRForm.getTo();
			reBillingGDRService = getReBillingService();
			setDefaults(request, reBillingGDRTO);
			
			List<RegionTO> regionsList;
			regionsList = reBillingGDRService.getRegions();
			if(!CGCollectionUtils.isEmpty(regionsList)){
			request.setAttribute(BillingConstants.REGIONS_LIST, regionsList);
			}
			
		}catch (CGBusinessException e) {
			LOGGER.error("ReBillingGDRAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.NO_REGION_FOUND);
		} catch (CGSystemException e) {
			LOGGER.error("ReBillingGDRAction::preparePage ..CGSystemException :"+e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.NO_REGION_FOUND);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("ReBillingGDRAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ReBillingGDRAction::preparePage::END------------>:::::::");
		return mapping.findForward(BillingConstants.SUCCESS);
	}
	private void setDefaults(HttpServletRequest request, ReBillingGDRTO reBillingGDRTO)
	{
		LOGGER.debug("ReBillingGDRAction::setDefaults::START------------>:::::::");
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(BillingConstants.USER_INFO);
		
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			reBillingGDRTO.setLoginOfficeId(officeTO.getOfficeId());
			reBillingGDRTO.setLoginOfficeCode(officeTO.getOfficeCode());
		}
		LOGGER.debug("ReBillingGDRAction::setDefaults::END------------>:::::::");
	}
	
	/**
	 * To populate station list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getStationsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("ReBillingGDRAction::getStationsList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingGDRService = getReBillingService();
		String region = request.getParameter("region");
		Integer regionId = Integer.parseInt(region);
		try {
			out = response.getWriter();
			List<CityTO> cityTOsList = reBillingGDRService.getCitiesByRegionId(regionId);
			if(!CGCollectionUtils.isEmpty(cityTOsList)){
				jsonResult = serializer.toJSON(cityTOsList).toString();
			}
			
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getStationsList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getStationsList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingGDRAction :: getStationsList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingGDRAction::getStationsList::END------------>:::::::");
		
	}
	
	/**
	 * To populate branches list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getBranchesList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("ReBillingGDRAction::getBranchesList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingGDRService = getReBillingService();
		String city = request.getParameter("station");
		Integer cityId = Integer.parseInt(city);
		try{
			out = response.getWriter();
			List<OfficeTO> officeTOsList = reBillingGDRService.getOfficesByCityId(cityId);
			if(!CGCollectionUtils.isEmpty(officeTOsList)){
				jsonResult = serializer.toJSON(officeTOsList).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getBranchesList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getBranchesList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingGDRAction :: getBranchesList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingGDRAction::getBranchesList::END------------>:::::::");
	}
	/**
	 * To populate customer list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getCustomersList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("ReBillingGDRAction::getCustomersList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingGDRService = getReBillingService();
		String office = request.getParameter("branch");
		Integer officeId = Integer.parseInt(office);
		try{
			out = response.getWriter();
			List<CustomerTO> customerTOsList = reBillingGDRService.getCustomersByOfficeId(officeId);
			if(!CGCollectionUtils.isEmpty(customerTOsList)){
				jsonResult = serializer.toJSON(customerTOsList).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getCustomersList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingGDRAction :: getCustomersList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingGDRAction :: getCustomersList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingGDRAction::getCustomersList::END------------>:::::::");
	}
	
	public void getRebillNosDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ReBillingGDRAction::getRebillNosDetails::START------------>:::::::");
		ReBillingGDRTO rebillingGDRTO = null;
		ReBillingGDRTO rebillingGDRTO2 = null;
		String errorMessage = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ReBillingGDRForm reBillingGDRForm = (ReBillingGDRForm) form;
			rebillingGDRTO = (ReBillingGDRTO) reBillingGDRForm.getTo();
			
			reBillingGDRService = getReBillingService();
			rebillingGDRTO2=reBillingGDRService.getRebillDetails(rebillingGDRTO);
			request.setAttribute("reBillGdrTO", rebillingGDRTO2);
			
		/*	successMessage = getMessageFromErrorBundle(request,
					BillingConstants.REBILLING_NUMBER_DETAILS_SAVED,
					new Object[] { rebillingTO2.getRebillingNo() });*/
			
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in getRebillNosDetails of ReBillingGDRAction.."
					+ e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getRebillNosDetails of ReBillingGDRAction.."
					+ e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in getRebillNosDetails of ReBillingGDRAction.."
					+ e);
		} finally {
			if(rebillingGDRTO2==null){
				rebillingGDRTO2 = new ReBillingGDRTO();
			}
			rebillingGDRTO2.setErrorMessage(errorMessage);
			//rebillingGDRTO2.setSuccessMessage(successMessage);
			String rebillingGDRTOJSON = JSONSerializer.toJSON(rebillingGDRTO2)
					.toString();
			out.write(rebillingGDRTOJSON);
		}

		LOGGER.debug("ReBillingGDRAction::getRebillNosDetails::END------------>:::::::");
	
	}
}
