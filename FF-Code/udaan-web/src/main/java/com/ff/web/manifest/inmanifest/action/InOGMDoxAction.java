/**
 * 
 */
package com.ff.web.manifest.inmanifest.action;

import java.io.IOException;
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
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestOGMDetailTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.manifest.service.InManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InOGMDoxConverter;
import com.ff.web.manifest.inmanifest.form.InOGMDoxManifestForm;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.service.InOGMDoxService;

// TODO: Auto-generated Javadoc
/**
 * The Class InOGMDoxAction.
 * 
 * @author uchauhan
 */
public class InOGMDoxAction extends InManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InOGMDoxAction.class);

	/** The in manifest universal service. */
	private InManifestUniversalService inManifestUniversalService;

	/** The in manifest common service. */
	private transient InManifestCommonService inManifestCommonService;

	/** The in ogm dox service. */
	private transient InOGMDoxService inOgmDoxService;

	/**
	 * prepares the pages with initial values.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward viewInOGMDox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InOGMDoxAction::viewInOGMDox::START------------>:::::::");
		List<LabelValueBean> officeTypeList = null;
		try {
			preparePage(request);
			inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
			officeTypeList = inManifestUniversalService.getOfficeTypeList();
			request.setAttribute("officeTypeList", officeTypeList);
			request.setAttribute("processCode",
					CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
			request.setAttribute("processCodeBPL",
					InManifestConstants.PROCESS_CODE_BPL);
			request.setAttribute("updatedProcessCode",
					CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);	
			request.setAttribute("maxAllowedRows",
					getConfigParamValue(request, InManifestConstants.MAX_CN_ALLOWED_FOR_IN_DOX));	
			
			ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			consignmentTypeTO = inManifestUniversalService.getConsgType(consignmentTypeTO);
			if (consignmentTypeTO != null) {
				request.setAttribute("consignmentTypeId",
						consignmentTypeTO.getConsignmentId());		
			}

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewInOGMDox of InOGMDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewInOGMDox of InOGMDoxAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewInOGMDox of InOGMDoxAction..."
					, e);
		}
		LOGGER.debug("InOGMDoxAction::viewInOGMDox::END------------>:::::::");
		return mapping.findForward(InManifestConstants.SUCCESS);

	}

	/**
	 * gets the details for given packet number and its corresponding
	 * consignments.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the consg manifested details
	 */
	public void getConsgManifestedDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("InOGMDoxAction::getConsgManifestedDetails::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		InManifestOGMTO ogmTO = new InManifestOGMTO();
		try {
			out = response.getWriter();
			inManifestCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			ManifestBaseTO baseTO = new ManifestBaseTO();
			//String loginOffice = request.getParameter("loggedInOffice");
			String manifestNum = request.getParameter("manifestNumber");

			baseTO.setManifestNumber(manifestNum);
			baseTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);

			inOgmDoxService = (InOGMDoxService) getBean(SpringConstants.IN_OGM_DOX_SERVICE);
			ogmTO = inOgmDoxService.getConsgManifestedDetails(baseTO);

			//ogmTO.setLoginRegionOffice(loginOffice);
			
			/*// ogmTO.setIsManifested(CommonConstants.YES);
			if (ogmTO.getManifestId() == null) {
				// get out manifest data
				baseTO.setProcessCode(InManifestConstants.PROCESS_CODE_IN_OGM);
				baseTO.setManifestNumber(manifestNum);
				baseTO.setUpdateProcessCode(InManifestConstants.PROCESS_CODE_IN_OGM);
				baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
				ogmTO = inOgmDoxService.getConsgManifestedDetails(baseTO);
				ogmTO.setIsManifested(CommonConstants.NO);
				ogmTO.setLoginRegionOffice(loginOffice);
				if (ogmTO.getManifestId() == null) {
					ogmTO = null;
				} else {
					ogmTO.setManifestId(null);
				}
			}*/
			
			jsonResult = JSONSerializer.toJSON(ogmTO).toString();

		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
			LOGGER.error("Exception happened in getConsgManifestedDetails of InOGMDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("Exception happened in getConsgManifestedDetails of InOGMDoxAction..."
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
			LOGGER.error("Exception happened in getConsgManifestedDetails of InOGMDoxAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("InOGMDoxAction::getConsgManifestedDetails::END------------>:::::::");
	}

	/**
	 * gets the consignment details for given consignment number.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the consg details
	 * @throws IOException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getConsgDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, CGSystemException {
		LOGGER.debug("InOGMDoxAction::getConsgDetails::START------------>:::::::");
		/*
		 * PrintWriter out = null; String manifestTOJSON = null;
		 */
		ConsignmentTO consgTO = null;
		String message = "";
		try {
			// out = response.getWriter();
			String consgNum = request.getParameter("consignmentNumber");
			InBagManifestTO inBagManifestTO = new InBagManifestTO();
			inBagManifestTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestTO.setConsgNumber(consgNum);

			// String manifestNum = request.getParameter("manifestNum");
			/*
			 * inManifestCommonService = (InManifestCommonService)
			 * getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE); consgTO =
			 * inManifestCommonService.getConsgDetails(consgNum,manifestNum);
			 */

			inOgmDoxService = (InOGMDoxService) getBean(SpringConstants.IN_OGM_DOX_SERVICE);
			consgTO = inOgmDoxService.getConsgDetails(inBagManifestTO);

			// manifestTOJSON = JSONSerializer.toJSON(consgTO).toString();
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getConsgDetails of InOGMDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getConsgDetails of InOGMDoxAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getConsgDetails of InOGMDoxAction..."
					, e);
		} finally {
			if (consgTO == null) {
				consgTO = new ConsignmentTO();
			}
			consgTO.setMessage(message);
			String consgTOJSON = serializer.toJSON(consgTO).toString();
			response.getWriter().print(consgTOJSON);
		}
		/*
		 * }catch (CGBusinessException e) { message =
		 * getMessageFromErrorBundle(request
		 * ,InManifestConstants.CONSIGNMENT_ALREADY_EXSITS);
		 * //consgTO.setMessage(message); manifestTOJSON = message; }catch (
		 * CGSystemException | IOException e) { LOGGER.error(
		 * "Exception happened in getConsgManifestedDetails of InOGMDoxAction..."
		 * , e); } finally { // manifestTOJSON =
		 * JSONSerializer.toJSON(consgTO).toString(); out.print(manifestTOJSON);
		 * out.flush(); out.close(); }
		 */
		LOGGER.debug("InOGMDoxAction::getConsgDetails::END------------>:::::::");
	}

	/**
	 * Save or update out manifest dox.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void saveOrUpdateInOGMDox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InOGMDoxAction::saveOrUpdateInOGMDox::START------------>:::::::");
		InManifestOGMTO inManifestOGMTO = null;
		InOGMDoxManifestForm inOGMDoxForm = null;
		// String transMsg = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<InManifestOGMDetailTO> inmanifestDtls = null;
		String message = null;
		try {
			out = response.getWriter();
			inOGMDoxForm = (InOGMDoxManifestForm) form;
			inManifestOGMTO = (InManifestOGMTO) inOGMDoxForm.getTo();
			inManifestOGMTO
					.setManifestType(InManifestConstants.MANIFEST_TYPE_IN);
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType
					.setConsignmentName(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			inManifestOGMTO.setConsignmentTypeTO(consgType);
			if (inManifestOGMTO != null) {
				inmanifestDtls = InOGMDoxConverter
						.createInOGMDoxDetailTO(inManifestOGMTO);

				inManifestOGMTO.setInManifestOGMDetailTOs(inmanifestDtls);
				inOgmDoxService = (InOGMDoxService) getBean(SpringConstants.IN_OGM_DOX_SERVICE);
				inManifestOGMTO = inOgmDoxService
						.saveOrUpdateInOGMDox(inManifestOGMTO);

				//calling TwoWayWrite service to save same in central
				twoWayWrite(inManifestOGMTO);
				
				message = prepareLessExcessPacketMsg(inManifestOGMTO, request);
			}
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			//message = getSystemExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_OGM_DOX_DETAILS, null);
			LOGGER.error("Exception happened in saveOrUpdateInOGMDox of InOGMDoxAction..."
					, e);			
		} catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateInOGMDox of InOGMDoxAction..."
					, e);			
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			//message = getGenericExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_OGM_DOX_DETAILS, null);
			LOGGER.error("Exception happened in saveOrUpdateInOGMDox of InOGMDoxAction..."
					, e);
		} finally {
			out.print(message);
			out.flush();
			out.close();
		}
		LOGGER.debug("InOGMDoxAction::saveOrUpdateInOGMDox::END------------>:::::::");
	}

	/**
	 * Prepare less excess packet msg.
	 * 
	 * @param inManifestOGMTO
	 *            the in manifest ogmto
	 * @param request
	 *            the request
	 * @return the string
	 */
	private String prepareLessExcessPacketMsg(InManifestOGMTO inManifestOGMTO,
			HttpServletRequest request) {
		LOGGER.debug("InOGMDoxAction::prepareLessExcessPacketMsg::START------------>:::::::");
		String message = null;
		if (StringUtils.isNotBlank(inManifestOGMTO.getLessConsgs())
				&& StringUtils.isNotBlank(inManifestOGMTO.getExcessConsgs())) {
			message = getMessageFromErrorBundle(
					request,
					InManifestConstants.IN_OGM_DOX_DETAILS_SAVED_LESS_EXCESS,
					new String[] { inManifestOGMTO.getManifestNumber(),
							inManifestOGMTO.getLessConsgs(),
							inManifestOGMTO.getExcessConsgs() });
		} else if (StringUtils.isNotBlank(inManifestOGMTO.getLessConsgs())
				&& StringUtils.isBlank(inManifestOGMTO.getExcessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_OGM_DOX_DETAILS_SAVED_LESS,
					new String[] { inManifestOGMTO.getManifestNumber(),
							inManifestOGMTO.getLessConsgs() });
		} else if (StringUtils.isNotBlank(inManifestOGMTO.getExcessConsgs())
				&& StringUtils.isBlank(inManifestOGMTO.getLessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_OGM_DOX_DETAILS_SAVED_EXCESS,
					new String[] { inManifestOGMTO.getManifestNumber(),
							inManifestOGMTO.getExcessConsgs() });
		} else if (StringUtils.isBlank(inManifestOGMTO.getExcessConsgs())
				&& StringUtils.isBlank(inManifestOGMTO.getLessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_OGM_DOX_DETAILS_SAVED,
					new String[] { inManifestOGMTO.getManifestNumber() });
		}
		LOGGER.debug("InOGMDoxAction::prepareLessExcessPacketMsg::END------------>:::::::");
		return message;
	}

	public ActionForward printManifestedDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.trace("InOGMDoxAction::printManifestedDetails::START------------>:::::::");
		InManifestOGMTO ogmTO = new InManifestOGMTO();
		try {
			inManifestCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			ManifestBaseTO baseTO = new ManifestBaseTO();
			String loginOffice = request.getParameter("loggedInOffice");
			if (StringUtils.isNotEmpty(request.getParameter("manifestNumber"))) {
				baseTO.setManifestNumber(request.getParameter("manifestNumber"));
				baseTO.setLoggedInOfficeId(Integer.valueOf(request
						.getParameter("loggedInOfficeId")));
				baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
				
				/*
				baseTO.setUpdateProcessCode(request
						.getParameter("updatedProcessCode"));
				baseTO.setProcessCode(request.getParameter("processCode"));
				OfficeTO destinationOfficeTO = new OfficeTO();
				destinationOfficeTO.setOfficeId(Integer.valueOf(request
						.getParameter("loggedInOfficeId")));
				baseTO.setDestinationOfficeTO(destinationOfficeTO);*/

				inOgmDoxService = (InOGMDoxService) getBean(SpringConstants.IN_OGM_DOX_SERVICE);
				ogmTO = inOgmDoxService.getConsgManifestedDetails(baseTO);
				ogmTO.setLoginRegionOffice(loginOffice);
				HttpSession session = request.getSession(Boolean.FALSE);
				UserInfoTO userInfoTO = (UserInfoTO) session
						.getAttribute(InManifestConstants.USER_INFO);

				OfficeTO officeTO = userInfoTO.getOfficeTo();
				if (!StringUtil.isNull(officeTO)) {
					ogmTO.setLoggedInOfficeName(officeTO.getOfficeName());
					ogmTO.setLoggedInOfficeCity(officeTO.getAddress3());
				}
				request.setAttribute("ogmTO", ogmTO);

			}

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in printManifestedDetails of InOGMDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in printManifestedDetails of InOGMDoxAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in printManifestedDetails of InOGMDoxAction..."
					, e);
		}
		LOGGER.trace("InOGMDoxAction::printManifestedDetails::END------------>:::::::");
		return mapping.findForward(InManifestConstants.URL_PRINT_IN_OGM);
	}

	@SuppressWarnings("static-access")
	public void validateCoMailNumber(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException,
			CGSystemException {
		LOGGER.debug("InOGMDoxAction::validateCoMailNumber::START------------>:::::::");
		String message = null;
		InManifestValidationTO inManifestValidationTO = null;
		try {

			final InManifestTO inManifestTO = new InManifestTO();

			inManifestTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inManifestTO.setCoMailNo(request.getParameter("coMailNo"));

			inOgmDoxService = (InOGMDoxService) getBean(SpringConstants.IN_OGM_DOX_SERVICE);
			inManifestValidationTO = inOgmDoxService
					.validateCoMailNumber(inManifestTO);
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateCoMailNumber of InOGMDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateCoMailNumber of InOGMDoxAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateCoMailNumber of InOGMDoxAction..."
					, e);
		} finally {
			if (inManifestValidationTO == null) {
				inManifestValidationTO = new InManifestValidationTO();
			}
			inManifestValidationTO.setErrorMsg(message);
			String inManifestValidationTOJSON = serializer.toJSON(
					inManifestValidationTO).toString();
			response.getWriter().print(inManifestValidationTOJSON);
		}
		LOGGER.debug("InOGMDoxAction::validateCoMailNumber::END------------>:::::::");
	}
	/**
	 * Gets the config param value.
	 *
	 * @param request the request
	 * @param paramKey the param key
	 * @return the config param value
	 */
	public String getConfigParamValue(final HttpServletRequest request,String paramKey){
		String pramValue=null;
		if(!CGCollectionUtils.isEmpty(configurableParams)){
			pramValue=configurableParams.get(paramKey);
		} 
		return pramValue;
	}
	
}
