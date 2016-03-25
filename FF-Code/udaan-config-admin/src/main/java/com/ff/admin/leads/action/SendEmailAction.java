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
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.SendEmailForm;
import com.ff.admin.leads.service.SendEmailService;
import com.ff.leads.LeadTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.mysql.jdbc.StringUtils;

/**
 * @author abarudwa
 *
 */
public class SendEmailAction extends CGBaseAction{
	
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SendEmailAction.class);
	
	private SendEmailService sendEmailService = null; 
	
	/**
	 * @return the sendEmailService
	 */
	public SendEmailService getSendEmailService() {
		if (StringUtil.isNull(sendEmailService)){
			sendEmailService = (SendEmailService) getBean(AdminSpringConstants.SEND_EMAIL_SERVICE);
		}
		return sendEmailService;
	}

	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("SendEmailAction::preparePage::START------------>:::::::");
		LeadTO leadTO = null;
		SendEmailForm sendEmailForm = null;
		ActionMessage actionMessage = null;
		try{
			sendEmailForm = (SendEmailForm) form;
			leadTO = (LeadTO) sendEmailForm.getTo();
			sendEmailService = getSendEmailService();
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
			
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if(!StringUtil.isNull(officeTO)){
				leadTO.setUserId(userInfoTO.getUserto().getUserId());
				leadTO.setLoginOfficeId(officeTO.getOfficeId());
				leadTO.setLoginOfficeCode(officeTO.getOfficeCode());
				leadTO.setRegionId(officeTO.getRegionTO().getRegionId());
				leadTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			}
			String leadNumber = request.getParameter("leadNumber");
			leadTO = sendEmailService.getLeadDetails(leadNumber);
			
			
			((SendEmailForm)form).setTo(leadTO);	
		} catch(CGSystemException  e) {
		    LOGGER.error("CreateLeadAction::preparePage::CGSystemException", e);
		    getSystemException(request,e);
		} catch(CGBusinessException e) {
			 LOGGER.error("CreateLeadAction::preparePage::CGBusinessException", e);
			 getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Error occured in SendEmailAction :: preparePage() ::" ,e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("SendEmailAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
	}
	
	public ActionForward sendEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("SendEmailAction::sendEmail::START------------>:::::::");
		SendEmailForm sendEmailForm = (SendEmailForm)form;
		LeadTO leadTO = (LeadTO) sendEmailForm.getTo();
		//int count=10;
		String[] sentCcs= {};
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			MailSenderTO mailSenderTO = new MailSenderTO();
			if(!StringUtils.isNullOrEmpty(leadTO.getSentTo()))
			{
			String[] sendTos = leadTO.getSentTo().split(CommonConstants.CHARACTER_SEMI_COLON);
			//leadTO.setTo(sendTos);
			mailSenderTO.setTo(sendTos);
			}
			if(!StringUtils.isNullOrEmpty(leadTO.getSentCc()))
			{
				sentCcs = leadTO.getSentCc().split(CommonConstants.CHARACTER_SEMI_COLON);
				mailSenderTO.setCc(sentCcs);
			}
			mailSenderTO.setMailSubject(leadTO.getSubject());
			mailSenderTO.setPlainMailBody(leadTO.getDescription());
			jsonResult = sendEmailService.sendEmail(mailSenderTO);
		}catch(Exception e){
			LOGGER.error("SendEmailAction::sendEmail() .. :" + e);
			jsonResult = LeadCommonConstants.ERROR_IN_SENDING_EMAIL;
		}finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SendEmailAction::sendEmail() ::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS); 
	}
	
	@SuppressWarnings("static-access")
	public void emailSalesExecutive(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.trace("SendEmailAction :: emailSalesExecutive() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		SendEmailForm sendEmailForm = (SendEmailForm)form;
		LeadTO leadTO = (LeadTO) sendEmailForm.getTo();
		try{
			String leadNumber = request.getParameter("leadNumber");
			out = response.getWriter();
			sendEmailService = getSendEmailService();
			leadTO = sendEmailService.getLeadDetails(leadNumber);
			jsonResult = serializer.toJSON(leadTO).toString();
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SendEmailAction :: emailSalesExecutive() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SendEmailAction :: emailSalesExecutive() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SendEmailAction :: emailSalesExecutive() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("SendEmailAction::emailSalesExecutive()  ::END------------>:::::::");
	}
	@SuppressWarnings("static-access")
	public void emailCustomer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.trace("SendEmailAction :: emailCustomer() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		SendEmailForm sendEmailForm = (SendEmailForm)form;
		LeadTO leadTO = (LeadTO) sendEmailForm.getTo();
		try{
			String leadNumber = request.getParameter("leadNumber");
			out = response.getWriter();
			sendEmailService = getSendEmailService();
			leadTO = sendEmailService.getLeadDetails(leadNumber);
			jsonResult = serializer.toJSON(leadTO).toString();
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SendEmailAction :: emailCustomer() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SendEmailAction :: emailCustomer() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SendEmailAction :: emailCustomer() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("SendEmailAction::emailCustomer()  ::END------------>:::::::");
	}
}
