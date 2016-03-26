/**
 * 
 */
package com.ff.web.booking.converter;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.BulkBookingVendorDtlsTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.constants.BookingExcelConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;

/**
 * The Class BulkBookingConverter.
 * 
 * @author uchauhan
 */
public class BulkBookingConverter extends BaseBookingConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkBookingConverter.class);

	/**
	 * Bulk booking to convertor.
	 * 
	 * @param bookingDetails
	 *            the booking details
	 * @param cnValidation
	 * @return
	 * @return the list
	 * @throws CGSystemException
	 */
	public static List<Object> bulkBookingTOConvertor(
			List<List> bookingDetails, List<String> consgnumbers,
			CreditCustomerBookingParcelTO bulkBookingParcelTO,
			BookingValidationTO cnValidation,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkBookingConverter::bulkBookingTOConvertor::START------------>:::::::transforming all CNs from file to objects"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<BookingDO> bookingDOs = new ArrayList<>(bookingDetails.size());
		List<ConsignmentDO> consignmentDOs = new ArrayList<>(
				bookingDetails.size());
		BookingDO booking = null;
		ConsignmentDO consgDO = null;
		List<Object> list = new ArrayList<>();
		
		Integer createdBy=bulkBookingParcelTO.getCreatedBy();
		Date createdDate=new Date();
		BulkBookingVendorDtlsTO bulkBookingVendorDtls = new BulkBookingVendorDtlsTO();
		BookingTypeDO bookingTypeDO = new BookingTypeDO();
		bookingTypeDO.setBookingTypeId(bulkBookingParcelTO.getBookingTypeId());
		bookingTypeDO.setBookingType(bulkBookingParcelTO.getBookingType());
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		process = bookingCommonService.getProcess(process);
		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(process.getProcessId());
		processDO.setProcessCode(process.getProcessCode());
		ConsignmentTypeTO params = new ConsignmentTypeTO();
		params.setConsignmentCode(bulkBookingParcelTO.getConsgTypeName());
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType(params);
		ConsignmentTypeTO docType = consgTypes.get(0);
		ConsignmentTypeDO consgType = new ConsignmentTypeDO();
		consgType.setConsignmentId(docType.getConsignmentId());
		consgType.setConsignmentCode(docType.getConsignmentCode());

		ConsigneeConsignorDO cntAddress = setupConsignorAddress(
				bulkBookingParcelTO.getCustomerId(), bookingCommonService);
		cntAddress.setCreatedBy(createdBy);
		cntAddress.setCreatedDate(createdDate);

		int count = 0;
		String consgSeries = "";
		for (List<String> backdatedTOlst : bookingDetails) {
			LOGGER.debug("BulkBookingConverter::bookingDO::START------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			booking = new BookingDO();
			consgDO = new ConsignmentDO();
			if (!StringUtil.isStringEmpty(backdatedTOlst.get(0))) {
				if (backdatedTOlst.get(0).equalsIgnoreCase(
						BookingExcelConstants.DATE))
					continue;
			}
			booking.setBookingDate(createdDate);
			booking.setCreatedDate(createdDate);
			consgDO.setEventDate(createdDate);
			booking.setCreatedBy(createdBy);
			booking.setUpdatedBy(createdBy);
			consgDO.setCreatedBy(createdBy);
			consgDO.setUpdatedBy(createdBy);
			
			booking.setBookingType(bookingTypeDO);
			booking.setBookingOfficeId(bulkBookingParcelTO.getBookingOfficeId());

			if (!CGCollectionUtils.isEmpty(consgnumbers)
					&& StringUtils.isNotEmpty(backdatedTOlst.get(1))) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.CONSG_NO_WRONLY_ENTERED);
			}/*
			 * else if (CGCollectionUtils.isEmpty(consgnumbers) &&
			 * StringUtils.isEmpty(backdatedTOlst.get(1))) { throw new
			 * CGBusinessException
			 * (BookingErrorCodesConstants.CONSG_NO_WRONLY_ENTERED); }
			 */
			booking.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
			booking.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);
			LOGGER.debug("BulkBookingConverter::bulkBookingTO"
					+ backdatedTOlst.get(1).trim() + "...." + count);
			if (!StringUtils.isEmpty(backdatedTOlst.get(1))) {
				booking.setConsgNumber(backdatedTOlst.get(1).trim()
						.toUpperCase());
				consgDO.setConsgNo(backdatedTOlst.get(1).trim().toUpperCase());
			} else {
				if (!CGCollectionUtils.isEmpty(consgnumbers)
						&& consgnumbers.size() >= count) {
					booking.setConsgNumber(consgnumbers.get(count));
					consgDO.setConsgNo(consgnumbers.get(count));
				}
			}
			if (StringUtils.isNotEmpty(booking.getConsgNumber()))
				consgSeries = booking.getConsgNumber().substring(4, 5);

			if (!StringUtil.isEmptyInteger(process.getProcessId())) {
				booking.setUpdatedProcess(processDO);
				consgDO.setUpdatedProcess(processDO);
			}
			if (!StringUtil.isEmptyList(consgTypes)) {
				booking.setConsgTypeId(consgType);
				consgDO.setConsgType(consgType);
			}
			if (StringUtils.isNotEmpty(backdatedTOlst.get(2))) {
				booking.setRefNo(backdatedTOlst.get(2).trim());
				consgDO.setRefNo(backdatedTOlst.get(2).trim());
			}

			booking.setConsigneeAddr(backdatedTOlst.get(5));
			booking.setAltConsigneeAddr(backdatedTOlst.get(26));

			CustomerDO custDO = new CustomerDO();
			if (!StringUtil.isEmptyInteger(bulkBookingParcelTO.getCustomerId())) {
				custDO.setCustomerId(bulkBookingParcelTO.getCustomerId());
				consgDO.setCustomer(bulkBookingParcelTO.getCustomerId());
			}
			booking.setCustomerId(custDO);
			if (StringUtils.isNotEmpty(bulkBookingParcelTO
					.getCustomerCodeSingle()))
				booking.setShippedToCode(bulkBookingParcelTO
						.getCustomerCodeSingle());

			// Setting Bulk vendor details
			if (StringUtils.isNotEmpty(backdatedTOlst.get(13))
					|| StringUtils.isNotEmpty(backdatedTOlst.get(16))) {
				bulkBookingVendorDtls.setTransactionNo(backdatedTOlst.get(13)
						.trim());
				bulkBookingVendorDtls.setVendorName(backdatedTOlst.get(16)
						.trim());
				bulkBookingVendorDtls
						.setVendorPickupLoc(backdatedTOlst.get(17));
				bulkBookingVendorDtls
						.setVendorContactNo(backdatedTOlst.get(18));
				bulkBookingVendorDtls.setPaymentMethodType(backdatedTOlst
						.get(19));
				BulkBookingVendorDtlsDO bulkBookingVendor = BookingUtils
						.setupBulkBookingVendorDtls(bulkBookingVendorDtls);
				if (!StringUtil.isNull(bulkBookingVendorDtls)
						&& StringUtils.isNotEmpty(bulkBookingVendorDtls
								.getVendorName())) {
					booking.setBulkBookingVendorDtls(bulkBookingVendor);
				}

			}

			String insuredBy = backdatedTOlst.get(24);
			InsuredByTO insuredByTO = new InsuredByTO();
			InsuredByDO insuredByDO = new InsuredByDO();
			// Setting newly added policyNo
			String policyNo = backdatedTOlst.get(25);

			if (!StringUtil.isNull(consgType)
					&& StringUtils.isNotEmpty(consgType.getConsignmentCode())
					&& StringUtils.equalsIgnoreCase(
							CommonConstants.CONSIGNMENT_TYPE_PARCEL,
							consgType.getConsignmentCode())) {

				//------------------------------
				
				if (StringUtils.isNotEmpty(insuredBy)) {
					insuredByTO = bookingCommonService.getInsuredByNameOrCode(
							insuredBy, null, null);
				}
				if (!StringUtil.isNull(insuredByTO)
						&& !StringUtil.isEmptyInteger(insuredByTO
								.getInsuredById())) {
					insuredByDO.setInsuredById(insuredByTO.getInsuredById());
					insuredByDO
							.setInsuredByCode(insuredByTO.getInsuredByCode());
					insuredByDO
							.setInsuredByDesc(insuredByTO.getInsuredByDesc());
					booking.setInsuredBy(insuredByDO);
				} else {
					insuredByDO.setInsuredByDesc(insuredBy);
					booking.setInsuredBy(insuredByDO);
				}

				/*if (StringUtils.isNotEmpty(insuredBy)) {
					String insuredByCode = null;
					if (insuredBy.trim().equalsIgnoreCase("First Flight")) {
						insuredByCode = "F";
						insuredByTO = bookingCommonService
								.getInsuredByNameOrCode(null, insuredByCode,
										null);

					} else if (insuredBy.trim().equalsIgnoreCase("Consignor")) {
						insuredByCode = "C";

						insuredByTO = bookingCommonService
								.getInsuredByNameOrCode(null, insuredByCode,
										null);

					} else {
						insuredByTO.setInsuredByDesc(insuredBy.trim());
					}
				}*/
				/*if (!StringUtil.isNull(insuredByTO)) {
					insuredByDO.setInsuredById(insuredByTO.getInsuredById());
					insuredByDO
							.setInsuredByCode(insuredByTO.getInsuredByCode());
					insuredByDO
							.setInsuredByDesc(insuredByTO.getInsuredByDesc());
					booking.setInsuredBy(insuredByDO);
				}*/
				
				if (StringUtils.isNotEmpty(policyNo)) {
					booking.setInsurencePolicyNo(policyNo.trim());
				}
				String decValStr = backdatedTOlst.get(23);
				double decVal = 0;
				try {
					decVal = Double.parseDouble(decValStr);
					if (BigDecimal.valueOf(decVal).toPlainString().length() > 10) {
						decVal = -1;
					}
				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BulkBookingConverter.convertWeight()", e);
				} finally {
					booking.setDeclaredValue(decVal);
					booking.setDecValStr(decValStr);
				}

				if (!StringUtil.isNull(insuredByDO)) {
					consgDO.setInsuredBy(insuredByDO);
				}

				if (StringUtils.isNotEmpty(policyNo)) {
					consgDO.setInsurencePolicyNo(policyNo);
				}
				CNContentDO content = new CNContentDO();
				if (StringUtils.isNotEmpty(backdatedTOlst.get(4))) {
					content.setCnContentName(backdatedTOlst.get(4).trim());
				}
				consgDO.setDeclaredValue(decVal);
				consgDO.setCnContentId(content);

				// Number of Pieces / Quantity 
				String noOfPcsStr = backdatedTOlst.get(15);
				int noOfPcs = 0;

				try {
					if(noOfPcsStr.length() > 3)
						noOfPcs = -1;
					else 
						noOfPcs = Integer.parseInt(backdatedTOlst.get(15).trim());

				} catch (NumberFormatException numberFormatException) {
					LOGGER.error(
							"ERROR::BulkBookingConverter::NoOfPiecesConversion",
							numberFormatException);
				}
				finally {
					consgDO.setNoOfPcs(noOfPcs);
					booking.setNoOfPieces(noOfPcs);
					booking.setNoOfPcsStr(noOfPcsStr);
				}
				
				
				/*if (StringUtils.isNotEmpty(backdatedTOlst.get(15)) && backdatedTOlst.get(15).length() <= 3 )
					consgDO.setNoOfPcs(Integer.parseInt(backdatedTOlst.get(15)
							.trim()));
				
				if (StringUtils.isNotEmpty(backdatedTOlst.get(15)) && backdatedTOlst.get(15).length() <= 3)
					booking.setNoOfPieces(Integer.parseInt(backdatedTOlst.get(
							15).trim()));*/
			}
			if (StringUtils.isNotEmpty(backdatedTOlst.get(28))) {
				booking.setAltPincode(backdatedTOlst.get(28).trim());
			}
			double finalWt = 0;
			String finalWtStr = backdatedTOlst.get(10);
			try {
				String[] parts = finalWtStr.split("\\.");
				if ((BigDecimal.valueOf(finalWt).toPlainString().length() > 8) || parts.length > 2 || parts[0].length() > 4 || (parts.length < 2 ? false : parts[1].length() > 3)){
						finalWt = -1;
				} else {
					finalWt = Double.parseDouble(finalWtStr);
				}
			} catch (NumberFormatException e) {
				LOGGER.error("ERROR : BulkBookingConverter.convertWeight()", e);
			} finally {
				booking.setFianlWeight(finalWt);
				booking.setActualWeight(finalWt);
				booking.setActWtStr(finalWtStr);
			}
			//booking.setProcessNumber(bulkBookingParcelTO.getProcessNumber());
			try {

				Date orderDate = DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(backdatedTOlst
								.get(20));
				booking.setOrderDate(orderDate);
			} catch (Exception e) {
				LOGGER.error("ERROR : BulkBookingConverter.OrderDate()", e);
			}

			PincodeDO pin = new PincodeDO();

			if (!StringUtil.isNull(backdatedTOlst.get(7))) {
				pin.setPincode(backdatedTOlst.get(7).trim());
				booking.setPincodeId(pin);
			}
			LOGGER.debug("BulkBookingConverter::bookingDO::END------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());

			LOGGER.debug("BulkBookingConverter::consgDO::START------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			consgDO.setCreatedDate(new Date());
			// Setting Billing flags
			consgDO.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
			consgDO.setChangedAfterBillingWtDest(CommonConstants.NO);
			consgDO.setChangedAfterNewRateCmpnt(CommonConstants.NO);

			consgDO.setOrgOffId(bulkBookingParcelTO.getBookingOfficeId());
			consgDO.setOperatingOffice(bulkBookingParcelTO.getBookingOfficeId());

			if (StringUtils.isNotEmpty(consgSeries)
					&& StringUtil.isDigit(consgSeries.charAt(0))) {
				consgSeries = "N";
			}
			List<ProductTO> prodList = cnValidation.getProductTOList();
			if (StringUtils.isNotEmpty(consgSeries)) {
				for (ProductTO prod : prodList) {
					if (prod.getConsgSeries().equalsIgnoreCase(consgSeries)) {
						consgDO.setProductId(prod.getProductId());
					}

				}
			}
			if (!StringUtil.isEmptyDouble(booking.getFianlWeight()))
				consgDO.setFinalWeight(booking.getFianlWeight());
			if (!StringUtil.isEmptyDouble(booking.getActualWeight()))
				consgDO.setActualWeight(booking.getActualWeight());

			if (!StringUtil.isNull(pin)) {
				consgDO.setDestPincodeId(pin);
			}

			double allowedMaxValue = 999999;
			if (StringUtils.isNotEmpty(backdatedTOlst.get(21))) {
				String lcAmtStr = backdatedTOlst.get(21);
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
			consgDO.setLcBankName(backdatedTOlst.get(22));
			if (StringUtils.isNotEmpty(backdatedTOlst.get(14))) {
				double codAmt = -1;
				String codAmtStr = backdatedTOlst.get(14);
				try {
					codAmt = Double.parseDouble(codAmtStr);
					codAmtStr = BigDecimal.valueOf(codAmt).toPlainString();
					if (Double.compare(codAmt, allowedMaxValue) > 0) {
						codAmt = -1;
					}
					// codAmt = Double.parseDouble(codAmtStr);
				} catch (NumberFormatException e) {
					LOGGER.error(
							"ERROR : BulkBookingConverter.CodAmtConvert()", e);
				} finally {
					consgDO.setCodAmt(codAmt);
					consgDO.setCodAmtStr(codAmtStr);
				}
			}

			if (!StringUtil.isEmptyInteger(consgDO.getConsgId())) {
				consgDO.setDtUpdateToCentral(CommonConstants.YES);
				consgDO.setDtToCentral(CommonConstants.NO);
			} else {
				consgDO.setDtToCentral(CommonConstants.NO);
			}
			consgDO.setConsgStatus("B");

			// Setting Consignee
			setUpConsignee(backdatedTOlst, consgDO);
			if (!StringUtil.isNull(consgDO.getConsignee()))
				booking.setConsigneeId(consgDO.getConsignee());
			// Setting Alternate Consignee
			setUpAlternateConsignee(backdatedTOlst, consgDO);

			if (!StringUtil.isNull(cntAddress)) {
				consgDO.setConsignor(cntAddress);
			}
			if (!StringUtil.isNull(consgDO.getConsignor()))
				booking.setConsignorId(consgDO.getConsignor());
			ConsignmentTO consgTO = new ConsignmentTO();
			consgTO.setOrgOffId(bulkBookingParcelTO.getBookingOfficeId());
			OfficeTO loggedInOffice = new OfficeTO();
			loggedInOffice
					.setOfficeId(bulkBookingParcelTO.getBookingOfficeId());
			/*Integer consgOpLevel = bookingCommonService.getConsgOperatingLevel(
					consgTO, loggedInOffice);
			consgDO.setOperatingLevel(consgOpLevel);*/

			if (!StringUtil.isNull(consgDO.getConsignee())) {
				consgDO.setMobileNo(consgDO.getConsignee().getMobile());
			}

			if (!StringUtil.isNull(bulkBookingParcelTO.getConsgStickerType()))
				booking.setBlkBookingType(bulkBookingParcelTO
						.getConsgStickerType());
			else
				LOGGER.error("ERROR : The value for Consgnment sticker type for bulk booking is null..."
						+ DateUtil.getCurrentTimeInMilliSeconds());

			// booking.setWeightCapturedMode(bookingTO.getWeightCapturedMode());

			LOGGER.debug("BulkBookingConverter::consgDO::END------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			count = count + 1;
			booking.setOriginCityId(bulkBookingParcelTO.getCityId());
			bookingDOs.add(booking);
			consignmentDOs.add(consgDO);

			LOGGER.debug("BulkBookingConverter::bulkBookingTOConvertor::END------------>:::::::transforming individual CN from file to objects"
					+ DateUtil.getCurrentTimeInMilliSeconds());
		}
		list.add(bookingDOs);
		list.add(consignmentDOs);

		LOGGER.debug("BulkBookingConverter::bulkBookingTOConvertor::END------------>:::::::transforming all CNs from file to objects"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return list;

	}

	public static ConsignmentBillingRateDO prepareCNBillingRateDO(
			ConsignmentRateCalculationOutputTO calculatedRate)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		LOGGER.debug("BulkBookingConverter :: prepareCNBillingRateDO() :: START");
		ConsignmentBillingRateDO consgBillingRateDO = new ConsignmentBillingRateDO();
		PropertyUtils.copyProperties(consgBillingRateDO, calculatedRate);
		consgBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
		LOGGER.debug("BulkBookingConverter :: prepareCNBillingRateDO() :: END");
		return consgBillingRateDO;
	}

	public static ConsignmentTO convertConsignmentDOToTO(
			ConsignmentDO consignmentDO,
			BookingCommonService bookingCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BulkBookingConverter :: convertConsignmentDOToTO() :: START");
		ConsignmentTO consignmentTO = new ConsignmentTO();
		ProductTO product = null;
		/** Set pincode if poncode is null */
		PincodeTO destPinTO = null;
		if ((StringUtil.isStringEmpty(consignmentDO.getDestPincodeId()
				.getPincode()))
				&& (!StringUtil.isEmptyInteger(consignmentDO.getDestPincodeId()
						.getPincodeId()))) {
			destPinTO = bookingCommonService
					.getPincodeByPincodeId(consignmentDO.getDestPincodeId()
							.getPincodeId());
			if (!StringUtil.isNull(destPinTO)) {
				consignmentDO.getDestPincodeId().setPincode(
						destPinTO.getPincode());
			}
		}

		consignmentTO = setUpConsignmentDtls(consignmentDO);
		if (!StringUtil.isEmptyInteger(consignmentDO.getProductId())) {
			ProductDO productDO = bookingCommonService
					.getProductByProductId(consignmentDO.getProductId());
			if (!StringUtil.isNull(productDO)) {
				product = new ProductTO();
				CGObjectConverter.createToFromDomain(productDO, product);
			}
		}
		consignmentTO.setBookingDate(DateUtil.getCurrentDate());

		if (!StringUtil.isNull(product)) {
			consignmentTO.setProductTO(product);
		}
		CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
		if (!StringUtil.isNull(consignmentDO.getRateType())) {
			cNPricingDetailsTO.setRateType("CC");
		}
		if (!StringUtil.isEmptyDouble(consignmentDO.getDiscount())) {
			cNPricingDetailsTO.setDiscount(consignmentDO.getDiscount());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
			cNPricingDetailsTO.setTopayChg(consignmentDO.getTopayAmt());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getSplChg())) {
			cNPricingDetailsTO.setSplChg(consignmentDO.getSplChg());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getDeclaredValue())) {
			cNPricingDetailsTO.setDeclaredvalue(consignmentDO
					.getDeclaredValue());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
			cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
			cNPricingDetailsTO.setLcAmount(consignmentDO.getLcAmount());
		}

		if (!StringUtil.isNull(product)) {
			if (product.getProductName().equalsIgnoreCase("Emotional Bond")) {
				cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO
						.getEbPreferencesCodes());
			}
			if (product.getProductName().equalsIgnoreCase("Priority")) {
				cNPricingDetailsTO.setServicesOn(consignmentDO.getServicedOn());
			}
		}
		
		String productCode = product.getConsgSeries();

		switch (productCode) {
		case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
			if (StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.BOOKING_RETURN_NULL_FOR_LC);
			}
			break;

		case CommonConstants.PRODUCT_SERIES_CASH_COD:
			if (StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.BOOKING_RETURN_NULL_FOR_COD);
			}
			break;

		}
		
		consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
		LOGGER.debug("BulkBookingConverter :: convertConsignmentDOToTO() :: END");
		return consignmentTO;
	}

	private static ConsignmentTO setUpConsignmentDtls(
			ConsignmentDO consignmentDO) throws CGBusinessException {
		LOGGER.debug("BulkBookingConverter::setUpConsignmentDtls::START----->");
		ConsignmentTO consgTO = new ConsignmentTO();
		convertConsignmentDO2TO(consignmentDO, consgTO);
		LOGGER.debug("BulkBookingConverter::setUpConsignmentDtls::END----->");
		return consgTO;
	}

	private static void convertConsignmentDO2TO(ConsignmentDO consgDO,
			ConsignmentTO consgTO) throws CGBusinessException {
		LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::START----->");
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		if (!StringUtil.isNull(consgDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consgDO.getDestPincodeId(),
					destPin);
			consgTO.setDestPincode(destPin);
		}

		if (!StringUtil.isNull(consgDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter
					.createToFromDomain(consgDO.getConsgType(), typeTO);
			consgTO.setTypeTO(typeTO);
		}
		if (!StringUtil.isNull(consgDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
					insuredBy);
			consgTO.setInsuredByTO(insuredBy);
		}
		if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(consgDO.getVolWeight());
			volWeightDtls.setHeight(consgDO.getHeight());
			volWeightDtls.setLength(consgDO.getLength());
			volWeightDtls.setBreadth(consgDO.getBreath());
			consgTO.setVolWightDtls(volWeightDtls);
			consgTO.setVolWeight(consgDO.getVolWeight());
			consgTO.setFinalWeight(consgDO.getFinalWeight());
		}
		LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::END----->");

	}

	private static void setUpAlternateConsignee(List<String> backdatedTOlst,
			ConsignmentDO consgDO) {
		if (!StringUtil.isStringEmpty(backdatedTOlst.get(26))
				|| !StringUtil.isStringEmpty(backdatedTOlst.get(27))
				|| !StringUtil.isStringEmpty(backdatedTOlst.get(28))
				|| !StringUtil.isStringEmpty(backdatedTOlst.get(29))
				|| !StringUtil.isStringEmpty(backdatedTOlst.get(30))) {
			LOGGER.debug("BillingCommonConverter::setUpAlternateConsignee::START----->");
			ConsigneeConsignorDO cneCnrDtls = new ConsigneeConsignorDO();
			StringBuilder cneAddress = new StringBuilder();
			cneCnrDtls.setFirstName(backdatedTOlst.get(3));
			cneCnrDtls.setPhone(backdatedTOlst.get(11));
			cneCnrDtls.setMobile(backdatedTOlst.get(12));
			cneAddress.append(backdatedTOlst.get(26).isEmpty() ? "~"
					: backdatedTOlst.get(26));
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(backdatedTOlst.get(27).isEmpty() ? "~"
					: backdatedTOlst.get(27));
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(backdatedTOlst.get(29).isEmpty() ? "~"
					: backdatedTOlst.get(29));
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(backdatedTOlst.get(30).isEmpty() ? "~"
					: backdatedTOlst.get(30));
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(backdatedTOlst.get(28).isEmpty() ? "~"
					: backdatedTOlst.get(28));
			cneCnrDtls.setAddress(cneAddress.toString());
			cneCnrDtls.setPartyType(BookingConstants.CONSIGNEE);
			cneCnrDtls.setCreatedBy(consgDO.getCreatedBy());
			cneCnrDtls.setCreatedDate(new Date());
			consgDO.setAltConsigneeAddr(cneCnrDtls);
			LOGGER.debug("BillingCommonConverter::setUpAlternateConsignee::END----->");
		}
	}

	private static void setUpConsignee(List<String> backdatedTOlst,
			ConsignmentDO consgDO) {
		LOGGER.debug("BillingCommonConverter::setUpConsignee::START----->");
		ConsigneeConsignorDO cneCnrDtls = new ConsigneeConsignorDO();

		StringBuilder cneAddress = new StringBuilder();
		cneCnrDtls.setFirstName(backdatedTOlst.get(3));
		cneCnrDtls.setPhone(backdatedTOlst.get(11));
		cneCnrDtls.setMobile(backdatedTOlst.get(12));

		cneAddress.append(backdatedTOlst.get(5).isEmpty() ? "~"
				: backdatedTOlst.get(5));
		cneAddress.append(CommonConstants.COMMA);
		cneAddress.append(backdatedTOlst.get(6).isEmpty() ? "~"
				: backdatedTOlst.get(6));
		cneAddress.append(CommonConstants.COMMA);
		cneAddress.append(backdatedTOlst.get(8).isEmpty() ? "~"
				: backdatedTOlst.get(8));
		cneAddress.append(CommonConstants.COMMA);
		cneAddress.append(backdatedTOlst.get(9).isEmpty() ? "~"
				: backdatedTOlst.get(9));
		cneAddress.append(CommonConstants.COMMA);
		cneAddress.append(backdatedTOlst.get(7).isEmpty() ? "~"
				: backdatedTOlst.get(7));
		cneCnrDtls.setAddress(cneAddress.toString());
		cneCnrDtls.setPartyType(BookingConstants.CONSIGNEE);
		cneCnrDtls.setCreatedBy(consgDO.getCreatedBy());
		cneCnrDtls.setCreatedDate(new Date());
		consgDO.setConsignee(cneCnrDtls);
		LOGGER.debug("BillingCommonConverter::setUpConsignee::END----->");
	}

	/**
	 * Gets the row content.
	 * 
	 * @param column
	 *            the column
	 * @param index
	 *            the index
	 * @param bulkBookingTO
	 *            the bulk booking to
	 * @return the row content
	 */
	@SuppressWarnings("unused")
	private static CreditCustomerBookingParcelTO getRowContent(String column,
			int index, CreditCustomerBookingParcelTO bulkBookingTO,
			List<String> consgnumbers) {
		LOGGER.debug("BillingCommonConverter::getRowContent::START----->");
		StringBuilder cneAddress = new StringBuilder();
		switch (index) {
		case BookingExcelConstants.DATE_INDEX:
			bulkBookingTO.setBookingDate(column);
			break;

		case BookingExcelConstants.AWB_NO_INDEX:
			bulkBookingTO.setConsgNumber(consgnumbers.get(index));
			break;

		case BookingExcelConstants.REF_NO_INDEX:
			bulkBookingTO.setRefNo(column);
			break;

		case BookingExcelConstants.CONSIGNEE_NAME_INDEX:
			bulkBookingTO.getConsignee().setFirstName(column);
			break;

		case BookingExcelConstants.PRODUCT_INDEX:
			bulkBookingTO.setContent(column);
			break;

		case BookingExcelConstants.ADDRESS_INDEX:
			cneAddress.append(column);
			// bulkBookingTO.getConsignee().setAddress(column);
			break;

		case BookingExcelConstants.CITY_INDEX:
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(column);
			break;

		case BookingExcelConstants.PINCODE_INDEX:
			bulkBookingTO.setPincode(column);
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(column);
			break;

		case BookingExcelConstants.STATE_INDEX:
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(column);
			break;

		case BookingExcelConstants.COUNTRY_INDEX:
			cneAddress.append(CommonConstants.COMMA);
			cneAddress.append(column);
			bulkBookingTO.getConsignee().setAddress(cneAddress.toString());
			break;

		case BookingExcelConstants.ACT_WT_INDEX:
			bulkBookingTO.setActualWeight(Double.parseDouble(column));
			break;

		case BookingExcelConstants.PHONE_INDEX:
			bulkBookingTO.getConsignee().setPhone(column);
			break;

		case BookingExcelConstants.MOBILE_INDEX:
			bulkBookingTO.getConsignee().setMobile(column);
			break;

		case BookingExcelConstants.TRNS_NO_INDEX:
			bulkBookingTO.setTransactionNo(column);
			break;

		case BookingExcelConstants.COD_NO_INDEX:
			bulkBookingTO.setCodAmount(Double.parseDouble(column));
			break;

		case BookingExcelConstants.QNTY_INDEX:
			bulkBookingTO.setNoOfPieces(Integer.parseInt(column));
			break;

		case BookingExcelConstants.VENDOR_INDEX:
			bulkBookingTO.getBulkBookingVendorDtlsTO().setVendorName(column);
			break;

		case BookingExcelConstants.VENDOR_LOC_INDEX:
			bulkBookingTO.getBulkBookingVendorDtlsTO().setVendorPickupLoc(
					column);
			break;

		case BookingExcelConstants.VENDOR_CONTACT_INDEX:
			bulkBookingTO.getBulkBookingVendorDtlsTO().setVendorContactNo(
					column);
			break;

		case BookingExcelConstants.PAYMENT_METHOD_INDEX:
			bulkBookingTO.getBulkBookingVendorDtlsTO().setPaymentMethodType(
					column);
			break;

		case BookingExcelConstants.ORDER_DATE_INDEX:
			bulkBookingTO.setOrderDate(column);
			break;
		case BookingExcelConstants.LC_AMT_INDEX:
			bulkBookingTO.getCnPricingDtls().setLcAmount(
					Double.parseDouble(column));
			break;
		case BookingExcelConstants.LC_BANK_INDEX:
			bulkBookingTO.getCnPricingDtls().setBankName(column);
			break;

		default:
			break;

		}
		LOGGER.debug("BillingCommonConverter::getRowContent::END----->");
		return bulkBookingTO;

	}

	/**
	 * Bulk BookingDomainConverter converter.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<BookingDO> bulkBookingDomainConverter(
			List<CreditCustomerBookingParcelTO> bookingTOs,
			List<String> cnNoList) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BulkBookingConverter::bulkBookingDomainConverter::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<BookingDO> bookings = new ArrayList(bookingTOs.size());
		for (CreditCustomerBookingParcelTO bookingTO : bookingTOs) {
			BookingDO booking = convert(bookingTO);
			// Collections.
			/*
			 * if(!StringUtil.isNull(cnNoList)){ String cnNumber =
			 * cnNoList.remove(0);
			 * booking.setConsgNumber(cnNumber.toUpperCase());
			 * bookingTO.setConsgNumber(cnNumber.toUpperCase()); }
			 */
			booking.setNoOfPieces(bookingTO.getNoOfPieces());
			CustomerDO custDO = new CustomerDO();
			custDO.setCustomerId(bookingTO.getCustomerId());
			booking.setCustomerId(custDO);
			booking.setRefNo(bookingTO.getRefNo());
			// Setting Content
			if (!StringUtil.isEmptyInteger(bookingTO.getCnContentId())) {
				CNContentDO cnContent = new CNContentDO();
				cnContent.setCnContentId(bookingTO.getCnContentId());
				booking.setCnContentId(cnContent);
			} else {
				booking.setOtherCNContent(bookingTO.getOtherCNContent());
			}
			// Settting Bulk Vendor details
			if (!StringUtil.isNull(bookingTO.getBulkBookingVendorDtlsTO())
					&& StringUtils.isNotEmpty(bookingTO
							.getBulkBookingVendorDtlsTO().getVendorName())) {
				BulkBookingVendorDtlsDO bulkBookingVendor = BookingUtils
						.setupBulkBookingVendorDtls(bookingTO
								.getBulkBookingVendorDtlsTO());
				booking.setBulkBookingVendorDtls(bulkBookingVendor);
			}
			if (StringUtils.isNotEmpty(bookingTO.getInsuredBy())) {
				InsuredByDO insuredBy = new InsuredByDO();
				insuredBy.setInsuredById(bookingTO.getInsuredById());
				booking.setInsuredBy(insuredBy);
			}

			if (StringUtils.isNotEmpty(bookingTO.getPolicyNo())) {
				booking.setInsurencePolicyNo(bookingTO.getPolicyNo());
			}
			if (bookingTO.getDeclaredValue() != null
					&& bookingTO.getDeclaredValue() != -1) {
				booking.setDeclaredValue(bookingTO.getDeclaredValue());
			}
			bookings.add(booking);
		}
		LOGGER.debug("BulkBookingConverter::bulkBookingDomainConverter::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return bookings;

	}

	public static ConsigneeConsignorDO setupConsignorAddress(
			Integer customerId, BookingCommonService bookingCommonService)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BulkBookingConverter::bulkBookingDomainConverter::Start------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		CustomerTO customer = bookingCommonService.getCustomer(customerId);
		ConsigneeConsignorDO cntAddress = new ConsigneeConsignorDO();
		if (!StringUtil.isNull(customer)) {
			PickupDeliveryAddressTO address = customer.getAddress();
			if (!StringUtil.isNull(address)) {
				StringBuilder addressBuilder = new StringBuilder();
				addressBuilder.append(address.getAddress1());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress2());
				addressBuilder.append(",");
				addressBuilder.append(address.getAddress3());
				cntAddress.setAddress(addressBuilder.toString());
				cntAddress.setFirstName(customer.getBusinessName());
				cntAddress.setMobile(address.getMobile());
				cntAddress.setPartyType(BookingConstants.CONSIGNOR);
				cntAddress.setPhone(address.getPhone());
			}
		}
		LOGGER.debug("BulkBookingConverter::bulkBookingDomainConverter::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return cntAddress;

	}
}
