package com.ff.web.booking.converter;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.FOCBookingDoxTO;
import com.ff.booking.FOCBookingParcelTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class FOCBookingConverter.
 */
public class FOCBookingConverter extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingConverter.class);

	/**
	 * Foc booking dox domain converter.
	 * 
	 * @param focBookingDoxTO
	 *            the foc booking dox to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingDO focBookingDoxDomainConverter(
			FOCBookingDoxTO focBookingDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("FOCBookingConverter::focBookingDoxDomainConverter::START------------>:::::::");
		BookingDO booking = null;
		try {
			if (focBookingDoxTO != null) {
				// Calling base converter
				booking = convert(focBookingDoxTO);
				// Setting consigee and consignor
				ConsigneeConsignorDO consignee = BookingUtils
						.setUpConsigneeConsignorDetails(focBookingDoxTO
								.getConsignee());
				if (consignee != null) {
					booking.setConsigneeId(consignee);
				}
				ConsigneeConsignorDO consignor = BookingUtils
						.setUpConsigneeConsignorDetails(focBookingDoxTO
								.getConsignor());
				if (consignor != null) {
					booking.setConsignorId(consignor);
				}
				if (!StringUtil.isEmptyInteger(focBookingDoxTO.getApproverId()))
					booking.setApprovedBy(focBookingDoxTO.getApproverId());

			}
		} catch (Exception e) {
			LOGGER.debug("Exception in FOCBookingConverter :: cashBookingDomainConverter() :"
					+ e.getMessage());
		}
		LOGGER.debug("FOCBookingConverter::focBookingDoxDomainConverter::END------------>:::::::");
		return booking;
	}

	/**
	 * Foc booking parcel domain converter.
	 * 
	 * @param focBookingParcelTO
	 *            the foc booking parcel to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public static BookingDO focBookingParcelDomainConverter(
			FOCBookingParcelTO focBookingParcelTO) throws CGBusinessException,
			CGBusinessException {
		LOGGER.debug("FOCBookingConverter::focBookingParcelDomainConverter::START------------>:::::::");
		BookingDO booking = new BookingDO();
		Integer consgTypeId = 0;
		try {
			if (focBookingParcelTO != null) {
				// Calling base converter
				if (StringUtils.isNotEmpty(focBookingParcelTO
						.getConsgTypeName())) {
					consgTypeId = Integer.parseInt(focBookingParcelTO
							.getConsgTypeName().split("#")[0]);
					focBookingParcelTO.setConsgTypeId(consgTypeId);
				}
				booking = convert(focBookingParcelTO);
				booking.setNoOfPieces(focBookingParcelTO.getNoOfPieces());
				booking.setPrice(focBookingParcelTO.getPrice());

				if (!StringUtil.isEmptyInteger(focBookingParcelTO
						.getCnContents().getCnContentId())) {
					CNContentDO cnContent = new CNContentDO();
					cnContent.setCnContentId(focBookingParcelTO.getCnContents()
							.getCnContentId());
					booking.setCnContentId(cnContent);
					if (!StringUtil.isNull(focBookingParcelTO
							.getOtherCNContent())) {
						booking.setOtherCNContent(focBookingParcelTO
								.getOtherCNContent());
					} else {
						booking.setOtherCNContent(null);
					}
				}
				if (!StringUtil.isEmptyInteger(focBookingParcelTO
						.getCnPaperWorks().getCnPaperWorkId())) {
					CNPaperWorksDO cnPaperWork = new CNPaperWorksDO();
					cnPaperWork.setCnPaperWorkId(focBookingParcelTO
							.getCnPaperWorks().getCnPaperWorkId());
					booking.setCnPaperWorkId(cnPaperWork);
					booking.setPaperWorkRefNo(focBookingParcelTO
							.getPaperWorkRefNo());
				}
				if (focBookingParcelTO.getLength() != null
						&& focBookingParcelTO.getLength() > 0)
					booking.setLength(focBookingParcelTO.getLength());
				if (focBookingParcelTO.getHeight() != null
						&& focBookingParcelTO.getHeight() > 0)
					booking.setHeight(focBookingParcelTO.getHeight());
				if (focBookingParcelTO.getBreath() != null
						&& focBookingParcelTO.getBreath() > 0)
					booking.setBreath(focBookingParcelTO.getBreath());
				if (focBookingParcelTO.getVolWeight() != null
						&& focBookingParcelTO.getVolWeight() > 0) {
					booking.setVolWeight(focBookingParcelTO.getVolWeight());
				}

				// Setting consigee and consignor
				ConsigneeConsignorDO consignee = BookingUtils
						.setUpConsigneeConsignorDetails(focBookingParcelTO
								.getConsignee());
				if (consignee != null) {
					booking.setConsigneeId(consignee);
				}
				ConsigneeConsignorDO consignor = BookingUtils
						.setUpConsigneeConsignorDetails(focBookingParcelTO
								.getConsignor());
				if (consignor != null) {
					booking.setConsignorId(consignor);
				}

				if (!StringUtil.isEmptyInteger(focBookingParcelTO
						.getDlvTimeMapId()))
					booking.setPincodeDlvTimeMapId(focBookingParcelTO
							.getDlvTimeMapId());
				if (!StringUtil.isEmptyInteger(focBookingParcelTO
						.getApproverId()))
					booking.setApprovedBy(focBookingParcelTO.getApproverId());
				// Setting Declared value
				if (!StringUtil.isEmptyDouble(focBookingParcelTO
						.getDeclaredValue()))
					booking.setDeclaredValue(focBookingParcelTO
							.getDeclaredValue());

			}
		} catch (Exception e) {
			LOGGER.debug("Exception in FOCBookingConverter :: FOCBookingParcelDomainConverter() :"
					+ e.getMessage());
		}
		LOGGER.debug("FOCBookingConverter::focBookingParcelDomainConverter::END------------>:::::::");
		return booking;
	}
}
