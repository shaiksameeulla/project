package com.ff.web.manifest.rthrto.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.domain.manifest.ConsignmentReturnReasonDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.rthrto.RthRtoValidationDetailsTO;
import com.ff.manifest.rthrto.RthRtoValidationTO;
import com.ff.manifest.rthrto.RtoConsignmentReportTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.rthrto.utils.RthRtoManifestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoValidationConverter.
 * 
 * @author narmdr
 */
public class RthRtoValidationConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoValidationConverter.class);
	/** The rth rto manifest common service. */
	private static RthRtoManifestCommonService rthRtoManifestCommonService;
	
	/**
	 * Sets the rth rto manifest common service.
	 *
	 * @param rthRtoManifestCommonService the rthRtoManifestCommonService to set
	 */
	public static void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		RthRtoValidationConverter.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}

	/**
	 * Rth rto validation transfer converter.
	 *
	 * @param consignmentReturnDO the consignment return do
	 * @param rthRtoValidationTO the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 */
	public static void rthRtoValidationTransferConverter(
			final ConsignmentReturnDO consignmentReturnDO,
			final RthRtoValidationTO rthRtoValidationTO)
			throws CGBusinessException {
		LOGGER.trace("RthRtoValidationConverter::rthRtoValidationTransferConverter()::START");
		final OfficeTO officeTO = new OfficeTO();
		final ProcessTO processTO = new ProcessTO();
		rthRtoValidationTO.setOfficeTO(officeTO);
		rthRtoValidationTO.setProcessTO(processTO);
		
		CGObjectConverter.createToFromDomain(consignmentReturnDO, rthRtoValidationTO);
		if(consignmentReturnDO.getOfficeDO()!=null){
			CGObjectConverter.createToFromDomain(consignmentReturnDO.getOfficeDO(), officeTO);
		}
		if(consignmentReturnDO.getProcessDO()!=null){
			CGObjectConverter.createToFromDomain(consignmentReturnDO.getProcessDO(), processTO);
		}

		if (consignmentReturnDO.getDrsDateTime() != null) {
			rthRtoValidationTO.setDrsDateTimeStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(consignmentReturnDO
							.getDrsDateTime()));
		}
		/*if (consignmentReturnDO.getConsignmentDO().getDeliveryDateTime() != null) {
			rthRtoValidationTO.setDrsDateTimeStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(consignmentReturnDO
							.getConsignmentDO().getDeliveryDateTime()));
		}*/
		final Set<ConsignmentReturnReasonDO> consignmentReturnReasonDOs = consignmentReturnDO.getConsignmentReturnReasonDOs();
		
		if(!StringUtil.isEmptyColletion(consignmentReturnReasonDOs)){
			List<RthRtoValidationDetailsTO> rthRtoValidationDetailsTOs = new ArrayList<>();
			for (ConsignmentReturnReasonDO consignmentReturnReasonDO : consignmentReturnReasonDOs) {
				RthRtoValidationDetailsTO rthRtoValidationDetailsTO = rthRtoValidationDetailsTransferConverter(consignmentReturnReasonDO);
				rthRtoValidationDetailsTOs.add(rthRtoValidationDetailsTO);
			}
			Collections.sort(rthRtoValidationDetailsTOs);
			rthRtoValidationTO.setRthRtoValidationDetailsTOs(rthRtoValidationDetailsTOs);
		}
		
		//consignment converter
		if (!StringUtil.isNull(consignmentReturnDO.getConsignmentDO())) {
			ConsignmentTO consignmentTO = new ConsignmentTO();
			convertConsignmentDO2TO(consignmentReturnDO.getConsignmentDO(), consignmentTO);
			convertConsignmentTO2RthRtoValidationTO(consignmentTO, rthRtoValidationTO);
		}
		LOGGER.trace("RthRtoValidationConverter::rthRtoValidationTransferConverter()::END");
	}
	
	/**
	 * Rth rto validation details transfer converter.
	 *
	 * @param consignmentReturnReasonDO the consignment return reason do
	 * @return the rth rto validation details to
	 * @throws CGBusinessException the cG business exception
	 */
	private static RthRtoValidationDetailsTO rthRtoValidationDetailsTransferConverter(
			final ConsignmentReturnReasonDO consignmentReturnReasonDO) throws CGBusinessException {
		LOGGER.trace("RthRtoValidationConverter::rthRtoValidationDetailsTransferConverter()::START");
		final RthRtoValidationDetailsTO rthRtoValidationDetailsTO = new RthRtoValidationDetailsTO();
		
		final ReasonTO reasonTO = new ReasonTO();
		rthRtoValidationDetailsTO.setReasonTO(reasonTO);
		
		CGObjectConverter.createToFromDomain(consignmentReturnReasonDO, rthRtoValidationDetailsTO);
		if(consignmentReturnReasonDO.getReasonDO()!=null){
			CGObjectConverter.createToFromDomain(consignmentReturnReasonDO.getReasonDO(), reasonTO);
		}
		if (consignmentReturnReasonDO.getDate() != null) {
			rthRtoValidationDetailsTO.setDateStr(DateUtil
					.getDDMMYYYYDateToString(consignmentReturnReasonDO.getDate()));
		}
		LOGGER.trace("RthRtoValidationConverter::rthRtoValidationDetailsTransferConverter()::END");
		return rthRtoValidationDetailsTO;
	}

	/**
	 * Consignment return domain converter4 rth rto validation.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the consignment return do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static ConsignmentReturnDO consignmentReturnDomainConverter4RthRtoValidation(final RthRtoValidationTO rthRtoValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoValidationConverter::consignmentReturnDomainConverter4RthRtoValidation()::START");
		final ConsignmentReturnDO consignmentReturnDO = new ConsignmentReturnDO();
		
		if(!StringUtil.isEmptyInteger(rthRtoValidationTO.getConsignmentReturnId())){
			RthRtoManifestUtils.setUpdateFlag4DBSync(consignmentReturnDO);
		}else{
			RthRtoManifestUtils.setSaveFlag4DBSync(consignmentReturnDO);
			generateAndSetProcessNumber(rthRtoValidationTO);
		}
		
		CGObjectConverter.createDomainFromTo(rthRtoValidationTO, consignmentReturnDO);

		if(StringUtil.isEmptyInteger(rthRtoValidationTO.getConsignmentReturnId())){
			consignmentReturnDO.setConsignmentReturnId(null);
		}
		if (rthRtoValidationTO.getOfficeTO() != null
				&& !StringUtil.isEmptyInteger(rthRtoValidationTO.getOfficeTO()
						.getOfficeId())) {
			final OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(rthRtoValidationTO.getOfficeTO().getOfficeId());
			consignmentReturnDO.setOfficeDO(officeDO);
		}
		if(!StringUtil.isEmptyInteger(rthRtoValidationTO.getConsignmentId())){
			final ConsignmentDO consignmentDO = new ConsignmentDO();
			consignmentDO.setConsgId(rthRtoValidationTO.getConsignmentId());
			consignmentReturnDO.setConsignmentDO(consignmentDO);
		}
		if (StringUtils.isNotBlank(rthRtoValidationTO.getDrsDateTimeStr())) {
			consignmentReturnDO.setDrsDateTime(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(rthRtoValidationTO
							.getDrsDateTimeStr()));
		}
		if (rthRtoValidationTO.getProcessTO() != null){
			ProcessTO processTO = rthRtoValidationTO.getProcessTO();

			if(StringUtil.isEmptyInteger(processTO.getProcessId())){
				processTO = rthRtoManifestCommonService.getProcess(processTO);
			}
			if(processTO!=null){
				ProcessDO processDO = new ProcessDO();
				processDO.setProcessId(processTO.getProcessId());
				consignmentReturnDO.setProcessDO(processDO);
			}
		}		
		
		Set<ConsignmentReturnReasonDO> consignmentReturnReasonDOs = null;
		final int length = rthRtoValidationTO.getReasonId().length;
		if(length>0){
			consignmentReturnReasonDOs = new LinkedHashSet<>();
		}
		for(int i=0; i<length;i++){
			if(StringUtil.isEmptyInteger(rthRtoValidationTO.getReasonId()[i])){
				continue;
			}
			ConsignmentReturnReasonDO consignmentReturnReasonDO = new ConsignmentReturnReasonDO();

			if (!StringUtil.isEmptyInteger(rthRtoValidationTO.getConsignmentReturnReasonId()[i])) {
				consignmentReturnReasonDO
						.setConsignmentReturnReasonId(rthRtoValidationTO
								.getConsignmentReturnReasonId()[i]);
			}
			if(!StringUtil.isEmptyInteger(rthRtoValidationTO.getReasonId()[i])){
				final ReasonDO reasonDO = new ReasonDO();
				reasonDO.setReasonId(rthRtoValidationTO.getReasonId()[i]);
				consignmentReturnReasonDO.setReasonDO(reasonDO);
			}
			if (StringUtils.isNotBlank(rthRtoValidationTO.getDateStr()[i])) {
				final Date date = DateUtil.stringToDDMMYYYYFormat(rthRtoValidationTO.getDateStr()[i]);
				consignmentReturnReasonDO.setDate(date);
			}
			consignmentReturnReasonDO.setTime(rthRtoValidationTO.getTime()[i]);
			consignmentReturnReasonDO.setRemarks(rthRtoValidationTO.getRemarks()[i]);
			consignmentReturnReasonDOs.add(consignmentReturnReasonDO);
		}
		consignmentReturnDO.setConsignmentReturnReasonDOs(consignmentReturnReasonDOs);
		InManifestUtils.setCreatedByUpdatedBy(consignmentReturnDO);
		LOGGER.trace("RthRtoValidationConverter::consignmentReturnDomainConverter4RthRtoValidation()::END");
		return consignmentReturnDO;
	}
	
	/**
	 * Generate and set process number.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void generateAndSetProcessNumber(final RthRtoValidationTO rthRtoValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoValidationConverter::generateAndSetProcessNumber()::START");
		final ProcessTO processTO = rthRtoValidationTO.getProcessTO();
		final OfficeTO officeTO = new OfficeTO();
		if (rthRtoValidationTO.getLoggedInOfficeTO() != null
				&& !StringUtil.isEmptyInteger(rthRtoValidationTO.getLoggedInOfficeTO()
						.getOfficeId())) {
			officeTO.setOfficeId(rthRtoValidationTO.getLoggedInOfficeTO().getOfficeId());
		}
		if(StringUtils.isBlank(rthRtoValidationTO.getProcessNumber())){
			String processNumber = rthRtoManifestCommonService.createProcessNumber(processTO, officeTO);
			rthRtoValidationTO.setProcessNumber(processNumber);
		}
		LOGGER.trace("RthRtoValidationConverter::generateAndSetProcessNumber()::END");
	}

	/**
	 * Convert consignment t o2 rth rto validation to.
	 *
	 * @param consignmentTO the consignment to
	 * @param rthRtoValidationTO the rth rto validation to
	 */
	public static void convertConsignmentTO2RthRtoValidationTO(
			ConsignmentTO consignmentTO, RthRtoValidationTO rthRtoValidationTO) {
		LOGGER.trace("RthRtoValidationConverter::convertConsignmentTO2RthRtoValidationTO()::START");
		rthRtoValidationTO.setConsignmentId(consignmentTO.getConsgId());
		rthRtoValidationTO.setConsignmentTypeTO(consignmentTO.getTypeTO());
		rthRtoValidationTO.setActualWeight(consignmentTO.getFinalWeight());

		if(consignmentTO.getConsignorTO()!=null){
			if(StringUtils.isNotBlank(consignmentTO.getConsignorTO().getMobile())){
				rthRtoValidationTO.setContactNumber(consignmentTO.getConsignorTO().getMobile());				
			}
			else if(StringUtils.isNotBlank(consignmentTO.getConsignorTO().getPhone())){
				rthRtoValidationTO.setContactNumber(consignmentTO.getConsignorTO().getPhone());				
			}
		}
		/*if(consignmentTO.getDeliveryDateTime()!=null){
			rthRtoValidationTO.setDrsDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
					(consignmentTO.getDeliveryDateTime()));			
		}*/
		LOGGER.trace("RthRtoValidationConverter::convertConsignmentTO2RthRtoValidationTO()::END");
	}
	
	/**
	 * Convert consignment d o2 to.
	 *
	 * @param consgDO the consg do
	 * @param consgTO the consg to
	 * @throws CGBusinessException the cG business exception
	 */
	public static void convertConsignmentDO2TO(ConsignmentDO consgDO,
			ConsignmentTO consgTO) throws CGBusinessException {
		LOGGER.trace("RthRtoValidationConverter::convertConsignmentDO2TO()::START");
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		
		if (!StringUtil.isNull(consgDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsgType(),
					typeTO);
			consgTO.setTypeTO(typeTO);
		}

		if (!StringUtil.isNull(consgDO.getConsignor())) {
			ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
			CGObjectConverter.createToFromDomain(consgDO.getConsignor(),
					consignorTO);
			consgTO.setConsignorTO(consignorTO);
		}
		LOGGER.trace("RthRtoValidationConverter::convertConsignmentDO2TO()::END");
	}

	/**
	 * Convert delivery details t o2 rth rto validation to.
	 *
	 * @param deliveryDetailsTO the delivery details to
	 * @param rthRtoValidationTO the rth rto validation to
	 */
	public static void convertDeliveryDetailsTO2RthRtoValidationTO(
			DeliveryDetailsTO deliveryDetailsTO,
			RthRtoValidationTO rthRtoValidationTO) {
		LOGGER.trace("RthRtoValidationConverter::convertDeliveryDetailsTO2RthRtoValidationTO()::START");
		if (deliveryDetailsTO.getConsignmentTO() != null) {
			RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(
					deliveryDetailsTO.getConsignmentTO(), rthRtoValidationTO);
		}
		
		if (deliveryDetailsTO.getDeliveryTO() != null
				&& deliveryDetailsTO.getDeliveryTO().getDrsDate() != null) {
			rthRtoValidationTO.setDrsDateTimeStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(deliveryDetailsTO
							.getDeliveryTO().getDrsDate()));
		}
		LOGGER.trace("RthRtoValidationConverter::convertDeliveryDetailsTO2RthRtoValidationTO()::END");
	}

	/**
	 * Rto consignment report transfer converter.
	 *
	 * @param consignmentReturnDO the consignment return do
	 * @param rtoConsignmentReportTO the rto consignment report to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void rtoConsignmentReportTransferConverter(
			final ConsignmentReturnDO consignmentReturnDO,
			final RtoConsignmentReportTO rtoConsignmentReportTO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoValidationConverter::rtoConsignmentReportTransferConverter()::START");		
		if (consignmentReturnDO.getConsignmentDO() != null) {
			rtoConsignmentReportTO.setConsgNumber(consignmentReturnDO.getConsignmentDO().getConsgNo());			
		}

		//get and set Received date start
		String receivedDate = null;
		if (consignmentReturnDO.getConsignmentDO() != null
				&& !StringUtil.isEmptyInteger(consignmentReturnDO
						.getConsignmentDO().getConsgId())) {

			receivedDate = rthRtoManifestCommonService.getInManifestDateByConsgId(consignmentReturnDO
					.getConsignmentDO().getConsgId());
		}
		if(StringUtils.isBlank(receivedDate)){
			receivedDate = CommonConstants.EMPTY_STRING;
		}
		rtoConsignmentReportTO.setReceivedDate(receivedDate);
		//get and set Received date end
		
		if(!StringUtil.isEmptyColletion(consignmentReturnDO.getConsignmentReturnReasonDOs())){

			//TODO convert this one to StringBuilder
			String reasons = CommonConstants.EMPTY_STRING;
			String remarks = CommonConstants.EMPTY_STRING;
			
			for (ConsignmentReturnReasonDO consignmentReturnReasonDO : consignmentReturnDO.getConsignmentReturnReasonDOs()) {
				if(consignmentReturnReasonDO.getReasonDO()!=null){
					if(StringUtils.isNotBlank(consignmentReturnReasonDO.getReasonDO().getReasonName())){					
						if(StringUtils.isNotBlank(reasons)){
							reasons = reasons + CommonConstants.COMMA + " " + consignmentReturnReasonDO.getReasonDO().getReasonName();
						}else{
							reasons = consignmentReturnReasonDO.getReasonDO().getReasonName();
						}
					}
				}
				
				if(StringUtils.isNotBlank(consignmentReturnReasonDO.getRemarks())){					
					if(StringUtils.isNotBlank(remarks)){
						remarks = remarks + CommonConstants.COMMA + " " + consignmentReturnReasonDO.getRemarks();
					}else{
						remarks = consignmentReturnReasonDO.getRemarks();
					}
				}
			}
			
			rtoConsignmentReportTO.setRemarks(remarks);
			rtoConsignmentReportTO.setReasons(reasons);
		}
		LOGGER.trace("RthRtoValidationConverter::rtoConsignmentReportTransferConverter()::END");
	}	

}
