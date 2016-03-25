package com.ff.admin.coloading.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.form.VehiclesContractColoadingForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * 
 * @author isawarka
 */
public class ColoadingVehiclesContractAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingVehiclesContractAction.class);

	private ColoadingService coloadingService;

	public ActionForward preparePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
		LOGGER.debug("VehiclesContractAction:preparePage:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		VehiclesContractColoadingForm coloadingForm = (VehiclesContractColoadingForm) form;
		ColoadingVehicleContractTO coloadingTo = (ColoadingVehicleContractTO) coloadingForm.getTo();
		coloadingTo = new ColoadingVehicleContractTO();
		ActionMessage actionMessage = null;
		String url = ColoadingConstants.VEHICLES_COLOADING_SUCCESS;
		try {
			if(!isLoggedInOfficeRHO(request)){
				url = ColoadingConstants.VEHICLE_COLOADING_WELCOME;
				actionMessage = new ActionMessage(ColoadingConstants.VEHICLE_COLOADING_ONLY_ALLOWED_AT_RHO);
			} else {
				loadCommonData(request, coloadingTo, coloadingForm);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("VehiclesContractAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("VehiclesContractAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("VehiclesContractAction::preparePage ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("VehiclesContractAction:preparePage:End");
		return mapping.findForward(ColoadingConstants.VEHICLES_COLOADING_SUCCESS);
	}
	
	/**
	 * To check whether Logged In Office is brach/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isLoggedInOfficeRHO(HttpServletRequest request){
		LOGGER.debug("ValidateExpenseAction::isLoggedInOfficeRHO()::START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO!=null && loggedInOfficeTO.getOfficeTypeTO()!=null 
				&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
			result = true;
		}
		LOGGER.debug("ColoadingVehiclesContractAction::isLoggedInOfficeRHO()::END");
		return result;
	}	
	
	/**
	 * This method is used Renew the Configured Rate into the data base if user
	 * clicks on Renew Button and then click on Save Button on Train Coloading
	 * screen.Saved data can be Submited later on
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward renewAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:renewAction:Start");
			
			VehiclesContractColoadingForm coloadingForm = (VehiclesContractColoadingForm) form;
			ColoadingVehicleContractTO coloadingTo = (ColoadingVehicleContractTO) coloadingForm.getTo();
			
			ColoadingVehicleContractDO  vehicleContractDO =	coloadingService.searchVehicleFutureData(coloadingTo);
			if(vehicleContractDO != null){
				// set GpsEnabled field
				vehicleContractDO.setGpsEnabled(coloadingTo.getGpsEnabled());
				
				coloadingTo = ColoadingCommonConverter.convertVehicleToFromVehicleDo(coloadingTo, vehicleContractDO);
				if(ColoadingConstants.RENEW_R.equals(coloadingTo.getStoreStatus())){
					coloadingTo.setIsRenewalAllow(false);
				}else if(coloadingTo.getIsRenewalAllow() && ColoadingConstants.SUBMIT_P.equals(coloadingTo.getStoreStatus())){
					Date currentDate = DateUtil.getCurrentDate();
					if(vehicleContractDO.getEffectiveFrom().after(currentDate)){
						coloadingTo.setIsRenewalAllow(false);
					}
				}
			}
			coloadingTo.setRenewFlag(ColoadingConstants.RENEW_R);
			coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
		
			loadCommonData(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::renewAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::renewAction ..CGSystemException :"
					+ e); // actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::renewAction ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:renewAction:End");
		return mapping.findForward(ColoadingConstants.VEHICLES_COLOADING_SUCCESS);
	}
	
	public ActionForward searchAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("VehiclesContractAction:searchAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		VehiclesContractColoadingForm coloadingForm = (VehiclesContractColoadingForm) form;
		ColoadingVehicleContractTO coloadingTo = (ColoadingVehicleContractTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			ColoadingVehicleContractDO  vehicleContractDO =	coloadingService.searchVehicle(coloadingTo);
			if(vehicleContractDO != null){
				coloadingTo = ColoadingCommonConverter.convertVehicleToFromVehicleDo(coloadingTo, vehicleContractDO);
				if(ColoadingConstants.RENEW_R.equals(coloadingTo.getStoreStatus())){
					coloadingTo.setIsRenewalAllow(false);
				}else if(coloadingTo.getIsRenewalAllow() && ColoadingConstants.SUBMIT_P.equals(coloadingTo.getStoreStatus())){
					Date currentDate = DateUtil.getCurrentDate();
					if(vehicleContractDO.getEffectiveFrom().after(currentDate)){
						coloadingTo.setIsRenewalAllow(false);
					}
				}
			}else{
				String vehicleNo = coloadingTo.getVehicleNo();
				String effectiveFrom = coloadingTo.getEffectiveFrom();
				coloadingTo = new ColoadingVehicleContractTO();
				coloadingTo.setVehicleNo(vehicleNo);
				coloadingTo.setEffectiveFrom(effectiveFrom);
			}
			loadCommonData(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("VehiclesContractAction::searchAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("VehiclesContractAction::searchAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("VehiclesContractAction::searchAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("VehiclesContractAction:searchAction:End");
		return mapping.findForward(ColoadingConstants.VEHICLES_COLOADING_SUCCESS);
	}

	public ActionForward submitAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("VehiclesContractAction:submitAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		VehiclesContractColoadingForm coloadingForm = (VehiclesContractColoadingForm) form;
		ColoadingVehicleContractTO coloadingTo = (ColoadingVehicleContractTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		OfficeTO officeTo = null;
		try {
			
			HttpSession session = request.getSession();
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(ColoadingConstants.USER_INFO);
			if(userInfoTO != null && userInfoTO.getOfficeTo() != null) {
				officeTo = userInfoTO.getOfficeTo();
			}
			
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			coloadingTo.setStoreStatus(ColoadingConstants.SUBMIT_P);
			ColoadingVehicleContractDO  vehicleContractDO =	coloadingService.saveColoadingVehicle(coloadingTo, coloadingService.getUserID(request), officeTo);
			if(vehicleContractDO != null){
				coloadingTo = ColoadingCommonConverter.convertVehicleToFromVehicleDo(coloadingTo, vehicleContractDO);
				if(ColoadingConstants.SUBMIT_P.equals(String.valueOf(vehicleContractDO.getStoreStatus()))){
					actionMessage = new ActionMessage(ColoadingConstants.CL0005);
				}else{
					vehicleContractDO.setStoreStatus(ColoadingConstants.SUBMIT_CHAR);
					actionMessage = new ActionMessage(ColoadingConstants.CL0018);
				}
				Date currentDate = DateUtil.getCurrentDate();
				if(vehicleContractDO.getEffectiveFrom().after(currentDate)){
					coloadingTo.setIsRenewalAllow(false);
				}
			}else{
				actionMessage = new ActionMessage(ColoadingConstants.CL0012);
				coloadingTo.setStoreStatus(null);
			}
			loadCommonData(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("VehiclesContractAction::submitAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("VehiclesContractAction::submitAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("VehiclesContractAction::submitAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("VehiclesContractAction:submitAction:End");
		return mapping.findForward(ColoadingConstants.VEHICLES_COLOADING_SUCCESS);
	}

	private void loadCommonData(HttpServletRequest request,ColoadingVehicleContractTO coloadingTo, VehiclesContractColoadingForm coloadingForm)
			throws CGBusinessException, CGSystemException {
		List<StockStandardTypeTO> typeList = coloadingService
				.getStockStdType(ColoadingConstants.VEHICLE_TYPE);
		if (typeList != null) {
		request.setAttribute(ColoadingConstants.VEHICLE_TYPE_LIST,
				typeList);
		}
		typeList = coloadingService
				.getStockStdType(ColoadingConstants.RATE_TYPE);
		if (typeList != null) {
			request.setAttribute(ColoadingConstants.RATE_TYPE_LIST,
					typeList);
			typeList = null;
		}
		typeList = coloadingService
				.getStockStdType(ColoadingConstants.DUTY_HOURS);
		if (typeList != null) {
			request.setAttribute(ColoadingConstants.DUTY_HOURS_LIST,
					typeList);
			typeList = null;
		}
		typeList = coloadingService
				.getStockStdType(ColoadingConstants.FUEL_TYPE);
		if (typeList != null) {
			request.setAttribute(ColoadingConstants.FUEL_TYPE_LIST,
					typeList);
			typeList = null;
		}
		
		Integer regionId = getRegionForLoggedinUser(request);
		if(regionId != null){
		List<ColoadingVendorTO> coloadingVendorTOs = coloadingService
				.getVendorsList(regionId, ColoadingConstants.ROAD);
		request.setAttribute(ColoadingConstants.VENDOR_LIST, coloadingVendorTOs);
		}
		request.setAttribute("to", coloadingTo);
		coloadingForm.setTo(coloadingTo);
		request.setAttribute("vehiclesContractColoadingForm", coloadingForm);
	} 
	
	
	/**
	 * This method is used to get the regionId for logged in user
	 * 
	 * @param request
	 * @return
	 */
	private Integer getRegionForLoggedinUser(HttpServletRequest request) {
		LOGGER.debug("ColoadingAction:getRegionForLoggedinUser:Start");
		HttpSession session = request.getSession();
		Integer regionID = null;
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(ColoadingConstants.USER_INFO);
		if(userInfoTO != null && userInfoTO.getOfficeTo() != null && "1000".equals(userInfoTO.getOfficeTo().getOfficeCode())){
			regionID = -1;
		}else{
		if (userInfoTO != null && userInfoTO.getOfficeTo() != null && userInfoTO.getOfficeTo().getRegionTO() != null) {
			if (!StringUtil.isNull(userInfoTO.getOfficeTo().getRegionTO().getRegionId())) {
				regionID = userInfoTO.getOfficeTo().getRegionTO().getRegionId();
			}
		}
		}
		LOGGER.debug("ColoadingAction:getRegionForLoggedinUser:End");
		return regionID;
	}
	
}
