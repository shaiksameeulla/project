/**
 * 
 */
package com.ff.web.booking.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.EmotionalBondBookingTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.form.EmotionalBondBookingForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.EmotionalBondBookingService;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;
import com.ff.web.global.service.GlobalService;

//import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * The Class EmotionalBondBookingAction.
 * 
 * @author uchauhan
 */
public class EmotionalBondBookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EmotionalBondBookingAction.class);

	/** The emotional bond booking service. */
	private EmotionalBondBookingService emotionalBondBookingService = null;
	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The global service. */
	private GlobalService globalService = null;

	/**
	 * Save emotional bond booking data.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void saveEmotionalBondBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("EmotionalBondBookingAction::saveEmotionalBondBooking::START------------>:::::::");
		EmotionalBondBookingTO emotionalBondBookingTO = null;
		EmotionalBondBookingForm emotionalBondBookingForm = null;
		String transMag = "";
		PrintWriter out = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		String ebNum = "";
		OfficeTO loggedInOffice = null;
		Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls = null;
		try {
			out = response.getWriter();
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			String officeCode = userInfoTO.getOfficeTo().getOfficeCode();
			Integer seqNums = 1;
			ebNum = emotionalBondBookingService.generateEBNum(officeCode,
					seqNums);
			emotionalBondBookingForm = (EmotionalBondBookingForm) form;
			emotionalBondBookingTO = (EmotionalBondBookingTO) emotionalBondBookingForm
					.getTo();
			loggedInOffice = getLoggedInOffice(request);
			emotionalBondBookingTO.setConsgNumber(ebNum);
			/*emotionalBondBookingTO.setProcessNumber(getProcessNumber(
					CommonConstants.PROCESS_BOOKING,
					loggedInOffice.getOfficeCode()));*/
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			String office = loggedInOffice.getOfficeId() + "#"
					+ loggedInOffice.getOfficeName() + " "
					+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
			emotionalBondBookingTO.setBookingOffCode(office);
			emotionalBondBookingTO.setOriginCity(city.getCityName());

			CNPricingDetailsTO consgPricingDtls=new CNPricingDetailsTO();
			consgPricingDtls.setRateType(RateCommonConstants.RATE_TYPE_CASH);
			emotionalBondBookingTO.setCnPricingDtls(consgPricingDtls);
			
			if (emotionalBondBookingTO != null) {
				consgRateDtls = getConsgRateDetails(request);
				BookingResultTO result = emotionalBondBookingService
						.saveOrUpdateBookingEmotionalBond(
								emotionalBondBookingTO, consgRateDtls);

				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(),
						result.getSuccessCNsIds());
				transMag = transMag + "#" + ebNum;

			}
		} catch (Exception e) {
			transMag = CommonConstants.FAILURE + "#" + ebNum;
			LOGGER.error(
					"Error occured in EmotionalBondBookingAction :: saveOrUpdateBABookingParcel() ::",
					e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("EmotionalBondBookingAction::saveEmotionalBondBooking::END------------>:::::::");
	}

	/**
	 * Populates the screen with initial data
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
	 */
	public ActionForward viewEmotionalBondBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("EmotionalBondBookingAction::viewEmotionalBondBooking::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		Integer stateId = null;
		ActionMessage actionMessage = null;
		try {
			emotionalBondBookingService = (EmotionalBondBookingService) getBean(SpringConstants.EMOTIONAL_BOND_BOOKING_SERVICE);
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			session.removeAttribute(BookingConstants.CONSG_RATE_DTLS);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			CityTO city = bookingCommonService.getCityByIdOrCode(
					officeTO.getCityId(), CommonConstants.EMPTY_STRING);
			if (!StringUtil.isNull(city)) {
				stateId = city.getState();
			}
			List<BookingPreferenceDetailsTO> bookingPrefDetails = emotionalBondBookingService
					.getBookingPrefDetails(stateId);
			List<List<BookingPreferenceDetailsTO>> rowContainer = getRowContent(bookingPrefDetails);
			request.setAttribute("rowContainer", rowContainer);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("bookingType",
					BookingConstants.EMOTIONAL_BOND_BOOKING);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute("createdBy", userInfoTO.getUserto()
					.getUserId());
			request.setAttribute("updatedBy", userInfoTO.getUserto()
					.getUserId());
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::EmotionalBondBookingAction::viewEmotionalBondBooking() :: "
					+ e.getMessage());
			getSystemException(request, e);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::EmotionalBondBookingAction::viewEmotionalBondBooking() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(
					BookingErrorCodesConstants.PREF_NOT_CONFIGURED);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::EmotionalBondBookingAction::viewEmotionalBondBooking() :: "
					+ e.getMessage());
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("EmotionalBondBookingAction::viewEmotionalBondBooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.EMOTIONAL_BOND_BOOKING_URL);
	}

	/**
	 * Gets the row content and populates the screen with preference options.
	 * 
	 * @param bookingPrefDetails
	 *            the booking pref details
	 * @return the row content
	 */
	private List<List<BookingPreferenceDetailsTO>> getRowContent(
			List<BookingPreferenceDetailsTO> bookingPrefDetails) {
		LOGGER.debug("EmotionalBondBookingAction::getRowContent::END------------>:::::::");
		List<List<BookingPreferenceDetailsTO>> rowContainer = null;
		if (bookingPrefDetails != null && !bookingPrefDetails.isEmpty()) {
			int temp = 0;// mnifestTypes.size();
			rowContainer = new ArrayList<List<BookingPreferenceDetailsTO>>();
			List<BookingPreferenceDetailsTO> mftTypeRows = null;
			for (BookingPreferenceDetailsTO to : bookingPrefDetails) {
				if (temp == 0 || temp % 2 == 0) {
					mftTypeRows = new ArrayList<BookingPreferenceDetailsTO>();
					rowContainer.add(mftTypeRows);
				}

				mftTypeRows.add(to);
				temp++;
			}
		}
		LOGGER.debug("EmotionalBondBookingAction::getRowContent::END------------>:::::::");
		return rowContainer;
	}

	/**
	 * populates screen with initial details
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
	public ActionForward viewBookingsDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("EmotionalBondBookingAction::viewBookingsDtls::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		ActionMessage actionMessage = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("originBranch", userInfoTO.getOfficeTo()
					.getOfficeName());
			request.setAttribute("bookingType",
					BookingConstants.EMOTIONAL_BOND_BOOKING);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingParcel() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("EmotionalBondBookingAction::viewBookingsDtls::END------------>:::::::");
		return mapping.findForward("viewEBBookings");
	}

	/**
	 * Retrives the booking details for selected date
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the eB bookings dtls
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	@SuppressWarnings("unused")
	public ActionForward getEBBookingsDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("EmotionalBondBookingAction::getEBBookingsDtls::START------------>:::::::");
		EmotionalBondBookingTO emotionalBondBookingTO = null;
		EmotionalBondBookingForm emotionalBondBookingForm = null;
		String transMag = "";
		PrintWriter out = null;
		List<EmotionalBondBookingTO> ebBookingTOs = null;
		List<StockStandardTypeTO> standardTos = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		Integer loginUserId = 0;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			loginUserId = userInfoTO.getOfficeTo().getOfficeId();
			emotionalBondBookingForm = (EmotionalBondBookingForm) form;
			emotionalBondBookingTO = (EmotionalBondBookingTO) emotionalBondBookingForm
					.getTo();
			String typeName = BookingConstants.STANDARD_BOOKING_TYPE;
			if (emotionalBondBookingTO != null) {
				emotionalBondBookingService = (EmotionalBondBookingService) getBean(SpringConstants.EMOTIONAL_BOND_BOOKING_SERVICE);
				request.setAttribute("bookingType",
						BookingConstants.EMOTIONAL_BOND_BOOKING);
				String bookingType = request.getParameter("bookingType");
				ebBookingTOs = emotionalBondBookingService.getEBBookings(
						emotionalBondBookingTO.getDelvDateTime(),
						BookingConstants.EMOTIONAL_BOND_BOOKING, loginUserId);
				if (!StringUtil.isEmptyList(ebBookingTOs)) {
					globalService = (GlobalService) getBean(SpringConstants.GLOBAL_SERVICE);
					standardTos = globalService
							.getAllStockStandardType(typeName);
					request.setAttribute("standardTos", standardTos);
					request.setAttribute("baBookingTOs", ebBookingTOs);
					request.setAttribute("originOfficeId", loginUserId);
					request.setAttribute("originBranch", userInfoTO
							.getOfficeTo().getOfficeName());
				} else {
					transMag = getMessageFromErrorBundle(request,
							BookingErrorCodesConstants.NO_RECORDS_EXISTS);
					request.setAttribute("transMag", transMag);
				}

			}
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in EmotionalBondBookingAction :: getEBBookingsDtls() ::"
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Error occured in EmotionalBondBookingAction :: getEBBookingsDtls() ::"
					+ e.getMessage());
		}
		LOGGER.debug("EmotionalBondBookingAction::getEBBookingsDtls::END------------>:::::::");
		return mapping.findForward("viewEBBookings");

	}

	/**
	 * Updates the bookings details.
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
	public ActionForward updateEBBookingsDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("EmotionalBondBookingAction::updateEBBookingsDtls::START------------>:::::::");
		EmotionalBondBookingTO emotionalBondBookingTO = null;
		EmotionalBondBookingForm emotionalBondBookingForm = null;
		Boolean isUpdated = Boolean.FALSE;
		String transMag = "";
		try {
			emotionalBondBookingForm = (EmotionalBondBookingForm) form;
			emotionalBondBookingTO = (EmotionalBondBookingTO) emotionalBondBookingForm
					.getTo();
			if (emotionalBondBookingTO != null) {
				emotionalBondBookingService = (EmotionalBondBookingService) getBean(SpringConstants.EMOTIONAL_BOND_BOOKING_SERVICE);
				isUpdated = emotionalBondBookingService
						.updateEBBookingsDtls(emotionalBondBookingTO);
				if (isUpdated) {
					transMag = getMessageFromErrorBundle(request,
							BookingErrorCodesConstants.VALID_BOOKINGS_UPDATED);
					request.setAttribute("transMag", transMag);
				}

			}
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in EmotionalBondBookingAction :: updateEBBookingsDtls() ::"
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Error occured in EmotionalBondBookingAction :: updateEBBookingsDtls() ::"
					+ e.getMessage());
		}
		LOGGER.debug("EmotionalBondBookingAction::updateEBBookingsDtls::END------------>:::::::");
		return mapping.findForward("viewEBBookings");
	}

	/**
	 * Gets the message from error bundle.
	 * 
	 * @param request
	 *            the request
	 * @param e
	 *            the e
	 * @return the message from error bundle
	 */
	private String getMessageFromErrorBundle(HttpServletRequest request,
			String e) {
		LOGGER.debug("EmotionalBondBookingAction::getMessageFromErrorBundle::Start------------>:::::::");
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(e);
		}
		LOGGER.debug("EmotionalBondBookingAction::getMessageFromErrorBundle::END------------>:::::::");
		return msg;
	}

	/**
	 * Gets the error bundle.
	 * 
	 * @param request
	 *            the request
	 * @return the error bundle
	 */
	private MessageResources getErrorBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}

	/**
	 * Gets the emotional bond rate details.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the emotional bond rate details
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void calcRateForEmotionalBondBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BookingAction::calcCNRateForCash::START------------>:::::::");
		CNPricingDetailsTO cnRateDtls = new CNPricingDetailsTO();
		String cnRateDtlsJSON = "";
		PrintWriter out = null;
		String errorMsg = "";
		ConsignmentTO rateInputs = null;
		ConsignmentRateCalculationOutputTO rateOutput = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		HttpSession session = null;
		try {
			session = (HttpSession) request.getSession(false);
			LOGGER.debug("BookingAction :: calculateRateForConsignment() :: Rate Calculation..START..:"
					+ System.currentTimeMillis());
			out = response.getWriter();

			rateCompnents = (Map<String, ConsignmentRateCalculationOutputTO>) session
					.getAttribute(BookingConstants.CONSG_RATE_DTLS);
			if (rateCompnents == null || rateCompnents.isEmpty()) {
				rateCompnents = new HashMap<>();
				session.setAttribute(BookingConstants.CONSG_RATE_DTLS,
						rateCompnents);
			}
			String prefName = request.getParameter("currentPrefName");
			String prefStatus = request.getParameter("currentPrefStatus");

			if (prefStatus != null && prefStatus.equalsIgnoreCase("false")) {
				rateCompnents.remove(prefName);
			} else {
				rateInputs = prepareRateInputsEmotionalBond(request);
				rateOutput = bookingCommonService
						.calcRateForConsingment(rateInputs);
				rateCompnents.put(prefName, rateOutput);
			}

			LOGGER.debug("BookingAction :: calculateRateForConsignment() :: Rate Calculation..END..:"
					+ System.currentTimeMillis());
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			errorMsg = getSystemExceptionMessage(request, e);
			cnRateDtls.setMessage(errorMsg);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			errorMsg = getBusinessErrorFromWrapper(request, e);
			cnRateDtls.setMessage(errorMsg);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BookingAction :: calculateRateForConsignment() ::",
					e);
			errorMsg = getGenericExceptionMessage(request, e);
			// cnRateDtls = new CNPricingDetailsTO();
			cnRateDtls.setMessage(errorMsg);
		} finally {
			prepareEBRateOutput(cnRateDtls, rateCompnents);
			serializer = CGJasonConverter.getJsonObject();
			cnRateDtlsJSON = serializer.toJSON(cnRateDtls).toString();

			out.print(cnRateDtlsJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BookingAction::calcCNRate::END------------>:::::::");
	}

	private void prepareEBRateOutput(CNPricingDetailsTO output,
			Map<String, ConsignmentRateCalculationOutputTO> rateCompnents) {
		LOGGER.debug("EmotionalBondBookingAction::prepareEBRateOutput::START------------>:::::::");
		double finalPrice = 0.0;
		for (ConsignmentRateCalculationOutputTO comp : rateCompnents.values()) {
			finalPrice += comp.getGrandTotalIncludingTax();
		}
		output.setFinalPrice(finalPrice);
		LOGGER.debug("EmotionalBondBookingAction::prepareEBRateOutput::END------------>:::::::");
	}

	private ConsignmentTO prepareRateInputsEmotionalBond(
			HttpServletRequest request) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BookingAction::prepareRateInputsEmotionalBond::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		emotionalBondBookingService = (EmotionalBondBookingService) getBean(SpringConstants.EMOTIONAL_BOND_BOOKING_SERVICE);
		ConsignmentTO inputTO = new ConsignmentTO();
		String productCode = request.getParameter("productCode");
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
		inputTO.setBookingDate(DateUtil.getCurrentDate());
		cnPricing.setRateType(request.getParameter("rateType"));
		// String preferencesIds = request.getParameter("preferenceIds");
		String prefName = request.getParameter("currentPrefName");
		/*
		 * List<Integer> preferenceIdList = (List<Integer>) StringUtil
		 * .parseIntegerList(prefName, CommonConstants.COMMA);
		 */
		List<Integer> prefId = new ArrayList<Integer>();
		prefId.add(Integer.parseInt(prefName));
		List<String> preferenceIdCodes = emotionalBondBookingService
				.getBookingPrefCodes(prefId);

		cnPricing.setEbPreferencesCodes(preferenceIdCodes.get(0));
		inputTO.setConsgPriceDtls(cnPricing);
		LOGGER.debug("BookingAction::prepareRateInputs::END------------>:::::::");
		return inputTO;
	}
}
