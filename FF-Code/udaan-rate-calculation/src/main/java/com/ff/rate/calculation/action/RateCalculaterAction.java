package com.ff.rate.calculation.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.rate.calculation.form.RateCalculatorForm;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.BARateCalculationInputTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;

public class RateCalculaterAction extends CGBaseAction {

	private static final Logger LOGGER = Logger
			.getLogger(RateCalculaterAction.class);

	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("RateCalculaterAction::preparePage::start=====>");
		RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
		try {
			List<BookingPreferenceDetailsTO> bookingPrefDetails = serviceFactory
					.getBookingPrefDetails();
			request.setAttribute("ebPreferences", bookingPrefDetails);
		} catch (CGSystemException e) {
			LOGGER.error(
					"RateCalculaterAction::preparePage::CGSyatemException::", e);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"RateCalculaterAction::preparePage::CGBusinessException::",
					e);
		}
		request.setAttribute("todaysDate", DateUtil.getCurrentDateInDDMMYYYY());
		return mapping.findForward("viewInputPage");
	}

	public ActionForward calculateRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RateCalculaterAction::calculateRate::start  :: System.currentTimeMillis()=====>"
				+ System.currentTimeMillis());
		RateCalculatorForm rateForm = (RateCalculatorForm) form;
		RateCalculationInputTO inputTO = (RateCalculationInputTO) rateForm
				.getTo();
		RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");

		RateCalculationUniversalService rateCalculationUniversalService = (RateCalculationUniversalService) getBean("rateCalculationUniversalService");

		Boolean isValidProduct = false;
		inputTO = prepareInput(inputTO);
		try {

			// Rate Calculation Type is not provided
			if (StringUtils.isEmpty(inputTO.getRateType())
					|| inputTO.getRateType() == null)
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_RATE_TYPE);

			RateCalculationService rateService = serviceFactory
					.getService(inputTO.getRateType());
			if (rateService != null) {
				// Is Product is validated for Contract
				ProductToBeValidatedInputTO productToBeValidatedInputTO = new ProductToBeValidatedInputTO();
				prepareProductToBeValidatedInputTO(inputTO,
						productToBeValidatedInputTO);
				isValidProduct = rateService
						.isProductValidForContract(productToBeValidatedInputTO);
				if (!isValidProduct) {
					throw new CGBusinessException(
							RateErrorConstants.VALIDATE_PRODUCT_FOR_RATE_CALCULATION);
				}
				// Calculate rates
				if (inputTO.getRateCalculationFOR().equalsIgnoreCase("B")) {
					RateCalculationOutputTO result = rateService
							.calculateRate(inputTO);
					if(result == null && RateCommonConstants.YES.equalsIgnoreCase(inputTO.getIsRTO())){
						throw new CGBusinessException(
								RateErrorConstants.VALIDATE_RATE_APPLICABLE_FOR_RTO);
					}
					request.setAttribute(RateCommonConstants.COMPONENTS,
							result.getComponents());
				} else {
					OctroiRateCalculationOutputTO result = rateService
							.calculateOCTROI(inputTO);
					request.setAttribute(RateCommonConstants.COMPONENTS,
							result.getComponents());
				}
				/* Testing Bulk Rate Calculation */
				if(!StringUtil.isEmptyInteger(inputTO.getCnLimit())){
					List<ConsignmentBilling> consignmentDOs=rateCalculationUniversalService.getConsignmentForRate(inputTO.getCnLimit(), inputTO.getRateType());
					List<ConsignmentTO> consignmentTOs = rateCalculationUniversalService.convertConsignmentDOsToTOs(consignmentDOs);
					List<ConsignmentRateCalculationOutputTO> calculatedRates = new ArrayList<>();
					long startTm1=System.currentTimeMillis();
					LOGGER.debug("RateCalculaterAction::calculateRate::Rate Calculation Time Start ====>"+ startTm1);
					for (ConsignmentTO consignmentTO : consignmentTOs) {
						ConsignmentRateCalculationOutputTO resultTo = rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
						calculatedRates.add(resultTo);
					}
					long endTm1=System.currentTimeMillis();
					long elapseTm1=endTm1-startTm1;
					LOGGER.debug("RateCalculaterAction::calculateRate::Rate Calculation Time for  ----->"+calculatedRates.size()  +"  Consignment is ----> "+elapseTm1);
					LOGGER.debug(calculatedRates.size());
					
				}
				
				
			}
		} catch (CGBusinessException e) {
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(RateCommonConstants.RATE_ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			request.setAttribute("ErrorCode", e.getMessage());
			request.setAttribute("ErrorMsg", errorMsg);
			LOGGER.error("RateCalculaterAction::calculateRate::CGBusinessException::"
					,e);

		} catch (CGSystemException e) {
			LOGGER.error(
					"RateCalculaterAction::calculateRate::CGSyatemException::",
					e);
		} catch (Exception e) {
			LOGGER.error("RateCalculaterAction::calculateRate::Exception::", e);
		}
		// Response Time
		LOGGER.trace("RateCalculaterAction::calculateRate::END  :: =====>"
				+ System.currentTimeMillis());
		if (inputTO.getRateCalculationFOR().equalsIgnoreCase("B")) {
			return mapping.findForward(RateCommonConstants.RESULT_PAGE);
		} else {
			return mapping.findForward(RateCommonConstants.RESULT_OCTROI_PAGE);
		}
		
	}

	private void prepareProductToBeValidatedInputTO(
			RateCalculationInputTO inputTO,
			ProductToBeValidatedInputTO productToBeValidatedInputTO) {

		productToBeValidatedInputTO.setProductCode(inputTO.getProductCode());
		productToBeValidatedInputTO.setRateType(inputTO.getRateType());
		productToBeValidatedInputTO.setOriginCityCode(inputTO
				.getOriginCityCode());
		productToBeValidatedInputTO.setCustomerCode(inputTO.getCustomerCode());
		productToBeValidatedInputTO.setCalculationRequestDate(inputTO
				.getCalculationRequestDate());
		productToBeValidatedInputTO.setEbPreference(inputTO.getEbPreference());

	}

	private RateCalculationInputTO prepareInput(RateCalculationInputTO inputTO) {
		try {
			// RateCalculationInputTO baseInput = getCreditCustomerInputTO();
			RateCalculationInputTO baseInput = getBAInputTO();
			PropertyUtils.copyProperties(baseInput, inputTO);
		} catch (IllegalAccessException e) {
			LOGGER.error("RateCalculaterAction::prepareInput::", e);
		} catch (InvocationTargetException e) {
			LOGGER.error("RateCalculaterAction::prepareInput::", e);
		} catch (NoSuchMethodException e) {
			LOGGER.error("RateCalculaterAction::prepareInput::", e);
		}

		return inputTO;
	}

	private RateCalculationInputTO getBAInputTO() {
		RateCalculationInputTO baseInput = new BARateCalculationInputTO();
		baseInput.setRateType("BA");
		return baseInput;
	}
}
