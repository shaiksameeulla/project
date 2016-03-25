package com.ff.admin.coloading.action;

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

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.form.SurfaceRateEntryForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.SurfaceRateEntryTO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.umc.UserInfoTO;

/**
 * 
 * @author isawarka
 */
public class ColoadingSurfaceRateEntryAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingSurfaceRateEntryAction.class);

	/**
	 * Coloading service to perform the business validation and data base call
	 */
	private ColoadingService coloadingService;
	
	/**
	 * This method is used to render pre-populate data if user enter into the
	 * Rate Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingSurfaceRateEntryAction:preparePage:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		ActionMessage actionMessage = null;
		try {
			SurfaceRateEntryForm coloadingForm = (SurfaceRateEntryForm) form;
			SurfaceRateEntryTO coloadingTo = (SurfaceRateEntryTO) coloadingForm.getTo();
			coloadingTo = new SurfaceRateEntryTO();
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingSurfaceRateEntryAction:preparePage:End");
		return mapping.findForward(ColoadingConstants.SURFACE_RATE_SUCCESS);
	}


	private void loadCommonData(HttpServletRequest request,
			SurfaceRateEntryForm coloadingForm, SurfaceRateEntryTO coloadingTo)
			throws CGBusinessException, CGSystemException {
		Integer regionId = getRegionForLoggedinUser(request);
		if(regionId != null){
		List<ColoadingVendorTO> coloadingVendorTOs = coloadingService
				.getVendorsList(regionId,ColoadingConstants.ROAD);
		request.setAttribute(ColoadingConstants.VENDOR_LIST, coloadingVendorTOs);
		}
		request.setAttribute("to", coloadingTo);
		coloadingForm.setTo(coloadingTo);
		request.setAttribute("surfaceRateEntryForm", coloadingForm);
	}

	
	public ActionForward submitAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingSurfaceRateEntryAction:submitAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		SurfaceRateEntryForm coloadingForm = (SurfaceRateEntryForm) form;
		SurfaceRateEntryTO coloadingTo = (SurfaceRateEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO    =	coloadingService.saveSurfaceRateEntry(coloadingTo, coloadingService.getUserID(request));
			if(surfaceRateEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertSurfaceRateEntryToFromSurfaceRateEntryDo(coloadingTo, surfaceRateEntryDO);
				actionMessage = new ActionMessage(ColoadingConstants.CL0005);
			}
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::submitAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::submitAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::submitAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingSurfaceRateEntryAction:submitAction:End");
		return mapping.findForward(ColoadingConstants.SURFACE_RATE_SUCCESS);
	}
	
	public ColoadingService getColoadingService() {
		return coloadingService;
	}

	public void setColoadingService(ColoadingService coloadingService) {
		this.coloadingService = coloadingService;
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
	
	
	public ActionForward loadVendorSavedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingSurfaceRateEntryAction:loadVendorSavedData:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		SurfaceRateEntryForm coloadingForm = (SurfaceRateEntryForm) form;
		SurfaceRateEntryTO coloadingTo = (SurfaceRateEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			
			Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO    =	coloadingService.loadVendorSavedData(vendorId);
			if(surfaceRateEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertSurfaceRateEntryToFromSurfaceRateEntryDo(coloadingTo, surfaceRateEntryDO);
			}else{
				coloadingTo = new SurfaceRateEntryTO();
				coloadingTo.setVendorId(vendorId);
			}
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::loadVendorSavedData ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::loadVendorSavedData ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingSurfaceRateEntryAction::loadVendorSavedData ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingSurfaceRateEntryAction:loadVendorSavedData:End");
		return mapping.findForward(ColoadingConstants.SURFACE_RATE_SUCCESS);
	}
}
