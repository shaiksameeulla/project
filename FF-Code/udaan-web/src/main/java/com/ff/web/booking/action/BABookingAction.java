package com.ff.web.booking.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BABookingDoxTO;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.converter.BABookingConverter;
import com.ff.web.booking.form.BABookingDoxForm;
import com.ff.web.booking.form.BABookingParcelForm;
import com.ff.web.booking.service.BABookingService;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;

/**
 * The Class BABookingAction.
 */
public class BABookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BABookingAction.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The ba booking service. */
	private BABookingService baBookingService = null;

	/** The business common service. */
	private BusinessCommonService businessCommonService;

	private DecimalFormat newFormat = new DecimalFormat("#.000");

	/**
	 * View ba booking.
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
	public ActionForward viewBABooking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BABookingAction::viewBABooking::START------------>:::::::");
		OfficeTO loggedInOffice = null;
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			loggedInOffice = getLoggedInOffice(request);
			request.setAttribute("originOfficeId", loggedInOffice.getOfficeId());
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			request.setAttribute("originCity", city.getCityCode());
			setContentValues(request, response);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute("docType", docType);
			setDefaultValues(request, response);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception Occured in::BABookingAction::viewBABooking() :: ",
					e);
			actionMessage = new ActionMessage(e.getMessage(), "City");
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::BABookingAction::viewBABooking() :: ",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BABookingAction::viewBABooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_VIEW_BA_BOOKING_DOX);

	}

	/**
	 * View ba booking parcel.
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
	public ActionForward viewBABookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BABookingAction::viewBABookingParcel::START------------>:::::::");
		OfficeTO loggedInOffice = null;
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			loggedInOffice = getLoggedInOffice(request);
			request.setAttribute("originOfficeId", loggedInOffice.getOfficeId());
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			request.setAttribute("originCity", city.getCityCode());
			setContentValues(request, response);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute("docType", docType);
			setDefaultValues(request, response);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::BABookingAction::viewBABookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(e.getMessage(), "City");
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::BABookingAction::viewBABookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BABookingAction::viewBABookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BABookingAction::viewBABookingParcel::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_VIEW_BA_BOOKING_PARCEL);

	}

	/**
	 * Sets the default values.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding XXX page
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @Method : setDefaultValues
	 * @Desc : gets the content list,current date, insurance details
	 */
	private void setDefaultValues(HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("setDefaultValues::setDefaultValues::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
		List<InsuredByTO> insuredDtls = bookingCommonService.getInsuredByDtls();
		request.setAttribute("insurance", insuredDtls);
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();
		request.setAttribute("consgTypes", consgTypes);
		request.setAttribute("bookingType", BookingConstants.BA_BOOKING);
		request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
		request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
		LOGGER.debug("setDefaultValues::setDefaultValues::END------------>:::::::");

	}

	/**
	 * Save or update ba booking dox.
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
	public void saveOrUpdateBABookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BABookingAction::saveOrUpdateBABookingDox::START------------>:::::::");
		BABookingDoxTO bookingDoxTO = null;
		BABookingDoxForm baBookingDoxForm = null;
		String transMag = "";
		PrintWriter out = null;
		BookingWrapperTO bookingWrapperTO = null;
		OfficeTO loggedInOffice = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		boolean isWeighingMachineConnected = Boolean.parseBoolean(request.getParameter(UniversalBookingConstants.REQUEST_PARAM_IS_WEIGHING_MACHINE_CONNECTED));
		try {
			out = response.getWriter();
			baBookingDoxForm = (BABookingDoxForm) form;
			bookingDoxTO = (BABookingDoxTO) baBookingDoxForm.getTo();
			if (bookingDoxTO != null) {
				// Setting the weight captured mode
				if (isWeighingMachineConnected) {
					for (int i=0; i < bookingDoxTO.getWeightCapturedModes().length; i++) {
						bookingDoxTO.getWeightCapturedModes()[i] = CommonConstants.RECORD_STATUS_ACTIVE;
					}
				}
				// Preparing List of TO's from UI
				loggedInOffice = getLoggedInOffice(request);
				bookingWrapperTO = setUpBABookingDoxTos(bookingDoxTO,
						loggedInOffice);
				// Getting Rate compoments from Session
				rateCompnents = getConsgRateDetails(request);
				if (!CGCollectionUtils.isEmpty(rateCompnents))
					bookingWrapperTO.setConsgRateDetails(rateCompnents);
				baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
				BookingResultTO result = baBookingService
						.createBookingAndConsigmentsDox(bookingWrapperTO);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());

			}
		} catch (Exception e) {
			transMag = CommonConstants.FAILURE + "#" + CommonConstants.NO;
			LOGGER.error(
					"Error occured in BABookingAction :: saveOrUpdateBABookingDox() ::",
					e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("BABookingAction::saveOrUpdateBABookingDox::END------------>:::::::");
	}

	/**
	 * Save or update ba booking parcel.
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
	public void saveOrUpdateBABookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BABookingAction::saveOrUpdateBABookingParcel::START------------>:::::::");
		BABookingParcelTO baBookingParcelTO = null;
		BABookingParcelForm baBookingParcelForm = null;
		String transMag = "";
		PrintWriter out = null;
		OfficeTO loggedInOffice = null;
		BookingWrapperTO bookingWrapperTO = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		boolean isWeighingMachineConnected = Boolean.parseBoolean(request.getParameter(UniversalBookingConstants.REQUEST_PARAM_IS_WEIGHING_MACHINE_CONNECTED));
		try {
			out = response.getWriter();
			baBookingParcelForm = (BABookingParcelForm) form;
			baBookingParcelTO = (BABookingParcelTO) baBookingParcelForm.getTo();
			if (baBookingParcelTO != null) {
				// Setting the weight captured mode
				if (isWeighingMachineConnected) {
					for (int i=0; i < baBookingParcelTO.getWeightCapturedModes().length; i++) {
						baBookingParcelTO.getWeightCapturedModes()[i] = CommonConstants.RECORD_STATUS_ACTIVE;
					}
				}
				baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
				// Preparing List of TO's from UI
				loggedInOffice = getLoggedInOffice(request);
				bookingWrapperTO = setUpBABookingParcelTos(baBookingParcelTO,
						loggedInOffice);
				// Getting Rate compoments from Session
				rateCompnents = getConsgRateDetails(request);
				if (!CGCollectionUtils.isEmpty(rateCompnents))
					bookingWrapperTO.setConsgRateDetails(rateCompnents);
				baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
				BookingResultTO result = baBookingService
						.createBookingAndConsigmentsParcel(bookingWrapperTO);
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());

			}
		} catch (Exception e) {
			transMag = CommonConstants.FAILURE + "#" + CommonConstants.NO;
			LOGGER.error(
					"Error occured in BABookingAction :: saveOrUpdateBABookingParcel() ::",
					e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("BABookingAction::saveOrUpdateBABookingParcel::END------------>:::::::");
	}

	/**
	 * Sets the priority service values.
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
	@SuppressWarnings("static-access")
	public void setPriorityServiceValues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BABookingAction::setPriorityServiceValues::START------------>:::::::");
		List<PincodeProductServiceabilityTO> pincodeDeliveryTimeMapTOs = null;
		String prioritySelectJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		String pincode = "";
		String consgSeries = "";
		Integer cityId;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			pincode = request.getParameter("pincode");
			consgSeries = request.getParameter("consgSeries");
			cityId = userInfoTO.getOfficeTo().getCityId();
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			pincodeDeliveryTimeMapTOs = bookingCommonService
					.getPincodeDlvTimeMaps(pincode, cityId, consgSeries);
			if (pincodeDeliveryTimeMapTOs != null)
				prioritySelectJSON = serializer.toJSON(
						pincodeDeliveryTimeMapTOs).toString();
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BABookingAction :: setPriorityServiceValues() ::",
					e);
			prioritySelectJSON = getSystemExceptionMessage(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in BABookingAction :: setPriorityServiceValues() ::",
					e);
			prioritySelectJSON = getMessageFromErrorBundle(request,
					e.getMessage(), null);

		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BABookingAction :: setPriorityServiceValues() ::",
					e);
			prioritySelectJSON = getGenericExceptionMessage(request, e);
		} finally {
			// Need to throw exception.. report to the caller
			out.print(prioritySelectJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BABookingAction::setPriorityServiceValues::END------------>:::::::");
	}

	/**
	 * Checks if is valied ba code.
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
	public void isValiedBACode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BABookingAction::isValiedBACode::START------------>:::::::");
		PrintWriter out = null;
		CustomerTO businessAssociateTO = new CustomerTO();
		Integer baIDvalue = null;
		try {
			out = response.getWriter();
			businessAssociateTO.setCustomerCode(request.getParameter("baCode"));
			businessCommonService = (BusinessCommonService) getBean(SpringConstants.BUSINESS_COMMON_SERVICE);
			baIDvalue = businessCommonService
					.isValiedBACode(businessAssociateTO);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BABookingAction :: isValiedBACode() ::"
					+ e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error occured in BABookingAction :: isValiedBACode() ::"
					+ e.getMessage());
		} finally {
			out.print(baIDvalue);
			out.flush();
			out.close();
		}
		LOGGER.debug("BABookingAction::isValiedBACode::END------------>:::::::");
	}

	/**
	 * Sets the up ba booking dox tos.
	 * 
	 * @param bookingDoxTO
	 *            the booking dox to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingWrapperTO setUpBABookingDoxTos(BABookingDoxTO bookingDoxTO,
			OfficeTO loggedInOffice) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CashBookingAction::setUpBABookingDoxTos::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		BookingTypeTO bookingType = bookingCommonService
				.getBookingType(BookingConstants.BA_BOOKING);
		ProcessTO process = getProcess();
		bookingDoxTO.setProcessTO(process);
		BookingWrapperTO bookingWrapperTO = null;
		bookingDoxTO.setBookingTypeId(bookingType.getBookingTypeId());
		bookingDoxTO.setBookingType(bookingType.getBookingType());
		/*bookingDoxTO
				.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
		String office = loggedInOffice.getOfficeId() + "#"
				+ loggedInOffice.getOfficeName() + " "
				+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
		bookingDoxTO.setBookingOffCode(office);
		CityTO city = bookingCommonService.getCityByIdOrCode(
				loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
		bookingDoxTO.setOriginCity(city.getCityName());
		bookingWrapperTO = BABookingConverter.baBookingDoxConverter(
				bookingDoxTO, bookingCommonService);
		LOGGER.debug("CashBookingAction::setUpBABookingDoxTos::END------------>:::::::");
		return bookingWrapperTO;
	}

	/**
	 * Sets the up ba booking parcel tos.
	 * 
	 * @param bookingParcelTO
	 *            the booking parcel to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingWrapperTO setUpBABookingParcelTos(
			BABookingParcelTO bookingParcelTO, OfficeTO loggedInOffice)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BABookingAction::setUpBABookingParcelTos::START------------>:::::::");
		BookingWrapperTO bookingWrapperTO = null;
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		BookingTypeTO bookingType = bookingCommonService
				.getBookingType(BookingConstants.BA_BOOKING);
		ProcessTO process = getProcess();
		bookingParcelTO.setProcessTO(process);
		bookingParcelTO.setBookingTypeId(bookingType.getBookingTypeId());
		bookingParcelTO.setBookingType(bookingType.getBookingType());
		/*bookingParcelTO
				.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
		String office = loggedInOffice.getOfficeId() + "#"
				+ loggedInOffice.getOfficeName() + " "
				+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
		bookingParcelTO.setBookingOffCode(office);
		CityTO city = bookingCommonService.getCityByIdOrCode(
				loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
		bookingParcelTO.setOriginCity(city.getCityName());
		bookingWrapperTO = BABookingConverter.baBookingParcelConverter(
				bookingParcelTO, bookingCommonService);
		LOGGER.debug("BABookingAction::setUpBABookingParcelTos::END------------>:::::::");
		return bookingWrapperTO;
	}

	/**
	 * Gets the bA bookings dtls.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the bA bookings dtls
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	@SuppressWarnings("unused")
	public ActionForward getBABookingsDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("BABookingAction::getBABookingsDtls::START------------>:::::::");
		BABookingParcelTO bookingDTO = null;
		BABookingDoxForm baBookingDoxForm = null;
		String transMag = "";
		PrintWriter out = null;
		List<BABookingParcelTO> baBookingTOs = null;
		BABookingParcelTO baBookingParcelTO = null;
		BABookingParcelForm baBookingParcelForm = null;
		ActionMessage actionMessage = null;
		try {
			baBookingParcelForm = (BABookingParcelForm) form;
			baBookingParcelTO = (BABookingParcelTO) baBookingParcelForm.getTo();
			if (baBookingParcelTO != null) {
				baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
				if (StringUtils.isNotEmpty(baBookingParcelTO.getBookingDate())
						&& !StringUtil.isEmptyInteger(baBookingParcelTO
								.getBizAssociateId())) {
					baBookingTOs = baBookingService.getBABookings(
							baBookingParcelTO.getBookingDate(),
							baBookingParcelTO.getBizAssociateId());
					if (StringUtil.isEmptyColletion(baBookingTOs)) {
						request.setAttribute("printDisable", "Y");
					} else {
						request.setAttribute("printDisable", "N");
					}
					request.setAttribute("baBookingTOs", baBookingTOs);
					// Setting for retaining selected search criteria
					request.setAttribute("baBookingParcelTO", baBookingParcelTO);
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BABookingAction :: saveOrUpdateBABookingDox() ::",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BABookingAction :: saveOrUpdateBABookingDox() ::",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BABookingAction::getBABookingsDtls::END------------>:::::::");
		return mapping.findForward("viewBABookings");

	}

	public ActionForward viewBookingForBA(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		return mapping.findForward("viewBABookings");
	}

	public ActionForward printBABookingDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BABookingAction::printBABookingDtls::START------------>:::::::");
		List<BABookingParcelTO> baBookingTOs = null;
		BABookingParcelTO baBookingParcelTO = null;
		BABookingParcelForm baBookingParcelForm = null;
		ActionMessage actionMessage = null;
		try {
			baBookingParcelForm = (BABookingParcelForm) form;
			baBookingParcelTO = (BABookingParcelTO) baBookingParcelForm.getTo();
			baBookingService = (BABookingService) getBean(SpringConstants.BA_BOOKING_SERVICE);
			baBookingTOs = baBookingService.getBABookings(
					baBookingParcelTO.getBookingDate(),
					baBookingParcelTO.getBizAssociateId());
			calcTotals(request, baBookingTOs);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BABookingAction :: printBABookingDtls() ::",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BABookingAction :: printBABookingDtls() ::",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BABookingAction::printBABookingDtls::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_BA_BOOKING_PRINT);
	}

	private void calcTotals(HttpServletRequest request,
			List<BABookingParcelTO> baBookingTOs) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("calcTotals::calcTotals::START------------>:::::::");
		Double finalWeight = 0.0;
		Double fuelChg = 0.0;
		Double serviceTax = 0.0;
		Double eduCessChg = 0.0;
		Double higherEduCessChg = 0.0;
		Double finalAmt = 0.0;
		Double slabChg = 0.0;
		Double baamt = 0.0;
		Double codamt = 0.0;
		Double risSurchrg = 0.0;
		String bookingDate = "";
		int noOfPcs = 0;
		if (baBookingTOs != null && baBookingTOs.size() > 0) {
			for (BABookingParcelTO baBookingTO : baBookingTOs) {

				bookingDate = baBookingTO.getBookingDate();
				// Double rowTotal = new Double(0);
				DecimalFormat df = new DecimalFormat("#.##");
				if (baBookingTO.getFinalWeight() != null) {
					// **************start***************************************
					// code for formatting double value
					baBookingTO.setFinalWeight(Double.parseDouble(df
							.format(baBookingTO.getFinalWeight())));

					// **************end***************************************
					finalWeight += baBookingTO.getFinalWeight();
					// rowTotal += baBookingTO.getFinalWeight();
				}
				if (baBookingTO.getConsigmentTO() != null
						&& baBookingTO.getConsigmentTO().getConsgPriceDtls() != null) {
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getFuelChg())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setFuelChg(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getFuelChg())));

						// **************end***************************************

						fuelChg += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getFuelChg();
					}
					// rowTotal +=
					// baBookingTO.getConsigmentTO().getConsgPriceDtls().getFuelChg();
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getServiceTax())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setServiceTax(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getServiceTax())));

						// **************end***************************************
						serviceTax += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getServiceTax();
					}
					// rowTotal +=
					// baBookingTO.getCnPricingDtls().getServiceTax();
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getEduCessChg())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setEduCessChg(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getEduCessChg())));

						// **************end***************************************
						eduCessChg += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getEduCessChg();
					}
					// rowTotal +=
					// baBookingTO.getCnPricingDtls().getEduCessChg();
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getHigherEduCessChg())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setHigherEduCessChg(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getHigherEduCessChg())));

						// **************end***************************************
						higherEduCessChg += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getHigherEduCessChg();
					}
					// rowTotal +=
					// baBookingTO.getCnPricingDtls().getHigherEduCessChg();
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getFinalPrice())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setFinalPrice(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getFinalPrice())));

						// **************end***************************************
						finalAmt += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getFinalPrice();
					}
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getFreightChg())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setFreightChg(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getFreightChg())));

						// **************end***************************************
						slabChg += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getFreightChg();
					}
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getRiskSurChg())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setRiskSurChg(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getRiskSurChg())));

						// **************end***************************************
						risSurchrg += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getRiskSurChg();
					}
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getBaAmt())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setBaAmt(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getBaAmt())));

						// **************end***************************************
						baamt += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getBaAmt();
					}
					if (!StringUtil.isEmptyDouble(baBookingTO.getConsigmentTO()
							.getConsgPriceDtls().getCodAmt())) {
						// **************start***************************************
						// code for formatting double value
						baBookingTO
								.getConsigmentTO()
								.getConsgPriceDtls()
								.setCodAmt(
										Double.parseDouble(df
												.format(baBookingTO
														.getConsigmentTO()
														.getConsgPriceDtls()
														.getCodAmt())));

						// **************end***************************************
						codamt += baBookingTO.getConsigmentTO()
								.getConsgPriceDtls().getCodAmt();
					}
				}

				// Set your desired format here.
				if (!StringUtil.isEmptyInteger(baBookingTO.getNoOfPieces()))
					noOfPcs = noOfPcs + baBookingTO.getNoOfPieces();
				baBookingTO.setPrintRowTotal(df.format(finalAmt));

			}

		}
		OfficeTO loggedInOffice = getLoggedInOffice(request);
		request.setAttribute("bookingDate", bookingDate);
		request.setAttribute("baBookingOffice", loggedInOffice);
		request.setAttribute("baBookingTOs", baBookingTOs);
		request.setAttribute("finalWeight", Double
				.parseDouble(new DecimalFormat("###.##").format(finalWeight)));
		request.setAttribute("fuelChg",
				Double.parseDouble(new DecimalFormat("###.##").format(fuelChg)));
		request.setAttribute("serviceTax", Double
				.parseDouble(new DecimalFormat("###.##").format(serviceTax)));
		request.setAttribute("eduCessChg", Double
				.parseDouble(new DecimalFormat("###.##").format(eduCessChg)));
		request.setAttribute("higherEduCessChg", Double
				.parseDouble(new DecimalFormat("###.##")
						.format(higherEduCessChg)));
		// request.setAttribute("finalAmt", String.format("0:0.000", finalAmt));
		request.setAttribute("baAmt",
				Double.parseDouble(new DecimalFormat("###.##").format(baamt)));
		request.setAttribute("codAmt",
				Double.parseDouble(new DecimalFormat("###.##").format(codamt)));
		request.setAttribute("finalAmt", Double.parseDouble(new DecimalFormat(
				"###.##").format(finalAmt)));
		request.setAttribute("slabChg",
				Double.parseDouble(new DecimalFormat("###.##").format(slabChg)));
		request.setAttribute("totalNoOfPcs", noOfPcs);
		request.setAttribute("risSurchrg", Double
				.parseDouble(new DecimalFormat("###.##").format(risSurchrg)));
		request.setAttribute("todayDate", DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute("todayTime", DateUtil.getCurrentTimeInHHmmss());
		LOGGER.debug("calcTotals::calcTotals::END------------>:::::::");

	}

}
