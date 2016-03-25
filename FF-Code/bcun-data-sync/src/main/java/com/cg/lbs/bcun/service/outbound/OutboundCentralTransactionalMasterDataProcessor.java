package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;

/**
 * The Class OutboundCentralTransactionalMasterDataProcessor.
 * 
 * @author narmdr
 */
public class OutboundCentralTransactionalMasterDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger logger = LoggerFactory.getLogger(OutboundCentralTransactionalMasterDataProcessor.class);
	/*
	*//**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 *//*
	@Deprecated
	public void proceedOutbountDataPreparation() throws HttpException, ClassNotFoundException, IOException{
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_TRANSACTIONAL_MASTER);//getting for outboundConfig.prop
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						logger.error("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				logger.info("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::no office is configured ");
			}
		} 
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	*//**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 *//*
	@Deprecated
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
				bcunService.proceedOutboundProcess(configOffice, configTo);
			} catch (IOException e) {
				logger.error("OutboundCentralTransactionalMasterDataProcessor::proceedDataSyncForOffice::error======>" + e.getMessage());
			}
		}
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedDataSyncForOffice::end=====>");
	}*/

	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	public void proceedOutbountTransactionMasterDataPreparation() throws HttpException, ClassNotFoundException, IOException{
		logger.info("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::start=====>");
		//Getting list of offices which has configured<> for data transfer
		List <Integer> officeIds=(List <Integer>)bcunService.getNumberByNamedQuery("getAllActiveDatasyncOfficeIDList");
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::configured Offices=====>" + officeIds);
		
		if(!CollectionUtils.isEmpty(officeIds)){
			Set<Integer> destinationOfficeids=new HashSet<Integer>(officeIds);
			//Getting all the Transaction masters which has configured<OutboundConfig.properties> for synchronization 
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_TRANSACTIONAL_MASTER);//getting for outboundConfig.prop
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::configured Processes=====>" + configProcess);
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundConfigPropertyTO configTO :configProcess) {
					try {
						prepareBlobForDestinationOffices(destinationOfficeids,
								configTO);
					} catch (Exception ex) {
						logger.error("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::error::process::"+configTO.getPropKey() , ex);
					}
				}
			} else{
				logger.warn("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::NO  Processes configured");
			}
		}else{
			logger.warn("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::NO  Office is configured");
		}
		logger.info("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::end=====>");
	}

	private void prepareBlobForDestinationOffices(
			Set<Integer> destinationOfficeids, OutboundConfigPropertyTO configTO) {
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndRowCount(configTO.getNamedQuery(), configTO.getMaxRowCount());
		logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::available data for[ " +configTO.getPropKey() + " ] :: " + baseList.size());
		boolean isForContract=false;
		if(!StringUtil.isStringEmpty(configTO.getPropKey()) && configTO.getDoName().contains("RateContractDO")){
			isForContract=true;
		}
		if(baseList != null  && !baseList.isEmpty()) {
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + "for all offices started...");
			//Getting list of processes for which data has to be proceed
			try {
				logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::starting zip file creation and storing to DB for process: " + configTO.getPropKey());
				bcunService.prepareNStoreZipFile(baseList,destinationOfficeids, configTO.getPropKey(),isForContract);
				logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::ending zip file creation and storing to DB for process: " + configTO.getPropKey());
			} catch (Exception ex) {
				logger.error("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::error::office::"+ configTO.getPropKey(), ex);
			}
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + "for all offices completed...");
		} else{
			logger.debug("OutboundCentralTransactionalMasterDataProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + ": No Records Exisits");
		}
	}
	
	
}
