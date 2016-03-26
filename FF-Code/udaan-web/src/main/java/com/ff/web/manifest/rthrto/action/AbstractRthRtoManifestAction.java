package com.ff.web.manifest.rthrto.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.manifest.ManifestIssueValidationTO;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRthRtoManifestAction.
 * 
 * @author narmdr
 */
public abstract class AbstractRthRtoManifestAction extends CGBaseAction {
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractRthRtoManifestAction.class);
	
	/** The rth rto manifest common service. */
	private RthRtoManifestCommonService rthRtoManifestCommonService; 
	
	/**
	 * Gets the hub offices of login city.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the hub offices of login city
	 */
	@SuppressWarnings("static-access")
	public void getHubOfficesOfLoginCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.trace("AbstractRthRtoManifestAction::getHubOfficesOfLoginCity::START------------>:::::::");
		PrintWriter out=null;
		List<OfficeTO> officeTOs=null;
		String jsonResult=null;
		try{
			out = response.getWriter();
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			Integer cityId=officeTO.getCityId();
			if(!StringUtil.isEmptyInteger(cityId)){
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				OfficeTO loginOfficeTO=new OfficeTO();
				loginOfficeTO.setCityId(cityId);
				officeTOs=rthRtoManifestCommonService.getOfficesByOffice(loginOfficeTO);			
				if(!StringUtil.isEmptyList(officeTOs)){
					jsonResult = serializer.toJSON(officeTOs).toString();
				}
			}	
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesOfLoginCity() :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesOfLoginCity() :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesOfLoginCity() :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AbstractRthRtoManifestAction::getHubOfficesOfLoginCity::END------------>:::::::");
	}	
	public void getHubOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.trace("AbstractRthRtoManifestAction::getHubOfficesByCity::START------------>:::::::");
		PrintWriter out=null;
		List<OfficeTO> officeTOs=null;
		String jsonResult=null;
		try{
			out = response.getWriter();
			String city = request.getParameter("cityId");
			if(StringUtils.isNotEmpty(city)){
				Integer cityId = Integer.parseInt(city);
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				OfficeTO officeTO=new OfficeTO();
				officeTO.setCityId(cityId);
				OfficeTypeTO officeTypeTO=new OfficeTypeTO();
				officeTypeTO.setOffcTypeCode(CommonConstants.OFF_TYPE_HUB_OFFICE);
				officeTO.setOfficeTypeTO(officeTypeTO);
				officeTOs=rthRtoManifestCommonService.getOfficesByOffice(officeTO);			
				if(!StringUtil.isEmptyList(officeTOs)){
					jsonResult = serializer.toJSON(officeTOs).toString();
				}
			}	
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesByCity() :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesByCity() :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getHubOfficesByCity() :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AbstractRthRtoManifestAction::getHubOfficesByCity::END------------>:::::::");
	}
	/**
	 * Gets the reasons by reason type.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the reasons by reason type
	 * @throws CGBusinessException the cG business exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("static-access")
	public void getReasonsByReasonType(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CGBusinessException,
			IOException {
		LOGGER.debug("AbstractRthRtoManifestAction::getReasonsByReasonType::START------------>:::::::");		
		List<ReasonTO> reasonTOs = null;
		PrintWriter out=null;
		String jsonResult = null;
		try {
			out = response.getWriter();
			final String reasonType = request.getParameter(RthRtoManifestConstatnts.REASON_TYPE);
			if(StringUtils.isNotBlank(reasonType)){
				final ReasonTO reasonTO = new ReasonTO();
				reasonTO.setReasonType(reasonType);
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				reasonTOs = rthRtoManifestCommonService.getReasonsByReasonType(reasonTO);				
			}
			jsonResult = serializer.toJSON(reasonTOs).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getReasonsByReasonType() :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getReasonsByReasonType() :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getReasonsByReasonType() :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRthRtoManifestAction::getReasonsByReasonType::END------------>:::::::");
	}
	
	/**
	 * Gets the cities by region.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the cities by region
	 */
	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("AbstractRthRtoManifestAction::getCitiesByRegion::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String region = request.getParameter(OutManifestConstants.REGION_ID);
			List<CityTO> cityTOs=null;
			if(StringUtils.isNotEmpty(region)){
				Integer regionId=Integer.parseInt(region);
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				CityTO cityTO = new CityTO();
				cityTO.setRegion(regionId);
				cityTOs = rthRtoManifestCommonService.getCitiesByCity(cityTO);
			}
			if (!StringUtil.isEmptyList(cityTOs))
				jsonResult = serializer.toJSON(cityTOs).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getCitiesByRegion() :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getCitiesByRegion() :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getCitiesByRegion() :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AbstractRthRtoManifestAction::getCitiesByRegion::END------------>:::::::");
	}
	
	/**
	 * Gets the offices by city.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the offices by city
	 */
	@SuppressWarnings("static-access")
	public void getOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("AbstractRthRtoManifestAction::getOfficesByCity::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter(RthRtoManifestConstatnts.CITY_ID);
			List<OfficeTO> officeTOs=null;
			if(StringUtils.isNotEmpty(city)){
				Integer cityId=Integer.parseInt(city);
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				officeTOs = rthRtoManifestCommonService.getHubAndBrnchOffices4City(cityId);
			}
			if (!StringUtil.isEmptyList(officeTOs))
				jsonResult = serializer.toJSON(officeTOs).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getOfficesByCity() :: "
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getOfficesByCity() :: "
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::AbstractRthRtoManifestAction::getOfficesByCity() :: "
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AbstractRthRtoManifestAction::getOfficesByCity::END------------>:::::::");
	}
	
	/**
	 * Checks if is manifest no issued.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("static-access")
	public void isManifestNoIssued(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CGSystemException,
			IOException {
		LOGGER.debug("AbstractRthRtoManifestAction::isManifestNoIssued::START------------>:::::::");
		ManifestIssueValidationTO stockIssueValidationTO = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String manifestNo = CommonConstants.EMPTY_STRING;
		String seriesType = CommonConstants.EMPTY_STRING;
		String cityCode = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		boolean isIssued = false;
		String errorMsg = null;
		try {
			manifestNo = request.getParameter(RthRtoManifestConstatnts.MANIFEST_NO);
			seriesType = request.getParameter(RthRtoManifestConstatnts.PARAM_SERIES_TYPE);
			cityCode = request.getParameter(RthRtoManifestConstatnts.PARAM_CITY_CODE);
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if (StringUtils.isNotEmpty(manifestNo)
					&& StringUtils.isNotEmpty(seriesType)) {
				stockIssueValidationTO = new ManifestIssueValidationTO();
				stockIssueValidationTO.setStockItemNumber(manifestNo);
				stockIssueValidationTO.setSeriesType(seriesType);
				stockIssueValidationTO
						.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
				stockIssueValidationTO.setIssuedTOPartyId(officeTO
						.getOfficeId());
				stockIssueValidationTO.setRegionCode(officeTO.getRegionTO()
						.getRegionCode());
				stockIssueValidationTO.setCityCode(cityCode);
				ManifestUniversalService manifestUniversalService = (ManifestUniversalService) getBean(SpringConstants.MANIFEST_UNIVERSAL_SERVICE);
				isIssued = manifestUniversalService
						.isManifesIssued(stockIssueValidationTO);
			}
			stockIssueValidationTO.setIsIssued((isIssued) ? CommonConstants.YES : CommonConstants.NO);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNoIssued of AbstractRthRtoManifestAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			stockIssueValidationTO.setIsIssued(CommonConstants.NO);
			LOGGER.error("Exception happened in isManifestNoIssued of AbstractRthRtoManifestAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in isManifestNoIssued of AbstractRthRtoManifestAction..."
					, e);
		} finally {
			stockIssueValidationTO.setErrorMsg(errorMsg);
			jsonResult = serializer.toJSON(stockIssueValidationTO).toString();
			out = response.getWriter();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRthRtoManifestAction::isManifestNoIssued::END------------>:::::::");
	}
	
	/**
	 * Checks if is branch office.
	 *
	 * @param request the request
	 * @return true, if is branch office
	 */
	public boolean isBranchOffice(HttpServletRequest request) {
		LOGGER.debug("AbstractRthRtoManifestAction::isBranchOffice::START------------>:::::::");
		//try {
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();

			if (loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO() != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				return true;
			}
		/*} catch (Exception e) {
			LOGGER.error("Exception happened in isBranchOffice of AbstractRthRtoManifestAction..."
					, e);
		}*/
		LOGGER.debug("AbstractRthRtoManifestAction::isBranchOffice::END------------>:::::::");
		return false;
	}
	
	/**
	 * Checks if is hub office.
	 *
	 * @param request the request
	 * @return true, if is hub office
	 */
	public boolean isHubOffice(HttpServletRequest request) {
		LOGGER.debug("AbstractRthRtoManifestAction::isHubOffice::START------------>:::::::");
		//try {
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();

			if (loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO() != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				return true;
			}
		/*} catch (Exception e) {
			LOGGER.error("Exception happened in isHubOffice of AbstractRthRtoManifestAction..."
					, e);
		}*/
		LOGGER.debug("AbstractRthRtoManifestAction::isHubOffice::END------------>:::::::");
		return false;
	}
	
	/**
	 * To set consignment return type for RTH/RTO validation purpose
	 * 
	 * @param consigValidationTO
	 */
	public void setConsgReturnType(ConsignmentValidationTO consigValidationTO) {
		LOGGER.trace("AbstractRthRtoManifestAction :: setConsgReturnType() :: START");
		String consgReturnType = CommonConstants.EMPTY_STRING;
		if(consigValidationTO.getManifestType().equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTH)){
			consgReturnType = RthRtoManifestConstatnts.RETURN_TYPE_RTH;
		} else if (consigValidationTO.getManifestType().equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTO)) {
			consgReturnType = RthRtoManifestConstatnts.RETURN_TYPE_RTO;
		}
		consigValidationTO.setConsgReturnType(consgReturnType);
		LOGGER.trace("AbstractRthRtoManifestAction :: setConsgReturnType() :: END");
	}
	
	
	public void calculateCnRtoRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("AbstractRthRtoManifestAction :: calculateCnRtoRate() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);

			/* Prepare consignment TO */
			ConsignmentTO consignmentTO = prepareConsgTO(request);

			/*
			 * setting booking details i.e. booking Date, customer id, rate
			 * customer category code.
			 */
			//setBookingDtls(consignmentTO);
			
			consignmentTO.setConsgStatus("R");

			/* Calculate Rates */
			ConsignmentRateCalculationOutputTO cnRateCalcOutputTO = rthRtoManifestCommonService
					.calculateRtoRateForConsignment(consignmentTO);

			if (!StringUtil.isNull(cnRateCalcOutputTO)) {
				rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
						.getAttribute(RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS);
				if (!CGCollectionUtils.isEmpty(rateCompnents)
						&& rateCompnents.size() > 0)
					rateCompnents.put(consignmentTO.getConsgNo(),
							cnRateCalcOutputTO);
				else {
					rateCompnents = new HashMap<String, ConsignmentRateCalculationOutputTO>();
					rateCompnents.put(consignmentTO.getConsgNo(),
							cnRateCalcOutputTO);
				}
				session.setAttribute(
						RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS,
						rateCompnents);
				/*cnRateDtls = ManifestUtil
						.setUpRateCompoments(cnRateCalcOutputTO);*/
				jsonResult = JSONSerializer.toJSON(cnRateCalcOutputTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in AbstractRthRtoManifestAction :: ..:calculateCnRtoRate()",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in AbstractRthRtoManifestAction :: ..:calculateCnRtoRate()",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in AbstractRthRtoManifestAction :: ..:calculateCnRtoRate()",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AbstractRthRtoManifestAction :: calculateCnRtoRate() :: END");
	}
	
	
	private ConsignmentTO prepareConsgTO(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelAction :: prepareConsgTO() :: START");
		ConsignmentTO consignmentTO = new ConsignmentTO();
		// Consignment Number
		String consgNo = request.getParameter("consigntNo");
		if (!StringUtil.isStringEmpty(consgNo)) {
			consignmentTO.setConsgNo(consgNo);
			rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
			consignmentTO=rthRtoManifestCommonService.getConsgDetailsByNo(consgNo);
		}
		
		LOGGER.trace("OutManifestParcelAction :: prepareConsgTO() :: END");
		return consignmentTO;
	}
	
	/**
	 * Two way write.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 */
	public void twoWayWrite(RthRtoManifestTO rthRtoManifestTO) {
		rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
		rthRtoManifestCommonService.twoWayWrite(rthRtoManifestTO);
	}
}
