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
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class OutboundCentralPickupDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(OutboundCentralPickupDataProcessor.class);
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountDataPreparation() throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_PICKUP);//getting for outboundConfig.prop
			LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						LOGGER.error("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				LOGGER.warn("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::process(es) are not configured=====>");
			}
		} else {
			LOGGER.warn("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::office(s) are not configured=====>");
		}
		LOGGER.debug("OutboundCentralPickupDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		LOGGER.debug("OutboundCentralPickupDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
					bcunService.proceedOutboundProcess(configOffice, configTo);
			} catch (IOException e) {
				LOGGER.error("OutboundCentralPickupDataProcessor::proceedDataSyncForOffice::error======>" , e);
			}
		}
		LOGGER.debug("OutboundCentralPickupDataProcessor::proceedDataSyncForOffice::end=====>");
	}

	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
}
