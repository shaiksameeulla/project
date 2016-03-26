package com.ff.web.booking.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.form.CashBookingDoxForm;
import com.ff.web.booking.form.CashBookingParcelForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.CashBookingService;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;

/**
 * The Class CashBookingAction.
 */
public class CashBookingAction extends BookingAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The cash booking service. */
	private CashBookingService cashBookingService = null;

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService = null;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService = null;

	/**
	 * Creates the cash booking dox.
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
	 * @Method : showCashBookingDox
	 * @Desc :
	 */
	public ActionForward createCashBookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("CashBookingAction::createCashBookingDox::START------------>:::::::");
		ActionMessage actionMessage = null;
		try {
			setUpDefaultValuesDox(request, response);
			request.setAttribute("bookingType", BookingConstants.CASH_BOOKING);
			// setPaymentDetails(request, response);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			getSystemException(request, e);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CashBookingAction::createCashBookingDox::START------------>:::::::");
		return mapping.findForward(BookingConstants.URL_CASH_BOOKING_DOX);

	}

	/**
	 * Creates the cash booking parcel.
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
	 * @Method : createCashBookingParcel
	 * @Desc :
	 */
	public ActionForward createCashBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("CashBookingAction::createCashBookingParcel::START------------>:::::::");
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			request.setAttribute("docType", docType);
			setUpDefaultValuesDox(request, response);
			// setPaymentDetails(request, response);
			setContentValues(request, response);
			request.setAttribute("bookingType", BookingConstants.CASH_BOOKING);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingParcel() :: "
					+ e.getMessage());
			getSystemException(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingParcel() :: "
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CashBookingAction::createCashBookingParcel::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_CASH_BOOKING_PARCEL);

	}

	/**
	 * Save or update cash booking dox.
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
	 * @Method : Save Cash booking Dox
	 * @Desc :
	 */
	public void saveOrUpdateCashBookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("CashBookingAction::saveOrUpdateCashBookingDox::START------------>:::::::");
		CashBookingDoxTO cashBookingDox = null;
		CashBookingDoxForm cashBookingDoxForm = null;
		String transMag = "";
		PrintWriter out = null;
		OfficeTO loggedInOffice = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			out = response.getWriter();
			cashBookingDoxForm = (CashBookingDoxForm) form;
			cashBookingDox = (CashBookingDoxTO) cashBookingDoxForm.getTo();
			if (cashBookingDox != null) {
				loggedInOffice = getLoggedInOffice(request);
				/*cashBookingDox.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
				bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
				CityTO city = bookingCommonService.getCityByIdOrCode(
						loggedInOffice.getCityId(),
						CommonConstants.EMPTY_STRING);
				String office = loggedInOffice.getOfficeId() + "#"
						+ loggedInOffice.getOfficeName() + " "
						+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
				cashBookingDox.setBookingOffCode(office);
				cashBookingDox.setOriginCity(city.getCityName());
				cashBookingService = getCashBookingService();
				// Getting Rate compoments from Session
				rateCompnents = getConsgRateDetails(request);
				if (rateCompnents == null
						|| rateCompnents.isEmpty()
						|| rateCompnents.get(cashBookingDox.getConsgNumber()) == null){
					LOGGER.error(
							"Error occured in :: CashBookingAction ::saveOrUpdateCasgBookingDox()::: Rate Not Calculated");
					new CGBusinessException(
							BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				}

				cashBookingDox.getCnPricingDtls().setRateType(
						RateCommonConstants.RATE_TYPE_CASH);
				BookingResultTO result = cashBookingService
						.saveOrUpdateBookingDox(cashBookingDox, rateCompnents);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());
				String messge = getMessageFromErrorBundle(request,
						BookingErrorCodesConstants.CN_BOOKED, null);
				transMag = transMag + "#" + messge;
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::",
					e);
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			transMag = CommonConstants.FAILURE + "#" + errorMsg;
		} catch (CGSystemException e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error(
					"CGSystemException occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::",
					e);
		} catch (Exception e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error(
					"Exception occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::",
					e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();

		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("CashBookingAction::saveOrUpdateCashBookingDox::END------------>:::::::");

	}

	/**
	 * Save or update cash booking parcel.
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
	 * @Method : Save Cash booking Dox
	 * @Desc :
	 */
	public void saveOrUpdateCashBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("CashBookingAction::saveOrUpdateCashBookingParcel::START------------>:::::::");
		CashBookingParcelTO cashBookingParcel = null;
		CashBookingParcelForm cashBookingPatcelForm = null;
		String transMag = "";
		PrintWriter out = null;
		OfficeTO loggedInOffice = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			out = response.getWriter();
			cashBookingPatcelForm = (CashBookingParcelForm) form;
			cashBookingParcel = (CashBookingParcelTO) cashBookingPatcelForm
					.getTo();
			if (cashBookingParcel != null) {
				loggedInOffice = getLoggedInOffice(request);
				/*cashBookingParcel.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
				bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
				CityTO city = bookingCommonService.getCityByIdOrCode(
						loggedInOffice.getCityId(),
						CommonConstants.EMPTY_STRING);
				String office = loggedInOffice.getOfficeId() + "#"
						+ loggedInOffice.getOfficeName() + " "
						+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
				cashBookingParcel.setBookingOffCode(office);
				cashBookingParcel.setOriginCity(city.getCityName());
				cashBookingService = getCashBookingService();
				rateCompnents = getConsgRateDetails(request);
				if (rateCompnents == null
						|| rateCompnents.isEmpty()
						|| rateCompnents
								.get(cashBookingParcel.getConsgNumber()) == null){
					LOGGER.error(
							"Error occured in :: CashBookingAction ::saveOrUpdateCashBookingParcel()::: Rate Not Calculated");
					new CGBusinessException(
							BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				}

				cashBookingParcel.getCnPricingDtls().setRateType(
						RateCommonConstants.RATE_TYPE_CASH);
				BookingResultTO result = cashBookingService
						.saveOrUpdateBookingParcel(cashBookingParcel,
								rateCompnents);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());
				String messge = getMessageFromErrorBundle(request,
						BookingErrorCodesConstants.CN_BOOKED, null);
				transMag = transMag + "#" + messge;
			}

		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in :: CashBookingAction :: saveOrUpdateCashBookingParcel() ::",
					e);
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			transMag = CommonConstants.FAILURE + "#" + errorMsg;
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCashBookingParcel() ::"
					+ e.getMessage());
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
		} catch (Exception e) {
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCashBookingParcel() ::"
					+ e.getMessage());
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("CashBookingAction::saveOrUpdateCashBookingParcel::END------------>:::::::");
	}

	/**
	 * Sets the default values.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	/*
	 * private void setDefaultValues(HttpServletRequest request,
	 * HttpServletResponse response) throws CGSystemException {
	 * setUpDefaultValuesDox(request, response);
	 * request.setAttribute("bookingType", BookingConstants.CASH_BOOKING); }
	 */

	/**
	 * Validate discount.
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
	public void validateDiscount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashBookingAction::validateDiscount::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			bookingValidateTO = new BookingValidationTO();
			if (StringUtils.isNotEmpty(request.getParameter("discount")))
				bookingValidateTO.setMaxDiscount(Double.parseDouble(request
						.getParameter("discount")));
			bookingValidateTO.setBookingType(BookingConstants.CASH_BOOKING);
			cashBookingService = getCashBookingService();
			bookingValidateTO = cashBookingService
					.validateCashDiscount(bookingValidateTO);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validateDiscount() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validateDiscount() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validateDiscount() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashBookingAction::validateDiscount::END------------>:::::::");
	}

	/**
	 * Validate privilege card.
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
	public void validatePrivilegeCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException, IOException {
		LOGGER.debug("CashBookingAction::validatePrivilegeCard::START------------>:::::::");
		BookingValidationTO bookingValidateTO = null;
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			bookingValidateTO = new BookingValidationTO();
			if (StringUtils
					.isNotEmpty(request.getParameter("privilegeCardAmt")))
				bookingValidateTO.setPrivilegeCardAmt(Double
						.parseDouble(request.getParameter("privilegeCardAmt")));
			bookingValidateTO.setPrivilegeCardNo(request
					.getParameter("privilegeCardNo"));
			bookingValidateTO.setBookingType(BookingConstants.CASH_BOOKING);
			cashBookingService = getCashBookingService();
			bookingValidateTO = cashBookingService
					.validatePrivilegeCard(bookingValidateTO);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validatePrivilegeCard() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			if (bookingValidateTO.getPrivilegeCardAvalBal() != null
					&& bookingValidateTO.getPrivilegeCardAvalBal() > 0)
				errorMsg = errorMsg + " "
						+ bookingValidateTO.getPrivilegeCardAvalBal();
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validateDiscount() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error("Error occured in :: CashBookingAction :: validateDiscount() ::"
					+ e.getMessage());
			String errorMsg = getMessageFromErrorBundle(request,
					UdaanCommonErrorCodes.SYS_ERROR, null);
			bookingValidateTO.setErrorMsg(errorMsg);
		} finally {
			if (bookingValidateTO != null)
				cnValidationJSON = serializer.toJSON(bookingValidateTO)
						.toString();
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashBookingAction::validatePrivilegeCard::END------------>:::::::");
	}

	/**
	 * Gets the cash booking service.
	 * 
	 * @return the cash booking service
	 */
	private CashBookingService getCashBookingService() {
		if (StringUtil.isNull(cashBookingService))
			cashBookingService = (CashBookingService) getBean(SpringConstants.CASH_BOOKING_SERVICE);
		return cashBookingService;
	}

	

	private BookingUniversalService getBookingUniversalService() {
		if (StringUtil.isNull(bookingUniversalService))
			bookingUniversalService = (BookingUniversalService) getBean(SpringConstants.BOOKING_UNIVERSAL_SERVICE);
		return bookingUniversalService;
	}

	private BookingCommonService getBookingCommonService() {
		if (StringUtil.isNull(bookingCommonService))
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		return bookingCommonService;
	}

	private GeographyCommonService getGeographyCommonService() {
		if (StringUtil.isNull(geographyCommonService))
			geographyCommonService = (GeographyCommonService) getBean(SpringConstants.GEOGRAPHY_COMMON_SERVICE);
		return geographyCommonService;
	}

	public ActionForward printCashBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("CashBookingAction::printCashBooking::START------------>:::::::");
		String consgNumber = "";
		List<ConsignmentModificationTO> bookings = null;
		ConsignmentModificationTO consignmentModificationTO = null;
		ConsignorConsigneeTO consignorTO = null;
		ConsignorConsigneeTO consigneeTO = null;
		CityTO cityTO = null;
		String dvalueText="";
		List<String> dateWeight = new ArrayList<>(10);
		try {
			setUpDefaultValuesDox(request, response);
			consgNumber = request.getParameter("consgNumber");
			bookingUniversalService = getBookingUniversalService();
			bookingCommonService = getBookingCommonService();
			geographyCommonService = getGeographyCommonService();
			List<String> consgNumbers = new ArrayList<String>(1);
			consgNumbers.add(consgNumber);
			// setPaymentDetails(request, response);
			List<InsuredByTO> insuredDtls = bookingCommonService
					.getInsuredByDtls();
			request.setAttribute(BookingConstants.INSURED_DTLS, insuredDtls);
			// setContentValues
			List<CNContentTO> cnContentTOs = bookingCommonService
					.getContentValues();
			request.setAttribute(BookingConstants.CN_CONTENT_TO, cnContentTOs);

			bookings = bookingUniversalService.getBookings(consgNumbers,
					CommonConstants.EMPTY_STRING);
			if (!StringUtil.isEmptyList(bookings)) {
				consignmentModificationTO = bookings.get(0);
				// Setting rate details
				consignmentModificationTO
						.setCnPricingDtls(consignmentModificationTO
								.getCnPricingDtls());
				request.setAttribute(
						BookingConstants.CONSIGNMENT_MODIFICATION_TO,
						consignmentModificationTO);
				
				Double dValue=consignmentModificationTO.getDeclaredValue();
				DecimalFormat format=new DecimalFormat("#");
				format.setMaximumFractionDigits(0);
				if(dValue !=null)
				{
					dvalueText=format.format(dValue);
				}
				request.setAttribute("decl_value", dvalueText);
				consignorTO = consignmentModificationTO.getConsignor();
				consigneeTO = consignmentModificationTO.getConsignee();
				request.setAttribute(BookingConstants.CONSIGNOR_TO, consignorTO);
				request.setAttribute(BookingConstants.CONSIGNEE_TO, consigneeTO);
			}
			if (!StringUtil.isEmptyInteger(consignmentModificationTO
					.getBookingOfficeId())) {
				cityTO = geographyCommonService
						.getCityByOfficeId(consignmentModificationTO
								.getBookingOfficeId());
				request.setAttribute(BookingConstants.BOOKING_CITY,
						cityTO.getCityName());
			}
			if (!StringUtil.isEmptyInteger(consignmentModificationTO
					.getPincodeId())) {
				cityTO = geographyCommonService
						.getCityByPincodeId(consignmentModificationTO
								.getPincodeId());
				request.setAttribute(BookingConstants.DESTINATION_CITY,
						cityTO.getCityName());
			}
			if (!StringUtil.isStringEmpty(consignmentModificationTO
					.getBookingDate())) {
				String date = consignmentModificationTO.getBookingDate();
				String splitDate[] = date.split(" ");
				String bookingDate = splitDate[0];
				String bookingTime = splitDate[1];
				dateWeight.add(bookingDate);
				dateWeight.add(bookingTime);
			}
			if (!StringUtil.isEmptyDouble(consignmentModificationTO
					.getActualWeight())) {
				Double actualWeight = consignmentModificationTO
						.getActualWeight();
				String actualWeight1 = actualWeight.toString();
				String splitActualWeight[] = actualWeight1.split("\\.");

				String kgs = splitActualWeight[0];
				String gms = splitActualWeight[1];
				dateWeight.add(kgs);
				dateWeight.add(gms);
			}
			if (!StringUtil.isEmptyDouble(consignmentModificationTO
					.getFinalWeight())) {
				Double finalWeight = consignmentModificationTO.getFinalWeight();
				String finalWeight1 = finalWeight.toString();
				String splitFinalWeight[] = finalWeight1.split("\\.");
				String kgs2 = splitFinalWeight[0];
				String gms2 = splitFinalWeight[1];
				dateWeight.add(kgs2);
				dateWeight.add(gms2);
			}
			if (!StringUtil.isEmptyList(dateWeight)) {
				request.setAttribute(BookingConstants.DATE_WEIGHT, dateWeight);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: CashBookingAction :: printCashBooking() :: ", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: CashBookingAction :: printCashBooking() :: ", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CashBookingAction :: printCashBooking() :: ", e);
			getGenericException(request, e);
		}
		LOGGER.debug("CashBookingAction::printCashBooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_PRINT_CASH_BOOKING);

	}

}
