package com.ff.admin.stockmanagement.stockissue.action;

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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.action.AbstractStockAction;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockissue.form.StockIssueForm;
import com.ff.admin.stockmanagement.stockissue.service.StockIssueService;
import com.ff.business.CustomerTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.StockUserTO;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.umc.UserTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockIssueAction.
 *
 * @author hkansagr
 */

public class StockIssueAction extends AbstractStockAction{
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockIssueAction.class);
	
	/** The stock issue service. */
	public transient StockIssueService stockIssueService;
	
	
	/**
	 * Gets the issue service for stock.
	 *
	 * @return the issue service for stock
	 */
	private StockIssueService getIssueServiceForStock()
	{
		if(StringUtil.isNull(stockIssueService)) {
			stockIssueService = (StockIssueService)getBean(AdminSpringConstants.STOCK_ISSUE_SERVICE);
		}
		return stockIssueService;
	}
	



	/**
	 * Name : viewFormDetails
	 * purpose : to view stock issue form
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
		LOGGER.debug("StockIssueAction::viewFormDetails ..Start");
		StockIssueTO issueTo = new StockIssueTO();
		issueStartUp(request, issueTo);
		saveToken(request);
		((StockIssueForm) form).setTo(issueTo);
		LOGGER.debug("StockIssueAction::viewFormDetails ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Issue start up.
	 *
	 * @param request the request
	 * @param issueTO the issue to
	 */
	private void issueStartUp(HttpServletRequest request, StockIssueTO issueTO) {

		final UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			issueTO.setLoggedInUserId(userTo.getUserId());
			
			if(StringUtil.isEmptyInteger(issueTO.getCreatedByUserId())) {
				issueTO.setCreatedByUserId(userTo.getUserId());
				issueTO.setUpdatedByUserId(userTo.getUserId());
			}
		}
		
		final OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null) {
			issueTO.setLoggedInOfficeId(officeTo.getOfficeId());
			issueTO.setLoggedInOfficeName(officeTo.getOfficeCode()+FrameworkConstants.CHARACTER_HYPHEN+officeTo.getOfficeName());
			
			issueTO.setLoggedInOfficeCode(officeTo.getOfficeCode());
			issueTO.setIssueOfficeId(officeTo.getOfficeId());
		}
		if(StringUtil.isStringEmpty(issueTO.getIssueDateStr())&& StringUtil.isEmptyLong(issueTO.getStockIssueId())){
			issueTO.setIssueDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			//to.setIssueTimeStr(DateUtil.getCurrentTime());
		}
		
		if(!StringUtil.isEmptyLong(issueTO.getStockIssueId()) || !StringUtil.isStringEmpty(issueTO.getTransactionFromType())) {
			setItemDetails(request);
		}
		setStockIssueStdTypeDetails(request);

		if(issueTO.getPaymentTO()!=null && StringUtil.isStringEmpty(issueTO.getPaymentTO().getPaymentDateStr()) && StringUtil.isEmptyLong(issueTO.getPaymentTO().getStockPaymentId())){
				issueTO.getPaymentTO().setPaymentDateStr(DateUtil.todayDate());
		}
		
		setGlobalDetails(request,(StockHeaderTO)issueTO);
		if(!CGCollectionUtils.isEmpty(issueTO.getPartyTypeDtls())){
			request.setAttribute(StockCommonConstants.REQ_PARAM_ISSUE_PARTY_TYPE_DTLS, issueTO.getPartyTypeDtls());

		}
		issueTO.setPaymentCash(StockUniveralConstants.STOCK_PAYMENT_CASH);
		issueTO.setPaymentChq(StockUniveralConstants.STOCK_PAYMENT_CHQ);
		issueTO.setPaymentDd(StockUniveralConstants.STOCK_PAYMENT_DD);
		setUserDtlsDetails(request, (StockHeaderTO)issueTO);
		
		setConfigParamsForStock(request, (StockHeaderTO)issueTO);
		populateTaxComponentsByLoggedInOffice(request, issueTO.getPaymentTO());
	}

	
	
	/**
	 * Name : saveIssueDtls
	 * purpose : to save stock issue details in the database by the RHO
	 * Input : StockIssueForm with StockIssueTo
	 * return : populate confirmation message to the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveIssueDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueAction::saveIssueDtls ..Start");
		
		stockIssueService = getIssueServiceForStock();
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		StockIssueForm issueForm = (StockIssueForm)form;
		StockIssueTO stockIssueInputTo = (StockIssueTO)issueForm.getTo();
		String action=null;
		try {
			/*if(!isTokenValid(request)){
				actionMessage =  new ActionMessage(AdminErrorConstants.DUPLICATE_FORM_SUBMISSION);
			}else{*/
				if(StringUtil.isEmptyLong(stockIssueInputTo.getStockIssueId())){
					//save operation
					action=StockCommonConstants.SAVED;
				result = stockIssueService.saveIssueDtlsForBranch(stockIssueInputTo);
				}else{
					//update Operation
					action=StockCommonConstants.UPDATED;
					result = stockIssueService.updateIssueDtlsForBranch(stockIssueInputTo);
				}
				if(result){			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_SAVED_SUCCESS,StockCommonConstants.STOCK_ISSUE_NUM,stockIssueInputTo.getStockIssueNumber(),action);
					
				}else {			
					actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED,StockCommonConstants.STOCK_ISSUE);
				}
			//}
		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueAction::saveIssueDtls ..Exception::", e);
			if(e.getBusinessMessage()!=null){
				getBusinessError(request,e);
			}else{
				actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			}
		} catch (CGSystemException e) {
			LOGGER.error("StockIssueAction::saveIssueDtls ..Exception::", e);
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
		} catch (Exception e) {
			LOGGER.error("StockIssueAction::saveIssueDtls ..Exception::", e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_SAVED_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			getGenericException(request, e);
		} finally {
			resetToken(request);
			prepareActionMessage(request, actionMessage);
			stockIssueInputTo = new StockIssueTO();
			issueStartUp(request, stockIssueInputTo);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		}
		((StockIssueForm) form).setTo(stockIssueInputTo);
		LOGGER.debug("StockIssueAction::saveIssueDtls ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * Name : findDetailsByRequisitionNumber
	 * Pre-Condition : logged in office should be equal to stock Requisition 's RHO(Supplying Field in DB)
	 * Purpose : to find requisition details from the database(By Requisition Number)
	 * Input : StockIssueForm with StockIssueTo
	 * Return : populate stock issue details in the screen for modify/update/edit.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByRequisitionNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueAction::findDetailsByRequisitionNumber ..Start");
		stockIssueService = getIssueServiceForStock();
		StockIssueForm issueForm = (StockIssueForm)form;
		StockIssueTO sockIssueInputTo = (StockIssueTO)issueForm.getTo();
		ActionMessage actionMessage = null;
		 try {
			 sockIssueInputTo = stockIssueService.findDetailsByRequisitionNumber(sockIssueInputTo);
			 //check any warnings/Business Exceptions
			final boolean errorStatus1 = ExceptionUtil.checkError(sockIssueInputTo);
			 if(errorStatus1) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(sockIssueInputTo, request);
				saveActionMessage(request);
			 }
			 saveToken(request);
		} catch (CGBusinessException e) {
			sockIssueInputTo = new StockIssueTO();
			LOGGER.error("StockIssueAction::findDetailsByRequisitionNumber ..Exception", e);
			getBusinessError(request,e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			sockIssueInputTo = new StockIssueTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockIssueAction::findDetailsByRequisitionNumber ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		}catch (Exception e) {
			sockIssueInputTo = new StockIssueTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockIssueAction::findDetailsByRequisitionNumber ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		} finally {
			prepareActionMessage(request, actionMessage);
			issueStartUp(request, sockIssueInputTo);
		}
		((StockIssueForm) form).setTo(sockIssueInputTo);
		LOGGER.debug("StockIssueAction::findDetailsByRequisitionNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	
	/**
	 * Name : findDetailsByIssueNumber
	 * purpose : to find Issue dtls from the db (ByIssueNumber)
	 * Pre-Condition : logged in office should be equal to stock issue created office.
	 * Input : StockIssueForm with StockIssueTo
	 * return : populate stock Issue details in the screen for Modify/update/edit
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDetailsByIssueNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueAction::findRequisitionDtlsByReqNumber ..Start");
		stockIssueService = getIssueServiceForStock();
		StockIssueForm reqForm=(StockIssueForm)form;
		StockIssueTO stockIssueInputTo =(StockIssueTO) reqForm.getTo();
		ActionMessage actionMessage=null;
		 try {
			 stockIssueInputTo = stockIssueService.findDetailsByIssueNumber(stockIssueInputTo);
			 //check any warnings/Business Exceptions
			 boolean errorStatus1 = ExceptionUtil.checkError(stockIssueInputTo);
				if(errorStatus1) {
					//if so extract them and propagate to screen
					ExceptionUtil.prepareActionMessage(stockIssueInputTo, request);
					saveActionMessage(request);
				}
				saveToken(request);
		} catch (CGBusinessException e) {
			stockIssueInputTo=new StockIssueTO();
			LOGGER.error("StockIssueAction::findRequisitionDtlsByReqNumber ..Exception", e);
			getBusinessError(request,e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			stockIssueInputTo=new StockIssueTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			LOGGER.error("StockIssueAction::findRequisitionDtlsByReqNumber ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		}catch (Exception e) {
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE);
			stockIssueInputTo=new StockIssueTO();
			LOGGER.error("StockIssueAction::findRequisitionDtlsByReqNumber ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		}finally{
			prepareActionMessage(request, actionMessage);
			issueStartUp(request, stockIssueInputTo);
		}
		 ((StockIssueForm) form).setTo(stockIssueInputTo);
		LOGGER.debug("StockIssueAction::findRequisitionDtlsByReqNumber ..END");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Ajax get receiving type by code.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@Deprecated
	public void ajaxGetReceivingTypeByCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		StockUserTO stockUserTo=null;
		String result="";
		StockValidationTO validationTo=null;
		OfficeTO officeTo=null;
		stockIssueService = getIssueServiceForStock();
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String code = request.getParameter(StockCommonConstants.CODE);
			String receivingType = request.getParameter(StockCommonConstants.RECEIVING_TYPE);
			officeTo = getLoginOfficeTO(request);
			if(!StringUtil.isStringEmpty(code)&& !StringUtil.isStringEmpty(receivingType)){
				validationTo=new StockValidationTO();
				validationTo.setReceipientCode(code);
				validationTo.setLoggedInOfficeId(officeTo.getOfficeId());
				validationTo.setIssuedTOType(receivingType);
				stockUserTo = stockIssueService.getReceipientDetails(validationTo);
			}
			if(!StringUtil.isNull(stockUserTo)){
				result = serializer.toJSON(stockUserTo).toString();
				}
			
		}  catch (CGBusinessException e) {
			LOGGER.error("StockIssueAction::ajaxGetReceivingTypeByCode::CGBusinessException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("StockIssueAction::ajaxGetReceivingTypeByCode::CGSystemException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("StockIssueAction::ajaxGetReceivingTypeByCode::Exception=======>",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);	
		}
		
		finally {
			
			out.print(result);
			out.flush();
			out.close();
		}

	}
	
	/**
	 * Checks if is valid series for issue.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void isValidSeriesForIssue(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response){
		LOGGER.debug("StockIssueAction::isValidSeriesForIssue::Start=======>");
		StockValidationTO validationTo=null; 
		String stSIno=request.getParameter(StockCommonConstants.REQ_PARAM_START_SERIAL_NUMBER);
		String qnty=request.getParameter(StockCommonConstants.REQ_PARAM_QUANTITY);
		String partyTypeId=request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_PARTY_TYPE_ID);
		String issueType=request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_TO_PARTY_TYPE);
		String itemId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_ID);
		String itemDetailsId=request.getParameter(StockCommonConstants.REQ_PARAM_ITEM_DETAILS_ID);
		String seriesType=request.getParameter(StockCommonConstants.SERIES_TYPE);
		String receipientCode=request.getParameter(StockCommonConstants.STOCK_ISSUE_RECEIPIENT);
		String cityCode=request.getParameter(StockCommonConstants.STOCK_CITY_CODE);
		String screenName=request.getParameter("screenName");
		Integer loggedInofficeId=getLoginOfficeTO(request).getOfficeId();
		
		
		String result="";
		
		
		PrintWriter out=null;
		
		try {
			validationTo=new StockValidationTO();
			out=response.getWriter();
			stockCommonService = getCommonServiceForStock();
			validationTo.setStartSerialNumber(stSIno);
			validationTo.setQuantity(StringUtil.convertStringToInteger(qnty));
			validationTo.setItemId(StringUtil.convertStringToInteger(itemId));
			validationTo.setItemDetailsId(StringUtil.convertStringToLong(itemDetailsId));
			validationTo.setIssuedTOType(issueType);
			validationTo.setPartyTypeId(StringUtil.convertStringToInteger(partyTypeId));
			validationTo.setLoggedInOfficeId(loggedInofficeId);
			validationTo.setSeriesType(seriesType);
			/**
			 * since stock cancellation/stock issue uses same service hence we are modifying here
			 * Artifact artf3400495 : Booked cment is not able to cancel
			 * here we are passing screenName as reqparam from the stock cancellation screen hence we should exclude stock consumption code from stock cancellation
			 */
			validationTo.setScreenName(screenName);
			setGlobalDetailsForValidations(request,validationTo);
			if(!StringUtil.isStringEmpty(issueType)&& issueType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BRANCH)){
				validationTo.setOfficeCode(receipientCode);
				validationTo.setCityCode(cityCode);
			}
			long starttime=System.currentTimeMillis();
			LOGGER.debug("StockIssueAction::isValidSeriesForIssue ..Before Calling method: isSeriesValidForStockIssue:[ "+starttime+"]");
			result= stockCommonService.isSeriesValidForStockIssue(validationTo);
			long endtime=System.currentTimeMillis();
			LOGGER.debug("StockIssueAction::isValidSeriesForIssue ..After calling Method: isSeriesValidForStockIssue Time [ "+endtime +"]Total diff :["+(endtime-starttime)+"]");
			
		} catch (CGBusinessException e) {
			LOGGER.error("StockIssueAction::isValidSeriesForIssue::CGBusinessException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("StockIssueAction::isValidSeriesForIssue::CGSystemException=======>",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("StockIssueAction::isValidSeriesForIssue::Exception=======>",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(StockUniveralConstants.RESP_ERROR,exception);	
		}
		out.print(result);
		out.flush();
	}
	/**
	 * ajaxGetCustomerDetailsById
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void ajaxGetBaFrDetailsById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		CustomerTO stockUserTo=null;
		String result="";
		Integer customerId=null;
		String partyType=request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_TO_PARTY_TYPE);
		String partytypeId = request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_PARTY_TYPE_ID);
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			
			
			customerId=StringUtil.convertStringToInteger(partytypeId);
			if(!StringUtil.isEmptyInteger(customerId)){
				stockUserTo=getCustomerTOByIdFromSession(request, customerId,StockCommonConstants.REQ_PARAM_CUSTOMER_TO_LIST+partyType);
			}
			if(!StringUtil.isNull(stockUserTo)){
				result = serializer.toJSON(stockUserTo).toString();
				}
			
		} catch (Exception e) {
			LOGGER.error("StockIssueAction:: ajaxGetCustomerDetailsById", e);
			result=getGenericExceptionMessage(request, e);
		}
		
		finally {
			
			out.print(result);
			out.flush();
			out.close();
		}
		

	}
	public ActionForward printStockIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("StockIssueAction::printStockIssue ..Start");
		stockIssueService = getIssueServiceForStock();
		ActionMessage actionMessage = null;
		 StockIssueTO stockIssueInputTo= null;
		 try {
			  stockIssueInputTo= new StockIssueTO();
				issueStartUp(request, stockIssueInputTo);
			 String issueNumber=request.getParameter(StockCommonConstants.QRY_PARAM_ISSUE_NUMBER);
			 stockIssueInputTo.setStockIssueNumber(issueNumber);
			 stockIssueInputTo = stockIssueService.findDetailsByIssueNumber(stockIssueInputTo);
			
		} catch (CGBusinessException e) {
			stockIssueInputTo = new StockIssueTO();
			LOGGER.error("StockIssueAction::printStockIssue ..Exception", e);
			getBusinessError(request,e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		} catch (CGSystemException e) {
			stockIssueInputTo = new StockIssueTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockIssueAction::printStockIssue ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		}catch (Exception e) {
			stockIssueInputTo = new StockIssueTO();
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_REQUISITION);
			LOGGER.error("StockIssueAction::printStockIssue ..Exception", e);
			setUrl(request, "./stockIssue.do?submitName=viewFormDetails");
		} finally {
			prepareActionMessage(request, actionMessage);
			issueStartUp(request, stockIssueInputTo);
		}
		((StockIssueForm) form).setTo(stockIssueInputTo);
		LOGGER.debug("StockIssueAction::printStockIssue ..END");
		return mapping.findForward(StockCommonConstants.URL);
	}
	
	
	
}
