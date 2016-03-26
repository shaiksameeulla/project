package com.ff.web.manifest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.LoadLotTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.universe.terminal.TerminalUtil;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.Utils.OutManifestTOFactory;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.validator.ManifestValidator;

public abstract class OutManifestAction extends CGBaseAction {
	private OutManifestCommonService outManifestCommonService;
	private ManifestValidator manifestValidator;
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestAction.class);

	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;

	/**
	 * To get Manifest Common Service
	 * 
	 * @return manifestCommonService
	 */
	private ManifestCommonService getManifestCommonService() {
		if (manifestCommonService == null) {
			manifestCommonService = (ManifestCommonService) getBean(SpringConstants.MANIFEST_COMMON_SERVICE);
		}
		return manifestCommonService;
	}

	/**
	 * @param manifestFactoryTO
	 * @param request
	 * @return
	 */
	public OutManifestBaseTO getManifestBasicDtls(
			ManifestFactoryTO manifestFactoryTO, HttpServletRequest request) {
		LOGGER.debug("OutManifestAction :: getManifestBasicDtls() :: START------------>:::::::");
		OutManifestBaseTO outManifestBaseTO = null;
		try {
			outManifestBaseTO = OutManifestTOFactory
					.getOutManifestBaseTO(manifestFactoryTO);
			// set Manifest date
			outManifestBaseTO.setManifestDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat());
			// set Manifest login details in Region-Office Name Format
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			if (!StringUtil.isNull(userInfoTO)) {
				Map<String, String> configurableParams = userInfoTO
						.getConfigurableParams();
				// setting common user id
				outManifestBaseTO.setCreatedBy(userInfoTO.getUserto()
						.getUserId());
				outManifestBaseTO.setUpdatedBy(userInfoTO.getUserto()
						.getUserId());

				OfficeTO officeTO = userInfoTO.getOfficeTo();
				Integer loggedInOfficeId = officeTO.getOfficeId();
				String officeCode = officeTO.getOfficeCode();
				String loginOfficeName = officeTO.getOfficeName();
				Integer loginRegionId = officeTO.getRegionTO().getRegionId();
				Integer repHubId = officeTO.getReportingHUB();
				outManifestBaseTO.setLoginOfficeType(officeTO.getOfficeTypeTO()
						.getOffcTypeCode());
				outManifestBaseTO.setRegionCode(officeTO.getRegionTO()
						.getRegionCode());
				if (!StringUtil.isEmptyInteger(officeTO.getReportingHUB())) {
					outManifestBaseTO
							.setLoginRepHub(officeTO.getReportingHUB());
				}
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				CityTO cityTO = new CityTO();
				cityTO.setCityId(officeTO.getCityId());
				List<CityTO> cityTOs = outManifestCommonService
						.getCitiesByCity(cityTO);
				if (!StringUtil.isEmptyList(cityTOs)) {
					String loginCityCode = cityTOs.get(0).getCityCode();
					outManifestBaseTO
							.setLoginCityId(cityTOs.get(0).getCityId());
					outManifestBaseTO.setLoginCityCode(loginCityCode);
				}
				/*
				 * OfficeTO regionOfficeTO = outManifestCommonService
				 * .getOfficeDetails(regionId); String regionName =
				 * regionOfficeTO.getOfficeName();
				 */
				String loginRegionOffice = officeTO.getRegionTO()
						.getRegionName()
						+ CommonConstants.HYPHEN
						+ loginOfficeName;

				/* OriginCityId attribute for checking route in BPL Out Manifest */
				Integer originCityId = officeTO.getCityId();
				request.setAttribute("originCityId", originCityId);

				outManifestBaseTO.setLoginOfficeId(loggedInOfficeId);
				outManifestBaseTO.setLoginOfficeName(loginRegionOffice);
				outManifestBaseTO.setRegionId(loginRegionId);
				outManifestBaseTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				outManifestBaseTO.setOfficeCode(officeCode);
				if (!StringUtil.isEmptyInteger(repHubId)) {
					outManifestBaseTO.setRepHubOfficeId(repHubId);
				}
				outManifestBaseTO.setConfigurableParams(configurableParams);
				setManifestConstants(request);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutManifestAction :: getManifestBasicDtls() ::"
					+ e.getMessage());

		}
		LOGGER.debug("OutManifestAction :: getManifestBasicDtls() :: END------------>:::::::");
		return outManifestBaseTO;
	}

	// To set the Constants for Java Script files
	private void setManifestConstants(HttpServletRequest request) {
		request.setAttribute("MANIFEST_STATUS_CLOSE",
				OutManifestConstants.MANIFEST_STATUS_CLOSE);
		request.setAttribute("MANIFEST_STATUS_OPEN",
				OutManifestConstants.MANIFEST_STATUS_OPEN);
		request.setAttribute("SERIES_TYPE_OGM_STICKERS",
				UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
		request.setAttribute("SERIES_TYPE_BPL_NO",
				UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
		request.setAttribute("SERIES_TYPE_BAG_LOCK_NO",
				UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO);
		request.setAttribute("SERIES_TYPE_MBPL_NO",
				UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS);
		request.setAttribute(OutManifestConstants.MANIFEST_TYPE_PURE,
				OutManifestConstants.MANIFEST_TYPE_PURE);
		request.setAttribute(
				OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE,
				OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE);
		request.setAttribute("OFF_TYPE_CODE_HUB",
				CommonConstants.OFF_TYPE_HUB_OFFICE);
		request.setAttribute("OFF_TYPE_CODE_BRANCH",
				CommonConstants.OFF_TYPE_BRANCH_OFFICE);
		request.setAttribute("OFF_TYPE_REGION_HEAD_OFFICE",
				CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);
		request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
		request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);
	}

	// get all the cities based on region
	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: getCitiesByRegion() :: START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		try {
			String region = request
					.getParameter(OutManifestConstants.REGION_ID);
			String manifestType = request
					.getParameter(OutManifestConstants.MANIFEST_TYPE);

			ManifestRegionTO manifestRegionTO = null;
			if (StringUtils.isNotEmpty(region)) {
				manifestRegionTO = new ManifestRegionTO();
				RegionTO regionTO = new RegionTO();
				regionTO.setRegionId(Integer.parseInt(region));
				manifestRegionTO.setManifestType(manifestType);
				manifestRegionTO.setRegionTO(regionTO);
			}
			if (!StringUtil.isNull(manifestRegionTO)) {

				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				List<CityTO> cityTOs = outManifestCommonService
						.getCitiesByRegion(manifestRegionTO);

				if (!StringUtil.isEmptyList(cityTOs))
					jsonResult = serializer.toJSON(cityTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: getCitiesByRegion() :: END------------>:::::::");
	}

	// get all the offices based on city
	@SuppressWarnings("static-access")
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: getAllOfficesByCity() :: START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		List<OfficeTO> officeTOs = null;
		try {
			String city = request.getParameter("cityId");
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if (cityId != null && cityId > 0) {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				officeTOs = outManifestCommonService
						.getAllOfficesByCity(cityId);

				if (!StringUtil.isEmptyList(officeTOs))
					jsonResult = serializer.toJSON(officeTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("OutManifestAction :: getAllOfficesByCity() :: END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void getAllOfficesByCityAndOfficeType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("OutManifestAction :: getAllOfficesByCityAndOfficeType() :: START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();

		String jsonResult = CommonConstants.EMPTY_STRING;
		List<OfficeTO> officeTOs = null;
		try {
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
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				officeTOs = outManifestCommonService
						.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);
			}
			if (!StringUtil.isEmptyList(officeTOs))
				jsonResult = serializer.toJSON(officeTOs).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCityAndOfficeType()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCityAndOfficeType()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::getAllOfficesByCityAndOfficeType()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("OutManifestAction :: getAllOfficesByCityAndOfficeType() :: END------------>:::::::");
	}

	/*
	 * @SuppressWarnings("static-access") public void
	 * validateConsignment(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) { LOGGER.debug(
	 * "OutManifestAction :: validateConsignment() :: START------------>:::::::"
	 * ); OutManifestValidate cnValidateTO = null; String cnValidationJSON =
	 * CommonConstants.EMPTY_STRING; PrintWriter out = null; OfficeTO officeTO =
	 * null; CityTO cityTO = null; try { out = response.getWriter();
	 * cnValidateTO = new OutManifestValidate(); if
	 * (StringUtils.isNotEmpty(request .getParameter("manifestDirection"))) {
	 * cnValidateTO.setManifestDirection(request
	 * .getParameter("manifestDirection")); } if
	 * (!StringUtil.isStringEmpty(request
	 * .getParameter("allowedConsgManifestedType"))) {
	 * cnValidateTO.setAllowedConsgManifestedType(request
	 * .getParameter("allowedConsgManifestedType").split(
	 * CommonConstants.COMMA)); } else { cnValidateTO
	 * .setAllowedConsgManifestedType(new String[] {
	 * CommonConstants.EMPTY_STRING }); } if
	 * (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
	 * officeTO = new OfficeTO(); officeTO.setOfficeId(Integer.parseInt(request
	 * .getParameter("loginOfficeId"))); cnValidateTO.setOriginOffice(officeTO);
	 * }
	 * 
	 * if (StringUtils.isNotEmpty(request.getParameter("consgNumber"))) {
	 * cnValidateTO .setConsgNumber(request.getParameter("consgNumber")); } if
	 * (StringUtils.isNotEmpty(request.getParameter("officeId"))) { officeTO =
	 * new OfficeTO(); officeTO.setOfficeId(Integer.parseInt(request
	 * .getParameter("officeId"))); cnValidateTO.setDestOffice(officeTO); } if
	 * (StringUtils.isNotEmpty(request.getParameter("cityId"))) { cityTO = new
	 * CityTO(); cityTO.setCityId(Integer.parseInt(request
	 * .getParameter("cityId"))); cnValidateTO.setDestCityTO(cityTO); } if
	 * (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
	 * cnValidateTO.setManifestNumber(request .getParameter("manifestNo")); }
	 * outManifestCommonService = (OutManifestCommonService)
	 * getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE); cnValidateTO =
	 * outManifestCommonService .validateConsignment(cnValidateTO); } catch
	 * (Exception e) { ResourceBundle errorMessages = null; errorMessages =
	 * ResourceBundle .getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
	 * String errorMsg = errorMessages.getString(e.getMessage());
	 * cnValidateTO.setErrorMsg(errorMsg);
	 * LOGGER.error("OutManifestAction :: validateConsignment() ::" +
	 * e.getMessage()); } finally { if (!StringUtil.isNull(cnValidateTO))
	 * cnValidationJSON = serializer.toJSON(cnValidateTO).toString();
	 * out.print(cnValidationJSON); out.flush(); out.close(); } LOGGER.debug(
	 * "OutManifestAction :: validateConsignment() :: END------------>:::::::");
	 * }
	 */

	@SuppressWarnings("static-access")
	public void validateManifestNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: validateManifestNo() :: START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		String stockIssueJSON = CommonConstants.EMPTY_STRING;
		ManifestStockIssueInputs stockIssueInputs = null;
		String manifestScanlevel = "";
		String officeCode = "";
		try {

			String stockNo = request.getParameter("stockItemNo");
			String seriesType = request.getParameter("seriesType");
			String regionCode = request.getParameter("regionCode");
			String loginCityCode = request.getParameter("loginCityCode");
			String loginOfficeId = request.getParameter("loggedinOfficeId");
			Integer officeId = Integer.parseInt(request
					.getParameter("loggedinOfficeId"));// change for stock
														// validation
			String manifestType = request.getParameter("manifestType");
			String processCode = request
					.getParameter(OutManifestConstants.PROCESS_CODE);
			if (request.getParameter("manifestScanlevel") != null)
				manifestScanlevel = request.getParameter("manifestScanlevel");

			if (request.getParameter("officeCode") != null)
				officeCode = request.getParameter("officeCode");

			stockIssueInputs = new ManifestStockIssueInputs();
			stockIssueInputs.setStockItemNumber(stockNo);
			stockIssueInputs.setSeriesType(seriesType);
			stockIssueInputs
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockIssueInputs.setIssuedTOPartyId(officeId);// change for stock
															// validation
			stockIssueInputs.setRegionCode(regionCode);
			stockIssueInputs.setManifestType(manifestType);
			stockIssueInputs.setManifestScanlevel(manifestScanlevel);
			stockIssueInputs.setManifestProcessCode(processCode);
			stockIssueInputs.setLoginCityCode(loginCityCode);
			stockIssueInputs.setOfficeCode(officeCode);

			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			stockIssueInputs = outManifestCommonService.validateManifestNo(
					stockIssueInputs, loginOfficeId);

			if (!StringUtil.isNull(stockIssueInputs))
				stockIssueJSON = serializer.toJSON(stockIssueInputs).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(stockIssueJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: validateManifestNo() :: END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void validatePincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: validatePincode() :: START------------>:::::::");
		Integer destCityId = CommonConstants.ZERO;
		PrintWriter out = null;
		out = response.getWriter();

		String pincodeValidationJSON = CommonConstants.EMPTY_STRING;
		OutManifestValidate outMnifestValidateTO = null;
		try {
			String pincode = request.getParameter("pincode");
			String consgSeries = request.getParameter("consgSeries");
			String isPickupCN = request.getParameter("isPickupCN");
			if (StringUtils.isNotEmpty(request.getParameter("destCityId")))
				destCityId = Integer.parseInt(request
						.getParameter("destCityId"));

			OutManifestValidate pincodeValidateTO = new OutManifestValidate();
			pincodeValidateTO.setConsignmentSeries(consgSeries);
			pincodeValidateTO.setIsCNProcessedFromPickup(isPickupCN);
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincode(pincode);
			pincodeValidateTO.setCnPincodeTO(pincodeTO);

			// Added by Himal for OGM pincode servicibility issue fix for
			// Transshipment
			String isChkTransCity = request
					.getParameter("isChkTransCityPincodeServ");
			if (!StringUtil.isStringEmpty(isChkTransCity)
					&& isChkTransCity.equalsIgnoreCase(CommonConstants.YES)) {
				pincodeValidateTO
						.setChkTransCityPincodeServ(CommonConstants.YES);
				// Set destination pincode
				setDestPincodeForOGMTran(pincode, pincodeValidateTO);

			}
			String manifestProcessCode = request
					.getParameter("manifestProcessCode");
			if (!StringUtil.isStringEmpty(manifestProcessCode)) {
				pincodeValidateTO.setManifestProcessCode(manifestProcessCode);
			}

			/* Added By Himal on 08-OCT-2013 10:49 PM */
			OfficeTO officeTO = outManifestCommonService
					.getOfficeDetails(Integer.parseInt(request
							.getParameter("destOfficeId")));
			// officeTO.setOfficeId(destOfficeId);

			pincodeValidateTO.setDestOffice(officeTO);
			CityTO cityTO = new CityTO();
			cityTO.setCityId(destCityId);
			pincodeValidateTO.setDestCityTO(cityTO);
			manifestValidator = (ManifestValidator) springApplicationContext
					.getBean(SpringConstants.OUT_MANIFEST_COMMON_MANIFEST_VALIDATOR);
			if (StringUtils
					.endsWithIgnoreCase(
							request.getParameter(OutManifestConstants.MANIFEST_OPEN_TYPE),
							"P")) {
				outMnifestValidateTO = manifestValidator
						.ffclValidationForPincode(pincodeValidateTO);
			} else {
				outMnifestValidateTO = manifestValidator
						.ffclValidationForPincode(pincodeValidateTO);
				outMnifestValidateTO = manifestValidator
						.isValidPincode(pincodeValidateTO);
			}
			if (!StringUtil.isNull(outMnifestValidateTO))
				pincodeValidationJSON = serializer.toJSON(outMnifestValidateTO)
						.toString();
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::validatePincode()::Exception::"
					+ e.getMessage());
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::validatePincode()::Exception::"
					+ e.getMessage());
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::validatePincode()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(pincodeValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: validatePincode() :: END------------>:::::::");
	}

	/**
	 * To set destination pincode for OGM transshipment
	 * 
	 * @param pincode
	 * @param pincodeValidateTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private void setDestPincodeForOGMTran(String pincode,
			OutManifestValidate pincodeValidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestAction :: setDestPincodeForOGMTran() :: START ");
		List<PincodeTO> pincodeList = null;
		ConsignmentTO consgTO = new ConsignmentTO();
		PincodeTO destPincode = new PincodeTO();
		destPincode.setPincode(pincode);
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		pincodeList = outManifestCommonService
				.getPincodeDetailsByPincode(destPincode);
		if (!CGCollectionUtils.isEmpty(pincodeList)) {
			destPincode = pincodeList.get(0);
			consgTO.setDestPincode(destPincode);
			pincodeValidateTO.setConsgTO(consgTO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
		}
		LOGGER.debug("OutManifestAction :: setDestPincodeForOGMTran() :: END ");
	}

	@SuppressWarnings("static-access")
	public void validatePincodeWithManifestType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("OutManifestAction :: validatePincodeWithManifestType() :: START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();

		String pincodeValidationJSON = CommonConstants.EMPTY_STRING;
		OutManifestValidate outMnifestValidateTO = null;
		try {
			outMnifestValidateTO = prepareValidatePincodeInputs(request,
					outMnifestValidateTO);

			// Added by Himal for BPL Parcel pincode servicibility issue fix for
			// Pure - pickup/Transshipment - Null Pointer Exception
			String isChkTransCity = request
					.getParameter("isChkTransCityPincodeServ");
			if (!StringUtil.isStringEmpty(isChkTransCity)
					&& isChkTransCity.equalsIgnoreCase(CommonConstants.YES)) {
				outMnifestValidateTO
						.setChkTransCityPincodeServ(CommonConstants.YES);
				// Set destination pincode
				setDestPincodeForOGMTran(request.getParameter("pincode"),
						outMnifestValidateTO);
			}
			String manifestProcessCode = request
					.getParameter("manifestProcessCode");
			if (!StringUtil.isStringEmpty(manifestProcessCode)) {
				outMnifestValidateTO
						.setManifestProcessCode(manifestProcessCode);
			}
			String loginOfficeType = request.getParameter("loginOfficeType");

			manifestValidator = (ManifestValidator) springApplicationContext
					.getBean(SpringConstants.OUT_MANIFEST_COMMON_MANIFEST_VALIDATOR);

			if (request.getParameter("bplManifestType").equalsIgnoreCase(
					OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
				outMnifestValidateTO = manifestValidator
						.ffclValidationForPincode(outMnifestValidateTO);
				if (!StringUtil.isStringEmpty(loginOfficeType)
						&& loginOfficeType
								.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE))
					manifestValidator
							.validateTranshimentCity(outMnifestValidateTO);
			} else {
				outMnifestValidateTO = manifestValidator
						.ffclValidationForPincode(outMnifestValidateTO);
				outMnifestValidateTO = manifestValidator
						.isValidPincode(outMnifestValidateTO);
			}

			if (!StringUtil.isNull(outMnifestValidateTO))
				pincodeValidationJSON = serializer.toJSON(outMnifestValidateTO)
						.toString();
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::validatePincodeWithManifestType()::Exception::"
					+ e.getMessage());
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::validatePincodeWithManifestType()::Exception::"
					+ e.getMessage());
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::validatePincodeWithManifestType()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			pincodeValidationJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(pincodeValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: validatePincodeWithManifestType() :: END------------>:::::::");
	}

	private OutManifestValidate prepareValidatePincodeInputs(
			HttpServletRequest request, OutManifestValidate pincodeValidateTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("OutManifestAction :: prepareValidatePincodeInputs() :: START------------>:::::::");
		Integer destOfficeId = CommonConstants.ZERO;
		Integer destCityId = CommonConstants.ZERO;
		pincodeValidateTO = new OutManifestValidate();
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);

		String pincode = request.getParameter("pincode");
		String consgSeries = request.getParameter("consgSeries");
		String isPickupCN = request.getParameter("isPickupCN");
		pincodeValidateTO.setConsignmentSeries(consgSeries);
		pincodeValidateTO.setIsCNProcessedFromPickup(isPickupCN);
		if (StringUtils.isNotEmpty(pincode)) {
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincode(pincode);
			pincodeValidateTO.setCnPincodeTO(pincodeTO);
		}
		if (StringUtils.isNotEmpty(request.getParameter("destOfficeId"))) {
			destOfficeId = Integer.parseInt(request
					.getParameter("destOfficeId"));
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(destOfficeId);
			pincodeValidateTO.setDestOffice(officeTO);
		}
		if (StringUtils.isNotEmpty(request.getParameter("destCityId"))) {
			destCityId = Integer.parseInt(request.getParameter("destCityId"));
			CityTO cityTO = new CityTO();
			cityTO.setCityId(destCityId);
			pincodeValidateTO.setDestCityTO(cityTO);
		}

		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		OfficeTO loginOfficeTO = userInfoTO.getOfficeTo();

		String officeType = "";
		OfficeTypeTO officeTypeTO = null;
		if (StringUtils.isNotEmpty(request
				.getParameter(OutManifestConstants.OFFICE_TYPE))) {
			officeType = request.getParameter(OutManifestConstants.OFFICE_TYPE);
			officeTypeTO = new OfficeTypeTO();
			officeTypeTO.setOffcTypeId(Integer.parseInt(officeType));
			officeTypeTO = outManifestCommonService
					.getOfficeTypeDOByOfficeTypeIdOrCode(officeTypeTO);
			OfficeTO destOffice = pincodeValidateTO.getDestOffice();
			if (StringUtil.isNull(destOffice)) {
				destOffice = new OfficeTO();
			}
			destOffice.setOfficeTypeTO(officeTypeTO);
			pincodeValidateTO.setDestOffice(destOffice);
		}
		// Origin Branch to Destination HUB
		String bplManifestType = request.getParameter("bplManifestType");
		if (!StringUtil.isNull(officeTypeTO)
				&& StringUtils.equalsIgnoreCase(loginOfficeTO.getOfficeTypeTO()
						.getOffcTypeCode(),
						CommonConstants.OFF_TYPE_BRANCH_OFFICE)
				&& StringUtils.equalsIgnoreCase(officeTypeTO.getOffcTypeCode(),
						CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			if (StringUtils.equalsIgnoreCase(bplManifestType,
					OutManifestConstants.MANIFEST_TYPE_PURE)) {
				pincodeValidateTO
						.setChkTransCityPincodeServ(CommonConstants.YES);
			} else if (StringUtils.equalsIgnoreCase(bplManifestType,
					OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
				List<Integer> transCityIds = outManifestCommonService
						.getServicedCityByTransshipmentCity(destCityId);
				transCityIds.add(destCityId);
				pincodeValidateTO.setTranshipmentCityIds(transCityIds);
				pincodeValidateTO
						.setChkTransCityPincodeServ(CommonConstants.YES);
			}
		}
		// Origin HUB to Destination HUB
		else if (!StringUtil.isNull(officeTypeTO)
				&& StringUtils
						.equalsIgnoreCase(loginOfficeTO.getOfficeTypeTO()
								.getOffcTypeCode(),
								CommonConstants.OFF_TYPE_HUB_OFFICE)
				&& StringUtils.equalsIgnoreCase(officeTypeTO.getOffcTypeCode(),
						CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			if (StringUtils.equalsIgnoreCase(bplManifestType,
					OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
				List<Integer> transCityIds = outManifestCommonService
						.getServicedCityByTransshipmentCity(destCityId);
				transCityIds.add(destCityId);
				pincodeValidateTO.setTranshipmentCityIds(transCityIds);
				pincodeValidateTO
						.setChkTransCityPincodeServ(CommonConstants.YES);
			}
		}
		LOGGER.debug("OutManifestAction :: prepareValidatePincodeInputs() :: END------------>:::::::");
		return pincodeValidateTO;
	}

	public void getRfIdByRfNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.info("OutManifestAction::getRfIdByRfNo::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();

		String jsonResult = null;
		String rfIdNo = null;
		Integer rfId = null;
		try {
			if (StringUtils.isNotEmpty(request.getParameter("rfNo"))) {
				rfIdNo = request.getParameter("rfNo");
			}
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			rfId = outManifestCommonService.getRfIdByRfNo(rfIdNo);

			if (StringUtil.isNull(rfId)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.INVALID_RADIO_FREQUENCY,
								null));
			} else {
				jsonResult = rfId.toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::getRfIdByRfNo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::getRfIdByRfNo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::getRfIdByRfNo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.info("OutManifestAction::getRfIdByRfNo::END------------>:::::::");
	}

	public void validateComail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: validateComail() :: START------------>:::::::");
		String comailNo = request.getParameter("comailNo");
		Integer manifestId = null;
		String isComailManifested = "N";
		String comailPresentAndNotManifsted = "N";
		String allowToSave = "N";
		PrintWriter out = null;
		out = response.getWriter();
		try {
			if (!StringUtil.isStringEmpty(request.getParameter("manifestId"))) {
				manifestId = Integer.parseInt(request
						.getParameter("manifestId"));
			}
			if (StringUtils.isNotEmpty(comailNo)) {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				isComailManifested = outManifestCommonService.validateComail(
						comailNo, manifestId);

				if (isComailManifested.equalsIgnoreCase("N")) {

					Boolean isExistInComail = outManifestCommonService
							.isExistInComailTable(comailNo);
					if (isExistInComail) {
						comailPresentAndNotManifsted = "Y";
					}

				}
				if (isComailManifested.equalsIgnoreCase("Y")) {
					allowToSave = "N";
				} else if (isComailManifested.equalsIgnoreCase("N")
						&& comailPresentAndNotManifsted.equalsIgnoreCase("Y")) {
					allowToSave = "Y";
				} else if (isComailManifested.equalsIgnoreCase("N")
						&& comailPresentAndNotManifsted.equalsIgnoreCase("N")) {
					allowToSave = "Y";
				}

			}
		} catch (Exception e) {
			LOGGER.error("ERROR:: OutManifestAction :: validateComail() ::"
					+ e.getMessage());
		} finally {
			out.print(allowToSave);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: validateComail() :: END------------>:::::::");
	}

	public void getRHOOfficeIdByOffice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("OutManifestAction :: getRHOOfficeIdByOffice() :: START------------>:::::::");
		String officeId = request.getParameter("officeId");
		String repHubOfficeId = null;
		PrintWriter out = null;
		OfficeTO office = null;
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(officeId)) {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				office = outManifestCommonService.getOfficeDetails(Integer
						.parseInt(officeId));

				if (!StringUtil.isNull(office)
						&& !StringUtil.isEmptyInteger(office.getReportingHUB())) {

					repHubOfficeId = office.getReportingHUB().toString();

				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::getRHOOfficeIdByOffice()::Exception::"
					+ e.getMessage());
			repHubOfficeId = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::getRHOOfficeIdByOffice()::Exception::"
					+ e.getMessage());
			repHubOfficeId = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::getRHOOfficeIdByOffice()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			repHubOfficeId = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(repHubOfficeId);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: getRHOOfficeIdByOffice() :: END------------>:::::::");
	}

	/*
	 * public void isManifestNoIssued(final ActionMapping mapping, final
	 * ActionForm form, final HttpServletRequest request, final
	 * HttpServletResponse response) { LOGGER.debug(
	 * "OutManifestAction::isManifestNoIssued::START------------>:::::::");
	 * ManifestIssueValidationTO stockIssueValidationTO = null; PrintWriter out
	 * = null; String jsonResult = CommonConstants.EMPTY_STRING; String
	 * manifestNo = CommonConstants.EMPTY_STRING; String seriesType =
	 * CommonConstants.EMPTY_STRING; HttpSession session = null; UserInfoTO
	 * userInfoTO = null; boolean isIssued = false; try { manifestNo =
	 * request.getParameter("manifestNo"); seriesType =
	 * request.getParameter("seriesType"); session = (HttpSession)
	 * request.getSession(false); userInfoTO = (UserInfoTO) session
	 * .getAttribute(UmcConstants.USER_INFO); OfficeTO officeTO =
	 * userInfoTO.getOfficeTo(); if (StringUtils.isNotEmpty(manifestNo) &&
	 * StringUtils.isNotEmpty(seriesType)) { stockIssueValidationTO = new
	 * ManifestIssueValidationTO();
	 * stockIssueValidationTO.setStockItemNumber(manifestNo);
	 * stockIssueValidationTO.setSeriesType(seriesType); stockIssueValidationTO
	 * .setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
	 * stockIssueValidationTO.setIssuedTOPartyId(officeTO
	 * .getRegionTO().getRegionId());
	 * stockIssueValidationTO.setRegionCode(officeTO.getRegionTO()
	 * .getRegionCode()); manifestUniversalService = (ManifestUniversalService)
	 * getBean(SpringConstants.MANIFEST_UNIVERSAL_SERVICE); isIssued =
	 * manifestUniversalService .isManifesIssued(stockIssueValidationTO); }
	 * stockIssueValidationTO.setIsIssued((isIssued) ? CommonConstants.YES :
	 * CommonConstants.NO); } catch (CGBusinessException e) {
	 * 
	 * LOGGER.error("ERROR :: OutManifestAction::isManifestNoIssued..." +
	 * e.getMessage()); } finally { jsonResult =
	 * serializer.toJSON(stockIssueValidationTO).toString(); out =
	 * response.getWriter(); out.print(jsonResult); out.flush(); out.close(); }
	 * LOGGER
	 * .debug("OutManifestAction::isManifestNoIssued::END------------>:::::::");
	 * }
	 */

	public void getComailIdByComailNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String comailNo = request.getParameter("comailNo");
		Integer comailId = null;
		PrintWriter out = null;
		out = response.getWriter();
		try {
			if (StringUtils.isNotEmpty(comailNo)) {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				comailId = outManifestCommonService
						.getComailIdByComailNo(comailNo);

			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR:: OutManifestAction :: getComailIdByComailNo() ::",
					e);
		} finally {
			out.print(comailId.toString());
			out.flush();
			out.close();
		}

	}

	public void isValiedBagLockNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("OutManifestAction :: isValiedBagLockNo() :: START------------>:::::::");
		PrintWriter out = null;
		String result = null;
		out = response.getWriter();
		try {

			String bagLockNo = request
					.getParameter(ManifestUniversalConstants.BAG_LOCK_NO);

			if (!StringUtils.isEmpty(bagLockNo)) {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				Boolean isValied = outManifestCommonService
						.isValiedBagLockNo(bagLockNo);

				if (isValied) {
					result = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.BAG_LOCK_ALREADY_USED,
									null));
				} else {
					result = prepareCommonException(
							FrameworkConstants.SUCCESS_FLAG,
							ManifestErrorCodesConstants.ROUTE_FOUND);
				}

			}

		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("OutManifestAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestAction :: isValiedBagLockNo() :: END------------>:::::::");
	}

	public List<LabelValueBean> setLoadDropDown() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("OutManifestAction :: setLoadDropDown() :: START------------>:::::::");
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		List<LoadLotTO> loadNoTo = outManifestCommonService.getLoadNo();
		List<LabelValueBean> loadNoList = new ArrayList<LabelValueBean>();
		for (LoadLotTO loadTO : loadNoTo) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(loadTO.getLoadNo().toString());
			lvb.setValue(loadTO.getLoadLotId() + "");
			loadNoList.add(lvb);
		}
		LOGGER.debug("OutManifestAction :: setLoadDropDown() :: END------------>:::::::");
		return loadNoList;

	}

	/**
	 * To validate whether manifest is exist for perticular process or not
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void isManifestExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.trace("OutManifestAction :: isManifestExist() :: START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		String jsonResult = null;
		try {

			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			String manifestProcessCode = request
					.getParameter(OutManifestConstants.MANIFEST_PROCESS_CODE);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO.setManifestProcessCode(manifestProcessCode);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				manifestCommonService = getManifestCommonService();
				manifestCommonService.isManifestExist(manifestTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("OutManifestAction :: isManifestExist() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("OutManifestAction :: isManifestExist() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("OutManifestAction :: isManifestExist() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("OutManifestAction :: isManifestExist() :: END------------>:::::::");
	}

	/**
	 * To calculate rate for consignment for Parcel and Dox
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void calculateCnRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.debug("OutManifestParcelAction :: calculateCnRate() :: START");
		PrintWriter out = null;
		out = response.getWriter();

		String jsonResult = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		CNPricingDetailsTO cnRateDtls = null;
		try {
			session = (HttpSession) request.getSession(false);

			/* Prepare consignment TO */
			ConsignmentTO consignmentTO = prepareConsgTO(request);

			/*
			 * setting booking details i.e. booking Date, customer id, rate
			 * customer category code.
			 */
			setBookingDtls(consignmentTO);
			LOGGER.debug("OutManifestParcelAction :: calculateCnRate() :: Rate Input Values :: Consg No"
					+ consignmentTO.getConsgNo()
					+ "Booking Date :: "
					+ consignmentTO.getBookingDate());
			/* Calculate Rates */
			ConsignmentRateCalculationOutputTO cnRateCalcOutputTO = outManifestCommonService
					.calculateRateForConsignment(consignmentTO);

			if (!StringUtil.isNull(cnRateCalcOutputTO)) {
				LOGGER.debug("OutManifestParcelAction :: calculateCnRate() :: Rate Input Values :: Consg No"
						+ consignmentTO.getConsgNo()
						+ "Booking Date :: "
						+ consignmentTO.getBookingDate());
				rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
						.getAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
				if (!CGCollectionUtils.isEmpty(rateCompnents)) {
					LOGGER.info("OutManifestParcelAction :: calculateCnRate() :: Rate calculated :: for Consg No ::"
							+ consignmentTO.getConsgNo()
							+ " :: Total Calculated Value :: "
							+ cnRateCalcOutputTO.getGrandTotalIncludingTax());
					rateCompnents.put(consignmentTO.getConsgNo(),
							cnRateCalcOutputTO);
				} else {
					LOGGER.info("OutManifestParcelAction :: calculateCnRate() :: Rate ..calculated :: for Consg No ::"
							+ consignmentTO.getConsgNo());
					rateCompnents = new HashMap<String, ConsignmentRateCalculationOutputTO>();
					rateCompnents.put(consignmentTO.getConsgNo(),
							cnRateCalcOutputTO);
				}
				session.setAttribute(
						ManifestConstants.MANIFEST_CONSG_RATE_DTLS,
						rateCompnents);
				cnRateDtls = ManifestUtil
						.setUpRateCompoments(cnRateCalcOutputTO);
				jsonResult = JSONSerializer.toJSON(cnRateDtls).toString();
			} else {
				// Consider as ERROR mode
				LOGGER.error("OutManifestParcelAction :: calculateCnRate() :: Rate does not ..calculated :: for Consg No ::"
						+ consignmentTO.getConsgNo());
				throw new CGBusinessException("OMRC001");
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in OutManifestParcelAction :: ..:calculateCnRate()",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in OutManifestParcelAction :: ..:calculateCnRate()",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in OutManifestParcelAction :: ..:calculateCnRate()",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("OutManifestParcelAction :: calculateCnRate() :: END");
	}

	/**
	 * To prepare consignmentTO from request parameters
	 * 
	 * @param request
	 * @return consignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ConsignmentTO prepareConsgTO(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelAction :: prepareConsgTO() :: START");
		ConsignmentTO consignmentTO = new ConsignmentTO();
		CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
		// Consignment Number
		String consgNo = request.getParameter("consigntNo");
		if (!StringUtil.isStringEmpty(consgNo)) {
			consignmentTO.setConsgNo(consgNo);
		}
		// Set Product Code
		if (!StringUtil.isStringEmpty(consgNo)) {
			String cnSeries = CommonConstants.EMPTY_STRING;
			Character cnSeriesChar = consgNo.substring(4, 5).toCharArray()[0];
			if (Character.isDigit(cnSeriesChar)) {
				cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
			} else {
				cnSeries = cnSeriesChar.toString();
			}
			ProductTO productTO = outManifestCommonService
					.getProductByConsgSeries(cnSeries);
			if (!StringUtil.isNull(productTO)) {
				consignmentTO.setProductTO(productTO);
			}
		}
		// Pincode
		if (!StringUtil.isStringEmpty(request.getParameter("pincode"))) {
			PincodeTO destPincode = new PincodeTO();
			destPincode.setPincode(request.getParameter("pincode"));
			consignmentTO.setDestPincode(destPincode);
		}
		// Weight
		if (!StringUtil.isStringEmpty(request.getParameter("weight"))) {
			consignmentTO.setFinalWeight(Double.parseDouble(request
					.getParameter("weight")));
		}
		// Insured By
		if (!StringUtil.isStringEmpty(request.getParameter("insuredBy"))) {
			InsuredByTO insuredByTO = new InsuredByTO();
			insuredByTO.setInsuredByCode(request.getParameter("insuredBy"));
			consignmentTO.setInsuredByTO(insuredByTO);
		}
		// Operating Office Id
		if (!StringUtil.isStringEmpty(request.getParameter("loginOfficeId"))) {
			consignmentTO.setOperatingOffice(Integer.parseInt(request
					.getParameter("loginOfficeId")));
		}
		// Consignment Type
		if (!StringUtil.isStringEmpty(request.getParameter("consgType"))) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			typeTO.setConsignmentCode(request.getParameter("consgType"));
			consignmentTO.setTypeTO(typeTO);
		}
		// Consg status
		consignmentTO.setConsgStatus(BookingConstants.BOOKING_NORMAL_PROCESS);

		// Declared Value
		if (!StringUtil.isStringEmpty(request.getParameter("declaredValue"))) {
			consgPriceDtls.setDeclaredvalue(Double.parseDouble(request
					.getParameter("declaredValue")));
		}
		// LC Amount
		if (!StringUtil.isStringEmpty(request.getParameter("lcAmt"))) {
			consgPriceDtls.setLcAmount(Double.parseDouble(request
					.getParameter("lcAmt")));
		}
		// COD Amount
		if (!StringUtil.isStringEmpty(request.getParameter("codAmt"))) {
			consgPriceDtls.setCodAmt(Double.parseDouble(request
					.getParameter("codAmt")));
		}
		// To pay amount
		if (!StringUtil.isStringEmpty(request.getParameter("toPayAmt"))) {
			consgPriceDtls.setTopayChg(Double.parseDouble(request
					.getParameter("toPayAmt")));
		}
		consignmentTO.setConsgPriceDtls(consgPriceDtls);

		LOGGER.trace("OutManifestParcelAction :: prepareConsgTO() :: END");
		return consignmentTO;
	}

	/**
	 * To set booking details
	 * 
	 * @param consignmentTO
	 */
	private void setBookingDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelAction :: setBookingDtls() :: START");
		outManifestCommonService.setBookingDtls(consignmentTO);
		LOGGER.trace("OutManifestParcelAction :: setBookingDtls() :: END");
	}

	/**
	 * Two way write.
	 * 
	 * @param outManifestBaseTO
	 *            the in manifest to
	 */
	public void twoWayWrite(OutManifestBaseTO outManifestBaseTO) {
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		outManifestCommonService.twoWayWrite(outManifestBaseTO);
	}

	/**
	 * To check whether Weighing machine connected to requested client or not.
	 * (On load of screen) if connected, then it will return Y else N
	 * 
	 * @param request
	 * @return isWMConnected
	 */
	public String isWeighingMachineConnected(HttpServletRequest request) {
		LOGGER.trace("OutManifestAction :: isWeighingMachineConnected() :: START");
		String requestIpAddress = request.getRemoteHost();
		LOGGER.info("OutManifestAction :: isWeighingMachineConnected() :: request received for weight from client["
				+ requestIpAddress + "]");
		String weight = "-1";
		String isWMConnected = CommonConstants.EMPTY_STRING;
		try {
			String url = (String) request.getSession().getAttribute(
					UdaanCommonConstants.WEIGHT_READER_URL);
			LOGGER.info("OutManifestAction :: isWeighingMachineConnected() :: url["
					+ url + "]");
			if (!StringUtil.isStringEmpty(url)) {
				weight = TerminalUtil.getWeighingMachineReading(url);
			}
			LOGGER.info("OutManifestAction :: isWeighingMachineConnected() :: weight received from client :: "
					+ weight);
		} catch (Exception e) {
			LOGGER.error("OutManifestAction :: isWeighingMachineConnected() :: error :: "
					+ e.getMessage());
		} finally {
			isWMConnected = (!StringUtil.isStringEmpty(weight) && !weight
					.equals("-1")) ? CommonConstants.YES : CommonConstants.NO;
		}
		LOGGER.trace("OutManifestAction :: isWeighingMachineConnected() :: END");
		return isWMConnected;
	}

}
