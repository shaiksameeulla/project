package com.ff.web.booking.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;
import org.slf4j.Logger;

/**
 * The Class BookingAction.
 */
public abstract class BookingAction extends CGBaseAction {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	public void process2WayWrite(List<Integer> successBookingIds,
			List<Integer> successCNIds) {
		try {
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			bookingCommonService.process2WayWrite(successBookingIds,
					successCNIds);
		} catch (Exception ex) {
			LOGGER.error(
					"CashBookingServiceImpl::saveOrUpdateBookingDox:: error in 2way write",
					ex);
		}
	}

	/**
	 * Calc cn rate.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws IOException
	 * @throws CGBaseException
	 *             the cG base exception
	 * @Method : calcCNRate
	 * @Desc :
	 */
	@SuppressWarnings("static-access")
	public void calcCNRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::calcCNRate::START------------>:::::::");
		CNPricingDetailsTO cnRateDtls = null;
		String cnRateDtlsJSON = "";
		PrintWriter out = null;
		out = response.getWriter();

		try {
			// FIXME.. Rate Integration
			RateCalculationInputTO inputs = prepareRateInputs(request);
			cnRateDtls = bookingCommonService.getConsgRate(inputs);
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: calcCNRate() ::", e);
			cnRateDtlsJSON = "ERROR";
		} finally {
			if (cnRateDtlsJSON != null) {
				serializer = CGJasonConverter.getJsonObject();
				cnRateDtlsJSON = serializer.toJSON(cnRateDtls).toString();
			}
			out.print(cnRateDtlsJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::calcCNRate::END------------>:::::::");

	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public void calculateRateForConsignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("BookingAction::calculateRateForConsignment::START------------>:::::::");
		CNPricingDetailsTO cnRateDtls = null;
		String cnRateDtlsJSON = "";
		PrintWriter out = null;
		out = response.getWriter();

		String errorMsg = "";
		ConsignmentTO rateInputs = null;
		ConsignmentRateCalculationOutputTO rateOutput = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try {
			session = (HttpSession) request.getSession(false);
			LOGGER.debug("BookingAction :: calculateRateForConsignment() :: Rate Calculation..START..:"
					+ System.currentTimeMillis());
			rateInputs = prepareRateInputsForConsignment(request);
			LOGGER.debug("BookingAction::calculateRateForConsignment:: calculating rate for consignment ["
					+ rateInputs.getConsgNo() + "]");
			rateOutput = bookingCommonService
					.calcRateForConsingment(rateInputs);
			if (!StringUtil.isNull(rateOutput)) {
				LOGGER.debug("BookingAction::calculateRateForConsignment:: calculated rate output for consignment ["
						+ rateInputs.getConsgNo()
						+ "] is: "
						+ rateOutput.getGrandTotalIncludingTax());
				rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
						.getAttribute(BookingConstants.CONSG_RATE_DTLS);
				if (!CGCollectionUtils.isEmpty(rateCompnents)) {
					rateCompnents.put(rateInputs.getConsgNo(), rateOutput);
				} else {
					rateCompnents = new HashMap<>();
					rateCompnents.put(rateInputs.getConsgNo(), rateOutput);
				}
				session.setAttribute(BookingConstants.CONSG_RATE_DTLS,
						rateCompnents);
				cnRateDtls = BookingUtils.setUpRateCompoments(rateOutput);
				// Setting back rate type
				cnRateDtls.setRateType(rateInputs.getConsgPriceDtls()
						.getRateType());

			} else {
				LOGGER.debug("BookingAction::calculateRateForConsignment:: calculated rate output for consignment ["
						+ rateInputs.getConsgNo() + "] is null");
			}
			LOGGER.debug("BookingAction :: calculateRateForConsignment() :: Rate Calculation..END..:"
					+ System.currentTimeMillis());
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			cnRateDtls = new CNPricingDetailsTO();
			errorMsg = getSystemExceptionMessage(request, e);
			cnRateDtls.setMessage(errorMsg);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			errorMsg = getBusinessErrorFromWrapper(request, e);
			// errorMsg = "Contract is not available for rate calculation.";
			cnRateDtls = new CNPricingDetailsTO();
			cnRateDtls.setMessage(errorMsg);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			errorMsg = getGenericExceptionMessage(request, e);
			cnRateDtls = new CNPricingDetailsTO();
			cnRateDtls.setMessage(errorMsg);
		} finally {
			if (cnRateDtlsJSON != null) {
				serializer = CGJasonConverter.getJsonObject();
				cnRateDtlsJSON = serializer.toJSON(cnRateDtls).toString();
			}
			out.print(cnRateDtlsJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::calculateRateForConsignment::END------------>:::::::");

	}

	/**
	 * Validate declaredvalue.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void validateDeclaredvalue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::validateDeclaredvalue::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		Double declaredValue = 0.0;
		try {
			bookingValidateTO = new BookingValidationTO();

			try {
				declaredValue = Double.parseDouble(request
						.getParameter("declaredVal"));
			} catch (Exception ex) {
				LOGGER.error("BookingAction :: validateDeclatedvalue() ::", ex);
			}

			String bookingType = request.getParameter("bookingType");
			bookingValidateTO.setDeclaredValue(declaredValue);
			bookingValidateTO.setBookingType(bookingType);
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			bookingValidateTO = bookingCommonService
					.validateDeclaredValue(bookingValidateTO);
		} catch (Exception e) {
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setErrorMsg(errorMsg);
			LOGGER.error(
					"Error occured in BookingAction :: validateDeclatedvalue() ::",
					e);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validateDeclaredvalue::END------------>:::::::");
	}

	/**
	 * Gets the booking type config.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the booking type config
	 * @throws IOException
	 */
	public void getBookingTypeConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getBookingTypeConfig::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		BookingTypeConfigTO bookingTypeConfigTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();

		try {
			bookingValidateTO = new BookingValidationTO();
			if (StringUtils.isNotEmpty(request.getParameter("baCode")))
				bookingValidateTO
						.setBookingType(request.getParameter("baCode"));
			String bookingType = request.getParameter("baCode");
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			bookingTypeConfigTO = bookingCommonService
					.getBookingTypeConfig(bookingType);
		} catch (Exception e) {
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setErrorMsg(errorMsg);
			LOGGER.error(
					"Error occured in BookingAction::getBookingTypeConfig ::",
					e);
		} finally {
			if (bookingTypeConfigTO != null)
				cnValidationJSON = serializer.toJSON(bookingTypeConfigTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getBookingTypeConfig::END------------>:::::::");
	}

	/**
	 * Validate consignment.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Method : createCashBookingParcel
	 * @Desc :
	 */
	@SuppressWarnings("static-access")
	public void validateConsignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::validateConsignment::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();

		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			bookingValidateTO = new BookingValidationTO();
			if (StringUtils.isNotEmpty(request.getParameter("consgNumber")))
				bookingValidateTO.setConsgNumber(request
						.getParameter("consgNumber"));
			if (StringUtils.isNotEmpty(request.getParameter("rowCount")))
				bookingValidateTO.setRowCount(Integer.parseInt(request
						.getParameter("rowCount")));
			bookingValidateTO.setBookingType(request
					.getParameter("bookingType"));
			bookingValidateTO.setBookingOfficeId(userInfoTO.getOfficeTo()
					.getOfficeId());
			bookingValidateTO.setIssuedTOPartyId1(userInfoTO.getOfficeTo()
					.getOfficeId());
			bookingValidateTO.setOfficeCode(userInfoTO.getOfficeTo()
					.getOfficeCode());
			bookingValidateTO.setLoggedInCityId(userInfoTO.getOfficeTo().getCityId());
			if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
					request.getParameter("bookingType"))) {
				if (StringUtils.isNotEmpty(request.getParameter("baId"))) {
					Integer baId = Integer.parseInt(request
							.getParameter("baId"));
					bookingValidateTO.setIssuedTOPartyId(baId);
				}
			}
			if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
					request.getParameter("bookingType"))
					|| StringUtils.equalsIgnoreCase(
							BookingConstants.FOC_BOOKING,
							request.getParameter("bookingType"))) {
				bookingValidateTO.setIssuedTOPartyId(userInfoTO.getOfficeTo()
						.getOfficeId());
			}
			if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
					request.getParameter("bookingType"))) {
				if (StringUtils.isNotEmpty(request.getParameter("custId"))) {
					Integer custId = Integer.parseInt(request
							.getParameter("custId"));
					bookingValidateTO.setIssuedTOPartyId(custId);
				}
			}
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			// Getting RHO
			OfficeTO rhoOffice = bookingCommonService.getOfficeByIdOrCode(
					userInfoTO.getOfficeTo().getReportingRHO(),
					CommonConstants.EMPTY_STRING);
			if (!StringUtil.isNull(rhoOffice))
				bookingValidateTO.setRhoCode(rhoOffice.getOfficeCode());
			bookingValidateTO.setProcessCode(CommonConstants.PROCESS_BOOKING);
			bookingValidateTO = bookingCommonService
					.validateConsignment(bookingValidateTO);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BookingAction :: validateConsignment() ::"
					+ e.getMessage());
			ResourceBundle errorMessages = null;
			if (e.getMessage().startsWith("ERRSTOCK")) {
				errorMessages = ResourceBundle
						.getBundle(FrameworkConstants.UNIVERALS_MSG_PROP_FILE_NAME);
			} else {
				errorMessages = ResourceBundle
						.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			}
			String errorMsg = errorMessages.getString(e.getMessage());
			bookingValidateTO.setIsValidCN(CommonConstants.NO);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BookingAction :: validateConsignment() ::"
					+ ExceptionUtil.getExceptionStackTrace(e));
			String errorMsg = getSystemExceptionMessage(request, e);
			bookingValidateTO.setErrorMsg(errorMsg);
			bookingValidateTO.setIsValidCN(CommonConstants.NO);
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: validateConsignment() ::"
					+ ExceptionUtil.getExceptionStackTrace(e));
			String errorMsg = ExceptionUtil.getExceptionStackTrace(e);
			bookingValidateTO.setErrorMsg(errorMsg);
			bookingValidateTO.setIsValidCN(CommonConstants.NO);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validateConsignment::END------------>:::::::");
	}

	/**
	 * Validate pincode.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Method : createCashBookingParcel
	 * @Desc :
	 */
	@SuppressWarnings("static-access")
	public void validatePincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::validatePincode::START------------>:::::::");
		BookingValidationTO bookingValidateTO = new BookingValidationTO();
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();

		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			bookingValidateTO.setCityId(userInfoTO.getOfficeTo().getCityId());
			if (StringUtils.isNotEmpty(request.getParameter("bookingOfficeId")))
				bookingValidateTO.setBookingOfficeId((Integer.parseInt(request
						.getParameter("bookingOfficeId"))));
			if (StringUtils.isNotEmpty(request.getParameter("pincode")))
				bookingValidateTO.setPincode(request.getParameter("pincode"));

			if (StringUtils.isNotEmpty(request.getParameter("consgSeries")))
				bookingValidateTO.setConsgSeries(request
						.getParameter("consgSeries"));
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			bookingValidateTO = bookingCommonService
					.validatePincode(bookingValidateTO);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in :: BookingAction :: validatePincode() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in :: BookingAction :: validatePincode() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setIsValidPincode(CommonConstants.NO);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: validatePincode() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setIsValidPincode(CommonConstants.NO);
			bookingValidateTO.setErrorMsg(errorMsg);
			LOGGER.error("Error occured in BookingAction::validatePincode: ::",
					e);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validatePincode::END------------>:::::::");
	}

	/**
	 * Validate pincode for priority product.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Method : createCashBookingParcel
	 * @Desc :
	 */
	@SuppressWarnings("static-access")
	public void validatePincodeForPriorityProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("BookingAction::validatePincodeForPriorityProduct::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();

		try {
			bookingValidateTO = new BookingValidationTO();
			if (StringUtils.isNotEmpty(request.getParameter("bookingOfficeId")))
				bookingValidateTO.setBookingOfficeId((Integer.parseInt(request
						.getParameter("bookingOfficeId"))));
			if (StringUtils.isNotEmpty(request.getParameter("pincode")))
				bookingValidateTO.setPincode(request.getParameter("pincode"));
			if (StringUtils.isNotEmpty(request.getParameter("consgSeries")))
				bookingValidateTO.setConsgSeries(request
						.getParameter("consgSeries"));
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			bookingValidateTO = bookingCommonService
					.validatePincodeForPriorityProduct(bookingValidateTO);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in :: BookingAction :: validatePincodeForPriorityProduct() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in :: BookingAction :: validatePincodeForPriorityProduct() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setErrorMsg(errorMsg);
			LOGGER.error("Error occured in BookingAction :: validatePincodeForPriorityProduct() ::"
					+ e.getMessage());
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validatePincodeForPriorityProduct::END------------>:::::::");
	}

	/**
	 * Sets the payment details.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException
	 */
	public void getPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.debug("getPaymentDetails::getPaymentDetails::Start------------>:::::::");
		List<PaymentModeTO> paymentModeTOs = null;
		String payModeDtlsJSON = null;
		PrintWriter out = null;
		out = response.getWriter();

		try {
			String prodCode = request.getParameter("prodCode");
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			if (!StringUtil.isNull(prodCode)
					&& StringUtils.isNotEmpty(prodCode)
					&& StringUtils.equalsIgnoreCase(
							CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD,
							prodCode)) {
				PaymentModeTO payModeCash = bookingCommonService
						.getPaymentMode(CommonConstants.PROCESS_BOOKING,
								CommonConstants.PAYMENT_MODE_CODE_CASH);
				if (!StringUtil.isNull(payModeCash)) {
					paymentModeTOs = new ArrayList<PaymentModeTO>();
					paymentModeTOs.add(payModeCash);
				}
			} else {
				paymentModeTOs = bookingCommonService.getPaymentDetails();
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: getPaymentDetails() ::"
					+ e.getMessage());
		} finally {
			if (paymentModeTOs != null)
				payModeDtlsJSON = serializer.toJSON(paymentModeTOs).toString();
			out.print(payModeDtlsJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("getPaymentDetails::getPaymentDetails::End------------>:::::::");
	}

	/**
	 * Gets the approvers.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws IOException
	 * @Method : getApprovers
	 * @Desc :
	 */
	public void getApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getApprovers::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		OfficeTO officeTO = null;
		java.io.PrintWriter out = null;
		out = response.getWriter();

		List<EmployeeTO> employeeTOs = null;
		String jsonResult = null;
		Integer screenId = 0;
		String bookingType = null;
		String bookingScreen = null;
		try {
			bookingCommonService = (BookingCommonService) springApplicationContext
					.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			response.setContentType("text/javascript");
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			officeTO = userInfoTO.getOfficeTo();
			bookingType = request.getParameter("bookingType");
			if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
					bookingType)) {
				bookingScreen = BookingConstants.CASH_BOOK_SCREEN;
			} else if (StringUtils.equalsIgnoreCase(
					BookingConstants.FOC_BOOKING, bookingType)) {
				bookingScreen = BookingConstants.FOC_BOOK_SCREEN;
			}
			ApplScreensTO bookingScreenTO = bookingCommonService
					.getScreenByCodeOrName(CommonConstants.EMPTY_STRING,
							bookingScreen);
			if (!StringUtil.isNull(bookingScreenTO)) {
				screenId = bookingScreenTO.getScreenId();
				employeeTOs = bookingCommonService.getApprovers(screenId,
						officeTO.getOfficeId());
				serializer = CGJasonConverter.getJsonObject();
				jsonResult = serializer.toJSON(employeeTOs).toString();
			}
		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("Error occured in BookingAction :: getApprovers() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			// FIXME : Taken care in JS
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: getApprovers() ::"
					+ e.getMessage());
			jsonResult = getGenericExceptionMessage(request, e);

		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getApprovers::END------------>:::::::");
	}

	/**
	 * Sets the content values.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @Method : setContentValues
	 * @Desc : gets the content list for parcel
	 */
	public void setContentValues(HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingAction::setContentValues::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<CNContentTO> cnContentTOs = bookingCommonService
				.getContentValues();
		request.setAttribute("contentVal", cnContentTOs);
		LOGGER.debug("BookingAction::setContentValues::END------------>:::::::");

	}

	public void getOfficeByIdOrCode(HttpServletRequest request,
			HttpServletResponse response, UserInfoTO userInfoTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingAction::getOfficeByIdOrCode::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		OfficeTO rhoOffice = bookingCommonService.getOfficeByIdOrCode(
				userInfoTO.getOfficeTo().getReportingRHO(),
				CommonConstants.EMPTY_STRING);
		if (!StringUtil.isNull(rhoOffice))
			request.setAttribute("loginRHOCode", rhoOffice.getOfficeCode());

		LOGGER.debug("BookingAction::getOfficeByIdOrCode::END------------>:::::::");

	}

	/**
	 * Gets the paper works.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws IOException
	 * @Method : getPaperWorks
	 * @Desc : gets mandatory paperwork for the given pincode
	 */
	public void getPaperWorks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getPaperWorks::START------------>:::::::");
		java.io.PrintWriter out = null;
		out = response.getWriter();
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		String jsonResult = null;
		String pincode = "";
		Double declaredValue = 0.00;
		String docType = "";
		try {
			pincode = request.getParameter("pincode");
			if (StringUtils.isNotEmpty(request.getParameter("declaredValue")))
				declaredValue = Double.parseDouble(request
						.getParameter("declaredValue"));
			docType = request.getParameter("docType");
			CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
			paperWorkValidationTO.setPincode(pincode);
			paperWorkValidationTO.setDocType(docType);
			paperWorkValidationTO.setDeclatedValue(declaredValue);

			response.setContentType("text/javascript");
			bookingCommonService = (BookingCommonService) springApplicationContext
					.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			cnPaperWorksTOs = bookingCommonService
					.getPaperWorks(paperWorkValidationTO);
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(cnPaperWorksTOs).toString();
		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("Error occured in BookingAction :: getPaperWorks() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			// FIXME : Taken care in JS
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: getPaperWorks() ::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getPaperWorks::END------------>:::::::");
	}

	/**
	 * Gets the insuarnce config dtls.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the insuarnce config dtls
	 * @throws IOException
	 */
	public void getInsuarnceConfigDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getInsuarnceConfigDtls::START------------>:::::::");
		java.io.PrintWriter out = null;
		out = response.getWriter();

		List<InsuranceConfigTO> insuranceConfigTO = null;
		String jsonResult = null;
		bookingCommonService = (BookingCommonService) springApplicationContext
				.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		double declaredValue = 0;
		try {
			declaredValue = Double.parseDouble(request
					.getParameter("declaredVal"));
		} catch (NumberFormatException e) {
			LOGGER.error(
					"ERROR : BookingAction.getInsuarnceConfigDtls()::Dec value is null so setting to 0.0",
					e);
		}
		String bookingType = request.getParameter("bookingType");
		try {
			response.setContentType("text/javascript");
			insuranceConfigTO = bookingCommonService.getInsuarnceConfigDtls(
					declaredValue, bookingType);
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(insuranceConfigTO).toString();

		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("Error occured in BookingAction :: getInsuarnceConfigDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: getInsuarnceConfigDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);

		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getInsuarnceConfigDtls::END------------>:::::::");
	}

	/**
	 * Gets the booking config dtls.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the booking config dtls
	 * @throws IOException
	 */
	public void getBookingConfigDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getBookingConfigDtls::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		java.io.PrintWriter out = null;
		out = response.getWriter();

		BookingTypeConfigTO bookingTypeConfigTO = null;
		String jsonResult = null;
		bookingCommonService = (BookingCommonService) springApplicationContext
				.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		Double declaredValue = Double.parseDouble(request
				.getParameter("declaredVal"));
		String bookingType = request.getParameter("bookingType");
		try {
			bookingValidateTO = new BookingValidationTO();
			response.setContentType("text/javascript");
			bookingTypeConfigTO = bookingCommonService
					.getBookingTypeConfig(bookingType);
			if (declaredValue > (bookingTypeConfigTO.getMaxDeclaredValAllowed())) {
				bookingValidateTO.setIsValidDeclaredVal("N");
			}
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(bookingValidateTO).toString();

		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("Error occured in BookingAction :: getBookingConfigDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getBookingConfigDtls::END------------>:::::::");
	}

	/**
	 * Validate cust code.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public void validateCustCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::validateCustCode::START------------>:::::::");
		java.io.PrintWriter out = null;
		out = response.getWriter();
		String jsonResult = null;
		// Boolean isCustValid = Boolean.TRUE;
		BookingValidationTO bookingValidateTO = new BookingValidationTO();
		bookingCommonService = (BookingCommonService) springApplicationContext
				.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		String custCode = request.getParameter("custCode");
		CustomerTO customer = null;
		try {
			response.setContentType("text/javascript");
			customer = bookingCommonService.validateCustCode(custCode,
					CommonConstants.EMPTY_INTEGER);
			if (!StringUtil.isNull(customer)) {
				bookingValidateTO.setCustID(customer.getCustomerId());
				bookingValidateTO.setCustCode(customer.getCustomerCode()
						+ " - " + customer.getBusinessName());
			} else {
				bookingValidateTO.setIsCustValid("N");
			}
			serializer = CGJasonConverter.getJsonObject();
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: validateCustCode() ::"
					+ e.getMessage());
			String errMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setIsCustValid("N");
			bookingValidateTO.setErrorMsg(errMsg);

		} finally {
			jsonResult = serializer.toJSON(bookingValidateTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validateCustCode::END------------>:::::::");
	}

	/**
	 * Gets the customet dtls.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the customet dtls
	 * @throws IOException
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public void getCustometDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("BookingAction::getCustometDtls::START------------>:::::::");
		java.io.PrintWriter out = null;
		out = response.getWriter();

		String jsonResult = null;
		Integer customerId = 0;
		// Boolean isCustValid = Boolean.TRUE;
		bookingCommonService = (BookingCommonService) springApplicationContext
				.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		if (StringUtils.isNotEmpty(request.getParameter("customerId")))
			customerId = Integer.parseInt(request.getParameter("customerId"));
		CustomerTO customer = null;
		try {
			response.setContentType("text/javascript");
			customer = bookingCommonService.validateCustCode(
					CommonConstants.EMPTY_STRING, customerId);
			if (!StringUtil.isNull(customer)) {
				serializer = CGJasonConverter.getJsonObject();
				jsonResult = serializer.toJSON(customer).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: getCustometDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getCustometDtls::END------------>:::::::");
	}

	/**
	 * Gets the process.
	 * 
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProcessTO getProcess() throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingAction::getProcess::Start------------>:::::::");
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		bookingCommonService = (BookingCommonService) springApplicationContext
				.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		process = bookingCommonService.getProcess(process);
		LOGGER.debug("BookingAction::getProcess::END------------>:::::::");
		return process;
	}

	/**
	 * Sets the up default values dox.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void setUpDefaultValuesDox(HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingAction::setUpDefaultValuesDox::Start------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<InsuredByTO> insuredDtls = bookingCommonService.getInsuredByDtls();
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();
		request.setAttribute("consgTypes", consgTypes);
		request.setAttribute("insurance", insuredDtls);
		request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
				.getOfficeId());
		request.setAttribute("insurance", insuredDtls);
		CityTO city = bookingCommonService.getCityByIdOrCode(userInfoTO
				.getOfficeTo().getCityId(), CommonConstants.EMPTY_STRING);
		request.setAttribute("originCity", city.getCityCode());
		request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
		request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		request.setAttribute("CONSG_TYPE_DOX",
				CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		request.setAttribute("CONSG_TYPE_PPX",
				CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		LOGGER.debug("BookingAction::setUpDefaultValuesDox::END------------>:::::::");
	}

	/*
	 * public String getProcessNumber(String processCode, String officeCode)
	 * throws CGSystemException, CGBusinessException {
	 * LOGGER.debug("BookingAction::getProcessNumber::Start------------>:::::::"
	 * ); bookingCommonService = (BookingCommonService)
	 * getBean(SpringConstants.BOOKING_COMMON_SERVICE);
	 * LOGGER.debug("BookingAction::getProcessNumber::End------------>:::::::");
	 * return bookingCommonService.getProcessNumber(processCode, officeCode); }
	 */

	public OfficeTO getLoggedInOffice(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingAction::getLoggedInOffice::Start------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		LOGGER.debug("BookingAction::getLoggedInOffice::End------------>:::::::");
		return userInfoTO.getOfficeTo();
	}

	private RateCalculationInputTO prepareRateInputs(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingAction::prepareRateInputs::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		RateCalculationInputTO inputTO = new RateCalculationInputTO();
		ProductTO product = bookingCommonService
				.getProductByConsgSeries(request.getParameter("productCode"));
		inputTO.setConsignmentType(request.getParameter("consgType"));
		inputTO.setProductCode(product.getProductCode());
		inputTO.setOriginCityCode(request.getParameter("originCity"));
		inputTO.setDestinationPincode(request.getParameter("destPincode"));
		inputTO.setWeight(Double.parseDouble(request
				.getParameter("consgWeight")));
		inputTO.setRateType(request.getParameter("rateType"));
		inputTO.setDiscount(StringUtils.isNotEmpty(request
				.getParameter("discount")) ? Double.parseDouble(request
				.getParameter("discount")) : 0.00);
		inputTO.setDeclaredValue(StringUtils.isNotEmpty(request
				.getParameter("declaredValue")) ? Double.parseDouble(request
				.getParameter("declaredValue")) : 0.00);
		inputTO.setInsuredBy(StringUtils.isNotEmpty(request
				.getParameter("insuredBy")) ? request.getParameter("insuredBy")
				: null);
		inputTO.setOtherCharges((StringUtils.isNotEmpty(request
				.getParameter("splCharges")) ? Double.parseDouble(request
				.getParameter("splCharges")) : 0.00));
		inputTO.setCustomerCode((StringUtils.isNotEmpty(request
				.getParameter("partyCode")) ? request.getParameter("partyCode")
				: null));
		LOGGER.debug("BookingAction::prepareRateInputs::END------------>:::::::");
		return inputTO;
	}

	private ConsignmentTO prepareRateInputsForConsignment(
			HttpServletRequest request) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingAction::prepareRateInputs::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		ConsignmentTO inputTO = new ConsignmentTO();
		inputTO.setConsgNo(request.getParameter("consgNumber"));
		String productCode = request.getParameter("productCode");

		productCode = productCode == null
				|| Character.isDigit(productCode.charAt(0)) ? CommonConstants.PRODUCT_SERIES_NORMALCREDIT
				: productCode;
		ProductTO product = bookingCommonService
				.getProductByConsgSeries(productCode);
		ConsignmentTypeTO consgType = new ConsignmentTypeTO();
		CNPricingDetailsTO cnPricing = new CNPricingDetailsTO();
		consgType.setConsignmentCode(request.getParameter("consgType"));
		inputTO.setTypeTO(consgType);
		inputTO.setProductTO(product);
		inputTO.setOperatingOffice(Integer.parseInt(request
				.getParameter("bookingOfficeId")));
		PincodeTO destPin = new PincodeTO();
		destPin.setPincode(request.getParameter("destPincode"));
		inputTO.setDestPincode(destPin);
		inputTO.setFinalWeight(Double.parseDouble(request
				.getParameter("consgWeight")));
		cnPricing.setRateType(request.getParameter("rateType"));
		if (!StringUtil.isNull(request.getParameter("insuredBy"))
				&& StringUtils.isNotEmpty(request.getParameter("insuredBy"))) {
			InsuredByTO insuredByTO = bookingCommonService
					.getInsuredByNameOrCode(CommonConstants.EMPTY_STRING,
							CommonConstants.EMPTY_STRING,
							Integer.parseInt(request.getParameter("insuredBy")));
			inputTO.setInsuredByTO(insuredByTO);
		}
		if (!StringUtil.isNull(request.getParameter("customerId"))
				&& StringUtils.isNotEmpty(request.getParameter("customerId"))) {
			inputTO.setCustomer(Integer.parseInt(request
					.getParameter("customerId")));
			// for testing
			// inputTO.setCustomer(2629);
			// For ACC Customers
			CustomerTO customer = bookingCommonService.getCustomerByIdOrCode(
					Integer.parseInt(request.getParameter("customerId")),
					CommonConstants.EMPTY_STRING);
			if (StringUtils.equalsIgnoreCase(CommonConstants.CUSTOMER_CODE_ACC,
					customer.getCustomerTypeTO().getCustomerTypeCode())) {
				cnPricing.setRateType(RateCommonConstants.RATE_TYPE_CASH);
			}
		}
		inputTO.setBookingDate(DateUtil.getCurrentDate());

		if (!StringUtil.isStringEmpty(request.getParameter("discount"))) {
			Double discount = Double.parseDouble(request
					.getParameter("discount"));
			if (!StringUtil.isEmptyDouble(discount)) {
				cnPricing.setDiscount(discount);
			}
		}

		if (!StringUtil.isStringEmpty(request.getParameter("declaredValue"))) {
			Double declaredValue = Double.parseDouble(request
					.getParameter("declaredValue"));
			if (!StringUtil.isEmptyDouble(declaredValue)) {
				cnPricing.setDeclaredvalue(declaredValue);
			}
		}

		if (!StringUtil.isStringEmpty(request.getParameter("splCharges"))) {
			Double splCharges = Double.parseDouble(request
					.getParameter("splCharges"));
			if (!StringUtil.isEmptyDouble(splCharges)) {
				cnPricing.setSplChg(splCharges);
			}
		}

		if (!StringUtil.isStringEmpty(request.getParameter("lcAmt"))) {
			Double lcAmt = Double.parseDouble(request.getParameter("lcAmt"));
			if (!StringUtil.isEmptyDouble(lcAmt)) {
				cnPricing.setLcAmount(lcAmt);
			}
		}

		if (!StringUtil.isStringEmpty(request.getParameter("codAmt"))) {
			Double codAmt = Double.parseDouble(request.getParameter("codAmt"));
			if (!StringUtil.isEmptyDouble(codAmt)) {
				cnPricing.setCodAmt(codAmt);
			}
		}

		/*
		 * cnPricing.setSplChg((StringUtils.isNotEmpty(request
		 * .getParameter("splCharges")) ? Double.parseDouble(request
		 * .getParameter("splCharges")) : 0.00));
		 */
		// Need to check with rate service delivery time is not an input fields
		// for all booking and its available in cash booking only
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, productCode)) {
			String priorityService = request.getParameter("priorityService");
			if (priorityService.contains("Before")) {
				cnPricing.setServicesOn("B");
			} else if (priorityService.contains("After")) {
				cnPricing.setServicesOn("A");
			} else {
				cnPricing.setServicesOn("S");
			}
			/*cnPricing.setServicesOn(priorityService != null
			&& priorityService.contains("Before") ? "B" : "A");*/
		}

		switch (productCode) {
		case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
			if (StringUtil.isEmptyDouble(cnPricing.getLcAmount())) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.BOOKING_RETURN_NULL_FOR_LC);
			}
			break;

		case CommonConstants.PRODUCT_SERIES_CASH_COD:
			if (StringUtil.isEmptyDouble(cnPricing.getCodAmt())) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.BOOKING_RETURN_NULL_FOR_COD);
			}
			break;

		}

		inputTO.setConsgPriceDtls(cnPricing);
		LOGGER.debug("BookingAction::prepareRateInputs::END------------>:::::::");
		return inputTO;
	}

	public void getInsuredDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException, IOException {
		LOGGER.debug("BookingAction::getInsuredDtls::START------------>:::::::");
		String jsonResult = null;
		java.io.PrintWriter out = null;
		out = response.getWriter();
		try {
			response.setContentType("text/javascript");
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			List<InsuredByTO> insuredDtls = bookingCommonService
					.getInsuredByDtls();
			if (!StringUtil.isNull(insuredDtls)) {
				serializer = CGJasonConverter.getJsonObject();
				jsonResult = serializer.toJSON(insuredDtls).toString();
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: insuredDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::getInsuredDtls::END------------>:::::::");
	}

	public void validateInsuarnceConfigDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException, IOException {
		LOGGER.debug("BookingAction::validateInsuarnceConfigDtls::START------------>:::::::");
		String jsonResult = null;
		java.io.PrintWriter out = null;
		out = response.getWriter();

		InsuranceConfigTO insuranceConfigTO = null;
		Integer insuredById = 0;
		try {
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			Double declaredValue = Double.parseDouble(request
					.getParameter("declaredVal"));
			String bookingType = request.getParameter("bookingType");
			if (StringUtils.isNotEmpty(request.getParameter("insuredBy"))) {
				insuredById = Integer.parseInt(request
						.getParameter("insuredBy"));
			}
			insuranceConfigTO = bookingCommonService
					.validateInsuarnceConfigDtls(declaredValue, bookingType,
							insuredById);
			if (!StringUtil.isNull(insuranceConfigTO)) {
				serializer = CGJasonConverter.getJsonObject();
				jsonResult = serializer.toJSON(insuranceConfigTO).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in BookingAction :: insuredDtls() ::"
					+ e.getMessage());
			jsonResult = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);

		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::validateInsuarnceConfigDtls::END------------>:::::::");
	}

	@SuppressWarnings("unchecked")
	public Map<String, ConsignmentRateCalculationOutputTO> getConsgRateDetails(
			HttpServletRequest request) {
		LOGGER.debug("BookingAction::getConsgRateDetails::START------------>:::::::");
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		session = (HttpSession) request.getSession(false);
		rateCompnents = 
				(Map<String, ConsignmentRateCalculationOutputTO>) session.getAttribute(BookingConstants.CONSG_RATE_DTLS);
		LOGGER.debug("BookingAction::getConsgRateDetails::Rate Component ::::::"+rateCompnents);
		LOGGER.debug("BookingAction::getConsgRateDetails::END------------>:::::::");
		return rateCompnents;
	}

	public void setNullConsgRateDtls(HttpServletRequest request) {
		LOGGER.debug("BookingAction::setNullConsgRateDtls::Start------------>:::::::");
		HttpSession session = null;
		session = (HttpSession) request.getSession(false);
		session.setAttribute(BookingConstants.CONSG_RATE_DTLS, null);
		LOGGER.debug("BookingAction::setNullConsgRateDtls::END------------>:::::::");
	}
}
