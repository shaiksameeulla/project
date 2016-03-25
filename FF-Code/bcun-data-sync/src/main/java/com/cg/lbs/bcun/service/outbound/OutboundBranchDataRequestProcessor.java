package com.cg.lbs.bcun.service.outbound;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.BcunCentralAuthenticationUtil;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.utils.ZipUtility;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;

/**
 * @author mohammal
 * Feb 12, 2013
 * Process out bound branch data
 */
@Deprecated
public class OutboundBranchDataRequestProcessor {

	
	/**
	 * Helper class to create http request
	 */
	private DataRequestUtil dataRequestUtil;
	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchDataRequestProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		String uniqueRequestId="107"+StringUtil.getRandomNumber();
		//Getting configured branch request URL
		String branchRequestUrl = dataRequestUtil.branchRequestUrl()+"&uniqueRequestId=" + uniqueRequestId;
		LOGGER.debug("OutboundBranchDataRequestProcessor::processBranchdata::start=====>Request ID:["+uniqueRequestId+"] branchRequestUrl :["+branchRequestUrl+"]");
		//Getting request parameters in TO
		OutboundBranchDataTO requestTO = dataRequestUtil.prepareHttpRequestTO();
		if(requestTO!=null){
			requestTO.setUniqueRequestId(uniqueRequestId);
		}
		if(BcunCentralAuthenticationUtil.getBcunCentralAuthenticationStatus()){
			LOGGER.debug("####### Authentication success :: hence data can be retrieved from Central server" );
			//Posting request to server
			byte[] serverResponse = dataRequestUtil.postRequestToServer(branchRequestUrl, requestTO);
			if(serverResponse != null && serverResponse.length>0) {
				LOGGER.debug("OutboundBranchDataRequestProcessor::processBranchdata::Packet Received for the request=====>Request ID:["+requestTO!=null?requestTO.getUniqueRequestId():null+"]");
				//COnverting response byte array into string 
				//Converting JSON string into TO 
				

				try {
					requestTO.setFileData(serverResponse);
					processResponseData(requestTO);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.error("OutboundBranchDataRequestProcessor::BolbExtraction::Exception=====>for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]",e);
					setFlagForFailedTransfers(requestTO);
				}
				LOGGER.debug("####### OutboundBranchDataRequestProcessor::Starts two way handshaking::#########START for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]" );
				//set the flag to T in the central server.
				setDataTransferFlagToTransferred(requestTO);
				LOGGER.debug("####### OutboundBranchDataRequestProcessor::Starts two way handshaking::#########END for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]" );
			} else if(requestTO.getHttpStatusCode()!=HttpStatus.SC_OK) {
				LOGGER.warn("####### OutboundBranchDataRequestProcessor::processBranchdata::central server is not responding #########");
				//set the flag to central for corrupt data.
				LOGGER.warn("####### OutboundBranchDataRequestProcessor::Starts two way handshaking::For status code[ "+requestTO.getHttpStatusCode()+"]::#########START for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]");
				setFlagForFailedTransfers(requestTO);
				LOGGER.warn("####### OutboundBranchDataRequestProcessor::Starts two way handshaking::For status code[ "+requestTO.getHttpStatusCode()+"]::#########END for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]" );
			}
		}else{
			LOGGER.error("####### Authentication failed :: hence data cannot be retrieved from Central server" );
		}
		
		LOGGER.debug("OutboundBranchDataRequestProcessor::processBranchdata::end=====>Request ID:["+uniqueRequestId+"]");
	}

	

	/**
	 * @param requestTO
	 * @throws HttpException
	 * @throws IOException
	 */
	private void setFlagForFailedTransfers(OutboundBranchDataTO requestTO)
			throws HttpException, IOException {
		LOGGER.debug("####### OutboundBranchDataRequestProcessor::setFlagForFailedTransfers :: START::for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]");
		if(!CGCollectionUtils.isEmpty(requestTO.getPacketIds())){
			String statusUpdateUrl = dataRequestUtil.statusUpdateUrl();
			requestTO.setUpdateFlagStatus(BcunConstant.DATA_TRANSFER_STATUS_NEW);
			dataRequestUtil.postRequestToServer(statusUpdateUrl, requestTO);
		}else{
			LOGGER.error("####### OutboundBranchDataRequestProcessor::Starts two way handshaking::setFlagForFailedTransfers : Packet id is empty" );
		}
		LOGGER.debug("####### OutboundBranchDataRequestProcessor::setFlagForFailedTransfers :: START::for the RequestId ["+requestTO!=null?requestTO.getUniqueRequestId():null+"]");
	}

	/**
	 * @param requestTO
	 * @throws HttpException
	 * @throws IOException
	 */
	private void setDataTransferFlagToTransferred(OutboundBranchDataTO requestTO)
			throws HttpException, IOException {
		String statusUpdateUrl = dataRequestUtil.statusUpdateUrl()+"&uniqueRequestId=" + requestTO.getUniqueRequestId();
		if(requestTO!=null && !CGCollectionUtils.isEmpty(requestTO.getPacketIds())){
			requestTO.setFileData(null);//making sure response will not flow back to central server
			requestTO.setZipFIles(null);//making sure response will not flow back to central server
			LOGGER.debug("####### OutboundBranchDataRequestProcessor::about to initiate URL::setDataTransferFlagToTransferred ::"+statusUpdateUrl +"With PacketOfficeId ["+requestTO.getDataExtctrIdStr()+"]for the RequestId ["+requestTO.getUniqueRequestId()+"]");
			requestTO.setUpdateFlagStatus(BcunConstant.DATA_TRANSFER_STATUS_TRANSFERRED);
			dataRequestUtil.postRequestToServer(statusUpdateUrl, requestTO);
		}
	}
	
	/**
	 * Process central server response
	 * @param inputTO is server response
	 * @throws IOException 
	 */
	private void processResponseData(OutboundBranchDataTO inputTO) throws IOException {
		if(inputTO == null)
			return;
		//Preparing file from server response
		prepareFile(inputTO);
		//inserting data received from central server to branch database
		//insertFileDataIntoDatabase();
	}
	
	/**
	 * Used to prepare file from central server's response
	 * @param inputTO containing response data
	 * @throws IOException 
	 */
	private void prepareFile(OutboundBranchDataTO inputTO) throws IOException {
		//Getting file location
		LOGGER.debug("OutboundBranchDataRequestProcessor::prepareFile::START For  the RequestId ["+inputTO!=null?inputTO.getUniqueRequestId():null+"]");
		String centralFileLocation = bcunService.getBaseFileLocation();
		String uniqueId=inputTO.getUniqueRequestId();
		//Getting zip file contents from TO
		byte[] zipFiles =  inputTO.getFileData();
		String preprocessFolder=centralFileLocation+File.separator+inputTO.getUniqueRequestId();
		File preprocessDir= new File(preprocessFolder);
		try {
			preprocessDir.mkdirs();
		} catch (Exception e1) {
			LOGGER.error("OutboundBranchDataRequestProcessor::prepareFile::ERROR (folder creation)["+uniqueId+"]",e1);
			throw e1;
		}
		//Iterating through zip content
		if(zipFiles != null && zipFiles.length >0) {
			try {
				LOGGER.debug("OutboundBranchDataRequestProcessor::prepareFile:: data downloaded ["+uniqueId+"]");
				//Creating ZIP file location with name
				String fileName = preprocessFolder + File.separator + System.currentTimeMillis() + ".zip" ;
				//Creating ZIP file
				ZipUtility.createLocalZipFile(fileName , zipFiles);
				//extracting ZIP file for XML file
				ZipUtility.unzip(fileName, preprocessFolder);
				//Deleting ZIP file
				boolean isDeleted = bcunService.deleteFile(fileName);
				ZipUtility.unzipFromFolder(preprocessFolder, centralFileLocation);
			
				LOGGER.debug("OutboundBranchDataRequestProcessor::prepareFile::deleting zip file after extracting:: " + isDeleted+"for the RequestId ["+uniqueId+"]");
			} catch (Exception e) {
				LOGGER.error("OutboundBranchDataRequestProcessor::prepareFile::ERROR (zip processing)["+uniqueId+"]",e);
				throw e;
			}finally{
				try {
					FileUtils.deleteDirectory(preprocessDir);
				}catch(Exception e){
					LOGGER.error("OutboundBranchDataRequestProcessor::prepareFile::ERROR (folder deletion)["+uniqueId+"]",e);
				}
			}
		}
		LOGGER.debug("OutboundBranchDataRequestProcessor::prepareFile::END For  the RequestId ["+uniqueId+"]");
	}
	
	
	
	/**
	 * Spring's setter injection
	 * @param requestUtil
	 */
	public void setDataRequestUtil(DataRequestUtil requestUtil) {
		this.dataRequestUtil = requestUtil;
	}

	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}

	public void sendEmailNotificationForBlobDownloadError() {
		// TODO Auto-generated method stub
		bcunService.sendEmailNotificationForBlobDownloadError();
	}
}
