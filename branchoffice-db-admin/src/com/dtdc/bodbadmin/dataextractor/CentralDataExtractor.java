/**
 * 
 */
package com.dtdc.bodbadmin.dataextractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BusinessConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.SplitModelUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.bs.LocalDBDataPersistService;
import com.dtdc.bodbadmin.constant.BranchDBAdminConstant;
import com.dtdc.bodbadmin.integration.UserHttpClient;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.bodbadmin.xmlutil.CTBSXMLParser;
import com.dtdc.bodbadmin.ziputil.ZipUtility;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.to.manifestextractor.DataExtractorTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralDataExtractor.
 *
 * @author nisahoo
 */
public class CentralDataExtractor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(CentralDataExtractor.class);
	
	
	/** The user http client. */
	private UserHttpClient userHttpClient;
	
	/** The message prop. */
	private Properties messageProp;
	
	/** The local db data persist service. */
	private LocalDBDataPersistService localDBDataPersistService;
	
	 /** The is windows. */
 	Boolean isWindows;
	 
 	/** The local zip dir path. */
 	String localZipDirPath =null;
	 
 	/** The local un zip folder. */
 	String localUnZipFolder =null;
	 
 	/** The local un zip dir path. */
 	String localUnZipDirPath =null;
	 
 	/** The local un zip temp dir path. */
 	String localUnZipTempDirPath =null;
	 
 	/** The local error dir path. */
 	String localErrorDirPath =null;
	 
 	/** The local archive dir path. */
 	String localArchiveDirPath =null;
	 
 	/** The local manual dir path. */
 	String localManualDirPath =null;
	 
 	/** The local error log dir path. */
 	String localErrorLogDirPath =null;
	
	/**
	 * File initializer.
	 */
	private  void fileInitializer() {
		String osName =System.getProperty(BranchDBAdminConstant.OS_NAME);
		isWindows = osName.toUpperCase().contains(BranchDBAdminConstant.WINDOWS);
		
		if(isWindows){
			LOGGER.debug("CentralDataExtractor : Picking ****WINDOWS***** based file structure: ");
			localErrorDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ERROR_DIRECTORY_WINDOWS);
			localZipDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ZIP_DIRECTORY_WINDOW);

			localUnZipDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_UNZIP_DIRECTORY_WINDOW);

			localUnZipTempDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_UNZIP_TEMP_DIRECTORY_WINDOW);
			localArchiveDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ARCHIVE_FOLDER_WINDOWS);
			
			localManualDirPath = messageProp.getProperty(BranchDBAdminConstant.LOCAL_MANUAL_DIR_WINDOWS);
			
			localErrorLogDirPath = messageProp.getProperty(BranchDBAdminConstant.LOCAL_ERRROR_LOCAL_DIR_WINDOWS);
		}else{
			LOGGER.debug("CentralDataExtractor : Picking ****LINUX***** based file structure: ");
			localErrorDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ERROR_DIRECTORY_LINUX);
			localZipDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ZIP_DIRECTORY_LINUX);

			localUnZipDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_UNZIP_DIRECTORY_LINUX);

			localUnZipTempDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_UNZIP_TEMP_DIRECTORY_LINUX);
			
			localArchiveDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_ARCHIVE_FOLDER_LINUX);
			
			localManualDirPath = messageProp
			.getProperty(BranchDBAdminConstant.LOCAL_MANUAL_DIR_LINUX);
			localErrorLogDirPath = messageProp.getProperty(BranchDBAdminConstant.LOCAL_ERRROR_LOCAL_DIR_LINUX);
		}
	}
	

	/**
	 * Gets the message prop.
	 *
	 * @return the message prop
	 */
	public Properties getMessageProp() {
		return messageProp;
	}

	/**
	 * Sets the message prop.
	 *
	 * @param messageProp the new message prop
	 */
	public void setMessageProp(Properties messageProp) {
		this.messageProp = messageProp;
	}

	/**
	 * Gets the user http client.
	 *
	 * @return the user http client
	 */
	public UserHttpClient getUserHttpClient() {
		return userHttpClient;
	}

	/**
	 * Sets the user http client.
	 *
	 * @param userHttpClient the new user http client
	 */
	public void setUserHttpClient(UserHttpClient userHttpClient) {
		this.userHttpClient = userHttpClient;
	}

	/**
	 * Gets the local db data persist service.
	 *
	 * @return the local db data persist service
	 */
	public LocalDBDataPersistService getLocalDBDataPersistService() {
		return localDBDataPersistService;
	}

	/**
	 * Sets the local db data persist service.
	 *
	 * @param localDBDataPersistService the new local db data persist service
	 */
	public void setLocalDBDataPersistService(
			LocalDBDataPersistService localDBDataPersistService) {
		this.localDBDataPersistService = localDBDataPersistService;
	}

	/**
	 * The function is invoked by Quartz Scheduler to fetch Outgoing Manifest &
	 * Booking Data and persist the data to local branch DB.
	 *
	 * @throws CGBusinessException the cG business exception
	 * @throws Exception the exception
	 */
	public void dataExtractor() throws CGBusinessException,Exception {
		
		fileInitializer();
		LOGGER.debug("CentralDataExtractor : dataExtractor() : SATRT");
		File errorFolder = null;
		Integer maxFetchRecords = 2;
		String branchCode = "";
		DataExtractorTO extractorTo = new DataExtractorTO();
		byte[] incomingFileData = null;
		File tempUnZipFolder = null;
		File manualFolder = null;
		File localUnZipDir = null;
		File errorLogFolder=null;
		String dataExtctrIdStrArray =null;
		 String dataExtctrIdStr =null;
		 String requestNumber = StringUtil.generateRamdomNumber();
		
		
		
		try {

			/* Perform transparent logging in */
			
			//creating Manual_upload Directory if not available
			manualFolder = new File(localManualDirPath);
				if(manualFolder != null && !manualFolder.exists()){
				manualFolder.mkdirs();
			}
			
			//creating error Directory if not available
			errorFolder = new File(localErrorDirPath);
			// creating a error folder(Whenever any exception occurred
			// in this block all these files moved into the error
			// folder)
				if(errorFolder != null && !errorFolder.exists()){
				errorFolder.mkdirs();
				}
				
				//creating error log Directory if not available
				errorLogFolder = new File(localErrorLogDirPath);
				// creating a error folder(Whenever any exception occurred
				// in this block all these files moved into the error
				// folder)
					if(errorLogFolder != null && !errorLogFolder.exists()){
						errorLogFolder.mkdirs();
					}
			
			//creating unzip Directory if not available
			localUnZipDir = new File(localUnZipDirPath);
				if(localUnZipDir !=null && !localUnZipDir.exists()){
				localUnZipDir.mkdirs();
				}
			//creating unzip Directory if not available
			 File localArchive = new File(localArchiveDirPath);
			
				if(localArchive != null && !localArchive.exists()){
					localArchive.mkdirs();
				}
		
			
			LOGGER.debug("CentralDataExtractor : connecting to the online Mode(++++++ Perform transparent logging in ++++++)");
			if (SplitModelUtil.checkOnlineMode(null, null, null)) {
				LOGGER.debug("CentralDataExtractor : checkOnlineMode() : returned true");
				LOGGER.debug("CentralDataExtractor : checkOnlineMode() : #### Performing Data Extraction Operations ##### START");
				List<DataExtractorTO> dataExtractorTOList = new ArrayList<DataExtractorTO>();

				/* Get the branch Code */
				branchCode = messageProp
						.getProperty(BranchDBAdminConstant.BRANCH_CODE);
				
				String recordsToBeFetch = messageProp
						.getProperty(BranchDBAdminConstant.MAX_FETCH_RECORD);
				LOGGER.debug("CentralDataExtractor : branchCode : "
						+ branchCode);
				
				if (!StringUtil.isEmpty(recordsToBeFetch)) {
					maxFetchRecords = Integer.parseInt(recordsToBeFetch);
					LOGGER.debug("CentralDataExtractor : maxFetchRecords maxFetchRecords: "
							+ maxFetchRecords);
				} else {
					LOGGER.debug("CentralDataExtractor : maxFetchRecords (taking default value): "
							+ maxFetchRecords);
				}
				extractorTo.setMaxRecords(maxFetchRecords);
				extractorTo.setBranchCode(branchCode);
				extractorTo.setRandomNumber(requestNumber);
				dataExtractorTOList.add(extractorTo);

				CGBaseTO baseTO = new CGBaseTO();
				baseTO.setBaseList(dataExtractorTOList);
				baseTO.setObjectType(DataExtractorTO.class);
				baseTO.setRequestType(ApplicationConstants.VALIDATION_REQUEST);
				baseTO.setBeanId(BranchDBAdminConstant.SERVICE_NAME);
				baseTO.setClassType(BranchDBAdminConstant.SERVICE_CLASS_NAME);
				baseTO.setMethodName(BranchDBAdminConstant.LOAD_DATA_FOR_BRANCH_METHOD);

				/* Fetch Data from Central Server */

				LOGGER.debug("CentralDataExtractor :  invoking \"createRemoteRequest\"  ");
				
				LOGGER.debug("CentralDataExtractor :  sending request --->with request Id :["+requestNumber+"]");
				baseTO = userHttpClient.createRemoteRequest(baseTO);

				if (baseTO != null) {
					extractorTo = (DataExtractorTO) baseTO;
					incomingFileData = extractorTo.getFileData();
					LOGGER.debug("CentralDataExtractor :  got response for  request Id :["+requestNumber+"]");
					LOGGER.debug("++++++++++++File Data from Server Received...+++++++++ For Branch : ["
							+ branchCode + "]");
					LOGGER.debug("--- with status ----- IsAborted ["
							+ extractorTo.getIsAborted() + "]" + "\t"
							+ " HttpStatusCode :["
							+ extractorTo.getHttpStatusCode() + "]");
				}

				if (incomingFileData != null && incomingFileData.length > 0
						&& !extractorTo.getIsAborted()
						&& extractorTo.getHttpStatusCode() == HttpStatus.SC_OK) {
					LOGGER.debug("CentralDataExtractor :  got data  for  request Id :["+requestNumber+"] for dataExtractor Id"+ extractorTo.getDataExtctrIdStr() );
					dataExtctrIdStr = extractorTo.getDataExtctrIdStr();
					dataExtctrIdStrArray = extractorTo.getDataExtctrIdStrArray();
					
				 localUnZipFolder = localUnZipDirPath + File.separator
					+ branchCode;
				 
					
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED ZIP FILES - START");
					// Create the Zip File at the Local Branch
					
					File localZipDir = new File(localZipDirPath);

						if(localZipDir != null && !localZipDir.exists()){
						localZipDir.mkdirs();
						}
					LOGGER.debug("CentralDataExtractor : dataExtractor() : LOCAL ZIP DIRECTORY CREATED");
					//Following line creates unique zip file
					String zipFile = localZipDir.getAbsolutePath()
							+ File.separator + branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+ DateFormatterUtil.getCurrentTimeInString()+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber()
							+ BranchDBAdminConstant.ZIP_FILE_EXTENSION ;
					LOGGER.debug("CentralDataExtractor : dataExtractor() :  ZIP FILE path - ["+zipFile+"]");
					ZipUtility.createLocalZipFile(zipFile, incomingFileData);
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED ZIP FILES - END");

					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED UNZIP FILES - START");
					// Create the Zip File at the Local Branch which Holds all
					// the XML Files
					
					
					//added random number for uniqueness
					//Modified date :17-feb-2012
					String localUnZipTempFolder = localUnZipTempDirPath
					+ File.separator + branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+ StringUtil.generateRamdomNumber();
					
					// Unzip the Parent Folder
					ZipUtility.unzip(zipFile, localUnZipTempFolder);

					// Read the Unzipped Temp Folder and Unzip the sub zip
					// folder
					tempUnZipFolder = new File(localUnZipTempFolder);
					File[] subZipfiles = tempUnZipFolder.listFiles();
					if (subZipfiles != null && subZipfiles.length > 0) {
						for (int i = 0; i < subZipfiles.length; i++) {
							String[] tempFileName = subZipfiles[i].getName()
									.split("\\.");
							String subFileExtension = tempFileName[1];
							if (subFileExtension
									.equalsIgnoreCase(BranchDBAdminConstant.ZIP_EXTENSION)) {
								ZipUtility.unzip(
										subZipfiles[i].getAbsolutePath(),
										localUnZipFolder);
							} else {
								CGXMLUtil.moveFile(localUnZipFolder,
										subZipfiles[i]);
							}
						}
					}
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED UNZIP FILES - END");
					// Delete the Unzip Temp Folder
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED UNZIP DELETE FILES - START");
					folderRename(localUnZipTempFolder);
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED UNZIP DELETE FILES - END");
					
					LOGGER.debug("CentralDataExtractor : dataExtractor() : DELETE THE ZIP FOLDER - AFTER SAVING THE DATA BO DATABASE - START");
					folderRename(zipFile);
					LOGGER.debug("CentralDataExtractor : dataExtractor() : DELETE THE ZIP FOLDER - AFTER SAVING THE DATA BO DATABASE - END");
					// Read the Unzipped Folder and Parse XML
					LOGGER.debug("CentralDataExtractor : dataExtractor() : READ THE UN ZIPPED FOLDER AND PARSE XML - START");
					File unZipFolder = new File(localUnZipFolder);
					File[] files = unZipFolder.listFiles();
					String localUnZipFolder1=localUnZipFolder;
					if (files != null && files.length > 0) {
						
						readDataForAllProcess(localUnZipFolder1);
					}
					LOGGER.debug("CentralDataExtractor : dataExtractor() : READ THE UN ZIPPED FOLDER AND PARSE XML - END");
					
					

					LOGGER.debug("CentralDataExtractor : checkOnlineMode() : #### Performing Data Extraction Operations ##### END");
				} else {
					LOGGER.debug("CentralDataExtractor : dataExtractor() : incomingFileData - NOT FOUND status :["+extractorTo.getHttpStatusCode()+"]");
					
					if(!StringUtil.isEmpty(extractorTo.getDataExtctrIdStr())){
						String idParams = extractorTo.getDataExtctrIdStr();
						extractorTo = new DataExtractorTO();
						List<DataExtractorTO> dataExtractorList = new ArrayList<DataExtractorTO>();
						dataExtractorList.add(extractorTo);
						extractorTo.setBranchCode(branchCode);
						//Here both dataExtctrIdStr,dataExtctrIdStrArray will be same but way of representation (content )will differ
						extractorTo.setRandomNumber(requestNumber);
						extractorTo.setDataExtctrIdStr(idParams);
						
						CGBaseTO baseTODup = new CGBaseTO();
						baseTO.setBaseList(dataExtractorList);
						baseTO.setObjectType(DataExtractorTO.class);
						baseTO.setRequestType(ApplicationConstants.VALIDATION_REQUEST);
						baseTO.setBeanId(BranchDBAdminConstant.SERVICE_NAME);
						baseTO.setClassType(BranchDBAdminConstant.SERVICE_CLASS_NAME);
						baseTO.setMethodName(BranchDBAdminConstant.STATUS_RESTORE_FOR_BRANCH);

						/* Fetch Data from Central Server */

						LOGGER.debug("CentralDataExtractor :  invoking \"createRemoteRequest\" to change status from T to R  ");
						baseTO = userHttpClient.createRemoteRequest(baseTODup);
						LOGGER.debug("******CentralDataExtractor :  invoking Acknowledge method for changing  status *********END ******");
						
					}else{
						LOGGER.debug("CentralDataExtractor : dataExtractor() : incomingFileData - NOT FOUND COMPLETELY ");
					}
					
				}
				LOGGER.debug("CentralDataExtractor : dataExtractor() : END");
			} else {
				LOGGER.error("###################################################################");
				LOGGER.error("CentralDataExtractor : checkOnlineMode() : Unable to contact online - Central Server");
				LOGGER.error("####################################################################");
				
				
			}
			if (extractorTo != null && incomingFileData != null
					&& incomingFileData.length > 0) {

				if (!extractorTo.getIsAborted()
						&& extractorTo.getHttpStatusCode() == HttpStatus.SC_OK) {
					LOGGER.debug("******CentralDataExtractor :  invoking Acknowledge method for changing  status *********START ******");
					extractorTo = new DataExtractorTO();
					List<DataExtractorTO> dataExtractorTOList = new ArrayList<DataExtractorTO>();
					dataExtractorTOList.add(extractorTo);
					extractorTo.setBranchCode(branchCode);
					extractorTo.setRandomNumber(requestNumber);
					//Here both dataExtctrIdStr,dataExtctrIdStrArray will be same but way of representation (content )will differ
					extractorTo.setDataExtctrIdStr(dataExtctrIdStr);
					
					CGBaseTO baseTO = new CGBaseTO();
					baseTO.setBaseList(dataExtractorTOList);
					baseTO.setObjectType(DataExtractorTO.class);
					baseTO.setRequestType(ApplicationConstants.VALIDATION_REQUEST);
					baseTO.setBeanId(BranchDBAdminConstant.SERVICE_NAME);
					baseTO.setClassType(BranchDBAdminConstant.SERVICE_CLASS_NAME);
					baseTO.setMethodName(BranchDBAdminConstant.STATUS_UPDATE_FOR_BRANCH);

					/* Fetch Data from Central Server */

					LOGGER.debug("CentralDataExtractor :  invoking \"createRemoteRequest\" to change status from T to R  ");
					baseTO = userHttpClient.createRemoteRequest(baseTO);
					LOGGER.debug("******CentralDataExtractor :  invoking Acknowledge method for changing  status *********END ******");
				} else {
					LOGGER.debug("CentralDataExtractor :  invoking Acknowledge method ######## Data Received with Inturruption /aborted status### ");
					throw new CGBusinessException();
				}
			}

		}catch (CGBusinessException e) { 
			LOGGER.error("CentralDataExtractor::dataExtractor::Exception occured:"
					+e.getMessage());
			moveFilesToErrorDir(localErrorDirPath, localUnZipDir, e);
			
		}
		
		catch (Exception e) {
			LOGGER.error("CentralDataExtractor::dataExtractor::Exception occured:"
					+e.getMessage());
			moveFilesToErrorDir(localErrorDirPath, localUnZipDir, e);
		}  
		
		
		LOGGER.debug("*********START*******CentralDataExtractor : dataExtractor() : Visiting the manual folder, data Reading  from local directory ["+localManualDirPath+"]");
		readDataForAllProcess(localManualDirPath);
		
		LOGGER.debug("*********END*******CentralDataExtractor : dataExtractor() :  Visited the manual folder , data Reading  from  ["+localManualDirPath+"]");
	
		
	}


	/**
	 * Read data for all process.
	 *
	 * @param localUnZipFolder1 the local un zip folder1
	 * @throws Exception the exception
	 */
	private synchronized void readDataForAllProcess(String localUnZipFolder1)throws CGBusinessException{
		try {
			readBookingXML(localUnZipFolder1);
			readManifestXML(localUnZipFolder1);
			readHeldUpReleaseXML(localUnZipFolder1);
			readGoodsCanclXML(localUnZipFolder1);
			readGoodsRenewalXML(localUnZipFolder1);
			readDispatchDataXML(localUnZipFolder1);
			readGoodsIssueXML(localUnZipFolder1);
			// Added by Narasimha Rao Kattunga
			readDlvMnfstXML(localUnZipFolder1);
			readRTOXML(localUnZipFolder1);
		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readDataForAllProcess::Exception occured:"
					+e.getMessage());
		}
	}

	/**
	 * Move files to error dir.
	 *
	 * @param localErrorDirPath the local error dir path
	 * @param localUnZipDir the local un zip dir
	 * @param e the e
	 * @throws Exception the exception
	 */
	private void moveFilesToErrorDir(String localErrorDirPath,
			File localUnZipDir, Exception e) throws CGBusinessException{
		// IF ERROR / EXCEPTION , MOVE XML FILES TO ERROR FOLDER
		LOGGER.debug("CentralDataExtractor : dataExtractor() : IF ERROR / EXCEPTION , MOVE XML FILES TO ERROR FOLDER - START");
		LOGGER.debug("CentralDataExtractor : dataExtractor() : IF ERROR / EXCEPTION , MOVE XML FILES TO ERROR FOLDER - "
				+ e);
		File[] filesError = null;
		
		try {
			if(localUnZipDir != null){
			filesError = localUnZipDir.listFiles();
			LOGGER.debug("CentralDataExtractor : dataExtractor() : filesError"
					+ filesError);

			if (filesError != null && filesError.length > 0) {
				for (int k = 0; k < filesError.length; k++) {
					LOGGER.debug("CentralDataExtractor : dataExtractor() : filesError Name "
							+ filesError[k].getName());
					CGXMLUtil.moveFile(localErrorDirPath, filesError[k]);
				}
			}
}
		} catch (Exception e1) {
			LOGGER.error("CentralDataExtractor::moveFilesToErrorDir::Exception occured:"
					+e.getMessage());
		}
		LOGGER.debug("Error Occured in ExtractOutgoingManifest : dataExtractor() :"
				+ e.getMessage());
		LOGGER.debug("CentralDataExtractor : dataExtractor() : IF ERROR / EXCEPTION , MOVE XML FILES TO ERROR FOLDER - END");
	}	
	/**
	 * Folder rename.
	 *
	 * @param dirPath the dir path
	 */
	private void folderRename(String dirPath){
		if(!StringUtil.isEmpty(dirPath)){
		File oldFolder = new File(dirPath);
		String newPath= null;
		if(oldFolder.exists()){
		if(dirPath.endsWith(BranchDBAdminConstant.ZIP_FILE_EXTENSION)){
			newPath = dirPath.substring(0,dirPath.lastIndexOf(ApplicationConstants.CHARACTER_DOT));
			newPath = newPath +ApplicationConstants.DIR_PROCESSED+BranchDBAdminConstant.ZIP_FILE_EXTENSION;
		}else{
			newPath = dirPath +ApplicationConstants.DIR_PROCESSED;
		}
		File newFolder = new File(newPath);
		
		if(!oldFolder.renameTo(newFolder)){
			LOGGER.debug("Folder Renaming does not happend for the path :["+dirPath+"]");
		}else{
			LOGGER.debug("Folder Renaming has done successfully for the  old path :["+dirPath+"] and New path :["+newPath+"]" );
		}
		}
		}else {
			LOGGER.debug("Folder path is empty :["+dirPath+"]" );
		}
	}

	/**
	 * Read booking xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readBookingXML(String fileDir) throws CGBusinessException{
		
		BookingDetailsData jaxbObj = null;
		File[] files =null;
		List<BookingDO> bookingDoList =null;
		try {

			LOGGER.debug("START of readBookingXML....");
			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.BOOKING_INBOUND_FILE_MATCH);
			LOGGER.debug("Match Criteria = [" + fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("Number of files = [" + files.length
					+ " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					try {
						
						
						LOGGER.debug("START of reading file at location ..."
								+ inboundFile);
						// 2. Unmarshal the XML file to the Jaxb Object
						jaxbObj = (BookingDetailsData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.BOOKING_FLAG);
						LOGGER.debug("UNmarshalled the object ..." + jaxbObj);
						
						LOGGER.debug("Copied from JAXB object to the TO object ...");
						// 4. Accessing the service and update the data
						if (jaxbObj != null) {
							LOGGER.debug("No of records in TO object..."
									+ jaxbObj.getBookingDetails().size());

							/* Save Booking Data to Local Branch */
							
							
							localDBDataPersistService.persistBookingDataToLocalDB(jaxbObj);
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								LOGGER.error("readBookingXML() : DATA SAVED TO BOOKING TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readBookingXML::Exception occured:"
										+e.getMessage());
							}
							LOGGER.debug("ARchive the file ...");
						}
					} catch (CGBusinessException e) {
						LOGGER.error("readBookingXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						LOGGER.error("CentralDataExtractor::readBookingXML::Exception occured:"
								+e.getMessage());
						//Handle the problem**************START
						List<String> faultConsgList = new ArrayList<String>();
						bookingDoList  = localDBDataPersistService.getBookingDoListFromJAXB(jaxbObj);
						for( BookingDO bookingD: bookingDoList){
							try{

								localDBDataPersistService.persistBookingDoToLocalDB(bookingD);


							}catch(Exception exp){
								faultConsgList.add(bookingD.getConsignmentNumber()+" :"+ exp.getMessage()+ApplicationConstants.CHARACTER_NEW_LINE);
								LOGGER.error("readBookingXML() : ERROR HAPPEND DURING SAVING PROCESS(###### saving Booking Do one by one ###########):for  File Name - [ "
										+ inboundFile.getName()+" ]"+"Exception :"+exp.getMessage());
							}
						}
						
						if(faultConsgList!=null && !faultConsgList.isEmpty()&& faultConsgList.size() != bookingDoList.size()){
							
							try {
								moveFileToErrorLog(inboundFile, faultConsgList);
							} catch (Exception e1) {
								LOGGER.error("readBookingXML() :moving file to Error Log File: File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readBookingXML::Exception occured:"
										+e1.getMessage());
							}
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception innerExp) {
								LOGGER.error("readBookingXML() : DATA SAVED TO BOOKING TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readBookingXML::Exception occured:"
										+innerExp.getMessage());
							}
							
						}else{
							CGXMLUtil
							.moveFile(localErrorDirPath,
									inboundFile);
						}
						//Handle the problem***************END
						
						
					}catch (Exception e) {
						LOGGER.error("readBookingXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
				}
			}
			LOGGER.debug("END of readBookingXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readBookingXML::Exception occured:"
					+e.getMessage());
			
			moveFilesToErrorFolder(files);
			
		}
	}


	/**
	 * Move file to error log.
	 *
	 * @param inboundFile the inbound file
	 * @param faultConsList the fault cons list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void moveFileToErrorLog(File inboundFile,
			List<String> faultConsList) throws IOException {
		LOGGER.debug("moveFileToErrorLog ---START");
		LOGGER.debug("moveFileToErrorLog ---File Name :["+inboundFile+"]");
		File f = new File(localErrorLogDirPath+File.separator+inboundFile.getName());
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		fw.write(faultConsList.toString().replace(",", ""));
		fw.close();
		LOGGER.debug("moveFileToErrorLog ---END");
	}


	/**
	 * Move files to error folder.
	 *
	 * @param files the files
	 */
	private void moveFilesToErrorFolder(File[] files) {
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				File erroredFile = files[i];
				try {
					CGXMLUtil
							.moveFile(localErrorDirPath,
											erroredFile);
				} catch (Exception error) {
					LOGGER.error("ERROR HAPPEND DURING MOVING THE FILE TO ERROR FOLDER : File Name - "
							+ erroredFile.getName());
					LOGGER.error("CentralDataExtractor::moveFilesToErrorFolder::Exception occured:"
							+error.getMessage());
				}
			}
		}
	}

	/**
	 * Read manifest xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readManifestXML(String fileDir) throws CGBusinessException {
		ManifestData jaxbObj =null;
		File[] files=null;
		try {

			LOGGER.debug("START of readManifestXML....");

			// 1. Read the XML file(s) from the remote location which satisfies
			// the match criteria.
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.MANIFEST_INBOUND_FILE_MATCH);
			LOGGER.debug("Match Criteria = [" + fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("Number of files = [" + files.length
					+ " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files !=null && files.length > 0) {
				
				LOGGER.debug("readManifestXML() : Number of files = ["
						+ files.length + " ] And List of Files = [" + files
						+ " ]");
				//creating channel for each file(XML) and applying lock
				//Modified date :13-March-2012
				FileChannel channel=null;
				FileLock lock=null;
				
				
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					
					RandomAccessFile raF =null;
					LOGGER.debug("readManifestXML() : file name [" +inboundFile.getName()+ " ]");
					try {
						raF =new RandomAccessFile(inboundFile,"rws");
						 channel  =raF.getChannel(); 
						if( channel !=null && channel.isOpen()){ 
							lock = channel.lock(); 
							
					    if( lock != null && lock.isValid()){					
					    	LOGGER.debug("readManifestXML() : Start of reading files at location ..." + inboundFile.getName());
					    	
						jaxbObj = (ManifestData) new CTBSXMLParser()
								.unmarshalXMLToObjectByStream(raF,BusinessConstants.MANIFEST_FLAG);
						LOGGER.debug("UNmarshalled the object ..." + jaxbObj);

						
						

						// 4. Accessing the service and update the data
						if (jaxbObj != null) {
							LOGGER.debug("No of records in TO(Manifest) object..."
									+ jaxbObj.getManifest().size());

							/* Save Manifest Data to Local Branch */
							localDBDataPersistService
									.persistManifestDataToLocalDB(jaxbObj);

							
							try {
				    			CGXMLUtil.copyAndMoveFile(localArchiveDirPath,	raF,inboundFile.getName());
				    		}  catch (Exception e) {
				    			LOGGER.error("readManifestXML() : DATA SAVED TO GOODS ISSUE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
				    					+ inboundFile.getName()+"Exception :"+e.getMessage());
				    		}
				    		
				    		
				    		if( lock !=null && lock.isValid()){
					    		LOGGER.debug("readManifestXML() : releasing the lock ... fot the file name ["+inboundFile.getName()+"]");
					    		lock.release();
					    	}
				    		if(channel !=null && channel.isOpen()){
					    		channel.close();
					    		inboundFile.delete();
					    		
					    	}
				    		LOGGER.debug("readManifestXML() ::Archive the file ...["+inboundFile.getName()+"]");
				    		
				    		
				    	}
				    	
				    	
				    }
					}
				}catch(ClosedChannelException closed){
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest.... ClosedChannelException"+closed.getMessage());
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest....Exception and CONTINUING THE PROCESS");
					continue;
				}catch(OverlappingFileLockException fileLock){
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest.... OverlappingFileLockException"+fileLock.getMessage());
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest....OverlappingFileLockException and CONTINUING THE PROCESS");
					continue;
				}catch(IOException io){
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest.... IOException"+io.getMessage());
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest....IOException and CONTINUING THE PROCESS");
					continue;
				}catch(NonWritableChannelException  nce){
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest.... NonWritableChannelException"+nce.getMessage());
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest....NonWritableChannelException and CONTINUING THE PROCESS");
					continue;
				}catch(NonReadableChannelException  nrce){
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest.... NonReadableChannelException"+nrce.getMessage());
					LOGGER.error("CentralDataExtractor.readManifestXML() in Manifest....NonReadableChannelException and CONTINUING THE PROCESS");
					continue;
				}
				catch(CGBusinessException e){
					LOGGER.error("CentralDataExtractor.readManifestXML : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
							+ inboundFile.getName()+" ]TO ERROR FOLDER"+"CGBusinessException :"+e.getMessage());
					
					
					
					
					if( lock !=null && !lock.isValid()){
						lock.release();
						}
					
					if(channel !=null && channel.isOpen()){
			    		channel.close();
			    	}
					CGXMLUtil
					.moveFile(localErrorDirPath,
							inboundFile);
					
				}	
				catch (Exception e) {
					LOGGER.error("CentralDataExtractor.readManifestXML : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
							+ inboundFile.getName()+" ]TO ERROR FOLDER"+"Exception :"+e.getMessage());
					
					if( lock !=null && !lock.isValid()){
						lock.release();
						}
					if(channel !=null && channel.isOpen()){
			    		channel.close();
			    	}
					
					CGXMLUtil
					.moveFile(localErrorDirPath,
							inboundFile);
					
				}
				finally{
					if(raF != null ){
						raF.close();
					}
				}
				
				}
			}else{
				LOGGER.debug("No matching file found  in readManifestXML....");
			}
			LOGGER.debug("END of readManifestXML....");

		} catch (Exception e) {
			
			LOGGER.error("CentralDataExtractor::readManifestXML()::Exception occured while updating the database for Manifest "+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Read rtoxml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readRTOXML(String fileDir) throws CGBusinessException{
		RtoData jaxbObj =null;
		File[] files=null;
		try {
			LOGGER.debug("START of readRTOXML....");

			// 1. Read the XML file(s) from the remote location which satisfies
			// the match criteria.
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.RTO_DATA_FILE_MATCH);
			LOGGER.debug("Match Criteria = [" + fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("Number of files = [" + files.length
					+ " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files !=null && files.length > 0) {
				
				LOGGER.debug("readRTOXML() : Number of files = ["
						+ files.length + " ] And List of Files = [" + files
						+ " ]");
				//creating channel for each file(XML) and applying lock
				//Modified date :13-March-2012
				FileChannel channel=null;
				FileLock lock=null;
				
				
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					
					RandomAccessFile raF =null;
					LOGGER.debug("readRTOXML() : file name [" +inboundFile.getName()+ " ]");
					try {
						raF =new RandomAccessFile(inboundFile,"rws");
						 channel  =raF.getChannel(); 
						if( channel !=null && channel.isOpen()){
							lock = channel.lock(); 
							
					    if( lock != null && lock.isValid()){					
					    	LOGGER.debug("readRTOXML() : Start of reading files at location ..." + inboundFile.getName());
					    	
						jaxbObj = (RtoData) new CTBSXMLParser()
								.unmarshalXMLToObjectByStream(raF,BusinessConstants.RTO_FLAG);
						LOGGER.debug("UNmarshalled the object ..." + jaxbObj);						
						

						// 4. Accessing the service and update the data
						if (jaxbObj != null) {
							LOGGER.debug("No of records in TO(RTO) object..."
									+ jaxbObj.getRto().size());

							/* Save Rto Data to Local Branch */
							localDBDataPersistService
									.persistRtoDataToLocalDB(jaxbObj);

							
							try {
				    			CGXMLUtil.copyAndMoveFile(localArchiveDirPath,	raF,inboundFile.getName());
				    		}/* catch (IOException e) {
				    			LOGGER.error("readRTOXML() : DATA SAVED TO GOODS ISSUE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
				    					+ inboundFile.getName()+"IOException :"+e.getMessage());
				    		}*/ catch (Exception e) {
				    			LOGGER.error("readRTOXML() : DATA SAVED TO GOODS ISSUE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
				    					+ inboundFile.getName()+"Exception :"+e.getMessage());
				    		}
				    		
				    		
				    		if( lock !=null && lock.isValid()){
					    		LOGGER.debug("readRTOXML() : releasing the lock ... fot the file name ["+inboundFile.getName()+"]");
					    		lock.release();
					    	}
				    		if(channel !=null && channel.isOpen()){
					    		channel.close();
					    		inboundFile.delete();
					    		
					    	}
				    		LOGGER.debug("readRTOXML() ::Archive the file ...["+inboundFile.getName()+"]");
				    	}
					    }
					}
				}catch(ClosedChannelException closed){
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO.... ClosedChannelException"+closed.getMessage());
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO....Exception and CONTINUING THE PROCESS");
					continue;
				}catch(OverlappingFileLockException fileLock){
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO.... OverlappingFileLockException"+fileLock.getMessage());
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO....OverlappingFileLockException and CONTINUING THE PROCESS");
					continue;
				}catch(IOException io){
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO.... IOException"+io.getMessage());
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO....IOException and CONTINUING THE PROCESS");
					continue;
				}catch(NonWritableChannelException  nce){
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO.... NonWritableChannelException"+nce.getMessage());
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO....NonWritableChannelException and CONTINUING THE PROCESS");
					continue;
				}catch(NonReadableChannelException  nrce){
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO.... NonReadableChannelException"+nrce.getMessage());
					LOGGER.error("CentralDataExtractor.readRTOXML() in RTO....NonReadableChannelException and CONTINUING THE PROCESS");
					continue;
				}
				catch(CGBusinessException e){
					LOGGER.error("CentralDataExtractor.readRTOXML : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
							+ inboundFile.getName()+" ]TO ERROR FOLDER"+"CGBusinessException :"+e.getMessage());
					
					if( lock !=null && !lock.isValid()){
						lock.release();
						}
					
					if(channel !=null && channel.isOpen()){
			    		channel.close();
			    	}
					CGXMLUtil
					.moveFile(localErrorDirPath,
							inboundFile);
					
				}	
				catch (Exception e) {
					LOGGER.error("CentralDataExtractor.readRTOXML : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
							+ inboundFile.getName()+" ]TO ERROR FOLDER"+"Exception :"+e.getMessage());
					
					if( lock !=null && !lock.isValid()){
						lock.release();
						}
					if(channel !=null && channel.isOpen()){
			    		channel.close();
			    	}
					
					CGXMLUtil
					.moveFile(localErrorDirPath,
							inboundFile);
					
				}
				finally{
					if(raF != null ){
						raF.close();
					}
				}
				
				}
			}else{
				LOGGER.debug("No matching file found  in readRTOXML....");
			}
			LOGGER.debug("END of readRTOXML....");

		} catch (Exception e) {			
			LOGGER.error("CentralDataExtractor::readRTOXML()::Exception occured while updating the database for RTO "+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}
	
	/**
	 * Read held up release xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readHeldUpReleaseXML(String fileDir) throws CGBusinessException {
		HeldupReleaseData jaxbObj = null;
		File[] files=null;
		try {

			LOGGER.debug("START of readHeldUpReleaseXML....");

			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.HELDUP_INBOUND_FILE_MATCH);
			LOGGER.debug("Match Criteria = [" + fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("Number of files = [" + files.length
					+ " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					LOGGER.debug("START of reading file at location ..."
							+ inboundFile.getName());

					try {
						// 2. Unmarshal the XML file to the Jaxb Object
						jaxbObj = (HeldupReleaseData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.HELDUP_RELEASE_FLAG);
						LOGGER.debug("UNmarshalled the object ..." + jaxbObj);

						
						if (jaxbObj != null
								&& !jaxbObj.getHeldupRelease().isEmpty()) {
							LOGGER.debug("No of records in TO object..."
									+ jaxbObj.getHeldupRelease().size());

							/* Save Heldup Data to Local Branch */
							localDBDataPersistService
									.persistHeldUpReleaseToLocalDB(jaxbObj);

							
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								LOGGER.error("readHeldUpReleaseXML() : DATA SAVED TO HELDUP RELEASE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readHeldUpReleaseXML::Exception occured:"
										+e.getMessage());
							}
							LOGGER.debug("ARchive the file ...");
						}
					} catch (Exception e) {
						LOGGER.error("readHeldUpReleaseXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
				}
			}
			LOGGER.debug("END of readHeldUpReleaseXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readHeldUpReleaseXML::Exception occured:"
					+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Read goods renewal xml.
	 * 
	 * @param fileDir
	 *            the file dir
	 * @throws Exception
	 *             the exception
	 */
	public void readGoodsRenewalXML(String fileDir) throws CGBusinessException{
		GoodsRenewalData jaxbObj = null;
		File[] files=null;
		try {

			LOGGER.debug("readGoodsRenewalXML() : Start readGoodsRenewalXML()....");

			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.GOODS_RENEWAL_INBOUND_FILE_MATCH);
			LOGGER.debug("readGoodsRenewalXML() :  Match Criteria = ["
					+ fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("readGoodsRenewalXML() : Number of files = ["
					+ files.length + " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					LOGGER.debug("readGoodsRenewalXML() : Start of reading files at location ..."
							+ inboundFile.getName());

					// 2. Unmarshal the XML file to the Jaxb Object
					try {
						jaxbObj = (GoodsRenewalData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.GOODS_RENEWAL_FLAG);
						LOGGER.debug("readGoodsRenewalXML() :  Un-marshalled the object ..."
								+ jaxbObj);

						

						// 4. Accessing the service and update the data
						if (jaxbObj != null && !jaxbObj.getGoodsRenewal().isEmpty()) {
							LOGGER.debug("readGoodsRenewalXML() : No of records in TO object..."
									+ jaxbObj.getGoodsRenewal().size());

							/* Save Manifest Data to Local Branch */
							localDBDataPersistService
									.persistGoodsRenewalToLocalDB(jaxbObj);

							

							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								LOGGER.error("readGoodsRenewalXML() : DATA SAVED TO GOODS RENEWAL TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readGoodsRenewalXML::Exception occured:"
										+e.getMessage());
							}
							LOGGER.debug("Archive the file ...");
						}
					} catch (Exception e) {
						LOGGER.error("readGoodsRenewalXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
				}
			}
			LOGGER.debug("END of readGoodsRenewalXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readGoodsRenewalXML::Exception occured:"
					+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Read goods cancl xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readGoodsCanclXML(String fileDir) throws CGBusinessException{
		GoodsCancellationData jaxbObj = null;
		File[] files=null;
		try {

			LOGGER.debug("Start readGoodsCanclXML()....");

			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.GOODS_CANCL_FILE_MATCH);
			LOGGER.debug("readGoodsCanclXML() : Match Criteria = ["
					+ fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("readGoodsCanclXML() : Number of files = ["
					+ files.length + " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					LOGGER.debug("readGoodsCanclXML() : Start of reading files at location ..."
							+ inboundFile.getName());

					try {
						// 2. Unmarshal the XML file to the Jaxb Object
						jaxbObj = (GoodsCancellationData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.GOODS_CANCELLATION_FLAG);
						LOGGER.debug("readGoodsCanclXML() : Un-marshalled the object ..."
								+ jaxbObj);

						

						// 4. Accessing the service and update the data
						if (jaxbObj != null
								&& !jaxbObj.getGoodsCancellation().isEmpty()) {
							LOGGER.debug("readGoodsCanclXML() : No of records in TO object..."
									+ jaxbObj.getGoodsCancellation().size());

							/* Save Manifest Data to Local Branch */
							localDBDataPersistService
									.persistGoodsCanclToLocalDB(jaxbObj);


							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								LOGGER.error("readGoodsCanclXML() : DATA SAVED TO GOODS CANCELLATION TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readGoodsCanclXML::Exception occured:"
										+e.getMessage());
							}
							LOGGER.debug("Archive the file ...");
						}
					} catch (Exception e) {
						LOGGER.error("readGoodsCanclXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
				}
			}
			LOGGER.debug("END of readGoodsCanclXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readGoodsCanclXML::Exception occured:"
					+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Read dispatch data xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readDispatchDataXML(String fileDir) throws CGBusinessException {
		DispatchDetailsData jaxbObj =null;
		File[] files=null;
		try {

			LOGGER.debug("Start readDispatchDataXML()....");

			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.DISPATCH_DATA_FILE_MATCH);
			LOGGER.debug("Match Criteria = [" + fileNameMatchCriteria + " ]");
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			LOGGER.debug("Number of files = [" + files.length
					+ " ] And List of Files = [" + files + " ]");

			// if files exists in the location, iterate and create file objects
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					LOGGER.debug("Start of reading files at location ..."
							+ inboundFile.getName());

					// 2. Unmarshal the XML file to the Jaxb Object
					try {
						jaxbObj = (DispatchDetailsData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.DISPACH_FLAG);
						LOGGER.debug("Un-marshalled the object ..." + jaxbObj);

						

						// 4. Accessing the service and update the data
						if (jaxbObj != null
								&& !jaxbObj.getDispatchDetails().isEmpty()) {
							LOGGER.debug("No of records in TO object..."
									+ jaxbObj.getDispatchDetails().size());

							/* Save Dispatch Data to Local Branch */
							localDBDataPersistService
									.persistDispatchDetailsToLocalDB(jaxbObj);
							
							CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							
							LOGGER.debug("Archive the file ...");
						}
					} catch (Exception e) {
						LOGGER.error("CentralDataExtractor.readDispatchDataXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
				}
			}
			LOGGER.debug("END of readDispatchDataXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readDispatchDataXML::Exception occured:"
					+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Read goods issue xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readGoodsIssueXML(String fileDir) throws CGBusinessException {
		GoodsIssueData jaxbObj =null;
		File[] files=null;
		try {

			LOGGER.debug("Start readGoodsIssueXML()....");

			// 1. Read the XML file(s) from the remote location which satisfies
			// the match criteria.
			// String fileDir =
			LOGGER.debug("(readGoodsIssueXML)Directory  = [" + fileDir + " ]");
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.GOODS_ISSUE_DATA_FILE_MATCH);
			LOGGER.debug("readGoodsIssueXML() : Match Criteria = ["
					+ fileNameMatchCriteria + " ]");
			files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);

			// if files exists in the location, iterate and create file objects
			if (files != null && files.length > 0) {
				LOGGER.debug("readGoodsIssueXML() : Number of files = ["
						+ files.length + " ] And List of Files = [" + files
						+ " ]");
				//creating channel for each file(XML) and applying lock
				//Modified date :17-feb-2012
				FileChannel channel=null;
				FileLock lock=null;
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					RandomAccessFile raF =null;
					LOGGER.debug("readGoodsIssueXML() : file name [" +inboundFile.getName()+ " ]");
					try {
						raF =new RandomAccessFile(inboundFile,"rws");
						 channel  =raF.getChannel(); 
						if( channel !=null && channel.isOpen()){ 
							lock = channel.lock(); 
							
					    if( lock != null && lock.isValid()){					
					    	LOGGER.debug("readGoodsIssueXML() : Start of reading files at location ..." + inboundFile.getName());
						// 2. Unmarshal the XML file to the Jaxb Object					
					    	jaxbObj = (GoodsIssueData) new CTBSXMLParser().unmarshalXMLToObjectByStream(raF, BusinessConstants.GOODS_ISSUE_FLAG);
					    	LOGGER.debug("readGoodsIssueXML() : Un-marshalled the object ..."+ jaxbObj);

					    	if (jaxbObj != null && !jaxbObj.getGoodsIssue().isEmpty()) {
					    		LOGGER.debug("readGoodsIssueXML() : No of records in TO object..."
					    				+ jaxbObj.getGoodsIssue().size());

					    		/* Save Goods Issue Data to Local Branch */
					    		localDBDataPersistService.persistGoodsIssueDetailsToLocalDB(jaxbObj);
					    		
					    		
					    		
						    	
					    		try {
					    			CGXMLUtil.copyAndMoveFile(localArchiveDirPath,	raF,inboundFile.getName());
					    		} /*catch (IOException e) {
					    			LOGGER.error("readGoodsIssueXML() : DATA SAVED TO GOODS ISSUE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
					    					+ inboundFile.getName()+"IOException :"+e.getMessage());
					    		}*/ catch (Exception e) {
					    			LOGGER.error("readGoodsIssueXML() : DATA SAVED TO GOODS ISSUE TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
					    					+ inboundFile.getName()+"Exception :"+e.getMessage());
					    		}
					    		
					    		
					    		if( lock !=null && lock.isValid()){
						    		LOGGER.debug("readGoodsIssueXML() : releasing the lock ... fot the file name ["+inboundFile.getName()+"]");
						    		lock.release();
						    	}
					    		if(channel !=null && channel.isOpen()){
						    		channel.close();
						    		inboundFile.delete();
						    		
						    	}
					    		LOGGER.debug("readGoodsIssueXML() ::Archive the file ...["+inboundFile.getName()+"]");
					    		
					    		
					    	}
					    	
					    	
					    }
						}
					}catch(ClosedChannelException closed){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue.... ClosedChannelException"+closed.getMessage());
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue....Exception and CONTINUING THE PROCESS");
						continue;
					}catch(OverlappingFileLockException fileLock){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue.... OverlappingFileLockException"+fileLock.getMessage());
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue....OverlappingFileLockException and CONTINUING THE PROCESS");
						continue;
					}catch(IOException io){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue.... IOException"+io.getMessage());
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue....IOException and CONTINUING THE PROCESS");
						continue;
					}catch(NonWritableChannelException  nce){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue.... NonWritableChannelException"+nce.getMessage());
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue....NonWritableChannelException and CONTINUING THE PROCESS");
						continue;
					}catch(NonReadableChannelException  nrce){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue.... NonReadableChannelException"+nrce.getMessage());
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() in GoodsIssue....NonReadableChannelException and CONTINUING THE PROCESS");
						continue;
					}
					catch(CGBusinessException e){
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
								+ inboundFile.getName()+" ]TO ERROR FOLDER"+"CGBusinessException :"+e.getMessage());
						
						
						
						
						if( lock !=null && !lock.isValid()){
							lock.release();
							}
						
						if(channel !=null && channel.isOpen()){
				    		channel.close();
				    	}
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
						
					}	
					catch (Exception e) {
						LOGGER.error("CentralDataExtractor.readGoodsIssueXML : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - ["
								+ inboundFile.getName()+" ]TO ERROR FOLDER"+"Exception :"+e.getMessage());
						
						
						if( lock !=null && !lock.isValid()){
							lock.release();
							}
						if(channel !=null && channel.isOpen()){
				    		channel.close();
				    	}
						
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
						
					}
					finally{
						if(raF != null ){
							raF.close();
						}
					}
					
					
					
					
				}
			}else{
				LOGGER.debug("No matching file found  in GoodsIssue....");
			}
			LOGGER.debug("END of readGoodsIssueXML....");

		} catch (Exception e) {
			
			LOGGER.error("CentralDataExtractor::readGoodsIssueXML():: Exception occured while updating the database for Goods readGoodsIssueXML "+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

	/**
	 * Added by Narasimha Rao Kattunga Reading the Delivery manifest xml.
	 *
	 * @param fileDir the file dir
	 * @throws Exception the exception
	 */
	public void readDlvMnfstXML(String fileDir) throws CGBusinessException{
		DeliveyManifestData jaxbObj =null;
		File[] files=null;
		try {
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.DELIVERY_MANIFEST_DATA_FILE_MATCH);
			 files = CGXMLUtil.retrieveMatchFileNames(fileDir,
					fileNameMatchCriteria, BranchDBAdminConstant.XMLFILE_EXTN);
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File inboundFile = files[i];
					
					LOGGER.debug("readDlvMnfstXML() : Start of reading files at location ..."
							+ inboundFile.getName());
					try {
						jaxbObj = (DeliveyManifestData) new CTBSXMLParser()
								.unmarshalXMLToObject(inboundFile, BusinessConstants.DELIVERY_FLAG);
						if (jaxbObj != null
								&& !jaxbObj.getDeliveryManifest().isEmpty()) {
							/* Save Goods Issue Data to Local Branch */
							localDBDataPersistService
									.persistDlvMnfstDetailsToLocalDB(jaxbObj);
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								LOGGER.error("readDlvMnfstXML() : Data does not saved into Delivery table : Error occured : File Name - "
										+ inboundFile.getName());
								LOGGER.error("CentralDataExtractor::readDlvMnfstXML::Exception occured:"
										+e.getMessage());
							}
							LOGGER.debug("Archive the file ...");
						}
					} catch (Exception e) {
						LOGGER.error("readDlvMnfstXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						CGXMLUtil
						.moveFile(localErrorDirPath,
								inboundFile);
					}
					
					
				}
			}
			LOGGER.debug("END of readDlvMnfstXML....");

		} catch (Exception e) {
			LOGGER.error("CentralDataExtractor::readDlvMnfstXML::Exception occured:"
					+e.getMessage());
			moveFilesToErrorFolder(files);
		}
	}

}
