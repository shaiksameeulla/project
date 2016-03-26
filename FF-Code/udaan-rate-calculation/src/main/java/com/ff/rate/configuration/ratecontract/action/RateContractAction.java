package com.ff.rate.configuration.ratecontract.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
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
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratecontract.form.RateContractForm;
import com.ff.rate.configuration.ratecontract.service.RateContractService;
import com.ff.rate.configuration.ratequotation.action.RateQuotationAction;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.rate.configuration.ratequotation.service.RateQuotationService;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.to.ratemanagement.masters.ContractPaymentBillingLocationTO;
import com.ff.to.ratemanagement.masters.RateContractSpocTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.operations.ratecontract.RateCustomerSearchTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationCODChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;

/**
 * @author hkansagr
 */

public class RateContractAction extends RateQuotationAction {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RateContractAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The Rate Contract Service. */
	private RateContractService rateContractService;

	/**
	 * @param request
	 * @param rateQuotationListViewTO
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private void getDefultContractListViewUIValues(HttpServletRequest request,
			RateQuotationListViewTO rateQuotationListViewTO){
		LOGGER.trace("RateContractAction::getDefultContractListViewUIValues::START------------>:::::::");
		ActionMessage actionMessage = null;
		try {

			String empType = request.getParameter(RateQuotationConstants.EMP);
			request.setAttribute(RateQuotationConstants.EMP_TYPE, empType);

			rateContractService = getRateContractService();

			rateQuotationListViewTO.setFromDate(DateUtil
					.getCurrentDateInDDMMYYYY());
			rateQuotationListViewTO.setToDate(DateUtil
					.getCurrentDateInDDMMYYYY());

			UserTO userTo = getLoginUserTO(request);
			rateQuotationListViewTO.setUserId(userTo.getUserId());
			OfficeTO ofcTo = new OfficeTO();
			ofcTo = getLoginOfficeTO(request);
			rateQuotationListViewTO.setOfficeType(ofcTo.getOfficeTypeTO().getOffcTypeCode());
			CityTO loginCityTO = new CityTO();
			loginCityTO.setCityId(ofcTo.getCityId());
			loginCityTO = rateContractService.getCity(loginCityTO);

			if (empType.equals(RateQuotationConstants.EMP_TYPE_E)) {

				String ofcTypeCode = ofcTo.getOfficeTypeTO().getOffcTypeCode();
				if(ofcTypeCode.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
					rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
				}
				else{
					Integer loggedInRHO = ofcTo.getReportingRHO();
					if(!StringUtil.isEmptyInteger(loggedInRHO)){
					ofcTo = rateContractService.getOfficeDetails(loggedInRHO);
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

				//ofcList = (List<OfficeTO>) session
					//	.getAttribute(RateQuotationConstants.REGION_ALL_LIST);
				String ofcTypeCode = ofcTo.getOfficeTypeTO().getOffcTypeCode();
					if(ofcTypeCode.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){

					ofcList = getLoginUserRHOOffice(request);

					if (!CGCollectionUtils.isEmpty(ofcList)) {
						//session.setAttribute(RateQuotationConstants.REGION_ALL_LIST, ofcList);
						if (ofcList.size() > 1) {
							request.setAttribute(RateQuotationConstants.REGION_ALL_LIST, ofcList);
						} else {
							rateQuotationListViewTO.setRegionalName(ofcList.get(0).getOfficeName());
							rateQuotationListViewTO.setRegionOfcId(ofcList.get(0).getOfficeId());
						}
						
						List<CityTO> cList = getCityListOfReportedOfficesOfRHO(ofcList);
						
						if (cList.size() > 1) {
							request.setAttribute(RateQuotationConstants.STATION_REG_LIST, cList);
						}else{
							rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
							rateQuotationListViewTO.setCityName(loginCityTO.getCityName());
						}
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
						ofcTo = rateContractService.getOfficeDetails(loggedInRHO);
						rateQuotationListViewTO.setRegionOfcId(ofcTo.getOfficeId());
						rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());
						}else{
							rateQuotationListViewTO.setRegionalName(ofcTo.getOfficeName());	
						}
						rateQuotationListViewTO.setCityId(loginCityTO.getCityId());
						rateQuotationListViewTO.setCityName(loginCityTO.getCityName());
					}
			}
			getStandardType(request,RateContractConstants.CONTRACT_LIST_V_STATUS,RateContractConstants.STATUS_LIST);
		} catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefultContractListViewUIValues of RateContractAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefultContractListViewUIValues of RateContractAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefultContractListViewUIValues of RateContractAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage = new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
			}
		LOGGER.trace("RateContractAction::getDefultContractListViewUIValues::END------------>:::::::");
		
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getRateContractListViewDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::getRateContractListViewDetails::START------------>:::::::");
		List<RateQuotationListViewTO> rqlvTOList = null;
		Integer userId = null;
		String fromDate = null;
		String toDate = null;
		String contractNo = null;
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
			contractNo = request.getParameter(RateContractConstants.RATE_CONTRACT_NO);
			status = request.getParameter(RateQuotationConstants.STATUS);
			type = request.getParameter(RateQuotationConstants.TYPE);
			officeType = request.getParameter("ofcType");

			rateContractService = getRateContractService();

			if(type.equalsIgnoreCase(RateQuotationConstants.EMP_TYPE_E)){
				
				rqlvTOList = rateContractService.rateContractListViewSEDetails(
						userId, fromDate, toDate, contractNo, status);
			}
			else{
				
			if (!StringUtil.isStringEmpty(request.getParameter(RateQuotationConstants.CITY))) {
				String[] cityList = request.getParameter(RateQuotationConstants.CITY).split(RateQuotationConstants.RATE_COMMA);
				int cityLen = cityList.length;
				cityIds = new Integer[cityLen];
				for (int i = 0; i < cityLen; i++) {
					cityIds[i] = Integer.parseInt(cityList[i]);
				}
			}
			String[] regionList = request.getParameter(RateQuotationConstants.REGION).split(RateQuotationConstants.RATE_COMMA);
			int regionLen = regionList.length;
			Integer[] regionIds = new Integer[regionLen];
			for (int i = 0; i < regionLen; i++) {
				if(!StringUtil.isStringEmpty(regionList[i])){
					regionIds[i] = Integer.parseInt(regionList[i]);
				}
			}

			rqlvTOList = rateContractService.rateContractListViewDetails(
					userId, regionIds, cityIds, fromDate, toDate, contractNo,
					status, type, officeType);
			}
			if (!CGCollectionUtils.isEmpty(rqlvTOList)){
				data = serializer.toJSON(rqlvTOList).toString();
			}

		}catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateContractAction::getRateContractListViewDetails::END------------>:::::::");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listViewContract(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RateContractAction::listViewContract::START------------>:::::::");
		RateQuotationListViewTO rateQuotationListViewTO = new RateQuotationListViewTO();
		
			// rateQuotationListViewTO.setRateQuotationType(RateQuotationConstants.NORMAL);
			getDefultContractListViewUIValues(request, rateQuotationListViewTO);
			((RateContractForm) form).setRateQuotationListViewTO(rateQuotationListViewTO);

		LOGGER.debug("RateContractAction::listViewContract::END------------>:::::::");

		return mapping.findForward(RateContractConstants.LIST_VIEW);
	}

	/**
	 * To view Rate - Domestic Contract Normal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view Rate Contract Normal
	 * @throws Exception
	 */
	public ActionForward viewRateContractNormal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RateContractAction::viewRateContractNormal()::START");
		RateContractForm rateContractForm = new RateContractForm();
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		RateContractTO to = null;
			to = (RateContractTO) rateContractForm.getTo();
			String rateQuotationType = RateQuotationConstants.NORMAL;
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
			rateQuotationTO.setRateQuotationType(RateQuotationConstants.NORMAL);
			
			String rateContractType = RateContractConstants.NORMAL_CONTRACT;
			request.setAttribute(RateContractConstants.RATE_CONTRACT_TYPE, rateContractType);
			request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
			request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);
			getDefaultUIValues(request, rateQuotationTO);
			getDefaultUIValuesContract(request, to);
			getCodChagreValue(request, rateQuotationTO, "N", "normalCODList");
			to.setRateQuotationTO(rateQuotationTO);
			saveToken(request);
			((RateContractForm) form).setTo(to);
		LOGGER.trace("RateContractAction::viewRateContractNormal()::END");
		return mapping.findForward(RateContractConstants.VIEW_RATE_CONTRACT_NORMAL);
	}

	/**
	 * To view Rate - Domestic Contract ECommerce
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view Rate Contract ECommerce
	 * @throws Exception
	 */
	public ActionForward viewRateContractECommerce(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RateContractAction::viewRateContractECommerce()::START");
		RateContractForm rateContractForm = new RateContractForm();
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		RateContractTO to = null;
			to = (RateContractTO) rateContractForm.getTo();
			getDefaultUIValuesContract(request, to);
			String rateQuotationType = RateQuotationConstants.ECOMMERCE;
			request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
			String rateContractType = RateContractConstants.ECCOMERCE_CONTRACT;
			request.setAttribute(RateContractConstants.RATE_CONTRACT_TYPE, rateContractType);
			request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
			request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);
			rateQuotationTO
					.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
			getDefaultUIValues(request, rateQuotationTO);
			getCodChagreValue(request, rateQuotationTO, "E", "ecommerceCODList");
			saveToken(request);
			to.setRateQuotationTO(rateQuotationTO);
			((RateContractForm) form).setTo(to);
		
		LOGGER.debug("RateContractAction::viewRateContractECommerce()::END");
		return mapping
				.findForward(RateContractConstants.VIEW_RATE_CONTRACT_ECOMMERCE);
	}

	/**
	 * To get default UI value(s)
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	private void getDefaultUIValuesContract(HttpServletRequest request,
			RateContractTO to){
		LOGGER.debug("RateContractAction::getDefaultUIValuesContract()::START");
		
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(RateContractConstants.USER_INFO);
		UserTO userTO = userInfo.getUserto();
		String userType = request.getParameter(RateContractConstants.USER);
		to.setUserType(userType);
		request.setAttribute(RateContractConstants.USER_TYPE, userType);
		/* Common - Created By & Updated By User */
		if (!StringUtil.isNull(userTO)) {
			if (StringUtil.isEmptyInteger(to.getCreatedBy())) {
				to.setCreatedBy(userTO.getUserId());
			} else {
				to.setCreatedBy(to.getCreatedBy());
			}
			to.setUpdatedBy(userTO.getUserId());
		}
		Integer userId = userTO.getUserId();
		request.setAttribute(RateQuotationConstants.CREATED_BY, userId);

		/* Common Details */
		getDefaultCommonContractValues(request, to);

		/* Basic Info */
		getDefaultBasicDetails(request, to);

		/* Billing Details */
		getDefaultBillingDtls(request, to);

		/* Pickup/Delivery Details */
		getDefaultPickupDlvDtls(request, to);
		
		getStandardType(request, RateContractConstants.CONTRACT_COMPLAINT, RateContractConstants.COMPLAINT_LIST);
		
		LOGGER.debug("RateContractAction::getDefaultUIValuesContract()::END");
	}

	/**
	 * To get default common details
	 * 
	 * @param request
	 * @param to
	 */
	private void getDefaultCommonContractValues(HttpServletRequest request,
			RateContractTO to) {
		LOGGER.trace("RateContractAction::getDefaultCommonContractValues()::START");
		/*
		 * Rate Contract Status C- Created,S- Submitted,A- Active,I- Inactive,B-
		 * Blocked
		 */
		request.setAttribute(RateCommonConstants.PARAM_CONTRACT_CREATED,
				RateCommonConstants.CONTRACT_STATUS_CREATED);// C
		request.setAttribute(RateCommonConstants.PARAM_CONTRACT_SUBMITTED,
				RateCommonConstants.CONTRACT_STATUS_SUBMITTED);// S
		request.setAttribute(RateCommonConstants.PARAM_CONTRACT_ACTIVE,
				RateCommonConstants.CONTRACT_STATUS_ACTIVE);// A
		request.setAttribute(RateCommonConstants.PARAM_CONTRACT_INACTIVE,
				RateCommonConstants.CONTRACT_STATUS_INACTIVE);// I
		request.setAttribute(RateCommonConstants.PARAM_CONTRACT_BLOCKED,
				RateCommonConstants.CONTRACT_STATUS_BLOCKED);// B
		LOGGER.trace("RateContractAction::getDefaultCommonContractValues()::END");
	}

	/**
	 * @param request
	 * @param to
	 */
	private void getDefaultBasicDetails(HttpServletRequest request,
			RateContractTO to) {
		LOGGER.trace("RateContractAction::getDefaultBasicDetails()::START");
		String quotationNo = null;
		String contractNo = null;
		String quotationId = null;
		String contractId = null;

		if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.RATE_QUOTATION_NO))) {
			quotationNo = request.getParameter(RateQuotationConstants.RATE_QUOTATION_NO);
			request.setAttribute(RateQuotationConstants.QUOT_NUMBER, quotationNo);
		}
		if (!StringUtil.isNull(request.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER))) {
			contractNo = request.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER);
			request.setAttribute(RateContractConstants.RATE_CONTRACT_NO, contractNo);
		}
		if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.RATE_QUOTATION_ID))) {
			quotationId = request.getParameter(RateQuotationConstants.RATE_QUOTATION_ID);
			request.setAttribute(RateQuotationConstants.QUOTATION_ID, quotationId);
		}
		if (!StringUtil.isNull(request.getParameter(RateContractConstants.RATE_CONTRACT_ID))) {
			contractId = request.getParameter(RateContractConstants.RATE_CONTRACT_ID);
			request.setAttribute(RateContractConstants.CONTRACT_ID, contractId);
		}
		LOGGER.trace("RateContractAction::getDefaultBasicDetails()::END");
	}

	/**
	 * To get default Billing Detail(s)
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	//@SuppressWarnings("unchecked")
	private void getDefaultBillingDtls(HttpServletRequest request,
			RateContractTO to)  {
		LOGGER.trace("RateContractAction::getDefaultBillingDtls()::START");
		//HttpSession session = request.getSession(Boolean.FALSE);
		//List<StockStandardTypeTO> list = null;

		/* Billing Details - Contract Type */
		getStandardType(request,RateContractConstants.CONTRACT_TYPE_TYPE_NAME,RateContractConstants.CONTRACT_TYPE);
		
		/*Billing Details - Billing Type */
		getStandardType(request,RateContractConstants.BILLING_TYPE_TYPE_NAME,RateContractConstants.BILLING_TYPE);
		
		/* Billing Details - Billing Mode */
		getStandardType(request,RateContractConstants.BILLING_MODE_TYPE_NAME,RateContractConstants.BILLING_MODE);
		
		/*  Billing Details - Billing Cycle  */
		getStandardType(request,RateContractConstants.BILLING_CYCLE_TYPE_NAME,RateContractConstants.BILLING_CYCLE);
		
		/* Billing Details - Billing Mode  */
		getStandardType(request,RateContractConstants.PAYMENT_TERM_TYPE_NAME,RateContractConstants.PAYMENT_TERM);
		
		
		/* Billing Details - Contract Type */
		/*list = (List<StockStandardTypeTO>) session
				.getAttribute(RateContractConstants.CONTRACT_TYPE);
		if (StringUtil.isEmptyColletion(list)) {
			list = getStandardType(RateContractConstants.CONTRACT_TYPE_TYPE_NAME);
			session.setAttribute(RateContractConstants.CONTRACT_TYPE, list);
		}
		request.setAttribute(RateContractConstants.CONTRACT_TYPE, list);

		 Billing Details - Billing Type 
		list = (List<StockStandardTypeTO>) session
				.getAttribute(RateContractConstants.BILLING_TYPE);
		if (StringUtil.isEmptyColletion(list)) {
			list = getStandardType(RateContractConstants.BILLING_TYPE_TYPE_NAME);
			session.setAttribute(RateContractConstants.BILLING_TYPE, list);
		}
		request.setAttribute(RateContractConstants.BILLING_TYPE, list);

		 Billing Details - Billing Cycle 
		list = (List<StockStandardTypeTO>) session
				.getAttribute(RateContractConstants.BILLING_CYCLE);
		if (StringUtil.isEmptyColletion(list)) {
			list = getStandardType(RateContractConstants.BILLING_CYCLE_TYPE_NAME);
			session.setAttribute(RateContractConstants.BILLING_CYCLE, list);
		}
		request.setAttribute(RateContractConstants.BILLING_CYCLE, list);

		 Billing Details - Billing Mode 
		list = (List<StockStandardTypeTO>) session
				.getAttribute(RateContractConstants.BILLING_MODE);
		if (StringUtil.isEmptyColletion(list)) {
			list = getStandardType(RateContractConstants.BILLING_MODE_TYPE_NAME);
			session.setAttribute(RateContractConstants.BILLING_MODE, list);
		}
		request.setAttribute(RateContractConstants.BILLING_MODE, list);

		 Billing Details - Payment Term 
		list = (List<StockStandardTypeTO>) session
				.getAttribute(RateContractConstants.PAYMENT_TERM);
		if (StringUtil.isEmptyColletion(list)) {
			list = getStandardType(RateContractConstants.PAYMENT_TERM_TYPE_NAME);
			session.setAttribute(RateContractConstants.PAYMENT_TERM, list);
		}
		request.setAttribute(RateContractConstants.PAYMENT_TERM, list);
		*/
		/* Billing Details - Octroi Born By */
		/*
		 * list=(List<StockStandardTypeTO>)session.getAttribute(
		 * RateContractConstants.OCTROI_BORN_BY);
		 * if(StringUtil.isEmptyColletion(list)){ list =
		 * getStandardType(RateContractConstants.OCTROI_BORN_BY_TYPE_NAME);
		 * session.setAttribute(RateContractConstants.OCTROI_BORN_BY, list); }
		 * request.setAttribute(RateContractConstants.OCTROI_BORN_BY, list);
		 */

		/* Billing Details - Contract For */
		/*
		 * list=(List<StockStandardTypeTO>)session.getAttribute(
		 * RateContractConstants.CONTRACT_FOR);
		 * if(StringUtil.isEmptyColletion(list)){ list =
		 * getStandardType(RateContractConstants.CONTRACT_FOR_TYPE_NAME);
		 * session.setAttribute(RateContractConstants.CONTRACT_FOR, list); }
		 * request.setAttribute(RateContractConstants.CONTRACT_FOR, list);
		 */

		/* Billing Details Setting - Default Value(s) */
		request.setAttribute(RateContractConstants.PARAM_NORMAL_CONTRACT,
				RateContractConstants.NORMAL_CONTRACT);
		request.setAttribute(RateContractConstants.PARAM_BILL_TYPE_DBDP,
				RateContractConstants.BILL_TYPE_DBDP);
		request.setAttribute(RateContractConstants.PARAM_HARD_COPY,
				RateContractConstants.HARD_COPY);
		request.setAttribute(RateContractConstants.PARAM_MONTHLY_BILLING,
				RateContractConstants.MONTHLY_BILLING);
		/*
		 * request.setAttribute(RateContractConstants.PARAM_OCTROI_BY_CE,
		 * RateContractConstants.OCTROI_BY_CE);
		 */
		request.setAttribute(RateContractConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		LOGGER.trace("RateContractAction::getDefaultBillingDtls()::END");
	}

	/**
	 * To get default Pickup/Delivery Detail(s)
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	private void getDefaultPickupDlvDtls(HttpServletRequest request,
			RateContractTO to) {
		LOGGER.trace("RateContractAction::getDefaultPickupDlvDtls()::START");
		request.setAttribute(RateContractConstants.PARAM_ACTIVE,
				RateContractConstants.ACTIVE);
		request.setAttribute(RateContractConstants.PARAM_INACTIVE,
				RateContractConstants.INACTIVE);

		/* Pickup/Delivery Contract Type i.e. P - Pickup, D - Delivery. */
		request.setAttribute(RateContractConstants.PARAM_PICKUP_CONTRACT_TYPE,
				RateContractConstants.CONTRACT_TYPE_PICKUP);
		request.setAttribute(RateContractConstants.PARAM_DLV_CONTRACT_TYPE,
				RateContractConstants.CONTRACT_TYPE_DELIVERY);

		/* Pickup/Delivery Type of Billing i.e. DBDP, CBCP, DBCP. */
		request.setAttribute(RateContractConstants.BILL_TYPE_DBDP,
				RateContractConstants.BILL_TYPE_DBDP);// DD
		request.setAttribute(RateContractConstants.BILL_TYPE_CBCP,
				RateContractConstants.BILL_TYPE_CBCP);// CC
		request.setAttribute(RateContractConstants.BILL_TYPE_DBCP,
				RateContractConstants.BILL_TYPE_DBCP);// DC

		/* Rate Contract Type i.e. N-Normal, E-ECommerce. */
		request.setAttribute(RateContractConstants.PARAM_NORM,
				RateContractConstants.NORMAL_CONTRACT);// N
		request.setAttribute(RateContractConstants.PARAM_ECOM,
				RateContractConstants.ECCOMERCE_CONTRACT);// E

		/* Rate Billing Contract Type i.e. B-Billing, P-Payment. */
		request.setAttribute(RateContractConstants.PARAM_BILL,
				RateContractConstants.LOCATION_TYPE_BILLING);// B
		request.setAttribute(RateContractConstants.PARAM_PAY,
				RateContractConstants.LOCATION_TYPE_PAYMENT);// P
		
				/* Rate Contract Type i.e. N-Normal, R-Reverse Logistic. */
				request.setAttribute(RateContractConstants.PARAM_NORMAL_CON,
						RateContractConstants.NORMAL_CON);// N
				request.setAttribute(RateContractConstants.PARAM_REVS_LOGI_CON,
						RateContractConstants.REVS_LOGI_CON);// R
		LOGGER.trace("RateContractAction::getDefaultPickupDlvDtls()::END");
	}

	/**
	 * To Save Rate Contract Billing Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveRateContractBillingDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::saveRateContractBillingDtls()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		RateContractForm rateContractForm = (RateContractForm) form;
		RateContractTO to = null;
		String isDeletePickupOrDlvLocations = null;
		try {
			to = (RateContractTO) rateContractForm.getTo();
			if(!StringUtil.isStringEmpty(request.getParameter(RateContractConstants.IS_DELETE_PICKUP_OR_DLV_LOCATIONS))){
				isDeletePickupOrDlvLocations = request.getParameter(RateContractConstants.IS_DELETE_PICKUP_OR_DLV_LOCATIONS);
				to.setIsDeletePickupOrDlvLocations(isDeletePickupOrDlvLocations);
			}
			rateContractService = getRateContractService();
			to = rateContractService.saveRateContractBillingDtls(to);

			if (!to.isSaved()) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				to.setTransMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				jsonResult = serializer.toJSON(to).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateContractAction::saveRateContractBillingDtls()::END");
	}

	/**
	 * To Search Rate Contract Billing Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public void searchRateContractBillingDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::searchRateContractBillingDtls()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			String rateContractId = request.getParameter(RateContractConstants.RATE_CONTRACT_ID);
			RateContractTO to = new RateContractTO();
			to.setRateContractId(Integer.parseInt(rateContractId));
			rateContractService = getRateContractService();
			to = rateContractService.searchRateContractBillingDtls(to);
			if (!StringUtil.isNull(to)) {
				jsonResult = JSONSerializer.toJSON(to).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::searchRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::searchRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::searchRateContractBillingDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateContractAction::searchRateContractBillingDtls()::END");
	}
	
	/**
	 * To Search Rate Contract Billing Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public void searchRateContractPickDlvDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::searchRateContractPickDlvDtls()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			String rateContractId = request.getParameter(RateContractConstants.RATE_CONTRACT_ID);
			RateContractTO to = new RateContractTO();
			RateContractDO contractDO = null;//new RateContractDO();
			to.setRateContractId(Integer.parseInt(rateContractId));
			rateContractService = getRateContractService();
			contractDO = rateContractService.searchRateContractPickDlvDtls(to);
			if (!StringUtil.isNull(contractDO)) {
				jsonResult = JSONSerializer.toJSON(contractDO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::searchRateContractPickDlvDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::searchRateContractPickDlvDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::searchRateContractPickDlvDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateContractAction::searchRateContractPickDlvDtls()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access" })
	public void searchContractDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::searchContractDetails()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String rateContractNo = null;
		Integer createdBy = null;
		Integer rateContractId = null;
		String rateContractType = null;
		String userType = null;
		String jsonResult = "";
		RateContractTO contractTO = new RateContractTO();
		try {
			if (!StringUtil.isNull(request.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER))) {
				rateContractNo = request.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER);
				contractTO.setRateContractNo(rateContractNo);
			}
			if (!StringUtil.isStringEmpty(request.getParameter(RateContractConstants.RATE_CONTRACT_ID))) {
				rateContractId = Integer.parseInt(request.getParameter(RateContractConstants.RATE_CONTRACT_ID));
				contractTO.setRateContractId(rateContractId);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.CREATED_BY))) {
				createdBy = Integer.parseInt(request.getParameter(RateQuotationConstants.CREATED_BY).trim());
				contractTO.setCreatedBy(createdBy);
			}
			if (!StringUtil.isNull(request.getParameter(RateQuotationConstants.USER_TYPE))) {
				userType = request.getParameter(RateQuotationConstants.USER_TYPE).trim();
				contractTO.setUserType(userType);
			}
			if (!StringUtil.isStringEmpty(request.getParameter(RateContractConstants.RATE_CONTRACT_TYPE))) {
				rateContractType = (request.getParameter(RateContractConstants.RATE_CONTRACT_TYPE));
				contractTO.setRateContractType(rateContractType);
			}
			rateContractService = getRateContractService();
			contractTO = rateContractService.searchContractDetails(contractTO);
			
			if(!StringUtil.isNull(contractTO) && !StringUtil.isNull(contractTO.getRateQuotationTO())){ 
				if(!StringUtil.isNull(contractTO.getRateQuotationTO().getRhoOfcId())){
					String rhoOfcName = getOfficeByofficeId(contractTO.getRateQuotationTO().getRhoOfcId());
					if(!StringUtil.isStringEmpty(rhoOfcName)){
						contractTO.getRateQuotationTO().setRhoOfcName(rhoOfcName);
					}
					contractTO.setSalesCityList(getAllCities(contractTO.getRateQuotationTO().getRhoOfcId()));
					contractTO.setSalesOfcList(getAllOfficesByCity(contractTO.getRateQuotationTO().getCustomer().getSalesOffice().getCityId()));
					contractTO.setSalesPersonList(getEmployeesOfOffice(contractTO.getRateQuotationTO().getCustomer().getSalesOffice()));
				}else{
					if(contractTO.getRateQuotationTO().getCustomer().getSalesOffice().getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
						OfficeTO offcTO = contractTO.getRateQuotationTO().getCustomer().getSalesOffice();
						CityTO cityTO = new CityTO();
						cityTO.setCityId(offcTO.getCityId());
						cityTO.setCityName(offcTO.getCityName());
						List<CityTO> cityList = new ArrayList<CityTO>(1);
						cityList.add(cityTO);
						List<OfficeTO> offcList = new ArrayList<OfficeTO>(1);
						offcList.add(offcTO);
						contractTO.setSalesCityList(cityList);
						contractTO.setSalesOfcList(offcList);
						contractTO.setSalesPersonList(getEmployeesOfOffice(contractTO.getRateQuotationTO().getCustomer().getSalesOffice()));
					}
				}
			}
			
			if (!StringUtil.isNull(contractTO)) {
				jsonResult = serializer.toJSON(contractTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::searchContractDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::searchContractDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::searchContractDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("RateContractAction::searchContractDetails()::END");
	}

	/**
	 * To save rate contract Pickup/Delivery details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public void saveRateContractPickupDlvDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException  {
		LOGGER.debug("RateContractAction::saveRateContractPickupDtls()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		RateContractForm rateContractForm = (RateContractForm) form;
		RateContractTO to = null;
		try {
			to = (RateContractTO) rateContractForm.getTo();
			rateContractService = getRateContractService();
			RateContractDO rcDO = rateContractService.saveRateContractPickupDlvDtls(to);

			if (StringUtil.isNull(rcDO)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				to.setTransMsg(getMessageFromErrorBundle(request,
						RateErrorConstants.DATA_SAVED_SUCCESSFULLY, null));
				jsonResult = JSONSerializer.toJSON(rcDO).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveRateContractPickupDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveRateContractPickupDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveRateContractPickupDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("RateContractAction::saveRateContractPickupDtls()::END");
	}

	/**
	 * To get Rate Contract Service
	 * 
	 * @return rateContractService
	 */
	private RateContractService getRateContractService() {
		if (StringUtil.isNull(rateContractService)) {
			rateContractService = (RateContractService) getBean(RateSpringConstants.RATE_CONTRACT_SERVICE);
		}
		return rateContractService;
	}

	/**
	 * get the customer search info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view Rate Contract Normal
	 * @throws Exception
	 */
	public ActionForward searchCustomerInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("RateContractAction::searchCustomerInfo()::START");
		ActionMessage actionMessage = null;
		try {
			String custName = request
					.getParameter(RateContractConstants.CUST_NAME);
			rateContractService = getRateContractService();
			List<RateCustomerSearchTO> custTOs = rateContractService
					.searchCustomerInfo(custName);
			request.setAttribute(RateContractConstants.CUST_TOS, custTOs);
			// ((RateContractForm)form).setCustTO(custTO);
			// saveToken(request);
		} catch(CGSystemException e){
			LOGGER.error("Exception happened in searchCustomerInfo of RateContractAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in searchCustomerInfo of RateContractAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in searchCustomerInfo of RateContractAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("RateContractAction::searchCustomerInfo()::END");
		return mapping.findForward(RateContractConstants.SEARCH_CUSTOMER_INFO);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public void searchRateContractPickupDlvDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::searchRateContractPickupDlvDtls()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			String rateContractId = request.getParameter(RateContractConstants.RATE_CONTRACT_ID);
			RateContractTO to = new RateContractTO();
			to.setRateContractId(Integer.parseInt(rateContractId));
			rateContractService = getRateContractService();
			List<ContractPaymentBillingLocationTO> conPayBillLocTO = rateContractService.searchRateContractPickupDlvDtls(to);
			if (!StringUtil.isEmptyColletion(conPayBillLocTO)) {
				jsonResult = JSONSerializer.toJSON(conPayBillLocTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::searchRateContractPickupDlvDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::searchRateContractPickupDlvDtls()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::searchRateContractPickupDlvDtls()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateContractAction::searchRateContractPickupDlvDtls()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void submitContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::submitContract()::START");
		PrintWriter out = null;
		out = response.getWriter();
		
		// Integer rateContractId = null;
		String jsonResult = "";
		Boolean isUpdated = false;
		RateContractTO contractTO = null;//new RateContractTO();
		boolean isSaved;
		String fromMailId = null; 
		try {
			RateContractForm rateContractForm = (RateContractForm) form;

			RateContractSpocTO rcsTO = (RateContractSpocTO) rateContractForm.getRateContractSpocTO();
			contractTO = (RateContractTO) rateContractForm.getTo();
			rcsTO.setRateContractId(contractTO.getRateContractId());
			rateContractService = getRateContractService();
			isSaved = rateContractService.saveRateContractSpocDetails(rcsTO);
			if (isSaved) {
				fromMailId = getConfigParamsValue(FrameworkConstants.CONFIG_PARAM_FOR_FROM_EMAIL_ID,request);
				contractTO.setFromMailId(fromMailId);
				LOGGER.debug("RateContractAction::submitContract()::[" + contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
				isUpdated = rateContractService.submitContract(contractTO);
			}

			if (!isUpdated) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY,
								null));
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.CONTRACT_SUBMITTED_SUCCESSFULLY,
								null));

			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::submitContract()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::submitContract()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::submitContract()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("RateContractAction::submitContract()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateContractBasicInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::saveOrUpdateContractBasicInfo()::START");
		RateContractForm rateContractForm = null;
		RateContractTO contractTo = null;
		RateQuotationTO rateQuotationTO = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";

		try {
			serializer = CGJasonConverter.getJsonObject();
			rateContractForm = (RateContractForm) form;
			contractTo = (RateContractTO) rateContractForm.getTo();
			

			if (contractTo != null) {
				rateQuotationTO = contractTo.getRateQuotationTO();
				if (rateQuotationTO != null) {
					if (rateQuotationTO.getRateQuotationType().equals(RateCommonConstants.RATE_QUOTATION_TYPE_N)) {
						String indCode = 
								getIndustryCatCode(request,	Integer.parseInt(rateQuotationTO.getCustomer().getIndustryCategory().split(CommonConstants.TILD)[0]));
						rateQuotationTO.setIndCatCode(indCode);
					}
					rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_C);
					RateQuotationService rateQuotationService = (RateQuotationService) getBean(RateQuotationConstants.RATE_QUOTATION_SERVICE);
					rateQuotationTO = rateQuotationService.saveOrUpdateBasicInfo(rateQuotationTO);

				}
			}
			if (rateQuotationTO!=null && !rateQuotationTO.isSaved()) {
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
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractBasicInfo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractBasicInfo()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractBasicInfo()::Exception::"	+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveOrUpdateContractBasicInfo()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateContractFixedCharges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::saveOrUpdateContractFixedCharges()::START");
		RateQuotaionFixedChargesTO fixedChargesTO = null;
		RateContractForm rateContractForm = null;
		RateContractTO contractTo = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";

		try {
			serializer = CGJasonConverter.getJsonObject();
			rateContractForm = (RateContractForm) form;
			contractTo = (RateContractTO) rateContractForm.getTo();
			fixedChargesTO = (RateQuotaionFixedChargesTO) rateContractForm
					.getRateQuotationFixedChargesTO();
			rateQuotationTO = contractTo.getRateQuotationTO();
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_C);
			fixedChargesTO.setRateQuotation(rateQuotationTO);
			
			
			List<RateQuotationCODChargeTO> codList = new ArrayList<RateQuotationCODChargeTO>();
			if (!(StringUtil.isNull(fixedChargesTO.getCodChargesChk()))) {
				if (fixedChargesTO.getCodChargesChk().equals(RateQuotationConstants.ON)) {
					int fixcodchrgsLen = fixedChargesTO.getCodChargeId().length;
					for (int i = 1; i <= fixcodchrgsLen; i++) {
						String selected = request.getParameter(RateQuotationConstants.FIXED_CHRGS_TYPE + i);
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
			RateQuotationService rateQuotationService = (RateQuotationService) getBean(RateQuotationConstants.RATE_QUOTATION_SERVICE);
			if (fixedChargesTO != null) {

				fixedChargesTO  = rateQuotationService
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
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractFixedCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractFixedCharges()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveOrUpdateContractFixedCharges()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateContractRTOCharges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::saveOrUpdateContractRTOCharges()::START");
		RateQuotaionRTOChargesTO rtoChargesTO = null;
		RateContractForm rateContractForm = null;
		RateContractTO contractTo = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";

		try {
			serializer = CGJasonConverter.getJsonObject();
			rateContractForm = (RateContractForm) form;
			contractTo = (RateContractTO) rateContractForm.getTo();
			rtoChargesTO = (RateQuotaionRTOChargesTO) rateContractForm
					.getRateQuotationRTOChargesTO();
			rateQuotationTO = contractTo.getRateQuotationTO();
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_C);
			rtoChargesTO.setRateQuotation(rateQuotationTO);
			RateQuotationService rateQuotationService = (RateQuotationService) getBean(RateQuotationConstants.RATE_QUOTATION_SERVICE);
			if (rtoChargesTO != null) {
				rtoChargesTO  = rateQuotationService
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
			LOGGER.error("RateContractAction::saveOrUpdateContractRTOCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRTOCharges()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRTOCharges()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveOrUpdateContractRTOCharges()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateContractRateQuotationProposedRates(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException, IOException {
		LOGGER.trace("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::START");
		// String status = null;
		PrintWriter out = null;
		out = response.getWriter();
		
		String data = "";
		try {
			HttpSession session = request.getSession(false);
			serializer = CGJasonConverter.getJsonObject();
			RateContractForm rateContractForm = null;
			RateContractTO contractTo = null;

			rateContractForm = (RateContractForm) form;
			contractTo = (RateContractTO) rateContractForm.getTo();

			RateQuotationProposedRatesTO rqprTO = (RateQuotationProposedRatesTO) rateContractForm
					.getProposedRatesTO();
			if(!StringUtil.isStringEmpty(rqprTO.getIndCatCode())){
				String  indCode = getIndustryCatCode(
					request,
					Integer.parseInt(rqprTO.getIndCatCode().split(CommonConstants.TILD)[0]));
					rqprTO.setIndCatCode(rqprTO.getIndCatCode()+CommonConstants.TILD+indCode);
			}
			
			RateQuotationTO rqTO = contractTo.getRateQuotationTO();
			UserTO user = getLoginUserTO(request);
			rqTO.setUserId(user.getUserId());
			rqTO.setQuotationUsedFor(RateQuotationConstants.CHAR_C);
			
			
			Integer userId = contractTo.getCreatedBy();
			
			OfficeTO ofcTO = getOfficeByUserId(userId);
			Integer regionId = null;
			
			if(!StringUtil.isNull(ofcTO)){
				if(ofcTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					regionId = null;
				}else{
					regionId = ofcTO.getRegionTO().getRegionId();
				}
			}
			rqprTO.setRegionId(regionId);
			
			
			
			Integer custId = rqprTO.getCustCatId();
			RateQuotationService rateQuotationService = (RateQuotationService) getBean(RateQuotationConstants.RATE_QUOTATION_SERVICE);
			rqprTO  = rateQuotationService.saveOrUpdateSlabRateDeatails(rqprTO,
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
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::END");
	}// End of saveOrUpdateRateQuotationProposedRates
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateEcomerceContractFixedCharges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::saveOrUpdateEcomerceContractFixedCharges()::START");
		RateQuotaionFixedChargesTO fixedChargesTO = null;
		RateContractForm rateContractForm = null;
		RateContractTO contractTo = null;
		RateQuotationTO rateQuotationTO = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";

	

		try {
			serializer = CGJasonConverter.getJsonObject();
			rateContractForm = (RateContractForm) form;
			contractTo = (RateContractTO) rateContractForm.getTo();
			fixedChargesTO = (RateQuotaionFixedChargesTO) rateContractForm
					.getRateQuotationFixedChargesTO();
			rateQuotationTO = contractTo.getRateQuotationTO();
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.CHAR_C);
			fixedChargesTO.setRateQuotation(rateQuotationTO);

			List<RateQuotationCODChargeTO> codList = new ArrayList<RateQuotationCODChargeTO>();
			int fixcodchrgsLen = fixedChargesTO.getCodChargeId().length;
			for (int i = 1; i <= fixcodchrgsLen; i++) {
				String selected = request.getParameter(RateQuotationConstants.FIXED_CHRGS_TYPE + i);
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

			RateQuotationService rateQuotationService = (RateQuotationService) getBean(RateQuotationConstants.RATE_QUOTATION_SERVICE);
			if (fixedChargesTO != null) {

				fixedChargesTO  = rateQuotationService
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
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveOrUpdateContractRateQuotationProposedRates()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}  finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveOrUpdateEcomerceContractFixedCharges()::END");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward renewContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.trace("RateContractAction::renewContract()::START");
		Integer contractId = null;
		Integer quotationId = null;
		Integer userId = null;
		String officeCode = null;
		String contractType = null;
		HttpSession session = request.getSession(false);
		String url = "";
		try{
			contractId = Integer.parseInt(request.getParameter(RateContractConstants.CONTRACT_ID));
			quotationId = Integer.parseInt(request.getParameter(RateQuotationConstants.QUOTATION_ID));
			contractType = request.getParameter(RateContractConstants.CONTRACT_TYPE);
			UserInfoTO userInfoTo = (UserInfoTO)session.getAttribute(RateContractConstants.USER_INFO);
			
			userId = userInfoTo.getUserto().getUserId();
			
			officeCode = userInfoTo.getOfficeTo().getOfficeCode();
			String contractNo = null;
			
			contractNo = rateContractService.renewContract(contractId, quotationId, userId, officeCode);
			
			
			RateContractForm rateContractForm = new RateContractForm();
			RateQuotationTO rateQuotationTO = new RateQuotationTO();
			RateContractTO to = null;
			
				to = (RateContractTO) rateContractForm.getTo();
				
				if(contractType.equals(RateContractConstants.NORMAL_CONTRACT)){
				String rateQuotationType = RateQuotationConstants.NORMAL;
				request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
				rateQuotationTO.setRateQuotationType(RateQuotationConstants.NORMAL);
				String rateContractType = RateContractConstants.NORMAL_CONTRACT;
				request.setAttribute(RateContractConstants.RATE_CONTRACT_TYPE, rateContractType);
				getDefaultUIValues(request, rateQuotationTO);
				getDefaultUIValuesContract(request, to);
				getCodChagreValue(request, rateQuotationTO, "N", "normalCODList");
				saveToken(request);
				to.setRateContractNo(contractNo);
				((RateContractForm) form).setTo(to);
				request.setAttribute(RateContractConstants.RATE_CONTRACT_NO, contractNo);
				url = RateContractConstants.VIEW_RATE_CONTRACT_NORMAL;
				}else{
					String rateQuotationType = RateQuotationConstants.ECOMMERCE;
					request.setAttribute(RateQuotationConstants.RATE_QUOTATION_TYPE, rateQuotationType);
					rateQuotationTO.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
					String rateContractType = RateContractConstants.ECCOMERCE_CONTRACT;
					request.setAttribute(RateContractConstants.RATE_CONTRACT_TYPE, rateContractType);
					getDefaultUIValues(request, rateQuotationTO);
					getDefaultUIValuesContract(request, to);
					getCodChagreValue(request, rateQuotationTO, "E", "ecommerceCODList");
					saveToken(request);
					to.setRateContractNo(contractNo);
					((RateContractForm) form).setTo(to);
					request.setAttribute(RateContractConstants.RATE_CONTRACT_NO, contractNo);
					url = RateContractConstants.VIEW_RATE_CONTRACT_ECOMMERCE;
				}
			
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in renewContract of RateContractAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in renewContract of RateContractAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in renewContract of RateContractAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			prepareActionMessage(request, new ActionMessage(exception));
		}	
			
		
		LOGGER.trace("RateContractAction::renewContract()::END");
		return mapping
				.findForward(url);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listViewRateContract(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("RateContractAction::listViewRateContract::START------------>:::::::");
		RateContractTO rateContractTO = new RateContractTO();
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		String url = null;
		String contractType="";
		String page = "";
		String empType;
	
			if(!StringUtil.isNull(request.getParameter(RateQuotationConstants.PAGE))){
			page = request.getParameter(RateQuotationConstants.PAGE);
			request.setAttribute(RateQuotationConstants.LIST_VIEW_PAGE, page);
			}
			if(!StringUtil.isNull(request.getParameter(RateQuotationConstants.EMP_TYPE))){
				empType = request.getParameter(RateQuotationConstants.EMP_TYPE);
				request.setAttribute(RateQuotationConstants.EMP_TYPE, empType);
				}
			if (request.getParameter(RateQuotationConstants.TYPE).equals(RateQuotationConstants.NORMAL)) {
				rateContractTO
						.setRateContractType(RateContractConstants.NORMAL_CONTRACT);
				rateQuotationTO.setRateQuotationType(RateQuotationConstants.NORMAL);
				rateQuotationTO.setQuotationUsedFor(RateContractConstants.CHAR_C);
				contractType = RateQuotationConstants.NORMAL;
				getCodChagreValue(request, rateQuotationTO, "N", "normalCODList");
				url = RateContractConstants.VIEW_RATE_CONTRACT_NORMAL;
			} else if (request.getParameter(RateQuotationConstants.TYPE).equals(RateQuotationConstants.ECOMMERCE)) {
				rateContractTO
				.setRateContractType(RateContractConstants.ECCOMERCE_CONTRACT);
				rateQuotationTO.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
				rateQuotationTO.setQuotationUsedFor(RateContractConstants.CHAR_C);
				contractType = RateQuotationConstants.ECOMMERCE;
				getCodChagreValue(request, rateQuotationTO, "E", "ecommerceCODList");
				url = RateContractConstants.VIEW_RATE_CONTRACT_ECOMMERCE;
			}
			rateContractTO.setRateQuotationTO(rateQuotationTO);
			request.setAttribute(RateContractConstants.RATE_CONTRACT_TYPE, contractType);
			getDefaultViewContractUIValuesContract(request, rateContractTO);
			if (!StringUtil.isNull(request.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER))){
				rateContractTO.setRateContractNo(request
						.getParameter(RateContractConstants.RATE_CONTRACT_NUMBER));
			}
			((RateContractForm) form).setTo(rateContractTO);

		
		LOGGER.debug("RateContractAction::listViewRateContract::END------------>:::::::");

		return mapping.findForward(url);
	}
	
	/**
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	private void getDefaultViewContractUIValuesContract(HttpServletRequest request,
			RateContractTO to){
		LOGGER.trace("RateContractAction::getDefaultViewContractUIValuesContract::START------------>:::::::");
		/* Common Details */
		getDefaultCommonContractValues(request, to);

		/* Basic Info */
		getDefaultBasicDetails(request, to);

		/* Billing Details */
		getDefaultBillingDtls(request, to);

		/* Pickup/Delivery Details */
		getDefaultPickupDlvDtls(request, to);

		getDefaultViewQuotationUIValues(request, to.getRateQuotationTO());
		Integer userId = Integer.parseInt(request.getParameter(RateQuotationConstants.USER_ID));
		request.setAttribute(RateQuotationConstants.CREATED_BY, userId);
		
		getStandardType(request, RateContractConstants.CONTRACT_COMPLAINT, RateContractConstants.COMPLAINT_LIST);
		LOGGER.trace("RateContractAction::getDefaultViewContractUIValuesContract::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void searchContractSpocDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("RateContractAction::searchContractSpocDetails::START------------>:::::::");
		String jsonResult = "";
		PrintWriter out = null;
		out = response.getWriter();
		List<RateContractSpocTO> rcsTOList = null;
		Integer contractId = null;
		String contactType = null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			
			
			contactType = request.getParameter("contactType");
			contractId = Integer.parseInt(request.getParameter("contractId").trim());
			
			rateContractService = getRateContractService();
			rcsTOList = rateContractService.getRateContractSpocDetails(contactType,contractId);
			
			jsonResult = serializer.toJSON(rcsTOList).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::searchContractSpocDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::searchContractSpocDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::searchContractSpocDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::searchContractSpocDetails::END------------>:::::::");
	}
	
	
	public void saveContractSpocDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::saveContractSpocDetails------------>START:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		Boolean isSaved;
		try {
			serializer = CGJasonConverter.getJsonObject();
			RateContractForm rateContractForm = (RateContractForm) form;
			RateContractSpocTO rcsTO = (RateContractSpocTO) rateContractForm
					.getRateContractSpocTO();
			RateContractTO rcTO = (RateContractTO) rateContractForm.getTo();
			rcsTO.setRateContractId(rcTO.getRateContractId());
			rateContractService = getRateContractService();
			isSaved = rateContractService.saveRateContractSpocDetails(rcsTO);

			if (!isSaved) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_NOT_SAVED, null));
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.DATA_SAVED_SUCCESSFULLY,
								null));

			}

		} catch (CGBusinessException e) {
			LOGGER.error("RateContractAction::saveContractSpocDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateContractAction::saveContractSpocDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateContractAction::saveContractSpocDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::saveContractSpocDetails------------>END:::::::");
	}
	
	public void validateContractDates(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("RateContractAction::validateContractDates------------>START:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = "";
		try {
			serializer = CGJasonConverter.getJsonObject();
			String fromDate = request.getParameter(RateContractConstants.PARAM_FROM_DT);
			String toDate = request.getParameter(RateContractConstants.PARAM_TO_DT);
			
			String dateStr = fromDate;
			SimpleDateFormat sdf = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(sdf.parse(dateStr));
			
			c1.add(Calendar.YEAR, 1);
			c1.add(Calendar.DATE, -1);
			dateStr = sdf.format(c1.getTime());
			
			if(DateUtil.stringToDDMMYYYYFormat(toDate).compareTo(DateUtil.stringToDDMMYYYYFormat(dateStr)) <=0){
				jsonResult = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,RateContractConstants.SUCCESS);
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getMessageFromErrorBundle(request,
						RateErrorConstants.DATE_SHOULD_NOT_EXCEED_ONE_YEAR,
						null));
			}

		} catch (Exception e) {
			LOGGER.error("RateContractAction::validateContractDates()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateContractAction::validateContractDates------------>END:::::::");
		
	}
	
	
	@SuppressWarnings("static-access")
	public void getValidatePincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		PincodeTO pincodeTO = new PincodeTO();
		String pincodeTOJSON = null;
		//CityTO cityTO = new CityTO();
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

				pincodeTOJSON = serializer.toJSON(pincodeTO).toString();
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
}