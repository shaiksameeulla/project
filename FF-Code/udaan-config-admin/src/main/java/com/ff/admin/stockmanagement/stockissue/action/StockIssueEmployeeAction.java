package com.ff.admin.stockmanagement.stockissue.action;

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
import com.ff.admin.stockmanagement.stockissue.form.StockIssueEmployeeForm;
import com.ff.admin.stockmanagement.stockissue.service.StockIssueService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;
import com.ff.umc.UserTO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * The Class StockIssueEmployeeAction.
 *
 * @author hkansagr
 */

public class StockIssueEmployeeAction extends AbstractStockAction
{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockIssueEmployeeAction.class);
	
	/** The stock issue service. */
	public transient StockIssueService stockIssueService;
	
	/**
	 * Gets the issue emp service for stock.
	 *
	 * @return the issue emp service for stock
	 */
	private StockIssueService getIssueEmpServiceForStock()
	{
		if(StringUtil.isNull(stockIssueService)) {
			stockIssueService = (StockIssueService)getBean(AdminSpringConstants.STOCK_ISSUE_SERVICE);
		}
		return stockIssueService;
	}
	
	//Stock issue from RHO to PICKUP BOY(EMPLOYEE)
	/**
	 * Name 	: viewFormDetails
	 * purpose 	: to view stock issue to emp./pickup boy form
	 * Input 	:
	 * return 	: populate form with default values
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueEmployeeAction::viewFormDetails ..Start");
		
		StockIssueEmployeeTO issueEmpTo = new StockIssueEmployeeTO();
		issueEmpStartUp(request, issueEmpTo);
		saveToken(request);
		//StockIssueEmployeeForm is common for Employee and Customer
		((StockIssueEmployeeForm) form).setTo(issueEmpTo);
		
		LOGGER.debug("StockIssueEmployeeAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Issue emp start up.
	 *
	 * @param request the request
	 * @param stockIssueEmpTo the stock issue emp to
	 */
	private void issueEmpStartUp(final HttpServletRequest request, StockIssueEmployeeTO stockIssueEmpTo) {
		final UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			stockIssueEmpTo.setLoggedInUserId(userTo.getUserId());
			
			if(StringUtil.isEmptyInteger(stockIssueEmpTo.getCreatedByUserId())) {
				stockIssueEmpTo.setCreatedByUserId(userTo.getUserId());
				stockIssueEmpTo.setUpdatedByUserId(userTo.getUserId());
			}
		}
	final OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null) {
			stockIssueEmpTo.setLoggedInOfficeId(officeTo.getOfficeId());
			stockIssueEmpTo.setLoggedInOfficeCode(officeTo.getOfficeCode());
		}
		if(StringUtil.isStringEmpty(stockIssueEmpTo.getCreatedIssueDate())) {
			stockIssueEmpTo.setCreatedIssueDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		
		getCnoteDetails(request);
		getLoggedInOfficeEmployeeDetails(request);
		stockIssueEmpTo.setIssuedToType(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
		stockIssueEmpTo.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
		//stockIssueEmpTo.setNoSeries(StockCommonConstants.NO_SERIES);
		setGlobalDetails(request,(StockHeaderTO)stockIssueEmpTo);
		setConfigParamsForStock(request, (StockHeaderTO)stockIssueEmpTo);
	}
	
	/**
	 * Save issue employee dtls.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveIssueEmployeeDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueEmployeeAction::saveIssueEmployeeDtls ..Start");
		
		stockIssueService = getIssueEmpServiceForStock();
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		StockIssueEmployeeForm issueForm = (StockIssueEmployeeForm)form;
		StockIssueEmployeeTO stockIssueEmpTo = (StockIssueEmployeeTO)issueForm.getTo();
		String action=null;
		try {
			//For the issue customer/employee, we can use same method for save details

			if(!isTokenValid(request)){
				actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			}else{
				if(StringUtil.isEmptyLong(stockIssueEmpTo.getStockIssueId())){
					//save operation
					action=StockCommonConstants.SAVED;
				}else{
					//update Operation
					action=StockCommonConstants.UPDATED;
				}
				result = stockIssueService.saveIssueEmployeeDtls(stockIssueEmpTo);
				if(result){			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_ISSUE_NUM,stockIssueEmpTo.getStockIssueNumber(),action);
					
				}else {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_ISSUE);
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueEmployeeAction::saveIssueEmployeeDtls ..Exception", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("StockIssueEmployeeAction::saveIssueEmployeeDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
		} catch (Exception e) {
			LOGGER.error("StockIssueEmployeeAction::saveIssueEmployeeDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
		} finally {
			resetToken(request);
			prepareActionMessage(request, actionMessage);
			stockIssueEmpTo = new StockIssueEmployeeTO();
			issueEmpStartUp(request, stockIssueEmpTo);
			setUrl(request,"./stockIssueEmployee.do?submitName=viewFormDetails");
		}
		((StockIssueEmployeeForm) form).setTo(stockIssueEmpTo);
		LOGGER.debug("StockIssueEmployeeAction::saveIssueEmployeeDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	
	
	/**
	 * Find issue employee dtls.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findIssueEmployeeDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueEmployeeAction::findIssueEmployeeDtls ..START");
		
		stockIssueService = getIssueEmpServiceForStock();
		StockIssueEmployeeForm issueForm = (StockIssueEmployeeForm)form;
		StockIssueEmployeeTO issueEmpInputTo = (StockIssueEmployeeTO)issueForm.getTo();
		ActionMessage actionMessage = null;
		try {
			//For the issue customer/employee, we can use same method for save details
			issueEmpInputTo = stockIssueService.findIssueEmployeeDtls(issueEmpInputTo);
			final boolean errorStatus1 = ExceptionUtil.checkError(issueEmpInputTo);
			 if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(issueEmpInputTo, request);
				saveActionMessage(request);
			 }
			saveToken(request);
		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueEmployeeAction::findIssueEmployeeDtls ..Exception", e);
			getBusinessError(request, e);
			issueEmpInputTo = new StockIssueEmployeeTO();
			setUrl(request,"./stockIssueEmployee.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			LOGGER.error("StockIssueEmployeeAction::findIssueEmployeeDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			issueEmpInputTo = new StockIssueEmployeeTO();
			setUrl(request,"./stockIssueEmployee.do?submitName=viewFormDetails");
		} catch (Exception e) {
			LOGGER.error("StockIssueEmployeeAction::findIssueEmployeeDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			issueEmpInputTo = new StockIssueEmployeeTO();
			setUrl(request,"./stockIssueEmployee.do?submitName=viewFormDetails");
		} finally {
			prepareActionMessage(request, actionMessage);
			issueEmpStartUp(request, issueEmpInputTo);
		}
		((StockIssueEmployeeForm) form).setTo(issueEmpInputTo);
		LOGGER.debug("StockIssueEmployeeAction::findIssueCustomerDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
}
