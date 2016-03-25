package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
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
public class OutboundCentralUmcDataProcessor {

	/**
	 * out bound specific BCUN service implementation object
	 */
	private BcunDatasyncService bcunService;

	/**
	 * Logger to log the process specific messages
	 */
	private final static Logger logger = LoggerFactory.getLogger(OutboundCentralUmcDataProcessor.class);
	
	/**
	 * prepare out bound process data and put into table
	 * so that data can be served on demand
	 */
	public void proceedOutbountDataPreparation() throws HttpException, ClassNotFoundException, IOException{
		logger.info("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::start=====>");
		//Getting list of offices for which data has to be transfered
		List<OutboundDatasyncConfigOfficeDO> configOffices =  (List<OutboundDatasyncConfigOfficeDO>)bcunService.getDataByNamedQuery("getAllActiveDatasyncOffice");
		if(configOffices != null  && !configOffices.isEmpty()) {
			logger.debug("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::processing for " + configOffices.size() + " offices");
			//Getting list of processes for which data has to be proceed
			List<OutboundConfigPropertyTO> configProcess = OutboundPropertyReader.getOutBoundPropertyListByProcess(BcunConstant.OUTBOUND_PROCESS_NAME_UMC);//getting for outboundConfig.prop
			logger.debug("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::processing for " + configProcess.size() + " processes for every offices");
			if(configProcess != null && !configProcess.isEmpty()) {
				for(OutboundDatasyncConfigOfficeDO configOffice : configOffices) {
					try {
						logger.debug("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::processing start for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
						//Processing office specific data
						proceedDataSyncForOffice(configOffice, configProcess);
						logger.debug("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::processing end for [" + configOffice.getOutboundOffice().getOfficeName() + "] office");
					} catch (Exception ex) {
						logger.error("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::processing error for [" + configOffice.getOutboundOffice().getOfficeName() + "] office",ex);
					}
				}
			}else{
				logger.info("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::No process is configured=====>");
			}
		} else {
			logger.warn("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::No office is configured=====>");
		}
		logger.info("OutboundCentralUmcDataProcessor::proceedOutbountDataPreparation::end=====>");
	}
	
	/**
	 * Proceed office specific process
	 * @param configOffice for which process has to be proceed
	 * @param configProcess list of configured processes
	 */
	private void proceedDataSyncForOffice(OutboundDatasyncConfigOfficeDO configOffice, List<OutboundConfigPropertyTO> configProcess) {
		logger.debug("OutboundCentralUmcDataProcessor::proceedDataSyncForOffice::start=====>");
		if(configOffice == null || configProcess == null || configProcess.isEmpty())
			return;
		for(OutboundConfigPropertyTO configTo : configProcess) {
			try {
					bcunService.proceedOutboundProcess(configOffice, configTo);
			} catch (Exception e) {
				logger.error("OutboundCentralUmcDataProcessor::proceedDataSyncForOffice::error======>" , e);
			}
		}
		logger.debug("OutboundCentralUmcDataProcessor::proceedDataSyncForOffice::end=====>");
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
			logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::");
			String jsonObject = bcunService.getJSONFromBaseDOList(baseList);
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String fileLocation =bcunService.getProcessFileLocation();
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
			long currentTime = System.currentTimeMillis();
			String xmlFileName = fileLocation + File.separator + configProcess.getProcess()+ "-" + currentTime + ".xml";
			File xmlFIle = new File(xmlFileName);
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = bcunService.writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = fileLocation + File.separator + configProcess.getProcess()+ "-" + currentTime + ".zip";
			logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = bcunService.createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::unable to create zip file");
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
				logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}
	}*/
	
	/*public void prepareNStoreZipFile(List<CGBaseDO> baseList, Integer office, String processName) throws IOException {
		//List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::");
			String jsonObject = bcunService.getJSONFromBaseDOList(baseList);
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String fileLocation =bcunService.getProcessFileLocation();
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
			long currentTime = System.currentTimeMillis();
			String xmlFileName = fileLocation + File.separator + processName+ "-" + currentTime + ".xml";
			File xmlFIle = new File(xmlFileName);
			logger.trace("OutboundCentralUmcDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = bcunService.writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = fileLocation + File.separator + processName + "-" + currentTime + ".zip";
			logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = bcunService.createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::unable to create zip file");
				} else {
					OutboundDatasyncDataDO branchData = getOutboundDatasyncDataFromZipFile(zipFileName, blob, office, processName);
					
				}
			} else {
				logger.debug("OutboundCentralUmcDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}
	}
	*/
	/*private OutboundDatasyncDataDO getOutboundDatasyncDataFromZipFile(String zipFileName, byte[] blob, OutboundConfigPropertyTO configProcess) {
		OutboundDatasyncDataDO dataDO = new OutboundDatasyncDataDO(); 
		dataDO.setExtractedData(blob);
		dataDO.setExtractedDate(new Date());
		dataDO.setProcessName(configProcess.getProcess());
		dataDO.setIsTransfered("N");
		return dataDO;
	}
	*/
	
	/*private OutboundDatasyncDataDO getOutboundDatasyncDataFromZipFile(String zipFileName, byte[] blob, Integer configOffice, String processName) {
		OutboundDatasyncDataDO dataDO = new OutboundDatasyncDataDO(); 
		OfficeDO officeDo = new OfficeDO();
		officeDo.setOfficeId(configOffice);
		dataDO.setOutboundOffice(officeDo);
		dataDO.setExtractedData(blob);
		dataDO.setExtractedDate(new Date());
		dataDO.setProcessName(processName);
		dataDO.setIsTransfered("N");
		return dataDO;
	}*/
	
	private void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			bcunService.saveOrUpdateTransferedEntity(baseDO);
		}
		
	}
	
	
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
}
