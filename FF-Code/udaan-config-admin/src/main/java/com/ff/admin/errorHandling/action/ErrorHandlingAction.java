package com.ff.admin.errorHandling.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.errorHandling.service.ErrorHandlingService;
import com.ff.admin.leads.action.AbstractCreateLead;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.errorHandling.ErrorHandlingTo;

public class ErrorHandlingAction extends AbstractCreateLead {
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingAction.class);
	
	private ErrorHandlingService errorHandlingService;
	

	
	@SuppressWarnings("static-access")
	public ActionForward viewErrorHandlingScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("ErrorHandlingAction::viewErrorHandlingScreen::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		String effectiveFromDate = null;
		
		ActionMessage actionMessage = null;
		
		try{
			out = response.getWriter();
			effectiveFromDate = request.getParameter("effectiveFromStr");
			String effectiveToDate = request.getParameter("effectiveToStr");
			String interfaceName = request.getParameter("interfaceid");
			
			errorHandlingService = getErrorHandlingService();
			
			List<ErrorHandlingTo> ErrorHandlingToData = errorHandlingService.getSAPCustomerData(effectiveFromDate, effectiveToDate, interfaceName);
			LOGGER.debug("sapCustomerDataList: " + ErrorHandlingToData);
				
			if(effectiveFromDate != null) {
				jsonResult = serializer.toJSON(ErrorHandlingToData).toString();
			}
			
		}catch (CGBusinessException e) {
			LOGGER.error("ErrorHandlingAction::preparePage ..CGBusinessException :" + e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ErrorHandlingAction::preparePage ..CGSystemException :" + e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("ErrorHandlingAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
		}finally{
			if(effectiveFromDate != null) {
				out.print(jsonResult);
				out.flush();
				out.close();
			}
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ErrorHandlingAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
	}	
	
	@SuppressWarnings("static-access")
	public ActionForward viewTransactionErrorHandling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ErrorHandlingAction::viewTransactionErrorHandling::START------------>:::::::");
		
		ActionMessage actionMessage = null;
		String errorListElements = null;
		String strArray[] = null;
		try{
			String errorArray[] = request.getParameterValues("errorArray");
			String effectiveFromDate = request.getParameter("effectiveFromStr");
			String effectiveToDate = request.getParameter("effectiveToStr");
					
			String str = Arrays.toString(errorArray).replace("[", "").replace("]", "");
			
			List<ErrorHandlingTo>  errorHandlingToList = new ArrayList<ErrorHandlingTo>();
			
			StringTokenizer errorListTokenizer = new StringTokenizer(str, CommonConstants.COMMA);
			while(errorListTokenizer.hasMoreElements()){
				ErrorHandlingTo errorHandlingTo = new ErrorHandlingTo();
				errorListElements = errorListTokenizer.nextToken();
				
				strArray = errorListElements.split("~");
				errorHandlingTo.setCustomerNo(strArray[0]);
				errorHandlingTo.setException(strArray[1]);
				errorHandlingToList.add(errorHandlingTo);
			}
			
			request.setAttribute("errorHandlingToList", errorHandlingToList);
			request.setAttribute("effectiveFromDate", effectiveFromDate);
			request.setAttribute("effectiveToDate", effectiveToDate);
			
		}catch (Exception e) {
			LOGGER.error("ErrorHandlingAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
		}
		prepareActionMessage(request, actionMessage);
		
		LOGGER.debug("ErrorHandlingAction::viewTransactionErrorHandling::END------------>:::::::");
		return mapping.findForward("newJsp");
	}
	
	private ErrorHandlingService getErrorHandlingService(){
		if(StringUtil.isNull(errorHandlingService)){
			errorHandlingService = (ErrorHandlingService) getBean(AdminSpringConstants.ERROR_HANDLING_SERVICE);
		}
		return errorHandlingService;
	}	

}
