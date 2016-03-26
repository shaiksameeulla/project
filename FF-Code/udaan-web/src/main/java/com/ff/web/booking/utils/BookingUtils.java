package com.ff.web.booking.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

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
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.BulkBookingVendorDtlsTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPaymentDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.ConsignmentRateDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.PrivilegeCardDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.PincodeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.operations.ratequotation.ConsignmentRateTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingExcelConstants;
import com.ff.web.consignment.dao.ConsignmentDAO;

/**
 * The Class BookingUtils.
 */
public class BookingUtils {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(BookingUtils.class);

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookingUtils.class);

	/**
	 * Sets the up consignee consignor details.
	 * 
	 * @param cneCnrTO
	 *            the cne cnr to
	 * @return the consignee consignor do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ConsigneeConsignorDO setUpConsigneeConsignorDetails(
			ConsignorConsigneeTO cneCnrTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingUtils::setUpConsigneeConsignorDetails::START------------>:::::::");
		ConsigneeConsignorDO cneCnrDtls = new ConsigneeConsignorDO();
		CGObjectConverter.createDomainFromTo(cneCnrTO, cneCnrDtls);
		cneCnrDtls.setCreatedBy(cneCnrTO.getCreatedBy());
		cneCnrDtls.setUpdatedBy(cneCnrTO.getUpdatedBy());
		cneCnrDtls.setCreatedDate(new Date());
		
		LOGGER.debug("BookingUtils::setUpConsigneeConsignorDetails::END------------>:::::::");
		return cneCnrDtls;
	}

	/**
	 * Sets the up payment details.
	 * 
	 * @param payment
	 *            the payment
	 * @return the booking payment do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static BookingPaymentDO setUpPaymentDetails(BookingPaymentTO payment)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingUtils::setUpConsigneeConsignorDetails::START------------>:::::::");
		BookingPaymentDO paymentDtls = new BookingPaymentDO();
		CGObjectConverter.createDomainFromTo(payment, paymentDtls);
		if (StringUtils.isNotEmpty(payment.getChqDateStr()))
			paymentDtls.setChqDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(payment
							.getChqDateStr()));
		if (StringUtils.isNotEmpty(payment.getPaymentMode()))
			paymentDtls.setPaymentModeId(Integer.parseInt(payment
					.getPaymentMode().split("#")[0]));
		LOGGER.debug("BookingUtils::setUpConsigneeConsignorDetails::END------------>:::::::");
		return paymentDtls;
	}

	/**
	 * Sets the up privilege card dtls.
	 * 
	 * @param payment
	 *            the payment
	 * @param bookingType
	 *            the booking type
	 * @return the privilege card transaction do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static PrivilegeCardTransactionDO setUpPrivilegeCardDtls(
			BookingPaymentTO payment, BookingTypeDO bookingType,
			String consgNumber) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingUtils::setUpPrivilegeCardDtls::Start------------>:::::::");
		PrivilegeCardTransactionDO privgCardTransDO = new PrivilegeCardTransactionDO();
		privgCardTransDO.setAmount(payment.getPrivilegeCardAmt());
		PrivilegeCardDO privgCardNo = new PrivilegeCardDO();
		privgCardNo.setPrivilegeCardId(payment.getPrivilegeCardId());
		privgCardTransDO.setPrivilegeCard(privgCardNo);
		privgCardTransDO.setBookingType(bookingType);
		privgCardTransDO.setConsgNumber(consgNumber);
		privgCardTransDO.setCreatedBy(payment.getCreatedBy());
		privgCardTransDO.setUpdatedBy(payment.getCreatedBy());
		privgCardTransDO.setCreatedDate(new Date());
		LOGGER.debug("BookingUtils::setUpPrivilegeCardDtls::END------------>:::::::");
		return privgCardTransDO;
	}

	/**
	 * Sets the up cn pricing details.
	 * 
	 * @param cnPricingDetailsTO
	 *            the cn pricing details to
	 * @return the cN pricing details do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void setUpCNPricingDetails(ConsignmentDO consgDO,
			CNPricingDetailsTO cnPricingDetailsTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BookingUtils::setUpCNPricingDetails::Start------------>:::::::");
		if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getDiscount()))
			consgDO.setDiscount(cnPricingDetailsTO.getDiscount());
		if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getCodAmt()))
			consgDO.setCodAmt(cnPricingDetailsTO.getCodAmt());
		String consgSeries = consgDO.getConsgNo()
				.substring(4, 5);
		if (!CGCollectionUtils.isEmpty(consgDO.getConsgRateDtls())) {
			if (StringUtils.equalsIgnoreCase("T",
					consgSeries)) {
				for (ConsignmentBillingRateDO dos : consgDO.getConsgRateDtls()) {
					if (dos.getRateCalculatedFor().equalsIgnoreCase("B"))
						consgDO.setTopayAmt(dos.getGrandTotalIncludingTax());
				}
			}
		}
		/*if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getSplChg()))
			consgDO.setSplChg(cnPricingDetailsTO.getSplChg());*/
		//changes made to get screen input for spl charges while booking
				if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getInputSplChg()))
					consgDO.setSplChg(cnPricingDetailsTO.getInputSplChg());
				//code ends here
		if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getDeclaredvalue()))
			consgDO.setDeclaredValue(cnPricingDetailsTO.getDeclaredvalue());
		if (StringUtils.isNotEmpty(cnPricingDetailsTO.getBankName()))
			consgDO.setLcBankName(cnPricingDetailsTO.getBankName());
		if (!StringUtil.isEmptyDouble(cnPricingDetailsTO.getLcAmount()))
			consgDO.setLcAmount(cnPricingDetailsTO.getLcAmount());
		if (StringUtils.isNotEmpty(cnPricingDetailsTO.getEbPreferencesCodes()))
			consgDO.setEbPreferencesCodes(cnPricingDetailsTO
					.getEbPreferencesCodes());
		if (StringUtils.isNotEmpty(cnPricingDetailsTO.getRateType()))
			consgDO.setRateType(cnPricingDetailsTO.getRateType());
		if (StringUtils.isNotEmpty(cnPricingDetailsTO.getServicesOn()))
			consgDO.setServicedOn(cnPricingDetailsTO.getServicesOn());
		LOGGER.debug("BookingUtils::setUpCNPricingDetails::end------------>:::::::");
	}

	/**
	 * Sets the up consignment.
	 * 
	 * @param consingment
	 *            the consingment
	 * @return the consignment do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ConsignmentDO setUpConsignment(ConsignmentTO consingment)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingUtils::setUpConsignment::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		ConsignmentDO consgDO = new ConsignmentDO();
		CGObjectConverter.createDomainFromTo(consingment, consgDO);
		PincodeDO destPin = new PincodeDO();
		destPin.setPincodeId(consingment.getDestPincode().getPincodeId());
		consgDO.setDestPincodeId(destPin);
		consgDO.setCreatedDate(new Date());
		consgDO.setEventDate(new Date());
		consgDO.setCreatedBy(consingment.getCreatedBy());
		consgDO.setUpdatedBy(consingment.getUpdatedBy());
		ConsignmentTypeDO congType = new ConsignmentTypeDO();
		if (!StringUtil.isEmptyInteger(consingment.getConsgTypeId())) {
			congType.setConsignmentId(consingment.getConsgTypeId());
			consgDO.setConsgType(congType);
		}
		ProcessDO process = new ProcessDO();
		process.setProcessId(consingment.getUpdatedProcessFrom().getProcessId());
		consgDO.setUpdatedProcess(process);
		// Setting DY sync flags
		if (!StringUtil.isEmptyInteger(consingment.getConsgId())) {
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
		} else {
			consgDO.setDtToCentral(CommonConstants.NO);
		}
		consgDO.setConsgStatus("B");
		// Setting child CNS
		if (!StringUtil.isEmptyColletion(consingment.getChildTOSet())) {
			Set<ChildConsignmentDO> chilsCns = BookingUtils
					.setUpChildConsignments(consingment.getChildTOSet());
			if (!StringUtil.isEmptyColletion(chilsCns)){
				for (ChildConsignmentDO childConsignmentDO : chilsCns) {
					childConsignmentDO.setConsignment(consgDO);
				}
				consgDO.setChildCNs(chilsCns);
			}
		}
		// Setting volweight
		if (!StringUtil.isNull(consingment.getVolWightDtls())) {
			consgDO.setVolWeight(consingment.getVolWightDtls().getVolWeight());
			consgDO.setHeight(consingment.getVolWightDtls().getHeight());
			consgDO.setBreath(consingment.getVolWightDtls().getBreadth());
			consgDO.setLength(consingment.getVolWightDtls().getLength());
		}
		
		if (!StringUtil.isNull(consingment.getCnContents())) {
			CNContentDO cnContent = new CNContentDO();
			cnContent.setCnContentId(consingment.getCnContents()
					.getCnContentId());
			consgDO.setCnContentId(cnContent);

			if (!StringUtil.isNull(consingment.getCnContents()
					.getOtherContent())) {
				consgDO.setOtherCNContent(consingment.getCnContents()
						.getOtherContent());
			}
			else if (!StringUtil.isNull(consingment.getOtherCNContent())) {
				consgDO.setOtherCNContent(consingment.getOtherCNContent());
			}
			else{
				consgDO.setOtherCNContent(null);
			}
		
		}

		
		if (!StringUtil.isNull(consingment.getCnPaperWorks())) {
			CNPaperWorksDO paperWork = new CNPaperWorksDO();
			paperWork.setCnPaperWorkId(consingment.getCnPaperWorks()
					.getCnPaperWorkId());
			consgDO.setCnPaperWorkId(paperWork);
			consgDO.setPaperWorkRefNo(consingment.getCnPaperWorks()
					.getPaperWorkRefNum());
		}
		if (!StringUtil.isNull(consingment.getInsuredByTO())) {
			InsuredByDO insuredBy = new InsuredByDO();
			insuredBy.setInsuredById(consingment.getInsuredByTO()
					.getInsuredById());
			consgDO.setInsuredBy(insuredBy);
			consgDO.setInsurencePolicyNo(consingment.getInsuredByTO()
					.getPolicyNo());
		}
		consgDO.setMobileNo(consingment.getMobileNo());
		// Setting consigee and consignor
		if (!StringUtil.isNull(consingment.getConsigneeTO())) {
			ConsigneeConsignorDO consignee = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsigneeTO());
			consgDO.setConsignee(consignee);
		}
		if (!StringUtil.isNull(consingment.getConsignorTO())) {
			ConsigneeConsignorDO consignor = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsignorTO());
			consgDO.setConsignor(consignor);
		}
		if (!StringUtil.isNull(consingment.getAltConsigneeAddrTO())) {
			ConsigneeConsignorDO consignor = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getAltConsigneeAddrTO());
			consgDO.setAltConsigneeAddr(consignor);
		}

		// Setup Consignment pricing details
		if (!CGCollectionUtils.isEmpty(consingment.getConsgRateOutputTOs())) {
			Set<ConsignmentBillingRateDO> consgRateDtls = BookingUtils
					.setupConsgRateDtls(consingment.getConsgNo(),
							consingment.getConsgRateOutputTOs());
			if(!CGCollectionUtils.isEmpty(consgRateDtls) && consgRateDtls.iterator().hasNext()){
				consgRateDtls.iterator().next().setConsignmentDO(consgDO);
			}else{
				LOGGER.debug("BookingUtils::setUpConsignment::consg rate is null------------>:::::::");
			}
			
			for(ConsignmentBillingRateDO cRate:consgRateDtls){
				cRate.setCreatedBy(consingment.getCreatedBy());
				cRate.setUpdatedBy(consingment.getUpdatedBy());
			}
			consgDO.setConsgRateDtls(consgRateDtls);
		}

		// Setup Consignment pricing details
		if (!StringUtil.isNull(consingment.getConsgPriceDtls())) {
			setUpCNPricingDetails(consgDO, consingment.getConsgPriceDtls());
		}
		LOGGER.debug("BookingUtils::setUpConsignment::end------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return consgDO;
	}

	public static ConsignmentDO prepareConsignment(ConsignmentTO consingment,
			Set<ConsignmentBillingRateDO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = new ConsignmentDO();
		consgDO.setConsgStatus("B");
		CGObjectConverter.createDomainFromTo(consingment, consgDO);
		consgDO.setCreatedDate(new Date());
		consgDO.setEventDate(new Date());
		PincodeDO destPin = new PincodeDO();
		destPin.setPincodeId(consingment.getDestPincode().getPincodeId());
		consgDO.setDestPincodeId(destPin);
		ConsignmentTypeDO congType = new ConsignmentTypeDO();
		if (!StringUtil.isEmptyInteger(consingment.getConsgTypeId())) {
			congType.setConsignmentId(consingment.getConsgTypeId());
			consgDO.setConsgType(congType);
		}
		ProcessDO process = new ProcessDO();
		process.setProcessId(consingment.getUpdatedProcessFrom().getProcessId());
		consgDO.setUpdatedProcess(process);
		// Setting DY sync flags
		if (!StringUtil.isEmptyInteger(consingment.getConsgId())) {
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
		} else {
			consgDO.setDtToCentral(CommonConstants.NO);
		}
		// Setting child CNS
		if (!StringUtil.isEmptyColletion(consingment.getChildTOSet())) {
			Set<ChildConsignmentDO> chilsCns = BookingUtils
					.setUpChildConsignments(consingment.getChildTOSet());
			if (!StringUtil.isEmptyColletion(chilsCns)){
				consgDO.setChildCNs(chilsCns);
			}
		}
		// Setting volweight
		if (!StringUtil.isNull(consingment.getVolWightDtls())) {
			consgDO.setVolWeight(consingment.getVolWightDtls().getVolWeight());
			consgDO.setHeight(consingment.getVolWightDtls().getHeight());
			consgDO.setBreath(consingment.getVolWightDtls().getBreadth());
			consgDO.setLength(consingment.getVolWightDtls().getLength());
		}
		if (!StringUtil.isNull(consingment.getCnContents())) {
			CNContentDO cnContent = new CNContentDO();
			cnContent.setCnContentId(consingment.getCnContents()
					.getCnContentId());
			consgDO.setCnContentId(cnContent);
			consgDO.setOtherCNContent(consingment.getCnContents()
					.getOtherContent());
		}
		if (!StringUtil.isNull(consingment.getCnPaperWorks())) {
			CNPaperWorksDO paperWork = new CNPaperWorksDO();
			paperWork.setCnPaperWorkId(consingment.getCnPaperWorks()
					.getCnPaperWorkId());
			consgDO.setCnPaperWorkId(paperWork);
			consgDO.setPaperWorkRefNo(consingment.getCnPaperWorks()
					.getPaperWorkRefNum());
		}
		if (!StringUtil.isNull(consingment.getInsuredByTO())) {
			InsuredByDO insuredBy = new InsuredByDO();
			insuredBy.setInsuredById(consingment.getInsuredByTO()
					.getInsuredById());
			consgDO.setInsuredBy(insuredBy);
			consgDO.setInsurencePolicyNo(consingment.getInsuredByTO()
					.getPolicyNo());
		}
		consgDO.setMobileNo(consingment.getMobileNo());
		// Setting consigee and consignor
		if (!StringUtil.isNull(consingment.getConsigneeTO())) {
			ConsigneeConsignorDO consignee = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsigneeTO());
			consgDO.setConsignee(consignee);
		}
		if (!StringUtil.isNull(consingment.getConsignorTO())) {
			ConsigneeConsignorDO consignor = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsignorTO());
			consgDO.setConsignor(consignor);
		}
		
		
		if (!StringUtil.isNull(consingment.getBaAmount())) {
			consgDO.setBaAmt(consingment.getBaAmount());
		}
		if (!StringUtil.isNull(consingment.getCodAmt())) {
			consgDO.setCodAmt(consingment.getCodAmt());
		}
		
		// Setup Consignment pricing details

		if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
			ConsignmentBillingRateDO rateDO=consgRateDtls.iterator().next();
			rateDO.setConsignmentDO(consgDO);
			consgDO.setConsgRateDtls(consgRateDtls);
			StringBuffer rateDetails=new StringBuffer("ConsignmentRate Dtsl :for  Consg No :[");
			rateDetails.append(consgDO.getConsgNo());
			rateDetails.append("] Cod Amount :[");
			rateDetails.append(rateDO.getCodAmount());
			rateDetails.append("] LC Amount :[");
			rateDetails.append(rateDO.getLcAmount());
			LOGGER.info("BookingUtils :: prepareConsignment() ::Consg rate details "+rateDetails);
		}

		if (!StringUtil.isNull(consingment.getConsgPriceDtls())) {
			setUpCNPricingDetails(consgDO, consingment.getConsgPriceDtls());
		}
		return consgDO;
	}

	public static ConsignmentDO setUpDoxConsignment(ConsignmentTO consingment)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = new ConsignmentDO();
		consgDO.setConsgStatus("B");
		CGObjectConverter.createDomainFromTo(consingment, consgDO);
		PincodeDO destPin = new PincodeDO();
		destPin.setPincodeId(consingment.getDestPincode().getPincodeId());
		consgDO.setDestPincodeId(destPin);
		ConsignmentTypeDO congType = new ConsignmentTypeDO();
		if (!StringUtil.isEmptyInteger(consingment.getConsgTypeId())) {
			congType.setConsignmentId(consingment.getConsgTypeId());
			consgDO.setConsgType(congType);
		}
		ProcessDO process = new ProcessDO();
		process.setProcessId(consingment.getUpdatedProcessFrom().getProcessId());
		consgDO.setUpdatedProcess(process);
		// Setting DY sync flags
		if (!StringUtil.isEmptyInteger(consingment.getConsgId())) {
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
		} else {
			consgDO.setDtToCentral(CommonConstants.NO);
		}
		// Setting consigee and consignor
		if (!StringUtil.isNull(consingment.getConsigneeTO())) {
			ConsigneeConsignorDO consignee = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsigneeTO());
			consgDO.setConsignee(consignee);
		}
		if (!StringUtil.isNull(consingment.getConsignorTO())) {
			ConsigneeConsignorDO consignor = BookingUtils
					.setUpConsigneeConsignorDetails(consingment
							.getConsignorTO());
			consgDO.setConsignor(consignor);
		}
		// Setup Consignment pricing details
		if (!StringUtil.isNull(consingment.getConsgPriceDtls())) {
			setUpCNPricingDetails(consgDO, consingment.getConsgPriceDtls());
		}
		return consgDO;
	}

	/**
	 * This is created by SH
	 * 
	 * @param consingment
	 * @param consignmentDAO
	 * @param bookingDoList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ConsignmentDO setUpConsignment(ConsignmentTO consingment,
			ConsignmentDAO consignmentDAO, List<BookingDO> bookingDoList)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = new ConsignmentDO();
		consgDO.setConsgStatus("B");
		CGObjectConverter.createDomainFromTo(consingment, consgDO);
		PincodeDO destPin = new PincodeDO();
		destPin.setPincodeId(consingment.getDestPincode().getPincodeId());
		consgDO.setDestPincodeId(destPin);
		ConsignmentTypeDO congType = new ConsignmentTypeDO();
		if (!StringUtil.isEmptyInteger(consingment.getConsgTypeId())) {
			congType.setConsignmentId(consingment.getConsgTypeId());
			consgDO.setConsgType(congType);
		}
		ProcessDO process = new ProcessDO();
		process.setProcessId(consingment.getUpdatedProcessFrom().getProcessId());
		consgDO.setUpdatedProcess(process);
		// Setting DY sync flags
		if (!StringUtil.isEmptyInteger(consingment.getConsgId())) {
			consgDO.setDtUpdateToCentral(CommonConstants.YES);
			consgDO.setDtToCentral(CommonConstants.NO);
		} else {
			consgDO.setDtToCentral(CommonConstants.NO);
		}
		// Setting child CNS
		if (!StringUtil.isEmptyColletion(consingment.getChildTOSet())) {
			Set<ChildConsignmentDO> chilsCns = BookingUtils
					.setUpChildConsignments(consingment.getChildTOSet(),
							consignmentDAO);
			if (!StringUtil.isEmptyColletion(chilsCns))
				consgDO.setChildCNs(chilsCns);
		}
		// Setting volweight
		if (!StringUtil.isNull(consingment.getVolWightDtls())) {
			consgDO.setVolWeight(consingment.getVolWightDtls().getVolWeight());
			consgDO.setHeight(consingment.getVolWightDtls().getHeight());
			consgDO.setBreath(consingment.getVolWightDtls().getBreadth());
			consgDO.setLength(consingment.getVolWightDtls().getLength());
		}
		if (!StringUtil.isNull(consingment.getCnContents())) {
			CNContentDO cnContent = new CNContentDO();
			cnContent.setCnContentId(consingment.getCnContents()
					.getCnContentId());
			consgDO.setCnContentId(cnContent);
			consgDO.setOtherCNContent(consingment.getCnContents()
					.getOtherContent());
		}
		if (!StringUtil.isNull(consingment.getCnPaperWorks())) {
			CNPaperWorksDO paperWork = new CNPaperWorksDO();
			paperWork.setCnPaperWorkId(consingment.getCnPaperWorks()
					.getCnPaperWorkId());
			consgDO.setCnPaperWorkId(paperWork);
			consgDO.setPaperWorkRefNo(consingment.getCnPaperWorks()
					.getPaperWorkRefNum());
		}
		if (!StringUtil.isNull(consingment.getInsuredByTO())) {
			InsuredByDO insuredBy = new InsuredByDO();
			insuredBy.setInsuredById(consingment.getInsuredByTO()
					.getInsuredById());
			consgDO.setInsuredBy(insuredBy);
			consgDO.setInsurencePolicyNo(consingment.getInsuredByTO()
					.getPolicyNo());
		}
		consgDO.setMobileNo(consingment.getMobileNo());
		// Setting consigee and consignor
		// TODO:set consignee details
		ConsigneeConsignorDO party = null;
		consgDO.setConsignee(party);
		consgDO.setConsignor(party);
		// Setup Consignment pricing details
		if (!StringUtil.isNull(consingment.getConsgPriceDtls())) {
			setUpCNPricingDetails(consgDO, consingment.getConsgPriceDtls());
			// consgPricigDtls.setConsignment(consgDO);
			// consgDO.setConsgPricingDtls(consgPricigDtls);
		}

		return consgDO;
	}

	/**
	 * Sets the up consg content.
	 * 
	 * @param cnContent
	 *            the cn content
	 * @return the cN content do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static CNContentDO setUpConsgContent(CNContentTO cnContent)
			throws CGBusinessException, CGSystemException {
		CNContentDO consgContent = new CNContentDO();
		CGObjectConverter.createDomainFromTo(cnContent, consgContent);
		return consgContent;
	}

	/**
	 * Sets the up consg paper works.
	 * 
	 * @param cnPaperwork
	 *            the cn paperwork
	 * @return the cN paper works do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static CNPaperWorksDO setUpConsgPaperWorks(CNPaperWorksTO cnPaperwork)
			throws CGBusinessException, CGSystemException {
		CNPaperWorksDO consgPaperwork = new CNPaperWorksDO();
		CGObjectConverter.createDomainFromTo(cnPaperwork, consgPaperwork);
		return consgPaperwork;
	}

	/**
	 * Sets the up child consignments.
	 * 
	 * @param childCNs
	 *            the child c ns
	 * @return the sets the
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static Set<ChildConsignmentDO> setUpChildConsignments(
			Set<ChildConsignmentTO> childCNs) throws CGBusinessException,
			CGSystemException {
		Set<ChildConsignmentDO> childCnsSet = new HashSet<ChildConsignmentDO>();
		for (ChildConsignmentTO childCN : childCNs) {
			ChildConsignmentDO childCNDO = new ChildConsignmentDO();
			childCNDO.setChildConsgNumber(childCN.getChildConsgNumber());
			childCNDO.setChildConsgWeight(childCN.getChildConsgWeight());
			childCNDO.setDtToCentral(CommonConstants.NO);
			childCNDO.setCreatedBy(childCN.getCreatedBy());
			childCNDO.setUpdatedBy(childCN.getUpdatedBy());
			childCNDO.setCreatedDate(new Date());
			childCnsSet.add(childCNDO);
		}
		return childCnsSet;
	}

	public static Set<ChildConsignmentDO> setUpChildConsignments(
			Set<ChildConsignmentTO> childCNs, ConsignmentDAO consignmentDAO)
			throws CGBusinessException, CGSystemException {
		Set<ChildConsignmentDO> childCnsSet = new HashSet<ChildConsignmentDO>();
		for (ChildConsignmentTO childCN : childCNs) {
			ChildConsignmentDO childCNDO = new ChildConsignmentDO();
			childCNDO.setChildConsgNumber(childCN.getChildConsgNumber());
			childCNDO.setChildConsgWeight(childCN.getChildConsgWeight());
			// For Update
			Integer childCNId = consignmentDAO.getChildConsgIdByConsgNo(childCN
					.getChildConsgNumber());
			if (!StringUtil.isEmptyInteger(childCNId)) {
				childCNDO.setBookingChildCNId(childCNId);
				childCNDO.setDtUpdateToCentral(CommonConstants.YES);
				childCNDO.setDtToCentral(CommonConstants.NO);
			} else {
				childCNDO.setDtToCentral(CommonConstants.NO);
			}
			if(!StringUtil.isEmptyInteger(childCN.getConsignmentId())){
				ConsignmentDO consignmentDO = new ConsignmentDO();
				consignmentDO.setConsgId(childCN.getConsignmentId());
				childCNDO.setConsignment(consignmentDO);
			}
			childCnsSet.add(childCNDO);
		}
		return childCnsSet;
	}

	/**
	 * Sets the up child consignment t os.
	 * 
	 * @param childCNs
	 *            the child c ns
	 * @return the sets the
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static Set<ChildConsignmentTO> setUpChildConsignmentTOs(
			String childCNs) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BookingUtils::setUpChildConsignmentTOs::start------------>:::::::");
		Set<ChildConsignmentTO> childCnsSet = null;
		if (StringUtils.isNotEmpty(childCNs)) {
			childCnsSet = new HashSet<>();
			StringTokenizer st = new StringTokenizer(childCNs,
					CommonConstants.HASH);
			while (st.hasMoreTokens()) {
				ChildConsignmentTO childCN = new ChildConsignmentTO();
				String chilsCNDtls = st.nextToken();
				childCN.setChildConsgNumber(chilsCNDtls
						.split(CommonConstants.COMMA)[0]);
				childCN.setChildConsgWeight(Double.parseDouble(chilsCNDtls
						.split(CommonConstants.COMMA)[1]));
				childCnsSet.add(childCN);
			}
		}
		LOGGER.debug("BookingUtils::setUpChildConsignmentTOs::END------------>:::::::");
		return childCnsSet;
	}

	/**
	 * Sets the up consg pricing dtls.
	 * 
	 * @param cnPriceDtls
	 *            the cn price dtls
	 * @return the cN pricing details to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static CNPricingDetailsTO setUpConsgPricingDtls(String cnPriceDtls)
			throws CGBusinessException, CGSystemException {
		CNPricingDetailsTO cnPricingDetailsTO = null;
		if (StringUtils.isNotEmpty(cnPriceDtls)) {
			String[] priceDetaiilsToken = cnPriceDtls
					.split(CommonConstants.HASH);
			cnPricingDetailsTO = new CNPricingDetailsTO();
			// finalAmount#Freight Chg#Risk
			// SurChg#AirportHandlings#ServiceTax#Higher Edu Cess #Fuel Chg#
			// Topay Chg#Spl Chg#EduCessChg
			// finalPrice + "#" + fuelChg + "#" + riskSurChg + "#"
			// + topayChg + "#" + airportHandlingChg + "#" + splChg + "#"
			// + serviceTax + "#" + eduCessChg + "#" + higherEduCessChg + "#"
			// + freightChg;
			cnPricingDetailsTO.setFinalPrice(Double
					.parseDouble(priceDetaiilsToken[0]));
			cnPricingDetailsTO.setFreightChg(Double
					.parseDouble(priceDetaiilsToken[9]));
			cnPricingDetailsTO.setRiskSurChg(Double
					.parseDouble(priceDetaiilsToken[2]));
			cnPricingDetailsTO.setAirportHandlingChg(Double
					.parseDouble(priceDetaiilsToken[4]));
			cnPricingDetailsTO.setServiceTax(Double
					.parseDouble(priceDetaiilsToken[6]));
			cnPricingDetailsTO.setHigherEduCessChg(Double
					.parseDouble(priceDetaiilsToken[8]));
			cnPricingDetailsTO.setFuelChg(Double
					.parseDouble(priceDetaiilsToken[1]));
			cnPricingDetailsTO.setTopayChg(Double
					.parseDouble(priceDetaiilsToken[3]));
			cnPricingDetailsTO.setSplChg(Double
					.parseDouble(priceDetaiilsToken[5]));
			cnPricingDetailsTO.setEduCessChg(Double
					.parseDouble(priceDetaiilsToken[7]));
			if (priceDetaiilsToken.length > 10)
				cnPricingDetailsTO.setRateType(priceDetaiilsToken[10]);
		}
		return cnPricingDetailsTO;
	}

	/**
	 * Sets the up consignee consignor dtls.
	 * 
	 * @param cneCnrDtls
	 *            the cne cnr dtls
	 * @return the consignor consignee to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ConsignorConsigneeTO setUpConsigneeConsignorDtls(
			String cneCnrDtls) throws CGBusinessException, CGSystemException {
		ConsignorConsigneeTO cneCnrDtlsTO = null;
		if (StringUtils.isNotEmpty(cneCnrDtls)) {
			String[] priceDetaiilsToken = cneCnrDtls
					.split(CommonConstants.HASH);
			cneCnrDtlsTO = new ConsignorConsigneeTO();
			cneCnrDtlsTO.setFirstName(priceDetaiilsToken[0]);
			cneCnrDtlsTO.setAddress(priceDetaiilsToken[1]);
			cneCnrDtlsTO.setMobile(priceDetaiilsToken[2]);
			cneCnrDtlsTO.setPhone(priceDetaiilsToken[3]);
			cneCnrDtlsTO.setPartyType(priceDetaiilsToken[4]);
		}
		return cneCnrDtlsTO;
	}

	/**
	 * Validate file upload header.
	 * 
	 * @param excelHeaderList
	 *            the excel header list
	 * @return the boolean
	 */
	public static Boolean validateFileUploadHeader(List<String> excelHeaderList) {

		boolean result = true;
		List<String> headerLst = getHeaderList();
		if (excelHeaderList != null && !excelHeaderList.isEmpty()
				&& (headerLst.size() == excelHeaderList.size())) {
			for (int i = 0; i < headerLst.size(); i++) {
				if (!headerLst.get(i).equalsIgnoreCase(excelHeaderList.get(i))) {
					result = false;
					break;
				}
			}
		} else
			result = false;
		return result;
	}

	/**
	 * Gets the header list.
	 * 
	 * @return the header list
	 */
	public static List<String> getHeaderList() {

		List<String> headerList = new ArrayList<String>();
		headerList.add(BookingExcelConstants.CN_NO);
		headerList.add(BookingExcelConstants.CUSTOMER_CODE);
		headerList.add(BookingExcelConstants.CUST_REF_NO);
		headerList.add(BookingExcelConstants.BOOKING_TYPE);
		headerList.add(BookingExcelConstants.DOC_TYPE);
		headerList.add(BookingExcelConstants.PINCODE);
		headerList.add(BookingExcelConstants.WEIGHT);
		headerList.add(BookingExcelConstants.VOLUMETRIC_WT);
		headerList.add(BookingExcelConstants.CONSIGNOR_NAME);
		headerList.add(BookingExcelConstants.CONSIGNOR_MOBILE);
		headerList.add(BookingExcelConstants.CONSIGNOR_ADDRESS);
		headerList.add(BookingExcelConstants.CONSIGNEE_NAME);
		headerList.add(BookingExcelConstants.ADDRESS);
		headerList.add(BookingExcelConstants.PHONE);
		headerList.add(BookingExcelConstants.MOBILE);
		headerList.add(BookingExcelConstants.CONTENT);
		headerList.add(BookingExcelConstants.DECLARED_VALUE);
		headerList.add(BookingExcelConstants.INSURED_BY);
		headerList.add(BookingExcelConstants.POLICY_NO);
		headerList.add(BookingExcelConstants.PAPERWORKS);
		headerList.add(BookingExcelConstants.PAPERWORKS_NO);
		headerList.add(BookingExcelConstants.COD_AMOUT);
		headerList.add(BookingExcelConstants.PRIORITY_SERVICE);
		headerList.add(BookingExcelConstants.LC_AMT);
		headerList.add(BookingExcelConstants.LC_BANK);
		return headerList;
	}

	/**
	 * Validate bulk file upload header.
	 * 
	 * @param excelHeaderList
	 *            the excel header list
	 * @return the boolean
	 */
	public static Boolean validateBulkFileUploadHeader(
			List<String> excelHeaderList) {
		LOGGER.debug("BookingUtils::validateBulkFileUploadHeader::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		boolean result = true;
		List<String> headerLst = getBulkHeaderList();
		if (excelHeaderList != null && !excelHeaderList.isEmpty()
				&& (headerLst.size() == excelHeaderList.size())) {
			for (int i = 0; i < headerLst.size(); i++) {
				if (!headerLst.get(i).equalsIgnoreCase(excelHeaderList.get(i))) {
					result = false;
					break;
				}
			}
		} else
			result = false;
		LOGGER.debug("BookingUtils::validateBulkFileUploadHeader::End------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return result;
	}

	/**
	 * Gets the bulk header list.
	 * 
	 * @return the bulk header list
	 */
	public static List<String> getBulkHeaderList() {
		List<String> headerList = new ArrayList<String>();

		headerList.add(BookingExcelConstants.DATE);
		headerList.add(BookingExcelConstants.CONSG_NO);
		headerList.add(BookingExcelConstants.CUST_REF_NO);
		headerList.add(BookingExcelConstants.CONSIGNEE_NAME);
		headerList.add(BookingExcelConstants.PRODUCT);
		headerList.add(BookingExcelConstants.ADDRESS);
		headerList.add(BookingExcelConstants.CITY);
		headerList.add(BookingExcelConstants.PINCODE);
		headerList.add(BookingExcelConstants.STATE);
		headerList.add(BookingExcelConstants.COUNTRY);
		headerList.add(BookingExcelConstants.WEIGHT);
		headerList.add(BookingExcelConstants.PHONE);
		headerList.add(BookingExcelConstants.MOBILE);
		headerList.add(BookingExcelConstants.TRANSACTION_NO);
		headerList.add(BookingExcelConstants.COD_AMOUT);
		headerList.add(BookingExcelConstants.QTY);
		headerList.add(BookingExcelConstants.VENDOR);
		headerList.add(BookingExcelConstants.VENDOR_PICKUP_LOC);
		headerList.add(BookingExcelConstants.VENDOR_PICKUP_CONTACT_NO);
		headerList.add(BookingExcelConstants.PAYMENT_METHOD);
		headerList.add(BookingExcelConstants.ORDER_DATE);
		headerList.add(BookingExcelConstants.LC_AMT);
		headerList.add(BookingExcelConstants.LC_BANK);
		headerList.add(BookingExcelConstants.DECLARED_VALUE);
		headerList.add(BookingExcelConstants.INSURED_BY);
		headerList.add(BookingExcelConstants.POLICY_NO);
		headerList.add(BookingExcelConstants.ALT_ADDRESS);
		headerList.add(BookingExcelConstants.ALT_CITY);
		headerList.add(BookingExcelConstants.ALT_PINCODE);
		headerList.add(BookingExcelConstants.ALT_STATE);
		headerList.add(BookingExcelConstants.ALT_COUNTRY);
		return headerList;

	}

	public static ConsignmentTO setUpConsignmentDtls(ConsignmentDO consgDO)
			throws CGBusinessException {
		ConsignmentTO consgTO = null;
		consgTO = new ConsignmentTO();
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		if (!StringUtil.isNull(consgDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consgDO.getDestPincodeId(),
					destPin);
			consgTO.setDestPincode(destPin);
		}

		if (!StringUtil.isNull(consgDO.getChildCNs())) {
			StringBuilder chindCNDtls = new StringBuilder();
			for (ChildConsignmentDO childCN : consgDO.getChildCNs()) {
				chindCNDtls.append(childCN.getChildConsgNumber());
				chindCNDtls.append(CommonConstants.HASH);
				chindCNDtls.append(childCN.getChildConsgWeight());
			}
			consgTO.setChildCNsDtls(chindCNDtls.toString());
		}
		if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
			CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnPaperWorkId(),
					cnPaperworkTO);
			cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperworkTO);
		}
		if (!StringUtil.isNull(consgDO.getCnContentId())) {
			CNContentTO cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
					cnContentTO);
			cnContentTO.setOtherContent(consgDO.getOtherCNContent());
			consgTO.setCnContents(cnContentTO);
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
		}
		return consgTO;
	}

	public static BulkBookingVendorDtlsDO setupBulkBookingVendorDtls(
			BulkBookingVendorDtlsTO bulkBookingVendor)
			throws CGBusinessException, CGSystemException {
		BulkBookingVendorDtlsDO bulkBookingVendorDO = new BulkBookingVendorDtlsDO();
		CGObjectConverter.createDomainFromTo(bulkBookingVendor,
				bulkBookingVendorDO);
		return bulkBookingVendorDO;
	}

	public static ConsignorConsigneeTO setUpConsignorFromPickupDlvLoc(
			PickupDeliveryAddressTO pickDlvAddTO) throws CGBusinessException,
			CGSystemException {
		ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
		consignor.setFirstName(pickDlvAddTO.getName());
		StringBuilder cnrAdd = new StringBuilder();
		cnrAdd.append(pickDlvAddTO.getAddress1());
		cnrAdd.append(CommonConstants.COMMA);
		cnrAdd.append(pickDlvAddTO.getAddress2());
		cnrAdd.append(CommonConstants.COMMA);
		cnrAdd.append(pickDlvAddTO.getAddress3());
		cnrAdd.append(CommonConstants.COMMA);
		consignor.setAddress(cnrAdd.toString());
		consignor.setMobile(pickDlvAddTO.getMobile());
		consignor.setPhone(pickDlvAddTO.getPhone());
		return consignor;
	}

	// Reverse pickup entries
	public static ConsignorConsigneeTO setUpConsignorFromRevPickupAddr(
			PickupOrderDetailsTO revPickupAddrTO) throws CGBusinessException,
			CGSystemException {
		ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
		consignor.setFirstName(revPickupAddrTO.getConsignnorName());
		StringBuilder cnrAdd = new StringBuilder();
		cnrAdd.append(revPickupAddrTO.getAddress());
		consignor.setAddress(cnrAdd.toString());
		consignor.setMobile(revPickupAddrTO.getMobile());
		return consignor;
	}

	public static Set<ConsignmentRateTO> setUpRateCompomnets(String rateDtls) {
		Set<ConsignmentRateTO> consgRateTOs = new HashSet<ConsignmentRateTO>();
		if (StringUtils.isNotEmpty(rateDtls)) {
			String[] rateCompList = rateDtls.split(CommonConstants.COMMA);
			for (String rateComp : rateCompList) {
				ConsignmentRateTO consgRateTO = new ConsignmentRateTO();
				String[] rateCompValues = rateComp.split(CommonConstants.HASH);
				RateComponentTO rateCompoment = new RateComponentTO();
				rateCompoment.setRateComponentId(Integer
						.parseInt(rateCompValues[0]));
				consgRateTO.setCalculatedValue(Double
						.parseDouble(rateCompValues[0]));
				consgRateTO.setRateComponent(rateCompoment);
				consgRateTOs.add(consgRateTO);
			}
		}
		return consgRateTOs;
	}

	public static Set<ConsignmentRateTO> setUpRateCompomnets(
			List<RateComponentTO> consgRatecompoments) {
		Set<ConsignmentRateTO> consgRateTOs = new HashSet<ConsignmentRateTO>();
		if (!StringUtil.isEmptyList((consgRatecompoments))) {
			for (RateComponentTO rateComponentTO : consgRatecompoments) {
				ConsignmentRateTO consgRateTO = new ConsignmentRateTO();
				consgRateTO.setRateComponent(rateComponentTO);
				consgRateTO.setCalculatedValue(rateComponentTO
						.getCalculatedValue());
				consgRateTOs.add(consgRateTO);
			}
		}
		return consgRateTOs;
	}

	public static Set<ConsignmentRateDO> setUpRateCompomnetDOs(
			Set<ConsignmentRateTO> consgRateTOs) {
		Set<ConsignmentRateDO> consgRateDOs = new HashSet<ConsignmentRateDO>();
		for (ConsignmentRateTO rateCompTO : consgRateTOs) {
			ConsignmentRateDO consgRateDO = new ConsignmentRateDO();
			RateComponentDO rateCompDO = new RateComponentDO();
			rateCompDO.setRateComponentId(rateCompTO.getRateComponent()
					.getRateComponentId());
			consgRateDO.setRateComponentDO(rateCompDO);
			consgRateDO.setCalculatedValue(rateCompTO.getCalculatedValue());
			consgRateDOs.add(consgRateDO);
		}
		return consgRateDOs;
	}

	public static Set<ConsignmentBillingRateDO> setupConsgRateDtls(
			String consgNumber,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException {

		LOGGER.debug("BookingUtils::setupConsgRateDtls:: seting rate details for the consignment ["
				+ consgNumber + "]::START");
		Set<ConsignmentBillingRateDO> consgRateDOs = new HashSet<ConsignmentBillingRateDO>();
		if (!CGCollectionUtils.isEmpty((consgRateDtls))) {
			ConsignmentBillingRateDO rateDO = null;
			ConsignmentRateCalculationOutputTO consgRate = consgRateDtls
					.get(consgNumber);
			if(consgRate != null) {
					LOGGER.debug("BookingUtils::setupConsgRateDtls:: consignment ["+consgNumber+"] grand total rate is: " + consgRate.getGrandTotalIncludingTax());
				rateDO = new ConsignmentBillingRateDO();
				CGObjectConverter.createDomainFromTo(consgRate, rateDO);
				rateDO.setAirportHandlingCharge(consgRate
						.getAirportHandlingCharge());
			} else if (consgRate == null
					&& consgRateDtls.keySet().iterator().next().length() < 10) {
				rateDO = setupConsgRateDtlsForEB(consgRateDtls);
			} else {
				LOGGER.debug("BookingUtils::setupConsgRateDtls:: consignment ["
						+ consgNumber + "] rate is null");
			}

			if (rateDO != null) {
				rateDO.setRateCalculatedFor("B");
				rateDO.setCreatedDate(new Date());
				consgRateDOs.add(rateDO);
			}
		
		}
		LOGGER.debug("BookingUtils::setupConsgRateDtls:: seting rate details for the consignment ["
				+ consgNumber + "]::END");
		return consgRateDOs;

	}

	public static ConsignmentBillingRateDO setupConsgRateDtlsForEB(
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException {

		ConsignmentBillingRateDO consgRateDO = new ConsignmentBillingRateDO();
		if (!CGCollectionUtils.isEmpty((consgRateDtls))) {
			// ConsignmentBillingRateDO rateDO = new ConsignmentBillingRateDO();
			prepareRateResult(consgRateDO, consgRateDtls.values());
			consgRateDO.setRateCalculatedFor("B");
			// consgRateDO.settOPayCharge(consgRateDO.getGrandTotalIncludingTax());

		}

		return consgRateDO;
	}

	private static void prepareRateResult(ConsignmentBillingRateDO rateDo,
			Collection<ConsignmentRateCalculationOutputTO> rateTOs) {
		double finalSlabRate = 0;
		double fuelSurcharge = 0;
		double riskSurcharge = 0;

		double tOPayCharge = 0;
		double codCharges = 0;
		double parcelHandlingCharge = 0;
		// double airportHandlingCharge = 0;
		double documentHandlingCharge = 0;
		double rtoDiscount = 0;
		double otherOrSpecialCharge = 0;
		double discount = 0;
		double serviceTax = 0;
		double educationCess = 0;
		double higherEducationCess = 0;
		double stateTax = 0;
		double surchargeOnStateTax = 0;
		double octroi = 0;
		double octroiServiceCharge = 0;
		double serviceTaxOnOctroiServiceCharge = 0;
		double eduCessOnOctroiServiceCharge = 0;
		double higherEduCessOnOctroiServiceCharge = 0;
		double totalWithoutTax = 0;
		double grandTotalIncludingTax = 0;
		double lcCharge = 0;
		double declaredValue = 0;
		double slabRate = 0;
		double finalSlabRateAddedToRiskSurcharge = 0;
		double lcAmount = 0;
		double stateTaxOnOctroiServiceCharge = 0;
		double surchargeOnStateTaxOnoctroiServiceCharge = 0;
		double codAmount = 0;
		for (ConsignmentRateCalculationOutputTO rateTo : rateTOs) {
			finalSlabRate += rateTo.getFinalSlabRate() != null ? rateTo
					.getFinalSlabRate() : 0;
			fuelSurcharge += rateTo.getFuelSurcharge() != null ? rateTo
					.getFuelSurcharge() : 0;
			riskSurcharge += rateTo.getRiskSurcharge() != null ? rateTo
					.getRiskSurcharge() : 0;
			tOPayCharge += rateTo.gettOPayCharge() != null ? rateTo
					.gettOPayCharge() : 0;
			codCharges += rateTo.getCodCharges() != null ? rateTo
					.getCodCharges() : 0;
			parcelHandlingCharge += rateTo.getParcelHandlingCharge() != null ? rateTo
					.getParcelHandlingCharge() : 0;
			// airportHandlingCharge += rateTo.getAirPortHandlingCharge();
			documentHandlingCharge += rateTo.getDocumentHandlingCharge() != null ? rateTo
					.getDocumentHandlingCharge() : 0;
			rtoDiscount += rateTo.getRtoDiscount() != null ? rateTo
					.getRtoDiscount() : 0;
			otherOrSpecialCharge += rateTo.getOtherOrSpecialCharge() != null ? rateTo
					.getOtherOrSpecialCharge() : 0;
			discount += rateTo.getDiscount() != null ? rateTo.getDiscount() : 0;
			serviceTax += rateTo.getServiceTax() != null ? rateTo
					.getServiceTax() : 0;
			educationCess += rateTo.getEducationCess() != null ? rateTo
					.getEducationCess() : 0;
			higherEducationCess += rateTo.getHigherEducationCess() != null ? rateTo
					.getHigherEducationCess() : 0;
			stateTax += rateTo.getStateTax() != null ? rateTo.getStateTax() : 0;
			surchargeOnStateTax += rateTo.getSurchargeOnStateTax() != null ? rateTo
					.getSurchargeOnStateTax() : 0;
			;
			octroi += rateTo.getOctroi() != null ? rateTo.getOctroi() : 0;
			octroiServiceCharge += rateTo.getOctroiServiceCharge() != null ? rateTo
					.getOctroiServiceCharge() : 0;
			serviceTaxOnOctroiServiceCharge += rateTo
					.getServiceTaxOnOctroiServiceCharge() != null ? rateTo
					.getServiceTaxOnOctroiServiceCharge() : 0;
			eduCessOnOctroiServiceCharge += rateTo
					.getEduCessOnOctroiServiceCharge() != null ? rateTo
					.getEduCessOnOctroiServiceCharge() : 0;
			higherEduCessOnOctroiServiceCharge += rateTo
					.getHigherEduCessOnOctroiServiceCharge() != null ? rateTo
					.getHigherEduCessOnOctroiServiceCharge() : 0;
			totalWithoutTax += rateTo.getTotalWithoutTax() != null ? rateTo
					.getTotalWithoutTax() : 0;
			grandTotalIncludingTax += rateTo.getGrandTotalIncludingTax() != null ? rateTo
					.getGrandTotalIncludingTax() : 0;
			lcCharge += rateTo.getLcCharge() != null ? rateTo.getLcCharge() : 0;
			declaredValue += rateTo.getDeclaredValue() != null ? rateTo
					.getDeclaredValue() : 0;
			slabRate += rateTo.getSlabRate() != null ? rateTo.getSlabRate() : 0;
			finalSlabRateAddedToRiskSurcharge += rateTo
					.getFinalSlabRateAddedToRiskSurcharge() != null ? rateTo
					.getFinalSlabRateAddedToRiskSurcharge() : 0;
			lcAmount += rateTo.getLcAmount() != null ? rateTo.getLcAmount() : 0;
			stateTaxOnOctroiServiceCharge += rateTo
					.getStateTaxOnOctroiServiceCharge() != null ? rateTo
					.getStateTaxOnOctroiServiceCharge() : 0;
			surchargeOnStateTaxOnoctroiServiceCharge += rateTo
					.getSurchargeOnStateTaxOnoctroiServiceCharge() != null ? rateTo
					.getSurchargeOnStateTaxOnoctroiServiceCharge() : 0;
			codAmount += rateTo.getCodAmount() != null ? rateTo.getCodAmount()
					: 0;
		}

		// Setting all the fields
		rateDo.setFinalSlabRate(finalSlabRate);
		rateDo.setFuelSurcharge(fuelSurcharge);
		rateDo.setRiskSurcharge(riskSurcharge);
		rateDo.settOPayCharge(tOPayCharge);
		rateDo.setCodCharges(codCharges);
		rateDo.setParcelHandlingCharge(parcelHandlingCharge);
		// rateDo.setAirPortHandlingCharge(airportHandlingCharge);
		rateDo.setDocumentHandlingCharge(documentHandlingCharge);
		rateDo.setRtoDiscount(rtoDiscount);
		rateDo.setOtherOrSpecialCharge(otherOrSpecialCharge);
		rateDo.setDiscount(discount);
		rateDo.setServiceTax(serviceTax);
		rateDo.setEducationCess(educationCess);
		rateDo.setHigherEducationCess(higherEducationCess);
		rateDo.setStateTax(stateTax);
		rateDo.setSurchargeOnStateTax(surchargeOnStateTax);
		rateDo.setOctroi(octroi);
		rateDo.setOctroiServiceCharge(octroiServiceCharge);
		rateDo.setServiceTaxOnOctroiServiceCharge(serviceTaxOnOctroiServiceCharge);
		rateDo.setEduCessOnOctroiServiceCharge(eduCessOnOctroiServiceCharge);
		rateDo.setHigherEduCessOnOctroiServiceCharge(higherEduCessOnOctroiServiceCharge);
		rateDo.setTotalWithoutTax(totalWithoutTax);
		rateDo.setGrandTotalIncludingTax(grandTotalIncludingTax);
		rateDo.setLcCharge(lcCharge);
		rateDo.setDeclaredValue(declaredValue);
		rateDo.setSlabRate(slabRate);
		rateDo.setFinalSlabRateAddedToRiskSurcharge(finalSlabRateAddedToRiskSurcharge);
		rateDo.setLcAmount(lcAmount);
		rateDo.setStateTaxOnOctroiServiceCharge(stateTaxOnOctroiServiceCharge);
		rateDo.setSurchargeOnStateTaxOnoctroiServiceCharge(surchargeOnStateTaxOnoctroiServiceCharge);
		rateDo.setCodAmount(codAmount);
	}

	public static CNPricingDetailsTO setUpRateCompoments(
			ConsignmentRateCalculationOutputTO rateOutput) {
		CNPricingDetailsTO consgRateDtls = new CNPricingDetailsTO();
		if (!StringUtil.isNull(rateOutput)) {
			consgRateDtls.setAirportHandlingChg(rateOutput
					.getAirportHandlingCharge());
			consgRateDtls.setFinalPrice(rateOutput.getGrandTotalIncludingTax());
			consgRateDtls.setCodAmt(rateOutput.getCodAmount());
			consgRateDtls.setFuelChg(rateOutput.getFuelSurcharge());
			consgRateDtls.setServiceTax(rateOutput.getServiceTax());
			consgRateDtls.setRiskSurChg(rateOutput.getRiskSurcharge());
			consgRateDtls.setTopayChg(rateOutput.gettOPayCharge());
			consgRateDtls.setEduCessChg(rateOutput.getEducationCess());
			consgRateDtls.setHigherEduCessChg(rateOutput
					.getHigherEducationCess());
			consgRateDtls.setSplChg(rateOutput.getOtherOrSpecialCharge());
			// consgRateDtls.setFreightChg(rateOutput.getSlabRate());
			consgRateDtls.setFreightChg(rateOutput.getFinalSlabRate());
		}
		return consgRateDtls;
	}

	public static String[] removeElements(String[] allElements) {
		List<String> str2 = new ArrayList<>();
		for (String i : allElements) {
			if (!StringUtil.isStringEmpty(i)) {
				str2.add(i);
			}
		}
		String[] countries = str2.toArray(new String[str2.size()]);
		return countries;
	}
	
	public static void validateMandatoryData(BookingDO bookingDO)
			throws CGBusinessException {

		if (StringUtil.isNull(bookingDO)) {
			throw new CGBusinessException(
					"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details Cannot be Null");
		} else {

			if (CommonConstants.PROCESS_PICKUP.equalsIgnoreCase(bookingDO.getUpdatedProcess().getProcessCode())) {
				LOGGER.info("BookingCommonDAOImpl::validateMandatoryData::--->> Pickup Process --->> Not checking mandatory fields <<---");
			} else {
				
				/*
				 * The below If..else condition is especially added for EB booking.
				 * In EB booking, the final weight & actual weight of the concerned consignment is always null.
				 * Since there is a "not-null" check for final weight in the consignment HBM, the final weight is stamped as 0.0
				 * Also, for any type of booking other than EB, even if the booking type is absent, the actual & final weight fields will
				 * still get checked.  
				 * */
				if (!StringUtil.isNull(bookingDO.getBookingType()) && 
						!StringUtil.isStringEmpty(bookingDO.getBookingType().getBookingType())) {
					if (!BookingConstants.EMOTIONAL_BOND_BOOKING.equals(bookingDO.getBookingType().getBookingType())) {
						if (StringUtil.isEmptyDouble(bookingDO.getFianlWeight())) {
							throw new CGBusinessException(
									"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Final Weight] Cannot be Null");
						}
						if (StringUtil.isEmptyDouble(bookingDO.getActualWeight())) {
							throw new CGBusinessException(
									"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Actual Weight] Cannot be Null");
						}
					}
				}
				else {
					if (StringUtil.isEmptyDouble(bookingDO.getFianlWeight())) {
						throw new CGBusinessException(
								"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Final Weight] Cannot be Null");
					}
					if (StringUtil.isEmptyDouble(bookingDO.getActualWeight())) {
						throw new CGBusinessException(
								"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Actual Weight] Cannot be Null");
					}
				}
				
				
				if (StringUtil.isStringEmpty(bookingDO.getConsgNumber())) {
					throw new CGBusinessException(
							"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Consignment Number] Cannot be Null");
				}
				if (StringUtil.isNull(bookingDO.getPincodeId())
						|| StringUtil.isEmptyInteger(bookingDO.getPincodeId().getPincodeId())) {
					throw new CGBusinessException(
							"BookingCommonDAOImpl::isConsgBooked::--->> Mandatory Booking Details [Destination Pincode] Cannot be Null");
				}

			}
		}
	}
}
