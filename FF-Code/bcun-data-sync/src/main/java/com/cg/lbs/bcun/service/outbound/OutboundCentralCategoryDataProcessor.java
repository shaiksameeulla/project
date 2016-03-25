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
public class OutboundCentralCategoryDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger logger = LoggerFactory.getLogger(OutboundCentralCategoryDataProcessor.class);
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountDataPreparation(String outboundOfficeCategory) throws HttpException, ClassNotFoundException, IOException {
		logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::Outbound Office Category : " + outboundOfficeCategory + " ::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQueryAndNamedParam(
				BcunConstant.QUERY_GET_ALL_ACTIVE_DATA_SYNC_OFFICE_BY_CATEGORY,BcunConstant.PARAM_OUTBOUND_OFFICE_CATEGORY,outboundOfficeCategory);
		if(configOffices != null  && !configOffices.isEmpty()) {
			logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_OTHERS);//getting for outboundConfig.prop
			logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						logger.error("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}
		} 
		logger.debug("OutboundCentralCategoryDataProcessor::proceedOutbountDataPreparation::Outbound Office Category : " + outboundOfficeCategory + " ::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		logger.debug("OutboundCentralCategoryDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
				bcunService.proceedOutboundProcess(configOffice, configTo);
			} catch (IOException e) {
				logger.error("OutboundCentralCategoryDataProcessor::proceedDataSyncForOffice::error======>" , e);
			}
		}
		logger.debug("OutboundCentralCategoryDataProcessor::proceedDataSyncForOffice::end=====>");
	}

	/**
	 * 
	 * @param configOffice
	 * @param configProcess
	 * @throws IOException
	 */
	/*private void proceedIndividualProcess(OutboundDatasyncConfigOfficeDO configOffice, OutboundConfigPropertyTO configProcess) throws IOException {
		List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			logger.debug("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::");
			String jsonObject = bcunService.getJSONFromBaseDOList(baseList);
			logger.trace("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String fileLocation =bcunService.getProcessFileLocation();
			logger.trace("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
			long currentTime = System.currentTimeMillis();
			String xmlFileName = fileLocation + File.separator + configProcess.getProcess()+ "-" + currentTime + ".xml";
			File xmlFIle = new File(xmlFileName);
			logger.trace("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = bcunService.writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = fileLocation + File.separator + configProcess.getProcess()+ "-" + currentTime + ".zip";
			logger.debug("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = bcunService.createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					logger.debug("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::unable to create zip file");
				} else {
					OutboundDatasyncDataDO branchData = getOutboundDatasyncDataFromZipFile(zipFileName, blob, configOffice.getOutboundOffice().getOfficeId(), configProcess);
					bcunService.saveOrUpdateTransferedEntity(branchData);
					Integer reportingHubId = configOffice.getOutboundOffice().getReportingHUB();
					if(reportingHubId!=null){
						OutboundDatasyncDataDO hubData = getOutboundDatasyncDataFromZipFile(zipFileName, blob, reportingHubId, configProcess);
						bcunService.saveOrUpdateTransferedEntity(hubData);
					}
					updateTransferedStatus(baseList);
				}
			} else {
				logger.debug("OutboundCentralCategoryDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}
	}*/
	
	
	/*private OutboundDatasyncDataDO getOutboundDatasyncDataFromZipFile(String zipFileName, byte[] blob, Integer configOffice, OutboundConfigPropertyTO configProcess) {
		OutboundDatasyncDataDO dataDO = new OutboundDatasyncDataDO(); 
		OfficeDO officeDo = new OfficeDO();
		officeDo.setOfficeId(configOffice);
		dataDO.setOutboundOffice(officeDo);
		dataDO.setExtractedData(blob);
		dataDO.setExtractedDate(new Date());
		dataDO.setProcessName(configProcess.getProcess());
		return dataDO;
	}
	
	private void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			bcunService.updateEntityStatus(baseDO);
		}
		
	}*/
	
	
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
}
