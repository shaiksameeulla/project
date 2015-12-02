/**
 * 
 */
package com.dtdc.bodbadmin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.file.FolderRenamingException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.dao.CentralDataExtractorDAO;
import com.dtdc.bodbadmin.utility.CentralDataExtractorConstant;
import com.dtdc.bodbadmin.ziputil.ZipUtility;
import com.dtdc.domain.dataextraction.DataExtractionDO;
import com.dtdc.to.manifestextractor.DataExtractorTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BODBAdminRequestProcessorServiceImpl.
 *
 * @author nisahoo
 */
public class BODBAdminRequestProcessorServiceImpl implements
		BODBAdminRequestProcessorService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(BODBAdminRequestProcessorServiceImpl.class);
	
	/** The central data extractor dao. */
	private CentralDataExtractorDAO centralDataExtractorDAO;
	
	/** The message prop. */
	private Properties messageProp;

	/**
	 * Gets the central data extractor dao.
	 *
	 * @return the central data extractor dao
	 */
	public CentralDataExtractorDAO getCentralDataExtractorDAO() {
		return centralDataExtractorDAO;
	}

	/**
	 * Sets the central data extractor dao.
	 *
	 * @param centralDataExtractorDAO the new central data extractor dao
	 */
	public void setCentralDataExtractorDAO(
			CentralDataExtractorDAO centralDataExtractorDAO) {
		this.centralDataExtractorDAO = centralDataExtractorDAO;
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

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.BODBAdminRequestProcessorService#loadDataForBranch(CGBaseTO)
	 */
	@Override
	public CGBaseTO loadDataForBranch(CGBaseTO baseTO)throws CGBusinessException{
		LOGGER.debug("CentralDataExtractorServiceImpl : loadDataForBranch() : START");
		
		DataExtractorTO extractorTO = null;
		Integer maxFetchRecords=null;
		List<Integer> listOfIds=null;
		StringBuffer stringIds = new StringBuffer();
		String tempBranchFolderPath=null;
		Boolean isGotException=Boolean.FALSE;
		String randomNumber=null;
		String absolutePath=null;
		try {
			extractorTO = (DataExtractorTO) baseTO.getBaseList().get(0);
			String branchCode = extractorTO.getBranchCode();
			maxFetchRecords = extractorTO.getMaxRecords();
			randomNumber = StringUtil.isEmpty(extractorTO.getRandomNumber())?StringUtil.generateRamdomNumber():extractorTO.getRandomNumber();
			LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch-->  Data Extract for Branch = [ "+branchCode+" ] with request id ["+extractorTO.getRandomNumber()+"]");
			tempBranchFolderPath = messageProp.getProperty(CentralDataExtractorConstant.XML_DATA_RETRIEVAL_FOR_BRANCH)+File.separator + branchCode+File.separator+branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+randomNumber+ApplicationConstants.CHARACTER_UNDERSCORE+ StringUtil.generateRamdomNumber();
			File tempBranchFolder = new File(tempBranchFolderPath);
			
			if(!StringUtil.isEmpty(tempBranchFolderPath)  && tempBranchFolder!= null && !tempBranchFolder.exists() ){
				tempBranchFolder.mkdirs();
			}
			absolutePath = tempBranchFolder.getAbsolutePath();
			List<DataExtractionDO> dataExtractionList = centralDataExtractorDAO.getExtractedDataByBranchCode(branchCode, maxFetchRecords);
			if(dataExtractionList != null & !dataExtractionList.isEmpty()){
				listOfIds= new ArrayList<Integer>(dataExtractionList.size());
					LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch--> absolutePath "+absolutePath);
					LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch--> DataExtractorDo Size ["+dataExtractionList!=null && !dataExtractionList.isEmpty()?dataExtractionList.size():0+"]");
					for(DataExtractionDO dataExtractionDO : dataExtractionList){
						// Create a Zip Files of all the multiple extracted Data for branch
						//count+=1;//Date :17-02-2012 commented Reason :instead of adding count,we are adding random number to avoid file clashes
						String zipFile = absolutePath
						+ File.separator + branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+dataExtractionDO.getDataExtractionId()+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber()
						+ CentralDataExtractorConstant.ZIPFILE_EXTN;
						LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch--> creating Zip file at central for Branch"+zipFile);
						ZipUtility.createLocalZipFile(zipFile, dataExtractionDO.getExtractedData());
						listOfIds.add(dataExtractionDO.getDataExtractionId());
						stringIds.append(dataExtractionDO.getDataExtractionId());
						stringIds.append(ApplicationConstants.CHARACTER_COMMA);
					}
					
					listOfIds = processFileList(tempBranchFolderPath,listOfIds,branchCode);
					stringIds = processIdList(listOfIds);
					LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch--> And collected IdList ["+listOfIds!=null && !listOfIds.isEmpty()?listOfIds.size():0+"]");
					LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::loadDataForBranch--> And stringIds ["+stringIds+"]");
					// Zip the Temporaray barnch folder containing multiple zip files
					byte[] zipCombo = ZipUtility.createInMemoryZipFile(tempBranchFolderPath);
					extractorTO.setFileData(zipCombo);
					extractorTO.setListOfIds(listOfIds);
					extractorTO.setDataExtctrIdStr(stringIds.toString());
					if(listOfIds !=null && !listOfIds.isEmpty()){
					centralDataExtractorDAO.modifyDataExtractionStatusToTransmission(listOfIds);
					}
					deleteBranchDirectory(tempBranchFolderPath);
					
				
			}
			
		}catch(Exception ex){
			LOGGER.error("BODBAdminRequestProcessorServiceImpl::loadDataForBranch::Exception occured:"
					+ex.getMessage());
			LOGGER.error("Error occured while fetching Central DB Data... for ID List ["+stringIds+"]");
			isGotException = Boolean.TRUE;
			throw new CGBusinessException(ex);
		}
		finally{
			if(!StringUtil.isEmpty(tempBranchFolderPath) && !isGotException){
				deleteBranchDirectory(tempBranchFolderPath);
			}
		}
		
		LOGGER.debug("CentralDataExtractorServiceImpl : loadDataForBranch() : END");
		return extractorTO;
	}
	
	/**
	 * Process id list.
	 *
	 * @param listOfIds the list of ids
	 * @return the string buffer
	 */
	private StringBuffer processIdList(List<Integer> listOfIds){
		LOGGER.debug("CentralDataExtractorServiceImpl : loadDataForBranch() : processIdList with ["+listOfIds!=null && !listOfIds.isEmpty()? listOfIds.toString() :"0");
		StringBuffer idValue = new StringBuffer();
		for(Integer value :listOfIds){
			idValue.append(value);
			idValue.append(ApplicationConstants.CHARACTER_COMMA);
		}
		LOGGER.debug("CentralDataExtractorServiceImpl : loadDataForBranch() : processIdList (String ) ["+idValue+"]");
		return idValue;
	}
	
	/**
	 * Process file list.
	 *
	 * @param tempfolderpath the tempfolderpath
	 * @param idParamList the id param list
	 * @param branchCode the branch code
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<Integer> processFileList(String tempfolderpath ,List<Integer> idParamList,String branchCode)throws IOException{
		List<Integer> idList= new ArrayList<Integer>(0);
		File unzipFolder = new File(tempfolderpath);
		StringBuilder fileNameList =new StringBuilder();
		if (unzipFolder.isDirectory()) {
			File[] files = unzipFolder.listFiles();
			for (int i = 0; i < files.length; i++) {
				fileNameList.append(files[i].getName());
			}
		}
		if(!StringUtil.isEmpty(fileNameList.toString())){
			for(Integer value :idParamList){
				String identifier = branchCode+ApplicationConstants.CHARACTER_UNDERSCORE+value+ApplicationConstants.CHARACTER_UNDERSCORE;
				if(fileNameList.toString().contains(identifier)){
				idList.add(value);
				}
			}
		}
	return idList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.BODBAdminRequestProcessorService#statusUpdateForBranch(CGBaseTO)
	 */
	@Override
	public CGBaseTO statusUpdateForBranch(CGBaseTO baseTO)
			throws CGBusinessException {
		String branchCode="";
		DataExtractorTO extractorTO = null;
		Boolean result=Boolean.FALSE;
		List<Integer>  idList= null;
		try {
			LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::statusUpdateForBranch #####START");
			extractorTO = (DataExtractorTO) baseTO.getBaseList().get(0);
			 branchCode = extractorTO.getBranchCode();
			 	 idList = getListOfExtarctrId(extractorTO);
			
			LOGGER.debug("Data Extract for Branch = [ "+branchCode+" ]" );
			
			List<DataExtractionDO> dataExtractionList = centralDataExtractorDAO.getTransitExtractedData(branchCode, idList);
			result = centralDataExtractorDAO.updateDataExtractionWithReadDataStatus(dataExtractionList);
			LOGGER.debug("BODBAdminRequestProcessorServiceImpl :statusUpdateForBranch :updated Status ["+result+" ]");
		}catch (Exception e) {
			LOGGER.error("BODBAdminRequestProcessorServiceImpl::statusUpdateForBranch::Exception occured:"
					+e.getMessage());
			LOGGER.error("BODBAdminRequestProcessorServiceImpl :statusUpdateForBranch = [ "+branchCode+" ]");
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::statusUpdateForBranch #####END with status["+result+"]");
		extractorTO.setStatus(result);
		return extractorTO;
	}

	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.service.BODBAdminRequestProcessorService#statusRestoreForBranch(CGBaseTO)
	 */
	@Override
	public CGBaseTO statusRestoreForBranch(CGBaseTO baseTO)
			throws CGBusinessException {
		String branchCode="";
		DataExtractorTO extractorTO = null;
		Boolean result=Boolean.FALSE;
		List<Integer>  idList= null;
		try {
			LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::statusRestoreForBranch #####START");
			extractorTO = (DataExtractorTO) baseTO.getBaseList().get(0);
			 branchCode = extractorTO.getBranchCode();
			 	 idList = getListOfExtarctrId(extractorTO);
			
			LOGGER.debug("Data Extract for Branch = [ "+branchCode+" ]" );
			
			result = centralDataExtractorDAO.restoreDataExtractionStatusToTransmission(idList);
			LOGGER.debug("BODBAdminRequestProcessorServiceImpl :statusRestoreForBranch :updated Status ["+result+" ]");
		}catch (Exception e) {
			LOGGER.error("BODBAdminRequestProcessorServiceImpl::statusRestoreForBranch::Exception occured:"
					+e.getMessage());
			LOGGER.error("BODBAdminRequestProcessorServiceImpl :statusRestoreForBranch = [ "+branchCode+" ]");
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BODBAdminRequestProcessorServiceImpl ::statusRestoreForBranch #####END with status["+result+"]");
		extractorTO.setStatus(result);
		return extractorTO;
	}
	
	
	/**
	 * Gets the list of extarctr id.
	 *
	 * @param extractorTO the extractor to
	 * @return the list of extarctr id
	 * @throws NumberFormatException the number format exception
	 */
	private List<Integer> getListOfExtarctrId(DataExtractorTO extractorTO)
			throws NumberFormatException {
		
		List<Integer> intList = null;
		List<String> strList = null;
		String dataExtctrIdStr=null;
		
		String a=extractorTO.getDataExtctrIdStrArray();
		if(!StringUtil.isEmpty(a)){
		String b = a.replace("[", "");
		String c = b.replace("]", "");		
		String[] strArray = c.split( "," );
		if(!StringUtil.isNull(strArray)){
		 strList = Arrays.asList( strArray );
		 if(strList !=null && !strList.isEmpty()){
		 intList = new ArrayList<Integer>(strList.size());
		for(String value:strList){
			if(!StringUtil.isEmpty(value)){
			intList.add(Integer.valueOf(value.trim()));
			}
		}
		 }
		}
		}
		if(intList == null || intList.isEmpty()){
		 dataExtctrIdStr = extractorTO.getDataExtctrIdStr();
			String strArray[]= dataExtctrIdStr.split(ApplicationConstants.CHARACTER_COMMA);
			if(strArray !=null) {
			strList = Arrays.asList( strArray );
			}
			 if(strList !=null && !strList.isEmpty()){
				 intList = new ArrayList<Integer>(strList.size());
				for(String value:strList){
					if(!StringUtil.isEmpty(value)){
					intList.add(Integer.valueOf(value.trim()));
					}
				}
				 }
		}
		return intList;
	}
	
	/**
	 * Delete branch directory.
	 *
	 * @param branchFolderPath the branch folder path
	 */
	private void deleteBranchDirectory(String branchFolderPath) {
		new File(branchFolderPath);
		
		try {
			FileUtils.renameFolder(branchFolderPath, branchFolderPath+ApplicationConstants.DIR_PROCESSED);
		} catch (FolderRenamingException e) {
			LOGGER.error("BODBAdminRequestProcessorServiceImpl::deleteBranchDirectory::Exception occured:"
					+e.getMessage());
		}
	}
}
