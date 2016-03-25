/*
 * 
 */
package com.cg.lbs.bcun.service.inbound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.utility.ReconcillationPropertyReader;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;
/**
 * @author bmodala
 * Aug 19, 2015
 * Used to process the in bound data from the branch office.
 */
public class InboundBranchReconciliationDataProcessor {

	/**
	 *  Log the message of the process.
	 */
	private Logger logger = LoggerFactory.getLogger(InboundBranchReconciliationDataProcessor.class);

	/**
	 * BCUN service which will process the opration.
	 */
	private BcunDatasyncService bcunService;

	

	

	private String processingBrCode;
	
	private Map<String,Date> processWiseDate=null;
	/**
	 * Starting points to setup the process
	 */
	public void proceedDatasync() throws HttpException, ClassNotFoundException, IOException {
		logger.debug("InboundBranchReconciliationDataProcessor::proceedDatasync::start");
		//Starting file creation for all the configured process
		try {
			logger.debug("InboundBranchReconciliationDataProcessor::proceedDatasync::starting file creation....");
			createProcessFiles();
			logger.debug("InboundBranchReconciliationDataProcessor::proceedDatasync::file creation completed....");
		} catch (Exception ex) {
			logger.error("InboundBranchReconciliationDataProcessor::proceedDatasync::error while creating files::" ,ex);
		}

		logger.debug("InboundBranchReconciliationDataProcessor::processDatasync::end");
	}


	/**
	 * Create the file contains process data as a JSON string.
	 */

	@SuppressWarnings("unchecked")
	private void createProcessFiles() {
		//Reading all the process configuration	
		List<ReconcillationConfigPropertyTO> configProps = ReconcillationPropertyReader.getReconcillationConfigProperty();
		logger.trace("InboundBranchReconciliationDataProcessor::createProcessFiles::configProps::" + configProps);
		if(configProps != null && !configProps.isEmpty()) {
			if(CGCollectionUtils.isEmpty(processWiseDate)){
			processWiseDate= new HashMap<>(configProps.size());
			}
			//Processing for all the processes one by one
			for (ReconcillationConfigPropertyTO configTo : configProps) {
				try {
					Date transactionDate = getProcessWiseDate(configTo);
					configTo.setTransactionDate(transactionDate);	
					logger.trace("InboundBranchReconciliationDataProcessor::createProcessFiles::process name::" + configTo.getProcess());
					//starting individual process processing
					boolean isWritenToFile = proceedIndividualProcess(configTo);
					//individual process processing ended
					logger.debug("InboundBranchReconciliationDataProcessor::createProcessFiles::process name::" + isWritenToFile);
				} catch (Exception ex) {
					logger.error("InboundBranchReconciliationDataProcessor::createProcessFiles::error::" ,ex);
				}
			}
		} else {
			logger.warn("InboundBranchReconciliationDataProcessor::processDatasync::inbound config properties are null or empty...");
		}
	}


	private Date getProcessWiseDate(ReconcillationConfigPropertyTO configTo) throws CGBusinessException {
		Date transactionDate=null;  
		if(processWiseDate.containsKey(configTo.getProcess())){
			transactionDate=processWiseDate.get(configTo.getProcess());
		}else{
			@SuppressWarnings("unchecked")
			List<String> fromTransactionDate=(List<String>)bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getConfigurableValueByParam",new String[]{"paramName"},new Object[]{"RECONCILLATION_FROM_DATE_"+configTo.getProcess()});
			if(!CGCollectionUtils.isEmpty(fromTransactionDate)){
				transactionDate=DateUtil.getPreviousDate(StringUtil.parseInteger(fromTransactionDate.get(0)));
				processWiseDate.put(configTo.getProcess(), transactionDate);
			}else{
				throw new CGBusinessException("No data found for process Bcun Reconcillation");
			}
		}
		return transactionDate;
	}

	/**
	 * Takes individual process configured properties and process it.
	 * @param configTo is configured properties
	 * @return true if success else false
	 * @throws IOException 
	 * @throws CGSystemException 
	 */
	@SuppressWarnings("unchecked")
	private boolean proceedIndividualProcess(ReconcillationConfigPropertyTO configTo) throws IOException, CGSystemException {
		logger.info("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::start");
		//Flag will be used to represent file creation status
		boolean isWritenToFile = false;
		RandomAccessFile raF =null;
		FileChannel channel=null;
		FileLock lock=null;
		File xmlFIle =null;
		FileWriter writer=null;
		//Getting configured named query 
		String namedQuery = configTo.getNamedQueryAtbranch();
		String fileName = null;
		logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::named query::" + namedQuery);
		if(namedQuery != null && !namedQuery.isEmpty()) {
			//Getting process data based on configured query.
			configTo.setNamedQuery(namedQuery);
			List<CGBaseDO> unSyncedData=(List<CGBaseDO>) bcunService.getReconcillationData(configTo);
			//Processing fetched data
			if(unSyncedData != null && !unSyncedData.isEmpty()) {
				//preProcessDeliveryDO(configTo, unSyncedData);
				logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::b4 getting json from baseDo list===>");
				try {
					logger.debug("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::");
					//Getting JSON string of fetched result
					String jsonObject = bcunService.getJSONFromBaseDOList(unSyncedData);
					logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
					//Reading configured file location
					//Creating JSON xml file at configured file location
					fileName = getFileName(configTo.getPropKey());
					xmlFIle = new File(fileName);
					raF =new RandomAccessFile(xmlFIle,"rw");
					channel  =raF.getChannel(); 
					if( channel !=null && channel.isOpen()){ 
						lock = channel.lock(); 
					}

					if(lock==null){
						logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::lock not obtained ===>" + fileName);	
					}else{
						logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::writting to file with name===>" + fileName);
						//Writing JSOn string to the created file
						//isWritenToFile = bcunService.writeJSONFile(jsonObject, raF);
						writer = new FileWriter(raF.getFD());
						//Writing JSON string on file
						writer.write(jsonObject);
						//flushing  
						writer.flush();
						
						isWritenToFile =true;
						logger.info("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::file created::" + isWritenToFile);
					}

				}catch(ClosedChannelException closed){
					logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::.... ClosedChannelException",closed);
				}catch(OverlappingFileLockException fileLock){
					logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::file created.... OverlappingFileLockException",fileLock);
				}catch(NonWritableChannelException  nce){
					logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::file created.... NonWritableChannelException",nce);
				}catch(NonReadableChannelException  nrce){
					logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::file created.... NonReadableChannelException",nrce);
				} catch (Exception e) {
					logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::error::" ,e);
					throw e;
				}finally{
					if( lock !=null && lock.isValid()){
						lock.release();
						logger.debug("readReconciliationXMLFile() : releasing the lock ... for the file name ["+fileName+"]");
					}
					if(channel !=null && channel.isOpen()){
						channel.close();
						logger.debug("readReconciliationXMLFile() : closing Channel ");	
					}
					if(writer!=null){
						writer.close();
						logger.debug("readReconciliationXMLFile() : closing File writer ");
					}
					if(raF != null ){
						raF.close();
						logger.debug("readReconciliationXMLFile() : closing Random acccess File");
					}
					if(!isWritenToFile && xmlFIle!=null && xmlFIle.exists()){
						StringBuilder errorLogger= new StringBuilder("deleting "+xmlFIle.getName()+" : the file since it's an interruption while writing the file  and delete status ["+xmlFIle.delete()+"]");
						logger.error(errorLogger.toString());
					}
				}
				boolean isFileCompleted=bcunService.isFileWrittenCompletely(xmlFIle);
				if(isWritenToFile && xmlFIle!=null && xmlFIle.length()>0 && isFileCompleted){
					//Updating branch database for transfered data
					if(!CGCollectionUtils.isEmpty(unSyncedData)){		
						String namedQueryString="getReconcillationDetailsByProcess";  
						String[] params ={"transoffice","transDate","processName"};
						Date branchDate= DateUtil.getCurrentDate();
						for(CGBaseDO CGBaseDO:unSyncedData){
							List<CGBaseDO> reconcillationList;
							try {
								BcunReconcillationDO reconcillationDO=(BcunReconcillationDO)CGBaseDO;
								reconcillationDO.setBranchLastRequestInDate(branchDate);
								Object[] values ={reconcillationDO.getTransactionOfficeId(),reconcillationDO.getTransactionDate(),reconcillationDO.getProcessName()};
								reconcillationList = (List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam(namedQueryString, params, values);
								if(!CGCollectionUtils.isEmpty(reconcillationList)){
									BcunReconcillationDO  srcReconcillationDO= (BcunReconcillationDO)reconcillationList.get(0);
									srcReconcillationDO.setBranchCNCount(reconcillationDO.getBranchCNCount());
									srcReconcillationDO.setBranchLastRequestInDate(branchDate);
									reconcillationDO=srcReconcillationDO;
								}else{	 
									reconcillationList= new ArrayList<>(1);
									reconcillationDO.setCentralCNCount(BigInteger.ZERO);
									reconcillationList.add(CGBaseDO);
								}
								bcunService.saveOrUpdateTransferedEntities(reconcillationList);
							} catch (Exception e) {
								logger.error("InboundBranchReconciliationDataProcessor::Branch statistics for Reconciliation :: EXCEPTION",e);
							}
							
						}
					}
					
					logger.info("InboundBranchReconciliationDataProcessor::proceedIndividualProcess:: DB updated about to start for the file :["+xmlFIle.getName()+"]");
				}else{
					logger.error("InboundBranchReconciliationDataProcessor::Data Read From DB But not writtern to File hence flag is not updating in DB");
				}

			} else {
				logger.info("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::there is no data for transfer::");
			}
		} else {
			logger.error("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::named query is empty or null");
		}
		logger.info("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::is written to file===>" + isWritenToFile+"END");
		return isWritenToFile;
	}

	private String getFileName(String propKey) {
		String fileNamePrefix = getFileNamePrefix();
		String fileName = fileNamePrefix + propKey + "-" + System.currentTimeMillis() + ".xml";
		logger.info("InboundBranchReconciliationDataProcessor::getFileName::" + fileName);
		return fileName;
	}

	private String getFileNamePrefix() {
		String fileLocation = bcunService.getBaseFileLocation() + File.separator + "DataSync-bcunInbound-" + processingBrCode + "-";
		logger.trace("InboundBranchReconciliationDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
		return fileLocation;
	}
	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	
	public void setProcessingBrCode(String processingBrCode) {
		this.processingBrCode = processingBrCode;
	}
}
