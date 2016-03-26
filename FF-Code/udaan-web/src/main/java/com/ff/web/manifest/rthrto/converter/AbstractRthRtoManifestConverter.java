package com.ff.web.manifest.rthrto.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.OutManifestDestinationDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.utils.RthRtoManifestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRthRtoManifestConverter.
 */
public abstract class AbstractRthRtoManifestConverter {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractRthRtoManifestConverter.class);
	/**
	 * Convert consignment toto rth rto details to.
	 *
	 * @param consignmentTO the consignment to
	 * @return the rth rto details to
	 * @throws CGBusinessException the cG business exception
	 */
	public static RthRtoDetailsTO convertConsignmentTotoRthRtoDetailsTO(
			ConsignmentTO consignmentTO) throws CGBusinessException {
		LOGGER.trace("AbstractRthRtoManifestConverter::convertConsignmentTotoRthRtoDetailsTO()::START");
		RthRtoDetailsTO detailsTO = new RthRtoDetailsTO();
		detailsTO.setConsgNumber(consignmentTO.getConsgNo());
		detailsTO.setConsignmentId(consignmentTO.getConsgId());
		detailsTO.setNumOfPc(consignmentTO.getNoOfPcs());
		if (!StringUtil.isNull(consignmentTO.getDestPincode()))
			detailsTO.setPincode(consignmentTO.getDestPincode().getPincode());
		detailsTO.setActualWeight(consignmentTO.getFinalWeight());
		StringBuilder cnContent = new StringBuilder();
		if (!StringUtil.isNull(consignmentTO.getCnContents())) {
			if (!StringUtil.isEmptyInteger(consignmentTO.getCnContents()
					.getCnContentId())){				
				cnContent=cnContent.append(consignmentTO.getCnContents().getCnContentCode()); 
				if(StringUtils.isNotEmpty(consignmentTO.getCnContents().getCnContentName())){
					cnContent=cnContent.append(CommonConstants.HYPHEN);
					cnContent=cnContent.append(consignmentTO.getCnContents().getCnContentName());
				}	
				else{
					cnContent=cnContent.append(RthRtoManifestConstatnts.CN_CONTENT_OTHER); 
					if(StringUtils.isNotEmpty(consignmentTO.getCnContents().getOtherContent())){
						cnContent=cnContent.append(CommonConstants.HYPHEN);
						cnContent=cnContent.append(consignmentTO.getCnContents().getOtherContent());
					}
				}
			}
			detailsTO.setCnContent(cnContent.toString());	
			// to print other contents
			if(!StringUtil.isStringEmpty(consignmentTO.getCnContents().getCnContentName())){
				detailsTO.setCnContentName(consignmentTO.getCnContents().getCnContentName());
			}
			if(!StringUtil.isStringEmpty(consignmentTO.getOtherCNContent())){
				detailsTO.setOtherContent(consignmentTO.getOtherCNContent());
			}
		}
		
		StringBuilder cnPaperWorks = new StringBuilder();
		if (!StringUtil.isNull(consignmentTO.getCnPaperWorks())){
			cnPaperWorks=cnPaperWorks.append(consignmentTO.getCnPaperWorks().getCnPaperWorkName()); 
			if(StringUtils.isNotEmpty(consignmentTO.getCnPaperWorks().getPaperWorkRefNum())){
				cnPaperWorks=cnPaperWorks.append(CommonConstants.HYPHEN);
				cnPaperWorks=cnPaperWorks.append(consignmentTO.getCnPaperWorks().getPaperWorkRefNum());
			}
			detailsTO.setCnPaperWorks(cnPaperWorks.toString());
		}
		
		/* For RTO DOX/PPX, check DEST. city & ORIGIN city of consignment should be same. */
		if(!StringUtil.isEmptyInteger(consignmentTO.getOrgOffId())){
			detailsTO.setCnOriginOfficeId(consignmentTO.getOrgOffId());
		}
		
		//TODO : Rate Integration
		/*CNPricingDetailsTO cnPricingDetailsTO = consignmentTO.getConsgPriceDtls();
		if (cnPricingDetailsTO != null) {
			detailsTO.setCodAmt(cnPricingDetailsTO
					.getCodAmt());
			detailsTO.setToPayAmt(cnPricingDetailsTO
					.getTopayChg());
		}*/

		// Added by Himal
		// To set To pay and COD amount
		if (!StringUtil.isEmptyDouble(consignmentTO.getTopayAmt())) {
			detailsTO.setToPayAmt(consignmentTO.getTopayAmt());
		}
		if (!StringUtil.isEmptyDouble(consignmentTO.getCodAmt())) {
			detailsTO.setCodAmt(consignmentTO.getCodAmt());
		}
		
		/*Set<ConsignmentRateTO> rateDetails = null;//consignmentTO.getConsgRateDetails();
		if(StringUtil.isNull(rateDetails)){
			throw new CGBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_RATE_NOT_FOUND);
		}else{
			for (ConsignmentRateTO consignmentRateTO : rateDetails) {
				RateComponentTO  rateComponentTO=consignmentRateTO.getRateComponent();
				if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_COD,rateComponentTO.getRateComponentCode())){
					detailsTO.setCodAmt(consignmentRateTO.getCalculatedValue());
				}
				if(StringUtils.equalsIgnoreCase(RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES,rateComponentTO.getRateComponentCode())){
					detailsTO.setToPayAmt(consignmentRateTO.getCalculatedValue());
				}			
			}
		}*/
		LOGGER.trace("AbstractRthRtoManifestConverter::convertConsignmentTotoRthRtoDetailsTO()::END");
		return detailsTO;
	}
	
	/**
	 * Rth rto manifest domainconverter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the manifest do
	 * @throws CGBusinessException the cG business exception
	 */
	public static ManifestDO rthRtoManifestDomainconverter(RthRtoManifestTO rthRtoManifestTO) throws CGBusinessException{
		LOGGER.trace("AbstractRthRtoManifestConverter::rthRtoManifestDomainconverter()::START");
		ManifestDO manifestDO=new ManifestDO();
		RthRtoManifestUtils.setCreatedAndUpdatedDate(manifestDO);
		if(!StringUtil.isEmptyInteger(rthRtoManifestTO.getManifestId())){
				manifestDO.setManifestId(rthRtoManifestTO.getManifestId());
				RthRtoManifestUtils.setUpdateFlag4DBSync(manifestDO);
		}else{
			RthRtoManifestUtils.setSaveFlag4DBSync(manifestDO);
			//generateAndSetProcessNumber(rthRtoManifestTO);
		}
		manifestDO.setManifestNo(rthRtoManifestTO.getManifestNumber());
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
//		manifestDO.setManifestDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(rthRtoManifestTO.getManifestDate()));
		manifestDO.setManifestDate(Calendar.getInstance().getTime());
		OfficeDO officeDO = null;
		if (!StringUtil.isNull(rthRtoManifestTO.getOriginOfficeTO())) {
			officeDO = new OfficeDO();
			officeDO=(OfficeDO) CGObjectConverter.createDomainFromTo(rthRtoManifestTO.getOriginOfficeTO(), officeDO);
			manifestDO.setOriginOffice(officeDO);
			manifestDO.setOperatingOffice(rthRtoManifestTO.getOriginOfficeTO().getOfficeId());
		}
		if (!StringUtil.isNull(rthRtoManifestTO.getDestCityTO()) 
				&& !StringUtil.isEmptyInteger(rthRtoManifestTO.getDestCityTO().getCityId())) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(rthRtoManifestTO.getDestCityTO().getCityId());
			manifestDO.setDestinationCity(cityDO);
		}
		if (!StringUtil.isNull(rthRtoManifestTO.getDestOfficeTO())) {
			officeDO = new OfficeDO();
			officeDO=(OfficeDO) CGObjectConverter.createDomainFromTo(rthRtoManifestTO.getDestOfficeTO(), officeDO);
			manifestDO.setDestOffice(officeDO);			
		}
		manifestDO.setManifestType(rthRtoManifestTO.getManifestType());
		if (!StringUtil.isNull(rthRtoManifestTO.getConsignmentTypeTO())) {
			ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
			consgTypeDO.setConsignmentId(rthRtoManifestTO
					.getConsignmentTypeTO().getConsignmentId());
			manifestDO.setManifestLoadContent(consgTypeDO);
		}
		if(!StringUtil.isNull(rthRtoManifestTO.getUpdateProcessTO())){
			ProcessDO processDO = new ProcessDO();
			processDO=(ProcessDO) CGObjectConverter.createDomainFromTo(rthRtoManifestTO.getUpdateProcessTO(), processDO);
			manifestDO.setUpdatingProcess(processDO);
			manifestDO.setManifestProcessCode(rthRtoManifestTO.getUpdateProcessTO().getProcessCode());		
		}

		if (!StringUtil.isEmptyColletion(rthRtoManifestTO.getDestOffIds())) {
			Set<OutManifestDestinationDO> outManifestDestSet=setOutManifestDestinationDtls(rthRtoManifestTO);
			manifestDO.setMultipleDestinations(outManifestDestSet);
		}
		
		manifestDO.setManifestStatus(RthRtoManifestConstatnts.MANIFEST_STATUS_CLOSE);		
		//New columns for BCUN Support
		manifestDO.setManifestDirection(RthRtoManifestConstatnts.MANIFEST_DIRECTION_OUT);
		
		//Added by Himal
		if(!StringUtil.isStringEmpty(rthRtoManifestTO.getBagLockNo())) {
			manifestDO.setBagLockNo(rthRtoManifestTO.getBagLockNo());
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::rthRtoManifestDomainconverter()::END");
		return manifestDO;
	}
	
	/**
	 * Sets the out manifest destination dtls.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the sets the
	 */
	public static Set<OutManifestDestinationDO> setOutManifestDestinationDtls(
			RthRtoManifestTO rthRtoManifestTO){
		LOGGER.trace("AbstractRthRtoManifestConverter::setOutManifestDestinationDtls()::START");
		Set<OutManifestDestinationDO> outManifestDestSet = null;
		Set<Integer> destOfficeIds =rthRtoManifestTO.getDestOffIds();
		outManifestDestSet = new HashSet<>(destOfficeIds.size());
		for (Integer officeId : destOfficeIds) {
			OutManifestDestinationDO mnfstDest = null;
			mnfstDest = setOutManifestDestDO(officeId);
			outManifestDestSet.add(mnfstDest);
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::setOutManifestDestinationDtls()::END");
		return outManifestDestSet;
	}
	
	/**
	 * Sets the out manifest dest do.
	 *
	 * @param officeId the office id
	 * @return the out manifest destination do
	 */
	private static OutManifestDestinationDO setOutManifestDestDO(Integer officeId) {
		LOGGER.trace("AbstractRthRtoManifestConverter::setOutManifestDestDO()::START");
		OutManifestDestinationDO mnfstDest = new OutManifestDestinationDO();
		RthRtoManifestUtils.setSaveFlag4DBSync(mnfstDest);
		OfficeDO officeDO = new OfficeDO();
		officeDO.setOfficeId(officeId);
		mnfstDest.setOffice(officeDO);
		LOGGER.trace("AbstractRthRtoManifestConverter::setOutManifestDestDO()::END");
		return mnfstDest;		
	}
	
	/**
	 * Rth rto manifest process domainconverter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the manifest process do
	 * @throws CGBusinessException the cG business exception
	 */
	/*public static ManifestProcessDO rthRtoManifestProcessDomainconverter(RthRtoManifestTO rthRtoManifestTO) throws CGBusinessException{
		LOGGER.trace("AbstractRthRtoManifestConverter::rthRtoManifestProcessDomainconverter()::START");
		ManifestProcessDO manifestProcessDO=new ManifestProcessDO();
		RthRtoManifestUtils.setCreatedAndUpdatedDate(manifestProcessDO);
		if(!StringUtil.isEmptyInteger(rthRtoManifestTO.getManifestProcessId())){
				manifestProcessDO.setManifestProcessId(rthRtoManifestTO.getManifestProcessId());
				RthRtoManifestUtils.setUpdateFlag4DBSync(manifestProcessDO);
		}else{
			RthRtoManifestUtils.setSaveFlag4DBSync(manifestProcessDO);
			//generateAndSetProcessNumber(rthRtoManifestTO);
		}
		manifestProcessDO.setManifestNo(rthRtoManifestTO.getManifestNumber());
		manifestProcessDO.setManifestWeight(rthRtoManifestTO.getManifestWeight());
		manifestProcessDO.setManifestDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(rthRtoManifestTO
						.getManifestDate()));
		manifestProcessDO.setManifestDirection(RthRtoManifestConstatnts.MANIFEST_DIRECTION_OUT);
		if (!StringUtil.isNull(rthRtoManifestTO.getOriginOfficeTO())) {
			manifestProcessDO.setOriginOfficeId(rthRtoManifestTO.getOriginOfficeTO().getOfficeId());
		}
		if (!StringUtil.isNull(rthRtoManifestTO.getDestOfficeTO())) {
			manifestProcessDO.setDestOfficeId(rthRtoManifestTO.getDestOfficeTO().getOfficeId());			
		}
		int noofElements=rthRtoManifestTO.getRthRtoDetailsTOs().size();
		manifestProcessDO.setNoOfElements(noofElements);
		manifestProcessDO.setManifestStatus(RthRtoManifestConstatnts.MANIFEST_STATUS_CLOSE);
		
		// Added By Himal to set Destination City 
		if (!StringUtil.isNull(rthRtoManifestTO.getDestCityTO()) 
				&& !StringUtil.isEmptyInteger(rthRtoManifestTO.getDestCityTO().getCityId())) {
			manifestProcessDO.setDestCityId(rthRtoManifestTO.getDestCityTO().getCityId());
		}
		//Added by Himal
		if(!StringUtil.isStringEmpty(rthRtoManifestTO.getBagLockNo())) {
			manifestProcessDO.setBagLockNo(rthRtoManifestTO.getBagLockNo());
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::rthRtoManifestProcessDomainconverter()::END");
		return manifestProcessDO;
	}*/
	
	/**
	 * Manifest domain converter.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @param manifestDO the manifest do
	 * @throws CGBusinessException the cG business exception
	 */
	public static void manifestDomainConverter(RthRtoManifestTO rthRtoManifestTO,ManifestDO manifestDO) throws CGBusinessException{
		LOGGER.trace("AbstractRthRtoManifestConverter::manifestDomainConverter()::START");
		rthRtoManifestTO.setManifestDate(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(manifestDO.getManifestDate()));
		rthRtoManifestTO.setManifestId(manifestDO.getManifestId());
		rthRtoManifestTO.setManifestNumber(manifestDO.getManifestNo());
		
		OfficeDO destOfficeDO=manifestDO.getDestOffice();
		if(!StringUtil.isNull(destOfficeDO)){
			if (!StringUtil.isNull(destOfficeDO.getMappedRegionDO())) {
				RegionTO regionTO = new RegionTO();
				regionTO.setRegionId(destOfficeDO.getMappedRegionDO().getRegionId());
				//regionTO.setRegionName(destOfficeDO.getMappedRegionDO().getRegionName());
				rthRtoManifestTO.setDestRegionTO(regionTO);
			}
			CityDO cityDO = manifestDO.getDestinationCity();
			if (!StringUtil.isNull(cityDO)) {				
				CityTO cityTO = new CityTO();
				cityTO.setCityId(cityDO.getCityId());
				cityTO.setCityCode(cityDO.getCityCode());
				cityTO.setCityName(cityDO.getCityName());
				rthRtoManifestTO.setDestCityTO(cityTO);
			}
		
			OfficeTO destOfficeTO=new OfficeTO();
			destOfficeTO.setOfficeId(destOfficeDO.getOfficeId());
			destOfficeTO.setOfficeName(destOfficeDO.getOfficeName());
			rthRtoManifestTO.setDestOfficeTO(destOfficeTO);
		}
		//Added by Himal
		if(!StringUtil.isStringEmpty(manifestDO.getBagLockNo())) {
			rthRtoManifestTO.setBagLockNo(manifestDO.getBagLockNo());
		}
		//added to print origin office address
		if(!StringUtil.isNull(manifestDO.getOriginOffice())){
			OfficeTO originOfficeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(manifestDO.getOriginOffice(), originOfficeTO);
			rthRtoManifestTO.setOriginOfficeTO(originOfficeTO);
		}
		if(!StringUtil.isNull(manifestDO.getOriginOffice().getPincode())){
			rthRtoManifestTO.setPincode(manifestDO.getOriginOffice().getPincode());
		}
		//Grid Data
		List<RthRtoDetailsTO> rthRtoDetailsTOs=getConsignmentManifestDtls(manifestDO);
		//Collections.sort(rthRtoDetailsTOs);
		rthRtoManifestTO.setRthRtoDetailsTOs(rthRtoDetailsTOs);		
		LOGGER.trace("AbstractRthRtoManifestConverter::manifestDomainConverter()::END");
	}
	
	/**
	 * Gets the consignment manifest dtls.
	 *
	 * @param manifestDO the manifest do
	 * @return the consignment manifest dtls
	 * @throws CGBusinessException the cG business exception
	 */
	public static List<RthRtoDetailsTO> getConsignmentManifestDtls(
			ManifestDO manifestDO) throws CGBusinessException{
		LOGGER.trace("AbstractRthRtoManifestConverter::getConsignmentManifestDtls()::START");
		List<RthRtoDetailsTO> rthRtoDetailsTOs=null;
		// Integer consgCount=0;
		Set<ConsignmentDO> consigManifestDOs=manifestDO.getConsignments();
		if(!StringUtil.isEmptyColletion(consigManifestDOs)){
			rthRtoDetailsTOs=new ArrayList<>(manifestDO.getNoOfElements());
			for (ConsignmentDO consignmentManifestDO : consigManifestDOs) {
				//consgCount++;
				RthRtoDetailsTO rthRtoDetailsTO=setConsignmentDetails(consignmentManifestDO);
				rthRtoDetailsTO.setConsgCount(consigManifestDOs.size());
				rthRtoDetailsTOs.add(rthRtoDetailsTO);
			}
			//Collections.sort(rthRtoDetailsTOs);			
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::getConsignmentManifestDtls()::END");
		return rthRtoDetailsTOs;
	}
	
	/**
	 * Sets the consignment details.
	 *
	 * @param consignmentDO the consignment do
	 * @return the rth rto details to
	 * @throws CGBusinessException the cG business exception
	 */
	public static RthRtoDetailsTO setConsignmentDetails(ConsignmentDO consignmentDO) throws CGBusinessException{
		LOGGER.trace("AbstractRthRtoManifestConverter::setConsignmentDetails()::START");
		RthRtoDetailsTO rthRtoDetailsTO=null;
		if(!StringUtil.isNull(consignmentDO)){
			ConsignmentTO consignmentTO=new ConsignmentTO();
			consignmentTO=(ConsignmentTO) CGObjectConverter.createToFromDomain(consignmentDO, consignmentTO);			
			if(!StringUtil.isNull(consignmentDO.getDestPincodeId())){
				PincodeTO pincodeTO=new PincodeTO();
				pincodeTO=(PincodeTO) CGObjectConverter.createToFromDomain(consignmentDO.getDestPincodeId(), pincodeTO);
				consignmentTO.setDestPincode(pincodeTO);
			}
			if (!StringUtil.isNull(consignmentDO.getCnContentId())) {
				CNContentTO cnContentTO = new CNContentTO();
				CGObjectConverter.createToFromDomain(consignmentDO.getCnContentId(),
						cnContentTO);
				cnContentTO.setOtherContent(consignmentDO.getOtherCNContent());
				consignmentTO.setCnContents(cnContentTO);
			}
			if (!StringUtil.isNull(consignmentDO.getCnPaperWorkId())) {
				CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
				CGObjectConverter.createToFromDomain(consignmentDO.getCnPaperWorkId(),
						cnPaperworkTO);
				cnPaperworkTO.setPaperWorkRefNum(consignmentDO.getPaperWorkRefNo());
				consignmentTO.setCnPaperWorks(cnPaperworkTO);
			}
						
			//TODO : Rate Integration, Setting Comsignment rates
			/*Set<ConsignmentRateDO> consgRateDtls = null;//consignmentDO.getRateDtls();
			if (!StringUtil.isEmptyColletion(consgRateDtls)) {
				Set<ConsignmentRateTO> consgRateDtlsTOs = new HashSet<ConsignmentRateTO>();
				for (ConsignmentRateDO consgRateDO : consgRateDtls) {
					ConsignmentRateTO consgRateTO = new ConsignmentRateTO();
					if (StringUtils.equalsIgnoreCase("FNSLB", consgRateDO
							.getRateComponentDO().getRateComponentCode())) {
						//consignmentTO.setConsgFinalRate(consgRateDO.getCalculatedValue());
					}
					consgRateTO.setConsignmentRateId(consgRateDO
							.getConsignmentRateId());
					consgRateTO
							.setCalculatedValue(consgRateDO.getCalculatedValue());
					RateComponentTO rateComponent = new RateComponentTO();
					CGObjectConverter.createToFromDomain(
							consgRateDO.getRateComponentDO(), rateComponent);
					consgRateTO.setRateComponent(rateComponent);
					consgRateDtlsTOs.add(consgRateTO);

				}
				//consignmentTO.setConsgRateDetails(consgRateDtlsTOs);
			}*/			
			rthRtoDetailsTO=convertConsignmentTotoRthRtoDetailsTO(consignmentTO);
			if(!StringUtil.isNull(consignmentDO.getCnReturnReason())){
				ReasonTO reasonTO=new ReasonTO();
				reasonTO.setReasonId(consignmentDO.getCnReturnReason().getReasonId());
				rthRtoDetailsTO.setReasonTO(reasonTO);
				rthRtoDetailsTO.setReasonName(consignmentDO.getCnReturnReason().getReasonName());
				rthRtoDetailsTO.setReasonCode(consignmentDO.getCnReturnReason().getReasonCode());
			}
			rthRtoDetailsTO.setRemarks(consignmentDO.getRemarks());
		}		
		if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
			rthRtoDetailsTO.setCodAmt(consignmentDO.getCodAmt());
		}
		if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
			rthRtoDetailsTO.setToPayAmt(consignmentDO.getTopayAmt());
		}
		if (!StringUtil.isEmptyDouble(consignmentDO.getDeclaredValue())) {
			rthRtoDetailsTO.setDeclaredValue(consignmentDO.getDeclaredValue());
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::setConsignmentDetails()::END");
		return rthRtoDetailsTO;
	}
	
	/**
	 * To calculate total manifest weight and set manifest weight
	 * 
	 * @param consignmentDOsSet
	 * @return manifestWeight
	 * @author hkansagr
	 */
	public static double setManifestWt(Set<ConsignmentDO> consignmentDOsSet) {
		LOGGER.trace("AbstractRthRtoManifestConverter::setManifestWt()::START");
		double manifestWeight = 0.0;
		for (ConsignmentDO consignmentDO : consignmentDOsSet) {
			if (!StringUtil.isEmptyDouble(consignmentDO.getFinalWeight())) {
				manifestWeight += consignmentDO.getFinalWeight();
			}
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::setManifestWt()::END");
		return manifestWeight;
	}

	/**
	 * To get consignment status based on manifest type if RTH - H, RTO - R
	 * 
	 * @param manifestType
	 * @return consgStatus
	 */
	public static String getConsgStatus(String manifestType) {
		LOGGER.trace("AbstractRthRtoManifestConverter::getConsgStatus()::START");
		String consgStatus = CommonConstants.EMPTY_STRING;
		if (manifestType.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTH)) {
			consgStatus = CommonConstants.CONSIGNMENT_STATUS_RTH;
		} else if (manifestType
				.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTO)) {
			consgStatus = RthRtoManifestConstatnts.CONSIGNMENT_STATUS_RETURNED;
		}
		LOGGER.trace("AbstractRthRtoManifestConverter::getConsgStatus()::END");
		return consgStatus;
	}
	
}
