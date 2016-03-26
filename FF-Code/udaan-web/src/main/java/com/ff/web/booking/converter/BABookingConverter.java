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
import com.ff.booking.BABookingDoxTO;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingWrapperTO;
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
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class BABookingConverter.
 */
public class BABookingConverter extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BABookingConverter.class);

	/**
	 * Ba booking dox domain converter.
	 * 
	 * @param baBookingTOs
	 *            the ba booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperDO baBookingDoxDomainConverter(
			BookingWrapperTO bookingWrapperTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BABookingConverter::baBookingDoxDomainConverter::START------------>:::::::");
		List<BookingDO> bookings = null;
		List<ConsignmentDO> consignments = null;
		BookingWrapperDO bookingWrapperDO = new BookingWrapperDO();
		Set<String> failureConsignments = new HashSet<String>();
		try {
			if (!StringUtil.isEmptyColletion(bookingWrapperTO
					.getFailureConsignments())) {
				failureConsignments = bookingWrapperTO.getFailureConsignments();
			}
			List<? extends BookingTO> successBookings = bookingWrapperTO
					.getSucessConsignments();
			bookings = new ArrayList<BookingDO>(successBookings.size());
			consignments = new ArrayList<ConsignmentDO>(successBookings.size());

			if (!successBookings.isEmpty()) {
				for (BookingTO bookingTO : successBookings) {
					// Start... For Bulk Saving - Roll back only failure
					try {
						ConsignmentDO consingment = new ConsignmentDO();
						BABookingDoxTO baBookingTO = (BABookingDoxTO) bookingTO;
						BookingDO booking = convert(baBookingTO);
						// Start..Customer enhanchments
						CustomerDO baCustomer = new CustomerDO();
						baCustomer.setCustomerId(baBookingTO
								.getBizAssociateId());
						booking.setCustomerId(baCustomer);
						booking.setRefNo(baBookingTO.getRefNo());
						booking.setNoOfPieces(baBookingTO.getNoOfPieces());
						if (StringUtil.isNull(bookingWrapperTO
								.getConsgRateDetails())
								|| StringUtil.isNull(bookingWrapperTO
										.getConsgRateDetails().get(
												bookingTO.getConsgNumber()))) {
							throw new CGBusinessException();
						}
						// Preparing Consingment
						consingment = prepareDoxConsignments(bookingTO,
								bookingCommonService,
								bookingWrapperTO.getConsgRateDetails());
						consingment.setRefNo(baBookingTO.getRefNo());
						bookings.add(booking);
						consignments.add(consingment);
					} catch (CGBusinessException e) {
						LOGGER.error(
								"CGBusinessException in BABookingConverter :: baBookingDomainConverter() :: faulure consignment DO"
										+ bookingTO.getConsgNumber(), e);
						failureConsignments.add(bookingTO.getConsgNumber());
					} catch (Exception e) {
						LOGGER.error(
								"Exception in BABookingConverter :: baBookingDomainConverter() :: faulure consignment DO"
										+ bookingTO.getConsgNumber(),
								e.getMessage());
						failureConsignments.add(bookingTO.getConsgNumber());
					}
				}
			}
			bookingWrapperDO.setSucessConsignments(bookings);
			bookingWrapperDO.setConsingments(consignments);
			if (!StringUtil.isEmptyColletion(failureConsignments)
					&& failureConsignments.size() > 0)
				bookingWrapperDO.setFailureConsignments(failureConsignments);
		} catch (Exception e) {
			LOGGER.error("Exception in BABookingConverter :: baBookingDomainConverter() ::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BABookingConverter::baBookingDoxDomainConverter::END------------>:::::::");
		return bookingWrapperDO;
	}

	/**
	 * Ba booking parcel domain converter.
	 * 
	 * @param baBookingTOs
	 *            the ba booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperDO baBookingParcelDomainConverter(
			BookingWrapperTO bookingWrapperTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BABookingConverter::baBookingParcelDomainConverter::START------------>:::::::");

		Set<String> failureConsignments = new HashSet<String>();
		BookingWrapperDO bookingWrapperDO = new BookingWrapperDO();

		List<BookingDO> bookings = null;
		List<ConsignmentDO> consignments = null;

		try {
			if (!StringUtil.isEmptyColletion(bookingWrapperTO
					.getFailureConsignments())) {
				failureConsignments = bookingWrapperTO.getFailureConsignments();
			}

			List<? extends BookingTO> successBookings = bookingWrapperTO
					.getSucessConsignments();

			bookings = new ArrayList<BookingDO>(successBookings.size());
			consignments = new ArrayList<ConsignmentDO>(successBookings.size());

			if (!successBookings.isEmpty()) {
				for (BookingTO baBooking : bookingWrapperTO
						.getSucessConsignments()) {
					// Start... For Bulk Saving - Roll back only failure CNS
					try {
						ConsignmentDO consingment = new ConsignmentDO();
						BABookingParcelTO baBookingTO = (BABookingParcelTO) baBooking;
						BookingDO booking = convert(baBookingTO);
						booking.setNoOfPieces(baBookingTO.getNoOfPieces());
						// Start..Customer enhanchments
						CustomerDO baCustomer = new CustomerDO();
						baCustomer.setCustomerId(baBookingTO
								.getBizAssociateId());
						// booking.setBusinessAssociateId(baBookingTO.getBizAssociateId());
						booking.setCustomerId(baCustomer);
						// End..Customer enhanchments
						booking.setRefNo(baBookingTO.getRefNo());
						// Setting Consignee & Consignor
						if (!StringUtil.isNull(baBookingTO.getConsignee())) {
							ConsigneeConsignorDO consignee = BookingUtils
									.setUpConsigneeConsignorDetails(baBookingTO
											.getConsignee());
							booking.setConsigneeId(consignee);
						}
						if (!StringUtil.isNull(baBookingTO.getConsignor())) {
							ConsigneeConsignorDO consignor = BookingUtils
									.setUpConsigneeConsignorDetails(baBookingTO
											.getConsignor());
							booking.setConsignorId(consignor);
						}
						if (!StringUtil.isEmptyDouble(baBookingTO
								.getCnPricingDtls().getDeclaredvalue())) {
							booking.setDeclaredValue(baBookingTO
									.getCnPricingDtls().getDeclaredvalue());
						}
						// Setting vol weight details
						if (baBookingTO.getLength() != null
								&& baBookingTO.getLength() > 0)
							booking.setVolWeight(baBookingTO.getLength());
						if (baBookingTO.getHeight() != null
								&& baBookingTO.getHeight() > 0)
							booking.setHeight(baBookingTO.getHeight());
						if (baBookingTO.getBreath() != null
								&& baBookingTO.getBreath() > 0)
							booking.setBreath(baBookingTO.getBreath());
						if (baBookingTO.getVolWeight() != null
								&& baBookingTO.getVolWeight() > 0)
							booking.setVolWeight(baBookingTO.getVolWeight());
						// Setting paperworks
						if (!StringUtil.isEmptyInteger(baBookingTO
								.getCnPaperworkId())) {
							CNPaperWorksDO cnPaperWork = new CNPaperWorksDO();
							cnPaperWork.setCnPaperWorkId(baBookingTO
									.getCnPaperworkId());
							booking.setCnPaperWorkId(cnPaperWork);
							booking.setPaperWorkRefNo(baBookingTO
									.getPaperWorkRefNo());
						}
						// Setting Content
						if (!StringUtil.isEmptyInteger(baBookingTO
								.getCnContentId())) {
							CNContentDO cnContent = new CNContentDO();
							cnContent.setCnContentId(baBookingTO
									.getCnContentId());
							booking.setCnContentId(cnContent);
						} else {
							booking.setOtherCNContent(baBookingTO
									.getOtherCNContent());
						}
						if (!StringUtil.isEmptyInteger(baBookingTO
								.getDlvTimeMapId()))
							booking.setPincodeDlvTimeMapId(baBookingTO
									.getDlvTimeMapId());
						// Insurence Dtls
						if (!StringUtil.isEmptyInteger(baBookingTO
								.getInsuredById())) {
							InsuredByDO insuredBy = new InsuredByDO();
							insuredBy.setInsuredById(baBookingTO
									.getInsuredById());
							booking.setInsuredBy(insuredBy);
							booking.setInsurencePolicyNo(baBookingTO
									.getPolicyNo());
						}
						if (StringUtil.isNull(bookingWrapperTO
								.getConsgRateDetails())
								|| StringUtil.isNull(bookingWrapperTO
										.getConsgRateDetails().get(
												baBooking.getConsgNumber()))) {
							throw new CGBusinessException();
						}

						// Preparing Consingment
						consingment = prepareParcelConsignments(baBookingTO,
								bookingCommonService,
								bookingWrapperTO.getConsgRateDetails());
						consingment.setRefNo(baBookingTO.getRefNo());
						bookings.add(booking);
						consignments.add(consingment);
					} catch (CGBusinessException e) {
						LOGGER.error(
								"CGBusinessException in BABookingConverter :: baBookingDomainConverter() :: faulure consignment DO"
										+ baBooking.getConsgNumber(),
								e.getMessage());
						failureConsignments.add(baBooking.getConsgNumber());
					} catch (Exception e) {
						LOGGER.error(
								"Exception in BABookingConverter :: baBookingDomainConverter() :: faulure consignment DO"
										+ baBooking.getConsgNumber(),
								e.getMessage());
						failureConsignments.add(baBooking.getConsgNumber());
					}
				}
			}
			bookingWrapperDO.setSucessConsignments(bookings);
			bookingWrapperDO.setConsingments(consignments);
			if (!StringUtil.isEmptyColletion(failureConsignments)
					&& failureConsignments.size() > 0)
				bookingWrapperDO.setFailureConsignments(failureConsignments);

		} catch (Exception e) {
			LOGGER.error("Exception in :: BABookingConverter :: baBookingDomainConverter() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("BABookingConverter::baBookingParcelDomainConverter::END------------>:::::::");
		return bookingWrapperDO;
	}

	/**
	 * Ba booking dox converter.
	 * 
	 * @param baBookingTO
	 *            the ba booking to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperTO baBookingDoxConverter(
			BABookingDoxTO baBookingTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BABookingConverter::baBookingDoxConverter::START------------>:::::::");
		BABookingDoxTO baBookingDoxTO = null;
		List<BABookingDoxTO> baBookingDoxTOs = null;
		Set<String> failureConsignments = new HashSet<String>();
		BookingWrapperTO bookingWrapperTO = new BookingWrapperTO();
		String consgNumber = "";
		int consgLen = baBookingTO.getConsgNumbers().length;
		if (!StringUtil.isNull(baBookingTO)) {
			baBookingDoxTO = new BABookingDoxTO();
			baBookingDoxTOs = new ArrayList<BABookingDoxTO>();
			if (baBookingTO.getConsgNumbers() != null && consgLen > 0) {
				for (int rowCount = 0; rowCount < consgLen; rowCount++) {
					if (StringUtils
							.isNotEmpty(baBookingTO.getConsgNumbers()[rowCount])) {
						// Start... For Bulk Saving - Roll back only failure
						// consignments
						try {
							consgNumber = baBookingTO.getConsgNumbers()[rowCount];
							baBookingDoxTO = (BABookingDoxTO) setUpBookingTOs(
									baBookingTO, rowCount);
							// Setting rate Type
							baBookingDoxTO.getCnPricingDtls().setRateType(
									RateCommonConstants.RATE_TYPE_BA);
							baBookingDoxTO.setBizAssociateId(baBookingTO
									.getBizAssociateId());

							if (baBookingTO.getPriorityServicedOns() != null
									&& baBookingTO.getPriorityServicedOns().length > rowCount
									&& baBookingTO.getPriorityServicedOns()[rowCount] != null)
								baBookingDoxTO.setPriorityServiced(baBookingTO
										.getPriorityServicedOns()[rowCount]);

							baBookingDoxTO
									.setRefNo(baBookingTO.getRefNos()[rowCount]);
							if (baBookingTO.getChargeableWeights() != null
									&& baBookingTO.getChargeableWeights().length > 0
									&& baBookingTO.getChargeableWeights()[rowCount] != 0)
								baBookingDoxTO.setFinalWeight(baBookingTO
										.getChargeableWeights()[rowCount]);

							if (baBookingTO.getCodAmts() != null
									&& baBookingTO.getCodAmts().length > rowCount
									&& baBookingTO.getCodAmts()[rowCount] != 0)
								baBookingDoxTO.setCodAmount(baBookingTO
										.getCodAmts()[rowCount]);

							if (baBookingTO.getBaAmts() != null
									&& baBookingTO.getBaAmts().length > 0
									&& baBookingTO.getBaAmts()[rowCount] != 0)
								baBookingDoxTO
										.setBaAmt(baBookingTO.getBaAmts()[rowCount]);

							// Settng Customer address
							if (StringUtil
									.isNull(baBookingDoxTO.getConsignor())) {
								CustomerTO customer = bookingCommonService
										.getCustomerByIdOrCode(
												baBookingTO.getBizAssociateId(),
												CommonConstants.EMPTY_STRING);
								if (!StringUtil.isNull(customer)) {
									PickupDeliveryAddressTO address = customer
											.getAddress();
									if (!StringUtil.isNull(address)) {
										setupConsignorAddress(baBookingDoxTO,
												customer, address);
									}
								}
							}
							baBookingDoxTO.setCreatedBy(baBookingTO.getCreatedBy());
							baBookingDoxTO.setUpdatedBy(baBookingTO.getCreatedBy());
							baBookingDoxTOs.add(baBookingDoxTO);
						} catch (Exception e) {
							LOGGER.error(
									"Exception in :: BABookingConverter :: baBookingDomainConverter() : Failure for Consingment "
											+ consgNumber, e);
							failureConsignments.add(consgNumber);

						}
						bookingWrapperTO.setSucessConsignments(baBookingDoxTOs);
						if (!StringUtil.isEmptyColletion(failureConsignments)
								&& failureConsignments.size() > 0) {
							bookingWrapperTO
									.setFailureConsignments(failureConsignments);
						}
					}
				}
			}
		}
		LOGGER.debug("BABookingConverter::baBookingDoxConverter::END------------>:::::::");
		return bookingWrapperTO;
	}

	private static void setupConsignorAddress(BABookingDoxTO baBookingDoxTO,
			CustomerTO customer, PickupDeliveryAddressTO address) {
		LOGGER.debug("BABookingConverter::setupConsignorAddress::START------------>:::::::");
		ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
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
		baBookingDoxTO.setConsignor(consignor);
		LOGGER.debug("BABookingConverter::setupConsignorAddress::END------------>:::::::");
	}

	/**
	 * Ba booking parcel converter.
	 * 
	 * @param baBookingTO
	 *            the ba booking to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingWrapperTO baBookingParcelConverter(
			BABookingParcelTO baBookingTO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BABookingConverter::baBookingParcelConverter::START------------>:::::::");
		BABookingParcelTO baBookingParcelTO = null;
		Set<String> failureConsignments = new HashSet<String>();
		BookingWrapperTO bookingWrapperTO = new BookingWrapperTO();
		List<BABookingParcelTO> baBookingParcelTOs = null;
		String consgNumber = "";
		int consgLen = baBookingTO.getConsgNumbers().length;
		if (!StringUtil.isNull(baBookingTO)) {
			baBookingParcelTO = new BABookingParcelTO();
			baBookingParcelTOs = new ArrayList<BABookingParcelTO>();
			if (baBookingTO.getConsgNumbers() != null && consgLen > 0) {
				for (int rowCount = 0; rowCount < consgLen; rowCount++) {
					if (StringUtils
							.isNotEmpty(baBookingTO.getConsgNumbers()[rowCount])) {
						// Start... For Bulk Saving - Roll back only failure
						// consignments
						try {
							consgNumber = baBookingTO.getConsgNumbers()[rowCount];
							baBookingParcelTO = (BABookingParcelTO) setUpBookingTOs(
									baBookingTO, rowCount);
							baBookingParcelTO.setBizAssociateId(baBookingTO
									.getBizAssociateId());
							baBookingParcelTO.getCnPricingDtls().setRateType(
									RateCommonConstants.RATE_TYPE_BA);

							if (baBookingTO.getPriorityServicedOns() != null
									&& baBookingTO.getPriorityServicedOns().length > rowCount
									&& baBookingTO.getPriorityServicedOns()[rowCount] != null)
								baBookingParcelTO
										.setPriorityServiced(baBookingTO
												.getPriorityServicedOns()[rowCount]);

							if (baBookingTO.getRefNos() != null
									&& baBookingTO.getRefNos().length > 0
									&& baBookingTO.getRefNos()[rowCount] != null)
								baBookingParcelTO.setRefNo(baBookingTO
										.getRefNos()[rowCount]);
							if (baBookingTO.getChargeableWeights() != null
									&& baBookingTO.getChargeableWeights().length > 0
									&& baBookingTO.getChargeableWeights()[rowCount] != 0)
								baBookingParcelTO.setFinalWeight(baBookingTO
										.getChargeableWeights()[rowCount]);
							// Child Consignments
							if (StringUtils.isNotEmpty(baBookingTO
									.getChildCNDetails()[rowCount]))
								baBookingParcelTO.setChildCNsDtls(baBookingTO
										.getChildCNDetails()[rowCount]);
							// Setting content
							if (baBookingTO.getCnContentIds() != null
									&& baBookingTO.getCnContentIds().length > 0
									&& baBookingTO.getCnContentIds()[rowCount] != 0)
								baBookingParcelTO.setCnContentId(baBookingTO
										.getCnContentIds()[rowCount]);

							if (baBookingTO.getOtherCNContents() != null
									&& baBookingTO.getOtherCNContents().length > 0
									&& StringUtils.isNotEmpty(baBookingTO
											.getOtherCNContents()[rowCount]))
								baBookingParcelTO.setOtherCNContent(baBookingTO
										.getOtherCNContents()[rowCount]);
							// Settings Paperworks
							if (baBookingTO.getCnPaperWorkIds() != null
									&& baBookingTO.getCnPaperWorkIds().length > 0
									&& baBookingTO.getCnPaperWorkIds()[rowCount] != 0)
								baBookingParcelTO.setCnPaperworkId(baBookingTO
										.getCnPaperWorkIds()[rowCount]);
							if (baBookingTO.getPaperRefNum() != null
									&& baBookingTO.getPaperRefNum().length > 0
									&& baBookingTO.getPaperRefNum()[rowCount] != null)
								baBookingParcelTO.setPaperWorkRefNo(baBookingTO
										.getPaperRefNum()[rowCount]);
							// Setting volweight
							if (baBookingTO.getLengths() != null
									&& baBookingTO.getLengths().length > 0
									&& baBookingTO.getLengths()[rowCount] != 0)
								baBookingParcelTO.setLength(baBookingTO
										.getLengths()[rowCount]);
							if (baBookingTO.getHeights() != null
									&& baBookingTO.getHeights().length > 0
									&& baBookingTO.getHeights()[rowCount] != 0)
								baBookingParcelTO.setHeight(baBookingTO
										.getHeights()[rowCount]);
							if (baBookingTO.getBreaths() != null
									&& baBookingTO.getBreaths().length > 0
									&& baBookingTO.getBreaths()[rowCount] != 0)
								baBookingParcelTO.setBreath(baBookingTO
										.getBreaths()[rowCount]);
							// Insurence details
							if (baBookingTO.getInsuaranceNos() != null
									&& baBookingTO.getInsuaranceNos().length > 0
									&& baBookingTO.getInsuaranceNos()[rowCount] != 0)
								baBookingParcelTO.setInsuredById(baBookingTO
										.getInsuaranceNos()[rowCount]);
							if (baBookingTO.getPolicyNos() != null
									&& baBookingTO.getPolicyNos().length > 0
									&& baBookingTO.getPolicyNos()[rowCount] != null)
								baBookingParcelTO.setPolicyNo(baBookingTO
										.getPolicyNos()[rowCount]);
							if (baBookingTO.getDlvTimeMapIds() != null
									&& baBookingTO.getDlvTimeMapIds().length > 0
									&& baBookingTO.getDlvTimeMapIds()[rowCount] > 0)
								baBookingParcelTO.setDlvTimeMapId(baBookingTO
										.getDlvTimeMapIds()[rowCount]);

							if (baBookingTO.getCodAmts() != null
									&& baBookingTO.getCodAmts().length > rowCount
									&& baBookingTO.getCodAmts()[rowCount] != 0)
								baBookingParcelTO.setCodAmount(baBookingTO
										.getCodAmts()[rowCount]);

							if (baBookingTO.getBaAmts() != null
									&& baBookingTO.getBaAmts().length > 0
									&& baBookingTO.getBaAmts()[rowCount] != 0)
								baBookingParcelTO.setBaAmt(baBookingTO
										.getBaAmts()[rowCount]);

							// Settng Customer address
							if (StringUtil.isNull(baBookingParcelTO
									.getConsignor())) {
								setupConsigneeAddress(bookingCommonService,
										baBookingParcelTO);
							}
							baBookingParcelTO.setCreatedBy(baBookingTO.getCreatedBy());
							baBookingParcelTO.setUpdatedBy(baBookingTO.getCreatedBy());
							baBookingParcelTOs.add(baBookingParcelTO);
						} catch (Exception e) {
							LOGGER.error(
									"Exception in :: BABookingConverter :: baBookingDomainConverter() : Failure for Consingment "
											+ consgNumber, e);
							failureConsignments.add(consgNumber);

						}
					}
					bookingWrapperTO.setSucessConsignments(baBookingParcelTOs);
					if (!StringUtil.isEmptyColletion(failureConsignments)
							&& failureConsignments.size() > 0) {
						bookingWrapperTO
								.setFailureConsignments(failureConsignments);
					}
				}
			}
		}
		LOGGER.debug("BABookingConverter::baBookingParcelConverter::END------------>:::::::");
		return bookingWrapperTO;
	}

	private static void setupConsigneeAddress(
			BookingCommonService bookingCommonService,
			BABookingParcelTO baBookingParcelTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BABookingConverter::setupConsigneeAddress::START------------>:::::::");
		CustomerTO customer = bookingCommonService.getCustomerByIdOrCode(
				baBookingParcelTO.getBizAssociateId(),
				CommonConstants.EMPTY_STRING);
		if (!StringUtil.isNull(customer)) {
			PickupDeliveryAddressTO address = customer.getAddress();
			if (!StringUtil.isNull(address)) {
				ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
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
				baBookingParcelTO.setConsignor(consignor);
			}
		}
		LOGGER.debug("BABookingConverter::setupConsigneeAddress::END------------>:::::::");
	}
}
