/**
 * 
 */
package com.ff.web.booking.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.EmotionalBondBookingTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.booking.BookingPreferenceMappingDO;

/**
 * The Class EmotionalBondBookingConvertor.
 *
 * @author uchauhan
 */
public class EmotionalBondBookingConvertor extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashBookingConverter.class);

	/**
	 * Emotional bond booking domain converter.
	 *
	 * @param emotionalBondBookingTO the emotional bond booking to
	 * @return the booking do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static BookingDO emotionalBondBookingDomainConverter(
			EmotionalBondBookingTO emotionalBondBookingTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("EmotionalBondBookingConvertor::emotionalBondBookingDomainConverter::START------------>:::::::");
		BookingDO booking = null;
		try {
			if (emotionalBondBookingTO != null) {
				// Calling base converter
				booking = convert(emotionalBondBookingTO);
				if (!StringUtil.isStringEmpty(emotionalBondBookingTO.getDelvDateTime())) {
					Date delvDateTime = DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(emotionalBondBookingTO.getDelvDateTime());
					booking.setDeliveryDate(delvDateTime);
				}
				// Seeting preferrences
				List<Integer> preferrencesIds = 
						StringUtil.parseIntegerList(emotionalBondBookingTO.getPreferenceIds(), CommonConstants.COMMA);
				Set<BookingPreferenceMappingDO> prefDtls = new HashSet<BookingPreferenceMappingDO>(preferrencesIds.size());
				for (Integer preferrencesId : preferrencesIds) {
					BookingPreferenceMappingDO prefMappingDO = new BookingPreferenceMappingDO();
					prefMappingDO.setSplInstructions(emotionalBondBookingTO.getInstruction());
					prefMappingDO.setOtherPref(emotionalBondBookingTO.getOtherPref());
					prefMappingDO.setCreatedDate(new Date());
					prefMappingDO.setCreatedBy(emotionalBondBookingTO.getCreatedBy());
					prefMappingDO.setUpdatedBy(emotionalBondBookingTO.getUpdatedBy());
					BookingPreferenceDetailsDO prefDO = new BookingPreferenceDetailsDO();
					prefDO.setPreferenceId(preferrencesId);
					prefDO.setCreatedDate(new Date());
					prefMappingDO.setCreatedDate(new Date());
					prefDO.setCreatedBy(emotionalBondBookingTO.getCreatedBy());
					prefDO.setUpdatedBy(emotionalBondBookingTO.getUpdatedBy());
					prefMappingDO.setCreatedBy(emotionalBondBookingTO.getCreatedBy());
					prefMappingDO.setUpdatedBy(emotionalBondBookingTO.getUpdatedBy());
					prefMappingDO.setReferenceId(prefDO);
					prefDtls.add(prefMappingDO);
				}
				booking.setBokingPrefs(prefDtls);
				booking.setActualWeight(0.0);
				booking.setFianlWeight(0.0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in EmotionalBondBookingConvertor() :" + e.getMessage());
		}
		LOGGER.debug("EmotionalBondBookingConvertor::emotionalBondBookingDomainConverter::END------------>:::::::");
		return booking;
	}

	/**
	 * Creates the domain converter.
	 *
	 * @param emotionalBondBookingTO the emotional bond booking to
	 * @return  list of BookingDO
	 */
	public static List<BookingDO> createDomainConverter(
			EmotionalBondBookingTO emotionalBondBookingTO) {
		LOGGER.debug("EmotionalBondBookingConvertor::emotionalBondBookingDomainConverter::START------------>:::::::");
		BookingDO bookingDO = null;
		List<BookingDO> bookingDOs = null;
		if (!StringUtil.isNull(emotionalBondBookingTO)) {
			
			int consgLen=emotionalBondBookingTO.getIsChecked().length;
			
			bookingDOs = new ArrayList<BookingDO>(consgLen);
			
			for (int count = 0; count < consgLen; count++) {
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						emotionalBondBookingTO.getIsChecked()[count])) {
					bookingDO = new BookingDO();
					bookingDO.setBookingId(emotionalBondBookingTO
							.getBookingIds()[count]);
					bookingDO
							.setStatus(emotionalBondBookingTO.getStatus()[count]);
					bookingDOs.add(bookingDO);
				}
			}
		}
		LOGGER.debug("EmotionalBondBookingConvertor::emotionalBondBookingDomainConverter::END------------>:::::::");
		return bookingDOs;
	}
}
