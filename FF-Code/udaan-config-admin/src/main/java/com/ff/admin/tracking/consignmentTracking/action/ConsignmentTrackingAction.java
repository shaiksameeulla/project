/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.consignmentTracking.form.ConsignmentTrackingForm;
import com.ff.admin.tracking.consignmentTracking.service.ConsignmentTrackingService;
import com.ff.notification.service.TrackingNotificationService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingConsignmentTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.organization.service.OrganizationCommonService;

/**
 * @author uchauhan
 * 
 */

public class ConsignmentTrackingAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentTrackingAction.class);
	
	/** The consignment tracking service. */
	private ConsignmentTrackingService consignmentTrackingService;

	/** The organizationCommonService. */
	private OrganizationCommonService organizationCommonService;

	public ActionForward viewConsignmentTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ConsignmentTrackingAction::viewConsignmentTracking::START----->");
		ActionMessage actionMessage = null;
		String requestType = null;
		try {
			requestType = request.getParameter("screen");
			consignmentTrackingService = (ConsignmentTrackingService) getBean(AdminSpringConstants.CONSIGNMENT_TRACKING_SERVICE);
			List<StockStandardTypeTO> typeNameTo = consignmentTrackingService.getTypeName();
			request.setAttribute(AdminSpringConstants.TYPENAME_TO, typeNameTo);
			
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("userType", userInfoTO.getUserto().getUserType());
		}  catch (CGBusinessException e) {
			LOGGER.error("ConsignmentTrackingAction::viewConsignmentTracking ..CGBusinessException :",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentTrackingAction::viewConsignmentTracking ..CGSystemException :",e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("ConsignmentTrackingAction::viewConsignmentTracking ..Exception :",e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ConsignmentTrackingAction::viewConsignmentTracking::END----->");
		if(StringUtils.equalsIgnoreCase(requestType, "trackingPopup")){
			request.setAttribute("cmp", "Y");
			return mapping.findForward(AdminSpringConstants.VIEW_TRACKING_POPUP);
		}else{
			return mapping.findForward(CommonConstants.SUCCESS);
		}		
	}

	public void viewTrackInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("ConsignmentTrackingAction::viewTrackInformation::START----->");
		PrintWriter out = null;
		String manifestTOJSON = null;
		TrackingConsignmentTO trackingTO = null;
		try {
			out = response.getWriter();
			String type = request.getParameter("type");
			String number = request.getParameter("number");
			
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			String loginUserType = userInfoTO.getUserto().getUserType();
			consignmentTrackingService = (ConsignmentTrackingService) getBean(AdminSpringConstants.CONSIGNMENT_TRACKING_SERVICE);
			if (type.equalsIgnoreCase("CN")) {
				logger.append("\nConsignmentTrackingAction::viewTrackInformation::Track Consignment No ----->"+ number);
				logger.append(" ::Start Time::" + startTimeInMilis);
				LOGGER.debug(logger.toString());
				
				String consgNum = number;
				trackingTO = consignmentTrackingService.viewTrackInformation(consgNum, null, loginUserType);
			} else if (type.equalsIgnoreCase("RN")) {
				logger.append("\nConsignmentTrackingAction::viewTrackInformation::Track Reference No ----->"+ number);
				logger.append(" ::Start Time::" + startTimeInMilis);
				LOGGER.debug(logger.toString());
				
				String refNum = number;
				trackingTO = consignmentTrackingService.viewTrackInformation(null, refNum, loginUserType);
			}
					
			manifestTOJSON = JSONSerializer.toJSON(trackingTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentTrackingAction::viewTrackInformation ..CGBusinessException :",e);
			manifestTOJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("ConsignmentTrackingAction::viewTrackInformation ..CGSystemException :",e);
			String exception=getSystemExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}catch (Exception e) {
			LOGGER.error("ConsignmentTrackingAction :: viewTrackInformation() :: ERROR :: ",e);
			String exception=getGenericExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		} 
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("ConsignmentTrackingAction::viewTrackInformation::END----->:: End Time : " +
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}

	public ActionForward showOffice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ConsignmentTrackingAction::showOffice::START----->");
		OfficeTO officeTO = null;
		ActionMessage actionMessage = null;
		try {

			String officeId = request.getParameter("officeId");
			if (officeId != null) {
				Integer offcId = Integer.parseInt(officeId);
				organizationCommonService = (OrganizationCommonService) getBean(AdminSpringConstants.ORGANIZATION_COMMON_SERVICE);
				officeTO = organizationCommonService.getOfficeDetails(offcId);
				ConsignmentTrackingForm consgForm = (ConsignmentTrackingForm) form;
				TrackingConsignmentTO consgTO = (TrackingConsignmentTO) consgForm
						.getTo();
				consgTO.setOfficeTO(officeTO);
				request.setAttribute("consgTO", consgTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentTrackingAction::showOffice ..CGBusinessException :",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentTrackingAction::showOffice ..CGSystemException :",e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("ConsignmentTrackingAction::showOffice ..Exception :",e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("ConsignmentTrackingAction::showOffice::START----->");
		return mapping.findForward(CommonConstants.OFFICE_POPUP);
	}

	@SuppressWarnings("static-access")
	public void sendMailOrSMS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ConsignmentTrackingAction::sendMailOrSMS::START----->");
		String mobile = request.getParameter("mobileNo");
		String email = request.getParameter("mailId");
		String cnNumber = request.getParameter("cnNumber");
		String status = request.getParameter("status");
		PrintWriter out = null;
		String statusMessage= null;
		String manifestTOJSON = null;
		JSONSerializer serializer = null;
		boolean track=false;
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		try {
			if((mobile == null || mobile.isEmpty()) && (email == null || email.isEmpty())){
				statusMessage="Mobile or email is mandatory";
				return;
			}
			TrackingNotificationService notifyService = (TrackingNotificationService) getBean("trackingNotificationService");
			if(mobile != null && !mobile.isEmpty())
				track = notifyService.sendSMSNotification(cnNumber, mobile);
			if(email != null && !email.isEmpty())
				track = notifyService.sendEmailNotification(cnNumber, email);
			out = response.getWriter();
			if(track==true){
				statusMessage="{\"message\":\"SUCCESS\"}";
				manifestTOJSON = serializer.toJSON(statusMessage).toString();
				LOGGER.debug("ConsignmentTrackingAction::sendMailOrSMS::--->"+track);
			}else{
				statusMessage="{\"message\":\"ERROR\"}";
				manifestTOJSON = serializer.toJSON(statusMessage).toString();
				LOGGER.debug("ConsignmentTrackingAction::sendMailOrSMS::--->"+track);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CGBusinessException e) {
			e.printStackTrace();
		} catch (CGSystemException e) {
			e.printStackTrace();
		}finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		} 
		LOGGER.debug("ConsignmentTrackingAction::sendMailOrSMS::END----->");
	}
}
