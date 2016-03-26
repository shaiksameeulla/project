package com.ff.web.manifest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
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
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.LoadLotTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyOutManifestDoxDetailsTO;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.constants.GlobalConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.converter.ThirdPartyOutManifestDoxConverter;
import com.ff.web.manifest.form.ThirdPartyOutManifestDoxForm;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.service.ThirdPartyManifestDoxService;

/**
 * @author cbhure
 *
 */

public class ThirdPartyOutManifestDoxAction extends OutManifestAction {
	private OutManifestCommonService outManifestCommonService;
	private GlobalService globalService;
	private ThirdPartyManifestDoxService thirdPartyManifestDoxService;
	private OutManifestUniversalService outManifestUniversalService;
	private ManifestCommonService manifestCommonService;
	private final static Logger LOGGER = LoggerFactory.getLogger(ThirdPartyOutManifestDoxAction.class);
	
	/**
	 * 
	 * To get an instance of globalService
	 * 
	 * @return  : globalService
	 */
	
	
	private GlobalService getGlobalService() {
		if (StringUtil.isNull(globalService)) {
			globalService = (GlobalService) getBean(GlobalConstants.GLOBAL_SERVICE);
		}
		return globalService;
	}

	/**
	 * 
	 * To get an instance of thirdPartyManifestDoxService
	 * 
	 * @return : thirdPartyManifestDoxService
	 */
	
	private ThirdPartyManifestDoxService getThirdPartyManifestDoxService() {
		if (StringUtil.isNull(thirdPartyManifestDoxService)) {
			thirdPartyManifestDoxService = (ThirdPartyManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_DOX);
		}
		return thirdPartyManifestDoxService;
	}
	
	/**
	 * To show the screen for Third Party Out Manifest For Dox
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return view thirdPartyOutManifestDox.jsp
	 */
	
	public ActionForward viewThirdPartyOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ThirdPartyOutManifestDoxTO thirdPartyoutManifestDoxTO = null;
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			List<LoadLotTO> loadNoTo = outManifestCommonService.getLoadNo();
			List<LabelValueBean> loadNoList = new ArrayList<LabelValueBean>();
			
			//To Populate third Party type List
			for (LoadLotTO loadTO : loadNoTo) {
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(loadTO.getLoadNo().toString());
				lvb.setValue(loadTO.getLoadLotId() + "");
				loadNoList.add(lvb);
			}
			globalService = getGlobalService();
			String typeName = GlobalConstants.THIRD_PARTY_TYPE;
			List<StockStandardTypeTO> thirdPartyTo = globalService
					.getAllStockStandardType(typeName);
			List<LabelValueBean> thirdPartyTypeList = new ArrayList<LabelValueBean>();
			
			//To Populate list of Third Party Type
			for (StockStandardTypeTO thirdPartyTO : thirdPartyTo) {
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(thirdPartyTO.getDescription());
				lvb.setValue(thirdPartyTO.getStdTypeCode());
				thirdPartyTypeList.add(lvb);
			}

			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
			manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			thirdPartyoutManifestDoxTO = (ThirdPartyOutManifestDoxTO) getManifestBasicDtls(	manifestFactoryTO, request);
			thirdPartyoutManifestDoxTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
			request.setAttribute("seriesType", thirdPartyoutManifestDoxTO.getSeriesType());
			request.setAttribute("loginOfficeId", thirdPartyoutManifestDoxTO.getLoginOfficeId());
			request.setAttribute("regionId", thirdPartyoutManifestDoxTO.getRegionId());
			thirdPartyoutManifestDoxTO.setLoadList(loadNoList);
			thirdPartyoutManifestDoxTO.setThirdPartyTypeList(thirdPartyTypeList);
			
			//setting the configurable params
			if (!StringUtil.isEmptyMap(thirdPartyoutManifestDoxTO
					.getConfigurableParams())) {
				thirdPartyoutManifestDoxTO.setMaxCNsAllowed(thirdPartyoutManifestDoxTO
						.getConfigurableParams().get(
								ManifestConstants.TPOM_DOX_MAX_CNS_ALLOWED));
				thirdPartyoutManifestDoxTO.setMaxWeightAllowed((thirdPartyoutManifestDoxTO
						.getConfigurableParams()
						.get(ManifestConstants.TPOM_DOX_MAX_WEIGHT_ALLOWED)));
				thirdPartyoutManifestDoxTO.setMaxTolerenceAllowed(thirdPartyoutManifestDoxTO
						.getConfigurableParams()
						.get(ManifestConstants.TPOM_DOX_MAX_TOLLRENCE_ALLOWED));
			}
			request.setAttribute(OutManifestConstants.PROCESS_CODE, 
					CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
			((ThirdPartyOutManifestDoxForm) form).setTo(thirdPartyoutManifestDoxTO);

		} catch (Exception e) {
			LOGGER.error("ThirdPartyOutManifestDoxAction :: viewThirdPartyOutManifestDox() ::",e);
		}

		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	/**
	 * Populate details for third party names
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	@SuppressWarnings("static-access")
	public void getThirdPartyName(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response){

	//	List<String> partyName = null;
		PrintWriter out = null;
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			String partyID = request
					.getParameter(UdaanCommonConstants.THIRD_PARTY_ID);
			String partyType = CommonConstants.EMPTY_STRING;
			List<LoadMovementVendorTO> vendorTO = null;
		
			thirdPartyManifestDoxService = getThirdPartyManifestDoxService();
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			userInfoTO =(UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			
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
		}  catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyOutManifestDOXAction :: getThirdPartyName() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyOutManifestDOXAction :: getThirdPartyName() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ThirdPartyOutManifestDOXAction :: getThirdPartyName() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyOutManifestDOXAction :: getThirdPartyName() :: END");
	}
	
	/**
	 * Save or Update Manifest
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	public void saveOrUpdateOutManifestTPDX(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		
		ThirdPartyOutManifestDoxForm thirdPartyOutMnfstDoxForm = null;
		ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO  = null;
		String transMsg = "";
		PrintWriter out = null;
		String jsonResult = "";
		List<ThirdPartyOutManifestDoxDetailsTO> thirdPartyOutmanifestDtls = null;
		try {
			
			out = response.getWriter();
			thirdPartyOutMnfstDoxForm = (ThirdPartyOutManifestDoxForm) form;
			thirdPartyOutManifestDoxTO = (ThirdPartyOutManifestDoxTO) thirdPartyOutMnfstDoxForm.getTo();
			thirdPartyManifestDoxService = (ThirdPartyManifestDoxService)getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_DOX);
			thirdPartyOutManifestDoxTO.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
			thirdPartyOutManifestDoxTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
			if(!StringUtil.isNull(thirdPartyOutManifestDoxTO.getLoginCityId())){
				thirdPartyOutManifestDoxTO.setDestinationCityId(thirdPartyOutManifestDoxTO.getLoginCityId());
			}
			if(!StringUtil.isNull(thirdPartyOutManifestDoxTO.getLoginOfficeId())){
				thirdPartyOutManifestDoxTO.setDestinationOfficeId(thirdPartyOutManifestDoxTO.getLoginOfficeId());
			}
			
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
					
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			thirdPartyOutManifestDoxTO.setConsignmentTypeTO(consgType);
			
			setThirdPartyOutmanifestDoxTO(thirdPartyOutManifestDoxTO);
			
			if (!StringUtil.isNull(thirdPartyOutManifestDoxTO)) {
				if (StringUtil.isEmpty(thirdPartyOutManifestDoxTO.getConsgNos())) {
					//throw new CGBusinessException("ERROROMD001");
					prepareActionMessage(request, new ActionMessage("ERROROMD001"));
				}
				thirdPartyOutmanifestDtls =ThirdPartyOutManifestDoxConverter
						.thirdPartyOutManifestDoxDtlsConverter(thirdPartyOutManifestDoxTO);
				thirdPartyOutManifestDoxTO.setThirdPartyOutManifestDoxDetailsToList(thirdPartyOutmanifestDtls);
			}
			ManifestDO manifestDO = null;
			/*ManifestProcessDO manifestProcessDO = new ManifestProcessDO();*/
			List<BookingDO> allBooking = new ArrayList<BookingDO>();
			List<ConsignmentDO> bookingConsignment = new ArrayList<ConsignmentDO>();
			Set<ConsignmentDO> allConsignments = new LinkedHashSet<ConsignmentDO>();
			manifestDO = prepareManifestForThirdPartyOutDox(manifestDO,
					thirdPartyOutManifestDoxTO, allBooking, bookingConsignment, allConsignments);
			manifestDO.setConsignments(allConsignments);
	
			transMsg = thirdPartyManifestDoxService.saveOrUpdateThirdPartyOutManifestDox(
					manifestDO, thirdPartyOutManifestDoxTO, allBooking,
					bookingConsignment, allConsignments);
			
			if(transMsg.equalsIgnoreCase("O")){
				thirdPartyOutManifestDoxTO
				.setSuccessMessage(getMessageFromErrorBundle(
						request,
						ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
						null));
				
				
				jsonResult = JSONSerializer.toJSON(thirdPartyOutManifestDoxTO).toString();
				
			}else if(transMsg.equalsIgnoreCase("C")){
				twoWayWrite(thirdPartyOutManifestDoxTO);
				thirdPartyOutManifestDoxTO
				.setSuccessMessage(getMessageFromErrorBundle(
						request,
						ManifestErrorCodesConstants.MANIFEST_CLOSED_SUCCESSFULLY,
						null));
				
				
				jsonResult = JSONSerializer.toJSON(thirdPartyOutManifestDoxTO).toString();
			}
		}
	catch (CGBusinessException e) {
		LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:saveOrUpdateThirdPartyOutManifestDox()"
				,e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
	} 
	catch (CGSystemException e) {
		LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:saveOrUpdateThirdPartyOutManifestDox()"
				+ e.getMessage());
		String exception=getSystemExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		
	}catch (Exception e) {
		LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:saveOrUpdateThirdPartyOutManifestDox()"
				+ e.getMessage());
		String exception = getGenericExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
				exception);
	}
	finally {
		out.print(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.trace("ThirdPartyOutManifestDoxAction::saveOrUpdateThirdPartyOutManifestDox::END");
}

	
	/**
	 * To fetch the details of consignment at grid.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException - when response not found. 
	 */
	
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {  
		
		LOGGER.trace("ThirdPartyOutManifestDoxAction::getConsignmentDtls::START");
		PrintWriter out = null;
		String jsonResult = "";
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
			OfficeTO officeTO= null;
			CityTO cityTO=null;
			try {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				out = response.getWriter();
				cnValidateTO = new OutManifestValidate();
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
				if (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
					
					officeTO = outManifestCommonService.getOfficeDetails(Integer.parseInt(request
							.getParameter("loginOfficeId")));
				
					cnValidateTO.setDestOffice(officeTO);
					
					cityTO = new CityTO();
					cityTO.setCityId(officeTO.getCityId());
					cnValidateTO.setDestCityTO(cityTO);
				}
				/*if (StringUtils.isNotEmpty(request.getParameter("cityId"))) {
					cityTO = new CityTO();
					cityTO.setCityId(Integer.parseInt(request
							.getParameter("cityId")));
					cnValidateTO.setDestCityTO(cityTO);
				}*/
				if (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
					cnValidateTO.setManifestNumber(request
							.getParameter("manifestNo"));
				}
				
				
				manifestFactoryTO=new ManifestFactoryTO();
				manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());
				
				ConsignmentTypeTO  consTypeTO = new ConsignmentTypeTO();
				consTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);
				cnValidateTO.setConsignmentTypeTO(consTypeTO);
				
				cnValidateTO.setManifestProcessCode(OutManifestConstants.PROCESS_CODE_TPDX);
				
			
				/*cnValidateTO = outManifestCommonService
						.validateConsignment(cnValidateTO);*/
				
				
				cnValidateTO = outManifestCommonService.validateConsignmentForThirdPartyManifest(cnValidateTO);
			
				//else{
				//gets the consignment details
					thirdPartyManifestDoxService = (ThirdPartyManifestDoxService)getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_DOX);
					ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDtlTO = null;
					thirdPartyOutManifestDoxDtlTO = thirdPartyManifestDoxService.getConsignmentDtls(cnValidateTO);
				if(thirdPartyOutManifestDoxDtlTO!=null){
				jsonResult=serializer.toJSON(thirdPartyOutManifestDoxDtlTO).toString();
				}else{
					jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,"No Details found");
				}
				//}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:getConsignmentDtls()"
					,e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}
			catch (CGSystemException e) {
				LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:getConsignmentDtls()"
						,e);
				String exception=getSystemExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			}
			catch (Exception e) {
				LOGGER.error("Error occured in ThirdPartyOutManifestDoxAction :: ..:getConsignmentDtls()"
						,e);
				String exception = getGenericExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						exception);
				
			}finally{
				out.print(jsonResult);
				out.flush();
				out.close();
			}
		LOGGER.trace("ThirdPartyOutManifestDoxAction::getConsignmentDtls::END");
	}
	
	
	
	public void getInManifestConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {  
		
		ManifestFactoryTO manifestFactoryTO = null;
		ThirdPartyOutManifestDoxDetailsTO thirdPartyOutManifestDoxDetlTO = null;
		String result = "";
		PrintWriter out = null;
		ActionMessage actionMessage = null;
		
		thirdPartyManifestDoxService = getThirdPartyManifestDoxService();
		try {
			String consignmentNo = request.getParameter(OutManifestConstants.CONSIGNMENT_NO);
			String loginOfficeId = request.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);  
			Integer officeId = Integer.valueOf(loginOfficeId);
			if (StringUtils.isNotEmpty(consignmentNo)) {
				manifestFactoryTO = new ManifestFactoryTO();
				manifestFactoryTO.setConsgNumber(consignmentNo);
				manifestFactoryTO.setLoginOfficeId(officeId);
				manifestFactoryTO
						.setManifestType(OutManifestConstants.THIRD_PARTY_MANIFEST);
				manifestFactoryTO
						.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				thirdPartyManifestDoxService = (ThirdPartyManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_DOX);
				thirdPartyOutManifestDoxDetlTO = thirdPartyManifestDoxService.getInManifestdConsignmentDtls(manifestFactoryTO);
			}
		}catch (CGBusinessException e) {
			actionMessage =  new ActionMessage(ManifestErrorCodesConstants.CONSG_NOT_FOUND);
			LOGGER.error("ThirdPartyOutManifestDoxAction :: getConsignmentDtls() ::",e);
		}catch (Exception e) {
			actionMessage =  new ActionMessage(ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
			LOGGER.error("ThirdPartyOutManifestDoxAction :: getConsignmentDtls() ::",e);
		}finally{
			prepareActionMessage(request,actionMessage);
			out = response.getWriter();
			result = serializer.toJSON(thirdPartyOutManifestDoxDetlTO).toString();
			out.print(result);
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * To find the details by Manifest No
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	@SuppressWarnings("static-access")
	public void searchManifestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ThirdPartyOutManifestDoxAction::searchManifestDetails::START");
		PrintWriter out = null;
		String jsonResult = "";
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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				thirdPartyManifestDoxService = (ThirdPartyManifestDoxService) getBean(SpringConstants.OUT_MANIFEST_THIRD_PARTY_DOX);
				//search the manifest details
				ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO = thirdPartyManifestDoxService
						.searchThirdPartyManifestDtls(manifestTO);
				//jsonResult = serializer.toJSON(branchOutManifestDoxTO).toString();
				if (!StringUtil.isNull(thirdPartyOutManifestDoxTO)) {
					jsonResult = serializer.toJSON(thirdPartyOutManifestDoxTO).toString();
					}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in searchManifestDetails of ThirdPartyOutManifestDoxAction..."
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in searchManifestDetails of ThirdPartyOutManifestDoxAction..."
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception e) {
			LOGGER.error("Exception happened in searchManifestDetails of ThirdPartyOutManifestDoxAction..."
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally { 
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ThirdPartyOutManifestDoxAction::searchManifestDetails::END");
	}

	
	public void prepareActionMessage(HttpServletRequest request,
			ActionMessage actionMessage) {
		if(actionMessage!=null){
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
			request.setAttribute(CommonConstants.INFO_MESSAGE, actionMessages);
		}
	}
	
	
	public ActionForward printThirdPartyOutManifestDox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ThirdPartyOutManifestDoxAction::printThirdPartyOutManifestDox::START------------>:::::::");
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
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				thirdPartyManifestDoxService = getThirdPartyManifestDoxService();
				ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO = thirdPartyManifestDoxService.searchThirdPartyManifestDtls(manifestTO);
				request.setAttribute("thirdPartyOutManifestDoxTO", thirdPartyOutManifestDoxTO);
			}
		} catch (Exception e) {
			LOGGER.error("ThirdPartyOutManifestDoxAction :: printThirdPartyOutManifestDox() ::"
					+ e.getMessage());
		} 
		LOGGER.debug("ThirdPartyOutManifestDoxAction::printThirdPartyOutManifestDox::END------------>:::::::");
		return mapping.findForward(ManifestConstants.URL_PRINT_THIRDPARTY_DOX);
	}
	
	private void setThirdPartyOutmanifestDoxTO(ThirdPartyOutManifestDoxTO thirdPartyOutmanifestDoxTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("ThirdPartyOutManifestDoxAction :: setThirdPartyOutmanifestDoxTO() :: START ::------------>:::::::");
		OfficeTO loginOfficeTO = null;
		Integer operatingLevel = thirdPartyOutmanifestDoxTO.getOperatingLevel();
		// If manifest is creating first time, set the below values
		if (StringUtil.isEmptyInteger(thirdPartyOutmanifestDoxTO.getManifestId())) {
			outManifestUniversalService = (OutManifestUniversalService) getBean(SpringConstants.OUT_MANIFEST_UNIVERSAL_SERVICE);
			// Setting Login Office details
			loginOfficeTO = new OfficeTO();
			loginOfficeTO.setOfficeId(thirdPartyOutmanifestDoxTO.getLoginOfficeId());
			if (StringUtils.isNotEmpty(thirdPartyOutmanifestDoxTO.getOfficeCode())) {
				loginOfficeTO.setOfficeCode(thirdPartyOutmanifestDoxTO.getOfficeCode());
			}
			// Setting manifested product series
			/*if (StringUtils.isNotEmpty(branchOutmanifestDoxTO
					.getManifestedProductSeries())) {
				ProductTO productTO = outManifestCommonService
						.getProductByConsgSeries(branchOutmanifestDoxTO
								.getManifestedProductSeries());
				outmanifestDoxTO.setProduct(productTO);
			}*/
			// calculating operating level
			operatingLevel = outManifestUniversalService.calcOperatingLevel(
					thirdPartyOutmanifestDoxTO, loginOfficeTO);
			thirdPartyOutmanifestDoxTO.setOperatingLevel(operatingLevel);

			// Getting Consignment Type details based on
			List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
					.getConsignmentTypes(thirdPartyOutmanifestDoxTO
							.getConsignmentTypeTO());
			// Setting Consignment type id
			if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
				ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs
						.get(0);
				thirdPartyOutmanifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
			}
			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO
					.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
			processTO = outManifestUniversalService.getProcess(processTO);
			thirdPartyOutmanifestDoxTO.setProcessId(processTO.getProcessId());
			thirdPartyOutmanifestDoxTO.setProcessCode(processTO.getProcessCode());

			// Setting process number
			String processNumber = outManifestCommonService
					.createProcessNumber(processTO, loginOfficeTO);
			thirdPartyOutmanifestDoxTO.setProcessNo(processNumber);
		}

		// The below attributes will always get updated
		if(StringUtil.isNull(thirdPartyOutmanifestDoxTO.getDestinationOfficeId())){
		if (thirdPartyOutmanifestDoxTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
			thirdPartyOutmanifestDoxTO.setIsMulDestination(CommonConstants.YES);
		}
		}
		
		LOGGER.trace("ThirdPartyOutManifestDoxAction :: setThirdPartyOutmanifestDoxTO() :: END------------>:::::::");
	}
	
	private ManifestDO prepareManifestForThirdPartyOutDox(ManifestDO manifestDO,
			ThirdPartyOutManifestDoxTO thirdPartyOutmanifestDoxTO, List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		/* prepare Manifest for search */
		manifestCommonService = (ManifestCommonService) getBean(SpringConstants.MANIFEST_COMMON_SERVICE);
		manifestDO = OutManifestBaseConverter
				.prepateManifestDO(thirdPartyOutmanifestDoxTO);

		//set grid position
		GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
		if (!StringUtil.isEmpty(thirdPartyOutmanifestDoxTO.getConsgNos())) {
			gridItemOrderDO.setConsignments(thirdPartyOutmanifestDoxTO.getConsgNos());
		}
		if (!StringUtil.isEmpty(thirdPartyOutmanifestDoxTO.getComailNos())) {
			gridItemOrderDO.setComails(thirdPartyOutmanifestDoxTO.getComailNos());
		}
		gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
				ManifestConstants.ACTION_SAVE);
		manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition()
				.toString());
		
		
		

		for (int i = 0; i < thirdPartyOutmanifestDoxTO.getConsgNos().length; i++) {
			
					if(!StringUtil.isStringEmpty(thirdPartyOutmanifestDoxTO.getConsgNos()[i])){
					/** prepare consignment */
			
					//ConsignmentDO consignmentDO = new ConsignmentDO();
					
					/* Setting consignment number */
					//consignmentDO.setConsgNo(branchOutmanifestDoxTO.getConsgNos()[i]);
						String consgNo = thirdPartyOutmanifestDoxTO.getConsgNos()[i];
					ConsignmentDO consignmentDO = manifestCommonService
							.getConsignment(consgNo);
					/* Setting Pincode */
					/*PincodeDO pincodeDO = new PincodeDO();
					pincodeDO.setPincodeId(outmanifestDoxTO.getPincodeIds()[i]);
					consignmentDO.setDestPincodeId(pincodeDO);*/

					/* Setting Final and Actual Weight */
					/*consignmentDO.setActualWeight(branchOutmanifestDoxTO
							.getBookingWeights()[i]);
					consignmentDO.setFinalWeight(branchOutmanifestDoxTO
							.getBookingWeights()[i]);*/

					/* Setting Process */
					if (!StringUtil.isEmptyInteger(thirdPartyOutmanifestDoxTO
							.getProcessId())) {
						ProcessDO updatedProcessDO = new ProcessDO();
						updatedProcessDO.setProcessId(thirdPartyOutmanifestDoxTO
								.getProcessId());
						consignmentDO.setUpdatedProcess(updatedProcessDO);
					}

					/** Setting Billing Flags - Added By Himal on 6 Dec. 2013 */
					boolean isBillingFlagReq = Boolean.FALSE;
					if (!StringUtil.isEmptyDouble(consignmentDO.getFinalWeight()) 
							&& (consignmentDO.getFinalWeight().doubleValue() != thirdPartyOutmanifestDoxTO.getWeights()[i].doubleValue())) {
						isBillingFlagReq = Boolean.TRUE;
					} else if (StringUtil.isEmptyDouble(consignmentDO.getFinalWeight())) {
						isBillingFlagReq = Boolean.TRUE;
					}
					if(isBillingFlagReq) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
					}

					/* Setting status */
					/*consignmentDO.setConsgStatus("T");*/

					// Origin office 
					/*consignmentDO.setOrgOffId(thirdPartyOutmanifestDoxTO
							.getLoginOfficeId());*/

					/* Operation Level */
					ConsignmentTO consigmentTO = new ConsignmentTO();
					consigmentTO.setOrgOffId(thirdPartyOutmanifestDoxTO
							.getLoginOfficeId());
					consigmentTO.setDestOffice(null);
					if (thirdPartyOutmanifestDoxTO.getDestinationOfficeId() != null) {
						OfficeTO destoffTO = new OfficeTO();
						destoffTO.setOfficeId(thirdPartyOutmanifestDoxTO
								.getDestinationOfficeId());
						consigmentTO.setDestOffice(destoffTO);
					}
					/*CityTO destCityTO = new CityTO();
					if(!StringUtil.isNull(branchOutmanifestDoxTO.getDestCityIds()[i])){
					destCityTO.setCityId(branchOutmanifestDoxTO.getDestCityIds()[i]);
					consigmentTO.setDestCity(destCityTO);
					}*/
					OfficeTO offTO = new OfficeTO();
					offTO.setOfficeId(thirdPartyOutmanifestDoxTO.getLoginOfficeId());
					/*Integer operatingLevel = outManifestCommonService
							.getConsgOperatingLevel(consigmentTO, offTO);
					consignmentDO.setOperatingLevel(operatingLevel);*/

					/*
					 * operating level needs to be set in case of
					 * weight/destination changes
					 */
					consignmentDO.setOperatingOffice(thirdPartyOutmanifestDoxTO
							.getLoginOfficeId());

					
					allConsignments.add(consignmentDO);
					}//end of if loop

				} /* END of FOR Loop - Outer */
		

		manifestDO.setConsignments(allConsignments);

		
		/* prepare Manifest */
		manifestDO = ThirdPartyOutManifestDoxConverter.prepareManifestDO(
				thirdPartyOutmanifestDoxTO, manifestDO);

		return manifestDO;
	}

}
