/** Start of Changes by <31913> on 10/01/2013
 * 
 */
package com.ff.admin.stockmanagement.stocktransfer.action;

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
import com.ff.admin.stockmanagement.stocktransfer.constants.StockTransferConstants;
import com.ff.admin.stockmanagement.stocktransfer.form.StockTransferForm;
import com.ff.admin.stockmanagement.stocktransfer.service.StockTransferService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;
import com.ff.umc.UserTO;

/**
 * The Class StockTransferAction.
 *
 * @author hkansagr
 */

public class StockTransferAction extends AbstractStockAction{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockTransferAction.class);
	
	/** The transfer service. */
	public transient StockTransferService transferService;
	
	/**
	 * Gets the stock transfer service.
	 *
	 * @return the stock transfer service
	 */
	public StockTransferService getStockTransferService() {
		return transferService;
	}
	
	/**
	 * Sets the stock transfer service.
	 *
	 * @param transferService the new stock transfer service
	 */
	public void setStockTransferService(StockTransferService transferService) {
		this.transferService = transferService;
	}
	
	/**
	 * Gets the transfer service for stock.
	 *
	 * @return the transfer service for stock
	 */
	public StockTransferService getTransferServiceForStock() {
		
		if(StringUtil.isNull(transferService)){
			transferService = (StockTransferService)getBean(AdminSpringConstants.STOCK_TRANSFER_SERVICE);
		}
			return transferService;
	}
	
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
		LOGGER.debug("StockTransferAction::viewFormDetails ..Start");
		StockTransferTO transferTo=new StockTransferTO();
		
		transferStartUp(request,transferTo);
		saveToken(request);
		((StockTransferForm) form).setTo(transferTo);
		LOGGER.debug("StockTransferAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Transfer start up.
	 *
	 * @param request the request
	 * @param to the to
	 */
	
	private void transferStartUp(HttpServletRequest request, StockTransferTO to){
		final OfficeTO loggedInofficeTO=getLoginOfficeTO(request);
		final UserTO userTo = getLoginUserTO(request);
		setStockTransferStdTypeDetails(request);
		if(to!=null){
			
			
			if(loggedInofficeTO!=null){
				to.setLoggedInOfficeCode(loggedInofficeTO.getOfficeCode());
				to.setLoggedInOfficeId(loggedInofficeTO.getOfficeId());
				to.setLoggedInOfficeName(loggedInofficeTO.getOfficeName());
			}
			if(StringUtil.isStringEmpty(to.getTransferDateStr())){
				to.setTransferDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			}
			
			if(StringUtil.isEmptyLong(to.getStockTransferId())){
				to.setStockIssueNumber(null);
				to.setStockTransferNumber(null);
				
			}
			if(userTo!=null) {
				to.setLoggedInUserId(userTo.getUserId());
			}
			if(userTo!=null && StringUtil.isEmptyInteger(to.getCreatedByUserId())) {
				to.setCreatedByUserId(userTo.getUserId());
				to.setUpdatedByUserId(userTo.getUserId());
			}
			if(!StringUtil.isEmptyLong(to.getStockTransferId())){
				request.setAttribute(StockCommonConstants.REQ_PARAM_CNOTES,to.getItemMap());
			}else{
				getCnoteDetails(request);
			}
			setGlobalDetails(request,(StockHeaderTO)to);
			setConfigParamsForStock(request,(StockHeaderTO)to);
		}
	}
	
	
	/**
	 * Name : saveTransferDtls
	 * purpose : to save stock Transfer details in the database
	 * Input : StockTransferForm with StockTransferTO
	 * return : propagate confirmation message to the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveTransferDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("StockTransferAction::saveTransferDtls ..Start");
		
		transferService = getTransferServiceForStock(); 
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		StockTransferForm transferForm = (StockTransferForm)form;
		StockTransferTO transferTo =(StockTransferTO) transferForm.getTo();
		String action=null;
		try {
			if(!isTokenValid(request)){
				actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			}else{
				if(StringUtil.isEmptyLong(transferTo.getStockTransferId())){
					//save operation
					action=StockCommonConstants.SAVED;
				result = transferService.saveTransferDetails(transferTo);
				}else{
					//update Operation
					action=StockCommonConstants.UPDATED;
					//FIXME yet to implement Update Functionality
				}
				if(result){			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_TRANSFER_NUM,transferTo.getStockTransferNumber(),action);
					
				}else {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_TRANSFER);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("StockTransferAction::saveTransferDtls  ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockTransferAction::saveTransferDtls  ..Exception::",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_TRANSFER);
		} catch (Exception e) {
			LOGGER.error("StockTransferAction::saveIssueDtlsStockTransferAction::saveTransferDtls  ..Exception::",e);
			getGenericException(request, e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_TRANSFER);
		} finally {
			resetToken(request);
			setUrl(request,"./stockTransfer.do?submitName=viewFormDetails");
			prepareActionMessage(request, actionMessage);
			transferTo = new StockTransferTO();
			transferStartUp(request,transferTo);
		}
		((StockTransferForm) form).setTo(transferTo);
		LOGGER.info("StockTransferAction::saveTransferDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * Name : findDetailsByTransferNumber
	 * Pre-Condition : logged in office should be equal to stock Transfer
	 * Purpose : to find requisition details from the database(By Requisition Number)
	 * Input : StockIssueForm with StockIssueTo
	 * Return : populate stock Transfer details in the screen for.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByTransferNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("StockTransferAction::findDetailsByTransferNumber ..Start");
		transferService =getTransferServiceForStock();
		StockTransferForm transferForm = (StockTransferForm)form;
		StockTransferTO transferTo = (StockTransferTO)transferForm.getTo();
		ActionMessage actionMessage = null;
		 try {
			 transferTo = transferService.findTransferDetails(transferTo);
			 prepareTransferPartyDtlsForFind(request, transferTo);
			 //check any warnings/Business Exceptions
			final boolean errorStatus1 = ExceptionUtil.checkError(transferTo);
			 if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(transferTo, request);
				saveActionMessage(request);
			 }
			 saveToken(request);
		} catch (CGBusinessException e) {
			transferTo = new StockTransferTO();
			LOGGER.error("StockTransferAction::findDetailsByTransferNumber ..Exception",e);
			getBusinessError(request,e);
			setUrl(request,"./stockTransfer.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			transferTo = new StockTransferTO();
			setUrl(request,"./stockTransfer.do?submitName=viewFormDetails");
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_TRANSFER);
			LOGGER.error("StockTransferAction::findDetailsByTransferNumber ..Exception",e);
		}catch (Exception e) {
			transferTo = new StockTransferTO();
			getGenericException(request, e);
			LOGGER.error("StockTransferAction::findDetailsByTransferNumber ..Exception",e);
			setUrl(request,"./stockTransfer.do?submitName=viewFormDetails");
		} finally {
			prepareActionMessage(request, actionMessage);
			transferStartUp(request,transferTo);
		}
		((StockTransferForm) form).setTo(transferTo);
		LOGGER.info("StockTransferAction::findDetailsByTransferNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Prepare transfer party dtls for find.
	 *
	 * @param request the request
	 * @param transferTo the transfer to
	 */
	private void prepareTransferPartyDtlsForFind(HttpServletRequest request,
			StockTransferTO transferTo) {
		request.setAttribute(StockCommonConstants.TRANSFER_TO_PARTY_MAP_REQ_PARAMS, transferTo.getTransferTOPartyMap());
		 request.setAttribute(StockCommonConstants.TRANSFER_FROM_PARTY_MAP_REQ_PARAMS, transferTo.getTransferFromPartyMap());
	}
	
	
	/**
	 * Ajax action series validation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxActionSeriesValidation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockTransferAction::ajaxActionSeriesValidation::Start=======>");
		String transferFrom=request.getParameter(StockTransferConstants.STOCK_TRANSFER_FROM);
		String transferTO=request.getParameter(StockTransferConstants.STOCK_TRANSFER_TO);
		
		String transferFromPersonId=request.getParameter(StockTransferConstants.STOCK_TRANSFER_FROM_PERSON_ID);
		
		String itemId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_ID);
		String qnty=request.getParameter(StockCommonConstants.REQ_PARAM_QUANTITY);
		String stNumber=request.getParameter(StockCommonConstants.REQ_PARAM_START_SERIAL_NUMBER);
		String endStNumber = request.getParameter(StockCommonConstants.REQ_PARAM_END_SERIAL_NUMBER);
		Integer loggedInofficeId=getLoginOfficeTO(request).getOfficeId();
		java.io.PrintWriter out=null;
		String result="";
		stockCommonService = getCommonServiceForStock();
		try {
			out=response.getWriter();
			StockValidationTO validationTo= new StockValidationTO();
			validationTo.setStartSerialNumber(stNumber);
			validationTo.setEndSerialNumber(endStNumber);
			validationTo.setPartyTypeId(StringUtil.parseInteger(transferFromPersonId));
			validationTo.setIssuedTOType(transferFrom);
			validationTo.setQuantity(StringUtil.parseInteger(qnty));
			validationTo.setItemId(StringUtil.parseInteger(itemId));
			validationTo.setTransferTO(transferTO);
			setGlobalDetailsForValidations(request,validationTo);
			validationTo.setLoggedInOfficeId(loggedInofficeId);
			long starttime=System.currentTimeMillis();
			LOGGER.debug("StockTransferAction::ajaxActionSeriesValidation ..Before Calling method: isSeriesValidForTransfer:[ "+starttime+"]");
			result= stockCommonService.isSeriesValidForTransfer(validationTo);
			long endtime=System.currentTimeMillis();
			LOGGER.debug("StockTransferAction::ajaxActionSeriesValidation ..After calling Method: isSeriesValidForTransfer Time [ "+endtime +"]Total diff :["+(endtime-starttime)+"]");
			
			validationTo=null;//nullifying the Object
		}catch (CGBusinessException e) {
			LOGGER.error("StockTransferAction::ajaxActionSeriesValidation::CGBusinessException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("StockTransferAction::ajaxActionSeriesValidation::CGSystemException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("StockTransferAction:: ajaxActionGetPartyTypeDetails", e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);	
		}
		
		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("StockTransferAction::ajaxActionGetPartyTypeDetails::end=======>");
	}
	
	
}
/** End of Changes by <31913> on 18/09/2013
 * 
 */
