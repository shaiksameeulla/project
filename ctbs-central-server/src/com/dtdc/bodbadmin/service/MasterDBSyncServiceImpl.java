/**
 * 
 */
package com.dtdc.bodbadmin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONNull;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.file.FolderRenamingException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.dao.CentralDataExtractorDAO;
import com.dtdc.bodbadmin.utility.CentralDataExtractorConstant;
import com.dtdc.bodbadmin.utility.DomainToTransferObjectConverter;
import com.dtdc.bodbadmin.utility.MasterDBSyncPropertyReader;
import com.dtdc.bodbadmin.ziputil.ZipUtility;
import com.dtdc.domain.dataextraction.DataExtractionDO;
import com.dtdc.to.manifestextractor.DataExtractorTO;
import com.dtdc.to.utilities.MasterDBSyncInfoTO;


/**
 * @author nisahoo
 * 
 */
public class MasterDBSyncServiceImpl implements
MasterDBSyncService {

	private static final Logger LOGGER = Logger
	.getLogger(MasterDBSyncServiceImpl.class);
	public transient JSONSerializer serializer;
	
	private Properties messageProp;
	private CentralDataExtractorDAO centralDataExtractorDAO;
	private DomainToTransferObjectConverter objectConverter;

	public Properties getMessageProp() {
		return messageProp;
	}

	public void setMessageProp(Properties messageProp) {
		this.messageProp = messageProp;
	}

	public CentralDataExtractorDAO getCentralDataExtractorDAO() {
		return centralDataExtractorDAO;
	}

	public void setCentralDataExtractorDAO(
			CentralDataExtractorDAO centralDataExtractorDAO) {
		this.centralDataExtractorDAO = centralDataExtractorDAO;
	}

	public DomainToTransferObjectConverter getObjectConverter() {
		return objectConverter;
	}

	public void setObjectConverter(
			DomainToTransferObjectConverter objectConverter) {
		this.objectConverter = objectConverter;
	}

	@Override
	public void extractDataForAllBranches() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String branchFolder = "";
		try {
			/** Get all the branches */
			List<String> officeCodeList = centralDataExtractorDAO
			.getAllOfficeCodes();
			System.out.println("");
			for (String branchCode : officeCodeList) {

				branchFolder = extractDataForBranch(branchCode,null);
			}
			
		}  catch (Exception ex) {
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ ex.getMessage());
			
		} finally {
			deleteBranchDirectory(branchFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForBranch() : END");
	}
	
	@Override
	public void extractDataForAllFranchisees() throws CGBusinessException {

		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForAllFranchisees() : START");

		// The following constants for no of records to be fetched from each
		// process (configurable ie. taking the values from
		// properties(centralDataExtraction.properties) file)

		String franchiseeFolder = "";
		try {
			/** Get all the branches */
			List<String> franchiseeCodeList = centralDataExtractorDAO
			.getAllFranchiseeCodes();
			String frFlag="FR";
			for (String franchiseeCode : franchiseeCodeList) {

				franchiseeFolder = extractDataForBranch(franchiseeCode,frFlag);
			}
			
		}  catch (Exception ex) {
			LOGGER.error("Error occured while fetching Central DB Data..."
					+ ex.getMessage());
			
		} finally {
			deleteBranchDirectory(franchiseeFolder);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : extractDataForAllFranchisees() : END");
	}

	private String extractDataForBranch(String branchCode,String flag)
	throws CGBusinessException, CGSystemException, IOException {
		String branchFolder;
		LOGGER.debug("##########Data (extractDataForBranch)Extract for Branch = [ "
				+ branchCode + " ]###############");
		
			branchFolder=null;
			branchFolder = getFolderNameFromProps()
			+ File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
			
			List<MasterDBSyncInfoTO> masterDbSyncInfoList = MasterDBSyncPropertyReader
			.getDbSyncInfoList();
			
			if (!masterDbSyncInfoList.isEmpty()) {
				for (MasterDBSyncInfoTO dbSyncInfo : masterDbSyncInfoList) {

					try {
							LOGGER.debug("########USER START#######");
							checkAndUpdateBD(dbSyncInfo,branchCode,branchFolder,flag);
							
							LOGGER.debug("########USER END#######");

					} catch (Exception e) {
						LOGGER.error("BranchToCentralDBSyncBookingServiceImpl::extractDataForBranch::error in fetching data for [ "
								+ dbSyncInfo.getDoName() + " ]");
						LOGGER.debug("BranchToCentralDBSyncBookingServiceImpl::extractDataForBranch:: error in processing request for bean id[ "
								+ dbSyncInfo.getBeanId() + " ]");

						LOGGER.error(e.getMessage());
					}

				}
			}
				deleteBranchDirectory(branchFolder);
			
			//###################################### USER ###################### END
			
		return branchFolder;
	}

	private String getFolderNameFromProps() {
		return messageProp
		.getProperty(CentralDataExtractorConstant.XML_DATA_BASE_DIR);
	}

	/**
	 * @param branchCode
	 * @param branchFolder
	 * @param dataExtractionDO
	 * @param extractorTo TODO
	 * @return
	 * @throws IOException
	 */
	private DataExtractionDO prepareExtractedData(String branchCode,
			String branchFolder, DataExtractionDO dataExtractionDO, DataExtractorTO extractorTo)
	throws IOException {
		/** Validate XML files are created for Branch */
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]-----START");
		boolean xmlFilesCreated = checkFilesCreatedForBranch(branchFolder);
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]-----is XmlFilesCreated ["+xmlFilesCreated+"]");
		if (xmlFilesCreated) {
			/** Zip the files in memory */
			byte[] inMemoryZipFile = ZipUtility
			.createInMemoryZipFile(branchFolder);
			System.out.println("hrllo:");
			/** Create the Extracted Data for save */
			dataExtractionDO = createDataExtractionDOForSave(
					inMemoryZipFile, branchCode, extractorTo);
		}
		
		LOGGER.debug("prepareExtractedData ::-- branchCode :["+branchCode+"]" +"branchFolder :["+branchFolder+"]----->END");
		return dataExtractionDO;
	}

	private void deleteBranchDirectory(String branchFolderPath) {
		LOGGER.debug("CentralDataExtractorServiceImpl : deleteBranchDirectory() : START");	
		File branchFolder = new File(branchFolderPath);
		try {
			FileUtils.renameFolder(branchFolderPath,branchFolderPath+ApplicationConstants.CHARACTER_UNDERSCORE+ApplicationConstants.DIR_PROCESSED);
		} catch (FolderRenamingException e) {
			LOGGER.debug("CentralDataExtractorServiceImpl :deleteBranchDirectory--> renameFolder() : Exception"+e.getMessage());
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : deleteBranchDirectory() : END");
		
	}

	private boolean checkFilesCreatedForBranch(String branchFolderPath) {
		boolean fileExistsStatus = false;
		LOGGER.debug("CentralDataExtractorServiceImpl : checkFilesCreatedForBranch() : START");	
		File branchFolder = new File(branchFolderPath);
		if (branchFolder.isDirectory()) {
			int noOfXMLFiles = branchFolder.listFiles().length;

			if (noOfXMLFiles > 0) {
				fileExistsStatus = true;
			}
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : checkFilesCreatedForBranch() : END");
		return fileExistsStatus;
	}

	private DataExtractionDO createDataExtractionDOForSave(
			byte[] inMemoryZipFile, String branchCode, DataExtractorTO extractorTo) {

		LOGGER.debug("CentralDataExtractorServiceImpl : createDataExtractionDOForSave() : START");
		DataExtractionDO extractionDO = null;

		if (inMemoryZipFile != null) {
			extractionDO = new DataExtractionDO();
			extractionDO.setBranchCode(branchCode);
			extractionDO
			.setDataStatus(CentralDataExtractorConstant.EXTRACTED_DATA_UNREAD_STATUS);
			extractionDO.setExtractedDate(DateFormatterUtil.getCurrentDate());
			extractionDO.setExtractedData(inMemoryZipFile);
			extractionDO.setProcessName(extractorTo !=null && !StringUtil.isEmpty(extractorTo.getProcessName())?extractorTo.getProcessName().toUpperCase():null);
			extractionDO.setProcessFileName(extractorTo !=null && !StringUtil.isEmpty(extractorTo.getProcessFileName())?extractorTo.getProcessFileName().toUpperCase():null);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : createDataExtractionDOForSave() : END");
		return extractionDO;
	}

	private void populateProcessInfo(String processXMLPath,DataExtractorTO extractorTo) {
		if(extractorTo!=null && !StringUtil.isEmpty(processXMLPath)){
			String processFileName = processXMLPath.substring(processXMLPath.lastIndexOf(File.separator)+1);
			if(!StringUtil.isEmpty(processFileName)){
				extractorTo.setProcessFileName(processFileName);
				extractorTo.setProcessName((!StringUtil.isEmpty(processFileName)?processFileName.substring(processFileName.indexOf(ApplicationConstants.CHARACTER_UNDERSCORE)+1,processFileName.lastIndexOf(ApplicationConstants.CHARACTER_UNDERSCORE)):null));
			}
		}
	}

	private void checkAndUpdateBD(MasterDBSyncInfoTO dbSyncInfo,String branchCode,String banchFolder, String flag) throws CGSystemException{
		DataExtractorTO extractorTo=new DataExtractorTO();
		DataExtractionDO extractionDO =null;
		LOGGER.debug("CentralDataExtractorServiceImpl : checkAndUpdateBD() : START");
		List<CGBaseEntity> unSyncList = centralDataExtractorDAO.getUnSyncData(
				dbSyncInfo.getNamedQuery().trim(), dbSyncInfo.getMaxRowCount());
		
		if (unSyncList != null && !unSyncList.isEmpty()) {
			try {
				createXMLForBranch(unSyncList, dbSyncInfo, banchFolder,
						extractorTo);

				extractionDO = prepareExtractedData(branchCode, banchFolder,
						extractionDO, extractorTo);

				if (extractionDO != null && unSyncList != null
						&& !unSyncList.isEmpty()) {
					/** Save ZIP File */
					centralDataExtractorDAO
							.saveExtractedDataForBranch(extractionDO,flag);
					
					updateReadByLocal(unSyncList);
					/** Delete the XML Files in the Branch Folder */
					deleteBranchDirectory(banchFolder);
				}
			}
			catch (Exception e) {
				LOGGER.error("CentralDataExtractorServiceImpl : checkAndUpdateBD() : Error occurred :: "+e.getMessage());
			}
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : checkAndUpdateBD() : END");
	}
	
	private void createXMLForBranch(List<CGBaseEntity> unSyncList ,MasterDBSyncInfoTO dbSyncInfo,
			String branchFolderPath, DataExtractorTO dataExtractorTo) throws CGBusinessException {
		try {
			LOGGER.debug("CentralDataExtractorServiceImpl : createXMLForBranch() : START");
			CGBaseTO BaseTO = new CGBaseTO();
			serializer = CGJasonConverter.getJsonObject();
			
			BaseTO.setBaseListForEntityObj(unSyncList);
			
				BaseTO=prepareWritableBaseTo(BaseTO, dbSyncInfo);
				
				String userXMLFileName = "Master_"+dbSyncInfo.getProcess();
				String dateTimeFormat = messageProp
				.getProperty(CentralDataExtractorConstant.XMLFILE_TIMESTAMP_FORMAT);
				JsonConfig conf = new JsonConfig();
				conf.registerDefaultValueProcessor(Integer.class, 
				    new DefaultValueProcessor(){
				        public Object getDefaultValue(Class type){
				            return JSONNull.getInstance();
				        }
				    });
				
				String jsonObj = serializer.toJSON(BaseTO,conf).toString();
				
				File branchFolder = new File(branchFolderPath);
				if (!branchFolder.isDirectory()) {
					branchFolder.mkdirs();
				}
				
				
				
				if(!StringUtil.isEmpty(userXMLFileName)){
					char dot = '.';
					String processFileName = userXMLFileName.substring(userXMLFileName.lastIndexOf(dot)+1);
					if(!StringUtil.isEmpty(processFileName)){
						userXMLFileName=processFileName;
					}
				}
				
				String xMLPath = CGXMLUtil.createFileNameWithTimestamp(
						branchFolder.getAbsolutePath() + File.separator
						+ userXMLFileName, dateTimeFormat,
						ApplicationConstants.CHARACTER_UNDERSCORE);
				/** Create the XML */
				
				populateProcessInfo(xMLPath,dataExtractorTo);
				
				if (jsonObj != null) {
					File test = new File(xMLPath);
					if (!test.exists()) {
						test.createNewFile();
					}
					 FileOutputStream oos = new FileOutputStream(test);
					 oos.write(jsonObj.getBytes());
					 oos.close();
					
				}

				LOGGER.debug("CentralDataExtractorServiceImpl : createXMLForBranch() : END");	
		} catch (Exception e) {
			LOGGER.error("Error while creating XML for Manifest..."
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		
	}
	
	private CGBaseTO prepareWritableBaseTo(CGBaseTO baseTo,
			MasterDBSyncInfoTO dbSyncInfo) {
		LOGGER.debug("CentralDataExtractorServiceImpl : prepareWritableBaseTo() : START");	
		try {
			baseTo.setBeanId(dbSyncInfo.getBeanId());
			LOGGER.debug("BranchToCentralDBSyncServiceImpl::prepareWritableBaseTo: BeanId:======>"
					+ dbSyncInfo.getBeanId());
			baseTo.setClassType(dbSyncInfo.getServiceClass());
			LOGGER.debug("BranchToCentralDBSyncServiceImpl::prepareWritableBaseTo: ServiceClass======>"
					+ dbSyncInfo.getServiceClass());
			baseTo.setMethodName(dbSyncInfo.getServiceMethod());
			LOGGER.debug("BranchToCentralDBSyncServiceImpl::prepareWritableBaseTo: ServiceMethod======>"
					+ dbSyncInfo.getServiceMethod());
			baseTo.setRequestType(ApplicationConstants.WRITE_REQUEST);
			baseTo.setObjectType((Class<? extends CGBaseTO>) Class
					.forName(dbSyncInfo.getDoName()));
		}catch (Exception e) {
			LOGGER.debug("BranchToCentralDBSyncServiceImpl::prepareWritableBaseTo:Exception:e:getLocalizedMessage======>"+ e.getLocalizedMessage());
			LOGGER.debug("BranchToCentralDBSyncServiceImpl::prepareWritableBaseTo:Exception:e:getMessage======>"+ e.getMessage());
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : prepareWritableBaseTo() : END");	
		return baseTo;
	}

	private void updateReadByLocal(List<CGBaseEntity> unSyncList) {
		LOGGER.debug("CentralDataExtractorServiceImpl : updateReadByLocal() : START");	
		for(CGBaseEntity deo : unSyncList) {
			try {
				deo.setReadByLocal("Y");
				
				centralDataExtractorDAO.updateUnSyncData(deo);
			} catch (Exception ex) {
				LOGGER.debug("BranchToCentralDBSyncServiceImpl::updateBranchDB::nuable to update branch db for primary key [ " + deo.getChildEntityId() + " ]");
				LOGGER.debug("BranchToCentralDBSyncServiceImpl::updateBranchDB::Exception:ex:getLocalizedMessage" + ex.getLocalizedMessage());
				LOGGER.debug("BranchToCentralDBSyncServiceImpl::updateBranchDB::Exception:ex:getMessage" + ex.getMessage());
			}
			LOGGER.debug("CentralDataExtractorServiceImpl : updateReadByLocal() : END");	
		}
	}
}
