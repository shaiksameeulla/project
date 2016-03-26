package com.ff.web.manifest.rthrto.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestDoxTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.converter.RthRtoManifestDoxConverter;
import com.ff.web.manifest.rthrto.form.RthRtoManifestDoxForm;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.rthrto.service.RthRtoManifestDoxService;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestDoxAction.
 * 
 * @author hkansagr
 */
public class RthRtoManifestDoxAction extends AbstractRthRtoManifestAction {

	/** The Logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoManifestDoxAction.class);

	/** The rthRtoManifestCommonService. */
	private RthRtoManifestCommonService rthRtoManifestCommonService;

	/** The RthRtoManifestDoxService. */
	private RthRtoManifestDoxService rthRtoManifestDoxService;

	/**
	 * To view the details for RTH Manifest DOX.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return to view rthManifestDox screen
	 */
	public ActionForward viewRthManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::viewRthManifestDox()::START");
		RthRtoManifestDoxTO to = null;
		String url = RthRtoManifestConstatnts.VIEW_RTH_MANIFEST_DOX;
		try {
			/*if (!isBranchOffice(request)) {
				url = UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(
						RthRtoManifestConstatnts.RTH_ONLY_ALLOWED_AT_BRANCH_OFFICE);
				prepareActionMessage(request, actionMessage);
			} else {*/
			if (isBranchOffice(request) || isHubOffice(request)){
				RthRtoManifestDoxForm doxForm = new RthRtoManifestDoxForm();
				to = (RthRtoManifestDoxTO) doxForm.getTo();
				to.setManifestType(CommonConstants.MANIFEST_TYPE_RTH);
				setDefaultValues(request, to);
				saveToken(request);
				((RthRtoManifestDoxForm) form).setTo(to);
			}
		}/*
		 * catch (CGSystemException e) { getSystemException(request, e);
		 * LOGGER.error
		 * ("Exception happened in viewRthManifestDox of RthRtoManifestDoxAction..."
		 * , e); } catch (CGBusinessException e) { getBusinessError(request, e);
		 * LOGGER.error(
		 * "Exception happened in viewRthManifestDox of RthRtoManifestDoxAction..."
		 * , e); }
		 */catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewRthManifestDox of RthRtoManifestDoxAction..."
					, e);
		}
		LOGGER.trace("RthRtoManifestDoxAction::viewRthManifestDox()::END");
		return mapping.findForward(url);
	}

	/**
	 * To view the details for RTO Manifest DOX.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return to view rtoManifestDox screen
	 */
	public ActionForward viewRtoManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::viewRtoManifestDox()::START");
		RthRtoManifestDoxTO to = null;
		String url = RthRtoManifestConstatnts.VIEW_RTO_MANIFEST_DOX;
		try {
			if (!isHubOffice(request)) {
				url = UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(
						RthRtoManifestConstatnts.RTO_ONLY_ALLOWED_AT_HUB_OFFICE);
				prepareActionMessage(request, actionMessage);
			} else {
				RthRtoManifestDoxForm doxForm = new RthRtoManifestDoxForm();
				to = (RthRtoManifestDoxTO) doxForm.getTo();
				to.setManifestType(CommonConstants.MANIFEST_TYPE_RTO);
				setDefaultValues(request, to);
				saveToken(request);
				((RthRtoManifestDoxForm) form).setTo(to);
			}
		}/*
		 * catch (CGSystemException e) { getSystemException(request, e);
		 * LOGGER.error
		 * ("Exception happened in viewRtoManifestDox of RthRtoManifestDoxAction..."
		 * , e); } catch (CGBusinessException e) { getBusinessError(request, e);
		 * LOGGER.error(
		 * "Exception happened in viewRtoManifestDox of RthRtoManifestDoxAction..."
		 * , e); }
		 */catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewRtoManifestDox of RthRtoManifestDoxAction..."
					, e);
		}
		LOGGER.trace("RthRtoManifestDoxAction::viewRtoManifestDox()::END");
		return mapping.findForward(url);
	}

	/**
	 * To set Default value(s).
	 * 
	 * @param request
	 *            the request
	 * @param to
	 *            the to
	 */
	private void setDefaultValues(HttpServletRequest request,
			RthRtoManifestDoxTO to) {
		LOGGER.trace("RthRtoManifestDoxAction::setDefaultValues()::START");
		try {
			/* setting current date */
			if (StringUtil.isStringEmpty(to.getManifestDate()))
				to.setManifestDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

			/* setting login details */
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO officeTO = userInfoTO.getOfficeTo();

			/* setting dispatching office as login screen */
			to.setOriginOfficeTO(officeTO);

			rthRtoManifestCommonService = getRthRtoManifestCommonService();

			List<OfficeTO> officeTOs = null;
			List<RegionTO> regionTOs = null;
			List<CityTO> citiesByRegion = null;

			if (StringUtil.equals(to.getManifestType(),
					CommonConstants.MANIFEST_TYPE_RTH)) {
				if(!isBranchOffice(request)){
					//HUB
					citiesByRegion = rthRtoManifestCommonService.getCitiesByRegion(officeTO.getRegionTO().getRegionId());
				}else{
					//Branch
					/* setting destination office(s) */
					OfficeTO loginOfficeTO = new OfficeTO();
					loginOfficeTO.setCityId(officeTO.getCityId());
					OfficeTypeTO officeTypeTO = new OfficeTypeTO();
					officeTypeTO.setOffcTypeCode(CommonConstants.OFF_TYPE_HUB_OFFICE);
					loginOfficeTO.setOfficeTypeTO(officeTypeTO);
					officeTOs = rthRtoManifestCommonService.getOfficesByOffice(loginOfficeTO);
				}
			} else if (StringUtil.equals(to.getManifestType(),
					CommonConstants.MANIFEST_TYPE_RTO)) {
				/* setting region list */
				regionTOs = rthRtoManifestCommonService.getAllRegions();
			}

			/* setting origin city code */
			CityTO cityTO = new CityTO();
			cityTO.setCityId(officeTO.getCityId());
			List<CityTO> cityTOs = rthRtoManifestCommonService
					.getCitiesByCity(cityTO);
			if (!StringUtil.isEmptyList(cityTOs)) {
				String originCityCode = cityTOs.get(0).getCityCode();
				to.setOriginCityCode(originCityCode);
			}

			/* setting consignment type */
			ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			List<ConsignmentTypeTO> consignmentTypeTOs = rthRtoManifestCommonService
					.getConsignmentTypes(consignmentTypeTO);
			if (!StringUtil.isEmptyList(consignmentTypeTOs))
				to.setConsignmentTypeTO(consignmentTypeTOs.get(0));

			/* setting reason(s) list */
			ReasonTO reasonTO = new ReasonTO();
			reasonTO.setReasonType(UdaanCommonConstants.REASON_TYPE_FOR_RTO_RTH_DOX);
			List<ReasonTO> reasonTOs = rthRtoManifestCommonService
					.getReasonsByReasonType(reasonTO);

			/* setting RTH RTO constant(s) */
			request.setAttribute(RthRtoManifestConstatnts.MANIFEST_TYPE_RTH,
					CommonConstants.MANIFEST_TYPE_RTH);
			request.setAttribute(RthRtoManifestConstatnts.MANIFEST_TYPE_RTO,
					CommonConstants.MANIFEST_TYPE_RTO);

			/* setting Series Type OGM/BPL No */
			request.setAttribute(RthRtoManifestConstatnts.SERIES_TYPE_OGM_NO,
					UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
			request.setAttribute(RthRtoManifestConstatnts.SERIES_TYPE_BPL_NO,
					UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);

			/* setting object(s) to request */
			request.setAttribute(RthRtoManifestConstatnts.CITY_LIST, citiesByRegion);
			request.setAttribute(RthRtoManifestConstatnts.OFFICE_LIST,
					officeTOs);
			request.setAttribute(RthRtoManifestConstatnts.REASONS_LIST,
					reasonTOs);
			request.setAttribute(RthRtoManifestConstatnts.REGION_LIST,
					regionTOs);
			request.setAttribute(RthRtoManifestConstatnts.LOGIN_OFFICE_TYPE, officeTO.getOfficeTypeTO().getOffcTypeCode());
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in setDefaultValues of RthRtoManifestDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in setDefaultValues of RthRtoManifestDoxAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in setDefaultValues of RthRtoManifestDoxAction..."
					, e);
		}
		LOGGER.trace("RthRtoManifestDoxAction::setDefaultValues()::END");
	}

	/**
	 * To get consignment details.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the consignment dtls
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::getConsignmentDtls()::START");
		ConsignmentValidationTO consigValidationTO = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		RthRtoDetailsTO rthRtoManifestDtlTO = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			consigValidationTO = prepareConsgValidateInputs(request);
			if (!StringUtil.isNull(consigValidationTO)) {
				rthRtoManifestCommonService = getRthRtoManifestCommonService();
				rthRtoManifestDtlTO = rthRtoManifestCommonService
						.getConsignmentDetails(consigValidationTO);
			}
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getConsignmentDtls of RthRtoManifestDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getConsignmentDtls of RthRtoManifestDoxAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getConsignmentDtls of RthRtoManifestDoxAction..."
					, e);
		} finally {
			if (StringUtil.isNull(rthRtoManifestDtlTO)) {
				rthRtoManifestDtlTO = new RthRtoDetailsTO();
			}
			rthRtoManifestDtlTO.setErrorMsg(errorMsg);
			jsonResult = serializer.toJSON(rthRtoManifestDtlTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RthRtoManifestDoxAction::getConsignmentDtls()::END");
	}

	/**
	 * To prepare consignment validate input(s).
	 * 
	 * @param request
	 *            the request
	 * @return consigValidationTO
	 */
	private ConsignmentValidationTO prepareConsgValidateInputs(
			HttpServletRequest request) {
		LOGGER.trace("RthRtoManifestDoxAction::prepareConsgValidateInputs()::START");
		ConsignmentValidationTO consigValidationTO = null;
		if (!StringUtil.isStringEmpty(request
				.getParameter(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER))) {
			consigValidationTO = new ConsignmentValidationTO();
			consigValidationTO.setConsgNumber(request
					.getParameter(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER));
			consigValidationTO.setManifestType(request
					.getParameter(RthRtoManifestConstatnts.MANIFEST_TYPE));
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			typeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			consigValidationTO.setConsignmentTypeTO(typeTO);
			if (StringUtils.isNotEmpty(request
					.getParameter(RthRtoManifestConstatnts.ORIGIN_OFFICE_ID))) {
				Integer originOffice = Integer
						.parseInt(request
								.getParameter(RthRtoManifestConstatnts.ORIGIN_OFFICE_ID));
				consigValidationTO.setOriginOffice(originOffice);
			}
			if (StringUtils
					.isNotEmpty(request
							.getParameter(RthRtoManifestConstatnts.DESTINATION_CITY_ID))) {
				Integer destCityId = Integer
						.parseInt(request
								.getParameter(RthRtoManifestConstatnts.DESTINATION_CITY_ID));
				consigValidationTO.setDestCityId(destCityId);
			}
			/** Added by Himal - Setting consignment return type for validate */
			setConsgReturnType(consigValidationTO);
		}
		LOGGER.trace("RthRtoManifestDoxAction::prepareConsgValidateInputs()::END");
		return consigValidationTO;
	}

	/**
	 * To save or update RTH/RTO Manifest DOX Details.
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
	public void saveOrUpdateRthRtoManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::saveOrUpdateRthRtoManifestDox()::START");
		RthRtoManifestDoxTO rthRtoManifestDoxTO = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String transMsg = null;
		String errorMsg = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);
			RthRtoManifestDoxForm doxForm = (RthRtoManifestDoxForm) form;
			rthRtoManifestDoxTO = (RthRtoManifestDoxTO) doxForm.getTo();
			List<RthRtoDetailsTO> rthRtoDetailTO = RthRtoManifestDoxConverter
					.rthRtoDetailListConverter(rthRtoManifestDoxTO);
			rthRtoManifestDoxTO.setRthRtoDetailsTOs(rthRtoDetailTO);
			rthRtoManifestDoxService = getRthRtoManifestDoxService();
			rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
					.getAttribute(RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS);
			if(!StringUtil.isNull(rateCompnents)){
			 rthRtoManifestDoxTO.setRateCompnents(rateCompnents);
			 session.removeAttribute(RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS);
			}
			rthRtoManifestDoxTO = rthRtoManifestDoxService
					.saveOrUpdateRthRtoManifestDox(rthRtoManifestDoxTO);

			//calling TwoWayWrite service to save same in central
			twoWayWrite(rthRtoManifestDoxTO);
			
			transMsg = getMessageFromErrorBundle(request,
					ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
					null);

		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			// errorMsg =
			// errorMessages.getString(RthRtoManifestConstatnts.DATA_NOT_SAVED_DB_ISSUE);
			LOGGER.error("Exception happened in saveOrUpdateRthRtoManifestDox of RthRtoManifestDoxAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateRthRtoManifestDox of RthRtoManifestDoxAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in saveOrUpdateRthRtoManifestDox of RthRtoManifestDoxAction..."
					, e);
		} finally {
			if (rthRtoManifestDoxTO == null) {
				rthRtoManifestDoxTO = new RthRtoManifestDoxTO();
			}
			rthRtoManifestDoxTO.setErrorMsg(errorMsg);
			rthRtoManifestDoxTO.setTransMsg(transMsg);
			jsonResult = serializer.toJSON(rthRtoManifestDoxTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RthRtoManifestDoxAction::saveOrUpdateRthRtoManifestDox()::END");
	}

	/**
	 * To search the manifest details for RTH DOX.
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
	public ActionForward searchRthRtoManifestDoxDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::searchRthManifestDoxDtls()::START");
		ActionMessage actionMessage = null;
		RthRtoManifestDoxForm doxForm = null;
		RthRtoManifestDoxTO to = null;
		String manifestType = null;
		boolean isException = Boolean.FALSE;
		try {
			doxForm = (RthRtoManifestDoxForm) form;
			to = (RthRtoManifestDoxTO) doxForm.getTo();
			manifestType = to.getManifestType();
			rthRtoManifestCommonService = getRthRtoManifestCommonService();
			rthRtoManifestCommonService.searchRTOHManifestDetails(to);
			/* check any warnings/Business Exceptions */
			boolean errorStatus = ExceptionUtil.checkError(to);
			if (errorStatus) {
				/* if so extract them and propagate to screen */
				ExceptionUtil.prepareActionMessage(to, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error(
					"Exception happened in searchRthRtoManifestDoxDtls of RthRtoManifestDoxAction...",
					e);
			RthRtoManifestDoxForm rtohForm = new RthRtoManifestDoxForm();
			to = (RthRtoManifestDoxTO) (rtohForm.getTo());
			isException = Boolean.TRUE;
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error(
					"Exception happened in searchRthRtoManifestDoxDtls of RthRtoManifestDoxAction...",
					e);
			RthRtoManifestDoxForm rtohForm = new RthRtoManifestDoxForm();
			to = (RthRtoManifestDoxTO) (rtohForm.getTo());
			isException = Boolean.TRUE;
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error(
					"Exception happened in searchRthRtoManifestDoxDtls of RthRtoManifestDoxAction...",
					e);
			RthRtoManifestDoxForm rtohForm = new RthRtoManifestDoxForm();
			to = (RthRtoManifestDoxTO) (rtohForm.getTo());
			isException = Boolean.TRUE;
		} finally {
			prepareActionMessage(request, actionMessage);
			/*
			 * if (to == null) { doxForm = (RthRtoManifestDoxForm) form; to =
			 * (RthRtoManifestDoxTO) doxForm.getTo(); manifestType =
			 * to.getManifestType(); }
			 */
			if (isException) {
				String url = getRtohDoxURL(manifestType);
				request.setAttribute(
						RthRtoManifestConstatnts.PARAM_RTOH_DOX_URL, url);
			}
			to.setManifestType(manifestType);
			setDefaultValues(request, to);
		}
		((RthRtoManifestDoxForm) form).setTo(to);
		LOGGER.trace("RthRtoManifestDoxAction::searchRthManifestDoxDtls()::END");
		if (StringUtil.equals(to.getManifestType(),
				CommonConstants.MANIFEST_TYPE_RTH)) {/* RTH - "T" */
			return mapping
					.findForward(RthRtoManifestConstatnts.VIEW_RTH_MANIFEST_DOX);
		} else {/* RTO - "R" */
			return mapping
					.findForward(RthRtoManifestConstatnts.VIEW_RTO_MANIFEST_DOX);
		}
	}

	/**
	 * To get RTO/RTH DOX URL
	 * 
	 * @param manifestType
	 * @return rtohDoxURL
	 */
	private String getRtohDoxURL(String manifestType) {
		String rtohDoxURL = CommonConstants.EMPTY_STRING;
		if (StringUtil.equals(manifestType, CommonConstants.MANIFEST_TYPE_RTH)) {
			// RTH - H
			rtohDoxURL = "./rthRtoManifestDox.do?submitName=viewRthManifestDox";
		} else {
			// RTO - R
			rtohDoxURL = "./rthRtoManifestDox.do?submitName=viewRtoManifestDox";
		}
		return rtohDoxURL;
	}

	/**
	 * To validate the RTH/RTO manifest number during save.
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
	public void isRtohNoManifested(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::isRtohNoManifested()::START");
		PrintWriter out = null;
		String isManifested = CommonConstants.NO;
		try {
			out = response.getWriter();
			RthRtoManifestDoxForm rthRtoManifestDoxForm = (RthRtoManifestDoxForm) form;
			RthRtoManifestDoxTO rthRtoManifestDoxTO = (RthRtoManifestDoxTO) rthRtoManifestDoxForm
					.getTo();
			rthRtoManifestCommonService = getRthRtoManifestCommonService();
			boolean manifested = rthRtoManifestCommonService
					.isRtohNoManifested(rthRtoManifestDoxTO);
			if (manifested)
				isManifested = CommonConstants.YES;
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: RthRtoManifestDoxAction :: isRtohNoManifested() ::",
					e);
			isManifested = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In :: RthRtoManifestDoxAction :: isRtohNoManifested() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			isManifested = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In :: RthRtoManifestDoxAction :: isRtohNoManifested() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			isManifested = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(isManifested);
			out.flush();
			out.close();
		}
		LOGGER.trace("RthRtoManifestDoxAction::isRtohNoManifested()::END");
	}

	/**
	 * To get rthRtoManifestCommonService.
	 * 
	 * @return rthRtoManifestCommonService
	 */
	private RthRtoManifestCommonService getRthRtoManifestCommonService() {
		if (StringUtil.isNull(rthRtoManifestCommonService)) {
			rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
		}
		return rthRtoManifestCommonService;
	}

	/**
	 * To get rthRtoManifestDoxService.
	 * 
	 * @return rthRtoManifestDoxService
	 */
	private RthRtoManifestDoxService getRthRtoManifestDoxService() {
		if (StringUtil.isNull(rthRtoManifestDoxService)) {
			rthRtoManifestDoxService = (RthRtoManifestDoxService) getBean(SpringConstants.RTH_RTO_MANIFEST_DOX_SERVICE);
		}
		return rthRtoManifestDoxService;
	}

	/*
	 * public ActionForward printRthRtoManifestDoxDtls(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) {
	 * LOGGER.trace("RthRtoManifestDoxAction::printRthRtoManifestDoxDtls()::START"
	 * ); ActionMessage actionMessage = null; RthRtoManifestDoxForm doxForm =
	 * null; RthRtoManifestDoxTO to = null; String manifestType =
	 * CommonConstants.EMPTY_STRING; RthRtoManifestTO rthRtoManifestTO=null;
	 * try{ doxForm = (RthRtoManifestDoxForm)form; to =
	 * (RthRtoManifestDoxTO)doxForm.getTo(); manifestType =
	 * to.getManifestType(); rthRtoManifestCommonService =
	 * getRthRtoManifestCommonService(); rthRtoManifestTO =
	 * rthRtoManifestCommonService.searchRTOHManifestDetails(to);
	 * to.setManifestType(manifestType); setDefaultValues(request, to);
	 * request.setAttribute("rthRtoManifestPrintTO", rthRtoManifestTO); check
	 * any warnings/Business Exceptions boolean errorStatus =
	 * ExceptionUtil.checkError(to); if(errorStatus) { if so extract them and
	 * propagate to screen ExceptionUtil.prepareActionMessage(to, request);
	 * saveActionMessage(request); } saveToken(request); } catch
	 * (CGSystemException e) { getSystemException(request, e); LOGGER.error(
	 * "Exception happened in printRthRtoManifestDoxDtls of RthRtoManifestDoxAction..."
	 * , e); } catch (CGBusinessException e) { getBusinessError(request, e);
	 * actionMessage = new ActionMessage(ManifestErrorCodesConstants
	 * .MANIFEST_DETAILS_NOT_FOUND); LOGGER.error(
	 * "Exception happened in printRthRtoManifestDoxDtls of RthRtoManifestDoxAction..."
	 * , e); } catch (Exception e) { getGenericException(request, e);
	 * actionMessage = new ActionMessage(ManifestErrorCodesConstants
	 * .MANIFEST_DETAILS_NOT_FOUND); LOGGER.error(
	 * "Exception happened in printRthRtoManifestDoxDtls of RthRtoManifestDoxAction..."
	 * , e); } ((RthRtoManifestDoxForm)form).setTo(to);
	 * LOGGER.trace("RthRtoManifestDoxAction::printRthRtoManifestDoxDtls()::END"
	 * ); return
	 * mapping.findForward(RthRtoManifestConstatnts.URL_PRINT_RTH_RTO_DOX); }
	 */

	public ActionForward printRthRtoManifestDoxDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthRtoManifestDoxAction::printRthRtoManifestDoxDtls()::START");
		rthRtoManifestCommonService = getRthRtoManifestCommonService();
		RthRtoManifestDoxForm doxForm = null;
		RthRtoManifestDoxTO to = null;
		try {
			doxForm = (RthRtoManifestDoxForm) form;
			to = (RthRtoManifestDoxTO) doxForm.getTo();
			setDefaultValues(request, to);
			String manifestNumber = request.getParameter("manifestNumber");
			String manifestType = request.getParameter("manifestType");
			to.setManifestNumber(manifestNumber);
			to.setManifestType(manifestType);
			RthRtoManifestTO rthRtoManifestTO = null;
			rthRtoManifestTO = rthRtoManifestCommonService
					.searchRTOHManifestDetails(to);
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(InManifestConstants.USER_INFO);

			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if (!StringUtil.isNull(officeTO)) {
				rthRtoManifestTO.setLoggedInOfficeName(officeTO.getOfficeName());
				rthRtoManifestTO.setLoggedInOfficeCity(officeTO.getAddress3());
			}
			request.setAttribute("rthRtoManifestTO", rthRtoManifestTO);
		} catch (Exception e) {
			LOGGER.error("Exception happened in printRthRtoManifestDoxDtls of RthRtoManifestDoxAction..."
					, e);
		}
		LOGGER.trace("RthRtoManifestDoxAction::printRthRtoManifestDoxDtls()::END");
		return mapping
				.findForward(RthRtoManifestConstatnts.URL_PRINT_RTH_RTO_DOX);
	}
}
