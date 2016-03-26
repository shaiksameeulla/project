package com.ff.rate.configuration.ratequotation.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.StateTO;
import com.ff.jobservices.JobServicesTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.rate.configuration.ratequotation.form.RateQuotationForm;
import com.ff.rate.configuration.ratequotation.service.RateQuotationService;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.OctroiChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationCODChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.universe.jobservice.service.JobServicesUniversalService;

/**
 * @author rmaladi
 * 
 */
public class RateQuotationAction extends AbstractRateAction {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RateQuotationAction.class);

	private RateQuotationService rateQuotationService;

	/**
	 * View Form Details
	 * 
	 * @inputparam
	 * @return Populate the screen with defalut values
	 * @author Rohini Maladi
	 */

	public ActionForward viewRateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
			LOGGER.trace("RateQuotationAction::viewRateQuotation::START------------>:::::::");
			RateQuotationTO rateQuotationTO = new RateQuotationTO();
			ActionMessage actionMessage=null;
			rateQuotationTO.setRateQuotationType(RateQuotationConstants.NORMAL);
			getDefaultUIValues(request, rateQuotationTO);
			getCodChagreValue(request, rateQuotationTO,"N", "normalCODList");
			String rateQuotationType = RateQuotationConstants.NORMAL;
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_Q);
			String salesType = request.getParameter(RateQuotationConstants.RATE_QUOTAION_SALES);
			request.setAttribute(RateQuotationConstants.RATE_QUOT_SALES_TYPE, salesType);
			if (StringUtil.isStringEmpty(salesType) || (!salesType.equalsIgnoreCase(RateQuotationConstants.CHAR_E)
					&& !salesType.equalsIgnoreCase(RateQuotationConstants.CHAR_C))) {
				rateQuotationTO.setErrorMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.URL_IS_INVALID, null));
				actionMessage = new ActionMessage(
						RateErrorConstants.URL_IS_INVALID);
			}
			prepareActionMessage(request, actionMessage);
			((RateQuotationForm) form).setTo(rateQuotationTO);

		
		LOGGER.trace("RateQuotationAction::viewRateQuotation::END------------>:::::::");

		return mapping.findForward(RateQuotationConstants.NORMAL_QUOTATION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewEcommerceRateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateQuotationAction::viewEcommerceRateQuotation::START------------>:::::::");
		// RateQuotationForm rateQuatForm = new RateQuotationForm();
		ActionMessage actionMessage=null;
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
			rateQuotationTO
					.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
			getDefaultUIValues(request, rateQuotationTO);
			getCodChagreValue(request, rateQuotationTO,"E", "ecommerceCODList");
			String rateQuotationType = RateQuotationConstants.ECOMMERCE;
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_Q);
			String salesType = request.getParameter(RateQuotationConstants.RATE_QUOTAION_SALES);
			request.setAttribute(RateQuotationConstants.RATE_QUOT_SALES_TYPE, salesType);
			

			if (StringUtil.isStringEmpty(salesType) || (!salesType.equalsIgnoreCase(RateQuotationConstants.CHAR_E)
					&& !salesType.equalsIgnoreCase(RateQuotationConstants.CHAR_C))) {
				rateQuotationTO.setErrorMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.URL_IS_INVALID, null));
				actionMessage = new ActionMessage(
						RateErrorConstants.URL_IS_INVALID);

			}
			prepareActionMessage(request, actionMessage);
			((RateQuotationForm) form).setTo(rateQuotationTO);

		
			LOGGER.trace("RateQuotationAction::viewEcommerceRateQuotation::END------------>:::::::");
	
			return mapping.findForward(RateQuotationConstants.ECOMMERCE_QUOTATION);
	}

	/**
	 * @param request
	 * @param rateQuotationTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void getCodChagreValue(HttpServletRequest request,
			RateQuotationTO rateQuotationTO, String configuredType, String codType){
		LOGGER.trace("RateQuotationAction::getCodChagreValue::START------------>:::::::");
		ActionMessage actionMessage = null;
		try{
		
		List<CodChargeTO> codChargeTO = getDeclaredValueCodCharge(request, configuredType, codType);
		if (!CGCollectionUtils.isEmpty(codChargeTO)) {
			request.setAttribute(RateQuotationConstants.COD_CHARGE_TO,
					codChargeTO);
		} else {
			rateQuotationTO
			.setErrorMsg(getMessageFromErrorBundle(
					request,
					RateErrorConstants.COD_CHARGE_DTLS_NOT_EXIST,
					null));
			actionMessage = new ActionMessage(
					RateErrorConstants.COD_CHARGE_DTLS_NOT_EXIST);

			LOGGER.warn("RateQuotationAction:: getCodChagreValue ::COD Charge Details Does not exist");
		}
		}catch(CGBusinessException e){
			actionMessage = new ActionMessage(
					RateErrorConstants.COD_CHARGE_DTLS_NOT_EXIST);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getCodChagreValue of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getCodChagreValue of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::getCodChagreValue::END------------>:::::::");
	}

	/**
	 * Load the default values into TO
	 * 
	 * @param request
	 * @param rateQuotationTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	protected void getDefaultUIValues(HttpServletRequest request,
			RateQuotationTO rateQuotationTO){
		ActionMessage actionMessage = null;
		Integer loggedInRHO = null;
		String loggedInRHOName = null;
		String loggedInRHOCode = null;
		String loginRegionName = null;
		Integer loginCityId = null;
		String loginOfficeCode = null;
		String office = null;
		Integer loggedInOfficeId = null;
		String loginOfficeName = null;
		try {
			LOGGER.trace("RateQuotationAction::getDefaultUIValues::START------------>:::::::");
			String salesType = request.getParameter(RateQuotationConstants.RATE_QUOTAION_SALES);
			request.setAttribute(RateQuotationConstants.RATE_QUOT_SALES_TYPE, salesType);
			
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			rateQuotationService = getRateQuotationService();
			String createdDate = DateUtil.getCurrentDateInYYYYMMDDHHMM();
			rateQuotationTO.setCreatedDate(createdDate);
			rateQuotationTO.setStatus(RateQuotationConstants.NEW);
			request.setAttribute(RateQuotationConstants.CREATED_DATE, createdDate);
			userInfoTO = (UserInfoTO) session
					.getAttribute(RateQuotationConstants.USER_INFO);
			if(!StringUtil.isNull(userInfoTO) && !StringUtil.isNull(userInfoTO.getUserto()) && !StringUtil.isNull(userInfoTO.getUserto().getUserId())){
				Integer userId = userInfoTO.getUserto().getUserId();
				request.setAttribute(RateQuotationConstants.CREATED_BY, userId);
				if(!StringUtil.isNull(userInfoTO) && !StringUtil.isNull(userInfoTO.getOfficeTo()) && !StringUtil.isNull(userInfoTO.getOfficeTo().getOfficeId())){
					OfficeTO officeTO = userInfoTO.getOfficeTo();
					loggedInOfficeId = officeTO.getOfficeId();
					loginOfficeName = officeTO.getOfficeName();
					if(!officeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					if(!StringUtil.isNull(officeTO.getReportingRHO())){
						loggedInRHO = officeTO.getReportingRHO();
						OfficeTO ofcTo = new OfficeTO();
						ofcTo = rateQuotationService.getOfficeDetails(loggedInRHO);
						loggedInRHOName = ofcTo.getOfficeName();
						loggedInRHOCode = ofcTo.getOfficeCode();
						loginRegionName = loggedInRHOCode + CommonConstants.HYPHEN
									+ loggedInRHOName;
						if(!StringUtil.isNull(ofcTo.getRegionTO())){
							 ofcTo.getRegionTO().getRegionId();
						}
					}
					}else{
						loggedInRHOName = officeTO.getOfficeName();
						 loggedInRHOCode = officeTO.getOfficeCode();
						 loginRegionName = loggedInRHOCode + CommonConstants.HYPHEN
									+ loggedInRHOName;
						if(!StringUtil.isNull(officeTO.getRegionTO())){
							officeTO.getRegionTO().getRegionId();
						}
					}	
					
			 loginCityId = officeTO.getCityId();
			 loginOfficeCode = officeTO.getOfficeCode();
			 office = loginOfficeCode + CommonConstants.HYPHEN
					+ loginOfficeName;

			
			request.setAttribute(RateQuotationConstants.OFFICE, office);
			request.setAttribute(RateQuotationConstants.LOGGED_IN_OFFICE_ID, loggedInOfficeId);
			request.setAttribute(RateQuotationConstants.LOGIN_REGION_NAME, loginRegionName);
			request.setAttribute(RateQuotationConstants.LOGIN_OFFICE_CODE, loginOfficeCode);
			request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
			request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);

			request.setAttribute(RateQuotationConstants.LOGIN_CITY_ID, loginCityId);
			CityTO loginCityTO = new CityTO();
			if(!StringUtil.isEmptyInteger(loginCityId)){
				loginCityTO.setCityId(loginCityId);
				loginCityTO = rateQuotationService.getCity(loginCityTO);
				String stateId = loginCityTO.getState().toString();
				request.setAttribute(RateQuotationConstants.STATE_ID, stateId);
			}
			//CityTO cityTO = new CityTO();
			if((!StringUtil.isStringEmpty(salesType)) && salesType.equals("C")){
				if(officeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					List<OfficeTO> rhoOfcList = getAllRegionalOffices(request);
					if(!CGCollectionUtils.isEmpty(rhoOfcList)){
						request.setAttribute(RateQuotationConstants.QUOT_REGION_LIST, rhoOfcList);
					}
				}
				else{
					
					List<CityTO> rhoCity = null;
					
					if(officeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
						rhoCity = getAllCities(officeTO.getOfficeId());
					}
					else if(!StringUtil.isEmptyInteger(officeTO.getReportingRHO())){
						rhoCity = getAllCities(officeTO.getReportingRHO());				
					}
					
						if (!CGCollectionUtils.isEmpty(rhoCity)) {
							request.setAttribute(RateQuotationConstants.CITY_TOS, rhoCity);
						}else {
							rateQuotationTO
							.setErrorMsg(getMessageFromErrorBundle(
									request,
									RateErrorConstants.CITY_DTLS_NOT_EXIST,
									null));
							actionMessage = new ActionMessage(
									RateErrorConstants.CITY_DTLS_NOT_EXIST);
						LOGGER.warn("RateQuotationAction:: getDefaultUIValues :: city Details Does not exist");
						}
				}
			}
			//cityTO.setCityId(loginCityId);
			if(officeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
				rateQuotationTO.setEmpOfcType(CommonConstants.OFF_TYPE_CORP_OFFICE);
			}

			if((StringUtil.isStringEmpty(salesType)) || salesType.equals("E")){
			//cityTO = rateQuotationService.getCity(cityTO);
			if (!StringUtil.isNull(loginCityTO)) {
			String cityName = loginCityTO.getCityName();
			request.setAttribute(RateQuotationConstants.CITY_NAME, cityName);
			} else {
				rateQuotationTO
				.setErrorMsg(getMessageFromErrorBundle(
						request,
						RateErrorConstants.CITY_DTLS_NOT_EXIST,
						null));
				actionMessage = new ActionMessage(
						RateErrorConstants.CITY_DTLS_NOT_EXIST);
				LOGGER.warn("RateQuotationAction:: getDefaultUIValues :: city Details Does not exist");
			}

		
			EmployeeUserTO employeeUserTO = rateQuotationService
					.getEmployeeUser(userId);
			if (!StringUtil.isNull(employeeUserTO)) {
			String employeeName = employeeUserTO.getEmpTO().getFirstName()
					+ CommonConstants.HYPHEN
					+ employeeUserTO.getEmpTO().getLastName();
			request.setAttribute("employeeName", employeeName);
			Integer employeeId = employeeUserTO.getEmpTO().getEmployeeId();
			request.setAttribute("employeeId", employeeId);
			}else {
				rateQuotationTO
				.setErrorMsg(getMessageFromErrorBundle(
						request,
						RateErrorConstants.EMPLOYEE_DTLS_NOT_EXIST,
						null));
				
				actionMessage = new ActionMessage(
						RateErrorConstants.EMPLOYEE_DTLS_NOT_EXIST);
				LOGGER.warn("RateQuotationAction:: getDefaultUIValues :: Employee Details Does not exist");
			}
			}

			BookingTypeConfigTO configTO=getBookingTypeConfigVWDeno();
			if(!StringUtil.isNull(configTO)){
				rateQuotationTO.setVwDenominator(configTO.getDenominator());
			}else {
				rateQuotationTO
				.setErrorMsg(getMessageFromErrorBundle(
						request,
						RateErrorConstants.VW_DENO_NOT_EXIST,
						null));
				actionMessage = new ActionMessage(
						RateErrorConstants.VW_DENO_NOT_EXIST);
				LOGGER.warn("RateQuotationAction:: getDefaultUIValues :: Employee Details Does not exist");
			}
			
			
			
			
			request.setAttribute("government",
					RateQuotationConstants.GOVERNMENT);
			compositePrintUrl(request);
			getDefaultQuotationCommonUIValues(request,
					rateQuotationTO);
				}
			}
			
			if(!StringUtil.isNull(request.getParameter(RateQuotationConstants.LEAD_NUMBER))){
				setUpLeadsValuesToQuotation(request,rateQuotationTO);
			}
			
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefaultUIValues of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefaultUIValues of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getCodChagreValue of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::getDefaultUIValues::END------------>:::::::");
	}

	

	/**
	 * @param request
	 * @param rateQuotationTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	protected void getDefaultQuotationCommonUIValues(HttpServletRequest request,
			RateQuotationTO rateQuotationTO){
		ActionMessage actionMessage = null;
		try{
			LOGGER.trace("RateQuotationAction::getDefaultQuotationCommonUIValues::START------------>:::::::");
			rateQuotationService = getRateQuotationService();
		List<StockStandardTypeTO> stockStandardTypeTOList = rateQuotationService
				.getStandardType(RateQuotationConstants.BUSINESS_TYPE);
		if(!CGCollectionUtils.isEmpty(stockStandardTypeTOList)){
			request.setAttribute(RateQuotationConstants.STOCK_STANDRD_TYPE_TO_LIST,
				stockStandardTypeTOList);
		}else {
			rateQuotationTO
							.setErrorMsg(getMessageFromErrorBundle(
									request,
									RateErrorConstants.BUSINESS_TYPE_DTLS_NOT_EXIST,
									null));
			actionMessage = new ActionMessage(
					RateErrorConstants.DETAILS_DOES_NOT_EXIST,
						RateErrorConstants.BUSINESS_TYPE_DTLS_NOT_EXIST);
		
			LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: BUSINESS TYPE Details Does not exist");
				
			}
		
		BookingTypeConfigTO configTO=getBookingTypeConfigVWDeno();
		if(!StringUtil.isNull(configTO)){
			rateQuotationTO.setVwDenominator(configTO.getDenominator());
		}else {
			rateQuotationTO
			.setErrorMsg(getMessageFromErrorBundle(
					request,
					RateErrorConstants.VW_DENO_NOT_EXIST,
					null));
			actionMessage = new ActionMessage(
					RateErrorConstants.VW_DENO_NOT_EXIST);
			LOGGER.warn("RateQuotationAction:: getDefaultUIValues :: Employee Details Does not exist");
		}
		

		List<StockStandardTypeTO> stockStandardTypeTOList1 = rateQuotationService
				.getStandardType(RateQuotationConstants.CUSTOMER_DEPARTMENT);
		if(!CGCollectionUtils.isEmpty(stockStandardTypeTOList1)){
			request.setAttribute(RateQuotationConstants.CUSTOMER_DEPARTMENT_LIST,
				stockStandardTypeTOList1);
		} else {
				rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.CUSTOMER_DEPARTMENT_DTLS_NOT_EXIST,
								null));
				actionMessage =  new ActionMessage(
						RateErrorConstants.CUSTOMER_DEPARTMENT_DTLS_NOT_EXIST);
			
				LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: CUSTOMER DEPARTMENT Details Does not exist");
			}
		
		List<StockStandardTypeTO> stockStandardTypeTOList2 = rateQuotationService
				.getStandardType(RateQuotationConstants.CONSIGNOR_CONSIGNEE);
		if(!CGCollectionUtils.isEmpty(stockStandardTypeTOList2)){
			StockStandardTypeTO octroiBourneBy = new StockStandardTypeTO();
			for(StockStandardTypeTO octroiBourne : stockStandardTypeTOList2){
				if(octroiBourne.getDescription().equals(RateCommonConstants.CONSIGNEE)){
					octroiBourneBy = octroiBourne;
				}
			}
			stockStandardTypeTOList2.remove(octroiBourneBy);
			stockStandardTypeTOList2.add(0,octroiBourneBy);
			
			request.setAttribute(RateQuotationConstants.OCTROI_BOURNE_BY_LIST, stockStandardTypeTOList2);
		} else {
			rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.OCTROI_BOURNE_BY_DTLS_NOT_EXIST,
								null));
			actionMessage = new ActionMessage(
					RateErrorConstants.OCTROI_BOURNE_BY_DTLS_NOT_EXIST);
			
				LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: OCTROI BOURNE BY LIST Details Does not exist");
			}
		
		
		List<StockStandardTypeTO> stockStandardTypeTOList3 = rateQuotationService
				.getStandardType(RateQuotationConstants.RATE_QUOTATION_TITLE);
		if(!CGCollectionUtils.isEmpty(stockStandardTypeTOList3)){
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TITLE, stockStandardTypeTOList3);
		} else {
			rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.OCTROI_BOURNE_BY_DTLS_NOT_EXIST,
								null));
			
				actionMessage = new ActionMessage(
						RateErrorConstants.RATE_QUOTATION_TITLE_NOT_EXIST);
			
			LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: RATE QUOTATION TITLE Details Does not exist");
			}
		
		
		List<RateIndustryTypeTO> rateIndustryTypeTOList = getRateIndustryTypeList(request);
		if(!CGCollectionUtils.isEmpty(rateIndustryTypeTOList)){
		request.setAttribute(RateQuotationConstants.RATE_INDUSTRY_TYPE_LIST,
				rateIndustryTypeTOList);
		} else {
			rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.RATE_INDUSTRY_TYPE_DTLS_NOT_EXIST,
								null));
			
				actionMessage = new ActionMessage(
					RateErrorConstants.RATE_INDUSTRY_TYPE_DTLS_NOT_EXIST);
			
				LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: RATE INDUSTRY TYPE Details Does not exist");
			}
		
		List<RateIndustryCategoryTO> industryCategoryToList = null;
		
		if (rateQuotationTO.getRateQuotationType().equals(
				RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)){
			industryCategoryToList = getRateIndustryCategoryList(request,
					RateCommonConstants.RATE_QUOTATION);
		}else if (rateQuotationTO.getRateQuotationType().equals(
				RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E)) {
			industryCategoryToList = getRateIndustryCategoryList(request,
					RateCommonConstants.RATE_BENCH_MARK);
			if(!CGCollectionUtils.isEmpty(industryCategoryToList)){
			for (RateIndustryCategoryTO ricTO : industryCategoryToList) {
				if (ricTO.getRateIndustryCategoryCode().equals(
						RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE)) {
					request.setAttribute(
							RateQuotationConstants.ECOMMERCE_CUST_CAT_ID,
							ricTO.getRateCustomerCategoryId());
					request.setAttribute(
							RateQuotationConstants.IND_CAT_CUST_CAT_ID,
							ricTO.getRateIndustryCategoryId()+CommonConstants.TILD+ricTO.getRateCustomerCategoryId());
					break;
				}
			}
			}
		}
		if(!CGCollectionUtils.isEmpty(industryCategoryToList)){
			request.setAttribute(RateQuotationConstants.IND_CAT_LIST,
				industryCategoryToList);
		
		for (RateIndustryCategoryTO ricTO : industryCategoryToList) {
			if (ricTO.getRateIndustryCategoryCode().equals(
					RateQuotationConstants.BFSI)) { //to enable dropdown
				request.setAttribute(RateQuotationConstants.IND_CAT_GNRL,
						ricTO.getRateIndustryCategoryId());
				break;
			}
		}
		}else{
			rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.RATE_INDUSTRY_CATEGORY_DTLS_NOT_EXIST,
								null));
			actionMessage = new ActionMessage(
					RateErrorConstants.RATE_INDUSTRY_CATEGORY_DTLS_NOT_EXIST);
			LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: RATE INDUSTRY Category Details Does not exist");
			}	
			

		
	
		List<CustomerGroupTO> customerGroupTOList = getcustomerGroup(request);
		if(!CGCollectionUtils.isEmpty(customerGroupTOList)){
			request.setAttribute(RateQuotationConstants.CUSTOMER_GROUP_LIST, customerGroupTOList);
		} else {
			rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.CUSTOMER_GROUP_DTLS_NOT_EXIST,
								null));
			actionMessage = new ActionMessage(
					RateErrorConstants.CUSTOMER_GROUP_DTLS_NOT_EXIST);
			
				LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: CUSTOMER GROUP Details Does not exist");
			}
		
		List<InsuredByTO> insuredByTOs = getRiskSurchargeInsuredBy(request);
		if(!CGCollectionUtils.isEmpty(insuredByTOs)){
			request.setAttribute(RateQuotationConstants.INSURED_BY_TOS, insuredByTOs);
		} else {
				rateQuotationTO
						.setErrorMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.INSURED_BY_DTLS_NOT_EXIST,
								null));
			
				actionMessage = new ActionMessage(
					RateErrorConstants.INSURED_BY_DTLS_NOT_EXIST);
			
				LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: INSURED BY Details Does not exist");
			}
		rateQuotationTO.setLcCode(RateQuotationConstants.LC_CODE);
		getProposedRateDefultUIValues(request, rateQuotationTO);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefaultQuotationCommonUIValues of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefaultQuotationCommonUIValues of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefaultQuotationCommonUIValues of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		
		LOGGER.trace("RateQuotationAction::getDefaultQuotationCommonUIValues::START------------>:::::::");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getCityName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		CityTO cityTO = null;//new CityTO();
		String cityTOJSON = null;
		String pincode = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		try {
			LOGGER.trace("RateQuotationAction::getCityName::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			if (StringUtils.isNotEmpty(request
					.getParameter(RateQuotationConstants.PINCODE))) {
				pincode = request.getParameter(RateQuotationConstants.PINCODE);
			}
			
			cityTO = getCityTOByPincode(pincode);

			if (!StringUtil.isNull(cityTO) && !StringUtil.isNull(cityTO.getCityId())) {
				cityTOJSON = serializer.toJSON(cityTO).toString();
			} else {
				cityTOJSON = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.INVALID_PINCODE, null));
			}

		}catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getCityName()::Exception::"
					+ e.getMessage());
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getCityName()::Exception::"
					+ e.getMessage());
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getCityName()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			cityTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(cityTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getCityName::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getPincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		PincodeTO pincodeTO = new PincodeTO();
		String pincodeTOJSON = null;
		CityTO cityTO = null;//new CityTO();
		PrintWriter out = null;
		out = response.getWriter();
		
		try {
			LOGGER.trace("RateQuotationAction::getPincode::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			if (StringUtils.isNotEmpty(request
					.getParameter(RateQuotationConstants.PINCODE))) {
				pincodeTO.setPincode(request
						.getParameter(RateQuotationConstants.PINCODE));
			}
			
			pincodeTO = validatePincode(pincodeTO);

			if (!StringUtil.isNull(pincodeTO) && !StringUtil.isNull(pincodeTO.getPincodeId())) {

			
				cityTO = getCityTOByPincode(pincodeTO.getPincode());

				if (!StringUtil.isNull(cityTO) && !StringUtil.isNull(cityTO.getCityId())) {
					pincodeTO.setCityTO(cityTO);
					pincodeTOJSON = serializer.toJSON(pincodeTO).toString();
				} else {
					pincodeTOJSON = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(request,
									RateErrorConstants.INVALID_PINCODE, null));
				}
			} else {
				pincodeTOJSON = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.INVALID_PINCODE, null));
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getPincode()::Exception::"
					+ e.getMessage());
			pincodeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getPincode()::Exception::"
					+ e.getMessage());
			pincodeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getPincode()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			pincodeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(pincodeTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getPincode::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getAlloffices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {

		String officeTOJSON = null;
		Integer loginCityId = null;
		PrintWriter out = null;
		List<OfficeTO> officeTOs = null;
		UserInfoTO userInfoTO = null;
		try {
			LOGGER.trace("RateQuotationAction::getAlloffices::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request
					.getParameter(RateQuotationConstants.STATION))) {
				loginCityId = Integer.parseInt(request
						.getParameter(RateQuotationConstants.STATION));

			}
			
			userInfoTO = getLoginUserInfoTO(request);
			if(userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
				officeTOs = getAllOfficesByCityAndOfcType(loginCityId,CommonConstants.OFF_TYPE_CORP_OFFICE);
			}else if(userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				officeTOs = getAllOfficesByCityAndOfcType(loginCityId,CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);
			}else{
				officeTOs = getAllOfficesByCity(loginCityId);
			}

			if (!StringUtil.isEmptyList(officeTOs)) {

				officeTOJSON = serializer.toJSON(officeTOs).toString();
			} else {
				officeTOJSON = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.OFFICE_DTLS_NOT_FOUND, null));
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getAlloffices()::Exception::"
					+ e.getMessage());
			officeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getAlloffices()::Exception::"
					+ e.getMessage());
			officeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getAlloffices()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			officeTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(officeTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getAlloffices::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getAllEmployees(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		OfficeTO officeTO = new OfficeTO();
		String employeeTOJSON = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		try {
			LOGGER.trace("RateQuotationAction::getAllEmployees::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			if (StringUtils.isNotEmpty(request.getParameter(RateQuotationConstants.SALES_OFFICE))) {
				officeTO.setOfficeId(Integer.parseInt(request
						.getParameter(RateQuotationConstants.SALES_OFFICE)));
			}
			

			List<EmployeeTO> employeeTO = getEmployeesOfOffice(officeTO);

			if (!StringUtil.isEmptyList(employeeTO)) {

				employeeTOJSON = serializer.toJSON(employeeTO).toString();
			} else {
				employeeTOJSON = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.EMPLOYEE_DTLS_NOT_EXIST, null));
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getAllEmployees()::Exception::"
					+ e.getMessage());
			employeeTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getAllEmployees()::Exception::"
					+ e.getMessage());
			employeeTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getAllEmployees()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			employeeTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(employeeTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getAllEmployees::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateBasicInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		RateQuotationTO rateQuotationTO = null;
		RateQuotationForm rateQuotationForm = null;
		PrintWriter out = null;
		out = response.getWriter();
		String jsonResult = "";
		
		try {
			LOGGER.trace("RateQuotationAction::saveOrUpdateBasicInfo::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			rateQuotationForm = (RateQuotationForm) form;
			rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();

			if (rateQuotationTO != null) {
				if (rateQuotationTO.getRateQuotationType().equals(
						RateCommonConstants.RATE_QUOTATION_TYPE_N)){
					String  indCode = getIndustryCatCode(
							request,
							Integer.parseInt(rateQuotationTO.getCustomer()
									.getIndustryCategory()
									.split(CommonConstants.TILD)[0]));
						rateQuotationTO.setIndCatCode(indCode);
				}
				rateQuotationTO.setLoginOfficeCode(getLoginOfficeCode(request));
				rateQuotationService = getRateQuotationService();
				rateQuotationTO = rateQuotationService
						.saveOrUpdateBasicInfo(rateQuotationTO);
				if (!rateQuotationTO.isSaved()) {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(request,
									RateErrorConstants.DATA_NOT_SAVED, null));
				} else {
					rateQuotationTO.setTransMsg(getMessageFromErrorBundle(
							request,
							RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
					jsonResult = serializer.toJSON(rateQuotationTO).toString();
				}
				
			}
		}  catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateBasicInfo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateBasicInfo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateBasicInfo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::saveOrUpdateBasicInfo::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access" })
	public void searchQuotationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		out = response.getWriter();
		
		String quotationNo = null;
		Integer createdBy = null;
		Integer rateQuotationId = null;
		String jsonResult = "";
		String rateQuotationType=null;
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		try {
			LOGGER.trace("RateQuotationAction::searchQuotationDetails::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.QUOTATION_NUMBER))) {
				quotationNo = request.getParameter(RateQuotationConstants.QUOTATION_NUMBER);
				rateQuotationTO.setRateQuotationNo(quotationNo);
			}
			if (!StringUtil.isStringEmpty(request
					.getParameter(RateQuotationConstants.RATE_QUOTATION_ID))) {
				rateQuotationId = Integer.parseInt(request
						.getParameter(RateQuotationConstants.RATE_QUOTATION_ID));
				rateQuotationTO.setRateQuotationId(rateQuotationId);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.CREATED_BY))) {
				createdBy = Integer.parseInt(request.getParameter(RateQuotationConstants.CREATED_BY));
				rateQuotationTO.setCreatedBy(createdBy);
			}
			if (!StringUtil.isStringEmpty(request
					.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE))) {
				rateQuotationType = (request
						.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE));
				rateQuotationTO.setRateQuotationType(rateQuotationType);
			}
			
			
			rateQuotationTO
					.setQuotationUsedFor(RateQuotationConstants.QUOTATION);

			rateQuotationService = getRateQuotationService();
			rateQuotationTO = rateQuotationService
					.searchQuotationDetails(rateQuotationTO);
			if(!StringUtil.isEmptyInteger(rateQuotationTO.getRhoOfcId())){
				List<CityTO> cityList = getAllCities(rateQuotationTO.getRhoOfcId());
				if(!CGCollectionUtils.isEmpty(cityList)){
					rateQuotationTO.setCityTOList(cityList);
				}
			}
			
			jsonResult = serializer.toJSON(rateQuotationTO).toString();

		}catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::searchQuotationDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::searchQuotationDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::searchQuotationDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::searchQuotationDetails::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void loadDefaultFixedChargesValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		HttpSession session = request.getSession(false);
		List<RateComponentTO> componentTOs = null;
		List<RateQuotaionFixedChargesTO> quotaionFixedChargesTOs = null;
		List<Object> list = new ArrayList<>();
		try {
			LOGGER.trace("RateQuotationAction::loadDefaultFixedChargesValue::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			List<RateTaxComponentTO> rateTaxComponentTO = null;//new ArrayList<RateTaxComponentTO>();
			List<RateQuotationCODChargeTO> codChargeTOs = new ArrayList<RateQuotationCODChargeTO>();

			String quotationId = request.getParameter(RateQuotationConstants.QUOTATION_ID);
			Integer stateId = Integer.parseInt(request.getParameter(RateQuotationConstants.STATE_ID));
			componentTOs = new ArrayList<RateComponentTO>();
			quotaionFixedChargesTOs = new ArrayList<RateQuotaionFixedChargesTO>();
			rateQuotationService = getRateQuotationService();
			componentTOs = (List<RateComponentTO>) session
					.getAttribute(RateCommonConstants.RATE_COMPONENT_LIST);
			rateTaxComponentTO = (List<RateTaxComponentTO>) session
					.getAttribute(RateCommonConstants.RATE_TAX_COMPONENT_LIST);
			if (CGCollectionUtils.isEmpty(componentTOs)) {
				componentTOs = rateQuotationService
						.loadDefaultRateComponentValue();

			}
			if (CGCollectionUtils.isEmpty(rateTaxComponentTO)) {
				rateTaxComponentTO = rateQuotationService
						.loadDefaultRateTaxComponentValue(stateId);

			}

			if (!CGCollectionUtils.isEmpty(rateTaxComponentTO)) {
				session.setAttribute(
						RateCommonConstants.RATE_TAX_COMPONENT_LIST,
						rateTaxComponentTO);
			}

			if (!CGCollectionUtils.isEmpty(componentTOs)) {
				session.setAttribute(RateCommonConstants.RATE_COMPONENT_LIST,
						componentTOs);
			}

			if (!StringUtil.isEmptyInteger(Integer.parseInt(quotationId))) {
				quotaionFixedChargesTOs = rateQuotationService
						.loadDefaultFixedChargesValue(quotationId);

			}

			if (!StringUtil.isEmptyInteger(Integer.parseInt(quotationId))) {
				codChargeTOs = rateQuotationService
						.loadQuotationCodCharge(quotationId);

			}

			list.add(quotaionFixedChargesTOs);
			list.add(componentTOs);
			list.add(rateTaxComponentTO);
			list.add(codChargeTOs);

			jsonResult = serializer.toJSON(list).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::loadDefaultFixedChargesValue()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::loadDefaultFixedChargesValue()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::loadDefaultFixedChargesValue()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::loadDefaultFixedChargesValue::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getOctroiChargeValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		try {
			LOGGER.trace("RateQuotationAction::getOctroiChargeValue::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			String octroiBourneBy = request.getParameter(RateQuotationConstants.RISK_SURCHARGES);
			OctroiChargeTO octroiChargeTO = new OctroiChargeTO();

			if (StringUtils.isNotEmpty(octroiBourneBy)) {

				octroiChargeTO.setOctroiBourneBy(octroiBourneBy);

				rateQuotationService = getRateQuotationService();
				octroiChargeTO = rateQuotationService
						.getOctroiChargeValue(octroiChargeTO);

				jsonResult = serializer.toJSON(octroiChargeTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getOctroiChargeValue()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getOctroiChargeValue()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getOctroiChargeValue()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getOctroiChargeValue::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateEcomerceFixedCharges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		RateQuotaionFixedChargesTO fixedChargesTO = null;
		RateQuotationForm rateQuotationForm = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		try {
			LOGGER.trace("RateQuotationAction::saveOrUpdateEcomerceFixedCharges::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			rateQuotationForm = (RateQuotationForm) form;
			fixedChargesTO = (RateQuotaionFixedChargesTO) rateQuotationForm
					.getRateQuotationFixedChargesTO();
			rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();
			fixedChargesTO.setRateQuotation(rateQuotationTO);

			List<RateQuotationCODChargeTO> codList = null;//new ArrayList<RateQuotationCODChargeTO>();
			codList =  prepareCODCharges(request, fixedChargesTO);
			
			rateQuotationService = getRateQuotationService();
			if (fixedChargesTO != null) {

				fixedChargesTO = rateQuotationService
						.saveOrUpdateEcomerceFixedCharges(fixedChargesTO,
								codList);
			
			}

			if (!fixedChargesTO.isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				fixedChargesTO.setTransMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				jsonResult = serializer.toJSON(fixedChargesTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateEcomerceFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateEcomerceFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateEcomerceFixedCharges()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::saveOrUpdateEcomerceFixedCharges::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateFixedCharges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RateQuotaionFixedChargesTO fixedChargesTO = null;
		RateQuotationForm rateQuotationForm = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		String jsonResult = "";
		
		try {
			LOGGER.trace("RateQuotationAction::saveOrUpdateFixedCharges::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			rateQuotationForm = (RateQuotationForm) form;
			fixedChargesTO = (RateQuotaionFixedChargesTO) rateQuotationForm
					.getRateQuotationFixedChargesTO();
			rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();
			fixedChargesTO.setRateQuotation(rateQuotationTO);
			
			
			List<RateQuotationCODChargeTO> codList = new ArrayList<RateQuotationCODChargeTO>();
			codList = prepareCODCharges(request, fixedChargesTO);

			
			rateQuotationService = getRateQuotationService();
			if (fixedChargesTO != null) {

				fixedChargesTO = rateQuotationService
						.saveOrUpdateFixedCharges(fixedChargesTO,codList);
				
			}
			
			if (!fixedChargesTO.isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				fixedChargesTO.setTransMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				jsonResult = serializer.toJSON(fixedChargesTO).toString();
			}
			
		}catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateFixedCharges()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::saveOrUpdateFixedCharges::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRTOCharges(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		RateQuotaionRTOChargesTO rtoChargesTO = null;
		RateQuotationForm rateQuotationForm = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		out = response.getWriter();
		String jsonResult = "";
		
		try {
			LOGGER.trace("RateQuotationAction::saveOrUpdateRTOCharges::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			
			rateQuotationForm = (RateQuotationForm) form;
			rtoChargesTO = (RateQuotaionRTOChargesTO) rateQuotationForm
					.getRateQuotationRTOChargesTO();
			rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();
			rtoChargesTO.setRateQuotation(rateQuotationTO);
			rateQuotationService = getRateQuotationService();
			if (rtoChargesTO != null) {
				rtoChargesTO = rateQuotationService
						.saveOrUpdateRTOCharges(rtoChargesTO);
				}
			
			if (!rtoChargesTO.isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				rtoChargesTO.setTransMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				jsonResult = serializer.toJSON(rtoChargesTO).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRTOCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRTOCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRTOCharges()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("RateQuotationAction::saveOrUpdateRTOCharges::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access" })
	public void loadRTOChargesDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";

		RateQuotaionRTOChargesTO quotaionRTOChargesTOs = null;
		try {
			LOGGER.trace("RateQuotationAction::loadRTOChargesDefault::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			String quotationId = request.getParameter(RateQuotationConstants.QUOTATION_ID);
			rateQuotationService = getRateQuotationService();
			if (!StringUtil.isEmptyInteger(Integer.parseInt(quotationId))) {
				quotaionRTOChargesTOs = rateQuotationService
						.loadRTOChargesDefault(quotationId);
			}
			jsonResult = serializer.toJSON(quotaionRTOChargesTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::loadRTOChargesDefault()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::loadRTOChargesDefault()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::loadRTOChargesDefault()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::loadRTOChargesDefault::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void copyQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		out = response.getWriter();
		
		String quotationNo = null;
		String loginOfficeCode = null;
		Integer createdBy = null;
		Integer updatedBy = null;
		String jsonResult = "";
		String rateQuotationType = "";
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		try {
			LOGGER.trace("RateQuotationAction::copyQuotation::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			if (!StringUtil.isNull(request
					.getParameter(RateQuotationConstants.QUOTATION_NUMBER))) {
				quotationNo = request
						.getParameter(RateQuotationConstants.QUOTATION_NUMBER);
				rateQuotationTO.setRateQuotationNo(quotationNo);
			}
			if (!StringUtil.isNull(request
					.getParameter(RateQuotationConstants.LOGIN_OFFICE_CODE))) {
				loginOfficeCode = request
						.getParameter(RateQuotationConstants.LOGIN_OFFICE_CODE);
				rateQuotationTO.setLoginOfficeCode(loginOfficeCode);
			}
			if (!StringUtil.isNull(request
					.getParameter(RateQuotationConstants.CREATED_BY))) {
				createdBy = Integer.parseInt(request
						.getParameter(RateQuotationConstants.CREATED_BY));
				rateQuotationTO.setCreatedBy(createdBy);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.UPDATED_BY))) {
				updatedBy = Integer.parseInt(request.getParameter(RateQuotationConstants.UPDATED_BY));
				rateQuotationTO.setUpdatedBy(updatedBy);
			}
			if (!StringUtil.isStringEmpty(request
					.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE))) {
				rateQuotationType = (request
						.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE));
				rateQuotationTO.setRateQuotationType(rateQuotationType);
			}

			rateQuotationTO
					.setQuotationUsedFor(RateQuotationConstants.QUOTATION);

			rateQuotationService = getRateQuotationService();
			rateQuotationTO = rateQuotationService.copyQuotation(rateQuotationTO);

			if (!rateQuotationTO.isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.QUOTATION_NOT_COPIED_SUCCESSFULLY,
								null));
			} else {
				rateQuotationTO
						.setTransMsg(getMessageFromErrorBundle(
								request,
								RateErrorConstants.QUOTATION_COPIED_SUCCESSFULLY,
								null));
				jsonResult = serializer.toJSON(rateQuotationTO).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::copyQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::copyQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::copyQuotation()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::copyQuotation::END------------>:::::::");
	}

	/**
	 * Load the RateBenchMArk details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@SuppressWarnings({ "static-access", "unchecked", "unused" })
	public void getValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		out = response.getWriter();
		String data = "";
		HttpSession session = request.getSession(false);
		List<RateProductCategoryTO> prodCatList = null;

		Integer prodCatId = null;
		Integer quotationId = null;
		String quotType = null;
		String custCode = null;

		try {
			LOGGER.trace("RateQuotationAction::getValues::START------------>:::::::");

			serializer = CGJasonConverter.getJsonObject();
			

			RateQuotationProposedRatesTO rateQuotationProposedRatesTO = new RateQuotationProposedRatesTO();
			quotType = request.getParameter(RateQuotationConstants.QUOT_TYPE);
			custCode = getCustCatCode(request,
					Integer.parseInt(request.getParameter(RateQuotationConstants.CUST_ID)));

			prodCatId = Integer.parseInt(request
					.getParameter(RateQuotationConstants.PRODUCT_CATEGOTY_ID));
			if (!StringUtil.isStringEmpty(request.getParameter(RateQuotationConstants.QUOTATION_ID))) {
				quotationId = Integer.parseInt(request
						.getParameter(RateQuotationConstants.QUOTATION_ID));
				rateQuotationProposedRatesTO.setRateQuotationId(quotationId);
			}

			rateQuotationProposedRatesTO.setRateProdCatId(prodCatId);
			rateQuotationProposedRatesTO.setCustCatCode(custCode);
			if (quotType
					.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)) {
				prodCatList = (List<RateProductCategoryTO>) session
						.getAttribute(RateCommonConstants.RATE_QUOT_PROD_CAT_N_LIST);
			} else if (quotType
					.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E)) {
				prodCatList = (List<RateProductCategoryTO>) session
						.getAttribute(RateCommonConstants.RATE_QUOT_PROD_CAT_E_LIST);
			}
		
			setUpValues(request, rateQuotationProposedRatesTO, quotType);
			/*if (!StringUtil.isNull(rateQuotationProposedRatesTO
					.getRateMatrixMap())) {
				for (RateProductCategoryTO rpcTO : prodCatList) {
					session.setAttribute(
							rpcTO.getRateProductCategoryId().toString(),
							rateQuotationProposedRatesTO.getRateMatrixMap()
									.get(rpcTO.getRateProductCategoryId()
											.toString()));
				}
			}*/

			data = serializer.toJSON(rateQuotationProposedRatesTO).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getValues()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getValues()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getValues()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getValues::END------------>:::::::");
	}

	/**
	 * Setup Grid values of RateBenchMark
	 * 
	 * @param request
	 * @param rateBenchMarkHeaderTO
	 * @throws CGBusinessException,CGSystemException 
	 */
	@SuppressWarnings({ "unchecked" })
	public void setUpValues(HttpServletRequest request,
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO,
			String quotType) throws CGBusinessException,CGSystemException {
		HttpSession session = request.getSession(false);
		List<StateTO> statesList = null;
		
			LOGGER.trace("RateQuotationAction::setUpValues::START------------>:::::::");
			List<RateProductCategoryTO> prodCatList = null;
			if (quotType
					.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)) {
				prodCatList = (List<RateProductCategoryTO>) session
						.getAttribute(RateCommonConstants.RATE_QUOT_PROD_CAT_N_LIST);
			} else if (quotType
					.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E)) {
				prodCatList = (List<RateProductCategoryTO>) session
						.getAttribute(RateCommonConstants.RATE_QUOT_PROD_CAT_E_LIST);
			}

			rateQuotationProposedRatesTO.setRateProdCatList(prodCatList);

			RateQuotationProductCategoryHeaderTO prodHeaderTO = null;
			if(!StringUtil.isNull(rateQuotationProposedRatesTO
							.getRateQuotationProdCatHeaderId())){
			 prodHeaderTO = (RateQuotationProductCategoryHeaderTO) session
					.getAttribute(rateQuotationProposedRatesTO
							.getRateQuotationProdCatHeaderId()
									.toString());
			}
			if (!StringUtil.isNull(prodHeaderTO)){
				rateQuotationProposedRatesTO
						.setProductCatHeaderTO(prodHeaderTO);
			}else {
				rateQuotationService = getRateQuotationService();
				if (!StringUtil.isEmptyInteger(rateQuotationProposedRatesTO
						.getRateQuotationId())){
					rateQuotationService
							.getRateQuotationProposedRateDetails(rateQuotationProposedRatesTO);
				}
				if (!StringUtil.isNull(rateQuotationProposedRatesTO
						.getProductCatHeaderTO())){
					session.setAttribute(rateQuotationProposedRatesTO
						.getProductCatHeaderTO()
						.getRateQuotationProductCategoryHeaderId().toString(),
						rateQuotationProposedRatesTO.getProductCatHeaderTO());
				}
			}
			
			statesList = getStatesList(request);
			if(!CGCollectionUtils.isEmpty(statesList)){
				rateQuotationProposedRatesTO.setStatesList(statesList);
			}
				
			
			
			setupGlobalValues(rateQuotationProposedRatesTO, quotType, request);

		
		LOGGER.trace("RateQuotationAction::setUpValues::END------------>:::::::");
	}

	/**
	 * Save RateBenchMark details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRateQuotationProposedRates(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException, IOException {
		// String status = null;
		PrintWriter out = null;
		out = response.getWriter();
		String data = "";
	
		try {
			LOGGER.trace("RateQuotationAction::saveOrUpdateRateQuotationProposedRates::START------------>:::::::");
			HttpSession session = request.getSession(false);
			serializer = CGJasonConverter.getJsonObject();
			
			RateQuotationForm rateQuotationForm = (RateQuotationForm) form;
			RateQuotationProposedRatesTO rqprTO = (RateQuotationProposedRatesTO) rateQuotationForm
					.getProposedRatesTO();
			RateQuotationTO rqTO = (RateQuotationTO) rateQuotationForm.getTo();
			UserTO user = getLoginUserTO(request);
			rqTO.setUserId(user.getUserId());
			Integer custId = rqprTO.getCustCatId();
			rateQuotationService = getRateQuotationService();
			// RateQuotationProductCategoryHeaderTO rqpchDO = new
			// RateQuotationProductCategoryHeaderTO();
			rqprTO = rateQuotationService.saveOrUpdateSlabRateDeatails(rqprTO,
					rqTO);
			session.setAttribute(rqprTO.getProductCatHeaderTO()
					.getRateQuotationProductCategoryHeaderId().toString(),
					rqprTO.getProductCatHeaderTO());
			rqprTO.setCustCatCode(getCustCatCode(request, custId));

			setupGlobalValues(rqprTO, rqTO.getRateQuotationType(), request);
			
			
			if (!rqprTO.isSaved()) {
				data = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				rqprTO.setTransMsg(getMessageFromErrorBundle(
						request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				data = serializer.toJSON(rqprTO).toString();
			}
		
		}catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::saveOrUpdateRateQuotationProposedRates::END------------>:::::::");
	}

	/**
	 * get RateBenchMarkService Object
	 * 
	 * @return
	 */
	public RateQuotationService getRateQuotationService() {
		if (StringUtil.isNull(rateQuotationService)) {
			rateQuotationService = (RateQuotationService) getBean(RateSpringConstants.RATE_QUOTATION_SERVICE);
		}
		return rateQuotationService;
	}

	/**
	 * @param rateQuotationProposedRatesTO
	 * @param quotType
	 * @param request
	 */
	public void setupGlobalValues(
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO,
			String quotType, HttpServletRequest request) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationAction::setupGlobalValues::START------------>:::::::");
		// HttpSession session=request.getSession(false);
		List<RateVobSlabsTO> vobSlabsList = null;
		List<RateWeightSlabsTO> wtSlabList = null;
		List<RateSectorsTO> sectorsList = null;
		List<RateMinChargeableWeightTO> minChrgWtList = null;
		List<RateProductCategoryTO> prodCatList = null;
		String custCode = rateQuotationProposedRatesTO.getCustCatCode();
		String module = RateCommonConstants.RATE_QUOTATION;

		prodCatList = getRateProductCategoryList(request, module, quotType);
		vobSlabsList = getRateVobSlabsList(request, module, quotType, custCode);
		wtSlabList = getRateWeightSlabsList(request, module, quotType, custCode);
		sectorsList = getRateConfigSectorsList(request, module, quotType,
				custCode);
		if (quotType
				.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)){
			minChrgWtList = getRateMinChargeWtList(request, module, quotType,
					custCode);
		}
		rateQuotationProposedRatesTO.setRateProdCatList(prodCatList);
		rateQuotationProposedRatesTO.setRateVobSlabsList(vobSlabsList);
		rateQuotationProposedRatesTO.setRateWtSlabsList(wtSlabList);
		rateQuotationProposedRatesTO.setRateSectorsList(sectorsList);
		rateQuotationProposedRatesTO.setRateMinChargWtList(minChrgWtList);

		/*OfficeTO ofcTo = new OfficeTO();
		ofcTo = getLoginOfficeTO(request);
		
		if(ofcTo.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
			rateQuotationProposedRatesTO.setRegionCode("");			
		}else{
			rateQuotationProposedRatesTO.setRegionCode(getUserZoneCode(request));
		}*/
		LOGGER.trace("RateQuotationAction::setupGlobalValues::END------------>:::::::");
	}

	/**
	 * @param request
	 * @param rateQuotationTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getProposedRateDefultUIValues(HttpServletRequest request,
			RateQuotationTO rateQuotationTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RateQuotationAction::getProposedRateDefultUIValues::START------------>:::::::");
		List<LabelValueBean> prodCatList = null;
		ActionMessage actionMessage = null;
		/*
		 * List<RateMinChargeableWeightTO> minChrgWtList = null;
		 * List<RateVobSlabsTO> vobSlabsList = null; List<RateSectorsTO>
		 * sectorsList = null; List<RateWeightSlabsTO> wtSlabList = null;
		 */String type = null;
		String listElement = "";
		HttpSession session = request.getSession(Boolean.FALSE);
		try {
			if (rateQuotationTO.getRateQuotationType().equals(
					RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)) {
				type = RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N;
				listElement = RateCommonConstants.RATE_QUOT_PROD_CAT_N_LIST;
			} else if (rateQuotationTO.getRateQuotationType().equals(
					RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E)) {
				type = RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E;
				listElement = RateCommonConstants.RATE_QUOT_PROD_CAT_E_LIST;
			}

			List<RateProductCategoryTO> prodCatToList = getRateProductCategoryList(
					request, RateCommonConstants.RATE_QUOTATION, type);
			if (!CGCollectionUtils.isEmpty(prodCatToList)) {
				prodCatList = new ArrayList<LabelValueBean>();
				for (RateProductCategoryTO rateProductCategoryTO : prodCatToList) {
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(rateProductCategoryTO
							.getRateProductCategoryCode());
					lvb.setValue(rateProductCategoryTO
							.getRateProductCategoryId().toString());
					prodCatList.add(lvb);
				}
				request.setAttribute(listElement, prodCatList);
				request.setAttribute(RateCommonConstants.RATE_PROD_CAT_LIST,
						prodCatToList);
				session.setAttribute("productCode", prodCatToList.get(0)
						.getRateProductCategoryCode());
				session.setAttribute("productId", prodCatToList.get(0)
						.getRateProductCategoryId());
			} else {
				rateQuotationTO
							.setErrorMsg(getMessageFromErrorBundle(
									request,
									RateErrorConstants.PRODUCT_CATEGORY_DTLS_NOT_EXIST,
									null));
					actionMessage = new ActionMessage(
							RateErrorConstants.PRODUCT_CATEGORY_DTLS_NOT_EXIST);
				
				LOGGER.warn("RateQuotationAction:: getProposedRateDefultUIValues :: BUSINESS TYPE Details Does not exist");

			}

			getRateCustomerCategoryList(request);

		} catch(CGSystemException e){
			LOGGER.error("Exception happened in getProposedRateDefultUIValues of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getProposedRateDefultUIValues of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getProposedRateDefultUIValues of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::getProposedRateDefultUIValues::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void submitRateQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {

		RateQuotaionRTOChargesTO rtoChargesTO = null;
		RateQuotationForm rateQuotationForm = null;
		RateQuotationTO rateQuotationTO = null;
		String indCode = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		try {
			LOGGER.trace("RateQuotationAction::submitRateQuotation::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			rateQuotationForm = (RateQuotationForm) form;
			rtoChargesTO = (RateQuotaionRTOChargesTO) rateQuotationForm
					.getRateQuotationRTOChargesTO();
			rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();
			rtoChargesTO.setRateQuotation(rateQuotationTO);
			rateQuotationService = getRateQuotationService();
			if (rtoChargesTO != null) {
				
				if (rateQuotationTO.getRateQuotationType().equals(
						RateCommonConstants.RATE_QUOTATION_TYPE_N)){
					if(!StringUtil.isNull(rateQuotationTO) && !StringUtil.isNull(rateQuotationTO.getCustomer()) && !StringUtil.isNull(rateQuotationTO.getCustomer()
							.getIndustryCategory())){
					indCode = getIndustryCatCode(
							request,
							Integer.parseInt(rateQuotationTO.getCustomer()
									.getIndustryCategory()
									.split(CommonConstants.TILD)[0]));
					}
				}
				
				
				Integer regionId = null;
				OfficeTO ofcTo = rateQuotationService.getOfficeDetails(rateQuotationTO.getCustomer().getSalesOffice().getOfficeId());				
				if(!CommonConstants.OFF_TYPE_CORP_OFFICE.equals(ofcTo.getOfficeTypeTO().getOffcTypeCode())){
					regionId = ofcTo.getRegionTO().getRegionId();
				}
				rateQuotationService.submitRateQuotation(rtoChargesTO, indCode, regionId);
				

				if (!rtoChargesTO.isSaved()) {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									RateErrorConstants.QUOTATION_NOT_SUBMITTED_SUCCESSFULLY,
									null));
				} else {
					rtoChargesTO
							.setTransMsg(getMessageFromErrorBundle(
									request,
									RateErrorConstants.QUOTATION_SUBMITTED_SUCCESSFULLY,
									null));
					jsonResult = serializer.toJSON(rtoChargesTO).toString();

				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::submitRateQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::submitRateQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::submitRateQuotation()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::submitRateQuotation::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void createContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		String quotationNo = null;
		String loginOfficeCode = null;
		Integer createdBy = null;
		Integer updatedBy = null;
		String jsonResult = "";
		String rateQuotationType="";
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		RateContractTO rateContractTO = new RateContractTO();
		try {
			LOGGER.trace("RateQuotationAction::createContract::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.QUOTATION_NUMBER))) {
				quotationNo = request.getParameter(RateQuotationConstants.QUOTATION_NUMBER);
				rateQuotationTO.setRateQuotationNo(quotationNo);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.LOGIN_OFFICE_CODE))) {
				loginOfficeCode = request.getParameter(RateQuotationConstants.LOGIN_OFFICE_CODE);
				rateQuotationTO.setLoginOfficeCode(loginOfficeCode);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.CREATED_BY))) {
				createdBy = Integer.parseInt(request.getParameter(RateQuotationConstants.CREATED_BY));
				rateQuotationTO.setCreatedBy(createdBy);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.UPDATED_BY))) {
				updatedBy = Integer.parseInt(request.getParameter(RateQuotationConstants.UPDATED_BY));
				rateQuotationTO.setUpdatedBy(updatedBy);
			}
			if (!StringUtil.isStringEmpty(request.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE))) {
				rateQuotationType = (request.getParameter(RateQuotationConstants.RATE_QUOTATION_TYPE));
				rateQuotationTO.setRateQuotationType(rateQuotationType);
			}

			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CONTRACT);
			rateQuotationTO.setLoginOfficeCode(getLoginOfficeCode(request));

			rateQuotationService = getRateQuotationService();
			rateContractTO = rateQuotationService.createContract(rateQuotationTO, rateContractTO);

			if (!rateContractTO.getRateQuotationTO().isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.CONTRACT_NOT_CREATED_SUCCESSFULLY,
								null));
			} else {
				jsonResult = serializer.toJSON(rateContractTO).toString();

			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::createContract()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::createContract()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::createContract()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::createContract::END------------>:::::::");
	}

	/**
	 * @param request
	 * @param custId
	 * @return
	 */
	public String getCustCatCode(HttpServletRequest request, Integer custId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationAction::getCustCatCode::START------------>:::::::");
		String custCatCode = null;
		List<RateCustomerCategoryTO> custCatList = getRateCustomerCategoryList(request);
		if (!CGCollectionUtils.isEmpty(custCatList)) {
			for (RateCustomerCategoryTO custTO : custCatList) {
				if (custTO.getRateCustomerCategoryId().equals(custId)) {
					custCatCode = custTO.getRateCustomerCategoryCode();
					break;
				}
			}
		}else {
			prepareActionMessage(request, new ActionMessage(
					RateErrorConstants.RATE_CUSTOMER_CATEGORY_DTLS_NOT_EXIST));
			LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: RATE INDUSTRY TYPE Details Does not exist");
		}
		LOGGER.trace("RateQuotationAction::getCustCatCode::END------------>:::::::");
		return custCatCode;

	}

	/**
	 * @param request
	 * @param indCatId
	 * @return
	 */
	public String getIndustryCatCode(HttpServletRequest request,
			Integer indCatId) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationAction::getIndustryCatCode::START------------>:::::::");
		String indCatCode = null;
			List<RateIndustryCategoryTO> indCatList = getRateIndustryCategoryList(
				request, RateCommonConstants.RATE_QUOTATION);
		if (!CGCollectionUtils.isEmpty(indCatList)) {
			for (RateIndustryCategoryTO indTO : indCatList) {
				if (indTO.getRateIndustryCategoryId().equals(indCatId)) {
					indCatCode = indTO.getRateIndustryCategoryCode();
					break;
				}
			}
		}else {
			prepareActionMessage(request, new ActionMessage(
					RateErrorConstants.RATE_INDUSTRY_TYPE_DTLS_NOT_EXIST));
			LOGGER.warn("RateQuotationAction:: getDefaultQuotationCommonUIValues :: RATE INDUSTRY TYPE Details Does not exist");
		}
		
		LOGGER.trace("RateQuotationAction::getIndustryCatCode::END------------>:::::::");
		return indCatCode;

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listViewQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateQuotationAction::listViewQuotation::START------------>:::::::");
		RateQuotationListViewTO rateQuotationListViewTO = new RateQuotationListViewTO();
				// rateQuotationListViewTO.setRateQuotationType(RateQuotationConstants.NORMAL);
			getDefultListViewUIValues(request, rateQuotationListViewTO);
			((RateQuotationForm) form).setRateQuotationListViewTO(rateQuotationListViewTO);
			return mapping.findForward(RateQuotationConstants.LIST_VIEW);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getRateQuotationListViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateQuotationAction::getRateQuotationListViewDetails::START------------>:::::::");
		List<RateQuotationListViewTO> rqlvTOList = null;
		Integer userId = null;
		String fromDate = null;
		String toDate = null;
		String quotationNo = null;
		String status = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		String data = "";
		String type = "";
		Integer[] cityIds = null;
		String officeType = null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.USER_ID))){
				userId = Integer.parseInt(request.getParameter(RateQuotationConstants.USER_ID));
			}
			fromDate = request.getParameter(RateQuotationConstants.FROM_DATE);
			toDate = request.getParameter(RateQuotationConstants.TO_DATE);
			quotationNo = request.getParameter(RateQuotationConstants.QUOT_NUMBER);
			status = request.getParameter(RateQuotationConstants.STATUS);
			type = request.getParameter(RateQuotationConstants.TYPE);
			officeType = request.getParameter("ofcType");

			rateQuotationService = getRateQuotationService();

			if(type.equalsIgnoreCase(RateQuotationConstants.EMP_TYPE_E)){
				
				rqlvTOList = rateQuotationService.rateQuotationListViewSEDetails(
						userId, fromDate, toDate, quotationNo, status);
			}
			else{
			
			if (!StringUtil.isStringEmpty(request.getParameter(RateQuotationConstants.CITY))) {
				String[] cityList = request.getParameter(RateQuotationConstants.CITY).split(RateQuotationConstants.RATE_COMMA);
				cityIds = new Integer[cityList.length];
				for (int i = 0; i < cityList.length; i++) {
					if(!StringUtil.isStringEmpty(cityList[i])){
						cityIds[i] = Integer.parseInt(cityList[i]);
					}
				}
			}
			String[] regionList = request.getParameter(RateQuotationConstants.REGION).split(RateQuotationConstants.RATE_COMMA);
			Integer[] regionIds = new Integer[regionList.length];
			for (int i = 0; i < regionList.length; i++) {
				if(!StringUtil.isStringEmpty(regionList[i])){
					regionIds[i] = Integer.parseInt(regionList[i]);
				}
			}
			rqlvTOList = rateQuotationService.rateQuotationListViewDetails(
					userId, regionIds, cityIds, fromDate, toDate, quotationNo,
					status, type, officeType);
			}
			
			if (!CGCollectionUtils.isEmpty(rqlvTOList)){
				data = serializer.toJSON(rqlvTOList).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getRateQuotationListViewDetails::END------------>:::::::");

	}

	/**
	 * @param request
	 * @param rateQuotationListViewTO
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private void getDefultListViewUIValues(HttpServletRequest request,
			RateQuotationListViewTO rateQuotationListViewTO){

		ActionMessage actionMessage=null;

		try {
			LOGGER.trace("RateQuotationAction::getDefultListViewUIValues::START------------>:::::::");
			String empType = request.getParameter(RateQuotationConstants.EMP);
			request.setAttribute(RateQuotationConstants.EMP_TYPE, empType);

			rateQuotationService = getRateQuotationService();

			rateQuotationListViewTO.setFromDate(DateUtil
					.getCurrentDateInDDMMYYYY());
			rateQuotationListViewTO.setToDate(DateUtil
					.getCurrentDateInDDMMYYYY());
			request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
			request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);
			UserTO userTo = getLoginUserTO(request);
			rateQuotationListViewTO.setUserId(userTo.getUserId());
			OfficeTO ofcTo = new OfficeTO();
			ofcTo = getLoginOfficeTO(request);
			if(!StringUtil.isNull(ofcTo) && !StringUtil.isEmptyInteger(ofcTo.getOfficeId())){
			rateQuotationListViewTO.setOfficeType(ofcTo.getOfficeTypeTO().getOffcTypeCode());
			CityTO loginCityTO = new CityTO();
			loginCityTO.setCityId(ofcTo.getCityId());
			loginCityTO = rateQuotationService.getCity(loginCityTO);
			if (empType.equals(RateQuotationConstants.EMP_TYPE_E)) {
				String ofcTypeCode = ofcTo.getOfficeTypeTO().getOffcTypeCode();
				if(ofcTypeCode.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
					rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
				}
				else{
					Integer loggedInRHO = ofcTo.getReportingRHO();
					if(!StringUtil.isEmptyInteger(loggedInRHO)){
					ofcTo = rateQuotationService.getOfficeDetails(loggedInRHO);
					rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
					rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
					}else{
						rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());	
					}
				}
				rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
				rateQuotationListViewTO.setCityName(loginCityTO.getCityName());

			} else if (empType.equals(RateQuotationConstants.EMP_TYPE_C)) {
				List<OfficeTO> ofcList = null;
				String ofcTypeCode = ofcTo.getOfficeTypeTO().getOffcTypeCode();
					if(ofcTypeCode.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){

						ofcList = getLoginUserRHOOffice(request);
	
						if (!CGCollectionUtils.isEmpty(ofcList)) {
							//session.setAttribute(RateQuotationConstants.REGION_ALL_LIST, ofcList);
							if (ofcList.size() > 1) {
								request.setAttribute(RateQuotationConstants.REGION_ALL_LIST, ofcList);
							} else {
								rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
								rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
							}
							
							List<CityTO> cList = getCityListOfReportedOfficesOfRHO(ofcList);
							
							if (cList.size() > 1) {
								request.setAttribute(RateQuotationConstants.STATION_REG_LIST, cList);
							}else{
								rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
								rateQuotationListViewTO.setCityName(loginCityTO.getCityName());
							}
						}else{
							prepareActionMessage(request, new ActionMessage(
									RateErrorConstants.DETAILS_DOES_NOT_EXIST,
									RateErrorConstants.REGIONAL_OFC_DTLS_NOT_EXIST));
							LOGGER.warn("RateQuotationAction:: getDefultListViewUIValues :: Regional Ofc Details Does not exist");
						}
					}else if(ofcTypeCode.equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
						List<OfficeTO> rhoOfcList = getAllRegionalOffices(request);
						if(!CGCollectionUtils.isEmpty(rhoOfcList)){
							if(rhoOfcList.size() > 1){
								request.setAttribute(RateQuotationConstants.REGION_ALL_LIST, rhoOfcList);
								request.setAttribute(RateQuotationConstants.STATION_REG_LIST, rhoOfcList);
							}else{
								rateQuotationListViewTO.setRegionalName(rhoOfcList.get(0).getOfficeName());
								rateQuotationListViewTO.setRegionOfcId(rhoOfcList.get(0).getOfficeId());
								List<CityTO> cList = getCityListOfReportedOfficesOfRHO(rhoOfcList);
								
								if (cList.size() > 1) {
									request.setAttribute(RateQuotationConstants.STATION_REG_LIST, cList);
								}else{
									rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
									rateQuotationListViewTO.setCityName(loginCityTO.getCityName());
								}
							}
						}
						
					}else{
						Integer loggedInRHO = ofcTo.getReportingRHO();
						if(!StringUtil.isEmptyInteger(loggedInRHO)){
						ofcTo = rateQuotationService.getOfficeDetails(loggedInRHO);
						rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
						rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
						}else{
							rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());	
						}
						rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
						rateQuotationListViewTO.setCityName(loginCityTO.getCityName());
					}
			}
			}else{
				prepareActionMessage(request, new ActionMessage(
						RateErrorConstants.DETAILS_DOES_NOT_EXIST,
						RateErrorConstants.USER_OFC_DTLS_NOT_EXIST));
				LOGGER.warn("RateQuotationAction:: getDefultListViewUIValues :: Regional Ofc Details Does not exist");
				}
			getStandardType(request,RateQuotationConstants.QUOTATION_LIST_V_STATUS,RateQuotationConstants.STATUS_LIST);	
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in listViewRateQuotation of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in listViewRateQuotation of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in listViewRateQuotation of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
			
		} 
		LOGGER.trace("RateQuotationAction::getDefultListViewUIValues::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getStationsByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.trace("RateQuotationAction::getStationsByRegion::START------------>:::::::");
		List<CityTO> cityTOList = null;

		Integer region = null;
		PrintWriter out = null;
		out = response.getWriter();

		String data = "";
		try {
			serializer = CGJasonConverter.getJsonObject();
			
			region = Integer.parseInt(request.getParameter(RateQuotationConstants.REGION));

			cityTOList = getCityListOfReportedOffices(region);

			if (!CGCollectionUtils.isEmpty(cityTOList)){
				data = serializer.toJSON(cityTOList).toString();
			}else{
				data = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.CITY_DETAILS_NOT_FOUND,
								null));
				}
				
		}  catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::saveOrUpdateBasicInfo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::getStationsByRegion::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listViewRateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateQuotationAction::listViewRateQuotation::START------------>:::::::");
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		String url = null;
		String quotationType="";
		String page = "";
		
		
			if(!StringUtil.isNull(request.getParameter(RateQuotationConstants.PAGE))){
			page = request.getParameter(RateQuotationConstants.PAGE);
			request.setAttribute(RateQuotationConstants.LIST_VIEW_PAGE, page);
			}
			if(!StringUtil.isNull(request.getParameter(RateQuotationConstants.EMP_TYPE))){
				request.setAttribute(RateQuotationConstants.EMP_TYPE, request.getParameter(RateQuotationConstants.EMP_TYPE));
			}
			if(!StringUtil.isNull(request.getParameter("approverLevel"))){
				request.setAttribute("approverLevel", request.getParameter("approverLevel"));
			}
			if (request.getParameter(RateQuotationConstants.TYPE).equals(RateQuotationConstants.NORMAL)) {
				rateQuotationTO
						.setRateQuotationType(RateQuotationConstants.NORMAL);
				rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_Q);
				quotationType = RateQuotationConstants.NORMAL;
				getCodChagreValue(request, rateQuotationTO, "N", "normalCODList");
				url = RateQuotationConstants.VIEW_NORMAL_QUOTATION;
			} else if (request.getParameter(RateQuotationConstants.TYPE).equals(RateQuotationConstants.ECOMMERCE)) {
				rateQuotationTO
						.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
				rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_Q);
				quotationType = RateQuotationConstants.ECOMMERCE;
				getCodChagreValue(request, rateQuotationTO, "E", "ecommerceCODList");
				url = RateQuotationConstants.VIEW_ECOMMERCE_QUOTATION;
			}
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, quotationType);
			getDefaultViewQuotationUIValues(request, rateQuotationTO);
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.RATE_QUOTATION_NO))){
				rateQuotationTO.setRateQuotationNo(request
						.getParameter(RateQuotationConstants.RATE_QUOTATION_NO));
			}
			((RateQuotationForm) form).setTo(rateQuotationTO);

		LOGGER.trace("RateQuotationAction::listViewRateQuotation::END------------>:::::::");

		return mapping.findForward(url);
	}
	
	/**
	 * View approve domestic quotation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewApproveDomesticQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateQuotationAction::viewApproveDomesticQuotation::START------------>:::::::");
		
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session.getAttribute(RateQuotationConstants.USER_INFO);
			// String effectiveFrom = DateUtil.getCurrentDateInDDMMYYYY();
			/*Here the effectiveFrom is set to double spaces. The reason is to bypass the validation applied on screen
			If it is set to a single space then it will give an alert saying that date cannot be null*/
			String effectiveFrom = CommonConstants.SPACE+CommonConstants.SPACE;
			String effectiveTo = DateUtil.getCurrentDateInDDMMYYYY();
			//String quotationNo = "";
			
			rateQuotationTO.setFromDate(effectiveFrom);
			rateQuotationTO.setToDate(effectiveTo);
			Integer empId = userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId();
			rateQuotationTO.setUserId(empId);
			getDefaultApproveQuotationUIValues(request, rateQuotationTO, userInfoTO.getUserto().getUserId());
			((RateQuotationForm) form).setTo(rateQuotationTO);	
			
		
		
		LOGGER.trace("RateQuotationAction::viewApproveDomesticQuotation::END------------>:::::::");

		return mapping.findForward(RateQuotationConstants.APPROVE_RATE);
	}
	
	/**
	 * Sets the status drop down.
	 *
	 * @param request the request
	 * @param rateQuotationTO the rate quotation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void setStatusDropDown(HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RateQuotationAction::setStatusDropDown::START------------>:::::::");
		ActionMessage actionMessage=null;
		try{
			rateQuotationService = getRateQuotationService();
		List<StockStandardTypeTO> stockStandardTypeTOList = rateQuotationService
				.getStandardType(RateQuotationConstants.QUOTATION_STATUS);
		request.setAttribute(RateQuotationConstants.QUOT_STATUS, stockStandardTypeTOList);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in setStatusDropDown of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in setStatusDropDown of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in setStatusDropDown of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::setStatusDropDown::END------------>:::::::");
	}
	
	/**
	 * Search quotation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked", "unused" })
	public void searchApproveQuotations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		LOGGER.trace("RateQuotationAction::searchApproveQuotations::START------------>:::::::");
		List<RateQuotationListViewTO> rqlvTOList = null;
		//Integer empId = null;
		String fromDate = null;
		String toDate = null;
		String quotationNo = null;
		String status = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		String data = "";
		String type = "";
		HttpSession session = null;
		List<RateQuotationListViewTO> rateQuotatnViewTOList = null;
		String isEQApprover = CommonConstants.NO;
		UserInfoTO  userInfoTO = null;
		Integer userRegionId = null;
		try {
			session = request.getSession(Boolean.FALSE);
			serializer = CGJasonConverter.getJsonObject();
			/*if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.EMP_ID))){
				empId = Integer.parseInt(request.getParameter(RateQuotationConstants.EMP_ID));
			}*/
			
			/*From date has is set to EPOCH date. This has been done so that when the user clicks on Approve Quotation screen 
			the user should be able to see all the pending quotations until now & not just the quotations made on the current date*/
			fromDate = request.getParameter(RateQuotationConstants.FROM_DATE);
			if(StringUtil.isStringEmpty(fromDate.trim())) {
				/*To set from date equals to EPOCH date. The quotations will be searched from
				EPOCH date till the current date*/
				fromDate = "01/01/1970"; 
			}
			toDate = request.getParameter(RateQuotationConstants.TO_DATE);
			quotationNo = request.getParameter(RateQuotationConstants.QUOT_NUMBER);
			status = request.getParameter(RateQuotationConstants.STATUS);
			type = request.getParameter(RateQuotationConstants.TYPE);
			isEQApprover = request.getParameter(RateQuotationConstants.IS_ECOMMERCE__APPROVER);
			
			if(isEQApprover.equals(CommonConstants.YES)){
				session = (HttpSession) request.getSession(false);
				userInfoTO = (UserInfoTO) session.getAttribute(RateQuotationConstants.USER_INFO);
				if(!userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					userRegionId = userInfoTO.getOfficeTo().getRegionTO().getRegionId();
				}
			}

			rateQuotationService = getRateQuotationService();
			
			if( StringUtil.isStringEmpty(type) && !StringUtil.isNull((String)session.getAttribute(RateQuotationConstants.RATE_APPROVER))){
				type = (String)session.getAttribute(RateQuotationConstants.RATE_APPROVER);
			}
			
			if(!StringUtil.isStringEmpty(type)){
				if(type.equals(RateQuotationConstants.TYPE_R)){
					List<RegionRateBenchMarkDiscountTO> rrbmdTOList = null;
					rrbmdTOList = (List<RegionRateBenchMarkDiscountTO>)session.getAttribute(RateQuotationConstants.APPROVER_REGION_DSC_LIST);
				
					if(!CGCollectionUtils.isEmpty(rrbmdTOList)){
						rateQuotatnViewTOList = rateQuotationService.searchQuotationForRegional(rrbmdTOList, fromDate, toDate, status, quotationNo, isEQApprover, userRegionId);
					}else{
						if(!StringUtil.isEmptyInteger(userRegionId)){
							rateQuotatnViewTOList = rateQuotationService.searchQuotationForRegional(null, fromDate, toDate, status, quotationNo, isEQApprover, userRegionId);
						}
					}
				}else if(type.equals(RateQuotationConstants.TYPE_C)){
					List<Integer> rateIndustryCategryIdList = null;
					rateIndustryCategryIdList = (List<Integer>)session.getAttribute(RateQuotationConstants.APPROVER_BENCH_MARK_IND_CAT_LIST);
					
					if(!CGCollectionUtils.isEmpty(rateIndustryCategryIdList)){
						rateQuotatnViewTOList = rateQuotationService.searchQuotationForCorp(rateIndustryCategryIdList, fromDate, toDate, status, quotationNo, isEQApprover);
					}else{
						if(StringUtil.isEmptyInteger(userRegionId)){
							rateQuotatnViewTOList = rateQuotationService.searchQuotationForCorp(null, fromDate, toDate, status, quotationNo, isEQApprover);
						}
					}
				}
			}else{
				if(StringUtil.isEmptyInteger(userRegionId)){
					rateQuotatnViewTOList = rateQuotationService.searchQuotationForCorp(null, fromDate, toDate, status, quotationNo, isEQApprover);
				}else{
					rateQuotatnViewTOList = rateQuotationService.searchQuotationForRegional(null, fromDate, toDate, status, quotationNo, isEQApprover, userRegionId);
				}
			}
			
			

			
			if (!CGCollectionUtils.isEmpty(rateQuotatnViewTOList)){
				data = serializer.toJSON(rateQuotatnViewTOList).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::searchApproveQuotations()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::searchApproveQuotations()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::searchApproveQuotations()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::searchApproveQuotations::END------------>:::::::");

	}
	
	
	
	/**
	 * Approve quotation.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws IOException 
	 */
	public void approveQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.trace("RateQuotationAction::approveQuotation::START------------>:::::::");
		String jsonResult = "";
		java.io.PrintWriter out=null;
		out = response.getWriter();
		
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session  = request.getSession(Boolean.FALSE);
			String selectdQuotationNos = request.getParameter(RateQuotationConstants.SELECTED_QUOT_NOS);
			String selectdRequirdApprovValues = request.getParameter(RateQuotationConstants.SELECTED_APPROVAL_REQUIRED);
			String opName = request.getParameter(RateQuotationConstants.OPERATION_NAME);
			
			
			String selectdQuotationNosArray[] = selectdQuotationNos.split(RateQuotationConstants.RATE_COMMA);
			String selectdApprovRquirdValueArray[] = selectdRequirdApprovValues.split(RateQuotationConstants.RATE_COMMA);
			String quoatatnsApprovRequirdOfTypeRO[] = new String[selectdQuotationNosArray.length];
			String quoatatnsApprovRequirdOfTypeRC[] = new String[selectdQuotationNosArray.length];
			int selAryLen = selectdApprovRquirdValueArray.length;
			for(int i=0, j =0,k=0;k<selAryLen;k++){
				if(selectdApprovRquirdValueArray[k].equalsIgnoreCase(RateQuotationConstants.APPROVAL_RO)){
					quoatatnsApprovRequirdOfTypeRO[i]=selectdQuotationNosArray[k];
					i++;
				}else if(selectdApprovRquirdValueArray[k].equalsIgnoreCase(RateQuotationConstants.APPROVAL_RC)){
					quoatatnsApprovRequirdOfTypeRC[j]=selectdQuotationNosArray[k];
					j++;
				}
			}
			
			String approver = (String)session.getAttribute(RateQuotationConstants.RATE_APPROVER);
			if(StringUtil.isStringEmpty(approver)){
				session = (HttpSession) request.getSession(false);
				userInfoTO = (UserInfoTO) session.getAttribute(RateQuotationConstants.USER_INFO);
				if(userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					approver = RateQuotationConstants.TYPE_C;
				}else{
					approver = RateQuotationConstants.TYPE_R;
				}
			}
			
			
			rateQuotationService = getRateQuotationService();
			boolean flag = rateQuotationService.approveRejectDomesticQuotation(quoatatnsApprovRequirdOfTypeRO,quoatatnsApprovRequirdOfTypeRC,opName,approver);
			if (!StringUtil.isNull(opName)
					&& opName.equalsIgnoreCase(
							RateQuotationConstants.APPROVE_RATE)) {
				if (!flag) {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									RateErrorConstants.NOT_APPROVED_SUCCESSFULLY,
									null));
				} else {
					jsonResult = prepareCommonException(
							FrameworkConstants.SUCCESS_FLAG,
							getMessageFromErrorBundle(
									request,
									RateErrorConstants.APPROVED_SUCCESSFULLY,
									null));
				}
			} else {
				if (!flag) {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									RateErrorConstants.NOT_REJECTED_SUCCESSFULLY,
									null));
				} else {
					jsonResult = prepareCommonException(
							FrameworkConstants.SUCCESS_FLAG,
							getMessageFromErrorBundle(
									request,
									RateErrorConstants.REJECTED_SUCCESSFULLY,
									null));
				}
			}

		
			
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::approveQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::approveQuotation()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::approveQuotation()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateQuotationAction::approveQuotation::END------------>:::::::");
		//return null;
	}

	/**
	 * @param request
	 * @param rateQuotationTO
	 * @param userId 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void getDefaultApproveQuotationUIValues(HttpServletRequest request,
			RateQuotationTO rateQuotationTO, Integer userId){
		ActionMessage actionMessage=null;
		List<Integer> rateIndustryCategryIdList = null;
		List<RegionRateBenchMarkDiscountTO> rrbmdTOList = null;
		String type="";
		boolean isApprover = Boolean.FALSE; 
		try{
			LOGGER.trace("RateQuotationAction::getDefaultApproveQuotationUIValues::START------------>:::::::");
		HttpSession session = null;
		session = (HttpSession) request.getSession(false);
		request.setAttribute(RateQuotationConstants.FROM_DATE, DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute(RateQuotationConstants.TO_DATE, DateUtil.getCurrentDateInDDMMYYYY());
		
		type = (String)session.getAttribute(RateQuotationConstants.RATE_APPROVER);
		
		rateQuotationService = getRateQuotationService();
		
		//checks if login employee is a regional approver
		rrbmdTOList = (List<RegionRateBenchMarkDiscountTO>)session.getAttribute(RateQuotationConstants.APPROVER_REGION_DSC_LIST);
		
		if(CGCollectionUtils.isEmpty(rrbmdTOList)){
		rrbmdTOList = rateQuotationService.checkEmpIdRegionalApprovr(rateQuotationTO.getUserId()); // employee id is passed here as a parameter
		if(!CGCollectionUtils.isEmpty(rrbmdTOList)){
		session.setAttribute(RateQuotationConstants.APPROVER_REGION_DSC_LIST, rrbmdTOList);
		type = RateQuotationConstants.TYPE_R;
		}
		/*if(!CGCollectionUtils.isEmpty(rrbmdTOList)){
			rateQuotatnViewTOList = rateQuotationService.searchQuotationForRegional(rrbmdTOList, effectiveFrom, effectiveTo, quotationNo);
		}*/
		}
		if(CGCollectionUtils.isEmpty(rrbmdTOList)){
			rateIndustryCategryIdList = (List<Integer>)session.getAttribute(RateQuotationConstants.APPROVER_BENCH_MARK_IND_CAT_LIST);
			//checks if login employee is a corp approver 
			if(CGCollectionUtils.isEmpty(rateIndustryCategryIdList)){
			rateIndustryCategryIdList = rateQuotationService.checkEmpIdCorpApprovr(rateQuotationTO.getUserId());
			if(!CGCollectionUtils.isEmpty(rateIndustryCategryIdList)){
			session.setAttribute(RateQuotationConstants.APPROVER_BENCH_MARK_IND_CAT_LIST, rateIndustryCategryIdList);
			type = RateQuotationConstants.TYPE_C;
			}
			}
		}
		
		if(StringUtil.isStringEmpty(type)){
			UserInfoTO userInfoTO = getLoginUserInfoTO(request);
			if(userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
				type = RateQuotationConstants.TYPE_C;
			}else{
				type = RateQuotationConstants.TYPE_R;
			}
		}
		
		session.setAttribute(RateQuotationConstants.RATE_APPROVER, type);
		
		// Check whether the logged in employee is an approver for ecomm quotation. Currently
		isApprover = rateQuotationService.isEcommerceQuotationApprover(userId, "ECQA");
		if(isApprover){
			request.setAttribute(RateQuotationConstants.IS_ECOMMERCE__APPROVER, CommonConstants.YES);
		}else{
			request.setAttribute(RateQuotationConstants.IS_ECOMMERCE__APPROVER, CommonConstants.NO);
		}
		
		if(StringUtil.isNull(rateIndustryCategryIdList)&&StringUtil.isNull(rrbmdTOList) && (isApprover == Boolean.FALSE)){
			request.setAttribute(RateQuotationConstants.MESSAGE, RateQuotationConstants.QUOTATION_APPROVE_NOT_AUTHORIZED);
		}
		request.setAttribute(RateQuotationConstants.RATE_APPROVER, type);
		setStatusDropDown(request);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefaultApproveQuotationUIValues of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefaultApproveQuotationUIValues of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefaultApproveQuotationUIValues of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::getDefaultApproveQuotationUIValues::END------------>:::::::");
	}
	
	/**
	 * @param request
	 * @param rateQuotationTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	protected void getDefaultViewQuotationUIValues(HttpServletRequest request,
			RateQuotationTO rateQuotationTO) {
		ActionMessage actionMessage=null;
		try {
			LOGGER.trace("RateQuotationAction::getDefaultViewQuotationUIValues::START------------>:::::::");
			String salesType = null;
			Integer userEmpId =   Integer.parseInt(request.getParameter(RateQuotationConstants.USER_EMP_ID));
			Integer salesUserEmpId =   Integer.parseInt(request.getParameter(RateQuotationConstants.SALES_USER_EMP_ID));
			if(userEmpId.intValue() == salesUserEmpId.intValue()){
			salesType = RateQuotationConstants.SALES_TYPE_E;
			request.setAttribute(RateQuotationConstants.RATE_QUOT_SALES_TYPE, salesType);
			}else{
				salesType = RateQuotationConstants.SALES_TYPE_C;
				request.setAttribute(RateQuotationConstants.RATE_QUOT_SALES_TYPE, salesType);
			}
			
			String module = RateQuotationConstants.VIEW;
			request.setAttribute(RateQuotationConstants.QUOT_MODULE, module);
			rateQuotationTO.setModule(module);
			
			String createdDate = DateUtil.getCurrentDateInYYYYMMDDHHMM();
			rateQuotationTO.setCreatedDate(createdDate);
			request.setAttribute(RateQuotationConstants.CREATED_DATE, createdDate);
			Integer userId = Integer.parseInt(request.getParameter(RateQuotationConstants.QUOT_USER_ID));
			request.setAttribute(RateQuotationConstants.CREATED_BY, userId);
			String loginOfficeName = request.getParameter(RateQuotationConstants.SALES_OFFICE_NAME);
			
			rateQuotationService = getRateQuotationService();
			String office = loginOfficeName;
			String loginRegionName = request.getParameter(RateQuotationConstants.REGIONAL_NAME);;
			request.setAttribute(RateQuotationConstants.OFFICE, office);
			request.setAttribute(RateQuotationConstants.LOGIN_REGION_NAME, loginRegionName);
			OfficeTO ofcTO = getOfficeByempId(Integer.parseInt(request.getParameter(RateQuotationConstants.USER_EMP_ID)));
			request.setAttribute(RateQuotationConstants.LOGIN_CITY_ID, ofcTO.getCityId());
			CityTO loginCityTO = new CityTO();
			loginCityTO.setCityId( ofcTO.getCityId());
			loginCityTO = rateQuotationService.getCity(loginCityTO);
			String stateId = loginCityTO.getState().toString();
			request.setAttribute(RateQuotationConstants.STATE_ID, stateId);
			request.setAttribute(RateQuotationConstants.CITY_NAME, request.getParameter(RateQuotationConstants.CITY_NAME));
			
			EmployeeUserTO employeeUserTO = null;
				employeeUserTO = rateQuotationService
					.getEmployeeUser(userId);
			String employeeName = employeeUserTO.getEmpTO().getFirstName()
					+ CommonConstants.HYPHEN
					+ employeeUserTO.getEmpTO().getLastName();
			request.setAttribute(RateQuotationConstants.EMPLOYEE_NAME, employeeName);
			Integer employeeId = employeeUserTO.getEmpTO().getEmployeeId();
			request.setAttribute(RateQuotationConstants.EMPLOYEE_ID, employeeId);
			request.setAttribute(RateQuotationConstants.RATE_GOVERNMENT,
					RateQuotationConstants.GOVERNMENT);
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.APPROVER))) {
			rateQuotationTO.setApprover(request.getParameter(RateQuotationConstants.APPROVER));
			}
			compositePrintUrl(request);
			getDefaultQuotationCommonUIValues(request, rateQuotationTO);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefaultViewQuotationUIValues of RateQuotationAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefaultViewQuotationUIValues of RateQuotationAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefaultViewQuotationUIValues of RateQuotationAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateQuotationAction::getDefaultViewQuotationUIValues::START------------>:::::::");
	}
	
	@SuppressWarnings("unchecked")
	private List<CustomerGroupTO> getcustomerGroup(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {

		List<CustomerGroupTO> custGroupTOList = null;
		HttpSession session = request.getSession(false);
		String custGroup = RateQuotationConstants.CUST_GROUP;
		custGroupTOList = (List<CustomerGroupTO>) session
					.getAttribute(custGroup);
			if (CGCollectionUtils.isEmpty(custGroupTOList)) {
				rateQuotationService = getRateQuotationService();
				custGroupTOList = rateQuotationService.getcustomerGroup();
				if (!CGCollectionUtils.isEmpty(custGroupTOList)) {
					session.setAttribute(custGroup, custGroupTOList);
				}
			} 
		return custGroupTOList;
	}
	
	private void setUpLeadsValuesToQuotation(HttpServletRequest request, RateQuotationTO rqTO) throws CGBusinessException, CGSystemException{
		
		String customerName = request.getParameter(RateQuotationConstants.LEAD_CUSTOMER_NAME);
        String leadNumber = request.getParameter(RateQuotationConstants.LEAD_NUMBER);
        String contactPerson = request.getParameter(RateQuotationConstants.LEAD_CONTACT_PERSON);
        String contactNo = request.getParameter(RateQuotationConstants.LEAD_CONTACT_NO);
        String mobileNo = request.getParameter(RateQuotationConstants.LEAD_MOBILE_NO);
        String address1 = request.getParameter(RateQuotationConstants.LEAD_ADDRESS_ONE);
        String address2 = request.getParameter(RateQuotationConstants.LEAD_ADDRESS_TWO);
        String address3 = request.getParameter(RateQuotationConstants.LEAD_ADDRESS_THREE);
        //String city = request.getParameter("city");
        String pincode = request.getParameter(RateQuotationConstants.LEAD_PINCODE);
        String designation = request.getParameter(RateQuotationConstants.LEAD_DESIGNATION);
        String email = request.getParameter(RateQuotationConstants.LEAD_EMAIL);
        String indTypeCode = request.getParameter(RateQuotationConstants.LEAD_IND_TYPE_CODE);
        Integer industryTypeId = getIndustryTypeId(indTypeCode, request);
        
        if(!StringUtil.isEmptyInteger(industryTypeId)){
        	rqTO.getCustomer().setIndustryType(industryTypeId+CommonConstants.TILD+indTypeCode);
        	request.setAttribute(RateQuotationConstants.IND_TYPE_CODE, industryTypeId+CommonConstants.TILD+indTypeCode);
        }
        
       
        
        rqTO.getCustomer().setBusinessName(customerName);
        rqTO.getCustomer().setLegacyCustomerCode(leadNumber);
        rqTO.getCustomer().getPrimaryContact().setName(contactPerson);
        rqTO.getCustomer().getPrimaryContact().setContactNo(contactNo);
        rqTO.getCustomer().getPrimaryContact().setMobile(mobileNo);
        rqTO.getCustomer().getPrimaryContact().setDesignation(designation);
        rqTO.getCustomer().getPrimaryContact().setEmail(email);
        rqTO.getCustomer().getAddress().setAddress1(address1);
        rqTO.getCustomer().getAddress().setAddress2(address2);
        rqTO.getCustomer().getAddress().setAddress3(address3);
		
		PincodeTO pincodeTO = new PincodeTO(); 
		pincodeTO.setPincode(pincode);
		pincodeTO = validatePincode(pincodeTO);
	
		if(!StringUtil.isNull(pincodeTO)){
			CityTO cityTO = null;// new CityTO(); 
			cityTO = getCityTOByPincode(pincode);
			
			rqTO.getCustomer().getAddress().getPincode().setPincodeId(pincodeTO.getPincodeId());
			rqTO.getCustomer().getAddress().getPincode().setPincode(pincodeTO.getPincode());
			rqTO.getCustomer().getAddress().getCity().setCityId(cityTO.getCityId());
			rqTO.getCustomer().getAddress().getCity().setCityName(cityTO.getCityName());
		}
		
	}
	

	
	@SuppressWarnings("unchecked")
	private List<RateIndustryTypeTO> getRateIndustryTypeList(
			HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
	
		List<RateIndustryTypeTO> industryTypeToList = null;
		HttpSession session=request.getSession(false);
		String listElement = "rateIndTypeList";
		
		industryTypeToList = new ArrayList<RateIndustryTypeTO>();
		industryTypeToList = (List<RateIndustryTypeTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(industryTypeToList)) {
				rateCommonService = getRateCommonService();
				industryTypeToList  = rateQuotationService.getRateIndustryType();
			if(!CGCollectionUtils.isEmpty(industryTypeToList)){
				session.setAttribute(listElement, industryTypeToList);
				}
			}
			return industryTypeToList;
		}
	
	
	private Integer getIndustryTypeId(String indTypeCode, HttpServletRequest request) throws CGBusinessException, CGSystemException{
		
		Integer indTypeId = null;
		
		List<RateIndustryTypeTO> indTypeList = getRateIndustryTypeList(request);
		if(!CGCollectionUtils.isEmpty(indTypeList)){
			for(RateIndustryTypeTO typeTO : indTypeList){
				if(typeTO.getRateIndustryTypeCode().equals(indTypeCode)){
					indTypeId = typeTO.getRateIndustryTypeId();
				}
			}
		}
		
		
		return indTypeId;
		
	}

	private List<RateQuotationCODChargeTO> prepareCODCharges(HttpServletRequest request, RateQuotaionFixedChargesTO fixedChargesTO){
		List<RateQuotationCODChargeTO> codList = null;
		if (!(StringUtil.isNull(fixedChargesTO.getCodChargesChk()))) {
			if (fixedChargesTO.getCodChargesChk().equals(RateQuotationConstants.ON)) {
		
				codList = new ArrayList<RateQuotationCODChargeTO>();
				int fixedChrgsLen = fixedChargesTO.getCodChargeId().length;
				for (int i = 1; i <= fixedChrgsLen; i++) {
					String selected = request
							.getParameter(RateQuotationConstants.FIXED_CHRGS_TYPE
									+ i);
					if (selected.equals(RateQuotationConstants.FIXED_PERCENT)) {
						RateQuotationCODChargeTO codTO = new RateQuotationCODChargeTO();
						codTO.setConsiderFixed(CommonConstants.YES);
						codTO.setCodChargeId(fixedChargesTO.getCodChargeId()[i - 1]);
						codTO.setFixedChargeValue(fixedChargesTO
								.getFixedChargesEco()[i - 1]);
						codTO.setPercentileValue(fixedChargesTO.getCodPercent()[i - 1]);
						codList.add(codTO);
		
					} else if (selected.equals(RateQuotationConstants.COD_PERCENT)) {
						RateQuotationCODChargeTO codTO = new RateQuotationCODChargeTO();
						codTO.setConsideeHigherFixedPercent(CommonConstants.YES);
						codTO.setCodChargeId(fixedChargesTO.getCodChargeId()[i - 1]);
						codTO.setPercentileValue(fixedChargesTO.getCodPercent()[i - 1]);
						codTO.setFixedChargeValue(fixedChargesTO
								.getFixedChargesEco()[i - 1]);
						codList.add(codTO);
					}
				}
			}
		}				
		return codList;
	}
	
	
	public ActionForward fileUploadRateQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateQuotationAction::fileUploadRateQuotation::START------------>:::::::");
		
		
			
			String jobNumber = "";
			String returnPage = null;
		
			OfficeTO loggedInOffice = null;
			RateQuotationForm rateQuotationForm = null;
			try {
				rateQuotationForm = (RateQuotationForm) form;
				RateQuotationTO rateQuotationTO = (RateQuotationTO) rateQuotationForm.getTo();
				
				FormFile formFile= rateQuotationTO.getQuotationUploadFile();
				if(formFile != null && formFile.getFileSize() > 0) {
					loggedInOffice = getLoginOfficeTO(request);
					final String filePath = getServlet().getServletContext().getRealPath(File.separator);
					
					rateQuotationService = getRateQuotationService();
					
					jobNumber = getJobNumber(request);
					
					
					rateQuotationService.proceedUploadRateQuotation(rateQuotationTO, loggedInOffice, filePath, jobNumber);					
					
					request.setAttribute("jobNumber", jobNumber);
					returnPage = "rateQuotationStatusView";
				} else {
					returnPage = "loginPage";
				}
			} catch (Exception e) {
				LOGGER.error("Exception Occured in::RateQuotationAction::fileUploadRateQuotation() :: ",e);
			}
			LOGGER.debug("RateQuotationAction::fileUploadRateQuotation::END------------>:::::::");
			
			
			
		
		LOGGER.trace("RateQuotationAction::fileUploadRateQuotation::END------------>:::::::");

		return mapping.findForward(returnPage);	
	}
	
	public ActionForward viewRateQuotationUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
			LOGGER.trace("RateQuotationAction::viewRateQuotationUpload::START------------>:::::::");
			RateQuotationTO rateQuotationTO = new RateQuotationTO();
			
			((RateQuotationForm) form).setTo(rateQuotationTO);

		
		LOGGER.trace("RateQuotationAction::viewRateQuotationUpload::END------------>:::::::");

		return mapping.findForward("viewRateQuotationUpload");
	}
	
	private String getJobNumber(HttpServletRequest request) {
		/*UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);*/
		JobServicesTO jobTO = new JobServicesTO();
		String jobNumber = null;
		jobTO.setProcessCode("QUOTATION");
		jobTO.setJobStatus("I");
		jobTO.setCreatedDate(new Date());
		jobTO.setFileSubmissionDate(new Date());
		jobTO.setRemarks("Jobs service has initiated");
		//jobTO.setUpdateDate(updateDate)
		//jobTO.setCreatdBy(userInfoTO.getUserto().getUserId());
		//jobTO.setUpdateBy(userInfoTO.getUserto().getUserId());
		try {
			JobServicesUniversalService jobService = (JobServicesUniversalService) getBean("jobServicesUniversalService");
			jobTO = jobService.saveOrUpdateJobService(jobTO);
			jobNumber = jobTO.getJobNumber();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("RateQuotationAction::getJobNumber::error::",e);
		}
		
		return jobNumber;
	}
	
	public String getLoginOfficeCode(HttpServletRequest request){
		
		String loginOfcCode = null;
		UserInfoTO userInfoTO = getLoginUserInfoTO(request);
		loginOfcCode = userInfoTO.getOfficeTo().getOfficeCode();
		return  loginOfcCode;
	}
}
