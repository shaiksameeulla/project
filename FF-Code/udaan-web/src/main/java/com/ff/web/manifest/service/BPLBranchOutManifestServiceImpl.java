package com.ff.web.manifest.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.BPLBranchOutManifestDetailsTO;
import com.ff.manifest.BplBranchOutManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProcessTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.dao.OutManifestUniversalDAO;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.action.BPLOutManifestDoxAction;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.BPLBranchOutManifestConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;


public class BPLBranchOutManifestServiceImpl implements
		BPLBranchOutManifestService {
	private ManifestUniversalDAO manifestUniversalDAO;
	private OutManifestUniversalService outManifestUniversalService;
	private OutManifestUniversalDAO outManifestUniversalDAO;
	private OutManifestCommonService outManifestCommonService;
	private ManifestUniversalService manifestUniversalService;
	private ManifestCommonService manifestCommonService;

	public ManifestCommonService getManifestCommonService() {
		return manifestCommonService;
	}

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxAction.class);
	

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}
	
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}
	
	public void setOutManifestUniversalDAO(
			OutManifestUniversalDAO outManifestUniversalDAO) {
		this.outManifestUniversalDAO = outManifestUniversalDAO;
	}
	
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}
	
	public void setManifestUniversalService(
			ManifestUniversalService manifestUniversalService) {
		this.manifestUniversalService = manifestUniversalService;
	}
	
 // get teh grid manifest details
	public BplBranchOutManifestTO getManifestDtls(
			ManifestInputs manifestInputsTO) throws CGBusinessException,
			CGSystemException {
		BplBranchOutManifestTO bplBranchOutManifestTO = null;
		BPLBranchOutManifestDetailsTO bplBranchOutManifestDetlTO = null;
		ManifestDO manifestDO = null;
	
		//manifestDO = manifestUniversalDAO.getBoutGridManifestDtls(manifestInputsTO);
		//manifestDO = manifestCommonService.getManifestDtls(manifestInputsTO);
		manifestDO = manifestCommonService.getManifestDetails(manifestInputsTO);
		List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOs = 
				new ArrayList<BPLBranchOutManifestDetailsTO>();
		if (manifestDO != null) {
			bplBranchOutManifestTO = new BplBranchOutManifestTO();
			if(manifestDO.getManifestEmbeddedIn()!=null){
				ManifestInputs manifestTOs = new ManifestInputs();
				ProcessTO processTO = new ProcessTO();
				processTO
						.setProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
				processTO = outManifestUniversalService.getProcess(processTO);
				
				manifestTOs.setProcessId(processTO.getProcessId());
				manifestTOs.setGridManfstProcessCode(manifestDO.getManifestProcessCode());
				manifestTOs.setManifestNumber(manifestDO.getManifestNo());
				manifestTOs.setManifestId(manifestDO.getManifestId());
				manifestTOs.setHeaderManifestNo(manifestInputsTO.getHeaderManifestNo());
				manifestTOs.setLoginOfficeId(manifestDO.getOriginOffice().getOfficeId());
				manifestTOs.setManifestType(ManifestConstants.MANIFEST_DIRECTION_OUT);
				//inManifest Integration starts
				//check if type of manifestembedded in manifestDO is I 
				//isEmbeddedTypeIn = outManifestCommonService.isEmbeddedTypeIsOfInManifest(manifestDO.getManifestEmbeddedIn());
				
					if(!manifestDO.getManifestType().equalsIgnoreCase(ManifestConstants.MANIFEST_DIRECTION_IN)){
						if(outManifestCommonService.isManifestEmbeddedIn(manifestTOs)){
						bplBranchOutManifestTO.setManifestEmbedded(Boolean.TRUE);
						throw new CGBusinessException(
								ManifestErrorCodesConstants.MANIFEST_ALREADY_EMBEDDED);
						}
					}
					
					//inManifest Integration ends
				
				//TODO comment this after implementing inManifestIntegration
				/*if(outManifestCommonService.isManifestEmbeddedIn(manifestTOs)){
					bplBranchOutManifestTO.setManifestEmbedded(Boolean.TRUE);
				}else{
					bplBranchOutManifestTO.setManifestEmbedded(Boolean.FALSE);
				}*/
				
			}
		
			bplBranchOutManifestDetlTO = new BPLBranchOutManifestDetailsTO();
			bplBranchOutManifestDetlTO.setNoOfConsignment(manifestDO
					.getNoOfElements());
			
			bplBranchOutManifestDetlTO
					.setWeight(manifestDO.getManifestWeight());

			bplBranchOutManifestTO.setManifestId(manifestDO.getManifestId());
			//bplBranchOutManifestTO.getConsignmentTypeTO().setConsignmentId(manifestDO.getManifestLoadContent().getConsignmentId());
			if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
				ConsignmentTypeTO to = new ConsignmentTypeTO();
				to.setConsignmentId(manifestDO.getManifestLoadContent()
						.getConsignmentId());
				to.setConsignmentCode(manifestDO.getManifestLoadContent()
						.getConsignmentCode());
				to.setConsignmentName(manifestDO.getManifestLoadContent()
						.getConsignmentName());
				bplBranchOutManifestTO.setConsignmentTypeTO(to);
			}
			if (manifestDO.getDestOffice() != null) {
				Integer destOfficId = manifestDO.getDestOffice().getOfficeId();
				if(!(manifestInputsTO.getManifestDestOfficId().equals(destOfficId))){
					throw new CGBusinessException(
							ManifestErrorCodesConstants.DESTINATION_NOT_SERVICED);
					}
				}
				
				
				/*OfficeTO offcTo = new OfficeTO();
				offcTo.setOfficeId((manifestDO.getDestOffice().getOfficeId()));
				bplBranchOutManifestTO.setDestinationOfficeTO(offcTo);*/
			}
			if (manifestDO.getManifestProcessCode() != null) {
				ManifestProcessTO manifestProcesTO = new ManifestProcessTO(); 
				manifestProcesTO.setManifestProcessCode(manifestDO.getManifestProcessCode());
				bplBranchOutManifestTO.setManifestProcessTo(manifestProcesTO);
			}
			
			
			bplBranchOutManifestDetailsTOs.add(bplBranchOutManifestDetlTO);
			/*
			 * bplBranchOutManifestDoxTO.setFinalWeight(manifestDO.getManifestWeight
			 * ());
			 * bplBranchOutManifestDoxTO.setManifestId(manifestDO.getManifestId
			 * ());
			 * bplBranchOutManifestDoxTO.setBagLockNo(manifestDO.getBagLockNo
			 * ());
			 * bplBranchOutManifestDoxTO.setBagRFID(manifestDO.getBagRFID());
			 * bplBranchOutManifestDoxTO
			 * .setDestinationOfficeId(manifestDO.getOriginOffice
			 * ().getOfficeId());
			 */

			if (manifestDO.getDestinationCity() != null) {
				CityTO cityTo = new CityTO();
				cityTo.setCityName((manifestDO.getDestinationCity()
						.getCityName()));
				cityTo.setCityId((manifestDO.getDestinationCity().getCityId()));
				bplBranchOutManifestTO.setDestinationCityTO(cityTo);
			}
			bplBranchOutManifestTO.setBplBranchOutManifestDetailsTOsList(bplBranchOutManifestDetailsTOs);
			return bplBranchOutManifestTO;
	}
		
	

	
		
		//  save BPL
		public boolean saveOrUpdateBplBranchOutManifest(
				BplBranchOutManifestTO bplBranchOutManifestTO) throws CGBusinessException,
				CGSystemException {
			
			
			boolean isSaved = Boolean.FALSE;
			List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTO = null;
			Boolean searchedManifest = Boolean.FALSE;
			Date currentDate = new Date();

			/* 
			 * Validate Manifest Number whether it is Open/Closed /New get 
			 * the Complete manifest DO... from Database
			 */
			if (!StringUtil.isEmptyInteger(bplBranchOutManifestTO.getManifestId())) {
				searchedManifest = Boolean.TRUE;
			}
			
			/* If manifest is not already searched i.e. the ID is not set */
			ManifestInputs manifestInput=new ManifestInputs();
			manifestInput.setManifestNumber(bplBranchOutManifestTO.getManifestNo());
			manifestInput.setLoginOfficeId(bplBranchOutManifestTO.getLoginOfficeId());
			manifestInput.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestInput.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
			manifestInput.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
			ManifestDO manifestDOSearch = OutManifestBaseConverter.prepateManifestDO(manifestInput);
			//manifestDOSearch = manifestCommonService.getEmbeddedInManifest(manifestDOSearch);
			manifestDOSearch = manifestCommonService.getManifestForCreation(manifestDOSearch);
			
			if (!StringUtil.isNull(manifestDOSearch)) {
				if (StringUtils.equalsIgnoreCase(manifestDOSearch.getManifestStatus(),
						OutManifestConstants.CLOSE)) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
				} else if (StringUtils.equalsIgnoreCase(
						manifestDOSearch.getManifestStatus(), OutManifestConstants.OPEN)
						&& !searchedManifest) {
					/* 
					 * If the manifest status is Open throw a Business exception
					 * indicating the manifest is closed.
					 */
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
				}
			}
			
			if (bplBranchOutManifestTO != null) {
				
				//prepares bplBranchOutManifestDetailsTOList list
				bplBranchOutManifestDetailsTO =BPLBranchOutManifestConverter
						.prepareBPLBranchOutManifestDtlList(bplBranchOutManifestTO);
				//sets the list in the TO
				bplBranchOutManifestTO
						.setBplBranchOutManifestDetailsTOsList(bplBranchOutManifestDetailsTO);
			
			
					
					// Setting process id
					ProcessTO processTO = new ProcessTO();
					processTO
							.setProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
					processTO = outManifestUniversalService.getProcess(processTO);
					bplBranchOutManifestTO.setProcessId(processTO.getProcessId());
					bplBranchOutManifestTO.setProcessCode(processTO.getProcessCode());
					
					OfficeTO officeTO = new OfficeTO();
					officeTO.setOfficeId(bplBranchOutManifestTO.getLoginOfficeId());
					if(!StringUtil.isStringEmpty(bplBranchOutManifestTO.getOfficeCode())){
						officeTO.setOfficeCode(bplBranchOutManifestTO.getOfficeCode());
					}
					
					String processNumber = outManifestCommonService.createProcessNumber(processTO, officeTO);
					bplBranchOutManifestTO.setProcessNo(processNumber);
					
					//to calc operating level
					Integer operatingLevel=0;
					operatingLevel=outManifestUniversalService.calcOperatingLevel(bplBranchOutManifestTO,officeTO);
					bplBranchOutManifestTO.setOperatingLevel(operatingLevel);

					if (bplBranchOutManifestTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
						bplBranchOutManifestTO.setIsMulDestination(CommonConstants.YES);
					}
					
					//added for new change
					List<String> manifestNOList = new ArrayList<String>();
					String gridManifestNO = null;
					int noOfGridManifest = bplBranchOutManifestTO
							.getBplBranchOutManifestDetailsTOsList().size();
					for (int i = 0; i < noOfGridManifest; i++) {
						gridManifestNO = bplBranchOutManifestTO
								.getBplBranchOutManifestDetailsTOsList().get(i)
								.getManifestNo();
						manifestNOList.add(gridManifestNO);
					}

					String previousManifestNO = null;
					List<ManifestDO> manifestGridDOs = manifestCommonService
							.getManifests(manifestNOList, bplBranchOutManifestTO.getLoginOfficeId());
					LinkedHashSet<ManifestDO> manifestGridDOset = new LinkedHashSet<>();
					for (int i = 0; i < manifestNOList.size(); i++) {
						for (int j = 0; j < manifestGridDOs.size(); j++) {
							if (manifestGridDOs.get(j).getManifestNo().equalsIgnoreCase(previousManifestNO)){
								continue;
							} else {
								previousManifestNO = manifestGridDOs.get(j).getManifestNo();	
							}
							if (manifestNOList.get(i).equalsIgnoreCase(
									manifestGridDOs.get(j).getManifestNo())) {

								ProcessDO processDO = new ProcessDO();
								processDO.setProcessId(bplBranchOutManifestTO
										.getProcessId());
								if (manifestGridDOs
										.get(j)
										.getManifestDirection()
										.equalsIgnoreCase(
												CommonConstants.MANIFEST_TYPE_IN)) {
									ManifestDO newManifestDO = BPLBranchOutManifestConverter
											.convertInManifestGridPacketToDO(
													manifestGridDOs.get(j),
													processDO,bplBranchOutManifestTO);
									newManifestDO.setPosition(bplBranchOutManifestTO
											.getPosition()[i]);
									manifestGridDOset.add(newManifestDO);
								} else {
									ManifestDO manifest = manifestGridDOs.get(j);
									manifest.setUpdatingProcess(processDO);
									manifest.setPosition(bplBranchOutManifestTO
											.getPosition()[i]);
									manifestGridDOset.add(manifest);
								}

							}
						}
					}
					
					// Prepare ManifestDO
					ManifestDO manifestDO = BPLBranchOutManifestConverter
							.prepareManifestDOList(bplBranchOutManifestTO);

					if (!CGCollectionUtils.isEmpty(manifestGridDOset)) {
						manifestDO.setEmbeddedManifestDOs(manifestGridDOset);
					}
					
					// Prepare ManifestProcessDO
					/*ManifestProcessDO manifestProcessDO = BPLBranchOutManifestConverter
							.prepareManifestProcessDOList(bplBranchOutManifestTO);
					if(!StringUtil.isNull(bplBranchOutManifestTO.getManifestProcessTo())&& !StringUtil.isEmptyInteger(bplBranchOutManifestTO.getManifestProcessTo().getManifestProcessId())){
						manifestProcessDO.setManifestProcessId(bplBranchOutManifestTO.getManifestProcessTo().getManifestProcessId());
					}*/
					
					//to prevent duplicate entry whil savin in manifestProcess--start
					/*List<ManifestProcessDO> manifestProcessDOs = null;
					ManifestProcessDO manifestProcessDO = null;*/
					manifestDO.setCreatedDate(currentDate);
					/*if(StringUtil.isEmptyInteger(bplBranchOutManifestTO.getManifestId())){
					 Manifest Creation 
						manifestProcessDO = BPLBranchOutManifestConverter
								.prepareManifestProcessDOList(bplBranchOutManifestTO);
						//manifestProcessDO = manifestProcessDOs.get(0);
						manifestProcessDO.setCreatedDate(currentDate);
						
					} else {
					 Manifest update 
						ManifestInputs manifestTO = prepareForManifestProcess(bplBranchOutManifestTO);
						manifestProcessDOs = manifestUniversalDAO
								.getManifestProcessDtls(manifestTO);
						if(!StringUtil.isNull(manifestProcessDOs.get(0))){
						manifestProcessDO = manifestProcessDOs.get(0);
						}
						manifestProcessDO.setUpdatedDate(currentDate);
					}*/

					
					
					//to prevent duplicate entry whil savin in manifestProcess--end
					
					if (!StringUtil.isNull(manifestDO)) {
						//to set DT_TO_CENTRAL Y while saving
						ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
						/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO);*/
						
						manifestDO.setUpdatedDate(currentDate);
						
						
						isSaved = manifestCommonService.saveManifest(manifestDO);
						bplBranchOutManifestTO.setManifestId(manifestDO.getManifestId());
						/*ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
						manifestProcessTO.setManifestProcessId(manifestProcessDO
								.getManifestProcessId());*/
						/*bplBranchOutManifestTO.setManifestProcessTo(manifestProcessTO);*/
						
						//for two way write
						bplBranchOutManifestTO.setTwoWayManifestId(manifestDO.getManifestId());
						/*bplBranchOutManifestTO.setManifestProcessId(manifestProcessDO.getManifestProcessId());*/
					} else {
						isSaved = Boolean.FALSE;
						ExceptionUtil
								.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
					}
			}
			
			LOGGER.trace("BPLBranchOutManifestServiceImpl::saveOrUpdateBplBranchOutManifest::START");

			return isSaved;
		}

		//search manifest Details
		public BplBranchOutManifestTO searchManifestDtls(ManifestInputs manifestTO)
				throws CGBusinessException, CGSystemException {
			ManifestDO manifestDO = null;
			BplBranchOutManifestTO bplBranchOutManifestTO = null;
			List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOs = null;

			try {
				LOGGER.trace("BPLBranchOutManifestServiceImpl:: searchManifestDtls()::START------------>:::::::");

				ManifestDO manifest=OutManifestBaseConverter.prepateManifestDO(manifestTO);
				manifestDO = manifestCommonService.getEmbeddedInManifest(manifest);
				if (!StringUtil.isNull(manifestDO)) {
					if(!StringUtil.isNull(manifestDO.getManifestLoadContent())){
						if(manifestDO.getManifestLoadContent().getConsignmentCode().equals(ManifestConstants.CONSG_TYPE_PARCEL)){
							
							throw new CGBusinessException(ManifestErrorCodesConstants.MANIFEST_INVALID);
						}
					}
					
					bplBranchOutManifestTO = BPLBranchOutManifestConverter
							.bplBranchoutManifestDomainConverter(manifestDO);

					/* setting the embedded in field of grid */
					//manifestTO.setManifestId(mbplOutManifestTO.getManifestId());
					if (!StringUtil.isNull(manifestDO)) {
						bplBranchOutManifestDetailsTOs = BPLBranchOutManifestConverter
								.bplBranchOutManifestDomainConvertorForEmbeddedIn(manifestDO);
					}
					if (!StringUtil.isEmptyList(bplBranchOutManifestDetailsTOs)) {
						double consgToatalWt = 0.0;
						for (BPLBranchOutManifestDetailsTO bplBranchOutManifestDetailsTO : bplBranchOutManifestDetailsTOs) {
							if (!StringUtil.isEmptyDouble(bplBranchOutManifestDetailsTO
									.getWeight())) {
								consgToatalWt += bplBranchOutManifestDetailsTO
										.getWeight();
							}

						}
						bplBranchOutManifestTO.setConsigTotalWt(Double
								.parseDouble(new DecimalFormat("##.###")
										.format(consgToatalWt)));
						Collections.sort(bplBranchOutManifestDetailsTOs);
						bplBranchOutManifestTO
								.setBplBranchOutManifestDetailsTOsList(bplBranchOutManifestDetailsTOs);
					}

					// get RFID reference number by RFID
					String rfIdNo = outManifestUniversalService
							.getBagRfNoByRfId(bplBranchOutManifestTO.getBagRFID());
					bplBranchOutManifestTO.setRfidNo(rfIdNo);

				} else {
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_SEARCH_DETAILS_NOT_FOUND);
				}
		}catch(Exception e){
			LOGGER.error("Error occured in BPLBranchOutManifestServiceImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw e;
		}
			return bplBranchOutManifestTO;
}

		@Override
		public BplBranchOutManifestTO getTotalConsignmentCount(
				List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestTOs)
				throws CGBusinessException, CGSystemException {
			
			BplBranchOutManifestTO bplBranchOutManifestTO = new BplBranchOutManifestTO();
			ManifestDO manifestDOChild  = null;
			Long actualConsg = 0L;
			try {
				LOGGER.trace("BPLBranchOutManifestServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
				for (BPLBranchOutManifestDetailsTO bplBranchOutManifestDetailsTO : bplBranchOutManifestTOs) {
					if(!StringUtil.isEmptyInteger(bplBranchOutManifestDetailsTO.getManifestId())){
						ManifestBaseTO  baseTO1=new ManifestBaseTO();
						baseTO1.setManifestId(bplBranchOutManifestDetailsTO.getManifestId());
						baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
						 manifestDOChild = outManifestCommonService.getManifestById(bplBranchOutManifestDetailsTO.getManifestId());
					}
					if(!StringUtil.isNull(manifestDOChild)){
					/*	if (!StringUtil.isNull(manifestDOChild.getManifestLoadContent())) {
							if (manifestDOChild.getManifestLoadContent().getConsignmentId()
									.equals(2)) {
								if (!StringUtil.isNull(manifestDOChild.getNoOfElements())) {
									actualConsg += manifestDOChild.getNoOfElements();
								}
							} else {

								if(!StringUtil.isNull(manifestDOChild.getEmbeddedManifestDOs())){
									 Set<ManifestDO>  packetList= manifestDOChild.getEmbeddedManifestDOs();
									for(ManifestDO manifestPacket:packetList){
										actualConsg+=manifestUniversalDAO.getConsgCountByManifestId(manifestPacket
												.getManifestId());
									}
									
								}
					}
					}*/
						actualConsg+=manifestUniversalDAO.getConsgCountByManifestId(manifestDOChild
								.getManifestId());
						
						}
				}
				bplBranchOutManifestTO.setConsigTotal(actualConsg);
				bplBranchOutManifestTO.setRowcount(bplBranchOutManifestTOs.size());
				
			}catch (CGSystemException e) {
				LOGGER.error("Error occured in BPLBranchOutManifestServiceImpl :: getTotalConsignmentCount()..:"
						+ e.getMessage());
				throw e;
			} catch (Exception e) {
				LOGGER.error("Error occured in BPLBranchOutManifestServiceImpl :: getTotalConsignmentCount()..:"
						+ e.getMessage());
				throw e;
			}
			LOGGER.trace("BPLBranchOutManifestServiceImpl:: getTotalConsignmentCount()::END------------>:::::::");
			return bplBranchOutManifestTO;
		}
		
		private ManifestInputs prepareForManifestProcess(
				BplBranchOutManifestTO bplBranchOutManifestTO) {
			ManifestInputs manifestInputs = new ManifestInputs();
			manifestInputs.setManifestNumber(bplBranchOutManifestTO.getManifestNo());
			manifestInputs.setLoginOfficeId(bplBranchOutManifestTO.getLoginOfficeId());
			manifestInputs.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
			manifestInputs.setManifestDirection(bplBranchOutManifestTO
					.getManifestDirection());
			return manifestInputs;
		}
		
}
