package com.cg.lbs.bcun.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGBcunInbundDO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.utils.ZipUtility;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.dao.BcunDatasyncDAO;
import com.cg.lbs.bcun.service.dataformater.AbstractDataFormater;
import com.cg.lbs.bcun.service.outbound.officefinder.OutboundOfficeFinderService;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.to.BcunFileSequenceTO;
import com.cg.lbs.bcun.to.ManualDownloadInputTO;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.utility.BcunDoFormaterMapper;
import com.cg.lbs.bcun.utility.InboundPropertyReader;
import com.cg.lbs.bcun.utility.OutboundMasterDataPropertyReader;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;
import com.cg.lbs.bcun.utility.ReconcillationPropertyReader;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.domain.bcun.OutboundDataPacketDO;
import com.ff.domain.bcun.OutboundDataPacketWrapperDO;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.ratemanagement.masters.BcunRateContractDO;
import com.ff.domain.umc.BcunPasswordDO;
import com.ff.domain.umc.PasswordDO;


/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public abstract class AbstractBcunDatasyncServiceImpl implements BcunDatasyncService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractBcunDatasyncServiceImpl.class);
	
	private static final String PROCESSED_FOLDER_NAME="pr";
	private static final String ERROR_FOLDER_NAME="er";
	private static final String ERROR_FLAG_NAME="ER_";
	private static final String RE_PROCESS_FOLDER_NAME="temp";
	private static final String RE_PROCESS_FLAG_NAME="RP_";
	private static final String QUEUE_FLAG="Q";
	
	private EmailSenderUtil emailSenderUtil;
	
	/**
	 * BCUN DAO used to provide dada access from database. injected via dependency injection.
	 */
	private BcunDatasyncDAO datasyncDao;
	private String processingBrCode;
	
	/**
	 * BCUN configuration properties. injected via dependency injection.
	 */
	protected Properties bcunProperties;
	
	private String outboundFilePrefix; 
	
	/*
	 * (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getProcessDOListFromFile(java.io.File, java.lang.String)
	 */
	
	@Override
	public List<CGBaseDO> getProcessDOListFromFile(File file, String processDOName) throws IOException, ClassNotFoundException {
		if (file == null)
			return null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::start====>" + file.getName());
		ArrayList<CGBaseDO> baseDoList = null;
		InputStream is = null;
		try {
			//Getting input stream on file
			is = new FileInputStream(file);
			//Reading file content into string
			String jsonTxt = IOUtils.toString(is);
			//Mapper class to map string into java object
			ObjectMapper mapper = new ObjectMapper();
			//Creating class which has to be mapped
			Class clazz = Class.forName(processDOName);//RunsheetAssignmentHeaderDO.class;
			//Creating java type of array of JSON object
			JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
			//Converting JSON string into collection of java objects
			baseDoList = (ArrayList<CGBaseDO>)mapper.readValue(jsonTxt, type);
		} catch (Exception ex) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getJsonFromFile::Exception====>",ex);
			throw ex;
		} finally {
			if(is != null)
			is.close();
		}
		
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::end====>");
		
		return baseDoList;
	}
	
	@Override
	public String getContentFromFile(File file, String processDOName) throws IOException, ClassNotFoundException {
		if (file == null)
			return null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::start====>" + file.getName());
		//ArrayList<CGBaseDO> baseDoList = null;
		InputStream is = null;
		String jsonTxt = null;
		try {
			//Getting input stream on file
			is = new FileInputStream(file);
			//Reading file content into string
			jsonTxt = IOUtils.toString(is);
		} catch (Exception ex) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getJsonFromFile::Exception====>",ex);
			throw ex;
		} finally {
			if(is != null)
			is.close();
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::end====>");
		return jsonTxt;
	}
	
	@Override
	public  Boolean isValidJsonString(String jsonString){
		   boolean valid = false;
		   try {
		      final JsonParser parser = new ObjectMapper().getFactory()
		            .createJsonParser(jsonString);
		      while (parser.nextToken() != null) {
		      }
		      valid = true;
		   } catch (JsonParseException jpe) {
		      LOGGER.error("AbstractBcunDatasyncServiceImpl::isValidJsonString::Exception====>",jpe);
		   } catch (IOException ioe) {
		      LOGGER.error("AbstractBcunDatasyncServiceImpl::isValidJsonString::Exception====>",ioe);
		   }

		   return valid;
		}
	
	@Override
	public List<CGBaseDO> getProcessDOListFromJsonString(String jsonText, String doClassName) throws IOException, ClassNotFoundException {
		String formater = BcunDoFormaterMapper.getInboundFormaters().get(doClassName);
		ObjectMapper mapper = new ObjectMapper();
		//Creating class which has to be mapped
		Class clazz = Class.forName(doClassName);//RunsheetAssignmentHeaderDO.class;
		//Creating java type of array of JSON object
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		//Converting JSON string into collection of java objects
		ArrayList<CGBaseDO> baseDoList = (ArrayList<CGBaseDO>)mapper.readValue(jsonText, type);
		return baseDoList;
	}
	
	@Override
	public List<CGBaseDO> getProcessDOListFromJson(String jsonString, String processDOName) throws IOException, ClassNotFoundException {
		if (jsonString == null)
			return null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::start====>" + processDOName);
		ArrayList<CGBaseDO> baseDoList = null;
		//Mapper class to map string into java object
		ObjectMapper mapper = new ObjectMapper();
		//Creating class which has to be mapped
		Class clazz = Class.forName(processDOName);//RunsheetAssignmentHeaderDO.class;
		//Creating java type of array of JSON object
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		//Converting JSON string into collection of java objects
		baseDoList = (ArrayList<CGBaseDO>)mapper.readValue(jsonString, type);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::end====>");
		return baseDoList;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getJSONFromBaseDOList(java.util.List)
	 */
	@Override
	public String getJSONFromBaseDOList(List<CGBaseDO> baseDO) throws JsonGenerationException, JsonMappingException, IOException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJSONFromBaseDO::start====>");
		String jsonString = null;
		//Mapper class to map string into java object
		ObjectMapper mapper = new ObjectMapper();
		//Creating string writer
		StringWriter writer = new StringWriter();
		//Writing java object into writer based on mapper
		mapper.writeValue(writer, baseDO);
		//Creating JSON string from writer
		jsonString = writer.toString();
		//closing writer
		writer.close();
		mapper = null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJSONFromBaseDO::end====>");
		return jsonString;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#writeJSONFile(java.lang.String, java.io.File)
	 */
	@Override
	public boolean writeJSONFile(String jsonObject, File file) throws IOException {
		boolean isWritten = false;
		FileWriter writer =  null;
		if(StringUtil.isStringEmpty(jsonObject) || StringUtil.isNull(file)){
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::writeJSONFile::jsonObject :["+jsonObject+"] file :["+file+"] hence not writing to json file");
			return false;
		}
		try {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::writeJSONFile::start====>");
			//Creating file writer
			writer = new FileWriter(file);
			//Writing JSON string on file
			writer.write(jsonObject);
			//flushing  
			writer.flush();
			isWritten = true;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::writeJSONFile::end====>");
		} catch (IOException ex) {
			isWritten = false;
			LOGGER.error("AbstractBcunDatasyncServiceImpl::writeJSONFile::Exception====>",ex);
			throw ex;
		} finally {
			if(writer!=null){
			writer.close();//closing the writer
			}
		}
		if(file!=null && file.length()>0){
			isWritten = true;
		}else{
			isWritten = false;
		}
		return isWritten;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#createZipFile(java.lang.String, java.lang.String)
	 */
	@Override
	public byte[] createZipFile(String zipFileName, String contentFileName) throws IOException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createZipFile::start====>");
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		ZipOutputStream zos = null;
		byte[] fileContent = null;
		try {
			File contentFile = new File(contentFileName);
			fis = new FileInputStream(contentFile);
			//Creating byte array stream 
			baos = new ByteArrayOutputStream();
			//Zip stream for create a zip file
			zos = new ZipOutputStream(new BufferedOutputStream(baos));
			//Creating a zip entry
			ZipEntry entry = new ZipEntry(contentFile.getName());
			//Adding entry to zip file
			zos.putNextEntry(entry);
			//Creating byte array of zip file
			byte[] bytes = new byte[802400];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
			zos.closeEntry();
			fis.close();
			zos.close();
			fileContent =baos.toByteArray(); 
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::createZipFile::end====>");
		} catch (Exception ex) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::createZipFile:: Exception...",ex);
			fileContent=null;
		} finally {
			//fis.close();
			//zos.closeEntry();
			//zos.close();
			baos.close();
		}
		return fileContent;
	}
	
	@Deprecated
	@Override
	public byte[] getByteFromZipFile(String zipFileName) throws IOException {
		byte[] blob = null;
		
		/*Enumeration<ZipEntry>  entries = zip.entries();*/
		return blob;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getAllFilesNames(java.lang.String)
	 */
	@Override
	public String[] getAllFilesNames(String location) {
		return com.capgemini.lbs.framework.utils.FileUtils.getAllFilesNames(location,null);
	}
	
	@Override
	public String[] getAllTempFilesNames() {
		String location = getReprocessFileLocation();
		String[] fileNames = null;
		if(!StringUtil.isStringEmpty(location)) {
			//Creating a base directory of provided location
			File baseDir = new File(location);
			if(baseDir.isDirectory()) {
				fileNames = baseDir.list(com.capgemini.lbs.framework.utils.FileUtils.bcunFileFilter());
			}else {
				createFolderIfNotExist(location);
			}
		} 
		return fileNames;
	}
	
	@Override
	public String getProcessedFileLocation()  {
		return  getBaseFileLocation() + File.separator + PROCESSED_FOLDER_NAME;
	}
	
	@Override
	public String getReprocessFileLocation()  {
		return  getBaseFileLocation() + File.separator + RE_PROCESS_FOLDER_NAME;
	}
	
	@Override
	public String getErrorFileLocation() {
		return getBaseFileLocation() + File.separator + ERROR_FOLDER_NAME;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndRowCount(String namedQuery, Integer rowCount) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getDataByNamedQueryAndRowCount::namedQuery====>" + namedQuery);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getDataByNamedQueryAndRowCount::rowCount====>" + (rowCount != null ? rowCount.intValue() : 0));
		return datasyncDao.getDataByNamedQueryAndRowCount(namedQuery, rowCount);
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getOfficeDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<CGBaseDO> getOfficeDataByNamedQueryAndRowCount(String namedQuery, Integer rowCount, Integer officeId) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getDataByNamedQueryAndRowCount::namedQuery====>" + namedQuery);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getDataByNamedQueryAndRowCount::rowCount====>" + (rowCount != null ? rowCount.intValue() : 0));
		return datasyncDao.getOfficeDataByNamedQueryAndRowCount(namedQuery, rowCount, officeId);
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery,
			String param, Object value) {
		List<CGBaseDO> baseList = (List<CGBaseDO>) datasyncDao
				.getDataByNamedQueryAndNamedParam(namedQuery,
						new String[] { param }, new Object[] { value });
		return baseList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery, String[] params, Object[] values) {
		List<CGBaseDO> baseList = null;
		baseList = (List<CGBaseDO>)datasyncDao.getDataByNamedQueryAndNamedParam(namedQuery,params,values);
		return baseList;
	}
	@Override
	public List<Integer> getNumbersByNamedQueryAndNamedParam(String namedQuery, String[] params, Object[] values) {
		return datasyncDao.getNumbersByNamedQueryAndNamedParam(namedQuery,params,values);
	}
	
	@Override
	public List<?> getAnonymusTypeDataByNamedQueryAndNamedParam(String namedQuery, String[] params, Object[] values) {
		return datasyncDao.getAnonymusTypeDataByNamedQueryAndNamedParam(namedQuery,params,values);
	}
	@Override
	public List<Integer> getDestinationOfficeForStockCustomer(String namedQuery, String officeType,Integer loggedInoffice,List<Integer> officeList) {
		return datasyncDao.getDestinationOfficeForStockCustomer(namedQuery, officeType, loggedInoffice, officeList);
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getDataByNamedQuery(java.lang.String)
	 */
	@Override
	public List<OutboundDatasyncConfigOfficeDO> getDataByNamedQuery(String namedQuery) {
		List<OutboundDatasyncConfigOfficeDO> baseList = null;
		baseList = (List<OutboundDatasyncConfigOfficeDO>)datasyncDao.getDataByNamedQuery("getAllActiveDatasyncOffice");
		return baseList;
	}
	
	/**
	 * Gets the number by named query.
	 *
	 * @param namedQuery the named query
	 * @return the number by named query
	 */
	@Override
	public List<? extends Number> getNumberByNamedQuery(String namedQuery) {
		return datasyncDao.getNumberByNamedQuery(namedQuery);
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#saveOrUpdateTransferedEntity(com.capgemini.lbs.framework.domain.CGBaseDO)
	 */
	@Override
	public void saveOrUpdateTransferedEntity(CGBaseDO baseEntity)  {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateTransferedEntity::start====>");
		datasyncDao.saveOrUpdateTransferedEntity(baseEntity);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateTransferedEntity::end====>");
	}
	@Override
	public void persistOrUpdateTransferedEntity(CGBaseDO baseEntity)  {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::persistOrUpdateTransferedEntity::start====>");
		datasyncDao.persistOrUpdateTransferedEntity(baseEntity);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::persistOrUpdateTransferedEntity::end====>");
	}
	@Override
	public void updateEntityStatus(CGBaseDO baseEntity)  {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateEntityStatus::start====>");
		datasyncDao.updateEntityStatus(baseEntity);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateEntityStatus::end====>");
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#saveOrUpdateTransferedEntities(java.util.List)
	 */
	@Override
	public void saveOrUpdateTransferedEntities(List<CGBaseDO> baseEntities) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateTransferedEntities::start====>");
		datasyncDao.saveOrUpdateTransferedEntities(baseEntities);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateTransferedEntities::end====>");
	}
	
	@Override
	public void updateFlagAndUpdateEntitiesForBranchToCentralTransfer(List<CGBaseDO> baseEntities) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateFlagAndUpdateEntitiesForBranchToCentralTransfer::Updating Flag for DB update");
		for(CGBaseDO baseDo : baseEntities) {
			baseDo.setDtToCentral("Y");
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateFlagAndUpdateEntitiesForBranchToCentralTransfer::start====>");
		datasyncDao.saveOrUpdateTransferedEntities(baseEntities);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateFlagAndUpdateEntitiesForBranchToCentralTransfer::end====>");
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getOutboundBranchData(java.lang.String)
	 */
	@Override
	public String getOutboundBranchData(String jsonString) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound start====>");
		//Converting branch JSON request to java object
		OutboundBranchDataTO inputTO = (OutboundBranchDataTO)jsonStringToJava(jsonString, OutboundBranchDataTO.class);
		String uniqueId=inputTO!=null?inputTO.getUniqueRequestId():null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound:: getting requested data for branch [" + inputTO.getBranchCode()+"]..... for the Request Id:["+uniqueId+"]");
		//Getting branch requested data
	//	List<OutboundOfficePacketDO> branchDataList = datasyncDao.getOutboundBranchData(inputTO);
		List<OutboundDataPacketWrapperDO> branchDataList=datasyncDao.getDataPacketByOffice(inputTO);
		List<Long> packetIds = null; 
		if(branchDataList != null && !branchDataList.isEmpty()) {
			int blobListSize=branchDataList.size();
			StringBuilder loggerInfo = getLogDtls(inputTO, blobListSize);
			LOGGER.info(loggerInfo.toString());
			//Creating byte array of zip files to send back to requested branch
			List<byte[]> zipFIles = new ArrayList<byte[]>(blobListSize);
			packetIds = new ArrayList<Long>(blobListSize);
			for(OutboundDataPacketWrapperDO officePacket : branchDataList) {
				zipFIles.add(officePacket.getPacketData());
				packetIds.add(officePacket.getDataOfficePacketId());
			}
			inputTO.setZipFIles(zipFIles);
			inputTO.setPacketIds(packetIds);
			//inputTO.setBranchId(branchDataList.get(0).getOutboundOfficeId());
			loggerInfo.append(" adding to ZIP is END");
			LOGGER.info(loggerInfo.toString());
		} else {
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound:: there is no data for requested branch [" + inputTO.getBranchCode()+"]for the Request Id:["+uniqueId+"]");
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound::preparing JSON string for available data ====>for the Request Id:["+uniqueId+"]");
		//Creating JSON string of response object
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			//Writing JSON string into writer to convert it into java object
			mapper.writeValue(writer, inputTO);
			//updatePacketInitiatedStatus(branchDataList);
			updatePacketStatusAsInitiated(packetIds);
		} catch (JsonGenerationException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::JsonGenerationException::",e);
		} catch (JsonMappingException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::JsonMappingException::",e);
		} catch (IOException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::IOException::",e);
		}catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::Exception::",e);
		}
		jsonString = writer.toString();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound::JSON string prepared for available data ====>for the Request Id:["+uniqueId+"]");
		//String jsonOutput = CGJasonConverter.serializer.toJSON(inputTO).toString();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound end====>for the Request Id:["+uniqueId+"]");
		return jsonString;
	}
	
	/**
	 * Description :: it's newly created method  for Blob download optimization
	 * @param jsonString
	 * @return
	 * @author mohammes
	 * @throws Exception
	 */
	
	@Override
	public OutboundBranchDataTO getOutboundPacketDataForEachBranch(String jsonString) throws Exception {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch::central outbound start====>");
		//Converting branch JSON request to java object
		
		OutboundBranchDataTO inputTO = (OutboundBranchDataTO)jsonStringToJava(jsonString, OutboundBranchDataTO.class);
		String uniqueId=inputTO!=null?inputTO.getUniqueRequestId():"NoUniqueId";
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch::central outbound:: getting requested data for branch [" + inputTO.getBranchCode()+"]..... for the Request Id:["+uniqueId+"]");
		//Getting branch requested data
		List<OutboundDataPacketWrapperDO> branchDataList=datasyncDao.getDataPacketByOffice(inputTO);
		List<Long> packetIds = null; 
		if(branchDataList != null && !branchDataList.isEmpty()) {
			String tempBranchFolderPath=getBaseFileLocation()+File.separator+inputTO.getBranchCode()+FrameworkConstants.CHARACTER_UNDERSCORE+uniqueId;

			File tempBranchFolder = new File(tempBranchFolderPath);
			if(!StringUtil.isStringEmpty(tempBranchFolderPath)  && tempBranchFolder!= null && !tempBranchFolder.exists() ){
				tempBranchFolder.mkdirs();
			}
			try {
				int blobListSize=branchDataList.size();

				StringBuilder loggerInfo = getLogDtls(inputTO, blobListSize);
				LOGGER.info(loggerInfo.toString());
				//Creating byte array of zip files to send back to requested branch
				packetIds = new ArrayList<Long>(blobListSize);
				for(OutboundDataPacketWrapperDO officePacket : branchDataList) {
					String zipFile = tempBranchFolderPath
							+ File.separator +officePacket.getDataOfficePacketId()+ FrameworkConstants.ZIP_EXTENSION;
					ZipUtility.createLocalZipFile(zipFile, officePacket.getPacketData());
					packetIds.add(officePacket.getDataOfficePacketId());
				}
				inputTO.setPacketIds(packetIds);
				//inputTO.setBranchId(branchDataList.get(0).getOutboundOfficeId());
				loggerInfo.append(" adding to ZIP is END");
				LOGGER.info(loggerInfo.toString());
				byte[] zipCombo = ZipUtility.createInMemoryZipFile(tempBranchFolderPath);
				if(zipCombo!=null && zipCombo.length>0){
					inputTO.setFileData(zipCombo);
					updatePacketStatusAsInitiated(packetIds);
				}
				try {
					FileUtils.deleteDirectory(tempBranchFolder);
				} catch (Exception e) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch(deleteDirectory): ",e);
				}
			} catch (Exception e) {
				LOGGER.error("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch::central outbound:: ",e);
				throw e;
			}


		} else {
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch::central outbound:: there is no data for requested branch [" + inputTO.getBranchCode()+"]for the Request Id:["+uniqueId+"]");
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOutboundPacketDataForEachBranch::central outbound END====> for the UniqueId"+uniqueId);
		return inputTO;
	}

	private StringBuilder getLogDtls(OutboundBranchDataTO inputTO,
			int blobListSize) {
		StringBuilder loggerInfo=new StringBuilder("AbstractBcunDatasyncServiceImpl::getOutboundBranchData::central outbound:: total number of data available for transfer [" );
		loggerInfo.append(blobListSize);
		loggerInfo.append("] for the Request Id:[");
		loggerInfo.append(inputTO!=null?inputTO.getUniqueRequestId():null);
		loggerInfo.append("]");
		return loggerInfo;
	}
	
	@Async()
	@Override
	public void updatePacketStatus(String json) throws CGSystemException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updatePacketStatus::central outbound start====>");
		//Converting branch JSON request to java object
		String uniqueId=null;
		try {
			OutboundBranchDataTO inputTO = (OutboundBranchDataTO)jsonStringToJava(json, OutboundBranchDataTO.class);
			 uniqueId=inputTO!=null ?inputTO.getUniqueRequestId():null;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::updatePacketStatus::central outbound:: getting requested data for branch [" + inputTO.getBranchCode()+"] for the Request Id ["+uniqueId+"]");
			//Updating branch requested data
			String flag=inputTO.getUpdateFlagStatus();
			if(!StringUtil.isStringEmpty(flag)){
				String qryName=null;
				if(flag.equalsIgnoreCase(BcunConstant.DATA_TRANSFER_STATUS_TRANSFERRED)){
					qryName="updateTransferredStatus";
					datasyncDao.updatePacketStatus(inputTO,qryName);
				}else if(flag.equalsIgnoreCase(BcunConstant.DATA_TRANSFER_STATUS_NEW)){
					qryName="updateFailedPackets";
					datasyncDao.updatePacketStatus(inputTO, qryName);
				}
			}
			inputTO=null;
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::updatePacketStatus::ERROR ",e);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updatePacketTransferedFailStatus::central outbound END(Completed for"+uniqueId+")");
	}
	public void updatePacketStatusAsInitiated(List<Long> officePacketList) throws CGSystemException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updatePacketStatusAsInitiated::central outbound start====>");
		if(!CGCollectionUtils.isEmpty(officePacketList)){
			OutboundBranchDataTO packetInfo=new OutboundBranchDataTO();
			packetInfo.setPacketIds(officePacketList);
			datasyncDao.updatePacketStatus(packetInfo, "updateInitiatedStatus");
		}else{
			LOGGER.info("AbstractBcunDatasyncServiceImpl::updatePacketStatusAsInitiated:: officePacketList is empty not required to update");
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::updatePacketStatusAsInitiated::central outbound END====>");
	}
	
	@Override
	public void updatePacketInitiatedStatus(List<OutboundOfficePacketDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(OutboundOfficePacketDO packet : baseList) {
			packet.setTransferStatus(BcunConstant.DATA_TRANSFER_STATUS_INITIATED);//I-Initiated
			//packet.setProcessedDate(Calendar.getInstance().getTime());//Added Blob processed Date
			saveOrUpdateTransferedEntity(packet);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#jsonStringToJava(java.lang.String, java.lang.Class)
	 */
	@Override
	public Object jsonStringToJava(String jsonString, Class clazz) {
		//Getting mapper object
		ObjectMapper mapper = new ObjectMapper(); 
		Object requiredObject = null;
		try {
			//converting JSON string to java object with the help of mapper  
			requiredObject = mapper.readValue(jsonString, clazz);
		} catch (JsonParseException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::jsonStringToJava::JsonParseException::",e);
		} catch (JsonMappingException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::jsonStringToJava::JsonMappingException::",e);
		} catch (IOException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::jsonStringToJava::IOException::",e);
		}
		return requiredObject;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#processFileAndUploadToDatabase(java.lang.String)
	 */
	@Override
	public  void processFileAndUploadToDatabase(String fileLocation) {
		//Getting all the files at specified location
		String[] fileNames = getAllFilesNames(fileLocation);
		if(fileNames != null && fileNames.length > 0) {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::total file has to be proceed=" + fileNames.length);
			for(String fileName : fileNames) {
				//boolean isInboundFile = fileName.startsWith("DataSync-Outbound-");
				File processFile = new File(fileLocation + File.separator + fileName); 
				if(processFile.isDirectory() )
					continue;
				processFile = null;
				readFileAndUpdateDB(fileName, fileLocation);
			}
		}
	}
	
	@Override
	public  void processFileAndUploadToDatabase(List<BcunFileSequenceTO> fileList, String baseLocation) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::START#####");
		if(fileList != null && !fileList.isEmpty()) {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::total file has to be proceed=" + fileList.size());
			for(BcunFileSequenceTO fileTo : fileList) {
				//boolean isInboundFile = fileName.startsWith("DataSync-Outbound-");
				String fileName = fileTo.getFileName();
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::Processing File :["+fileName+"]");
				//boolean isReProcessFiles = fileName.contains(RE_PROCESS_FLAG_NAME);
				readFileAndUpdateDB(fileName, baseLocation);
			}
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::processFileAndUploadToDatabase::END#####");
	}
	
	protected void readFileAndUpdateDB(String fileName, String baseLocation) {
		boolean isRenamed = false;
		try {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::processing file name: " + fileName+"Started");
			
			//Getting process name based on file name
			String processName = getProcessName(fileName);
			//Reading in bound properties entry 
			String className = this.getClass().getName();
			boolean isInbound = className.contains("Inbound");
			boolean isMasterData = processName.contains("Master");
			BcunConfigPropertyTO confTo = null;
			if(isMasterData) {
				confTo = OutboundMasterDataPropertyReader.getMasterConfigPropertyByProcessName(processName);
			} else if(isInbound){
				confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
			}else{
				confTo = OutboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
			}
			//Reading process data from file
			String filePath = baseLocation + File.separator + fileName; //getFilePath(fileName, baseLocation);
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::filePath:["+filePath+"]");
			File processFile = new File(filePath);
			List<CGBaseDO> baseList = getProcessDOListFromFile(processFile, confTo.getDoName());
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::updating db for file: " + fileName);
			//Getting configured formatter class name 
			String dataFormater = confTo.getDataFormater();
			AbstractDataFormater formaterService = getFormaterClass(dataFormater);
			if(formaterService!=null){
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::Calling Formatter:["+formaterService!=null ?formaterService.getClass().getName():"NO Formatter Defined"+"]");
			}
			List<CGBaseDO> errorDataList =null;
			List<CGBaseDO> tempDataList = null;
			List<CGBaseDO> specialDataList = null;
			if(baseList!=null){
			errorDataList = new ArrayList<CGBaseDO>(baseList.size());
			tempDataList = new ArrayList<CGBaseDO>(baseList.size());
			specialDataList= new ArrayList<CGBaseDO>(baseList.size());
			}
			//if(!errorDataList.isEmpty())
			for(CGBaseDO baseDO : baseList) {
				try {
					CGBaseDO formatedBaseDO = formateData(baseDO, formaterService);
					LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::Processing Object:["+formatedBaseDO.getClass().getName()+"]");
					//Setting DT flag to Y to avoid cyclic transfer....
					setDefaultFlags(formatedBaseDO);
					updateCentralDB(formatedBaseDO);
				} /*catch (DataIntegrityViolationException ex) {
					errorDataList.add(baseDO);
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::categorised exception in updating individual entities.... : ", ex);
				} */catch (Exception ex) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::un categorised exception in updating individual entities.... : ", ex);
					checkAndAddErrorTypeList(tempDataList, errorDataList, ex, baseDO);
				}
				if(!StringUtil.isStringEmpty(baseDO.getMandatoryFlag()) &&baseDO.getMandatoryFlag().equalsIgnoreCase(CommonConstants.YES) ){
					checkForSpecialErrors(specialDataList, new CGBusinessException("not-null property references a null"), baseDO);
				}
			}
			
			
			
			
			try{
				
				if(!CGCollectionUtils.isEmpty(tempDataList)){
					LOGGER.warn("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::Creating  file For temp list of the File :[" + fileName+"]");
					createTempFile(tempDataList, fileName);
				}
				if(!CGCollectionUtils.isEmpty(errorDataList)){
					LOGGER.warn("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::Creating  file  For error list of the File :[" + fileName+"]");
					createErrorFile(errorDataList, fileName);
				}
				if(!CGCollectionUtils.isEmpty(specialDataList)){
					LOGGER.warn("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::Creating  file  For specialDataList list of the File :[" + fileName+"]");
					createSpecialTempFile(specialDataList, fileName);
				}
				isRenamed = renameFile(fileName, baseLocation, PROCESSED_FOLDER_NAME);
				
			}catch(Exception e){
				LOGGER.error("AbstractBcunDatasyncServiceImpl::Temporary Files creation failed::: EXCEPTION", e);
			}
			
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::File Renaming :[" + isRenamed+"] for the File :["+fileName+"]");
			//Moving original file to pr folder.
			 
			
			processFile = null;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::processing completed for file named: " + fileName);
		} catch (Exception ex) {
			isRenamed = renameFile(fileName, baseLocation, ERROR_FOLDER_NAME);
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::moved to er :EXCEPTION " + isRenamed);
			LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::moved to er : EXCEPTION", ex);
		}
	}
	
	@Override
	public void createTempFile(List<CGBaseDO> errorDataList, String fileName) throws IOException {
		String errorJsonString = getJSONFromBaseDOList(errorDataList);
		createTempFile(errorJsonString, fileName);
	}
	
	@Override
	public void createTempFile(String errorJsonString, String fileName)
			throws IOException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createTempFile::creating temp file start.....");
		String fileLocation = getReprocessFileLocation();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createTempFile::temp error fileLocation=> "+fileLocation);
		createFolderIfNotExist(fileLocation);
		//FIXME 
		int coutOccurence=StringUtil.countOccurrencesOf(fileName,RE_PROCESS_FLAG_NAME);
		if(coutOccurence>=5){
			fileName=StringUtil.delete(fileName, RE_PROCESS_FLAG_NAME);
			sendEmailNotificationForFileReprocessing(fileName);
			
			
			//send Email Notification & reset file Name
		}
		File errorFile = new File(fileLocation + File.separator + RE_PROCESS_FLAG_NAME + fileName); 
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createTempFile::temp error file name=> "+errorFile);
		writeJSONFile(errorJsonString, errorFile);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createTempFile::creating temp file end.....");
	}
	
	private void sendEmailNotificationForFileReprocessing(String fileName) {
		//send Email Notification & reset file Name
		MailSenderTO mailSenderTo = prepareEmailSenderTO(fileName);
		if(StringUtil.isNull(mailSenderTo)){
			return ;
		}
		try {
			emailSenderUtil.sendEmail(mailSenderTo);
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForFileReprocessing::Exception.....",e);
		}
	}
	
	@Override
	public  void sendEmailNotificationForFile(String fileName,String message) {
		//send Email Notification & reset file Name
		MailSenderTO mailSenderTo = prepareEmailSenderTO(fileName);
		if(!StringUtil.isStringEmpty(message)){
		mailSenderTo.setMailSubject(message);
		StringBuilder mailbody= new StringBuilder();
		mailbody.append("<html><head></head><body>");
		mailbody.append("Dear IT Department, <BR>");
		
		mailbody.append(" System identified as this  File :["+fileName+"] has not constructed properly(Incomplete) <BR> <BR>");
		mailbody.append(" <B> Please contact It Team</B> <BR>");
		mailbody.append(" <BR><BR> Regards,<BR> IT-Support Team");
		mailbody.append("</body></html>");
		mailSenderTo.setPlainMailBody(mailbody.toString());
		}
		if(StringUtil.isNull(mailSenderTo)){
			return ;
		}
		try {
			emailSenderUtil.sendEmail(mailSenderTo);
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForFileReprocessing::Exception.....",e);
		}
	}

	private MailSenderTO prepareEmailSenderTO(String fileName) {
		MailSenderTO mailSenderTo= new MailSenderTO();
		String fileOfficeEmailId=null;
		fileOfficeEmailId= getOfficeEmailIdFromFileName(fileName);
		mailSenderTo.setMailSubject("Issue with File Processing at Branch:["+processingBrCode+"]");
		StringBuilder tomailIds= new StringBuilder();
		List<String> officeMailIds = getEmailIdByOfficeCode(processingBrCode);
		List<String> receiverMailId = getBcunReceiverEmailId();
		if(!CGCollectionUtils.isEmpty(officeMailIds)){
			if(!StringUtil.isStringEmpty(officeMailIds.get(0))){
				tomailIds.append(officeMailIds.get(0).trim());
			}
		}
		if(!CGCollectionUtils.isEmpty(receiverMailId)){
			if(!StringUtil.isStringEmpty(receiverMailId.get(0))){
				if(!StringUtil.isStringEmpty(tomailIds.toString())){
					tomailIds.append(FrameworkConstants.CHARACTER_COMMA);
				}
				tomailIds.append(receiverMailId.get(0).trim());
			}
		}
		//For File Name of the Office
		if(!StringUtil.isStringEmpty(fileOfficeEmailId)){
			if(!StringUtil.isStringEmpty(tomailIds.toString())){
				tomailIds.append(FrameworkConstants.CHARACTER_COMMA);
			}
			tomailIds.append(fileOfficeEmailId.trim());
		}
		if(StringUtil.isNull(tomailIds) || StringUtil.isStringEmpty(tomailIds.toString())){
			LOGGER.error("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForFileReprocessing::To Email Id are empty :hence There is   no mail notification"+tomailIds);
		return null;
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForFileReprocessing::To Email Id"+tomailIds);
		
		StringBuilder mailbody= new StringBuilder();
		mailbody.append("<html><head></head><body>");
		mailbody.append("Dear IT Department, <BR>");
		mailbody.append(" Issue with File Processing at Branch :["+processingBrCode+"] <BR> <BR>");
		mailbody.append(" For the File Name :["+fileName+"] <BR> <BR>");
		mailbody.append(" <B> Temp reprocessing limit has reached. Resetting the count </B> <BR>");
		mailbody.append(" <BR><BR> Regards,<BR> IT-Support Team");
		mailbody.append("</body></html>");
		mailSenderTo.setPlainMailBody(mailbody.toString());
		mailSenderTo.setTo(tomailIds.toString().split(FrameworkConstants.CHARACTER_COMMA));
		return mailSenderTo;
	}

	private String getOfficeEmailIdFromFileName(String fileName) {
		List<String> fileOfficeEmailId=null;
		String emailId=null;
		String officeCode=null;
		String bcunType=BcunConstant.BCUN_PHRASE_INBOUND;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOfficeEmailIdFromFileName:: START #########FileName....."+fileName);
		try {
			String fileNameArray[]= fileName.split(FrameworkConstants.CHARACTER_HYPHEN);
			if(!StringUtil.isEmpty(fileNameArray) && fileNameArray.length>=2){
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOfficeEmailIdFromFileName:: Searching for "+bcunType+" keyword in the #########FileName....."+fileName);
				int indx=StringUtil.linearSearch(fileNameArray,bcunType);
				if(indx >=0){
					officeCode= fileNameArray[indx+1];
					if(!StringUtil.isStringEmpty(officeCode)){
						fileOfficeEmailId = getEmailIdByOfficeCode(officeCode);
					}
				}else{
					LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOfficeEmailIdFromFileName::  "+bcunType+" keyword  Does not Exist in the #########FileName....."+fileName);
				}
			}
			if(!CGCollectionUtils.isEmpty(fileOfficeEmailId)){
				emailId=fileOfficeEmailId.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::getOfficeEmailIdFromFileName::Exception #############FileName....."+fileName+": BranchCode:["+officeCode+"] Email Id:["+emailId+"] Exception ::",e);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getOfficeEmailIdFromFileName::END #############FileName....."+fileName+": BranchCode:["+officeCode+"] Email Id:["+emailId+"]");
		return emailId;
	}

	private List<String> getBcunReceiverEmailId() {
		List<String> receiverMailId= (List<String>)datasyncDao.getAnonymusTypeDataByNamedQueryAndNamedParam(BcunDataFormaterConstants.QRY_GET_CONFIG_PARAM_VALUE_BY_NAME, new String[]{BcunDataFormaterConstants.QRY_PARAM_PARAM_NAME}, new String[]{BcunDataFormaterConstants.QRY_GET_FILE_RE_PROCESSING_EMAIL_ID});
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getBcunReceiverEmailId::receiverMailId....."+receiverMailId);
		return receiverMailId;
	}

	private List<String> getEmailIdByOfficeCode(String officeCode) {
		List<String> officeMailIds= (List<String>)datasyncDao.getAnonymusTypeDataByNamedQueryAndNamedParam(BcunDataFormaterConstants.QRY_GET_OFFICE_EMAIL_BY_OFFICE_CODE, new String[]{BcunDataFormaterConstants.QRY_PARAM_BRANCH_CODE}, new String[]{officeCode});
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getEmailIdForConfiguredOffice::officeMailIds....."+officeMailIds);
		return officeMailIds;
	}
	
	@Override
	public void createErrorFile(List<CGBaseDO> errorDataList, String fileName) throws IOException {
		String errorJsonString = getJSONFromBaseDOList(errorDataList);
		createErrorFile(errorJsonString, fileName);
	}
	
	@Override
	public void createErrorFile(String errorJsonString, String fileName) throws IOException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createErrorFile::creating error file start.....");
		String fileLocation = getErrorFileLocation();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createErrorFile::error fileLocation=> "+fileLocation);
		createFolderIfNotExist(fileLocation);
		File errorFile = new File(fileLocation + File.separator + ERROR_FLAG_NAME + fileName); 
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createErrorFile::error file name=> "+errorFile);
		writeJSONFile(errorJsonString, errorFile);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createErrorFile::creating error file end.....");
	}
	@Override
	public void createUnParsingErrorFile(QueueDataContentTO queueContent) throws IOException {
		boolean isEmailSent=false;
		String fileName=queueContent.getFileName();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createUnParsingErrorFile::creating temp file start.....");
		try {
			String fileLocation = getErrorFileLocation();
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::createUnParsingErrorFile:: error fileLocation=> "+fileLocation);
			createFolderIfNotExist(fileLocation);
			File errorFile = new File(fileLocation + File.separator + ERROR_FLAG_NAME + fileName); 
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::createUnParsingErrorFile:: error file name=> "+errorFile);
			writeJSONFile(queueContent.getJsonText(), errorFile);
		} catch (Exception e) {
			isEmailSent=true;
			LOGGER.error("AbstractBcunDatasyncServiceImpl::createUnParsingErrorFile::Exception Occurred.....FileName:["+queueContent.getFileName()+"]",e);
			sendEmailNotificationForFileReprocessing(fileName);
			throw e;
		}
		if(!isEmailSent){
			sendEmailNotificationForFileReprocessing(fileName);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createUnParsingErrorFile::creating Error Xml file end.....");
	}
	
	@Override
	public void setDefaultFlags(CGBaseDO formatedBaseDO) {
		formatedBaseDO.setDtToCentral("Y");
		formatedBaseDO.setDtToOpsman("N");
		formatedBaseDO.setDtFromOpsman("N");
		formatedBaseDO.setDtUpdateToCentral("N");
	}
		
	@Override
	public void setCentralToBranchDTFlags(CGBaseDO baseDO) {
		baseDO.setDtToBranch(CommonConstants.NO);
	}
	
	public AbstractDataFormater getFormaterClass(String formaterClass) throws Exception {
		AbstractDataFormater formaterService = null;
		if(!StringUtil.isStringEmpty(formaterClass)) {
			try {
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::instantiating data formater " + formaterClass);
				formaterService = (AbstractDataFormater) Class.forName(formaterClass).newInstance();
			} catch (Exception ex) {
				LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::error in instantiating data formater " + formaterClass);
				throw ex;
			}
		}else{
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::NO Formatter is defined");
		}
		return formaterService;
	}
	
	protected void readErrorFileAndUpdateDB(String fileName, String fileLocation, File processFile) {
		boolean isRenamed = false;
		try {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::processing file named: " + fileName);
			
			BcunConfigPropertyTO confTo = getConfigPropFromFileName(fileName);
			//Reading process data from file
			List<CGBaseDO> baseList = getProcessDOListFromFile(processFile, confTo.getDoName());
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::updating db for file: " + fileName);
			//Getting configured formatter class name 
			String dataFormater = confTo.getDataFormater();
			AbstractDataFormater formaterService = null;
			if(!StringUtil.isStringEmpty(dataFormater)) {
				try {
					LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::instantiating data formater " + dataFormater);
					formaterService = (AbstractDataFormater) Class.forName(dataFormater).newInstance();
				} catch (Exception ex) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::error in instantiating data formater :: Exception...",ex);
				}
			}else{
				LOGGER.warn("AbstractBcunDatasyncServiceImpl::readErrorFileAndUpdateDB::NO Formatter is defined");
			}
			List<CGBaseDO> errorDataList = new ArrayList<CGBaseDO>();
			for(CGBaseDO baseDO : baseList) {
				try {
					CGBaseDO formatedBaseDO = formateData(baseDO, formaterService);
					//Setting DT flag to Y to avoid cyclic transfer....
					setDefaultFlags(formatedBaseDO);
					updateCentralDB(formatedBaseDO);
				} catch (IllegalArgumentException | IllegalStateException ex) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::categorised exception in updating individual entities.... : ", ex);
					errorDataList.add(baseDO);
				} catch (Exception ex) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::un categorised exception in updating individual entities.... : ", ex);
					errorDataList.add(baseDO);
				}
			}
			processFile = null;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::processing completed for file named: " + fileName);
		} catch (Exception ex) {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::moved to er : " + isRenamed);
			LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedFileProcessing::moved to er : ", ex);
		}
	}
	
	private BcunConfigPropertyTO getConfigPropFromFileName(String fileName) {
		BcunConfigPropertyTO confTo = null;
		//Getting process name based on file name
		String processName = getProcessName(fileName);
		//Reading in bound properties entry 
		String className = this.getClass().getName();
		boolean isInbound = className.contains("Inbound");
		boolean isMasterData = processName.contains("Master");
		if(isMasterData) {
			confTo = OutboundMasterDataPropertyReader.getMasterConfigPropertyByProcessName(processName);
		} else if(isInbound){
			confTo = InboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
		}else{
			confTo = OutboundPropertyReader.getInboundConfigPropertyByProcessName(processName);
		}
		return confTo;
	}
	
	private void createFolderIfNotExist(String folderStr) {
		File folder = new File(folderStr);
		try {
			if(!folder.exists())
				FileUtils.forceMkdir(folder);
		} catch (Exception ex) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::createFolderIfNotExist::EXCPTION======>" ,ex);
		}
		folder = null;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getProcessName(java.lang.String)
	 */
	public String getProcessName(String fileName) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getProcessDOName::fileName======>" + fileName);
		String processName = null;
		/*Getting process name from file name : 
		DataSync-Inbound-BranchCode-processName-RandomNo.xml
		DataSync-Inbound-MUL-processName-1376645539279.xml
		*/
		if(!StringUtil.isStringEmpty(fileName)) {
			String[] fileNameContents =  fileName.split("-");
			if(fileNameContents != null && fileNameContents.length > 0) {
				if(fileNameContents.length >= 3)
					processName = fileNameContents[3];
				else if(fileNameContents.length ==2)
					processName = fileNameContents[1];	
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::getProcessDOName::processName======>" + processName);
			}
		}else{
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::getProcessDOName::File name is Empty======>" );
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getProcessDOName::end======>");
		return processName;
	}
	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#updateCentralDB(java.util.List, java.lang.String)
	 */
	@Override
	public boolean updateCentralDB(List<CGBaseDO> baseList){
		boolean isUpdated = false;
		if(baseList != null && !baseList.isEmpty()) {
			saveOrUpdateTransferedEntities(baseList);
			isUpdated = true;
		}
		return isUpdated;
	}
	
	@Override
	public boolean updateCentralDB(CGBaseDO baseDO) {
		boolean isUpdated = false;
		if(baseDO != null) {
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateCentralDB::START======>");
			saveOrUpdateTransferedEntity(baseDO);
			isUpdated = true;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::updateCentralDB::END======>");
		}
		return isUpdated;
	}
	
	@Override
	public CGBaseDO formateData(CGBaseDO baseDO, AbstractDataFormater formaterService) throws CGBusinessException, CGSystemException {
		//Checking whether insert or update request
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::formateData::START======>");
		boolean isSave = baseDO.getDtUpdateToCentral() != null && baseDO.getDtUpdateToCentral().equalsIgnoreCase("Y") ? true : false;
		baseDO = formaterService == null ? baseDO : isSave? baseDO = formaterService.formatUpdateData(baseDO, this) : formaterService.formatInsertData(baseDO, this);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::formateData::END======>");
		return baseDO;
	}
	
	protected void formateData(List<CGBaseDO> baseList, AbstractDataFormater formaterService) throws InstantiationException, IllegalAccessException, ClassNotFoundException, CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::formateData::START======>");
		if(baseList != null && !baseList.isEmpty()) {
			for(CGBaseDO baseDO : baseList) {
				//Checking whether insert or update request
				formateData(baseDO, formaterService);
			}
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::formateData::END======>");
	}
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		boolean result = file.delete();
		return result;
	}
	
	public boolean renameFile(String fileName, String baseLocation, String processedFolder) {
		boolean isRenamed = false;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::renameFile::file Name[" + fileName + "] baseLocation:[ "+baseLocation+"] processedFolder :["+processedFolder+"]");
		String oldFilePath = baseLocation +  File.separator + fileName;//getFilePath(fileName, baseLocation);
		boolean apendInPath = baseLocation.contains(RE_PROCESS_FOLDER_NAME) || baseLocation.contains(PROCESSED_FOLDER_NAME) ||baseLocation.contains(ERROR_FOLDER_NAME) ;
		if(apendInPath) {
			int lastIndex = baseLocation.lastIndexOf(File.separator);
			baseLocation = baseLocation.substring(0, lastIndex);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::renameFile::file Name[" + fileName + "] baseLocation:[ "+baseLocation+"] processedFolder :["+processedFolder+"] oldFilePath :["+oldFilePath+"]");
		File folder = new File( baseLocation + File.separator + processedFolder);
		try {
			if(!folder.exists())
				FileUtils.forceMkdir(folder);
			folder = null;
			
			String processedLocation = baseLocation + File.separator + processedFolder + File.separator + processedFolder.toUpperCase() + "_"+ fileName;
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::renameFile::trying to move file to location[" + processedLocation + "]");
			File oldFile = new File(oldFilePath);
			File newFile = new File(processedLocation);
			isRenamed = oldFile.renameTo(newFile);
			if(!isRenamed){//new Code
				try {
					org.apache.commons.io.FileUtils.moveFile(oldFile, newFile);
					isRenamed=true;
				} catch (IOException e) {
					LOGGER.error("moveFile###ERROR(IOException) IN: File movement newFile "+newFile+":oldFile:"+oldFile,e);
					isRenamed=false;
				}
			}
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::renameFile::file movement has done[" + isRenamed + "]");
			newFile = null;
			oldFile = null;
		} catch (IOException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::renameFile::error(IOException)", e);
		}catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::renameFile::error(Exception)", e);
		}
		return isRenamed;
	}
	public boolean renameFileByManual(String fileName, String baseLocation, String processedFolder) {
		boolean isRenamed = false;
		LOGGER.warn("AbstractBcunDatasyncServiceImpl::renameFile::file Name[" + fileName + "] baseLocation:[ "+baseLocation+"] processedFolder :["+processedFolder+"]");
		String oldFilePath = baseLocation +  File.separator + fileName;//getFilePath(fileName, baseLocation);
		boolean apendInPath = baseLocation.contains(RE_PROCESS_FOLDER_NAME) || baseLocation.contains(PROCESSED_FOLDER_NAME) ||baseLocation.contains(ERROR_FOLDER_NAME) ;
		if(apendInPath) {
			int lastIndex = baseLocation.lastIndexOf(File.separator);
			baseLocation = baseLocation.substring(0, lastIndex);
		}
		LOGGER.warn("AbstractBcunDatasyncServiceImpl::renameFile::file Name[" + fileName + "] baseLocation:[ "+baseLocation+"] processedFolder :["+processedFolder+"] oldFilePath :["+oldFilePath+"]");
		File folder = new File( baseLocation + File.separator + processedFolder);
		try {
			if(!folder.exists())
				FileUtils.forceMkdir(folder);
			folder = null;
			
			String processedLocation = baseLocation + File.separator + processedFolder + File.separator + processedFolder.toUpperCase() + "_M_"+ fileName;
			LOGGER.warn("AbstractBcunDatasyncServiceImpl::renameFile::trying to move file to location[" + processedLocation + "]");
			File oldFile = new File(oldFilePath);
			File newFile = new File(processedLocation);
			isRenamed = oldFile.renameTo(newFile);
			if(!isRenamed){//new Code
				try {
					org.apache.commons.io.FileUtils.moveFile(oldFile, newFile);
					isRenamed=true;
				} catch (IOException e) {
					LOGGER.error("moveFile###ERROR(IOException) IN: File movement newFile "+newFile+":oldFile:"+oldFile,e);
					isRenamed=false;
				}
			}
			LOGGER.info("AbstractBcunDatasyncServiceImpl::renameFile::file movement has done[" + isRenamed + "]");
			newFile = null;
			oldFile = null;
		} catch (IOException e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::renameFile::error(IOException)", e);
		}catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::renameFile::error(Exception)", e);
		}
		return isRenamed;
	}
	
	
	@Override
	public List<CGBaseDO> getOutboundMasterDataFromDB(String hql, Integer maxRowFetch) {
		List<CGBaseDO> unSyncData = datasyncDao.getOutboundMasterDataFromDB(hql, maxRowFetch);
		return unSyncData;
	}
	
	@Override
	public Integer getUniqueId(String queryName, String[] params,Object[] values) {
		return datasyncDao.getUniqueId( queryName,  params, values);
	}
	
	@Override
	public String getInboundFileLocAtCentral() {
		return bcunProperties.getProperty("linux.central.inbound.file.location");
	}
	@Override
	public void prepareNStoreZipFile(List<CGBaseDO> baseList, Integer office, String processName) throws IOException {
		//List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::");
			String jsonObject = getJSONFromBaseDOList(baseList);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String xmlFileName = getOutboundFileName(processName);
			File xmlFIle = new File(xmlFileName);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = getOutboundZipFileName(processName);//fileLocation + File.separator + processName + "-" + currentTime + ".zip";
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create zip file");
				} else {
					Set<Integer> destinationOffices=new HashSet<>(1);
					destinationOffices.add(office);
					OutboundDataPacketDO packetData=preparePacket(processName, blob, destinationOffices);
					if(packetData!=null){
						LOGGER.info("OutboundCentralDataProcessor::Packet Created For the Process :[ "+ packetData.getFileName()+"]");

						try {
							//boolean isDeleted = xmlFIle.delete();
							//LOGGER.debug("OutboundCentralDataProcessor::deleting file after packet creation::deleted :[ "+ isDeleted+" ]");
							saveOrUpdateTransferedEntity(packetData);//new Implementation
							udateDataTransferToBranchStatus(baseList);
						} catch (Exception ex) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at Blob creation/flag update) :[ "+ packetData.getFileName()+"]",ex);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
							throw ex;
						}/* finally {
						xmlFIle.delete();
					}*/
						try {
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "pr");
						} catch (Exception e) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at the time of processed file renaming) :[ "+ packetData.getFileName()+"]",e);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
						}
					}else{
						LOGGER.error("OutboundCentralDataProcessor::No packet is created");
					}
				}
			} else {
				LOGGER.warn("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create xml file");
				//xmlFIle.delete();
			}
		}
	}
	
	@Override
	public void prepareNStoreZipFile(List<CGBaseDO> baseList, Set<Integer> destinationOffices, String processName) throws IOException {
		//List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(!CGCollectionUtils.isEmpty(baseList) && !CGCollectionUtils.isEmpty(destinationOffices)) {
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess:: With Multiple Destination List:");
			String jsonObject = getJSONFromBaseDOList(baseList);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String xmlFileName = getOutboundFileName(processName);//fileLocation + File.separator + processName+ "-" + currentTime + ".xml";
			File xmlFIle = new File(xmlFileName);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = getOutboundZipFileName(processName);//String zipFileName = fileLocation + File.separator + processName + "-" + currentTime + ".zip";
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create zip file");
					renameFile(xmlFileName, getBaseFileLocation(), "er");
				} else {
					OutboundDataPacketDO packetData=preparePacket(processName, blob, destinationOffices);
					if(packetData!=null){
						LOGGER.info("OutboundCentralDataProcessor::Packet Created For the Process :[ "+ packetData.getFileName()+"]");
						try {
							saveOrUpdateTransferedEntity(packetData);//new Implementation
							udateDataTransferToBranchStatus(baseList);
						} catch (Exception ex) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at Blob creation/flag update) :[ "+ packetData.getFileName()+"]",ex);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
							throw ex;
						}

						try {
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "pr");
						} catch (Exception e) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at the time of processed file renaming) :[ "+ packetData.getFileName()+"]",e);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
						}
					}else{
						LOGGER.error("OutboundCentralDataProcessor::No packet is created");
					}
				}
				
			} else {
				LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}else{
			LOGGER.error("OutboundCentralDataProcessor::prepareNStoreZipFile:: Blob not created  ::Base Do List Size[ " + (!CGCollectionUtils.isEmpty(baseList)?baseList.size():0)+"Office List :["+(!CGCollectionUtils.isEmpty(destinationOffices)?(destinationOffices.size()+" office(s)"+destinationOffices.toString()):0)+"]");
		}
	}
	
	@Override
	public void prepareAndStoreZipFile(List<CGBaseDO> baseList, Set<Integer> destinationOffices, String processName,String category) throws IOException {
		prepareAndStoreZipFile(baseList, destinationOffices, processName, category, null);
	}
	
	@Override
	public void prepareAndStoreZipFile(List<CGBaseDO> baseList, Set<Integer> destinationOffices, String processName,String category, String filePreFix) throws IOException {
		//List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(!CGCollectionUtils.isEmpty(baseList) && !CGCollectionUtils.isEmpty(destinationOffices)) {
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess:: With Multiple Destination List:");
			String jsonObject = getJSONFromBaseDOList(baseList);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
		//	String xmlFileName = getOutboundFileNameForCategory(processName,category);//fileLocation + File.separator + processName+ "-" + currentTime + ".xml";
			String xmlFileName = null;
			if(!StringUtil.isStringEmpty(filePreFix)){
				xmlFileName=getOutboundFileName(processName, getBaseFileLocation() + File.separator +filePreFix, FrameworkConstants.XML_EXTENSION);
			}else{
				xmlFileName = getOutboundFileNameForCategory(processName,category);//fileLocation + File.separator + processName+ "-" + currentTime + ".xml";
			}
			File xmlFIle = new File(xmlFileName);
			String destinationPrPath=getBaseFileLocation()+File.separator+PROCESSED_FOLDER_NAME+File.separator+"PR"+"_"+xmlFIle.getName();
			String destinationErPath=getBaseFileLocation()+File.separator+ERROR_FOLDER_NAME+File.separator+"ER"+"_"+xmlFIle.getName();
			if(!xmlFIle.exists()){
				com.capgemini.lbs.framework.utils.FileUtils.createDirectory(xmlFIle.getParent());
			}
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = writeJSONFile(jsonObject, xmlFIle);
			//String zipFileName = getOutboundZipFileNameForCategory(processName,category);
			String zipFileName = null;
			if(!StringUtil.isStringEmpty(filePreFix)){
				zipFileName= getOutboundFileName(processName, getBaseFileLocation() + File.separator +filePreFix, FrameworkConstants.ZIP_EXTENSION);
			}else{
				zipFileName = getOutboundZipFileNameForCategory(processName,category);
			}
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create zip file");
					org.apache.commons.io.FileUtils.moveFile(xmlFIle, new File(destinationErPath));
				} else {
					OutboundDataPacketDO packetData=preparePacket(processName, blob, destinationOffices);
					if(packetData!=null){
						LOGGER.info("OutboundCentralDataProcessor::Packet Created For the Process :[ "+ packetData.getFileName()+"]");
						try {
							saveOrUpdateTransferedEntity(packetData);//new Implementation
						} catch (Exception ex) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at Blob creation/flag update) :[ "+ packetData.getFileName()+"]",ex);
							//renameFile(xmlFIle.getName(), getOutboundBaseFileLocationByCategory(category), "er");
							org.apache.commons.io.FileUtils.moveFile(xmlFIle, new File(destinationErPath));
							throw ex;
						}

						try {
							org.apache.commons.io.FileUtils.moveFile(xmlFIle, new File(destinationPrPath));
						} catch (Exception e) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at the time of processed file renaming) :[ "+ packetData.getFileName()+"]",e);
							org.apache.commons.io.FileUtils.moveFile(xmlFIle, new File(destinationErPath));
						}
					}else{
						LOGGER.error("OutboundCentralDataProcessor::No packet is created");
					}
				}
				
			} else {
				LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}else{
			LOGGER.error("OutboundCentralDataProcessor::prepareNStoreZipFile:: Blob not created  ::Base Do List Size[ " + (!CGCollectionUtils.isEmpty(baseList)?baseList.size():0)+"Office List :["+(!CGCollectionUtils.isEmpty(destinationOffices)?(destinationOffices.size()+" office(s)"+destinationOffices.toString()):0)+"]");
		}
	}

	@Override
	public void prepareNStoreZipFile(List<CGBaseDO> baseList, Set<Integer> destinationOffices, String processName,boolean isForRateContract) throws IOException {
		//List<CGBaseDO> baseList = (List<CGBaseDO>) bcunService.getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(!CGCollectionUtils.isEmpty(baseList) && !CGCollectionUtils.isEmpty(destinationOffices)) {
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess:: With Multiple Destination List:");
			String jsonObject = getJSONFromBaseDOList(baseList);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
			String xmlFileName = getOutboundFileName(processName);//fileLocation + File.separator + processName+ "-" + currentTime + ".xml";
			File xmlFIle = new File(xmlFileName);
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
			boolean isWritenToXmlFile = writeJSONFile(jsonObject, xmlFIle);
			String zipFileName = getOutboundZipFileName(processName);//String zipFileName = fileLocation + File.separator + processName + "-" + currentTime + ".zip";
			LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::file created::" + isWritenToXmlFile);
			if(isWritenToXmlFile) {
				byte[] blob = createZipFile(zipFileName, xmlFileName);
				if(blob == null || blob.length == 0) {
					LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create zip file");
					renameFile(xmlFileName, getBaseFileLocation(), "er");
				} else {
					OutboundDataPacketDO packetData=preparePacket(processName, blob, destinationOffices);
					if(packetData!=null){
						LOGGER.info("OutboundCentralDataProcessor::Packet Created For the Process :[ "+ packetData.getFileName()+"]");
						try {
							saveOrUpdateTransferedEntity(packetData);//new Implementation
							if(isForRateContract){
								udateDataTransferToBranchStatusForContract(baseList);
							}else{
							udateDataTransferToBranchStatus(baseList);
							}
						} catch (Exception ex) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at Blob creation/flag update) :[ "+ packetData.getFileName()+"]",ex);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
							throw ex;
						}
						
						try {
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "pr");
						} catch (Exception e) {
							LOGGER.error("OutboundCentralDataProcessor::renameFile(at the time of processed file renaming) :[ "+ packetData.getFileName()+"]",e);
							renameFile(xmlFIle.getName(), getBaseFileLocation(), "er");
						}
					}else{
						LOGGER.error("OutboundCentralDataProcessor::No packet is created");
					}
				}
				
			} else {
				LOGGER.debug("OutboundCentralDataProcessor::proceedIndividualProcess::unable to create xml file");
			}
		}else{
			LOGGER.error("OutboundCentralDataProcessor::prepareNStoreZipFile:: Blob not created  ::Base Do List Size[ " + (!CGCollectionUtils.isEmpty(baseList)?baseList.size():0)+"Office List :["+(!CGCollectionUtils.isEmpty(destinationOffices)?(destinationOffices.size()+" office(s)"+destinationOffices.toString()):0)+"]");
		}
	}
	private String getOutboundFileName(String propKey) {
		return getOutboundFileName(propKey,getOutboundFileNamePrefix(),FrameworkConstants.XML_EXTENSION);
	}
	
	private String getOutboundFileName(String propKey,String fileNamePrefix,String fileTypeExtension) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(fileNamePrefix);
		fileName.append(propKey);
		fileName.append(FrameworkConstants.CHARACTER_HYPHEN);
		fileName.append(System.currentTimeMillis());
		fileName.append(fileTypeExtension);
		return fileName.toString();
	}
	private String getOutboundZipFileName(String propKey) {
		return getOutboundFileName(propKey,getOutboundFileNamePrefix(),FrameworkConstants.ZIP_EXTENSION);
	}
	private String getOutboundFileNamePrefix() {
		String fileLocation =getBaseFileLocation() + File.separator + "DataSync-Outbound-MUL-";
		LOGGER.debug("InboundBranchDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
		return fileLocation;
	}
	private String getOutboundZipFileNameForCategory(String propKey,String category) {
		return getOutboundFileName(propKey, getOutboundFileNamePrefixForCategory(category), FrameworkConstants.ZIP_EXTENSION);
	}
	private String getOutboundFileNameForCategory(String propKey,String category) {
		 return getOutboundFileName(propKey, getOutboundFileNamePrefixForCategory(category), FrameworkConstants.XML_EXTENSION);
	}
	private String getOutboundFileNamePrefixForCategory(String category) {
		String fileLocation = null;
		fileLocation = getBaseFileLocation();
		if(!StringUtil.isStringEmpty(category)){
			fileLocation = fileLocation + File.separator +category+File.separator+ "DataSync-Outbound-MUL-";
		}else{
			fileLocation = fileLocation +File.separator+ "DataSync-Outbound-MUL-";
		}
		LOGGER.debug("InboundBranchDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
		return fileLocation;
	}
	

	/**
	 * @param processName
	 * @param blob
	 * @param officeList
	 */
	private OutboundDataPacketDO preparePacket(String processName, byte[] blob,
			Set<Integer> officeList) {
		OutboundDataPacketDO packetData;
		packetData = new OutboundDataPacketDO(); 
		packetData.setPacketData(blob);
		packetData.setFileName(processName);
		Set<OutboundOfficePacketDO> outboundDtls= null;
		outboundDtls=prepareDatasyncOffice(processName, officeList, packetData);
		packetData.setOutboundDtls(outboundDtls);
		return packetData;
	}

	/**
	 * @param processName
	 * @param officeList
	 * @param packetData
	 */
	private Set<OutboundOfficePacketDO> prepareDatasyncOffice(String processName,
			Set<Integer> officeList, OutboundDataPacketDO packetData) {
		Set<OutboundOfficePacketDO> outboundDtls;
		outboundDtls= new HashSet<>(officeList.size());
		for(Integer officeId:officeList){
			if(!StringUtil.isEmptyInteger(officeId)){
				OutboundOfficePacketDO  officePacket=new OutboundOfficePacketDO();
				officePacket.setOutboundOfficeId(officeId);
				officePacket.setProcessName(processName);
				officePacket.setTransferStatus(BcunConstant.DATA_TRANSFER_STATUS_NEW);
				officePacket.setPacketDO(packetData);
				outboundDtls.add(officePacket);
			}
		}
		return outboundDtls;
	}
	
	
	@Override
	public void proceedOutboundProcess(OutboundDatasyncConfigOfficeDO configOffice, OutboundConfigPropertyTO configProcess) throws IOException {
		List<CGBaseDO> baseList = (List<CGBaseDO>) getOfficeDataByNamedQueryAndRowCount(configProcess.getNamedQuery(), configProcess.getMaxRowCount(), configOffice.getOutboundOffice().getOfficeId());
		if(baseList != null && !baseList.isEmpty()) {
			Integer branchId = configOffice.getOutboundOffice().getOfficeId();
			Set<Integer> officeIds =  null;
			String officeSenderClass = configProcess.getOfficesFinder();
			if(officeSenderClass != null) {
				try {
					OutboundOfficeFinderService officeFinder =  (OutboundOfficeFinderService)Class.forName(officeSenderClass).newInstance();
					officeIds =  officeFinder.getAllOutboundOffices(branchId, this, baseList);
				} catch (Exception e) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::proceedOutboundProcess::EXCEPTION",e);
					throw  new IOException(e.getMessage());
				}
			}
			if(officeIds != null && !officeIds.isEmpty()) {
				officeIds.add(branchId);
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Finder Class :["+officeSenderClass+"] Found  office Details :["+officeIds.toString()+"]");
				prepareNStoreZipFile(baseList, officeIds, configProcess.getPropKey());
			} else {
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutboundProcess:: Process Key : ["+configProcess.getPropKey()+"] Finder Class :["+officeSenderClass+"] did not find any office Details");
				prepareNStoreZipFile(baseList, branchId, configProcess.getPropKey());
			}
			LOGGER.info("AbstractBcunDatasyncServiceImpl::proceedOutboundProcess::processing of " + configProcess.getProcess() + "for all offices completed...");
			//udateDataTransferToBranchStatus(baseList); moved to prepareNstoreZipFile
		}
	}
	public boolean deleteFile(File file) {
		return false;
	}
	
	public void setDatasyncDao(BcunDatasyncDAO datasyncDao) {
		this.datasyncDao = datasyncDao;
	}

	

	public EmailSenderUtil getEmailSenderUtil() {
		return emailSenderUtil;
	}

	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public void setBcunProperties(Properties bcunProperties) {
		this.bcunProperties = bcunProperties;
	}
	 
	public void setProcessingBrCode(String processingBrCode) {
		this.processingBrCode = processingBrCode;
	}
 	/**
 	 * Delete from database.
 	 *
 	 * @param qryName the qry name
 	 * @param paramWithValues the param with values
 	 * @return true, if successful
 	 * @throws CGBusinessException the cG business exception
 	 * @throws CGSystemException the cG system exception
 	 */
	@Override
 	public boolean deleteFromDatabase(String qryName,Map<Object,Object> paramWithValues) throws CGBusinessException,CGSystemException{
		return datasyncDao.loadAndDeleteFromDatabase(qryName, paramWithValues);
	}
	/**
	 * @param baseList
	 */
	private void udateDataTransferToBranchStatus(List<CGBaseDO> baseList) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutbountMasterDataPreparation::udateDataTransferToBranchStatus ::START");
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
		}
		datasyncDao.saveOrUpdateTransferedEntities(baseList);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutbountMasterDataPreparation::udateDataTransferToBranchStatus ::END");
	}
	private void udateDataTransferToBranchStatusForContract(List<CGBaseDO> baseList){
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusForContract::START");
		List<Integer> rateContractIdList= new ArrayList<Integer>(baseList.size());
		for(CGBaseDO baseDO : baseList) {
			Integer rateContractId= ((BcunRateContractDO)baseDO).getRateContractId();
			rateContractIdList.add(rateContractId);
		}
		datasyncDao.saveOrUpdateTransferedEntitiesForRateContract(rateContractIdList,"updateUnsyncOutboundRateContract");
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusForContract::END");
	}
	@Override
	public void udateDataTransferToBranchStatusByPrimaryKeyList(List<Integer> pojoPrimayKey, String queryName){
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusByPrimaryKeyList::START");
		datasyncDao.saveOrUpdateTransferedEntitiesForRateContract(pojoPrimayKey,queryName);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusByPrimaryKeyList::END");
	}
	@Override
	public void udateDataTransferToBranchStatusForDrs(List<CGBaseDO> baseList){
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusForContract::START");
		List<CGBcunInbundDO> cgBcunInboundList= new ArrayList<CGBcunInbundDO>(baseList.size());
		for(CGBaseDO baseDO : baseList) {
			CGBcunInbundDO inbound=new CGBcunInbundDO();
			DeliveryDO deliveryDO=(DeliveryDO)baseDO;
			inbound.setPojoPrimarykey(deliveryDO.getDeliveryId());
			inbound.setBusinesskey(deliveryDO.getDrsStatus());
			cgBcunInboundList.add(inbound);
		}
		datasyncDao.updateBcunDataTransferFlag(cgBcunInboundList,"updateDrsBcunFlag");
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::udateDataTransferToBranchStatusForContract::END");
	}
	
	public void updatePasswordInCentral(PasswordDO passwordDO) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedInboundPasswordPreparation::udatePasswordInCentral ::START");
		
		datasyncDao.saveOrUpdateTransferedEntity(passwordDO);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedInboundPasswordPreparation::udatePasswordInCentral ::END");
	}
	
	public void updatePasswordInBranch(BcunPasswordDO bcunPasswordDO) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutboundPasswordPreparation::udatePasswordInBranch ::START");
		
		datasyncDao.saveOrUpdateTransferedEntity(bcunPasswordDO);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::proceedOutboundPasswordPreparation::udatePasswordInBranch ::END");
	}
	
	@Override
	public boolean canProceedFile(String fileName) {
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: checking file name, extention and creation date ");
		if(fileName == null || fileName.equals("") || !fileName.endsWith(".xml"))
			return false;
		
		int coutOccurence=StringUtil.countOccurrencesOf(fileName,QUEUE_FLAG);
		long noOfDaysOldFile=getNumberOfDaysOldByFileName(fileName);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: File :["+fileName+"] no of times processed till now :["+coutOccurence+"] and it's Days:["+noOfDaysOldFile+"] old file");
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: no of times processed till now :["+coutOccurence+"] fileName:["+fileName+"]");
		if(coutOccurence>25){
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: fileName ["+fileName+"] has already been process NO time :["+coutOccurence+"] hence it's not reprocessing further");
			return false;
		}
		String[] reProcess =  fileName.split("_");
		if(reProcess.length > 10)
			return false;
		if(reProcess == null || reProcess.length < 5)
			return true;
		String fileTimeStr = reProcess[reProcess.length-1];
		if(fileTimeStr != null && !fileTimeStr.isEmpty()) {
			String[] fileTimeArr = fileTimeStr.split("-");
			if(fileTimeArr != null && fileTimeArr.length >=4 ) {/*
				String timestampStr = fileTimeArr[4];
				
				Calendar fileCal = Calendar.getInstance();
				timestampStr = timestampStr.substring(0, timestampStr.indexOf("."));
				fileCal.setTimeInMillis(Long.parseLong(timestampStr));
				int fileDayOfMonth = fileCal.get(Calendar.DAY_OF_MONTH);
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: fileDayOfMonth::" + fileDayOfMonth);
				int threshold = fileDayOfMonth + reProcess.length - 5;
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: threshold::" + threshold);
				Calendar sysCal = Calendar.getInstance();
				int sysDayOfMonth = sysCal.get(Calendar.DAY_OF_MONTH);
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: sysDayOfMonth::" + sysDayOfMonth);
				if(threshold == sysDayOfMonth)
					return true;
			*/}
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::canProceedFile:: ends...");
		return true;
	}

	private long getNumberOfDaysOldByFileName(String fileName) {
		long noOfDaysOldFile=0l;
		try {
			long milliseconds=com.capgemini.lbs.framework.utils.FileUtils.getTimestampFromFileName(fileName);
			noOfDaysOldFile=DateUtil.DayDifferenceBetweenTwoDates(DateUtil.convertMillisecondsToDate(milliseconds), new Date());
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl::canProceedFile:: getNumberOfDaysOldByFileName...",e);
		}
		return noOfDaysOldFile;
	}
	@Override
	public boolean proceedManualUploadprocess(String fileName,InputStream filecontent,String officeCode)throws CGBusinessException,CGSystemException{
		boolean status=true;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:::proceedManualUploadprocess :: [brCode :"+officeCode+"] fileName :["+fileName+"] START");
		String osName =System.getProperty("os.name");
		Boolean isWindows = osName.toUpperCase().contains("WINDOWS");
		String rootAppender=File.separator+"manual_extraction"+File.separator+officeCode+File.separator+BcunConstant.BCUN_UPLOAD;
		String rootDirectory=null;
		String currentSysTime=DateUtil.getCurrentTimeInString();

		if(isWindows){
			rootDirectory= "D:"+rootAppender;
		}else{
			rootDirectory= rootAppender;
		}
		String zipBase =rootDirectory+File.separator+"zip"+currentSysTime;
		String unzipBase= getBaseFileLocation();
		
		
			File zipFolders = new File(zipBase);

			if (!zipFolders.exists()) {
				zipFolders.mkdirs();
			}
			try {
				ZipUtility.createLocalZipFile(zipBase, fileName, filecontent);
			} catch (IOException e) {
				LOGGER.error("AbstractBcunDatasyncServiceImpl:::proceedManualUploadprocess :: [brCode :"+officeCode+"] fileName :["+fileName+"] EXCEPTION (createLocalZipFile)",e);
				status=false;
			}
			try {
				ZipUtility.unzip(zipBase+File.separator+fileName,unzipBase);
			} catch (Exception e) {
				status=false;
				LOGGER.error("AbstractBcunDatasyncServiceImpl:::proceedManualUploadprocess :: [brCode :"+officeCode+"] fileName :["+fileName+"] EXCEPTION (unzip)",e);
			}
			


		LOGGER.debug("AbstractBcunDatasyncServiceImpl:::proceedManualUploadprocess :: [brCode :"+officeCode+"] fileName :["+fileName+"] START");
		return status;
		
	}
	@Override
	public String proceedManualDownloadprocess(ManualDownloadInputTO inputTo)throws CGBusinessException,CGSystemException, IOException{
		String donwloadUrl=null;
		String status=inputTo.getBlobStatus();
		String officeCode=inputTo.getOfficeCode();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:::proceedManualDownloadprocess :: [brCode :"+officeCode+"]"+"[stDate :"+inputTo.getStartDate()+"]"+"[endDate :"+inputTo.getEndDate()+"]"+"[ status :"+inputTo.getBlobStatus()+"]");
		String osName =System.getProperty("os.name");
		Boolean isWindows = osName.toUpperCase().contains("WINDOWS");
		String rootAppender=File.separator+"manual_extraction"+File.separator+inputTo.getOfficeCode()+File.separator+BcunConstant.BCUN_DOWNLOAD;
		String rootDirectory=null;
		String finalDestination=null;
		String finalBase=null;
		String currentSysTime=DateUtil.getCurrentTimeInString();

		if(isWindows){
			rootDirectory= "D:"+rootAppender;
		}else{
			rootDirectory= rootAppender;
		}
		String appender=File.separator+inputTo.getStartDateStr().replace("/", "")+FrameworkConstants.CHARACTER_UNDERSCORE+inputTo.getEndDateStr().replace("/", "")+FrameworkConstants.CHARACTER_UNDERSCORE+(StringUtil.isStringEmpty(status)?"All":status)+FrameworkConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
		finalBase=rootDirectory;
		finalDestination=rootDirectory+appender;
		String zipBase =rootDirectory+File.separator+"zip"+currentSysTime;
		String zipDirectory =zipBase+appender;
		String unzipBase=rootDirectory+File.separator+"unzip"+currentSysTime;
		String unZipDirectory =unzipBase+appender;
		Boolean isUpdateRequire = StringUtil.isStringEmpty(status)?(false):((status.equalsIgnoreCase(BcunConstant.DATA_TRANSFER_STATUS_NEW)||status.equalsIgnoreCase(BcunConstant.DATA_TRANSFER_STATUS_INITIATED))?true:false);
		
		
		List<OutboundDataPacketWrapperDO> packetList= datasyncDao.getPacketDetailsForManualDownload(inputTo);
		if(!CGCollectionUtils.isEmpty(packetList)){
			File zipFolders = new File(zipDirectory);

			if (!zipFolders.exists()) {
				zipFolders.mkdirs();
			}
			File baseDirectory=new File(finalBase);
			if (!baseDirectory.exists()) {
				baseDirectory.mkdirs();
			}
			File unzipFolders = new File(unZipDirectory);

			if (!unzipFolders.exists()) {
				unzipFolders.mkdirs();
			}
			List<Long> officePacketIdList=new ArrayList<Long>();
			for(OutboundDataPacketWrapperDO officepacketDO:packetList){
				byte[] incomingFileData=officepacketDO.getPacketData();
				String	zip =zipDirectory+File.separator+officeCode +FrameworkConstants.CHARACTER_UNDERSCORE+officepacketDO.getDataOfficePacketId();
				LOGGER.debug("ManualDownloadServlet::doPost:: processUserRequest=======>ZIP FILE Path ["+zip+"]"); 
				ZipUtility.createLocalZipFile(zip, incomingFileData);
				ZipUtility.unzip(zip,unZipDirectory);
				LOGGER.debug("ManualDownloadServlet::doPost:: processUserRequest=======>Unzip FILE Path ["+unZipDirectory+"]"); 
				zip=null;
				incomingFileData = null;
				if(isUpdateRequire){
					//officepacketDO.setProcessedDate(DateUtil.getCurrentDate());
					//officepacketDO.setTransferStatus(BcunConstant.DATA_TRANSFER_STATUS_MANUAL);
					officePacketIdList.add(officepacketDO.getDataOfficePacketId());
				}
			}
			donwloadUrl = createFinalZipFile(unZipDirectory,finalDestination);
			deleteFoldersForManualDownload(zipBase, unzipBase);
			if(isUpdateRequire){
				//datasyncDao.updateDownloadedPacketStatus(finalPacketList);
				OutboundBranchDataTO packetInfo= new OutboundBranchDataTO();
				packetInfo.setPacketIds(officePacketIdList);
				datasyncDao.updatePacketStatus(packetInfo, "updateManualPacketStatus");
			}
		}


		LOGGER.debug("AbstractBcunDatasyncServiceImpl:::proceedManualDownloadprocess :: ENDs with the Processed URL :["+donwloadUrl+"]");
		return donwloadUrl;
		
	}

	private void deleteFoldersForManualDownload(String zipDirectory,
			String unZipDirectory) {
		try {
			FileUtils.deleteDirectory(new File(unZipDirectory));
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl:::deleteFoldersForManualDownload (unZipDirectory):: Exception :",e);
		}
		try {
			FileUtils.deleteDirectory(new File(zipDirectory));
		} catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl:::deleteFoldersForManualDownload(zipDirectory) :: Exception :",e);
		}
	}
	public static String createFinalZipFile(String unzipFolderPath,String destinationFile) throws IOException {

		LOGGER.debug("ZipUtility : createInMemoryZipFile() : START");

		String finalZip;
		FileOutputStream baos =null;
		try {
			int BUFFER = 802400;
			byte buffer[] = new byte[BUFFER];
			//com.capgemini.lbs.framework.utils.FileUtils.createDirectory(destinationFile);
			finalZip = destinationFile+".zip";
			
			// Check to see if the directory exists
			File unzipFolder = new File(unzipFolderPath);
			if (unzipFolder.isDirectory()) {
				File finalZipFile=new File(finalZip);
				 baos = new FileOutputStream(finalZipFile);
				ZipOutputStream zout = new ZipOutputStream(
						new BufferedOutputStream(baos));

				// get a list of files from current directory
				File[] files = unzipFolder.listFiles();
				StringBuilder fileNameList =new StringBuilder();
				for (File file : files) {

					LOGGER.debug("Adding File...: " + file);
					LOGGER.debug("createInMemoryZipFile for the file  ["+file.getName() +"]");
					fileNameList.append(file.getName());
					FileInputStream fis = new FileInputStream(file);
					ZipEntry entry = new ZipEntry(file.getName());
					zout.putNextEntry(entry);

					int count;
					while ((count = fis.read(buffer)) != -1) {
						zout.write(buffer, 0, count);
					}

					zout.closeEntry();
					fis.close();
				}
				zout.close();
			}
		} catch (Exception e) {
			finalZip=null;
			LOGGER.error("ManualDownloadServlet::createFinalZipFile::Exception occured:",e);
		}finally{
			if(baos!=null){
				baos.close();
			}
		}




		LOGGER.debug("ZipUtility : createInMemoryZipFile() : END");
		return finalZip;

	}

	public void readFileAndInsertTo2WayQueue(String fileName,
			String baseLocation) {
		LOGGER.trace("AbstractBcunDatasyncServiceImpl : readFileAndInsertTo2WayQueue() : END");
	}

	@Override
	public void createProcessedFile(List<CGBaseDO> baseList, String fileName) throws IOException {
		if(StringUtil.isEmptyColletion(baseList)){
			return;
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createProcessedFile::creating Processed file start.....");
		String processedJsonString = getJSONFromBaseDOList(baseList);
		String fileLocation = getProcessedFileLocation();
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createProcessedFile::Processed fileLocation=> "+fileLocation);
		createFolderIfNotExist(fileLocation);
		File processedFile = new File(fileLocation + File.separator + PROCESSED_FOLDER_NAME.toUpperCase() + "_" + fileName); 
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createProcessedFile::Processed file name=> "+processedFile);
		writeJSONFile(processedJsonString, processedFile);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::createProcessedFile::creating Processed file end.....");	
	}

	@Override
	public void sendEmailNotificationForBlobDownloadError() {
		// send Email Notification for Blob Download Error
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForBlobDownloadError::Start.....");

		try {
			MailSenderTO mailSenderTo = new MailSenderTO();
			mailSenderTo.setMailSubject("Issue with Blob Download at Branch:["
					+ processingBrCode + "]");
			StringBuilder tomailIds = new StringBuilder();
			List<String> officeMailIds = getEmailIdByOfficeCode(processingBrCode);
			List<String> receiverMailId = getBcunReceiverEmailId();
			if (!CGCollectionUtils.isEmpty(officeMailIds)) {
				if (!StringUtil.isStringEmpty(officeMailIds.get(0))) {
					tomailIds.append(officeMailIds.get(0).trim());
				}
			}
			if (!CGCollectionUtils.isEmpty(receiverMailId)) {
				if (!StringUtil.isStringEmpty(receiverMailId.get(0))) {
					if (!StringUtil.isStringEmpty(tomailIds.toString())) {
						tomailIds.append(FrameworkConstants.CHARACTER_COMMA);
					}
					tomailIds.append(receiverMailId.get(0).trim());
				}
			}
			if (StringUtil.isNull(tomailIds)
					|| StringUtil.isStringEmpty(tomailIds.toString())) {
				LOGGER.error("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForBlobDownloadError::To Email Id are empty :hence There is no mail notification"
						+ tomailIds);
				return;
			}
			LOGGER.debug("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForBlobDownloadError::To Email Id"
					+ tomailIds);

			StringBuilder mailbody = new StringBuilder();
			mailbody.append("<html><head></head><body>");
			mailbody.append("Dear IT Department, <BR>");
			mailbody.append(" Issue with Blob Download at Branch :["
					+ processingBrCode + "] <BR> <BR>");
			mailbody.append(" <B> Please Contact System IT Dept.</B> <BR>");
			mailbody.append(" <BR><BR> Regards,<BR> IT-Support Team");
			mailbody.append("</body></html>");
			mailSenderTo.setPlainMailBody(mailbody.toString());
			mailSenderTo.setTo(tomailIds.toString().split(
					FrameworkConstants.CHARACTER_COMMA));
			mailSenderTo.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);

			emailSenderUtil.sendEmail(mailSenderTo);
		} catch (Exception e) {
			LOGGER.error(
					"AbstractBcunDatasyncServiceImpl::sendEmailNotificationForBlobDownloadError::Exception.....",
					e);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::sendEmailNotificationForBlobDownloadError::End.....");
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
	@Override
	public void checkForSpecialErrors(List<CGBaseDO> specailErrorList,Exception e, CGBaseDO baseDO) {
		String messgage=e.getMessage();
		if(!StringUtil.isStringEmpty(messgage) && messgage.contains("not-null property references a null")){
			specailErrorList.add(baseDO);
		}
	}
	
	@Override
	public void createSpecialTempFile(List<CGBaseDO> errorList, String fileName) throws IOException {
		if(!CGCollectionUtils.isEmpty(errorList)){
			try {
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::Queue is going to create temp file....");
				//String fileName =  queueContent.getFileName();//DataSync-Inbound-B991-createBooking-1378201200245.xml
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::... : Creating temp file for [ "+fileName+"]");
				String queueFileName = fileName.replace("DataSync", "DataSyncSPQ");
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::... :  Queue FileName :[ "+queueFileName+"]");

				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::creating temp file start.....");
				String fileLocation = getReprocessFileLocation()+File.separator+"special";
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::temp error fileLocation=> "+fileLocation);
				createFolderIfNotExist(fileLocation);
				File errorFile = new File(fileLocation + File.separator + fileName); 
				String errorJsonString = getJSONFromBaseDOList(errorList);
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::temp error file name=> "+errorFile);
				writeJSONFile(errorJsonString, errorFile);
				LOGGER.debug("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::creating temp file end.....");
			} catch (Exception e) {
				LOGGER.error("AbstractBcunDatasyncServiceImpl::createSpecialTempFile:ERROR",e);
			}
		
		} else {
			LOGGER.info("AbstractBcunDatasyncServiceImpl::createSpecialTempFile::There is no tem error to write in this batch.....");
		}
	}
	@Override
	public String prepareInboundBranchData() throws CGBusinessException,
			CGSystemException {
		/**
		 * Implementation details available in InboundBranchServiceImpl
		 */
		return null;
	}

	@Override
	public boolean updateConsgBillingStatus(String consgNumber)
			throws CGSystemException {
		// TODO Auto-generated method stub
		return datasyncDao.updateConsgBillingStatus(consgNumber);
	}
	@Override
	public boolean updateConsgStatusForDelivery(DeliveryDetailsDO consgDO)
			throws CGSystemException {
		return datasyncDao.updateConsgStatusForDelivery(consgDO);
	}
	
	@Override
	public boolean isFileWrittenCompletely(File writtenFile){
		boolean isValidFile=false;
		String fileName=writtenFile.getName();
		LOGGER.info("AbstractBcunDatasyncServiceImpl:: isFileWrittenCompletely :: START "+fileName);
		String jsonText=null;
		try {
			jsonText = getContentFromFile(writtenFile,  null);
		}  catch (Exception e) {
			LOGGER.error("AbstractBcunDatasyncServiceImpl:: isFileWrittenCompletely:: json Text not constructed properly" ,e);
		}
		if(StringUtil.isStringEmpty(jsonText)){
			LOGGER.error("AbstractBcunDatasyncServiceImpl:: isFileWrittenCompletely:: json Text not constructed properly" );
			isValidFile=false;
			//}else if(isValidJsonString(jsonText)){
		}else if(jsonText.startsWith(BcunConstant.JSON_STRING_STARTS_WITH_CHARACTER)&&jsonText.endsWith(BcunConstant.JSON_STRING_ENDS_WITH_CHARACTER)){
			isValidFile=true;
		}
		LOGGER.info("AbstractBcunDatasyncServiceImpl:: isFileWrittenCompletely :: END"+fileName +" File completed status :"+isValidFile);
		fileName=null;
		jsonText=null;
		return isValidFile;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public void prepareReconcillationStatisticsOnCentral(ReconcillationConfigPropertyTO configPropertyTO)throws CGSystemException,CGBusinessException{
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:: prepareReconcillationStatistics :: START ");	
		List<String> fromTransactionDate=(List<String>)getAnonymusTypeDataByNamedQueryAndNamedParam("getConfigurableValueByParam",new String[]{"paramName"},new Object[]{"RECONCILLATION_FROM_DATE_"+configPropertyTO.getProcess()});
		if(!CGCollectionUtils.isEmpty(fromTransactionDate)){
			configPropertyTO.setTransactionDate(DateUtil.getPreviousDate(StringUtil.parseInteger(fromTransactionDate.get(0))));
		}else{
			throw new CGBusinessException("No data found for process Bcun Reconcillation");
		}
		//getting the records from booking table and updating centralCN count
		List<CGBaseDO> reconcillationStatisticsList=(List<CGBaseDO>) datasyncDao.getReconcillationData(configPropertyTO);
		if(!CGCollectionUtils.isEmpty(reconcillationStatisticsList)){		
			String namedQuery="getReconcillationDetailsByProcess";
			String[] params ={"transoffice","transDate","processName"};
			for(CGBaseDO CGBaseDO:reconcillationStatisticsList){
				try {
					BcunReconcillationDO reconcillationDO=(BcunReconcillationDO)CGBaseDO;
					Object[] values ={reconcillationDO.getTransactionOfficeId(),reconcillationDO.getTransactionDate(),reconcillationDO.getProcessName()};
					// getting the records from reconcilliation table
					List<CGBaseDO> reconcillationList=datasyncDao.getDataByNamedQueryAndNamedParam(namedQuery, params, values);
					if(!CGCollectionUtils.isEmpty(reconcillationList)){
						BcunReconcillationDO  srcReconcillationDO= (BcunReconcillationDO)reconcillationList.get(0);
						srcReconcillationDO.setCentralCNCount(reconcillationDO.getCentralCNCount());
						srcReconcillationDO.setCentralLastProcessedDate(new Date());	
						if(!StringUtil.isNull(srcReconcillationDO.getBranchCNCount()) && srcReconcillationDO.getBranchCNCount().intValue() == reconcillationDO.getCentralCNCount().intValue()){
							srcReconcillationDO.setIsCountMatched("Y");
						}else{
							srcReconcillationDO.setIsCountMatched("N");
						}	
					}else{
						reconcillationList= new ArrayList<>(1);
						reconcillationList.add(CGBaseDO);
					}
					datasyncDao.saveOrUpdateTransferedEntities(reconcillationList);
				} catch (Exception e) {
					LOGGER.error("AbstractBcunDatasyncServiceImpl::prepareReconcillationStatisticsOnCentral:ERROR",e);
				}
			}	
		}	
		
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:: prepareReconcillationStatistics :: End ");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CGBaseDO> getReconcillationDataForBlob(ReconcillationConfigPropertyTO configPropertyTO) throws CGSystemException{
		return (List<CGBaseDO>) datasyncDao.getReconcillationDataForBlobCreation(configPropertyTO);		
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CGBaseDO> getReconcillationData(ReconcillationConfigPropertyTO configPropertyTO) throws CGSystemException{
		return (List<CGBaseDO>) datasyncDao.getReconcillationData(configPropertyTO);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void prepareReconcillationStatisticsForBranchData(BcunReconcillationDO fromBranch)throws CGSystemException{
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:: prepareReconcillationStatisticsForBranchData :: START ");	
		String namedQuery="getReconcillationDetailsByProcess";
		String[] params ={"transoffice","transDate","processName"};
		Object[] values ={fromBranch.getTransactionOfficeId(),fromBranch.getTransactionDate(),fromBranch.getProcessName()};
		List<CGBaseDO> reconcillationList=datasyncDao.getDataByNamedQueryAndNamedParam(namedQuery, params, values);
		ReconcillationConfigPropertyTO prooertyTO=ReconcillationPropertyReader.getReconcillationPropertyTOByProcess(fromBranch.getProcessName());
		List<?>  result =datasyncDao.getAnonymusTypeDataByNamedQueryAndNamedParam(prooertyTO.getNamedQueryForCentralCnCount(), new String[]{"transactionDate","transactionOfficeId"}, new Object[]{fromBranch.getTransactionDate(),fromBranch.getTransactionOfficeId()});
		BigInteger centralCount=null;
		if(!CGCollectionUtils.isEmpty(result)){
			centralCount= (BigInteger)result.get(0);
		}
		boolean updateFlag=false;
		if(!CGCollectionUtils.isEmpty(reconcillationList)){
			BcunReconcillationDO fromCentral=(BcunReconcillationDO)reconcillationList.get(0);
			fromCentral.setCentralCNCount(centralCount);
			updateFlag=compareBranchLastRequestDateWithCurrentDate(
					fromBranch,fromCentral);
		}else{
			updateFlag=true;
			List<BcunReconcillationDO> branchList=new ArrayList<>(1);
			fromBranch.setCentralCNCount(centralCount);
			Date processedDate=DateUtil.getCurrentDate();
			if(centralCount!=null && centralCount.intValue() == fromBranch.getBranchCNCount().intValue()){
				fromBranch.setIsCountMatched(FrameworkConstants.ENUM_YES);
				
			}else{
				fromBranch.setIsCountMatched(FrameworkConstants.ENUM_NO);
			}
			fromBranch.setBranchLastRequestInDate(processedDate);
			fromBranch.setCentralLastProcessedDate(processedDate);
			branchList.add(fromBranch);
			reconcillationList =(List)branchList;
		}
		if(updateFlag){
		datasyncDao.saveOrUpdateTransferedEntities(reconcillationList);
		}
		LOGGER.debug("AbstractBcunDatasyncServiceImpl:: prepareReconcillationStatisticsForBranchData :: End ");
	}
	private boolean compareBranchLastRequestDateWithCurrentDate(
			BcunReconcillationDO fromBranch,BcunReconcillationDO fromCentral) {
		boolean isValidRecord=false;

		if(fromCentral.getBranchLastRequestInDate()!=null&& fromBranch.getCentralLastProcessedDate().after(fromCentral.getBranchLastRequestInDate())){
			fromCentral.setBranchCNCount(fromBranch.getBranchCNCount());
			fromCentral.setBranchLastRequestInDate(new Date());
			isValidRecord=true;

		} 
		if(isValidRecord){//to make sure not updating with older file(if newer file has already been processed)
			if(fromCentral.getCentralCNCount()!=null && fromBranch.getBranchCNCount()!=null && fromCentral.getCentralCNCount().intValue() ==fromBranch.getBranchCNCount().intValue() && fromCentral.getCentralCNCount().intValue() !=0){
				fromCentral.setIsCountMatched(FrameworkConstants.ENUM_YES);
			}else{
				fromCentral.setIsCountMatched(FrameworkConstants.ENUM_NO);
			}
		}
		return isValidRecord;
	}
	@Override
	public void updateBcunDataTransferFlag(List<CGBcunInbundDO> cgBcunInboundList,
			String qryName){
		datasyncDao.updateBcunDataTransferFlag(cgBcunInboundList, qryName);
	}
	
}
