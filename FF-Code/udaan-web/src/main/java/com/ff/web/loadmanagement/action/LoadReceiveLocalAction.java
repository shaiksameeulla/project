package com.ff.web.loadmanagement.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadReceiveLocalTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.transport.TransportModeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.form.LoadReceiveLocalForm;
import com.ff.web.loadmanagement.service.LoadReceiveLocalService;
import com.ff.web.util.UdaanCommonConstants;

/**
 * The Class LoadReceiveLocalAction.
 *
 * @author narmdr
 */
public class LoadReceiveLocalAction extends LoadManagementAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadReceiveLocalAction.class);
	
	/** The load receive local service. */
	private transient LoadReceiveLocalService loadReceiveLocalService;
		
	/**
	 * View load receive local.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward and Forwarding to XXX page
	 */
	public ActionForward viewLoadReceiveLocal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::viewLoadReceiveLocal::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = new LoadReceiveLocalTO();
		
		try {
			getDefaultUIValues(request, loadReceiveLocalTO);	
			((LoadReceiveLocalForm) form).setTo(loadReceiveLocalTO);
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			//prepareActionMessage(request, LoadManagementConstants.ERROR_IN_LOADING_PAGE);
			LOGGER.error("Exception happened in viewLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		}
		LOGGER.debug("LoadReceiveLocalAction::viewLoadReceiveLocal::END------------>:::::::");	
	
		return mapping.findForward(LoadManagementConstants.URL_VIEW_LOAD_RECEIVE_LOCAL);
	}
	
	/**
	 * Gets the default ui values.
	 *
	 * @param request the request
	 * @param loadReceiveLocalTO the load receive local to
	 * @return the default ui values
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void getDefaultUIValues(HttpServletRequest request, LoadReceiveLocalTO loadReceiveLocalTO) 
			throws CGBusinessException, CGSystemException {
		loadReceiveLocalService = (LoadReceiveLocalService) getBean(SpringConstants.LOAD_RECEIVE_LOCAL_SERVICE);
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UdaanCommonConstants.USER);
		OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();	
		//Set Office Type
		if (loggedInofficeTO.getOfficeTypeTO() != null) {
			loadReceiveLocalTO.setOriginOfficeType(loggedInofficeTO
					.getOfficeTypeTO().getOffcTypeCode());
		}

		OfficeTO regionalOfficeTO = getOfficeByOfficeId(loggedInofficeTO.getReportingRHO());
		List<LabelValueBean> vehicleNoList = getVehicleNoListByOfficeId(regionalOfficeTO.getOfficeId());

		loadReceiveLocalTO.setReceiveDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		
		TransportModeTO transportModeTO = new TransportModeTO();
		transportModeTO.setTransportModeCode(LoadManagementConstants.ROAD_CODE);
		transportModeTO = loadReceiveLocalService.getTransportMode(transportModeTO);
		if(transportModeTO!=null){
			loadReceiveLocalTO.setTransportMode(transportModeTO.getTransportModeDesc());
			loadReceiveLocalTO.setTransportModeDetails(transportModeTO.getTransportModeId() +
					CommonConstants.TILD + transportModeTO.getTransportModeCode() + 
					CommonConstants.TILD + transportModeTO.getTransportModeDesc());
		}

		//loggedInoffice treated as destination Office
		loadReceiveLocalTO.setLoggedInOfficeId(loggedInofficeTO.getOfficeId());
		//officeId~cityId~ReportingRHOId
		loadReceiveLocalTO.setLoggedInOffice(loggedInofficeTO.getOfficeId() + 
				CommonConstants.TILD + loggedInofficeTO.getCityId() +
				CommonConstants.TILD + loggedInofficeTO.getReportingRHO());

		loadReceiveLocalTO.setDestOfficeId(loggedInofficeTO.getOfficeId());
		loadReceiveLocalTO.setDestOffice(loggedInofficeTO.getOfficeCode() 
				+ CommonConstants.HYPHEN + loggedInofficeTO.getOfficeName());
		if(loggedInofficeTO.getOfficeTypeTO()!=null){
			loadReceiveLocalTO.setDestOfficeType(loggedInofficeTO.getOfficeTypeTO().getOffcTypeDesc());
		}
		
		//origin region
		loadReceiveLocalTO.setRegionalOfficeId(regionalOfficeTO.getOfficeId());
		loadReceiveLocalTO.setRegionalOffice(regionalOfficeTO.getOfficeCode()
				+ CommonConstants.HYPHEN + regionalOfficeTO.getOfficeName());
		
		loadReceiveLocalTO.setVehicleNoList(vehicleNoList);
		loadReceiveLocalTO.setProcessCode(CommonConstants.PROCESS_RECEIVE);
	}
	
	/**
	 * Gets the load receive local to.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the load receive local to
	 */
	@SuppressWarnings("static-access")
	public void getLoadReceiveLocalTO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::getLoadReceiveLocalTO::START------------>:::::::");

		LoadReceiveLocalTO loadReceiveLocalTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String gatePassNumber = request.getParameter(LoadManagementConstants.GATE_PASS_NUMBER);
			Integer regionalOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.REGIONAL_OFFICE_ID));
			Integer loggedInOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.LOGGED_IN_OFFICE_ID));
			
			LoadManagementValidationTO loadManagementValidationTO = new LoadManagementValidationTO();
			loadManagementValidationTO.setGatePassNumber(gatePassNumber);
			loadManagementValidationTO.setRegionalOfficeId(regionalOfficeId);
			loadManagementValidationTO.setLoggedInOfficeId(loggedInOfficeId);
			
			loadReceiveLocalTO = loadReceiveLocalService.getLoadReceiveLocalTO(loadManagementValidationTO);
		
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getLoadReceiveLocalTO of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getLoadReceiveLocalTO of LoadReceiveLocalAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getLoadReceiveLocalTO of LoadReceiveLocalAction..."
					, e);
		} finally {
			if(loadReceiveLocalTO==null){
				loadReceiveLocalTO = new LoadReceiveLocalTO();
			}
			loadReceiveLocalTO.setErrorMsg(errorMsg);
			String loadReceiveLocalTOJSON = serializer.toJSON(loadReceiveLocalTO).toString();		
			out.print(loadReceiveLocalTOJSON);
		}
		LOGGER.debug("LoadReceiveLocalAction::getLoadReceiveLocalTO::END------------>:::::::");	
	}	
	
	/**
	 * Save or update load receive local.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateLoadReceiveLocal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::saveOrUpdateLoadReceiveLocal::START------------>:::::::");

		LoadReceiveLocalTO loadReceiveLocalTO = null;
		String errorMessage = null;
		String successMessage = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			LoadReceiveLocalForm loadReceiveLocalForm = (LoadReceiveLocalForm) form;
			LoadReceiveLocalTO loadReceiveLocalTO1 = (LoadReceiveLocalTO) loadReceiveLocalForm.getTo();			

			getAndSetLoggedInOfficeDtls(request, loadReceiveLocalTO1);
			loadReceiveLocalTO = loadReceiveLocalService.
					saveOrUpdateLoadReceiveLocal(loadReceiveLocalTO1);

			//calling TwoWayWrite service to save same in central
			twoWayWrite(loadReceiveLocalTO);
			
			successMessage = getMessageFromErrorBundle(request,
					LoadManagementConstants.GATEPASS_NUMBER_DETAILS_SAVED,
					new Object[] { loadReceiveLocalTO1.getGatePassNumber() });
		
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} finally {
			if (loadReceiveLocalTO == null) {
				loadReceiveLocalTO = new LoadReceiveLocalTO();
			}
			loadReceiveLocalTO.setErrorMessage(errorMessage);
			loadReceiveLocalTO.setSuccessMessage(successMessage);
			String loadReceiveLocalTOSON = serializer.toJSON(loadReceiveLocalTO).toString();		
			out.print(loadReceiveLocalTOSON);
		}
		LOGGER.debug("LoadReceiveLocalAction::saveOrUpdateLoadReceiveLocal::END------------>:::::::");	
	}
	
	/**
	 * get All the origin Offices By regionalOfficeId, exclude loggedInOffice.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the origin offices
	 */
	@SuppressWarnings("static-access")
	public void getOriginOffices(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::getOriginOffices::START------------>:::::::");
		
		try {
			Integer loggedInOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.LOGGED_IN_OFFICE_ID));
			Integer regionalOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.REGIONAL_OFFICE_ID));
			
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(loggedInOfficeId);
			officeTO.setReportingRHO(regionalOfficeId);
			
			List<OfficeTO> officeTOList  = loadReceiveLocalService.getOriginOffices(officeTO);
			String officeListJSON = serializer.toJSON(officeTOList).toString();
			response.getWriter().print(officeListJSON);
			
		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getOriginOffices of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getOriginOffices of LoadReceiveLocalAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getOriginOffices of LoadReceiveLocalAction..."
					, e);
		}	
		LOGGER.debug("LoadReceiveLocalAction::getOriginOffices::END------------>:::::::");	
	}
	
	
	/**
	 * Validate manifest number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void validateManifestNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::validateManifestNumber::START------------>:::::::");
		LoadReceiveManifestValidationTO loadReceiveManifestValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String manifestNumber = request.getParameter(LoadManagementConstants.MANIFEST_NUMBER);
			String loadMovementIdStr = request.getParameter(LoadManagementConstants.LOAD_MOVEMENT_ID);
			String receivedAgainstIdStr = request.getParameter(LoadManagementConstants.RECEIVED_AGAINST_ID);
			Integer destOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.DEST_OFFICE_ID));
			
			Integer loadMovementId = null;
			Integer receivedAgainstId = null;
			
			if(StringUtils.isNotBlank(loadMovementIdStr)){
				loadMovementId = Integer.valueOf(loadMovementIdStr);
			}
			if(StringUtils.isNotBlank(receivedAgainstIdStr)){
				receivedAgainstId = Integer.valueOf(receivedAgainstIdStr);
			}
			
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO1 = 
					new LoadReceiveManifestValidationTO();

			loadReceiveManifestValidationTO1.setManifestNumber(manifestNumber);		
			loadReceiveManifestValidationTO1.setLoadMovementId(loadMovementId);		
			loadReceiveManifestValidationTO1.setReceivedAgainstId(receivedAgainstId);
			loadReceiveManifestValidationTO1.setDestOfficeId(destOfficeId);
			
			loadReceiveManifestValidationTO  = loadReceiveLocalService.
					validateManifestNumber4ReceiveLocal(loadReceiveManifestValidationTO1);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveLocalAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveLocalAction..."
					, e);
		} finally {
			if (loadReceiveManifestValidationTO == null) {
				loadReceiveManifestValidationTO = new LoadReceiveManifestValidationTO();
			}
			loadReceiveManifestValidationTO.setErrorMsg(errorMsg);
			String loadReceiveManifestValidationTOJSON = 
					JSONSerializer.toJSON(loadReceiveManifestValidationTO).toString();
			out.print(loadReceiveManifestValidationTOJSON);
		}
		LOGGER.debug("LoadReceiveLocalAction::validateManifestNumber::END------------>:::::::");	
	}

	/**
	 * Prints the load receive local.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward printLoadReceiveLocal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveLocalAction::printLoadReceiveLocal::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = null;
		String gatePassNumber = null;
		String url = null;
		try {
			gatePassNumber = request
					.getParameter(LoadManagementConstants.GATE_PASS_NUMBER);
			Integer regionalOfficeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.REGIONAL_OFFICE_ID));
			Integer loggedInOfficeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.LOGGED_IN_OFFICE_ID));

			LoadManagementValidationTO loadManagementValidationTO = new LoadManagementValidationTO();
			loadManagementValidationTO.setGatePassNumber(gatePassNumber);
			loadManagementValidationTO.setRegionalOfficeId(regionalOfficeId);
			loadManagementValidationTO.setLoggedInOfficeId(loggedInOfficeId);
			loadReceiveLocalTO = loadReceiveLocalService
					.printLoadReceiveLocalTO(loadManagementValidationTO);
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
			loadReceiveLocalTO.setLoggedInOfficeTO(loggedInofficeTO);
			request.setAttribute("loadReceiveLocalTO", loadReceiveLocalTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in printLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in printLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in printLoadReceiveLocal of LoadReceiveLocalAction..."
					, e);
		} finally {
			if (loadReceiveLocalTO == null) {
				url = LoadManagementConstants.URL_VIEW_LOAD_RECEIVE_LOCAL;
			} else {
				url = LoadManagementConstants.URL_PRINT_LOAD_RECEIVE_LOCAL;
			}
		}

		LOGGER.debug("LoadReceiveLocalAction::printLoadReceiveLocal::END------------>:::::::");
		return mapping.findForward(url);

	}

}
