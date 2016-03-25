package com.ff.universe.booking.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.booking.FocBookingEmailTO;
import com.ff.booking.FocBookingTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.organization.EmployeeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.PrivilegeCardTransactionTO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.booking.dao.BookingUniversalDAO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;

public class BookingUniversalServiceImpl implements BookingUniversalService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingUniversalServiceImpl.class);
	private BookingUniversalDAO bookingUniversalDAO;
	private ConsignmentCommonService consignmentCommonService;
	private GeographyCommonService geographyCommonService;
	private OrganizationCommonService organizationCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private ManifestUniversalService manifestUniversalService;
	private StockUniversalService stockUniversalService;
	private EmailSenderUtil emailSenderUtil;

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * @return the stockUniversalService
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * @param stockUniversalService
	 *            the stockUniversalService to set
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	public BookingUniversalDAO getBookingUniversalDAO() {
		return bookingUniversalDAO;
	}

	public void setBookingUniversalDAO(BookingUniversalDAO bookingUniversalDAO) {
		this.bookingUniversalDAO = bookingUniversalDAO;
	}

	public ConsignmentCommonService getConsignmentCommonService() {
		return consignmentCommonService;
	}

	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public void setManifestUniversalService(
			ManifestUniversalService manifestUniversalService) {
		this.manifestUniversalService = manifestUniversalService;
	}

	@Override
	public List<ConsignmentModificationTO> getBookings(
			List<String> consgNumbers, String consgType)
			throws CGSystemException, CGBusinessException {
		List<BookingDO> bookings = new ArrayList<BookingDO>();
		List<ConsignmentModificationTO> bookingTOs = null;
		try {
			bookings = bookingUniversalDAO.getBookings(consgNumbers, consgType);
			if (!StringUtil.isEmptyList(bookings)) {
				bookingTOs = bookingTrasnferObjConverter(bookings);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BookingUniversalServiceImpl :: getBookings()..:",
					e);
			throw new CGBusinessException(UniversalErrorConstants.ERROR);
		}
		return bookingTOs;
	}

	public BookingValidationTO isConsignmentsBooked(
			BookingValidationTO bookingValidationInputs)
			throws CGBusinessException {
		return bookingUniversalDAO
				.isConsignmentsBooked(bookingValidationInputs);
	}

	private List<ConsignmentModificationTO> bookingTrasnferObjConverter(
			List<BookingDO> bookings) throws CGBusinessException,
			CGSystemException {
		List<ConsignmentModificationTO> bookingTOs = null;

		if (!StringUtil.isEmptyColletion(bookings)) {

			bookingTOs = new ArrayList<ConsignmentModificationTO>(
					bookings.size());
		}
		for (BookingDO booking : bookings) {
			ConsignmentModificationTO bookingTO = null;
			if (StringUtils.equalsIgnoreCase(CommonConstants.PROCESS_PICKUP,
					booking.getUpdatedProcess().getProcessCode())) {
				bookingTO = pickupBookingTransObjConverter(booking);
				bookingTO.setUpdatedProcessFrom(booking.getUpdatedProcess().getProcessId());
			} else {
				bookingTO = bookingTransObjConverter(booking);
				bookingTO.setUpdatedProcessFrom(booking.getUpdatedProcess().getProcessId());
			}

			bookingTOs.add(bookingTO);
		}
		return bookingTOs;
	}

	private ConsignmentModificationTO bookingTransObjConverter(BookingDO booking)
			throws CGBusinessException, CGSystemException {
		ConsignmentModificationTO bookingTO = new ConsignmentModificationTO();
		// Getting consignment
		ConsignmentTO consg = consignmentCommonService
				.getConsingmentDtls(booking.getConsgNumber());
		if (!StringUtil.isNull(consg)) {
			bookingTO.setConsigmentTO(consg);
			bookingTO.setCnPricingDtls(consg.getConsgPriceDtls());
			bookingTO.setBookingId(booking.getBookingId());
			/*
			 * String bookingTime = DateUtil.extractTimeFromDate(booking
			 * .getBookingDate());
			 */
			String bookingDate = DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(booking.getBookingDate());
			bookingTO.setBookingDate(bookingDate);
			bookingTO.setConsgNumber(booking.getConsgNumber());
			bookingTO.setBookingOfficeId(booking.getBookingOfficeId());
			bookingTO.setBookingType(booking.getBookingType().getBookingType());
			bookingTO.setNoOfPieces(bookingTO.getNoOfPieces());
			// for print
			bookingTO.setNoOfPieces(booking.getNoOfPieces());
			bookingTO.setDeclaredValue(booking.getDeclaredValue());

			bookingTO.setRefNo(booking.getRefNo());
			if (consg.getActualWeight() != null && consg.getActualWeight() > 0)
				bookingTO.setActualWeight(consg.getActualWeight());
			if (consg.getFinalWeight() != null && consg.getFinalWeight() > 0)
				bookingTO.setFinalWeight(consg.getFinalWeight());
			if (consg.getVolWeight() != null && consg.getVolWeight() > 0)
				bookingTO.setVolWeight(consg.getVolWeight());
			bookingTO.setConsgTypeId(booking.getConsgTypeId()
					.getConsignmentId());
			bookingTO.setConsgTypeName(booking.getConsgTypeId()
					.getConsignmentName());
			bookingTO.setConsgTypeCode(booking.getConsgTypeId()
					.getConsignmentCode());
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, booking
							.getConsgTypeId().getConsignmentName())) {
				bookingTO.setActualWeight(booking.getFianlWeight());
			}
			bookingTO.setPrice(consg.getPrice());
			if (consg.getHeight() != null && consg.getHeight() > 0)
				bookingTO.setHeight(consg.getHeight());
			if (consg.getLength() != null && consg.getLength() > 0)
				bookingTO.setLength(consg.getLength());
			if (consg.getBreath() != null && consg.getBreath() > 0)
				bookingTO.setBreath(consg.getBreath());
			bookingTO.setCnStatus(booking.getCnStatus());
			if (booking.getTrnaspmentChg() != null
					&& booking.getTrnaspmentChg() > 0)
				bookingTO.setTrnaspmentChg(booking.getTrnaspmentChg());

			bookingTO.setPincodeId(consg.getDestPincode().getPincodeId());
			bookingTO.setPincode(consg.getDestPincode().getPincode());
			CityTO city = geographyCommonService.getCity(consg.getDestPincode()
					.getPincode());
			if (city != null) {
				bookingTO.setCityId(city.getCityId());
				bookingTO.setCityName(city.getCityName());
			}

			CityTO loginCity = geographyCommonService.getCityByOfficeId(booking
					.getBookingOfficeId());
			if (loginCity != null) {
				bookingTO.setBookingCityId(loginCity.getCityId());
			}
			if (!StringUtil.isEmptyInteger(booking.getApprovedBy())) {
				EmployeeTO employee = organizationCommonService
						.getEmployeeDetails(booking.getApprovedBy());
				bookingTO.setApprovedById(booking.getApprovedBy());
				bookingTO.setApprovedBy(employee.getFirstName());
			}
			bookingTO.setWeightCapturedMode(booking.getWeightCapturedMode());
			bookingTO.setOtherCNContent(consg.getOtherCNContent());
			bookingTO.setPaperWorkRefNo(consg.getPaperWorkRefNo());
			bookingTO.setInsurencePolicyNo(consg.getInsurencePolicyNo());
			// Setting child entities
			if (!StringUtil.isNull(consg.getCnContents())) {
				bookingTO.setCnContents(consg.getCnContents());
				bookingTO.getCnContents().setOtherContent(
						consg.getCnContents().getOtherContent());
			}
			if (!StringUtil.isNull(consg.getCnPaperWorks())) {
				bookingTO.setCnPaperWorks(consg.getCnPaperWorks());
				bookingTO.setPaperWorkRefNo(booking.getPaperWorkRefNo());
			}
			if (!StringUtil.isNull(consg.getConsigneeTO())) {
				bookingTO.setConsignee(consg.getConsigneeTO());
			}
			if (!StringUtil.isNull(consg.getConsignorTO())) {
				bookingTO.setConsignor(consg.getConsignorTO());
			}

			// Setting insuredBy
			if (!StringUtil.isNull(consg.getInsuredByTO())) {
				bookingTO.setInsuredBy(consg.getInsuredByTO());
			}
		}

		if (!StringUtil.isNull(booking.getBookingPayment())) {
			BookingPaymentTO bookingPayment = new BookingPaymentTO();
			CGObjectConverter.createToFromDomain(booking.getBookingPayment(),
					bookingPayment);

			Integer paymentID = booking.getBookingPayment().getPaymentModeId();
			PaymentModeTO paymentModeTO = serviceOfferingCommonService
					.getPaymentDetails(paymentID);
			bookingTO.setPaymentMode(paymentModeTO.getPaymentCode());

			if (!StringUtil.isNull(booking.getBookingPayment().getChqDate())) {
				bookingPayment.setChqDateStr(DateUtil
						.getDDMMYYYYDateToString(booking.getBookingPayment()
								.getChqDate()));

			}

			if (!StringUtil.isNull(booking.getBookingPayment().getChqNo())) {
				bookingPayment.setChqNo(booking.getBookingPayment().getChqNo());

			}
			if (!StringUtil.isNull(booking.getBookingPayment()
					.getBankBranchName())) {
				bookingPayment.setBankBranchName(booking.getBookingPayment()
						.getBankBranchName());

			}
			if (!StringUtil.isNull(booking.getBookingPayment().getBankName())) {
				bookingPayment.setBankName(booking.getBookingPayment()
						.getBankName());
			}

			if (!StringUtil.isNull(paymentModeTO)
					&& paymentModeTO.getPaymentCode().equalsIgnoreCase("PVC")) {
				String consgNo = booking.getConsgNumber();
				PrivilegeCardTransactionTO cardTransactionTO = serviceOfferingCommonService
						.getprivilegeCardDtls(consgNo);
				if (!StringUtil.isNull(cardTransactionTO)) {
					bookingPayment.setPrivilegeCardAmt(cardTransactionTO
							.getAmount());
					bookingPayment.setPrivilegeCardNo(cardTransactionTO
							.getPrivilegeCardTO().getPrivilegeCardNo());
					bookingPayment.setPrivilegeCardId(cardTransactionTO
							.getPrivilegeCardTO().getPrivilegeCardId());
					bookingPayment.setPrivilegeCardTransId(cardTransactionTO
							.getPrivilegeCardTransactionId());

				}

			}
			bookingTO.setBookingPayment(bookingPayment);
		}
		// Setting delevery map Id
		if (!StringUtil.isEmptyInteger(booking.getPincodeDlvTimeMapId())) {
			PincodeProductServiceabilityTO pincodeProdServiceability = geographyCommonService
					.getPincodeProdServiceabilityDtls(booking
							.getPincodeDlvTimeMapId());
			bookingTO.setPincodeProdServiceability(pincodeProdServiceability);
			bookingTO.setDlvTimeMapId(pincodeProdServiceability
					.getPincodeDeliveryTimeMapId());
		}
		if (!StringUtil.isNull(booking.getCustomerId())) {
			bookingTO.setCustomerId(booking.getCustomerId().getCustomerId());
		}

		// setting consignment status
		/*
		 * boolean isConsgClosed = manifestUniversalService.isConsignmentClosed(
		 * booking.getConsgNumber(),
		 * ManifestUniversalConstants.MANIFEST_DIRECTION_OUT,
		 * ManifestUniversalConstants.MANIFEST_STATUS_CLOSED); if
		 * (isConsgClosed) bookingTO.setIsConsgClosed("Y");
		 */
		return bookingTO;
	}

	private ConsignmentModificationTO pickupBookingTransObjConverter(
			BookingDO booking) throws CGBusinessException, CGSystemException {
		ConsignmentModificationTO bookingTO = new ConsignmentModificationTO();
		bookingTO.setBookingId(booking.getBookingId());
		bookingTO.setBookingDate(DateUtil.getDDMMYYYYDateToString(booking
				.getBookingDate()));
		bookingTO.setConsgNumber(booking.getConsgNumber());
		bookingTO.setBookingOfficeId(booking.getBookingOfficeId());
		bookingTO.setBookingType(booking.getBookingType().getBookingType());
		bookingTO.setBookingTypeId(booking.getBookingType().getBookingTypeId());
		bookingTO.setRunsheetNo(booking.getPickRunsheetNo());
		// added for print
		bookingTO.setDeclaredValue(booking.getDeclaredValue());
		bookingTO.setNoOfPieces(booking.getNoOfPieces());

		if (!StringUtil.isNull(booking.getCustomerId())) {
			CustomerTO customer = new CustomerTO();
			CGObjectConverter.createToFromDomain(booking.getCustomerId(),
					customer);
			bookingTO.setCustomer(customer);
		}

		if (!StringUtil.isNull(booking.getPincodeId())) {
			bookingTO.setPincodeId(booking.getPincodeId().getPincodeId());
			bookingTO.setPincode(booking.getPincodeId().getPincode());
			CityTO city = geographyCommonService.getCity(booking.getPincodeId()
					.getPincode());
			if (city != null) {
				bookingTO.setCityId(city.getCityId());
				bookingTO.setCityName(city.getCityName());
			}
		}

		if (!StringUtil.isNull(booking.getConsigneeId())) {
			ConsignorConsigneeTO consignee = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(booking.getConsigneeId(),
					consignee);
			bookingTO.setConsignee(consignee);
		}
		if (!StringUtil.isNull(booking.getConsignorId())) {
			ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(booking.getConsignorId(),
					consignor);
			bookingTO.setConsignor(consignor);
		}

		return bookingTO;
	}

	@Override
	public Boolean isAtleastOneConsignmentBooked(List<String> consgList)
			throws CGBusinessException {
		return bookingUniversalDAO.isAtleastOneConsignmentBooked(consgList);
	}

	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGBusinessException, CGSystemException {
		return manifestUniversalService.isConsignmentClosed(consgNumber,
				manifestDirection, manifestStatus);
	}

	@Override
	public String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		return bookingUniversalDAO.getBookingTypeByConsgNumber(consignmentTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BookingCommonService#getBookingType(java.lang
	 * .String)
	 */
	public BookingTypeTO getBookingType(String bookingType)
			throws CGSystemException, CGBusinessException {
		BookingTypeTO bookingTypeTO = new BookingTypeTO();
		BookingTypeDO bookingTypeDO = bookingUniversalDAO
				.getBookingType(bookingType);
		if (!StringUtil.isNull((bookingTypeDO)))
			bookingTypeTO = (BookingTypeTO) CGObjectConverter
					.createToFromDomain(bookingTypeDO, bookingTypeTO);
		return bookingTypeTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.EmotionalBondBookingService#getBookingPrefDetails
	 * ()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGSystemException, CGBusinessException {
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		List<BookingPreferenceDetailsTO> bookingPrefTOs = null;
		bookingPrefDOs = bookingUniversalDAO.getBookingPrefDetails();
		if (!StringUtil.isEmptyColletion(bookingPrefDOs)) {
			bookingPrefTOs = new ArrayList<>();
			bookingPrefTOs = (List<BookingPreferenceDetailsTO>) CGObjectConverter
					.createTOListFromDomainList(bookingPrefDOs,
							BookingPreferenceDetailsTO.class);
		}
		return bookingPrefTOs;
	}

	@Override
	public List<CustomerTO> getContractCustomerList(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService
				.getContractCustomerListForStock(customerTO);
	}

	@Override
	public void sendEmailForFocBooking() throws CGSystemException,
			CGBusinessException ,HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("BookingUniversalServiceImpl::sendEmailForFocBooking::START------------>:::::::");
		try {
			getDataForEmailTemplate();
		} catch (Exception exception) {
			LOGGER.error(
					"Error occured in::BookingUniversalServiceImpl::sendEmailForFocBooking::",
					exception);
		}
		LOGGER.debug("BookingUniversalServiceImpl::sendEmailForFocBooking::END-------------->:::::::");
	}

	/**
	 * The getDataForEmailTemplate() will get the data for template.
	 * 
	 * @return the focBookingEmailTO will return the data, to use it in
	 *         template.
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private List<FocBookingEmailTO> getDataForEmailTemplate() {
		LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::START------------>:::::::");
		FocBookingEmailTO focBookingEmailTO = null;
		try {
			List<BookingDO> focBookingsByDateList = null;
			EmployeeTO employeeTO = new EmployeeTO();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			String previousDate = getDateBeforeSevenDays();

			focBookingsByDateList = bookingUniversalDAO
					.getFocBookingDetailsByDate(currentDate, previousDate);

			if (!CGCollectionUtils.isEmpty(focBookingsByDateList)) {

				ListIterator<BookingDO> focBookingDOs = focBookingsByDateList
						.listIterator();

				while (focBookingDOs.hasNext()) {
					BookingDO bookingDO = focBookingDOs.next();
					List<BookingTO> bookingTOs = new ArrayList<BookingTO>();

					// Getting Approver details
					LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::Getting Approver Details::START------------>:::::::");
					employeeTO = organizationCommonService
							.getEmployeeDetails(bookingDO.getApprovedBy());
					LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::Getting Approver Details::END------------>:::::::");

					//Setting approver's properties like Employee ID from Approver ID, FirstName SPACE LastName, EmailID
					LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::Setting approver's properties like Employee ID from Approver ID, FirstName SPACE LastName, EmailID------------>:::::::");
					focBookingEmailTO = new FocBookingEmailTO();
					focBookingEmailTO.setEmployeeId(bookingDO.getApprovedBy()
							.toString());
					focBookingEmailTO.setEmployeName(employeeTO.getFirstName()
							+ CommonConstants.SPACE + employeeTO.getLastName());
					focBookingEmailTO.setEmailTo(new String[] { employeeTO
							.getEmailId() });

					FocBookingTO focBookingTO = new FocBookingTO();
					focBookingTO = convertProperties(bookingDO, focBookingTO);
					bookingTOs.add(focBookingTO);

					while (focBookingDOs.hasNext()) {
						BookingDO bookingDO2 = focBookingDOs.next();
						if (bookingDO.getApprovedBy().equals(
								bookingDO2.getApprovedBy())) {
							FocBookingTO bookingTO2 = new FocBookingTO();
							bookingTO2 = convertProperties(bookingDO2,
									bookingTO2);
							bookingTOs.add(bookingTO2);
						} else {
							focBookingDOs.previous();
							break;
						}
					} // End of second while

					focBookingEmailTO.setBookingTOs(bookingTOs);

					// Sending Email
					sendEmailToApprovers(focBookingEmailTO);
				}// END of first while
			}

		} catch (CGBusinessException | CGSystemException exception) {
			LOGGER.error(
					"Error ocurred in::BookingUniversalServiceImpl::getDataForEmailTemplate()...::::::",
					exception);
			return null;
		}
		LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::END-------------->:::::::");
		return null;
	}

	private FocBookingTO convertProperties(BookingDO bookingDO,
			FocBookingTO focBookingTO) {

		focBookingTO.setConsgNumber(bookingDO.getConsgNumber());
		focBookingTO.setBookingDate(bookingDO.getBookingDate().toString());
		focBookingTO.setOriginCity(bookingDO.getOrgCityName());
		focBookingTO.setPrice(bookingDO.getPrice());
		return focBookingTO;
	}

	/**
	 * 
	 * @param focBookingEmailTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void sendEmailToApprovers(FocBookingEmailTO focBookingEmailTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingUniversalServiceImpl::sendEmailToApprovers::START------------>:::::::");
		MailSenderTO mailSenderTO = new MailSenderTO();

		String emailFrom = stockUniversalService
				.getConfigParamValueByName(CommonConstants.FFCL_FROM_EMAIL_ID);
		String[] emailTo = focBookingEmailTO.getEmailTo();

		// Setting template variables
		Map<Object, Object> templateVariables = new HashMap<Object, Object>();
		templateVariables.put(UniversalBookingConstants.FOC_BOOKING_EMAIL_TO,
				focBookingEmailTO);

		mailSenderTO.setFrom(emailFrom);
		mailSenderTO.setTo(emailTo);
		mailSenderTO
				.setMailSubject(UniversalBookingConstants.GET_FOC_BOOKING_EMAIL_SUBJECT);
		mailSenderTO
				.setTemplateName(UniversalBookingConstants.GET_FOC_BOOKING_EMAIL_TEMPLATE_NAME);
		mailSenderTO.setTemplateVariables(templateVariables);

		emailSenderUtil.sendEmail(mailSenderTO);
		LOGGER.debug("BookingUniversalServiceImpl::getDataForEmailTemplate::Method for Email called");
		LOGGER.debug("BookingUniversalServiceImpl::sendEmailToApprovers::END-------------->:::::::");
	}

	/**
	 * This Method getDateBeforeSevenDays() returns the date in String prior to
	 * seven days from current date. Example: today's date is 26 January 2014,
	 * method will return the date 19 January 2014
	 * 
	 * @return
	 */
	private String getDateBeforeSevenDays() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		return dateFormat.format(calendar.getTime());
	}
}
