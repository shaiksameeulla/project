package com.ff.web.booking.converter;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingGridTO;
import com.ff.booking.BookingParcelTO;
import com.ff.booking.BookingTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingTOFactory;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class BaseBookingConverter.
 */
public class BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BaseBookingConverter.class);

	/**
	 * Convert.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the booking do
	 */
	public static BookingDO convert(BookingTO bookingTO) {
		LOGGER.debug("BaseBookingConverter::convert::START------------>:::::::");
		Integer consgTypeId = 0;
		BookingDO booking = new BookingDO();
		booking.setCreatedBy(bookingTO.getCreatedBy());
		booking.setUpdatedBy(bookingTO.getUpdatedBy());
		//booking.setProcessNumber(bookingTO.getProcessNumber());
		if (!StringUtil.isEmptyInteger(bookingTO.getBookingId())) {
			booking.setBookingId(bookingTO.getBookingId());
			booking.setDtUpdateToCentral(CommonConstants.YES);
			booking.setDtToCentral(CommonConstants.NO);
		} else {
			booking.setDtToCentral(CommonConstants.NO);
		}
		booking.setCreatedDate(new Date());
		Date bookingDate = DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate());
		booking.setBookingDate(bookingDate);
		if (StringUtils.isNotEmpty(bookingTO.getConsgTypeName())) {
			consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName().split(
					"#")[0]);
			bookingTO.setConsgTypeId(consgTypeId);
			ConsignmentTypeDO consgType = new ConsignmentTypeDO();
			consgType.setConsignmentId(consgTypeId);
			booking.setConsgTypeId(consgType);
		}
		booking.setConsgNumber(bookingTO.getConsgNumber());
		booking.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
		booking.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);
		if (!StringUtil.isEmptyInteger(bookingTO.getPincodeId())) {
			PincodeDO pin = new PincodeDO();
			pin.setPincodeId(bookingTO.getPincodeId());
			booking.setPincodeId(pin);
		}
		if (!StringUtil.isEmptyInteger(bookingTO.getCityId())) {
			CityDO destCity = new CityDO();
			destCity.setCityId(bookingTO.getCityId());
			booking.setDestCityId(destCity);
		}
		booking.setFianlWeight(bookingTO.getFinalWeight());

		if (bookingTO.getVolWeight() != null && bookingTO.getVolWeight() > 0)
			booking.setVolWeight(bookingTO.getVolWeight());
		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0) {
			booking.setActualWeight(bookingTO.getActualWeight());
		} else {
			booking.setActualWeight(bookingTO.getFinalWeight());
		}

		booking.setPrice(bookingTO.getPrice());
		BookingTypeDO cashBooking = new BookingTypeDO();
		cashBooking.setBookingTypeId(bookingTO.getBookingTypeId());
		booking.setBookingType(cashBooking);
		booking.setBookingOfficeId(bookingTO.getBookingOfficeId());
		ProcessTO processInput = new ProcessTO();
		processInput.setProcessCode(bookingTO.getProcessTO().getProcessCode());
		if (!StringUtil.isEmptyInteger(bookingTO.getProcessTO().getProcessId())) {
			ProcessDO process = new ProcessDO();
			process.setProcessId(bookingTO.getProcessTO().getProcessId());
			booking.setUpdatedProcess(process);
		}
		LOGGER.debug("BaseBookingConverter::convert::END------------>:::::::");
		return booking;
	}

	/**
	 * Convert.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the booking do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingDO convert(BookingGridTO bookingTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BaseBookingConverter::convert::START------------>:::::::");
		// Getting Booking Type
		BookingDO booking = new BookingDO();
		if (!StringUtil.isEmptyInteger(bookingTO.getBookingId()))
			booking.setBookingId(bookingTO.getBookingId());
	//	booking.setProcessNumber(bookingTO.getProcessNumber());
		Date bookingDate = DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate());
		booking.setBookingDate(bookingDate);
		booking.setCreatedDate(new Date());
		booking.setCreatedBy(bookingTO.getCreatedBy());
		booking.setUpdatedBy(bookingTO.getUpdatedBy());
		ConsignmentTypeDO consgType = new ConsignmentTypeDO();
		consgType.setConsignmentId(bookingTO.getConsgTypeId());
		booking.setConsgTypeId(consgType);
		booking.setConsgNumber(bookingTO.getConsgNumber());
		booking.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
		booking.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);
		if (!StringUtil.isEmptyInteger(bookingTO.getPincodeId())) {
			PincodeDO pin = new PincodeDO();
			pin.setPincodeId(bookingTO.getPincodeId());
			booking.setPincodeId(pin);
		}
		if (!StringUtil.isEmptyInteger(bookingTO.getCityId())) {
			CityDO destCity = new CityDO();
			destCity.setCityId(bookingTO.getCityId());
			booking.setDestCityId(destCity);
		}
		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			booking.setFianlWeight(bookingTO.getFinalWeight());

		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0) {
			booking.setActualWeight(bookingTO.getActualWeight());
		} else {
			booking.setActualWeight(bookingTO.getFinalWeight());
		}
		booking.setPrice(bookingTO.getPrice());
		BookingTypeDO cashBooking = new BookingTypeDO();
		cashBooking.setBookingTypeId(bookingTO.getBookingTypeId());
		booking.setBookingType(cashBooking);
		booking.setBookingOfficeId(bookingTO.getBookingOfficeId());
		if (!StringUtil.isEmptyInteger(bookingTO.getProcessTO().getProcessId())) {
			ProcessDO process = new ProcessDO();
			process.setProcessId(bookingTO.getProcessTO().getProcessId());
			process.setProcessCode(bookingTO.getProcessTO().getProcessCode());
			booking.setUpdatedProcess(process);
		}
		booking.setWeightCapturedMode(bookingTO.getWeightCapturedMode());
		// Setting consigee and consignor
		/*
		 * if (!StringUtil.isNull(bookingTO.getConsignee())) {
		 * ConsigneeConsignorDO consignee = BookingUtils
		 * .setUpConsigneeConsignorDetails(bookingTO.getConsignee()); if
		 * (consignee != null) booking.setConsigneeId(consignee); } if
		 * (!StringUtil.isNull(bookingTO.getConsignor())) { ConsigneeConsignorDO
		 * consignor = BookingUtils
		 * .setUpConsigneeConsignorDetails(bookingTO.getConsignor()); if
		 * (consignor != null) booking.setConsignorId(consignor); }
		 */
		booking.setProcessNumber(bookingTO.getProcessNumber());
		LOGGER.debug("BaseBookingConverter::convert::END------------>:::::::");
		return booking;
	}

	/**
	 * Sets the up booking t os.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @param rowCount
	 *            the row count
	 * @return the booking to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingTO setUpBookingTOs(BookingGridTO bookingTO,
			int rowCount) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BaseBookingConverter::setUpBookingTOs::START------------>:::::::");
		// Getting Booking Type
		Integer consgTypeId = 0;
		BookingTO booking = null;
		String consgType = null;
		if (StringUtils.isNotEmpty(bookingTO.getConsgTypeName())) {
			consgTypeId = Integer.parseInt(bookingTO.getConsgTypeName().split("#")[0]);
			consgType = bookingTO.getConsgTypeName().split("#")[1];
			LOGGER.debug("BaseBookingConverter::setUpBookingTOs::consgType------------>:::::::" + consgType);
		}
		if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING, bookingTO.getBookingType())) {
			LOGGER.debug("BaseBookingConverter::setUpBookingTOs:: setting BA as a booking type");
			booking = BookingTOFactory.getBookingTO(BookingConstants.BA_BOOKING, consgType);
		} else if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING, bookingTO.getBookingType())) {
			booking = BookingTOFactory.getBookingTO(BookingConstants.CCC_BOOKING, consgType);
			LOGGER.debug("BaseBookingConverter::setUpBookingTOs:: setting CR as a booking type");
		}
		booking.setBookingOffCode(bookingTO.getBookingOffCode());
		booking.setOriginCity(bookingTO.getOriginCity());
		//booking.setProcessNumber(bookingTO.getProcessNumber());
		booking.setBookingDate(bookingTO.getBookingDate());
		booking.setProcessTO(bookingTO.getProcessTO());
		if (bookingTO.getBookingIds() != null
				&& bookingTO.getBookingIds().length > rowCount
				&& bookingTO.getBookingIds()[rowCount] != rowCount) {
			LOGGER.debug("BaseBookingConverter::setUpBookingTOs:: setting available booking id: " + bookingTO.getBookingIds()[rowCount]);
			booking.setBookingId(bookingTO.getBookingIds()[rowCount]);
		}
		if (StringUtils.isNotEmpty(bookingTO.getConsgTypeName())) {
			booking.setConsgTypeId(consgTypeId);
			booking.setConsgTypeName(consgType);
		}
		if (!StringUtil.isNull(bookingTO.getConsgNumbers())
				&& bookingTO.getConsgNumbers().length > rowCount) {
			booking.setConsgNumber(bookingTO.getConsgNumbers()[rowCount]);
		}
		booking.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
		if (bookingTO.getNumOfPcs() != null
				&& bookingTO.getNumOfPcs().length > rowCount
				&& bookingTO.getNumOfPcs()[rowCount] != 0) {
			booking.setNoOfPieces(bookingTO.getNumOfPcs()[rowCount]);
		}
		if (bookingTO.getPincodeIds() != null
				&& bookingTO.getPincodeIds().length > rowCount
				&& bookingTO.getPincodeIds()[rowCount] != 0) {
			booking.setPincodeId(bookingTO.getPincodeIds()[rowCount]);
		}
		if (bookingTO.getCityIds() != null
				&& bookingTO.getCityIds().length > rowCount
				&& bookingTO.getCityIds()[rowCount] != 0) {
			booking.setCityId(bookingTO.getCityIds()[rowCount]);
		}
		if (bookingTO.getFinalWeights() != null
				&& bookingTO.getFinalWeights().length > rowCount
				&& bookingTO.getFinalWeights()[rowCount] != 0) {
			booking.setFinalWeight(bookingTO.getFinalWeights()[rowCount]);
		}
		if (bookingTO.getActualWeights() != null
				&& bookingTO.getActualWeights().length > rowCount
				&& bookingTO.getActualWeights()[rowCount] != 0) {
			booking.setActualWeight(bookingTO.getActualWeights()[rowCount]);
		}
		// Setting Vol weight
		if (bookingTO.getVolWeights() != null
				&& bookingTO.getVolWeights().length > rowCount
				&& bookingTO.getVolWeights()[rowCount] != 0) {
			booking.setVolWeight(bookingTO.getVolWeights()[rowCount]);
		}
		booking.setPrice(bookingTO.getPrice());
		booking.setBookingTypeId(bookingTO.getBookingTypeId());
		booking.setBookingOfficeId(bookingTO.getBookingOfficeId());
		booking.setBookingProcess(BookingConstants.BOOKING_NORMAL_PROCESS);

		if (!StringUtil.isNull(bookingTO.getWeightCapturedModes())
				&& bookingTO.getWeightCapturedModes().length > rowCount) {
			booking.setWeightCapturedMode(bookingTO.getWeightCapturedModes()[rowCount]);
		}
		// Setting CN Pricing details
		if (!StringUtil.isNull(bookingTO.getConsgPricingDtls())
				&& bookingTO.getConsgPricingDtls().length > rowCount) {
			LOGGER.debug("BaseBookingConverter::setUpBookingTOs:: input CNPricingDetails: " + bookingTO.getConsgPricingDtls()[rowCount]);
			CNPricingDetailsTO cnPricingDetls = BookingUtils
					.setUpConsgPricingDtls(bookingTO.getConsgPricingDtls()[rowCount]);
			booking.setCnPricingDtls(cnPricingDetls);
			if (!StringUtil.isNull(cnPricingDetls)) {
				if (bookingTO.getDeclaredValues() != null
						&& bookingTO.getDeclaredValues().length > rowCount
						&& bookingTO.getDeclaredValues()[rowCount] != 0) {
					cnPricingDetls.setDeclaredvalue(bookingTO
							.getDeclaredValues()[rowCount]);
				}
				booking.setCnPricingDtls(cnPricingDetls);
			}
		}
		// Setting consignee & consignor
		if (!StringUtil.isNull(bookingTO.getCneAddressDtls())
				&& bookingTO.getCneAddressDtls().length > rowCount) {
			if (StringUtils.isNotEmpty(bookingTO.getCneAddressDtls()[rowCount])) {
				ConsignorConsigneeTO consignee = BookingUtils
						.setUpConsigneeConsignorDtls(bookingTO
								.getCneAddressDtls()[rowCount]);
				booking.setConsignee(consignee);
			}
		}
		if (!StringUtil.isNull(bookingTO.getCnrAddressDtls())
				&& bookingTO.getCnrAddressDtls().length > rowCount) {
			if (StringUtils.isNotEmpty(bookingTO.getCnrAddressDtls()[rowCount])) {
				ConsignorConsigneeTO consignor = BookingUtils
						.setUpConsigneeConsignorDtls(bookingTO
								.getCnrAddressDtls()[rowCount]);
				booking.setConsignor(consignor);
			}
		}
		LOGGER.debug("BaseBookingConverter::setUpBookingTOs::END------------>:::::::");
		return booking;
	}

	public static ConsignmentDO prepareDoxConsignments(BookingTO bookingTO,
			BookingCommonService bookingCommonService,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDetails)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BaseBookingConverter::prepareDoxConsignments::START------------>:::::::");
		ConsignmentDO consignment = new ConsignmentDO();
		ConsignmentTO consgTO = new ConsignmentTO();
		// Setting Billing flags
		setBillingStatus(consgTO);
		if (!StringUtil.isNull(bookingTO.getConsigmentTO())) {
			consgTO.setConsgId(bookingTO.getConsigmentTO().getConsgId());
		}
		consgTO.setConsgTypeId(bookingTO.getConsgTypeId());
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
		consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
		PincodeTO pinCode = new PincodeTO();
		pinCode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pinCode);

		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());

		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0)
			consgTO.setActualWeight(bookingTO.getActualWeight());

		ProductTO productTO = bookingCommonService
				.getProductByConsgSeries(bookingTO.getConsgNumber().substring(
						4, 5));

		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());

		CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
		if (!StringUtil.isNull(cnPriceDtls)) {
			consgTO.setConsgPriceDtls(cnPriceDtls);
		}
		consgTO.setReCalcRateReq(Boolean.FALSE);

		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());

		/*Integer consgOpLevel = bookingCommonService.getConsgOperatingLevel(
				consgTO, loggedInOffice);
		consgTO.setOperatingLevel(consgOpLevel);*/

		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			bookingTO.getConsignee().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignee().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			bookingTO.getConsignor().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignor().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}
		// Setting cuatomer / Business Associate
		if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
			consgTO.setCustomer(bookingTO.getCustomerId());
		} else if (!StringUtil.isEmptyInteger(bookingTO.getBizAssociateId())) {
			consgTO.setCustomer(bookingTO.getBizAssociateId());
		}
		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, bookingTO
						.getConsgNumber().substring(4, 5))) {
			if (!StringUtil.isStringEmpty(bookingTO.getPriorityServiced())) {
				String priorityService = bookingTO.getPriorityServiced();
				if (priorityService.contains("Before")) {
					consgTO.setServicedOn("B");
				} else if (priorityService.contains("After")) {
					consgTO.setServicedOn("A");
				} else {
					consgTO.setServicedOn("S");
				}
				// consgTO.setServicedOn(priorityService != null &&  priorityService.contains("Before") ? "B" : "A");
			}
		}

		if (!StringUtil.isNull(bookingTO.getBaAmt())) {
			consgTO.setBaAmount(bookingTO.getBaAmt());
		}

		if (!StringUtil.isNull(bookingTO.getCodAmount())) {
			consgTO.setCodAmt(bookingTO.getCodAmount());
		}

		consgTO.setEventDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate()));

		// Setting Consignment Rate Details
		Set<ConsignmentBillingRateDO> consgRateDtls = null;
		if (!CGCollectionUtils.isEmpty(consgRateDetails)) {
			consgRateDtls = BookingUtils.setupConsgRateDtls(
					bookingTO.getConsgNumber(), consgRateDetails);

			for (ConsignmentBillingRateDO cRate : consgRateDtls) {
				cRate.setCreatedBy(bookingTO.getCreatedBy());
				cRate.setUpdatedBy(bookingTO.getUpdatedBy());
			}
		}
		consignment = BookingUtils.prepareConsignment(consgTO, consgRateDtls);
		consignment.setCreatedBy(bookingTO.getCreatedBy());
		consignment.setUpdatedBy(bookingTO.getUpdatedBy());
		consignment.setConsgStatus("B");
		LOGGER.debug("BaseBookingConverter::prepareDoxConsignments::END------------>:::::::");
		return consignment;
	}

	public static ConsignmentDO prepareParcelConsignments(
			BookingParcelTO bookingTO,
			BookingCommonService bookingCommonService,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDetails)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsParcel::START------------>:::::::");
		ConsignmentTO consgTO = new ConsignmentTO();
		ConsignmentDO consignment = new ConsignmentDO();
		// Setting Billing flags
		setBillingStatus(consgTO);
		consgTO.setConsgTypeId(bookingTO.getConsgTypeId());
		consgTO.setConsgNo(bookingTO.getConsgNumber());
		consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
		consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
		PincodeTO pinCode = new PincodeTO();
		pinCode.setPincodeId(bookingTO.getPincodeId());
		consgTO.setDestPincode(pinCode);
		consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
		if (bookingTO.getFinalWeight() != null
				&& bookingTO.getFinalWeight() > 0)
			consgTO.setFinalWeight(bookingTO.getFinalWeight());
		if (bookingTO.getActualWeight() != null
				&& bookingTO.getActualWeight() > 0)
			consgTO.setActualWeight(bookingTO.getActualWeight());
		if (bookingTO.getVolWeight() != null && bookingTO.getVolWeight() > 0) {
			VolumetricWeightTO volWeight = new VolumetricWeightTO();
			volWeight.setVolWeight(bookingTO.getVolWeight());
			if (bookingTO.getLength() != null && bookingTO.getLength() > 0)
				volWeight.setLength(bookingTO.getLength());
			if (bookingTO.getHeight() != null && bookingTO.getHeight() > 0)
				volWeight.setHeight(bookingTO.getHeight());
			if (bookingTO.getBreath() != null && bookingTO.getBreath() > 0)
				volWeight.setBreadth(bookingTO.getBreath());
			consgTO.setVolWightDtls(volWeight);
		}
		ProductTO productTO = bookingCommonService
				.getProductByConsgSeries(bookingTO.getConsgNumber().substring(
						4, 5));
		if (productTO != null && productTO.getProductId() != null
				&& productTO.getProductId() > 0)
			consgTO.setProductId(productTO.getProductId());
		consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
		// Child Consignments
		if (StringUtils.isNotEmpty(bookingTO.getChildCNsDtls())) {
			Set<ChildConsignmentTO> chilsCns = BookingUtils
					.setUpChildConsignmentTOs(bookingTO.getChildCNsDtls());
			
			if (!StringUtil.isEmptyColletion(chilsCns)){
				for(ChildConsignmentTO child:chilsCns){
					child.setCreatedBy(bookingTO.getCreatedBy());
					child.setUpdatedBy(bookingTO.getUpdatedBy());
				}
				consgTO.setChildTOSet(chilsCns);
			}
		
		}
		// Setting CN Content
		if (!StringUtil.isEmptyInteger(bookingTO.getCnContentId())) {
			CNContentTO content = new CNContentTO();
			content.setCnContentId(bookingTO.getCnContentId());
			content.setOtherContent(bookingTO.getOtherCNContent());
			consgTO.setCnContents(content);
		}
		// Setting Paperworks
		if (!StringUtil.isEmptyInteger(bookingTO.getCnPaperworkId())) {
			CNPaperWorksTO cnPaperwork = new CNPaperWorksTO();
			cnPaperwork.setCnPaperWorkId(bookingTO.getCnPaperworkId());
			cnPaperwork.setPaperWorkRefNum(bookingTO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperwork);
		}
		// Setting insured by
		if (!StringUtil.isEmptyInteger(bookingTO.getInsuredById())) {
			InsuredByTO insuredBy = new InsuredByTO();
			insuredBy.setInsuredById(bookingTO.getInsuredById());
			insuredBy.setPolicyNo(bookingTO.getPolicyNo());
			consgTO.setInsuredByTO(insuredBy);
		}
		if (!StringUtil.isNull(bookingTO.getConsignee()))
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
		consgTO.setReCalcRateReq(Boolean.FALSE);
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
		/*Integer consgOpLevel = bookingCommonService.getConsgOperatingLevel(
				consgTO, loggedInOffice);
		consgTO.setOperatingLevel(consgOpLevel);*/
		if (!StringUtil.isNull(bookingTO.getConsignee())) {
			consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			bookingTO.getConsignee().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignee().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsigneeTO(bookingTO.getConsignee());
		}
		if (!StringUtil.isNull(bookingTO.getConsignor())) {
			bookingTO.getConsignor().setCreatedBy(bookingTO.getCreatedBy());
			bookingTO.getConsignor().setUpdatedBy(bookingTO.getUpdatedBy());
			consgTO.setConsignorTO(bookingTO.getConsignor());
		}
		// Setting cuatomer / Business Associate
		if (!StringUtil.isEmptyInteger(bookingTO.getCustomerId())) {
			consgTO.setCustomer(bookingTO.getCustomerId());
		} else if (!StringUtil.isEmptyInteger(bookingTO.getBizAssociateId())) {
			consgTO.setCustomer(bookingTO.getBizAssociateId());
		}
		consgTO.setEventDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(bookingTO.getBookingDate()));
		CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
		if (!StringUtil.isNull(cnPriceDtls)) {
			consgTO.setConsgPriceDtls(cnPriceDtls);
		}

		if (StringUtils.equalsIgnoreCase(
				CommonConstants.PRODUCT_SERIES_PRIORITY, bookingTO
						.getConsgNumber().substring(4, 5))) {
			if (!StringUtil.isStringEmpty(bookingTO.getPriorityServiced())) {
				String priorityService = bookingTO.getPriorityServiced();
				if (priorityService.contains("Before")) {
					consgTO.setServicedOn("B");
				} else if (priorityService.contains("After")) {
					consgTO.setServicedOn("A");
				} else {
					consgTO.setServicedOn("S");
				}
				// consgTO.setServicedOn(priorityService != null &&  priorityService.contains("Before") ? "B" : "A");
			}

		}

		if (!StringUtil.isNull(bookingTO.getBaAmt())) {
			consgTO.setBaAmount(bookingTO.getBaAmt());
		}

		if (!StringUtil.isNull(bookingTO.getCodAmount())) {
			consgTO.setCodAmt(bookingTO.getCodAmount());
		}
		
		Set<ConsignmentBillingRateDO> consgRateDtls = null;
		if (!CGCollectionUtils.isEmpty(consgRateDetails)) {
			consgRateDtls = BookingUtils
					.setupConsgRateDtls(bookingTO.getConsgNumber(),
							consgRateDetails);

			for (ConsignmentBillingRateDO cRate : consgRateDtls) {
				cRate.setCreatedBy(bookingTO.getCreatedBy());
				cRate.setUpdatedBy(bookingTO.getUpdatedBy());
			}
		}
		consignment = BookingUtils.prepareConsignment(consgTO, consgRateDtls);
		consignment.setCreatedBy(bookingTO.getCreatedBy());
		consignment.setUpdatedBy(bookingTO.getUpdatedBy());
		consignment.setConsgStatus("B");
		LOGGER.debug("BookingCommonServiceImpl::saveCNPricingDtlsParcel::END------------>:::::::");
		return consignment;
	}

	private static ConsignmentTO setBillingStatus(ConsignmentTO consg) {
		LOGGER.debug("BaseBookingConverter::setBillingStatus::START------------>:::::::");
		consg.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
		consg.setChangedAfterBillingWtDest(CommonConstants.NO);
		consg.setChangedAfterNewRateCmpnt(CommonConstants.NO);
		LOGGER.debug("BaseBookingConverter::setBillingStatus::END------------>:::::::");
		return consg;
	}
}
