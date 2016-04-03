package com.ff.report.billing.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.service.BillingCommonService;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.booking.BookingDO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BillingCommonConverter.
 * 
 * @author narmdr
 */
public class BillingCommonConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillingCommonConverter.class);

	private static BillingCommonService billingCommonService;

	/**
	 * @param billingCommonService
	 *            the billingCommonService to set
	 */
	@SuppressWarnings("static-access")
	public void setBillingCommonService(
			BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	/**
	 * Convert consignment d os to t os.
	 * 
	 * @param consignmentDOs
	 *            the consignment d os
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 */

	public static List<ConsignmentTO> convertConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("BillingCommonConverter::convertConsignmentDOsToTOs::START----->");
		List<ConsignmentTO> consignmentTOs = null;
		ProductTO product = null;
		Date bookingDate = null;
		BookingDO bookingDO = null;
		ConsignmentTO consignmentTO=null;
		if (!StringUtil.isEmptyColletion(consignmentDOs)) {
			consignmentTOs = new ArrayList<>(consignmentDOs.size());
			for (ConsignmentBilling consignmentDO : consignmentDOs) {
				consignmentTO=new ConsignmentTO();
				bookingDO = getConsgBookingDetails(consignmentDO.getConsgNo());
				if (!StringUtil.isNull(bookingDO)) {
					consignmentTO.setBookingType(bookingDO.getBookingType()
							.getBookingType());
				}
			
				 if(!consignmentTO.getBookingType().equals("CS") && !consignmentTO.getBookingType().equals("EB") && !consignmentTO.getBookingType().equals("FC") ){
				   consignmentTO = setUpConsignmentDtls(consignmentDO);
				   consignmentTO.setBookingType(bookingDO.getBookingType()
							.getBookingType());
				 if (!StringUtil.isEmptyInteger(consignmentDO.getProductId())) {
					product = getProduct(consignmentDO.getProductId());
				 }
				/*
				 * if(!StringUtil.isEmptyInteger(consignmentDO.getOrgOffId())){
				 * office =getOffice(consignmentDO.getOrgOffId()); }
				 * 
				 * if(!StringUtil.isEmptyInteger(office.getCityId())){
				 * city=getCityByOffice(office.getCityId()); }
				 * consignmentTO.setCityTO(city);
				 */
				/*bookingDO = getConsgBookingDetails(consignmentTO.getConsgNo());
				if (!StringUtil.isNull(bookingDO)) {
					consignmentTO.setBookingType(bookingDO.getBookingType()
							.getBookingType());
				}*/
				String consgStatus = consignmentTO.getConsgStatus();
				/*if (consgStatus != null && !consgStatus.equalsIgnoreCase("R")) {
					if (!StringUtil.isNull(bookingDO)) {
						bookingDate = bookingDO.getBookingDate();
						if (!StringUtil.isNull(bookingDate)) {
							consignmentTO.setBookingDate(bookingDate);
						}
					}
				}*/
				if(StringUtil.isStringEmpty(consgStatus)){
					if (!StringUtil.isNull(bookingDO)) {
						bookingDate = bookingDO.getBookingDate();
						if (!StringUtil.isNull(bookingDate)) {
							consignmentTO.setBookingDate(bookingDate);
						}
					}
				}
				else if(!consgStatus.equals("R")){
					if (!StringUtil.isNull(bookingDO)) {
						bookingDate = bookingDO.getBookingDate();
						if (!StringUtil.isNull(bookingDate)) {
							consignmentTO.setBookingDate(bookingDate);
						}
					}
				}
				

				if (!StringUtil.isNull(product)) {
					consignmentTO.setProductTO(product);
				}
				
				CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
				if (!StringUtil.isNull(consignmentDO.getRateType())) {
					cNPricingDetailsTO.setRateType(consignmentDO.getRateType());
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
					cNPricingDetailsTO.setDeclaredvalue(consignmentDO.getDeclaredValue());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
					cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
					cNPricingDetailsTO.setLcAmount(consignmentDO.getLcAmount());
				}
				
				if (!StringUtil.isNull(product)) {
					if(product.getProductName().equalsIgnoreCase("Emotional Bond")){
						cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO.getEbPreferencesCodes());
					}
					if(product.getProductName().equalsIgnoreCase("Priority")){
						cNPricingDetailsTO.setServicesOn(consignmentDO.getServicedOn());
					}
				}
				consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
				/*List<ConsignmentBillingRateTO> consignmentBillingRateTOs= convertBillingConsignmentRateDOsToTOs(consignmentDO.getConsgRateDtls());
				consignmentTO.setConsignmentBillingRateTOs(consignmentBillingRateTOs);*/
				consignmentTOs.add(consignmentTO);
			   }
			else{
				 consignmentTO.setConsgNo(consignmentDO.getConsgNo());
				 consignmentTOs.add(consignmentTO);
			   }
			}
		}
		LOGGER.debug("BillingCommonConverter::convertConsignmentDOsToTOs::END----->");
		return consignmentTOs;
	}

	/**
	 * Sets the up consignment dtls.
	 * 
	 * @param consgDO
	 *            the consg do
	 * @return the consignment to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private static ConsignmentTO setUpConsignmentDtls(ConsignmentBilling consgDO)
			throws CGBusinessException {

		LOGGER.debug("BillingCommonConverter::setUpConsignmentDtls::START----->");
		ConsignmentTO consgTO = new ConsignmentTO();

		/*if (!StringUtil.isEmptyColletion(consgDO.getChildCNs())) {
			StringBuilder chindCNDtls = new StringBuilder();
			for (ChildConsignmentDO childCN : consgDO.getChildCNs()) {
				chindCNDtls.append(childCN.getChildConsgNumber());
				chindCNDtls.append(CommonConstants.COMMA);
				chindCNDtls.append(childCN.getChildConsgWeight());
				chindCNDtls.append(CommonConstants.HASH);
			}
			consgTO.setChildCNsDtls(chindCNDtls.toString());
		}*/
		convertConsignmentDO2TO(consgDO, consgTO);
		LOGGER.debug("BillingCommonConverter::setUpConsignmentDtls::END----->");
		return consgTO;
	}

	/**
	 * Convert consignment d o2 to.
	 * 
	 * @param consgDO
	 *            the consg do
	 * @param consgTO
	 *            the consg to
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private static void convertConsignmentDO2TO(ConsignmentBilling consgDO,
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

		/*if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
			CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnPaperWorkId(),
					cnPaperworkTO);
			cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
			consgTO.setCnPaperWorks(cnPaperworkTO);
		}*/
		/*if (!StringUtil.isNull(consgDO.getCnContentId())) {
			CNContentTO cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
					cnContentTO);
			cnContentTO.setOtherContent(consgDO.getOtherCNContent());
			consgTO.setCnContents(cnContentTO);
		}*/
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
		/*if (!StringUtil.isNull(consgDO.getConsignor())) {
			ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignor(),
					consignorTO);
			consgTO.setConsignorTO(consignorTO);
		}*/
        
		
		
		
		
		/*if (!StringUtil.isNull(consgDO.getConsgPricingDtls())) {
			CNPricingDetailsTO cnPricingDetails = new CNPricingDetailsTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsgPricingDtls(),
					cnPricingDetails);
			consgTO.setConsgPriceDtls(cnPricingDetails);
		}*/
		  
		

		/*
		 * if (!StringUtil.isEmptyDouble(consgDO.getDeclaredValue())) {
		 * consgTO.setDeclaredValue(consgDO.getDeclaredValue()); }
		 */
		LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::END----->");
	}

	/**
	 * To get product by its Id
	 * 
	 * @param productId
	 * @return product
	 */
	private static ProductTO getProduct(Integer productId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonConverter::getProduct::START/END----->");
		return billingCommonService.getProduct(productId);

	}

	private static BookingDO getConsgBookingDetails(String consgNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonConverter::getBookingDate::START/END----->");
		return billingCommonService.getConsgBookingDetails(consgNo);
	}
}
