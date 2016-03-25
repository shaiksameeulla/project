package com.cg.lbs.bcun.service.inbound;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.AbstractBcunDatasyncServiceImpl;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.utility.InboundPropertyReader;
import com.cg.lbs.bcun.utility.QueueProducer;

/**
 * @author mohammal
 * Feb 25, 2013
 * Provides in bound central implementation of BCUN service 
 */
public class InboundCentralServiceImpl extends AbstractBcunDatasyncServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCentralServiceImpl.class);
	private static final String PROCESSED_FOLDER_NAME="pr";
	private static final String ERROR_FOLDER_NAME="er";
	private static final String RE_PROCESS_FOLDER_NAME="temp";
	private static final String RE_PROCESS_FLAG_NAME="RP_";
	
	//private transient InboundCentralDataProcessor inboundCentralDataProcessor;
	
	/**
	 * @param inboundCentralDataProcessor the inboundCentralDataProcessor to set
	 */
	/*public void setInboundCentralDataProcessor(
			InboundCentralDataProcessor inboundCentralDataProcessor) {
		this.inboundCentralDataProcessor = inboundCentralDataProcessor;
	}*/
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getBcunConfigProps()
	 */
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps() {
		return InboundPropertyReader.getInboundConfigProperty();
	}
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps(
			String processName) {
		return InboundPropertyReader.getInBoundPropertyListByProcess(processName);
	}
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getModeOfOpration()
	 */
	@Override
	public String getModeOfOpration() {
		// bcun.operation.mode
		return bcunProperties.getProperty("bcun.operation.mode");
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getProcessFileLocation()
	 */
	@Override
	public String getBaseFileLocation() {
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.central.inbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.central.inbound.file.location");
		}
		FileUtils.createDirectory(fileLocation);
		return fileLocation;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getErrorFileLocation()
	 */
	/*@Override
	public String getErrorFileLocation() {
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.central.inbound.error.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.central.inbound.error.file.location");
		}
		FileUtils.createDirectory(fileLocation);
		return fileLocation;
	}
*/
	@Override
	public void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			saveOrUpdateTransferedEntity(baseDO);
		}
		
	}
	
	
	
	@Override
	public  void processFileAndUploadToDatabase(String fileLocation) {
		//Getting all the files at specified location
		String[] fileNames = getAllFilesNames(fileLocation);
		if(fileNames != null && fileNames.length > 0) {
			LOGGER.trace("InboundCentralServiceImpl::processFileAndUploadToDatabase::total file has to be proceed=" + fileNames.length);
			for(String fileName : fileNames) {
				boolean isInboundFile = fileName.contains("Inbound");
				File processFile = new File(fileLocation + File.separator + fileName); 
				if(processFile.isDirectory() || !isInboundFile)
					continue;
				readFileAndUpdateDB(fileName, fileLocation);
			}
		}
	}
	
	@Override
	public  void processFileAndUploadToDatabase(List<BcunFileSequenceTO> fileList, String baseLocation) {
		LOGGER.trace("InboundCentralServiceImpl::processFileAndUploadToDatabase::###START with baseLocation" + baseLocation);
		if(fileList != null && !fileList.isEmpty()) {
			LOGGER.trace("InboundCentralServiceImpl::processFileAndUploadToDatabase::total file has to be proceed=" + fileList.size());
			for(BcunFileSequenceTO fileTo : fileList) {
				String fileName = fileTo.getFileName();
				File processFile = new File(baseLocation +  File.separator + fileName);
				if(processFile.isDirectory() )
					continue;
				//readFileAndUpdateDB(fileName, baseLocation);
				
				//Queue Process - Flag
				String process_byqueue= bcunProperties.getProperty("bcun.process.byqueue");
				if(process_byqueue.equalsIgnoreCase(BcunConstant.FLAG_YES)){
					readFileAndInsertToQueue(fileName, baseLocation) ;
				}else{
					readFileAndUpdateDB(fileName, baseLocation);
				}
			}
		}
		LOGGER.trace("InboundCentralServiceImpl::processFileAndUploadToDatabase::###END with baseLocation" + baseLocation);
	}
	
	
	protected void readFileAndInsertToQueue(String fileName, String baseLocation) {
		try {
			LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertToQueue::processing file named: " + fileName);
			
			QueueDataContentTO queueContent = new QueueDataContentTO();
			//Getting process name based on file name
			String processName = getProcessName(fileName);
			//Reading in bound properties entry 
			BcunConfigPropertyTO confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
			//Reading process data from file
			String filePath = baseLocation + File.separator + fileName; //getFilePath(fileName, baseLocation);
			File processFile = new File(filePath);
			String jsonText = getContentFromFile(processFile,  confTo.getDoName());
			if(StringUtil.isStringEmpty(jsonText)){
				LOGGER.error("InboundCentralServiceImpl::readFileAndInsertToQueue:: json Text not constructed properly" );
				return;
			//}else if(isValidJsonString(jsonText)){
			}else if(jsonText.startsWith(BcunConstant.JSON_STRING_STARTS_WITH_CHARACTER)&&jsonText.endsWith(BcunConstant.JSON_STRING_ENDS_WITH_CHARACTER)){
				queueContent.setJsonText(jsonText);
				queueContent.setDoName(confTo.getDoName());
				queueContent.setFileName(fileName);
				LOGGER.debug("InboundCentralServiceImpl::readFileAndInsertToQueue::updating db for file: " + fileName);
				try {
					if(fileName.startsWith(RE_PROCESS_FLAG_NAME)){
						LOGGER.info("InboundCentralServiceImpl::readFileAndInsertToQueue::posting in the TEMP-QUEUE from the file: " + fileName);
						QueueProducer.sendMessageTOTempQueue(queueContent);
					}else{
						LOGGER.info("InboundCentralServiceImpl::readFileAndInsertToQueue::posting in the UDAAN-QUEUE from the file: " + fileName);
						QueueProducer.sendMessage(queueContent);
					}
					LOGGER.debug("InboundCentralServiceImpl::readFileAndInsertToQueue::file content added success fully to Queue: " + fileName);
					//Putting file in successfully processed list
					renameFile(fileName, baseLocation, PROCESSED_FOLDER_NAME);
				} catch (Exception ex) {
					//renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
					LOGGER.error("InboundCentralServiceImpl::readFileAndInsertToQueue::unable to add file content to Queue", ex);
				}
				LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertToQueue::processing completed for file named: " + fileName);
			}else{
				processPartiallyConstructedFile(fileName, baseLocation,
						filePath);
				return;
			}
		} catch (Exception ex) {
			//Putting file in error folder
			renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
			LOGGER.error("InboundCentralServiceImpl::readFileAndInsertToQueue::moved to er : ", ex);
		}
	}
	private void processPartiallyConstructedFile(String fileName,
			String baseLocation, String filePath) {
		LOGGER.error("InboundCentralServiceImpl::readFileAndInsertToQueue::Since there is issue with JSON String ,hence file is not able to process : " + fileName+" file ");
		try {
			sendEmailNotificationForFile(fileName, "Dear Branch Manger,\n System have found there is an issue with the File : " + fileName+" As it's not constructed properly at the central server hence system cannot able to process it further,Please kindly contact with IT Department \n\nRegards\n FFCL IT Department");
			long fileModifiedTime=FileUtils.getFileLastModifiedDateTime(filePath);
			LOGGER.debug("InboundCentralServiceImpl::processPartiallyConstructedFile::EXCEPTION : " + fileName+" file path :["+filePath+"] fileModifiedime :["+fileModifiedTime+"]");
			long noOfHoursOldFile=DateUtil.getDateDifferenceByHours(fileModifiedTime, new Date().getTime());
			LOGGER.debug("InboundCentralServiceImpl::processPartiallyConstructedFile::EXCEPTION : " + fileName+" file path :["+filePath+"] fileModifiedime :["+fileModifiedTime+"] noOfHoursOldFile :["+noOfHoursOldFile+"]");
			if(noOfHoursOldFile>=2){
				LOGGER.warn("InboundCentralServiceImpl::processPartiallyConstructedFile::EXCEPTION : " + fileName+" file path :["+filePath+"] fileModifiedime :["+fileModifiedTime+"] noOfHoursOldFile :["+noOfHoursOldFile+"] File Renaming");
				renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
			}
		} catch (Exception e) {
			LOGGER.error("InboundCentralServiceImpl::processPartiallyConstructedFile::EXCEPTION : " + fileName+" file ",e);
		}
	}
	
	/*protected void readFileAndInsertToQueue(String fileName, String baseLocation) {
		try {
			LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertToQueue::processing file named: " + fileName);
			
			//Getting process name based on file name
			String processName = getProcessName(fileName);
			//Reading in bound properties entry 
			BcunConfigPropertyTO confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
			//Reading process data from file
			String filePath = baseLocation + File.separator + fileName; //getFilePath(fileName, baseLocation);
			File processFile = new File(filePath);
			String jsonText = getContentFromFile(processFile, confTo.getDoName());
			QueueProducer.sendMessage(jsonText);
			LOGGER.debug("InboundCentralServiceImpl::readFileAndInsertToQueue::file content added success fully to Queue: " + fileName);
			renameFile(fileName, baseLocation, PROCESSED_FOLDER_NAME);
			LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertToQueue::processing completed for file named: " + fileName);
		} catch (Exception ex) {
			renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
			LOGGER.error("InboundCentralServiceImpl::readFileAndInsertToQueue::moved to er : ", ex);
		}
	}*/

	public void readFileAndInsertTo2WayQueue(String fileName,
			String baseLocation) {
		try {
			LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::processing file named: "
					+ fileName);
			// Reading process data from file
			String filePath = baseLocation + File.separator + fileName; // getFilePath(fileName,
																		// baseLocation);
			File processFile = new File(filePath);
			String jsonText = getContentFromFile(processFile, null);
			if (StringUtil.isStringEmpty(jsonText)) {
				LOGGER.error("InboundCentralServiceImpl::readFileAndInsertTo2WayQueue:: json Text not constructed properly");
				return;
			}
			try {
				LOGGER.debug("InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::inserting file : "
						+ fileName + " Two Way Write Queue");
				
				//TODO uncomment after testing
				QueueProducer.sendTwoWayWriteQueueMessage(jsonText);
				
				//TODO comment after testing
				//inboundCentralDataProcessor.processTwoWayWriteQueueData(jsonText);
				
				LOGGER.debug("InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::inserted file : "
						+ fileName + " success fully to Two Way Write Queue");
				// Putting file in successfully processed list
				renameFile(fileName, baseLocation, PROCESSED_FOLDER_NAME);
			} catch (Exception ex) {
				// renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
				LOGGER.error(
						"InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::unable to add file content to Queue",
						ex);
			}
			LOGGER.trace("InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::processing completed for file named: "
					+ fileName);
		} catch (Exception ex) {
			// Putting file in error folder
			renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
			LOGGER.error(
					"InboundCentralServiceImpl::readFileAndInsertTo2WayQueue::moved to er : ",
					ex);
		}
	}
	
	public String[] getAllFilesNames(String location) {
		return com.capgemini.lbs.framework.utils.FileUtils.getAllFilesNames(location,FrameworkConstants.BCUN_FILE_IDENTIFIER_INBOUND);
	}
	
}
