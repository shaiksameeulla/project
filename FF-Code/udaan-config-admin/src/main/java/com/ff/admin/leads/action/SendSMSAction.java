/**
 * 
 */
package com.ff.admin.leads.action;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.SendSMSForm;
import com.ff.admin.leads.service.SendSMSService;
import com.ff.leads.LeadTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;

/**
 * @author abarudwa
 *
 */
public class SendSMSAction extends CGBaseAction{
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SendSMSAction.class);
	
	private SendSMSService sendSMSService;

	/**
	 * @return the sendSMSService
	 */
	public SendSMSService getSendSMSService() {
		if (StringUtil.isNull(sendSMSService)){
			sendSMSService = (SendSMSService) getBean(AdminSpringConstants.SEND_SMS_SERVICE);
		}
		return sendSMSService;
	}
	
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("SendSMSAction::preparePage::START------------>:::::::");
		LeadTO leadTO = null;
		SendSMSForm sendSMSForm = null;
		ActionMessage actionMessage = null;
		try{
			sendSMSForm = (SendSMSForm) form;
			leadTO = (LeadTO) sendSMSForm.getTo();
			sendSMSService = getSendSMSService();
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
			
			String phone = userInfoTO.getEmpUserTo().getEmpTO().getEmpPhone();
			leadTO.setSmsPhoneNo(phone);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if(!StringUtil.isNull(officeTO)){
				leadTO.setUserId(userInfoTO.getUserto().getUserId());
				leadTO.setLoginOfficeId(officeTO.getOfficeId());
				leadTO.setLoginOfficeCode(officeTO.getOfficeCode());
				leadTO.setRegionId(officeTO.getRegionTO().getRegionId());
				leadTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			}
			String leadNumber = request.getParameter("leadNumber");
			leadTO = sendSMSService.getLeadDetails(leadNumber);
			
			((SendSMSForm)form).setTo(leadTO);	
		} catch(CGSystemException  e) {
		    LOGGER.error("SendSMSAction::preparePage::CGSystemException", e);
		    getSystemException(request,e);
		} catch(CGBusinessException e) {
			 LOGGER.error("SendSMSAction::preparePage::CGBusinessException", e);
			 getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Error occured in SendSMSAction :: preparePage() ::", e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("SendSMSAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
	}
	
	public ActionForward sendSMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("SendSMSAction::sendSMS::START------------>:::::::");
		SendSMSForm sendSMSForm = (SendSMSForm)form;
		LeadTO leadTO2 = (LeadTO) sendSMSForm.getTo();
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
			Integer userId = userInfoTO.getUserto().getUserId();
			
			String salesExecutive = request.getParameter("salesExecutive");
			String customer = request.getParameter("customer");
			String leadNumber = request.getParameter("leadNumber");
			sendSMSService = getSendSMSService();
			LeadTO leadTO = new LeadTO();
			leadTO = sendSMSService.getLeadDetails(leadNumber);
			if(!StringUtil.isNull(salesExecutive))
			if(salesExecutive.equals(LeadCommonConstants.SMS_SALES_EXECUTIVE)){
				leadTO.setSmsPhoneNo(leadTO.getAssignedTo().getEmpTO().getEmpPhone());
			}
			if(!StringUtil.isNull(customer))
			if(customer.equals(LeadCommonConstants.SMS_CUSTOMER)){
				leadTO.setSmsPhoneNo(leadTO.getMobileNo());
			}
			
			jsonResult = sendSMSService.sendSMS(leadTO.getSmsPhoneNo(), leadTO2.getDescription(), userId);
			
		} catch(CGSystemException  e) {
			LOGGER.error("SendSMSAction::sendSMS() .. :" + e);
			jsonResult = LeadCommonConstants.ERROR_IN_SENDING_SMS;
		} catch(CGBusinessException e) {
			LOGGER.error("SendSMSAction::sendSMS() .. :" + e);
			jsonResult = LeadCommonConstants.ERROR_IN_SENDING_SMS;
		}catch(Exception e){
			LOGGER.error("SendSMSAction::sendSMS() .. :" + e);
			jsonResult = LeadCommonConstants.ERROR_IN_SENDING_SMS;
		}finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		return mapping.findForward(LeadCommonConstants.SUCCESS); 
	}

}
