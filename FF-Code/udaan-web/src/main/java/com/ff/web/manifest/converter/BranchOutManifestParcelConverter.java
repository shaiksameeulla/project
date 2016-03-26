package com.ff.web.manifest.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.BranchOutManifestParcelDetailsTO;
import com.ff.manifest.BranchOutManifestParcelTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * 
 * @author hkansagr
 *
 */

public class BranchOutManifestParcelConverter extends OutManifestBaseConverter {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestParcelConverter.class);
	
	private static ConsignmentCommonService consignmentCommonService = null;
	private static ConsignmentService consignmentService = null;
	private static OutManifestCommonService outManifestCommonService = null;
	private static OutManifestUniversalService outManifestUniversalService = null;
	
	/**
	 * @return the outManifestUniversalService
	 */
	public static OutManifestUniversalService getOutManifestUniversalService() {
		return outManifestUniversalService;
	}
	/**
	 * @param outManifestUniversalService the outManifestUniversalService to set
	 */
	public static void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		BranchOutManifestParcelConverter.outManifestUniversalService = outManifestUniversalService;
	}
	/**
	 * @return the outManifestCommonService
	 */
	public static OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}
	/**
	 * @param outManifestCommonService the outManifestCommonService to set
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		BranchOutManifestParcelConverter.outManifestCommonService = outManifestCommonService;
	}
	/**
	 * @return the consignmentCommonService
	 */
	public static ConsignmentCommonService getConsignmentCommonService() {
		return consignmentCommonService;
	}
	/**
	 * @param consignmentCommonService the consignmentCommonService to set
	 */
	public static void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		BranchOutManifestParcelConverter.consignmentCommonService = consignmentCommonService;
	}
	/**
	 * @return the consignmentService
	 */
	public static ConsignmentService getConsignmentService() {
		return consignmentService;
	}
	/**
	 * @param consignmentService the consignmentService to set
	 */
	public static void setConsignmentService(ConsignmentService consignmentService) {
		BranchOutManifestParcelConverter.consignmentService = consignmentService;
	}

	/**
	 * @Desc convert manifestDO to branchOutManifestParcelTO
	 * @param manifestDO
	 * @return branchOutManifestParcelTO
	 * @throws Exception
	 */
	public static BranchOutManifestParcelTO branchOutManifestParcelDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException,CGSystemException {
		 int consignCount=0;
		// Set the common attributes for the header
		BranchOutManifestParcelTO branchOutManifestParcelTO = (BranchOutManifestParcelTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getBranchOutManifestParcelFactory());
		
		// Set the specific attributes for header
		branchOutManifestParcelTO.setBagLockNo(manifestDO.getBagLockNo());

		/* get RFID reference number by RFID */
		String rfIdNo = outManifestUniversalService
				.getBagRfNoByRfId(branchOutManifestParcelTO.getBagRFID());
		branchOutManifestParcelTO.setRfidNo(rfIdNo);
		branchOutManifestParcelTO.setLoginOfficeName(branchOutManifestParcelTO.getLoginOfficeName());
		
		CityTO cityTO = new CityTO();
		cityTO.setCityId(branchOutManifestParcelTO.getDestinationCityId());
		List<CityTO> cityTOs = geographyCommonService.getCitiesByCity(cityTO);
		if (!StringUtil.isEmptyColletion(cityTOs)) {
			RegionTO destRegionTO = new RegionTO();
			destRegionTO.setRegionId(cityTOs.get(0).getRegion());
			branchOutManifestParcelTO.setDestRegionTO(destRegionTO);
		}
		
		/* Newly Added for BCUN - Load Lot Id */
		branchOutManifestParcelTO.setLoadNoId(manifestDO.getLoadLotId());
		
		
		
				Set<ConsignmentDO> consignmentDOs = null;
				Double totalWeight=0.0;
				int totalNoOfPcs  = 0;
				int rowCount = 0;
		// set the attributes for detail TO
		List<BranchOutManifestParcelDetailsTO> branchOutManifestParcelDetailTOs = new ArrayList<>(
				manifestDO.getNoOfElements());
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			consignmentDOs = manifestDO.getConsignments();
		for (ConsignmentDO consignment : consignmentDOs) {
			BranchOutManifestParcelDetailsTO branchOutManifestParcelDtlTO = (BranchOutManifestParcelDetailsTO) ManifestUtil
					.consignmentDomainConverter(
							consignment,
							ManifestUtil
									.prepareFactoryInputs(
											OutManifestConstants.BRANCH_MANIFEST,
											CommonConstants.CONSIGNMENT_TYPE_PARCEL));
			
			
			if (!StringUtil.isNull(branchOutManifestParcelDtlTO)) {
				CityTO city = geographyCommonService
						.getCity(branchOutManifestParcelDtlTO.getPincode());
				if (city != null) {
					branchOutManifestParcelDtlTO.setDestCityId(city.getCityId());
					branchOutManifestParcelDtlTO.setDestCity(city.getCityName());
				}
				
				branchOutManifestParcelDtlTO.setIsCN(CommonConstants.YES);
				/* outManifestpParcelDtlTO.setBookingType(""); */
				if(consignment.getNoOfPcs()!=null){
					branchOutManifestParcelDtlTO.setNoOfPcs(consignment.getNoOfPcs());
				totalNoOfPcs=totalNoOfPcs+consignment.getNoOfPcs();
				}
				if(consignment.getFinalWeight()!=null){
					branchOutManifestParcelDtlTO.setWeight(consignment
						.getFinalWeight());
				totalWeight+=consignment.getFinalWeight();
				}
				if (!StringUtil.isEmptyColletion(consignment
						.getChildCNs())) {
					StringBuilder chindCNDtls = new StringBuilder();
					for (ChildConsignmentDO childCN : consignment
							.getChildCNs()) {
						chindCNDtls.append(childCN.getChildConsgNumber());
						chindCNDtls.append(CommonConstants.COMMA);
						chindCNDtls.append(childCN.getChildConsgWeight());
						chindCNDtls.append(CommonConstants.HASH);
					}
					branchOutManifestParcelDtlTO.setChildCn(chindCNDtls
							.toString());
				}
				if (!StringUtil.isNull(consignment.getCnContentId())) {
					branchOutManifestParcelDtlTO.setCnContentId(consignment
							.getCnContentId().getCnContentId());
					branchOutManifestParcelDtlTO.setCnContentCode(consignment
							.getCnContentId().getCnContentCode());
					branchOutManifestParcelDtlTO.setCnContent(consignment
							.getCnContentId().getCnContentName());
					branchOutManifestParcelDtlTO.setOtherCNContent(consignment.getOtherCNContent());
				} else {
					branchOutManifestParcelDtlTO
							.setOtherCNContent(consignment
									.getOtherCNContent());
				}
				
				if (!StringUtil.isNull(consignment.getCnPaperWorkId())) {
					branchOutManifestParcelDtlTO.setPaperWorkId(consignment
							.getCnPaperWorkId().getCnPaperWorkId());
					branchOutManifestParcelDtlTO.setPaperWorkCode(consignment
							.getCnPaperWorkId().getCnPaperWorkCode());
					branchOutManifestParcelDtlTO.setPaperWork(consignment
							.getCnPaperWorkId().getCnPaperWorkName());
				}
			
				
				
				//setting the details (one way)
				if(!StringUtil.isNull(consignment.getDeclaredValue())){
					branchOutManifestParcelDtlTO.setDeclaredValue(consignment.getDeclaredValue());
				}
				if(!StringUtil.isNull(consignment.getTopayAmt())){
					branchOutManifestParcelDtlTO.setToPayAmt(consignment.getTopayAmt());
				}
				if(!StringUtil.isNull(consignment.getCodAmt())){	
					branchOutManifestParcelDtlTO.setCodAmt(consignment.getCodAmt());
				}
				
				
				
				//setting the details frim cnPrice(other way)
					/*CNPricingDetailsTO CNPricingDtlsTO = consignmentCommonService
					.getConsgPrincingDtls(consignment.getConsgNo());
				if (!StringUtil.isNull(CNPricingDtlsTO)) {
					branchOutManifestParcelDtlTO.setDeclaredValue(CNPricingDtlsTO
						.getDeclaredvalue());
				
					branchOutManifestParcelDtlTO.setPaperRefNum(consignment
						.getPaperWorkRefNo());
					branchOutManifestParcelDtlTO.setToPayAmt(CNPricingDtlsTO
						.getTopayChg());
					branchOutManifestParcelDtlTO.setCodAmt(CNPricingDtlsTO
						.getCodAmt());
				}*/
			}//end for loop
			consignCount++;
			branchOutManifestParcelDetailTOs.add(branchOutManifestParcelDtlTO);
		}//endIf loop
		}
		
		branchOutManifestParcelTO.setPrintTotalWeight( Double.parseDouble(new DecimalFormat("##.###").format(totalWeight)));
		branchOutManifestParcelTO.setRowCount(rowCount);
		branchOutManifestParcelTO.setPrintTotalNoOfPcs(totalNoOfPcs);
		branchOutManifestParcelTO.setConsignCount(consignCount);
		Collections.sort(branchOutManifestParcelDetailTOs);
		branchOutManifestParcelTO
			.setBranchOutManifestParcelDetailsList(branchOutManifestParcelDetailTOs);
	LOGGER.trace("BranchOutManifestParcelConverter::branchOutManifestParcelDomainConverter()::END");
	return branchOutManifestParcelTO;
		
	}
	
	/**
	 * @Desc convert cnModificationTO to branchOutManifestParcelDetailsTO
	 * @param cnModificationTO
	 * @return branchOutManifestParcelDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static BranchOutManifestParcelDetailsTO branchOutManifestParcelDtlsConverter(
			ConsignmentModificationTO cnModificationTO)
					throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter::branchOutManifestParcelDtlsConverter()::START");
		/* Setting Common attributes */
		/* consgNo, consgId, finalWeight...SETTED in cnDtlsToOutMnfstDtlBaseConverter */
		BranchOutManifestParcelDetailsTO branchOutManifestParcelDetailsTO = (BranchOutManifestParcelDetailsTO) 
				cnDtlsToOutMnfstDtlBaseConverter(cnModificationTO, 
						ManifestUtil.getBranchOutManifestParcelFactory());
		/* Setting specific attributes */
		branchOutManifestParcelDetailsTO.setBookingType(cnModificationTO
							.getBookingType());
		ConsignmentTO consignmentTO = cnModificationTO.getConsigmentTO();
		
		if (!StringUtil.isNull(consignmentTO)) {
			/* No. Of Pcs. */
			branchOutManifestParcelDetailsTO.setNoOfPcs(consignmentTO.getNoOfPcs());
			/* Child CN */
			if (StringUtils.isNotEmpty(consignmentTO.getChildCNsDtls())) {
				branchOutManifestParcelDetailsTO.setChildCn(consignmentTO
						.getChildCNsDtls());
			}
			/* CnContents */
			if(consignmentTO.getCnContents()!=null){
				branchOutManifestParcelDetailsTO.setCnContentId(consignmentTO
						.getCnContents().getCnContentId());
				branchOutManifestParcelDetailsTO.setCnContent(consignmentTO
						.getCnContents().getCnContentName());
			}
			
			//cn Other content
			if(consignmentTO.getOtherCNContent()!=null){
				branchOutManifestParcelDetailsTO.setOtherCNContent(consignmentTO
						.getOtherCNContent());
			}
			/* Paper Work */
			if(consignmentTO.getCnPaperWorks()!=null){
				branchOutManifestParcelDetailsTO.setPaperWorkId(consignmentTO
						.getCnPaperWorks().getCnPaperWorkId()); 
				branchOutManifestParcelDetailsTO.setPaperWork(consignmentTO
						.getCnPaperWorks().getCnPaperWorkName());
			} 
			
			CityTO destCity=new CityTO();
			destCity.setCityId(cnModificationTO.getConsigmentTO().getDestPincode().getCityId());
		
			CityTO cityTO = outManifestCommonService.getCity(destCity);
		
			if (!StringUtil.isNull(cityTO)) {
				branchOutManifestParcelDetailsTO.setDestCityId(cityTO.getCityId());
				branchOutManifestParcelDetailsTO.setDestCity(cityTO.getCityName());
			}
			/* Setting LC Amount details. */
			if(!StringUtil.isNull(cnModificationTO.getConsigmentTO().getDeclaredValue())){
				branchOutManifestParcelDetailsTO.setDeclaredValue(cnModificationTO.getConsigmentTO().getDeclaredValue());
			}
			if(!StringUtil.isNull(cnModificationTO.getConsigmentTO().getTopayAmt())){
				branchOutManifestParcelDetailsTO.setToPayAmt(cnModificationTO.getConsigmentTO().getTopayAmt());
			}
			if(!StringUtil.isNull(cnModificationTO.getConsigmentTO().getCodAmt())){	
				branchOutManifestParcelDetailsTO.setCodAmt(cnModificationTO.getConsigmentTO().getCodAmt());
			}
			}
			
			/*if(!StringUtil.isNull(cnModificationTO.getConsigmentTO())){
				Set<ConsignmentRateTO> rateDetails= cnModificationTO.getConsigmentTO().getConsgRateDetails();
				for (ConsignmentRateTO consignmentRateTO : rateDetails) {
					if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_DECLARED_VALUE, consignmentRateTO.getRateComponent().getRateComponentCode())) {
						branchOutManifestParcelDetailsTO.setDeclaredValue(consignmentRateTO.getRateComponent().getCalculatedValue());
						
					}
					if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES, consignmentRateTO.getRateComponent().getRateComponentCode())) {
						branchOutManifestParcelDetailsTO.setToPayAmt(consignmentRateTO.getRateComponent().getCalculatedValue());
						
					}
					if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_COD, consignmentRateTO.getRateComponent().getRateComponentCode())) {
						branchOutManifestParcelDetailsTO.setCodAmt(consignmentRateTO.getRateComponent().getCalculatedValue());
						
					}
					
				
				}
					
			}	*/
		
		

			/* TODO set value */
			/* branchOutManifestParcelDetailsTO.setCustomDutyAmt(cnModificationTO);
			branchOutManifestParcelDetailsTO.setServiceCharge(cnModificationTO);
			branchOutManifestParcelDetailsTO.setStateTax(cnModificationTO); */
		//}
		LOGGER.trace("BranchOutManifestParcelConverter::branchOutManifestParcelDtlsConverter()::END");
		return branchOutManifestParcelDetailsTO;
	}
	
	/**
	 * @Desc prepare branchOutManifestParcelDetailsTOs list from branchOutManifestParcelTO
	 * @param branchOutManifestParcelTO
	 * @return branchOutManifestParcelDetailsTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<BranchOutManifestParcelDetailsTO> prepareBranchOutManifestDtlsParcelList(
			BranchOutManifestParcelTO branchOutManifestParcelTO) 
				throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter::prepareBranchOutManifestDtlsParcelList()::START");
		BranchOutManifestParcelDetailsTO branchOutManifestParcelDetailsTO = null;
		List<BranchOutManifestParcelDetailsTO> branchOutManifestParcelDetailsTOs = null;
		
		if (!StringUtil.isNull(branchOutManifestParcelTO)) {
			branchOutManifestParcelDetailsTOs = new ArrayList<>();//branchOutManifestParcelTO.getConsgNos().length
			if (branchOutManifestParcelTO.getConsgNos()!=null
					&& branchOutManifestParcelTO.getConsgNos().length > 0) {
				for(int rowCount=0; rowCount<branchOutManifestParcelTO
						.getConsgNos().length; rowCount++) {
					if (StringUtils.isNotEmpty(branchOutManifestParcelTO
							.getConsgNos()[rowCount])) {
						/* Setting the common grid level attributes */
						branchOutManifestParcelDetailsTO = (BranchOutManifestParcelDetailsTO)setUpManifestDtlsTOs(
								branchOutManifestParcelTO, rowCount);
						
						/* Setting specific to Out manifest parcel */
						branchOutManifestParcelDetailsTO.setConsgId(branchOutManifestParcelTO.getConsgIds()[rowCount]);//hidden field
						
						branchOutManifestParcelDetailsTO.setConsgNo(branchOutManifestParcelTO.getConsgNos()[rowCount]);
						branchOutManifestParcelDetailsTO.setNoOfPcs(branchOutManifestParcelTO.getNoOfPcs()[rowCount]);
						branchOutManifestParcelDetailsTO.setWeight(branchOutManifestParcelTO.getWeights()[rowCount]);
						branchOutManifestParcelDetailsTO.setOldWeight(branchOutManifestParcelTO.getOldWeights()[rowCount]);
						branchOutManifestParcelDetailsTO.setCnContentId(branchOutManifestParcelTO.getCnContentIds()[rowCount]);
						branchOutManifestParcelDetailsTO.setCnContent(branchOutManifestParcelTO.getCnContent()[rowCount]);
						branchOutManifestParcelDetailsTO.setDeclaredValue(branchOutManifestParcelTO.getDeclaredValues()[rowCount]);
						branchOutManifestParcelDetailsTO.setPaperWorkId(branchOutManifestParcelTO.getPaperWorkIds()[rowCount]);
						branchOutManifestParcelDetailsTO.setPaperWork(branchOutManifestParcelTO.getPaperWork()[rowCount]);
						branchOutManifestParcelDetailsTO.setToPayAmt(branchOutManifestParcelTO.getToPayAmts()[rowCount]);
						branchOutManifestParcelDetailsTO.setCodAmt(branchOutManifestParcelTO.getCodAmts()[rowCount]);
						branchOutManifestParcelDetailsTO.setCustomDutyAmt(branchOutManifestParcelTO.getCustomDutyAmts()[rowCount]);
						branchOutManifestParcelDetailsTO.setServiceCharge(branchOutManifestParcelTO.getServiceCharges()[rowCount]);
						branchOutManifestParcelDetailsTO.setStateTax(branchOutManifestParcelTO.getStateTaxes()[rowCount]);
						branchOutManifestParcelDetailsTO.setPosition(branchOutManifestParcelTO.getPosition()[rowCount]);
						branchOutManifestParcelDetailsTOs.add(branchOutManifestParcelDetailsTO);
					}
				}
			}
		}
		LOGGER.trace("BranchOutManifestParcelConverter::prepareBranchOutManifestDtlsParcelList()::END");
		return branchOutManifestParcelDetailsTOs;
	}

	/**
	 * @Desc prepare manifestDOs list from branchOutManifestParcelTO
	 * @param branchOutManifestParcelTO
	 * @return manifestDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<ManifestDO> prepareManifestDOList(
			BranchOutManifestParcelTO branchOutManifestParcelTO)
				throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter::prepareManifestDOList()::START");
		List<ManifestDO> manifestDOs = new ArrayList<>();
		ManifestDO manifestDO = null;
		
		/* Setting Logged In City Id as Destination City Id */
		branchOutManifestParcelTO.setDestinationCityId(
				branchOutManifestParcelTO.getLoginCityId());
		
		/* Setting common attributes */
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(branchOutManifestParcelTO);

		/* Specific to branch out manifest parcel */
		manifestDO.setDestOfficeId(branchOutManifestParcelTO.getDestinationOfficeId());
		
		/* Newly Add for BCUN - Load Lot Id */
		manifestDO.setLoadLotId(branchOutManifestParcelTO.getLoadNoId());
		
		/* Set destination office */
		if (!StringUtil.isEmptyInteger(branchOutManifestParcelTO
				.getDestinationOfficeId())) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(branchOutManifestParcelTO.getDestinationOfficeId());
			manifestDO.setDestOffice(officeDO);
		}
		
		/* Set destination city */
		/*CityDO cityDO = new CityDO();
		cityDO.setCityId(branchOutManifestParcelTO.getLoginOfficeId());
		manifestDO.setDestinationCity(cityDO);*/
		
		/* Set ConsignmentManifestDO */
		Set<ConsignmentManifestDO> cnManifestSet = ManifestUtil
				.setConsignmentManifestDtls(branchOutManifestParcelTO
						.getBranchOutManifestParcelDetailsList());
		//manifestDO.setManifestConsgDtls(cnManifestSet);
		
		
		/* To update consg. wt if required. */
		boolean result = Boolean.FALSE;
		for(OutManifestDetailBaseTO to : branchOutManifestParcelTO
				.getBranchOutManifestParcelDetailsList()){
			try{
				
				if(to.getWeight().doubleValue()!=to.getOldWeight().doubleValue()){
					to.setDestOfficeId(manifestDO.getDestOffice().getOfficeId());
					to.setGridOriginOfficeId(manifestDO.getOriginOffice().getOfficeId());
				
					result = outManifestCommonService.updateConsgWeight(to, 
						branchOutManifestParcelTO.getProcessId());
					if(!result){/* FIXME remove this later if not required */
						LOGGER.info("Consignment weight not updated for consg. Id :" + to.getConsgId());
					}	
				}
			} catch(Exception e) {
				LOGGER.error("Exception occurs in BranchOutManifestParcelConverter::prepareManifestDOList()::"
					+ e.getMessage());
				throw new CGBusinessException(e);
			}
		}
		
		/*for(OutManifestDetailBaseTO to:branchOutManifestParcelTO.getBranchOutManifestParcelDetailsList()){
			boolean result = Boolean.FALSE;
			//setting process Id
			to.setProcessId(branchOutManifestParcelTO.getProcessId());
			if(!StringUtil.isEmptyDouble(to.getOldWeight())
					&& !StringUtil.isEmptyDouble(to.getWeight())){
				if(to.getWeight()>to.getOldWeight()){
					try{
						result = outManifestUniversalService.updateConsgWeight(to);
						if(!result){
							LOGGER.info("Consignment weight not updated:" + to.getConsgId());
						}
					} catch(Exception e) {
						LOGGER.error("Exception occurs in BranchOutManifestParcelConverter::prepareManifestDOList()::"
							+ e.getMessage());
					}
				}
			}
		}*/
		
		/* Set no. of elements */
		int noOfElements = CommonConstants.ZERO;
		noOfElements += cnManifestSet.size();
		manifestDO.setNoOfElements(noOfElements);
		
		manifestDOs.add(manifestDO);
		LOGGER.trace("BranchOutManifestParcelConverter::prepareManifestDOList()::END");
		return manifestDOs;
	}
	
	/**
	 * @Desc prepare manifestProcessDOs list from branchOutManifestParcelTO
	 * @param branchOutManifestParcelTO
	 * @return manifestProcessDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*public static List<ManifestProcessDO> prepareManifestProcessDOList(
			BranchOutManifestParcelTO branchOutManifestParcelTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter::prepareManifestProcessDOList()::START");
		List<ManifestProcessDO> manifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;

		 Setting common attributes 
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(branchOutManifestParcelTO);
		
		
		 * 	Specific to out manifest parcel:		
		 *	Set ConsignmentManifestDO 
		 
		Set<ConsignmentManifestDO> cnManifestSet = ManifestUtil
				.setConsignmentManifestDtls(branchOutManifestParcelTO
						.getBranchOutManifestParcelDetailsList());
		
		//addedto avoid duplicate entry in manifstProcess while saving
				if(!StringUtil.isNull(branchOutManifestParcelTO.getManifestProcessTo())&& !StringUtil.isEmptyInteger(branchOutManifestParcelTO.getManifestProcessTo().getManifestProcessId())){
					manifestProcessDO.setManifestProcessId(branchOutManifestParcelTO.getManifestProcessTo().getManifestProcessId());
				}
		manifestProcessDO.setManifestStatus(branchOutManifestParcelTO.getManifestStatus());
		manifestProcessDO.setLoadLotId(branchOutManifestParcelTO.getLoadNoId());
		
		
		if (branchOutManifestParcelTO.getConsgIds() != null
				&& branchOutManifestParcelTO.getConsgIds().length > 0) {
			cnManifestSet = ManifestUtil
					.setConsignmentManifestDtlsForBOUT(branchOutManifestParcelTO);
			noOfElements += cnManifestSet.size();
		}
		 Set no. of elements 
		int noOfElements = CommonConstants.ZERO;
		noOfElements += cnManifestSet.size();
		manifestProcessDO.setNoOfElements(noOfElements);
		
		manifestProcessDOs.add(manifestProcessDO);
		LOGGER.trace("BranchOutManifestParcelConverter::prepareManifestProcessDOList()::END");
		return manifestProcessDOs;
	}*/
	
	/**
	 * @Desc if no.Of Pcs. is greater than 1, then it allows to add/update child CN Dtls
	 * @param branchOutManifestParcelTO
	 * @return result
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<Integer> updateChildConsgDtls(BranchOutManifestParcelTO 
			branchOutManifestParcelTO) throws CGBusinessException,
				CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter::updateChildConsgDtls()::START");
		List<Integer> result = null;
		ConsignmentTO consignmentTO = null;
		Set<ChildConsignmentTO> childCNsSet = new HashSet<>();
		for(int i=0; i<branchOutManifestParcelTO.getConsgNos().length; i++){
			if(!branchOutManifestParcelTO.getConsgNos()[i].equals(CommonConstants.EMPTY_STRING) 
					&& branchOutManifestParcelTO.getNoOfPcs()[i]>1 
						&& branchOutManifestParcelTO.getIsCnUpdated()[i].equals(CommonConstants.YES)) {
				consignmentTO = consignmentCommonService
						.getConsingmentDtls(branchOutManifestParcelTO.getConsgNos()[i]);
				String childCNs = branchOutManifestParcelTO.getChildCns()[i];
				String childCNList[] = childCNs.split("#");
				for(int j=0; j<childCNList.length; j++){
					ChildConsignmentTO childTO = new ChildConsignmentTO();
					String str[] = childCNList[j].split(",");
					childTO.setChildConsgNumber(str[0]);
					childTO.setChildConsgWeight(Double.parseDouble(str[1]));
					
					/* ConsignmentDO consg = new ConsignmentDO(); */
					/* consg.setConsgId(branchOutManifestParcelTO.getConsgIds()[i]); */
					childTO.setConsignmentId(branchOutManifestParcelTO
							.getConsgIds()[i]);
					childCNsSet.add(childTO);
				}
				/* update no.of pcs. */
				consignmentTO.setNoOfPcs(branchOutManifestParcelTO.getNoOfPcs()[i]);
				ProcessTO processTO = new ProcessTO();
				processTO.setProcessId(branchOutManifestParcelTO.getProcessId());
				consignmentTO.setUpdatedProcessFrom(processTO);
				/* setting consg. type */
				if(branchOutManifestParcelTO.getConsignmentTypeTO()!=null){
					consignmentTO.setConsgTypeId(branchOutManifestParcelTO
							.getConsignmentTypeTO().getConsignmentId());
				}
				/* setting content Id */
				if(!StringUtil.isEmptyInteger(branchOutManifestParcelTO.getCnContentIds()[i])){
					CNContentTO cnContents = new CNContentTO();
					cnContents.setCnContentId(branchOutManifestParcelTO.getCnContentIds()[i]);
					consignmentTO.setCnContents(cnContents);
				}
				/* setting paper work Id */
				if(!StringUtil.isEmptyInteger(branchOutManifestParcelTO.getPaperWorkIds()[i])){
					CNPaperWorksTO cnPaperWorks = new CNPaperWorksTO();
					cnPaperWorks.setCnPaperWorkId(branchOutManifestParcelTO.getPaperWorkIds()[i]);
					consignmentTO.setCnPaperWorks(cnPaperWorks);
				}
				consignmentTO.setChildTOSet(childCNsSet);
				/* Ami added 0609 */
				List<ConsignmentTO> consgListTO=new ArrayList<>();
				consgListTO.add(consignmentTO);
				result = consignmentService.saveOrUpdateConsignment(consgListTO);
				//if(result=="FAILURE")
					return result;
			}/* end of IF */
		}
		LOGGER.trace("BranchOutManifestParcelConverter::updateChildConsgDtls()::END");
		return result;
	}
	
	/**
	 * To convert ConsignmentTO to BranchOutManifestParcelDetailsTO for in-manifest CN
	 * @param consTO
	 * @return branchOutParcelManifestDetailsTO
	 */
	public static BranchOutManifestParcelDetailsTO branchOutParcelGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		LOGGER.trace("BranchOutManifestParcelConverter::branchOutParcelGridDetailsForInManifConsg()::START");
		BranchOutManifestParcelDetailsTO branchOutParcelManifestDetailsTO = new BranchOutManifestParcelDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			branchOutParcelManifestDetailsTO.setConsgNo(consTO.getConsgNo());
			branchOutParcelManifestDetailsTO.setConsgId(consTO.getConsgId());
		}
	
		if(!StringUtil.isNull(consTO.getDestPincode())){
		/* branchOutParcelManifestDetailsTO.setPincode(consTO.getDestPincode().getPincode()); */
			branchOutParcelManifestDetailsTO
				.setPincodeId(consTO.getDestPincode().getPincodeId());
		}
		
		if(!StringUtil.isNull(consTO.getDestCity())){
			branchOutParcelManifestDetailsTO.setDestCityId(consTO.getDestCity().getCityId());
			branchOutParcelManifestDetailsTO.setDestCity(consTO.getDestCity().getCityName());
		}
		
		if(!StringUtil.isNull(consTO.getFinalWeight())){
			branchOutParcelManifestDetailsTO.setWeight(consTO.getFinalWeight());
			branchOutParcelManifestDetailsTO.setBkgWeight(consTO.getFinalWeight());
		}

		/* No. Of Pcs. */
		branchOutParcelManifestDetailsTO.setNoOfPcs(consTO.getNoOfPcs());
		/* Child CN */
		if (StringUtils.isNotEmpty(consTO.getChildCNsDtls())) {
			branchOutParcelManifestDetailsTO.setChildCn(consTO
			.getChildCNsDtls());
		}
		/* CnContents */
		if(consTO.getCnContents()!=null){
			branchOutParcelManifestDetailsTO.setCnContentId(consTO
					.getCnContents().getCnContentId());
			branchOutParcelManifestDetailsTO.setCnContent(consTO
					.getCnContents().getCnContentName());
		}
		/* Paper Work */
		if(consTO.getCnPaperWorks()!=null){
			branchOutParcelManifestDetailsTO.setPaperWorkId(consTO
					.getCnPaperWorks().getCnPaperWorkId()); 
			branchOutParcelManifestDetailsTO.setPaperWork(consTO
					.getCnPaperWorks().getCnPaperWorkName());
		} 	
		LOGGER.trace("BranchOutManifestParcelConverter::branchOutParcelGridDetailsForInManifConsg()::END");
		return branchOutParcelManifestDetailsTO;
	}
	
	public static ManifestDO prepareManifestDO(
			BranchOutManifestParcelTO branchOutmanifestParcelTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelConverter :: prepareManifestDO() :: START------------>:::::::");

		/* Setting HEADER attributes */
		manifestDO = outManifestTransferObjConverter(branchOutmanifestParcelTO,
				manifestDO);

		
		String manifestStatus = CommonConstants.EMPTY_STRING;

		int noOfConsg = 0;
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())
				&& !StringUtil.isEmptyInteger(manifestDO.getConsignments()
						.size())) {
			noOfConsg = manifestDO.getConsignments().size();
		}
		int noOfElements = noOfConsg ;
		Double manifestWeight = 0.0;
		for (BranchOutManifestParcelDetailsTO branchOutParcelDetailsTO : branchOutmanifestParcelTO
				.getBranchOutManifestParcelDetailsList()) {
			if (!StringUtil.isEmptyDouble(branchOutParcelDetailsTO.getWeight()))
				manifestWeight = manifestWeight + branchOutParcelDetailsTO.getWeight();
		}
		branchOutmanifestParcelTO.setFinalWeight(manifestWeight);
		manifestDO.setManifestWeight(manifestWeight);

		manifestStatus = branchOutmanifestParcelTO.getManifestStatus();
		if (StringUtils.isNotEmpty(branchOutmanifestParcelTO.getMaxCNsAllowed())
				&& StringUtils.isNotEmpty(branchOutmanifestParcelTO
						.getMaxComailsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(branchOutmanifestParcelTO
					.getMaxCNsAllowed());
		
			
			if (noOfConsg == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		manifestDO.setManifestStatus(manifestStatus);
	
		manifestDO.setNoOfElements(noOfElements);
		branchOutmanifestParcelTO.setNoOfElements(noOfElements);
		branchOutmanifestParcelTO.setManifestStatus(manifestStatus);
		LOGGER.trace("BranchOutManifestParcelConverter :: prepareManifestDO() :: END------------>:::::::");
		return manifestDO;
	}
	
}
