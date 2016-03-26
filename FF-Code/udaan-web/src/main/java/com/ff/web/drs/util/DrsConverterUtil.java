/**
 * 
 */
package com.ff.web.drs.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.IdentityProofTypeDO;
import com.ff.domain.serviceOffering.RelationDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.CodLcDrsDetailsTO;
import com.ff.to.drs.CodLcDrsTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.ManualDrsTO;
import com.ff.to.drs.RtoCodDrsTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public final class DrsConverterUtil {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DrsConverterUtil.class);
	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 * @throws CGBusinessException
	 */
	public static void convertHeaderTO2DO(final AbstractDeliveryTO drsInputTo,
			final DeliveryDO deliveryDO) throws CGBusinessException {
		//############################### Header Preparation START##############
		/** DeliveryDO  preparation*/
		if(StringUtil.isEmptyLong(drsInputTo.getDeliveryId())){
			deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_OPEN);
			deliveryDO.setDeliveryId(null);
		}else{
			deliveryDO.setDrsNumber(drsInputTo.getDrsNumber());
			deliveryDO.setDeliveryId(drsInputTo.getDeliveryId());
		}
		
		/** preparing DRS-FOR and Employee/DA Code information*/
		if(!StringUtil.isStringEmpty(drsInputTo.getDrsFor()) && !StringUtil.isEmptyInteger(drsInputTo.getDrsPartyId())){
			deliveryDO.setDrsFor(drsInputTo.getDrsFor());
			switch(drsInputTo.getDrsFor()){
			case DrsConstants.DRS_FOR_BA :
				final LoadMovementVendorDO baDO= new LoadMovementVendorDO();
				baDO.setVendorId(drsInputTo.getDrsPartyId());
				deliveryDO.setBaDO(baDO);
				break;
			case DrsConstants.DRS_FOR_CO_COURIER :
				final LoadMovementVendorDO coCourierDO= new LoadMovementVendorDO();
				coCourierDO.setVendorId(drsInputTo.getDrsPartyId());
				deliveryDO.setCoCourierDO(coCourierDO);
				break;
			case DrsConstants.DRS_FOR_FIELD_STAFF :
				final EmployeeDO fieldStaffDO= new EmployeeDO();
				fieldStaffDO.setEmployeeId(drsInputTo.getDrsPartyId());
				deliveryDO.setFieldStaffDO(fieldStaffDO);
				break;

			case DrsConstants.DRS_FOR_FR :
				final LoadMovementVendorDO franchiseDO= new LoadMovementVendorDO();
				franchiseDO.setVendorId(drsInputTo.getDrsPartyId());
				deliveryDO.setFranchiseDO(franchiseDO);
				break;
				
			default :
				/** Invalid DRS-FOR*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{DrsCommonConstants.DRS_FOR_TYPE.toUpperCase()});

			}

		}else{
			/**  throw CGBusiness Exception If DRS-For/ DRS-party type is invalid*/
			LOGGER.error("DrsConverterUtil ::convertHeaderTO2DO ::Exception (DRS-For/ DRS-party type is invalid)");
			if(StringUtil.isEmptyInteger(drsInputTo.getDrsPartyId())){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{DrsCommonConstants.DRS_FOR_TYPE_ID});
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{DrsCommonConstants.DRS_FOR_TYPE.toUpperCase()});
			}
		}
		/** preparing DRS-Time & FS-Out Time*/
		deliveryDO.setDrsDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(drsInputTo.getDrsDateTimeStr()));
		deliveryDO.setFsOutTime(DateUtil.getCurrentDate());

		/** Set DrsScreen Code*/
		deliveryDO.setDrsScreenCode(drsInputTo.getDrsScreenCode());
		
		deliveryDO.setManifestDrsType(drsInputTo.getManifestDrsType());

		/** preparing Load-Number & YP-DRS*/
		deliveryDO.setLoadNumber(drsInputTo.getLoadNumber());
		if(!StringUtil.isStringEmpty(drsInputTo.getYpDrs())){
			deliveryDO.setYpDrs(drsInputTo.getYpDrs().trim().toUpperCase());
		}

		/** preparing Consignmnet type*/
		if(!StringUtil.isStringEmpty(drsInputTo.getConsignmentType())){
			deliveryDO.setConsignmentType(drsInputTo.getConsignmentType());
		}else{
			deliveryDO.setConsignmentType(DrsConstants.CONSG_TYPE_NA);
			
		}
		/** set DRS-Office Details*/
		if(!StringUtil.isEmptyInteger(drsInputTo.getLoginOfficeId())){
			OfficeDO createdOfficeDO= new OfficeDO();
			createdOfficeDO.setOfficeId(drsInputTo.getLoginOfficeId());
			deliveryDO.setCreatedOfficeDO(createdOfficeDO);
		}
		/** set User Details*/
		deliveryDO.setCreatedBy(drsInputTo.getCreatedByUserId());
		if(!StringUtil.isEmptyLong(drsInputTo.getDeliveryId())){
			deliveryDO.setUpdatedBy(drsInputTo.getLoggedInUserId());
		}
		if(!StringUtil.isStringEmpty(drsInputTo.getFsInTimeDateStr())){
			deliveryDO.setFsInTime(DateUtil.combineDateWithTimeHHMM(drsInputTo.getFsInTimeDateStr(), drsInputTo.getFsInTimeHHStr(),drsInputTo.getFsInTimeMMStr()));
		}
		
		
		if(!StringUtil.isStringEmpty(drsInputTo.getFsOutTimeDateStr()) && !StringUtil.isStringEmpty(drsInputTo.getFsOutTimeHHStr()) ){
			deliveryDO.setFsOutTime(DateUtil.combineDateWithTimeHHMM(drsInputTo.getFsOutTimeDateStr(),drsInputTo.getFsOutTimeHHStr(),drsInputTo.getFsOutTimeMMStr()));
		}
		deliveryDO.setDtToCentral(CommonConstants.NO);
		//############################### Header Preparation END##############
	}

	public static DeliveryTO convertHeaderDO2TO(final DeliveryDO drsInputDo,
			final AbstractDeliveryTO deliveryTO) throws CGBusinessException {
		DeliveryTO outTO=null;
		outTO=new DeliveryTO();
		 if(StringUtil.isStringEmpty(drsInputDo.getIsDrsDicarded())|| drsInputDo.getIsDrsDicarded().equalsIgnoreCase(UniversalDeliveryContants.DRS_DISCARDED_YES)){
			/** check whether DRS is discarded or not if it's so then modification not allowed */
			LOGGER.error("DrsConverterUtil ::convertHeaderDO2TO ::Business Exception (DRS is discarded)");
			DrsUtil.setBusinessException4Modification(outTO,UdaanWebErrorConstants.DRS_NO_ALREADY_DICARDED,new String[]{drsInputDo.getDrsNumber()});
			outTO.setIsDrsDicarded(drsInputDo.getIsDrsDicarded());
		}
		//############################### Header Preparation START##############
		/** DeliveryTO  preparation*/
		boolean drsTypeFlag=false;
		/** preparing DRS-FOR and Employee/DA Code information*/
		if(!StringUtil.isStringEmpty(drsInputDo.getDrsFor())){

			CGObjectConverter.createToFromDomain(drsInputDo, outTO);
			switch(drsInputDo.getDrsFor()){
			case DrsConstants.DRS_FOR_BA :
				if(!StringUtil.isNull(drsInputDo.getBaDO())){
					LoadMovementVendorTO baTO= new LoadMovementVendorTO();
					CGObjectConverter.createToFromDomain(drsInputDo.getBaDO(), baTO);
					outTO.setBaTO(baTO);
				}else{
					drsTypeFlag=true;
				}
				break;
			case DrsConstants.DRS_FOR_CO_COURIER :
				if(!StringUtil.isNull(drsInputDo.getCoCourierDO())){
					LoadMovementVendorTO coCourierTO= new LoadMovementVendorTO();
					CGObjectConverter.createToFromDomain(drsInputDo.getCoCourierDO(), coCourierTO);
					outTO.setCoCourierTO(coCourierTO);
				}else{
					drsTypeFlag=true;
				}
				break;
			case DrsConstants.DRS_FOR_FIELD_STAFF :
				if(!StringUtil.isNull(drsInputDo.getFieldStaffDO())){
					EmployeeTO fieldStaffTO= new EmployeeTO();
					CGObjectConverter.createToFromDomain(drsInputDo.getFieldStaffDO(), fieldStaffTO);
					outTO.setFieldStaffTO(fieldStaffTO);
				}else{
					drsTypeFlag=true;
				}

				break;

			case DrsConstants.DRS_FOR_FR :
				if(!StringUtil.isNull(drsInputDo.getFranchiseDO())){
					LoadMovementVendorTO franchiseTO= new LoadMovementVendorTO();
					CGObjectConverter.createToFromDomain(drsInputDo.getFranchiseDO(), franchiseTO);
					outTO.setFranchiseTO(franchiseTO);
				}else{
					drsTypeFlag=true;
				}
				break;
			default :
				/** Invalid DRS-FOR*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{DrsCommonConstants.DRS_FOR_TYPE.toUpperCase()});

			}
			if(drsTypeFlag){
				//Throw Business Exception 
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{drsInputDo.getDrsFor()});
			}
		}else{
			/**  throw CGBusiness Exception If DRS-For/ DRS-party type is invalid*/

			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST, new String[]{DrsCommonConstants.DRS_FOR_TYPE.toUpperCase()});
		}
		/** created By Office */
		if(!StringUtil.isNull(drsInputDo.getCreatedOfficeDO())){
			OfficeTO createdOfficeTO= new OfficeTO();
			CGObjectConverter.createToFromDomain(drsInputDo.getCreatedOfficeDO(), createdOfficeTO);
			outTO.setCreatedOfficeTO(createdOfficeTO);
		}
		
		//############################### Header Preparation END##############
		return outTO;
	}
	public static DeliveryDetailsTO convertDetailsDO2TO(final DeliveryDetailsDO drsInputDo) throws CGBusinessException {
		//############################### Grid Preparation START##############
		
		DeliveryDetailsTO outTO=new DeliveryDetailsTO();
		/** DeliveryDetailsTO  preparation*/
		CGObjectConverter.createToFromDomain(drsInputDo, outTO);
		
		/** Consignment  details*/
		if(!StringUtil.isNull(drsInputDo.getConsignmentDO())){
			convertConsgDO2TO(drsInputDo, outTO);
		}
		outTO.setLcAmount(drsInputDo.getLcAmount());
		outTO.setToPayAmount(drsInputDo.getToPayAmount());
		outTO.setCodAmount(drsInputDo.getCodAmount());
		
		if(!StringUtil.isNull(drsInputDo.getOriginCityDO())){
			CityTO originTO= new CityTO();
			CGObjectConverter.createToFromDomain(drsInputDo.getOriginCityDO(), originTO);
			outTO.setOriginCityTO(originTO);
		}
		if(!StringUtil.isNull(drsInputDo.getRelationDO())){
			RelationTO relationTo= new RelationTO();
			CGObjectConverter.createToFromDomain(drsInputDo.getRelationDO(), relationTo);
			outTO.setRelationTO(relationTo);
			Map<Integer,String> relationMap= new HashMap<>(1);
			relationMap.put(relationTo.getRelationId(), relationTo.getRelationCode()+FrameworkConstants.CHARACTER_HYPHEN+relationTo.getRelationDescription());
			outTO.setRelationMap(relationMap);
		}
		if(!StringUtil.isNull(drsInputDo.getReasonDO())){
			ReasonTO reasonTo= new ReasonTO();
			CGObjectConverter.createToFromDomain(drsInputDo.getReasonDO(), reasonTo);
			outTO.setReasonTO(reasonTo);
			Map<Integer,String> nonDlvReason= new HashMap<>(1);
			nonDlvReason.put(reasonTo.getReasonId(), reasonTo.getReasonCode()+FrameworkConstants.CHARACTER_HYPHEN+reasonTo.getReasonName());
			outTO.setNonDlvReason(nonDlvReason);
		}else{
			/** since Reason id is null and  delivery type is null/empty then sets the delivery type as Office _dlv (default)*/
			if(StringUtil.isStringEmpty(drsInputDo.getDeliveryType())){
				outTO.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_OFFICE);
			}
		}
		if(!StringUtil.isNull(drsInputDo.getIdProofDO())){
			IdentityProofTypeTO idProofTo= new IdentityProofTypeTO();
			CGObjectConverter.createToFromDomain(drsInputDo.getIdProofDO(), idProofTo);
			outTO.setIdProofTO(idProofTo);
			
			Map<Integer,String> idProofMap= new HashMap<>(1);
			idProofMap.put(idProofTo.getIdentityProofTypeId(), idProofTo.getIdentityProofTypeCode()+FrameworkConstants.CHARACTER_HYPHEN+idProofTo.getIdentityProofTypeName());
			outTO.setIdProofMap(idProofMap);
		}
		


		//############################### Grid Preparation END##############
		return outTO;
	}

	/**
	 * @param drsInputDo
	 * @param outTO
	 * @throws CGBusinessException
	 */
	private static void convertConsgDO2TO(final DeliveryDetailsDO drsInputDo,
			DeliveryDetailsTO outTO) throws CGBusinessException {
		
		ConsignmentDO  consgDO=drsInputDo.getConsignmentDO();
		if(!StringUtil.isNull(consgDO)){
			ConsignmentTO consgTO= new ConsignmentTO();
			CGObjectConverter.createToFromDomain(consgDO, consgTO);
			if (!StringUtil.isNull(consgDO.getConsgType())) {
				ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
				CGObjectConverter.createToFromDomain(consgDO.getConsgType(),
						typeTO);
				consgTO.setTypeTO(typeTO);
			}
			outTO.setConsignmentTO(consgTO);
			/*outTO.setLcAmount(consgDO.getLcAmount());
			outTO.setToPayAmount(consgDO.getTopayAmt());
			outTO.setCodAmount(consgDO.getCodAmt());*/
			
			outTO.setLcAmount(drsInputDo.getLcAmount());
			outTO.setToPayAmount(drsInputDo.getToPayAmount());
			outTO.setCodAmount(drsInputDo.getCodAmount());
			outTO.setBaAmount(drsInputDo.getBaAmount());

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
			if (!StringUtil.isNull(consgDO.getConsignee())) {
				ConsignorConsigneeTO consignee=new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consgDO.getConsignee(),
						consignee);
				consgTO.setConsigneeTO(consignee);
			}
			if (!StringUtil.isNull(consgDO.getConsignor())) {
				ConsignorConsigneeTO consignor=new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consgDO.getConsignor(),
						consignor);
				consgTO.setConsignorTO(consignor);
			}
			if(!StringUtil.isStringEmpty(outTO.getParentChildCnType()) && outTO.getParentChildCnType().equalsIgnoreCase(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE)){
				//child CN
				String consignMent=outTO.getConsignmentNumber();
				if(!CGCollectionUtils.isEmpty(consgDO.getChildCNs())){
					Set<ChildConsignmentTO> childToSet=new HashSet<>(consgDO.getChildCNs().size());
					for(ChildConsignmentDO childDO:consgDO.getChildCNs()){
						
						ChildConsignmentTO childTO=new ChildConsignmentTO();
						CGObjectConverter.createToFromDomain(childDO,
								childTO);
						if(consignMent.equalsIgnoreCase(childDO.getChildConsgNumber())){
							/**  artf3113656 : Delivery runsheet! start*/
							consgTO.setNoOfPcs(1);
							consgTO.setActualWeight(childDO.getChildConsgWeight());
							consgTO.setFinalWeight(childDO.getChildConsgWeight());
							/**  artf3113656 : Delivery runsheet! END*/
						}
						childToSet.add(childTO);
					}
					consgTO.setChildTOSet(childToSet);
				}
			}
			outTO.setConsignmentTO(consgTO);
		}
	}
	
	/**
	 * Convert delivery t o2 base to.
	 *
	 * @param drsInputTo the drs input to
	 * @param outTo the out to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void convertDeliveryTO2BaseTO(AbstractDeliveryTO drsInputTo,
			DeliveryTO outTo) throws CGBusinessException, CGSystemException {
		try {
			PropertyUtils.copyProperties(drsInputTo, outTo);
		} catch (Exception obj) {
			throw new CGBusinessException(obj.getMessage());

		}
		Integer drsPartyId=null;
		switch(outTo.getDrsFor()){
		case DrsConstants.DRS_FOR_BA :
			drsPartyId= outTo.getBaTO().getVendorId();
			break;
		case DrsConstants.DRS_FOR_CO_COURIER :
			drsPartyId= outTo.getCoCourierTO().getVendorId();
			break;
		case DrsConstants.DRS_FOR_FIELD_STAFF :
			drsPartyId= outTo.getFieldStaffTO().getEmployeeId();
			break;
		case DrsConstants.DRS_FOR_FR :
			drsPartyId= outTo.getFranchiseTO().getVendorId();
			break;
		default :
			/** Invalid DRS-FOR*/
			LOGGER.warn("DrsConverterUtil::convertDeliveryTO2BaseTO:: invalid DRS-FOR ");
		}
		drsInputTo.setDrsPartyId(drsPartyId);
		drsInputTo.setDrsDateTimeStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(outTo.getDrsDate()));
		
		drsInputTo.setFsInTimeDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getFsInTime()));
		//drsInputTo.setFsInTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(outTo.getFsInTime()));
		
		drsInputTo.setFsInTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(outTo.getFsInTime()));
		drsInputTo.setFsInTimeHHStr(DateUtil.extractHourInHHFormatFromDate(outTo.getFsInTime()));
		
		drsInputTo.setFsOutTimeDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getFsOutTime()));
		//drsInputTo.setFsOutTimeMinStr(DateUtil.extractT(outTo.getFsOutTime()));
		drsInputTo.setFsOutTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(outTo.getFsOutTime()));
		drsInputTo.setFsOutTimeHHStr(DateUtil.extractHourInHHFormatFromDate(outTo.getFsOutTime()));

		drsInputTo.setCreatedByUserId(outTo.getCreatedBy());
		
		drsInputTo.setCreatedDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getDrsDate()));
		drsInputTo.setCreatedTimeStr(DateUtil.extractTimeFromDate(outTo.getDrsDate()));
		
		drsInputTo.setUpdatedDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getFsInTime()));
		drsInputTo.setUpdatedTimeStr(DateUtil.extractTimeFromDate(outTo.getFsInTime()));
		
		if(!StringUtil.isNull(outTo.getCreatedOfficeTO())){
			OfficeTO createdOffice=outTo.getCreatedOfficeTO();
			drsInputTo.setDrsOfficeName(createdOffice.getOfficeCode()+CommonConstants.HYPHEN+createdOffice.getOfficeName());
		}
		if(!StringUtil.isNull(outTo.getFsInTime())){
			drsInputTo.setFsInTimeDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getFsInTime()));
			//drsInputTo.setFsInTimeMinStr(DateUtil.getTimeFromDate(outTo.getFsInTime()));
			
			drsInputTo.setFsInTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(outTo.getFsInTime()));
			drsInputTo.setFsInTimeHHStr(DateUtil.extractHourInHHFormatFromDate(outTo.getFsInTime()));
		}
		if(!StringUtil.isNull(outTo.getFsOutTime())){
			drsInputTo.setFsOutTimeDateStr(DateUtil.getDDMMYYYYDateToString(outTo.getFsOutTime()));
			//drsInputTo.setFsOutTimeMinStr(DateUtil.getTimeFromDate(outTo.getFsOutTime()));
			
			drsInputTo.setFsOutTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(outTo.getFsOutTime()));
			drsInputTo.setFsOutTimeHHStr(DateUtil.extractHourInHHFormatFromDate(outTo.getFsOutTime()));
		}
	}
	/**
	 * 
	 * @param consgDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static DeliveryConsignmentTO convertConsgDO2DlvConsgTO(ConsignmentDO consgDO)
			throws CGBusinessException, CGSystemException {
		DeliveryConsignmentTO outTo=null;
		if (consgDO != null) {
			outTo = new DeliveryConsignmentTO();

			try {
				PropertyUtils.copyProperties(outTo, consgDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("DrsConverterUtil::convertConsgDO2DlvConsgTO:: Exception ",e);
				throw new CGBusinessException(e);
			}
			if (!StringUtil.isNull(consgDO.getConsgType())) {
				outTo.setConsgTypeId(consgDO.getConsgType()
						.getConsignmentId());
				outTo.setConsignmentTypeCode(consgDO.getConsgType()
						.getConsignmentCode());
				outTo.setConsignmentTypeName(consgDO.getConsgType()
						.getConsignmentName());
			}
			if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
				CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
				CGObjectConverter.createToFromDomain(
						consgDO.getCnPaperWorkId(), cnPaperworkTO);
				cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
				outTo.setCnPaperWorks(cnPaperworkTO);
			}
			if (!StringUtil.isNull(consgDO.getCnContentId())) {
				CNContentTO cnContentTO = new CNContentTO();
				CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
						cnContentTO);
				cnContentTO.setOtherContent(consgDO.getOtherCNContent());
				cnContentTO.setCnContentName(getConsignmentContent(consgDO));
				outTo.setCnContents(cnContentTO);
			}
			if (!StringUtil.isNull(consgDO.getInsuredBy())) {
				InsuredByTO insuredBy = new InsuredByTO();
				CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
						insuredBy);
				outTo.setInsuredByTO(insuredBy);
			}
			if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
				VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
				volWeightDtls.setVolWeight(consgDO.getVolWeight());
				volWeightDtls.setHeight(consgDO.getHeight());
				volWeightDtls.setLength(consgDO.getLength());
				volWeightDtls.setBreadth(consgDO.getBreath());
				outTo.setVolWightDtls(volWeightDtls);
			}
			
			if (!StringUtil.isNull(consgDO.getConsignee())) {
				ConsigneeConsignorDO consignee=consgDO.getConsignee();
				ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignee,
						consigneeTO);
				outTo.setConsigneeTO(consigneeTO);
			}
			if (!StringUtil.isNull(consgDO.getConsignor())) {
				ConsigneeConsignorDO consignor=consgDO.getConsignor();
				ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignor, consignorTO);
				outTo.setConsignorTO(consignorTO);
			}

		}
		return outTo;
	}
	
	public static Map<Integer, String> prepareCustomerMap(List<CustomerTO> result) {
		Map<Integer, String> baMapDtls;
		baMapDtls= new HashMap<>(result.size());
		for(CustomerTO baTo:result){
			prepareEachCustomerMap(baMapDtls, baTo);
		}
		if(!CGCollectionUtils.isEmpty(baMapDtls)){
			baMapDtls= CGCollectionUtils.sortByValue(baMapDtls);
		}
		return baMapDtls;
	}

	/**
	 * @param baMapDtls
	 * @param baTo
	 */
	public static void prepareEachCustomerMap(Map<Integer, String> baMapDtls,
			CustomerTO baTo) {
		String  name=baTo.getCustomerCode()+CommonConstants.HYPHEN+(!StringUtil.isStringEmpty(baTo.getBusinessName())?baTo.getBusinessName():"" );
		name=name.replaceAll(",", "");
		baMapDtls.put(baTo.getCustomerId(), name);
	}
	
	public static Map<Integer, String> prepareVendorMap(List<LoadMovementVendorTO> vendorTOList) {
		Map<Integer, String> vendorList=null;
		if(!CGCollectionUtils.isEmpty(vendorTOList)){
			vendorList= new HashMap<>(vendorTOList.size());
			for(LoadMovementVendorTO vendor :vendorTOList){
				prepareEachVendorMap(vendorList, vendor);
			}
		}
		if(!CGCollectionUtils.isEmpty(vendorList)){
			vendorList= CGCollectionUtils.sortByValue(vendorList);
		}
		
		return vendorList;
	}

	/**
	 * @param vendorList
	 * @param vendor
	 */
	public static void prepareEachVendorMap(Map<Integer, String> vendorList,
			LoadMovementVendorTO vendor) {
		String name=vendor.getVendorCode()+FrameworkConstants.CHARACTER_HYPHEN+vendor.getBusinessName();
			name=name.replaceAll(",", "");
		vendorList.put(vendor.getVendorId(),name );
	}
	
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 * @throws CGBusinessException 
	 */
	public static void setConsgParentChildType(AbstractDeliveryTO drsInputTo,
			int counter, DeliveryDetailsDO detailDO) throws CGBusinessException {
		if(!StringUtil.isEmpty(drsInputTo.getRowParentChildCnType())&& !StringUtil.isStringEmpty(drsInputTo.getRowParentChildCnType()[counter])){
			detailDO.setParentChildCnType(drsInputTo.getRowParentChildCnType()[counter]);
		}else{
			LOGGER.error("DrsConverterUtil :: setConsgParentChildType :: Parent/child CN type does not exist :hence throwing exception ");
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
		}
	}
	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 * @throws CGBusinessException
	 */
	public static void generateDrsNumber(AbstractDeliveryTO drsInputTo, DeliveryDO deliveryDO,DeliveryCommonService deliveryCommonService)
			throws CGBusinessException {
		if(StringUtil.isStringEmpty(drsInputTo.getDrsNumber())){
			String drsNumber = null;
			try {
				drsNumber= DrsUtil.getDrsNumber(drsInputTo, deliveryCommonService);
			} catch (Exception e) {
				LOGGER.error("DrsConverterUtil :: savePrepareDrs ::Problem in number generation ",e);
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PROBLEM_NUMBER_GENERATION);
			}
			deliveryDO.setDrsNumber(drsNumber);
		}
	}
	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 * @throws CGBusinessException
	 */
	public static String generateManualDrsNumber(AbstractDeliveryTO drsInputTo,DeliveryCommonService deliveryCommonService)
			throws CGBusinessException {
		String drsNumber=null;
		try {
			drsNumber= DrsUtil.getManualDrsNumber(drsInputTo, deliveryCommonService);
		} catch (Exception e) {
			LOGGER.error("DrsConverterUtil :: generateManualDrsNumber ::Problem in number generation ",e);
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PROBLEM_NUMBER_GENERATION);
		}
		return drsNumber;
	}
	/**
	 * @param drsInputTo
	 * @param gridSize
	 * @return
	 * @throws CGBusinessException
	 */
	public static int getGridSize(AbstractDeliveryTO drsInputTo)
			throws CGBusinessException {
		int gridSize=0;
		if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
			 gridSize=drsInputTo.getRowConsignmentNumber().length;
		}
		if(gridSize<1){
			LOGGER.error("DrsConverterUtil :: getGridSize :: not even one consignment details exist at Grid :hence throwing exception ");
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
		}
		return gridSize;
	}
	public static int getGridRowIdSize(AbstractDeliveryTO drsInputTo)
			throws CGBusinessException {
		int gridSize=0;
		if(!StringUtil.isEmpty(drsInputTo.getRowId())){
			 gridSize=drsInputTo.getRowId().length;
		}
		if(gridSize<1){
			LOGGER.error("DrsConverterUtil :: getGridRowIdSize :: not even one consignment details exist at Grid :hence throwing exception ");
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
		}
		return gridSize;
	}
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 * @throws CGBusinessException 
	 */
	public static void setOriginCityDetails(AbstractDeliveryTO drsInputTo, int counter,
			DeliveryDetailsDO detailDO) throws CGBusinessException {
		/** Inspect & proceed Origin City */
		if(!StringUtil.isEmptyInteger(drsInputTo.getRowOriginCityId()[counter])){
			CityDO originCityDO= new CityDO();
			originCityDO.setCityId(drsInputTo.getRowOriginCityId()[counter]);
			detailDO.setOriginCityDO(originCityDO);
		}else{
			//throw Exception
			LOGGER.error("DrsConverterUtil :: setOriginCityDetails :: City details does not exist :hence throwing exception ");
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR,new String[]{DrsCommonConstants.CITY,DrsCommonConstants.AT_LINE_NO+(counter+1)});
		}
	}
	
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 */
	public static void setCodLcPayCharges(CodLcDrsTO drsInputTo, int counter,
			DeliveryDetailsDO detailDO) {
		/**Setting the COD amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowCodAmount()[counter])){
			detailDO.setCodAmount(drsInputTo.getRowCodAmount()[counter]);
		}
		/**Setting the LC amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowLCAmount()[counter])){
			detailDO.setLcAmount(drsInputTo.getRowLCAmount()[counter]);
		}
		/**Setting the TO PAY amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowToPayAmount()[counter])){
			detailDO.setToPayAmount(drsInputTo.getRowToPayAmount()[counter]);
		}
		/**Setting the Other amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowOtherCharges()[counter])){
			detailDO.setOtherAmount(drsInputTo.getRowOtherCharges()[counter]);
		}
		if(!StringUtil.isEmpty(drsInputTo.getRowBaAmount())&&!StringUtil.isEmptyDouble(drsInputTo.getRowBaAmount()[counter])){
			detailDO.setBaAmount(drsInputTo.getRowBaAmount()[counter]);
		}
		StringBuffer rateDtls = getDrsAmount(drsInputTo, detailDO);
		LOGGER.info("DrsConverterUtil :: setCodLcPayCharges :: details "+rateDtls);
	}
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 */
	public static void setCodLcPayCharges(RtoCodDrsTO drsInputTo, int counter,
			DeliveryDetailsDO detailDO) {
		/**Setting the COD amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowCodAmount()[counter])){
			detailDO.setCodAmount(drsInputTo.getRowCodAmount()[counter]);
		}
		/**Setting the LC amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowLCAmount()[counter])){
			detailDO.setLcAmount(drsInputTo.getRowLCAmount()[counter]);
		}
		/**Setting the TO PAY amount in DeliveryDetailDO */
		if(!StringUtil.isEmptyDouble(drsInputTo.getRowToPayAmount()[counter])){
			detailDO.setToPayAmount(drsInputTo.getRowToPayAmount()[counter]);
		}
	}

	private static StringBuffer getDrsAmount(CodLcDrsTO drsInputTo,
			DeliveryDetailsDO detailDO) {
		StringBuffer rateDtls= new StringBuffer();
		rateDtls.append("CN Details :[");
		rateDtls.append(" CN Number :");
		rateDtls.append(detailDO.getConsignmentNumber());
		rateDtls.append("\t COD Amount :");
		rateDtls.append(detailDO.getCodAmount());
		rateDtls.append("\t LC Amount :");
		rateDtls.append(detailDO.getLcAmount());
		rateDtls.append("\t TOPay Amount :");
		rateDtls.append(detailDO.getToPayAmount());
		rateDtls.append("\t Other  Amount :");
		rateDtls.append(detailDO.getOtherAmount());
		rateDtls.append("\t BA  Amount :");
		rateDtls.append(detailDO.getBaAmount());
		rateDtls.append(" \t ]");
		return rateDtls;
	}
	
	private static StringBuffer getDrsModeOfPayment(CodLcDrsTO drsInputTo,
			DeliveryDetailsDO detailDO) {
		StringBuffer rateDtls= new StringBuffer();
		rateDtls.append("CN Details :[");
		rateDtls.append(" CN Number :");
		rateDtls.append(detailDO.getConsignmentNumber());
		rateDtls.append("\t MODE OF payment :");
		rateDtls.append(detailDO.getModeOfPayment());
		rateDtls.append("\t BankNameBranch");
		rateDtls.append(detailDO.getBankNameBranch());
		rateDtls.append(" \t ]");
		return rateDtls;
	}
	
	/** populate City Details in AbstractDlvDtlsTO
	 * @param dtlsTo
	 * @param detailsTo
	 */
	public static void prepareTOWithCityDtls(DeliveryDetailsTO dtlsTo,
			AbstractDeliveryDetailTO detailsTo) {
		if(!StringUtil.isNull(dtlsTo.getOriginCityTO())){
			detailsTo.setOriginCityId(dtlsTo.getOriginCityTO().getCityId());
			detailsTo.setOriginCityCode(dtlsTo.getOriginCityTO().getCityCode());
			//detailsTo.setOriginCityName(dtlsTo.getOriginCityTO().getCityCode()+CommonConstants.HYPHEN+dtlsTo.getOriginCityTO().getCityName());
			detailsTo.setOriginCityName(dtlsTo.getOriginCityTO().getCityName());
		}
	}
	
	public static void prepareDeliveryDtlsWithPaymentDts(CodLcDrsTO drsInputTo,
			int counter, DeliveryDetailsDO detailDO) throws CGBusinessException {
		if(!StringUtil.isEmpty(drsInputTo.getRowChequeDate()) && !StringUtil.isStringEmpty(drsInputTo.getRowChequeDate()[counter])){
			detailDO.setChequeDDDate(DateUtil.stringToDDMMYYYYFormat(drsInputTo.getRowChequeDate()[counter]));
			}
		
		if(!StringUtil.isEmpty(drsInputTo.getRowChequeNo()) && !StringUtil.isStringEmpty(drsInputTo.getRowChequeNo()[counter])){
			detailDO.setChequeDDNumber(drsInputTo.getRowChequeNo()[counter]);
			}
		
		if(!StringUtil.isEmpty(drsInputTo.getRowBankNameAndBranch()) && !StringUtil.isStringEmpty(drsInputTo.getRowBankNameAndBranch()[counter])){
			detailDO.setBankNameBranch(drsInputTo.getRowBankNameAndBranch()[counter]);
			}
		
		if(!StringUtil.isEmpty(drsInputTo.getRowModeOfPayment()) && !StringUtil.isStringEmpty(drsInputTo.getRowModeOfPayment()[counter])){
			detailDO.setModeOfPayment(drsInputTo.getRowModeOfPayment()[counter]);
		}else {
			
			if(!StringUtil.isStringEmpty(detailDO.getParentChildCnType())&& detailDO.getParentChildCnType().equalsIgnoreCase(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE) && isModeOfPaymentRequired(detailDO.getConsignmentNumber())){
				LOGGER.error("DrsConverterUtil :: prepareDeliveryDtlsWithPaymentDts :: Mode of Payment does not exist :hence throwing exception ");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR,new String[]{"Mode of payment",detailDO.getConsignmentNumber()+""+DrsCommonConstants.AT_LINE_NO+(counter+1)});
		
			}
		}
		StringBuffer rateDtls = getDrsModeOfPayment(drsInputTo, detailDO);
		LOGGER.info("DrsConverterUtil :: prepareDeliveryDtlsWithPaymentDts :: details "+rateDtls);
	}
	
	private static boolean isModeOfPaymentRequired(String consignment){
		boolean isPaymentRequired=false;
		try {
			String product=StockSeriesGenerator.getProductDtls(consignment);
			if(!StringUtil.isStringEmpty(product) && (product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_CASH_COD)|| product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT)|| product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD))){
				isPaymentRequired=true;
			}
		} catch (Exception e) {
			LOGGER.error("DrsConverterUtil :: isModeOfPaymentRequired :: details "+consignment,e);
		}
		
		
		return isPaymentRequired;
	}

	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 * @throws CGBusinessException
	 */
	public static void setConsgDO2DeliveryDO(AbstractDeliveryTO drsInputTo, int counter,
			DeliveryDetailsDO detailDO) throws CGBusinessException {
		String consgNumber=null;
		if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
			detailDO.setConsignmentNumber(drsInputTo.getRowConsignmentNumber()[counter]);
			consgNumber=detailDO.getConsignmentNumber();
		}
		if(!StringUtil.isEmptyInteger(drsInputTo.getRowConsignmentId()[counter])){
			ConsignmentDO cnDo= new ConsignmentDO();
			cnDo.setConsgId(drsInputTo.getRowConsignmentId()[counter]);
			detailDO.setConsignmentDO(cnDo);
		}else{
			if(StringUtil.isStringEmpty(consgNumber) || DrsUtil.isComailNumber(consgNumber)){
				//throw Exception
				LOGGER.error("DrsConverterUtil :: setConsgDO2DeliveryDO :: consignment details does not exist :hence throwing exception ");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR,new String[]{DrsCommonConstants.CONSIGNMENT_ID,DrsCommonConstants.AT_LINE_NO+(counter+1)});
			}
		}
	}
	
	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 */
	public static void populatePostSavePrepareDrs(AbstractDeliveryTO drsInputTo,
			DeliveryDO deliveryDO) {
		drsInputTo.setDrsNumber(deliveryDO.getDrsNumber());
		drsInputTo.setFsOutTimeDateStr(DateUtil.getDDMMYYYYDateToString(deliveryDO.getFsOutTime()));
		//drsInputTo.setFsOutTimeMinStr(DateUtil.getTimeFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setFsOutTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setFsOutTimeHHStr(DateUtil.extractHourInHHFormatFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setDeliveryId(deliveryDO.getDeliveryId());
	}
	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 */
	public static void populatePostUpdateDrs(AbstractDeliveryTO drsInputTo,
			DeliveryDO deliveryDO) {
		drsInputTo.setFsInTimeDateStr(DateUtil.getDDMMYYYYDateToString(deliveryDO.getFsOutTime()));
		//drsInputTo.setFsInTimeMinStr(DateUtil.getTimeFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setFsInTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setFsInTimeHHStr(DateUtil.extractHourInHHFormatFromDate(deliveryDO.getFsOutTime()));
		drsInputTo.setDrsStatus(deliveryDO.getDrsStatus());
	}
	/**
	 * @param dtlsTo
	 * @param detailsTo
	 */
	public static void prepareGridToFromDeliveryTO4PaymentDtls(
			DeliveryDetailsTO dtlsTo, CodLcDrsDetailsTO detailsTo) {
		if(!StringUtil.isNull(dtlsTo.getModeOfPayment())){
			detailsTo.setModeOfPayment(dtlsTo.getModeOfPayment());
		}
		if(!StringUtil.isNull(dtlsTo.getChequeDDDate())){
			detailsTo.setChequeDate(DateUtil.getDDMMYYYYDateToString(dtlsTo.getChequeDDDate()));
		}
		if(!StringUtil.isNull(dtlsTo.getChequeDDNumber())){
			detailsTo.setChequeNo(dtlsTo.getChequeDDNumber());
		}
		if(!StringUtil.isNull(dtlsTo.getBankNameBranch())){
			detailsTo.setBankNameAndBranch(dtlsTo.getBankNameBranch());
		}
	}
	
	/**
	 * @param dtlsTo
	 * @param detailsTo
	 */
	public static void prepareGridToFromDeliveryTO4Charges(DeliveryDetailsTO dtlsTo,
			CodLcDrsDetailsTO detailsTo) {
		if(!StringUtil.isNull(dtlsTo.getCodAmount())){
			detailsTo.setCodAmount(dtlsTo.getCodAmount());
		}
		if(!StringUtil.isNull(dtlsTo.getLcAmount())){
			detailsTo.setLcAmount(dtlsTo.getLcAmount());
		}
		if(!StringUtil.isNull(dtlsTo.getToPayAmount())){
			detailsTo.setToPayAmount(dtlsTo.getToPayAmount());
		}
		if(!StringUtil.isNull(dtlsTo.getBaAmount())){
			detailsTo.setBaAmount(dtlsTo.getBaAmount());
		}
		if(!StringUtil.isNull(dtlsTo.getOtherAmount())){
			detailsTo.setOtherCharges(dtlsTo.getOtherAmount());
		}
	}
	
	public static void prepareGridToFromConsgDO4Charges(ConsignmentDO consgDo,
			CodLcDrsDetailsTO detailsTo) {
		
		if(!StringUtil.isEmptyDouble(consgDo.getBaAmt())){
			detailsTo.setBaAmount(consgDo.getBaAmt());
		}else{
			if(!StringUtil.isNull(consgDo.getCodAmt())){
				detailsTo.setCodAmount(consgDo.getCodAmt());
			}
			if(!StringUtil.isNull(consgDo.getLcAmount())){
				detailsTo.setLcAmount(consgDo.getLcAmount());
			}
			if(!StringUtil.isNull(consgDo.getTopayAmt())){
				detailsTo.setToPayAmount(consgDo.getTopayAmt());
			}
		}
	}
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 */
	public static void prepareDeliveryDtlsWithDlvTime(AbstractDeliveryTO drsInputTo,
			int counter, DeliveryDetailsDO detailDO) {
		if(!StringUtil.isStringEmpty(drsInputTo.getRowDeliveryTimeInHH()[counter])&&!StringUtil.isStringEmpty(drsInputTo.getRowDeliveryTimeInMM()[counter])){
			String dlvTimeInHH= drsInputTo.getRowDeliveryTimeInHH()[counter];
			String dlvTimeInMM= drsInputTo.getRowDeliveryTimeInMM()[counter];
			//detailDO.setDeliveryDate(DateUtil.combineDateWithTimeHHMM(drsInputTo.getFsInTimeDateStr(), time));
			String dlvTime = dlvTimeInHH+":"+dlvTimeInMM;
			String dlvTime1 = dlvTimeInHH.concat(":").concat(dlvTimeInMM);
			detailDO.setDeliveryDate(DrsUtil.calculateDeliveryDate(drsInputTo,dlvTime ));
		}
	}
	/**
	 * @param drsInputTo
	 * @param counter
	 * @param detailDO
	 */
	public static void prepareDeliveryDtlsWithContactDtls(AbstractDeliveryTO drsInputTo,
			int counter, DeliveryDetailsDO detailDO) {
		if(!StringUtil.isEmpty(drsInputTo.getRowReceiverName()) && !StringUtil.isStringEmpty(drsInputTo.getRowReceiverName()[counter])){
		detailDO.setReceiverName(drsInputTo.getRowReceiverName()[counter]);
		}
		if(!StringUtil.isEmpty(drsInputTo.getRowContactNumber()) && !StringUtil.isStringEmpty(drsInputTo.getRowContactNumber()[counter])){
		detailDO.setContactNumber(drsInputTo.getRowContactNumber()[counter]);
		}
	}
	public static void prepareDeliveryDtlsWithCompanySealSign(ManualDrsTO drsInputTo,
			int counter, DeliveryDetailsDO detailDO) {
		if(!StringUtil.isEmpty(drsInputTo.getRowCompanySealSign()) && !StringUtil.isStringEmpty(drsInputTo.getRowCompanySealSign()[counter])){
			detailDO.setCompanySealSign(drsInputTo.getRowCompanySealSign()[counter]);
		}
	}
	public static void prepareDeliveryDtlsWithConsgNumber(
			AbstractDeliveryTO drsInputTo, int counter,
			DeliveryDetailsDO detailDO) throws CGBusinessException {
		/** Inspect & proceed Consignment Number */
		if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
			detailDO.setConsignmentNumber(drsInputTo.getRowConsignmentNumber()[counter].toUpperCase());
		}else{
			//throw Exception
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR,new String[]{DrsCommonConstants.CONSIGNMENT +DrsCommonConstants.DETAILS,DrsCommonConstants.AT_LINE_NO+(counter+1)});
			
		}
	}
	
	public static void prepareTOWithRelationAndIdDtls(DeliveryDetailsTO dtlsTo,
			AbstractDeliveryDetailTO detailsTo) {
		if(!StringUtil.isNull(dtlsTo.getRelationTO())){
			detailsTo.setRelationId(dtlsTo.getRelationTO().getRelationId());
		}
		if(!StringUtil.isNull(dtlsTo.getIdProofTO())){
			detailsTo.setIdentityProofTypeId(dtlsTo.getIdProofTO().getIdentityProofTypeId());
		}
		if(!StringUtil.isNull(dtlsTo.getIdNumber())){
			detailsTo.setIdNumber(dtlsTo.getIdNumber());
		}
	}
	
	public static void prepareDeliveryDtlsWithRelationAndIdDtls(
			AbstractDeliveryTO drsInputTo, int counter, DeliveryDetailsDO detailDO) {
		if(!StringUtil.isEmpty(drsInputTo.getRowRelationId()) && !StringUtil.isEmptyInteger(drsInputTo.getRowRelationId()[counter])){
			RelationDO relationDO = new RelationDO();
			relationDO.setRelationId(drsInputTo.getRowRelationId()[counter]);
			detailDO.setRelationDO(relationDO);
		}
		if(!StringUtil.isEmpty(drsInputTo.getRowIdentityProofId()) && !StringUtil.isEmptyInteger(drsInputTo.getRowIdentityProofId()[counter])){
			IdentityProofTypeDO idProofTypeDo = new IdentityProofTypeDO();
			idProofTypeDo.setIdentityProofTypeId(drsInputTo.getRowIdentityProofId()[counter]);
			detailDO.setIdProofDO(idProofTypeDo);
		}
		if(!StringUtil.isEmpty(drsInputTo.getRowIdNumber()) && !StringUtil.isStringEmpty(drsInputTo.getRowIdNumber()[counter])){
			detailDO.setIdNumber(drsInputTo.getRowIdNumber()[counter]);
			}
	}
	public static void prepareTOWithContentPaperwrks(AbstractDeliveryDetailTO detailsTo,
			ConsignmentTO consTO) {
		if(!StringUtil.isNull(consTO.getCnContents())){
			detailsTo.setContentId(consTO.getCnContents().getCnContentId());
			detailsTo.setContentName(getConsignmentContent(consTO));
		}
		if(!StringUtil.isNull(consTO.getCnPaperWorks())){
			detailsTo.setPaperWorkId(consTO.getCnPaperWorks().getCnPaperWorkId());
			detailsTo.setPaperWorkName(consTO.getCnPaperWorks().getCnPaperWorkName());
		}
	}
	
	public static void setConsignmentContentDtls(ConsignmentTO consTO) {
		if(consTO.getCnContents()!=null){
			consTO.getCnContents().setCnContentName(getConsignmentContent(consTO));
		}
	}
	private static String getConsignmentContent(ConsignmentTO consTO){
		return consTO.getCnContents().getCnContentName()+(!StringUtil.isStringEmpty(consTO.getOtherCNContent())?FrameworkConstants.CHARACTER_HYPHEN+consTO.getOtherCNContent():FrameworkConstants.EMPTY_STRING);
	}
	private static String getConsignmentContent(ConsignmentDO consTO){
		return consTO.getCnContentId().getCnContentName()+(!StringUtil.isStringEmpty(consTO.getOtherCNContent())?FrameworkConstants.CHARACTER_HYPHEN+consTO.getOtherCNContent():FrameworkConstants.EMPTY_STRING);
	}
	
	public static void setAttemptNumberDetailsToDeliveryDomain(
			AbstractDeliveryTO drsInputTo, int counter, DeliveryDetailsDO detailDO) {
		if(!StringUtil.isEmpty(drsInputTo.getRowAttemptNumber())&&!StringUtil.isEmptyInteger(drsInputTo.getRowAttemptNumber()[counter])){
			detailDO.setAttemptNumber(drsInputTo.getRowAttemptNumber()[counter]);
		}
	}
}
