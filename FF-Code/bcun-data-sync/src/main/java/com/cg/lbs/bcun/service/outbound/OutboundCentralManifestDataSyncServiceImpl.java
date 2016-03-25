package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.outbound.officefinder.ManifestOutboundOfficeFinderServiceImpl;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;
import com.ff.domain.manifest.BcunManifestDO;


/**
 * The Class OutboundCentralManifestDataSyncServiceImpl.
 *
 * @author narmdr
 * 
 */
public class OutboundCentralManifestDataSyncServiceImpl implements OutboundCentralManifestDataSyncService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutboundCentralManifestDataSyncServiceImpl.class);

	/** The bcun service. */
	private transient BcunDatasyncService bcunService;
	

	/**
	 * @param bcunService the bcunService to set
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.outbound.OutboundCentralManifestDataSyncService#proceedOutboundProcess(com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO, com.cg.lbs.bcun.to.OutboundConfigPropertyTO)
	 */
	public void proceedOutboundProcess(
			OutboundDatasyncConfigOfficeDO configOffice,
			OutboundConfigPropertyTO configProcess) throws IOException {
		LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "Start...");
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		List<Integer> pojoPkList=null;
		if(baseList != null && !baseList.isEmpty()) {
			pojoPkList = new ArrayList<Integer>(baseList.size());
			Integer branchId = configOffice.getOutboundOffice().getOfficeId();
			String officeSenderClass = configProcess.getOfficesFinder();

			/*
			 A. If Manifest having source/origin and selected destination. Then Manifest will travel to following locations :-
				1.	All hubs of  source/origin city.
				2.	All hubs of destination city and selected destination branch.

			 B. If Manifest having source/origin and destination as ALL. Then Manifest will travel to following locations :-
				1.	All hubs of  source/origin city.
				2.	All hubs and branches of destination city.
			*/
			
			
			if(officeSenderClass != null) {
				Set<Integer> hubsOfficeIds =  null;
				Set<Integer> branchesOfficeIds =  null;
				
				try {
					//OutboundOfficeFinderService officeFinder =  (OutboundOfficeFinderService)Class.forName(officeSenderClass).newInstance();
					
					/*
					  A. For Destination City Manifest will travel to following locations :-
						 1. Send to All Hubs of destination City
						 2. If Manifest has destination as ALL then send to all branches.
						 3. Group by selected destination office Id & send to selected destination office
					 */
					
					//A.1. Send to All Hubs of destination City
					//getAllHubs of destCity
					hubsOfficeIds =  ManifestOutboundOfficeFinderServiceImpl.getAllHubsOftheBranchCity(bcunService, branchId);
					
					if(!StringUtil.isEmptyColletion(hubsOfficeIds)) {
						LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Finder Class :["+officeSenderClass+"] Found all hubs office Details of destination city :["+hubsOfficeIds.toString()+"]");
						bcunService.prepareAndStoreZipFile(baseList, hubsOfficeIds, configProcess.getPropKey(),configProcess.getCategory());
					}
					
					//A. 2. If Manifest has destination as ALL then send to all branches.
					List<CGBaseDO> destBaseList = new ArrayList<>();
					List<CGBaseDO> reaminingDestBaseList = new ArrayList<>();

					//get all Manifest whose destination is all.
					for (CGBaseDO cgBaseDO : baseList) {
						BcunManifestDO bcunManifestDO = (BcunManifestDO) cgBaseDO;
						pojoPkList.add(bcunManifestDO.getManifestId());
						if(StringUtils.equals(CommonConstants.YES, bcunManifestDO.getMultipleDestination())){
							destBaseList.add(bcunManifestDO);
						}else{
							reaminingDestBaseList.add(bcunManifestDO);
						}
					}
					if(!StringUtil.isEmptyColletion(destBaseList)) {
						branchesOfficeIds =  ManifestOutboundOfficeFinderServiceImpl.getAllBranchesOfBranchCity(bcunService, branchId);
						
						if(!StringUtil.isEmptyColletion(branchesOfficeIds)) {
							LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Finder Class :["+officeSenderClass+"] Found all branches office Details of destination city :["+branchesOfficeIds.toString()+"]");
							bcunService.prepareAndStoreZipFile(destBaseList, branchesOfficeIds, configProcess.getPropKey(),configProcess.getCategory());
						}
					}
					
					//A.3. Group by selected destination office Id & send to selected destination office
					if(!StringUtil.isEmptyColletion(reaminingDestBaseList)) {

						Set<Integer> destOfficeIds =  new HashSet<>();
						//Create unique list of all different dest office
						for (CGBaseDO cgBaseDO : reaminingDestBaseList) {
							BcunManifestDO bcunManifestDO = (BcunManifestDO) cgBaseDO;
							if(!StringUtil.isEmptyInteger(bcunManifestDO.getDestOffice())){
								destOfficeIds.add(bcunManifestDO.getDestOffice());
							}
						}
						
						if(!StringUtil.isEmptyColletion(destOfficeIds)) {
							for (Integer destOfficeId : destOfficeIds) {
								List<CGBaseDO> dest1BaseList = new ArrayList<>();
								for (CGBaseDO cgBaseDO : reaminingDestBaseList) {
									BcunManifestDO bcunManifestDO = (BcunManifestDO) cgBaseDO;
									if(!StringUtil.isEmptyInteger(bcunManifestDO.getDestOffice())
											&& destOfficeId.equals(bcunManifestDO.getDestOffice())){
										dest1BaseList.add(cgBaseDO);
									}
								}
								if(!StringUtil.isEmptyColletion(dest1BaseList)) {
									HashSet<Integer> destOfficeList=new HashSet<Integer>(1);
									destOfficeList.add(destOfficeId);
									bcunService.prepareAndStoreZipFile(dest1BaseList,destOfficeList , configProcess.getPropKey(),configProcess.getCategory());									
								}
							}
						}						
					}

					
					/*
					  B. For Origin City Manifest will travel to following locations :-
						 1. All Hubs of origin City
					 */
					
					/*
					 * 2.Filter out All different origin/source officeIds & then
					 * 	2a. List out all DOs related to that origin/source officeId
					 * 	2b. Get All hub officeIds of origin/source officeId . 
					 * 		& then create prepareNStoreZipFile with list of hubsOfficeIds of origin City.
					 */
					
					Set<Integer> originOfficeIds =  new HashSet<>();
					//Create unique list of all different origin office
					for (CGBaseDO cgBaseDO : baseList) {
						BcunManifestDO bcunManifestDO = (BcunManifestDO) cgBaseDO;
						if(!StringUtil.isEmptyInteger(bcunManifestDO.getOriginOffice())){
							originOfficeIds.add(bcunManifestDO.getOriginOffice());
						}
					}
					
					if(!StringUtil.isEmptyColletion(originOfficeIds)) {
						for (Integer originOfficeId : originOfficeIds) {
							List<CGBaseDO> originBaseList = new ArrayList<>();
							for (CGBaseDO cgBaseDO : baseList) {
								BcunManifestDO bcunManifestDO = (BcunManifestDO) cgBaseDO;
								if(!StringUtil.isEmptyInteger(bcunManifestDO.getOriginOffice())
										&& originOfficeId.equals(bcunManifestDO.getOriginOffice())){
									originBaseList.add(cgBaseDO);
								}
							}

							hubsOfficeIds =  ManifestOutboundOfficeFinderServiceImpl.getAllHubsOftheBranchCity(bcunService, originOfficeId);
							//originBranchHubOfficeIds =  officeFinder.getAllOutboundOffices(originOfficeId, bcunService, originBaseList);					
							if(!StringUtil.isEmptyColletion(hubsOfficeIds)) {
								hubsOfficeIds.remove(originOfficeId);//removed origin office which is the source of Manifest
								if(!StringUtil.isEmptyColletion(hubsOfficeIds)) {
									LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Finder Class :["+officeSenderClass+"] Found all hubs office Details of origin city :["+hubsOfficeIds.toString()+"]");
									bcunService.prepareAndStoreZipFile(originBaseList, hubsOfficeIds, configProcess.getPropKey(),configProcess.getCategory());
								}
							}
						}
					}
					
					bcunService.udateDataTransferToBranchStatusByPrimaryKeyList(pojoPkList,"updateDataSycnForManifestByPrimaryKey");
					pojoPkList=null;
				} catch (Exception e) {
					LOGGER.error("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess::EXCEPTION :: ",e);
					throw e;
				}
			}
			LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "for all offices completed...");
			//udateDataTransferToBranchStatus(bcunService, baseList); moved to prepareNstoreZipFile
		}
		LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "End...");
	}

	private static void udateDataTransferToBranchStatus(
			BcunDatasyncService bcunService, List<CGBaseDO> baseList) {
		LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutbountMasterDataPreparation::udateDataTransferToBranchStatus ::START");
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
		}
		bcunService.saveOrUpdateTransferedEntities(baseList);
		LOGGER.debug("OutboundCentralManifestDataSyncServiceImpl::proceedOutbountMasterDataPreparation::udateDataTransferToBranchStatus ::END");
	}
	
}
