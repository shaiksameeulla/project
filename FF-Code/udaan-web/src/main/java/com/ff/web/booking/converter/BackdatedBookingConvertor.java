/**
 * 
 */
package com.ff.web.booking.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BackdatedBookingTO;
import com.ff.booking.BookingValidationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingExcelConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class BackdatedBookingConvertor.
 * 
 * @author uchauhan
 */
public class BackdatedBookingConvertor extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BackdatedBookingConvertor.class);

	/**
	 * Backdated booking to convertor.
	 * 
	 * @param bookingDetails
	 *            the booking details
	 * @param backdatedBooking
	 *            the backdated booking
	 * @param bookingCommonService
	 * @param cnValidation
	 * @return the list
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static List<Object> backdatedBookingTOConvertor(
			List<List> bookingDetails, BackdatedBookingTO backdatedBooking,
			BookingCommonService bookingCommonService,
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BackdatedBookingAction::backdatedBookingTOConvertor::START------------>:::::::");
		// Contructing TO from Excel
		List<BookingDO> bookingDOs = new ArrayList<>(bookingDetails.size());
		List<ConsignmentDO> consignmentDOs = new ArrayList<>(
				bookingDetails.size());
		BookingDO booking = null;
		ConsignmentDO consgDO = null;
		List<Object> list = new ArrayList<>();

		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();

		List<BookingTypeDO> bookingTypeDO = bookingCommonService
				.getAllBookingType();

		ConsignmentTypeDO consgType = null;
		CNContentDO cnContentDO = null;
		CNPaperWorksDO paperWorksDO = null;
		ProductDO productDO = null;
		Integer createdBy=backdatedBooking.getCreatedBy();
		Date date=new Date();
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		process = bookingCommonService.getProcess(process);
		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(process.getProcessId());
		String consgSeries = "";

		for (List<String> backdatedTOlst : bookingDetails) {

			booking = new BookingDO();
			consgDO = new ConsignmentDO();
			consgType = new ConsignmentTypeDO();
			cnContentDO = new CNContentDO();
			paperWorksDO = new CNPaperWorksDO();
			double VolWt = 0;
			double actualWt = 0;
			double decVal = 0;
			double codAmt = 0;

			if (backdatedTOlst.get(0).equalsIgnoreCase(
					BookingExcelConstants.CN_NO))
				continue;

			try {
				Date bookingDate = DateUtil.combineDateWithTimeHHMM(
						backdatedBooking.getBackDate(),
						backdatedBooking.getBackTime());
				booking.setBookingDate(bookingDate);
			} catch (Exception e) {
				LOGGER.error("ERROR :BackDatedBookingConverter.bookingDate()",
						e);
			}
			booking.setCreatedDate(date);
			consgDO.setCreatedDate(date);
			booking.setUpdatedBy(createdBy);
			consgDO.setCreatedBy(createdBy);
			booking.setCreatedBy(createdBy);
			consgDO.setUpdatedBy(createdBy);
			consgDO.setEventDate(date);

			booking.setOriginCityId(backdatedBooking.getOrigniCityId());
			boolean typeExist = Boolean.FALSE;
			if (!StringUtil.isStringEmpty(backdatedTOlst.get(3))) {
				for (BookingTypeDO typeDO : bookingTypeDO) {
					if (backdatedTOlst.get(3).trim()
							.equals(typeDO.getBookingType())) {
						booking.setBookingType(typeDO);
						typeExist = Boolean.TRUE;
						break;
					}
					if (!typeExist) {
						BookingTypeDO btypeDO = new BookingTypeDO();
						btypeDO.setBookingType(backdatedTOlst.get(3).trim());
						booking.setBookingType(btypeDO);
					}
				}
			}
			booking.setBookingOfficeId(cnValidation.getBookingOfficeId());
			PincodeDO pin = new PincodeDO();
			if (!StringUtil.isStringEmpty(backdatedTOlst.get(5))) {
				pin.setPincode(backdatedTOlst.get(5).trim());
				booking.setPincodeId(pin);
			}

			if (!StringUtil.isEmptyInteger(process.getProcessId())) {
				booking.setUpdatedProcess(processDO);
			}

			//booking.setProcessNumber(backdatedBooking.getProcessNumber());
			boolean conExist = Boolean.FALSE;
			if (!StringUtil.isStringEmpty(backdatedTOlst.get(4))) {
				for (ConsignmentTypeTO typeTO : consgTypes) {
					if (backdatedTOlst.get(4).trim()
							.equals(typeTO.getConsignmentCode())) {
						consgType.setConsignmentId(typeTO.getConsignmentId());
						consgType.setConsignmentCode(typeTO
								.getConsignmentCode());
						booking.setConsgTypeId(consgType);
						conExist = Boolean.TRUE;
						break;
					}
				}
				if (!conExist) {
					consgType.setConsignmentCode(backdatedTOlst.get(4).trim());
					booking.setConsgTypeId(consgType);
				}
			}
			if (StringUtils.isNotEmpty(backdatedTOlst.get(2)))
				booking.setRefNo(backdatedTOlst.get(2).trim());

			String actualWtStr = backdatedTOlst.get(6);
			String regex = "[0-9]+";
			try {

				String[] parts = actualWtStr.split("\\.");
				if ((BigDecimal.valueOf(actualWt).toPlainString().length() > 8)
						|| parts.length > 2
						|| parts[0].length() > 4
						|| !parts[0].matches(regex)
						|| (parts.length < 2 ? false
								: (parts[1].length() > 3 || !parts[1]
										.matches(regex)))) {
					actualWt = -1;
				} else {
					actualWt = Double.parseDouble(actualWtStr);
				}
			} catch (NumberFormatException e) {
				LOGGER.error(
						"ERROR : BackDatedBookingConverter.convertWeight()", e);
			} finally {
				booking.setActualWeight(actualWt);
				booking.setFianlWeight(actualWt);
				booking.setActWtStr(actualWtStr);
			}

			String insuredBy = backdatedTOlst.get(17);
			InsuredByTO insuredByTO = new InsuredByTO();
			InsuredByDO insuredByDO = new InsuredByDO();
			String policyNo = backdatedTOlst.get(18);

			if (StringUtils.isNotEmpty(backdatedTOlst.get(4))
					&& StringUtils.equalsIgnoreCase(
							CommonConstants.CONSIGNMENT_TYPE_PARCEL,
							backdatedTOlst.get(4).trim())) {
				String decValStr = backdatedTOlst.get(16);

				try {
					if (!StringUtil.isStringEmpty(decValStr)
							&& decValStr.length() > 10) {
						decVal = -1;// For invalid declared value passed from
									// excell
					} else {
						decVal = Double.parseDouble(decValStr);
					}

				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BackDatedBookingConverter.decValue()", e);
				} finally {
					booking.setDeclaredValue(decVal);
					booking.setDecValStr(decValStr);
				}

				String volWtStr = backdatedTOlst.get(7);
				try {
					VolWt = Double.parseDouble(volWtStr);
				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BackDatedBookingConverter.convertWeight()",
							e);
				} finally {
					booking.setVolWeight(VolWt);
					booking.setVolWtStr(volWtStr);
				}

				try {
					if (!StringUtil.isEmptyDouble(booking.getActualWeight())
							&& !StringUtil
									.isEmptyDouble(booking.getVolWeight())) {
						if (booking.getVolWeight() > booking.getActualWeight()) {
							booking.setFianlWeight(booking.getVolWeight());
						} else {
							if (!StringUtil.isEmptyDouble(booking
									.getActualWeight())) {
								booking.setFianlWeight(booking
										.getActualWeight());
							}
						}
					} else {
						if (!StringUtil
								.isEmptyDouble(booking.getActualWeight())) {
							booking.setFianlWeight(booking.getActualWeight());
						}
					}

				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BackDatedBookingConverter.decValue()", e);
				}

				if (StringUtils.isNotEmpty(backdatedTOlst.get(15))) {
					cnContentDO.setCnContentName(backdatedTOlst.get(15).trim());
				}
				booking.setCnContentId(cnContentDO);

				if (StringUtils.isNotEmpty(backdatedTOlst.get(19))) {
					paperWorksDO.setCnPaperWorkName(backdatedTOlst.get(19)
							.trim());
					booking.setCnPaperWorkId(paperWorksDO);
				}
				if (StringUtils.isNotEmpty(backdatedTOlst.get(20))) {
					booking.setPaperWorkRefNo(backdatedTOlst.get(20).trim());
				}

				if (StringUtils.isNotEmpty(insuredBy)) {
					String insuredByCode = null;
					if (insuredBy.trim().equalsIgnoreCase("first flight")) {
						insuredByCode = "F";
						insuredByTO = bookingCommonService
								.getInsuredByNameOrCode(null, insuredByCode,
										null);

					} else if (insuredBy.trim().equalsIgnoreCase("consigner")) {
						insuredByCode = "C";

						insuredByTO = bookingCommonService
								.getInsuredByNameOrCode(null, insuredByCode,
										null);

					} else {
						insuredByTO.setInsuredByDesc(insuredBy.trim());
					}
					if (!StringUtil.isNull(insuredByTO)) {
						insuredByDO
								.setInsuredById(insuredByTO.getInsuredById());
						insuredByDO.setInsuredByCode(insuredByTO
								.getInsuredByCode());
						insuredByDO.setInsuredByDesc(insuredByTO
								.getInsuredByDesc());
						booking.setInsuredBy(insuredByDO);
					}

				}

				if (StringUtils.isNotEmpty(policyNo)) {
					booking.setInsurencePolicyNo(policyNo.trim());
				}

			}

			booking.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
			booking.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);
			if (StringUtils.isNotEmpty(backdatedTOlst.get(0).trim()))
				booking.setConsgNumber(backdatedTOlst.get(0).trim()
						.toUpperCase());

			if (StringUtils.isNotEmpty(booking.getConsgNumber()))
				consgSeries = booking.getConsgNumber().substring(4, 5);

			CustomerDO custDO = new CustomerDO();
			if (StringUtils.isNotEmpty(backdatedTOlst.get(1))) {
				custDO.setCustomerCode(backdatedTOlst.get(1).trim());
				booking.setCustomerId(custDO);
			}
			if (StringUtils.isNotEmpty(backdatedTOlst.get(22))) {
				booking.setDlvTime(backdatedTOlst.get(22).trim());
				//Priority Booking Change starts
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.PRODUCT_SERIES_PRIORITY, booking
								.getConsgNumber().substring(4, 5))) {
					if(booking.getDlvTime().startsWith("B", 0)){
						consgDO.setServicedOn("B");
					}else if(booking.getDlvTime().startsWith("A", 0)){
						consgDO.setServicedOn("A");
					}else{
						consgDO.setServicedOn("S");
					}
				}
				//Priority Booking Change ends
			}

			LOGGER.debug("BulkBookingConverter::consgDO::START------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			// Setting Billing flags
			consgDO.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
			consgDO.setChangedAfterBillingWtDest(CommonConstants.NO);
			consgDO.setChangedAfterNewRateCmpnt(CommonConstants.NO); 

			consgDO.setOrgOffId(cnValidation.getBookingOfficeId());
			consgDO.setOperatingOffice(cnValidation.getBookingOfficeId());

			if (StringUtils.isNotEmpty(backdatedTOlst.get(0)))
				consgDO.setConsgNo(backdatedTOlst.get(0).trim().toUpperCase());

			if (!StringUtil.isNull(processDO)) {
				consgDO.setUpdatedProcess(processDO);
			}
			if (!StringUtil.isNull(consgType)) {
				consgDO.setConsgType(consgType);
			}
			if (StringUtils.isNotEmpty(backdatedTOlst.get(2)))
				consgDO.setRefNo(backdatedTOlst.get(2).trim());

			if (StringUtil.isDigit(consgSeries.charAt(0))) {
				consgSeries = "N";
			}
			List<ProductTO> prodList = cnValidation.getProductTOList();
			productDO = new ProductDO();
			for (ProductTO prod : prodList) {
				if (prod.getConsgSeries().equalsIgnoreCase(consgSeries)) {
					productDO.setProductCode(prod.getProductCode());
					productDO.setConsgSeries(prod.getConsgSeries());
					consgDO.setProductId(prod.getProductId());
					consgDO.setProductDO(productDO);
					break;
				}

			}

			if (StringUtils.isNotEmpty(backdatedTOlst.get(4))
					&& StringUtils.equalsIgnoreCase(
							CommonConstants.CONSIGNMENT_TYPE_PARCEL,
							backdatedTOlst.get(4))) {

				if (StringUtils.isNotEmpty(insuredBy)) {
					consgDO.setInsuredBy(insuredByDO);
				}
				if (StringUtils.isNotEmpty(policyNo)) {
					consgDO.setInsurencePolicyNo(policyNo.trim());
				}

				consgDO.setDeclaredValue(decVal);

				consgDO.setCnContentId(cnContentDO);

				if (StringUtils.isNotEmpty(backdatedTOlst.get(19))) {
					paperWorksDO.setCnPaperWorkName(backdatedTOlst.get(19)
							.trim());
					consgDO.setCnPaperWorkId(paperWorksDO);
				}
				if (StringUtils.isNotEmpty(backdatedTOlst.get(20))) {
					consgDO.setPaperWorkRefNo(backdatedTOlst.get(20).trim());
				}

				if (!StringUtil.isEmptyDouble(booking.getVolWeight()))
					consgDO.setVolWeight(booking.getVolWeight());

			}

			if (!StringUtil.isEmptyDouble(booking.getFianlWeight()))
				consgDO.setFinalWeight(booking.getFianlWeight());
			if (!StringUtil.isEmptyDouble(booking.getActualWeight()))
				consgDO.setActualWeight(booking.getActualWeight());

			if (!StringUtil.isNull(pin)) {
				consgDO.setDestPincodeId(pin);
			}

			double allowedMaxValue = 999999;
			String codAmtStr = backdatedTOlst.get(21);
			if (StringUtils.isNotEmpty(codAmtStr)) {
				codAmt = -1;
				try {
					codAmt = Double.parseDouble(codAmtStr);
					codAmtStr = BigDecimal.valueOf(codAmt).toPlainString();
					if (Double.compare(codAmt, allowedMaxValue) > 0) {
						codAmt = -1;
					}
				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BulkBookingConverter.CodAmtConvert()", e);
				} finally {
					consgDO.setCodAmt(codAmt);
					consgDO.setCodAmtStr(codAmtStr);
				}
			}

			String lcAmtStr = backdatedTOlst.get(23);
			if (StringUtils.isNotEmpty(lcAmtStr)) {
				double lcAmt = -1;
				try {
					lcAmt = Double.parseDouble(lcAmtStr);
					lcAmtStr = BigDecimal.valueOf(lcAmt).toPlainString();
					if (Double.compare(lcAmt, allowedMaxValue) > 0) {
						lcAmt = -1;
					}
				} catch (NumberFormatException e) {
					LOGGER.error("ERROR : BulkBookingConverter.LCAmtConvert()",
							e);
				} finally {
					consgDO.setLcAmount(lcAmt);
					consgDO.setLcAmtStr(lcAmtStr);
				}

			}
			consgDO.setLcBankName(backdatedTOlst.get(24));

			if (!StringUtil.isEmptyInteger(consgDO.getConsgId())) {
				consgDO.setDtUpdateToCentral(CommonConstants.YES);
				consgDO.setDtToCentral(CommonConstants.NO);
			} else {
				consgDO.setDtToCentral(CommonConstants.NO);
			}
			consgDO.setConsgStatus("B");

			// Setting Consignee
			setUpBackdatedConsignee(backdatedTOlst, consgDO);
			if (!StringUtil.isNull(consgDO.getConsignee()))
				booking.setConsigneeId(consgDO.getConsignee());
			// Setting Alternate Consignee
			setUpBackdatedConsignor(backdatedTOlst, consgDO);
			if (!StringUtil.isNull(consgDO.getConsignor()))
				booking.setConsignorId(consgDO.getConsignor());

			ConsignmentTO consgTO = new ConsignmentTO();
			consgTO.setOrgOffId(backdatedBooking.getBookingOfficeId());
			OfficeTO loggedInOffice = new OfficeTO();
			loggedInOffice.setOfficeId(backdatedBooking.getBookingOfficeId());
			/*Integer consgOpLevel = bookingCommonService.getConsgOperatingLevel(
					consgTO, loggedInOffice);
			consgDO.setOperatingLevel(consgOpLevel);*/

			if (!StringUtil.isNull(consgDO.getConsignee())) {
				consgDO.setMobileNo(consgDO.getConsignee().getMobile());
			}

			LOGGER.debug("BulkBookingConverter::consgDO::END------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());

			bookingDOs.add(booking);
			consignmentDOs.add(consgDO);

			LOGGER.debug("BulkBookingConverter::bulkBookingTOConvertor::END------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
		}
		list.add(bookingDOs);
		list.add(consignmentDOs);

		LOGGER.debug("BackDatedBookingConverter::backdatedBookingTOConvertor::END------------>:::::::");
		return list;
	}

	private static void setUpBackdatedConsignee(List<String> backdatedTOlst,
			ConsignmentDO consgDO) {
		LOGGER.debug("BackDatedBookingConverter::setUpBackdatedConsignee::END------------>:::::::");
		ConsigneeConsignorDO consignorDO = new ConsigneeConsignorDO();
		consignorDO.setFirstName(backdatedTOlst.get(11));
		consignorDO.setAddress(backdatedTOlst.get(12));
		consignorDO.setPhone(backdatedTOlst.get(13));
		consignorDO.setMobile(backdatedTOlst.get(14));
		consignorDO.setPartyType(BookingConstants.CONSIGNEE);
		consignorDO.setCreatedBy(consgDO.getCreatedBy());
		consignorDO.setCreatedDate(new Date());
		consgDO.setConsignee(consignorDO);
		LOGGER.debug("BackDatedBookingConverter::setUpBackdatedConsignee::END------------>:::::::");

	}

	private static void setUpBackdatedConsignor(List<String> backdatedTOlst,
			ConsignmentDO consgDO) {
		LOGGER.debug("BackDatedBookingConverter::setUpBackdatedConsignor::START------------>:::::::");
		ConsigneeConsignorDO consignorDO = new ConsigneeConsignorDO();
		consignorDO.setFirstName(backdatedTOlst.get(8));
		consignorDO.setMobile(backdatedTOlst.get(9));
		consignorDO.setAddress(backdatedTOlst.get(10));
		consignorDO.setPartyType(BookingConstants.CONSIGNOR);
		consignorDO.setCreatedBy(consgDO.getCreatedBy());
		consignorDO.setCreatedDate(new Date());
		consgDO.setConsignor(consignorDO);
		LOGGER.debug("BackDatedBookingConverter::setUpBackdatedConsignor::END------------>:::::::");
	}

	/**
	 * Backdated booking domain convertor.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 */
	public static List<BookingDO> backdatedBookingDomainConvertor(
			List<BackdatedBookingTO> bookingTOs) {
		LOGGER.debug("BackdatedBookingAction::backdatedBookingDomainConvertor::START------------>:::::::");
		List<BookingDO> bookings = null;
		try {
			if (StringUtil.isEmptyColletion(bookingTOs)) {
				return null;
			}
			bookings = new ArrayList<BookingDO>(bookingTOs.size());

			for (BackdatedBookingTO bookingTO : bookingTOs) {
				BookingDO booking = convert(bookingTO);
				if (!StringUtil.isEmptyInteger(bookingTO.getBizAssociateId())) {
					CustomerDO customer = new CustomerDO();
					customer.setCustomerId(bookingTO.getBizAssociateId());
					booking.setCustomerId(customer);
				} else if (!StringUtil.isEmptyInteger(bookingTO.getCustomeId())) {
					CustomerDO customer = new CustomerDO();
					customer.setCustomerId(bookingTO.getCustomeId());
					booking.setCustomerId(customer);
				}
				booking.setRefNo(bookingTO.getReferenceNo());
				// Setting Consignee & Consignor
				if (!StringUtil.isNull(bookingTO.getConsignee())) {
					ConsigneeConsignorDO consignee = BookingUtils
							.setUpConsigneeConsignorDetails(bookingTO
									.getConsignee());
					booking.setConsigneeId(consignee);
				}
				if (!StringUtil.isNull(bookingTO.getConsignor())) {
					ConsigneeConsignorDO consignor = BookingUtils
							.setUpConsigneeConsignorDetails(bookingTO
									.getConsignor());
					booking.setConsignorId(consignor);
				}
				// Setting vol weight details
				if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
					booking.setVolWeight(bookingTO.getLength());
				if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
					booking.setHeight(bookingTO.getHeight());
				if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
					booking.setBreath(bookingTO.getBreath());
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
				booking.setPrice(bookingTO.getCnPricingDtls().getFinalPrice());
				bookings.add(booking);
			}
		} catch (Exception e) {
			LOGGER.debug("Exception in BackdatedBookingAction :: backdatedBookingDomainConvertor() :"
					+ e.getMessage());
		}
		LOGGER.debug("BackdatedBookingAction::backdatedBookingDomainConvertor::END------------>:::::::");
		return bookings;
	}

}
