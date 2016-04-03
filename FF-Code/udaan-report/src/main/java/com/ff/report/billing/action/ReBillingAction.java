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
import com.ff.report.billing.form.ReBillingForm;
import com.ff.report.billing.service.ReBillingService;
import com.ff.report.constants.AdminSpringConstants;
import com.ff.to.billing.ReBillingTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author abarudwa
 *
 */
public class ReBillingAction extends AbstractBillingAction{
	
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReBillingAction.class);
	
	private ReBillingService reBillingService;
	
	/**
	 * @return the reBillingService
	 */
	public ReBillingService getReBillingService() {
		if(StringUtil.isNull(reBillingService)){
			reBillingService = (ReBillingService) getBean(AdminSpringConstants.RE_BILLING_SERVICE);
		}
		return reBillingService;
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

		LOGGER.debug("ReBillingAction::preparePage::START------------>:::::::");
		ReBillingTO reBillingTO = null;
		ReBillingForm reBillingForm = null;
		ActionMessage actionMessage = null;
		try {
			reBillingForm = (ReBillingForm) form;
			reBillingTO = (ReBillingTO) reBillingForm.getTo();
			reBillingService = getReBillingService();
			setDefaults(request, reBillingTO);

			List<RegionTO> regionsList;
			regionsList = reBillingService.getRegions();
			if (!StringUtil.isNull(regionsList)) {
				request.setAttribute(BillingConstants.REGIONS_LIST, regionsList);
			} 

		} catch (CGBusinessException e) {
			LOGGER.error("ReBillingAction::preparePage ..CGBusinessException :"
					+ e);
			 getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ReBillingAction::preparePage ..CGSystemException :"
					+ e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("ReBillingAction::preparePage ..Exception :" + e);
			getGenericException(request,e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ReBillingAction::preparePage::END------------>:::::::");
		return mapping.findForward(BillingConstants.SUCCESS);
	}
	
	
	private void setDefaults(HttpServletRequest request, ReBillingTO reBillingTO)
	{
		LOGGER.debug("ReBillingAction::setDefaults::START------------>:::::::");
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(BillingConstants.USER_INFO);
		
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			reBillingTO.setLoginOfficeId(officeTO.getOfficeId());
			reBillingTO.setLoginOfficeCode(officeTO.getOfficeCode());
		}
		LOGGER.debug("ReBillingAction::setDefaults::END------------>:::::::");
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
		
		LOGGER.debug("ReBillingAction::getStationsList::START------------>:::::::");		
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingService = getReBillingService();
		String region = request.getParameter("region");
		Integer regionId = Integer.parseInt(region);
		try {
			out = response.getWriter();
			List<CityTO> cityTOsList = reBillingService.getCitiesByRegionId(regionId);
			if(!CGCollectionUtils.isEmpty(cityTOsList)){
			jsonResult = serializer.toJSON(cityTOsList).toString();
			}
			
			
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getStationsList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getStationsList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingAction :: getStationsList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingAction::getStationsList::END------------>:::::::");
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
		
		LOGGER.debug("ReBillingAction::getBranchesList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingService = getReBillingService();
		String city = request.getParameter("station");
		Integer cityId = Integer.parseInt(city);
		try{
			out = response.getWriter();
			List<OfficeTO> officeTOsList = reBillingService.getOfficesByCityId(cityId);
			
			if(!CGCollectionUtils.isEmpty(officeTOsList)){
				jsonResult = serializer.toJSON(officeTOsList).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getBranchesList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getBranchesList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingAction :: getBranchesList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingAction::getBranchesList::END------------>:::::::");
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
		
		LOGGER.debug("ReBillingAction::getCustomersList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		reBillingService = getReBillingService();
		String office = request.getParameter("branch");
		Integer officeId = Integer.parseInt(office);
		try{
			out = response.getWriter();
			List<CustomerTO> customerTOsList = reBillingService.getCustomersByOfficeId(officeId);
			if(!CGCollectionUtils.isEmpty(customerTOsList)){
				jsonResult = serializer.toJSON(customerTOsList).toString();
			}
			
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getCustomersList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReBillingAction :: getCustomersList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ReBillingAction :: getCustomersList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReBillingAction::getCustomersList::END------------>:::::::");
	}
	
	
	public void reCalculate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ReBillingAction::reCalculate::START------------>:::::::");
		ReBillingTO rebillingTO = null;
		ReBillingTO rebillingTO2 = null;
		String errorMessage = null;
		String successMessage = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ReBillingForm reBillingForm = (ReBillingForm) form;
			rebillingTO = (ReBillingTO) reBillingForm.getTo();
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			rebillingTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			rebillingTO.setLoginOfficeCode(loggedInOfficeTO.getOfficeCode());
			//List<CustomerTO> customerTOsList = reBillingService.getCustomersByOfficeId(1);
			reBillingService = getReBillingService();
			rebillingTO2=reBillingService.saveOrUpdateReBilling(rebillingTO);
			
			/*successMessage = getMessageFromErrorBundle(request,
					LoadManagementConstants.GATEPASS_NUMBER_DETAILS_SAVED,
					new Object[] { loadMovementTO2.getGatePassNumber() });
			*/
			successMessage = getMessageFromErrorBundle(request,
					BillingConstants.REBILLING_NUMBER_DETAILS_SAVED,
					new Object[] { rebillingTO2.getRebillingNo() });
			
			reBillingService.getRebillingDetails();
			
			
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveLoadDispatch of ReBillingAction.."
					+ e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveLoadDispatch of ReBillingAction.."
					+ e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in reCalculate of ReBillingAction.."
					+ e);
		} finally {
			if(rebillingTO2==null){
				rebillingTO2 = new ReBillingTO();
			}
			rebillingTO2.setErrorMessage(errorMessage);
			rebillingTO2.setSuccessMessage(successMessage);
			String loadMovementTOJSON = JSONSerializer.toJSON(rebillingTO2)
					.toString();
			out.write(loadMovementTOJSON);
		}
		LOGGER.debug("ReBillingAction::reCalculate::END------------>:::::::");
	}

	
	
	@SuppressWarnings("static-access")
	public void reCalculateRates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("ReBillingAction::reCalculateRates::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try{
			ReBillingTO reBillingTO = null;
			ReBillingForm reBillingForm = null;
			reBillingForm = (ReBillingForm)form;
			reBillingTO = (ReBillingTO) reBillingForm.getTo();
			setDefaults(request, reBillingTO);
			out = response.getWriter();
			reBillingService = getReBillingService();
			//List<ConsignmentTO> consignmentTOs = reBillingService.getBookedConsignmentsByCustIdDateRange(reBillingTO.getCustomerTO().getCustomerId(),reBillingTO.getStartDateStr(), reBillingTO.getEndDateStr());
			//List<RateContractTO> rateContractTOs = reBillingService.getRateContractsByCustomerIds(reBillingTO.getCustomerTO().getCustomerId());
			/*List<RateCalculationOutputTO> rateCalculationOutputTOs = reBillingService.prepareRateInputs(consignmentTOs);
			jsonResult = serializer.toJSON(consignmentTOs).toString();//change consignmentTOs
*/		}catch(Exception e){
			LOGGER.error("Error occured in ReBillingAction :: reCalculateRates() ::"
					,e);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("ReBillingAction::reCalculateRates::END------------>:::::::");
	}
	
}
