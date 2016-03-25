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

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.action.AbstractStockAction;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockrequisition.form.ListStockRequisitionForm;
import com.ff.admin.stockmanagement.stockrequisition.service.StockRequisitionService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;

/**
 * The Class ListStockRequisitionAction.
 *
 * @author mohammes
 */
public class ListStockRequisitionAction extends AbstractStockAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ListStockRequisitionAction.class);

	/** The stock req service. */
	public StockRequisitionService stockReqService;
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

	/**
	 * Name : viewFormDetails
	 * purpose : to view created Requisition and/or Approved requisition form
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
		LOGGER.debug("ListStockRequisitionAction::viewFormDetails ..Start");
		ListStockRequisitionTO requisitionTo = new ListStockRequisitionTO();
		requisitionStartUp(request, requisitionTo);
		saveToken(request);
		((ListStockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("ListStockRequisitionAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * requisitionStartUp
	 * @param request
	 * @param requisitionTo
	 */
	private void requisitionStartUp(HttpServletRequest request,
			ListStockRequisitionTO requisitionTo) {

		final OfficeTO officeTo=getLoginOfficeTO(request);
		if(!StringUtil.isNull(officeTo)){
			requisitionTo.setLoggedInOfficeId(officeTo.getOfficeId());
			requisitionTo.setLoggedInOfficeCode(officeTo.getOfficeCode());
		}else{
			ActionMessage actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.LOGGED_IN_OFFICE);
			prepareActionMessage(request, actionMessage);
		}
		
		if(StringUtil.isStringEmpty(requisitionTo.getFromDateStr())){
			requisitionTo.setFromDateStr(DateUtil.todayDate());
			requisitionTo.setToDateStr(DateUtil.todayDate());
		}
		setUserDtlsDetails(request, (StockHeaderTO)requisitionTo);
		getOfficeMapUnderLoggedInOffice(request);
		setStockRequisitionStdTypeDetails(request);
		
	}

	/**
	 * searchRequisitonDetails:: Get Details based on given Inputs
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchRequisitonDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ListStockRequisitionAction::searchRequisitonDetails ..Start");
		ActionMessage actionMessage=null;
		ListStockRequisitionForm issueForm = (ListStockRequisitionForm)form;
		ListStockRequisitionTO requisitionTo = (ListStockRequisitionTO)issueForm.getTo();

		stockReqService=getReqServiceForStock();
		try {
			requisitionTo =stockReqService.searchRequisitionDetails(requisitionTo);
		} catch (CGBusinessException e) {
			requisitionTo= new ListStockRequisitionTO();
			getBusinessError(request,e);
			LOGGER.error("ListStockRequisitionAction::searchRequisitonDetails ::CGBusinessException:",e);
		}catch (CGSystemException e) {
			setUrl(request, "./stockReceipt.do?submitName=viewFormDetails");
			requisitionTo = new ListStockRequisitionTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("ListStockRequisitionAction::searchRequisitonDetails ..Exception::" ,e);
		}catch (Exception e) {
			requisitionTo= new ListStockRequisitionTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("ListStockRequisitionAction::searchRequisitonDetails ::Exception:",e);
		}
		prepareActionMessage(request, actionMessage);
		requisitionStartUp(request, requisitionTo);
		((ListStockRequisitionForm) form).setTo(requisitionTo);
		LOGGER.debug("ListStockRequisitionAction::searchRequisitonDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}


}
