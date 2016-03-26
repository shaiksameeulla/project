package com.ff.web.inbound.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.inbound.InboundBranchManualDataProcessor;
import com.ff.jobservices.JobServicesTO;
import com.ff.web.jobservice.constants.JobServicesConstants;
import com.ff.web.jobservice.form.JobServiceForm;

/**
 * @author mohammes
 * 
 */
public class InboundServiceAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(InboundServiceAction.class);
	public transient JSONSerializer serializer;

	

	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("InboundServiceAction::preparePage::START------------>:::::::");
		JobServicesTO jobServiceTO = new JobServicesTO();
		((JobServiceForm) form).setTo(jobServiceTO);
		LOGGER.debug("InboundServiceAction::preparePage::END------------>:::::::");

		return mapping.findForward(JobServicesConstants.SUCCESS_FORWARD);
	}
	
	public void prepareInboundZip(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response){
		LOGGER.debug("InboundServiceAction::prepareInboundZip::Start=======>");
		String jsonResult="";
		InboundBranchManualDataProcessor inboundBranchManualDataProcessor = null;
		PrintWriter out=null;
		
		try {
			out=response.getWriter();
			HttpSession sess= request.getSession(false);
			if(sess!=null){
				sess.removeAttribute(BcunConstant.DUMP_URL_SESSION);
			}
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			inboundBranchManualDataProcessor = (InboundBranchManualDataProcessor)getBean("inboundBranchManualDataProcessor");
			if(inboundBranchManualDataProcessor !=null){
				jsonResult=inboundBranchManualDataProcessor.proceedDatasync();
				sess.setAttribute(BcunConstant.DUMP_URL_SESSION,jsonResult);
				jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG, jsonResult);
			}else{
				jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, "Please activiate BCUN application");
			}
		}catch (CGBusinessException e) {
			LOGGER.error("InboundServiceAction::prepareInboundZip ..CGBusinessException :",e);
			jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("InboundServiceAction::prepareInboundZip ..CGSystemException :",e);
			jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("InboundServiceAction::prepareInboundZip ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}
		LOGGER.debug("InboundServiceAction::prepareInboundZip::END=======>");
		out.print(jsonResult);
		out.flush();
		out.close();
	}
}
