package com.ff.web.manifest.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestDoxPrintTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProductMapTO;
import com.ff.manifest.OutManifestDoxDetailsTO;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.constants.GlobalConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.form.OutManifestDoxForm;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.service.OutManifestDoxService;

/**
 * The Class OutManifestDoxAction.
 */
public class OutManifestDoxAction extends OutManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestDoxAction.class);

	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;

	/** The out manifest dox service. */
	private OutManifestDoxService outManifestDoxService;

	/** The manifest common service. */
	private ManifestCommonService manifestCommonService;

	/**
	 * View out manifest dox.
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
	public ActionForward viewOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("OutManifestDoxAction :: viewOutManifestDox() :: START------------>:::::::");
		try {
			OutManifestDoxTO outManifestDoxTO = null;
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO
					.setManifestType(OutManifestConstants.OUT_MANIFEST);
			manifestFactoryTO
					.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			outManifestDoxTO = (OutManifestDoxTO) getManifestBasicDtls(
					manifestFactoryTO, request);

			// set Regions
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			List<RegionTO> regionTOs = outManifestCommonService.getAllRegions();
			request.setAttribute(OutManifestConstants.REGION_TOS, regionTOs);
			outManifestDoxTO.setDestinationRegionList(regionTOs);
			// Setting process Code
			outManifestDoxTO
					.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
			outManifestDoxTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
			if (!StringUtil
					.isEmptyMap(outManifestDoxTO.getConfigurableParams())) {
				outManifestDoxTO.setMaxCNsAllowed(outManifestDoxTO
						.getConfigurableParams().get(
								ManifestConstants.OPKT_DOX_MAX_CNS_ALLOWED));
				outManifestDoxTO.setMaxComailsAllowed(outManifestDoxTO
						.getConfigurableParams()
						.get(ManifestConstants.OPKT_DOX_MAX_CO_MAILS_ALLOWED));

				request.setAttribute(
						OutManifestConstants.COMAIL_START_SERIES,
						outManifestDoxTO.getConfigurableParams().get(
								ManifestConstants.CO_MAIL_START_WITH));
			}
			// Allowed consignment(s) for RTO, Origin Misroute manifest(s)
			String allowedConsgManifestedType = null;
			if (StringUtils.equalsIgnoreCase(
					outManifestDoxTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				allowedConsgManifestedType = ManifestConstants.BRANCH_MISROUTE
						+ "," + ManifestConstants.ORIGIN_MISROUTE + ","
						+ ManifestConstants.MANIFEST_TYPE_POD;
			} else if (StringUtils.equalsIgnoreCase(
					outManifestDoxTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				allowedConsgManifestedType = ManifestConstants.ORIGIN_MISROUTE
						+ "," + ManifestConstants.MANIFEST_TYPE_RTO;

				// Set Manifest Type
				GlobalService globalService = (GlobalService) getBean(SpringConstants.GLOBAL_SERVICE);
				List<StockStandardTypeTO> manifestTypeList = globalService
						.getAllStockStandardType(GlobalConstants.MANIFEST_TYPE);
				if (CGCollectionUtils.isEmpty(manifestTypeList)) {
					throw new CGBusinessException(
							OutManifestConstants.ERROR_MANIFEST_TYPE);
				}
				request.setAttribute(OutManifestConstants.MANIFEST_TYPE_LIST,
						manifestTypeList);
			}
			outManifestDoxTO
					.setAllowedConsgManifestedType(allowedConsgManifestedType);
			ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			outManifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);

			/* set default user values i.e. created by, updated by user id */
			setUserDefaultValues(request, outManifestDoxTO);

			/** Setting request parameters */
			request.setAttribute(ManifestConstants.PARAM_CONSG_TYPE_DOX,
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);

			/** To set WM Connected flag */
			// outManifestDoxTO
			// .setIsWMConnected(isWeighingMachineConnected(request));

			((OutManifestDoxForm) form).setTo(outManifestDoxTO);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: viewOutManifestDox() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: viewOutManifestDox() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: viewOutManifestDox() :: ",
					e);
			getGenericException(request, e);
		}
		LOGGER.debug("OutManifestDoxAction :: viewOutManifestDox() :: END------------>:::::::");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	/**
	 * To set default created by, updated by user id
	 * 
	 * @param request
	 * @param outManifestDoxTO
	 */
	private void setUserDefaultValues(HttpServletRequest request,
			OutManifestDoxTO outManifestDoxTO) {
		/* get User Info from session attribute */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		/* setting created by, updated by user id */
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			outManifestDoxTO.setCreatedBy(userTO.getUserId());
			outManifestDoxTO.setUpdatedBy(userTO.getUserId());
		}
	}

	/**
	 * Validate consignment number.
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
	@SuppressWarnings({ "static-access", "unchecked" })
	public void validateConsignmentNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("OutManifestDoxAction :: validateConsignmentNumber() :: START------------>:::::::");
		OutManifestValidate cnValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			cnValidateTO = prepareValidateConsgInputs(request);
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			cnValidateTO = outManifestCommonService
					.validateConsignment(cnValidateTO);

			// To check for fresh manifest whether it is already used or not.
			String isManifestNoCheckReq = request
					.getParameter(OutManifestConstants.PARAM_IS_MANIFEST_NO_CHECK_REQ);
			if (!StringUtil.isStringEmpty(isManifestNoCheckReq)
					&& isManifestNoCheckReq
							.equalsIgnoreCase(CommonConstants.YES)) {
				manifestCommonService = getManifestCommonService();
				boolean isValidCnOrManifest = manifestCommonService
						.isValidateScanedManifestNo(cnValidateTO);
				if (!isValidCnOrManifest) {
					// Throw exception - Not allow to scan consignment in grid
					throw new CGBusinessException(
							ManifestErrorCodesConstants.SCANNED_MANIFEST_NO_NOT_VALID);
				}
			}
			/*
			 * In manifested check not allowed for branch - only booked CN
			 * allowed from branch for out manifest
			 */
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			if (loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE)
					&& cnValidateTO.getIsConsInManifestd().equalsIgnoreCase(
							CommonConstants.YES)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_NOTBOOKED_IN_OPERATING_OFFICE);
			}

			if ((cnValidateTO.getIsBulkBookedCN())
					.equalsIgnoreCase(CommonConstants.YES)
					&& (!StringUtil.isNull(cnValidateTO
							.getIsCNProcessedFromPickup()))
					&& (StringUtils.equalsIgnoreCase(
							cnValidateTO.getIsCNProcessedFromPickup(),
							CommonConstants.NO))) {
				/* Calculate Rates */
				ConsignmentTO cnTO = cnValidateTO.getConsgTO();
				String cnSeries = CommonConstants.EMPTY_STRING;
				Character cnSeriesChar = cnTO.getConsgNo().substring(4, 5)
						.toCharArray()[0];
				if (Character.isDigit(cnSeriesChar)) {
					cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
				} else {
					cnSeries = cnSeriesChar.toString();
				}
				ProductTO productTO = outManifestCommonService
						.getProductByConsgSeries(cnSeries);
				cnTO.setProductTO(productTO);
				cnTO.setBookingDate(cnTO.getCreatedDate());

				ConsignmentRateCalculationOutputTO cnRateCalcOutputTO = outManifestCommonService
						.calculateRateForConsignment(cnValidateTO.getConsgTO());
				if (!StringUtil.isNull(cnRateCalcOutputTO)) {
					rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
							.getAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
					if (!CGCollectionUtils.isEmpty(rateCompnents)
							&& rateCompnents.size() > 0)
						rateCompnents.put(cnValidateTO.getConsgTO()
								.getConsgNo(), cnRateCalcOutputTO);
					else {
						rateCompnents = new HashMap<String, ConsignmentRateCalculationOutputTO>();
						rateCompnents.put(cnValidateTO.getConsgTO()
								.getConsgNo(), cnRateCalcOutputTO);
					}
					session.setAttribute(
							ManifestConstants.MANIFEST_CONSG_RATE_DTLS,
							rateCompnents);
				}
			}
			// check for InManifest
			if (!(cnValidateTO.getIsConsInManifestd().equalsIgnoreCase(
					CommonConstants.YES) && cnValidateTO.getIsCNBooked()
					.equalsIgnoreCase(CommonConstants.NO))) {
				cnValidationJSON = getConsignmentDetails(cnValidateTO);
			} else {
				cnValidationJSON = serializer.toJSON(cnValidateTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: validateConsignmentNumber() ::",
					e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: validateConsignmentNumber() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: validateConsignmentNumber() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestDoxAction::validateConsignmentNumber()::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	private String getConsignmentDetails(OutManifestValidate cnValidateTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxAction :: getConsignmentDetails() :: START------------>:::::::");
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		ManifestFactoryTO manifestFactoryTO = null;
		OutManifestDoxDetailsTO outManifestDoxDtlTO = null;

		if (StringUtils.equalsIgnoreCase(
				cnValidateTO.getIsCNProcessedFromPickup(), CommonConstants.YES)) {
			manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setPickupCN(Boolean.TRUE);
		} else {
			manifestFactoryTO = ManifestUtil.prepareFactoryInputs(
					OutManifestConstants.OUT_MANIFEST,
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		}
		manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());
		outManifestDoxService = (OutManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_DOX_SERVICE);
		outManifestDoxDtlTO = outManifestDoxService.getConsignmentDtls(
				manifestFactoryTO, cnValidateTO);
		outManifestDoxDtlTO.setIsPickupCN(cnValidateTO
				.getIsCNProcessedFromPickup());
		if (!StringUtil.isNull(outManifestDoxDtlTO)) {
			cnValidationJSON = serializer.toJSON(outManifestDoxDtlTO)
					.toString();
		}
		LOGGER.debug("OutManifestDoxAction :: getConsignmentDetails() :: END------------>:::::::");
		return cnValidationJSON;
	}

	/**
	 * @param request
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws NumberFormatException
	 */
	private OutManifestValidate prepareValidateConsgInputs(
			HttpServletRequest request) throws NumberFormatException,
			CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxAction :: prepareValidateConsgInputs() :: START------------>:::::::");
		OutManifestValidate cnValidateTO;
		OfficeTO officeTO;
		CityTO cityTO;
		cnValidateTO = new OutManifestValidate();
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.CONSIGNMENT_NUMBER))) {
			cnValidateTO.setConsgNumber(request
					.getParameter(OutManifestConstants.CONSIGNMENT_NUMBER));
		}
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.OFFICE_ID))) {
			OfficeTO offTO = outManifestCommonService.getOfficeDetails(Integer
					.parseInt(request
							.getParameter(OutManifestConstants.OFFICE_ID)));
			cnValidateTO.setDestOffice(offTO);
		}
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.CITY_ID))) {
			Integer destCityId = Integer.parseInt(request
					.getParameter(OutManifestConstants.CITY_ID));
			cityTO = new CityTO();
			cityTO.setCityId(destCityId);
			cnValidateTO.setDestCityTO(cityTO);

			// To validate for TRANSHIPMENT
			if (!StringUtil.isStringEmpty(request
					.getParameter("ogmManifestType"))
					&& request
							.getParameter("ogmManifestType")
							.equalsIgnoreCase(
									OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
				List<Integer> transCityIds = outManifestCommonService
						.getServicedCityByTransshipmentCity(destCityId);
				transCityIds.add(destCityId);
				cnValidateTO.setTranshipmentCityIds(transCityIds);
				cnValidateTO.setChkTransCityPincodeServ(CommonConstants.YES);
			}
		}
		cnValidateTO
				.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(Integer.parseInt(request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID)));
			cnValidateTO.setOriginOffice(officeTO);
		}
		if (!StringUtil.isStringEmpty(request
				.getParameter("allowedConsgManifestedType"))) {
			cnValidateTO.setAllowedConsgManifestedType(request.getParameter(
					"allowedConsgManifestedType").split(CommonConstants.COMMA));
		} else {
			cnValidateTO
					.setAllowedConsgManifestedType(new String[] { CommonConstants.EMPTY_STRING });
		}
		if (StringUtils.isNotEmpty(request.getParameter("isPincodeServChk"))) {
			cnValidateTO.setIsPincodeServChkReq(request
					.getParameter("isPincodeServChk"));
		}
		if (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
			cnValidateTO.setManifestNumber(request.getParameter("manifestNo"));
		}
		ManifestProductMapTO manifestProductMapTO = new ManifestProductMapTO();
		manifestProductMapTO.setScannedProduct(Character.toString(request
				.getParameter(OutManifestConstants.CONSIGNMENT_NUMBER)
				.charAt(4)));
		manifestProductMapTO
				.setManifestProcess(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		manifestProductMapTO
				.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		manifestProductMapTO.setManifestOpenType(request
				.getParameter(OutManifestConstants.MANIFEST_OPEN_TYPE));
		manifestProductMapTO.setLoggedInOfficeType(request
				.getParameter(OutManifestConstants.OFFICE_TYPE));
		cnValidateTO.setManifestProductMapTO(manifestProductMapTO);
		ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
		typeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		cnValidateTO.setConsignmentTypeTO(typeTO);
		cnValidateTO
				.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		LOGGER.debug("OutManifestDoxAction :: prepareValidateConsgInputs() :: END------------>:::::::");
		return cnValidateTO;
	}

	/**
	 * Search manifest details.
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
	public void searchManifestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("OutManifestDoxAction :: searchManifestDetails() :: START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		String errorMsg = null;
		OutManifestDoxTO outManifestDoxTO = null;
		try {
			out = response.getWriter();
			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			manifestNo = manifestNo.toUpperCase();
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
				manifestTO
						.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				outManifestDoxService = (OutManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_DOX_SERVICE);
				outManifestDoxTO = outManifestDoxService
						.searchManifestDtls(manifestTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: searchManifestDetails() ::",
					e);
			/*
			 * errorMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
			 * getBusinessErrorFromWrapper(request, e));
			 */
			errorMsg = getBusinessErrorFromWrapper(request, e);
			outManifestDoxTO = new OutManifestDoxTO();
			outManifestDoxTO.setErrorMsg(errorMsg);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: searchManifestDetails() ::",
					e);
			errorMsg = getSystemExceptionMessage(request, e);
			// errorMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
			// exception);
			outManifestDoxTO = new OutManifestDoxTO();
			outManifestDoxTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: searchManifestDetails() ::",
					e);
			errorMsg = getGenericExceptionMessage(request, e);
			// errorMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
			// exception);
			outManifestDoxTO = new OutManifestDoxTO();
			outManifestDoxTO.setErrorMsg(errorMsg);
		} finally {
			jsonResult = createJsonObject(outManifestDoxTO);
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestDoxAction :: searchManifestDetails() :: END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	private String createJsonObject(OutManifestDoxTO outManifestDoxTO) {
		LOGGER.debug("OutManifestDoxAction :: createJsonObject() :: START------------>:::::::");
		StringBuilder jsonResult = null;
		if (!StringUtil.isNull(outManifestDoxTO)) {
			jsonResult = new StringBuilder();
			String doxToJson = serializer.toJSON(outManifestDoxTO).toString();
			String detailToJson = null;
			jsonResult = appendListName(jsonResult, doxToJson, "doxTO");
			if (!StringUtil.isEmptyList(outManifestDoxTO
					.getOutManifestDoxDetailTOs())) {
				// Collections.sort(outManifestDoxTO.getOutManifestDoxDetailTOs());
				detailToJson = serializer.toJSON(
						outManifestDoxTO.getOutManifestDoxDetailTOs())
						.toString();
			}
			jsonResult = appendListName(jsonResult, detailToJson, "detailTO");
		}
		LOGGER.debug("OutManifestDoxAction :: createJsonObject() :: END------------>:::::::");
		return jsonResult.toString();
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
	@SuppressWarnings("unchecked")
	public void saveOrUpdateOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder loggerStr = new StringBuilder();
		loggerStr
				.append("OutManifestDoxAction :: saveOrUpdateOutManifestDox() :: START------------>:::::::");
		OutManifestDoxForm outMnfstDoxForm = null;
		OutManifestDoxTO outmanifestDoxTO = null;
		PrintWriter out = null;
		String transMsg = CommonConstants.EMPTY_STRING;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);

			outMnfstDoxForm = (OutManifestDoxForm) form;
			outmanifestDoxTO = (OutManifestDoxTO) outMnfstDoxForm.getTo();

			outmanifestDoxTO.setManifestNo(outmanifestDoxTO.getManifestNo()
					.toUpperCase());
			loggerStr.append(" MANIFEST NO :: ");
			loggerStr.append(outmanifestDoxTO.getManifestNo());
			LOGGER.debug(loggerStr.toString());
			outmanifestDoxTO.setManifestType(OutManifestConstants.OUT_MANIFEST);
			outmanifestDoxTO
					.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
			ConsignmentTypeTO consgType = outmanifestDoxTO
					.getConsignmentTypeTO();
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			outmanifestDoxTO.setConsignmentTypeTO(consgType);
			setOutmanifestDoxTO(outmanifestDoxTO);

			if (!StringUtil.isNull(outmanifestDoxTO)) {
				if (StringUtil.isEmpty(outmanifestDoxTO.getConsgNos())
						&& StringUtil.isEmpty(outmanifestDoxTO.getComailNos())) {
					throw new CGBusinessException("ERROROMD001");
				}
				rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
						.getAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
				LOGGER.debug("OutManifestDoxAction :: saveOrUpdateOutManifestDox() ::available rate components are "
						+ rateCompnents);
				outmanifestDoxTO.setRateCompnents(rateCompnents);

				outManifestDoxService = (OutManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_DOX_SERVICE);
				transMsg = outManifestDoxService
						.saveOrUpdateOutManifestDox(outmanifestDoxTO);
				session.removeAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
				/** Call Two way write */
				if (outmanifestDoxTO.getManifestStatus().equalsIgnoreCase(
						OutManifestConstants.CLOSE)) {
					twoWayWrite(outmanifestDoxTO);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: OutManifestDoxAction :: saveOrUpdateOutManifestDox() ::",
					e);
			String message = e.getMessage();
			String[] messages = message.split("#");
			if (messages != null && messages.length > 1) {
				transMsg = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getBusinessErrorFromWrapper(request,
								new CGBusinessException(messages[0])));
				transMsg = transMsg.substring(0, transMsg.length() - 2) + " : "
						+ messages[1] + "\"}";
				LOGGER.debug("OutManifestDoxAction :: saveOrUpdateOutManifestDox() :: ..CGBusinessException :: ERROR Message : "
						+ transMsg);
			} else {
				transMsg = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getBusinessErrorFromWrapper(request, e));
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In :: OutManifestDoxAction :: saveOrUpdateOutManifestDox() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In :: OutManifestDoxAction :: saveOrUpdateOutManifestDox() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(transMsg);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestDoxAction :: saveOrUpdateOutManifestDox() :: END------------>::::::: MANIFEST NO :: "
				+ outmanifestDoxTO.getManifestNo());
	}

	private void setOutmanifestDoxTO(OutManifestDoxTO outmanifestDoxTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("OutManifestDoxAction :: setOutmanifestDoxTO() :: START ::------------>:::::::");
		OfficeTO loginOfficeTO = null;
		Integer operatingLevel = outmanifestDoxTO.getOperatingLevel();
		// If manifest is creating first time, set the below values
		if (StringUtil.isEmptyInteger(outmanifestDoxTO.getManifestId())) {
			outManifestUniversalService = (OutManifestUniversalService) getBean(SpringConstants.OUT_MANIFEST_UNIVERSAL_SERVICE);
			// Setting Login Office details
			loginOfficeTO = new OfficeTO();
			loginOfficeTO.setOfficeId(outmanifestDoxTO.getLoginOfficeId());
			if (StringUtils.isNotEmpty(outmanifestDoxTO.getOfficeCode())) {
				loginOfficeTO.setOfficeCode(outmanifestDoxTO.getOfficeCode());
			}
			// Setting manifested product series
			if (StringUtils.isNotEmpty(outmanifestDoxTO
					.getManifestedProductSeries())) {
				ProductTO productTO = outManifestCommonService
						.getProductByConsgSeries(outmanifestDoxTO
								.getManifestedProductSeries());
				outmanifestDoxTO.setProduct(productTO);
			}
			// calculating operating level
			operatingLevel = outManifestUniversalService.calcOperatingLevel(
					outmanifestDoxTO, loginOfficeTO);
			outmanifestDoxTO.setOperatingLevel(operatingLevel);

			// Getting Consignment Type details based on
			List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
					.getConsignmentTypes(outmanifestDoxTO
							.getConsignmentTypeTO());
			// Setting Consignment type id
			if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
				ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs
						.get(0);
				outmanifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
			}
			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO
					.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
			processTO = outManifestUniversalService.getProcess(processTO);
			outmanifestDoxTO.setProcessId(processTO.getProcessId());
			outmanifestDoxTO.setProcessCode(processTO.getProcessCode());

			// Setting process number
			String processNumber = outManifestCommonService
					.createProcessNumber(processTO, loginOfficeTO);
			outmanifestDoxTO.setProcessNo(processNumber);
		}

		// The below attributes will always get updated
		if (outmanifestDoxTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
			outmanifestDoxTO.setIsMulDestination(CommonConstants.YES);
		}
		/**
		 * changes related to out manifest destination office ids = origin city
		 * All hub offices + destination city all hub offices + selected
		 * destination office
		 **/

		List<Integer> destHubList = null;
		List<OfficeTO> destOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						outmanifestDoxTO.getDestinationCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			destHubList = new ArrayList<>();
			for (OfficeTO officeTO1 : destOfficeTOs) {
				destHubList.add(officeTO1.getOfficeId());
			}
			outmanifestDoxTO.setDestHubOffList(destHubList);
		}

		// If the Operating level is less than = 10 i.e. the origin Branch
		// then only add the origin hubs in out manifest destinations
		if (operatingLevel <= 10) {
			List<Integer> originHubList = null;
			List<OfficeTO> orgOfficeTOs = outManifestCommonService
					.getAllOfficesByCityAndOfficeTypeCode(
							outmanifestDoxTO.getLoginCityId(),
							CommonConstants.OFF_TYPE_HUB_OFFICE);
			if (!StringUtil.isEmptyList(destOfficeTOs)) {
				originHubList = new ArrayList<>();
				for (OfficeTO officeTO2 : orgOfficeTOs) {
					originHubList.add(officeTO2.getOfficeId());
				}
				outmanifestDoxTO.setOriginHubOffList(originHubList);
			}
		}
		LOGGER.trace("OutManifestDoxAction :: setOutmanifestDoxTO() :: END------------>:::::::");
	}

	/**
	 * Append list name.
	 * 
	 * @param stringBuilder
	 *            the string builder
	 * @param ajaxResponse
	 *            the ajax response
	 * @param listName
	 *            the list name
	 * @return the string builder
	 */
	private StringBuilder appendListName(StringBuilder stringBuilder,
			String ajaxResponse, String listName) {
		LOGGER.trace("OutManifestDoxAction :: appendListName() :: START------------>:::::::");
		stringBuilder
				.append(CommonConstants.TILD)
				.append(CommonConstants.OPENING_CURLY_BRACE)
				.append(CommonConstants.OPENING_INNER_QOUTES + listName
						+ CommonConstants.CLOSING_INNER_QOUTES)
				.append(CommonConstants.CHARACTER_COLON).append(ajaxResponse)
				.append(CommonConstants.CLOSING_CURLY_BRACE);
		LOGGER.trace("OutManifestDoxAction :: appendListName() :: END------------>:::::::");
		return stringBuilder;
	}

	/**
	 * Gets the in manifested consignment details.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the in manifested consignment details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings("static-access")
	public void getInManifestedConsignmentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestDoxAction :: getInManifestedConsignmentDetails() :: START------------>:::::::");
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		ManifestFactoryTO manifestFactoryTO = null;
		OutManifestDoxDetailsTO outManifestDoxDtlTO = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			serializer = CGJasonConverter.getJsonObject();
			String consignmentNo = request
					.getParameter(OutManifestConstants.CONSIGNMENT_NO);
			if (StringUtils.isNotEmpty(consignmentNo)) {
				manifestFactoryTO = new ManifestFactoryTO();
				manifestFactoryTO.setConsgNumber(consignmentNo);
				manifestFactoryTO
						.setManifestType(OutManifestConstants.OUT_MANIFEST);
				manifestFactoryTO
						.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			}
			outManifestDoxService = (OutManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_DOX_SERVICE);
			outManifestDoxDtlTO = outManifestDoxService
					.getInManifestdConsignmentDtls(manifestFactoryTO);
			if (!StringUtil.isNull(outManifestDoxDtlTO)) {
				cnValidationJSON = serializer.toJSON(outManifestDoxDtlTO)
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: getInManifestedConsignmentDetails() ::",
					e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: getInManifestedConsignmentDetails() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: getInManifestedConsignmentDetails() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			cnValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestDoxAction :: getInManifestedConsignmentDetails() :: END------------>:::::::");
	}

	/**
	 * @Desc print OutManifest dox details from database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward printOutManifestDoxDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("OutManifestDoxAction :: printOutManifestDoxDtls() :: START------------>:::::::");
		OutManifestDoxTO outManifestDoxTO = null;
		try {
			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
				manifestTO
						.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				outManifestDoxService = (OutManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_DOX_SERVICE);
				outManifestDoxTO = outManifestDoxService
						.searchManifestDtls(manifestTO);

				List<ManifestDoxPrintTO> mainList = new ArrayList<ManifestDoxPrintTO>();
				int rowsPerColm = 45;
				List<List<OutManifestDoxDetailsTO>> ogmLists = createLists(
						rowsPerColm,
						outManifestDoxTO.getOutManifestDoxDetailTOs());

				int sz = ogmLists.size();
				for (int i = 0; i < sz; i = i + 2) {
					ManifestDoxPrintTO dtlsTO = new ManifestDoxPrintTO();
					List<OutManifestDoxDetailsTO> firstCol = new ArrayList<OutManifestDoxDetailsTO>();
					List<OutManifestDoxDetailsTO> secondCol = new ArrayList<OutManifestDoxDetailsTO>();
					firstCol.addAll(ogmLists.get(i));
					int j = i + 1;
					if (j < sz) {
						secondCol.addAll(ogmLists.get(j));
						dtlsTO.setRightOGMList(secondCol);
					}
					dtlsTO.setLeftOGMList(firstCol);
					mainList.add(dtlsTO);
				}
				request.setAttribute("outManifestDoxTO", outManifestDoxTO);
				request.setAttribute("mainList", mainList);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: printOutManifestDoxDtls() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: printOutManifestDoxDtls() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestDoxAction :: printOutManifestDoxDtls() :: ",
					e);
			getGenericException(request, e);
		}
		LOGGER.debug("OutManifestDoxAction :: printOutManifestDoxDtls() :: END------------>:::::::");
		return mapping.findForward(ManifestConstants.URL_PRINT_OUTMANIFEST_DOX);
	}

	public List<List<OutManifestDoxDetailsTO>> createLists(int chunkSize,
			List<OutManifestDoxDetailsTO> ogmList) {
		List<List<OutManifestDoxDetailsTO>> lists = new ArrayList<List<OutManifestDoxDetailsTO>>();
		int totCol, totsize, i, j, k, m, n;

		totsize = ogmList.size();
		totCol = totsize / chunkSize;

		for (i = 0; i < totCol; i++) {
			m = i * chunkSize;
			n = (i + 1) * chunkSize;
			List<OutManifestDoxDetailsTO> chunk = new ArrayList<OutManifestDoxDetailsTO>();
			for (j = m; j < n; j++) {
				OutManifestDoxDetailsTO obj = ogmList.get(j);
				obj.setSrNo((j + 1));
				chunk.add(obj);
			}
			lists.add(chunk);
		}
		List<OutManifestDoxDetailsTO> chunk1 = new ArrayList<OutManifestDoxDetailsTO>();
		for (k = (totCol * chunkSize); k < totsize; k++) {
			OutManifestDoxDetailsTO obj = ogmList.get(k);
			obj.setSrNo((k + 1));
			chunk1.add(obj);
		}
		if (!chunk1.isEmpty()) {
			lists.add(chunk1);
		}
		return lists;
	}

	/**
	 * To get Manifest Common Service
	 * 
	 * @return manifestCommonService
	 */
	private ManifestCommonService getManifestCommonService() {
		if (manifestCommonService == null) {
			manifestCommonService = (ManifestCommonService) getBean("manifestCommonService");
		}
		return manifestCommonService;
	}

}
