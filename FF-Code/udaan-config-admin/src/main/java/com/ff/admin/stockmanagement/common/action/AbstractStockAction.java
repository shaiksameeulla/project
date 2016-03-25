/**
 * 
 */
package com.ff.admin.stockmanagement.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
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
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.stockissue.StockIssuePaymentDetailsTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.util.UniversalConverterUtil;

/**
 * The Class AbstractStockAction.
 *
 * @author mohammes
 * It's a base class for all Stockmangement action class
 * Usage : it has all reusable methods for entire module
 * Such as getting user/office/employee/config-params etc from the session
 */
public class AbstractStockAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractStockAction.class);

	/** The stock common service. */
	public transient StockCommonService stockCommonService;
	/** The serializer. */
	public transient JSONSerializer serializer;

	public  transient Map<String, String> configurableParams=null;

	/**
	 * Gets the stock common service.
	 *
	 * @return the stockCommonService
	 */
	public StockCommonService getStockCommonService() {
		return stockCommonService;
	}

	/**
	 * Sets the stock common service.
	 *
	 * @param stockCommonService the stockCommonService to set
	 */
	public void setStockCommonService(StockCommonService stockCommonService) {
		this.stockCommonService = stockCommonService;
	}

	/**
	 * Name : ajaxGetItemByTypeMap
	 * purpose : ajax call to get all item(s)/Material  by its itemType
	 * Input : request param in the name TYPE_ID
	 * return : return map<Integer,String> as String.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetItemByTypeMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out=null;
		Integer itemTypeId=null;
		Map<Integer,String> itemMap=null;
		stockCommonService = getCommonServiceForStock();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String itemType = request.getParameter(StockCommonConstants.TYPE_ID);
			if(StringUtil.isInteger(itemType)){
				itemTypeId = StringUtil.parseInteger(itemType);
				itemMap = stockCommonService.getItemByTypeAsMap(itemTypeId);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemByTypeMap", e);
		}catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemByTypeMap", e);
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemByTypeMap", e);
		}

		finally {
			out.print(CollectionUtils.isEmpty(itemMap)?null:itemMap.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * Gets the common service for stock.
	 *
	 * @return the common service for stock
	 */
	public StockCommonService getCommonServiceForStock() {

		if(StringUtil.isNull(stockCommonService)){
			stockCommonService = (StockCommonService)getBean(AdminSpringConstants.STOCK_COMMON_SERVICE);
		}
		return stockCommonService;
	}

	/**
	 * Name : ajaxGetItemTypeMap
	 * purpose : ajax call to get all itemType(s)/MaterialType
	 * Input : no input required
	 * return : return map<Integer,String> as String.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetItemTypeMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out=null;
		Map<Integer,String> itemTypeMap=null;
		HttpSession session=request.getSession(false);		
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			itemTypeMap = (Map<Integer,String>)session.getAttribute(StockCommonConstants.ITEM_TYPE_REQ_PARAMS);

			if(itemTypeMap==null || itemTypeMap.isEmpty()) {
				itemTypeMap = getItemTypeDtls();
				session.setAttribute(StockCommonConstants.ITEM_TYPE_REQ_PARAMS, itemTypeMap);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemTypeMap", e);
		}catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemTypeMap", e);
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemTypeMap", e);
		}

		finally {
			out.print(CollectionUtils.isEmpty(itemTypeMap)?null:itemTypeMap.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * Gets the item type dtls.
	 *
	 * @return the item type dtls
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public Map<Integer, String> getItemTypeDtls() throws CGSystemException,
	CGBusinessException {
		Map<Integer, String> itemTypeMap;
		stockCommonService = getCommonServiceForStock();
		itemTypeMap = stockCommonService.getItemTypeAsMap();
		return itemTypeMap;
	}

	/**
	 * Gets the item dtls.
	 *
	 * @return the item dtls
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public Map<Integer, String> getItemDtls() throws CGSystemException,
	CGBusinessException {
		Map<Integer, String> itemTypeMap;
		stockCommonService = getCommonServiceForStock();
		itemTypeMap = stockCommonService.getItemDtlsAsMap();
		return itemTypeMap;
	}



	/**
	 * Name : getLoginUserTO
	 * purpose : to get UserTO from Session
	 * Input : HttpServletRequest request
	 * return : UserTO.
	 *
	 * @param request the request
	 * @return the login user to
	 */
	public UserTO getLoginUserTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			return userInfo.getUserto();
		}
		return null;
	}
	/**
	 * isSessionAlive :: check whether session is exist or not
	 * @param request
	 * @return boolean
	 */

	public boolean isSessionAlive(HttpServletRequest request){
		UserTO userInfo = getLoginUserTO(request);
		if(userInfo!=null){
			return true;
		}
		return false;
	}




	/**
	 * Name : getLoginUserInfoTO
	 * purpose : to get UserInfoTO from Session
	 * Input : HttpServletRequest request
	 * return : UserInfoTO.
	 *
	 * @param request the request
	 * @return the login user info to
	 */
	public UserInfoTO getLoginUserInfoTO(HttpServletRequest request) {
		HttpSession session =request.getSession(Boolean.FALSE);
		UserInfoTO userInfo =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		return userInfo;
	}

	/**
	 * Name : getLoginEmpTO
	 * purpose : to get EmployeeTO from Session
	 * Input : HttpServletRequest request
	 * return : EmployeeTO.
	 *
	 * @param request the request
	 * @return the login emp to
	 */
	public EmployeeTO getLoginEmpTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			EmployeeUserTO empUserTo = userInfo.getEmpUserTo();
			if(empUserTo !=null){
				return empUserTo.getEmpTO();
			}
		}
		return null;
	}

	/**
	 * Name : getLoginOfficeTO
	 * purpose : to get OfficeTO from Session
	 * Input : HttpServletRequest request
	 * return : OfficeTO.
	 *
	 * @param request the request
	 * @return the login office to
	 */
	public OfficeTO getLoginOfficeTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			return  userInfo.getOfficeTo();
		}
		return null;
	}
	
	public String getLoginOfficeType(HttpServletRequest request)throws CGBusinessException{
		String officeType=null;
		OfficeTO officeTo=getLoginOfficeTO(request);
		if(officeTo!=null && officeTo.getOfficeTypeTO()!=null){
			officeType=officeTo.getOfficeTypeTO().getOffcTypeCode();
		}
		if(StringUtil.isStringEmpty(officeType)){
			throw new CGBusinessException(UniversalErrorConstants.IN_VALID_LOGGED_OFFICE_OFFICE_TYPE);
		}
		return officeType;
	}
	
	public boolean isCorporateOffice(HttpServletRequest request){
		Boolean isCorporate=false;
		String officeType=null;
		 try {
			officeType =getLoginOfficeType(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("AbstractStockAction:: isCorporateOffice ::Exception", e);;
		}
		 if(!StringUtil.isStringEmpty(officeType) && officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){
			 isCorporate=true;
		 }
		 
		 return isCorporate;
	}

	/**
	 * Name : getLoginRegionTO
	 * purpose : to get RegionTO from Session
	 * Input : HttpServletRequest request
	 * return : RegionTO.
	 *
	 * @param request the request
	 * @return the login region to
	 */
	public RegionTO getLoginRegionTO(HttpServletRequest request){
		OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null){
			return  officeTo.getRegionTO();
		}
		return null;
	}

	/**
	 * Name : isEmployeeLoggedIn
	 * purpose : to verify whether logged-in code is Of employee from Session
	 * Input : HttpServletRequest request
	 * return : Boolean.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean isEmployeeLoggedIn(HttpServletRequest request){
		EmployeeTO employeeTo=getLoginEmpTO(request);
		if(!StringUtil.isNull(employeeTo)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * Name : ajaxGetItemDetialsByItemId
	 * purpose : ajax call to get all item(s)/Material  by its itemType
	 * Input : request param in the name TYPE_ID(item/material id)
	 * return : return ItemTO as String.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetItemDetialsByItemId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out=null;
		Integer itemId=null;
		ItemTO itemTo=null;
		String jsonResult=null;
		stockCommonService = getCommonServiceForStock();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String item = request.getParameter(StockCommonConstants.TYPE_ID);

			serializer = CGJasonConverter.getJsonObject();
			if(StringUtil.isInteger(item)){
				itemId = StringUtil.parseInteger(item);
				itemTo = stockCommonService.getItemByItemId(itemId);
			}
			if(!StringUtil.isNull(itemTo)){
				jsonResult = serializer.toJSON(itemTo).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemDetialsByItemId ::Exception", e);
			jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemDetialsByItemId ::Exception", e);
			jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemDetialsByItemId ::Exception", e);
			jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}

		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}

	/**
	 * Name : ajaxGetItemDetialsByItemId
	 * purpose : ajax call to get all item(s)/Material  by its itemType
	 * Input : request param in the name TYPE_ID(item/material id)
	 * return : return ItemTO as String.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetItemDetialsByItemIdAndTypeId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out=null;
		Integer itemId=null;
		Integer itemTypeId=null;
		ItemTO itemTo=null;
		String jsonResult=null;
		stockCommonService = getCommonServiceForStock();
		Integer officeId=getLoginOfficeTO(request).getOfficeId();
		Integer stockQnty=null;
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String itemType = request.getParameter(StockCommonConstants.ITEM_TYPE_ID);
			String item = request.getParameter(StockCommonConstants.ITEM_ID);
			String issue = request.getParameter(StockCommonConstants.ISSUE);
			String issuedToType = request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_TO_PARTY_TYPE);

			serializer = CGJasonConverter.getJsonObject();
			if(StringUtil.isInteger(itemType) && StringUtil.isInteger(item)){
				itemId = StringUtil.parseInteger(item);
				itemTypeId= StringUtil.parseInteger(itemType);
				itemTo = stockCommonService.getItemByItemTypeAndItemId(itemTypeId,itemId);

				stockQnty = getStockForLoggedInOffice(itemId,officeId, issue);
				itemTo.setStockQuantity(stockQnty);
				if(!StringUtil.isStringEmpty(issue)&& !StringUtil.isStringEmpty(issuedToType) ){
				prepareMaterialRateDetails(request, itemTo, issuedToType);
				}
			}
			if(!StringUtil.isNull(itemTo)){
				jsonResult = serializer.toJSON(itemTo).toString();
			}

		} catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction::ajaxGetItemDetialsByItemId ..CGSystemException :",e);
			jsonResult= prepareCommonException(StockUniveralConstants.RESP_ERROR,getSystemExceptionMessage(request,e));
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractStockAction:: ajaxGetItemDetialsByItemId", e);
			jsonResult= prepareCommonException(StockUniveralConstants.RESP_ERROR,getBusinessErrorFromWrapper(request, e));
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction::ajaxGetItemDetialsByItemId ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(StockUniveralConstants.RESP_ERROR,exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}

	/**
	 * @param request
	 * @param itemTo
	 * @param issuedToType
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void prepareMaterialRateDetails(HttpServletRequest request,
			ItemTO itemTo, String issuedToType) throws CGSystemException,
			CGBusinessException {
		if(StringUtil.isStringEmpty(issuedToType)){
			return;
		}
		ItemTO itemToDtls=stockCommonService.getItemByItemId(itemTo.getItemId());
		configurableParams = getConfigParams(request);
		String expectedSeries=null;
		if(!CGCollectionUtils.isEmpty(configurableParams)){
		expectedSeries=configurableParams.get(StockCommonConstants.STOCK_ISSUE_BA_SERIES);
		}else{
			expectedSeries=CommonConstants.PRODUCT_SERIES_BA+FrameworkConstants.CHARACTER_COMMA+CommonConstants.PRODUCT_SERIES_PRIORITY;
		}
		if(!StringUtil.isNull(itemToDtls)){
			String itemSeries=itemToDtls.getItemSeries();
			ItemTypeTO typTo= itemToDtls.getItemTypeTO();
			if(typTo!=null){
				if(!StringUtil.isStringEmpty(typTo.getItemTypeCode())&& typTo.getItemTypeCode().equalsIgnoreCase(UdaanCommonConstants.SERIES_TYPE_CNOTES)){
					if(!StringUtil.isStringEmpty(itemSeries)){
						if(!expectedSeries.contains(itemSeries)){
							throw new CGBusinessException(AdminErrorConstants.MATERIAL_NOT_ALLOWED);
						}
					}else{
						throw new CGBusinessException(AdminErrorConstants.MATERIAL_NOT_ALLOWED);
					}
				}
			}
		}
		if(!StringUtil.isStringEmpty(issuedToType)&& issuedToType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA)){
			StockRateTO rateTo= new StockRateTO();
			rateTo.setItemId(itemTo.getItemId());
			rateTo.setCityTO(getLoggedInCityDetails(request));
			rateTo.setDate(DateUtil.getCurrentDate());
			rateTo.setQuantity(1);
			stockCommonService.calculateRateForBAMaterial(rateTo);
			itemTo.setRateTO(rateTo);
		}
	}

	/**
	 * Gets the stock for logged in office.
	 *
	 * @param itemId the item id
	 * @param itemTo the item to
	 * @param officeId the office id
	 * @param issue the issue
	 * @return the stock for logged in office
	 */
	public Integer getStockForLoggedInOffice(Integer itemId,Integer officeId, String issue) {
		Integer quantity=null;
		try {
			if(!StringUtil.isStringEmpty(issue)&& !StringUtil.isEmptyInteger(itemId)){
				quantity= stockCommonService.getStockQuantityByItemAndPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH, officeId, itemId);
				//itemTo.setStockQuantity(stockQnty);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: getStockForLoggedInOffice ::Exception", e);
		}
		return quantity;
	}

	/**
	 * Name : setItemDetails
	 * purpose : in order to set ItemType/Item details in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set Item/item type details in Session/request scope.
	 *
	 * @param request the new item details
	 */
	public void setItemDetails(HttpServletRequest request) {
		Map<Integer, String> itemTypeMap;
		Map<Integer, String> itemMap;
		HttpSession session=request.getSession(false);
		itemTypeMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.ITEM_TYPE_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(itemTypeMap)) {
				itemTypeMap = getItemTypeDtls();
				session.setAttribute(StockCommonConstants.ITEM_TYPE_REQ_PARAMS, itemTypeMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: setItemDetails:: Material Type ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(itemTypeMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_ITEM_TYPE_DTLS_NOT_EXIST));
			LOGGER.warn("AbstractStockAction:: setItemDetails :: Item Type Details Does not exist");
		}
		request.setAttribute(StockCommonConstants.ITEM_TYPE_REQ_PARAMS, itemTypeMap);
		itemMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.ITEM_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(itemMap)) {
				itemMap =getItemDtls();
				session.setAttribute(StockCommonConstants.ITEM_REQ_PARAMS, itemMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: setItemDetails::Material details:: EXCEPTION", e);
		}
		request.setAttribute(StockCommonConstants.ITEM_REQ_PARAMS, itemMap);
		if(CGCollectionUtils.isEmpty(itemMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_ITEM_DTLS_NOT_EXIST));
			LOGGER.warn("AbstractStockAction:: setItemDetails :: Item  Details Does not exist");
		}
	}

	/**
	 * Name : setStockIssueTypeDetails
	 * purpose : in order to display Drop Down in the Stock Issue to Branch/BA/FR screen and maintaining in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set Issued-To  dropdown  details in Session/request scope.
	 *
	 * @param request the new stock issue std type details
	 */
	public void setStockIssueStdTypeDetails(HttpServletRequest request) {
		setStockIssuedTOTypeDetails(request);
		setStockPaymentDetails(request);

	}

	/**
	 * @param request
	 */
	private void setStockIssuedTOTypeDetails(HttpServletRequest request) {
		Map<String, String> stdIssueTypeMap;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		stdIssueTypeMap = (Map<String,String> )session.getAttribute(StockCommonConstants.ISSUE_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(stdIssueTypeMap)) {
				stdIssueTypeMap = stockCommonService.getStockStandardTypesAsMap(StockCommonConstants.STOCK_STD_TYPE_ISSUE);
				session.setAttribute(StockCommonConstants.ISSUE_REQ_PARAMS, stdIssueTypeMap);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ISSUE_STANDRD_TYPE));
			LOGGER.error("AbstractStockAction:: setStockIssueStdTypeDetails", e);
		}
		if(CGCollectionUtils.isEmpty(stdIssueTypeMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_ISSUE_STANDRD_TYPE));
			LOGGER.warn("AbstractStockAction:: setStockIssueStdTypeDetails :: Does not Exist");
		}
		request.setAttribute(StockCommonConstants.ISSUE_REQ_PARAMS, stdIssueTypeMap);
	}
	private void setStockPaymentDetails(HttpServletRequest request) {
		Map<String, String> stockPaymentDtls;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		stockPaymentDtls = (Map<String,String> )session.getAttribute(StockCommonConstants.ISSUE_PAYMNT_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(stockPaymentDtls)) {
				stockPaymentDtls = stockCommonService.getStockStandardTypesAsMap(StockCommonConstants.STOCK_STD_TYPE_PAYMENT);
				session.setAttribute(StockCommonConstants.ISSUE_PAYMNT_REQ_PARAMS, stockPaymentDtls);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_STD_PAYMENT_TYPE));
			LOGGER.error("AbstractStockAction:: setStockPaymentDetails", e);
		}
		if(CGCollectionUtils.isEmpty(stockPaymentDtls)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_STD_PAYMENT_TYPE));
			LOGGER.warn("AbstractStockAction:: setStockPaymentDetails :: Does not Exist");
		}
		request.setAttribute(StockCommonConstants.ISSUE_PAYMNT_REQ_PARAMS, stockPaymentDtls);
	}
	/**
	 * setStockRequisitionStdTypeDetails :: for Requisition Status whether is Open/Approved
	 * @param request
	 */
	public void setStockRequisitionStdTypeDetails(HttpServletRequest request) {
		Map<String, String> requisitionStatus;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		requisitionStatus = (Map<String,String> )session.getAttribute(StockCommonConstants.REQUISITION_STATUS_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(requisitionStatus)) {
				requisitionStatus = stockCommonService.getStockStandardTypesAsMap(StockCommonConstants.REQUISITON_STATUS_STD_TYPE);
				session.setAttribute(StockCommonConstants.REQUISITION_STATUS_REQ_PARAMS, requisitionStatus);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.REQUISITION_STATUS_STANDRD_TYPE));
			LOGGER.error("AbstractStockAction:: setStockRequisitionStdTypeDetails", e);
		}
		if(CGCollectionUtils.isEmpty(requisitionStatus)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.REQUISITION_STATUS_STANDRD_TYPE));
			LOGGER.warn("AbstractStockAction:: setStockRequisitionStdTypeDetails :: Does not Exist");
		}
		request.setAttribute(StockCommonConstants.REQUISITION_STATUS_REQ_PARAMS, requisitionStatus);

	}
	/**
	 * setProcurementStdTypeDetails :: for Stock Requisition  approval at RHO (only necessary for SAP)
	 * @param request
	 */
	public void setProcurementStdTypeDetails(HttpServletRequest request) {
		Map<String, String> procurementTypeMap;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		procurementTypeMap = (Map<String,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_PROCUREMENT);
		try {
			if(CGCollectionUtils.isEmpty(procurementTypeMap)) {
				procurementTypeMap = stockCommonService.getStockStandardTypesAsMap(StockCommonConstants.STOCK_STD_TYPE_PROCUREMENT);
				session.setAttribute(StockCommonConstants.REQ_PARAM_PROCUREMENT, procurementTypeMap);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.PROCUREMENT_STANDRD_TYPE));
			LOGGER.error("AbstractStockAction:: setProcurementStdTypeDetails", e);
		}
		if(CGCollectionUtils.isEmpty(procurementTypeMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.PROCUREMENT_STANDRD_TYPE));
			LOGGER.warn("AbstractStockAction:: setProcurementStdTypeDetails :: Does not Exist");
		}
		request.setAttribute(StockCommonConstants.REQ_PARAM_PROCUREMENT, procurementTypeMap);

	}

	/**
	 * Name : setStockIssueTypeDetails
	 * purpose : in order to display Drop Down in the Stock Issue to Branch/BA/FR screen and maintaining in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set Issued-To  dropdown  details in Session/request scope.
	 *
	 * @param request the new stock transfer std type details
	 */
	public void setStockTransferStdTypeDetails(HttpServletRequest request) {
		Map<String, String> transfertTOmap;

		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		transfertTOmap = (Map<String,String> )session.getAttribute(StockCommonConstants.TRANSFER_TO_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(transfertTOmap)) {
				transfertTOmap = stockCommonService.getStockTransferTOStandardTypesAsMap();
				session.setAttribute(StockCommonConstants.TRANSFER_TO_REQ_PARAMS, transfertTOmap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: setStockTransferStdTypeDetails ::Exception", e);
		}
		request.setAttribute(StockCommonConstants.TRANSFER_TO_REQ_PARAMS, transfertTOmap);
		if(CGCollectionUtils.isEmpty(transfertTOmap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_TRANSFER_TO_STANDRD_TYPE));
			LOGGER.warn("AbstractStockAction:: setStockTransferStdTypeDetails :: transfert TO details Does not Exist");
		}

		Map<String, String> transferFromMap;

		transferFromMap = (Map<String,String> )session.getAttribute(StockCommonConstants.TRANSFER_FROM_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(transferFromMap)) {
				transferFromMap = stockCommonService.getStockTransferFromStandardTypesAsMap();
				session.setAttribute(StockCommonConstants.TRANSFER_FROM_REQ_PARAMS, transferFromMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: setStockTransferStdTypeDetails ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(transferFromMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_TRANSFER_FROM_STANDRD_TYPE));
			LOGGER.warn("AbstractStockAction:: setStockTransferStdTypeDetails :: transfert From details Does not Exist");
		}
		request.setAttribute(StockCommonConstants.TRANSFER_FROM_REQ_PARAMS, transferFromMap);

	}

	/**
	 * Gets the rHO office details.
	 *
	 * @param request the request
	 * @return the rHO office details
	 */
	public OfficeTO getRHOOfficeDetails(HttpServletRequest request)  {
		OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		OfficeTO rhoOffice=null;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		rhoOffice= (OfficeTO)session.getAttribute(StockCommonConstants.RHO_OFFICE_TO);
		if(loggedInOfficeTO!=null){
			Integer officeId = loggedInOfficeTO.getReportingRHO();

			if(!StringUtil.isEmptyInteger(officeId)){

				if(StringUtil.isNull(rhoOffice)){
					try {
						rhoOffice=	stockCommonService.getOfficeDetails(officeId);
					} catch (Exception e) {
						LOGGER.error("AbstractStockAction:: getOfficeDetailsByOfficeId ::RHO Office Details ::Exception ",e);
						prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.RHO_OFFICE_DETAILS));
					}
					if(rhoOffice!=null){
						request.setAttribute(StockCommonConstants.RHO_OFFICE_TO, rhoOffice);
					}else{
						prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.RHO_OFFICE_DETAILS));
						LOGGER.warn("AbstractStockAction:: getOfficeDetailsByOfficeId ::RHO Office Details  Does not Exist");
					}
				}
			}
		}
		return rhoOffice;
	}

	
	/**
	 * Gets the logged in city details.
	 *
	 * @param request the request
	 * @return the logged in city details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public CityTO getLoggedInCityDetails(HttpServletRequest request) throws CGSystemException, CGBusinessException{
		OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		Integer cityId=null;
		CityTO city=null;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		city= (CityTO)session.getAttribute(StockCommonConstants.LOGGED_IN_CITY);
		if(StringUtil.isNull(city)){
			if(loggedInOfficeTO!=null){
				cityId= loggedInOfficeTO.getCityId();
			}
			if(!StringUtil.isEmptyInteger(cityId)){
				city= stockCommonService.getCityById(cityId, null);
			}
			if(!StringUtil.isNull(city)){
				session.setAttribute(StockCommonConstants.LOGGED_IN_CITY,city);
			}
		}


		return city;
	}

	/**
	 * Sets the global details.
	 *
	 * @param request the request
	 * @param headerTO the header to
	 */
	public void setGlobalDetails(HttpServletRequest request,StockHeaderTO headerTO){
		String officeType=null;
		OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		if(loggedInOfficeTO!=null){
			officeType= loggedInOfficeTO.getOfficeTypeTO()!=null?loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode():null;
			headerTO.setOfficeType(officeType);
		}
		if(!StringUtil.isStringEmpty(officeType)&& officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
			headerTO.setRhoOfficeCode(loggedInOfficeTO.getOfficeCode());
		}else{
			OfficeTO rhoOffice =getRHOOfficeDetails(request);
			if(rhoOffice!=null){
				headerTO.setRhoOfficeCode(rhoOffice.getOfficeCode());
			}
		}

		headerTO.setOfficeRhoType(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);
		headerTO.setOfficeTypeCo(CommonConstants.OFF_TYPE_CORP_OFFICE);
		headerTO.setNoSeries(StockCommonConstants.NO_SERIES);/**for Comparing non-serial items**/
		headerTO.setNormalCnoteIdentifier(CommonConstants.PRODUCT_SERIES_NORMALCREDIT);
		
		headerTO.setIssuedToBranch(UdaanCommonConstants.ISSUED_TO_BRANCH);
		headerTO.setIssuedToFr(UdaanCommonConstants.ISSUED_TO_FR);
		headerTO.setIssuedToBa(UdaanCommonConstants.ISSUED_TO_BA);
		headerTO.setIssuedToCustomer(UdaanCommonConstants.ISSUED_TO_CUSTOMER);
		headerTO.setIssuedToEmp(UdaanCommonConstants.ISSUED_TO_EMPLOYEE);
		
		headerTO.setCreditCustomerType(CommonConstants.CUSTOMER_CODE_CREDIT);
		headerTO.setAccCustomerType(CommonConstants.CUSTOMER_CODE_ACC);
		headerTO.setLcCustomerType(CommonConstants.CUSTOMER_CODE_LC);
		headerTO.setCodCustomerType(CommonConstants.CUSTOMER_CODE_COD);
		headerTO.setCreditCardCustomerType(CommonConstants.CUSTOMER_CODE_CREDIT_CARD);
		headerTO.setGovtEntityCustomerType(CommonConstants.CUSTOMER_CODE_GOVT_ENTITY);
		headerTO.setReverseLogCustomerType(CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS);
		
		headerTO.setConsignmentType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
		headerTO.setComailNumber(UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO);
		headerTO.setBplStickers(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
		headerTO.setMbplStickers(UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS);
		headerTO.setOgmStickers(UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
		headerTO.setBagLocNumber(UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO);
		headerTO.setTransactionIssueType(StockCommonConstants.TRANSACTION_ISSUE_TYPE);
		headerTO.setTransactionPRType(StockCommonConstants.TRANSACTION_PR_TYPE);
		final	RegionTO region =getLoginRegionTO(request);
		headerTO.setRegionCode(region!=null ?region.getRegionCode():null);
		if(!StringUtil.isNull(loggedInOfficeTO)){
			headerTO.setLoggedInOfficeCode(loggedInOfficeTO.getOfficeCode());
		}
		if(StringUtil.isStringEmpty(headerTO.getCityCode())){
			try {
				final	CityTO	 city =getLoggedInCityDetails(request);
				headerTO.setCityCode(city!=null ? city.getCityCode():null);
			} catch (CGSystemException | CGBusinessException e) {
				LOGGER.error("AbstractStockAction:: setGlobalDetails ::City Details ::Exception ",e);
			}
		}
		setUserDtlsDetails(request, headerTO);

	}

	/**
	 * Sets the global details for validations.
	 *
	 * @param request the request
	 * @param validationTo the validation to
	 */
	public void setGlobalDetailsForValidations(HttpServletRequest request,StockValidationTO validationTo){
		String officeType=null;
		OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		if(loggedInOfficeTO!=null){
			officeType= loggedInOfficeTO.getOfficeTypeTO()!=null?loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode():null;
			validationTo.setOfficeCode(loggedInOfficeTO.getOfficeCode());
		}
		if(!StringUtil.isStringEmpty(officeType)&& officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
			validationTo.setRhoCode(loggedInOfficeTO.getOfficeCode());
		}else{
			OfficeTO rhoOffice =getRHOOfficeDetails(request);
			if(rhoOffice!=null){
				validationTo.setRhoCode(rhoOffice.getOfficeCode());
			}
		}
		if(!StringUtil.isNull(loggedInOfficeTO)){
			validationTo.setOfficeCode(loggedInOfficeTO.getOfficeCode());
		}
		final	RegionTO region =getLoginRegionTO(request);
		validationTo.setRegionCode(region!=null ?region.getRegionCode():null);
		try {
			final	CityTO	 city =getLoggedInCityDetails(request);
			validationTo.setCityCode(city!=null ? city.getCityCode():null);
		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("AbstractStockAction:: setGlobalDetailsForValidations ::Exception ",e);
		}

	}
	/**
	 * configurableParams
	 * @param request
	 * @return
	 */
	public Map<String, String> getConfigParams(final HttpServletRequest request){
		UserInfoTO uinforTo= getLoginUserInfoTO(request);
		if(!StringUtil.isNull(uinforTo)&& !CGCollectionUtils.isEmpty(uinforTo.getConfigurableParams())){
			configurableParams=uinforTo.getConfigurableParams();
		}
		return configurableParams;
	}
	/**
	 * setConfigParamsForStock
	 * @param request
	 * @param headerTO
	 */
	public void setConfigParamsForStock(HttpServletRequest request,StockHeaderTO headerTO){
		if(CGCollectionUtils.isEmpty(configurableParams)){
			configurableParams=getConfigParams(request);
		}
		if(!CGCollectionUtils.isEmpty(configurableParams)){
			headerTO.setBaAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_BA_SERIES));
			headerTO.setAccCustAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_ACC_CUSTOMER_SERIES));
			headerTO.setEmpAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_EMP_SERIES));
			headerTO.setFranchiseeAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_FR_SERIES));
			headerTO.setCreditCustAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_CREDIT_CUSTOMER_SERIES));
			headerTO.setCreditCardCustomerAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_CREDIT_CARD_CUSTOMER_SERIES));
			headerTO.setReverseLogCustomerAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_RL_CUSTOMER_SERIES));
			headerTO.setLcCustomerAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_LC_CUSTOMER_SERIES));
			headerTO.setGovtEntityCustomerAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_GOVT_CUSTOMER_SERIES));
			headerTO.setCodCustomerAllowedSeries(configurableParams.get(StockCommonConstants.STOCK_ISSUE_COD_CUSTOMER_SERIES));
		
		}
		
	}

	/**
	 * Name : getCnoteDetails
	 * purpose : in order to display Cnotes Drop Down in the Stock Issue to Customer/Employee screen and maintaining in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set cnotes  dropdown  details in Session/request scope.
	 *
	 * @param request the request
	 * @return the cnote details
	 */
	public void getCnoteDetails(HttpServletRequest request) {
		Map<Integer, String> cnotesMap;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		cnotesMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_CNOTES);
		try {
			if(CGCollectionUtils.isEmpty(cnotesMap)) {
				cnotesMap = stockCommonService.getAllCnoteItemDetails(UdaanCommonConstants.SERIES_TYPE_CNOTES);
				session.setAttribute(StockCommonConstants.REQ_PARAM_CNOTES, cnotesMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: getCnoteDetails ::Exception", e.getMessage());
		}
		if(CGCollectionUtils.isEmpty(cnotesMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_CNOTE_DTLS_NOT_EXIST));
			LOGGER.warn("AbstractStockAction:: getCnoteDetails ::Does not Exist");
		}
		request.setAttribute(StockCommonConstants.REQ_PARAM_CNOTES, cnotesMap);

	}
	
	
	/**
	 * Populate tax components by logged in office.
	 * Name : populateTaxComponentsByLoggedInOffice
	 * purpose : in order to hold Tax components details for the BA rate issue Screen maintaining in the session
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set cnotes  dropdown  details in Session/request scope.
	 * @param request the request
	 * @param paymntDtlsTo the paymnt dtls to
	 */
	public void populateTaxComponentsByLoggedInOffice(HttpServletRequest request,StockIssuePaymentDetailsTO paymntDtlsTo) {
		Map<String,Double> taxComponents;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		taxComponents = (Map<String,Double>)session.getAttribute(StockCommonConstants.TAX_SESION_COMP);
		try {
			if(CGCollectionUtils.isEmpty(taxComponents)) {
				taxComponents = stockCommonService.getTaxComponents(getLoggedInCityDetails(request),DateUtil.getCurrentDate());
				session.setAttribute(StockCommonConstants.TAX_SESION_COMP, taxComponents);
				initializeStockPaymentDetails(paymntDtlsTo, taxComponents);
			}else{
				initializeStockPaymentDetails(paymntDtlsTo, taxComponents);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: populateTaxComponentsByLoggedInOffice ::Exception", e.getMessage());
		}
	}

	/**
	 * @param paymntDtlsTo
	 * @param taxComponents
	 */
	private void initializeStockPaymentDetails(
			StockIssuePaymentDetailsTO paymntDtlsTo,
			Map<String, Double> taxComponents) {
		if(!StringUtil.isNull(paymntDtlsTo)&& StringUtil.isEmptyLong(paymntDtlsTo.getStockPaymentId())){
			UniversalConverterUtil.prepareValidTaxComponents(taxComponents);
			populateTaxDetails(paymntDtlsTo, taxComponents);
		}
	}

	

	/**
	 * @param paymntDtlsTo
	 * @param taxComponents
	 */
	private void populateTaxDetails(StockIssuePaymentDetailsTO paymntDtlsTo,
			Map<String, Double> taxComponents) {
		if(CGCollectionUtils.isEmpty(taxComponents)){
			LOGGER.error("AbstractStockAction:: populateTaxDetails :: ##################TAX DETAILS DOESNOT EXIST############");
		}else{
			for(String taxKey:taxComponents.keySet()){
				switch(taxKey){
				case RateUniversalConstants.STATE_TAX_CODE:
					paymntDtlsTo.setStateTax(taxComponents.get(taxKey));
					paymntDtlsTo.setIsForPanIndia("N");
					break;
				case RateUniversalConstants.SURCHARGE_ON_STATE_TAX_CODE:
					paymntDtlsTo.setSurChrgeStateTax(taxComponents.get(taxKey));
					break;
				case RateUniversalConstants.SERVICE_TAX_CODE:
					paymntDtlsTo.setServiceTax(taxComponents.get(taxKey));
					paymntDtlsTo.setIsForPanIndia("Y");
					break;
				case RateUniversalConstants.EDU_CESS_CODE:
					paymntDtlsTo.setEduCessTax(taxComponents.get(taxKey));
					break;
				case RateUniversalConstants.HIGHER_EDU_CES_CODE:
					paymntDtlsTo.setHeduCessTax(taxComponents.get(taxKey));
					break;
				}
			}
		}
	}

	/**
	 * Name : getSerialNumberedItemDetails
	 * purpose : in order to display SerialNumber itemDetails Drop Down in the Stock cancellations screen and maintaining in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set cnotes  dropdown  details in Session/request scope.
	 *
	 * @param request the request
	 * @return the serial numbered item details
	 */
	public void getSerialNumberedItemDetails(HttpServletRequest request) {
		Map<Integer, String> serialNumberedItems;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		serialNumberedItems = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_SERIAL_NUMBER_ITEMS);
		try {
			if(CGCollectionUtils.isEmpty(serialNumberedItems)) {
				serialNumberedItems = stockCommonService.getAllSerialNumberItemMap();
				session.setAttribute(StockCommonConstants.REQ_PARAM_SERIAL_NUMBER_ITEMS, serialNumberedItems);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_ITEM_DTLS_NOT_EXIST));
			LOGGER.error("AbstractStockAction:: getSerialNumberedItemDetails ::Exception", e.getMessage());
		}
		if(CGCollectionUtils.isEmpty(serialNumberedItems)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_ITEM_DTLS_NOT_EXIST));
		}
		request.setAttribute(StockCommonConstants.REQ_PARAM_SERIAL_NUMBER_ITEMS, serialNumberedItems);

	}

	/**
	 * Name : getLoggedInOfficeEmployeeDetails
	 * purpose : in order to display Cnotes Drop Down in the Stock Issue to Customer/Employee screen and maintaining in the session/request scope
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set cnotes  dropdown  details in Session/request scope.
	 *
	 * @param request the request
	 * @return the logged in office employee details
	 */
	public void getLoggedInOfficeEmployeeDetails(HttpServletRequest request) {
		Map<Integer, String> employeeMap;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		employeeMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_EMPLOYEE);
		Integer officeId=getLoginOfficeTO(request).getOfficeId();
		try {
			if(CGCollectionUtils.isEmpty(employeeMap)) {
				employeeMap = stockCommonService.getAllEmployeeDtlsByLoggedInOffice(officeId);
				session.setAttribute(StockCommonConstants.REQ_PARAM_EMPLOYEE, employeeMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: getLoggedInOfficeEmployeeDetails ::Exception", e.getMessage());
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_EMPLOYEE_DETAILS));
		}
		if(CGCollectionUtils.isEmpty(employeeMap)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_EMPLOYEE_DETAILS));
		}
		request.setAttribute(StockCommonConstants.REQ_PARAM_EMPLOYEE, employeeMap);

	}

	/**
	 * Name : getLoggedInOfficeCustomerDetails
	 * purpose : getLoggedInOfficeCustomerDetails
	 * Input : HttpServletRequest request, HttpSession session
	 * return : set cnotes  dropdown  details in Session/request scope.
	 *
	 * @param request the request
	 * @return the logged in office customer details
	 */
	public void getLoggedInOfficeCustomerDetails(HttpServletRequest request) {
		Map<Integer, String> customerMap;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		Integer officeId=getLoginOfficeTO(request).getOfficeId();
		customerMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER);
		try {
			if(CGCollectionUtils.isEmpty(customerMap)) {
				//customerMap = stockCommonService.getAllCustomerDtlsByLoggedInOffice(officeId);
				customerMap = stockCommonService.getStockCustomerDtlsByLoggedInOffice(officeId, getLoginOfficeType(request));
				session.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER, customerMap);
			}
			if(CGCollectionUtils.isEmpty(customerMap)){
				prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_CUSTOMER_DETAILS));
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: getLoggedInOfficeCustomerDetails ::Exception", e.getMessage());
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_CUSTOMER_DETAILS));
		}

		request.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER, customerMap);

	}
	
	/**
	 * It Holds Two Customer Details sessions i) Map of CustomerId,CustomerName for UI purpose
	 *                  ii) Map of CustomerId,CustomerTO for validations purpose
	 * @param request
	 */
	public void getLoggedInOfficeCustomerToList(HttpServletRequest request) {
		Map<Integer, CustomerTO> customerMapList;
		Map<Integer, String> customerMap=null;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		Integer officeId=getLoginOfficeTO(request).getOfficeId();
		customerMapList = (Map<Integer,CustomerTO> )session.getAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER_TO_LIST);
		customerMap = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER);
		try {
			if(CGCollectionUtils.isEmpty(customerMapList) || CGCollectionUtils.isEmpty(customerMap)) {
				//List<CustomerTO> customerTOList= stockCommonService.getCustomerListByLoggedInOffice(officeId);
				List<CustomerTO> customerTOList=stockCommonService.getAllCustomerByLoggedInOffice(officeId, getLoginOfficeType(request));
				if(!CGCollectionUtils.isEmpty(customerTOList)){
					customerMapList=StockBeanUtil.prepareCustomerListMap(customerTOList);
					customerMap=StockBeanUtil.prepareCustomerMap(customerTOList);
				}
				session.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER_TO_LIST, customerMapList);
				session.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER, customerMap);
			}else{
				request.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER, customerMap);
			}
			if(CGCollectionUtils.isEmpty(customerMap)){
				prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.STOCK_CUSTOMER_DETAILS));
			}
		} catch (Exception e) {
			LOGGER.error("AbstractStockAction:: getLoggedInOfficeCustomerToList ::Exception", e.getMessage());
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.STOCK_CUSTOMER_DETAILS));
		}

		request.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER, customerMap);

	}

	/**
	 * Name 	: ajaxGetAllItemDetails
	 * purpose 	: get item details
	 * Input 	: item id
	 * return 	: ItemTO.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetAllItemDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		java.io.PrintWriter out = null;
		ItemTO itemTO = null;
		String jsonResult = null;
		stockCommonService = getCommonServiceForStock();
		Integer officeId = getLoginOfficeTO(request).getOfficeId();
		Integer stockQnty = null;

		try {
			out = response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String itemID = request.getParameter(StockCommonConstants.ITEM_ID);
			String issue = request.getParameter(StockCommonConstants.ISSUE);//true

			serializer = CGJasonConverter.getJsonObject();
			if(StringUtil.isInteger(itemID)){
				Integer itemId = StringUtil.parseInteger(itemID);
				itemTO = new ItemTO();
				itemTO.setItemId(itemId);
				List<ItemTO> items = stockCommonService.getAllItemDetails(itemTO);
				if(!StringUtil.isEmptyColletion(items)){
					itemTO = items.get(0);
					if(itemTO.getItemTypeTO()!=null){
						String seriesType=itemTO.getItemTypeTO().getItemTypeCode();
						//below code is important from Series validations perspective
						/*if(!StringUtil.isStringEmpty(seriesType)&& seriesType.equalsIgnoreCase(UdaanCommonConstants.SERIES_TYPE_CNOTES)){
							itemTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES);
						}else{
							itemTO.setSeriesType(itemTO.getItemCode());
						}*/
						itemTO.setSeriesType(seriesType);
					}
					stockQnty = getStockForLoggedInOffice(itemId,officeId,issue);
					itemTO.setStockQuantity(stockQnty);
				}else{
					LOGGER.warn("AbstractStockAction:: ajaxGetAllItemDetails ::Item details does not Exist");
				}
			}
			if(!StringUtil.isNull(itemTO)){
				jsonResult = serializer.toJSON(itemTO).toString();
			}
		}  catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction::ajaxActionGetPartyTypeDetails ..CGSystemException :",e);
			jsonResult= prepareCommonException(StockUniveralConstants.RESP_ERROR,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction::ajaxActionGetPartyTypeDetails ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(StockUniveralConstants.RESP_ERROR,exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	/**
	 * @param request
	 */
	public  void setUrl(HttpServletRequest request,String url) {
		request.setAttribute(StockCommonConstants.URL, url);
	}
	public void setUserDtlsDetails(HttpServletRequest request,StockHeaderTO headerTO){
		final UserTO userinfo=getLoginUserTO(request);
		if(!StringUtil.isNull(userinfo)){
			headerTO.setUserInfo("$$$$$$$UserName :["+userinfo.getUserCode()+"Name :["+userinfo.getUserName()+"] $$$$$$");
		}
		headerTO.setTodayDate(DateUtil.todayDate());

	}
	/**
	 * For List View OF Stock Requisition Screen as a Dropdown
	 * @param request
	 */
	public void getOfficeMapUnderLoggedInOffice(HttpServletRequest request) {
		Map<Integer, String> officeList;
		HttpSession session=request.getSession(false);
		stockCommonService = getCommonServiceForStock();
		final OfficeTO officeTo=getLoginOfficeTO(request);
		officeList = (Map<Integer,String> )session.getAttribute(StockCommonConstants.REQ_OFFICE_MAP);
		try {
			if(CGCollectionUtils.isEmpty(officeList)) {
				officeList = stockCommonService.getOfficeUnderOfficeIdAsMap(officeTo.getOfficeId());
				if(CGCollectionUtils.isEmpty(officeList)){
					officeList= new HashMap<>(1);
					officeList.put(officeTo.getOfficeId(), officeTo.getOfficeCode()+FrameworkConstants.CHARACTER_HYPHEN+officeTo.getOfficeName());
				}else{
					officeList= CGCollectionUtils.sortByValue(officeList);
				}
				session.setAttribute(StockCommonConstants.REQ_OFFICE_MAP, officeList);
			}
		} catch (Exception e) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,StockCommonConstants.ST_REQ_OFFICE_DROP_DOWN));
			LOGGER.error("AbstractStockAction:: getOfficeMapUnderLoggedInOffice", e);
		}
		if(CGCollectionUtils.isEmpty(officeList)) {
			prepareActionMessage(request, new ActionMessage(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,StockCommonConstants.ST_REQ_OFFICE_DROP_DOWN));
			LOGGER.warn("AbstractStockAction:: getOfficeMapUnderLoggedInOffice :: Does not Exist");
		}
		request.setAttribute(StockCommonConstants.REQ_OFFICE_MAP, officeList);

	}
	/**
	 * Ajax action get party type details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxActionGetPartyTypeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractStockAction::ajaxActionGetPartyTypeDetails::Start=======>");
		String partyType=request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_TO_PARTY_TYPE);
		Integer loggedInofficeId=getLoginOfficeTO(request).getOfficeId();
		java.io.PrintWriter out=null;
		String result="";
		Map<Integer,String> partyDetailsMap=null;
		stockCommonService = getCommonServiceForStock();
		HttpSession session=request.getSession(false);		
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			if(!StringUtil.isStringEmpty(partyType)){
				partyDetailsMap = (Map<Integer,String> )session.getAttribute(partyType);
				if(partyDetailsMap==null || partyDetailsMap.isEmpty()) {
					if(partyType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_EMPLOYEE)){
					partyDetailsMap = stockCommonService.getPartyTypeDetailsByPartyType(partyType, loggedInofficeId, getLoginOfficeType(request));
					}else{
						List<CustomerTO> customerList=stockCommonService.getCustomerFrAndBADetailsByPartyType(partyType, loggedInofficeId, getLoginOfficeType(request));
						if(!StringUtil.isEmptyColletion(customerList)){
							partyDetailsMap = StockBeanUtil.prepareCustomerMap(customerList);
							Map <Integer,CustomerTO> customerMap = StockBeanUtil.prepareCustomerListMap(customerList);
							session.setAttribute(StockCommonConstants.REQ_PARAM_CUSTOMER_TO_LIST+partyType, customerMap);
							
						}else{
							LOGGER.warn("StockCommonServiceImpl::getAllBusinessAssociatesByLoggedInOffice details does not exist ");
						}
					}
					session.setAttribute(partyType, partyDetailsMap);
				}
			}
			result=CollectionUtils.isEmpty(partyDetailsMap)?null:partyDetailsMap.toString();

		}catch (CGSystemException e) {
			LOGGER.error("AbstractStockAction::ajaxActionGetPartyTypeDetails ..CGSystemException :",e);
			result= prepareCommonException(StockUniveralConstants.RESP_ERROR,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractStockAction::ajaxActionGetPartyTypeDetails ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(StockUniveralConstants.RESP_ERROR,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractStockAction::ajaxActionGetPartyTypeDetails::end=======>");
	}
	/**
	 * getCustomerTOById
	 * @param request
	 * @param customerId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CustomerTO getCustomerTOByIdFromSession(HttpServletRequest request,Integer customerId,String sessionFlag) throws CGSystemException, CGBusinessException{
		stockCommonService = getCommonServiceForStock();
		HttpSession session=request.getSession(false);
		CustomerTO stockUserTo=null;
		Map<Integer,CustomerTO>  customerMapList = (Map<Integer,CustomerTO> )session.getAttribute(sessionFlag);
		if(!CGCollectionUtils.isEmpty(customerMapList)){
			stockUserTo = customerMapList.get(customerId);
		}
		if(StringUtil.isNull(stockUserTo)){
			stockUserTo= new CustomerTO();
			stockUserTo.setCustomerId(customerId);
			stockUserTo = stockCommonService.getCustomerDetils(stockUserTo);
		}
	return stockUserTo;
	}
	
	/**
	 * ajaxGetCustomerDetailsById
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void ajaxGetCustomerDetailsById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		CustomerTO stockUserTo=null;
		String result="";
		Integer customerId=null;
		
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			String partytypeId = request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_PARTY_TYPE_ID);
			String partytype = request.getParameter(StockCommonConstants.REQ_PARAM_ISSUE_TO_PARTY_TYPE);
			String sessionFlag=StockCommonConstants.REQ_PARAM_CUSTOMER_TO_LIST;
			if(!StringUtil.isStringEmpty(partytype)){
				sessionFlag= sessionFlag+partytype.trim();
			}
			customerId=StringUtil.convertStringToInteger(partytypeId);
			if(!StringUtil.isEmptyInteger(customerId)){
				stockUserTo=getCustomerTOByIdFromSession(request, customerId,sessionFlag);
			}
			if(!StringUtil.isNull(stockUserTo)){
				result = serializer.toJSON(stockUserTo).toString();
				}
			
		} catch (Exception e) {
			LOGGER.error("StockIssueAction:: ajaxGetCustomerDetailsById", e);
			result= ExceptionUtil.getExceptionStackTrace(e);
			LOGGER.error("StockIssueAction:: ajaxGetCustomerDetailsById ::EXception", result);
		}
		
		finally {
			
			out.print(result);
			out.flush();
			out.close();
		}
		

	}
}
