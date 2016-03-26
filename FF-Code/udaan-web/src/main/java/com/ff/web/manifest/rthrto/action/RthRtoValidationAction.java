package com.ff.web.manifest.rthrto.action;

import java.io.PrintWriter;

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
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.manifest.rthrto.RthRtoValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.form.RthRtoValidationForm;
import com.ff.web.manifest.rthrto.service.RthRtoValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoValidationAction.
 * 
 * @author narmdr
 */
public class RthRtoValidationAction extends AbstractRthRtoManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoValidationAction.class);

	/** The rth rto validation service. */
	private transient RthRtoValidationService rthRtoValidationService;

	/**
	 * View rth validation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewRthValidation(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::viewRthValidation::START------------>:::::::");
		String url = RthRtoManifestConstatnts.URL_VIEW_RTH_VALIDATION;
		try {

/*			if (!isBranchOffice(request)) {
				url = UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(
						RthRtoManifestConstatnts.RTH_ONLY_ALLOWED_AT_BRANCH_OFFICE);
				prepareActionMessage(request, actionMessage);
				
			}else{*/
			if(isBranchOffice(request) || isHubOffice(request)){
				final RthRtoValidationTO rthRtoValidationTO = new RthRtoValidationTO();
				getDefaultUIValues4Rth(request, rthRtoValidationTO);
				((RthRtoValidationForm) form).setTo(rthRtoValidationTO);
			}

		} /*catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewRthValidation of RthRtoValidationAction..."
					, e);			
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewRthValidation of RthRtoValidationAction..."
					, e);
		} */catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewRthValidation of RthRtoValidationAction..."
					, e);
		}
		LOGGER.debug("RthRtoValidationAction::viewRthValidation::END------------>:::::::");
		
		return mapping.findForward(url);
	}
	
	/**
	 * View rto validation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewRtoValidation(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::viewRtoValidation::START------------>:::::::");
		
		String url = RthRtoManifestConstatnts.URL_VIEW_RTO_VALIDATION;
		try {
			if (!isHubOffice(request)) {
				url = UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(
						RthRtoManifestConstatnts.RTO_ONLY_ALLOWED_AT_HUB_OFFICE);
				prepareActionMessage(request, actionMessage);
				
			}else{

				final RthRtoValidationTO rthRtoValidationTO = new RthRtoValidationTO();
				getDefaultUIValues4Rto(request, rthRtoValidationTO);
				((RthRtoValidationForm) form).setTo(rthRtoValidationTO);
			}

		}/* catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewRtoValidation of RthRtoValidationAction..."
					, e);			
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewRtoValidation of RthRtoValidationAction..."
					, e);
		}*/ catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewRtoValidation of RthRtoValidationAction..."
					, e);
		}
		LOGGER.debug("RthRtoValidationAction::viewRtoValidation::END------------>:::::::");

		return mapping.findForward(url);
	}

	/**
	 * Gets the default ui values4 rto.
	 *
	 * @param request the request
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the default ui values4 rto
	 */
	private void getDefaultUIValues4Rto(HttpServletRequest request,
			RthRtoValidationTO rthRtoValidationTO) {
		rthRtoValidationTO.setReturnType(RthRtoManifestConstatnts.RETURN_TYPE_RTO);
		getCommonDefaultUIValues(request, rthRtoValidationTO);		
	}

	/**
	 * Gets the default ui values4 rth.
	 *
	 * @param request the request
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the default ui values4 rth
	 */
	private void getDefaultUIValues4Rth(HttpServletRequest request,
			RthRtoValidationTO rthRtoValidationTO) {
		rthRtoValidationTO.setReturnType(RthRtoManifestConstatnts.RETURN_TYPE_RTH);
		getCommonDefaultUIValues(request, rthRtoValidationTO);
	}

	/**
	 * Gets the common default ui values.
	 *
	 * @param request the request
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the common default ui values
	 */
	private void getCommonDefaultUIValues(HttpServletRequest request,
			RthRtoValidationTO rthRtoValidationTO) {
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
		final ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();

		final ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(CommonConstants.PROCESS_RTO_RTH);
		rthRtoValidationTO.setProcessTO(processTO);
		
		rthRtoValidationTO.setOfficeTO(loggedInofficeTO);
		rthRtoValidationTO.setConsignmentTypeTO(consignmentTypeTO);
		
		rthRtoValidationTO.setReasonTypeCode(UdaanCommonConstants.REASON_TYPE_FOR_RTO_RTH_VALIDATION);
		rthRtoValidationTO.setMaxReasonsForRth(Integer.valueOf(userInfoTO
				.getConfigurableParams().get(
						RthRtoManifestConstatnts.RTH_MAX_REASONS_ALLOWED)));
		rthRtoValidationTO.setMaxReasonsForRto(Integer.valueOf(userInfoTO
				.getConfigurableParams().get(
						RthRtoManifestConstatnts.RTO_MAX_REASONS_ALLOWED)));		
	}

	/**
	 * Gets the and set logged in office dtls.
	 *
	 * @param request the request
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the and set logged in office dtls
	 */
	private void getAndSetLoggedInOfficeDtls(final HttpServletRequest request,
			RthRtoValidationTO rthRtoValidationTO) {
		LOGGER.debug("RthRtoValidationAction::getAndSetLoggedInOfficeDtls::START------------>:::::::");
		//try {
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();

			rthRtoValidationTO.setLoggedInOfficeTO(loggedInOfficeTO);
		/*} catch (Exception e) {
			LOGGER.error("Exception happened in getAndSetLoggedInOfficeDtls of RthRtoValidationAction..."
					, e);
		}*/
		LOGGER.debug("RthRtoValidationAction::getAndSetLoggedInOfficeDtls::END------------>:::::::");
	}

	/**
	 * Save or update rth rto validation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveOrUpdateRthRtoValidation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::saveOrUpdateRthRtoValidation::START------------>:::::::");
		RthRtoValidationTO rthRtoValidationTO = null;
		//ActionMessage actionMessage = null;
		ActionForward actionForward = null;
		try {
			rthRtoValidationTO = (RthRtoValidationTO) ((RthRtoValidationForm) form)
					.getTo();
			getAndSetLoggedInOfficeDtls(request, rthRtoValidationTO);

			rthRtoValidationService = (RthRtoValidationService) getBean(SpringConstants.RTH_RTO_VALIDATION_SERVICE);
			rthRtoValidationService
					.saveOrUpdateRthRtoValidation(rthRtoValidationTO);
			
			//calling TwoWayWrite service to save same in central
			rthRtoValidationService.twoWayWrite(rthRtoValidationTO);
			
			/*actionMessage = new ActionMessage(
					RthRtoManifestConstatnts.RTH_RTO_VALIDATION_DETAILS_SAVED,
					rthRtoValidationTO.getConsignmentNumber());*/
			prepareActionMessage(request, RthRtoManifestConstatnts.RTH_RTO_VALIDATION_DETAILS_SAVED,
					new Object[]{rthRtoValidationTO.getConsignmentNumber()});
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in saveOrUpdateRthRtoValidation of RthRtoValidationAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			/*actionMessage = new ActionMessage(
					RthRtoManifestConstatnts.ERROR_IN_SAVING_RTH_RTO_VALIDATION_DETAILS);*/
			LOGGER.error("Exception happened in saveOrUpdateRthRtoValidation of RthRtoValidationAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in saveOrUpdateRthRtoValidation of RthRtoValidationAction..."
					, e);
		} finally {
			//prepareActionMessage(request, actionMessage);
			/*rthRtoValidationTO = new RthRtoValidationTO();
			getDefaultUIValues4Rth(request, rthRtoValidationTO);
			((RthRtoValidationForm) form).setTo(rthRtoValidationTO);*/
			if(RthRtoManifestConstatnts.RETURN_TYPE_RTH.equals(rthRtoValidationTO.getReturnType())){
				actionForward = viewRthValidation(mapping, form, request, response);
			}else{
				actionForward = viewRtoValidation(mapping, form, request, response);
			}
		}
		LOGGER.debug("RthRtoValidationAction::saveOrUpdateRthRtoValidation::END------------>:::::::");
		//return mapping.findForward(RthRtoManifestConstatnts.URL_VIEW_RTH_VALIDATION);
		return actionForward;
	}

	/**
	 * Find consignment number4 rth.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void findConsignmentNumber4Rth(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::findConsignmentNumber4Rth::START------------>:::::::");		
		RthRtoValidationTO rthRtoValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			rthRtoValidationTO = (RthRtoValidationTO) ((RthRtoValidationForm) form)
					.getTo();
			rthRtoValidationService = (RthRtoValidationService) getBean(SpringConstants.RTH_RTO_VALIDATION_SERVICE);
			rthRtoValidationTO = rthRtoValidationService.findConsignmentNumber4Rth(rthRtoValidationTO);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in findConsignmentNumber4Rth of RthRtoValidationAction..."
					, e);			
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			/*errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(),
					new String[] { rthRtoValidationTO.getConsignmentNumber() });*/
			LOGGER.error("Exception happened in findConsignmentNumber4Rth of RthRtoValidationAction..."
					, e);
			
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in findConsignmentNumber4Rth of RthRtoValidationAction..."
					, e);			
		} finally {
			if(rthRtoValidationTO==null){
				rthRtoValidationTO = new RthRtoValidationTO();
			}
			rthRtoValidationTO.setErrorMsg(errorMsg);
			String rthRtoValidationTOJSON = serializer.toJSON(rthRtoValidationTO).toString();
			out.print(rthRtoValidationTOJSON);
		}
		LOGGER.debug("RthRtoValidationAction::findConsignmentNumber4Rth::END------------>:::::::");
	}

	/**
	 * Find consignment number4 rto.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void findConsignmentNumber4Rto(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::findConsignmentNumber4Rto::START------------>:::::::");		
		RthRtoValidationTO rthRtoValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			rthRtoValidationTO = (RthRtoValidationTO) ((RthRtoValidationForm) form)
					.getTo();
			rthRtoValidationService = (RthRtoValidationService) getBean(SpringConstants.RTH_RTO_VALIDATION_SERVICE);
			rthRtoValidationTO = rthRtoValidationService.findConsignmentNumber4Rto(rthRtoValidationTO);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in findConsignmentNumber4Rto of RthRtoValidationAction..."
					, e);			
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			/*errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(),
					new String[] { rthRtoValidationTO.getConsignmentNumber() });*/
			LOGGER.error("Exception happened in findConsignmentNumber4Rto of RthRtoValidationAction..."
					, e);
			
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in findConsignmentNumber4Rto of RthRtoValidationAction..."
					, e);			
		} finally {
			if(rthRtoValidationTO==null){
				rthRtoValidationTO = new RthRtoValidationTO();
			}
			rthRtoValidationTO.setErrorMsg(errorMsg);
			String rthRtoValidationTOJSON = serializer.toJSON(rthRtoValidationTO).toString();
			out.print(rthRtoValidationTOJSON);
		}
		LOGGER.debug("RthRtoValidationAction::findConsignmentNumber4Rto::END------------>:::::::");
	}
	
	/**
	 * Gets the current date time.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the current date time
	 */
	public void getCurrentDateTime(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("RthRtoValidationAction::getCurrentDateTime::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = "";
		try {
			out = response.getWriter();
			//response.getWriter().print(DateUtil.getDateInDDMMYYYYHHMMSSSlashFormat());
			jsonResult = DateUtil.getDateInDDMMYYYYHHMMSSSlashFormat();
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getCurrentDateTime of RthRtoValidationAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RthRtoValidationAction::getCurrentDateTime::END------------>:::::::");
	}
}
