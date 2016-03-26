package com.ff.web.manifest.inmanifest.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.global.RemarksTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestIssueValidationTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.service.InManifestUniversalService;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;

/**
 * The Class InManifestAction.
 * 
 * @author uchauhan
 */
public abstract class InManifestAction extends CGBaseAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InManifestAction.class);

	/** The in mbpl common service. */
	private InManifestCommonService inMBPLCommonService;

	/** The in manifest universal service. */
	private InManifestUniversalService inManifestUniversalService;

	/** The in manifest universal service. */
	private ManifestUniversalService manifestUniversalService;

	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * Initializes the page with default values.
	 * 
	 * @param request
	 *            the request
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void preparePage(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestAction::preparePage::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		Integer loggedInOfficeId = officeTO.getOfficeId();
		Integer cityId = officeTO.getCityId();
		// String loginOfficeName = officeTO.getOfficeName();
		// Integer regionId = officeTO.getReportingRHO();
		inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
		/*
		 * OfficeTO regionOfficeTO = inManifestUniversalService
		 * .getOfficeDetails(regionId); String regionName =
		 * regionOfficeTO.getOfficeName();
		 */
		/*
		 * String loginRegionOffice = regionName + CommonConstants.HYPHEN +
		 * loginOfficeName;
		 */
		request.setAttribute("destCityId", cityId);
		if (officeTO.getRegionTO() != null) {
			String loginRegionOffice = officeTO.getRegionTO()
					.getRegionDisplayName()
					+ CommonConstants.HYPHEN
					+ officeTO.getOfficeName();
			request.setAttribute("destinationOffice", loginRegionOffice);
		}

		request.setAttribute(InManifestConstants.LOGIN_OFFICE_ID,
				loggedInOfficeId);
		List<RegionTO> regionTOs = inManifestUniversalService.getAllRegions();
		request.setAttribute(InManifestConstants.REGION_TOS, regionTOs);
		// Get Remarks Details for manifest
		setRemarksDetails(request);
		LOGGER.trace("InManifestAction::preparePage::END------------>:::::::");
	}

	/**
	 * Gets the manifest dtls.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @Method : getManifestDtls
	 * @Desc : retrives the records when a manifest number is scanned
	 */
	public void getManifestDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ManifestBaseTO baseTO = new ManifestBaseTO();

		LOGGER.trace("InManifestAction::getManifestDtls::START------------>:::::::");
		PrintWriter out = null;
		String manifestTOJSON = null;
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request.getParameter("manifestNumber"))
					&& StringUtils.isNotEmpty(request
							.getParameter("processCode"))) {
				baseTO.setProcessCode(request.getParameter("processCode"));
				baseTO.setManifestNumber(request.getParameter("manifestNumber"));
				baseTO.setUpdateProcessCode(request
						.getParameter("updatedProcessCode"));

				inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
				baseTO = (ManifestBaseTO) inMBPLCommonService
						.getManifestDtls(baseTO);
			}
			manifestTOJSON = JSONSerializer.toJSON(baseTO).toString();

		} catch (CGSystemException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getManifestDtls of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getManifestDtls of InManifestAction..."
					, e);
		} catch (Exception e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getManifestDtls of InManifestAction..."
					, e);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction ::getManifestDtls::END------------>:::::::");

	}

	/**
	 * get all the cities based on Region.
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the cities by region
	 */
	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InManifestAction::getCitiesByRegion::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			RegionTO regionTO = new RegionTO();
			String region = request.getParameter(InManifestConstants.REGION_ID);
			if (StringUtils.isNotEmpty(region)) {

				regionTO.setRegionId(Integer.parseInt(region));
			}

			inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			List<CityTO> cityTOs = inMBPLCommonService
					.getCitiesByRegion(regionTO);

			jsonResult = serializer.toJSON(cityTOs).toString();

		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getCitiesByRegion of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getCitiesByRegion of InManifestAction..."
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getCitiesByRegion of InManifestAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction::getCitiesByRegion::END------------>:::::::");
	}

	/**
	 * get all the offices by city.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the all offices by city
	 */
	@SuppressWarnings("static-access")
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("InManifestAction::getAllOfficesByCity::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if (!StringUtil.isEmptyInteger(cityId)) {
				inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
				List<OfficeTO> officeTOs = inMBPLCommonService
						.getAllOfficesByCity(cityId);
				jsonResult = serializer.toJSON(officeTOs).toString();

			}
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCity of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCity of InManifestAction..."
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCity of InManifestAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction::getAllOfficesByCity::END------------>:::::::");
	}

	/**
	 * get all the offices based on city and office type.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the all offices by city and office type
	 */
	@SuppressWarnings("static-access")
	public void getAllOfficesByCityAndOfficeType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("InManifestAction::getAllOfficesByCityAndOfficeType::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");
			String officeType = request.getParameter("officeTypeId");
			Integer cityId = 0;
			Integer officeTypeId = 0;
			if (StringUtils.isNotEmpty(city)
					&& StringUtils.isNotEmpty(officeType)) {
				cityId = Integer.parseInt(city);
				officeTypeId = Integer.parseInt(officeType);
			}
			if (cityId != null && cityId > 0) {
				inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
				List<OfficeTO> officeTOs = inMBPLCommonService
						.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);
				jsonResult = serializer.toJSON(officeTOs).toString();

			}
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCityAndOfficeType of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCityAndOfficeType of InManifestAction..."
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getAllOfficesByCityAndOfficeType of InManifestAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction::getAllOfficesByCityAndOfficeType::END------------>:::::::");
	}

	/**
	 * Validate pincode.
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
	public void validatePincode(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InManifestAction::validatePincode::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = null;
		PrintWriter out = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincode(request.getParameter("pincode"));

			inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			inManifestValidationTO = inMBPLCommonService
					.validatePincode(pincodeTO);

		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validatePincode of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validatePincode of InManifestAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validatePincode of InManifestAction..."
					, e);
		} finally {
			if (inManifestValidationTO == null) {
				inManifestValidationTO = new InManifestValidationTO();
			}
			inManifestValidationTO.setErrorMsg(errorMsg);
			String inManifestValidationTOJSON = serializer.toJSON(
					inManifestValidationTO).toString();
			out.print(inManifestValidationTOJSON);
		}
		LOGGER.debug("InManifestAction::validatePincode::END------------>:::::::");
	}

	/**
	 * Checks if is manifest no issued.
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
	public void isManifestNoIssued(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InManifestAction::isManifestNoIssued::START------------>:::::::");
		ManifestIssueValidationTO stockIssueValidationTO = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String manifestNo = CommonConstants.EMPTY_STRING;
		String seriesType = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		boolean isIssued = false;
		String errorMsg = null;
		try {
			out = response.getWriter();
			stockIssueValidationTO = new ManifestIssueValidationTO();
			manifestNo = request.getParameter("manifestNo");
			seriesType = request.getParameter("seriesType");
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if (StringUtils.isNotEmpty(manifestNo)
					&& StringUtils.isNotEmpty(seriesType)) {
				stockIssueValidationTO.setStockItemNumber(manifestNo);
				stockIssueValidationTO.setSeriesType(seriesType);
				stockIssueValidationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
				stockIssueValidationTO.setIssuedTOPartyId(officeTO
						.getRegionTO().getRegionId());
				stockIssueValidationTO.setRegionCode(officeTO.getRegionTO()
						.getRegionCode());
				manifestUniversalService = (ManifestUniversalService) getBean(SpringConstants.MANIFEST_UNIVERSAL_SERVICE);
				isIssued = manifestUniversalService
						.isManifesIssued(stockIssueValidationTO);
			}
			stockIssueValidationTO.setIsIssued((isIssued) ? CommonConstants.YES
					: CommonConstants.NO);
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNoIssued of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			stockIssueValidationTO.setIsIssued(CommonConstants.NO);
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in isManifestNoIssued of InManifestAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNoIssued of InManifestAction..."
					, e);
		} finally {
			stockIssueValidationTO.setErrorMsg(errorMsg);
			jsonResult = serializer.toJSON(stockIssueValidationTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("InManifestAction::isManifestNoIssued::END------------>:::::::");
	}

	public void getAndSetLoggedInOfficeDtls(final HttpServletRequest request,
			InManifestTO inManifestTO) {
		LOGGER.debug("InManifestAction::getAndSetLoggedInOfficeDtls::START------------>:::::::");
		try {
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();

			inManifestTO.setLoggedInOfficeTO(loggedInOfficeTO);
		} /*catch (CGSystemException e) {
			// errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getAndSetLoggedInOfficeDtls of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			// errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getAndSetLoggedInOfficeDtls of InManifestAction..."
					, e);
		} */catch (Exception e) {
			// errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getAndSetLoggedInOfficeDtls of InManifestAction..."
					, e);
		}
		LOGGER.debug("InManifestAction::getAndSetLoggedInOfficeDtls::END------------>:::::::");
	}

	/**
	 * Checks if is manifest num in manifested.
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
	public void isManifestNumInManifested(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InManifestAction::isManifestNumInManifested::START------------>:::::::");
		String errorMsg = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			final InManifestTO inManifestTO = new InManifestTO();
			inManifestTO.setManifestNumber(request
					.getParameter("manifestNumber"));
			inManifestTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inManifestTO.setProcessCode(request.getParameter("processCode"));
			inManifestTO.setUpdateProcessCode(request
					.getParameter("updatedProcessCode"));
			inManifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);

			inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
			boolean isInManifested = inMBPLCommonService
					.isManifestNumInManifested(inManifestTO);
			if (isInManifested) {
				errorMsg = getMessageFromErrorBundle(
						request,
						InManifestConstants.MANIFEST_NUMBER_ALREADY_IN_MANIFESTED,
						null);
			}

		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNumInManifested of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in isManifestNumInManifested of InManifestAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNumInManifested of InManifestAction..."
					, e);
		} finally {
			out.print(errorMsg);
		}
		LOGGER.debug("InManifestAction::isManifestNumInManifested::END------------>:::::::");
	}

	/**
	 * Gets the manifest dtls by manifest no.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the manifest dtls by manifest no
	 */
	public void getManifestDtlsByManifestNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("InManifestAction::getManifestDtlsByManifestNo::START------------>:::::::");
		ManifestBaseTO baseTO = null;
		PrintWriter out = null;
		String manifestTOJSON = null;
		try {
			out = response.getWriter();
			String manifestNumber = request.getParameter("manifestNumber");

			if (StringUtils.isNotEmpty(manifestNumber)) {

				inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
				baseTO = (ManifestBaseTO) inMBPLCommonService
						.getManifestDtlsByManifestNo(manifestNumber);
			}
			manifestTOJSON = JSONSerializer.toJSON(baseTO).toString();

		} catch (CGSystemException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getManifestDtlsByManifestNo of InManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getManifestDtlsByManifestNo of InManifestAction..."
					, e);
		} catch (Exception e) {
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getManifestDtlsByManifestNo of InManifestAction..."
					, e);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("InManifestAction ::getManifestDtlsByManifestNo::END------------>:::::::");

	}
	
	/**
	 * Sets the remarks details.
	 *
	 * @param request the new remarks details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unchecked")
	public void setRemarksDetails (HttpServletRequest request) throws CGSystemException, CGBusinessException{
		/* prepare remark drop down*/
		HttpSession session = (HttpSession) request.getSession(false);
		List<RemarksTO> remarks = (List<RemarksTO>) session
				.getAttribute(ManifestConstants.PARAM_IN_MANIFEST_REMARKS_LIST);
		if (StringUtil.isEmptyColletion(remarks)) {
			inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
			remarks = inManifestUniversalService
					.getInManifestRemarks(CommonConstants.PARAM_MANIFEST_REMARK_TYPE);
			session.setAttribute(ManifestConstants.PARAM_IN_MANIFEST_REMARKS_LIST, remarks);
		}
		request.setAttribute(ManifestConstants.PARAM_IN_MANIFEST_REMARKS_LIST, remarks);
	}
	
	/**
	 * Two way write.
	 *
	 * @param inManifestTO the in manifest to
	 */
	public void twoWayWrite(InManifestTO inManifestTO) {
		inMBPLCommonService = (InManifestCommonService) getBean(SpringConstants.IN_MANIFEST_COMMON_SERVICE);
		inMBPLCommonService.twoWayWrite(inManifestTO);
	}
	public void validateBranchCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		String transMsg = null;
		try {
			out = response.getWriter();
			String branchCode = request.getParameter("branchCode");
			inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
			transMsg = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, inManifestUniversalService.isValidBranchCode(branchCode));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: InManifestAction :: validateBranchCode() ::", e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: InManifestAction :: validateBranchCode() ::", e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (Exception e) {
			LOGGER.error("ERROR :: InManifestAction :: validateBranchCode() ::", e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(transMsg);
			out.flush();
			out.close();
		}
	}
}
