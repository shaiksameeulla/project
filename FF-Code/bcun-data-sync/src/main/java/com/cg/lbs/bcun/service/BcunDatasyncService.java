package com.cg.lbs.bcun.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGBcunInbundDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.service.dataformater.AbstractDataFormater;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.ManualDownloadInputTO;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.umc.BcunPasswordDO;
import com.ff.domain.umc.PasswordDO;

/**
 * @author mohammal Jan 15, 2013
 * 
 */
public interface BcunDatasyncService {

	/**
	 * This function is used for getting configured operation mode from BCUN properties.
	 * @return operation mode <FTP> or <QUEUE>
	 */
	public String getModeOfOpration();

	/**
	 * Used to get the location of files which have to be processed.
	 * File locations are configured in BCUN properties.
	 * @return fully qualified path of base directory contains files.
	 */
	public String getBaseFileLocation();

	/**
	 * Used to get the location error files. Application will keep the error prone files 
	 * at this location. File locations are configured in BCUN properties. 
	 * @return fully qualified path of error files directory
	 */
	
	public String getProcessedFileLocation();
	
	public String getErrorFileLocation();
	
	/**
	 * @param location
	 * @return
	 */
	public String getReprocessFileLocation() ;

	/**
	 * @return
	 */
	public String getInboundFileLocAtCentral();
	/**
	 * This function is used to get the process name from the file name.
	 * File name is combination of process name and current process time in milliseconds
	 * @param fileName name of process file created by application which contains 
	 * BCUN data which has to be transfered.
	 * @return name of process of which file is containing data.
	 */
	public String getProcessName(String fileName);

	/**
	 * This function is used to get all the entries of process and its properties
	 * for which BCUN has to transfer the data.
	 * @return list of configured BCUN process informations.
	 */
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps();
	
	/**
	 * This function is used to get all the entries of process and its properties
	 * for which BCUN has to transfer the data.
	 * @return list of configured BCUN process informations.
	 */
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps(String processName);

	/**
	 * Upload files from provide location to the FTP server's home directory.
	 * FTP servers information will be configured in BCUN properties file.
	 * @param location from which file have to be upload to central server.
	 */
	public void processFileAndUploadToDatabase(String fileLocation);
	
	/**
	 * @param fileList
	 * @param fileLocation
	 */
	public void processFileAndUploadToDatabase(List<BcunFileSequenceTO> fileList, String fileLocation); 
	
	/**
	 * This is used for inserting/updating BCUN data to central database. 
	 * @param baseList list of rows/base do's which have to be insert/update to central database.
	 * @param formaterClass is a class which is used to prepare insert/update data based of process
	 * @return true or false based on status
	 * @throws ClassNotFoundException throws if configured formatter is not a class
	 * @throws InstantiationException throws if unable to instantiate of configured class. 
	 * @throws IllegalAccessException throws while instantiating.
	 */
	public boolean updateCentralDB(List<CGBaseDO> baseList)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;

	/**
	 * Used to get the java object fron json string
	 * @param jsonString json string of java object
	 * @param clazz is a type of class which instance has to be created
	 * @return java object created from json string
	 */
	public Object jsonStringToJava(String jsonString, Class clazz);

	/**
	 * Used to get process DO's list from a file
	 * @param file which contains BCUN data
	 * @param processDOName name of the process for which file is containing data
	 * @return list of process Domain Objects
	 * @throws IOException throws IOExpetion if file not found
	 * @throws ClassNotFoundException throws if file contains classes other than mapped for process 
	 */
	public List<CGBaseDO> getProcessDOListFromFile(File file,
			String processDOName) throws IOException, ClassNotFoundException;
	
	/**
	 * @param jsonText
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<CGBaseDO> getProcessDOListFromJsonString(String jsonText, String doClassName) throws IOException, ClassNotFoundException;
	
	/**
	 * @param file
	 * @param processDOName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String getContentFromFile(File file, String processDOName) throws IOException, ClassNotFoundException;

	/**
	 * Used to get the JSON string from list of base DO's.
	 * @param baseDO is list of domain objects.
	 * @return JSON string of base DO's list.
	 * @throws IOException while processing
	 * @throws JsonMappingException while mapping to JSON string
	 * @throws JsonGenerationException
	 */
	public String getJSONFromBaseDOList(List<CGBaseDO> baseDO)
			throws JsonGenerationException, JsonMappingException, IOException;

	/**
	 * This function is used to write JSON string on the file.
	 * @param jsonObject JSON string which has to be written in file
	 * @param file which contains JSON string
	 * @return result of the written process whether success or failure
	 * @throws IOException while processing the process
	 */
	public boolean writeJSONFile(String jsonObject, File file)
			throws IOException;

	
	/**
	 * This function is used to create a zip file
	 * @param zipFileName name of the created zip file
	 * @param contentFileName contens of the zip file
	 * @return byte array of created zip file
	 * @throws IOException is thrown if file not available
	 */
	public byte[] createZipFile(String zipFileName, String contentFileName)
			throws IOException;

	/**
	 * This function is used to create a zip file
	 * @param zipFileName name of the created zip file
	 * @return byte array of created zip file
	 * @throws IOException is thrown if file not available
	 */
	public byte[] getByteFromZipFile(String zipFileName) throws IOException;

	/**
	 * This function is used to get all the files names available ate provided locations
	 * @param location of the files
	 * @return all the files available ar provided location
	 */
	public String[] getAllFilesNames(String location);

	/**
	 * @param location
	 * @return
	 */
	public String[] getAllTempFilesNames();
	
	/**
	 * Get data from database based on named query and row count
	 * @param namedQuery is query name which will be executed for fetching data
	 * @param rowCount is number of rows has to be fetched
	 * @return list of base DO's returned by named query for row count
	 */
	public List<? extends CGBaseDO> getDataByNamedQueryAndRowCount(
			String namedQuery, Integer rowCount);

	/**
	 * Get data from database based on named query and row count
	 * @param namedQuery is query name which will be executed for fetching data
	 * @param rowCount rowCount is number of rows has to be fetched
	 * @param officeId is the office forwhich data has to be fetched
	 * @return list of base DO's returned by named query for row count
	 */
	public List<CGBaseDO> getOfficeDataByNamedQueryAndRowCount(
			String namedQuery, Integer rowCount, Integer officeId);

	/**
	 * Get data based on named query
	 * @param namedQuery name of query which has to be executed
	 * @return list of base DO's returned by named query
	 */
	public List<? extends CGBaseDO> getDataByNamedQuery(String namedQuery);

	/**
	 * @param namedQuery namedQuery is query name which will be executed for fetching data
	 * @param params list of parameters required to execute the query
	 * @param values are value has to passed for configured parameters
	 * @return list of base DO's returned by named query
	 */
	public List<? extends CGBaseDO> getDataByNamedQueryAndNamedParam(
			String namedQuery, String[] params, Object[] values);

	/**
	 * @param namedQuery namedQuery is query name which will be executed for fetching data
	 * @param param parameter required to execute the query
	 * @param value is value has to passed for configured parameter
	 * @return list of base DO's returned by named query
	 */
	public List<? extends CGBaseDO> getDataByNamedQueryAndNamedParam(
			String namedQuery, String param, Object value);

	/**
	 * used to save/update the DO
	 * @param baseEntity entity which has to be saved/update
	 */
	public void saveOrUpdateTransferedEntity(CGBaseDO baseEntity);

	/**
	 * Used to update a set of records.
	 * @param baseEntitis which has to be updated
	 */
	public void saveOrUpdateTransferedEntities(List<CGBaseDO> baseEntity);

	/**
	 * This function is used in out bound bcun data synchronization. function take parameter as a JSON string 
	 * and fetch required data and convert it into JSON string and send back to client
	 * @param jsonString parameter in form of JSON string
	 * @return zip file in the form of JSON string
	 */
	public String getOutboundBranchData(String jsonString);

	/**
	 * Function is used to delete the the specified file.
	 * @param fileName name of file which has to be deleted
	 * @return true if file deleted successfully or false
	 */
	public boolean deleteFile(String fileName);

	/**
	 * Function is used to delete the the specified file
	 * @param file which has to be deleted
	 * @return true if file deleted successfully or false
	 */
	public boolean deleteFile(File file);

	/**
	 * This function is used to rename the file
	 * @param file which has be renamed
	 * @param baseLocation is location where file is available
	 * @param processedFolder new location where file has to be moved
	 * @return true if file renamed successfully or false
	 */
	
	public boolean renameFile(String filePath, String baseLocation,
			String processedFolder);
	/**
	 * This function is used to fetched the primary key of the process/table/do.
	 * @param queryName name of the query which is used for fetch the key
	 * @param params list of parameters used by provided query
	 * @param values list of values used by provided parameters
	 * @return key of the process
	 */
	public Integer getUniqueId(String queryName, String[] params,Object[] values);

	/**
	 * @param hql
	 * @param maxRowFetch
	 * @return
	 */
	public List<CGBaseDO> getOutboundMasterDataFromDB(String hql, Integer maxRowFetch) ;
	
	/**
	 * @param baseList
	 * @param office
	 * @param processName
	 * @throws IOException
	 */
	public void prepareNStoreZipFile(List<CGBaseDO> baseList, Integer office, String processName) throws IOException;

	/**
	 * @param baseList
	 */
	public void updateTransferedStatus(List<CGBaseDO> baseList);

	/**
	 * @param baseList
	 */
	public void updatePacketInitiatedStatus(List<OutboundOfficePacketDO> baseList);
	
	/**
	 * @param json
	 * @throws CGSystemException
	 */
	public void updatePacketStatus(String json) throws CGSystemException;
	/**
	 * Delete from database.
	 *
	 * @param qryName the qry name
	 * @param paramWithValues the param with values
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean deleteFromDatabase(String qryName,
			Map<Object, Object> paramWithValues) throws CGBusinessException,
			CGSystemException;
	/**
	 * Update entity status.
	 *
	 * @param baseEntity the base entity
	 */
	void updateEntityStatus(CGBaseDO baseEntity);
	
	/**
	 * @param jsonString
	 * @param processDOName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<CGBaseDO> getProcessDOListFromJson(String jsonString, String processDOName) throws IOException, ClassNotFoundException ;

	/**
	 * Prepare n store zip file.
	 *
	 * @param baseList the base list
	 * @param destinationOffices the destination offices
	 * @param processName the process name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void prepareNStoreZipFile(List<CGBaseDO> baseList,
			Set<Integer> destinationOffices, String processName)
			throws IOException;

	/**
	 * Gets the numbers by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the numbers by named query and named param
	 */
	List<Integer> getNumbersByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values);
	
	/**
	 * @param configOffice
	 * @param configProcess
	 * @throws IOException
	 */
	public void proceedOutboundProcess(OutboundDatasyncConfigOfficeDO configOffice, OutboundConfigPropertyTO configProcess) throws IOException;

	/**
	 * Gets the number by named query.
	 *
	 * @param namedQuery the named query
	 * @return the number by named query
	 */
	List<? extends Number> getNumberByNamedQuery(String namedQuery);
	
	/**
	 * @param fileName
	 * @return
	 */
	boolean canProceedFile(String fileName);
	
	/**
	 * @param formaterClass
	 * @return
	 * @throws Exception
	 */
	public AbstractDataFormater getFormaterClass(String formaterClass) throws Exception;
	
	public CGBaseDO formateData(CGBaseDO baseDO, AbstractDataFormater formaterService) throws CGBusinessException, CGSystemException;
	
	//public boolean updateCentralDB(List<CGBaseDO> baseList);
	
	public boolean updateCentralDB(CGBaseDO baseDO);
	
	public void setDefaultFlags(CGBaseDO formatedBaseDO);
	
	public void createErrorFile(List<CGBaseDO> errorDataList, String fileName) throws IOException;
	public void createTempFile(List<CGBaseDO> errorDataList, String fileName) throws IOException;

	/**
	 * Gets the anonymus type data by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the anonymus type data by named query and named param
	 */
	List<?> getAnonymusTypeDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values);

	/**
	 * Gets the destination office for stock customer.
	 *
	 * @param namedQuery the named query
	 * @param officeType the office type
	 * @param loggedInoffice the logged inoffice
	 * @param officeList the office list
	 * @return the destination office for stock customer
	 */
	List<Integer> getDestinationOfficeForStockCustomer(String namedQuery,
			String officeType, Integer loggedInoffice, List<Integer> officeList);
	
	public void updatePasswordInCentral(PasswordDO passwordDO);
	
	public void updatePasswordInBranch(BcunPasswordDO bcunPasswordDO);

	/**
	 * Creates the un parsing error file.
	 *
	 * @param queueContent the queue content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void createUnParsingErrorFile(QueueDataContentTO queueContent)
			throws IOException;

	/**
	 * Proceed manual downloadprocess.
	 *
	 * @param inputTo the input to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String proceedManualDownloadprocess(ManualDownloadInputTO inputTo)
			throws CGBusinessException, CGSystemException, IOException;

	/**
	 * Creates the temp file.
	 *
	 * @param errorJsonString the error json string
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createTempFile(String errorJsonString, String fileName)
			throws IOException;

	/**
	 * Creates the error file.
	 *
	 * @param errorJsonString the error json string
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createErrorFile(String errorJsonString, String fileName)
			throws IOException;

	/**
	 * Read2 way file and insert to queue.
	 *
	 * @param fileName the file name
	 * @param fileLocation the file location
	 */
	public void readFileAndInsertTo2WayQueue(String fileName,
			String fileLocation);

	/**
	 * Creates the processed file.
	 *
	 * @param baseList the base list
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createProcessedFile(List<CGBaseDO> baseList, String fileName)
			throws IOException;

	/**
	 * Sets the central to branch dt flags.
	 *
	 * @param baseDO the new central to branch dt flags
	 */
	void setCentralToBranchDTFlags(CGBaseDO baseDO);

	/**
	 * Checks if is valid json string.
	 *
	 * @param jsonString the json string
	 * @return the boolean
	 */
	Boolean isValidJsonString(String jsonString);

	/**
	 * Send email notification for file.
	 *
	 * @param fileName the file name
	 * @param message the message
	 */
	void sendEmailNotificationForFile(String fileName, String message);

	/**
	 * Update flag and update entities for branch to central transfer.
	 *
	 * @param baseEntities the base entities
	 */
	void updateFlagAndUpdateEntitiesForBranchToCentralTransfer(
			List<CGBaseDO> baseEntities);

	public void sendEmailNotificationForBlobDownloadError();

	boolean proceedManualUploadprocess(String fileName,InputStream filecontent,String officeCode)
			throws CGBusinessException, CGSystemException;
	String prepareInboundBranchData()
			throws CGBusinessException, CGSystemException;

	void prepareNStoreZipFile(List<CGBaseDO> baseList,
			Set<Integer> destinationOffices, String processName,
			boolean isForRateContract) throws IOException;

	/**
	 * Update consg billing status.
	 *
	 * @param consgNumber the consg number
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean updateConsgBillingStatus(String consgNumber) throws CGSystemException;

	/**
	 * Description :: it's newly created method  for Blob download optimization
	 * @param jsonString
	 * @return
	 * @author mohammes
	 * @throws Exception
	 */
	
	OutboundBranchDataTO getOutboundPacketDataForEachBranch(String jsonString) throws Exception;

	void persistOrUpdateTransferedEntity(CGBaseDO baseEntity);

	void udateDataTransferToBranchStatusForDrs(List<CGBaseDO> baseList);

	boolean updateConsgStatusForDelivery(DeliveryDetailsDO consgDO)
			throws CGSystemException;

	void checkForSpecialErrors(List<CGBaseDO> specailErrorList,
			Exception e, CGBaseDO baseDO);

	void createSpecialTempFile(List<CGBaseDO> errorList, String fileName)
			throws IOException;

	/**
	 * Checks if is file written completely.
	 *
	 * @param writtenFile the written file
	 * @return true, if is file written completely
	 */
	boolean isFileWrittenCompletely(File writtenFile);

	void prepareAndStoreZipFile(List<CGBaseDO> baseList,
			Set<Integer> destinationOffices, String processName, String category)
			throws IOException;

	void udateDataTransferToBranchStatusByPrimaryKeyList(
			List<Integer> pojoPrimayKey, String queryName);

	void prepareAndStoreZipFile(List<CGBaseDO> baseList,
			Set<Integer> destinationOffices, String processName, String category,String filePreFix)
			throws IOException;

	void prepareReconcillationStatisticsOnCentral(
			ReconcillationConfigPropertyTO configPropertyTO)
			throws CGSystemException, CGBusinessException;

	List<CGBaseDO> getReconcillationDataForBlob(
			ReconcillationConfigPropertyTO configPropertyTO)
			throws CGSystemException;

	List<CGBaseDO> getReconcillationData(
			ReconcillationConfigPropertyTO configPropertyTO)
			throws CGSystemException;

	void prepareReconcillationStatisticsForBranchData(
			BcunReconcillationDO fromBranch) throws CGSystemException;

	void updateBcunDataTransferFlag(List<CGBcunInbundDO> cgBcunInboundList,
			String qryName);

}
