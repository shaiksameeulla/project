package com.ff.admin.mec.collection.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.collection.form.BulkCollectionValidationForm;
import com.ff.admin.mec.collection.service.ValidateCollectionService;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.business.CustomerTO;
import com.ff.geography.RegionTO;
import com.ff.mec.collection.BulkCollectionValidationTO;
import com.ff.mec.collection.BulkCollectionValidationWrapperTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;

public class BulkCollectionValidationAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory.getLogger(BulkCollectionValidationAction.class);
	
	/** The mecCommonService. */
	private MECCommonService mecCommonService;
	
	/** The validateCollectionService. */
	private ValidateCollectionService validateCollectionService;
	
	
	/**
	 * Prepares the initial view of the page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward viewBulkCollectionValidation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkCollectionValidationAction :: viewBulkCollectionValidation :: START");
		ActionMessage actionMessage = null;
		String url = MECCommonConstants.ACTION_FORWARD_VIEW_BULK_COLLECTION_VALIDATION;
		try {
			BulkCollectionValidationForm bulkCollectionValidationForm = (BulkCollectionValidationForm)form;
			BulkCollectionValidationTO bulkCollectionValidationTo = (BulkCollectionValidationTO)bulkCollectionValidationForm.getTo();
			
			// Check whether the logged in user is a corporate user
			if (!isCorporateUser(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.BULK_COLLECTION_VALIDATION_ACCESS_RIGHTS);
			}
			
			// Set default values for the page
			setUpDefaultValues(request, bulkCollectionValidationTo);
		}
		catch (CGBusinessException e) {
			LOGGER.error("BulkCollectionValidationAction :: viewBulkCollectionValidation :: ERROR", e);
			getBusinessError(request, e);
		}
		catch (CGSystemException e) {
			LOGGER.error("BulkCollectionValidationAction :: viewBulkCollectionValidation :: ERROR", e);
			getSystemException(request, e);
		}
		catch (Exception e) {
			LOGGER.error("BulkCollectionValidationAction :: viewBulkCollectionValidation :: ERROR", e);
			getGenericException(request, e);
		}
		finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BulkCollectionValidationAction :: viewBulkCollectionValidation :: END");
		return mapping.findForward(url);
	}
	
	@SuppressWarnings("unchecked")
	private void setUpDefaultValues(HttpServletRequest request, BulkCollectionValidationTO bulkCollectionValidationTo) 
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkCollectionValidationAction :: setUpDefaultValues :: START");
		HttpSession session = (HttpSession)request.getSession(false);
		mecCommonService = getMecCommonService();
		
		bulkCollectionValidationTo.setFromDate(DateUtil.getCurrentDateInDDMMYYYY());
		bulkCollectionValidationTo.setToDate(DateUtil.getCurrentDateInDDMMYYYY());
		
		// Populating regions
		List<RegionTO> regionTOs = (List<RegionTO>) session.getAttribute(MECCommonConstants.REGIONTOs);
		if (CGCollectionUtils.isEmpty(regionTOs)) {
			regionTOs = mecCommonService.getAllRegions();
			session.setAttribute(MECCommonConstants.REGIONTOs, regionTOs);
		}
		request.setAttribute(MECCommonConstants.REGIONTOs, regionTOs);
		bulkCollectionValidationTo.setRegionToList(regionTOs);
		LOGGER.debug("BulkCollectionValidationAction :: setUpDefaultValues :: END");
	}
	
	/**
	 * This method is called during an Ajax call. Hence, this method does not have any return type.
	 * It sends the data back to the Ajax call via out.print()
	 * */
	@SuppressWarnings("unchecked")
	public void getCustomersForSelectedRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkCollectionValidationAction :: getCustomersForSelectedRegion :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		List<CustomerTO> customerToList = null;
		Map<Integer,List<CustomerTO>> customerToMap = null;
		try {
			session = request.getSession(false);
			out = response.getWriter();
			BulkCollectionValidationForm bulkCollectionValidationForm = (BulkCollectionValidationForm)form;
			BulkCollectionValidationTO bulkCollectionValidationTo = (BulkCollectionValidationTO)bulkCollectionValidationForm.getTo();
			Integer selectedRegionId = bulkCollectionValidationTo.getRegionId();
			customerToMap = (Map<Integer,List<CustomerTO>>)session.getAttribute(MECCommonConstants.PARAM_LIABILITY_CUSTOMER);
			if (CollectionUtils.isEmpty(customerToMap) || !customerToMap.containsKey(selectedRegionId)) {
				customerToList = mecCommonService.getLiabilityCustomersForLiability(selectedRegionId);
				if (!CollectionUtils.isEmpty(customerToList)) {
					customerToMap = new HashMap<>();
					customerToMap.put(selectedRegionId, customerToList);
					session.setAttribute(MECCommonConstants.PARAM_LIABILITY_CUSTOMER, customerToMap);
				}
			}
			else {
				customerToList = customerToMap.get(selectedRegionId);
			}
			
			jsonResult = JSONSerializer.toJSON(customerToList).toString();
		}
		catch (CGBusinessException e) {
			LOGGER.error("BulkCollectionValidationAction :: getCustomersForSelectedRegion :: ERROR", e);
			getBusinessError(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		}
		catch (CGSystemException e) {
			LOGGER.error("BulkCollectionValidationAction :: getCustomersForSelectedRegion :: ERROR", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		catch (Exception e) {
			LOGGER.error("BulkCollectionValidationAction :: getCustomersForSelectedRegion :: ERROR", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkCollectionValidationAction :: getCustomersForSelectedRegion :: END");
	}
	
	
	/**
	 * Method called by ajax call. It searches collection depending upon the selected parameters
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void searchCollectionDetailsForBulkValidation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkCollectionValidationAction :: searchCollectionDetailsForBulkValidation :: START");
		PrintWriter out = null;
		String jsonResult = "";
		try {
			out = response.getWriter();
			BulkCollectionValidationForm bulkCollectionValidationForm = (BulkCollectionValidationForm)form;
			BulkCollectionValidationTO bulkCollectionValidationTo = (BulkCollectionValidationTO)bulkCollectionValidationForm.getTo();
			Date fromDate = null;
			Date toDate = null;
			Integer customerId = null;
			
			if (!StringUtil.isStringEmpty(request.getParameter(MECCommonConstants.PARAM_FROM_DATE))) {
				fromDate = DateUtil.getDateFromString(request.getParameter(MECCommonConstants.PARAM_FROM_DATE), FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			}
			
			if (!StringUtil.isStringEmpty(request.getParameter(MECCommonConstants.PARAM_TO_DATE))) {
				toDate = DateUtil.getDateFromString(request.getParameter(MECCommonConstants.PARAM_TO_DATE), FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			}
			
			if (!StringUtil.isStringEmpty(request.getParameter(MECCommonConstants.PARAM_CUST_ID))) {
				customerId = Integer.parseInt(request.getParameter(MECCommonConstants.PARAM_CUST_ID));
			}
			
			setPaginationParameters(bulkCollectionValidationTo, request, customerId, fromDate, toDate);
			
			List<BulkCollectionValidationWrapperTO>  bulkCollectionValidationWrapperToList  = 
					mecCommonService.searchCollectionDetailsForBulkValidation(customerId, fromDate, toDate, 
							bulkCollectionValidationTo.getFirstResult());
			
			bulkCollectionValidationTo.setBulkCollectionDetails(bulkCollectionValidationWrapperToList);
			jsonResult = JSONSerializer.toJSON(bulkCollectionValidationTo).toString();
		}
		catch (CGBusinessException e) {
			LOGGER.error("BulkCollectionValidationAction :: searchCollectionDetailsForBulkValidation :: ERROR", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		}
		catch (CGSystemException e) {
			LOGGER.error("BulkCollectionValidationAction :: searchCollectionDetailsForBulkValidation :: ERROR", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
		catch (Exception e) {
			LOGGER.error("BulkCollectionValidationAction :: searchCollectionDetailsForBulkValidation :: ERROR", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkCollectionValidationAction :: searchCollectionDetailsForBulkValidation :: END");
	}
	
	
	private void setPaginationParameters(BulkCollectionValidationTO bulkCollectionValidationTo, HttpServletRequest request, 
			Integer customerId, Date fromDate, Date toDate) throws CGBusinessException, CGSystemException  {
		String navigationLabel = null;
		Integer requestedPageNumber = null;
		Double numberOfPages = null;
		Integer totalNumberOfRecords = 
				mecCommonService.getTotalNumberOfRecordsForBulkValidation(customerId, fromDate, toDate);
		numberOfPages = Math.ceil(totalNumberOfRecords / (MECCommonConstants.NO_OF_RECORDS_PER_PAGE * 1.0));
		bulkCollectionValidationTo.setNumberOfPages(numberOfPages.intValue());
		bulkCollectionValidationTo.setNumberOfRecordsPerPage(MECCommonConstants.NO_OF_RECORDS_PER_PAGE);
		
		if (!StringUtil.isStringEmpty(request.getParameter(MECCommonConstants.PARAM_NAVIGATION_LABEL))) {
			navigationLabel = request.getParameter(MECCommonConstants.PARAM_NAVIGATION_LABEL);
		}
		
		if (!StringUtil.isStringEmpty(request.getParameter(MECCommonConstants.PARAM_PAGE_NUMBER))) {
			requestedPageNumber = Integer.parseInt(request.getParameter(MECCommonConstants.PARAM_PAGE_NUMBER));
		}
		
		if (MECCommonConstants.SUBMITTED_STATUS.equals(navigationLabel)) {
			bulkCollectionValidationTo.setFirstResult(0);
			bulkCollectionValidationTo.setCurrentPageNumber(1);
		}
		else {
			Integer lastResult = requestedPageNumber * MECCommonConstants.NO_OF_RECORDS_PER_PAGE;
			Integer firstResult = lastResult - MECCommonConstants.NO_OF_RECORDS_PER_PAGE;
			bulkCollectionValidationTo.setFirstResult(firstResult);
			bulkCollectionValidationTo.setCurrentPageNumber(requestedPageNumber);
		}
		bulkCollectionValidationTo.setNavigationLabel(navigationLabel);
	}
	
	/**
	 * Validates selected transactions and updates the records accordingly in the database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException
	 */
	public void validateSelectedTransactions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException, IOException {
		LOGGER.debug("BulkCollectionValidationAction :: validateSelectedTransactions :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		BulkCollectionValidationTO bulkCollectionValidationTo = null;
		try {
			BulkCollectionValidationForm bulkCollectionValidationForm = (BulkCollectionValidationForm)form;
			bulkCollectionValidationTo = (BulkCollectionValidationTO)bulkCollectionValidationForm.getTo();
			String[] transactionNumberArray = bulkCollectionValidationTo.getTransactionNumbers();
			List<String> transactionNumberList = null;
			
			if (transactionNumberArray != null && transactionNumberArray.length > 0) {
				transactionNumberList = getTransactionNumberListFromArray(transactionNumberArray);
				Integer updatedBy = getLoggedInUserId(request);
				validateCollectionService = getValidateCollectionService();
				validateCollectionService.validateTxns(transactionNumberList, updatedBy);
			}
			
			/*String transMsg = getMessageFromErrorBundle(request,
					MECCommonConstants.TXNS_VALIDATE_SUCCESSFULLY, null);
			to.setTransMsg(transMsg);*/
			
			jsonResult = JSONSerializer.toJSON(bulkCollectionValidationTo).toString();
		} 
		catch (CGBusinessException e) {
			LOGGER.error("BulkCollectionValidationAction :: validateSelectedTransactions :: ERROR", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} 
		catch (CGSystemException e) {
			LOGGER.error("BulkCollectionValidationAction :: validateSelectedTransactions :: ERROR", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
		catch (Exception e) {
			LOGGER.error("BulkCollectionValidationAction :: validateSelectedTransactions :: ERROR", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} 
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkCollectionValidationAction :: validateSelectedTransactions :: END");
	}
	
	
	private List<String> getTransactionNumberListFromArray(String[] transactionNumberArray) {
		List<String> transactionNumberList = new ArrayList<>();
		for (int i = 0; i < transactionNumberArray.length; i++) {
			if (!StringUtil.isStringEmpty(transactionNumberArray[i])) {
				transactionNumberList.add(transactionNumberArray[i]);
			}
		}
		return transactionNumberList;
	}
	
	
	/**
	 * To get Logged in user id.
	 * 
	 * @param request
	 * @return userId
	 */
	private Integer getLoggedInUserId(HttpServletRequest request) {
		LOGGER.debug("BulkCollectionValidationAction :: getLoggedInUserId :: START");
		Integer userId = null;
		HttpSession session = request.getSession(false);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			userId = userTO.getUserId();
		}
		LOGGER.debug("BulkCollectionValidationAction :: getLoggedInUserId :: END");
		return userId;
	}

	
	/**
	 * To check whether Logged In Office is branch/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isCorporateUser(HttpServletRequest request) {
		LOGGER.debug("BulkCollectionValidationAction :: isBranchOrHubOffice :: START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO != null
				&& loggedInOfficeTO.getOfficeTypeTO() != null
				&& (loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE))) {
			result = true;
		}
		LOGGER.debug("BulkCollectionValidationAction :: isBranchOrHubOffice :: END");
		return result;
	}
	
	
	private MECCommonService getMecCommonService() {
		if (StringUtil.isNull(mecCommonService)) {
			mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
		}
		return mecCommonService;
	}
	
	
	private ValidateCollectionService getValidateCollectionService() {
		if (StringUtil.isNull(validateCollectionService)) {
			validateCollectionService = (ValidateCollectionService) getBean(AdminSpringConstants.VALIDATE_COLLECTION_SERVICE);
		}
		return validateCollectionService;
	}
}
