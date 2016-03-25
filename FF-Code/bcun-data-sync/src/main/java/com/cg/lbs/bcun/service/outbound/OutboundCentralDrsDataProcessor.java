/*
 * 
 */
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
 * @author mohammes
 *
 */
public class OutboundCentralDrsDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(OutboundCentralDrsDataProcessor.class);
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountDataPreparation()  throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_DRS);//getting for outboundConfig.prop
			LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						LOGGER.error("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				LOGGER.warn("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation:: Processes are not configured=====>");
			}
		} else {
			LOGGER.warn("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation:: Office are not configured=====>");
		}
		LOGGER.debug("OutboundCentralDrsDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		LOGGER.debug("OutboundCentralDrsDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo:configProcess) {
			try {
				bcunService.proceedOutboundProcess(configOffice, configTo);
			} catch (Exception e) {
				LOGGER.error("OutboundCentralDrsDataProcessor::proceedDataSyncForOffice::error======>" , e);
			}
		}
		LOGGER.debug("OutboundCentralDrsDataProcessor::proceedDataSyncForOffice::end=====>");
	}

	
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
}
