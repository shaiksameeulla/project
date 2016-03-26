package com.ff.web.booking.action;

import java.io.PrintWriter;
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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.converter.CreditCustomerBookingConverter;
import com.ff.web.booking.form.CreditCustomerBookingDoxForm;
import com.ff.web.booking.form.CreditCustomerBookingParcelForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.CreditCustomerBookingService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;

/**
 * The Class CreditCustomerBookingAction.
 */
public class CreditCustomerBookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingAction.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The credit customer booking service. */
	private CreditCustomerBookingService creditCustomerBookingService = null;

	/**
	 * View credit customer booking dox.
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
	public ActionForward viewCreditCustomerBookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("CreditCustomerBookingAction::viewCreditCustomerBookingDox::START------------>:::::::");
		OfficeTO loggedInOffice = null;
		ActionMessage actionMessage = null;
		try {
			loggedInOffice = getLoggedInOffice(request);
			request.setAttribute("originOfficeId", loggedInOffice.getOfficeId());
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			request.setAttribute("originCity", city.getCityCode());
			setDefaultValues(request, response);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception Occured in::CreditCustomerBookingAction::viewCreditCustomerBookingDox() :: ",
					e);
			actionMessage = new ActionMessage(e.getMessage(), "City");
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CreditCustomerBookingAction::viewCreditCustomerBookingDox() :: ",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CreditCustomerBookingAction::viewCreditCustomerBookingDox::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_VIEW_CREDIT_CUSTOMER_BOOKING_DOX);

	}

	/**
	 * View credit customer booking parcel.
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
	public ActionForward viewCreditCustomerBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("CreditCustomerBookingAction::viewCreditCustomerBookingParcel::START------------>:::::::");
		OfficeTO loggedInOffice = null;
		ActionMessage actionMessage = null;
		try {
			String docType = request.getParameter("docType");
			loggedInOffice = getLoggedInOffice(request);
			setContentValues(request, response);
			request.setAttribute("originOfficeId", loggedInOffice.getOfficeId());
			request.setAttribute("docType", docType);
			setDefaultValues(request, response);
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			request.setAttribute("originCity", city.getCityCode());
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception Occured in::CreditCustomerBookingAction::viewCreditCustomerBookingParcel() :: ",
					e);
			actionMessage = new ActionMessage(e.getMessage(), "City");
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CreditCustomerBookingAction::viewCreditCustomerBookingParcel() :: ",
					e);
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CreditCustomerBookingAction::viewCreditCustomerBookingParcel::END------------>:::::::");
		return mapping
				.findForward(BookingConstants.URL_VIEW_CREDIT_CUSTOMER_BOOKING_PARCEL);

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
		LOGGER.debug("CreditCustomerBookingAction::setContentValues::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<CNContentTO> cnContentTOs = bookingCommonService
				.getContentValues();
		request.setAttribute("contentVal", cnContentTOs);
		LOGGER.debug("CreditCustomerBookingAction::setContentValues::END------------>:::::::");
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
	 *             the cG business exception
	 */
	private void setDefaultValues(HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CreditCustomerBookingAction::setDefaultValues::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();
		List<InsuredByTO> insuredDtls = bookingCommonService.getInsuredByDtls();
		request.setAttribute("insurance", insuredDtls);
		request.setAttribute("consgTypes", consgTypes);
		request.setAttribute("bookingType", BookingConstants.CCC_BOOKING);
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
		request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
		LOGGER.debug("CreditCustomerBookingAction::setDefaultValues::END------------>:::::::");
	}

	/**
	 * Save or update credit cust booking parcel.
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
	public void saveOrUpdateCreditCustBookingParcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingParcel::START------------>:::::::");
		CreditCustomerBookingParcelTO creditCustBookingParcelTO = null;
		CreditCustomerBookingParcelForm creditCustBookingParcelForm = null;
		String transMag = "";
		PrintWriter out = null;
		//List<CreditCustomerBookingParcelTO> bookingDoxTOs = null;
		OfficeTO loggedInOffice = null;
		BookingWrapperTO bookingWrapperTO = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		boolean isWeighingMachineConnected = Boolean.parseBoolean(request.getParameter(UniversalBookingConstants.REQUEST_PARAM_IS_WEIGHING_MACHINE_CONNECTED));
		try {
			out = response.getWriter();
			creditCustBookingParcelForm = (CreditCustomerBookingParcelForm) form;
			creditCustBookingParcelTO = (CreditCustomerBookingParcelTO) creditCustBookingParcelForm.getTo();
			if (creditCustBookingParcelTO != null) {
				// Setting the weight captured mode
				if (isWeighingMachineConnected) {
					for (int i=0; i < creditCustBookingParcelTO.getWeightCapturedModes().length; i++) {
						creditCustBookingParcelTO.getWeightCapturedModes()[i] = CommonConstants.RECORD_STATUS_ACTIVE;
					}
				}
				
				// Preparing List of TO's from UI
				loggedInOffice = getLoggedInOffice(request);
				bookingWrapperTO = setUpCreditCustBookingParcelTos(creditCustBookingParcelTO, loggedInOffice);
				// Getting Rate components from Session
				if(!CGCollectionUtils.isEmpty(bookingWrapperTO.getSucessConsignments())) {
				rateCompnents = getConsgRateDetails(request);
				if (!CGCollectionUtils.isEmpty(rateCompnents)){
					bookingWrapperTO.setConsgRateDetails(rateCompnents);
				}
				else {
					LOGGER.error("Error occured in CreditCustomerBookingAction :: saveOrUpdateCreditCustBookingParcel() ::RateComponent doen not exist(throwing business exception) ");
					throw new CGBusinessException(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				}
				creditCustomerBookingService = (CreditCustomerBookingService) getBean(SpringConstants.CC_BOOKING_SERVICE);
				BookingResultTO result = creditCustomerBookingService.createBookingAndConsigmentsParcel(bookingWrapperTO);
				
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(), result.getSuccessCNsIds());
				}else{
					LOGGER.debug("Error occured in CreditCustomerBookingAction :: saveOrUpdateCreditCustBookingParcel() ::Prepared success consg list is empty");
					throw new Exception("Prepared success consg list is empty");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in CreditCustomerBookingAction :: saveOrUpdateCreditCustBookingParcel() ::"
					,e);
			transMag = CommonConstants.FAILURE + "#" + CommonConstants.NO;
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingParcel::END------------>:::::::");
	}

	/**
	 * Save or update credit cust booking dox.
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
	public void saveOrUpdateCreditCustBookingDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::START------------>:::::::");
		CreditCustomerBookingDoxTO creditCustBookingDoxTO = null;
		CreditCustomerBookingDoxForm creditCustBookingDoxForm = null;
		String transMag = "";
		PrintWriter out = null;
	//	List<CreditCustomerBookingDoxTO> bookingDoxTOs = null;
		OfficeTO loggedInOffice = null;
		BookingWrapperTO bookingWrapperTO = null;
		Map<String, ConsignmentRateCalculationOutputTO> rateCompnents = null;
		boolean isWeighingMachineConnected = Boolean.parseBoolean(request.getParameter(UniversalBookingConstants.REQUEST_PARAM_IS_WEIGHING_MACHINE_CONNECTED));
		try {
			out = response.getWriter();
			creditCustBookingDoxForm = (CreditCustomerBookingDoxForm) form;
			creditCustBookingDoxTO = (CreditCustomerBookingDoxTO) creditCustBookingDoxForm.getTo();
			if (creditCustBookingDoxTO != null) {
				// Setting the weight captured mode flag
				if (isWeighingMachineConnected) {
					for (int i=0; i < creditCustBookingDoxTO.getWeightCapturedModes().length; i++) {
						creditCustBookingDoxTO.getWeightCapturedModes()[i] = CommonConstants.RECORD_STATUS_ACTIVE;
					}
				}
				
				// Preparing List of TO's from UI
				loggedInOffice = getLoggedInOffice(request);
				LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::calling setUpCreditCustBookingDoxTos() to prepare input TO");
				bookingWrapperTO = setUpCreditCustBookingDoxTos(creditCustBookingDoxTO, loggedInOffice);
				LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::setUpCreditCustBookingDoxTos() is completed");
				// Getting Rate compoments from Session
				rateCompnents = getConsgRateDetails(request);
				if (!CGCollectionUtils.isEmpty(rateCompnents)){
					bookingWrapperTO.setConsgRateDetails(rateCompnents);
				}else{
					LOGGER.error("Error occured in CreditCustomerBookingAction :: saveOrUpdateCreditCustBookingDox() ::RateComponent doen not exist(throwing business exception) ");
					throw new CGBusinessException(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
				}
				creditCustomerBookingService = (CreditCustomerBookingService) getBean(SpringConstants.CC_BOOKING_SERVICE);
				LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::b4 calling save of service");
				BookingResultTO result = creditCustomerBookingService.createBookingAndConsigmentsDox(bookingWrapperTO);
				LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::call completed for save service");
				transMag = result.getTransMessage();
				process2WayWrite(result.getSuccessBookingsIds(), result.getSuccessCNsIds());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in CreditCustomerBookingAction :: saveOrUpdateCreditCustBookingDox() ::"
					,e);
			transMag = CommonConstants.FAILURE + "#" + CommonConstants.NO;
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		// Setting rate compoments null
		setNullConsgRateDtls(request);
		LOGGER.debug("CreditCustomerBookingAction::saveOrUpdateCreditCustBookingDox::END------------>:::::::");
	}

	/**
	 * Sets the up credit cust booking dox tos.
	 * 
	 * @param bookingDoxTO
	 *            the booking dox to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingWrapperTO setUpCreditCustBookingDoxTos(
			CreditCustomerBookingDoxTO bookingDoxTO, OfficeTO loggedInOffice)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("CreditCustomerBookingAction::setUpCreditCustBookingDoxTos::START------------>:::::::");
		BookingWrapperTO bookingWrapperTO = null;
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		LOGGER.debug("CreditCustomerBookingAction::setUpCreditCustBookingDoxTos:: setting basic informations like process, loged in office, etc");
		//BookingTypeTO bookingType = bookingCommonService.getBookingType(BookingConstants.CCC_BOOKING);
		BookingTypeTO bookingType = bookingCommonService
				.getBookingType(BookingConstants.CCC_BOOKING);
	
		bookingDoxTO.setBookingTypeId(bookingType.getBookingTypeId());
		bookingDoxTO.setBookingType(bookingType.getBookingType());
		
		ProcessTO process = getProcess();
		bookingDoxTO.setProcessTO(process);
		
		String office = loggedInOffice.getOfficeId() + "#"
				+ loggedInOffice.getOfficeName() + " "
				+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
		bookingDoxTO.setBookingOffCode(office);
		CityTO city = bookingCommonService.getCityByIdOrCode(
				loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
		bookingDoxTO.setOriginCity(city.getCityName());
		/*bookingDoxTO
				.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
		bookingWrapperTO = CreditCustomerBookingConverter
				.creditCustBookingDoxConverter(bookingDoxTO,
						bookingCommonService);
		LOGGER.debug("CreditCustomerBookingAction::setUpCreditCustBookingDoxTos::END------------>:::::::");
		return bookingWrapperTO;
	}

	/**
	 * Sets the up credit cust booking parcel tos.
	 * 
	 * @param bookingParcelTO
	 *            the booking parcel to
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private BookingWrapperTO setUpCreditCustBookingParcelTos(
			CreditCustomerBookingParcelTO bookingParcelTO,
			OfficeTO loggedInOffice) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CreditCustomerBookingAction::setUpCreditCustBookingParcelTos::START------------>:::::::");
		BookingWrapperTO bookingWrapperTO = null;
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		BookingTypeTO bookingType = bookingCommonService.getBookingType(BookingConstants.CCC_BOOKING);
		ProcessTO process = getProcess();
		bookingParcelTO.setProcessTO(process);
		//List<CreditCustomerBookingParcelTO> baBookingParcelTOs = null;
		bookingParcelTO.setBookingTypeId(bookingType.getBookingTypeId());
		bookingParcelTO.setBookingType(bookingType.getBookingType());
		String office = loggedInOffice.getOfficeId() + "#"
				+ loggedInOffice.getOfficeName() + " "
				+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
		bookingParcelTO.setBookingOffCode(office);
		CityTO city = bookingCommonService.getCityByIdOrCode(loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
		bookingParcelTO.setOriginCity(city.getCityName());
	/*	bookingParcelTO
				.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
		String[] cnrAddressDtls= BookingUtils.removeElements(bookingParcelTO.getCnrAddressDtls());
		String[] cneAddressDtls= BookingUtils.removeElements(bookingParcelTO.getCneAddressDtls());
		
		bookingParcelTO.setCnrAddressDtls(cnrAddressDtls);
		bookingParcelTO.setCneAddressDtls(cneAddressDtls);
		
		bookingWrapperTO = CreditCustomerBookingConverter.creditCustBookingParcelConverter(bookingParcelTO,	bookingCommonService);
		LOGGER.debug("CreditCustomerBookingAction::setUpCreditCustBookingParcelTos::END------------>:::::::");
		return bookingWrapperTO;
	}

}
