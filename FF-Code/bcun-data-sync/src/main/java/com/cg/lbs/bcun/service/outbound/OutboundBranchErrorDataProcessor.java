package com.cg.lbs.bcun.service.outbound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.utility.OutboundMasterDataPropertyReader;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;

/**
 * @author mohammal
 * Feb 12, 2013
 * Process out bound branch data
 */
public class OutboundBranchErrorDataProcessor {

	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchErrorDataProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundBranchErrorDataProcessor::processBranchdata::start=====>");
		insertFileDataIntoDatabase();
		LOGGER.debug("OutboundBranchErrorDataProcessor::processBranchdata::end=====>");
	}
	
	/**
	 * 
	 */
	private void insertFileDataIntoDatabase() {
		//Processing file and uploading it to central
		String[] tempFileNames = bcunService.getAllTempFilesNames();
		String reProcessLoc = bcunService.getReprocessFileLocation();
		List<BcunFileSequenceTO> fileList = getFileListInSequence(tempFileNames);
		if(fileList != null && !fileList.isEmpty())
			bcunService.processFileAndUploadToDatabase(fileList, reProcessLoc);
	}
	
	
	
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;
		
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		for(String fileName : fileNames) {
			File file = new File(fileName);
			boolean canProceed = bcunService.canProceedFile(fileName);
			if(file.isDirectory()) {
				continue;
			} else if(fileName.contains("Master") && canProceed) {
				try {
					String processName = bcunService.getProcessName(fileName);
					BcunConfigPropertyTO confTo = OutboundMasterDataPropertyReader.getMasterConfigPropertyByProcessName(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("OutboundBranchErrorDataProcessor::getFileListInSequence::Exception=====>",ex);
				}
				
			} else if(fileName.contains("Outbound") && canProceed) {
				String processName = bcunService.getProcessName(fileName);
				BcunConfigPropertyTO confTo = OutboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
				int sequence = confTo.getSequence();
				BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
				sequenceTO.setSequenceNumber(100 + sequence);
				sequenceTO.setFileName(fileName);
				fileList.add(sequenceTO);
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
