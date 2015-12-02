/**
 * 
 */
package com.dtdc.bodbadmin.dataextractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.bs.LocalDBDataPersistService;
import com.dtdc.bodbadmin.constant.BranchDBAdminConstant;
import com.dtdc.bodbadmin.integration.UserHttpClient;
import com.dtdc.bodbadmin.ziputil.ZipUtility;
import com.dtdc.to.manifestextractor.DataExtractorTO;

/**
 * @author nisahoo
 * 
 * 
 */
public class CentralMasterDataExtractor {

	private static final Logger LOGGER = Logger
			.getLogger(CentralDataExtractor.class);
	
	
	private UserHttpClient userHttpClient;
	private Properties messageProp;
	private LocalDBDataPersistService localDBDataPersistService;
	
	 Boolean isWindows;
	 String localZipDirPath =null;
	 String localUnZipFolder =null;
	 String localUnZipDirPath =null;
	 String localUnZipTempDirPath =null;
	 String localErrorDirPath =null;
	 String localArchiveDirPath =null;
	 String localManualDirPath =null;
	 String localErrorLogDirPath =null;
	
		public void setMessageProp(Properties messageProp) {
			this.messageProp = messageProp;
		}


		public void setUserHttpClient(UserHttpClient userHttpClient) {
			this.userHttpClient = userHttpClient;
		}

		public void setLocalDBDataPersistService(
				LocalDBDataPersistService localDBDataPersistService) {
			this.localDBDataPersistService = localDBDataPersistService;
		}

	/**
	 * 
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
	 * The function is invoked by Quartz Scheduler to fetch Outgoing Manifest &
	 * Booking Data and persist the data to local branch DB.
	 */
	public void masterDataExtractor() throws CGBusinessException,Exception {
		
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

					localUnZipFolder = localUnZipDirPath + File.separator
					+ branchCode;
					
					LOGGER.debug("CentralDataExtractor : dataExtractor() : CREATED ZIP FILES - START");
					// Create the Zip File at the Local Branch
					
					File localZipDir = new File(localZipDirPath);

						if(localZipDir != null && !localZipDir.exists()){// creates directory if not Exist
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
						
						readDataForMasterProcess(localUnZipFolder1);
					}
					LOGGER.debug("CentralDataExtractor : dataExtractor() : READ THE UN ZIPPED FOLDER AND PARSE XML - END");
					
					LOGGER.debug("CentralDataExtractor : dataExtractor() : END");
			

		}catch (CGBusinessException e) { 
			LOGGER.error("CentralDataExtractor :  dataExtractor method ######## EXception Occurred### " +e.getMessage());
			moveFilesToErrorDir(localErrorDirPath, localUnZipDir, e);
			//throw e;
			
		}
		
		catch (Exception e) {
			LOGGER.error("CentralDataExtractor :  dataExtractor method ######## EXception Occurred### " +e.getMessage());
			moveFilesToErrorDir(localErrorDirPath, localUnZipDir, e);
		} 
		
		
		LOGGER.debug("*********START*******CentralDataExtractor : dataExtractor() : Visiting the manual folder, data Reading  from local directory ["+localManualDirPath+"]");
		readDataForMasterProcess(localManualDirPath);
		
		LOGGER.debug("*********END*******CentralDataExtractor : dataExtractor() :  Visited the manual folder , data Reading  from  ["+localManualDirPath+"]");
	
		
	}


	/**
	 * @param localUnZipFolder1
	 * @throws Exception
	 */
	private synchronized void readDataForMasterProcess(String localUnZipFolder1)throws CGBusinessException {

		try {
			masterRead(localUnZipFolder1);
		} catch (Exception e) {
			LOGGER.error("CentralMasterDataExtractor::readDataForMasterProcess::Exception occured:"
					+ e.getMessage());
		}
	}

	/**
	 * @param localErrorDirPath
	 * @param localUnZipDir
	 * @param e
	 * @throws Exception
	 */
	private void moveFilesToErrorDir(String localErrorDirPath,
			File localUnZipDir, Exception e)throws CGBusinessException{
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
			// TODO Auto-generated catch block
			LOGGER.error("Error / Exception occured during moving file to error folder :"
					+ e.getMessage());
		}
		LOGGER.debug("Error Occured in ExtractOutgoingManifest : dataExtractor() :"
				+ e.getMessage());
		LOGGER.debug("CentralDataExtractor : dataExtractor() : IF ERROR / EXCEPTION , MOVE XML FILES TO ERROR FOLDER - END");
	}
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
	 * @param files
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
					// TODO Auto-generated catch block
					LOGGER.error("ERROR HAPPEND DURING MOVING THE FILE TO ERROR FOLDER : File Name - "
							+ erroredFile.getName());
				}
			}
		}
	}


public void masterRead(String fileDir)throws CGBusinessException{
		
		JSONObject jsonObject = null;
		File[] files =null;
		List<CGBaseEntity> baseEntity =null;
		try {
 
			LOGGER.debug("START of readBookingXML....");
			LOGGER.debug("START of readBookingXML.... ddd");
			
			String fileNameMatchCriteria = messageProp
					.getProperty(BranchDBAdminConstant.MASTER_TABLES_FILE_MATCH);
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
						
						
						LOGGER.debug("START of reading file at locations ..."
								+ inboundFile);
						// 2. Unmarshal the XML file to the Jaxb Object
						
						jsonObject =getJsonFromFile(inboundFile);
						baseEntity =getBaseEntityFromJson(jsonObject);
						
						LOGGER.debug("Copied from JAXB object to the TO object ...");
						// 4. Accessing the service and update the data
							LOGGER.debug("No of records in TO object..."
									+ baseEntity.size());

							/* Save  Data to Local Branch */
							Boolean status =localDBDataPersistService.persistBaseEntityToLocalDB(baseEntity);
							
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								LOGGER.error("readBookingXML() : DATA SAVED TO BOOKING TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
							}
							LOGGER.debug("ARchive the file ...");
					} catch (CGBusinessException e) {
						LOGGER.error("readMasterDataXML() : ERROR HAPPEND DURING SAVING PROCESS:Hence moving  File Name - [ "
								+ inboundFile.getName()+" ] TO ERROR FOLDER"+"Exception :"+e.getMessage());
						//Handle the problem**************START
						List<String> faultConsgList = new ArrayList<String>();
						baseEntity =getBaseEntityFromJson(jsonObject);
							try{

								localDBDataPersistService.persistBaseEntityToLocalDB(baseEntity);


							}catch(Exception exp){
								faultConsgList.add(exp.getMessage()+ApplicationConstants.CHARACTER_NEW_LINE);
								LOGGER.error("readBookingXML() : ERROR HAPPEND DURING SAVING PROCESS(###### saving Booking Do one by one ###########):for  File Name - [ "
										+ inboundFile.getName()+" ]"+"Exception :"+exp.getMessage());
							}
						
						if(faultConsgList!=null && !faultConsgList.isEmpty()&& faultConsgList.size() != baseEntity.size()){
							
							try {
								moveFileToErrorLog(inboundFile, faultConsgList);
							} catch (Exception e1) {
								LOGGER.error("readBookingXML() :moving file to Error Log File: File Name - "
										+ inboundFile.getName());
							}
							try {
								CGXMLUtil.moveFile(localArchiveDirPath,	inboundFile);
							} catch (Exception innerExp) {
								// TODO Auto-generated catch block
								LOGGER.error("readBookingXML() : DATA SAVED TO BOOKING TABLE : ERROR HAPPEND DURING MOVING THE FILE TO ARCHIVE FOLDER : File Name - "
										+ inboundFile.getName());
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
			LOGGER.error(
					"In CentralDataExtractor.readBookingXML()... Exception occured while updating the database for Booking XML",
					e);
			
			moveFilesToErrorFolder(files);
			
		}
	}
	
public JSONObject getJsonFromFile(File file) throws IOException {
	if(file == null){
		return null;
	}
	InputStream is = new FileInputStream(file);
	InputStreamReader inputstr = new InputStreamReader(is);
	StringBuilder sb=new StringBuilder();
	BufferedReader br = new BufferedReader(inputstr);
	String read = br.readLine();

	while(read != null) {
	    sb.append(read);
	    read =br.readLine();
	}
	
	String jsonTxt =  sb.toString();
	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
	is.close();
	return jsonObject;
}
	
	public CGBaseTO getBaseTOFromJson(JSONObject jsonObject)throws CGBusinessException{
		if(jsonObject == null){
			return null;
		}
		CGBaseTO baseTo = null;
		String objectType = null;
		String jsonobjectType = null;
		Map<String, Class<?>> map = null;
		Map<String, Class<?>> objectHirearchyMap = null;
		try {
			objectType = (String) jsonObject.get("objectType");
			if (jsonObject.get("jsonobjectType") != JSONNull
					.getInstance()) {
				jsonobjectType = (String) jsonObject
						.get("jsonobjectType");
			}
			map = new HashMap<String, Class<?>>();
			objectHirearchyMap = new HashMap<String, Class<?>>();
			try {
				if (!StringUtil.isEmpty(jsonobjectType)) {
					objectHirearchyMap.put("jsonChildObject",
							Class.forName(jsonobjectType));
				}
				objectHirearchyMap.put("baseList", Class.forName(objectType));

				map.putAll(objectHirearchyMap);
				
			} catch (ClassNotFoundException e) {
			}
			// Type Cast the Json Object in CGBaseTO object
			baseTo = (CGBaseTO) jsonObject.toBean(jsonObject, CGBaseTO.class, map);
		} finally {
			objectType = null;
			jsonobjectType = null;
			map = null;
			objectHirearchyMap = null;
		}
		return baseTo;
	}
	
	public List<CGBaseEntity> getBaseEntityFromJson(JSONObject jsonObject)throws CGBusinessException{
		if(jsonObject == null){
			return null;
		}
		List<CGBaseEntity> baseEntity = new ArrayList<CGBaseEntity>();
		String objectType = null;
		String jsonobjectType = null;
		Map<String, Class<?>> map = null;
		Map<String, Class<?>> objectHirearchyMap = null;
		try {
			objectType = (String) jsonObject.get("objectType");
			if (jsonObject.get("jsonobjectType") != JSONNull
					.getInstance()) {
				jsonobjectType = (String) jsonObject
						.get("jsonobjectType");
			}
			map = new HashMap<String, Class<?>>();
			objectHirearchyMap = new HashMap<String, Class<?>>();
			try {
				if (!StringUtil.isEmpty(jsonobjectType)) {
					objectHirearchyMap.put("jsonChildObject",
							Class.forName(jsonobjectType));
				}
				objectHirearchyMap.put("baseListForEntityObj", Class.forName(objectType));
				objectHirearchyMap.put("baseList", Class.forName("com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO"));
				map.putAll(objectHirearchyMap);
				
			} catch (ClassNotFoundException e) {
			}
			// Type Cast the Json Object in CGBaseTO object
			CGBaseTO baseTo =(CGBaseTO)jsonObject.toBean(jsonObject, CGBaseTO.class, map);
			for(CGBaseEntity cgBaseEntity:baseTo.getBaseListForEntityObj()){
				baseEntity.add(cgBaseEntity);
			}
		} finally {
			objectType = null;
			jsonobjectType = null;
			map = null;
			objectHirearchyMap = null;
		}
		return baseEntity;
	}
}
