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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
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
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.converter.RthRtoManifestParcelConverter;
import com.ff.web.manifest.rthrto.form.RthRtoManifestForm;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.rthrto.service.RthRtoManifestParcelService;

// TODO: Auto-generated Javadoc
/**
 * The Class RthManifestParcelAction.
 */
public class RthManifestParcelAction extends AbstractRthRtoManifestAction{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(RthManifestParcelAction.class);
	
	/** The rth rto manifest common service. */
	private RthRtoManifestCommonService rthRtoManifestCommonService;
	
	/**
	 * View rth manifest parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewRthManifestParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthManifestParcelAction::viewRthManifestParcel::START------------>:::::::");
		RthRtoManifestTO rthManifestTO =null;
		String forwardString=UmcConstants.WELCOME;
		try{
			if (isBranchOffice(request) || isHubOffice(request)){
				rthManifestTO = setFormValues(form, request);
				
				ReasonTO reasonTO = new ReasonTO();
				reasonTO.setReasonType(UdaanCommonConstants.REASON_TYPE_FOR_RTO_RTH_VALIDATION);
				List<ReasonTO> reasonTOs = rthRtoManifestCommonService.getReasonsByReasonType(reasonTO);			
				
				rthManifestTO.setManifestType(CommonConstants.MANIFEST_TYPE_RTH);
				
				((RthRtoManifestForm) form).setTo(rthManifestTO);
				setRthRtoConstants(request);				
				request.setAttribute(RthRtoManifestConstatnts.REASONS_LIST, reasonTOs);
				
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				OfficeTO officeTO=rthManifestTO.getOriginOfficeTO();
				OfficeTypeTO officeTypeTO=new OfficeTypeTO();
				officeTypeTO.setOffcTypeCode(CommonConstants.OFF_TYPE_HUB_OFFICE);
				
				OfficeTO loginOfficeTO=new OfficeTO();
				loginOfficeTO.setOfficeTypeTO(officeTypeTO);
				if(isBranchOffice(request)){
					//Set destination hub offices
					loginOfficeTO.setCityId(officeTO.getCityId());
					List<OfficeTO> officeTOs = rthRtoManifestCommonService.getOfficesByOffice(loginOfficeTO);
					request.setAttribute(RthRtoManifestConstatnts.OFFICE_LIST, officeTOs);
				}else{
					request.setAttribute(RthRtoManifestConstatnts.CITY_LIST, rthManifestTO.getCityTOList());
				}
				
				request.setAttribute(RthRtoManifestConstatnts.LOGIN_OFFICE_TYPE, officeTO.getOfficeTypeTO().getOffcTypeCode());
				forwardString = RthRtoManifestConstatnts.VIEW_RTH_MANIFEST_PARCEL;
			}
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRthManifestParcel() ::", e);
			
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRthManifestParcel() ::", e);
			
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRthManifestParcel() ::", e);
		}
		LOGGER.trace("RthManifestParcelAction::viewRthManifestParcel::END------------>:::::::");
		return mapping.findForward(forwardString);	
	}
	
	/**
	 * View rto manifest parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewRtoManifestParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RthManifestParcelAction::viewRtoManifestParcel::START------------>:::::::");
		RthRtoManifestTO rthManifestTO =null;
		String forwardString=CommonConstants.EMPTY_STRING;
		try{
			rthManifestTO = setFormValues(form, request);
			if(!isBranchOffice(request)){
				rthManifestTO.setManifestType(CommonConstants.MANIFEST_TYPE_RTO);
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				
				List<RegionTO> regionTOs = rthRtoManifestCommonService.getAllRegions();
				
				ReasonTO reasonTO = new ReasonTO();
				reasonTO.setReasonType(UdaanCommonConstants.REASON_TYPE_FOR_RTO_RTH_VALIDATION);
				List<ReasonTO> reasonTOs = rthRtoManifestCommonService.getReasonsByReasonType(reasonTO);
				
				((RthRtoManifestForm) form).setTo(rthManifestTO);
				setRthRtoConstants(request);
				request.setAttribute(RthRtoManifestConstatnts.REGION_LIST, regionTOs);
				request.setAttribute(RthRtoManifestConstatnts.REASONS_LIST, reasonTOs);
				
				forwardString=RthRtoManifestConstatnts.VIEW_RTO_MANIFEST_PARCEL;
			}else{
				forwardString=UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(
						RthRtoManifestConstatnts.RTO_ONLY_ALLOWED_AT_HUB_OFFICE);
				prepareActionMessage(request, actionMessage);
			}			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRtoManifestParcel() ::", e);
			
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRtoManifestParcel() ::", e);
			
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: viewRtoManifestParcel() ::", e);
		}
		LOGGER.trace("RthManifestParcelAction::viewRtoManifestParcel::END------------>:::::::");
		return mapping.findForward(forwardString);		
	}
	
	/**
	 * Sets the form values.
	 *
	 * @param form the form
	 * @param request the request
	 * @return the rth rto manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private RthRtoManifestTO setFormValues(ActionForm form,
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RthManifestParcelAction::setFormValues::START------------>:::::::");
//		RthRtoManifestForm rthRtoManifestForm = (RthRtoManifestForm) form;
		RthRtoManifestForm rthRtoManifestForm = new RthRtoManifestForm();
		RthRtoManifestTO rthManifestTO = (RthRtoManifestTO) rthRtoManifestForm.getTo();
		// set Manifest date
		rthManifestTO.setManifestDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		// set login office details in dispatching office
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		rthManifestTO.setOriginOfficeTO(officeTO);
		CityTO cityTO = new CityTO();
		cityTO.setCityId(officeTO.getCityId());
		
		rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
		List<CityTO> cityTOs = rthRtoManifestCommonService.getCitiesByCity(cityTO);
		if (!StringUtil.isEmptyList(cityTOs)) {
			String originCityCode = cityTOs.get(0).getCityCode();
			rthManifestTO.setOriginCityCode(originCityCode);
		}
		
		List<CityTO> citiesByRegion = rthRtoManifestCommonService.getCitiesByRegion(officeTO.getRegionTO().getRegionId());
		rthManifestTO.setCityTOList(citiesByRegion);
		//Added by Himal
		rthManifestTO.setBagLockNo(CommonConstants.EMPTY_STRING);
		
		//Set consignment type
		ConsignmentTypeTO consignmentTypeTO=new ConsignmentTypeTO();
		consignmentTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		List<ConsignmentTypeTO> consignmentTypeTOs=rthRtoManifestCommonService.getConsignmentTypes(consignmentTypeTO);
		if(!StringUtil.isEmptyList(consignmentTypeTOs))
			rthManifestTO.setConsignmentTypeTO(consignmentTypeTOs.get(0));

		rthManifestTO.setProcessCode(CommonConstants.PROCESS_RTO_RTH);
		rthManifestTO
				.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
		if (!StringUtil.isNull(officeTO.getRegionTO())) {
			rthManifestTO.setRegionCode(officeTO.getRegionTO().getRegionCode());
			rthManifestTO.setRegionId(officeTO.getRegionTO().getRegionId());
		}
		request.setAttribute("SERIES_TYPE_BAG_LOCK_NO",
				UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO);
		
		LOGGER.trace("RthManifestParcelAction::setFormValues::END------------>:::::::");
		return rthManifestTO;
	}
	
	/**
	 * Sets the rth rto constants.
	 *
	 * @param request the new rth rto constants
	 */
	private void setRthRtoConstants(HttpServletRequest request){
		request.setAttribute("MANIFEST_TYPE_RTH", CommonConstants.MANIFEST_TYPE_RTH);
		request.setAttribute("MANIFEST_TYPE_RTO", CommonConstants.MANIFEST_TYPE_RTO);
		request.setAttribute("SERIES_TYPE_OGM_NO",UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
		request.setAttribute("SERIES_TYPE_BPL_NO",UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
		request.setAttribute("CONSIGNMENT_TYPE_PARCEL",CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		request.setAttribute("OFF_TYPE_HUB_OFFICE",CommonConstants.OFF_TYPE_HUB_OFFICE);
	}
	
	/**
	 * Gets the consignment details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the consignment details
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RthManifestParcelAction::getConsignmentDetails::START------------>:::::::");
		ConsignmentValidationTO consigValidationTO= null;
		String consgResultJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		RthRtoDetailsTO rthRtoManifestDtlTO = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			consigValidationTO = prepareConsgValidateInputs(request);
			if(!StringUtil.isNull(consigValidationTO)){
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				rthRtoManifestDtlTO=rthRtoManifestCommonService.getConsignmentDetails(consigValidationTO);
			}			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: getConsignmentDetails() ::", e);
			
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: getConsignmentDetails() ::", e);
			
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: getConsignmentDetails() ::", e);
			
		} finally {
			if (StringUtil.isNull(rthRtoManifestDtlTO)) {
				rthRtoManifestDtlTO = new RthRtoDetailsTO();
			}
			rthRtoManifestDtlTO.setErrorMsg(errorMsg);
			consgResultJSON = serializer.toJSON(rthRtoManifestDtlTO).toString();			
			out.print(consgResultJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("RthManifestParcelAction::getConsignmentDetails::END------------>:::::::");
	}
	
	/**
	 * Prepare consg validate inputs.
	 *
	 * @param request the request
	 * @return the consignment validation to
	 */
	private ConsignmentValidationTO prepareConsgValidateInputs(HttpServletRequest request){
		LOGGER.trace("RthManifestParcelAction::prepareConsgValidateInputs::START------------>:::::::");
		ConsignmentValidationTO consigValidationTO= null;
		if (StringUtils.isNotEmpty(request.getParameter(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER))) {
			consigValidationTO=new ConsignmentValidationTO();
			consigValidationTO.setConsgNumber(request.getParameter(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER));
			consigValidationTO.setManifestType(request.getParameter(RthRtoManifestConstatnts.MANIFEST_TYPE));
			ConsignmentTypeTO typeTO=new ConsignmentTypeTO();
			typeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			consigValidationTO.setConsignmentTypeTO(typeTO);
			if(StringUtils.isNotEmpty(request.getParameter(RthRtoManifestConstatnts.ORIGIN_OFFICE_ID))){
				Integer originOffice=Integer.parseInt(request.getParameter(RthRtoManifestConstatnts.ORIGIN_OFFICE_ID));
				consigValidationTO.setOriginOffice(originOffice);
			}
			if(StringUtils.isNotEmpty(request.getParameter(RthRtoManifestConstatnts.DESTINATION_CITY_ID))){
				Integer destCityId=Integer.parseInt(request.getParameter(RthRtoManifestConstatnts.DESTINATION_CITY_ID));
				consigValidationTO.setDestCityId(destCityId);
			}
			/** Added by Himal - Setting consignment return type for validate */
			setConsgReturnType(consigValidationTO);
		}
		LOGGER.trace("RthManifestParcelAction::prepareConsgValidateInputs::END------------>:::::::");
		return  consigValidationTO;
	}
	
	/**
	 * Save or update rth rto manifest parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void saveOrUpdateRthRtoManifestParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RthManifestParcelAction::saveOrUpdateRthRtoManifestParcel::START------------>:::::::");
		PrintWriter out = null;
		RthRtoManifestTO rthRtoManifestTO=null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String transMsg = null;
		String errorMsg = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try{
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);
			RthRtoManifestForm rthRtoManifestForm = (RthRtoManifestForm) form;
			rthRtoManifestTO = (RthRtoManifestTO) rthRtoManifestForm.getTo();
			List<RthRtoDetailsTO> rthRtoDetailsTOs=RthRtoManifestParcelConverter.rthRtoDetailListConverter(rthRtoManifestTO);
			rthRtoManifestTO.setRthRtoDetailsTOs(rthRtoDetailsTOs);
			RthRtoManifestParcelService rthRtoManifestParcelService = (RthRtoManifestParcelService) getBean(SpringConstants.RTH_RTO_MANIFEST_PARCEL_SERVICE);
			rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
					.getAttribute(RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS);
			if(!StringUtil.isNull(rateCompnents)){
			 rthRtoManifestTO.setRateCompnents(rateCompnents);
			 session.removeAttribute(RthRtoManifestConstatnts.RTO_MANIFEST_RATE_DTLS);
			}
			rthRtoManifestTO=rthRtoManifestParcelService.saveOrUpdateRthRtoManifestParcel(rthRtoManifestTO);

			//calling TwoWayWrite service to save same in central
			twoWayWrite(rthRtoManifestTO);
			
			/*if (!StringUtil.isNull(rthRtoManifestTO)) {
				jsonResult = serializer.toJSON(rthRtoManifestTO).toString();
			}*/
			transMsg = getMessageFromErrorBundle(request, ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY, null);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: saveOrUpdateRthRtoManifestParcel() ::", e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: saveOrUpdateRthRtoManifestParcel() ::", e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("ERROR :: RthManifestParcelAction :: saveOrUpdateRthRtoManifestParcel() ::", e);
		} finally {
			if (rthRtoManifestTO == null) {
				rthRtoManifestTO = new RthRtoManifestTO();
			}
			rthRtoManifestTO.setErrorMsg(errorMsg);
			rthRtoManifestTO.setTransMsg(transMsg);
			jsonResult = serializer.toJSON(rthRtoManifestTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RthManifestParcelAction::saveOrUpdateRthRtoManifestParcel::END------------>:::::::");
	}
	
	/**
	 * Search rtoh manifest details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void searchRTOHManifestDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RthManifestParcelAction::searchRTOHManifestDetails::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		RthRtoManifestTO rthRtoManifestTO=null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			RthRtoManifestForm rthRtoManifestForm = (RthRtoManifestForm) form;
			rthRtoManifestTO = (RthRtoManifestTO) rthRtoManifestForm.getTo();
			if(StringUtils.isNotEmpty(rthRtoManifestTO.getManifestNumber())){
				rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
				rthRtoManifestTO=rthRtoManifestCommonService.searchRTOHManifestDetails(rthRtoManifestTO);
			}
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("RthManifestParcelAction :: searchRTOHManifestDetails() ::"
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("RthManifestParcelAction :: searchRTOHManifestDetails() ::"
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("RthManifestParcelAction :: searchRTOHManifestDetails() ::"
					, e);
		} finally {
			if (rthRtoManifestTO == null) {
				rthRtoManifestTO = new RthRtoManifestTO();
			}
			rthRtoManifestTO.setErrorMsg(errorMsg);
			jsonResult = serializer.toJSON(rthRtoManifestTO).toString();
			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RthManifestParcelAction::searchRTOHManifestDetails::END------------>:::::::");
	}
	
	/**
	 * Checks if is rtoh no manifested.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void isRtohNoManifested(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.trace("RthManifestParcelAction::isRtohNoManifested::START------------>:::::::");
		PrintWriter out = null;
		String isManifested = CommonConstants.NO;
		try {
			out = response.getWriter();
			RthRtoManifestForm rthRtoManifestForm = (RthRtoManifestForm) form;
			RthRtoManifestTO rthRtoManifestTO = (RthRtoManifestTO) rthRtoManifestForm.getTo();
			rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
			boolean manifested=rthRtoManifestCommonService.isRtohNoManifested(rthRtoManifestTO);
			if(manifested)
				isManifested=CommonConstants.YES;
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: RthManifestParcelAction :: isRtohNoManifested() ::",
					e);
			isManifested = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In :: RthManifestParcelAction :: isRtohNoManifested() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			isManifested = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In :: RthManifestParcelAction :: isRtohNoManifested() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			isManifested = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(isManifested);
			out.flush();
			out.close();
		}
		LOGGER.trace("RthManifestParcelAction::isRtohNoManifested::END------------>:::::::");
	}
	
	/**
	 * Prints the rtoh manifest ppx.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward printRthRtoManifestParcelDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("RthManifestParcelAction::printRthRtoManifestParcelDtls()::START");
		rthRtoManifestCommonService = (RthRtoManifestCommonService) getBean(SpringConstants.RTH_RTO_MANIFEST_COMMON_SERVICE);
		RthRtoManifestForm ppxForm = null;
		RthRtoManifestTO to = null;
		try {
			ppxForm = (RthRtoManifestForm) form;
			to = (RthRtoManifestTO) ppxForm.getTo();
			setFormValues(ppxForm, request);
			String manifestNumber = request.getParameter("manifestNumber");
			String manifestType = request.getParameter("manifestType");
			to.setManifestNumber(manifestNumber);
			to.setManifestType(manifestType);
			RthRtoManifestTO rthRtoManifestTO = null;
			rthRtoManifestTO = rthRtoManifestCommonService.searchRTOHManifestDetails(to);
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
			LOGGER.error("Exception happened in printRthRtoManifestParcelDtls of RthManifestParcelAction..."
					, e);
		}
		LOGGER.debug("RthManifestParcelAction::printRthRtoManifestParcelDtls()::END");
		return mapping
				.findForward(RthRtoManifestConstatnts.URL_PRINT_RTH_RTO_PPX);
	}
}
