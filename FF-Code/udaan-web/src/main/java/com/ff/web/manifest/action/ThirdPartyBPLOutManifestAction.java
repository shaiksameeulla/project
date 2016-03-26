package com.ff.web.manifest.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.LoadMovementVendorTO;
import com.ff.geography.CityTO;
import com.ff.manifest.LoadLotTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyBPLDetailsTO;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.constants.GlobalConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.form.ThirdPartyBPLOutManifestForm;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.service.ThirdPartyBPLService;

public class ThirdPartyBPLOutManifestAction extends OutManifestAction {

	/** The LOGGER */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyBPLOutManifestAction.class);

	/** The outManifestCommonService. */
	private OutManifestCommonService outManifestCommonService;

	/** The globalService. */
	private GlobalService globalService;

	/** thirdPartyBPLservice. */
	private ThirdPartyBPLService thirdPartyBPLservice;

	/**
	 * To get Third Party BPL Service.
	 * 
	 * @return thirdPartyBPLservice
	 */
	private ThirdPartyBPLService getThirdPartyBPLservice() {
		if (StringUtil.isNull(thirdPartyBPLservice)) {
			thirdPartyBPLservice = (ThirdPartyBPLService) getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_BPL);
		}
		return thirdPartyBPLservice;
	}

	/**
	 * To get Global Service.
	 * 
	 * @return globalService
	 */
	private GlobalService getGlobalService() {
		if (StringUtil.isNull(globalService)) {
			globalService = (GlobalService) getBean(GlobalConstants.GLOBAL_SERVICE);
		}
		return globalService;
	}

	/**
	 * To get Out Manifest Common Service.
	 * 
	 * @return outManifestCommonService
	 */
	private OutManifestCommonService getOutManifestCommonService() {
		if (StringUtil.isNull(outManifestCommonService)) {
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		}
		return outManifestCommonService;
	}

	/**
	 * To view third party BPL screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to the third party BPL view
	 */
	public ActionForward viewThirdPartyBPL(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: viewThirdPartyBPL() :: START");
		ActionMessage actionMessage = null;
		ThirdPartyBPLOutManifestTO thirdPartyBPLManifestTO = new ThirdPartyBPLOutManifestTO();
		try {
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO
					.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
			manifestFactoryTO
					.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			manifestFactoryTO
					.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
			thirdPartyBPLManifestTO = (ThirdPartyBPLOutManifestTO) getManifestBasicDtls(
					manifestFactoryTO, request);

			/* populating load number */
			outManifestCommonService = getOutManifestCommonService();
			List<LabelValueBean> loadNoList = new ArrayList<LabelValueBean>();
			List<LoadLotTO> loadNoTo = outManifestCommonService.getLoadNo();
			if (!CGCollectionUtils.isEmpty(loadNoTo)) {
				for (LoadLotTO loadTO : loadNoTo) {
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(loadTO.getLoadNo().toString());
					lvb.setValue(loadTO.getLoadLotId() + "");
					loadNoList.add(lvb);
				}
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.LOAD_NO_NOT_POPULATED);
			}
			thirdPartyBPLManifestTO.setLoadList(loadNoList);

			/* populating third party type */
			globalService = getGlobalService();
			List<LabelValueBean> thirdPartyTypeList = new ArrayList<LabelValueBean>();
			String typeName = GlobalConstants.THIRD_PARTY_TYPE;
			List<StockStandardTypeTO> thirdPartyTo = globalService
					.getAllStockStandardType(typeName);
			if (!CGCollectionUtils.isEmpty(thirdPartyTo)) {
				for (StockStandardTypeTO thirdPartyTO : thirdPartyTo) {
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(thirdPartyTO.getDescription());
					lvb.setValue(thirdPartyTO.getStdTypeCode());
					thirdPartyTypeList.add(lvb);
				}
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.THIRD_PARTY_TYPE_NOT_POPULATED);
			}
			thirdPartyBPLManifestTO.setThirdPartyTypeList(thirdPartyTypeList);

			thirdPartyBPLManifestTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
			if (!StringUtil.isEmptyMap(thirdPartyBPLManifestTO
					.getConfigurableParams())) {
				thirdPartyBPLManifestTO
						.setMaxCNsAllowed(thirdPartyBPLManifestTO
								.getConfigurableParams()
								.get(ManifestConstants.TP_BPL_MAX_CNS_ALLOWED));
				thirdPartyBPLManifestTO
						.setMaxWeightAllowed((thirdPartyBPLManifestTO
								.getConfigurableParams()
								.get(ManifestConstants.TP_BPL_MAX_WEIGHT_ALLOWED)));
				thirdPartyBPLManifestTO
						.setMaxTolerenceAllowed(thirdPartyBPLManifestTO
								.getConfigurableParams()
								.get(ManifestConstants.TP_BPL_MAX_TOLERANCE_ALLOWED));
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONFIG_PARAM_NOT_SET_FOR_TPBP);
			}

			request.setAttribute(OutManifestConstants.PROCESS_CODE,
					OutManifestConstants.PROCESS_CODE_TPBP);
			request.setAttribute(OutManifestConstants.SERIES_TYPE,
					thirdPartyBPLManifestTO.getSeriesType());
			request.setAttribute(OutManifestConstants.LOGIN_OFFICE_ID,
					thirdPartyBPLManifestTO.getLoginOfficeId());
			request.setAttribute(OutManifestConstants.REGION_ID,
					thirdPartyBPLManifestTO.getRegionId());
			request.setAttribute(OutManifestConstants.IS_THIRDPARTY_SCREEN,
					CommonConstants.YES);

			/* set default user values i.e. created by, updated by user id */
			setUserDefaultValues(request, thirdPartyBPLManifestTO);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: viewThirdPartyBPL() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: viewThirdPartyBPL() :: ",
					e);
			actionMessage = new ActionMessage(
					ManifestErrorCodesConstants.DB_ERR_PLS_TRY_AGAIN);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: viewThirdPartyBPL() :: ",
					e);
			getGenericException(request, e);
		} finally {
			resetToken(request);
			prepareActionMessage(request, actionMessage);
		}
		((ThirdPartyBPLOutManifestForm) form).setTo(thirdPartyBPLManifestTO);
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: viewThirdPartyBPL() :: END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	/**
	 * To set default created by, updated by user id
	 * 
	 * @param request
	 * @param thirdPartyBPLManifestTO
	 */
	private void setUserDefaultValues(HttpServletRequest request,
			ThirdPartyBPLOutManifestTO thirdPartyBPLManifestTO) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: setUserDefaultValues() :: START");
		/* get User Info from session attribute */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		/* setting created by, updated by user id */
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			thirdPartyBPLManifestTO.setCreatedBy(userTO.getUserId());
			thirdPartyBPLManifestTO.setUpdatedBy(userTO.getUserId());
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: setUserDefaultValues() :: END");
	}

	/**
	 * To get Third Party Name
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getThirdPartyName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: getThirdPartyName() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String partyType = CommonConstants.EMPTY_STRING;
		List<LoadMovementVendorTO> vendorTO = null;
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = null;

		try {
			out = response.getWriter();
			String partyID = request
					.getParameter(UdaanCommonConstants.THIRD_PARTY_ID);
			outManifestCommonService = getOutManifestCommonService();

			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);

			if (!StringUtil.isNull(partyID)
					&& !StringUtil.isStringEmpty(partyID)) {
				if (partyID.equalsIgnoreCase(ManifestConstants.PARTY_TYPE_BA)) {
					partyType = ManifestConstants.PARTY_TYPE_BA;
				} else if (partyID
						.equalsIgnoreCase(ManifestConstants.PARTY_TYPE_CC)) {
					partyType = ManifestConstants.PARTY_TYPE_CC;
				} else if (partyID
						.equalsIgnoreCase(ManifestConstants.PARTY_TYPE_FR)) {
					partyType = ManifestConstants.PARTY_TYPE_FR;
				}
				vendorTO = outManifestCommonService.getPartyNames(partyType,
						userInfoTO.getOfficeTo().getOfficeId());
				if (!CGCollectionUtils.isEmpty(vendorTO)) {
					jsonResult = serializer.toJSON(vendorTO).toString();
				} else {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.THIRD_PARTY_NAME_NOT_POPULATED);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: getThirdPartyName() :: END");
	}

	/**
	 * To get consignment details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction::getConsignmentDtls::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = "";
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		try {
			out = response.getWriter();
			thirdPartyBPLservice = getThirdPartyBPLservice();

			/* prepare consignment validate TO */
			cnValidateTO = prepareCNValidateTO(request);

			/* prepare manifest factory TO */
			manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());
			manifestFactoryTO
					.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
			manifestFactoryTO
					.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);

			/* validating consignment number */
			outManifestCommonService = getOutManifestCommonService();
			cnValidateTO
					.setManifestProcessCode(OutManifestConstants.PROCESS_CODE_TPBP);
			cnValidateTO = outManifestCommonService
					.validateConsignment(cnValidateTO);

			/*
			 * To check whether parent CN should allow or not, if child is in
			 * delivery process. (Added by HIMAL - 16-04-2014)
			 */
			boolean validCNFlag = thirdPartyBPLservice
					.isConsignmentExistInDRS(cnValidateTO.getConsgNumber());
			if (validCNFlag) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_ALREADY_IN_DRS_PROCESS);
			}

			/* To gets the consignment details */
			thirdPartyBPLDetailsTO = thirdPartyBPLservice
					.getConsignmentDtls(cnValidateTO);
			if (thirdPartyBPLDetailsTO != null) {
				jsonResult = serializer.toJSON(thirdPartyBPLDetailsTO)
						.toString();
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG, "No Details found");
			}
			// }
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getConsignmentDtls() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getConsignmentDtls() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getConsignmentDtls() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);

		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction ::getConsignmentDtls::END------------>:::::::");
	}

	/**
	 * To prepare CN validate TO
	 * 
	 * @param request
	 * @return cnValidateTO
	 */
	private OutManifestValidate prepareCNValidateTO(HttpServletRequest request) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: prepareCNValidateTO() :: START");
		OfficeTO officeTO = null;
		OutManifestValidate cnValidateTO = new OutManifestValidate();

		/** Setting request parameters */
		/* Consignment Number */
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.CONSIGNMENT_NO))) {
			cnValidateTO.setConsgNumber(request
					.getParameter(OutManifestConstants.CONSIGNMENT_NO));
		}
		/* Manifest Direction */
		if (StringUtils.isNotEmpty(request.getParameter("manifestDirection"))) {
			cnValidateTO.setManifestDirection(request
					.getParameter("manifestDirection"));
		}
		/* Allowed Consg Manifested Type */
		if (!StringUtil.isStringEmpty(request
				.getParameter("allowedConsgManifestedType"))) {
			cnValidateTO.setAllowedConsgManifestedType(request.getParameter(
					"allowedConsgManifestedType").split(CommonConstants.COMMA));
		} else {
			cnValidateTO
					.setAllowedConsgManifestedType(new String[] { CommonConstants.EMPTY_STRING });
		}
		/* Logged In Office */
		if (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(Integer.parseInt(request
					.getParameter("loginOfficeId")));
			cnValidateTO.setOriginOffice(officeTO);
		}
		/* office id */
		if (StringUtils.isNotEmpty(request.getParameter("officeId"))) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(Integer.parseInt(request
					.getParameter("officeId")));
			cnValidateTO.setDestOffice(officeTO);
		}
		/* Manifest No. */
		if (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
			cnValidateTO.setManifestNumber(request.getParameter("manifestNo"));
		}

		/* Destination City */
		if (StringUtils.isNotEmpty(request.getParameter("loginCityId"))) {
			CityTO destCityTO = new CityTO();
			Integer destCityId = Integer.parseInt(request
					.getParameter("loginCityId"));
			destCityTO.setCityId(destCityId);
			cnValidateTO.setDestCityTO(destCityTO);
		}

		/** Setting consignment type as PPX */
		ConsignmentTypeTO consTypeTO = new ConsignmentTypeTO();
		consTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		cnValidateTO.setConsignmentTypeTO(consTypeTO);
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: prepareCNValidateTO() :: START");
		return cnValidateTO;
	}

	/**
	 * To get Manifest Dtls in grid
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getManifestDtlsByProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction::getManifestDtlsByProcess::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		try {
			out = response.getWriter();
			ManifestInputs manifestInputTO = new ManifestInputs();
			/* The manifest noumber in grid. */
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.MANIFEST_NO))) {
				manifestInputTO.setManifestNumber(request
						.getParameter(OutManifestConstants.MANIFEST_NO));
			}
			/* The login office id. */
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) {
				Integer loginOffId = Integer.parseInt(request
						.getParameter(OutManifestConstants.LOGIN_OFFICE_ID));
				manifestInputTO.setLoginOfficeId(loginOffId);
			}
			/* The header manifest number. */
			if (StringUtils
					.isNotEmpty(request
							.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO))) {
				manifestInputTO
						.setHeaderManifestNo(request
								.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO));
			}
			// Third Party Name
			if (!StringUtil.isStringEmpty(request
					.getParameter(OutManifestConstants.PARAM_THIRD_PARTY_NAME))) {
				manifestInputTO
						.setThirdPartyName(Integer.parseInt(request
								.getParameter(OutManifestConstants.PARAM_THIRD_PARTY_NAME)));
			}
			// Third Party Type
			if (!StringUtil.isStringEmpty(request
					.getParameter(OutManifestConstants.PARAM_THIRD_PARTY_TYPE))) {
				manifestInputTO
						.setThirdPartyType(request
								.getParameter(OutManifestConstants.PARAM_THIRD_PARTY_TYPE));
			}
			thirdPartyBPLservice = getThirdPartyBPLservice();
			thirdPartyBPLDetailsTO = thirdPartyBPLservice
					.getThirdPartyManifestDtls(manifestInputTO);
			jsonResult = serializer.toJSON(thirdPartyBPLDetailsTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getManifestDtlsByProcess() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getManifestDtlsByProcess() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: getManifestDtlsByProcess() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);

		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction ::getManifestDtlsByProcess::END------------>:::::::");
	}

	/**
	 * To save or update third party BPL details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateOutManifestTPBP(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ThirdPartyBPLOutManifestForm thirdPartyBPLForm = (ThirdPartyBPLOutManifestForm) form;
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO = (ThirdPartyBPLOutManifestTO) thirdPartyBPLForm
					.getTo();

			ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			outManifestCommonService = getOutManifestCommonService();
			List<ConsignmentTypeTO> consignmentTypeTOs = outManifestCommonService
					.getConsignmentTypes(consignmentTypeTO);
			if (!CGCollectionUtils.isEmpty(consignmentTypeTOs)) {
				consignmentTypeTO = consignmentTypeTOs.get(0);
			}
			thirdPartyBPLTO.setConsignmentTypeTO(consignmentTypeTO);
			thirdPartyBPLTO
					.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
			setProcessTO(thirdPartyBPLTO);
			thirdPartyBPLservice = getThirdPartyBPLservice();
			jsonResult = thirdPartyBPLservice
					.saveOrUpdateOutManifestTPBP(thirdPartyBPLTO);

			/** Propagating action message as per result */
			if (jsonResult.equals(OutManifestConstants.MANIFEST_STATUS_OPEN)) {
				thirdPartyBPLTO
						.setSuccessMessage(getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
								null));
			} else if (jsonResult
					.equals(OutManifestConstants.MANIFEST_STATUS_CLOSE)) {
				/** Call Two way write */
				twoWayWrite(thirdPartyBPLTO);
				if (StringUtil.isEmptyInteger(thirdPartyBPLTO.getManifestId())) {
					thirdPartyBPLTO
							.setSuccessMessage(getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.MANIFEST_SAVED_CLOSED_SUCCESSFULLY,
									null));
				} else {
					thirdPartyBPLTO
							.setSuccessMessage(getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.MANIFEST_CLOSED_SUCCESSFULLY,
									null));
				}
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
			}
			jsonResult = JSONSerializer.toJSON(thirdPartyBPLTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: saveOrUpdateOutManifestTPBP() :: END");
	}

	/**
	 * To set ProcessTO
	 * 
	 * @param thirdPartyBPLTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void setProcessTO(ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: setProcessTO() :: START");
		ProcessTO processTO = new ProcessTO();
		processTO
				.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		processTO = outManifestCommonService.getProcess(processTO);
		thirdPartyBPLTO.setProcessTO(processTO);
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: setProcessTO() :: END");
	}

	/**
	 * To search third party BPL details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void searchManifestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: searchManifestDetails() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				thirdPartyBPLservice = getThirdPartyBPLservice();
				ThirdPartyBPLOutManifestTO thirdPartyBPLTO = thirdPartyBPLservice
						.searchManifestDtls(manifestTO);
				jsonResult = serializer.toJSON(thirdPartyBPLTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception happened in ThirdPartyBPLOutManifestAction :: searchManifestDetails() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception happened in ThirdPartyBPLOutManifestAction :: searchManifestDetails() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception happened in ThirdPartyBPLOutManifestAction :: searchManifestDetails() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: searchManifestDetails() :: START");
	}

	/**
	 * To print third Party Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to print view for third party BPL
	 * @throws Exception
	 */
	public ActionForward printThirdPartyDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: printThirdPartyDtls() :: START");
		ThirdPartyBPLOutManifestTO thirdPartyBPLTO = null;
		ThirdPartyBPLOutManifestTO thirdPartyBPLTO1 = null;
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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				thirdPartyBPLservice = getThirdPartyBPLservice();
				thirdPartyBPLTO = thirdPartyBPLservice
						.searchManifestDtls(manifestTO);
				if (!StringUtil.isNull(thirdPartyBPLTO
						.getThirdPartyBPLDetailsListTO())) {
					thirdPartyBPLTO1 = thirdPartyBPLservice
							.getTotalConsignmentCount(thirdPartyBPLTO
									.getThirdPartyBPLDetailsListTO());
				}
				if (!StringUtil.isNull(thirdPartyBPLTO1)) {
					if (!StringUtil.isNull(thirdPartyBPLTO1.getTotalConsg())) {
						thirdPartyBPLTO.setTotalConsg(thirdPartyBPLTO1
								.getTotalConsg());
					}

					if (!StringUtil.isNull(thirdPartyBPLTO1.getTotalComail())) {
						thirdPartyBPLTO.setTotalComail(thirdPartyBPLTO1
								.getTotalComail());
					}

					if (!StringUtil.isEmptyInteger(thirdPartyBPLTO1
							.getTotalPacket())) {
						thirdPartyBPLTO.setTotalPacket(thirdPartyBPLTO1
								.getTotalPacket());
					}
				}
				request.setAttribute("thirdPartyBPLTO", thirdPartyBPLTO);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception happened in ThirdPartyBPLOutManifestAction :: printThirdPartyDtls() ::",
					e);
		}
		LOGGER.trace("ThirdPartyBPLOutManifestAction :: printThirdPartyDtls() :: END");
		return mapping.findForward(ManifestConstants.URL_PRINT_THIRDPARTY_BPL);
	}

}
