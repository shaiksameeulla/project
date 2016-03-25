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
import com.cg.lbs.bcun.utility.OutboundPropertyReader;

/**
 * @author mohammal
 * Feb 12, 2013
 * Process out bound branch data
 */
public class OutboundBranchTransDataProcessor {

	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchTransDataProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		LOGGER.debug("OutboundBranchTransDataProcessor::processBranchdata::start=====>");
		insertFileDataIntoDatabase();
		LOGGER.debug("OutboundBranchTransDataProcessor::processBranchdata::end=====>");
	}
	
	/**
	 * 
	 */
	private void insertFileDataIntoDatabase() {
		//Getting file location
		LOGGER.trace("OutboundBranchTransDataProcessor::processBranchdata::insertFileDataIntoDatabase::START=====>");
		String fileLocation = bcunService.getBaseFileLocation();
		//Processing file and uploading it to central
		LOGGER.trace("OutboundBranchTransDataProcessor::processBranchdata::insertFileDataIntoDatabase::fileLocation=====>"+fileLocation);
		String[] fileNames = bcunService.getAllFilesNames(fileLocation);
		List<BcunFileSequenceTO> fileList = getFileListInSequence(fileNames);
		if(fileList != null && !fileList.isEmpty()){
			LOGGER.trace("OutboundBranchTransDataProcessor::processBranchdata::insertFileDataIntoDatabase::File List=====>"+fileList);
			bcunService.processFileAndUploadToDatabase(fileList, fileLocation);
		}else{
			LOGGER.warn("####### OutboundBranchTransDataProcessor::processBranchdata::insertFileDataIntoDatabase::No files process #########");
		}
		LOGGER.trace("OutboundBranchTransDataProcessor::processBranchdata::insertFileDataIntoDatabase::END=====>");
	}
	
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;
		
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		for(String fileName : fileNames) {
			if(!fileName.contains("Master") && !fileName.equals("pr")&& !fileName.equals("er")&& !fileName.equals("temp")) {
				try {
					String processName = bcunService.getProcessName(fileName);
					BcunConfigPropertyTO confTo = OutboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("OutboundBranchTransDataProcessor::getFileListInSequence::Exception::",ex);
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
