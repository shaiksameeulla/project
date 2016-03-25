package com.ff.admin.stockmanagement.stockcancel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.action.AbstractStockAction;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockcancel.form.StockCancellationForm;
import com.ff.admin.stockmanagement.stockcancel.service.StockCancellationService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;
import com.ff.umc.UserTO;

/**
 * The Class StockCancellationAction.
 */
public class StockCancellationAction extends AbstractStockAction{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockCancellationAction.class);
	
	/** The cancellation service. */
	public StockCancellationService cancellationService;
	       
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
		
		LOGGER.debug("StockCancellationAction::viewFormDetails ..Start");
		
		StockCancellationTO cancellationTo = new StockCancellationTO();
		cancellationStartUp(request, cancellationTo);
		((StockCancellationForm) form).setTo(cancellationTo);
		
		LOGGER.debug("StockCancellationAction::viewFormDetails ..END");
		
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Save cancellation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveCancellation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("StockCancellationAction::saveCancellation ..Start");
		
		cancellationService = getStockCancellationService();
		Boolean result =Boolean.FALSE;
		ActionMessage actionMessage=null;
		StockCancellationForm cancelForm=(StockCancellationForm)form;
		StockCancellationTO cancellationTo =(StockCancellationTO)cancelForm.getTo(); 
		 
		 try {
				 result = cancellationService.saveCancellation(cancellationTo);

				 if(result){			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_CANCELLATION_NUM,cancellationTo.getCancellationNo(),StockCommonConstants.SAVED);
					 setUrl(request,"./stockCancel.do?submitName=viewFormDetails");
				 }else {			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_CANCELLATION);
				 }
			
		}catch (CGBusinessException e) {
			LOGGER.error("StockCancellationAction::saveCancellation ..Exception", e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockCancellationAction::saveCancellation ..Exception", e);
		} catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_CANCELLATION);
			LOGGER.error("StockCancellationAction::saveCancellation ..Exception", e);
		}finally{
			prepareActionMessage(request, actionMessage);
			cancellationTo = new StockCancellationTO();
			cancellationStartUp(request, cancellationTo);
		}
		 
	    ((StockCancellationForm) form).setTo(cancellationTo);
	    
		LOGGER.debug("StockCancellationAction::saveCancellation ..END");
		
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Gets the stock cancellation service.
	 *
	 * @return the stock cancellation service
	 */
	private StockCancellationService getStockCancellationService() {
		if(StringUtil.isNull(cancellationService)){
			cancellationService = (StockCancellationService)getBean(AdminSpringConstants.STOCK_CANCELLATION_SERVICE);
		}
			return cancellationService;
	}
	
/**
 * Cancellation start up.
 *
 * @param request the request
 * @param to the to
 */
private void cancellationStartUp(HttpServletRequest request,StockCancellationTO to) {
		
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
			to.setLoggedInOfficeId(officeTo.getOfficeId());
			if(!StringUtil.isEmptyInteger(officeTo.getOfficeId())){
				to.setCancellationOfficeId(officeTo.getOfficeId());
			}
			to.setLoggedInOfficeCode(officeTo.getOfficeCode());
		}
		
		if(StringUtil.isStringEmpty(to.getCancelledDateStr())){
			to.setCancelledDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		if(!StringUtil.isEmptyLong(to.getStockCancelledId())){
			request.setAttribute(StockCommonConstants.REQ_PARAM_SERIAL_NUMBER_ITEMS,to.getItemMap());
		}else{
			getSerialNumberedItemDetails(request);
		}
		setGlobalDetails(request,(StockHeaderTO)to);
	}

/**
 * Find details by cancellation number.
 *
 * @param mapping the mapping
 * @param form the form
 * @param request the request
 * @param response the response
 * @return the action forward
 */
public ActionForward findDetailsByCancellationNumber(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response){

	LOGGER.debug("StockCancellationAction::findDetailsByCancellationNumber ..Start");
	
	cancellationService = getStockCancellationService();
	StockCancellationForm cancelForm = (StockCancellationForm)form;
	StockCancellationTO cancellationTo = (StockCancellationTO)cancelForm.getTo();
	ActionMessage actionMessage = null;
	
	 try {
		 cancellationTo = cancellationService.findDetailsByCancellationNumber(cancellationTo);
		final boolean errorStatus1 = ExceptionUtil.checkError(cancellationTo);
		 if(errorStatus1) {
			ExceptionUtil.prepareActionMessage(cancellationTo, request);
			saveActionMessage(request);
		 }
	} catch (CGBusinessException e) {
		cancellationTo = new StockCancellationTO();
		LOGGER.error("StockCancellationAction::findDetailsByCancellationNumber ..Exception", e);
		getBusinessError(request,e);
		setUrl(request,"./stockCancel.do?submitName=viewFormDetails");
	} catch (CGSystemException e) {
		cancellationTo = new StockCancellationTO();
		actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_CANCELLATION);
		LOGGER.error("StockCancellationAction::findDetailsByCancellationNumber ..Exception", e);
		setUrl(request,"./stockCancel.do?submitName=viewFormDetails");
	}catch (Exception e) {
		cancellationTo = new StockCancellationTO();
		actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_CANCELLATION);
		LOGGER.error("StockCancellationAction::findDetailsByCancellationNumber ..Exception", e);
		setUrl(request,"./stockCancel.do?submitName=viewFormDetails");
	} finally {
		prepareActionMessage(request, actionMessage);
		cancellationStartUp(request, cancellationTo);
	}
	((StockCancellationForm) form).setTo(cancellationTo);
	LOGGER.debug("StockCancellationAction::findDetailsByCancellationNumber ..END");
	return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);

	
}




}
