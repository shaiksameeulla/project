/**
 * 
 */
package com.ff.web.ratecalculator.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rateCalculator.RateCalculatorTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.ratecalculator.constants.RateCalculatorConstants;
import com.ff.web.ratecalculator.form.RateCalculatorForm;
import com.ff.web.ratecalculator.service.RateCalculatorService;

/**
 * @author prmeher
 * 
 */
public class RateCalculatorAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCalculatorAction.class);

	private RateCalculatorService rateCalculatorService;

	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * view rate calculator
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward viewRateCalculator(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RateCalculatorAction::viewRateCalculator::START------------>:::::::");

		setUpDefaultValues(request);

		LOGGER.debug("RateCalculatorAction::viewRateCalculator::END------------>:::::::");

		return mapping
				.findForward(RateCalculatorConstants.VIEW_RATE_CALCULATOR);
	}

	/**
	 * @param request
	 */
	private void setUpDefaultValues(HttpServletRequest request) {
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("originOfficeCode", userInfoTO.getOfficeTo()
					.getOfficeCode());
			request.setAttribute("originCityId", userInfoTO.getOfficeTo()
					.getCityId());
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			// get Product Type
			rateCalculatorService = (RateCalculatorService) getBean(RateCalculatorConstants.RATE_CALCULATOR_SERVICE);
			List<ProductTO> productList = rateCalculatorService
					.getProductTypes();
			request.setAttribute("productList", productList);
			List<ConsignmentTypeTO> consgTypeList = rateCalculatorService
					.getConsignmentType();
			request.setAttribute("consgTypeList", consgTypeList);
			List<StockStandardTypeTO> servicedOnList = rateCalculatorService
					.getStockStdTypeByType("SERVICED_ON");
			request.setAttribute("servicedOnList", servicedOnList);
			List<BookingPreferenceDetailsTO> preferenceDetails = rateCalculatorService.getAllPreferenceDetails();
			request.setAttribute("preferenceDetails", preferenceDetails);
		} catch (Exception e) {
			LOGGER.error("RateCalculatorAction :: setUpDefaultValues() ::"
					,e);
		}
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
	public void getCityName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		CityTO cityTO = new CityTO();
		String cityTOJSON = null;
		String pincode = null;
		PrintWriter out = null;

		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request.getParameter("pincode"))) {
				pincode = request.getParameter("pincode");
			}
			GeographyCommonService geographyCommonService = (GeographyCommonService) getBean("geographyCommonService");
			cityTO = geographyCommonService.getCity(pincode);

			if (!StringUtil.isNull(cityTO.getCityId())) {

				cityTOJSON = serializer.toJSON(cityTO).toString();
			} else {
				cityTOJSON = "INVALID";
			}

		} catch (Exception e) {
			cityTOJSON = "INVALID";

			LOGGER.error("RateCalculatorAction :: getCity() ::"
					+ e.getMessage());
		} finally {

			out.print(cityTOJSON);
			out.flush();
			out.close();
		}
	}

	@SuppressWarnings("static-access")
	public void calculateRates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException {
		PrintWriter out = null;
		String resultTOJSON = "";
		RateCalculationOutputTO resultTO = null;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			RateCalculatorForm rateForm = (RateCalculatorForm) form;
			RateCalculatorTO rateCalculatorTO = (RateCalculatorTO) rateForm.getTo();
			rateCalculatorService = (RateCalculatorService) getBean(RateCalculatorConstants.RATE_CALCULATOR_SERVICE);
			RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
			resultTO = rateCalculatorService.calculateRates(rateCalculatorTO, serviceFactory);
			resultTOJSON = serializer.toJSON(resultTO).toString();
		} catch (CGBusinessException e) {
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(RateCommonConstants.RATE_ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			JSONObject detailObj = new JSONObject(); 
			detailObj.put(DrsConstants.ERROR_FLAG, errorMsg);
			resultTOJSON = detailObj.toString();
			LOGGER.error("RateCalculatorAction :: calculateRates() ::"
					+ e.getMessage());
		}
		catch (Exception e) {
			LOGGER.error("RateCalculatorAction :: calculateRates() ::"
					+ e.getMessage());
		}finally {
			out.print(resultTOJSON);
			out.flush();
			out.close();
		}

	}


}
