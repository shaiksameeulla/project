package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;

/**
 * @author narmdr
 * Aug 14, 2014
 * 
 */
public class OutboundCentralManifestCategoryDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(OutboundCentralManifestCategoryDataProcessor.class);
	
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
	public void proceedOutbountDataPreparation(String outboundOfficeCategory) throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::Outbound Office Category : " + outboundOfficeCategory + " ::start=====>");
		
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQueryAndNamedParam(
				BcunConstant.QUERY_GET_ALL_ACTIVE_DATA_SYNC_OFFICE_BY_CATEGORY,BcunConstant.PARAM_OUTBOUND_OFFICE_CATEGORY,outboundOfficeCategory);
		if(configOffices != null  && !configOffices.isEmpty()) {
			LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_MANIFEST);//getting for outboundConfig.prop
			LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess,outboundOfficeCategory);
						LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						LOGGER.error("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				LOGGER.warn("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::There are no processes mapped for Category: " + outboundOfficeCategory);
			}
		} else {
			LOGGER.warn("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::There are no offices mapped for Category: " + outboundOfficeCategory);
		}
		LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedOutbountDataPreparation::Outbound Office Category : " + outboundOfficeCategory + " ::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess,String category) {
		LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
				if(configTo.getPropKey().equals("createManifest")){
					//proceedManifestOutboundProcess(configOffice, configTo);//new approach
					configTo.setCategory(category);
					outboundCentralManifestDataSyncService.proceedOutboundProcess(configOffice, configTo);
				} else {
					bcunService.proceedOutboundProcess(configOffice, configTo);//RTH/RTO
				}
			} catch (IOException e) {
				LOGGER.error("OutboundCentralManifestCategoryDataProcessor::proceedDataSyncForOffice::error======>" + e.getMessage());
			}
		}
		LOGGER.debug("OutboundCentralManifestCategoryDataProcessor::proceedDataSyncForOffice::end=====>");
	}

}
