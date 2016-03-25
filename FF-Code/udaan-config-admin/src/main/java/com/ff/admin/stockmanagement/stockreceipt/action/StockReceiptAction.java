package com.ff.admin.stockmanagement.stockreceipt.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.action.AbstractStockAction;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockreceipt.constants.StockReceiptConstants;
import com.ff.admin.stockmanagement.stockreceipt.form.StockReceiptForm;
import com.ff.admin.stockmanagement.stockreceipt.service.StockReceiptService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.umc.UserTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockReceiptAction.
 *
 * @author mohammes
 */

public class StockReceiptAction	extends AbstractStockAction {
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReceiptAction.class);
	
	/** The receipt service. */
	private StockReceiptService receiptService = null; 
	
	/**
	 * Gets the receipt service for stock.
	 *
	 * @return the receipt service for stock
	 */
	private StockReceiptService getReceiptServiceForStock()
	{
		if(StringUtil.isNull(receiptService)) {
			receiptService = (StockReceiptService)getBean(AdminSpringConstants.STOCK_RECEIPT_SERVICE);
		}
		return receiptService;
	}
	
	/**
	 * Name 	: viewFormDetails
	 * purpose 	: to view stock receipt form
	 * Input 	:
	 * return 	: populate form with default values.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReceiptAction::viewFormDetails ..Start");

		StockReceiptTO to = new StockReceiptTO();
		receiptStartUp(request, to);
		saveToken(request);
		((StockReceiptForm)form).setTo(to);
		
		LOGGER.debug("StockReceiptAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Receipt start up.
	 *
	 * @param request the request
	 * @param receiptTo the receipt to
	 */
	private void receiptStartUp(HttpServletRequest request, 
			StockReceiptTO receiptTo){

		UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			receiptTo.setLoggedInUserId(userTo.getUserId());
			if(StringUtil.isEmptyInteger(receiptTo.getCreatedByUserId())) {
				receiptTo.setCreatedByUserId(userTo.getUserId());
				receiptTo.setUpdatedByUserId(userTo.getUserId());
			}
		}
		
		OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null) {
			receiptTo.setLoggedInOfficeId(officeTo.getOfficeId());
			receiptTo.setLoggedInOfficeCode(officeTo.getOfficeCode());
			if(StringUtil.isEmptyInteger(receiptTo.getReceiptOfficeId())){
				receiptTo.setReceiptOfficeId(officeTo.getOfficeId());
			}
		}
		if(StringUtil.isStringEmpty(receiptTo.getReceiptDateStr())){
			receiptTo.setReceiptDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			//to.setReceiptTimeStr(DateUtil.getCurrentTime());
		}
		
		
		setGlobalDetails(request,(StockHeaderTO)receiptTo);

	}
	
	/**
	 * Name 	: saveReceiptDetails
	 * purpose 	: to save stock receipt details in the database
	 * Input 	: StockReceiptForm with StockReceiptTO
	 * return 	: populate confirmation message to the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveReceiptDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("StockReceiptAction::saveReceiptDetails ..Start");
		receiptService = getReceiptServiceForStock();
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		StockReceiptForm receiptForm = (StockReceiptForm)form;
		StockReceiptTO to =(StockReceiptTO) receiptForm.getTo();
		String rhoReceipt=to.getScreenType();
		String action = null;
		try {
			if(!isTokenValid(request)){
				actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			}else{
				if(StringUtil.isEmptyLong(to.getStockReceiptId())) { 
					//Save operation
					action = StockCommonConstants.SAVED;
					result = receiptService.saveReceiptDtls(to);
				  } else { 
					//Update operation
					action = StockCommonConstants.UPDATED;
					result = receiptService.updateReceiptDtls(to);
				}
				if(result) {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_RECEIPT_NUM,to.getAcknowledgementNumber(),action);
					/*request.setAttribute("url", "./stockReceipt.do?submitName=viewFormDetails");*/
				} else {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_RECEIPT_NUM,action);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("StockReceiptAction::saveReceiptDetails ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockReceiptAction::saveReceiptDetails ..Exception::",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_RECEIPT);
		} catch (Exception e) {
			LOGGER.error("StockReceiptAction::saveReceiptDetails ..Exception::",e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_RECEIPT);
			getGenericException(request, e);
		} finally {
			resetToken(request);
			if(!StringUtil.isStringEmpty(rhoReceipt)){
			 setUrl(request, "./stockReceiptRho.do?submitName=viewFormDetails");
			}
			 else{
				 setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
			 }
			prepareActionMessage(request, actionMessage);
			to = new StockReceiptTO();
			receiptStartUp(request, to);
		}
		((StockReceiptForm) form).setTo(to);
		LOGGER.debug("StockReceiptAction::saveReceiptDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Name 	: 	findDetailsByReceiptNumber
	 * purpose 	: 	to find receipt details from the DB by the branch
	 * Input 	: 	StockReceiptForm with StockReceiptTO
	 * return 	: 	populate stock receipt details in the screen for modify/update/edit.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByReceiptNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReceiptAction::findDetailsByReceiptNumber ..Start");
		receiptService = getReceiptServiceForStock();
		StockReceiptForm recForm=(StockReceiptForm)form;
		StockReceiptTO receiptTo = (StockReceiptTO)recForm.getTo();
		ActionMessage actionMessage = null;
		try {
			receiptTo = receiptService.findDetailsByReceiptNumber(receiptTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus1 = ExceptionUtil.checkError(receiptTo);
			if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(receiptTo, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByReceiptNumber ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
			receiptTo = new StockReceiptTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RECEIPT);
			LOGGER.error("StockReceiptAction::findDetailsByReceiptNumber ..Exception::",e);
		}catch (Exception e) {
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
			receiptTo = new StockReceiptTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RECEIPT);
			LOGGER.error("StockReceiptAction::findDetailsByReceiptNumber ..Exception::",e);
		}finally{
			prepareActionMessage(request, actionMessage);
			receiptStartUp(request, receiptTo);
		}
		((StockReceiptForm) form).setTo(receiptTo);
		LOGGER.debug("StockReceiptAction::findDetailsByReceiptNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Name 	: 	findDetailsByIssueNumber
	 * purpose 	: 	to find stock issue details from the DB by the branch
	 * Input 	:
	 * return 	: 	populate stock issue details in the screen.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByIssueNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReceiptAction::findDetailsByIssueNumber ..Start");
		receiptService = getReceiptServiceForStock();
		StockReceiptForm recForm=(StockReceiptForm)form;
		StockReceiptTO receiptTo =(StockReceiptTO) recForm.getTo();
		ActionMessage actionMessage = null;
		try {
			receiptTo = receiptService.findDetailsByIssueNumber(receiptTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus1 = ExceptionUtil.checkError(receiptTo);
			if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(receiptTo, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByIssueNumber ..Exception::",e);
			getBusinessError(request,e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			receiptTo = new StockReceiptTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			LOGGER.error("StockReceiptAction::findDetailsByIssueNumber ..Exception::",e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByIssueNumber ..Exception::",e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		}finally{
			prepareActionMessage(request, actionMessage);
			receiptStartUp(request, receiptTo);
		}
		((StockReceiptForm) form).setTo(receiptTo);
		LOGGER.debug("StockReceiptAction::findDetailsByIssueNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Name 	: 	findDetailsByRequisitionNumber
	 * purpose 	: 	to find requisition details from the DB by the branch
	 * Input 	:
	 * return 	: 	populate stock requisition details in the screen.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByRequisitionNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReceiptAction::findDetailsByRequisitionNumber ..Start");
		receiptService = getReceiptServiceForStock();
		StockReceiptForm recForm=(StockReceiptForm)form;
		StockReceiptTO receiptTo = (StockReceiptTO)recForm.getTo();
		ActionMessage actionMessage = null;
		try {
			receiptTo = receiptService.findDetailsByRequisitionNumber(receiptTo);
			//check any warnings/Business Exceptions
			boolean errorStatus1 = ExceptionUtil.checkError(receiptTo);
			if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(receiptTo, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception::",e);
			getBusinessError(request,e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			receiptTo = new StockReceiptTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception::",e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception::",e);
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
		}finally{
			prepareActionMessage(request, actionMessage);
			receiptStartUp(request, receiptTo);
		}
		((StockReceiptForm) form).setTo(receiptTo);
		LOGGER.debug("StockReceiptAction::findDetailsByRequisitionNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	/**
	 * findDetailsByRequisitionNumberAtRHO
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findDetailsByRequisitionNumberAtRHO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReceiptAction::findDetailsByRequisitionNumber ..Start");
		receiptService = getReceiptServiceForStock();
		StockReceiptForm recForm=(StockReceiptForm)form;
		StockReceiptTO receiptTo = (StockReceiptTO)recForm.getTo();
		ActionMessage actionMessage = null;
		try {
			receiptTo = receiptService.findDetailsByRequisitionNumber(receiptTo);
			//check any warnings/Business Exceptions
			boolean errorStatus1 = ExceptionUtil.checkError(receiptTo);
			if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(receiptTo, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception:",e);
			getBusinessError(request,e);
			setUrl(request, "./stockReceiptRho.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			receiptTo = new StockReceiptTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception:",e);
			setUrl(request, "./stockReceiptRho.do?submitName=viewFormDetails");
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			receiptTo = new StockReceiptTO();
			LOGGER.error("StockReceiptAction::findDetailsByRequisitionNumber ..Exception:",e);
			setUrl(request, "./stockReceiptRho.do?submitName=viewFormDetails");
		}finally{
			prepareActionMessage(request, actionMessage);
			receiptStartUp(request, receiptTo);
		}
		((StockReceiptForm) form).setTo(receiptTo);
		LOGGER.debug("StockReceiptAction::findDetailsByRequisitionNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	/**
	 * Checks if is valid series for stock receipt.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void isValidSeriesForStockReceipt(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response){
		LOGGER.debug("GoodsIssueAction::validateSerialNumbersInGoodsReceipt::Start=======>");
		StockValidationTO validationTo=null; 
		String stSIno=request.getParameter(StockCommonConstants.REQ_PARAM_START_SERIAL_NUMBER);
		String qnty=request.getParameter(StockCommonConstants.REQ_PARAM_QUANTITY);
		
		String itemId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_ID);
		String itemDetailsId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_DETAILS_ID);
		String receiptItemDtlId=request.getParameter(StockReceiptConstants.RECEIPT_ITEM_DTLS_ID);
		String seriesType=request.getParameter(StockCommonConstants.SERIES_TYPE);
		String transactionType=request.getParameter(StockReceiptConstants.TRANSACTION_TYPE);
		String transactionNumber=request.getParameter(StockCommonConstants.TRANSACTION_NUMBER);
		String seriesStartsWith=request.getParameter(StockCommonConstants.REQ_PARAM_SERIES_STARTS_WITH);
		String rhoScreenType=request.getParameter(StockCommonConstants.REQ_PARAM_RHO_SCREEN);
		Integer loggedInofficeId=getLoginOfficeTO(request).getOfficeId();
		
		
		String result="";
		
		
		PrintWriter out=null;
		
		try {
			validationTo=new StockValidationTO();
			out=response.getWriter();
			receiptService = getReceiptServiceForStock();
			validationTo.setStartSerialNumber(stSIno);
			validationTo.setQuantity(StringUtil.convertStringToInteger(qnty));
			validationTo.setItemId(StringUtil.convertStringToInteger(itemId));
			validationTo.setItemDetailsId(StringUtil.convertStringToLong(itemDetailsId));
			validationTo.setStockReceiptItemDetailsId(StringUtil.convertStringToLong(receiptItemDtlId));
			validationTo.setLoggedInOfficeId(loggedInofficeId);
			validationTo.setPartyTypeId(loggedInofficeId);
			validationTo.setSeriesType(seriesType);
			validationTo.setTransactionType(transactionType);
			validationTo.setTransactionNumber(transactionNumber);
			validationTo.setForRhoScreen(rhoScreenType);
			if(!StringUtil.isStringEmpty(rhoScreenType) && !StringUtil.isStringEmpty(transactionType)&& transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE) ){
				validationTo.setOfficeProduct(seriesStartsWith);
			}else{
				setGlobalDetailsForValidations(request,validationTo);
				
				
			}
			result= receiptService.isValidSeriesForReceipt(validationTo);
			
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockReceiptAction::isValidSeriesForStockReceipt::CGBusinessException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("StockReceiptAction::isValidSeriesForStockReceipt::CGSystemException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("StockReceiptAction::isValidSeriesForStockReceipt::Exception=======>",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(StockUniveralConstants.RESP_ERROR,exception);	
		}
		out.print(result);
		out.flush();
	}
	
}
