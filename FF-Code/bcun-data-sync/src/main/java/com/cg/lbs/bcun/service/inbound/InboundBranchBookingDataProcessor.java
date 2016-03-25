package com.cg.lbs.bcun.service.inbound;

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
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.SimpleFTPClient;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.InboundConfigPropertyTO;

/**
 * @author mohammal
 * Jan 15, 2013
 * Used to process the in bound data from the branch office.
 */
public class InboundBranchBookingDataProcessor {

	/**
	 *  Log the message of the process.
	 */
	private Logger logger = LoggerFactory.getLogger(InboundBranchBookingDataProcessor.class);
	
	/**
	 * BCUN service which will process the opration.
	 */
	private BcunDatasyncService bcunService;

	/**
	 * FTP client used transfer BCUN xml file from branch office to
	 * central office
	 */
	private SimpleFTPClient ftpCLient;
	
	/**
	 * Contains all the FTP server settings
	 */
	private Properties ftpProp;
	
	private String processingBrCode;
	private String filePrefix; 
	/**
	 * Starting points to setup the process
	 */
	public void proceedDatasync() throws HttpException, ClassNotFoundException, IOException {
		logger.info("InboundBranchBookingDataProcessor::proceedDatasync::start");
		//Starting file creation for all the configured process
		try {
			logger.debug("InboundBranchBookingDataProcessor::proceedDatasync::starting file creation....");
			createProcessFiles();
			logger.debug("InboundBranchBookingDataProcessor::proceedDatasync::file creation completed....");
		} catch (Exception ex) {
			logger.error("InboundBranchBookingDataProcessor::proceedDatasync::error while creating files::" ,ex);
		}
		
		logger.info("InboundBranchBookingDataProcessor::processDatasync::end");
	}
	
	
	/**
	 * Create the file contains process data as a JSON string.
	 */
	
	@SuppressWarnings("unchecked")
	private void createProcessFiles() {
		//Reading all the process configuration
		List<InboundConfigPropertyTO> configProps = (List<InboundConfigPropertyTO>)bcunService.getBcunConfigProps(BcunConstant.INBOUND_PROCESS_NAME_BOOKING);
		logger.trace("InboundBranchBookingDataProcessor::createProcessFiles::configProps::" + configProps);
		if(configProps != null && !configProps.isEmpty()) {
			//Processing for all the processes one by one
			for (InboundConfigPropertyTO configTo : configProps) {
				try {
					logger.trace("InboundBranchBookingDataProcessor::createProcessFiles::process name::" + configTo.getProcess());
					//starting individual process processing
					boolean isWritenToFile = proceedIndividualProcess(configTo);
					//individual process processing ended
					logger.debug("InboundBranchBookingDataProcessor::createProcessFiles::process name::" + isWritenToFile);
				} catch (Exception ex) {
						logger.error("InboundBranchBookingDataProcessor::createProcessFiles::error::" ,ex);
					}
			}
		} else {
			logger.info("InboundBranchBookingDataProcessor::processDatasync::inbound config properties are null or empty...");
		}
	}
	
	/**
	 * Takes individual process configured properties and process it.
	 * @param configTo is configured properties
	 * @return true if success else false
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private boolean proceedIndividualProcess(InboundConfigPropertyTO configTo) throws IOException {
		logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::start");
		//Flag will be used to represent file creation status
		boolean isWritenToFile = false;
		RandomAccessFile raF =null;
		FileChannel channel=null;
		FileLock lock=null;
		File xmlFIle =null;
		FileWriter writer=null;
		
		//Getting configured named query 
		String namedQuery = configTo.getNamedQuery();
		logger.trace("InboundBranchBookingDataProcessor::proceedIndividualProcess::named query::" + namedQuery);
		if(namedQuery != null && !namedQuery.isEmpty()) {
			//Getting process data based on configured query..
			List<CGBaseDO> unSyncedData = (List<CGBaseDO>)bcunService.getDataByNamedQueryAndRowCount(namedQuery, configTo.getMaxRowCount());
			//Processing fetched data
			if(unSyncedData != null && !unSyncedData.isEmpty()) {
				logger.trace("InboundBranchBookingDataProcessor::proceedIndividualProcess::b4 getting json from baseDo list===>");
				try {
					logger.debug("InboundBranchBookingDataProcessor::proceedIndividualProcess::");
					//Getting JSON string of fetched result
					String jsonObject = bcunService.getJSONFromBaseDOList(unSyncedData);
					logger.trace("InboundBranchBookingDataProcessor::proceedIndividualProcess::jsonObject===>" + jsonObject);
					
					
					//Reading configured file location
					//Creating JSON xml file at configured file location
					
					String fileName = getFileName(configTo.getPropKey());
					 xmlFIle = new File(fileName);
					raF =new RandomAccessFile(xmlFIle,"rw");
					 channel  =raF.getChannel(); 
						if( channel !=null && channel.isOpen()){ 
							lock = channel.lock(); 
						}
						
						if(lock==null){
							logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::lock not obtained ===>" + xmlFIle.getName());	
						}else{
							logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::writting to file with name===>" + xmlFIle.getName());
							//Writing JSOn string to the created file
							//isWritenToFile = bcunService.writeJSONFile(jsonObject, raF);
							//Writing JSOn string to the created file
							//isWritenToFile = bcunService.writeJSONFile(jsonObject, raF);
							writer = new FileWriter(raF.getFD());
							//Writing JSON string on file
							writer.write(jsonObject);
							//flushing  
							writer.flush();
							isWritenToFile =true;
							logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::file created::" + isWritenToFile);
						}
					
				}catch(ClosedChannelException closed){
					logger.error("InboundBranchBookingDataProcessor::proceedIndividualProcess::.... ClosedChannelException",closed);
				}catch(OverlappingFileLockException fileLock){
					logger.error("InboundBranchBookingDataProcessor::proceedIndividualProcess::file created.... OverlappingFileLockException",fileLock);
				}catch(NonWritableChannelException  nce){
					logger.error("InboundBranchBookingDataProcessor::proceedIndividualProcess::file created.... NonWritableChannelException",nce);
				}catch(NonReadableChannelException  nrce){
					logger.error("InboundBranchBookingDataProcessor::proceedIndividualProcess::file created.... NonReadableChannelException",nrce);
				} catch (Exception e) {
					logger.error("InboundBranchBookingDataProcessor::proceedIndividualProcess::error::" , e);
					throw e;
				}
				finally{
					if( lock !=null && lock.isValid()){
						logger.debug("readBookingXMLFile() : releasing the lock ... fot the file name ["+(xmlFIle!=null ?xmlFIle.getName():"")+"]");
			    		lock.release();
			    	}
		    		if(channel !=null && channel.isOpen()){
		    			logger.debug("readBookingXMLFile() : closing File Channel ");
			    		channel.close();
			    		
			    	}
		    		if(writer!=null){
						writer.close();
						logger.debug("readBookingXMLFile() : closing File writer ");
					}
					if(raF != null ){
						logger.debug("readBookingXMLFile() : closing Random Access File ");
						raF.close();
					}
					if(!isWritenToFile && xmlFIle!=null && xmlFIle.exists()){
						StringBuilder errorLogger= new StringBuilder("deleting "+xmlFIle.getName()+" : the file since it's an interruption while writing the file  and delete status ["+xmlFIle.delete()+"]");
						logger.error(errorLogger.toString());
					}
				}
				boolean isFileCompleted=bcunService.isFileWrittenCompletely(xmlFIle);
				if(isWritenToFile && xmlFIle!=null && xmlFIle.length()>0 && isFileCompleted){
					//Updating branch database for transfered data
					logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess:: DB updated about to start for the file :["+xmlFIle.getName()+"]");
					bcunService.updateFlagAndUpdateEntitiesForBranchToCentralTransfer(unSyncedData);
				}else{
					logger.error("InboundBranchBookingDataProcessor::Data Read From DB But not writtern to File hence flag is not updating in DB");
				}
			} else {
				logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::there is no data for transfer::");
			}
		} else {
			logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::named query is empty or null");
		}
		logger.info("InboundBranchBookingDataProcessor::proceedIndividualProcess::is written to file===>" + isWritenToFile+" ### END");
		return isWritenToFile;
	}

	private String getFileName(String propKey) {
		String fileNamePrefix = getFileNamePrefix();
		String fileName = fileNamePrefix + propKey + "-" + System.currentTimeMillis() + ".xml";
		logger.info("InboundBranchBookingDataProcessor::getFileName::" + fileName);
		return fileName;
	}
	
	
	private String getFileNamePrefix() {
		String fileLocation = null;
		if(filePrefix == null)
		{
			fileLocation = bcunService.getBaseFileLocation();
			filePrefix = fileLocation + File.separator + "DataSync-Inbound-" + processingBrCode + "-";
		}
		logger.trace("InboundBranchBookingDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
		return filePrefix;
	}
	
	
	
	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
	/**
	 * Spring's setter injection
	 * @param ftpCLient
	 */
	public void setFtpCLient(SimpleFTPClient ftpCLient) {
		this.ftpCLient = ftpCLient;
	}

	/**
	 * Spring's setter injection
	 * @param ftpProp
	 */
	public void setFtpProp(Properties ftpProp) {
		this.ftpProp = ftpProp;
	}


	public void setProcessingBrCode(String processingBrCode) {
		this.processingBrCode = processingBrCode;
	}
	
}
