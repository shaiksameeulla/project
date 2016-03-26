package com.ff.web.manifest.converter;

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
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ComailManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.Utils.OutManifestTOFactory;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author nihsingh
 *
 */
public class BranchOutManifestDoxConverter extends OutManifestBaseConverter {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestParcelConverter.class);
	private static ConsignmentCommonService consignmentCommonService = null;
	/** The out manifest common service. */
	private static OutManifestCommonService outManifestCommonService;
	
	
	/**
	 * Sets the out manifest common service.
	 *
	 * @param outManifestCommonService the new out manifest common service
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		BranchOutManifestDoxConverter.outManifestCommonService = outManifestCommonService;
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
		BranchOutManifestDoxConverter.consignmentCommonService = consignmentCommonService;
	}
	
	
	
	/**@Desc Prepares list of branchoutmanifestdoxDetail
	 * @param branchOutManifestDoxTO
	 * @return List<BranchOutManifestDoxDetailsTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<BranchOutManifestDoxDetailsTO> prepareBranchOutManifestDtlDoxList(
			BranchOutManifestDoxTO branchOutManifestDoxTO) throws CGBusinessException,
			CGSystemException {
		BranchOutManifestDoxDetailsTO branchOutManifestDoxDetailsTO = null;
		List<BranchOutManifestDoxDetailsTO> branchOutManifestDoxDetailsTOs = null;
		int comailPosition = 1;
		if (!StringUtil.isNull(branchOutManifestDoxTO)) {
			branchOutManifestDoxDetailsTOs = new ArrayList<>(branchOutManifestDoxTO.getConsgNos().length + branchOutManifestDoxTO
					.getComailNos().length);
		//	if (branchOutManifestDoxTO.getConsgNos() != null)
			/*if (branchOutManifestDoxTO.getConsgNos() != null
					||branchOutManifestDoxTO.getComailNos()!=null){*/ 
				for (int rowCount = 0; rowCount < branchOutManifestDoxTO
						.getConsgNos().length; rowCount++) {
					
					
					
						// Setting the common grid level attributes
						branchOutManifestDoxDetailsTO = (BranchOutManifestDoxDetailsTO) setUpManifestDtlsTOs(
								branchOutManifestDoxTO, rowCount);
						// setting attributes specific to Branch Out manifest Dox
						
						/*if (!StringUtil.isEmpty(branchOutManifestDoxTO.getComailNos())
								&& StringUtils.isNotEmpty(branchOutManifestDoxTO.getComailNos()[rowCount])) {
							branchOutManifestDoxDetailsTO.setComailNo(branchOutManifestDoxTO.getComailNos()[rowCount]);
						}*/
						
						if (branchOutManifestDoxTO.getLcAmounts() != null
								&& branchOutManifestDoxTO.getLcAmounts().length > 0) {
							branchOutManifestDoxDetailsTO
									.setLcAmount(branchOutManifestDoxTO
											.getLcAmounts()[rowCount]);}
						
						if (branchOutManifestDoxTO.getBankNames() != null
								&& branchOutManifestDoxTO.getBankNames().length > 0){
							branchOutManifestDoxDetailsTO
									.setBankName(branchOutManifestDoxTO
											.getBankNames()[rowCount]);
						}
						
						//for removing null probm in consManifst table on delete
						if (!StringUtil.isEmptyInteger(branchOutManifestDoxTO.getConsgManifestedIds()[rowCount])) {
							branchOutManifestDoxDetailsTO
									.setConsgManifestedId(branchOutManifestDoxTO
											.getConsgManifestedIds()[rowCount]);
							
						}
						
						if (branchOutManifestDoxTO
								.getOldWeights()[rowCount]!=null){
						branchOutManifestDoxDetailsTO					//added for consignmnt wt updation in db
						.setOldWeight(branchOutManifestDoxTO
								.getOldWeights()[rowCount]);
						}

						branchOutManifestDoxDetailsTO.setPosition(
								branchOutManifestDoxTO.getPosition()[rowCount]);

						
						branchOutManifestDoxDetailsTOs.add(branchOutManifestDoxDetailsTO);
					}
				/**For saving comail**/
				ComailTO comailTO = null;
				for (int rowCount = 0; rowCount < branchOutManifestDoxTO.getComailNos().length; rowCount++) {

					// If the comail list is not empty
					if (StringUtils
							.isNotEmpty(branchOutManifestDoxTO.getComailNos()[rowCount])) {

						branchOutManifestDoxDetailsTO = new BranchOutManifestDoxDetailsTO();
						// UAT
						comailTO = new ComailTO();
						if (StringUtils
								.isNotEmpty(branchOutManifestDoxTO.getComailNos()[rowCount])) {
							branchOutManifestDoxDetailsTO.setComailNo(branchOutManifestDoxTO
									.getComailNos()[rowCount]);
							comailTO.setCoMailNo(branchOutManifestDoxTO.getConsgNos()[rowCount]);
							if (!StringUtil.isEmpty(branchOutManifestDoxTO.getComailIds())
									&& !StringUtil.isEmptyInteger(branchOutManifestDoxTO
											.getComailIds()[rowCount])) {
								//noOfElements++;
								branchOutManifestDoxDetailsTO.setComailId(branchOutManifestDoxTO
										.getComailIds()[rowCount]);
								// UAT
								comailTO.setCoMailId(branchOutManifestDoxTO.getConsgIds()[rowCount]);
								branchOutManifestDoxDetailsTO
										.setComailManifestedId(branchOutManifestDoxTO
												.getComailManifestedIds()[rowCount]);
							}
							branchOutManifestDoxDetailsTO.setPosition(comailPosition++);
							// UAT
							branchOutManifestDoxDetailsTO.setComailTO(comailTO);
							branchOutManifestDoxTO.getComails().add(comailTO);
							branchOutManifestDoxDetailsTO.setGridItemType("M");

						}
						//branchOutManifestDoxDetailsTOs.add(branchOutManifestDoxDetailsTO);
						/*branchOutManifestDoxTO.getBranchOutManifestDoxDetailsTOList().add(
								branchOutManifestDoxDetailsTO);*/
					}
					branchOutManifestDoxDetailsTOs.add(branchOutManifestDoxDetailsTO);
				//}
			}
		//return branchOutManifestDoxDetailsTOs;
	}
		return branchOutManifestDoxDetailsTOs;
	}
	
	
	/**@Desc Prepares the list of manifestDO
	 * @param branchoutmanifestDoxTO
	 * @return List<ManifestDO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*public static List<ManifestDO> prepareManifestDOList(
			BranchOutManifestDoxTO branchoutmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BranchOutManifestDoxConverter::prepareManifestDOList::START");
		List<ManifestDO> manifestDOs = new ArrayList<>();
		ManifestDO manifestDO = null;
		// Setting Common attributes
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(branchoutmanifestDoxTO);
		//Specific to BranchOut manifest dox
		//manifestDO.setContainsOnlyCoMail(branchoutmanifestDoxTO.getIsCoMailOnly());
		manifestDO.setDestOfficeId(branchoutmanifestDoxTO.getDestinationOfficeId());
		
		int noOfElements = CommonConstants.ZERO;
		Set<ConsignmentManifestDO> cnManifestSet = null;
		if (branchoutmanifestDoxTO.getConsgIds() != null
				&& branchoutmanifestDoxTO.getConsgIds().length > 0) {
			cnManifestSet = ManifestUtil
					.setConsignmentManifestDtlsForBOUT(branchoutmanifestDoxTO);
			noOfElements += cnManifestSet.size();
		}
		manifestDO.setManifestConsgDtls(cnManifestSet);
		
		
		
		
		
		//code start To update consg. wt in db if required.
				boolean result = Boolean.FALSE;
				for(OutManifestDetailBaseTO to : branchoutmanifestDoxTO
						.getBranchOutManifestDoxDetailsTOList()){
					try{
					if (!StringUtil.isNull(to)){
						if(to.getWeight()!=to.getOldWeight()){
								to.setDestOfficeId(manifestDO.getDestOffice().getOfficeId());
								to.setGridOriginOfficeId(manifestDO.getOriginOffice().getOfficeId());
							
							result = outManifestCommonService.updateConsgWeight(to, 
									branchoutmanifestDoxTO.getProcessId());
							if(!result){ FIXME remove this later if not required 
								LOGGER.info("Consignment weight not updated for consg. Id :" + to.getConsgId());
							}
						}
					}
					}
					catch(Exception e) {
						LOGGER.error("Exception occurs in BranchOutManifestParcelConverter::prepareManifestDOList()::"
							+ e.getMessage());
					}
				}
			//code end To update consg. wt in db if required.
				
		
		//manifestDO.setManifestDate(branchoutmanifestDoxTO.getManifestDate());
		// Set ConsignmentManifestDO
		Set<ConsignmentManifestDO> cnManifestSet1 = ManifestUtil
				.setConsignmentManifestDtls(branchoutmanifestDoxTO.getBranchOutManifestDoxDetailsTOList());
		manifestDO.setManifestConsgDtls(cnManifestSet1);
		// Set ComailManifestDO
				Set<ComailManifestDO> cMailManifestSet = null;
				if (!StringUtil.isEmpty(branchoutmanifestDoxTO.getComailNos())) {
					cMailManifestSet = setBranchCoMailManifestDtls(
							branchoutmanifestDoxTO,
							branchoutmanifestDoxTO.getBranchOutManifestDoxDetailsTOList());
					noOfElements += cMailManifestSet.size();
				}
		manifestDO.setNoOfElements(noOfElements);
		manifestDO.setLoadLotId(branchoutmanifestDoxTO.getLoadNo());
		if(!StringUtil.isNull(cMailManifestSet)){
		manifestDO.setManifestComailDtls(cMailManifestSet);
		}
		manifestDOs.add(manifestDO);
		LOGGER.trace("BranchOutManifestDoxConverter::prepareManifestDOList::END");
		return manifestDOs;
	}*/
	
	// Setting ComailManifestDO
		/**
		 * Sets the co mail manifest dtls.
		 *
		 * @param baseTO the base to
		 * @param outmanifestTOs the outmanifest t os
		 * @return the sets the
		 */
		public static Set<ComailManifestDO> setBranchCoMailManifestDtls(OutManifestBaseTO baseTO,
				List<? extends OutManifestDetailBaseTO> outmanifestTOs) {
			Set<ComailManifestDO> comailManifestSet = new HashSet<>(
					outmanifestTOs.size());
			Integer originOfficeId=baseTO.getLoginOfficeId();
			Integer destOfficeId=baseTO.getDestinationOfficeId();		
			for (OutManifestDetailBaseTO outmanifestDtlTO : outmanifestTOs) {
				if (!StringUtil.isNull(outmanifestDtlTO)){
				if(StringUtils.equalsIgnoreCase(outmanifestDtlTO.getIsCN(), CommonConstants.YES)){
					if (StringUtils.isNotEmpty(outmanifestDtlTO.getComailNo())) {
						ComailManifestDO comailMnfstdDO = new ComailManifestDO();
						
						
						ComailDO comailDO = new ComailDO();
						if(!StringUtil.isEmptyInteger(outmanifestDtlTO.getComailId())){
						comailDO.setCoMailId(outmanifestDtlTO.getComailId());
						
						}
						comailDO.setCoMailNo(outmanifestDtlTO.getComailNo());
						comailDO.setOriginOffice(originOfficeId);
						comailDO.setDestinationOffice(destOfficeId);
						comailMnfstdDO.setComailDO(comailDO);
						//comailMnfstdDO.setPosition(outmanifestDtlTO.getPosition());
						if(!StringUtil.isEmptyInteger(outmanifestDtlTO.getComailManifestedId())){
						comailMnfstdDO.setCoMailManifestId(outmanifestDtlTO.getComailManifestedId());
						}
						comailManifestSet.add(comailMnfstdDO);
					}
				}
				}			
			}

			return comailManifestSet;
		}
	
	
	
	
	/**@Desc prepares List of ManifestProcessDO
	 * @param branchOutmanifestDoxTO
	 * @return List<ManifestProcessDO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*public static List<ManifestProcessDO> prepareManifestProcessDOList(
			BranchOutManifestDoxTO branchOutmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		//int noOfElements = CommonConstants.ZERO;
		//Set<ConsignmentManifestDO> cnManifestSet = null;
		List<ManifestProcessDO> ManifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;
		// Setting Common attributes
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(branchOutmanifestDoxTO);
		// Specific to Branch Out manifest dox
		manifestProcessDO.setLoadLotId(branchOutmanifestDoxTO.getLoadNo());
		
		//addedto avoid duplicate entry in manifstProcess while saving
		if(!StringUtil.isNull(branchOutmanifestDoxTO.getManifestProcessTo())&& !StringUtil.isEmptyInteger(branchOutmanifestDoxTO.getManifestProcessTo().getManifestProcessId())){
			manifestProcessDO.setManifestProcessId(branchOutmanifestDoxTO.getManifestProcessTo().getManifestProcessId());
		}
		if (branchOutmanifestDoxTO.getConsgIds() != null
				&& branchOutmanifestDoxTO.getConsgIds().length > 0) {
			cnManifestSet = ManifestUtil
					.setConsignmentManifestDtlsForBOUT(branchOutmanifestDoxTO);
			noOfElements += cnManifestSet.size();
		}
		//manifestProcessDO.setNoOfElements(noOfElements);
		//manifestProcessDO.setNoOfElements(manifestDO.getNoOfElements());
		ManifestProcessDOs.add(manifestProcessDO);
		return ManifestProcessDOs;
	}*/
	
	
	/**@Desc converts the cons details to BranchOutMnfstDtl
	 * @param consignmentModificationTO
	 * @return BranchOutManifestDoxDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static BranchOutManifestDoxDetailsTO cnDtlsToBranchOutMnfstDtlBaseConverter(
			ConsignmentModificationTO consignmentModificationTO
			) throws CGBusinessException,
			CGSystemException {
		// Setting Common attributes
				BranchOutManifestDoxDetailsTO branchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) cnDtlsToOutMnfstDtlBaseConverter(
						consignmentModificationTO, ManifestUtil.getBranchOutManifestDoxFactory());
				// Setting specific attributes
				CityTO destCity=new CityTO();
				destCity.setCityId(consignmentModificationTO.getConsigmentTO().getDestPincode().getCityId());
			
				CityTO cityTO = outManifestCommonService.getCity(destCity);
			
				if (!StringUtil.isNull(cityTO)) {
					branchOutManifestDoxDtlTO.setDestCityId(cityTO.getCityId());
					branchOutManifestDoxDtlTO.setDestCity(cityTO.getCityName());
				}
				//Setting LC Amount details.
				//FIXME code commented
				/*CNPricingDetailsTO CNPricingDtlsTO = outManifestCommonService
						.getConsgPrincingDtls(branchOutManifestDoxDtlTO.getConsgNo());	*/
				//branchOutManifestDoxDtlTO.setLcAmount(CNPricingDtlsTO.getLcAmount());
				if(!StringUtil.isNull(consignmentModificationTO.getConsigmentTO().getLcBankName())){
					branchOutManifestDoxDtlTO.setBankName(consignmentModificationTO.getConsigmentTO().getLcBankName());
				}
				if(!StringUtil.isNull(consignmentModificationTO.getConsigmentTO().getLcAmount())){
					branchOutManifestDoxDtlTO.setLcAmount(consignmentModificationTO.getConsigmentTO().getLcAmount());
				}
				if(!StringUtil.isNull(consignmentModificationTO.getConsigmentTO().getTopayAmt())){
					branchOutManifestDoxDtlTO.setToPayAmount(consignmentModificationTO.getConsigmentTO().getTopayAmt());
				}
				if(!StringUtil.isNull(consignmentModificationTO.getConsigmentTO().getCodAmt())){
					branchOutManifestDoxDtlTO.setCodAmount(consignmentModificationTO.getConsigmentTO().getCodAmt());
				}
				
				
				
				/*if(!StringUtil.isNull(consignmentModificationTO.getConsigmentTO().getConsgRateDetails())){
					Set<ConsignmentRateTO> rateDetails= consignmentModificationTO.getConsigmentTO().getConsgRateDetails();
					for (ConsignmentRateTO consignmentRateTO : rateDetails) {
						if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES, consignmentRateTO.getRateComponent().getRateComponentCode())) {
							branchOutManifestDoxDtlTO.setLcAmount(consignmentRateTO.getRateComponent().getCalculatedValue());
							
						}
						if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES, consignmentRateTO.getRateComponent().getRateComponentCode())) {
							branchOutManifestDoxDtlTO.setToPayAmount(consignmentRateTO.getRateComponent().getCalculatedValue());
							
						}
						if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_COD, consignmentRateTO.getRateComponent().getRateComponentCode())) {
							branchOutManifestDoxDtlTO.setCodAmount(consignmentRateTO.getRateComponent().getCalculatedValue());
							
						}
						
					
					}
						
				}	*/
			
				
				
				
				return branchOutManifestDoxDtlTO;
		}
	
	/**@Desc Converts From ManifestDO to BranchOutManifestDoxTO
	 * @param manifestDO
	 * @return BranchOutManifestDoxTO
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	/*public static BranchOutManifestDoxTO branchOutManifestDoxDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException, CGSystemException {
		 int comailCount=0;
		 int cosignCount=0;
		// Set the common attributes for the header
		BranchOutManifestDoxTO branchOutManifestDoxTO = (BranchOutManifestDoxTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getBranchOutManifestDoxFactory());
		// Set the specific attributes for header
		branchOutManifestDoxTO.setManifestId(manifestDO.getManifestId());
		if (!StringUtil.isEmptyInteger(manifestDO.getLoadLotId()))
			branchOutManifestDoxTO.setLoadNo(manifestDO.getLoadLotId());
		

		// set the attributes for detail TO
		List<BranchOutManifestDoxDetailsTO> branchOutManifestDoxDetailTOs = new ArrayList<>(
				manifestDO.getNoOfElements());
		BranchOutManifestDoxDetailsTO barnchOutManifestDoxDtlTO = null;
		Set<ConsignmentManifestDO> manifestConsgDtls = manifestDO
				.getManifestConsgDtls();
		if (!StringUtil.isEmptyColletion(manifestConsgDtls)) {
			for (ConsignmentManifestDO manifestCN : manifestConsgDtls) {
				ConsignmentDO consignnmentDO = manifestCN.getConsignment();
				barnchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) ManifestUtil
						.consignmentDomainConverter(
								manifestCN.getConsignment(),
								ManifestUtil.getBranchOutManifestDoxFactory());
				cosignCount++;
				
				
				//TODO
				Set<ConsignmentRateDO> rateDetails = consignnmentDO.getRateDtls();
				for (ConsignmentRateDO consignmentRateDO : rateDetails) {
					RateComponentDO  rateComponentDO=consignmentRateDO.getRateComponentDO();
					if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES,rateComponentDO.getRateComponentCode())){
						barnchOutManifestDoxDtlTO.setLcAmount(consignmentRateDO.getCalculatedValue());
					}
					
				}
				//FIXME code commented
				if(!StringUtil.isNull(barnchOutManifestDoxDtlTO)) {
					CNPricingDetailsTO CNPricingDtlsTO = outManifestCommonService
							.getConsgPrincingDtls(barnchOutManifestDoxDtlTO.getConsgNo());
					if(!StringUtil.isNull(consignnmentDO.getConsgPricingDtls())){
						barnchOutManifestDoxDtlTO.setLcAmount(consignnmentDO.getConsgPricingDtls().getLcAmount());
					}
					//Double lcAmt = CNPricingDtlsTO.getLcAmount();
					//barnchOutManifestDoxDtlTO.setLcAmount(lcAmt);
				}
				
				
					String bankName = "HDFC";
					
					//barnchOutManifestDoxDtlTO.setBankName(bankName);
					if(!StringUtil.isNull(consignnmentDO.getConsgPricingDtls())){
						barnchOutManifestDoxDtlTO.setBankName(consignnmentDO.getConsgPricingDtls().getBankName());
					}
					barnchOutManifestDoxDtlTO.setPosition(manifestCN.getPosition());
					barnchOutManifestDoxDtlTO.setConsgManifestedId(manifestCN.getConsignmentManifestId());
				
				branchOutManifestDoxDetailTOs.add(barnchOutManifestDoxDtlTO);
			}
		}
		Set<ComailManifestDO> comailManifestDOs = manifestDO
				.getManifestComailDtls();
		if (!StringUtil.isEmptyColletion(comailManifestDOs)) {
			for (ComailManifestDO comailManifestDO : comailManifestDOs) {
				barnchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) 
						comailDomainConverter(comailManifestDO.getComailDO(),
								ManifestUtil.getBranchOutManifestDoxFactory());
				//barnchOutManifestDoxDtlTO.setLcAmount(0.00);
				//barnchOutManifestDoxDtlTO.setBankName("");
				barnchOutManifestDoxDtlTO.setComailManifestedId(comailManifestDO.getCoMailManifestId());
				comailCount++;
				//barnchOutManifestDoxDtlTO.setPosition(comailManifestDO.getPosition());
				branchOutManifestDoxDetailTOs.add(barnchOutManifestDoxDtlTO);
			}
		}
		branchOutManifestDoxTO.setPrintComailCount(comailCount);
		branchOutManifestDoxTO.setPrintConsigCount(cosignCount);
		Collections.sort(branchOutManifestDoxDetailTOs);
		branchOutManifestDoxTO.setBranchOutManifestDoxDetailsTOList(branchOutManifestDoxDetailTOs);
		return branchOutManifestDoxTO;
	}*/
	
	
	
	/**
	 * Comail domain converter.
	 *
	 * @param comailDO the comail do
	 * @param manifestFactoryTO the manifest factory to
	 * @return the out manifest detail base to
	 */
	public static OutManifestDetailBaseTO comailDomainConverter(
			ComailDO comailDO, ManifestFactoryTO manifestFactoryTO) {
		OutManifestDetailBaseTO outManifestDtlTO = null;
		if (!StringUtil.isNull(comailDO)) {
			outManifestDtlTO = OutManifestTOFactory
					.getOutManifestDetailBaseTO(manifestFactoryTO);
			outManifestDtlTO.setComailId(comailDO.getCoMailId());
			outManifestDtlTO.setComailNo(comailDO.getCoMailNo());
			//outManifestDtlTO.setWeight(comailDO.getManifestWeight());
		}
		return outManifestDtlTO;
	}
	
	public static OutManifestDetailBaseTO setUpBranchManifestDoxDtlsTOs(OutManifestBaseTO manifestBaseTO, int rowCount){
	
	ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
	if (manifestBaseTO.getConsignmentTypeTO() != null)
		if (manifestBaseTO.getConsignmentTypeTO().getConsignmentCode() != null)
			manifestFactoryTO.setConsgType(manifestBaseTO
					.getConsignmentTypeTO().getConsignmentCode());
	if (manifestBaseTO.getManifestType() != null)
		manifestFactoryTO.setManifestType(manifestBaseTO.getManifestType());

	OutManifestDetailBaseTO manifestTO = OutManifestTOFactory
			.getOutManifestDetailBaseTO(manifestFactoryTO);
	return manifestTO;
	}
	
	public static BranchOutManifestDoxDetailsTO branchOutGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		BranchOutManifestDoxDetailsTO branchOutManifestDetailsTO = new BranchOutManifestDoxDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			branchOutManifestDetailsTO.setConsgNo(consTO.getConsgNo());
			branchOutManifestDetailsTO.setConsgId(consTO.getConsgId());
		}
	
		if(!StringUtil.isNull(consTO.getDestPincode())){
		//branchOutManifestDetailsTO.setPincode(consTO.getDestPincode().getPincode());
		branchOutManifestDetailsTO
				.setPincodeId(consTO.getDestPincode().getPincodeId());
		}
		
		if(!StringUtil.isNull(consTO.getDestCity())){
		branchOutManifestDetailsTO.setDestCityId(consTO.getDestCity().getCityId());
		branchOutManifestDetailsTO.setDestCity(consTO.getDestCity().getCityName());
		}
		
		if(!StringUtil.isNull(consTO.getFinalWeight())){
		branchOutManifestDetailsTO.setWeight(consTO.getFinalWeight());
		branchOutManifestDetailsTO.setBkgWeight(consTO.getFinalWeight());
		}
		
		if(!StringUtil.isNull(consTO.getLcBankName())){
			branchOutManifestDetailsTO.setBankName(consTO.getLcBankName());
		}
		if(!StringUtil.isNull(consTO.getLcAmount())){
			branchOutManifestDetailsTO.setLcAmount(consTO.getLcAmount());
		}
		if(!StringUtil.isNull(consTO.getTopayAmt())){
			branchOutManifestDetailsTO.setToPayAmount(consTO.getTopayAmt());
		}
		if(!StringUtil.isNull(consTO.getCodAmt())){
			branchOutManifestDetailsTO.setCodAmount(consTO.getCodAmt());
		}

		return branchOutManifestDetailsTO;
	}
	
	public static ManifestDO prepareManifestDO(
			BranchOutManifestDoxTO branchOutmanifestDoxTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestDoxConverter :: prepareManifestDO() :: START------------>:::::::");

		/* Setting HEADER attributes */
		manifestDO = outManifestTransferObjConverter(branchOutmanifestDoxTO,
				manifestDO);

		/* Specific to Out manifest dox */
		/*manifestDO.setContainsOnlyCoMail(branchOutmanifestDoxTO.getIsCoMailOnly());
		if (!StringUtil.isNull(outmanifestDoxTO.getProduct())) {
			ProductDO productDO = new ProductDO();
			productDO = (ProductDO) CGObjectConverter.createDomainFromTo(
					outmanifestDoxTO.getProduct(), productDO);
			manifestDO.setManifestedProductSeries(productDO);
			manifestDO.setAllowSpecificContent(CommonConstants.YES);
		} else {
			manifestDO.setAllowSpecificContent(CommonConstants.NO);
		}*/
		/* Newly added fields for BCUN */
		//manifestDO.setManifestOpenType(branchOutmanifestDoxTO.getManifestOpenType());

		/*
		 * Setting grid items Set ConsignmentManifestDO Setting corresponding
		 * Consignment Ids in Out manifest details TO
		 */
		/*
		 * if (!StringUtil.isEmptyList(consignmentDOList)) {
		 * setPickedupConsignmentIds(outmanifestDoxTO, consignmentDOList); }
		 */
		String manifestStatus = CommonConstants.EMPTY_STRING;

		int noOfConsg = 0, noOfComail = 0;
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())
				&& !StringUtil.isEmptyInteger(manifestDO.getConsignments()
						.size())) {
			noOfConsg = manifestDO.getConsignments().size();
		}
		
		if (!StringUtil.isEmptyColletion(manifestDO.getComails())
				&& !StringUtil.isEmptyInteger(manifestDO.getComails().size())) {
			noOfComail = manifestDO.getComails().size();
		}

		int noOfElements = noOfConsg + noOfComail;

		/** Set ConsignmentDO */
		/*
		 * Set<ConsignmentDO> consignments = new LinkedHashSet<ConsignmentDO>();
		 * List<String> consgNoList = new ArrayList<String>();
		 * List<ConsignmentDO> consgDOList = null; if
		 * (!StringUtil.isEmpty(outmanifestDoxTO.getConsgNos())) { Prepare
		 * consigment nos. list for booked CN. for (int i = 0; i <
		 * outmanifestDoxTO.getConsgNos().length; i++) { if
		 * (outmanifestDoxTO.getStatus()[i] == "N") {
		 * consgNoList.add(outmanifestDoxTO.getConsgNos()[i]); } }
		 * 
		 * Get all consg. by consg. nos. if
		 * (!StringUtil.isEmptyColletion(consgNoList) && consgNoList.size() > 0)
		 * { consgDOList = manifestCommonService .getConsignments(consgNoList);
		 * consignments.addAll(consgDOList); }
		 * 
		 * if (!StringUtil.isEmptyColletion(consignmentDOList)) {
		 * consignments.addAll(consignmentDOList); } noOfElements +=
		 * consignments.size(); manifestDO.setConsignments(consignments); }
		 *//** Set ComailDO */
		/*
		 * 
		 * Set<ComailDO> comailSet = null; if
		 * (!StringUtil.isEmptyColletion(manifestDO.getComails())) {
		 * 
		 * comailSet = ManifestUtil.setCoMailManifestDtls(outmanifestDoxTO,
		 * outmanifestDoxTO.getOutManifestDoxDetailTOs());
		 * 
		 * comailSet = ManifestUtil.setCoMailDtls(outmanifestDoxTO,
		 * outmanifestDoxTO.getOutManifestDoxDetailTOs(),
		 * manifestDO.getComails()); noOfElements += comailSet.size();
		 * manifestDO.setComails(comailSet); }
		 */

		Double manifestWeight = 0.0;
		for (BranchOutManifestDoxDetailsTO branchOutDoxDetailsTO : branchOutmanifestDoxTO
				.getBranchOutManifestDoxDetailsTOList()) {
			if (!StringUtil.isEmptyDouble(branchOutDoxDetailsTO.getWeight()))
				manifestWeight = manifestWeight + branchOutDoxDetailsTO.getWeight();
		}
		branchOutmanifestDoxTO.setFinalWeight(manifestWeight);
		manifestDO.setManifestWeight(manifestWeight);

		manifestStatus = branchOutmanifestDoxTO.getManifestStatus();
		if (StringUtils.isNotEmpty(branchOutmanifestDoxTO.getMaxCNsAllowed())
				&& StringUtils.isNotEmpty(branchOutmanifestDoxTO
						.getMaxComailsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(branchOutmanifestDoxTO
					.getMaxCNsAllowed());
			// if Comail is checked all the rows in the grid are considered as
			// co-mails only and max grid size is max CNs and max Co-mails.
			if (noOfConsg == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		manifestDO.setManifestStatus(manifestStatus);
		// manifestDO.setComails(comailSet);
		manifestDO.setNoOfElements(noOfElements);
		branchOutmanifestDoxTO.setNoOfElements(noOfElements);
		branchOutmanifestDoxTO.setManifestStatus(manifestStatus);
		LOGGER.trace("BranchOutManifestDoxConverter :: prepareManifestDO() :: END------------>:::::::");
		return manifestDO;
	}
	
	
	/**@Desc Converts From ManifestDO to BranchOutManifestDoxTO
	 * @param manifestDO
	 * @return BranchOutManifestDoxTO
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	public static BranchOutManifestDoxTO branchOutManifestDoxDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException, CGSystemException {
		 int comailCount=0;
		 int cosignCount=0;
		// Set the common attributes for the header
		BranchOutManifestDoxTO branchOutManifestDoxTO = (BranchOutManifestDoxTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getBranchOutManifestDoxFactory());
		// Set the specific attributes for header
		branchOutManifestDoxTO.setManifestId(manifestDO.getManifestId());
		if (!StringUtil.isEmptyInteger(manifestDO.getLoadLotId())){
			branchOutManifestDoxTO.setLoadNo(manifestDO.getLoadLotId());
		}
		

		// prepare consignment details
				Set<ConsignmentDO> consignmentDOs = null;
		
		// set the attributes for detail TO
		List<BranchOutManifestDoxDetailsTO> branchOutManifestDoxDetailTOs = new ArrayList<>(
				manifestDO.getNoOfElements());
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			consignmentDOs = manifestDO.getConsignments();
		for (ConsignmentDO consignment : consignmentDOs) {
			BranchOutManifestDoxDetailsTO branchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) ManifestUtil
					.consignmentDomainConverter(
							consignment,
							ManifestUtil
									.prepareFactoryInputs(
											OutManifestConstants.BRANCH_MANIFEST,
				
											CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));
		//setting the specific details for detailTO from cnPrice(one way)
			if(!StringUtil.isNull(consignment.getLcBankName())){
				branchOutManifestDoxDtlTO.setBankName(consignment.getLcBankName());
			}
			if(!StringUtil.isNull(consignment.getLcAmount())){
				branchOutManifestDoxDtlTO.setLcAmount(consignment.getLcAmount());
			}
			
			
			
			//setting the specific details for detailTO from cnPrice(other way)
			/*CNPricingDetailsTO CNPricingDtlsTO = consignmentCommonService
					.getConsgPrincingDtls(consignment.getConsgNo());
				if (!StringUtil.isNull(CNPricingDtlsTO)) {
					branchOutManifestDoxDtlTO.setBankName(CNPricingDtlsTO
						.getBankName());
					branchOutManifestDoxDtlTO.setLcAmount(CNPricingDtlsTO
							.getLcAmount());
				
				}*/
			
			// Set Destination
			/*CityTO city = outManifestCommonService
					.getCity(outManifestDoxDtlTO.getPincode());
			if (!StringUtil.isNull(city)) {
				outManifestDoxDtlTO.setDestCityId(city.getCityId());
				outManifestDoxDtlTO.setDestCity(city.getCityName());
			}*/
			// Set Consignee
			/*if (!StringUtil.isNull(doxConsignment.getConsignee())) {
				outManifestDoxDtlTO.setMobileNo(doxConsignment
						.getConsignee().getMobile());
				outManifestDoxDtlTO.setConsigneeId(doxConsignment
						.getConsignee().getPartyId());
			}*/
			// Total weight for printer
			if (!StringUtil.isEmptyDouble(branchOutManifestDoxDtlTO.getWeight())) {
				branchOutManifestDoxDtlTO.getWeight();
			}
			cosignCount++;
			branchOutManifestDoxDetailTOs.add(branchOutManifestDoxDtlTO);
		}/* END of FOR EACH */
		}//end of if
		
		
		// prepare comail details
				Set<ComailDO> comails = null;
				// Set the attributes for detailTO
				if (!StringUtil.isEmptyColletion(manifestDO.getComails())) {
					comails = manifestDO.getComails();

					for (ComailDO comailDO : comails) {
						BranchOutManifestDoxDetailsTO bracnhOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) ManifestUtil
								.comailDomainConverter(
										comailDO,
										ManifestUtil
												.prepareFactoryInputs(
														OutManifestConstants.BRANCH_MANIFEST,
														CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));
						branchOutManifestDoxDetailTOs.add(bracnhOutManifestDoxDtlTO);
						comailCount++;
					}/* END of FOR EACH */
				}
		
				branchOutManifestDoxTO.setPrintComailCount(comailCount);
				branchOutManifestDoxTO.setPrintConsigCount(cosignCount);
				Collections.sort(branchOutManifestDoxDetailTOs);
				branchOutManifestDoxTO.setBranchOutManifestDoxDetailsTOList(branchOutManifestDoxDetailTOs);
				//branchOutManifestDoxTO.setTotalConsg(count);
				//branchOutManifestDoxTO.setTotalLcAmount(lcTotal);
				//branchOutManifestDoxTO.setConsigTotalWt(Double.parseDouble(new DecimalFormat("##.###").format(consgToatalWt)));
				LOGGER.trace("OutManifestDoxConverter :: outManifestDoxDomainConverter() :: END------------>:::::::");
				return branchOutManifestDoxTO;
		
		/*BranchOutManifestDoxDetailsTO barnchOutManifestDoxDtlTO = null;
		Set<ConsignmentManifestDO> manifestConsgDtls = manifestDO
				.getManifestConsgDtls();
		if (!StringUtil.isEmptyColletion(manifestConsgDtls)) {
			for (ConsignmentManifestDO manifestCN : manifestConsgDtls) {
				ConsignmentDO consignnmentDO = manifestCN.getConsignment();
				barnchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) ManifestUtil
						.consignmentDomainConverter(
								manifestCN.getConsignment(),
								ManifestUtil.getBranchOutManifestDoxFactory());
				cosignCount++;
				
				
				//TODO
				Set<ConsignmentRateDO> rateDetails = consignnmentDO.getRateDtls();
				for (ConsignmentRateDO consignmentRateDO : rateDetails) {
					RateComponentDO  rateComponentDO=consignmentRateDO.getRateComponentDO();
					if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES,rateComponentDO.getRateComponentCode())){
						barnchOutManifestDoxDtlTO.setLcAmount(consignmentRateDO.getCalculatedValue());
					}
					
				}
				//FIXME code commented
				if(!StringUtil.isNull(barnchOutManifestDoxDtlTO)) {
					CNPricingDetailsTO CNPricingDtlsTO = outManifestCommonService
							.getConsgPrincingDtls(barnchOutManifestDoxDtlTO.getConsgNo());
					if(!StringUtil.isNull(consignnmentDO.getConsgPricingDtls())){
						barnchOutManifestDoxDtlTO.setLcAmount(consignnmentDO.getConsgPricingDtls().getLcAmount());
					}
					//Double lcAmt = CNPricingDtlsTO.getLcAmount();
					//barnchOutManifestDoxDtlTO.setLcAmount(lcAmt);
				}
				
				
					String bankName = "HDFC";
					
					//barnchOutManifestDoxDtlTO.setBankName(bankName);
					if(!StringUtil.isNull(consignnmentDO.getConsgPricingDtls())){
						barnchOutManifestDoxDtlTO.setBankName(consignnmentDO.getConsgPricingDtls().getBankName());
					}
					barnchOutManifestDoxDtlTO.setPosition(manifestCN.getPosition());
					barnchOutManifestDoxDtlTO.setConsgManifestedId(manifestCN.getConsignmentManifestId());
				
				branchOutManifestDoxDetailTOs.add(barnchOutManifestDoxDtlTO);
			}
		}*/
		/*Set<ComailManifestDO> comailManifestDOs = manifestDO
				.getManifestComailDtls();
		if (!StringUtil.isEmptyColletion(comailManifestDOs)) {
			for (ComailManifestDO comailManifestDO : comailManifestDOs) {
				barnchOutManifestDoxDtlTO = (BranchOutManifestDoxDetailsTO) 
						comailDomainConverter(comailManifestDO.getComailDO(),
								ManifestUtil.getBranchOutManifestDoxFactory());
				//barnchOutManifestDoxDtlTO.setLcAmount(0.00);
				//barnchOutManifestDoxDtlTO.setBankName("");
				barnchOutManifestDoxDtlTO.setComailManifestedId(comailManifestDO.getCoMailManifestId());
				comailCount++;
				//barnchOutManifestDoxDtlTO.setPosition(comailManifestDO.getPosition());
				branchOutManifestDoxDetailTOs.add(barnchOutManifestDoxDtlTO);
			}
		}*/
		/*branchOutManifestDoxTO.setPrintComailCount(comailCount);
		branchOutManifestDoxTO.setPrintConsigCount(cosignCount);
		Collections.sort(branchOutManifestDoxDetailTOs);
		branchOutManifestDoxTO.setBranchOutManifestDoxDetailsTOList(branchOutManifestDoxDetailTOs);
		return branchOutManifestDoxTO;*/
	}
}