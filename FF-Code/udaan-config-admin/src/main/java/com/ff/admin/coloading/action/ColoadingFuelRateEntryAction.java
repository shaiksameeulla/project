package com.ff.admin.coloading.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.form.FuelRateEntryForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.geography.CityDO;

/**
 * 
 * @author isawarka
 */
public class ColoadingFuelRateEntryAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingFuelRateEntryAction.class);

	/**
	 * Coloading service to perform the business validation and data base call
	 */
	private ColoadingService coloadingService;
	
	/**
	 * This method is used to render pre-populate data if user enter into the
	 * Fuel Rate Entry Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingFuelRateEntryAction:preparePage:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		ActionMessage actionMessage = null;
		try {
			FuelRateEntryForm coloadingForm = (FuelRateEntryForm) form;
			FuelRateEntryTO coloadingTo = (FuelRateEntryTO) coloadingForm.getTo();
			coloadingTo = new FuelRateEntryTO();
			loadCommonData(request, coloadingForm, coloadingTo);
			coloadingTo.setEffectiveFrom(DateUtil.getDDMMYYYYDateToString(DateUtil.getFutureDate(1)));
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingFuelRateEntryAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingFuelRateEntryAction:preparePage:End");
		return mapping.findForward(ColoadingConstants.FUEL_SUCCESS);
	}

	/**
	 * This method is used when user change the city from City drop down in 
	 * Fuel Rate Entry Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward loadExistingData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingFuelRateEntryAction:submitAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		FuelRateEntryForm coloadingForm = (FuelRateEntryForm) form;
		FuelRateEntryTO coloadingTo = (FuelRateEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			ColoadingFuelRateEntryDO fuelRateEntryDO  =	coloadingService.loadFuelRateEntryData(coloadingTo);
			if(fuelRateEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertFuelRateEntryToFromFuelRateEntryDo(coloadingTo, fuelRateEntryDO);
				if(ColoadingConstants.RENEW_R.equals(coloadingTo.getStoreStatus())){
					coloadingTo.setIsRenewalAllow(false);
				}else if(coloadingTo.getIsRenewalAllow() && ColoadingConstants.SUBMIT_P.equals(coloadingTo.getStoreStatus())){
					Date currentDate = DateUtil.getCurrentDate();
					if(fuelRateEntryDO.getEffectiveFrom().after(currentDate)){
						coloadingTo.setIsRenewalAllow(false);
					}
				}
			}
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingFuelRateEntryAction:submitAction:End");
		return mapping.findForward(ColoadingConstants.FUEL_SUCCESS);
	}
	
	public ActionForward submitAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingFuelRateEntryAction:submitAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		FuelRateEntryForm coloadingForm = (FuelRateEntryForm) form;
		FuelRateEntryTO coloadingTo = (FuelRateEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			coloadingTo.setStoreStatus(String.valueOf(ColoadingConstants.SUBMIT_CHAR));
			ColoadingFuelRateEntryDO fuelRateEntryDO  =	coloadingService.saveFuelRateEntry(coloadingTo, coloadingService.getUserID(request));
			if(fuelRateEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertFuelRateEntryToFromFuelRateEntryDo(coloadingTo, fuelRateEntryDO);
				actionMessage = new ActionMessage(ColoadingConstants.CL0005);
				Date currentDate = DateUtil.getCurrentDate();
				if(fuelRateEntryDO.getEffectiveFrom().after(currentDate)){
					coloadingTo.setIsRenewalAllow(false);
				}
			}
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingFuelRateEntryAction::submitAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingFuelRateEntryAction:submitAction:End");
		return mapping.findForward(ColoadingConstants.FUEL_SUCCESS);
	}
	
	public ActionForward renewAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingFuelRateEntryAction:renewAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		FuelRateEntryForm coloadingForm = (FuelRateEntryForm) form;
		FuelRateEntryTO coloadingTo = (FuelRateEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			ColoadingFuelRateEntryDO fuelRateEntryDO  =	coloadingService.loadFutureFuelRateEntryData(coloadingTo);
			if(fuelRateEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertFuelRateEntryToFromFuelRateEntryDo(coloadingTo, fuelRateEntryDO);
				if(coloadingTo.getIsRenewalAllow() && ColoadingConstants.SUBMIT_P.equals(coloadingTo.getStoreStatus())){
					Date currentDate = DateUtil.getCurrentDate();
					if(fuelRateEntryDO.getEffectiveFrom().after(currentDate)){
						coloadingTo.setIsRenewalAllow(false);
					}
				}
			}
			coloadingTo.setRenewFlag(ColoadingConstants.RENEW_R);
			coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::renewAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingFuelRateEntryAction::renewAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingFuelRateEntryAction::renewAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingFuelRateEntryAction:renewAction:End");
		return mapping.findForward(ColoadingConstants.FUEL_SUCCESS);
	}


	private void loadCommonData(HttpServletRequest request,
			FuelRateEntryForm coloadingForm, FuelRateEntryTO coloadingTo)
			throws CGBusinessException, CGSystemException {
		List<CityDO> cityList = coloadingService.getCityDOList();
		request.setAttribute(ColoadingConstants.ORIGION_CITY_LIST, cityList);
		
		request.setAttribute("to", coloadingTo);
		coloadingForm.setTo(coloadingTo);
		request.setAttribute("fuelColoadingForm", coloadingForm);
	}
	
	public ColoadingService getColoadingService() {
		return coloadingService;
	}

	public void setColoadingService(ColoadingService coloadingService) {
		this.coloadingService = coloadingService;
	}

}
