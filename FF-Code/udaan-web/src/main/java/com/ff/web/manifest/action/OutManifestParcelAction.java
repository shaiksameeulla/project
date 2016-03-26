/*
 * 
 */
package com.ff.web.manifest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestParcelDetailsTO;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.constants.GlobalConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.form.OutManifestParcelForm;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.service.OutManifestParcelService;

/**
 * The Class OutManifestParcelAction.
 */
public class OutManifestParcelAction extends OutManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestParcelAction.class);

	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;

	/** The out manifest parcel service. */
	private OutManifestParcelService outManifestParcelService;

	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;

	/**
	 * View out manifest parcel.
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
	public ActionForward viewOutManifestParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			OutManifestParcelTO outManifestParcelTO = null;
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO
					.setManifestType(OutManifestConstants.OUT_MANIFEST);
			manifestFactoryTO
					.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			outManifestParcelTO = (OutManifestParcelTO) getManifestBasicDtls(
					manifestFactoryTO, request);
			outManifestParcelTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
			// set Regions
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			List<RegionTO> regionTOs = outManifestCommonService.getAllRegions();
			request.setAttribute(OutManifestConstants.REGION_TOS, regionTOs);
			outManifestParcelTO.setDestinationRegionList(regionTOs);

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

			// Set Office Type
			List<LabelValueBean> officeTypeList = outManifestCommonService
					.getOfficeTypeList();
			if (CGCollectionUtils.isEmpty(officeTypeList)) {
				throw new CGBusinessException(
						OutManifestConstants.ERROR_OFFICE_TYPE);
			}
			request.setAttribute(OutManifestConstants.OFFICE_TYPE_LIST,
					officeTypeList);
			// Setting process Code
			outManifestParcelTO
					.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
			// Set Configurable params
			if (!StringUtil.isEmptyMap(outManifestParcelTO
					.getConfigurableParams())) {
				outManifestParcelTO.setMaxCNsAllowed(outManifestParcelTO
						.getConfigurableParams().get(
								ManifestConstants.OPKT_PAX_MAX_CNS_ALLOWED));
				// Narasimha : Code modified by for weight tolerance
				Double maxWeight = Double.parseDouble(outManifestParcelTO
						.getConfigurableParams().get(
								ManifestConstants.OPKT_PAX_MAX_WEIGHT_ALLOWED));
				Double weightTolerrence = Double
						.parseDouble(outManifestParcelTO
								.getConfigurableParams()
								.get(ManifestConstants.OPKT_PAX_MAX_TOLLRENCE_ALLOWED));
				/*
				 * Double maxWeightAllowed = maxWeight + (maxWeight *
				 * weightTolerrence / 100);
				 */
				outManifestParcelTO.setMaxWeightAllowed(maxWeight.toString());
				outManifestParcelTO.setMaxTolerenceAllowed(weightTolerrence
						.toString());
			}
			setContentValues(request, response);

			String allowedConsgManifestedType = null;
			if (StringUtils.equalsIgnoreCase(
					outManifestParcelTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				allowedConsgManifestedType = ManifestConstants.BRANCH_MISROUTE
						+ "," + ManifestConstants.ORIGIN_MISROUTE;
			} else if (StringUtils.equalsIgnoreCase(
					outManifestParcelTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				allowedConsgManifestedType = ManifestConstants.ORIGIN_MISROUTE
						+ "," + ManifestConstants.MANIFEST_TYPE_RTO;
			}
			outManifestParcelTO
					.setAllowedConsgManifestedType(allowedConsgManifestedType);

			/** Setting request parameters */
			request.setAttribute(ManifestConstants.PARAM_CONSG_TYPE_PPX,
					CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			request.setAttribute(ManifestConstants.PARAM_CONSIGNOR,
					CommonConstants.INSURED_BY_DESC_CONSIGNOR);
			request.setAttribute(ManifestConstants.PARAM_INSURED_BY_TYPE_FFCL,
					CommonConstants.INSURED_BY_CODE_FFCL);
			request.setAttribute(
					ManifestConstants.PARAM_INSURED_BY_TYPE_CONSIGNOR,
					CommonConstants.INSURED_BY_CODE_CONSIGNOR);

			/** To set WM Connected flag */
			// outManifestParcelTO
			// .setIsWMConnected(isWeighingMachineConnected(request));

			((OutManifestParcelForm) form).setTo(outManifestParcelTO);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: viewOutManifestParcel() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: viewOutManifestParcel() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: viewOutManifestParcel() :: ",
					e);
			getGenericException(request, e);
		}
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	/**
	 * Prepare validate consg inputs.
	 * 
	 * @param request
	 *            the request
	 * @param cnValidateTO
	 *            the cn validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*
	 * private OutManifestValidate prepareValidateConsgInputs(
	 * HttpServletRequest request, OutManifestValidate cnValidateTO) throws
	 * CGSystemException, CGBusinessException { OfficeTO officeTO = null; CityTO
	 * cityTO = null; cnValidateTO = new OutManifestValidate();
	 * outManifestCommonService = (OutManifestCommonService)
	 * getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE); if
	 * (StringUtils.isNotEmpty(request
	 * .getParameter(OutManifestConstants.CONSIGNMENT_NUMBER))) {
	 * cnValidateTO.setConsgNumber(request
	 * .getParameter(OutManifestConstants.CONSIGNMENT_NUMBER)); } if
	 * (StringUtils.isNotEmpty(request
	 * .getParameter(OutManifestConstants.OFFICE_ID))) { officeTO = new
	 * OfficeTO(); officeTO.setOfficeId(Integer.parseInt(request
	 * .getParameter(OutManifestConstants.OFFICE_ID)));
	 * cnValidateTO.setDestOffice(officeTO); } Integer destCityId=null; if
	 * (StringUtils.isNotEmpty(request
	 * .getParameter(OutManifestConstants.CITY_ID))) { cityTO = new CityTO();
	 * destCityId=Integer.parseInt(request
	 * .getParameter(OutManifestConstants.CITY_ID));
	 * cityTO.setCityId(destCityId); cnValidateTO.setDestCityTO(cityTO); }
	 * 
	 * HttpSession session = null; UserInfoTO userInfoTO = null; session =
	 * (HttpSession) request.getSession(false); userInfoTO = (UserInfoTO)
	 * session .getAttribute(UmcConstants.USER_INFO); OfficeTO loginOfficeTO =
	 * userInfoTO.getOfficeTo();
	 * 
	 * String officeType=""; OfficeTypeTO officeTypeTO=null; if
	 * (StringUtils.isNotEmpty
	 * (request.getParameter(OutManifestConstants.OFFICE_TYPE))) {
	 * officeType=request.getParameter(OutManifestConstants.OFFICE_TYPE);
	 * officeTypeTO=new OfficeTypeTO();
	 * officeTypeTO.setOffcTypeId(Integer.parseInt(officeType));
	 * officeTypeTO=outManifestCommonService
	 * .getOfficeTypeDOByOfficeTypeIdOrCode(officeTypeTO); } //Origin Branch to
	 * Destination HUB String
	 * bplManifestType=request.getParameter("bplManifestType");
	 * if(!StringUtil.isNull(officeTypeTO) &&
	 * StringUtils.equalsIgnoreCase(loginOfficeTO
	 * .getOfficeTypeTO().getOffcTypeCode
	 * (),CommonConstants.OFF_TYPE_BRANCH_OFFICE) &&
	 * StringUtils.equalsIgnoreCase
	 * (officeTypeTO.getOffcTypeCode(),CommonConstants.OFF_TYPE_HUB_OFFICE)){
	 * if(StringUtils.equalsIgnoreCase(bplManifestType,OutManifestConstants.
	 * MANIFEST_TYPE_PURE)){
	 * cnValidateTO.setChkTransCityPincodeServ(CommonConstants.YES); }else
	 * if(StringUtils.equalsIgnoreCase(bplManifestType,OutManifestConstants.
	 * MANIFEST_TYPE_TRANSHIPMENT_CODE)){ List<Integer> transCityIds
	 * =outManifestCommonService.getServicedCityByTransshipmentCity(destCityId);
	 * transCityIds.add(destCityId);
	 * cnValidateTO.setTranshipmentCityIds(transCityIds);
	 * cnValidateTO.setChkTransCityPincodeServ(CommonConstants.YES); } }
	 * //Origin HUB to Destination HUB else if(!StringUtil.isNull(officeTypeTO)
	 * &&
	 * StringUtils.equalsIgnoreCase(loginOfficeTO.getOfficeTypeTO().getOffcTypeCode
	 * (),CommonConstants.OFF_TYPE_HUB_OFFICE) &&
	 * StringUtils.equalsIgnoreCase(officeTypeTO
	 * .getOffcTypeCode(),CommonConstants.OFF_TYPE_HUB_OFFICE)){
	 * if(StringUtils.equalsIgnoreCase
	 * (bplManifestType,OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)){
	 * List<Integer> transCityIds
	 * =outManifestCommonService.getServicedCityByTransshipmentCity(destCityId);
	 * transCityIds.add(destCityId);
	 * cnValidateTO.setTranshipmentCityIds(transCityIds);
	 * cnValidateTO.setChkTransCityPincodeServ(CommonConstants.YES); } }
	 * 
	 * cnValidateTO
	 * .setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT); if
	 * (StringUtils.isNotEmpty(request
	 * .getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) { officeTO = new
	 * OfficeTO(); officeTO.setOfficeId(Integer.parseInt(request
	 * .getParameter(OutManifestConstants.LOGIN_OFFICE_ID)));
	 * cnValidateTO.setOriginOffice(officeTO); } if
	 * (!StringUtil.isStringEmpty(request
	 * .getParameter("allowedConsgManifestedType"))) {
	 * cnValidateTO.setAllowedConsgManifestedType(request
	 * .getParameter("allowedConsgManifestedType")
	 * .split(CommonConstants.COMMA)); } else {
	 * cnValidateTO.setAllowedConsgManifestedType( new
	 * String[]{CommonConstants.EMPTY_STRING}); } if
	 * (StringUtils.isNotEmpty(request.getParameter("isPincodeServChk"))) {
	 * cnValidateTO
	 * .setIsPincodeServChkReq(request.getParameter("isPincodeServChk")); }
	 * ConsignmentTypeTO typeTO=new ConsignmentTypeTO();
	 * typeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
	 * cnValidateTO.setConsignmentTypeTO(typeTO); return cnValidateTO; }
	 */

	/**
	 * Sets the content values.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void setContentValues(HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException {
		outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
		List<CNContentTO> cnContentTOs = outManifestCommonService
				.getContentValues();
		request.setAttribute("contentVal", cnContentTOs);
		List<InsuredByTO> insuredDtls = outManifestParcelService
				.getInsuredByDtls();
		request.setAttribute("insurance", insuredDtls);
	}

	/**
	 * Gets the paper works.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the paper works
	 */
	@SuppressWarnings("static-access")
	public void getPaperWorks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		String jsonResult = null;
		String pincode = CommonConstants.EMPTY_STRING;
		Double declaredValue = 0.00;
		try {
			out = response.getWriter();
			pincode = request.getParameter("pincode");
			if (StringUtils.isNotEmpty(request.getParameter("declaredValue"))) {
				declaredValue = Double.parseDouble(request
						.getParameter("declaredValue"));
				CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
				paperWorkValidationTO.setPincode(pincode);
				paperWorkValidationTO
						.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				paperWorkValidationTO.setDeclatedValue(declaredValue);

				outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
				cnPaperWorksTOs = outManifestParcelService
						.getPaperWorks(paperWorkValidationTO);
			}
			if (!StringUtil.isNull(cnPaperWorksTOs)) {
				jsonResult = serializer.toJSON(cnPaperWorksTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: getPaperWorks() ::", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: getPaperWorks() ::", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: getPaperWorks() ::", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}

	/**
	 * Validate declaredvalue.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("static-access")
	public void validateDeclaredvalue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			bookingValidateTO = new BookingValidationTO();
			out = response.getWriter();
			Double declaredValue = Double.parseDouble(request
					.getParameter("declaredVal"));
			String bookingType = request.getParameter("bookingType");
			bookingValidateTO.setDeclaredValue(declaredValue);
			bookingValidateTO.setBookingType(bookingType);
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			bookingValidateTO = outManifestCommonService
					.validateDeclaredValue(bookingValidateTO);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: validateDeclatedvalue() ::",
					e);
			errorMsg = getBusinessErrorFromWrapper(request, e);
			bookingValidateTO = new BookingValidationTO();
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: validateDeclatedvalue() ::",
					e);
			errorMsg = getSystemExceptionMessage(request, e);
			bookingValidateTO = new BookingValidationTO();
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: validateDeclatedvalue() ::",
					e);
			errorMsg = getGenericExceptionMessage(request, e);
			bookingValidateTO = new BookingValidationTO();
			bookingValidateTO.setErrorMsg(errorMsg);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
	}

	/**
	 * Save or update out manifest parcel.
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
	public void saveOrUpdateOutManifestParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OutManifestParcelForm outMnfstParcelForm = null;
		OutManifestParcelTO outmanifestParcelTO = null;
		String transMsg = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);

			outMnfstParcelForm = (OutManifestParcelForm) form;
			outmanifestParcelTO = (OutManifestParcelTO) outMnfstParcelForm
					.getTo();
			outmanifestParcelTO
					.setManifestType(OutManifestConstants.OUT_MANIFEST);

			setOutmanifestParcelTO(outmanifestParcelTO);
			if (!StringUtil.isNull(outmanifestParcelTO)) {
				if (StringUtil.isEmpty(outmanifestParcelTO.getConsgNos())) {
					throw new CGBusinessException(
							OutManifestConstants.VALIDATE_GRID);
				}

				rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
						.getAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
				outmanifestParcelTO.setRateCompnents(rateCompnents);

				outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
				transMsg = outManifestParcelService
						.saveOrUpdateOutManifestParcel(outmanifestParcelTO);
				session.removeAttribute(ManifestConstants.MANIFEST_CONSG_RATE_DTLS);
				/** Call Two way write */
				if (outmanifestParcelTO.getManifestStatus().equalsIgnoreCase(
						OutManifestConstants.CLOSE)) {
					twoWayWrite(outmanifestParcelTO);
				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"OutManifestParcelAction :: saveOrUpdateOutManifestParcel() ::",
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
				LOGGER.debug("OutManifestParcelAction :: saveOrUpdateOutManifestParcel() :: ..CGBusinessException :: ERROR Message : "
						+ transMsg);
			} else {
				transMsg = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getBusinessErrorFromWrapper(request, e));
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"OutManifestParcelAction :: saveOrUpdateOutManifestParcel() ::",
					e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error(
					"OutManifestParcelAction :: saveOrUpdateOutManifestParcel() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			transMsg = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(transMsg);
			out.flush();
			out.close();
		}

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
		PrintWriter out = null;
		String jsonResult = null;
		OutManifestParcelTO outManifestParcelTO = null;
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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);

				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
				outManifestParcelTO = outManifestParcelService
						.searchManifestDtls(manifestTO);

			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"OutManifestParcelAction :: searchManifestDetails() ::", e);
			String errorMsg = getBusinessErrorFromWrapper(request, e);
			outManifestParcelTO = new OutManifestParcelTO();
			outManifestParcelTO.setErrorMsg(errorMsg);
		} catch (CGSystemException e) {
			LOGGER.error(
					"OutManifestParcelAction :: searchManifestDetails() ::", e);
			String errorMsg = getSystemExceptionMessage(request, e);
			outManifestParcelTO = new OutManifestParcelTO();
			outManifestParcelTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			String errorMsg = getGenericExceptionMessage(request, e);
			outManifestParcelTO = new OutManifestParcelTO();
			outManifestParcelTO.setErrorMsg(errorMsg);
			LOGGER.error(
					"OutManifestParcelAction :: searchManifestDetails() ::", e);
		} finally {
			jsonResult = createJsonObject(outManifestParcelTO);
			out.print(jsonResult);
			out.flush();
			out.close();
		}
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

	/*
	 * public void getInManifestedConsignmentDetails(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) throws CGBusinessException, CGSystemException { String
	 * cnValidationJSON = CommonConstants.EMPTY_STRING; ManifestFactoryTO
	 * manifestFactoryTO = null; OutManifestParcelDetailsTO outManifestPpxDtlTO
	 * = null; PrintWriter out = null; try{ String consignmentNo = request
	 * .getParameter(OutManifestConstants.CONSIGNMENT_NO); if
	 * (StringUtils.isNotEmpty(consignmentNo)) { manifestFactoryTO = new
	 * ManifestFactoryTO(); manifestFactoryTO.setConsgNumber(consignmentNo);
	 * manifestFactoryTO .setManifestType(OutManifestConstants.OUT_MANIFEST);
	 * manifestFactoryTO
	 * .setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT); }
	 * outManifestParcelService = (OutManifestParcelService)
	 * getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE); outManifestPpxDtlTO
	 * = outManifestParcelService
	 * .getInManifestdConsignmentDtls(manifestFactoryTO); out =
	 * response.getWriter(); cnValidationJSON =
	 * serializer.toJSON(outManifestPpxDtlTO).toString(); } }
	 */
	/**
	 * @Desc print OutManifest dox details from database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward printOutManifestParcelDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
				OutManifestParcelTO outManifestParcelTO = outManifestParcelService
						.searchManifestDtls(manifestTO);

				request.setAttribute("outManifestParcelTO", outManifestParcelTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: printOutManifestParcelDtls() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: printOutManifestParcelDtls() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: OutManifestParcelAction :: printOutManifestParcelDtls() :: ",
					e);
			getGenericException(request, e);
		}
		LOGGER.debug("OutManifestParcelAction::printOutManifestParcelDtls::END");
		return mapping
				.findForward(ManifestConstants.URL_PRINT_OUTMANIFEST_PARCEL);
	}

	// Ami Added on 2009
	@SuppressWarnings({ "static-access", "unchecked" })
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("OutManifestParcelAction::getConsignmentDtls::START");
		PrintWriter out = null;
		String jsonResult = "";
		String manifestType = "";
		String loginOfficeType = "";
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
		OfficeTO officeTO = null;
		CityTO cityTO = null;
		Integer destCityId = null;
		HttpSession session = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);
			cnValidateTO = new OutManifestValidate();
			// origin branch to origin hub transhipment :allow any destn consg
			if (StringUtils.isNotEmpty(request.getParameter("bplManifestType"))) {

				manifestType = request.getParameter("bplManifestType");
				if (StringUtils.isNotEmpty(request
						.getParameter("loginOfficeType"))) {
					loginOfficeType = request.getParameter("loginOfficeType");

					if (manifestType
							.equalsIgnoreCase(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)
							&& loginOfficeType
									.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
						cnValidateTO.setIsPincodeServChkReq(CommonConstants.NO);
					} else if (manifestType
							.equalsIgnoreCase(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
						destCityId = Integer.parseInt(request
								.getParameter(OutManifestConstants.CITY_ID));
						if (!StringUtil.isEmptyInteger(destCityId)) {
							List<Integer> transCityIds = outManifestCommonService
									.getServicedCityByTransshipmentCity(destCityId);
							transCityIds.add(destCityId);
							cnValidateTO.setTranshipmentCityIds(transCityIds);
						}
					}

				}
			}

			if (StringUtils.isNotEmpty(request
					.getParameter("manifestDirection"))) {
				cnValidateTO.setManifestDirection(request
						.getParameter("manifestDirection"));
			}
			if (!StringUtil.isStringEmpty(request
					.getParameter("allowedConsgManifestedType"))) {
				cnValidateTO.setAllowedConsgManifestedType(request
						.getParameter("allowedConsgManifestedType").split(
								CommonConstants.COMMA));
			} else {
				cnValidateTO
						.setAllowedConsgManifestedType(new String[] { CommonConstants.EMPTY_STRING });
			}
			if (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
				officeTO = new OfficeTO();
				officeTO.setOfficeId(Integer.parseInt(request
						.getParameter("loginOfficeId")));
				cnValidateTO.setOriginOffice(officeTO);
			}

			if (StringUtils.isNotEmpty(request.getParameter("consgNumber"))) {
				cnValidateTO
						.setConsgNumber(request.getParameter("consgNumber"));
			}
			if (StringUtils.isNotEmpty(request.getParameter("officeId"))) {
				officeTO = new OfficeTO();
				officeTO.setOfficeId(Integer.parseInt(request
						.getParameter("officeId")));
				cnValidateTO.setDestOffice(officeTO);
			}
			String officeType = "";
			OfficeTypeTO officeTypeTO = null;
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.OFFICE_TYPE))) {
				officeType = request
						.getParameter(OutManifestConstants.OFFICE_TYPE);
				officeTypeTO = new OfficeTypeTO();
				officeTypeTO.setOffcTypeId(Integer.parseInt(officeType));
				officeTypeTO = outManifestCommonService
						.getOfficeTypeDOByOfficeTypeIdOrCode(officeTypeTO);
				OfficeTO destOffice = cnValidateTO.getDestOffice();
				if (StringUtil.isNull(destOffice)) {
					destOffice = new OfficeTO();
				}
				destOffice.setOfficeTypeTO(officeTypeTO);
				cnValidateTO.setDestOffice(destOffice);
			}
			if (StringUtils.isNotEmpty(request.getParameter("cityId"))) {
				cityTO = new CityTO();
				cityTO.setCityId(Integer.parseInt(request
						.getParameter("cityId")));
				cnValidateTO.setDestCityTO(cityTO);
			}
			if (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
				cnValidateTO.setManifestNumber(request
						.getParameter("manifestNo"));
			}

			manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());

			ConsignmentTypeTO consTypeTO = new ConsignmentTypeTO();
			consTypeTO
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE);
			cnValidateTO.setConsignmentTypeTO(consTypeTO);
			cnValidateTO
					.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);

			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			cnValidateTO = outManifestCommonService
					.validateConsignment(cnValidateTO);

			/*
			 * In manifested check not allowed for branch - only booked CN
			 * allowed from branch for out manifest
			 */
			if (loginOfficeType.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE)
					&& cnValidateTO.getIsConsInManifestd().equalsIgnoreCase(
							CommonConstants.YES)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_NOTBOOKED_IN_OPERATING_OFFICE);
			}

			OutManifestParcelDetailsTO outManifestPPXDtlTO = null;

			// gets the consignment details
			outManifestParcelService = (OutManifestParcelService) getBean(SpringConstants.OUT_MANIFEST_PARCEL_SERVICE);
			outManifestPPXDtlTO = outManifestParcelService.getConsignmentDtls(
					manifestFactoryTO, cnValidateTO);

			if ((cnValidateTO.getIsBulkBookedCN())
					.equalsIgnoreCase(CommonConstants.YES)
					&& (cnValidateTO.getIsCNProcessedFromPickup()
							.equalsIgnoreCase(CommonConstants.NO))) {
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
						.calculateRateForConsignment(cnTO);

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

			if (outManifestPPXDtlTO != null) {
				if (!StringUtil.isNull(cnValidateTO.getUpdatedProcessFrom())
						&& StringUtil.equals(cnValidateTO
								.getUpdatedProcessFrom().getProcessCode(),
								ManifestConstants.PICKUP_PROCESS_CODE)) {
					outManifestPPXDtlTO.setIsPickupCN(CommonConstants.YES);
				} else {
					outManifestPPXDtlTO.setIsPickupCN(CommonConstants.NO);
				}
				jsonResult = serializer.toJSON(outManifestPPXDtlTO).toString();
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG, "No Details found");
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in OutManifestParcelAction :: ..:getConsignmentDtls()"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in OutManifestParcelAction :: ..:getConsignmentDtls()"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestParcelAction :: ..:getConsignmentDtls()"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("OutManifestParcelAction::getConsignmentDtls::END");

	}

	// Ami Added on 2009 starts

	@SuppressWarnings("static-access")
	private String createJsonObject(OutManifestParcelTO outManifestParcelTO) {
		LOGGER.debug("OutManifestParcelAction :: createJsonObject() :: START------------>:::::::");
		StringBuilder jsonResult = null;
		if (!StringUtil.isNull(outManifestParcelTO)) {
			jsonResult = new StringBuilder();
			String doxToJson = serializer.toJSON(outManifestParcelTO)
					.toString();
			String detailToJson = null;
			jsonResult = appendListName(jsonResult, doxToJson, "ppxTO");
			if (!StringUtil.isEmptyList(outManifestParcelTO
					.getOutManifestParcelDetailsList())) {
				Collections.sort(outManifestParcelTO
						.getOutManifestParcelDetailsList());
				detailToJson = serializer.toJSON(
						outManifestParcelTO.getOutManifestParcelDetailsList())
						.toString();
			}
			jsonResult = appendListName(jsonResult, detailToJson, "detailTO");
		}
		LOGGER.debug("OutManifestParcelAction :: createJsonObject() :: END------------>:::::::");
		return jsonResult.toString();
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
		LOGGER.trace("OutManifestParcelAction :: appendListName() :: START------------>:::::::");
		stringBuilder
				.append(CommonConstants.TILD)
				.append(CommonConstants.OPENING_CURLY_BRACE)
				.append(CommonConstants.OPENING_INNER_QOUTES + listName
						+ CommonConstants.CLOSING_INNER_QOUTES)
				.append(CommonConstants.CHARACTER_COLON).append(ajaxResponse)
				.append(CommonConstants.CLOSING_CURLY_BRACE);
		LOGGER.trace("OutManifestParcelAction :: appendListName() :: END------------>:::::::");
		return stringBuilder;
	}

	private void setOutmanifestParcelTO(OutManifestParcelTO outmanifestParcelTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("OutManifestParcelAction :: setoutmanifestParcelTO() :: START ::------------>:::::::");
		OfficeTO loginOfficeTO = null;
		Integer operatingLevel = outmanifestParcelTO.getOperatingLevel();
		outManifestUniversalService = (OutManifestUniversalService) getBean(SpringConstants.OUT_MANIFEST_UNIVERSAL_SERVICE);
		// If manifest is creating first time, set the below values
		if (StringUtil.isEmptyInteger(outmanifestParcelTO.getManifestId())) {
			// Setting Login Office details
			loginOfficeTO = new OfficeTO();
			loginOfficeTO.setOfficeId(outmanifestParcelTO.getLoginOfficeId());
			if (StringUtils.isNotEmpty(outmanifestParcelTO.getOfficeCode())) {
				loginOfficeTO
						.setOfficeCode(outmanifestParcelTO.getOfficeCode());
			}

			// calculating operating level
			try {
				operatingLevel = outManifestUniversalService
						.calcOperatingLevel(outmanifestParcelTO, loginOfficeTO);
			} catch (Exception e) {
				LOGGER.error(
						"ERROR :: OutManifestParcelAction :: setOutmanifestParcelTO() ::",
						e);
				if (!StringUtil.isEmptyInteger(outmanifestParcelTO
						.getOperatingLevel()))
					operatingLevel = outmanifestParcelTO.getOperatingLevel();
				else
					operatingLevel = null;
			}
			outmanifestParcelTO.setOperatingLevel(operatingLevel);

			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO
					.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
			processTO = outManifestUniversalService.getProcess(processTO);
			outmanifestParcelTO.setProcessId(processTO.getProcessId());
			outmanifestParcelTO.setProcessCode(processTO.getProcessCode());

			// Setting process number
			String processNumber = outManifestCommonService
					.createProcessNumber(processTO, loginOfficeTO);
			outmanifestParcelTO.setProcessNo(processNumber);
		}

		// Getting Consignment Type details based on
		ConsignmentTypeTO consgType = new ConsignmentTypeTO();
		consgType.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		outmanifestParcelTO.setConsignmentTypeTO(consgType);
		List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
				.getConsignmentTypes(consgType);
		// Setting Consignment type id
		if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
			ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs.get(0);
			outmanifestParcelTO.setConsignmentTypeTO(consignmentTypeTO);
		}

		// The below attributes will always get updated
		if (outmanifestParcelTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
			outmanifestParcelTO.setIsMulDestination(CommonConstants.YES);
		}
		/**
		 * changes related to out manifest destination office ids = origin city
		 * All hub offices + destination city all hub offices + selected
		 * destination office
		 **/

		List<Integer> destHubList = null;
		List<OfficeTO> destOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						outmanifestParcelTO.getDestinationCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			destHubList = new ArrayList<>();
			for (OfficeTO officeTO1 : destOfficeTOs) {
				destHubList.add(officeTO1.getOfficeId());
			}
			outmanifestParcelTO.setDestHubOffList(destHubList);
		}

		// If the Operating level is less than = 10 i.e. the origin Branch
		// then only add the origin hubs in out manifest destinations
		if (operatingLevel <= 10) {
			List<Integer> originHubList = null;
			List<OfficeTO> orgOfficeTOs = outManifestCommonService
					.getAllOfficesByCityAndOfficeTypeCode(
							outmanifestParcelTO.getLoginCityId(),
							CommonConstants.OFF_TYPE_HUB_OFFICE);
			if (!StringUtil.isEmptyList(destOfficeTOs)) {
				originHubList = new ArrayList<>();
				for (OfficeTO officeTO2 : orgOfficeTOs) {
					originHubList.add(officeTO2.getOfficeId());
				}
				outmanifestParcelTO.setOriginHubOffList(originHubList);
			}
		}
		LOGGER.trace("OutManifestParcelAction :: setOutmanifestParcelTO() :: END------------>:::::::");
	}

}
