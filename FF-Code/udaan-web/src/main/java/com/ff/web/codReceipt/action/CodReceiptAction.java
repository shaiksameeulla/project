package com.ff.web.codReceipt.action;

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

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.OfficeTO;
import com.ff.to.codreceipt.CodReceiptTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.codReceipt.constants.CodReceiptConstants;
import com.ff.web.codReceipt.service.CodReceiptService;
import com.ff.web.common.SpringConstants;

public class CodReceiptAction extends CGBaseAction {
	
	private CodReceiptService codReceiptService;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CodReceiptAction.class);
	
	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("CodReceiptAction::preparePage::START----->");
		request.setAttribute("currDate", DateUtil.getCurrentDateInYYYYMMDDHHMM());
		LOGGER.debug("CodReceiptAction::preparePage::END----->");
		return mapping.findForward(CodReceiptConstants.SUCCESS);
	}
	
	public void searchConsignmentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CodReceiptAction::searchConsignmentDetails::START----->");
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		String jsonResult =null;
		PrintWriter out = null;
		CodReceiptTO codReceiptTO=null;
		JSONSerializer serializer=null;
		try {
			 out = response.getWriter();
			String consgNo = request.getParameter("ConsgNo");
			codReceiptService = (CodReceiptService) getBean(SpringConstants.COD_RECEIPT_SERVICE);
			codReceiptTO=codReceiptService.getConsgDetails(consgNo);
			//String codNo=codReceiptService.generateCodReceiptNumber(loggedInOfficeTO.getOfficeCode());
			if(!StringUtil.isNull(codReceiptTO)){
				codReceiptTO.setRegionName(loggedInOfficeTO.getRegionTO().getRegionName());
				codReceiptTO.setBranchName(loggedInOfficeTO.getOfficeName());
				jsonResult = serializer.toJSON(codReceiptTO).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CodReceiptAction :: searchConsignmentDetails() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CodReceiptAction :: searchConsignmentDetails() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CodReceiptAction :: searchConsignmentDetails() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CodReceiptAction::searchConsignmentDetails::END----->");
	}
	 
	
	public ActionForward printCodReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		ActionMessage actionMessage = null;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		CodReceiptTO codReceiptTO=null;
		try{
			String consgNo = request.getParameter("ConsgNo");
			codReceiptService = (CodReceiptService) getBean(SpringConstants.COD_RECEIPT_SERVICE);
			codReceiptTO=codReceiptService.getConsgDetails(consgNo);
			codReceiptTO.setCurrDate(DateUtil.getCurrentDateInYYYYMMDDHHMM().toString());
			codReceiptTO.setRegionName(loggedInOfficeTO.getRegionTO().getRegionName());
			codReceiptTO.setBranchName(loggedInOfficeTO.getOfficeName());
			String codNo=codReceiptService.generateCodReceiptNumber(loggedInOfficeTO.getOfficeCode());
			if(!StringUtil.isNull(codNo)){
				codReceiptTO.setCodReceiptNo(codNo);
			}
			request.setAttribute("codReceiptDetails", codReceiptTO);
		} catch (CGBusinessException e) {
			LOGGER.error("CodReceiptAction::printCodReceipt ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("CodReceiptAction::printCodReceipt ..CGSystemException :"+e);
			//actionMessage =  new ActionMessage(e.getMessage());
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("CodReceiptAction::printCodReceipt ..Exception :"+e);
			getGenericException(request,e);
			//prepareCommonException(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CodReceiptAction::printCodReceipt::END----->");
		return mapping.findForward(CodReceiptConstants.URL_CODRECEIPT_PRINT);
	}
	
}
