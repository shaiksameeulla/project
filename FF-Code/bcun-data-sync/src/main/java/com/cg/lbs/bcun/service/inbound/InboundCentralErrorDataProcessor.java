package com.cg.lbs.bcun.service.inbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.utility.InboundPropertyReader;

/**
 * @author mohammal
 * Feb 12, 2013
 * Process out bound branch data
 */
public class InboundCentralErrorDataProcessor {

	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCentralErrorDataProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processCentraldata() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.info("InboundCentralErrorDataProcessor::processBranchdata::process temp folder start=====>");
		insertFileDataIntoDatabase();
		LOGGER.info("InboundCentralErrorDataProcessor::processBranchdata::process temp folder end=====>");
	}
	
	/**
	 * 
	 */
	private void insertFileDataIntoDatabase() {
		//Getting file location
		String reProcessLoc = bcunService.getReprocessFileLocation();
		LOGGER.debug("InboundCentralErrorDataProcessor::insertFileDataIntoDatabase::processing temp file from location[" + reProcessLoc + "]");
		String[] tempFileNames = bcunService.getAllTempFilesNames();
		List<BcunFileSequenceTO> fileList = getFileListInSequence(tempFileNames, reProcessLoc);
		if(fileList != null && !fileList.isEmpty()){
			bcunService.processFileAndUploadToDatabase(fileList, reProcessLoc);
		}else{
			LOGGER.info("InboundCentralErrorDataProcessor::insertFileDataIntoDatabase:: No file is exist at temp location");
		}
	}
	
	
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames, String fileLocation) {
		LOGGER.debug("InboundCentralErrorDataProcessor::getFileListInSequence::getting temp files in sequence starts...");
		if(fileNames == null || fileNames.length == 0){
			LOGGER.warn("InboundCentralErrorDataProcessor::getFileListInSequence::No file exist for the location :["+fileLocation+"] fileNames.length :["+fileNames.length+"]");
			return null;
		}
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>(fileNames.length);
		for(String fileName : fileNames) {
			boolean canProceed = bcunService.canProceedFile(fileName);
			LOGGER.info("InboundCentralErrorDataProcessor::getFileListInSequence::considering temp file[" + fileName  + "] for processing [" + canProceed + "]");
			if(fileName.contains("Inbound") && canProceed) {
				String processName = bcunService.getProcessName(fileName);
				BcunConfigPropertyTO confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
				int sequence = confTo.getSequence();
				BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
				sequenceTO.setSequenceNumber(100 + sequence);
				sequenceTO.setFileName(fileName);
				fileList.add(sequenceTO);
				
			} else if (fileName.contains(BcunConstant.TWO_WAY_WRITE) && canProceed) {
				//TwoWayWrite Temp file process start here
				LOGGER.info("InboundCentralErrorDataProcessor::getFileListInSequence::TwoWayWrite temp file : [" + fileName  + "] processing start.");
				bcunService.readFileAndInsertTo2WayQueue(fileName, fileLocation);
				
			}else {
				LOGGER.debug("InboundCentralErrorDataProcessor::getFileListInSequence::temp file[" + fileName  + "] cannot be processed due to invalid name or timestamp in the name");
			}
		}
		LOGGER.debug("InboundCentralErrorDataProcessor::getFileListInSequence::processing available number of files in temp [" + fileList.size() + "]");
		Collections.sort(fileList);
		LOGGER.debug("InboundCentralErrorDataProcessor::getFileListInSequence::available temp files for processing");
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
