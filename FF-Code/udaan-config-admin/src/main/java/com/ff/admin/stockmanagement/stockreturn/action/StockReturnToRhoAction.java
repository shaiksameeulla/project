/**
 * 
 */
package com.ff.admin.stockmanagement.stockreturn.action;

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
import com.ff.admin.stockmanagement.stockreturn.form.StockReturnForm;
import com.ff.admin.stockmanagement.stockreturn.service.StockReturnService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;
import com.ff.umc.UserTO;

/**
 * The Class StockReturnTORhoAction.
 *
 * @author mohammes
 */
public class StockReturnToRhoAction extends AbstractStockAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReturnToRhoAction.class);
	
	/** The stock return service. */
	public StockReturnService stockReturnService;
	
	/**
	 * View form details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockReturnToRhoAction::viewFormDetails ..Start");
		
		StockReturnTO returnTo = new StockReturnTO();
		returnStartUp(request, returnTo);
		saveToken(request);
		((StockReturnForm) form).setTo(returnTo);
		LOGGER.debug("StockReturnToRhoAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	/**
	 * Find details by issue number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByAcknowledgeNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		LOGGER.debug("StockReturnToRhoAction::findDetailsByAckNumber ..Start");
		
		stockReturnService = getReturnServiceForStock();
		StockReturnForm returnForm = (StockReturnForm)form;
		StockReturnTO returnTo = (StockReturnTO)returnForm.getTo();
		ActionMessage actionMessage = null;
		 try {
			 returnTo = stockReturnService.findDetailsByAcknowledgementNumber(returnTo);
			final boolean errorStatus1 = ExceptionUtil.checkError(returnTo);
			 if(errorStatus1) {
				ExceptionUtil.prepareActionMessage(returnTo, request);
				saveActionMessage(request);
			 }
		} catch (CGBusinessException e) {
			returnTo = new StockReturnTO();
			LOGGER.error("StockReturnToRhoAction::findDetailsByAckNumber ..Exception",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			returnTo = new StockReturnTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RETURN);
			LOGGER.error("StockReturnToRhoAction::findDetailsByAckNumber ..Exception",e);
		}catch (Exception e) {
			returnTo = new StockReturnTO();
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RETURN);
			getGenericException(request, e);
			LOGGER.error("StockReturnToRhoAction::findDetailsByAckNumber ..Exception",e);
		} finally {
			prepareActionMessage(request, actionMessage);
			returnStartUp(request, returnTo);
		}
		((StockReturnForm) form).setTo(returnTo);
		LOGGER.debug("StockReturnToRhoAction::findDetailsByAckNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
		
	}
	
	/**
	 * Find details by return number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByReturnNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		LOGGER.debug("StockReturnToRhoAction::findDetailsByReturnNumber ..Start");
		
		stockReturnService = getReturnServiceForStock();
		StockReturnForm returnForm = (StockReturnForm)form;
		StockReturnTO returnTo = (StockReturnTO)returnForm.getTo();
		ActionMessage actionMessage = null;
		 try {
			 returnTo = stockReturnService.findDetailsByReturnNumber(returnTo);
			 boolean errorStatus1 = ExceptionUtil.checkError(returnTo);
			 if(errorStatus1) {
				ExceptionUtil.prepareActionMessage(returnTo, request);
				saveActionMessage(request);
			 }
		} catch (CGBusinessException e) {
			returnTo = new StockReturnTO();
			LOGGER.error("StockReturnToRhoAction::findDetailsByReturnNumber ..Exception",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			returnTo = new StockReturnTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RETURN);
			LOGGER.error("StockReturnToRhoAction::findDetailsByReturnNumber ..Exception",e);
		}catch (Exception e) {
			returnTo = new StockReturnTO();
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_RETURN);
			getGenericException(request, e);
			LOGGER.error("StockReturnToRhoAction::findDetailsByReturnNumber ..Exception",e);
		} finally {
			prepareActionMessage(request, actionMessage);
			returnStartUp(request, returnTo);
		}
		((StockReturnForm) form).setTo(returnTo);
		LOGGER.debug("StockReturnToRhoAction::findDetailsByReturnNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	
		
	}
	
	
	/**
	 * Return start up.
	 *
	 * @param request the request
	 * @param to the to
	 */
	private void returnStartUp(HttpServletRequest request,StockReturnTO to) {
		
		UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			to.setLoggedInUserId(userTo.getUserId());
			if(StringUtil.isEmptyInteger(to.getCreatedByUserId())) {
				to.setCreatedByUserId(userTo.getUserId());
				to.setUpdatedByUserId(userTo.getUserId());
			}
		}
		
		OfficeTO officeTo = getLoginOfficeTO(request);
		
		if(officeTo!=null){
			if(!StringUtil.isEmptyInteger(officeTo.getReportingRHO())){
			to.setIssuedOfficeId(officeTo.getReportingRHO());
			}else{
				prepareActionMessage(request, AdminErrorConstants.REPORTING_RHO_NOT_EXIST_FOR_LOGGED_IN_OFFICE);
			}
			to.setLoggedInOfficeId(officeTo.getOfficeId());
			if(!StringUtil.isEmptyInteger(officeTo.getOfficeId())){
				to.setReturningOfficeId(officeTo.getOfficeId());
			}
			to.setLoggedInOfficeCode(officeTo.getOfficeCode());
			if(officeTo.getOfficeTypeTO()!=null){
			to.setOfficeType(officeTo.getOfficeTypeTO().getOffcTypeCode());
			}
		}
		
		if(StringUtil.isStringEmpty(to.getReturnDateStr())){
			to.setReturnDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		
		setGlobalDetails(request,(StockHeaderTO)to);
		
	}
	
	/**
	 * Gets the return service for stock.
	 *
	 * @return the return service for stock
	 */
	private StockReturnService getReturnServiceForStock() {
		if(StringUtil.isNull(stockReturnService)){
			stockReturnService = (StockReturnService)getBean(AdminSpringConstants.STOCK_RETURN_SERVICE);
		}
			return stockReturnService;
	}
	
	/**
	 * Save return details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveReturnDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("StockReturnToRhoAction::saveReturnDetails ..Start");
		
		stockReturnService = getReturnServiceForStock();
		Boolean result =Boolean.FALSE;
		ActionMessage actionMessage=null;
		StockReturnForm returnForm=(StockReturnForm)form;
		StockReturnTO returnTo = (StockReturnTO)returnForm.getTo(); 
		String action=null;
		 
		 try {
			 
			 if(StringUtil.isEmptyLong(returnTo.getStockReturnId())){
					//save operation
					action=StockCommonConstants.SAVED;
					result = stockReturnService.saveReturnDetails(returnTo);
				}else{
					//update Operation
					action=StockCommonConstants.UPDATED;
					//not implemented
					result = stockReturnService.saveReturnDetails(returnTo);
				}
				 

				 if(result){			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_RETURN_NUM,returnTo.getStockReturnNumber(),action);
				 }else {			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_RETURN,action);
				 }
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockReturnToRhoAction::saveReturnDetails ..Exception",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockReturnToRhoAction::saveReturnDetails ..Exception",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_RETURN);
		}catch (Exception e) {
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_RETURN);
			LOGGER.error("StockReturnToRhoAction::saveReturnDetails ..Exception",e);
			getGenericException(request, e);
		}finally{
			prepareActionMessage(request, actionMessage);
			returnTo = new StockReturnTO();
			returnStartUp(request, returnTo);
		}
		 ((StockReturnForm) form).setTo(returnTo);
		LOGGER.debug("StockReturnToRhoAction::saveReturnDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Checks if is valid series for stock return.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void isValidSeriesForStockReturn(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response){
		
		LOGGER.debug("StockReturnToRhoAction::isValidSeriesForStockReturn::Start");
		StockValidationTO validationTo=null;
		String stSIno=request.getParameter(StockCommonConstants.REQ_PARAM_START_SERIAL_NUMBER);
		String qnty=request.getParameter(StockCommonConstants.REQ_PARAM_QUANTITY);
		String transactionNumber=request.getParameter(StockCommonConstants.TRANSACTION_NUMBER);
		String itemId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_ID);
		String itemDetailsId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_DETAILS_ID);
		Integer loggedInofficeId=getLoginOfficeTO(request).getOfficeId();
		String seriesType=request.getParameter(StockCommonConstants.SERIES_TYPE);
		
		String result = "";
		
		PrintWriter out = null;
		
		try {
			validationTo=new StockValidationTO();
			out=response.getWriter();
			stockCommonService = getCommonServiceForStock();
			validationTo.setStartSerialNumber(stSIno);
			validationTo.setQuantity(StringUtil.convertStringToInteger(qnty));
			validationTo.setItemId(StringUtil.convertStringToInteger(itemId));
			validationTo.setStockReceiptItemDetailsId(StringUtil.convertStringToLong(itemDetailsId));
			validationTo.setTransactionNumber(transactionNumber);
			validationTo.setPartyTypeId(loggedInofficeId);
			validationTo.setLoggedInOfficeId(loggedInofficeId);
			validationTo.setSeriesType(seriesType);
			setGlobalDetailsForValidations(request,validationTo);
			result= stockCommonService.isSeriesValidForStockReturnFromBranch(validationTo);
			
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockReturnToRhoAction::isValidSeriesForStockReturn::CGBusinessException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("StockReturnToRhoAction::isValidSeriesForStockReturn::CGSystemException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("StockReturnToRhoAction::isValidSeriesForStockReturn::Exception=======>",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);	
		}
		out.print(result);
		out.flush();
	}
	
	
}

