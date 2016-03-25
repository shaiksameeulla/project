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
public class InboundBranchDataProcessor {

	/**
	 *  Log the message of the process.
	 */
	private Logger logger = LoggerFactory.getLogger(InboundBranchDataProcessor.class);
	
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
	public void proceedDatasync() throws HttpException, ClassNotFoundException, IOException
	{
		logger.info("InboundBranchDataProcessor::proceedDatasync::start");
		//Starting file creation for all the configured process
		try {
			logger.debug("InboundBranchDataProcessor::proceedDatasync::starting file creation....");
			createProcessFiles();
			logger.debug("InboundBranchDataProcessor::proceedDatasync::file creation completed....");
		} catch (Exception ex) {
			logger.error("InboundBranchDataProcessor::proceedDatasync::error while creating files::" ,ex);
		}
		logger.info("InboundBranchDataProcessor::processDatasync::end");
	}
	
	
	/**
	 * Create the file contains process data as a JSON string.
	 */
	
	@SuppressWarnings("unchecked")
	private void createProcessFiles() {
		//Reading all the process configuration
		List<InboundConfigPropertyTO> configProps = (List<InboundConfigPropertyTO>)bcunService.getBcunConfigProps(BcunConstant.INBOUND_PROCESS_NAME_OTHERS);
		logger.trace("InboundBranchDataProcessor::createProcessFiles::configProps::" + configProps);
		if(configProps != null && !configProps.isEmpty()) {
			//Processing for all the processes one by one
			for (InboundConfigPropertyTO configTo : configProps) {
					try {
						logger.trace("InboundBranchDataProcessor::createProcessFiles::process name::" + configTo.getProcess());
						//starting individual process processing
						boolean isWritenToFile = proceedIndividualProcess(configTo);
						//individual process processing ended
						logger.debug("InboundBranchDataProcessor::createProcessFiles::process name::" + isWritenToFile);
					} catch (Exception ex) {
						logger.error("InboundBranchDataProcessor::createProcessFiles::error::",ex);
					}
			}
		} else {
			logger.info("InboundBranchDataProcessor::processDatasync::inbound config properties are null or empty...");
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
		
		//Flag will be used to represent file creation status
		boolean isWritenToFile = false;
		RandomAccessFile raF =null;
		FileChannel channel=null;
		FileLock lock=null;
		File xmlFIle =null;
		FileWriter writer=null;
		//Getting configured named query 
		String namedQuery = configTo.getNamedQuery();
		String processKey = configTo.getPropKey();
		logger.info("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::named query::[" + namedQuery+"] ## START");
		if(namedQuery != null && !namedQuery.isEmpty()) {
			//Getting process data based on configured query..
			List<CGBaseDO> unSyncedData = (List<CGBaseDO>)bcunService.getDataByNamedQueryAndRowCount(namedQuery, configTo.getMaxRowCount());
			//Processing fetched data
			if(unSyncedData != null && !unSyncedData.isEmpty()) {
				//preProcessDeliveryDO(configTo, unSyncedData);
				logger.trace("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::b4 getting json from baseDo list===>");
				try {
					logger.debug("InboundBranchDataProcessor::proceedIndividualProcess::");
					//Getting JSON string of fetched result
					String jsonObject = bcunService.getJSONFromBaseDOList(unSyncedData);
					logger.trace("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::jsonObject===>" + jsonObject);
					
					
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
							logger.trace("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::lock not obtained ===>" + xmlFIle.getName());	
						}else{
							logger.trace("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::writting to file with name===>" + xmlFIle.getName());
							//Writing JSOn string to the created file
							//isWritenToFile = bcunService.writeJSONFile(jsonObject, raF);
							writer = new FileWriter(raF.getFD());
							//Writing JSON string on file
							writer.write(jsonObject);
							//flushing  
							writer.flush();
							
							isWritenToFile =true;
							logger.info("InboundBranchDataProcessor::proceedIndividualProcess::file created::" + isWritenToFile);
						}
					
				}catch(ClosedChannelException closed){
					logger.error("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::.... ClosedChannelException",closed);
				}catch(OverlappingFileLockException fileLock){
					logger.error("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::file created.... OverlappingFileLockException",fileLock);
				}catch(NonWritableChannelException  nce){
					logger.error("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::file created.... NonWritableChannelException",nce);
				}catch(NonReadableChannelException  nrce){
					logger.error("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::file created.... NonReadableChannelException",nrce);
				} catch (Exception e) {
					logger.error("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::error::" ,e);
					throw e;
				}finally{
					if( lock !=null && lock.isValid()){
			    		lock.release();
			    		logger.debug("read"+processKey+"XMLFile() : releasing the lock ... for the file name ["+(xmlFIle!=null ?xmlFIle.getName():"")+"]");
			    	}
		    		if(channel !=null && channel.isOpen()){
			    		channel.close();
			    		logger.debug("read"+processKey+"XMLFile() : closing Channel ");
			    		
			    	}
					
					if(writer!=null){
						writer.close();
						logger.debug("read"+processKey+"XMLFile() : closing File writer ");
					}
					if(raF != null ){
						raF.close();
						logger.debug("read"+processKey+"XMLFile() : closing Random acccess File");
					}
					if(!isWritenToFile && xmlFIle!=null && xmlFIle.exists()){
						StringBuilder errorLogger= new StringBuilder("deleting "+xmlFIle.getName()+" : the file since it's an interruption while writing the file  and delete status ["+xmlFIle.delete()+"]");
						logger.error(errorLogger.toString());
					}
				}
				boolean isFileCompleted=bcunService.isFileWrittenCompletely(xmlFIle);
				if(isWritenToFile && xmlFIle!=null && xmlFIle.length()>0 && isFileCompleted){
					//Updating branch database for transfered data
					logger.info("InboundBranchDataProcessor::proceedIndividualProcess:: DB updated about to start for the file :["+xmlFIle.getName()+"]");
					bcunService.updateFlagAndUpdateEntitiesForBranchToCentralTransfer(unSyncedData);
				}else{
					logger.error("InboundBranchDataProcessor::Data Read From DB But not writtern to File for the process("+processKey+") hence flag is not updating in DB");
				}
				
			} else {
				logger.info("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::there is no data for transfer for named query::" + namedQuery);
			}
		} else {
			logger.warn("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::named query is empty or null");
		}
		logger.info("InboundBranchDataProcessor::proceedIndividualProcess("+processKey+")::is written to file===>" + isWritenToFile +"### END");
		return isWritenToFile;
	}

	private String getFileName(String propKey) {
		String fileNamePrefix = getFileNamePrefix();
		String fileName = fileNamePrefix + propKey + "-" + System.currentTimeMillis() + ".xml";
		logger.info("InboundBranchDataProcessor::getFileName::" + fileName);
		return fileName;
	}
	
	
	private String getFileNamePrefix() {
		String fileLocation = null;
		if(filePrefix == null)
		{
			fileLocation = bcunService.getBaseFileLocation();
			filePrefix = fileLocation + File.separator + "DataSync-Inbound-" + processingBrCode + "-";
		}
		logger.trace("InboundBranchDataProcessor::proceedIndividualProcess::fileLocation===>" + fileLocation);
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
