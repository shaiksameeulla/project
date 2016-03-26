package com.ff.web.booking.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.booking.FOCBookingDoxTO;
import com.ff.booking.FOCBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.form.FOCBookingDoxForm;
import com.ff.web.booking.form.FOCBookingParcelForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.FOCBookingService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;

/**
 * The Class FOCBookingAction.
 */
public class FOCBookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(FOCBookingAction.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;
	// private CashBookingService cashBookingService = null;
	/** The foc booking service. */
	private FOCBookingService focBookingService = null;

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService = null;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService = null;

	
	/** The Business Common service. */
	private BusinessCommonService businessCommonService = null;
	/**
	 * View foc booking.
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
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ActionForward viewFOCBooking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("FOCBookingAction::viewFOCBooking::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			setContentValues(request, response);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute("docType", docType);
			request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
			request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
			setDefaultValues(request, response);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBooking() :: "
					+ e.getMessage());
			getSystemException(request, e);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBooking() :: "
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBooking() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("FOCBookingAction::viewFOCBooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_VIEW_FOC_BOOKING_DOX);

	}

	/**
	 * View foc booking parcel.
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
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ActionForward viewFOCBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("FOCBookingAction::viewFOCBookingParcel::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			setContentValues(request, response);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute("docType", docType);
			request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
			request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
			setDefaultValues(request, response);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBookingParcel() :: "
					+ e.getMessage());
			getSystemException(request, e);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBookingParcel() :: "
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::FOCBookingAction::viewFOCBookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("FOCBookingAction::viewFOCBookingParcel::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_VIEW_FOC_BOOKING_PARCEL);

	}

	/**
	 * Sets the default values.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 */
	private void setDefaultValues(HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("FOCBookingAction::setDefaultValues::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<InsuredByTO> insuredDtls = bookingCommonService.getInsuredByDtls();
		request.setAttribute("insurance", insuredDtls);
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();
		request.setAttribute("consgTypes", consgTypes);
		request.setAttribute("bookingType", BookingConstants.FOC_BOOKING);
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		LOGGER.debug("FOCBookingAction::setDefaultValues::END------------>:::::::");
	}

	/**
	 * Save or update foc booking dox.
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
	 */
	public void saveOrUpdateFOCBookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("FOCBookingAction::saveOrUpdateFOCBookingDox::START------------>:::::::");
		FOCBookingDoxTO focBookingDoxTO = null;
		FOCBookingDoxForm focBookingDoxForm = null;
		String transMag = "";
		PrintWriter out = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			focBookingDoxForm = (FOCBookingDoxForm) form;
			focBookingDoxTO = (FOCBookingDoxTO) focBookingDoxForm.getTo();
			if (focBookingDoxTO != null) {
				focBookingService = getFOCBookingService();
				// Setting CN Pricing rates
				CNPricingDetailsTO cnPricingDetls = BookingUtils
						.setUpConsgPricingDtls(focBookingDoxTO
								.getConsgRateDtls());
				if (!StringUtil.isNull(cnPricingDetls)) {
					cnPricingDetls
							.setRateType(RateCommonConstants.RATE_TYPE_CASH);
					focBookingDoxTO.setCnPricingDtls(cnPricingDetls);
				}
				rateCompnents = getConsgRateDetails(request);
				if (rateCompnents == null
						|| rateCompnents.isEmpty()
						|| rateCompnents.get(focBookingDoxTO.getConsgNumber()) == null)
					throw new CGBusinessException(
							BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				BookingResultTO result = focBookingService
						.saveOrUpdateBookingDox(focBookingDoxTO, rateCompnents);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());
				String messge = getMessageFromErrorBundle(request,
						BookingErrorCodesConstants.CN_BOOKED, null);
				transMag = transMag + "#" + messge;
				out = response.getWriter();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in :: CashBookingAction :: validateDiscount() ::",
					e);
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			transMag = CommonConstants.FAILURE + "#" + errorMsg;

		} catch (CGSystemException e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::"
					+ e.getMessage());
		} catch (Exception e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::"
					+ e.getMessage());
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("FOCBookingAction::saveOrUpdateFOCBookingDox::END------------>:::::::");
	}

	public ActionForward printFOCBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("FOCBookingAction::saveOrUpdateFOCBookingDox::START------------>:::::::");
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
		
		} catch (Exception e) {
			LOGGER.error("Error occured in :: FOCBookingAction :: printFOCBooking() ::"
					+ e.getMessage());
		}
		LOGGER.debug("FOCBookingAction::printFOCBooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_PRINT_FOC_BOOKING);
	}

	/**
	 * Save or update foc booking parcel.
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
	 */
	public void saveOrUpdateFocBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("FOCBookingAction::saveOrUpdateFocBookingParcel::START------------>:::::::");
		FOCBookingParcelTO fOCBookingParcelTO = null;
		FOCBookingParcelForm fOCBookingParcelForm = null;
		String transMag = "";
		PrintWriter out = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		try {
			fOCBookingParcelForm = (FOCBookingParcelForm) form;
			fOCBookingParcelTO = (FOCBookingParcelTO) fOCBookingParcelForm
					.getTo();
			if (fOCBookingParcelTO != null) {
				focBookingService = getFOCBookingService();
				// Setting CN Pricing rates
				CNPricingDetailsTO cnPricingDetls = BookingUtils
						.setUpConsgPricingDtls(fOCBookingParcelTO
								.getConsgRateDtls());
				if (!StringUtil.isNull(cnPricingDetls)) {
					cnPricingDetls
							.setRateType(RateCommonConstants.RATE_TYPE_CASH);
					fOCBookingParcelTO.setCnPricingDtls(cnPricingDetls);
					if (!StringUtil.isEmptyDouble(fOCBookingParcelTO
							.getDeclaredValue())) {
						cnPricingDetls.setDeclaredvalue(fOCBookingParcelTO
								.getDeclaredValue());
					}
				}
				rateCompnents = getConsgRateDetails(request);
				if (rateCompnents == null
						|| rateCompnents.isEmpty()
						|| rateCompnents.get(fOCBookingParcelTO
								.getConsgNumber()) == null)
					new CGBusinessException(
							BookingErrorCodesConstants.RATE_NOT_CALCULATED);

				BookingResultTO result = focBookingService
						.saveOrUpdateBookingParcel(fOCBookingParcelTO,
								rateCompnents);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());
				String messge = getMessageFromErrorBundle(request,
						BookingErrorCodesConstants.CN_BOOKED, null);
				transMag = transMag + "#" + messge;
			}
			out = response.getWriter();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in :: CashBookingAction :: validateDiscount() ::",
					e);
			String errorMsg = getMessageFromErrorBundle(request,
					e.getMessage(), null);
			transMag = CommonConstants.FAILURE + "#" + errorMsg;

		} catch (CGSystemException e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::"
					+ e.getMessage());
		} catch (Exception e) {
			String sysError = getMessageFromErrorBundle(request,
					BookingErrorCodesConstants.CN_NOT_BOOKED, null);
			transMag = CommonConstants.FAILURE + "#" + sysError;
			LOGGER.error("Error occured in :: CashBookingAction :: saveOrUpdateCasgBookingDox() ::"
					+ e.getMessage());
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("FOCBookingAction::saveOrUpdateFocBookingParcel::END------------>:::::::");
	}

	/**
	 * Gets the fOC booking service.
	 * 
	 * @return the fOC booking service
	 */
	private FOCBookingService getFOCBookingService() {
		if (StringUtil.isNull(focBookingService))
			focBookingService = (FOCBookingService) getBean(SpringConstants.FOC_BOOKING_SERVICE);
		return focBookingService;
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
