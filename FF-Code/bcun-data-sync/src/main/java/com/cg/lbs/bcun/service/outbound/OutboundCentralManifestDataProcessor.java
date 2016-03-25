package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.outbound.officefinder.OutboundOfficeFinderService;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class OutboundCentralManifestDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(OutboundCentralManifestDataProcessor.class);
	
	/** The outbound central manifest data sync service. */
	private transient OutboundCentralManifestDataSyncService outboundCentralManifestDataSyncService;
	
	/**
	 * @param outboundCentralManifestDataSyncService the outboundCentralManifestDataSyncService to set
	 */
	public void setOutboundCentralManifestDataSyncService(
			OutboundCentralManifestDataSyncService outboundCentralManifestDataSyncService) {
		this.outboundCentralManifestDataSyncService = outboundCentralManifestDataSyncService;
	}

	/**
	 * Sets the bcun service.
	 *
	 * @param bcunService the new bcun service
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountDataPreparation() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_MANIFEST);//getting for outboundConfig.prop
			LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						LOGGER.error("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				LOGGER.warn("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::There are no Processes mapped");
			}
		} else {
			LOGGER.warn("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::There are no offices mapped");
		}
		LOGGER.debug("OutboundCentralManifestDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		LOGGER.debug("OutboundCentralManifestDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
				if(configTo.getPropKey().equals("createManifest")){
					//proceedManifestOutboundProcess(configOffice, configTo);//new approach
					outboundCentralManifestDataSyncService.proceedOutboundProcess(configOffice, configTo);
				} else {
					bcunService.proceedOutboundProcess(configOffice, configTo);//RTH/RTO
				}
			} catch (IOException e) {
				LOGGER.error("OutboundCentralManifestDataProcessor::proceedDataSyncForOffice::error======>" + e.getMessage());
			}
		}
		LOGGER.debug("OutboundCentralManifestDataProcessor::proceedDataSyncForOffice::end=====>");
	}


	/**
	 * 
	 * @param configOffice
	 * @param configProcess
	 * @throws IOException
	 */
	private void proceedIndividualProcess(OutboundDatasyncConfigOfficeDO configOffice, OutboundConfigPropertyTO configProcess) throws IOException {
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			Integer branchId = configOffice.getOutboundOffice().getOfficeId();
			Set<Integer> officeIds =  null;
			String officeSenderClass = configProcess.getOfficesFinder();
			if(officeSenderClass != null) {
				try {
					OutboundOfficeFinderService officeFinder =  (OutboundOfficeFinderService)Class.forName(officeSenderClass).newInstance();
					officeIds =  officeFinder.getAllOutboundOffices(branchId, bcunService, baseList);
				} catch (Exception e) {
					LOGGER.error("OutboundCentralManifestDataProcessor::proceedIndividualProcess::error======>",e);
				}
			}
			if(officeIds != null && !officeIds.isEmpty()) {
				officeIds.add(branchId);
				bcunService.prepareNStoreZipFile(baseList, officeIds, configProcess.getPropKey());
			} else {
				bcunService.prepareNStoreZipFile(baseList, branchId, configProcess.getPropKey());
			}
			
		}
	}
	
}
