package com.cg.lbs.bcun.service.inbound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.utils.ZipUtility;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.dataformater.AbstractDataFormater;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.to.TwoWayWriteDataContentTO;
import com.cg.lbs.bcun.utility.BcunDoFormaterMapper;
import com.cg.lbs.bcun.utility.InboundPropertyReader;
import com.cg.lbs.bcun.utility.QueueProducer;
import com.cg.lbs.bcun.utility.TwoWayWriteUtil;

/**
 * @author mohammal
 * Jan 15, 2013
 * Process data for in bound central office/server
 */
public class InboundCentralDataProcessor {
	/**
	 * Logger to log the message
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCentralDataProcessor.class);
	
	/**
	 * BCUN service used to process data.
	 */
	private BcunDatasyncService bcunService;
	
	public void proceedFileProcessing() throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("InboundCentralDataProcessor::proceedFileProcessing::starts======>");
		//Reading file location
		String fileLocation = bcunService.getBaseFileLocation();
		LOGGER.debug("InboundCentralDataProcessor::proceedFileProcessing::fileLocation======>" + fileLocation);
		String[] fileNames = bcunService.getAllFilesNames(fileLocation);
		List<BcunFileSequenceTO> fileList = getFileListInSequence(fileNames);
		if(fileList != null && !fileList.isEmpty()){
			bcunService.processFileAndUploadToDatabase(fileList, fileLocation);
		}else{
			LOGGER.info("InboundCentralDataProcessor::No file exists at fileLocation======>" + fileLocation);
		}
		LOGGER.debug("InboundCentralDataProcessor::proceedFileProcessing::end======>");
	}
	
	
		
	private List<BcunFileSequenceTO> getFileListInSequence(String[] fileNames) {
		if(fileNames == null || fileNames.length == 0)
			return null;
		
		List<BcunFileSequenceTO> fileList = new ArrayList<BcunFileSequenceTO>();
		for(String fileName : fileNames) {
			if(fileName.contains("Inbound") && !fileName.equals("pr")&& !fileName.equals("er")) {
				try {
					String processName = bcunService.getProcessName(fileName);
					BcunConfigPropertyTO confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
					int sequence = confTo.getSequence();
					BcunFileSequenceTO sequenceTO = new BcunFileSequenceTO();
					sequenceTO.setSequenceNumber(sequence);
					sequenceTO.setFileName(fileName);
					fileList.add(sequenceTO);
				} catch (Exception ex) {
					LOGGER.error("InboundCentralDataProcessor::getFileListInSequence:: Exception...",ex);
				}
				
			}
		}
		Collections.sort(fileList);
		return fileList;
	}
	
	public void processQueueData(QueueDataContentTO queueContent) throws Exception  {
		LOGGER.info("InboundCentralDataProcessor::processQueueData:: started...");
		if(queueContent == null){
			LOGGER.error("InboundCentralDataProcessor::processQueueData:: queue message cannot be null");
			throw new CGBusinessException("queue message cannot be null");
		}
		String doName = queueContent.getDoName();
		LOGGER.info("InboundCentralDataProcessor::processQueueData:: doName ::" + doName);
		String jsonText = queueContent.getJsonText();
		LOGGER.trace("InboundCentralDataProcessor::processQueueData:: jsonText ::" + jsonText);
		if(doName == null || doName.isEmpty() ||  jsonText == null || jsonText.isEmpty()){
			LOGGER.error("InboundCentralDataProcessor::processQueueData::domain object name and json test");
			throw new CGBusinessException("InboundCentralDataProcessor::processQueueData::domain object name and json test");
		}
		formatAndSaveQueueData(queueContent);
		/* sami: additional code to handle Json parsing errors,after json validations in previous stages
		 * if(jsonText.startsWith(BcunConstant.JSON_STRING_STARTS_WITH_CHARACTER)&&jsonText.endsWith(BcunConstant.JSON_STRING_ENDS_WITH_CHARACTER)){
		formatAndSaveQueueData(queueContent);
		}else{
			LOGGER.error("InboundCentralDataProcessor::processQueueData:: JSON parsing Issue..."+queueContent.getFileName());
			bcunService.createErrorFile(jsonText, queueContent.getFileName());
			bcunService.sendEmailNotificationForFile(queueContent.getFileName(), "Dear Branch Manger,\n System have found there is an issue with the File : " + queueContent.getFileName()+" As it's not constructed properly at the central server hence system cannot able to process it further,Please kindly contact with IT Department \n\nRegards\n FFCL IT Department");
		}*/
		LOGGER.info("InboundCentralDataProcessor::processQueueData:: completed...");
		
	}
	
	private void formatAndSaveQueueData(QueueDataContentTO queueContent) throws Exception {
		List<CGBaseDO> errorList = null;
		List<CGBaseDO> tempList = null;
		List<CGBaseDO> baseList =null;
		List<CGBaseDO> specailErrorList =null;
		LOGGER.info("InboundCentralDataProcessor::formatAndSaveQueueData:: START with QueueContent # Do Name :["+queueContent.getDoName()+"] fileName :["+queueContent.getFileName()+"]" );
		try {
			 try {
				baseList = bcunService.getProcessDOListFromJson(queueContent.getJsonText(), queueContent.getDoName());
			} catch (Exception e) {
				bcunService.createUnParsingErrorFile(queueContent);
				LOGGER.error("InboundCentralDataProcessor::formatAndSaveQueueData:: Exception  For QueueContent # Do Name :["+queueContent.getDoName()+"] fileName :["+queueContent.getFileName()+"]" ,e);
				throw e;
			}
			if(baseList == null || baseList.isEmpty()){
				throw new CGBusinessException("InboundCentralDataProcessor::processQueueData::unable to get object list from jsonText");
			}else{
				errorList = new ArrayList(baseList.size());
				tempList = new ArrayList(baseList.size());
				specailErrorList = new ArrayList(baseList.size());
			}
			String formatorName = BcunDoFormaterMapper.getInboundFormaters().get(queueContent.getDoName());
			LOGGER.info("InboundCentralDataProcessor::formatAndSaveQueueData:: formatorName ["+formatorName+"]" );
			AbstractDataFormater formaterService = bcunService.getFormaterClass(formatorName);
			for(CGBaseDO baseDO : baseList) {
				String className=baseDO.getClass().getName();
				try {
					LOGGER.info("InboundCentralDataProcessor::formatAndSaveQueueData:: Processing "+className+"START" );
					CGBaseDO formatedBaseDO = bcunService.formateData(baseDO, formaterService);
					//Setting DT flag to Y to avoid cyclic transfer....
					bcunService.setDefaultFlags(formatedBaseDO);
					//set dtToBranch flag to N to enable blob creation for updated entity....
					bcunService.setCentralToBranchDTFlags(formatedBaseDO);
					bcunService.updateCentralDB(formatedBaseDO);
					LOGGER.info("InboundCentralDataProcessor::formatAndSaveQueueData:: Processing "+className+"DONE" );
					//Posting notification if applicable
					validateAndPostNotification(formatedBaseDO);
					
				} /*catch (DataIntegrityViolationException ex) {
					errorList.add(baseDO);
					LOGGER.error("InboundCentralDataProcessor::proceedFileProcessing::categorised exception in updating individual entities.... : ", ex);
				}*/ catch (Exception ex) {
					checkAndAddErrorTypeList(tempList, errorList, ex, baseDO);
					LOGGER.error("InboundCentralDataProcessor::proceedFileProcessing::un categorised exception in updating individual entities.... : EXception for the className["+className+"]", ex);
				}
				if(!StringUtil.isStringEmpty(baseDO.getMandatoryFlag()) &&baseDO.getMandatoryFlag().equalsIgnoreCase(CommonConstants.YES) ){
				bcunService.checkForSpecialErrors(specailErrorList, new CGBusinessException("not-null property references a null"), baseDO);
				}
			}
			//Creating error file with error prone data 
			createTempFile(tempList, queueContent.getFileName());
			createErrorFile(errorList, queueContent.getFileName());
			bcunService.createSpecialTempFile(specailErrorList, queueContent.getFileName());
			
		} catch (Exception e) {
			LOGGER.error("InboundCentralDataProcessor::formatAndSaveQueueData::... : ", e);
			throw e;
		}
		LOGGER.info("InboundCentralDataProcessor::formatAndSaveQueueData:: ENDS with QueueContent # Do Name :["+queueContent.getDoName()+"] fileName :["+queueContent.getFileName()+"]" );
	}
	
	private void validateAndPostNotification(CGBaseDO baseDO) {
		if(baseDO.isNotificationRequired()) {
			//QueueProducer.postNotification(deliveryDetailDO.getConsignmentNumber(), deliveryDetailDO.getDeliveryStatus(), null);
			QueueProducer.postNotification(baseDO);
		}
	}
	
	private void checkAndAddErrorTypeList(List<CGBaseDO> tempList, List<CGBaseDO> errorList, Exception e, CGBaseDO baseDO) {
		String errorType = ExceptionUtil.dataSyncExceptionType(e);
		LOGGER.debug("InboundCentralDataProcessor::checkAndAddErrorTypeList::Queue has got error type: " + errorType);
		if(errorType != null && errorType.equalsIgnoreCase(ExceptionUtil.DATA_SYNC_RE_PROCESS_TYPE)) {
			tempList.add(baseDO);
		} else {
			errorList.add(baseDO);
		}
	}
	
	private void createTempFile(List<CGBaseDO> errorList, String fileName) throws IOException {
		if(!CGCollectionUtils.isEmpty(errorList)){
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::Queue is going to create temp file....");
			//String fileName =  queueContent.getFileName();//DataSync-Inbound-B991-createBooking-1378201200245.xml
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::... : Creating temp file for [ "+fileName+"]");
			String queueFileName = fileName.replace("DataSync", "DataSyncQ");
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::... :  Queue FileName :[ "+queueFileName+"]");
			bcunService.createTempFile(errorList,queueFileName);
		} else {
			LOGGER.info("InboundCentralDataProcessor::createTempFile::There is no tem error to write in this batch.....");
		}
	}
	private void createErrorFile(List<CGBaseDO> errorList, String fileName) throws IOException {
		if(!CGCollectionUtils.isEmpty(errorList)){
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::Queue is going to create error file....");
			//String fileName =  queueContent.getFileName();//DataSync-Inbound-B991-createBooking-1378201200245.xml
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::... : Creating temp file for [ "+fileName+"]");
			String queueFileName = fileName.replace("DataSync", "DataSyncQER");
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::... :  Queue FileName :[ "+queueFileName+"]");
			bcunService.createErrorFile(errorList,queueFileName);
		}else {
			LOGGER.info("InboundCentralDataProcessor::createErrorFile::There is no error to write in this batch.....");
		}
	}

	public void createErrorFileFromQueue(QueueDataContentTO queueContent) throws IOException {
		LOGGER.debug("InboundCentralDataProcessor::createErrorFileFromQueue::Queue is going to create error file....START");
		if(queueContent!=null){
			LOGGER.debug("InboundCentralDataProcessor::createErrorFileFromQueue::Queue is going to create error file.... for the File Name:["+queueContent.getFileName()+"] and invoking bcunService.createErrorFile");
			bcunService.createErrorFile(queueContent.getJsonText(),queueContent.getFileName());
		}else {
			LOGGER.warn("InboundCentralDataProcessor::createErrorFileFromQueue::Queue is going to create error file....QueueDataContentTO is null");
		}
		LOGGER.debug("InboundCentralDataProcessor::createErrorFileFromQueue::Queue is going to create error file....END");
	}
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
	private void formatAndSaveTwoWayWriteQueueData(
			TwoWayWriteDataContentTO twoWayWriteDataContentTO, String jsonStr) throws Exception {
		List<CGBaseDO> errorList = null;
		List<CGBaseDO> tempList = null;
		List<String> errorDoNames = null;
		List<String> tempDoNames = null;
		//move to pr
		List<CGBaseDO> baseList =null;

		try {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData:: START with QueueContent # Do Names :["
					+ twoWayWriteDataContentTO.getDoNames());
			String[] doNames = twoWayWriteDataContentTO.getDoNames();
			LOGGER.debug("InboundCentralDataProcessor::formatAndSaveTwoWayWriteQueueData:: doNames ::"
					+ doNames);
			String[] jsonObjectArrayStr = twoWayWriteDataContentTO
					.getJsonObjectArrayStr();
			LOGGER.trace("InboundCentralDataProcessor::formatAndSaveTwoWayWriteQueueData:: jsonObjectArrayStr ::"
					+ jsonObjectArrayStr);

			if (!StringUtil.isEmpty(jsonObjectArrayStr)) {
				errorList = new ArrayList<>(jsonObjectArrayStr.length);
				tempList = new ArrayList<>(jsonObjectArrayStr.length);
				baseList = new ArrayList<>(jsonObjectArrayStr.length);
				errorDoNames = new ArrayList<>(jsonObjectArrayStr.length);
				tempDoNames = new ArrayList<>(jsonObjectArrayStr.length);
			}

			for (int j = 0; j < jsonObjectArrayStr.length; j++) {
				String formatorName = BcunDoFormaterMapper
						.getInboundFormaters().get(doNames[j]);
				LOGGER.trace("AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData:: formatorName ["
						+ formatorName + "]");
				AbstractDataFormater formaterService = bcunService
						.getFormaterClass(formatorName);

				CGBaseDO cgBaseDO = TwoWayWriteUtil.getProcessDOFromJson(
						twoWayWriteDataContentTO.getJsonObjectArrayStr()[j],
						twoWayWriteDataContentTO.getDoNames()[j]);

				try {
					if (cgBaseDO == null) {
						// continue;
						throw new CGBusinessException(
								"InboundCentralDataProcessor::formatAndSaveTwoWayWriteQueueData::unable to get object from jsonText");
					}

					LOGGER.trace("AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData:: Processing "
							+ cgBaseDO.getClass().getName() + "START");
					CGBaseDO formatedBaseDO = bcunService.formateData(cgBaseDO,
							formaterService);
					// Setting DT flag to Y to avoid cyclic transfer....
					bcunService.setDefaultFlags(formatedBaseDO);
					
					// Setting ToWayWrite DT_TO_Central flag to T, to assure data is updated by ToWayWrite Process....
					formatedBaseDO.setDtToCentral(BcunConstant.TWO_WAY_WRITE_TRANSFER_STATUS);

					//set dtToBranch flag to N to enable blob creation for updated entity....
					bcunService.setCentralToBranchDTFlags(formatedBaseDO);
					
					bcunService.updateCentralDB(formatedBaseDO);
					LOGGER.trace("AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData:: Processing "
							+ cgBaseDO.getClass().getName() + "DONE");
					
					baseList.add(cgBaseDO);

				} catch (Exception ex) {
					checkAndAddErrorTypeList(tempList, errorList, tempDoNames,
							errorDoNames, ex, cgBaseDO,
							twoWayWriteDataContentTO.getDoNames()[j]);
					LOGGER.error(
							"AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData::un categorised exception in updating individual entities.... : Exception for the className["
									+ twoWayWriteDataContentTO.getDoNames()[j]
									+ "]", ex);
				}
			}
			// Creating error file with error prone data
			createTempFile(tempList, tempDoNames,
					twoWayWriteDataContentTO.getFileName());
			// createErrorFile(errorList, errorDoNames,
			// twoWayWriteDataContentTO.getFileName());
			createErrorFile(errorList, twoWayWriteDataContentTO.getFileName());
			bcunService.createProcessedFile(baseList, twoWayWriteDataContentTO.getFileName());
			
		} catch (Exception e) {
			LOGGER.error(
					"AbstractBcunDatasyncServiceImpl::formatAndSaveTwoWayWriteQueueData::... : ",
					e);
			throw e;
		}
	}

	private void createErrorFile(List<CGBaseDO> errorDOs,
			List<String> errorDoNames, String fileName) throws ClassNotFoundException, IOException {
		if(!CGCollectionUtils.isEmpty(errorDOs) && !CGCollectionUtils.isEmpty(errorDoNames)){
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::Queue is going to create error file....");
			String jsonStr = getTwoWayWriteDataContentTOToJsonStr(errorDOs, errorDoNames, fileName);
			//String fileName =  queueContent.getFileName();//DataSync-Inbound-B991-createBooking-1378201200245.xml
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::... : Creating temp file for [ "+fileName+"]");
			String queueFileName = fileName.replace("DataSync", "DataSyncQER");
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::... :  Queue FileName :[ "+queueFileName+"]");
			bcunService.createErrorFile(jsonStr,queueFileName);
		}else {
			LOGGER.debug("InboundCentralDataProcessor::createErrorFile::There is no error to write in this batch.....");
		}
	}

	private void createTempFile(List<CGBaseDO> tempDOs,
			List<String> tempDoNames, String fileName) throws ClassNotFoundException, IOException {
		if(!CGCollectionUtils.isEmpty(tempDOs) && !CGCollectionUtils.isEmpty(tempDoNames)){
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::Queue is going to create temp file....");
			//String fileName =  queueContent.getFileName();//DataSync-Inbound-B991-createBooking-1378201200245.xml
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::... : Creating temp file for [ "+fileName+"]");
			String queueFileName = fileName.replace("DataSync", "DataSyncQ");
			LOGGER.debug("InboundCentralDataProcessor::createTempFile::... :  Queue FileName :[ "+queueFileName+"]");
			String jsonStr = getTwoWayWriteDataContentTOToJsonStr(tempDOs, tempDoNames, queueFileName);
			bcunService.createTempFile(jsonStr, queueFileName);
		} else {
			LOGGER.info("InboundCentralDataProcessor::createTempFile::There is no tem error to write in this batch.....");
		}
	}

	private String getTwoWayWriteDataContentTOToJsonStr(
			List<CGBaseDO> cgBaseDOs, List<String> doNames, String fileName)
			throws ClassNotFoundException, IOException {
		TwoWayWriteDataContentTO twoWayWriteDataContentTO = new TwoWayWriteDataContentTO();
		String[] doNameArr = new String[doNames.size()];
		doNameArr = doNames.toArray(doNameArr);
		twoWayWriteDataContentTO.setFileName(fileName);
		twoWayWriteDataContentTO.setDoNames(doNameArr);
		String jsonStr = TwoWayWriteUtil
				.convertTwoWayWriteDataContentTOToJsonStr(
						twoWayWriteDataContentTO, cgBaseDOs);
		return jsonStr;
	}

	private void checkAndAddErrorTypeList(List<CGBaseDO> tempList,
			List<CGBaseDO> errorList, List<String> tempDoNames,
			List<String> errorDoNames, Exception e, CGBaseDO cgBaseDO,
			String doName) {
		String errorType = ExceptionUtil.dataSyncExceptionType(e);
		LOGGER.debug("InboundCentralDataProcessor::checkAndAddErrorTypeList::Queue has got error type: "
				+ errorType);
		if (errorType != null
				&& errorType
						.equalsIgnoreCase(ExceptionUtil.DATA_SYNC_RE_PROCESS_TYPE)) {
			tempList.add(cgBaseDO);
			tempDoNames.add(doName);
		} else {
			errorList.add(cgBaseDO);
			errorDoNames.add(doName);
		}
	}

	public void processTwoWayWriteQueueData(
			String twoWayWriteDataContentTOJSONStr) throws Exception  {
		LOGGER.debug("InboundCentralDataProcessor::processTwoWayWriteQueueData:: started...");
		if(StringUtils.isBlank(twoWayWriteDataContentTOJSONStr)){
			throw new CGBusinessException("Two way write queue message cannot be null");			
		}
		TwoWayWriteDataContentTO twoWayWriteDataContentTO = TwoWayWriteUtil.convertJsonStrToTwoWayWriteDataContentTO(twoWayWriteDataContentTOJSONStr);
		
		LOGGER.debug("InboundCentralDataProcessor::processTwoWayWriteQueueData:: doNames ::" + twoWayWriteDataContentTO.getDoNames());
		LOGGER.trace("InboundCentralDataProcessor::processTwoWayWriteQueueData:: jsonObjectArrayStr ::" + twoWayWriteDataContentTO.getJsonObjectArrayStr());
		if(StringUtil.isEmpty(twoWayWriteDataContentTO.getDoNames()) || StringUtil.isEmpty(twoWayWriteDataContentTO.getJsonObjectArrayStr()) )
			throw new CGBusinessException("InboundCentralDataProcessor::processTwoWayWriteQueueData::Either of them or both domain object name and jsonObjectArrayStr are null");
		
		formatAndSaveTwoWayWriteQueueData(twoWayWriteDataContentTO, twoWayWriteDataContentTOJSONStr);
		LOGGER.debug("InboundCentralDataProcessor::processTwoWayWriteQueueData:: completed...");
	}

	public void create2WayErrorFile(String jsonStr) throws IOException {
		TwoWayWriteDataContentTO twoWayWriteDataContentTO = TwoWayWriteUtil
				.convertJsonStrToTwoWayWriteDataContentTO(jsonStr);
		LOGGER.debug("InboundCentralDataProcessor::create2WayErrorFile::Queue is going to create error file....");
		LOGGER.debug("InboundCentralDataProcessor::create2WayErrorFile::... : Creating error file for [ "
				+ twoWayWriteDataContentTO.getFileName() + "]");
		String queueFileName = twoWayWriteDataContentTO.getFileName().replace(
				"DataSync", "DataSyncQERP");
		LOGGER.debug("InboundCentralDataProcessor::create2WayErrorFile::... :  Queue FileName :[ "
				+ queueFileName + "]");
		bcunService.createErrorFile(jsonStr, queueFileName);
		LOGGER.error("InboundCentralDataProcessor::create2WayErrorFile::Error messages written succesfully in ERROR folder");
	}
	
	public void fileExtractor() throws HttpException, ClassNotFoundException, IOException{
		LOGGER.debug("InboundCentralDataProcessor::fileExtractor::starts======>");
		//Reading file location
		String fileLocation = bcunService.getBaseFileLocation();
		LOGGER.debug("InboundCentralDataProcessor::fileExtractor::fileLocation======>" + fileLocation);
		String[] fileNames = getAllZipFilesNames(fileLocation);
		for (String fileName:fileNames){
			try {
				ZipUtility.unzip(fileLocation+File.separator+fileName, fileLocation);
			bcunService.renameFile(fileName, fileLocation, BcunConstant.FLAG_PROCESSED.toLowerCase());
			} catch (Exception e) {
				LOGGER.error("InboundCentralDataProcessor::fileExtractor::file Name :["+fileName+"]ERROR======>",e);
			}
		}
		LOGGER.debug("InboundCentralDataProcessor::fileExtractor::end======>");
	}
	
	public String[] getAllZipFilesNames(String location) {
		String[] fileNames = null;
		//Checking the location is empty or not
		if(!StringUtil.isStringEmpty(location)) {
			//Creating a base directory of provided location
			File baseDir = new File(location);
			if(baseDir.isDirectory()) {
				fileNames = baseDir.list(com.capgemini.lbs.framework.utils.FileUtils.bcunFileFilter(FrameworkConstants.ZIP_EXTENSION));
			}
		}
		return fileNames;
	}
	

}
