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
 * The Class OutboundCentralConsignmentDataProcessor.
 * 
 * @author narmdr
 */
public class OutboundCentralConsignmentDataProcessor {

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(OutboundCentralConsignmentDataProcessor.class);
	
	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	
	/** The outbound central consignment data sync service. */
	private transient OutboundCentralConsignmentDataSyncService outboundCentralConsignmentDataSyncService;
	

	/**
	 * @param outboundCentralConsignmentDataSyncService the outboundCentralConsignmentDataSyncService to set
	 */
	public void setOutboundCentralConsignmentDataSyncService(
			OutboundCentralConsignmentDataSyncService outboundCentralConsignmentDataSyncService) {
		this.outboundCentralConsignmentDataSyncService = outboundCentralConsignmentDataSyncService;
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
	 * so that data can be served on demand.
	 */
	@SuppressWarnings("unchecked")
	public void proceedOutbountDataPreparation() throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_CONSIGNMENT);//getting for outboundConfig.prop
			LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						LOGGER.error("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}
		} 
		LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	/**
	 * Proceed office specific process.
	 *
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty()){
			LOGGER.warn("OutboundCentralConsignmentDataProcessor::proceedDataSyncForOffice::configProcess is empty, hence it's exiting");
			return;
		}
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
				outboundCentralConsignmentDataSyncService.proceedOutboundProcess(configOffice, configTo);
			} catch (IOException e) {
				LOGGER.error("OutboundCentralConsignmentDataProcessor::proceedDataSyncForOffice::error======>" , e);
			}
		}
		LOGGER.debug("OutboundCentralConsignmentDataProcessor::proceedDataSyncForOffice::end=====>");
	}

}
