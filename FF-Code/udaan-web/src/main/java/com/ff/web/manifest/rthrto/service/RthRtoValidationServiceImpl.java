package com.ff.web.manifest.rthrto.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.domain.manifest.ConsignmentReturnReasonDO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.rthrto.RthRtoValidationTO;
import com.ff.manifest.rthrto.RtoConsignmentReportTO;
import com.ff.manifest.rthrto.RtoReportTO;
import com.ff.organization.OfficeTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.universe.util.UdaanContextService;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.converter.RthRtoValidationConverter;
import com.ff.web.manifest.rthrto.dao.RthRtoValidationDAO;

/**
 * The Class RthRtoValidationServiceImpl.
 * 
 * @author narmdr
 */
public class RthRtoValidationServiceImpl implements RthRtoValidationService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoValidationServiceImpl.class);
	
	/** The rth rto validation dao. */
	private transient RthRtoValidationDAO rthRtoValidationDAO;	
	
	/** The rth rto manifest common service. */
	private transient RthRtoManifestCommonService rthRtoManifestCommonService;
	
	/** The email sender service. */
	private transient EmailSenderUtil emailSenderUtil;
	
	/** The udaan context service. */
	private transient UdaanContextService udaanContextService;
	
	/**
	 * Sets the rth rto validation dao.
	 *
	 * @param rthRtoValidationDAO the rthRtoValidationDAO to set
	 */
	public void setRthRtoValidationDAO(RthRtoValidationDAO rthRtoValidationDAO) {
		this.rthRtoValidationDAO = rthRtoValidationDAO;
	}

	/**
	 * Sets the rth rto manifest common service.
	 *
	 * @param rthRtoManifestCommonService the rthRtoManifestCommonService to set
	 */
	public void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		this.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}


	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * Sets the udaan context service.
	 *
	 * @param udaanContextService the udaanContextService to set
	 */
	public void setUdaanContextService(UdaanContextService udaanContextService) {
		this.udaanContextService = udaanContextService;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoValidationService#saveOrUpdateRthRtoValidation(com.ff.manifest.rthrto.RthRtoValidationTO)
	 */
	@Override
	public void saveOrUpdateRthRtoValidation(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RthRtoValidationServiceImpl::saveOrUpdateRthRtoValidation::START------------>:::::::");
		ConsignmentReturnDO consignmentReturnDO = RthRtoValidationConverter.consignmentReturnDomainConverter4RthRtoValidation(rthRtoValidationTO);
		rthRtoValidationDAO.saveOrUpdateConsignmentReturn(consignmentReturnDO);
		
		//setting id for TwoWayWrite
		rthRtoValidationTO.setConsignmentReturnId(consignmentReturnDO.getConsignmentReturnId());
		
		LOGGER.trace("RthRtoValidationServiceImpl::saveOrUpdateRthRtoValidation::END------------>:::::::");
	}

	/**
	 * Find consignment number for rth.
	 * <p>
     * <ul>
     * <li>Search Consignment Number in Rth details.
     * <li>If not, Check Consignment Number exist in DRS Details table.
     * <li>If Consignment Number not exist in any or exist & Consignment had O-OUT FOR DELIVERY, D-DELIVERED then throw
     * 		CGBusinessException with errorMsg .
     * </ul>
     * <p>
     * 
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public RthRtoValidationTO findConsignmentNumber4Rth(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RthRtoValidationServiceImpl::findConsignmentNumber4Rth::START------------>:::::::");
		RthRtoValidationTO rthRtoValidationTO2 = new RthRtoValidationTO();
		ConsignmentReturnDO consignmentReturnDO = (ConsignmentReturnDO) rthRtoValidationDAO
				.getDataByNamedQueryAndNamedParam(
						RthRtoManifestConstatnts.QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE,
						new String[] {
								RthRtoManifestConstatnts.PARAM_CONSIGNMENT_NUMBER,
								RthRtoManifestConstatnts.PARAM_OFFICE_ID,
								RthRtoManifestConstatnts.PARAM_RETURN_TYPE },
						new Object[] {
								rthRtoValidationTO.getConsignmentNumber(),
								rthRtoValidationTO.getOfficeTO().getOfficeId(),
								rthRtoValidationTO.getReturnType() });
		if(consignmentReturnDO!=null){
			rthRtoValidationTO2.setIsRth(Boolean.TRUE);
			//call converter
			//rthRtoValidationTransferConverter
			//transferObjConverter4RthRtoValidationTO
			RthRtoValidationConverter.rthRtoValidationTransferConverter(consignmentReturnDO, rthRtoValidationTO2);
			
			//check whether consg is RTOH manifested.
			rthRtoValidationTO2.setIsRtohManifest(isConsignmentRtohManifested(rthRtoValidationTO));
		} else {

			//Allow InManifest and Third Party(TPDX,TPBP) consg except others manifest consg
			validateIsConsignmentManifested(rthRtoValidationTO);
			
			/*********Drs Search Start**************************/
			/*search consg in drs, only pending consg is allowed
			param : consgNo*/
			DeliveryDetailsTO deliveryDetailsTO = rthRtoManifestCommonService
					.getDrsDtlsByConsgNo(rthRtoValidationTO.getConsignmentNumber());

			if(deliveryDetailsTO!=null){
				if(StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "D")
						|| StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "S")){
					ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED, new String[] { rthRtoValidationTO.getConsignmentNumber() });
					//throw new CGBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED);
					
				}else if(StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "O")){
					ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY, new String[] { rthRtoValidationTO.getConsignmentNumber() });
					//throw new CGBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY);
					
				}
				validateIsConsignmentDelivered(deliveryDetailsTO.getConsignmentTO());
				rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);
				RthRtoValidationConverter.convertDeliveryDetailsTO2RthRtoValidationTO(deliveryDetailsTO, rthRtoValidationTO2);
			}else{
				ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER, new String[] { rthRtoValidationTO.getConsignmentNumber() });
				//throw new CGBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER);
			}
			/*********Drs Search End**************************/
			
			
			/*********Consg Search Start**************************/
			/*ConsignmentTO consignmentTO = rthRtoManifestCommonService
					.getConsingmentDtls(rthRtoValidationTO
							.getConsignmentNumber());
			if(consignmentTO==null){
				throw new CGBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER);
			}

			rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);
			RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(consignmentTO, rthRtoValidationTO2);
*/
			/*********Consg Search End**************************/
		}

		LOGGER.trace("RthRtoValidationServiceImpl::findConsignmentNumber4Rth::END------------>:::::::");
		return rthRtoValidationTO2;
	}

	/**
	 * Checks if is consignment rtoh manifested.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return true, if is consignment rtoh manifested
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private boolean isConsignmentRtohManifested(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException {

		InManifestTO inManifestTO = new InManifestTO();
		inManifestTO.setConsgNumber(rthRtoValidationTO.getConsignmentNumber());
		inManifestTO.setLoggedInOfficeId(rthRtoValidationTO.getOfficeTO()
				.getOfficeId());
		inManifestTO
				.setManifestType(rthRtoValidationTO.getReturnType()
						.equalsIgnoreCase(
								RthRtoManifestConstatnts.RETURN_TYPE_RTO) ? CommonConstants.MANIFEST_TYPE_RTO
						: CommonConstants.MANIFEST_TYPE_RTH);

		if (!StringUtil.isEmptyInteger(rthRtoManifestCommonService
				.getManifestIdFromCnManifest(inManifestTO))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * Validate is consignment manifested.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private void validateIsConsignmentManifested(
			RthRtoValidationTO rthRtoValidationTO) throws CGSystemException,
			CGBusinessException {
		//Allow only InManifest and Third Party(TPDX,TPBP) consg except others manifest consg
		
		InManifestTO inManifestTO = new InManifestTO();
		inManifestTO.setConsgNumber(rthRtoValidationTO.getConsignmentNumber());
		inManifestTO.setLoggedInOfficeId(rthRtoValidationTO.getOfficeTO().getOfficeId());

		InManifestTO inManifestTO1 = rthRtoManifestCommonService.getManifestDtlsByConsgNoOperatingOffice(inManifestTO);
		if (inManifestTO1 != null
				&& !(StringUtils.equals(inManifestTO1.getManifestType(),
						CommonConstants.MANIFEST_TYPE_IN)
						|| StringUtils
								.equals(inManifestTO1.getProcessCode(),
										CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX) || StringUtils
							.equals(inManifestTO1.getProcessCode(),
									CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL))) {
			ExceptionUtil.prepareBusinessException(
					RthRtoManifestConstatnts.CONSIGNMENT_IS_ALREADY_MANIFESTED,
					new String[] { rthRtoValidationTO.getConsignmentNumber() });
		} else if (inManifestTO1 == null){
			ExceptionUtil.prepareBusinessException(
					RthRtoManifestConstatnts.CONSIGNMENT_IS_NEITHER_IN_MANIFESTED_NOR_THIRD_PARTY_MANIFESTED,
					new String[] { rthRtoValidationTO.getConsignmentNumber() });			
		}

//		boolean isCNManifested = rthRtoValidationDAO.isConsgManifested(rthRtoValidationTO);
//		if (isCNManifested) {
//			ExceptionUtil.prepareBusinessException(
//					RthRtoManifestConstatnts.CONSIGNMENT_IS_ALREADY_MANIFESTED,
//					new String[] { rthRtoValidationTO.getConsignmentNumber() });
//		}
	}

	/**
	 * Validate is consignment delivered.
	 *
	 * @param consignmentTO the consignment to
	 * @throws CGBusinessException the cG business exception
	 */
	private void validateIsConsignmentDelivered(ConsignmentTO consignmentTO)
			throws CGBusinessException {
		if (consignmentTO != null
				&& consignmentTO.getConsgStatus().equalsIgnoreCase(
						CommonConstants.CONSIGNMENT_STATUS_DELIVERED)) {
			ExceptionUtil.prepareBusinessException(
					RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED,
					new String[] { consignmentTO.getConsgNo() });
		}
	}

	/**
	 * Find consignment number for rto.
	 * <p>
     * <ul>
     * <li>Search Consignment Number in Rto details.
     * <li>If not, Check Consignment Number exist in Rth details.
     * <li>If not, Check Consignment Number exist in DRS Details table.
     * <li>If Consignment Number not exist in any or exist & Consignment had O-OUT FOR DELIVERY, D-DELIVERED then throw
     * 		CGBusinessException with errorMsg .
     * </ul>
     * <p>
     * 
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the rth rto validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public RthRtoValidationTO findConsignmentNumber4Rto(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RthRtoValidationServiceImpl::findConsignmentNumber4Rto::START------------>:::::::");
		RthRtoValidationTO rthRtoValidationTO2 = new RthRtoValidationTO();
				
		RthRtoValidationTO rthValidationTO = getRthDetails4Rto(rthRtoValidationTO);

		if(rthValidationTO!=null){
			rthRtoValidationTO2.setRthValidationDetailsTOs(rthValidationTO.getRthRtoValidationDetailsTOs());			
		}
		
		ConsignmentReturnDO consignmentReturnDO = (ConsignmentReturnDO) rthRtoValidationDAO
				.getDataByNamedQueryAndNamedParam(
						RthRtoManifestConstatnts.QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE,
						new String[] {
								RthRtoManifestConstatnts.PARAM_CONSIGNMENT_NUMBER,
								RthRtoManifestConstatnts.PARAM_OFFICE_ID,
								RthRtoManifestConstatnts.PARAM_RETURN_TYPE },
						new Object[] {
								rthRtoValidationTO.getConsignmentNumber(),
								rthRtoValidationTO.getOfficeTO().getOfficeId(),
								rthRtoValidationTO.getReturnType() });
		if(consignmentReturnDO!=null){
			rthRtoValidationTO2.setIsRto(Boolean.TRUE);
			//call converter
			RthRtoValidationConverter.rthRtoValidationTransferConverter(consignmentReturnDO, rthRtoValidationTO2);

			//check whether consg is RTOH manifested.
			rthRtoValidationTO2.setIsRtohManifest(isConsignmentRtohManifested(rthRtoValidationTO));
		} else {

			//if Consignment is not InManifested then throw CGBusinessException
			validateIsConsignmentInManifested(rthRtoValidationTO);
			
			rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);			
			//validate with rth data
			if(rthValidationTO!=null){
				//rthRtoValidationTO2.setIsRth(Boolean.TRUE);
				
				//get consg details start
				if (rthValidationTO.getConsignmentTO()!=null) {
					RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(rthValidationTO.getConsignmentTO(), rthRtoValidationTO2);
				}
				
				if(StringUtils.isBlank(rthRtoValidationTO2.getContactNumber())){
					rthRtoValidationTO2.setContactNumber(rthValidationTO.getContactNumber());
				}
				if (StringUtils.isBlank(rthRtoValidationTO2.getDrsDateTimeStr())) {
					rthRtoValidationTO2.setDrsDateTimeStr(rthValidationTO.getDrsDateTimeStr());
				}

				validateIsConsignmentDelivered(rthValidationTO.getConsignmentTO());
			} else {
				
				/*********Drs Search Start**************************/
				/*search consg in drs, only pending consg is allowed
				param : consgNo*/
				DeliveryDetailsTO deliveryDetailsTO = rthRtoManifestCommonService
						.getDrsDtlsByConsgNo(rthRtoValidationTO.getConsignmentNumber());

				if(deliveryDetailsTO!=null){
					if(StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "D")){
						ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED, new String[] { rthRtoValidationTO.getConsignmentNumber() });
						//throw new CGBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED);
						
					}else if(StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "O")){
						ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY, new String[] { rthRtoValidationTO.getConsignmentNumber() });
						//throw new CGBusinessException(RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY);
						
					}

					validateIsConsignmentDelivered(deliveryDetailsTO.getConsignmentTO());
					//rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);
					RthRtoValidationConverter.convertDeliveryDetailsTO2RthRtoValidationTO(deliveryDetailsTO, rthRtoValidationTO2);

					/*********Drs Search End**************************/
					
					//RTO to allow excess consignment 
				}else if(StringUtils.equals(rthRtoValidationTO.getIsExcessConsg(), CommonConstants.YES)){
					

					/*********Consg Search Start**************************/
					ConsignmentTO consignmentTO = rthRtoManifestCommonService
							.getConsingmentDtls(rthRtoValidationTO
									.getConsignmentNumber());
					if(consignmentTO==null){
						throw new CGBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER);
					}

					rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);
					RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(consignmentTO, rthRtoValidationTO2);
					rthRtoValidationTO2.setDrsDateTimeStr(rthRtoValidationTO.getDrsDateTimeStr());
					/*********Consg Search End**************************/
					
				} else {

					ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER, new String[] { rthRtoValidationTO.getConsignmentNumber() });
					//throw new CGBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER);
				}
				
			}						
			

			/*********Consg Search Start**************************/
			/*ConsignmentTO consignmentTO = rthRtoManifestCommonService
					.getConsingmentDtls(rthRtoValidationTO
							.getConsignmentNumber());
			if(consignmentTO==null){
				throw new CGBusinessException(RthRtoManifestConstatnts.INVALID_CONSIGNMENT_NUMBER);
			}

			rthRtoValidationTO2.setIsConsignment(Boolean.TRUE);
			RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(consignmentTO, rthRtoValidationTO2);*/
			/*********Consg Search End**************************/
			
		}

		LOGGER.trace("RthRtoValidationServiceImpl::findConsignmentNumber4Rto::END------------>:::::::");
		return rthRtoValidationTO2;
	}

	private void validateIsConsignmentInManifested(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException, CGSystemException {
		//if Consignment is not InManifested then throw CGBusinessException
		//Allow only InManifest and Third Party(TPDX,TPBP) consg except others manifest consg
		InManifestTO inManifestTO = new InManifestTO();
		inManifestTO.setConsgNumber(rthRtoValidationTO.getConsignmentNumber());
		inManifestTO.setLoggedInOfficeId(rthRtoValidationTO.getOfficeTO().getOfficeId());
//		inManifestTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);		
//		Integer manifestId = rthRtoManifestCommonService
//				.getManifestIdFromCnManifest(inManifestTO);
//		
//		if (StringUtil.isEmptyInteger(manifestId)) {
//			ExceptionUtil.prepareBusinessException(
//					RthRtoManifestConstatnts.CONSIGNMENT_IS_NOT_YET_IN_MANIFESTED,
//					new String[] { rthRtoValidationTO.getConsignmentNumber() });
//		}

		InManifestTO inManifestTO1 = rthRtoManifestCommonService.getManifestDtlsByConsgNoOperatingOffice(inManifestTO);
//		if (inManifestTO1 == null
//				|| !StringUtils.equals(inManifestTO1.getManifestType(),
//						CommonConstants.MANIFEST_TYPE_IN)) {
//			ExceptionUtil
//					.prepareBusinessException(
//							RthRtoManifestConstatnts.CONSIGNMENT_IS_NOT_YET_IN_MANIFESTED,
//							new String[] { rthRtoValidationTO
//									.getConsignmentNumber() });
//		}
		if (inManifestTO1 == null
				|| (inManifestTO1 != null && !(StringUtils.equals(
						inManifestTO1.getManifestType(),
						CommonConstants.MANIFEST_TYPE_IN)
						|| StringUtils
								.equals(inManifestTO1.getProcessCode(),
										CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX) || StringUtils
							.equals(inManifestTO1.getProcessCode(),
									CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL)))) {
			ExceptionUtil
					.prepareBusinessException(
							RthRtoManifestConstatnts.CONSIGNMENT_IS_NEITHER_IN_MANIFESTED_NOR_THIRD_PARTY_MANIFESTED,
							new String[] { rthRtoValidationTO
									.getConsignmentNumber() });
		}
		//rthRtoValidationTO.setDrsDateTimeStr(inManifestTO1 != null?inManifestTO1.getManifestDate():null);
		rthRtoValidationTO.setDrsDateTimeStr(inManifestTO1.getManifestDate());
		rthRtoValidationTO.setIsExcessConsg(inManifestTO1.getIsExcessConsg());
	}

	/**
	 * Gets the rth details4 rto.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return the rth details4 rto
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private RthRtoValidationTO getRthDetails4Rto(
			RthRtoValidationTO rthRtoValidationTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.trace("RthRtoValidationServiceImpl::getRthDetails4Rto::START------------>:::::::");
		RthRtoValidationTO rthValidationTO = null;
		
		/*************************/
		//Search Rth Reasons details for Consignment Number
		//getConsignmentReturnByConsgNoReturnType
		ConsignmentReturnDO consignmentReturnDO = (ConsignmentReturnDO) rthRtoValidationDAO
				.getDataByNamedQueryAndNamedParam(
						RthRtoManifestConstatnts.QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_RETURN_TYPE,
						new String[] {
								RthRtoManifestConstatnts.PARAM_CONSIGNMENT_NUMBER,
								RthRtoManifestConstatnts.PARAM_RETURN_TYPE },
						new Object[] {
								rthRtoValidationTO.getConsignmentNumber(),
								RthRtoManifestConstatnts.RETURN_TYPE_RTH });		

		/*************************/
		
		if(consignmentReturnDO!=null){
			rthValidationTO = new RthRtoValidationTO();
			RthRtoValidationConverter.rthRtoValidationTransferConverter(consignmentReturnDO, rthValidationTO);
			
			//consg conversion start
			if (!StringUtil.isNull(consignmentReturnDO.getConsignmentDO())) {
				ConsignmentTO consignmentTO = new ConsignmentTO();
				RthRtoValidationConverter.convertConsignmentDO2TO(consignmentReturnDO.getConsignmentDO(), consignmentTO);
				RthRtoValidationConverter.convertConsignmentTO2RthRtoValidationTO(consignmentTO, rthValidationTO);
				rthValidationTO.setConsignmentTO(consignmentTO);
			}
			//consg conversion end
			
		}
		LOGGER.trace("RthRtoValidationServiceImpl::getRthDetails4Rto::END------------>:::::::");
		return rthValidationTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoValidationService#generateEodReport4OnHoldConsignment()
	 */
	@Override
	public void generateEodReport4OnHoldConsignment()
			throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.trace("RthRtoValidationServiceImpl::generateEodReport4OnHoldConsignment::START------------>:::::::");
		try{
			updateOnHoldEmailConsgStatus();
			getOnHoldConsignmentsAndSendMail();
						
		}catch(Exception e){
			LOGGER.error("Exception Occured in::RthRtoValidationServiceImpl::generateEodReport4OnHoldConsignment() :: " , e);
			
		}
		LOGGER.trace("RthRtoValidationServiceImpl::generateEodReport4OnHoldConsignment::END------------>:::::::");
	}

	/**
	 * Update on hold email consg status.
	 *
	 * @throws CGSystemException the cG system exception
	 */
	private void updateOnHoldEmailConsgStatus() throws CGSystemException {
		LOGGER.trace("RthRtoValidationServiceImpl::updateOnHoldEmailConsgStatus::START------------>:::::::");
		List<Integer> consignmentReturnIds = rthRtoValidationDAO
				.getConsignmentReturnIds(
						RthRtoManifestConstatnts.REASON_ON_HOLD,
						RthRtoManifestConstatnts.RETURN_TYPE_RTO);

		if(!StringUtil.isEmptyColletion(consignmentReturnIds)){
			rthRtoValidationDAO.updateOnHoldConsgEmailStatus(consignmentReturnIds);
		}
		LOGGER.trace("RthRtoValidationServiceImpl::updateOnHoldEmailConsgStatus::END------------>:::::::");
	}

	/**
	 * Gets the on hold consignments and send mail.
	 *
	 * @return the on hold consignments and send mail
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@SuppressWarnings("unchecked")
	private void getOnHoldConsignmentsAndSendMail() throws CGSystemException, CGBusinessException {

		LOGGER.trace("RthRtoValidationServiceImpl::getOnHoldConsignmentsAndSendMail::START------------>:::::::");
		OfficeTO loggedInOfficeTO = null;
		if(udaanContextService.getUserInfoTO()!=null && udaanContextService.getUserInfoTO().getOfficeTo()!=null){
			loggedInOfficeTO = udaanContextService.getUserInfoTO().getOfficeTo();
		}else{
			loggedInOfficeTO = new OfficeTO();
		}
		
		List<ConsignmentReturnDO> consignmentReturnDOs = null;
		List<ConsignmentReturnReasonDO> consignmentReturnReasonDOs = (List<ConsignmentReturnReasonDO>) rthRtoValidationDAO
				.getDataListByNamedQueryAndNamedParam(
						"getConsignmentReturnReasonsByReturnTypeReasonCode",
						new String[] {
								RthRtoManifestConstatnts.PARAM_RETURN_TYPE,
								RthRtoManifestConstatnts.PARAM_REASON_CODE },
						new Object[] {
								RthRtoManifestConstatnts.RETURN_TYPE_RTO,
								RthRtoManifestConstatnts.REASON_ON_HOLD });

		if (!StringUtil.isEmptyColletion(consignmentReturnReasonDOs)) {
			consignmentReturnDOs = new ArrayList<>();
			
			for (ConsignmentReturnReasonDO consignmentReturnReasonDO : consignmentReturnReasonDOs) {
				if(consignmentReturnReasonDO.getConsignmentReturnDO()!=null){
					consignmentReturnDOs.add(consignmentReturnReasonDO.getConsignmentReturnDO());
				}
			}
		}

		
		if(!StringUtil.isEmptyColletion(consignmentReturnDOs)){
			RtoReportTO rtoReportTO = new RtoReportTO();
			List<RtoConsignmentReportTO> rtoConsignmentReportTOs = new ArrayList<>();
			OfficeTO originOfficeTO = null;
			
			for (ConsignmentReturnDO consignmentReturnDO : consignmentReturnDOs) {
				RtoConsignmentReportTO rtoConsignmentReportTO = new RtoConsignmentReportTO();
				RthRtoValidationConverter.rtoConsignmentReportTransferConverter(consignmentReturnDO, rtoConsignmentReportTO);
				
				//add logic for consg origin office wise filtering
				if (consignmentReturnDO.getConsignmentDO() != null
						&& !StringUtil.isEmptyInteger(consignmentReturnDO
								.getConsignmentDO().getOrgOffId())) {					
					originOfficeTO = rthRtoManifestCommonService.getOfficeByIdOrCode(consignmentReturnDO
							.getConsignmentDO().getOrgOffId(), null);
				}
				
				LOGGER.debug("Origin office id is="+consignmentReturnDO.getConsignmentDO().getOrgOffId());
				
				rtoConsignmentReportTOs.add(rtoConsignmentReportTO);
			}
			rtoReportTO.setRtoConsignmentReportTOs(rtoConsignmentReportTOs);
			rtoReportTO.setOriginOfficeTO(originOfficeTO);
			rtoReportTO.setLoggedInOfficeTO(loggedInOfficeTO);
			
			sendEmail4RtoReport(rtoReportTO);
		}

		LOGGER.trace("RthRtoValidationServiceImpl::getOnHoldConsignmentsAndSendMail::END------------>:::::::");
	}

	/**
	 * Send email4 rto report.
	 *
	 * @param rtoReportTO the rto report to
	 */
	private void sendEmail4RtoReport(RtoReportTO rtoReportTO) {

		LOGGER.trace("RthRtoValidationServiceImpl::sendEmail4RtoReport::START------------>:::::::");
		String fromEmail = FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;
		String toEmail = "test_user2@testfirstflight.com";
		
		Map<Object, Object> mailTemplate = new HashMap<>();
		mailTemplate.put("rtoReportTO", rtoReportTO);

		if (rtoReportTO.getOriginOfficeTO() != null
				&& StringUtils.isNotBlank(rtoReportTO.getOriginOfficeTO()
						.getEmail())) {
			toEmail = rtoReportTO.getOriginOfficeTO().getEmail();
		}
		if (rtoReportTO.getLoggedInOfficeTO() != null
				&& StringUtils.isNotBlank(rtoReportTO.getLoggedInOfficeTO()
						.getEmail())) {
			fromEmail = rtoReportTO.getLoggedInOfficeTO().getEmail();
		}
		MailSenderTO mailSenderTO = new MailSenderTO();
		mailSenderTO.setFrom(fromEmail);
		mailSenderTO.setTo(new String[] { toEmail,
				"test_user2@testfirstflight.com" });
		mailSenderTO.setMailSubject("RTO on hold Consignments");
		mailSenderTO.setTemplateName("rtoConsignmentNotification.vm");
		mailSenderTO.setTemplateVariables(mailTemplate);
		
		emailSenderUtil.sendEmail(mailSenderTO);
		
		/*emailSenderService.sendEmailByTemplate(fromEmail, toEmail,
				"RTO on hold Consignments", mailTemplate,
				"rtoConsignmentNotification.vm");*/
		
		LOGGER.trace("RthRtoValidationServiceImpl::sendEmail4RtoReport::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoValidationService#twoWayWrite(com.ff.manifest.rthrto.RthRtoValidationTO)
	 */
	@Override
	public void twoWayWrite(RthRtoValidationTO rthRtoValidationTO) {
		if (rthRtoValidationTO != null
				&& !StringUtil.isEmptyInteger(rthRtoValidationTO
						.getConsignmentReturnId())) {
			LOGGER.debug("RthRtoValidationServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>:::::::");
			TwoWayWriteProcessCall.twoWayWriteProcess(
					rthRtoValidationTO.getConsignmentReturnId(),
					CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT_RETURN);
		}
	}

/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Integer> orgOffList = new ArrayList<>();
		orgOffList.add(null);
		orgOffList.add(null);
		orgOffList.add(1);
		orgOffList.add(1);
		orgOffList.add(1);
		orgOffList.add(2);
		orgOffList.add(3);
		orgOffList.add(3);
		orgOffList.add(4);
		
		
		Integer prev = 0;
		List<Object> list = new ArrayList<>(); 
		List<Object> nullList = new ArrayList<>(); 
		List<Integer> newOffList = null;
		
		for (Integer orgOff : orgOffList) {
			if(orgOff==null){
				nullList.add(orgOff);
				continue;
			}
			if((prev==0) || (prev != null && !prev.equals(orgOff))){
				prev = orgOff;
				newOffList = new ArrayList<>();
				list.add(newOffList);
			}
			newOffList.add(orgOff);
		}

		
		
 	}*/

}
