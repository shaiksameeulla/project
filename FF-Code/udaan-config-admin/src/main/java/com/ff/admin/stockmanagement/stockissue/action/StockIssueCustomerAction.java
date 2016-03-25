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
 * The Class StockIssueCustomerAction.
 *
 * @author hkansagr
 */

public class StockIssueCustomerAction extends AbstractStockAction
{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockIssueCustomerAction.class);
	
	/** The stock issue service. */
	public transient StockIssueService stockIssueService;
	
	/**
	 * Gets the issue cust service for stock.
	 *
	 * @return the issue cust service for stock
	 */
	private StockIssueService getIssueCustServiceForStock()
	{
		if(StringUtil.isNull(stockIssueService)) {
			stockIssueService = (StockIssueService)getBean(AdminSpringConstants.STOCK_ISSUE_SERVICE);
		}
		return stockIssueService;
	}
	
	//Stock issue from RHO to CUSTOMER
	/**
	 * Name 	: viewFormDetails
	 * purpose 	: to view stock issue to customer form
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
		LOGGER.debug("StockIssueCustomerAction::viewFormDetails ..Start");
		
		StockIssueEmployeeTO issueCustomerTo = new StockIssueEmployeeTO();
		issueCustStartUp(request, issueCustomerTo);
		saveToken(request);
		//StockIssueEmployeeForm is common for Employee and Customer
		((StockIssueEmployeeForm) form).setTo(issueCustomerTo);
		
		LOGGER.debug("StockIssueCustomerAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Issue cust start up.
	 *
	 * @param request the request
	 * @param to the to
	 */
	private void issueCustStartUp(final HttpServletRequest request, StockIssueEmployeeTO to) 
	{
		final UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			to.setLoggedInUserId(userTo.getUserId());
			
			if(StringUtil.isEmptyInteger(to.getCreatedByUserId())) {
				to.setCreatedByUserId(userTo.getUserId());
				to.setUpdatedByUserId(userTo.getUserId());
			}
		}
		final OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null) {
			to.setLoggedInOfficeId(officeTo.getOfficeId());
			to.setLoggedInOfficeCode(officeTo.getOfficeCode());
		}
		if(StringUtil.isStringEmpty(to.getCreatedIssueDate())) {
			to.setCreatedIssueDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		
		getCnoteDetails(request);
		//getLoggedInOfficeCustomerDetails(request);
		getLoggedInOfficeCustomerToList(request);
		//to.setNoSeries(StockCommonConstants.NO_SERIES);
		setGlobalDetails(request,(StockHeaderTO)to);
		to.setIssuedToType(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
		to.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
		
		setConfigParamsForStock(request, (StockHeaderTO)to);
		
	}
	
	/**
	 * Save issue customer dtls.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveIssueCustomerDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueCustomerAction::saveIssueCustomerDtls ..Start");

		stockIssueService = getIssueCustServiceForStock();
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		String action=null;
		StockIssueEmployeeForm issueForm = (StockIssueEmployeeForm)form;
		StockIssueEmployeeTO issueCustomerTo =(StockIssueEmployeeTO) issueForm.getTo();
		try {
			//For the issue customer/employee, we can use same method for save details

			if(!isTokenValid(request)){
				actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			}else{
				if(StringUtil.isEmptyLong(issueCustomerTo.getStockIssueId())){
					//save operation
					action=StockCommonConstants.SAVED;
					result = stockIssueService.saveIssueEmployeeDtls(issueCustomerTo);
				}else{
					//update Operation
					action=StockCommonConstants.UPDATED;
					result = stockIssueService.saveIssueEmployeeDtls(issueCustomerTo);
				}
				if(result){			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_ISSUE_NUM,issueCustomerTo.getStockIssueNumber(),action);
					
				}else {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_ISSUE);
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueCustomerAction::saveIssueCustomerDtls ..Exception",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("StockIssueCustomerAction::saveIssueCustomerDtls ..Exception",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
		} catch (Exception e) {
			LOGGER.error("StockIssueCustomerAction::saveIssueCustomerDtls ..Exception",e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
		} finally {
			resetToken(request);
			prepareActionMessage(request, actionMessage);
			issueCustomerTo = new StockIssueEmployeeTO();
			issueCustStartUp(request, issueCustomerTo);
			setUrl(request, "./stockIssueCustomer.do?submitName=viewFormDetails");
		}
		((StockIssueEmployeeForm) form).setTo(issueCustomerTo);
		LOGGER.debug("StockIssueCustomerAction::saveIssueCustomerDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Find issue customer dtls.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findIssueCustomerDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueCustomerAction::findIssueCustomerDtls ..START");
		
		stockIssueService = getIssueCustServiceForStock();
		StockIssueEmployeeForm issueForm = (StockIssueEmployeeForm)form;
		StockIssueEmployeeTO issueCustomerTo = (StockIssueEmployeeTO)issueForm.getTo();
		ActionMessage actionMessage = null;
		try {
			//For the issue customer/employee, we can use same method for save details
			issueCustomerTo = stockIssueService.findIssueEmployeeDtls(issueCustomerTo);
			boolean errorStatus1 = ExceptionUtil.checkError(issueCustomerTo);
			 if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(issueCustomerTo, request);
				saveActionMessage(request);
			 }
			saveToken(request);
		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueCustomerAction::findIssueCustomerDtls ..Exception", e);
			getBusinessError(request, e);
			issueCustomerTo = new StockIssueEmployeeTO();
			setUrl(request, "./stockIssueCustomer.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			LOGGER.error("StockIssueCustomerAction::findIssueCustomerDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			issueCustomerTo = new StockIssueEmployeeTO();
			setUrl(request, "./stockIssueCustomer.do?submitName=viewFormDetails");
		} catch (Exception e) {
			LOGGER.error("StockIssueCustomerAction::findIssueCustomerDtls ..Exception", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			issueCustomerTo = new StockIssueEmployeeTO();
			setUrl(request, "./stockIssueCustomer.do?submitName=viewFormDetails");
		} finally {
			prepareActionMessage(request, actionMessage);
			issueCustStartUp(request, issueCustomerTo);
		}
		((StockIssueEmployeeForm) form).setTo(issueCustomerTo);
		LOGGER.debug("StockIssueCustomerAction::findIssueCustomerDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	


}
