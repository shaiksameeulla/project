package com.ff.web.manifest.inmanifest.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InBagManifestDoxTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.manifest.service.InManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.form.InBagManifestForm;
import com.ff.web.manifest.inmanifest.service.InBagManifestService;
import com.ff.web.util.UdaanCommonConstants;

/**
 * The Class InBagManifestAction.
 * 
 * @author narmdr
 */
public class InBagManifestAction extends InManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InBagManifestAction.class);
	
	/** The in bag manifest service. */
	private transient InBagManifestService inBagManifestService;
	
	/** The in manifest universal service. */
	private transient InManifestUniversalService inManifestUniversalService;

	/**
	 * View in bag manifest dox.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewInBagManifestDox(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestAction::viewInBagManifestDox::START------------>:::::::");
		try {
			final InBagManifestDoxTO inBagManifestDoxTO = new InBagManifestDoxTO();
			getDefaultUIValues(request, inBagManifestDoxTO);
			((InBagManifestForm) form).setTo(inBagManifestDoxTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewInBagManifestDox of InBagManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewInBagManifestDox of InBagManifestAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewInBagManifestDox of InBagManifestAction..."
					, e);
		}
		LOGGER.debug("InBagManifestAction::viewInBagManifestDox::END------------>:::::::");

		return mapping.findForward("viewInBagManifestDox");
	}

	/**
	 * Gets the default ui values.
	 *
	 * @param request the request
	 * @param inBagManifestDoxTO the in bag manifest dox to
	 * @return the default ui values
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private void getDefaultUIValues(final HttpServletRequest request,
			final InBagManifestDoxTO inBagManifestDoxTO)
			throws CGSystemException, CGBusinessException {
		inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UdaanCommonConstants.USER);
		final OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
		inBagManifestDoxTO.setManifestDateTime(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat());
		inBagManifestDoxTO.setLoggedInOfficeId(loggedInofficeTO
				.getOfficeId());
		inBagManifestDoxTO.setDestCityId(loggedInofficeTO
				.getCityId());
		/*inBagManifestDoxTO.setDestinationOfficeId(loggedInofficeTO
				.getOfficeId());*/
		inBagManifestDoxTO.setDestinationOffice(loggedInofficeTO
				.getOfficeCode()
				+ CommonConstants.HYPHEN
				+ loggedInofficeTO.getOfficeName());

		if (loggedInofficeTO.getRegionTO() != null) {
			inBagManifestDoxTO.setDestinationOffice(loggedInofficeTO
					.getRegionTO().getRegionDisplayName()
					+ CommonConstants.HYPHEN
					+ loggedInofficeTO.getOfficeName());
			
			inBagManifestDoxTO.setDestinationRegion(loggedInofficeTO
					.getRegionTO().getRegionDisplayName());
		}
		List<RegionTO> originRegionTOs = inManifestUniversalService
				.getAllRegions();
		if (originRegionTOs == null) {
			originRegionTOs = new ArrayList<RegionTO>();
		}
		final List<LabelValueBean> originOfficeTypeList = inManifestUniversalService
				.getOfficeTypeList();
		inBagManifestDoxTO.setOriginOfficeTypeList(originOfficeTypeList);
		inBagManifestDoxTO.setOriginRegionTOs(originRegionTOs);
		inBagManifestDoxTO
				.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);
		inBagManifestDoxTO
				.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);
		inBagManifestDoxTO
				.setGridProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		inBagManifestDoxTO
				.setGridOgmProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);

		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		// consignmentTypeTO.setConsignmentName(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		consignmentTypeTO
				.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		consignmentTypeTO = getConsgType(consignmentTypeTO);
		if (consignmentTypeTO != null) {
			inBagManifestDoxTO.setConsignmentTypeId(consignmentTypeTO
					.getConsignmentId());
		}
		// Set Remarks Option details
		setRemarksDetails(request);
	}

	/**
	 * Gets the consg type.
	 *
	 * @param consignmentTypeTO the consignment type to
	 * @return the consg type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private ConsignmentTypeTO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGBusinessException, CGSystemException {
		return inManifestUniversalService.getConsgType(consignmentTypeTO);
	}

	/**
	 * Save or update in bag manifet dox.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void saveOrUpdateInBagManifetDox(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestAction::saveOrUpdateInBagManifetDox::START------------>:::::::");
		// ActionMessage actionMessage = null;
		String message = null;
		InBagManifestDoxTO inBagManifestDoxTO = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			inBagManifestDoxTO = (InBagManifestDoxTO) ((InBagManifestForm) form)
					.getTo();
			getAndSetLoggedInOfficeDtls(request, inBagManifestDoxTO);			
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inBagManifestDoxTO = inBagManifestService
					.saveOrUpdateInBagManifestDox(inBagManifestDoxTO);

			//calling TwoWayWrite service to save same in central
			twoWayWrite(inBagManifestDoxTO);
			
			message = prepareLessExcessPacketMsg(inBagManifestDoxTO, request);
			// actionMessage = new ActionMessage("M015",
			// inBagManifestDoxTO.getManifestNumber(),
			// inBagManifestDoxTO.getLessManifest(),
			// inBagManifestDoxTO.getExcessManifest());
			/*message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_DOX_DETAILS_SAVED, new String[] {
							inBagManifestDoxTO.getManifestNumber(),
							inBagManifestDoxTO.getLessManifest(),
							inBagManifestDoxTO.getExcessManifest() });*/
			
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			//message = getSystemExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_DOX_DETAILS, null);
			//message = prepareErrorMessageSystemException(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_DOX_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifetDox of InBagManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifetDox of InBagManifestAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			//message = getGenericExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_DOX_DETAILS, null);
			//message = prepareErrorMessageSystemException(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_DOX_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifetDox of InBagManifestAction..."
					, e);
		} finally {
			/*
			 * prepareActionMessage(request, actionMessage);
			 * viewInBagManifestDox(mapping, form, request, response);
			 */
			/*String inBagManifestDoxTOJSON = serializer.toJSON(
					inBagManifestDoxTO).toString();
			response.getWriter().print(inBagManifestDoxTOJSON);*/
			out.print(message);
		}
		LOGGER.debug("InBagManifestAction::saveOrUpdateInBagManifetDox::END------------>:::::::");
		// return mapping.findForward("viewInBagManifestDox");
	}

	/**
	 * Find bpl number dox.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void findBplNumberDox(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestAction::findBplNumberDox::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			final InBagManifestDoxTO inBagManifestDoxTO = new InBagManifestDoxTO();
			inBagManifestDoxTO.setManifestNumber(request
					.getParameter("manifestNumber"));
			inBagManifestDoxTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inManifestValidationTO = inBagManifestService
					.findBplNumberDox(inBagManifestDoxTO);
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in findBplNumberDox of InBagManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in findBplNumberDox of InBagManifestAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in findBplNumberDox of InBagManifestAction..."
					, e);
		} finally {
			if(inManifestValidationTO==null){
				inManifestValidationTO = new InManifestValidationTO();
			}
			inManifestValidationTO.setErrorMsg(errorMsg);
			String inManifestValidationTOJSON = serializer.toJSON(
					inManifestValidationTO).toString();
			out.print(inManifestValidationTOJSON);
		}
		LOGGER.debug("InBagManifestAction::findBplNumberDox::END------------>:::::::");
	}

	/**
	 * Prepare less excess packet msg.
	 *
	 * @param inBagManifestDoxTO the in bag manifest dox to
	 * @param request the request
	 * @return the string
	 */
	private String prepareLessExcessPacketMsg(
			InBagManifestDoxTO inBagManifestDoxTO,
			HttpServletRequest request) {
		LOGGER.trace("InBagManifestAction::prepareLessExcessPacketMsg::START------------>:::::::");
		String message = null;
		if (StringUtils.isNotBlank(inBagManifestDoxTO.getLessManifest())
				&& StringUtils.isNotBlank(inBagManifestDoxTO
						.getExcessManifest())) {
			message = getMessageFromErrorBundle(
					request,
					InManifestConstants.IN_BPL_DOX_DETAILS_SAVED_LESS_EXCESS,
					new String[] { inBagManifestDoxTO.getManifestNumber(),
							inBagManifestDoxTO.getLessManifest(),
							inBagManifestDoxTO.getExcessManifest() });
		}
		if (StringUtils.isNotBlank(inBagManifestDoxTO.getLessManifest())
				&& StringUtils.isBlank(inBagManifestDoxTO.getExcessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_DOX_DETAILS_SAVED_LESS,
					new String[] { inBagManifestDoxTO.getManifestNumber(),
							inBagManifestDoxTO.getLessManifest() });
		}
		if (StringUtils.isNotBlank(inBagManifestDoxTO.getExcessManifest())
				&& StringUtils.isBlank(inBagManifestDoxTO.getLessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_DOX_DETAILS_SAVED_EXCESS,
					new String[] { inBagManifestDoxTO.getManifestNumber(),
							inBagManifestDoxTO.getExcessManifest() });
		}
		if (StringUtils.isBlank(inBagManifestDoxTO.getExcessManifest())
				&& StringUtils.isBlank(inBagManifestDoxTO.getLessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_DOX_DETAILS_SAVED,
					new String[] { inBagManifestDoxTO.getManifestNumber() });
		}
		LOGGER.trace("InBagManifestAction::prepareLessExcessPacketMsg::END------------>:::::::");
		return message;
	}

	/*
	 * public void deleteInBagManifetDox(final ActionMapping mapping, final
	 * ActionForm form, final HttpServletRequest request, final
	 * HttpServletResponse response) throws CGBusinessException { LOGGER.debug(
	 * "InBagManifestAction::deleteInBagManifetDox::START------------>:::::::");
	 * try { final InBagManifestDoxTO inBagManifestDoxTO = new
	 * InBagManifestDoxTO(); String deletedIds =
	 * request.getParameter("deletedIds"); inBagManifestService =
	 * (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
	 * final InManifestValidationTO inManifestValidationTO =
	 * inBagManifestService.findBplNumberDox(inBagManifestDoxTO); } catch
	 * (Exception e) { LOGGER.error(
	 * "Exception happened in deleteInBagManifetDox of InBagManifestAction..."
	 * +e); }finally { String loadReceiveManifestValidationTOJSON =
	 * JSONSerializer.toJSON(loadReceiveManifestValidationTO).toString();
	 * response.getWriter().print(loadReceiveManifestValidationTOJSON); }
	 * LOGGER.
	 * debug("InBagManifestAction::deleteInBagManifetDox::END------------>:::::::"
	 * ); }
	 */
	public ActionForward printBplNumberDox(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestAction::printBplNumberDox::START------------>:::::::");
		try {
			InBagManifestDoxTO inBagManifestDoxTO = new InBagManifestDoxTO();
			inBagManifestDoxTO.setManifestNumber(request
					.getParameter("manifestNumber"));
			inBagManifestDoxTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inBagManifestDoxTO = inBagManifestService
					.findBplNumberDox4Print(inBagManifestDoxTO);
			
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(InManifestConstants.USER_INFO);

			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if (!StringUtil.isNull(officeTO)) {
				inBagManifestDoxTO.setLoggedInOfficeName(officeTO.getOfficeName());
				inBagManifestDoxTO.setLoggedInOfficeCity(officeTO.getAddress3());
			}
				
			if(inBagManifestDoxTO!=null){
				inBagManifestDoxTO.setManifestNumber(request.getParameter("manifestNumber"));
			}
			request.setAttribute("inBagManifestDoxTO", inBagManifestDoxTO);
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in printBplNumberDox of InBagManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in printBplNumberDox of InBagManifestAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in printBplNumberDox of InBagManifestAction..."
					, e);
		}		
		return mapping.findForward("printInBagManifestDox");
	}
}
