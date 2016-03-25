package com.ff.admin.notification.action;

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
import org.apache.struts.action.ActionForward;
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
import com.ff.admin.notification.constants.BulkSmsOnDemandConstants;
import com.ff.admin.notification.form.BulkSmsOnDemandForm;
import com.ff.admin.notification.service.BulkSmsOnDemandService;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.notification.BulkSmsOnDemandTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

public class BulkSmsOnDemandAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory.getLogger(BulkSmsOnDemandAction.class);
	public transient JSONSerializer serializer;
	private BulkSmsOnDemandService bulkSmsOnDemandService;
	private BulkSmsOnDemandService getBulkSmsOnDemandBean(){
		if (StringUtil.isNull(bulkSmsOnDemandService)) {
			bulkSmsOnDemandService = (BulkSmsOnDemandService) getBean(BulkSmsOnDemandConstants.BULK_SMS_ON_DEMAND_SERVICE);
		}
		return bulkSmsOnDemandService;
	}
	public ActionForward viewBulkSmsOnDemand(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BulkSmsOnDemandAction::viewBulkSmsOnDemand::START------------>:::::::");
		try {
			BulkSmsOnDemandForm bulkSmsOnDemandForm = new BulkSmsOnDemandForm();
			BulkSmsOnDemandTO bulkSmsOnDemandTO = (BulkSmsOnDemandTO) bulkSmsOnDemandForm.getTo();
			
			setFormValues(request, bulkSmsOnDemandTO);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("BulkSmsOnDemandAction::viewBulkSmsOnDemand::ERROR ------------>:::::::");
		}
		LOGGER.trace("BulkSmsOnDemandAction::viewBulkSmsOnDemand::END------------>:::::::");
		return mapping.findForward(BulkSmsOnDemandConstants.BULK_SMS_ON_DEMAND_SCREEN_NAME);		
	}
	private void setFormValues(HttpServletRequest request,
			BulkSmsOnDemandTO bulkSmsOnDemandTO) throws CGBusinessException,
			CGSystemException {
		List<RegionTO> regionTOs = null;
		List<CityTO> cityTOs = null;
		RegionTO loginRegionTO = null;
		OfficeTO loginOfficeTO = getLoginOfficeTO(request).getOfficeTo();
		bulkSmsOnDemandTO.setOfficeType(loginOfficeTO.getOfficeTypeTO().getOffcTypeCode());
		
		if(StringUtils.equalsIgnoreCase(bulkSmsOnDemandTO.getOfficeType(), CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
			loginRegionTO = loginOfficeTO.getRegionTO();
			bulkSmsOnDemandTO.setRegionId(loginRegionTO.getRegionId());
			request.setAttribute(BulkSmsOnDemandConstants.REGION_ID, loginRegionTO.getRegionId());
		}
		bulkSmsOnDemandService = getBulkSmsOnDemandBean();
		
		regionTOs = bulkSmsOnDemandService.getAllRegions();
		
		if(!StringUtil.isEmptyInteger(bulkSmsOnDemandTO.getRegionId())){
			cityTOs = bulkSmsOnDemandService.getCitiesByRegion(bulkSmsOnDemandTO.getRegionId());
		}/*else if(!StringUtil.isEmptyInteger(bulkSmsOnDemandTO.getOfficeId())){
			officeTOs = bulkSmsOnDemandService.getAllOfficesByCity(bulkSmsOnDemandTO.getCityId());
		}*/
		Map<String,String> cnStatusMap = getAllConsignmentStatus();
		
		request.setAttribute(BulkSmsOnDemandConstants.REGION_LIST, regionTOs);
		request.setAttribute(BulkSmsOnDemandConstants.CITY_LIST, cityTOs);
//		request.setAttribute(BulkSmsOnDemandConstants.OFFICE_LIST, officeTOs);
		request.setAttribute(BulkSmsOnDemandConstants.CN_STATUS_LIST, cnStatusMap);
		request.setAttribute(BulkSmsOnDemandConstants.PARAM_TODAY_DATE, DateUtil.getCurrentDateInDDMMYYYY());
	}
	private Map<String, String> getAllConsignmentStatus() {
		Map<String,String> cnStatusMap = new HashMap<String,String>();
//		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_BOOK, BulkSmsOnDemandConstants.BOOK);
		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_RTH, BulkSmsOnDemandConstants.RTH);
//		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_RTOH, BulkSmsOnDemandConstants.RTO);
		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_PENDING, BulkSmsOnDemandConstants.PENDING);
//		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_DELIVERED, BulkSmsOnDemandConstants.DELIVERED);
//		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS, BulkSmsOnDemandConstants.RTO_DRS);
//		cnStatusMap.put(CommonConstants.CONSIGNMENT_STATUS_STOPDELV, BulkSmsOnDemandConstants.STOPDELV);
		return cnStatusMap;
	}
	private UserInfoTO getLoginOfficeTO(HttpServletRequest request) {
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		return userInfoTO;
	}
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("BulkSmsOnDemandAction::getCitiesByRegion::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String region = request.getParameter(BulkSmsOnDemandConstants.REGION_ID);
			List<CityTO> cityTOs=null;
			if(StringUtils.isNotEmpty(region)){
				Integer regionId=Integer.parseInt(region);
				bulkSmsOnDemandService = getBulkSmsOnDemandBean();
				cityTOs = bulkSmsOnDemandService.getCitiesByRegion(regionId);
			}
			if (!StringUtil.isEmptyList(cityTOs))
				jsonResult = serializer.toJSON(cityTOs).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getCitiesByRegion :: ", e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getCitiesByRegion :: ", e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getCitiesByRegion :: ", e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BulkSmsOnDemandAction::getCitiesByRegion::END------------>:::::::");
	}
	public void getOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("BulkSmsOnDemandAction::getOfficesByCity::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter(BulkSmsOnDemandConstants.CITY_ID);
			List<OfficeTO> officeTOs=null;
			if(StringUtils.isNotEmpty(city)){
				Integer cityId=Integer.parseInt(city);
				bulkSmsOnDemandService = getBulkSmsOnDemandBean();
				officeTOs = bulkSmsOnDemandService.getAllOfficesByCity(cityId);
			}
			if (!StringUtil.isEmptyList(officeTOs))
				jsonResult = serializer.toJSON(officeTOs).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getOfficesByCity :: ", e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getOfficesByCity :: ", e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getOfficesByCity :: ", e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BulkSmsOnDemandAction::getOfficesByCity::END------------>:::::::");
	}
	
	public void getConsignmentDetailsByStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		String jsonResult = null;
		BulkSmsOnDemandTO smsOnDemandTO = null;
		try {
			out = response.getWriter();
			BulkSmsOnDemandForm bulkSmsOnDemandForm = (BulkSmsOnDemandForm) form;
			smsOnDemandTO = (BulkSmsOnDemandTO) bulkSmsOnDemandForm.getTo();
			
			bulkSmsOnDemandService = getBulkSmsOnDemandBean();
			bulkSmsOnDemandService.getConsignmentDetailsByStatus(smsOnDemandTO);

			if (!StringUtil.isEmptyList(smsOnDemandTO.getConsignmentDtlTOs()))
				jsonResult = serializer.toJSON(smsOnDemandTO).toString();
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getConsignmentDetailsByStatus :: ", e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getConsignmentDetailsByStatus :: ", e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("BulkSmsOnDemandAction::getConsignmentDetailsByStatus :: ", e);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		} 
		LOGGER.trace("BulkSmsOnDemandAction::getConsignmentDetailsByStatus::END------------>:::::::");
	}
	
	public void sendBulkSMS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BulkSmsOnDemandAction::sendBulkSMS::START----->");
		PrintWriter out = null;
		String result= null;
		try {
			out = response.getWriter();
			BulkSmsOnDemandForm bulkSmsOnDemandForm = (BulkSmsOnDemandForm) form;
			BulkSmsOnDemandTO bulkSmsOnDemandTO = (BulkSmsOnDemandTO) bulkSmsOnDemandForm.getTo();
			bulkSmsOnDemandTO.setLoginUserId(getLoginOfficeTO(request).getUserto().getUserId());
			
			result = getBulkSmsOnDemandBean().sendBulkSMS(bulkSmsOnDemandTO);
			if(StringUtils.equalsIgnoreCase(result, CommonConstants.SUCCESS)){
				result = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, getMessageFromErrorBundle(request,
						BulkSmsOnDemandConstants.SMS_SENT_SUCCESSFULLY, null));
			}
			if(StringUtils.equalsIgnoreCase(result, CommonConstants.ERROR_MESSAGE)){
				result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getMessageFromErrorBundle(request,
						BulkSmsOnDemandConstants.PLEASE_ENTER_MOBILE_NOS, null));
			}			
		}catch (CGBusinessException e) {
			LOGGER.error("BulkSmsOnDemandAction::sendBulkSMS ::=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ConsigBulkSmsOnDemandActionnmentTrackingAction::sendBulkSMS ::=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("BulkSmsOnDemandAction::sendBulkSMS ::=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkSmsOnDemandAction::sendBulkSMS::END----->");
	}
}