/**
 * 
 */
package com.ff.web.manifest.inmanifest.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestDetailsDoxTO;
import com.ff.manifest.inmanifest.InBagManifestDetailsParcelTO;
import com.ff.manifest.inmanifest.InBagManifestDoxTO;
import com.ff.manifest.inmanifest.InBagManifestParcelTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InBPLManifestConverter;
import com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;

/**
 * The Class InBagManifestServiceImpl.
 *
 * @author narmdr
 */
public class InBagManifestServiceImpl implements InBagManifestService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InBagManifestServiceImpl.class);
	
	/** The in manifest common service. */
	private transient InManifestCommonService inManifestCommonService;
	
	/** The in manifest common dao. */
	private transient InManifestCommonDAO inManifestCommonDAO;	
	
	/** The tracking universal service. */
	private transient TrackingUniversalService trackingUniversalService;
	
	/** The manifest universal dao. */
	private transient ManifestUniversalDAO manifestUniversalDAO;
	
	/**
	 * @param trackingUniversalService the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
     * Sets the in manifest common service.
     *
     * @param inManifestCommonService the inManifestCommonService to set
     */
	public void setInManifestCommonService(
			final InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * Sets the in manifest common dao.
	 *
	 * @param inManifestCommonDAO the inManifestCommonDAO to set
	 */
	public void setInManifestCommonDAO(
			final InManifestCommonDAO inManifestCommonDAO) {
		this.inManifestCommonDAO = inManifestCommonDAO;
	}

	/**
	 * @param manifestUniversalDAO the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/**
     * find Bpl Number Dox
     * <p>
     * <ul>
     * <li>Check BPL Number in In Manifest.
     * <li>If not, Check Is there any BPL Number created by Receive.
     * <li>If BPL Number not exist in any then return
     * 		InManifestValidationTO with isNewManifest(true) field.
     * </ul>
     * <p>
     *
     * @param inBagManifestDoxTO the in bag manifest dox to
     * @return InManifestValidationTO :: If all the validations are passed then return InManifestValidationTO.
     * else throws CGBusinessException.
     * @throws CGBusinessException the cG base exception
     * @throws CGSystemException the cG system exception
     * @author R Narmdeshwar
     */
	@Override
	public InManifestValidationTO findBplNumberDox(InBagManifestDoxTO inBagManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InBagManifestServiceImpl::findBplNumberDox::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();
		InBagManifestDoxTO inBagManifestDoxTO1 = new InBagManifestDoxTO();
		
		ManifestDO manifestDO = null;
		//ManifestDO outManifestDO = null;
		
		/*ManifestBaseTO baseTO = new ManifestBaseTO();
		ConsignmentTypeTO consgTypeTO = new ConsignmentTypeTO();
		consgTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		baseTO.setManifestNumber(inBagManifestDoxTO.getManifestNumber());
		baseTO.setProcessCode(InManifestConstants.PROCESS_CODE_BPL_PARCEL);
		baseTO.setConsignmentTypeTO(consgTypeTO);

		if (inManifestCommonDAO.isBplDoxParcel(baseTO)) {
			throw new CGBusinessException(
					InManifestConstants.ONLY_DOX_TYPE_BPL_ALLOWED);
		}*/

		//check is BPL Dox type else throw CGBusinessException
		isBplDoxParcel(inBagManifestDoxTO.getManifestNumber(),
				CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		
		
		/*inBagManifestDoxTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);
		OfficeTO destinationOfficeTO = new OfficeTO();
		destinationOfficeTO.setOfficeId(inBagManifestDoxTO.getDestinationOfficeId());
		inBagManifestDoxTO.setDestinationOfficeTO(destinationOfficeTO);
		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		consignmentTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		inBagManifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
		inBagManifestDoxTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);*/
		
		//search BPL Number in In Manifest
		inBagManifestDoxTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		inBagManifestDoxTO.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
		manifestDO = inManifestCommonDAO.getManifestDetailsWithFetchProfile(inBagManifestDoxTO);
		//manifestDO = inManifestCommonDAO.getManifestByNoProcessType(inBagManifestDoxTO);
		InManifestUtils.resetFetchProfile(inBagManifestDoxTO);
		//manifestDO = inManifestCommonDAO.getManifestByNoProcessConsgType(inBagManifestDoxTO);

		if (manifestDO != null) {
			inManifestCommonService.validateIsManifestOutManifested(manifestDO);
			inManifestValidationTO.setIsInManifest(Boolean.TRUE);
			List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs = InBPLManifestConverter
					.transferObjConverter4InBagManifestDetailsDoxTO(manifestDO
							.getEmbeddedManifestDOs());
			inBagManifestDoxTO1
					.setInBagManifestDetailsDoxTOs(inBagManifestDetailsDoxTOs);
			/*ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
			manifestBaseTO.setManifestId(manifestDO.getManifestId());
			List<ManifestDO> manifestDOs = inManifestCommonDAO.getEmbeddedManifestDtlsByEmbeddedId(manifestBaseTO);*/
			/*ManifestInputs manifestInputs = new ManifestInputs();
			manifestInputs.setManifestId(manifestDO.getManifestId());
			List<ManifestDO> manifestDOs = manifestUniversalDAO.getEmbeddedManifestDtls(manifestInputs);*/
			/*Set<ManifestDO> manifestDOs = manifestDO.getEmbeddedManifestDOs();
			if(!StringUtil.isEmptyColletion(manifestDOs)){
				List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs = InBPLManifestConverter.transferObjConverter4InBagManifestDetailsDoxTO(manifestDOs);
				inBagManifestDoxTO1.setInBagManifestDetailsDoxTOs(inBagManifestDetailsDoxTOs);
			}*/
			
		}else{
			ExceptionUtil.prepareBusinessException(
					InManifestConstants.MANIFEST_NUMBER_NOT_YET_RECEIVED,
					new String[] { inBagManifestDoxTO.getManifestNumber() });
			//search for other manifest
			manifestDO = searchOtherManifestByNo(inBagManifestDoxTO.getManifestNumber());
			
			
			/*
			//search any BPL Number created by Receive
			inBagManifestDoxTO.setUpdateProcessCode(CommonConstants.PROCESS_RECEIVE);
			manifestDO = inManifestCommonDAO.getManifestByNoProcessType(inBagManifestDoxTO);
			//manifestDO = inManifestCommonDAO.getManifestDtls(inBagManifestDoxTO);	
			//manifestDO = inManifestCommonDAO.getManifestByNoProcessConsgType(inBagManifestDoxTO);


			if(manifestDO!=null){
				inManifestValidationTO.setIsInManifestByReceive(Boolean.TRUE);
				
				///////////////////////////////////////////////
				//search any BPL Number in Out Manifest for origin office

				inBagManifestDoxTO
						.setProcessCode(InManifestConstants.OUT_BPL_DOX_PROCESS_CODE);
				inBagManifestDoxTO
						.setUpdateProcessCode(InManifestConstants.OUT_BPL_DOX_UPDATE_PROCESS_CODE);
				
				if(manifestDO.getOriginOffice()==null){
					outManifestDO = inManifestCommonDAO
							.getManifestDtls(inBagManifestDoxTO);

					if(outManifestDO!=null){
						manifestDO.setOriginOffice(outManifestDO.getOriginOffice());
					}
				}
				/////////////////////////////////////////////////	
				
			}
			
			///////////////////////////////////////////////
			//search any BPL Number in Out Manifest
			if(manifestDO==null){
				//already outManifest searched at the time of Receive
				if(outManifestDO==null){

					inBagManifestDoxTO
							.setProcessCode(InManifestConstants.OUT_BPL_DOX_PROCESS_CODE);
					inBagManifestDoxTO
							.setUpdateProcessCode(InManifestConstants.OUT_BPL_DOX_UPDATE_PROCESS_CODE);
					
					outManifestDO = inManifestCommonDAO
							.getManifestDtls(inBagManifestDoxTO);
				}
				manifestDO = outManifestDO;
				if(manifestDO!=null){
					inManifestValidationTO.setIsOutManifest(Boolean.TRUE);
				}
			}
			/////////////////////////////////////////////////			
*/		
		}
		
		if(manifestDO!=null){
			InBPLManifestConverter.transferObjConverter4InBagManifestDoxTO(manifestDO, inBagManifestDoxTO1);//header
			inManifestValidationTO.setInBagManifestDoxTO(inBagManifestDoxTO1);
		}else{
			inManifestValidationTO.setIsNewManifest(Boolean.TRUE);
		}
		LOGGER.trace("InBagManifestServiceImpl::findBplNumberDox::END------------>:::::::");
		return inManifestValidationTO;
	}

	/**
	 * Search other manifest by no.
	 *
	 * @param manifestNumber the manifest number
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	private ManifestDO searchOtherManifestByNo(String manifestNumber)
			throws CGSystemException {
		ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
		manifestBaseTO.setManifestNumber(manifestNumber);
		ManifestDO manifestDO = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(manifestBaseTO);
		if (manifestDO != null) {
			manifestDO.setManifestId(null);
		}
		return manifestDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#saveOrUpdateInBagManifestDox(com.ff.manifest.inmanifest.InBagManifestDoxTO)
	 */
	@Override
	public InBagManifestDoxTO saveOrUpdateInBagManifestDox(
			InBagManifestDoxTO inBagManifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("InBagManifestServiceImpl::saveOrUpdateInBagManifestDox::START------------>:::::::");

		//Check is Header Manifest already inManifested
		inManifestCommonService.isManifestHeaderInManifested(inBagManifestDoxTO);
		

		//Less, Excess, Received status 
		InManifestTO inManifestTO = inManifestCommonService
				.prepareInManifestTO4LessExcess(inBagManifestDoxTO,
						InManifestConstants.OUT_BPL_DOX_PROCESS_CODE,
						InManifestConstants.OUT_BPL_DOX_UPDATE_PROCESS_CODE);
		inManifestCommonService.getLessExcessManifest(inBagManifestDoxTO, inManifestTO);
		
		/*ManifestDO headerManifestDO = new ManifestDO();
		InBPLManifestConverter.domainConverter4InManifestHeader(inBagManifestDoxTO, headerManifestDO);*/
		ManifestDO headerManifestDO = InBPLManifestConverter.domainConverter4InBagManifestDox(inBagManifestDoxTO);
		inManifestCommonService.getMnfstOpenTypeAndBplMnfstType(headerManifestDO);
		headerManifestDO = inManifestCommonDAO.saveOrUpdateManifest(headerManifestDO);

		//setting manifestId for TwoWayWrite
		InManifestUtils.setManifestId(inBagManifestDoxTO, headerManifestDO);
		
		//////
		/*List<ManifestDO> manifestDOs = InBPLManifestConverter.domainConverterList4BPLDox(inBagManifestDoxTO, headerManifestDO);
		Set<ManifestMappedEmbeddedDO> manifestMappedEmbeddedDOs = InBPLManifestConverter.domainConverterList4ManifestMappedEmbedded(inBagManifestDoxTO, headerManifestDO);
		//inManifestCommonDAO.saveOrUpdateManifestMappedEmbeddedDOs(manifestMappedEmbeddedDOs);
		
		//manifestDOs = inManifestCommonDAO.saveOrUpdateManifestList(manifestDOs);		

		InBPLManifestConverter.setManifestMappedEmbeddedDOs(headerManifestDO, manifestMappedEmbeddedDOs);
		headerManifestDO = inManifestCommonDAO.saveOrUpdateManifest(headerManifestDO);*/
		
/*		List<ManifestProcessDO> manifestProcessDOs = new ArrayList<ManifestProcessDO>();
		InBPLManifestConverter.prepareManifestProcessDOList(headerManifestDO, manifestProcessDOs);
		InManifestUtils.setDestOfficeAndCityToManifestProcess(headerManifestDO, manifestProcessDOs);
		inManifestCommonDAO.saveOrUpdateManifestProcess(manifestProcessDOs);
		//setting manifestProcessId for TwoWayWrite
		InManifestUtils.setManifestProcessId(inBagManifestDoxTO, manifestProcessDOs.get(0));*/

		//, CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX
		
		LOGGER.trace("InBagManifestServiceImpl::saveOrUpdateInBagManifestDox::END------------>:::::::");
		return inBagManifestDoxTO;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#findBplNumberParcel(com.ff.manifest.inmanifest.InBagManifestParcelTO)
	 */
	@Override
	public InManifestValidationTO findBplNumberParcel(
			InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InBagManifestServiceImpl::findBplNumberParcel::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();		
		ManifestDO manifestDO = null;

		//check is BPL Parcel type else throw CGBusinessException
		isBplDoxParcel(inBagManifestParcelTO.getManifestNumber(),
				CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		
		/*OfficeTO destinationOfficeTO = new OfficeTO();
		destinationOfficeTO.setOfficeId(inBagManifestParcelTO.getDestinationOfficeId());
		inBagManifestParcelTO.setDestinationOfficeTO(destinationOfficeTO);
		inBagManifestParcelTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		
		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		consignmentTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		inBagManifestParcelTO.setConsignmentTypeTO(consignmentTypeTO);
		inBagManifestParcelTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);*/
		
		//search BPL Number in In Manifest
		inBagManifestParcelTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		inBagManifestParcelTO.setIsFetchProfileManifestParcel(Boolean.TRUE);
		manifestDO = inManifestCommonDAO.getManifestDetailsWithFetchProfile(inBagManifestParcelTO);
		InManifestUtils.resetFetchProfile(inBagManifestParcelTO);
		//manifestDO = inManifestCommonDAO.getManifestByNoProcessType(inBagManifestParcelTO);
		//manifestDO = inManifestCommonDAO.getManifestByNoProcessConsgType(inBagManifestParcelTO);

		if(manifestDO!=null){
			inManifestCommonService.validateIsManifestOutManifested(manifestDO);
			inManifestValidationTO.setIsInManifest(Boolean.TRUE);
		}else{

			ExceptionUtil.prepareBusinessException(
					InManifestConstants.MANIFEST_NUMBER_NOT_YET_RECEIVED,
					new String[] { inBagManifestParcelTO.getManifestNumber() });
			
			//search for other manifest
			manifestDO = searchOtherManifestByNo(inBagManifestParcelTO.getManifestNumber());
			
			/*
			//search any BPL Number created by Receive
			inBagManifestParcelTO.setUpdateProcessCode(CommonConstants.PROCESS_RECEIVE);
			manifestDO = inManifestCommonDAO.getManifestByNoProcessType(inBagManifestParcelTO);
			//manifestDO = inManifestCommonDAO.getManifestByNoProcessConsgType(inBagManifestParcelTO);

			if(manifestDO!=null){
				
				inManifestValidationTO.setIsInManifestByReceive(Boolean.TRUE);
				
				///////////////////////////////////////////////
				//search any BPL Number in Out Manifest for origin office
				inBagManifestParcelTO
						.setProcessCode(InManifestConstants.OUT_BPL_PARCEL_PROCESS_CODE);
				inBagManifestParcelTO
						.setUpdateProcessCode(InManifestConstants.OUT_BPL_PARCEL_UPDATE_PROCESS_CODE);
		
				if(manifestDO.getOriginOffice()==null){
					final ManifestDO outManifestDO = inManifestCommonDAO
							.getManifestDtls(inBagManifestParcelTO);

					if (outManifestDO != null) {
						manifestDO.setOriginOffice(outManifestDO.getOriginOffice());
					}
				}
				/////////////////////////////////////////////////
			}
						
			///////////////////////////////////////////////
			//search any BPL Number in Out Manifest
			if(manifestDO==null){

				inBagManifestParcelTO
						.setProcessCode(InManifestConstants.OUT_BPL_PARCEL_PROCESS_CODE);
				inBagManifestParcelTO
						.setUpdateProcessCode(InManifestConstants.OUT_BPL_PARCEL_UPDATE_PROCESS_CODE);
				
				manifestDO = inManifestCommonDAO.getManifestDtls(inBagManifestParcelTO);
				if(manifestDO!=null){
					inManifestValidationTO.setIsOutManifest(Boolean.TRUE);
				}
			}
			/////////////////////////////////////////////////	
			*/
		}
		
		if(manifestDO!=null){
			InBagManifestParcelTO inBagManifestParcelTO1 = InBPLManifestConverter.transferObjConverter4InBagManifestParcelTO(manifestDO, inManifestValidationTO);
			inManifestValidationTO.setInBagManifestParcelTO(inBagManifestParcelTO1);
		}else{
			inManifestValidationTO.setIsNewManifest(Boolean.TRUE);
		}
		LOGGER.trace("InBagManifestServiceImpl::findBplNumberParcel::END------------>:::::::");
		return inManifestValidationTO;	
	}

	private void isBplDoxParcel(String manifestNumber,
			String consignmentTypeCode) throws CGSystemException,
			CGBusinessException {
		String processCode = InManifestConstants.PROCESS_CODE_BPL_DOX;
		String errorCode = InManifestConstants.ONLY_PARCEL_TYPE_BPL_ALLOWED;

		if (StringUtils.equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL,
				consignmentTypeCode)) {
			processCode = InManifestConstants.PROCESS_CODE_BPL_PARCEL;
			errorCode = InManifestConstants.ONLY_DOX_TYPE_BPL_ALLOWED;
		}

		ManifestBaseTO baseTO = new ManifestBaseTO();
		ConsignmentTypeTO consgTypeTO = new ConsignmentTypeTO();
		consgTypeTO.setConsignmentCode(consignmentTypeCode);
		baseTO.setManifestNumber(manifestNumber);
		baseTO.setProcessCode(processCode);
		baseTO.setConsignmentTypeTO(consgTypeTO);

		if (inManifestCommonDAO.isBplDoxParcel(baseTO)) {
			throw new CGBusinessException(errorCode);
		}
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#saveOrUpdateInBagManifestParcel(com.ff.manifest.inmanifest.InBagManifestParcelTO)
	 */
	@Override
	public InBagManifestParcelTO saveOrUpdateInBagManifestParcel(
			InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel::START------------>:::::::");
		
		//Check is Header Manifest already inManifested
		inManifestCommonService.isManifestHeaderInManifested(inBagManifestParcelTO);
		
		ManifestDO manifestDO = new ManifestDO();
		InBPLManifestConverter.manifestDomainConverter4BPLParcel(inBagManifestParcelTO, manifestDO);
		inManifestCommonService.getMnfstOpenTypeAndBplMnfstType(manifestDO);
		inManifestCommonService.setProductInConsg(manifestDO);
		//validate and mark consg as excess.
		inManifestCommonService.validateAndSetExcessConsgFlag(manifestDO);
		//manifestDO = inManifestCommonDAO.saveOrUpdateManifest(manifestDO);
		
		StringBuilder mnfstdtls = getManifestDtlsForLogger(manifestDO);
		
		LOGGER.info("InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel::before save------------>" +mnfstdtls);
		//TODO Using merge due to NonUniqueObjectException: a different object with the same identifier value was already associated with the session: 
		manifestDO = inManifestCommonDAO.saveOrMergeManifest(manifestDO);
		try {
			StringBuilder mnfstdtlsPostSave = getManifestDtlsForLogger(manifestDO);
			LOGGER.info("InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel::after save------------>" +mnfstdtlsPostSave);
		} catch (Exception e) {
			LOGGER.error("InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel(after save)::exception",e);
		}

		//setting manifestId for TwoWayWrite
		/*InManifestUtils.setManifestId(inBagManifestParcelTO, manifestDO);
		
		//List<ManifestProcessDO> manifestProcessDOs = new ArrayList<ManifestProcessDO>();
		InBPLManifestConverter.prepareManifestProcessDOList(manifestDO, manifestProcessDOs);
		InManifestUtils.setDestOfficeAndCityToManifestProcess(manifestDO, manifestProcessDOs);
		inManifestCommonDAO.saveOrUpdateManifestProcess(manifestProcessDOs);

		//setting manifestProcessId for TwoWayWrite
		InManifestUtils.setManifestProcessId(inBagManifestParcelTO, manifestProcessDOs.get(0));*/
		
		/*try {
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel()::saveOrUpdateManifestProcess:: "
					+ e.getMessage());
		}*/

		//less excess consg. TODO

		inBagManifestParcelTO
				.setUpdateProcessCode(InManifestConstants.OUT_BPL_PARCEL_UPDATE_PROCESS_CODE);
		inBagManifestParcelTO
				.setProcessCode(InManifestConstants.OUT_BPL_PARCEL_PROCESS_CODE);
		inManifestCommonService.getLessExcessConsg(inBagManifestParcelTO);
		/*InManifestTO inManifestTO = inManifestCommonService.prepareInManifestTO4LessExcessConsg(inBagManifestParcelTO, CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
		inManifestCommonService.getLessExcessConsg(inBagManifestParcelTO, inManifestTO);*/
		
		//Commented Because ProcessMap is no where using
		/*try {
			prepareAndSaveProcessMapTO4Parcel(manifestDO, inBagManifestParcelTO);
		} catch (Exception e) {
			// TODO: handle exception
		}*/

		LOGGER.trace("InBagManifestServiceImpl::saveOrUpdateInBagManifestParcel::END------------>:::::::");
		return inBagManifestParcelTO;
	}

	private StringBuilder getManifestDtlsForLogger(ManifestDO manifestDO) {
		StringBuilder mnfstdtls=new StringBuilder("manifest Details : manifest NO:");
		mnfstdtls.append(manifestDO.getManifestNo());
		mnfstdtls.append("manifest type :");
		mnfstdtls.append(manifestDO.getManifestType());
		mnfstdtls.append(" No of elements :");
		mnfstdtls.append(manifestDO.getNoOfElements());
		mnfstdtls.append("updating process ID :");
		mnfstdtls.append(manifestDO.getUpdatingProcess().getProcessId());
		mnfstdtls.append(" updating process Code :");
		mnfstdtls.append(manifestDO.getUpdatingProcess().getProcessCode());
		mnfstdtls.append(" Grid item position:");
		mnfstdtls.append(manifestDO.getGridItemPosition());
		mnfstdtls.append(" Grid item position:");
		mnfstdtls.append(manifestDO.getGridItemPosition());
		return mnfstdtls;
	}
	

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#validateConsgNumber(com.ff.manifest.inmanifest.InBagManifestParcelTO)
	 */
	@Override
	public InManifestValidationTO validateConsgNumber(
			InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InBagManifestServiceImpl::validateConsgNumber::START------------>:::::::");
		//validate consgNo is InManifested
//		inBagManifestParcelTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
//		inBagManifestParcelTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
//		inBagManifestParcelTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
//		if(inManifestCommonDAO.isConsgNoInManifested(inBagManifestParcelTO)){
//			ExceptionUtil.prepareBusinessException(InManifestConstants.CONSIGNMENT_NO_ALREADY_IN_MANIFESTED);
//			//throw new CGBusinessException(InManifestConstants.CONSIGNMENT_NO_ALREADY_IN_MANIFESTED);
//		}
		
		inManifestCommonService.validateConsgNoInManifested(inBagManifestParcelTO);
		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		consignmentTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);

		ConsignmentTO consignmentTO = new ConsignmentTO();
		consignmentTO.setConsgNo(inBagManifestParcelTO.getConsgNumber());
		consignmentTO.setTypeTO(consignmentTypeTO);	
		
		InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO = null;
		//ConsignmentDO consignmentDO = inManifestCommonDAO.getConsignmentDetails(inManifestTO);
		//ConsignmentDO consignmentDO = inManifestCommonService.getConsignmentDetails(consignmentTO);
		ConsignmentDO consignmentDO = inManifestCommonDAO.getConsignmentDetails(consignmentTO);//TODO
		if(consignmentDO!=null){
			inBagManifestDetailsParcelTO = InBPLManifestConverter.transferObjConverter4InBagManifestDetailsParcelTO(consignmentDO);

			//get Booking Type By Consg Number
			//ConsignmentTO consignmentTO = new ConsignmentTO();
			consignmentTO.setConsgNo(consignmentDO.getConsgNo());
			String bookingType = inManifestCommonService.getBookingTypeByConsgNumber(consignmentTO);
			inBagManifestDetailsParcelTO.setBookingType(bookingType);			
		}else{
			//Integer consignmentId = inManifestCommonDAO.getConsignmentIdByConsgNo(inManifestTO);
			Integer consignmentId = inManifestCommonService.getConsignmentIdByConsgNo(consignmentTO);
			if(!StringUtil.isEmptyInteger(consignmentId)){
				ExceptionUtil.prepareBusinessException(InManifestConstants.ONLY_PARCEL_TYPE_CONSIGNMENT_ALLOWED);
				//throw new CGBusinessException(InManifestConstants.ONLY_PARCEL_TYPE_CONSIGNMENT_ALLOWED);
			}
		}
		InManifestValidationTO inManifestValidationTO2 = new InManifestValidationTO();
		inManifestValidationTO2.setInBagManifestDetailsParcelTO(inBagManifestDetailsParcelTO);
		LOGGER.trace("InBagManifestServiceImpl::validateConsgNumber::END------------>:::::::");
		return inManifestValidationTO2;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#validateDeclaredValue(com.ff.booking.BookingValidationTO)
	 */
	@Override
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingValidationTO) throws CGBusinessException,
			CGSystemException {
		return inManifestCommonService.validateDeclaredValue(bookingValidationTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InBagManifestService#getPaperWorks(com.ff.serviceOfferring.CNPaperWorksTO)
	 */
	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGBusinessException,
			CGSystemException {
		return inManifestCommonService.getPaperWorks(paperWorkValidationTO);
	}

	@Override
	public InBagManifestDoxTO findBplNumberDox4Print(
			InBagManifestDoxTO inBagManifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("InBagManifestServiceImpl::findBplNumberDox4Print::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();
		InBagManifestDoxTO inBagManifestDoxTO1 = null;		
		ManifestDO manifestDO = null;

		inBagManifestDoxTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		inBagManifestDoxTO.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
		manifestDO = inManifestCommonDAO.getManifestDetailsWithFetchProfile(inBagManifestDoxTO);
		InManifestUtils.resetFetchProfile(inBagManifestDoxTO);
		
		if(manifestDO!=null){
			inBagManifestDoxTO1 = new InBagManifestDoxTO();

			InBPLManifestConverter.transferObjConverter4InBagManifestDoxTO(manifestDO, inBagManifestDoxTO1);//header
			inManifestValidationTO.setInBagManifestDoxTO(inBagManifestDoxTO1);

			int count=0;
			Long totalConsg=0L;
			if (!StringUtil.isEmptyColletion(manifestDO.getEmbeddedManifestDOs())) {
				
				List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs = InBPLManifestConverter.transferObjConverter4InBagManifestDetailsDoxTO(manifestDO
						.getEmbeddedManifestDOs());
				//quer for packet  out packet no ,type=out ,orig off 
				if(!StringUtil.isNull(inBagManifestDetailsDoxTOs)){
				for(InBagManifestDetailsDoxTO  inBagManifestDetailsDoxTO:inBagManifestDetailsDoxTOs){
					count++;
					ManifestBaseTO baseTO = new ManifestBaseTO(); 
					if(!StringUtil.isNull(inBagManifestDetailsDoxTO.getManifestNumber())){
						baseTO.setManifestNumber(inBagManifestDetailsDoxTO.getManifestNumber());
					}
					if(!StringUtil.isEmptyInteger(inBagManifestDetailsDoxTO.getOriginOfficeId())){
						baseTO.setOriginOfficeId(inBagManifestDetailsDoxTO.getOriginOfficeId());
					}
					baseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
					Integer manifestId=inManifestCommonDAO.getManifestIdByManifestNoAndTypeAndOrigin(baseTO);
					if(!StringUtil.isEmptyInteger(manifestId)){
						totalConsg+=manifestUniversalDAO.getConsgCountByManifestId(manifestId);
					}
					
				 }
				}
				inBagManifestDoxTO1.setRowCount(count);
				inBagManifestDoxTO1.setInBagManifestDetailsDoxTOs(inBagManifestDetailsDoxTOs);
				inBagManifestDoxTO1.setTotalConsg(totalConsg);
			}
		}
		LOGGER.trace("InBagManifestServiceImpl::findBplNumberDox4Print::END------------>:::::::");
		return inBagManifestDoxTO1;	
	}
}
