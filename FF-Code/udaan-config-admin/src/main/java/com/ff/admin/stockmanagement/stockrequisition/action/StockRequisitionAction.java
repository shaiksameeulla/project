/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.action;

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
import com.ff.admin.stockmanagement.stockrequisition.form.StockRequisitionForm;
import com.ff.admin.stockmanagement.stockrequisition.service.StockRequisitionService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;
import com.ff.umc.UserTO;

/**
 * The Class StockRequisitionAction.
 *
 * @author mohammes
 */
public class StockRequisitionAction extends AbstractStockAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockRequisitionAction.class);
	
	/** The stock req service. */
	public StockRequisitionService stockReqService;
	
	/**
	 * Name : viewFormDetails
	 * purpose : to view create Requisition and Approve requisition form
	 * Input :
	 * return : populate form with default values.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockRequisitionAction::viewFormDetails ..Start");
		StockRequisitionTO requisitionTo = new StockRequisitionTO();
		requisitionStartUp(request, requisitionTo,null);
		saveToken(request);
		((StockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("StockRequisitionAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * Requisition start up.
	 *
	 * @param request the request
	 * @param requisitionTo the requisition to
	 * @param flag the flag
	 */
	private void requisitionStartUp(HttpServletRequest request,
			StockRequisitionTO requisitionTo,String flag) {
		final UserTO userTo = getLoginUserTO(request);

		if(userTo!=null){
			requisitionTo.setLoggedInUserId(userTo.getUserId());
			
			if(StringUtil.isEmptyInteger(requisitionTo.getCreatedByUserId())){
				requisitionTo.setCreatedByUserId(userTo.getUserId());
				requisitionTo.setUpdatedByUserId(userTo.getUserId());
			}
			
		}
		final OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null){
			requisitionTo.setLoggedInOfficeId(officeTo.getOfficeId());
			requisitionTo.setLoggedInOfficeName(officeTo.getOfficeCode()+FrameworkConstants.CHARACTER_HYPHEN+officeTo.getOfficeName());
			if(StringUtil.isEmptyInteger(requisitionTo.getRequisitionOfficeId())){
				requisitionTo.setRequisitionOfficeId(officeTo.getOfficeId());
			}
			requisitionTo.setLoggedInOfficeCode(officeTo.getOfficeCode());
			if(StringUtil.isEmptyInteger(requisitionTo.getLoggedInRho())){
			requisitionTo.setLoggedInRho(officeTo.getReportingRHO());
			}
			if(officeTo.getOfficeTypeTO()!=null){
				requisitionTo.setOfficeType(officeTo.getOfficeTypeTO().getOffcTypeCode());
			}
		}
		if(StringUtil.isStringEmpty(requisitionTo.getReqCreatedDateStr())){
			requisitionTo.setReqCreatedDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			//to.setReqCreatedTimeStr(DateUtil.getCurrentTime());
		}
		//for approval
		requisitionTo.setStatus(StockCommonConstants.ACTIVE_STATUS);
		requisitionTo.setApproveFlagYes(StockCommonConstants.IS_APPROVED_Y);
		
		if(!StringUtil.isEmptyLong(requisitionTo.getStockRequisitionId()) && StringUtil.isStringEmpty(flag)){
			setItemDetails(request); 
		}
		
	}
	
	//Branch functionality start from here
	/**
	 * Name : saveRequisitionDtls
	 * purpose : to save stock requisition details in the db by the Branch
	 * Input : StockRequisitinForm with StockRequistiionTo
	 * return : populate confirmation message to the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveRequisitionDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockRequisitionAction::saveRequisitionDtls ..Start");
		stockReqService = getReqServiceForStock();
		Boolean result =Boolean.FALSE;
		ActionMessage actionMessage=null;
		StockRequisitionForm reqForm=(StockRequisitionForm)form;
		StockRequisitionTO requisitionTo =(StockRequisitionTO) reqForm.getTo();
		setUserDtlsDetails(request, requisitionTo);
		String action= StockCommonConstants.SAVED;
		 try {
			 if(!isTokenValid(request)){
				 actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			 }else{
				 result = stockReqService.saveStockRequisition(requisitionTo);

				 if(result){	
					 if(!StringUtil.isEmptyLong(requisitionTo.getStockRequisitionId())){
						 action=StockCommonConstants.UPDATED;
					 }
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_REQUISITION_NUM,requisitionTo.getRequisitionNumber(),action);
				 }else {			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_REQUISITION);
				 }
			 }
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockRequisitionAction::saveRequisitionDtls ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockRequisitionAction::saveRequisitionDtls ..Exception::",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
		}catch (Exception e) {
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockRequisitionAction::saveRequisitionDtls ..Exception::",e);
			getGenericException(request, e);
		}finally{
			resetToken(request);
			 setUrl(request, "./createRequisition.do?submitName=viewFormDetails");
			prepareActionMessage(request, actionMessage);
			requisitionTo=new StockRequisitionTO();
			requisitionStartUp(request, requisitionTo,null);
		}
		 ((StockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("StockRequisitionAction::saveRequisitionDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Name : findRequisitionDtlsByReqNumber
	 * purpose : to find requisition dtls from the db by the Branch
	 * Input : StockRequisitinForm with StockRequistiionTo
	 * return : populate stock requisiton details in the screen for Modify/update/edit.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findRequisitionDtlsByReqNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockRequisitionAction::findRequisitionDtlsByReqNumber ..Start");
		stockReqService = getReqServiceForStock();
		StockRequisitionForm reqForm=(StockRequisitionForm)form;
		StockRequisitionTO requisitionTo = (StockRequisitionTO)reqForm.getTo();
		ActionMessage actionMessage=null;
		 try {
			 if(StringUtil.isStringEmpty(requisitionTo.getRequisitionNumber())){
				 actionMessage =  new ActionMessage(AdminErrorConstants.STOCK_NUMBER_EMPTY,StockCommonConstants.STOCK_REQUISITION);
			 }else{
				 requisitionTo.setRequisitionNumber(requisitionTo.getRequisitionNumber().toUpperCase());
				 requisitionTo = stockReqService.findRequisitionDtlsByReqNumber(requisitionTo);
			 }
			
			 //check any warnings/Business Exceptions
			final boolean errorStatus1 = ExceptionUtil.checkError(requisitionTo);
				if(errorStatus1) {
					//if so extract them and propagate to screen
					ExceptionUtil.prepareActionMessage(requisitionTo, request);
					saveActionMessage(request);
				}
				saveToken(request);
		} catch (CGBusinessException e) {
			requisitionTo=new StockRequisitionTO();
			LOGGER.error("StockRequisitionAction::findRequisitionDtlsByReqNumber ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			requisitionTo=new StockRequisitionTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockRequisitionAction::findRequisitionDtlsByReqNumber ..Exception::",e);
		}catch (Exception e) {
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			requisitionTo=new StockRequisitionTO();
			LOGGER.error("StockRequisitionAction::findRequisitionDtlsByReqNumber ..Exception::",e);
		}finally{
			prepareActionMessage(request, actionMessage);
			requisitionStartUp(request, requisitionTo,null);
		}
		 ((StockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("StockRequisitionAction::findRequisitionDtlsByReqNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	//RHO functionality start from here
	/**
	 * Name : searchRequisitionDtls
	 * purpose : to search requisition dtls from the db by the RHO
	 * Input : StockRequisitinForm with StockRequistiionTo
	 * return : populate stock requisiton details in the screen for approve.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward searchRequisitionDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockRequisitionAction::searchRequisitionDtls ..Start");
		stockReqService = getReqServiceForStock();
		StockRequisitionForm reqForm=(StockRequisitionForm)form;
		StockRequisitionTO requisitionTo =(StockRequisitionTO) reqForm.getTo();
		ActionMessage actionMessage=null;
		String requisitonNumber=null;
		 try {
			 requisitonNumber = request.getParameter(StockCommonConstants.QRY_PARAM_REQ_NUMBER);
			 if(!StringUtil.isStringEmpty(requisitonNumber)){
				 requisitionTo = new StockRequisitionTO();
				 requisitionTo.setRequisitionNumber(requisitonNumber);
			 }
			 requisitionStartUp(request, requisitionTo,null);
			 requisitionTo = stockReqService.findReqDtlsByReqNumberForApprove(requisitionTo);
			 if(!StringUtil.isNull(requisitionTo)&& !StringUtil.isEmptyLong(requisitionTo.getStockRequisitionId())){
				 setProcurementStdTypeDetails(request);
				 if(isCorporateOffice(request)){
					 request.setAttribute(StockCommonConstants.CORPORATE_OFFICE_TYPE, StockCommonConstants.CORPORATE_OFFICE_TYPE);
				 }
			 }
			 saveToken(request);
		} catch (CGBusinessException e) {
			requisitionTo=new StockRequisitionTO();
			LOGGER.error("StockRequisitionAction::searchRequisitionDtls ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			requisitionTo=new StockRequisitionTO();
			LOGGER.error("StockRequisitionAction::searchRequisitionDtls ..Exception::",e);
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			requisitionTo=new StockRequisitionTO();
			LOGGER.error("StockRequisitionAction::searchRequisitionDtls ..Exception::",e);
		}finally{
			prepareActionMessage(request, actionMessage);
			requisitionStartUp(request, requisitionTo,StockCommonConstants.ACTIVE_STATUS);
		}
		 ((StockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("StockRequisitionAction::searchRequisitionDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Name : approveRequisitionDtls
	 * purpose : to update requisition dtls in the db by the RHO
	 * Input : StockRequisitinForm with StockRequistiionTo
	 * return : populate confirmation message to the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward approveRequisitionDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockRequisitionAction::approveRequisitionDtls ..Start");
		stockReqService = getReqServiceForStock();
		Boolean result =Boolean.FALSE;
		ActionMessage actionMessage=null;
		StockRequisitionForm reqForm=(StockRequisitionForm)form;
		StockRequisitionTO requisitionTo = (StockRequisitionTO)reqForm.getTo();
		
		 try {
			 if(!isTokenValid(request)){
				 actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			 }else{
				 result = stockReqService.approveStockRequisition(requisitionTo);

				 if(result){			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_REQUISITION_NUM,requisitionTo.getRequisitionNumber(),StockCommonConstants.APPROVED);
				 }else {			
					 actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_REQUISITION);
				 }
			 }
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockRequisitionAction::approveRequisitionDtls ..Exception::",e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("StockRequisitionAction::approveRequisitionDtls ..Exception::",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockRequisitionAction::approveRequisitionDtls ..Exception::",e);
		}finally{
			resetToken(request);
			prepareActionMessage(request, actionMessage);
			requisitionTo=new StockRequisitionTO();
			requisitionStartUp(request, requisitionTo,null);
			setUrl(request, "./approveRequisition.do?submitName=viewFormDetails");
		}
		 ((StockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("StockRequisitionAction::approveRequisitionDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Gets the req service for stock.
	 *
	 * @return the req service for stock
	 */
	private StockRequisitionService getReqServiceForStock() {
		if(StringUtil.isNull(stockReqService)){
			stockReqService = (StockRequisitionService)getBean(AdminSpringConstants.STOCK_REQUISTION_SERVICE);
		}
			return stockReqService;
	}
}
