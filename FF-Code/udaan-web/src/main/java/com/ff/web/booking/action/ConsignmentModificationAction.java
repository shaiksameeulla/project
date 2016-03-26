package com.ff.web.booking.action;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.organization.EmployeeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.umc.ApplScreensTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.form.ConsignmentModificatonForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.common.SpringConstants;

/**
 * The Class ConsignmentModificationAction.
 */
public class ConsignmentModificationAction extends BookingAction {

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentModificationAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService = null;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService = null;

	/** The Business Common service. */
	private BusinessCommonService businessCommonService = null;

	/**
	 * View consignment.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public ActionForward viewConsignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		return mapping
				.findForward(BookingConstants.URL_VIEW_CONSIGNMENT_BOOKING);
	}

	/**
	 * Gets the booking.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the booking
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public ActionForward getBooking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException {
		LOGGER.debug("ConsignmentModificationAction::getBooking::START------------>:::::::");
		String consgNumber = "";
		List<ConsignmentModificationTO> bookings = null;
		ConsignmentModificationTO booking = null;
		ConsignmentModificatonForm consgModiForm = null;
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		List<PaymentModeTO> paymentModeTOs = null;
		boolean isChildConsgBooked = Boolean.FALSE;
		try {
			consgModiForm = (ConsignmentModificatonForm) form;
			booking = (ConsignmentModificationTO) consgModiForm.getTo();
			consgNumber = booking.getConsgNumber();
			bookingUniversalService = (BookingUniversalService) getBean(SpringConstants.BOOKING_UNIVERSAL_SERVICE);
			bookingCommonService = (BookingCommonService) springApplicationContext
					.getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			List<String> consgNumbers = new ArrayList<String>(1);
			consgNumbers.add(consgNumber);
			List<InsuredByTO> insuredDtls = bookingCommonService
					.getInsuredByDtls();
			request.setAttribute("insurance", insuredDtls);
			setContentValues(request, response);
			
			List<ConsignmentTypeTO> consgTypes = bookingCommonService
					.getConsignmentType();
			request.setAttribute("consgTypes", consgTypes);
			
			bookings = bookingUniversalService.getBookings(consgNumbers,
					CommonConstants.EMPTY_STRING);
			if (!StringUtil.isEmptyList(bookings)) {
				boolean isConsig = bookingCommonService
						.isConsgNoManifestedForBooking(consgNumber);
				if (isConsig) {
					request.setAttribute("updateStatus", "M");
				}
			}
			if (!StringUtil.isEmptyList(bookings)) {
				booking = bookings.get(0);
				
				if(booking.getUpdatedProcessFrom()==1){
					request.setAttribute("updateStatus", "P");
				}else{
				// Setting rate details
				booking.setCnPricingDtls(booking.getCnPricingDtls());
				request.setAttribute("bookingType", booking.getBookingType());
				request.setAttribute("booking", booking);
				if (!StringUtil.isNull(booking.getConsigmentTO())) {
					CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
					paperWorkValidationTO.setPincode(booking.getPincode());
					paperWorkValidationTO
							.setDocType(booking.getConsgTypeCode());
					if (!StringUtil.isEmptyDouble(booking.getConsigmentTO()
							.getDeclaredValue())) {
						paperWorkValidationTO.setDeclatedValue(booking
								.getConsigmentTO().getDeclaredValue());
						cnPaperWorksTOs = bookingCommonService
								.getPaperWorks(paperWorkValidationTO);
					}
					request.setAttribute("cnPaperWorksTOs", cnPaperWorksTOs);
				}
				// Getting payment detsils
				String prodCode = "";
				if (!StringUtil.isNull(booking.getConsgNumber())) {
					prodCode = booking.getConsgNumber().substring(4, 5);
				}
				if (!StringUtil.isNull(prodCode)
						&& StringUtils.isNotEmpty(prodCode)
						&& StringUtils
								.equalsIgnoreCase(
										CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD,
										prodCode)) {
					PaymentModeTO payMode = null;
					paymentModeTOs = new ArrayList<PaymentModeTO>();
					payMode = bookingCommonService.getPaymentMode(
							CommonConstants.PROCESS_BOOKING,
							CommonConstants.PAYMENT_MODE_CODE_CASH);
					paymentModeTOs.add(payMode);
				} else {
					paymentModeTOs = bookingCommonService.getPaymentDetails();
				}
				request.setAttribute("payModes", paymentModeTOs);
				String bookingType = booking.getBookingType();
				String bookingScreen = null;
				Integer screenId = 0;
				List<EmployeeTO> employeeTOs = null;
				if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
						bookingType)) {
					bookingScreen = BookingConstants.CASH_BOOK_SCREEN;
				} else if (StringUtils.equalsIgnoreCase(
						BookingConstants.FOC_BOOKING, bookingType)) {
					bookingScreen = BookingConstants.FOC_BOOK_SCREEN;
				}
				
				if (!StringUtil.isStringEmpty(bookingScreen)) {
				ApplScreensTO bookingScreenTO = bookingCommonService
						.getScreenByCodeOrName(CommonConstants.EMPTY_STRING,
								bookingScreen);
				if (!StringUtil.isNull(bookingScreenTO)) {
					screenId = bookingScreenTO.getScreenId();
					employeeTOs = bookingCommonService.getApprovers(screenId,
							booking.getBookingOfficeId());
				}

				request.setAttribute("employeeTOs", employeeTOs);
				}
				// Setting priority service dtls
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.PRODUCT_SERIES_PRIORITY, prodCode)) {
					List<PincodeProductServiceabilityTO> pincodeDeliveryTimeMapTOs = null;
					pincodeDeliveryTimeMapTOs = bookingCommonService
							.getPincodeDlvTimeMaps(booking.getPincode(),
									booking.getBookingCityId(), prodCode);
					request.setAttribute("pincodeDeliveryTimeMapTOs",
							pincodeDeliveryTimeMapTOs);
				}
			} }else {

				isChildConsgBooked = bookingCommonService
						.isChildConsgBooked(consgNumber);
				if (isChildConsgBooked) {
					request.setAttribute("updateStatus", "C");
				} else {
					request.setAttribute("updateStatus", "N");
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in ConsignmentModificationAction :: getBooking() ::",
					e);
			request.setAttribute("updateStatus", "E");
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentModificationAction :: getBooking() ::"
					+ e.getMessage());
			request.setAttribute("updateStatus", "E");
		}
		LOGGER.debug("ConsignmentModificationAction::getBooking::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_VIEW_CONSIGNMENT_BOOKING);

	}

	/**
	 * Update booking.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public ActionForward updateBooking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException {
		LOGGER.debug("ConsignmentModificationAction::updateBooking::START------------>:::::::");
		String isUpdated = "N";
		ConsignmentModificationTO bookingTO = null;
		ConsignmentModificatonForm consgModiForm = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			consgModiForm = (ConsignmentModificatonForm) form;
			bookingTO = (ConsignmentModificationTO) consgModiForm.getTo();
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			// Getting Rate compoments from Session
			rateCompnents = getConsgRateDetails(request);
			// isUpdated =
			BookingResultTO result = bookingCommonService.updateBooking(
					bookingTO, rateCompnents);
			isUpdated = result.getTransMessage();
			process2WayWrite(result.getSuccessBookingsIds(),
					result.getSuccessCNsIds());
			request.setAttribute("updateStatus", isUpdated);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in ConsignmentModificationAction :: updateBooking() ::"
					+ e.getMessage());
			getBusinessError(request, e);

		} catch (CGSystemException e) {
			LOGGER.error("Error occured in ConsignmentModificationAction :: updateBooking() ::"
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentModificationAction :: updateBooking() ::"
					+ e.getMessage());
			getGenericException(request, e);
		}
		LOGGER.debug("ConsignmentModificationAction::updateBooking::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_VIEW_CONSIGNMENT_BOOKING);

	}

	/**
	 * function called for printing
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return the URL for print
	 * @throws CGBaseException
	 */
	public ActionForward printViewAndEditBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("ConsignmentModificationAction::printViewAndEditBooking::START------------>:::::::");
		String consgNumber = "";
		List<ConsignmentModificationTO> bookings = null;
		ConsignmentModificationTO consignmentModificationTO = null;
		ConsignorConsigneeTO consignorTO = null;
		ConsignorConsigneeTO consigneeTO = null;
		CityTO cityTO = null;
		String dvalueText = "";
		List<String> dateWeight = new ArrayList<>(10);
		try {
			setUpDefaultValuesDox(request, response);
			consgNumber = request.getParameter("consgNumber");
			bookingUniversalService = getBookingUniversalService();
			bookingCommonService = getBookingCommonService();
			geographyCommonService = getGeographyCommonService();
			businessCommonService = getBusinessCommonService();
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
				CustomerTO customerTO = businessCommonService
						.getCustomer(consignmentModificationTO.getCustomerId());

				consignmentModificationTO.setCustomer(customerTO);
				request.setAttribute(
						BookingConstants.CONSIGNMENT_MODIFICATION_TO,
						consignmentModificationTO);

				Double dValue = consignmentModificationTO.getDeclaredValue();
				DecimalFormat format = new DecimalFormat("#");
				format.setMaximumFractionDigits(0);
				if (dValue != null) {
					dvalueText = format.format(dValue);
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
		} catch (Exception e) {
			LOGGER.error("Error occured in ConsignmentModificationAction :: printViewAndEditBooking() ::"
					+ e.getMessage());
			getGenericException(request, e);
		}
		LOGGER.debug("ConsignmentModificationAction::printViewAndEditBooking::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_PRINT_CONSIGNMENT_BOOKING);

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

	private BusinessCommonService getBusinessCommonService() {
		if (StringUtil.isNull(businessCommonService))
			businessCommonService = (BusinessCommonService) getBean(SpringConstants.BUSINESS_COMMON_SERVICE);
		return businessCommonService;
	}

}
