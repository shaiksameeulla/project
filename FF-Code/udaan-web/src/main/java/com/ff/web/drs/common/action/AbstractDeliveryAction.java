/**
 * 
 */
package com.ff.web.drs.common.action;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.print.service.PrintJobUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.blkpending.form.BulkCnPendingDrsForm;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.CodLcToPayDrsForm;
import com.ff.web.drs.common.form.CreditCardDrsForm;
import com.ff.web.drs.common.form.ManualDrsForm;
import com.ff.web.drs.common.form.NormalPriorityDrsForm;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.manualdrs.service.ManualDrsService;
import com.ff.web.drs.updation.form.PendingDrsForm;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 *Start of Changes by <31913> on 24/04/2013
 */
public  class AbstractDeliveryAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractDeliveryAction.class);
	
	
	/** The Drs(Delivery) common service. */
	public transient DeliveryCommonService deliveryCommonService;
	/** The serializer. */
	public transient JSONSerializer serializer;
	public  transient Map<String, String> configurableParams=null;
	public ManualDrsService manualDrsService;
	
	/** The print job universal service. */
	public PrintJobUniversalService printJobUniversalService = null;

	/**
	 * To get print job universal service object bean
	 * 
	 * @return printJobUniversalService
	 */
	public PrintJobUniversalService getPrintJobUniversalService() {
		if (StringUtil.isNull(printJobUniversalService)) {
			printJobUniversalService = (PrintJobUniversalService) getBean("printJobUniversalService");
		}
		return printJobUniversalService;
	}
	
	/**
	 * Gets the common service for stock.
	 *
	 * @return the common service for stock
	 */
	public DeliveryCommonService getCommonServiceForDelivery() {
		
		if(StringUtil.isNull(deliveryCommonService)){
			deliveryCommonService = (DeliveryCommonService)getBean(SpringConstants.DRS_COMMON_SERVICE);
		}
			return deliveryCommonService;
	}
	public ManualDrsService getManualDrsService() {

		if(StringUtil.isNull(manualDrsService)){
			manualDrsService = (ManualDrsService)getBean(SpringConstants.DRS_MANUAL_SERVICE);
		}
		return manualDrsService;
	}
	/**
	 * Name : getLoginUserTO
	 * purpose : to get UserTO from Session
	 * Input : HttpServletRequest request
	 * return : UserTO 
	 */
	public UserTO getLoginUserTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			LOGGER.trace("AbstractDeliveryAction::getLoginUserTO");
			return userInfo.getUserto();
		}
		return null;
	}
	/**
	 * Name : getLoginUserInfoTO
	 * purpose : to get UserInfoTO from Session
	 * Input : HttpServletRequest request
	 * return : UserInfoTO 
	 */
	public UserInfoTO getLoginUserInfoTO(final HttpServletRequest request) {
		final HttpSession session =request.getSession(Boolean.FALSE);
		final UserInfoTO userInfo =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		return userInfo;
	}
	
	/**
	 * Gets the config params.
	 *
	 * @param request the request
	 * @return the config params
	 */
	public Map<String, String> getConfigParams(final HttpServletRequest request){
		UserInfoTO uinforTo= getLoginUserInfoTO(request);
		if(!StringUtil.isNull(uinforTo)&& !CGCollectionUtils.isEmpty(uinforTo.getConfigurableParams())){
			configurableParams=uinforTo.getConfigurableParams();
		}
		return configurableParams;
	}
	
	/**
	 * Gets the config param value.
	 *
	 * @param request the request
	 * @param paramKey the param key
	 * @return the config param value
	 */
	public String getConfigParamValue(final HttpServletRequest request,String paramKey){
		configurableParams= getConfigParams(request);
		String pramValue=null;
		if(!CGCollectionUtils.isEmpty(configurableParams)){
			if(paramKey.equalsIgnoreCase(UniversalDeliveryContants.MANUAL_DRS_CONFIG_PARAMS_SERIES)){
				pramValue=getConfigParamsValue(UniversalDeliveryContants.NP_DOX_DRS_CONFIG_PARAMS_SERIES)+FrameworkConstants.CHARACTER_COMMA+
						getConfigParamsValue(UniversalDeliveryContants.NP_PPX_DRS_CONFIG_PARAMS_SERIES)+FrameworkConstants.CHARACTER_COMMA+
						getConfigParamsValue(UniversalDeliveryContants.CCQ_DRS_CONFIG_PARAMS_SERIES)+FrameworkConstants.CHARACTER_COMMA+
						getConfigParamsValue(UniversalDeliveryContants.COD_LC_DOX_DRS_CONFIG_PARAMS_SERIES)+FrameworkConstants.CHARACTER_COMMA+
						getConfigParamsValue(UniversalDeliveryContants.COD_LC_PPX_DRS_CONFIG_PARAMS_SERIES)+FrameworkConstants.CHARACTER_COMMA;
			}else{
				pramValue=configurableParams.get(paramKey);
			}
		} 
		return pramValue;
	}
	/**
	 * @return
	 */
	public String getConfigParamsValue(String paramKey) {
		String paramValue=configurableParams.get(paramKey);
		return !StringUtil.isStringEmpty(paramValue)?paramValue:FrameworkConstants.EMPTY_STRING;
	}
	/**
	 * Name : getLoginEmpTO
	 * purpose : to get EmployeeTO from Session
	 * Input : HttpServletRequest request
	 * return : EmployeeTO 
	 */
	public EmployeeTO getLoginEmpTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			final EmployeeUserTO empUserTo = userInfo.getEmpUserTo();
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
	 * return : OfficeTO 
	 */
	public OfficeTO getLoginOfficeTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		OfficeTO officeTo=null;
		if(userInfo!=null){
			return  userInfo.getOfficeTo();
		}
		return officeTo;
	}
	
	/**
	 * Name : getLoginRegionTO
	 * purpose : to get RegionTO from Session
	 * Input : HttpServletRequest request
	 * return : RegionTO 
	 */
	public RegionTO getLoginRegionTO(final HttpServletRequest request){
		OfficeTO officeTo = getLoginOfficeTO(request);
		 RegionTO regionTo=null;
		if(officeTo!=null){
			regionTo= officeTo.getRegionTO();
		}
		return regionTo;
	}
	
	/**
	 * Name : isEmployeeLoggedIn
	 * purpose : to verify whether logged-in code is Of employee from Session
	 * Input : HttpServletRequest request
	 * return : Boolean 
	 */
	public Boolean isEmployeeLoggedIn(final HttpServletRequest request){
	final EmployeeTO employeeTo=getLoginEmpTO(request);
	boolean result = Boolean.FALSE;
		if(!StringUtil.isNull(employeeTo)){
			result = Boolean.TRUE;
		}
		return result;
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
		deliveryCommonService = getCommonServiceForDelivery();
		city= (CityTO)session.getAttribute(DrsConstants.LOGGED_IN_CITY);
		if(StringUtil.isNull(city)){
			if(loggedInOfficeTO!=null){
				cityId= loggedInOfficeTO.getCityId();
			}
			if(!StringUtil.isEmptyInteger(cityId)){
				city= deliveryCommonService.getCityById(cityId);
			}
			if(!StringUtil.isNull(city)){
				session.setAttribute(DrsConstants.LOGGED_IN_CITY,city);
			}
		}
		
		
		return city;
	}
	
	/**
	 * Sets the global details.
	 *
	 * @param request the request
	 * @param baseTO the base to
	 */
	public void setGlobalDetails(HttpServletRequest request,AbstractDeliveryTO baseTO){
		final String officeType;
		/**
		 * getting loggedInOfficeTO from session
		 */
		final OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		
		final UserTO userTo = getLoginUserTO(request);
		if(loggedInOfficeTO!=null){
			officeType= loggedInOfficeTO.getOfficeTypeTO()!=null?loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode():null;
			baseTO.setLoggedInOfficeType(officeType);
			baseTO.setLoginOfficeId(loggedInOfficeTO.getOfficeId());
			baseTO.setLoginOfficeCode(loggedInOfficeTO.getOfficeCode());
			
		}
		if(userTo!=null){
		baseTO.setLoggedInUserId(userTo.getUserId());
		if(StringUtil.isEmptyInteger(baseTO.getCreatedByUserId())){
			baseTO.setCreatedByUserId(userTo.getUserId());
		}
		}
		
		/**
		 * getting Region TO from session
		 */
		final	RegionTO region =getLoginRegionTO(request);
		if(!StringUtil.isNull(region)){
			baseTO.setLoginRegionCode(region.getRegionCode());
			baseTO.setLoginRegionId(region.getRegionId());
		}
		if(!StringUtil.isNull(loggedInOfficeTO)){
			baseTO.setLoginOfficeCode(loggedInOfficeTO.getOfficeCode());
			baseTO.setLoginOfficeId(loggedInOfficeTO.getOfficeId());
			baseTO.setLoginOfficeName(loggedInOfficeTO.getOfficeName());
			
		}
		
		/**
		 * getting CityTO TO from session/from the DB
		 */
		try {
			final	CityTO	 city =getLoggedInCityDetails(request);
			if(!StringUtil.isNull(city)){
				baseTO.setLoginCityCode(city.getCityCode());
				baseTO.setLoginCityId(city.getCityId());
			}

		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("AbstractDeliveryAction :: setGlobalDetails:: EXCEPTION (at City)",e);
		}
		baseTO.setConsigmntTypeDox(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		baseTO.setConsigmntTypePpx(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		
		if(StringUtil.isStringEmpty(baseTO.getDrsDateTimeStr())){
		baseTO.setDrsDateTimeStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		
		if(StringUtil.isStringEmpty(baseTO.getDrsOfficeName())){
		baseTO.setDrsOfficeName(loggedInOfficeTO.getOfficeCode()+CommonConstants.HYPHEN+loggedInOfficeTO.getOfficeName());
		}
		
		if(StringUtil.isEmptyLong(baseTO.getDeliveryId())){
			baseTO.setYpDrs(DrsConstants.YP_DRS_NO);
		}
		baseTO.setFlagNo(DrsConstants.FLAG_NO);
		baseTO.setFlagYes(DrsConstants.FLAG_YES);
		baseTO.setComailLength(UdaanCommonConstants.CO_MAIL_LENGTH);
		baseTO.setConsgNumLength(UdaanCommonConstants.CN_LENGTH);
		baseTO.setComailStartsWith(UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT);
		baseTO.setDlvTypeOffice(UniversalDeliveryContants.DELIVERY_TYPE_OFFICE);
		baseTO.setDlvTypeNonDlv(UniversalDeliveryContants.DELIVERY_TYPE_NO_DELIVERY);
		baseTO.setDrsStatusUpdate(DrsConstants.DRS_STATUS_UPDATED);
		/** Inspect whether party type details loaded already*/
		if(!CGCollectionUtils.isEmpty(baseTO.getPartyTypeMap())){
			request.setAttribute(DrsCommonConstants.DRS_PARTY_REQ_PARAMS, baseTO.getPartyTypeMap());
		}
		if(!StringUtil.isStringEmpty(baseTO.getDrsType())){
			request.setAttribute(DrsCommonConstants.DRS_TYPE, baseTO.getDrsType());
		}
		baseTO.setSeriesQ(CommonConstants.PRODUCT_SERIES_QUALITY);
		baseTO.setSeriesC(CommonConstants.PRODUCT_SERIES_CREDIT_CARD);
		baseTO.setSeriesL(CommonConstants.PRODUCT_SERIES_CASH_COD);
		baseTO.setSeriesT(CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD);
		baseTO.setSeriesD(CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT);
		
		/** Set current Date & time for FS in and Dlv time validations*/
		baseTO.setCurrentDateStr(DateUtil.todayDate());
		baseTO.setCurrentTimeStr(DateUtil.getCurrentTime());
		
		baseTO.setCompanySealAndSign(UniversalDeliveryContants.COMPANY_SEAL_AND_SIGN);
		baseTO.setDrsStatusClosed(DrsConstants.DRS_STATUS_CLOSED);
		
		baseTO.setDeliveredStatus(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
		baseTO.setPendingStatus(UniversalDeliveryContants.DELIVERY_STATUS_PENDING);
		
		baseTO.setManifestDrsTypeDrs(DrsConstants.MANIFEST_DRS_TYPE_DRS);
		baseTO.setManifestDrsTypeMnfst(DrsConstants.MANIFEST_DRS_TYPE_MANIFEST);
		baseTO.setPendingDrsScreenCode(DrsConstants.PENDING_DRS_SCREEN_CODE);
	}
	
	/**
	 * setStandardTypeDetails : To set Standard type details in the Dropdowns
	 * @param request
	 */
	public void setHeaderStandardTypeDetails(HttpServletRequest request) {
		Map<String, String> drsForMap;
		Map<String, String> ypDrsMap;
		deliveryCommonService = getCommonServiceForDelivery();
		HttpSession session=request.getSession(false);
		/** Preparing constants from standard types For DRS-FOR */
		drsForMap = (Map<String,String> )session.getAttribute(DrsCommonConstants.DRS_FOR_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(drsForMap)) {
				drsForMap =deliveryCommonService.getDrsForStdTypes();
						
			session.setAttribute(DrsCommonConstants.DRS_FOR_REQ_PARAMS, drsForMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails:: DRS-FOR Dropdown ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(drsForMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.DRS_FOR_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails :: DRS-FOR Dropdown Details Does not exist");
		}
		request.setAttribute(DrsCommonConstants.DRS_FOR_REQ_PARAMS, drsForMap);
		
		
		/** Preparing constants from standard types For YP-DRS */
		ypDrsMap = (Map<String,String> )session.getAttribute(DrsCommonConstants.YP_DRS_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(ypDrsMap)) {
				ypDrsMap= deliveryCommonService.getYpDrsStdTypes();
			session.setAttribute(DrsCommonConstants.YP_DRS_REQ_PARAMS, ypDrsMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails::YP-DRS dropdown details:: EXCEPTION", e);
		}
		request.setAttribute(DrsCommonConstants.YP_DRS_REQ_PARAMS, ypDrsMap);
		if(CGCollectionUtils.isEmpty(ypDrsMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.YP_DRS_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails ::YP-DRS dropdown Details Does not exist");
		}
		
		
		prepareLoadNumbers(request);
	}
	/**
	 * prepareLoadNumbers
	 * @param request
	 * @param session
	 */
	public void prepareLoadNumbers(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Map<Integer, Integer> loadNumberMap;
		/** Preparing constants from standard types For LOAD numbers*/
		loadNumberMap = (Map<Integer,Integer> )session.getAttribute(DrsCommonConstants.LOAD_NUMBER_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(loadNumberMap)) {
				loadNumberMap= deliveryCommonService.getLoadLotForDRS();
			session.setAttribute(DrsCommonConstants.LOAD_NUMBER_REQ_PARAMS, loadNumberMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails::loadNumberMap dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		request.setAttribute(DrsCommonConstants.LOAD_NUMBER_REQ_PARAMS, loadNumberMap);
		if(CGCollectionUtils.isEmpty(loadNumberMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.LOAD_NO_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails ::loadNumberMap dropdown Details Does not exist");
		}
	}
	
	/**
	 * Manual drs drop down.
	 *
	 * @param request the request
	 */
	public void manualDrsDropDown(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Map<String, String> manualDrsTypeDropDown;
		/** manualDrsTypeDropDown*/
		manualDrsTypeDropDown = (Map<String,String> )session.getAttribute(DrsCommonConstants.MANUAL_DRS_TYPE_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(manualDrsTypeDropDown)) {
				manualDrsTypeDropDown= deliveryCommonService.getStandardTypesAsMap(DrsConstants.MF_MANUAL_DRS_TYPE);
			session.setAttribute(DrsCommonConstants.MANUAL_DRS_TYPE_REQ_PARAMS, manualDrsTypeDropDown);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: manualDrsDropDown::ManualDrs type dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		request.setAttribute(DrsCommonConstants.MANUAL_DRS_TYPE_REQ_PARAMS, manualDrsTypeDropDown);
		if(CGCollectionUtils.isEmpty(manualDrsTypeDropDown)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.MANUAL_DRS_TYPE_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: manualDrsDropDown ::ManualDrs type dropdown Details Does not exist");
		}
	}
	/**
	 * prepareConsignmentTypeDetails
	 */
	public void prepareConsignmentTypeDetails(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Map<String, String> consignmentTypeMap;
		/** Preparing constants from standard types For LOAD numbers*/
		consignmentTypeMap = (Map<String,String> )session.getAttribute(DrsCommonConstants.CONSIGNEMNT_TYPE_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(consignmentTypeMap)) {
				consignmentTypeMap= deliveryCommonService.getConsignmentTypeForDelivery();
			session.setAttribute(DrsCommonConstants.CONSIGNEMNT_TYPE_REQ_PARAMS, consignmentTypeMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: prepareConsignmentTypeDetails::Consignment Type dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		request.setAttribute(DrsCommonConstants.CONSIGNEMNT_TYPE_REQ_PARAMS, consignmentTypeMap);
		if(CGCollectionUtils.isEmpty(consignmentTypeMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.CN_TYPE_NO_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: prepareConsignmentTypeDetails ::Consignment Type dropdown Details Does not exist");
		}
	}
	/**
	 * setStandardTypeDetails : To set Standard type details in the Dropdowns
	 * @param request
	 */
	public void setGridStandardTypeDetails(HttpServletRequest request) {
		Map<String, String> deliveryType;
		Map<String, String> drsSealSign;
		deliveryCommonService = getCommonServiceForDelivery();
		deliveryType = prepareDeliverytypeDetails(request);
		request.setAttribute(DrsCommonConstants.DELIVERY_TYPE_REQ_PARAMS, deliveryType);
		/** Preparing constants from standard types For DRS-SEAL-SIGN */
		drsSealSign = prepareDrsSealSignDropdownDtls(request);
		request.setAttribute(DrsCommonConstants.DRS_SEAL_REQ_PARAMS, drsSealSign);
	}
	
	/**
	 * Prepare drs seal sign dropdown dtls.
	 *
	 * @param request the request
	 * @param session the sessiono
	 * @return the map
	 */
	public Map<String, String> prepareDrsSealSignDropdownDtls(
			HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Map<String, String> drsSealSign;
		drsSealSign = (Map<String,String> )session.getAttribute(DrsCommonConstants.DRS_SEAL_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(drsSealSign)) {
				drsSealSign= deliveryCommonService.getSealAndSignStdTypes();
			session.setAttribute(DrsCommonConstants.DRS_SEAL_REQ_PARAMS, drsSealSign);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails::DRS-SEAL-SIGN dropdown details:: EXCEPTION", e);
		}
		
		if(CGCollectionUtils.isEmpty(drsSealSign)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.DRS_SEAL_SIGN));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails ::DRS-SEAL-SIGN dropdown Details Does not exist");
		}
		return drsSealSign;
	}
	public Map<String, String> prepareBulkDrsLoadTypeDropdownDtls(
			HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Map<String, String> drsTypeDropdownMap=null;
		drsTypeDropdownMap = (Map<String,String> )session.getAttribute(DrsCommonConstants.DRS_LOAD_TYPE_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(drsTypeDropdownMap)) {
				drsTypeDropdownMap= deliveryCommonService.getStandardTypesAsMap(DrsConstants.DRS_PENDING_BULK_LOAD_TYPE);
			session.setAttribute(DrsCommonConstants.DRS_LOAD_TYPE_REQ_PARAMS, drsTypeDropdownMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: prepareBulkDrsLoadTypeDropdownDtls:: EXCEPTION", e);
		}
		
		if(CGCollectionUtils.isEmpty(drsTypeDropdownMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.DRS_BULK_LOAD_TYPE));
			LOGGER.warn("AbstractDeliveryAction:: prepareBulkDrsLoadTypeDropdownDtls  Does not exist");
		}else{
			request.setAttribute(DrsCommonConstants.DRS_LOAD_TYPE_REQ_PARAMS, drsTypeDropdownMap);
		}
		return drsTypeDropdownMap;
	}
	
	public Map<String, String> prepareDeliverytypeDetails(
			HttpServletRequest request) {
		Map<String, String> deliveryType;
		HttpSession session=request.getSession(false);
		/** Preparing constants from standard types For Delivery Type */
		deliveryType = (Map<String,String> )session.getAttribute(DrsCommonConstants.DELIVERY_TYPE_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(deliveryType)) {
				deliveryType =deliveryCommonService.getDeliveryTypeStdTypes();
						
			session.setAttribute(DrsCommonConstants.DELIVERY_TYPE_REQ_PARAMS, deliveryType);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails:: Delivery Type Dropdown ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(deliveryType)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.DLV_TYPE_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails :: Delivery Type Dropdown Details Does not exist");
		}
		return deliveryType;
	}
	
	/**
	 * Name : ajaxGetPartyTypeDetailsForDRS
	 * purpose : Ajax call to populate Employee/DA Code (in the DRS Preparation Screen )
	 * Input : no input required
	 * return : return map<Integer,String> as String.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxGetPartyTypeDetailsForDRS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		Map<Integer,String> drsForPartyType=null;
		HttpSession session=request.getSession(false);	
		String drsForType = request.getParameter(DrsCommonConstants.DRS_FOR_TYPE);
		deliveryCommonService = getCommonServiceForDelivery();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			drsForPartyType = (Map<Integer,String>)session.getAttribute(drsForType);

			if(CollectionUtils.isEmpty(drsForPartyType)) {
				drsForPartyType = deliveryCommonService.getPartyTypeDetailsForDRS(drsForType, getLoginOfficeTO(request));
				session.setAttribute(drsForType, drsForPartyType);
			}

		}catch (CGSystemException e) {
			LOGGER.error("AbstractDeliveryAction::ajaxGetPartyTypeDetailsForDRS ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractDeliveryAction:: ajaxGetPartyTypeDetailsForDRS", e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request, e));
		}catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction::ajaxGetPartyTypeDetailsForDRS ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		
		finally {
			if(CollectionUtils.isEmpty(drsForPartyType)){
				out.print(jsonResult);
			}else{
				out.print(drsForPartyType.toString());
			}

			out.flush();
			out.close();
		}

	}
	
	
	
	/**
	 * Ajax validate consignment.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxValidateConsignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		final AbstractDeliveryTO drsToa =getDRSTOFromForm(form);
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String consgNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_CONSGNUMBER);
		DeliveryConsignmentTO dlvCnTo=null;
		try {
			out=response.getWriter();
			drsToa.setConsignmentNumber(consgNumber);
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			deliveryCommonService = getCommonServiceForDelivery();
			dlvCnTo=deliveryCommonService.validateConsignmentDetails(drsToa);
			if(!StringUtil.isNull(dlvCnTo)){
				jsonResult = serializer.toJSON(dlvCnTo).toString();
			}
			

		}catch(CGBusinessException e){
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignment ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			
		}catch(CGSystemException e){
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignment ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
			
		}catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: ajaxValidateConsignment", e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		
		finally {
			
				out.print(jsonResult);

			out.flush();
			out.close();
		}

	}
	/**
	 * ajaxGetNonDeliveryReasons :: get the Non Delivery Reaons From Reason Master
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	public void ajaxGetNonDeliveryReasons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		Map<Integer,String> nonDlvReasons=null;
			
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			LOGGER.debug("AbstractDeliveryAction:: ajaxGetNonDeliveryReasons::START");
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			nonDlvReasons = getPendingReasonsForDRS(request);

		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: ajaxGetNonDeliveryReasons", e);
		}
		finally {
			if(CollectionUtils.isEmpty(nonDlvReasons)){
				out.print(jsonResult);
			}else{
				out.print(nonDlvReasons.toString());
			}
			out.flush();
			out.close();
			LOGGER.debug("AbstractDeliveryAction:: ajaxGetNonDeliveryReasons::END");
		}

	}
	/**
	 * @param request
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Map<Integer, String> getPendingReasonsForDRS(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		Map<Integer, String> nonDlvReasons;
		HttpSession session=request.getSession(false);
		nonDlvReasons = (Map<Integer,String>)session.getAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS);

		if(CollectionUtils.isEmpty(nonDlvReasons)) {
			nonDlvReasons = deliveryCommonService.getNonDeliveryReasons();
			session.setAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS, nonDlvReasons);
			request.setAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS, nonDlvReasons);
		}
		return nonDlvReasons;
	}
	/**
	 * @param request
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Map<Integer, String> getPendingReasonsForBulkDRS(
			HttpServletRequest request) {
		Map<Integer, String> nonDlvReasons;
		HttpSession session=request.getSession(false);
		nonDlvReasons = (Map<Integer,String>)session.getAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS_FOR_BLK);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CollectionUtils.isEmpty(nonDlvReasons)) {
				nonDlvReasons = deliveryCommonService.getNonDeliveryReasonsByType(UdaanCommonConstants.REASON_TYPE_FOR_NON_DELIVERY_FOR_BULK_DRS);
				session.setAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS_FOR_BLK, nonDlvReasons);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: getPendingReasonsForBulkDRS", e);
		}
		
		if(CGCollectionUtils.isEmpty(nonDlvReasons)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.PENDING_REASON_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: getPendingReasonsForBulkDRS ::PendingReasonsForBulkDRS Details Does not exist");
		}else{
			request.setAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS_FOR_BLK, nonDlvReasons);
		}
		return nonDlvReasons;
	}
	
	
	/**
	 * Gets the dRSTO from form.
	 *
	 * @param form the form
	 * @return the dRSTO from form
	 */
	private AbstractDeliveryTO getDRSTOFromForm(ActionForm form){
		AbstractDeliveryTO drsTO=null;
		if(form instanceof NormalPriorityDrsForm){
			NormalPriorityDrsForm npFom=(NormalPriorityDrsForm)form;
			drsTO= (AbstractDeliveryTO)npFom.getTo();
		}else if(form instanceof CreditCardDrsForm){
			CreditCardDrsForm npFom=(CreditCardDrsForm)form;
			drsTO= (AbstractDeliveryTO)npFom.getTo();
		}else if(form instanceof CodLcToPayDrsForm){
			CodLcToPayDrsForm codLcFom=(CodLcToPayDrsForm)form;
			drsTO= (AbstractDeliveryTO)codLcFom.getTo();
		}else if(form instanceof PendingDrsForm){
			PendingDrsForm pendingForm=(PendingDrsForm)form;
			drsTO= (AbstractDeliveryTO)pendingForm.getTo();
		}else if(form instanceof ManualDrsForm){
			ManualDrsForm manualDrsForm=(ManualDrsForm)form;
			drsTO= (AbstractDeliveryTO)manualDrsForm.getTo();
		}else if(form instanceof BulkCnPendingDrsForm ){
			BulkCnPendingDrsForm bulkDrsForm=(BulkCnPendingDrsForm)form;
			drsTO= (AbstractDeliveryTO)bulkDrsForm.getTo();
		}
		return drsTO;
	}
	
	
	/**
	 * Sets the relation dtls.
	 *
	 * @param request the new relation dtls
	 */
	public void setRelationDtls(HttpServletRequest request) {
		
		Map<Integer, String> relations;
		relations = getRelationDropDown(request);
		request.setAttribute(DrsCommonConstants.RELATIONS_REQ_PARAMS, relations);
		if(CGCollectionUtils.isEmpty(relations)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.RELATIONS_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setRelationDtls ::relations dropdown Details Does not exist");
		}
	}
	/**
	 * @param request
	 * @return
	 */
	public Map<Integer, String> getRelationDropDown(HttpServletRequest request) {
		Map<Integer, String> relations;
		HttpSession session=request.getSession(false);
		deliveryCommonService = getCommonServiceForDelivery();
		/** Preparing Relations from Master table*/
		relations = (Map<Integer,String> )session.getAttribute(DrsCommonConstants.RELATIONS_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(relations)) {
				relations= deliveryCommonService.getAllRelationsForDelivery();
			session.setAttribute(DrsCommonConstants.RELATIONS_REQ_PARAMS, relations);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setRelationDtls ::relations dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		return relations;
	}
	
	/**
	 * Sets the id proof dtls.
	 *
	 * @param request the new id proof dtls
	 */
	public void setIdProofDtls(HttpServletRequest request) {
		
		Map<Integer, String> relations;
		
		relations = getIdentityProofDropDown(request);
		request.setAttribute(DrsCommonConstants.ID_PROOF_REQ_PARAMS, relations);
		if(CGCollectionUtils.isEmpty(relations)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.ID_PROOF_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setIdProofDtls :: dropdown Details Does not exist");
		}
	}
	/**
	 * @param request
	 * @return
	 */
	public Map<Integer, String> getIdentityProofDropDown(
			HttpServletRequest request) {
		Map<Integer, String> idProofMap;
		HttpSession session=request.getSession(false);
		/** Preparing Identity proofs from Master table*/
		idProofMap = (Map<Integer,String> )session.getAttribute(DrsCommonConstants.ID_PROOF_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(idProofMap)) {
				idProofMap= deliveryCommonService.getAllIdProofsForDelivery();
			session.setAttribute(DrsCommonConstants.ID_PROOF_REQ_PARAMS, idProofMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setIdProofDtls :: dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		return idProofMap;
	}
	/**
	 * Sets the Mode of Payment.
	 *
	 * @param request the new mode of payment details
	 */
	public void setModeOfPaymentDtls(HttpServletRequest request) {
		
		Map<String, String> modeOfPaymentDetails;
		modeOfPaymentDetails = prepareModeOfPayment(request);
		request.setAttribute(DrsCommonConstants.MODE_OF_PAYMENT_REQ_PARAMS, modeOfPaymentDetails);
	}
	
	/**
	 * Prepare mode of payment.
	 *
	 * @param request the request
	 * @return the map
	 */
	public Map<String, String> prepareModeOfPayment(HttpServletRequest request) {
		Map<String, String> modeOfPaymentDetails;
		HttpSession session=request.getSession(false);
		/** Preparing Identity proofs from Master table*/
		modeOfPaymentDetails = (Map<String,String> )session.getAttribute(DrsCommonConstants.MODE_OF_PAYMENT_REQ_PARAMS);
		deliveryCommonService = getCommonServiceForDelivery();
		try {
			if(CGCollectionUtils.isEmpty(modeOfPaymentDetails)) {
				modeOfPaymentDetails= (Map<String, String>) deliveryCommonService.getDrsPaymentTypeDtls();
			session.setAttribute(DrsCommonConstants.MODE_OF_PAYMENT_REQ_PARAMS, modeOfPaymentDetails);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setModeOfPaymentDtls :: dropdown details:: EXCEPTION", e.getLocalizedMessage());
		}
		if(CGCollectionUtils.isEmpty(modeOfPaymentDetails)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.MODE_OF_DETAILS_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setModeOfPaymentDtls :: dropdown Details Does not exist");
		}
		return modeOfPaymentDetails;
	}
	/**
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 */
	public Integer getMaxRowForDrs(final HttpServletRequest request,String screenName)
			throws NumberFormatException {
		Integer maxRows=null;
		try {
			String number=getConfigParamValue(request,screenName);
			if(!StringUtil.isStringEmpty(number)){
			maxRows= Integer.parseInt(number);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: getMaxRowForDrs :: Parsing Exception:: EXCEPTION", e);
		}
		return maxRows;
	}
	
	/**
	 * ajaxValidateDrsNumber :: to validate whether drs number already exist or not(it's for Manual Drs Functionality)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void ajaxValidateDrsNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		final AbstractDeliveryTO drsTo =getDRSTOFromForm(form); 
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		String status=null;
		try {
			out=response.getWriter();
			if(!StringUtil.isStringEmpty(drsNumber)){
			drsTo.setDrsNumber(drsNumber);
			}
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			deliveryCommonService = getCommonServiceForDelivery();
			status=deliveryCommonService.getDrsStatusByDrsNumber(drsTo);
			jsonResult = prepareCommonException(DrsConstants.SUCCESS_FLAG,status);
		}catch(CGBusinessException e){
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignment ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			
		}catch(CGSystemException e){
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignment ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
			
		}catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: ajaxValidateConsignment", e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
	
	
	/**
	 * Ajax validate consignment by drs number for manual drs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxValidateConsignmentByDrsNumberForManualDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request,final HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		final AbstractDeliveryTO drsToa =getDRSTOFromForm(form);
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		final String consgNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_CONSGNUMBER);
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		DeliveryConsignmentTO dlvCnTo=null;
		try {
			out=response.getWriter();
			drsToa.setConsignmentNumber(consgNumber);
			drsToa.setDrsNumber(drsNumber);
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			manualDrsService = getManualDrsService();
			dlvCnTo=manualDrsService.validateConsignmentDetails(drsToa);
			if(!StringUtil.isNull(dlvCnTo)){
				jsonResult = serializer.toJSON(dlvCnTo).toString();
			}
		}catch(CGBusinessException e){
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignmentByDrsNumber ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("AbstractDeliveryAction::ajaxValidateConsignmentByDrsNumber ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: ajaxValidateConsignmentByDrsNumber", e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		
		finally {
				out.print(jsonResult);
				out.flush();
				out.close();
		}

	}
	/**
	 * performTwoWayWrite
	 * @param drsTo
	 */
	public void performTwoWayWrite(AbstractDeliveryTO drsTo){
		try {
			deliveryCommonService=getCommonServiceForDelivery();
			deliveryCommonService.twoWayWriteForDRS(drsTo);
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: performTwoWayWrite", e);
		}
		}
	public void setAutoGeneratedMDrsNumber(final AbstractDeliveryTO drsTo,
			final HttpServletRequest request) {
		if(StringUtil.isEmptyLong(drsTo.getDeliveryId())){
			try {
				drsTo.setAutoGeneratedManualDrsNumber(DrsConverterUtil.generateManualDrsNumber(drsTo, getCommonServiceForDelivery()));
			} catch (CGBusinessException e) {
				getBusinessError(request, e);
			}
		}else{
			drsTo.setAutoGeneratedManualDrsNumber(null);
		}
	}
	
	
}


/**
 *
 *end of Changes by <31913> on 10/04/2013
 */
