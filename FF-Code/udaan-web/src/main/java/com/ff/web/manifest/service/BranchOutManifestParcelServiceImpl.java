package com.ff.web.manifest.service;

import java.util.Date;
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
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.BranchOutManifestParcelDetailsTO;
import com.ff.manifest.BranchOutManifestParcelTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.BranchOutManifestParcelConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;

/**
 * 
 * @author hkansagr
 *
 */

public class BranchOutManifestParcelServiceImpl implements BranchOutManifestParcelService {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestParcelServiceImpl.class);
	/** The manifestUniversalDAO */
	private ManifestUniversalDAO manifestUniversalDAO;
	
	/** The outManifestUniversalService */
	private OutManifestUniversalService outManifestUniversalService;
	
	/** The outManifestCommonService */
	private OutManifestCommonService outManifestCommonService;
	
	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;
	
	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}
	
	/**
	 * @param outManifestCommonService the outManifestCommonService to set
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}
	
	/**
	 * @return the manifestUniversalDAO
	 */
	public ManifestUniversalDAO getManifestUniversalDAO() {
		return manifestUniversalDAO;
	}
	/**
	 * @param manifestUniversalDAO the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}
	/**
	 * @return the outManifestUniversalService
	 */
	public OutManifestUniversalService getOutManifestUniversalService() {
		return outManifestUniversalService;
	}
	/**
	 * @param outManifestUniversalService the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}
	
	/**
	 * @method searchManifestDtls
	 * 
	 * search manifest details by manifestNumber
	 * 
	 * @author hkansagr
	 * @throws Exception 
	 */
	@Override
	public BranchOutManifestParcelTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelServiceImpl :: searchManifestDtls() :: START------------>:::::::");
		ManifestDO manifestDO = null;
		BranchOutManifestParcelTO branchOutManifestParcelTO = null;
		Long startTime = System.currentTimeMillis();
		// manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO);
		manifestDO = OutManifestBaseConverter.prepateManifestDO(manifestTO);
		manifestDO = manifestCommonService.getParcelManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());
			gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,  ManifestConstants.ACTION_SEARCH);
			manifestDO.setConsignments(gridItemOrderDO.getConsignmentDOs());
			
			branchOutManifestParcelTO = BranchOutManifestParcelConverter
					.branchOutManifestParcelDomainConverter(manifestDO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.debug("TIMER:" + (endTime - startTime));
		LOGGER.trace("BranchOutManifestParcelServiceImpl :: searchManifestDtls() :: END------------>:::::::");
		return branchOutManifestParcelTO;
	}
	
	/**
	 * @method getConsignmentDtls
	 * 
	 * get consignment details from database
	 * 
	 * @author hkansagr
	 */
	@Override
	public BranchOutManifestParcelDetailsTO getConsignmentDtls(OutManifestValidate
			cnValidateTO) throws CGBusinessException, CGSystemException{
		LOGGER.debug("BranchOutManifestParcelServiceImpl::getConsignmentDtls::START");
		BranchOutManifestParcelDetailsTO detailsTO = null;
		
			/*List<ConsignmentModificationTO> consignmentModificationTOs = 
							outManifestUniversalService.getBookingDtls(cnValidateTO);*/
			ConsignmentModificationTO consModifcatnTO =cnValidateTO.getConsignmentModificationTO();
			if (!StringUtil.isNull(consModifcatnTO)) {
				detailsTO = convertBookingDtlsTOListToBranchParcelDetailTO(consModifcatnTO);
				
			} 
		 
		LOGGER.debug("BranchOutManifestParcelServiceImpl::getConsignmentDtls::END");
		return detailsTO;
	}
	
	/**
	 * @method convertBookingDtlsTOListToBranchParcelDetailTO
	 * 
	 * Convert booking details list to BranchOutManifestParcelDetailsTO
	 * 
	 * @author hkansagr
	 */
	private BranchOutManifestParcelDetailsTO convertBookingDtlsTOListToBranchParcelDetailTO(
			ConsignmentModificationTO consignmentModificationTO) 
					throws CGBusinessException, CGSystemException {
		LOGGER.debug("BranchOutManifestParcelServiceImpl::convertBookingDtlsTOListToBranchParcelDetailTO::START");
		BranchOutManifestParcelDetailsTO detailTO = null;
		if (!StringUtil.isNull(consignmentModificationTO)) {
			detailTO = (BranchOutManifestParcelDetailsTO) BranchOutManifestParcelConverter
					.branchOutManifestParcelDtlsConverter(consignmentModificationTO);
		}
		LOGGER.debug("BranchOutManifestParcelServiceImpl::convertBookingDtlsTOListToBranchParcelDetailTO::END");
		return detailTO;
	}
	
	/**
	 * @method saveOrUpdateBranchOutManifestParcel
	 * 
	 * To save or update BRANCH OUT MANIFEST PARCEL details to database
	 * 
	 * @author hkansagr
	 */
	
	
	
	@Override
	public BranchOutManifestParcelDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException, CGSystemException{
		LOGGER.debug("BranchOutManifestParcelServiceImpl::getInManifestdConsignmentDtls()::START");
		BranchOutManifestParcelDetailsTO branchOutManifstParcelDetailsTO = null;
		try{
			/*List<ConsignmentTO> consignmtTOs = outManifestUniversalService
					.getConsgDtls(manifestFactoryTO);*/
			ConsignmentTO consignmntTO=outManifestUniversalService.getConsingmentDtls(manifestFactoryTO.getConsgNumber());
			branchOutManifstParcelDetailsTO = convertConsgDtlsTOListToBranchOutParcelDetailsTO(
					consignmntTO, manifestFactoryTO);
		} catch(Exception e) {
			LOGGER.debug("Exception occurs in BranchOutManifestParcelServiceImpl::getInManifestdConsignmentDtls()::"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BranchOutManifestParcelServiceImpl::getInManifestdConsignmentDtls()::END");
		return branchOutManifstParcelDetailsTO;
	}
	
	/**
	 * To convert CN Details to List of BranchOutManifestParcelDetailsTO
	 * @param consignmentTOs
	 * @param manifestFactoryTO
	 * @return barnchOutManifestParcelDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private BranchOutManifestParcelDetailsTO convertConsgDtlsTOListToBranchOutParcelDetailsTO(
			ConsignmentTO consignmentTO,	ManifestFactoryTO manifestFactoryTO) 
					throws CGBusinessException,	CGSystemException {
		LOGGER.debug("BranchOutManifestParcelServiceImpl::convertConsgDtlsTOListToBranchOutParcelDetailsTO()::START");
		BranchOutManifestParcelDetailsTO barnchOutManifestParcelDetailsTO = null;
		if (!StringUtil.isNull(consignmentTO)) {
			barnchOutManifestParcelDetailsTO = (BranchOutManifestParcelDetailsTO) BranchOutManifestParcelConverter
					.branchOutParcelGridDetailsForInManifConsg(consignmentTO);
		}
		LOGGER.debug("BranchOutManifestParcelServiceImpl::convertConsgDtlsTOListToBranchOutParcelDetailsTO()::END");
		return barnchOutManifestParcelDetailsTO;
	}
	
	public String saveOrUpdateBranchOutManifestParcel(ManifestDO manifest,
			BranchOutManifestParcelTO branchoutManifestParcelTO, List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BranchOutManifestParcelServiceImpl :: saveOrUpdateBranchOutManifestParcel() :: START------------>:::::::");
		/** Define variables */
		Boolean searchedManifest = Boolean.FALSE;
		ManifestDO manifestDO = null;
		Date currentDate = new Date();
		/**
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(manifest.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}

		/** If manifest is not already searched i.e. the ID is not set */
		manifestDO = manifestCommonService.getManifestForCreation(manifest);
		
		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				/**
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
		}else{
			manifest.setCreatedDate(currentDate);
			manifest.setUpdatedDate(currentDate);
		}
		
		//added by niharika bcoz it was savin status O if u close the manifest
		if(!StringUtil.isNull(manifestDO)){
			if(manifest.getManifestStatus().equalsIgnoreCase(OutManifestConstants.CLOSE)&& manifestDO.getManifestStatus().equalsIgnoreCase(OutManifestConstants.OPEN)){
				manifestDO.setManifestStatus(manifest.getManifestStatus());
			}
			if(!StringUtil.isNull(manifest.getManifestWeight())){
				manifestDO.setManifestWeight(manifest.getManifestWeight());
			}
			if(!StringUtil.isNull(manifest.getGridItemPosition())){
				manifestDO.setGridItemPosition(manifest.getGridItemPosition().toString());
				}
			if(!StringUtil.isNull(manifest.getNoOfElements())){
				manifestDO.setNoOfElements(manifest.getNoOfElements());
				}
			manifestDO.setUpdatedDate(currentDate);
		}
		
		
		if (StringUtil.isNull(manifestDO)) {
			manifestDO = manifest;
		}

		
		manifestDO.setConsignments(allConsignments);

		//to prevent duplicate entry whil savin in manifestProcess--start
				/*List<ManifestProcessDO> manifestProcessDOs = null;
				ManifestProcessDO manifestProcessDO = null;*/

				/*if(StringUtil.isEmptyInteger(branchoutManifestParcelTO.getManifestId())){
				 Manifest Creation 
					manifestProcessDOs = BranchOutManifestParcelConverter
							.prepareManifestProcessDOList(branchoutManifestParcelTO);
					manifestProcessDO = manifestProcessDOs.get(0);
					manifestProcessDO.setCreatedDate(currentDate);
				} else {
				 Manifest update 
					ManifestInputs manifestTO = prepareForManifestProcess(branchoutManifestParcelTO);
					manifestProcessDOs = manifestUniversalDAO
							.getManifestProcessDtls(manifestTO);
					if(!StringUtil.isNull(manifestProcessDOs.get(0))){
					manifestProcessDO = manifestProcessDOs.get(0);
					manifestProcessDO.setUpdatedDate(currentDate);
					}
				}*/
				
				//to set DT_TO_CENTRAL as Y while saving
				ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
				/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO);*/
			
				//to prevent duplicate entry whil savin in manifestProcess--end
		/** SAVING MANIFST and MANIFEST PROCESS */
		boolean result = manifestCommonService.saveManifest(manifestDO);
		//for two way write
		branchoutManifestParcelTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*branchoutManifestParcelTO.setManifestProcessId(manifestProcessDO.getManifestProcessId());*/
		if (!result) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}

		LOGGER.trace("BranchOutManifestParcelServiceImpl :: saveOrUpdateBranchOutManifestParcel() :: END------------>:::::::");
		return manifestDO.getManifestStatus();
	}
	
	private ManifestInputs prepareForManifestProcess(
			BranchOutManifestParcelTO branchOutManifestParcelTO) {
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(branchOutManifestParcelTO.getManifestNo());
		manifestInputs.setLoginOfficeId(branchOutManifestParcelTO.getLoginOfficeId());
		manifestInputs.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifestInputs.setManifestDirection(branchOutManifestParcelTO
				.getManifestDirection());
		return manifestInputs;
	}
	

}
