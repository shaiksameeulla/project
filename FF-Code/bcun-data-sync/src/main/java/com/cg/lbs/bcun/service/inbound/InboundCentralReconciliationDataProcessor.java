package com.cg.lbs.bcun.service.inbound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.utility.ReconcillationPropertyReader;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;

/**
 * @author bmodala
 * Aug 24, 2015
 * Process data for in bound central office/server
 */
public class InboundCentralReconciliationDataProcessor {
	/**
	 * Logger to log the message
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCentralReconciliationDataProcessor.class);

	/**
	 * BCUN service used to process data. 
	 */
	private BcunDatasyncService bcunService;  

	public void proceedFileProcessing() throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("InboundCentralReconciliationDataProcessor::proceedFileProcessing::starts======>");
		//Reading file location
		String fileLocation = bcunService.getBaseFileLocation();
		LOGGER.debug("InboundCentralReconciliationDataProcessor::proceedFileProcessing::fileLocation======>" + fileLocation);
		String[] fileNames = com.capgemini.lbs.framework.utils.FileUtils.getAllFilesNames(fileLocation, "bcunInbound");
		List<BcunFileSequenceTO> fileList = getFileListInSequence(fileNames);
		if(fileList != null && !fileList.isEmpty()){
			processFileAndUploadToDatabase(fileList, fileLocation);
		}else{
			LOGGER.info("InboundCentralReconciliationDataProcessor::No file exists at fileLocation======>" + fileLocation);
		}
		LOGGER.debug("InboundCentralReconciliationDataProcessor::proceedFileProcessing::end======>");
	}
	//beesu 
	private void processFileAndUploadToDatabase(List<BcunFileSequenceTO> fileList, String baseLocation) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::START#####");
		if(fileList != null && !fileList.isEmpty()) {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::total file has to be proceed=" + fileList.size());
			for(BcunFileSequenceTO fileTo : fileList) {
				//boolean isInboundFile = fileName.startsWith("DataSync-Outbound-");
				String fileName = fileTo.getFileName();
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::Processing File :["+fileName+"]");
				//boolean isReProcessFiles = fileName.contains(RE_PROCESS_FLAG_NAME);
				readFileAndUpdateDB(fileName, baseLocation);
				
			}
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::END#####");
	}

	@SuppressWarnings("unchecked")
	protected void readFileAndUpdateDB(String fileName, String baseLocation) {
		try {
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::processing file name: " + fileName+"Started");	
			//Getting process name based on file name
			String processName = bcunService.getProcessName(fileName);
			//Reading in bound properties entry 
			//String className = this.getClass().getName();
			//reconcillation starts
			boolean isReconcileData =processName.contains("Reconcillation");
			//reconcillation ends
			ReconcillationConfigPropertyTO confTo = null;  
			if(isReconcileData){
				confTo = ReconcillationPropertyReader.getReconcillationConfigPropertyByEachPropertyKey(processName);
			}
			//Reading process data from file
			String filePath = baseLocation + File.separator + fileName; //getFilePath(fileName, baseLocation);
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::filePath:["+filePath+"]");
			File processFile = new File(filePath);
			List<CGBaseDO> baseList = (List<CGBaseDO>)bcunService.getProcessDOListFromFile(processFile, confTo.getDoName());
			LOGGER.debug("OutboundBranchReconcileDataProcessor::proceedFileProcessing::updating db for file: " + fileName);
			//Getting configured formatter class name 
			//updating branch count on reconcillation transaction table
			for (CGBaseDO cgBaseDO : baseList) {
				BcunReconcillationDO reconcileDO =(BcunReconcillationDO)cgBaseDO;		
				try {
					reconcileDO.setDataReconcillationId(null);
					bcunService.prepareReconcillationStatisticsForBranchData(reconcileDO);
				} catch (Exception e) {
					LOGGER.error("OutboundBranchReconcileDataProcessor::proceedFileProcessing::moved to er : EXCEPTION", e);
				}
			}	
			bcunService.renameFile(fileName, baseLocation, "pr");			
		} catch (Exception ex) {
			LOGGER.error("OutboundBranchReconcileDataProcessor::proceedFileProcessing::moved to er : EXCEPTION", ex);
		}
	}
	//beesu	
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;	
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		for(String fileName : fileNames) {
			if(fileName.contains("Reconcillation") && !fileName.startsWith("pr")&& !fileName.startsWith("er")) {
				try {
					String processName = bcunService.getProcessName(fileName);
					ReconcillationConfigPropertyTO confTo = ReconcillationPropertyReader.getReconcillationConfigPropertyByEachPropertyKey(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("InboundCentralReconciliationDataProcessor::getFileListInSequence:: Exception...",ex);
				}			
			}
		}
		Collections.sort(fileList);
		return fileList;
	}
	/** 
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
}
