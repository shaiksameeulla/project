package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.outbound.officefinder.ManifestOutboundOfficeFinderServiceImpl;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;
import com.ff.domain.consignment.ConsignmentDO;


/**
 * The Class OutboundCentralConsignmentDataSyncServiceImpl.
 *
 * @author narmdr
 * 
 */
public class OutboundCentralConsignmentDataSyncServiceImpl implements OutboundCentralConsignmentDataSyncService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutboundCentralConsignmentDataSyncServiceImpl.class);

	/** The bcun service. */
	private transient BcunDatasyncService bcunService;

	/**
	 * @param bcunService the bcunService to set
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.outbound.OutboundCentralConsignmentDataSyncService#proceedOutboundProcess(com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO, com.cg.lbs.bcun.to.OutboundConfigPropertyTO)
	 */
	public void proceedOutboundProcess(
			OutboundDatasyncConfigOfficeDO configOffice,
			OutboundConfigPropertyTO configProcess) throws IOException {
		LOGGER.debug("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "Start...");
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			Integer branchId = configOffice.getOutboundOffice().getOfficeId();
			//String officeSenderClass = configProcess.getOfficesFinder();
		 	/*
			  Consignment will travel to following locations :-
				A.	All hubs and branches of source/origin city.
				B.	All hubs and branches of destination city.
			*/

			Set<Integer> branchesHubsOfficeIds =  null;
			
			try {
				/*
				  A. For Destination City : Consignment will travel to following locations :-
					 1. All Branches and Hubs of destCity
				 */
				
				//A.1. All Branches and Hubs of destCity
				//get All Branches and Hubs of destCity
				branchesHubsOfficeIds =  ManifestOutboundOfficeFinderServiceImpl.getAllBranchesAndHubsOftheBranchCity(bcunService, branchId);
				
				if(!StringUtil.isEmptyColletion(branchesHubsOfficeIds)) {
					LOGGER.debug("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Destination Office Id :["+branchId+"] Found all hubs office Details of destination city :["+branchesHubsOfficeIds.toString()+"]");
					bcunService.prepareNStoreZipFile(baseList, branchesHubsOfficeIds, configProcess.getPropKey());
				}
				
				/*
				  B. For Origin City : Consignment will travel to following locations :-
					 1.  All Branches and Hubs of origin City
				 */
				
				/*
				 * 2.Filter out All different origin/source officeIds & then
				 * 	2a. List out all DOs related to that origin/source officeId
				 * 	2b. Get All Branches and Hubs officeIds of origin/source officeId . 
				 * 		& then create prepareNStoreZipFile with list of branchesHubsOfficeIds of origin City.
				 */
				
				Set<Integer> originOfficeIds =  new HashSet<>();
				//Create unique list of all different origin office
				for (CGBaseDO cgBaseDO : baseList) {
					ConsignmentDO consignmentDO = (ConsignmentDO) cgBaseDO;
					if(!StringUtil.isEmptyInteger(consignmentDO.getOrgOffId())){
						originOfficeIds.add(consignmentDO.getOrgOffId());
					}
				}
				
				if(!StringUtil.isEmptyColletion(originOfficeIds)) {
					for (Integer originOfficeId : originOfficeIds) {
						List<CGBaseDO> originBaseList = new ArrayList<>();
						for (CGBaseDO cgBaseDO : baseList) {
							ConsignmentDO consignmentDO = (ConsignmentDO) cgBaseDO;
							if(!StringUtil.isEmptyInteger(consignmentDO.getOrgOffId())
									&& originOfficeId.equals(consignmentDO.getOrgOffId())){								
								originBaseList.add(cgBaseDO);
							}
						}

						branchesHubsOfficeIds =  ManifestOutboundOfficeFinderServiceImpl.getAllBranchesAndHubsOftheBranchCity(bcunService, originOfficeId);
						if(!StringUtil.isEmptyColletion(branchesHubsOfficeIds)) {
							if(!StringUtil.isEmptyColletion(branchesHubsOfficeIds)) {
								LOGGER.debug("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Origin Office Id :["+originOfficeId+"] Found all hubs office Details of origin city :["+branchesHubsOfficeIds.toString()+"]");
								bcunService.prepareNStoreZipFile(originBaseList, branchesHubsOfficeIds, configProcess.getPropKey());
							}
						}
					}
				}
				
			} catch (Exception e) {
				LOGGER.error("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess::EXCEPTION :: ",e);
				throw e;
			}
			LOGGER.debug("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "for all offices completed...");
			//udateDataTransferToBranchStatus(bcunService, baseList); moved to prepareNstoreZipFile
		}
		LOGGER.debug("OutboundCentralConsignmentDataSyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "End...");
	}
}
