package com.ff.web.booking.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.booking.CashDiscountEmailTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.converter.CashBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.dao.CashBookingDAO;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.CashBookingValidator;

/**
 * The Class CashBookingServiceImpl.
 */
public class CashBookingServiceImpl implements CashBookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingServiceImpl.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

	/** The cash booking validator. */
	private CashBookingValidator cashBookingValidator;
	private CashBookingDAO cashBookingDAO;
	private EmailSenderUtil emailSenderUtil;
	private OrganizationCommonService organizationCommonService;
	private StockUniversalService stockUniversalService;

	// private ConsignmentCommonService consignmentCommonService;

	/**
	 * @param stockUniversalService
	 *            the stockUniversalService to set
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * Sets the booking common service.
	 * 
	 * @param bookingCommonService
	 *            the new booking common service
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/**
	 * Sets the booking common dao.
	 * 
	 * @param bookingCommonDAO
	 *            the new booking common dao
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/**
	 * Sets the cash booking validator.
	 * 
	 * @param cashBookingValidator
	 *            the new cash booking validator
	 */
	public void setCashBookingValidator(
			CashBookingValidator cashBookingValidator) {
		this.cashBookingValidator = cashBookingValidator;
	}

	/**
	 * @param cashBookingDAO
	 *            the cashBookingDAO to set
	 */
	public void setCashBookingDAO(CashBookingDAO cashBookingDAO) {
		this.cashBookingDAO = cashBookingDAO;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.CashBookingService#saveOrUpdateBookingDox(
	 * com.ff.booking.CashBookingDoxTO)
	 */
	@Override
	public BookingResultTO saveOrUpdateBookingDox(
			CashBookingDoxTO cashBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdateBookingDox::START------------>:::::::");
		BookingDO booking = null;
		BookingResultTO result = new BookingResultTO();
		try {
			BookingTypeDO cashBooking = bookingCommonDAO
					.getBookingType(BookingConstants.CASH_BOOKING);
			cashBookingTO.setBookingTypeId(cashBooking.getBookingTypeId());
			ProcessTO process = getProcess();
			cashBookingTO.setProcessTO(process);
			cashBookingTO.getCnPricingDtls().setRateType(
					RateCommonConstants.RATE_TYPE_CASH);
			booking = CashBookingConverter
					.cashBookingDoxDomainConverter(cashBookingTO);
			if (booking != null) {
				if (TwoWayWriteProcessCall.isTwoWayWriteEnabled()) {
					booking.setDtToCentral(CommonConstants.YES);
				} else {
					booking.setDtToCentral(CommonConstants.NO);
				}

				List<BookingDO> bookings = new ArrayList<BookingDO>(1);
				bookings.add(booking);
				List<Integer> successBookingIds = bookingCommonDAO
						.saveOrUpdateBooking(bookings);
				if (successBookingIds != null && !successBookingIds.isEmpty()) {
					// Saving Consignment and Pricing
					cashBookingTO.setActualWeight(cashBookingTO
							.getFinalWeight());
					List<Integer> successCNIds = bookingCommonService
							.saveConsignmentAndRateDox(cashBookingTO,
									consgRateDtls);
					// Saving Privilege Card Details
					saveOrUpdatePrivilegeCardDtls(
							cashBookingTO.getBookingPayment(),
							booking.getBookingType(), cashBookingTO
									.getCnPricingDtls().getFinalPrice(),
							cashBookingTO.getConsgNumber(),
							cashBookingTO.getCreatedBy());
					if (successCNIds != null && !successCNIds.isEmpty()) {
						result.setSuccessBookingsIds(successBookingIds);
						result.setSuccessCNsIds(successCNIds);
						result.setTransStatus(true);
						result.setTransMessage(CommonConstants.SUCCESS);
					}
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdateBookingDox::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdateBookingDox::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdateBookingDox::END------------>:::::::");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.CashBookingService#saveOrUpdateBookingParcel
	 * (com.ff.booking.CashBookingParcelTO)
	 */
	@Override
	public BookingResultTO saveOrUpdateBookingParcel(
			CashBookingParcelTO cashBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdateBookingParcel::START------------>:::::::");
		BookingDO booking = null;
		BookingResultTO result = new BookingResultTO();
		try {
			BookingTypeDO cashBooking = bookingCommonDAO
					.getBookingType(BookingConstants.CASH_BOOKING);
			cashBookingTO.setBookingTypeId(cashBooking.getBookingTypeId());
			ProcessTO process = getProcess();
			cashBookingTO.setProcessTO(process);
			cashBookingTO.getCnPricingDtls().setRateType(
					RateCommonConstants.RATE_TYPE_CASH);
			booking = CashBookingConverter
					.cashBookingParcelDomainConverter(cashBookingTO);
			if (booking != null) {
				if (TwoWayWriteProcessCall.isTwoWayWriteEnabled()) {
					booking.setDtToCentral(CommonConstants.YES);
				} else {
					booking.setDtToCentral(CommonConstants.NO);
				}
				List<BookingDO> bookings = new ArrayList<BookingDO>(1);
				bookings.add(booking);
				List<Integer> successBookingIds = bookingCommonDAO
						.saveOrUpdateBooking(bookings);
				if (successBookingIds != null && !successBookingIds.isEmpty()) {
					// Saving Consignment and Pricing
					List<Integer> successCNIds = bookingCommonService
							.saveConsignmentAndRateParcel(cashBookingTO,
									consgRateDtls);
					// Saving Privilege Card Details
					saveOrUpdatePrivilegeCardDtls(
							cashBookingTO.getBookingPayment(),
							booking.getBookingType(), cashBookingTO
									.getCnPricingDtls().getFinalPrice(),
							cashBookingTO.getConsgNumber(),
							cashBookingTO.getCreatedBy());

					if (successCNIds != null && !successCNIds.isEmpty()) {
						result.setSuccessBookingsIds(successBookingIds);
						result.setSuccessCNsIds(successCNIds);
						result.setTransStatus(true);
						result.setTransMessage(CommonConstants.SUCCESS);
					}
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdateBookingParcel::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdateBookingParcel::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdateBookingParcel::END------------>:::::::");
		return result;
	}

	/**
	 * Save or update privilege card dtls.
	 * 
	 * @param payment
	 *            the payment
	 * @param bookingType
	 *            the booking type
	 * @param createdBy
	 * @param createdBy
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private void saveOrUpdatePrivilegeCardDtls(BookingPaymentTO payment,
			BookingTypeDO bookingType, Double finalPrice, String consgNumber,
			Integer createdBy) throws CGSystemException, CGBusinessException {
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdatePrivilegeCardDtls::START------------>:::::::");
		String paymentMode = "";
		try {
			paymentMode = payment.getPaymentMode().split("#")[1];
			payment.setPrivilegeCardAmt(finalPrice);
			payment.setCreatedBy(createdBy);
			if (StringUtils.equalsIgnoreCase(
					BookingConstants.PAYMENT_MODE_PRIVILEGE_CARD, paymentMode)) {
				PrivilegeCardTransactionDO privgCardDO = BookingUtils
						.setUpPrivilegeCardDtls(payment, bookingType,
								consgNumber);
				if (privgCardDO != null) {
					bookingCommonDAO
							.saveOrUpdatePrivilegeCardTransDtls(privgCardDO);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdatePrivilegeCardDtls::"
					+ e.getMessage());
			throw new CGBusinessException(e.getMessage());
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::saveOrUpdatePrivilegeCardDtls::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashBookingServiceImpl::saveOrUpdatePrivilegeCardDtls::END------------>:::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.CashBookingService#validateCashDiscount(com
	 * .ff.booking.BookingValidationTO)
	 */
	public BookingValidationTO validateCashDiscount(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException {
		return cashBookingValidator.validateCashDiscount(bookingValidationTO);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.CashBookingService#validatePrivilegeCard(com
	 * .ff.booking.BookingValidationTO)
	 */
	public BookingValidationTO validatePrivilegeCard(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException {
		return cashBookingValidator.validatePrivilegeCard(bookingValidationTO);

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
	private ProcessTO getProcess() throws CGSystemException,
			CGBusinessException {
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		process = bookingCommonService.getProcess(process);
		return process;
	}
	
	public void sendCashDiscountEmail() throws CGSystemException,
			CGBusinessException,HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("CashBookingServiceImpl::sendCashDiscountEmail::START------------>:::::::");
		List<BookingDO> cashDiscountBookings = cashBookingDAO
				.getDiscountBookings();

		if (!StringUtil.isEmptyList(cashDiscountBookings)) {
			List<CashDiscountEmailTO> cashDiscountTOs = new ArrayList<CashDiscountEmailTO>(
					cashDiscountBookings.size());
			for (BookingDO booking : cashDiscountBookings) {
				CashDiscountEmailTO cashDiscountEmail = new CashDiscountEmailTO();
				EmployeeTO approvedBy = organizationCommonService
						.getEmployeeDetails(booking.getApprovedBy());
				cashDiscountEmail.setEmployeeCode(approvedBy.getEmpCode());
				cashDiscountEmail.setEmployeeName(approvedBy.getFirstName());
				cashDiscountEmail.setEmployeeId(booking.getApprovedBy());
				cashDiscountEmail.setConsgNumber(booking.getConsgNumber());
				/*
				 * CNPricingDetailsTO cnPricingDtls = consignmentCommonService
				 * .getConsgPrincingDtls(booking.getConsgNumber());
				 */
				// cashDiscountEmail.setDiscount(cnPricingDtls.getDiscount());
				cashDiscountEmail.setEmail(approvedBy.getEmailId());
				OfficeTO bookingOffice = organizationCommonService
						.getOfficeByIdOrCode(booking.getBookingOfficeId(),
								CommonConstants.EMPTY_STRING);
				cashDiscountEmail.setBranchName(bookingOffice.getOfficeName());
				cashDiscountTOs.add(cashDiscountEmail);
			}
			// Sending email to employee
			Map<Integer, List<CashDiscountEmailTO>> employeeIdWiseMap = new HashMap<Integer, List<CashDiscountEmailTO>>();
			for (CashDiscountEmailTO cashDoscount : cashDiscountTOs) {
				if (employeeIdWiseMap.keySet() == null) {
					List<CashDiscountEmailTO> empWiseList = new ArrayList<CashDiscountEmailTO>();
					empWiseList.add(cashDoscount);
					employeeIdWiseMap.put(cashDoscount.getEmployeeId(),
							empWiseList);
				} else {
					List<CashDiscountEmailTO> nextList = (List<CashDiscountEmailTO>) employeeIdWiseMap
							.get(cashDoscount.getEmployeeId());
					if (nextList != null) {
						nextList.add(cashDoscount);
					} else {
						List<CashDiscountEmailTO> empWiseList = new ArrayList<CashDiscountEmailTO>();
						empWiseList.add(cashDoscount);
						employeeIdWiseMap.put(cashDoscount.getEmployeeId(),
								empWiseList);
					}
				}
			}
			for (Iterator iter = employeeIdWiseMap.entrySet().iterator(); iter
					.hasNext();) {
				CashDiscountEmailTO employee = null;
				Map.Entry entry = (Map.Entry) iter.next();
				List<CashDiscountEmailTO> empWiseList = (List<CashDiscountEmailTO>) entry
						.getValue();
				if (empWiseList != null && !empWiseList.isEmpty()) {
					employee = empWiseList.get(0);
				}

				String message = emailMessage(employee, empWiseList);
				sendCDEmail(employee, message);

			}
		}
		LOGGER.debug("CashBookingServiceImpl::sendCashDiscountEmail::END------------>:::::::");
	}

	/**
	 * Method to get Cash discount email template.
	 */
	private String emailMessage(CashDiscountEmailTO employee,
			List<CashDiscountEmailTO> empWiseList) throws CGBusinessException {
		String messageBody = null;
		StringBuffer messageBodyBuff = new StringBuffer();
		try {
			messageBodyBuff.append("<html><head></head><body>");
			messageBodyBuff.append("<table width='60%'>");
			messageBodyBuff.append("Subject : Cash Booking Discount"
					+ " :</b><br/><br/>");
			messageBodyBuff
					.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">Hellow,<br/><br/>");
			messageBodyBuff.append("<tr><td colSpan='5'>");
			messageBodyBuff
					.append("Please find a list of consignments for which a discount was offered this week."
							+ " :</b><br/><br/>");
			messageBodyBuff.append("</td></tr>");
			messageBodyBuff.append("<tr>");
			messageBodyBuff.append("<td width='20%'>");
			messageBodyBuff.append("<b>Branch Name</b>");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("<td width='20%'>");
			messageBodyBuff.append("<b>Consignment No</b>");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("<td width='15%'>");
			messageBodyBuff.append("<b>Discount(%)</b>");
			messageBodyBuff.append("</td>");
			messageBodyBuff.append("</tr>");
			messageBodyBuff.append("<tr><td></br>");
			messageBodyBuff.append("</td></tr>");

			for (CashDiscountEmailTO cashDiscount : empWiseList) {
				messageBodyBuff.append("<tr>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(cashDiscount.getBranchName());
				messageBodyBuff.append("</td>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(cashDiscount.getConsgNumber());
				messageBodyBuff.append("</td>");
				messageBodyBuff.append("<td>");
				messageBodyBuff.append(cashDiscount.getDiscount());
				messageBodyBuff.append("</td>");
				messageBodyBuff.append("</tr>");
				// Updating into Booking Table and Calculating rate

			}
			messageBodyBuff.append("<tr><td></br>");
			messageBodyBuff.append("</td></tr>");
			messageBodyBuff.append("<tr><br><td>");
			messageBodyBuff.append("Thank you,<br/><br/>");
			messageBodyBuff.append("</td></tr>");
			messageBodyBuff.append("<tr><td>");

			messageBodyBuff
					.append("For any queries? Send email to info@FirstFlight.com");

			messageBodyBuff.append("</td></tr></font></table></body></html>");
			messageBody = messageBodyBuff.toString();
		} catch (Exception e) {
			LOGGER.debug("Method emailMessage() " + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("Email Report Body - emailMessage: " + messageBody);
		return messageBody;
	}

	private void sendCDEmail(CashDiscountEmailTO employee, String messageBody) {
		LOGGER.debug("CashBookingServiceImpl::sendCDEmail::START------------>:::::::");
		MailSenderTO mailSenderTO = new MailSenderTO();
		try {
			String emailFrom = stockUniversalService
					.getConfigParamValueByName(CommonConstants.FFCL_FROM_EMAIL_ID);
			String[] emailTo = { employee.getEmail() };

			// Setting template variables
			mailSenderTO.setFrom(emailFrom);
			mailSenderTO.setTo(emailTo);
			mailSenderTO
					.setMailSubject(UniversalBookingConstants.CASH_BOOKING_DISCOUNT_SUBJECT);
			mailSenderTO.setPlainMailBody(messageBody);

			emailSenderUtil.sendEmail(mailSenderTO);

		} catch (CGBusinessException | CGSystemException exception) {
			LOGGER.error("CashBookingServiceImpl::sendCDEmail::" + exception);
		}
		LOGGER.debug("CashBookingServiceImpl::sendCDEmail::Method for Email called");
		LOGGER.debug("CashBookingServiceImpl::sendCDEmail::END-------------->:::::::");
	}

}
