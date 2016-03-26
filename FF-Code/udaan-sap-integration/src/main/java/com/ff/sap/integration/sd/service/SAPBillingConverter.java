package com.ff.sap.integration.sd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.booking.BookingDO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;



public class SAPBillingConverter {
	
	private static SDSAPIntegrationService sdSAPIntegrationService;
	
	
	

	/**
	 * @param sdSAPIntegrationService the sdSAPIntegrationService to set
	 */
	public static void setSdSAPIntegrationService(
			SDSAPIntegrationService sdSAPIntegrationService) {
		SAPBillingConverter.sdSAPIntegrationService = sdSAPIntegrationService;
	}


	Logger logger = Logger.getLogger(SAPBillingConverter.class);
	
	public static List<ConsignmentTO> convertConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs) throws CGBusinessException,
			CGSystemException {

		//logger.debug("SAPBillingConverter::convertConsignmentDOsToTOs::START----->");
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
		//logger.debug("BillingCommonConverter::convertConsignmentDOsToTOs::END----->");
		return consignmentTOs;
	}

	private static BookingDO getConsgBookingDetails(String consgNo)
			throws CGBusinessException, CGSystemException {
		//logger.debug("BillingCommonConverter::getBookingDate::START/END----->");
		return sdSAPIntegrationService.getConsgBookingDetails(consgNo);
	}
	
	
	private static ConsignmentTO setUpConsignmentDtls(ConsignmentBilling consgDO)
			throws CGBusinessException {

		//logger.debug("BillingCommonConverter::setUpConsignmentDtls::START----->");
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
		//logger.debug("BillingCommonConverter::setUpConsignmentDtls::END----->");
		return consgTO;
	}
	
	private static void convertConsignmentDO2TO(ConsignmentBilling consgDO,
			ConsignmentTO consgTO) throws CGBusinessException {

		//LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::START----->");
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
		//LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::END----->");
	}

	
	private static ProductTO getProduct(Integer productId)
			throws CGBusinessException, CGSystemException {
		//LOGGER.debug("BillingCommonConverter::getProduct::START/END----->");
		return sdSAPIntegrationService.getProduct(productId);

	}
	
	public static List<ConsignmentTO> convertBillingConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs) throws CGBusinessException,
			CGSystemException{

				/*logger.debug("SAPBillingConverter::convertConsignmentDOsToTOs::START----->");*/
				List<ConsignmentTO> consignmentTOs = null;
				ConsignmentTO consignmentTO=null;
				if (!StringUtil.isEmptyColletion(consignmentDOs)) {
					consignmentTOs = new ArrayList<>(consignmentDOs.size());
					for (ConsignmentBilling consignmentDO : consignmentDOs) {
						consignmentTO=new ConsignmentTO();
						consignmentTO.setBookingType(consignmentDO.getBOOKING_TYPE());
						if(!StringUtils.isEmpty(consignmentDO.getBOOKING_TYPE())){
						 if(!consignmentDO.getBOOKING_TYPE().equals("CS") && !consignmentDO.getBOOKING_TYPE().equals("EB") && !consignmentDO.getBOOKING_TYPE().equals("FC") ){
							 consignmentTO.setBookingType(consignmentDO.getBOOKING_TYPE());
							CGObjectConverter.createToFromDomain(consignmentDO, consignmentTO);
							if(!StringUtil.isNull(consignmentDO.getBOOKING_DATE())){
								consignmentTO.setBookingDate(consignmentDO.getBOOKING_DATE());
							}
							
							if(!StringUtils.isEmpty(consignmentDO.getPRODUCT_CODE())){
								ProductTO product = new ProductTO();
								product.setProductCode(consignmentDO.getPRODUCT_CODE());
								consignmentTO.setProductTO(product);
							}
							

							if(!StringUtil.isNull(consignmentDO.getEventDate())){
								consignmentTO.setEventDate(consignmentDO.getEventDate());
							}
							
							
							
							if (!StringUtil.isNull(consignmentDO.getDestPincodeId())) {
								PincodeTO destPin = new PincodeTO();
								CGObjectConverter.createToFromDomain(consignmentDO.getDestPincodeId(),
										destPin);
								consignmentTO.setDestPincode(destPin);
							}

							if (!StringUtil.isNull(consignmentDO.getConsgType())) {
								ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
								CGObjectConverter
										.createToFromDomain(consignmentDO.getConsgType(), typeTO);
								consignmentTO.setTypeTO(typeTO);
							}
							
							if (!StringUtil.isNull(consignmentDO.getInsuredBy())) {
								InsuredByTO insuredBy = new InsuredByTO();
								CGObjectConverter.createToFromDomain(consignmentDO.getInsuredBy(),
										insuredBy);
								consignmentTO.setInsuredByTO(insuredBy);
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
							
							if(!StringUtils.isEmpty(consignmentDO.getEbPreferencesCodes())){
							    cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO.getEbPreferencesCodes());
							}
							if(!StringUtils.isEmpty(consignmentDO.getServicedOn())){
								cNPricingDetailsTO.setServicesOn(consignmentDO.getServicedOn());
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
	
               }
				
		return consignmentTOs;
    }
}	
	
