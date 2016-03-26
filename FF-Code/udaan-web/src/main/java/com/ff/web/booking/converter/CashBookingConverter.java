package com.ff.web.booking.converter;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPaymentDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class CashBookingConverter.
 */
public class CashBookingConverter extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingConverter.class);

	/**
	 * Cash booking dox domain converter.
	 * 
	 * @param cashBookingTO
	 *            the cash booking to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingDO cashBookingDoxDomainConverter(
			CashBookingDoxTO cashBookingTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CashBookingServiceImpl::cashBookingDoxDomainConverter::START------------>:::::::");
		BookingDO booking = null;
		try {
			if (cashBookingTO != null) {
				// Calling base converter
				booking = convert(cashBookingTO);
				booking.setActualWeight(cashBookingTO.getFinalWeight());
				if (!StringUtil.isEmptyInteger(cashBookingTO.getApproverId()))
					booking.setApprovedBy(cashBookingTO.getApproverId());
			
				// Booking payment details
				if (cashBookingTO.getBookingPayment() != null) {
					BookingPaymentDO bookingPayment = BookingUtils
							.setUpPaymentDetails(cashBookingTO
									.getBookingPayment());
					bookingPayment.setCreatedBy(cashBookingTO.getCreatedBy());
					bookingPayment.setUpdatedBy(cashBookingTO.getUpdatedBy());
					bookingPayment.setCreatedDate(new Date());
					booking.setBookingPayment(bookingPayment);
				}
				booking.setWeightCapturedMode(cashBookingTO
						.getWeightCapturedMode());
				if (!StringUtil.isEmptyInteger(cashBookingTO.getDlvTimeMapId()))
					booking.setPincodeDlvTimeMapId(cashBookingTO
							.getDlvTimeMapId());
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::cashBookingDoxDomainConverter() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashBookingServiceImpl::cashBookingDoxDomainConverter::END------------>:::::::");
		return booking;
	}

	/**
	 * Cash booking parcel domain converter.
	 * 
	 * @param cashBookingTO
	 *            the cash booking to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public static BookingDO cashBookingParcelDomainConverter(
			CashBookingParcelTO cashBookingTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CashBookingServiceImpl::cashBookingParcelDomainConverter::START------------>:::::::");
		BookingDO booking = new BookingDO();
		Integer consgTypeId = 0;
		try {
			if (cashBookingTO != null) {
				// Calling base converter
				if (StringUtils.isNotEmpty(cashBookingTO.getConsgTypeName())) {
					consgTypeId = Integer.parseInt(cashBookingTO
							.getConsgTypeName().split("#")[0]);
					cashBookingTO.setConsgTypeId(consgTypeId);
				}
				booking = convert(cashBookingTO);
				booking.setNoOfPieces(cashBookingTO.getNoOfPieces());
				booking.setPrice(cashBookingTO.getPrice());
				if (!StringUtil.isEmptyInteger(cashBookingTO.getApproverId()))
					booking.setApprovedBy(cashBookingTO.getApproverId());
				
				if (!StringUtil.isEmptyInteger(cashBookingTO.getCnContents()
						.getCnContentId())) {
					CNContentDO cnContent = new CNContentDO();
					cnContent.setCnContentId(cashBookingTO.getCnContents()
							.getCnContentId());
					booking.setCnContentId(cnContent);
					if (!StringUtil.isNull(cashBookingTO.getOtherCNContent())) {
						booking.setOtherCNContent(cashBookingTO.getOtherCNContent());
						cashBookingTO.setOtherCNContent(cashBookingTO
								.getOtherCNContent());
					} else {
						booking.setOtherCNContent(null);
						cashBookingTO.setOtherCNContent(null);
					}
				}

				
				if (!StringUtil.isEmptyDouble(cashBookingTO.getCnPricingDtls().getDeclaredvalue())) {
					booking.setDeclaredValue(cashBookingTO.getCnPricingDtls().getDeclaredvalue());
				}
				if (!StringUtil.isEmptyInteger(cashBookingTO.getCnPaperWorks()
						.getCnPaperWorkId())) {
					CNPaperWorksDO cnPaperWork = new CNPaperWorksDO();
					cnPaperWork.setCnPaperWorkId(cashBookingTO
							.getCnPaperWorks().getCnPaperWorkId());
					booking.setCnPaperWorkId(cnPaperWork);
					booking.setPaperWorkRefNo(cashBookingTO.getPaperWorkRefNo());
					cashBookingTO.setCnPaperworkId(cashBookingTO
							.getCnPaperWorks().getCnPaperWorkId());
				}
				if (cashBookingTO.getLength() != null
						&& cashBookingTO.getLength() > 0)
					booking.setLength(cashBookingTO.getLength());
				if (cashBookingTO.getHeight() != null
						&& cashBookingTO.getHeight() > 0)
					booking.setHeight(cashBookingTO.getHeight());
				if (cashBookingTO.getBreath() != null
						&& cashBookingTO.getBreath() > 0)
					booking.setBreath(cashBookingTO.getBreath());
				if (cashBookingTO.getVolWeight() != null
						&& cashBookingTO.getVolWeight() > 0) {
					booking.setVolWeight(cashBookingTO.getVolWeight());
				}

				// Setting consigee and consignor
				/*ConsigneeConsignorDO consignee = BookingUtils
						.setUpConsigneeConsignorDetails(cashBookingTO
								.getConsignee());
				if (consignee != null) {
					booking.setConsigneeId(consignee);
				}
				ConsigneeConsignorDO consignor = BookingUtils
						.setUpConsigneeConsignorDetails(cashBookingTO
								.getConsignor());
				if (consignor != null) {
					booking.setConsignorId(consignor);
				}*/
				// Booking payment details
				if (cashBookingTO.getBookingPayment() != null) {
					BookingPaymentDO bookingPayment = BookingUtils
							.setUpPaymentDetails(cashBookingTO
									.getBookingPayment());
					bookingPayment.setCreatedBy(cashBookingTO.getCreatedBy());
					bookingPayment.setUpdatedBy(cashBookingTO.getUpdatedBy());
					bookingPayment.setCreatedDate(new Date());
					booking.setBookingPayment(bookingPayment);

				}
				booking.setWeightCapturedMode(cashBookingTO
						.getWeightCapturedMode());
				if (!StringUtil.isEmptyInteger(cashBookingTO.getDlvTimeMapId()))
					booking.setPincodeDlvTimeMapId(cashBookingTO
							.getDlvTimeMapId());
				// insurence details
				if (!StringUtil.isEmptyInteger(cashBookingTO.getInsuredById())) {
					InsuredByDO insuredBy = new InsuredByDO();
					insuredBy.setInsuredById(cashBookingTO.getInsuredById());
					booking.setInsuredBy(insuredBy);
					booking.setInsurencePolicyNo(cashBookingTO.getPolicyNo());
				}

			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingServiceImpl::cashBookingParcelDomainConverter() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("CashBookingServiceImpl::cashBookingParcelDomainConverter::END------------>:::::::");
		return booking;
	}
}
