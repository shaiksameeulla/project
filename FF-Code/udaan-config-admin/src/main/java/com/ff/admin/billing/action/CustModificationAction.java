package com.ff.admin.billing.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.form.CustModificationForm;
import com.ff.admin.billing.service.CnModificationCommonService;
import com.ff.admin.billing.service.CustModificationService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.billing.CustModificationTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.global.service.GlobalUniversalService;

public class CustModificationAction extends AbstractBillingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CustModificationAction.class);
	public transient JSONSerializer serializer;
	private CustModificationService custModificationService;
	private CnModificationCommonService cnModificationCommonService;
	private GlobalUniversalService globalUniversalService;
	
	public CustModificationService getCustModificationService(){
		if(StringUtil.isNull(custModificationService))
			custModificationService = (CustModificationService)getBean(AdminSpringConstants.CUST_MODIFICATION_SERVICE);
		return custModificationService;
	}
	public CnModificationCommonService getCnModificationCommonService(){
		if(StringUtil.isNull(cnModificationCommonService))
			cnModificationCommonService = (CnModificationCommonService)getBean(AdminSpringConstants.CN_MODIFICATION_COMMON_SERVICE);
		return cnModificationCommonService;
	}
	public GlobalUniversalService getGlobalUniversalService(){
		if(StringUtil.isNull(globalUniversalService))
			globalUniversalService = (GlobalUniversalService) getBean("globalUniversalService");
		return globalUniversalService;
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("CustModificationAction :: preparePage::START----->");
		String forwardString = null;
		//ActionMessage actionMessage =  null;
		try {
			//Somesh Comments: Both RHO and CO users should be allowed.
			//String loginOfficeTypeCode = getLoggedInOfficeType(request);
			/*if(StringUtils.equalsIgnoreCase(loginOfficeTypeCode, CommonConstants.OFF_TYPE_CORP_OFFICE)
					|| StringUtils.equalsIgnoreCase(loginOfficeTypeCode, CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){*/
				CustModificationTO custModificationTO = new CustModificationTO();
				custModificationTO.setCurrentDate(DateUtil.todaySystemDate());
				List<ConsignmentTypeTO> consgTypes = getCustModificationService().getConsignmentType();
				((CustModificationForm) form).setTo(custModificationTO);
				String backDateToBook = getGlobalUniversalService().getConfigParamValueByName(CommonConstants.CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED);
				request.setAttribute(CommonConstants.CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED, backDateToBook);
				request.setAttribute(BillingConstants.CONSIGNMENT_TYPES, consgTypes);
				request.setAttribute("CONSIGNMENT_TYPE_PARCEL_CODE", CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE);
				
				forwardString = BillingConstants.CUST_MODIFICATION_SUCCESS;
			/*}else{
				forwardString = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(BillingConstants.CN_MODIFICATION_ALLOWED_AT_CORPORATE_RHO_OFFICE);
				prepareActionMessage(request, actionMessage);
			}		*/	
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : CustModificationAction :: preparePage() :: ", e);
			getSystemException(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CustModificationAction :: preparePage() :: ", e);
			getBusinessError(request, e);
		}
		
		LOGGER.debug("CustModificationAction :: preparePage()::END");
		return mapping.findForward(forwardString);
	}

	@SuppressWarnings("static-access")
	public ActionForward getConsignmentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("CustModificationAction :: getConsignmentDetails ---> START :: ");
		
		String exception = null;
		PrintWriter out = null;
		String cnModifyJSON = CommonConstants.EMPTY_STRING;
		CustModificationTO custModificationTO = new CustModificationTO();
		try {
			out = response.getWriter();
			custModificationService = getCustModificationService();
			String consgNo = request.getParameter("ConsignmentNo");
			custModificationTO = custModificationService.getConsignmentDetails(consgNo);		
			
			logger.append(" CONSIGNMENT NO :: "+ consgNo);
			LOGGER.debug(logger.toString());
			
			//Excess consignment - city dropdown
			if(StringUtils.equalsIgnoreCase(custModificationTO.getIsExcessConsg(), CommonConstants.YES)){
				List<CityTO> cityList = custModificationService.getCitysByRegion(custModificationTO.getBkgRegionTO().getRegionId());
				custModificationTO.setCityList(cityList);				
			}else{
				List<CustomerTO> customerTOs = custModificationService.getCustomerDetails(custModificationTO.getCnOrgOffice(), custModificationTO.getCityId());
				custModificationTO.setCustomerTOs(customerTOs);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CustModificationAction :: getConsignmentDetails() ::"+e);
			exception = getBusinessErrorFromWrapper(request, e);
			custModificationTO.setErrorMessage(exception);
		}catch (CGSystemException e) {
			LOGGER.error("CustModificationAction :: getConsignmentDetails() ::"+e);
			exception = getSystemExceptionMessage(request,e);
			custModificationTO.setErrorMessage(exception);
		} catch(Exception e){
			LOGGER.error("CustModificationAction :: getConsignmentDetails() ::"+e);
			exception = getGenericExceptionMessage(request,e);
			custModificationTO.setErrorMessage(exception);
		} finally {			
			if (!StringUtil.isNull(custModificationTO)) {
				cnModifyJSON = serializer.toJSON(custModificationTO).toString();
			}
			out.print(cnModifyJSON);
			out.flush();
			out.close();
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("CustModificationAction::getConsignmentDetails ---> END ::" 
				+ " Time Consumed in HH:MM:SS::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return mapping.findForward(BillingConstants.CUST_MODIFICATION_SUCCESS);
	}

	
	
	public void getCustomerListByCityAndBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("CustModificationAction :: getCustomerListByCityAndBranch::START----->");
		String custmerJson = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");
			String office = request.getParameter("officeId");
			Integer cityId = Integer.parseInt(city);
			Integer officeId = Integer.parseInt(office);
			List<CustomerTO> customerTOList = getCustModificationService().getCustomerDetails(officeId,cityId);
			if (!CGCollectionUtils.isEmpty(customerTOList)){
				custmerJson = JSONSerializer.toJSON(customerTOList).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CustModificationAction :: getCustomerListByCityAndBranch :: --->"+ e.getLocalizedMessage());
			custmerJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("CustModificationAction :: getCustomerListByCityAndBranch :: --->"+ e.getMessage());
			custmerJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("CustModificationAction :: getCustomerListByCityAndBranch :: --->"+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			custmerJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(custmerJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("CustModificationAction :: getCustomerListByCityAndBranch :: END ----->");
	}
	public void saveOrUpdateCustModification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CustModificationAction :: saveOrUpdateCustModification :: START----->");
		String jsonResult = null;
		PrintWriter out = null;
		CustModificationTO custModificationTO = new CustModificationTO();
		boolean result = Boolean.FALSE;
		try {
			custModificationService = getCustModificationService();
			out = response.getWriter();
			CustModificationForm custModificationForm = (CustModificationForm) form;
			custModificationTO = (CustModificationTO) custModificationForm.getTo();
			custModificationTO.setScreenName("SNGL");
			
			//validate customer code and product code
			ProductToBeValidatedInputTO productToBeValidatedInputTO = getCnModificationCommonService().setProductContractInputTO(custModificationTO);
			RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
			RateCalculationService rateService = serviceFactory.getService(productToBeValidatedInputTO.getRateType());
			boolean isValidProduct = rateService.isProductValidForContract(productToBeValidatedInputTO);
			
			if(!isValidProduct){
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request, BillingConstants.PROD_NOT_CONTRACT_BY_CUST, null));
				return ;
			}
			ConsignmentTO consignmentTO = prepareConsignmentTO(request, custModificationTO);
			result = getCnModificationCommonService().saveOrUpdateConsignmentModification(consignmentTO, custModificationTO);

			if(result){
				jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, getMessageFromErrorBundle(request,
						BillingConstants.CN_MODIFICATION_SAVED, null));
			}  else {
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getMessageFromErrorBundle(request,
						BillingConstants.CN_MODIFICATION_FAILED, null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CustModificationAction :: saveOrUpdateCustModification() ::"+e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("CustModificationAction :: saveOrUpdateCustModification() :: "+e);
			String exception = getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("CustModificationAction :: saveOrUpdateCustModification() :: "+e);
			String exception = getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CustModificationAction :: saveOrUpdateCustModification :: END----->");	
	}
	private ConsignmentTO prepareConsignmentTO(HttpServletRequest request, CustModificationTO custModificationTO) {
		LOGGER.debug("CustModificationAction :: prepareConsignmentTO :: START----->");
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		
		ConsignmentTO consignmentTO = new ConsignmentTO();
		if(!StringUtil.isNull(custModificationTO.getCongNo())){
			consignmentTO.setConsgNo(custModificationTO.getCongNo());
		}			
		if(!StringUtil.isNull(custModificationTO.getNewCustTO())){
			consignmentTO.setCustomerTO(custModificationTO.getNewCustTO());
		}
		consignmentTO.setFinalWeight(custModificationTO.getNewCnFinalWeight());
		consignmentTO.setActualWeight(custModificationTO.getNewCnFinalWeight());
		consignmentTO.setTypeTO(custModificationTO.getCnTypeTO());
		consignmentTO.setConsgTypeId(custModificationTO.getCnTypeTO().getConsignmentId());
		consignmentTO.setDeclaredValue(custModificationTO.getNewDeclaredValue());
		consignmentTO.setIsExcessConsg(custModificationTO.getIsExcessConsg());	

		if(StringUtils.equalsIgnoreCase(custModificationTO.getIsExcessConsg(), CommonConstants.YES)){
			Date bookingDate = DateUtil.getTimeStampFromDateSlashFormatString(custModificationTO.getExBookingDate());
			consignmentTO.setBookingDate(bookingDate);			
			consignmentTO.setOrgOffId(custModificationTO.getExOffice());
			consignmentTO.setOperatingOffice(custModificationTO.getExOffice());
		}		
		if(!StringUtil.isEmptyInteger(userInfoTO.getUserto().getUserId())){
			consignmentTO.setCreatedBy(userInfoTO.getUserto().getUserId());
		}
		consignmentTO.setCreatedDate(Calendar.getInstance().getTime());

		LOGGER.debug("CustModificationAction :: prepareConsignmentTO :: END----->");
		return consignmentTO;
	}
}