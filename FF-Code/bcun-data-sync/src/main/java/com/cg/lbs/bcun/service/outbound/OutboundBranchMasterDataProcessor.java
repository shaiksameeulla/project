package com.cg.lbs.bcun.service.outbound;

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

/**
 * @author mohammal
 * Feb 12, 2013
 * Process out bound branch data
 */
public class OutboundBranchMasterDataProcessor {

	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchMasterDataProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundBranchMasterDataProcessor::processBranchdata::start=====>");
		insertFileDataIntoDatabase();
		LOGGER.debug("OutboundBranchMasterDataProcessor::processBranchdata::end=====>");
	}
	
	/**
	 * 
	 */
	private void insertFileDataIntoDatabase() {
		//Getting file location
		String fileLocation = bcunService.getBaseFileLocation();
		LOGGER.trace("OutboundBranchMasterDataProcessor::insertFileDataIntoDatabase::fileLocation=====>"+fileLocation+"###START###");
		//Processing file and uploading it to central
		String[] fileNames = bcunService.getAllFilesNames(fileLocation);
		//String[] tempFileNames = bcunService.getAllTempFilesNames(fileLocation);
		//String[] both = (String[]) ArrayUtils.addAll(fileNames, tempFileNames);
		List<BcunFileSequenceTO> fileList = getFileListInSequence(fileNames);
		if(fileList != null && !fileList.isEmpty()){
			LOGGER.trace("OutboundBranchMasterDataProcessor::insertFileDataIntoDatabase::Files are about to process=====>");
			bcunService.processFileAndUploadToDatabase(fileList, fileLocation);
		}else{
			LOGGER.warn("OutboundBranchMasterDataProcessor::insertFileDataIntoDatabase::Not File Exist=====>");
		}
		LOGGER.trace("OutboundBranchMasterDataProcessor::insertFileDataIntoDatabase::fileLocation=====>"+fileLocation+"###END###");
	}
	
	
	
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;
		
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		LOGGER.trace("OutboundBranchMasterDataProcessor::getFileListInSequence::File List=====>"+fileNames);
		for(String fileName : fileNames) {
			LOGGER.trace("OutboundBranchMasterDataProcessor::getFileListInSequence::File Name=====>"+fileName);
			if(fileName.contains("Master") && !fileName.equals("pr")&& !fileName.equals("er")) {
				try {
					String processName = bcunService.getProcessName(fileName);
					BcunConfigPropertyTO confTo = OutboundMasterDataPropertyReader.getMasterConfigPropertyByProcessName(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("OutboundBranchMasterDataProcessor::getFileListInSequence::Exception=====>",ex);
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
