package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundMasterDataPropertyReader;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class OutboundMasterDataProcessor {
	private static final String FROM = "FROM ";
	private static final String WHERE = " WHERE dtToBranch='N'";

	/**
	 * Helper class to create http request
	 */
	private HttpRequestUtil requestUtil;
	
	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger logger = LoggerFactory.getLogger(OutboundMasterDataProcessor.class);
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountMasterDataPreparation() throws HttpException, ClassNotFoundException, IOException {
		logger.info("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::start=====>");
		//Getting list of offices which has configured<> for data transfer
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::configured Offices=====>" + configOffices);
		//Getting all the masters which has configured<MasterDataConfig.properties> for synchronization 
		List<OutboundConfigPropertyTO> configProcess = OutboundMasterDataPropertyReader.getMasterDataConfigProperty();
		logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::configured Processes=====>" + configProcess);
		if(configProcess != null && !configProcess.isEmpty() && configOffices != null && !configOffices.isEmpty()) {
			Set<Integer> officeIds = new HashSet<Integer>(configOffices.size());
			for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
				officeIds.add(configOffice.getOutboundOffice().getOfficeId());
			}
			for(OutboundConfigPropertyTO configTO :configProcess) {
				try {
					if(configTO.getDoName() == null)
						continue;
					List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOutboundMasterDataFromDB(prepareQueryFromDO(configTO.getDoName()), configTO.getMaxRowCount());
					logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::available data for[ " +configTO.getPropKey() + " ] :: " + baseList.size());
					if(baseList != null  && !baseList.isEmpty()) {
						logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + "for all offices started...");
						//Getting list of processes for which data has to be proceed
						try {
							logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::starting zip file creation and storing to DB for process: " + configTO.getPropKey());
							bcunService.prepareNStoreZipFile(baseList,officeIds, configTO.getPropKey());
							logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::ending zip file creation and storing to DB for process: " + configTO.getPropKey());
							//bcunService.updateTransferedStatus(baseList);
						} catch (Exception ex) {
							logger.error("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::error::office::"+ configTO.getPropKey(), ex);
						}
						logger.debug("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + "for all offices completed...");
					} else{
						logger.debug("OutboundMasterDataWeeklyProcessor::proceedOutbountMasterDataPreparation::processing of " + configTO.getPropKey() + ": No Records Exisits");
					}
				} catch (Exception ex) {
					logger.error("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::error::process::"+configTO.getPropKey() , ex);
				}
				
			}
		}else{
			logger.warn("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::either no process is configured or no office is configured");
		}
		
		logger.info("OutboundMasterDataProcessor::proceedOutbountMasterDataPreparation::end=====>");
	}
	
	private String prepareQueryFromDO(String doName) {
		return FROM + doName + WHERE;
	}
	
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}

	public void setRequestUtil(HttpRequestUtil requestUtil) {
		this.requestUtil = requestUtil;
	}
}
