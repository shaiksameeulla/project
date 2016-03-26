package com.ff.web.manifest.inmanifest.action;

import java.io.PrintWriter;
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
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.CityTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.service.InManifestUniversalService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InMasterBagManifestConverter;
import com.ff.web.manifest.inmanifest.form.InMasterBagManifestForm;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.service.InMasterBagManifestService;

/**
 * The Class InMasterBagManifestAction.
 * 
 * @author uchauhan
 */
public class InMasterBagManifestAction extends InManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InMasterBagManifestAction.class);

	/** The in mbpl service. */
	private InMasterBagManifestService inMBPLService;

	/** The in manifest universal service. */
	private InManifestUniversalService inManifestUniversalService;

	/** The in mbpl common service. */
	private InManifestCommonService inMBPLCommonService;

	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/**
	 * Initializes the page with default values.
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
	public ActionForward viewInMBPL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.trace("InMasterBagManifestAction::viewInMBPL::START------------>:::::::");
		List<LabelValueBean> officeTypeList = null;
		try {
			preparePage(request);
			inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
			officeTypeList = inManifestUniversalService.getOfficeTypeList();
			request.setAttribute("officeTypeList", officeTypeList);
			request.setAttribute("processCode",
					CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
			request.setAttribute("processCodeBPL",
					InManifestConstants.PROCESS_CODE_BPL);
			request.setAttribute("updatedProcessCode",
					CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
			request.setAttribute("updatedProcessCodeBPL",
					CommonConstants.PROCESS_RECEIVE);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::viewInMBPL :: "
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::viewInMBPL :: "
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::viewInMBPL :: "
					, e);
		}
		LOGGER.trace("InMasterBagManifestAction::viewInMBPL::END------------>:::::::");
		return mapping.findForward(InManifestConstants.SUCCESS);

	}

	/**
	 * Gets the city by code.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the city by code
	 */
	@SuppressWarnings("static-access")
	public void getCityByCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InMasterBagManifestAction::getCityByCode::START------------>:::::::");
		PrintWriter out = null;
		String cityTOJSON = null;
		CityTO cityTO = new CityTO();
		try {
			out = response.getWriter();
			String cityCode = request.getParameter("cityCode");
			geographyCommonService = (GeographyCommonService) getBean("geographyCommonService");
			cityTO.setCityCode(cityCode);
			cityTO = geographyCommonService.getCity(cityTO);

			cityTOJSON = serializer.toJSON(cityTO).toString();

		} catch (CGSystemException e) {
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getCityByCode :: "
					, e);
		} catch (CGBusinessException e) {
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getCityByCode :: "
					, e);
		} catch (Exception e) {
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getCityByCode :: "
					, e);
		} finally {
			out.print(cityTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("InMasterBagManifestAction::getCityByCode::END------------>:::::::");
	}

	/**
	 * Gets the consignment type list.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the consignment type list
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentTypeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InMasterBagManifestAction::getConsignmentTypeList::START------------>:::::::");

		PrintWriter out = null;
		String consignmentTypeTOListJSON = null;
		try {
			out = response.getWriter();
			serviceOfferingCommonService = (ServiceOfferingCommonService) getBean("serviceOfferingCommonService");
			List<ConsignmentTypeTO> consignmentTypeTOList = serviceOfferingCommonService
					.getConsignmentType();
			consignmentTypeTOListJSON = serializer.toJSON(
					consignmentTypeTOList).toString();

		} catch (CGSystemException e) {
			consignmentTypeTOListJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getConsignmentTypeList :: "
					, e);
		} catch (CGBusinessException e) {
			consignmentTypeTOListJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getConsignmentTypeList :: "
					, e);
		} catch (Exception e) {
			consignmentTypeTOListJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getConsignmentTypeList :: "
					, e);
		} finally {
			out.print(consignmentTypeTOListJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("InMasterBagManifestAction::getConsignmentTypeList::END------------>:::::::");
	}

	/**
	 * gets the details for given BPL number.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the manifest grid dtls
	 */
	@SuppressWarnings("static-access")
	public void getManifestGridDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ManifestBaseTO baseTO = new ManifestBaseTO();
		InMasterBagManifestDetailsTO detailsTO = new InMasterBagManifestDetailsTO();

		LOGGER.trace("InManifestAction::getManifestDtls::START------------>:::::::");
		PrintWriter out = null;
		String manifestTOJSON = null;
		try {
			out = response.getWriter();

			/*No need
			 * baseTO.setUpdateProcessCode(request
					.getParameter("updatedProcessCode"));*/
			/*baseTO.setProcessCode(request.getParameter("processCode"));
			baseTO.setUpdateProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG
					+ CommonConstants.COMMA
					+ CommonConstants.PROCESS_RECEIVE);
			
			//baseTO.setDestinationOfficeId(loginOffId);
			*/
			
			String loginOfficeId = request
					.getParameter(InManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			baseTO.setLoggedInOfficeId(loginOffId);
			baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
			baseTO.setManifestNumber(request.getParameter("manifestNumber"));
			
			inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			detailsTO = (InMasterBagManifestDetailsTO) inMBPLCommonService
					.getManifestGridDtls(baseTO);
			
			manifestTOJSON = serializer.toJSON(detailsTO).toString();
			
		} catch (CGSystemException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getManifestDtls :: "
					, e);
		} catch (CGBusinessException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getManifestDtls :: "
					, e);
		} catch (Exception e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::getManifestDtls :: "
					, e);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction ::getManifestDtls::END------------>:::::::");

	}

	/**
	 * save the MBPL details.
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
	public void saveOrUpdateInMBPL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InMasterBagManifestAction::saveOrUpdateInMBPL::START------------>:::::::");

		InMasterBagManifestTO inMasterBagManifestTO = null;
		InMasterBagManifestForm inMasterBagManifestForm = null;
		String message = "";
		PrintWriter out = null;
		List<InMasterBagManifestDetailsTO> inMasterBagManifestDtlsTOs = null;
		try {
			out = response.getWriter();
			inMasterBagManifestForm = (InMasterBagManifestForm) form;
			inMasterBagManifestTO = (InMasterBagManifestTO) inMasterBagManifestForm
					.getTo();
			if (inMasterBagManifestTO != null) {
				// Preparing List of TO's from UI
				inMasterBagManifestDtlsTOs = setUpMasterBagManifestTos(inMasterBagManifestTO);
				inMasterBagManifestTO
						.setInMasterBagManifestDtlsTOs(inMasterBagManifestDtlsTOs);
				inMBPLService = (InMasterBagManifestService) getBean(SpringConstants.MBPL_IN_MANIFEST_SERVICE);
				inMasterBagManifestTO = inMBPLService
						.saveOrUpdateInMBPL(inMasterBagManifestTO);

				//calling TwoWayWrite service to save same in central
				twoWayWrite(inMasterBagManifestTO);
				
				message = prepareLessExcessPacketMsg(inMasterBagManifestTO,
						request);
			}
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			/*message = getSystemExceptionMessage(request, e,
					InManifestConstants.ERROR_IN_SAVING_IN_MBPL_DETAILS, null);*/
			LOGGER.error("Exception happened in saveOrUpdateInMBPL of InMasterBagManifestAction..."
					, e);

		} catch (CGBusinessException e) {
			/*message = getMessageFromErrorBundle(request,
					InManifestConstants.MANIFEST_ALREADY_EXSITS);*/
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateInMBPL of InMasterBagManifestAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			// message = prepareErrorMessageSystemException(request, e,
			// InManifestConstants.ERROR_IN_SAVING_IN_MBPL_DETAILS);
			/*message = getGenericExceptionMessage(request, e,
					InManifestConstants.ERROR_IN_SAVING_IN_MBPL_DETAILS, null);*/
			LOGGER.error("Exception happened in saveOrUpdateInMBPL of InMasterBagManifestAction..."
					, e);
		} finally {
			out.print(message);
			out.flush();
			out.close();
		}
		LOGGER.trace("InMasterBagManifestAction::saveOrUpdateInMBPL::END------------>:::::::");
	}

	/**
	 * Prepare less excess packet msg.
	 * 
	 * @param inMasterBagManifestTO
	 *            the in master bag manifest to
	 * @param request
	 *            the request
	 * @return the string
	 */
	private String prepareLessExcessPacketMsg(
			InMasterBagManifestTO inMasterBagManifestTO,
			HttpServletRequest request) {
		LOGGER.trace("InMasterBagManifestAction::prepareLessExcessPacketMsg::START------------>:::::::");
		String message = null;
		if (StringUtils.isNotBlank(inMasterBagManifestTO.getLessManifest())
				&& StringUtils.isNotBlank(inMasterBagManifestTO
						.getExcessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_MBPL_DOX_DETAILS_SAVED_LESS_EXCESS,
					new String[] { inMasterBagManifestTO.getManifestNumber(),
							inMasterBagManifestTO.getLessManifest(),
							inMasterBagManifestTO.getExcessManifest() });
		}
		if (StringUtils.isNotBlank(inMasterBagManifestTO.getLessManifest())
				&& StringUtils.isBlank(inMasterBagManifestTO
						.getExcessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_MBPL_DOX_DETAILS_SAVED_LESS,
					new String[] { inMasterBagManifestTO.getManifestNumber(),
							inMasterBagManifestTO.getLessManifest() });
		}
		if (StringUtils.isNotBlank(inMasterBagManifestTO.getExcessManifest())
				&& StringUtils.isBlank(inMasterBagManifestTO.getLessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_MBPL_DOX_DETAILS_SAVED_EXCESS,
					new String[] { inMasterBagManifestTO.getManifestNumber(),
							inMasterBagManifestTO.getExcessManifest() });
		}
		if (StringUtils.isBlank(inMasterBagManifestTO.getExcessManifest())
				&& StringUtils.isBlank(inMasterBagManifestTO.getLessManifest())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_MBPL_DOX_DETAILS_SAVED,
					new String[] { inMasterBagManifestTO.getManifestNumber() });
		}
		LOGGER.trace("InMasterBagManifestAction::prepareLessExcessPacketMsg::END------------>:::::::");
		return message;
	}

	/**
	 * prepares a list of InMasterBagManifestDetailsTO.
	 * 
	 * @param inMBPLTO
	 *            the in mbplto
	 * @return list of InMasterBagManifestDetailsTO
	 */
	private List<InMasterBagManifestDetailsTO> setUpMasterBagManifestTos(
			InMasterBagManifestTO inMBPLTO) {
		List<InMasterBagManifestDetailsTO> inMBPLDetailTOs = null;
		inMBPLDetailTOs = InMasterBagManifestConverter
				.inMBPLConverter(inMBPLTO);
		return inMBPLDetailTOs;
	}

	/**
	 * Search manifest details for mbpl number.
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
	@SuppressWarnings("static-access")
	public void searchMBPLManifest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InMasterBagManifestAction::searchMBPLManifest::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = "";
		InMasterBagManifestTO inMasterBagManifestTO = null;
		// BPLOutManifestDoxTO bplOutManifestDoxTOs = new BPLOutManifestDoxTO();
		try {
			out = response.getWriter();
			//String loginOffName = request.getParameter("loginOffName");
			String manifestNo = request.getParameter(
					InManifestConstants.MANIFEST_NO).trim();
			String loginOfficeId = request
					.getParameter(InManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {

				InMasterBagManifestTO manifestTO = new InMasterBagManifestTO();
				manifestTO.setManifestNumber(manifestNo);
				manifestTO.setLoggedInOfficeId(loginOffId);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN); // Manifest_Type
				/*
				manifestTO.setDestinationOfficeId(loginOffId);
				manifestTO
						.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
				manifestTO
						.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG
								+ CommonConstants.COMMA
								+ CommonConstants.PROCESS_RECEIVE);
				manifestTO
						.setManifestDirection(InManifestConstants.MANIFEST_DIRECTION_IN);*/
				inMBPLService = (InMasterBagManifestService) getBean(SpringConstants.MBPL_IN_MANIFEST_SERVICE);
				inMasterBagManifestTO = inMBPLService
						.searchMBPLManifest(manifestTO);
				if (inMasterBagManifestTO.getManifestNumber() == null) {
					inMasterBagManifestTO.setEmpty(true);
				}
				//inMasterBagManifestTO.setLoginOffName(loginOffName);
				jsonResult = serializer.toJSON(inMasterBagManifestTO)
						.toString();
			}
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::searchMBPLManifest :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::searchMBPLManifest :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::searchMBPLManifest :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("InMasterBagManifestAction::searchMBPLManifest::END------------>:::::::");
	}

	/**
	 * validates if the bag is already inManifested
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void isBPLExists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InMasterBagManifestAction::isBPLExists::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = "";
		ManifestBaseTO baseTO = new ManifestBaseTO();

		try {
			out = response.getWriter();
			String bplNo = request.getParameter("bplNo");
			if (bplNo != null) {
				baseTO.setManifestNumber(bplNo);
				baseTO.setProcessCode(InManifestConstants.PROCESS_CODE_BPL);
				baseTO.setUpdateProcessCode(InManifestConstants.PROCESS_CODE_BPL);
				inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
				baseTO = (ManifestBaseTO) inMBPLCommonService
						.isBPLExists(baseTO);
				jsonResult = baseTO.getTransMsg();
			}
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::isBPLExists :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::isBPLExists :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::isBPLExists :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("InMasterBagManifestAction::isBPLExists::END------------>:::::::");
	}

	public ActionForward printMBPLManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.trace("InMasterBagManifestAction::printMBPLManifest::START------------>:::::::");
		InMasterBagManifestTO inMasterBagManifestTO = null;
		InMasterBagManifestTO inMasterBagManifestTO1 = null;
		try {

			String loginOffName = request.getParameter("loginOffName");
			String manifestNo = request.getParameter(
					InManifestConstants.MANIFEST_NO).trim();
			String loginOfficeId = request
					.getParameter(InManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {

				InMasterBagManifestTO manifestTO = new InMasterBagManifestTO();
				manifestTO.setLoggedInOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
				/*manifestTO.setDestinationOfficeId(loginOffId);
				manifestTO
						.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);

				manifestTO
						.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
				manifestTO
						.setManifestDirection(InManifestConstants.MANIFEST_DIRECTION_IN);*/
				inMBPLService = (InMasterBagManifestService) getBean(SpringConstants.MBPL_IN_MANIFEST_SERVICE);
				inMasterBagManifestTO = inMBPLService
						.searchMBPLManifest(manifestTO);
				if (inMasterBagManifestTO.getManifestNumber() == null) {
					inMasterBagManifestTO.setEmpty(true);
				}
				inMasterBagManifestTO.setLoginOffName(loginOffName);

				if (!StringUtil.isNull(inMasterBagManifestTO
						.getInMasterBagManifestDtlsTOs())) {
					inMasterBagManifestTO1 = inMBPLService
							.getInfoForPrint(inMasterBagManifestTO
									.getInMasterBagManifestDtlsTOs());
				}

				if (!StringUtil.isNull(inMasterBagManifestTO1)) {
					if (!StringUtil.isNull(inMasterBagManifestTO1
							.getTotalConsg())) {
						inMasterBagManifestTO
								.setTotalConsg(inMasterBagManifestTO1
										.getTotalConsg());
					}

					if (!StringUtil.isNull(inMasterBagManifestTO1
							.getTotalComail())) {
						inMasterBagManifestTO
								.setTotalComail(inMasterBagManifestTO1
										.getTotalComail());
					}

					if (!StringUtil.isEmptyInteger(inMasterBagManifestTO1
							.getRowCount())) {
						inMasterBagManifestTO
								.setRowCount(inMasterBagManifestTO1
										.getRowCount());
					}
				}
				HttpSession session = request.getSession(Boolean.FALSE);
				UserInfoTO userInfoTO = (UserInfoTO) session
						.getAttribute(InManifestConstants.USER_INFO);

				OfficeTO officeTO = userInfoTO.getOfficeTo();
				if (!StringUtil.isNull(officeTO)) {
					inMasterBagManifestTO.setLoggedInOfficeName(officeTO.getOfficeName());
					inMasterBagManifestTO.setLoggedInOfficeCity(officeTO.getAddress3());
				}
				request.setAttribute("inMasterBagManifestTO",
						inMasterBagManifestTO);

			}
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::printMBPLManifest :: " , e);
			
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::printMBPLManifest :: " , e);
			
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception Occured in::InMasterBagManifestAction::printMBPLManifest :: " , e);
		}
		LOGGER.trace("InMasterBagManifestAction::printMBPLManifest::END------------>:::::::");
		return mapping.findForward(InManifestConstants.URL_PRINT_IN_MBPL);
	}

}
