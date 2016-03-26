package com.ff.web.booking.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;

/**
 * The Class CreditCustomerBookingConverter.
 */
public class CreditCustomerBookingConverter extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BABookingConverter.class);

	/**
	 * Creadit cust booking dox domain converter.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<BookingDO> creaditCustBookingDoxDomainConverter(
			List<CreditCustomerBookingDoxTO> bookingTOs,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::START------------>:::::::");
		List<BookingDO> bookings = null;
		try {
			bookings = new ArrayList<BookingDO>(bookingTOs.size());
			for (CreditCustomerBookingDoxTO bookingTO : bookingTOs) {
				BookingDO booking = convert(bookingTO);
				CustomerDO custDO = new CustomerDO();
				custDO.setCustomerId(bookingTO.getCustomerId());
				booking.setCustomerId(custDO);
				booking.setPickRunsheetNo(bookingTO.getPickupRunsheetNo());
				booking.setRefNo(bookingTO.getRefNo());
				booking.setNoOfPieces(bookingTO.getNoOfPieces());
				// Settng Customer address
				if (StringUtil.isNull(bookingTO.getConsignor())) {
					CustomerTO customer = bookingCommonService
							.getCustomerByIdOrCode(bookingTO.getCustomerId(),
									CommonConstants.EMPTY_STRING);
					if (!StringUtil.isNull(customer)) {
						PickupDeliveryAddressTO address = customer.getAddress();
						if (!StringUtil.isNull(address)) {
							ConsigneeConsignorDO consignor = new ConsigneeConsignorDO();
							StringBuilder addressBuilder = new StringBuilder();
							addressBuilder.append(address.getAddress1());
							addressBuilder.append(",");
							addressBuilder.append(address.getAddress2());
							addressBuilder.append(",");
							addressBuilder.append(address.getAddress3());
							consignor.setAddress(addressBuilder.toString());
							consignor.setFirstName(customer.getBusinessName());
							consignor.setMobile(address.getMobile());
							consignor.setPhone(address.getPhone());
							booking.setConsignorId(consignor);
						}
					}
				}
				bookings.add(booking);
			}

		} catch (Exception e) {
			LOGGER.error(
					"Exception in CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter:: :",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::END------------>:::::::");
		return bookings;
	}

	/**
	 * Creadit cust booking parcel domain converter.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static List<BookingDO> creaditCustBookingParcelDomainConverter(
			List<CreditCustomerBookingParcelTO> bookingTOs,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter::START------------>:::::::");
		List<BookingDO> bookings = null;
		try {
			bookings = new ArrayList<BookingDO>(bookingTOs.size());
			for (CreditCustomerBookingParcelTO bookingTO : bookingTOs) {
				BookingDO booking = convert(bookingTO);
				CustomerDO custDO = new CustomerDO();
				custDO.setCustomerId(bookingTO.getCustomerId());
				booking.setCustomerId(custDO);
				booking.setRefNo(bookingTO.getRefNo());
				booking.setPickRunsheetNo(bookingTO.getPickupRunsheetNo());
				booking.setNoOfPieces(bookingTO.getNoOfPieces());
				// Setting Consignee & Consignor
				/*
				 * if (!StringUtil.isNull(bookingTO.getConsignee())) {
				 * ConsigneeConsignorDO consignee = BookingUtils
				 * .setUpConsigneeConsignorDetails(bookingTO .getConsignee());
				 * booking.setConsigneeId(consignee); } if
				 * (!StringUtil.isNull(bookingTO.getConsignor())) {
				 * ConsigneeConsignorDO consignor = BookingUtils
				 * .setUpConsigneeConsignorDetails(bookingTO .getConsignor());
				 * booking.setConsignorId(consignor); }
				 */
				// Setting vol weight details
				if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
					booking.setVolWeight(bookingTO.getLength());
				if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
					booking.setHeight(bookingTO.getHeight());
				if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
					booking.setBreath(bookingTO.getBreath());
				if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
					booking.setLength(bookingTO.getLength());
				if (bookingTO.getVolWeight() != null
						&& bookingTO.getVolWeight() > 0)
					booking.setVolWeight(bookingTO.getVolWeight());
				// Setting paperworks
				if (!StringUtil.isEmptyInteger(bookingTO.getCnPaperworkId())) {
					CNPaperWorksDO cnPaperWork = new CNPaperWorksDO();
					cnPaperWork.setCnPaperWorkId(bookingTO.getCnPaperworkId());
					booking.setCnPaperWorkId(cnPaperWork);
					booking.setPaperWorkRefNo(bookingTO.getPaperWorkRefNo());
				}
				// Setting Content
				if (!StringUtil.isEmptyInteger(bookingTO.getCnContentId())) {
					CNContentDO cnContent = new CNContentDO();
					cnContent.setCnContentId(bookingTO.getCnContentId());
					booking.setCnContentId(cnContent);
				} else {
					booking.setOtherCNContent(bookingTO.getOtherCNContent());
				}
				if (!StringUtil.isEmptyInteger(bookingTO.getDlvTimeMapId()))
					booking.setPincodeDlvTimeMapId(bookingTO.getDlvTimeMapId());
				// Insurence Dtls
				if (!StringUtil.isEmptyInteger(bookingTO.getInsuredById())) {
					InsuredByDO insuredBy = new InsuredByDO();
					insuredBy.setInsuredById(bookingTO.getInsuredById());
					booking.setInsuredBy(insuredBy);
					booking.setInsurencePolicyNo(bookingTO.getPolicyNo());
				}
				// Settng Customer address
				if (StringUtil.isNull(bookingTO.getConsignor())) {
					CustomerTO customer = bookingCommonService
							.getCustomerByIdOrCode(bookingTO.getCustomerId(),
									CommonConstants.EMPTY_STRING);
					if (!StringUtil.isNull(customer)) {
						PickupDeliveryAddressTO address = customer.getAddress();
						if (!StringUtil.isNull(address)) {
							ConsigneeConsignorDO consignor = new ConsigneeConsignorDO();
							StringBuilder addressBuilder = new StringBuilder();
							addressBuilder.append(address.getAddress1());
							addressBuilder.append(",");
							addressBuilder.append(address.getAddress2());
							addressBuilder.append(",");
							addressBuilder.append(address.getAddress3());
							consignor.setAddress(addressBuilder.toString());
							consignor.setFirstName(customer.getBusinessName());
							consignor.setMobile(address.getMobile());
							consignor.setPhone(address.getPhone());
							booking.setConsignorId(consignor);
						}
					}
				}

				bookings.add(booking);
			}

		} catch (Exception e) {
			LOGGER.error(
					"Exception in CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter:: :",
					e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter::END------------>:::::::");
		return bookings;
	}

	/**
	 * Creadit cust booking dox domain converter.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperDO creaditCustBookingDoxDomainConverter(
			BookingWrapperTO bookingWrapperTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::START------------>:::::::");
		List<BookingDO> bookings = null;
		List<ConsignmentDO> consignments = null;
		BookingWrapperDO bookingWrapperDO = new BookingWrapperDO();
		Set<String> failureConsignments = new HashSet<String>();
		try {
			if (!StringUtil.isEmptyColletion(bookingWrapperTO
					.getFailureConsignments())
					&& bookingWrapperTO.getFailureConsignments().size() > 0) {
				failureConsignments = bookingWrapperTO.getFailureConsignments();
				LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::failure CNs on preparing input TO------------>:::::::" + failureConsignments);
			}

			List<? extends BookingTO> successBookings = bookingWrapperTO
					.getSucessConsignments();

			bookings = new ArrayList<BookingDO>(successBookings.size());
			consignments = new ArrayList<ConsignmentDO>(successBookings.size());

			if (!StringUtil.isEmptyColletion(successBookings)) {
				LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::success size before business validation" + successBookings.size());
				LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::failure size before business validation" + failureConsignments.size());
				LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::DO conversion and business validation started");
				for (BookingTO bookingBaseTO : bookingWrapperTO.getSucessConsignments()) {
					// Start... For Bulk Saving - Roll back only failure CNs
					try {
						ConsignmentDO consingment = new ConsignmentDO();
						CreditCustomerBookingDoxTO bookingTO = (CreditCustomerBookingDoxTO) bookingBaseTO;
						BookingDO booking = convert(bookingTO);
						CustomerDO custDO = new CustomerDO();
						custDO.setCustomerId(bookingTO.getCustomerId());
						booking.setCustomerId(custDO);

						if (!StringUtil.isStringEmpty(bookingBaseTO
								.getCustomerCodeSingle()))
							booking.setShippedToCode(bookingBaseTO
									.getCustomerCodeSingle());

						booking.setPickRunsheetNo(bookingTO
								.getPickupRunsheetNo());
						booking.setRefNo(bookingTO.getRefNo());
						booking.setNoOfPieces(bookingTO.getNoOfPieces());
						// Settng Customer address
						if (StringUtil.isNull(bookingTO.getConsignor())) {
							CustomerTO customer = bookingCommonService
									.getCustomerByIdOrCode(
											bookingTO.getCustomerId(),
											CommonConstants.EMPTY_STRING);
							if (!StringUtil.isNull(customer)) {
								PickupDeliveryAddressTO address = customer
										.getAddress();
								if (!StringUtil.isNull(address)) {
									setupConsigneeAddress(booking, customer,
											address);
								}
							}
						}
						if(booking.getUpdatedProcess().getProcessCode().equalsIgnoreCase("UPPU")){
							LOGGER.error(
									"Throwing business Exception in CreditCustomerBookingConverter :: creaditCustBookingDoxDomainConverter() :: Updated process is set as of pickup."
											+ bookingBaseTO.getConsgNumber());
							throw new CGBusinessException();
						}
						if (StringUtil.isNull(bookingWrapperTO
								.getConsgRateDetails())
								|| StringUtil
										.isNull(bookingWrapperTO
												.getConsgRateDetails()
												.get(bookingBaseTO
														.getConsgNumber()))) {
							LOGGER.error(
									"Throwing business Exception in CreditCustomerBookingConverter :: creaditCustBookingDoxDomainConverter() :: Rate is empty for consg no."
											+ bookingBaseTO.getConsgNumber());
							throw new CGBusinessException(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
						}

						// Preparing Consignment
						consingment = prepareDoxConsignments(bookingTO,
								bookingCommonService,
								bookingWrapperTO.getConsgRateDetails());
						consingment.setRefNo(bookingTO.getRefNo());
						if (!StringUtil
								.isEmptyDouble(bookingTO.getCodOrLCAmt())) {
							String consgSeries = bookingTO.getConsgNumber()
									.substring(4, 5);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.PRODUCT_SERIES_CASH_COD,
									consgSeries)
									|| StringUtils
											.equalsIgnoreCase(
													CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD,
													consgSeries)) {
								consingment
										.setCodAmt(bookingTO.getCodOrLCAmt());
							} else if (StringUtils
									.equalsIgnoreCase(
											CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT,
											consgSeries)) {
								consingment.setLcAmount(bookingTO
										.getCodOrLCAmt());
							}
						}
						if (StringUtils.isNotEmpty(bookingTO.getLcBankName())) {
							consingment
									.setLcBankName(bookingTO.getLcBankName());
						}
						bookings.add(booking);
						consignments.add(consingment);
					} catch (CGBusinessException e) {
						LOGGER.error(
								"CGBusinessException in CreditCustomerBookingConverter :: creaditCustBookingDoxDomainConverter() :: failure consignment DO"
										+ bookingBaseTO.getConsgNumber(), e);
						failureConsignments.add(bookingBaseTO.getConsgNumber());
					} catch (Exception e) {
						LOGGER.error(
								"Exception in CreditCustomerBookingConverter :: creaditCustBookingDoxDomainConverter() :: failure consignment DO"
										+ bookingBaseTO.getConsgNumber()
								,e);
						failureConsignments.add(bookingBaseTO.getConsgNumber());
					}
				}
				
				LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::DO conversion and business validation ended");
			}
			bookingWrapperDO.setSucessConsignments(bookings);
			bookingWrapperDO.setConsingments(consignments);
			if (!StringUtil.isEmptyColletion(failureConsignments)
					&& failureConsignments.size() > 0)
				bookingWrapperDO.setFailureConsignments(failureConsignments);
			
			LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::success booking list size after business validation" + successBookings.size());
			LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::success consignment list size after business validation" + consignments.size());
			LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter::failure list size after business validation" + failureConsignments.size());
		} catch (Exception e) {
			LOGGER.error(
					"Exception in CreditCustomerBookingConverter::creaditCustBookingDoxDomainConverter:: :",
					e);
			throw e;
		}
		
		
		return bookingWrapperDO;
	}

	private static void setupConsigneeAddress(BookingDO booking,
			CustomerTO customer, PickupDeliveryAddressTO address) {
		LOGGER.debug("CreditCustomerBookingConverter::setupConsigneeAddress::START------------>:::::::");
		ConsigneeConsignorDO consignor = new ConsigneeConsignorDO();
		StringBuilder addressBuilder = new StringBuilder();
		addressBuilder.append(address.getAddress1());
		addressBuilder.append(",");
		addressBuilder.append(address.getAddress2());
		addressBuilder.append(",");
		addressBuilder.append(address.getAddress3());
		consignor.setAddress(addressBuilder.toString());
		consignor.setFirstName(customer.getBusinessName());
		consignor.setMobile(address.getMobile());
		consignor.setPhone(address.getPhone());
		booking.setConsignorId(consignor);
		LOGGER.debug("CreditCustomerBookingConverter::setupConsigneeAddress::END------------>:::::::");
	}

	/**
	 * Creadit cust booking parcel domain converter.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperDO creaditCustBookingParcelDomainConverter(
			BookingWrapperTO bookingWrapperTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter::START------------>:::::::");
		List<BookingDO> bookings = null;
		List<ConsignmentDO> consignments = null;
		BookingWrapperDO bookingWrapperDO = new BookingWrapperDO();
		Set<String> failureConsignments = new HashSet<String>();
		try {
			if (!StringUtil.isEmptyColletion(bookingWrapperTO
					.getFailureConsignments())
					&& bookingWrapperTO.getFailureConsignments().size() > 0){
				failureConsignments = bookingWrapperTO.getFailureConsignments();
			}

			List<? extends BookingTO> successBookings = bookingWrapperTO
					.getSucessConsignments();

			bookings = new ArrayList<BookingDO>(successBookings.size());
			consignments = new ArrayList<ConsignmentDO>(successBookings.size());

			boolean is2wayWriteEnabled = TwoWayWriteProcessCall
					.isTwoWayWriteEnabled();

			if (!StringUtil.isEmptyColletion(successBookings)) {
				for (BookingTO bookingBaseTO : bookingWrapperTO
						.getSucessConsignments()) {
					try {
						ConsignmentDO consingment = new ConsignmentDO();
						CreditCustomerBookingParcelTO bookingTO = (CreditCustomerBookingParcelTO) bookingBaseTO;
						BookingDO booking = convert(bookingTO);
						CustomerDO custDO = new CustomerDO();
						custDO.setCustomerId(bookingTO.getCustomerId());
						booking.setCustomerId(custDO);
						if (is2wayWriteEnabled) {
							booking.setDtToCentral(CommonConstants.YES);
							consingment.setDtToCentral("Y");
						} else {
							booking.setDtToCentral(CommonConstants.NO);
							consingment.setDtToCentral("N");
						}

						if (!StringUtil.isStringEmpty(bookingBaseTO
								.getCustomerCodeSingle()))
							booking.setShippedToCode(bookingBaseTO
									.getCustomerCodeSingle());

						booking.setRefNo(bookingTO.getRefNo());
						booking.setPickRunsheetNo(bookingTO
								.getPickupRunsheetNo());
						booking.setNoOfPieces(bookingTO.getNoOfPieces());
						// Setting vol weight details
						if (bookingTO.getLength() != null
								&& bookingTO.getLength() > 0)
							booking.setVolWeight(bookingTO.getLength());
						if (bookingTO.getHeight() != null
								&& bookingTO.getHeight() > 0)
							booking.setHeight(bookingTO.getHeight());
						if (bookingTO.getBreath() != null
								&& bookingTO.getBreath() > 0)
							booking.setBreath(bookingTO.getBreath());
						if (bookingTO.getLength() != null
								&& bookingTO.getLength() > 0)
							booking.setLength(bookingTO.getLength());
						if (bookingTO.getVolWeight() != null
								&& bookingTO.getVolWeight() > 0)
							booking.setVolWeight(bookingTO.getVolWeight());
						// Setting paperworks
						if (!StringUtil.isEmptyInteger(bookingTO
								.getCnPaperworkId())) {
							CNPaperWorksDO cnPaperWork = new CNPaperWorksDO();
							cnPaperWork.setCnPaperWorkId(bookingTO
									.getCnPaperworkId());
							booking.setCnPaperWorkId(cnPaperWork);
							booking.setPaperWorkRefNo(bookingTO
									.getPaperWorkRefNo());
						}
						// Setting Content
						if (!StringUtil.isEmptyInteger(bookingTO
								.getCnContentId())) {
							CNContentDO cnContent = new CNContentDO();
							cnContent
									.setCnContentId(bookingTO.getCnContentId());
							booking.setCnContentId(cnContent);
						} else {
							booking.setOtherCNContent(bookingTO
									.getOtherCNContent());
						}
						if (!StringUtil.isEmptyInteger(bookingTO
								.getDlvTimeMapId()))
							booking.setPincodeDlvTimeMapId(bookingTO
									.getDlvTimeMapId());
						// Insurence Dtls
						if (!StringUtil.isEmptyInteger(bookingTO
								.getInsuredById())) {
							InsuredByDO insuredBy = new InsuredByDO();
							insuredBy
									.setInsuredById(bookingTO.getInsuredById());
							booking.setInsuredBy(insuredBy);
							booking.setInsurencePolicyNo(bookingTO
									.getPolicyNo());
						}
						if (!StringUtil.isEmptyDouble(bookingTO
								.getCnPricingDtls().getDeclaredvalue())) {
							booking.setDeclaredValue(bookingTO
									.getCnPricingDtls().getDeclaredvalue());
						}
						// Settng Customer address
						if (StringUtil.isNull(bookingTO.getConsignor())) {
							CustomerTO customer = bookingCommonService
									.getCustomerByIdOrCode(
											bookingTO.getCustomerId(),
											CommonConstants.EMPTY_STRING);
							if (!StringUtil.isNull(customer)) {
								PickupDeliveryAddressTO address = customer
										.getAddress();
								if (!StringUtil.isNull(address)) {
									setupConsigneeAddress(booking, customer,
											address);
								}
							}
						}
						
						if(booking.getUpdatedProcess().getProcessCode().equalsIgnoreCase("UPPU")){
							LOGGER.error(
									"Throwing business Exception in CreditCustomerBookingConverter :: creaditCustBookingParcelDomainConverter() :: Updated process is set as of pickup."
											+ bookingBaseTO.getConsgNumber());
							throw new CGBusinessException();
						}
						if (StringUtil.isNull(bookingWrapperTO
								.getConsgRateDetails())
								|| StringUtil
										.isNull(bookingWrapperTO
												.getConsgRateDetails()
												.get(bookingBaseTO
														.getConsgNumber()))) {
							LOGGER.error(
									"Throwing business Exception in CreditCustomerBookingConverter :: creaditCustBookingParcelDomainConverter() :: Rate is empty for consg no."
											+ bookingBaseTO.getConsgNumber());
							throw new CGBusinessException(BookingErrorCodesConstants.RATE_NOT_CALCULATED);
						}
						// Preparing Consingment
						consingment = prepareParcelConsignments(bookingTO,
								bookingCommonService,
								bookingWrapperTO.getConsgRateDetails());
						consingment.setRefNo(bookingTO.getRefNo());
						if (!StringUtil
								.isEmptyDouble(bookingTO.getCodOrLCAmt())) {
							String consgSeries = bookingTO.getConsgNumber()
									.substring(4, 5);
							if (StringUtils.equalsIgnoreCase(
									CommonConstants.PRODUCT_SERIES_CASH_COD,
									consgSeries)
									|| StringUtils
											.equalsIgnoreCase(
													CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD,
													consgSeries)) {
								consingment
										.setCodAmt(bookingTO.getCodOrLCAmt());
							} else if (StringUtils
									.equalsIgnoreCase(
											CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT,
											consgSeries)) {
								consingment.setLcAmount(bookingTO
										.getCodOrLCAmt());
							}
						}
						if (StringUtils.isNotEmpty(bookingTO.getLcBankName())) {
							consingment
									.setLcBankName(bookingTO.getLcBankName());
						}
						bookings.add(booking);
						consignments.add(consingment);
					} catch (CGBusinessException e) {
						LOGGER.error(
								"CGBusinessException in CreditCustomerBookingConverter :: creaditCustBookingParcelDomainConverter() :: failure consignment DO(Not rethrowing the exception)"
										+ bookingBaseTO.getConsgNumber(), e);
						failureConsignments.add(bookingBaseTO.getConsgNumber());
					} catch (Exception e) {
						LOGGER.error(
								"Exception in CreditCustomerBookingConverter :: creaditCustBookingParcelDomainConverter() :: failure consignment DO(Not rethrowing the exception)"
										+ bookingBaseTO.getConsgNumber(),e);
						failureConsignments.add(bookingBaseTO.getConsgNumber());
					}
				}
			}
			bookingWrapperDO.setSucessConsignments(bookings);
			bookingWrapperDO.setConsingments(consignments);
			if (!StringUtil.isEmptyColletion(failureConsignments)
					&& failureConsignments.size() > 0)
				bookingWrapperDO.setFailureConsignments(failureConsignments);
			LOGGER.debug(
					"CreditCustomerBookingConverter :: creaditCustBookingParcelDomainConverter() ::All failure consignment are"
							+ failureConsignments);
			
		} catch (Exception e) {
			LOGGER.error(
					"Exception in CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter:: :",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CreditCustomerBookingConverter::creaditCustBookingParcelDomainConverter::END------------>:::::::");
		return bookingWrapperDO;
	}

	private static void setupConsignorAddress(
			CreditCustomerBookingDoxTO bookingTO,
			BookingCommonService bookingCommonService,
			CreditCustomerBookingDoxTO bookingDoxTO, int rowCount, ConsignorConsigneeTO consignor)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("CreditCustomerBookingConverter::setupConsignorAddress::START------------>:::::::");
		CustomerTO customer = bookingCommonService.getCustomerByIdOrCode(
				bookingTO.getCustomerIds()[rowCount],
				CommonConstants.EMPTY_STRING);
		StringBuilder addressBuilder = null;
		if (!StringUtil.isNull(customer)) {
			PickupDeliveryAddressTO address = customer.getAddress();
			if (!StringUtil.isNull(address)) {
				addressBuilder = new StringBuilder();
				addressBuilder.append(address.getAddress1());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress2());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress3());
				consignor.setAddress(addressBuilder.toString());
				consignor.setFirstName(customer.getBusinessName());
				consignor.setMobile(address.getMobile());
				consignor.setPhone(address.getPhone());
				bookingDoxTO.setConsignor(consignor);
			}
		}
		LOGGER.debug("CreditCustomerBookingConverter::setupConsignorAddress::END------------>:::::::");
	}

	/**
	 * Credit cust booking parcel converter.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperTO creditCustBookingParcelConverter(
			CreditCustomerBookingParcelTO bookingTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingParcelConverter::START------------>:::::::");
		CreditCustomerBookingParcelTO bookingParcelTO = null;
		List<CreditCustomerBookingParcelTO> bookingParcelTOs = null;
		BookingWrapperTO bookingWrapperTO = null;
		ConsignorConsigneeTO consignor = null;
		String consgNumber = "";
		int consgLen = bookingTO.getConsgNumbers().length;
		if (!StringUtil.isNull(bookingTO)) {
			Set<String> failureConsignments = new HashSet<String>();
			bookingWrapperTO = new BookingWrapperTO();
			bookingParcelTO = new CreditCustomerBookingParcelTO();
			bookingParcelTOs = new ArrayList<CreditCustomerBookingParcelTO>();
			if (bookingTO.getConsgNumbers() != null && consgLen > 0) {
				for (int rowCount = 0; rowCount < consgLen; rowCount++) {
					if (StringUtils
							.isNotEmpty(bookingTO.getConsgNumbers()[rowCount])) {
						try {
							consgNumber = bookingTO.getConsgNumbers()[rowCount];

							bookingParcelTO = (CreditCustomerBookingParcelTO) setUpBookingTOs(
									bookingTO, rowCount);

							if (bookingTO.getCustomerCode() != null
									&& bookingTO.getCustomerCode().length > rowCount
									&& bookingTO.getCustomerCode()[rowCount] != null)
								bookingParcelTO.setCustomerCodeSingle(bookingTO
										.getCustomerCode()[rowCount]
										.split(CommonConstants.HYPHEN)[0]);

							CNPricingDetailsTO cnPricingDetls = bookingParcelTO
									.getCnPricingDtls();
							/* BookingTypeTO bookingType = null; */
							if (cnPricingDetls.getRateType() != null
									&& cnPricingDetls.getRateType()
											.equals("CH")) {
								/*
								 * bookingType = bookingCommonService
								 * .getBookingType
								 * (BookingConstants.CASH_BOOKING);
								 */
								bookingParcelTO.getCnPricingDtls().setRateType(
										RateCommonConstants.RATE_TYPE_CASH);
							} else {
								/*
								 * bookingType = bookingCommonService
								 * .getBookingType
								 * (BookingConstants.CCC_BOOKING);
								 */
								bookingParcelTO
										.getCnPricingDtls()
										.setRateType(
												RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
							}
							bookingParcelTO.setBookingTypeId(bookingTO
									.getBookingTypeId());

							bookingParcelTO.setCustomerId(bookingTO
									.getCustomerIds()[rowCount]);
							/*
							 * bookingParcelTO .getCnPricingDtls() .setRateType(
							 * RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
							 */
							if (bookingTO.getPickupRunsheetNos() != null
									&& bookingTO.getPickupRunsheetNos().length > rowCount
									&& bookingTO.getPickupRunsheetNos()[rowCount] != null)
								bookingParcelTO.setPickupRunsheetNo(bookingTO
										.getPickupRunsheetNos()[rowCount]);

							if (bookingTO.getPriorityServicedOns() != null
									&& bookingTO.getPriorityServicedOns().length > rowCount
									&& bookingTO.getPriorityServicedOns()[rowCount] != null)
								bookingParcelTO.setPriorityServiced(bookingTO
										.getPriorityServicedOns()[rowCount]);

							if (bookingTO.getRefNos() != null
									&& bookingTO.getRefNos().length > rowCount
									&& bookingTO.getRefNos()[rowCount] != null)
								bookingParcelTO
										.setRefNo(bookingTO.getRefNos()[rowCount]);
							if (bookingTO.getChargeableWeights() != null
									&& bookingTO.getChargeableWeights().length > rowCount
									&& bookingTO.getChargeableWeights()[rowCount] != 0)
								bookingParcelTO.setFinalWeight(bookingTO
										.getChargeableWeights()[rowCount]);
							// Child Consignments
							if (StringUtils.isNotEmpty(bookingTO
									.getChildCNDetails()[rowCount]))
								bookingParcelTO.setChildCNsDtls(bookingTO
										.getChildCNDetails()[rowCount]);
							// Setting content
							if (bookingTO.getCnContentIds() != null
									&& bookingTO.getCnContentIds().length > rowCount
									&& bookingTO.getCnContentIds()[rowCount] != 0)
								bookingParcelTO.setCnContentId(bookingTO
										.getCnContentIds()[rowCount]);

							if (bookingTO.getOtherCNContents() != null
									&& bookingTO.getOtherCNContents().length > rowCount
									&& StringUtils.isNotEmpty(bookingTO
											.getOtherCNContents()[rowCount]))
								bookingParcelTO.setOtherCNContent(bookingTO
										.getOtherCNContents()[rowCount]);
							// Settings Paperworks
							if (bookingTO.getCnPaperWorkIds() != null
									&& bookingTO.getCnPaperWorkIds().length > rowCount
									&& bookingTO.getCnPaperWorkIds()[rowCount] != 0)
								bookingParcelTO.setCnPaperworkId(bookingTO
										.getCnPaperWorkIds()[rowCount]);
							if (bookingTO.getPaperRefNum() != null
									&& bookingTO.getPaperRefNum().length > rowCount
									&& bookingTO.getPaperRefNum()[rowCount] != null)
								bookingParcelTO.setPaperWorkRefNo(bookingTO
										.getPaperRefNum()[rowCount]);
							// Setting volweight
							if (bookingTO.getLengths() != null
									&& bookingTO.getLengths().length > rowCount
									&& bookingTO.getLengths()[rowCount] != 0)
								bookingParcelTO.setLength(bookingTO
										.getLengths()[rowCount]);
							if (bookingTO.getHeights() != null
									&& bookingTO.getHeights().length > rowCount
									&& bookingTO.getHeights()[rowCount] != 0)
								bookingParcelTO.setHeight(bookingTO
										.getHeights()[rowCount]);
							if (bookingTO.getBreaths() != null
									&& bookingTO.getBreaths().length > rowCount
									&& bookingTO.getBreaths()[rowCount] != 0)
								bookingParcelTO.setBreath(bookingTO
										.getBreaths()[rowCount]);
							// Insurence details
							if (bookingTO.getInsuaranceNos() != null
									&& bookingTO.getInsuaranceNos().length > rowCount
									&& bookingTO.getInsuaranceNos()[rowCount] != 0)
								bookingParcelTO.setInsuredById(bookingTO
										.getInsuaranceNos()[rowCount]);
							if (bookingTO.getPolicyNos() != null
									&& bookingTO.getPolicyNos().length > rowCount
									&& bookingTO.getPolicyNos()[rowCount] != null)
								bookingParcelTO.setPolicyNo(bookingTO
										.getPolicyNos()[rowCount]);
							if (bookingTO.getDlvTimeMapIds() != null
									&& bookingTO.getDlvTimeMapIds().length > rowCount
									&& bookingTO.getDlvTimeMapIds()[rowCount] > 0)
								bookingParcelTO.setDlvTimeMapId(bookingTO
										.getDlvTimeMapIds()[rowCount]);
							bookingParcelTO.setNoOfPieces(bookingTO
									.getNumOfPieces()[rowCount]);
							// Settng Customer address
							if (StringUtil.isNull(bookingParcelTO
									.getConsignor())) {
								consignor = new ConsignorConsigneeTO();
								setupConsignorAddress(bookingTO,
										bookingCommonService, bookingParcelTO,
										rowCount, consignor);
							}
							if (bookingTO.getCodOrLCAmts() != null
									&& bookingTO.getCodOrLCAmts().length > rowCount
									&& bookingTO.getCodOrLCAmts()[rowCount] != 0)
								bookingParcelTO.setCodOrLCAmt(bookingTO
										.getCodOrLCAmts()[rowCount]);
							if (bookingTO.getLcBankNames() != null
									&& bookingTO.getLcBankNames().length > rowCount
									&& bookingTO.getLcBankNames()[rowCount] != null)
								bookingParcelTO.setLcBankName(bookingTO
										.getLcBankNames()[rowCount]);
							
							bookingParcelTO.setCreatedBy(bookingTO.getCreatedBy());
							bookingParcelTO.setUpdatedBy(bookingTO.getCreatedBy());
							bookingParcelTOs.add(bookingParcelTO);
						} catch (Exception e) {
							LOGGER.error(
									"Exception in :: CreditCustomerBookingConverter :: creditCustBookingParcelConverter() : Failure for Consingment "
											+ consgNumber, e);
							failureConsignments.add(consgNumber);
						}
					}
					bookingWrapperTO.setSucessConsignments(bookingParcelTOs);
					if (!StringUtil.isEmptyColletion(failureConsignments)
							&& failureConsignments.size() > 0) {
						bookingWrapperTO
								.setFailureConsignments(failureConsignments);
					}
				}
			}
		}
		LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingParcelConverter::END------------>:::::::");
		return bookingWrapperTO;
	}

	private static void setupConsignorAddress(
			CreditCustomerBookingParcelTO bookingTO,
			BookingCommonService bookingCommonService,
			CreditCustomerBookingParcelTO bookingParcelTO, int rowCount, ConsignorConsigneeTO consignor)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("CreditCustomerBookingConverter::setupConsignorAddress::START------------>:::::::");
		CustomerTO customer = bookingCommonService.getCustomerByIdOrCode(
				bookingTO.getCustomerIds()[rowCount],
				CommonConstants.EMPTY_STRING);
		StringBuilder addressBuilder = null;
		if (!StringUtil.isNull(customer)) {
			PickupDeliveryAddressTO address = customer.getAddress();
			if (!StringUtil.isNull(address)) {
				addressBuilder = new StringBuilder();
				addressBuilder.append(address.getAddress1());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress2());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress3());
				consignor.setAddress(addressBuilder.toString());
				consignor.setFirstName(customer.getBusinessName());
				consignor.setMobile(address.getMobile());
				consignor.setPhone(address.getPhone());
				bookingParcelTO.setConsignor(consignor);
			}
		}
		LOGGER.debug("CreditCustomerBookingConverter::setupConsignorAddress::END------------>:::::::");
	}

	/**
	 * Credit cust booking dox converter.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperTO creditCustBookingDoxConverter(
			CreditCustomerBookingDoxTO bookingTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter::START------------>:::::::");
		CreditCustomerBookingDoxTO bookingDoxTO = null;
		List<CreditCustomerBookingDoxTO> bookingDoxTOs = null;
		ConsignorConsigneeTO consignor = null;
		BookingWrapperTO bookingWrapperTO = null;
		try {
			Set<String> failureConsignments = null;
			bookingWrapperTO = new BookingWrapperTO();
			String consgNumber = "";
			if (!StringUtil.isNull(bookingTO)) {
				failureConsignments = new HashSet<String>();
				bookingDoxTO = new CreditCustomerBookingDoxTO();
				bookingDoxTOs = new ArrayList<CreditCustomerBookingDoxTO>();

				if (bookingTO.getConsgNumbers() != null
						&& bookingTO.getConsgNumbers().length > 0) {
					LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter::b4 starting conversion...total ROW entered in UI screen are: " + bookingTO.getConsgNumbers().length);
					for (int rowCount = 0; rowCount < bookingTO
							.getConsgNumbers().length; rowCount++) {
						if (StringUtils
								.isNotEmpty(bookingTO.getConsgNumbers()[rowCount])) {
							// Start... For Bulk Saving - Roll back only failure
							// CNs
							try {
								consgNumber = bookingTO.getConsgNumbers()[rowCount];
								LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter:: staring preparation for CN[" + consgNumber + "]");
								bookingDoxTO = (CreditCustomerBookingDoxTO) setUpBookingTOs(bookingTO, rowCount);
								if (bookingTO.getCustomerCode() != null
										&& bookingTO.getCustomerCode().length > rowCount
										&& bookingTO.getCustomerCode()[rowCount] != null)
									bookingDoxTO
											.setCustomerCodeSingle(bookingTO
													.getCustomerCode()[rowCount]
													.split(CommonConstants.HYPHEN)[0]);

								CNPricingDetailsTO cnPricingDetls = bookingDoxTO
										.getCnPricingDtls();
								/* BookingTypeTO bookingType = null; */
								if (cnPricingDetls.getRateType() != null
										&& cnPricingDetls.getRateType().equals(
												"CH")) {
									/*
									 * bookingType = bookingCommonService
									 * .getBookingType
									 * (BookingConstants.CASH_BOOKING);
									 */
									LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter:: setting rate type as: " + RateCommonConstants.RATE_TYPE_CASH);
									bookingDoxTO
											.getCnPricingDtls()
											.setRateType(
													RateCommonConstants.RATE_TYPE_CASH);
								} else {
									/*
									 * bookingType = bookingCommonService
									 * .getBookingType
									 * (BookingConstants.CCC_BOOKING);
									 */
									LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter:: setting rate type as: " + RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
									bookingDoxTO
											.getCnPricingDtls()
											.setRateType(
													RateCommonConstants.RATE_TYPE_CREDIT_CUSTOMER);
								}
								
								
								bookingDoxTO.setBookingTypeId(bookingTO
										.getBookingTypeId());
								bookingDoxTO.setCustomerId(bookingTO
										.getCustomerIds()[rowCount]);

								if (bookingTO.getPickupRunsheetNos() != null
										&& bookingTO.getPickupRunsheetNos().length > rowCount
										&& bookingTO.getPickupRunsheetNos()[rowCount] != null)
									bookingDoxTO.setPickupRunsheetNo(bookingTO
											.getPickupRunsheetNos()[rowCount]);
								bookingDoxTO
										.setRefNo(bookingTO.getRefNos()[rowCount]);

								if (bookingTO.getPriorityServicedOns() != null
										&& bookingTO.getPriorityServicedOns().length > rowCount
										&& bookingTO.getPriorityServicedOns()[rowCount] != null)
									bookingDoxTO
											.setPriorityServiced(bookingTO
													.getPriorityServicedOns()[rowCount]);

								bookingDoxTO
										.setRefNo(bookingTO.getRefNos()[rowCount]);

								if (bookingTO.getChargeableWeights() != null
										&& bookingTO.getChargeableWeights().length > rowCount
										&& bookingTO.getChargeableWeights()[rowCount] != 0)
									bookingDoxTO.setFinalWeight(bookingTO
											.getChargeableWeights()[rowCount]);
								// Settng Customer address
								if (StringUtil.isNull(bookingDoxTO
										.getConsignor())) {
									consignor = new ConsignorConsigneeTO();
									setupConsignorAddress(bookingTO,
											bookingCommonService, bookingDoxTO,
											rowCount, consignor);
								}
								if (bookingTO.getCodOrLCAmts() != null
										&& bookingTO.getCodOrLCAmts().length > rowCount
										&& bookingTO.getCodOrLCAmts()[rowCount] != 0)
									bookingDoxTO.setCodOrLCAmt(bookingTO
											.getCodOrLCAmts()[rowCount]);
								if (bookingTO.getLcBankNames() != null
										&& bookingTO.getLcBankNames().length > rowCount
										&& bookingTO.getLcBankNames()[rowCount] != null)
									bookingDoxTO.setLcBankName(bookingTO
											.getLcBankNames()[rowCount]);
								bookingDoxTO.setCreatedBy(bookingTO.getCreatedBy());
								bookingDoxTO.setUpdatedBy(bookingTO.getCreatedBy());
								LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter::[" + rowCount + "] added in input TO list");
								bookingDoxTOs.add(bookingDoxTO);
							} catch (Exception e) {
								LOGGER.error(
										"Exception in :: CreditCustomerBookingConverter :: creditCustBookingDoxConverter() row[" + rowCount + "]: Failure for Consingment "
												+ consgNumber, e);
								failureConsignments.add(consgNumber);
							}
							bookingWrapperTO
									.setSucessConsignments(bookingDoxTOs);
							if (!StringUtil
									.isEmptyColletion(failureConsignments)
									&& failureConsignments.size() > 0) {
								bookingWrapperTO
										.setFailureConsignments(failureConsignments);
								LOGGER.debug(
										"CreditCustomerBookingConverter :: creditCustBookingDoxConverter() ::All failure consignment are"
												+ failureConsignments);
							}
						} else {
							LOGGER.debug("CreditCustomerBookingConverter :: creditCustBookingDoxConverter() ::ROW[" + rowCount +"] is empty---->");
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in :: CreditCustomerBookingConverter :: creditCustBookingDoxConverter()");
		}
		LOGGER.debug("CreditCustomerBookingConverter::creditCustBookingDoxConverter::END------------>:::::::");
		return bookingWrapperTO;
	}
}
