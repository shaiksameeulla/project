package com.ff.web.loadmanagement.action;

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
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveOutstationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.web.common.SpringConstants;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.form.LoadReceiveOutstationForm;
import com.ff.web.loadmanagement.service.LoadReceiveOutstationService;
import com.ff.web.util.UdaanCommonConstants;

/**
 * The Class LoadReceiveOutstationAction.
 * 
 * @author narmdr
 */
public class LoadReceiveOutstationAction extends LoadManagementAction{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadReceiveOutstationAction.class);
	
	/** The load receive outstation service. */
	private transient LoadReceiveOutstationService loadReceiveOutstationService;
	
	/**
	 * View load receive outstation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws Exception the exception
	 */
	public ActionForward viewLoadReceiveOutstation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveOutstationAction::viewLoadReceiveOutstation::START------------>:::::::");
		LoadReceiveOutstationTO loadReceiveOutstationTO = new LoadReceiveOutstationTO();
		try {
			getDefaultUIValues(request, loadReceiveOutstationTO);	
			((LoadReceiveOutstationForm) form).setTo(loadReceiveOutstationTO);
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			//prepareActionMessage(request, LoadManagementConstants.ERROR_IN_LOADING_PAGE);
			LOGGER.error("Exception happened in viewLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		}
		LOGGER.debug("LoadReceiveOutstationAction::viewLoadReceiveOutstation::END------------>:::::::");	
		
		return mapping.findForward(LoadManagementConstants.URL_VIEW_LOAD_RECEIVE_OUTSTATION);
	}
	
	/**
	 * Gets the default ui values.
	 *
	 * @param request the request
	 * @param loadReceiveOutstationTO the load receive local to
	 * @return the default ui values
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void getDefaultUIValues(HttpServletRequest request, LoadReceiveOutstationTO loadReceiveOutstationTO) 
			throws CGBusinessException, CGSystemException {
		loadReceiveOutstationService = (LoadReceiveOutstationService) getBean(SpringConstants.LOAD_RECEIVE_OUTSTATION_SERVICE);
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UdaanCommonConstants.USER);
		OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();	

		loadReceiveOutstationTO.setReceiveDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());	
		//loggedInoffice treated as destination Office
		loadReceiveOutstationTO.setLoggedInOfficeId(loggedInofficeTO.getOfficeId());
		//officeId~cityId~ReportingRHOId
		/*loadReceiveOutstationTO.setLoggedInOffice(loggedInofficeTO.getOfficeId() + 
				CommonConstants.TILD + loggedInofficeTO.getCityId() +
				CommonConstants.TILD + loggedInofficeTO.getReportingRHO());*/

		loadReceiveOutstationTO.setDestOffice(loggedInofficeTO.getOfficeCode() 
				+ CommonConstants.HYPHEN + loggedInofficeTO.getOfficeName());
		loadReceiveOutstationTO.setDestOfficeId(loggedInofficeTO.getOfficeId());
		if(loggedInofficeTO.getOfficeTypeTO()!=null){
			loadReceiveOutstationTO.setDestOfficeType(loggedInofficeTO.getOfficeTypeTO().getOffcTypeDesc());
		}		
		loadReceiveOutstationTO.setRegionalOfficeId(loggedInofficeTO.getReportingRHO());
		loadReceiveOutstationTO.setLoggedInOfficeCode(loggedInofficeTO.getOfficeCode());//OfficeCode
		loadReceiveOutstationTO.setProcessCode(CommonConstants.PROCESS_RECEIVE);
	}
	
	/**
	 * Checks if is receive number exist.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @throws Exception the exception
	 */
	@SuppressWarnings("static-access")
	public void isReceiveNumberExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("LoadReceiveOutstationAction::isReceiveNumberExist::START------------>:::::::");
		LoadReceiveValidationTO loadReceiveValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String receiveNumber = request.getParameter(LoadManagementConstants.RECEIVE_NUMBER);

			loadReceiveValidationTO = new LoadReceiveValidationTO();
			loadReceiveValidationTO.setReceiveNumber(receiveNumber);
			
			loadReceiveOutstationService.isReceiveNumberExist(loadReceiveValidationTO);
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in isReceiveNumberExist of LoadReceiveOutstationAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in isReceiveNumberExist of LoadReceiveOutstationAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in isReceiveNumberExist of LoadReceiveOutstationAction..."
					, e);
		}finally {
			if(loadReceiveValidationTO==null){
				loadReceiveValidationTO = new LoadReceiveValidationTO();
			}
			loadReceiveValidationTO.setErrorMsg(errorMsg);
			String loadReceiveValidationTOJSON = serializer.toJSON(loadReceiveValidationTO).toString();		
			out.print(loadReceiveValidationTOJSON);
		}
		LOGGER.debug("LoadReceiveOutstationAction::isReceiveNumberExist::END------------>:::::::");	
	}	

	/**
	 * Gets the transport mode list.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the transport mode list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("static-access")
	public void getTransportModeList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveOutstationAction::getAllTransportModeList::START------------>:::::::");
		List<LabelValueBean> transportModeList = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			transportModeList = getAllTransportModeList();
		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getAllTransportModeList of LoadReceiveOutstationAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getAllTransportModeList of LoadReceiveOutstationAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getAllTransportModeList of LoadReceiveOutstationAction..."
					, e);
		} finally {
			String transportModeListJSON = serializer.toJSON(transportModeList).toString();		
			out.print(transportModeListJSON);
		}
		LOGGER.debug("LoadReceiveOutstationAction::getAllTransportModeList::END------------>:::::::");	
	}	
	
	/**
	 * Save or update load receive outstation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws Exception the exception
	 */
	public ActionForward saveOrUpdateLoadReceiveOutstation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("LoadReceiveOutstationAction::saveOrUpdateLoadReceiveOutstation::START------------>:::::::");
		LoadReceiveOutstationTO loadReceiveOutstationTO = null;
		ActionMessage actionMessage = null;
		try {
			LoadReceiveOutstationForm loadReceiveOutstationForm = (LoadReceiveOutstationForm) form;
			loadReceiveOutstationTO = (LoadReceiveOutstationTO) loadReceiveOutstationForm.getTo();
			getAndSetLoggedInOfficeDtls(request, loadReceiveOutstationTO);

			loadReceiveOutstationService.saveOrUpdateLoadReceiveOutstation(loadReceiveOutstationTO);
			
			//calling TwoWayWrite service to save same in central
			twoWayWrite(loadReceiveOutstationTO);
			actionMessage = new ActionMessage(LoadManagementConstants.RECEIVE_OUTSTATION_DETAILS_SAVED, loadReceiveOutstationTO.getReceiveNumber());		
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		}/* catch (Exception e) {
			actionMessage = new ActionMessage(
					LoadManagementConstants.ERROR_IN_SAVING_LOAD_RECEIVE_OUTSTATION_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
					, e);
		}*/ finally {
			//resetToken(request);
			prepareActionMessage(request, actionMessage);
			loadReceiveOutstationTO = new LoadReceiveOutstationTO();
			try {
				getDefaultUIValues(request, loadReceiveOutstationTO);
			} catch (CGBusinessException e) {
				LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
						, e);
			} catch (CGSystemException e) {
				LOGGER.error("Exception happened in saveOrUpdateLoadReceiveOutstation of LoadReceiveOutstationAction..."
						, e);
			}
			((LoadReceiveOutstationForm) form).setTo(loadReceiveOutstationTO);
		}
		LOGGER.debug("LoadReceiveOutstationAction::saveOrUpdateLoadReceiveOutstation::END------------>:::::::");	
		return mapping.findForward("viewLoadReceiveOutstation");
	}

	/**
	 * Validate manifest number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @throws Exception the exception
	 */
	public void validateManifestNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveOutstationAction::validateManifestNumber::START------------>:::::::");
		LoadReceiveManifestValidationTO loadReceiveManifestValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String manifestNumber = request.getParameter(LoadManagementConstants.MANIFEST_NUMBER);
			Integer destOfficeId = Integer.valueOf(request.getParameter(LoadManagementConstants.DEST_OFFICE_ID));
						
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO1 = 
					new LoadReceiveManifestValidationTO();
			loadReceiveManifestValidationTO1.setManifestNumber(manifestNumber);
			loadReceiveManifestValidationTO1.setDestOfficeId(destOfficeId);			
			loadReceiveManifestValidationTO  = loadReceiveOutstationService.
					validateManifestNumber4ReceiveOutstation(loadReceiveManifestValidationTO1);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveOutstationAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveOutstationAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadReceiveOutstationAction..."
					, e);
		} finally {
			if(loadReceiveManifestValidationTO==null){
				loadReceiveManifestValidationTO = new LoadReceiveManifestValidationTO();
			}
			loadReceiveManifestValidationTO.setErrorMsg(errorMsg);
			String loadReceiveManifestValidationTOJSON = 
					JSONSerializer.toJSON(loadReceiveManifestValidationTO).toString();
			out.print(loadReceiveManifestValidationTOJSON);
		}		
		LOGGER.debug("LoadReceiveOutstationAction::validateManifestNumber::END------------>:::::::");	
	}
}
