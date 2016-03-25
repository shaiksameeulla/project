package com.cg.lbs.bcun.service.outbound;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
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
public class OutboundBranchDataExtractProcessor {

	
	/**
	 * Helper class to create http request
	 */
	private HttpRequestUtil requestUtil;
	
	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;
	
	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchDataExtractProcessor.class);
	
	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void processBranchdata() throws HttpException, ClassNotFoundException, IOException {
		String uniqueRequestId="119"+StringUtil.getRandomNumber();
		//Getting configured branch request URL
		String branchRequestUrl = requestUtil.branchRequestUrl()+"&uniqueRequestId=" + uniqueRequestId;
		LOGGER.debug("OutboundBranchDataExtractProcessor::processBranchdata::start=====>Request ID:["+uniqueRequestId+"] branchRequestUrl :["+branchRequestUrl+"]");
		//Getting request parameters in TO
		OutboundBranchDataTO requestTO = requestUtil.prepareHttpRequestTO();
		if(requestTO!=null){
			requestTO.setUniqueRequestId(uniqueRequestId);
		}
		if(BcunCentralAuthenticationUtil.getBcunCentralAuthenticationStatus()){
			boolean isResponseProcessedWithErrors=false;
			LOGGER.debug("####### Authentication success :: hence data can be retrieved from Central server" );
			//Posting request to server
			byte[] serverResponse = requestUtil.postRequestToServer(branchRequestUrl, requestTO);
			if(serverResponse != null && serverResponse.length>0) {
				LOGGER.debug("OutboundBranchDataExtractProcessor::processBranchdata::Packet Received for the request=====>Request ID:["+uniqueRequestId+"]");
				//COnverting response byte array into string 
				//Converting JSON string into TO 
				OutboundBranchDataTO inputTO=null;
				inputTO = getOutboundBranchData(serverResponse, inputTO);

				try {
					if(!StringUtil.isNull(inputTO)){
						//Processing response received from central server
						List<byte[]>  zipFiles = inputTO.getZipFIles();
						if(zipFiles != null && !zipFiles.isEmpty()) {
							isResponseProcessedWithErrors=processResponseData(inputTO);
						}
					}else{
						LOGGER.warn("####### OutboundBranchDataExtractProcessor::processBranchdata::since there is problem with Json then revert it back to the older stage of the Bolb Status #########");
						//since there is problem with Json then revert it back to the older stage of the Bolb Status
						//setFlagForFailedTransfers(requestTO);
						isResponseProcessedWithErrors=true;

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.error("OutboundBranchDataExtractProcessor::BolbExtraction::Exception=====>for the RequestId ["+uniqueRequestId+"]",e);
					isResponseProcessedWithErrors=true;
				}
				LOGGER.debug("####### OutboundBranchDataExtractProcessor::Starts two way handshaking::#########START for the RequestId ["+uniqueRequestId+"]" );
				if(isResponseProcessedWithErrors){
					setFlagForFailedTransfers(requestTO);
				}else{
				//set the flag to T in the central server.
				setDataTransferFlagToTransferred(requestTO);
				}
				LOGGER.debug("####### OutboundBranchDataExtractProcessor::Starts two way handshaking::#########END for the RequestId ["+uniqueRequestId+"]" );
			} else if(requestTO.getHttpStatusCode()!=HttpStatus.SC_OK) {
				LOGGER.warn("####### OutboundBranchDataExtractProcessor::processBranchdata::central server is not responding #########");
				//set the flag to central for corrupt data.
				LOGGER.warn("####### OutboundBranchDataExtractProcessor::Starts two way handshaking::For status code[ "+requestTO.getHttpStatusCode()+"]::#########START for the RequestId ["+uniqueRequestId+"]");
				setFlagForFailedTransfers(requestTO);
				LOGGER.warn("####### OutboundBranchDataExtractProcessor::Starts two way handshaking::For status code[ "+requestTO.getHttpStatusCode()+"]::#########END for the RequestId ["+uniqueRequestId+"]" );
			}
		}else{
			LOGGER.error("####### Authentication failed :: hence data cannot be retrieved from Central server" );
		}
		
		LOGGER.debug("OutboundBranchDataExtractProcessor::processBranchdata::end=====>Request ID:["+uniqueRequestId+"]");
	}

	/**
	 * @param serverResponse
	 * @param inputTO
	 * @return
	 */
	private OutboundBranchDataTO getOutboundBranchData(byte[] serverResponse,
			OutboundBranchDataTO inputTO) {
		try {
			String jsonString = new String(serverResponse);
			inputTO = (OutboundBranchDataTO)bcunService.jsonStringToJava(jsonString, OutboundBranchDataTO.class);
		} catch (Exception e1) {
			LOGGER.error("OutboundBranchDataExtractProcessor::getOutboundBranchData::Exception=====>for the RequestId ["+inputTO!=null?inputTO.getUniqueRequestId():null+"]",e1);
		}
		return inputTO;
	}

	/**
	 * @param requestTO
	 * @throws HttpException
	 * @throws IOException
	 */
	private void setFlagForFailedTransfers(OutboundBranchDataTO requestTO)
			throws HttpException, IOException {
		String requestId=requestTO!=null?requestTO.getUniqueRequestId():null;
		LOGGER.debug("####### OutboundBranchDataExtractProcessor::setFlagForFailedTransfers :: START::for the RequestId ["+requestId+"]");
		if(!CGCollectionUtils.isEmpty(requestTO.getPacketIds())){
			String statusUpdateUrl = requestUtil.statusUpdateUrl();
			requestTO.setUpdateFlagStatus(BcunConstant.DATA_TRANSFER_STATUS_NEW);
			requestUtil.postRequestToServer(statusUpdateUrl, requestTO);
		}else{
			LOGGER.error("####### OutboundBranchDataExtractProcessor::Starts two way handshaking::setFlagForFailedTransfers : Packet id is empty" );
		}
		LOGGER.debug("####### OutboundBranchDataExtractProcessor::setFlagForFailedTransfers :: START::for the RequestId ["+requestId+"]");
	}

	/**
	 * @param requestTO
	 * @throws HttpException
	 * @throws IOException
	 */
	private void setDataTransferFlagToTransferred(OutboundBranchDataTO requestTO)
			throws HttpException, IOException {
		String statusUpdateUrl = requestUtil.statusUpdateUrl()+"&uniqueRequestId=" + requestTO.getUniqueRequestId();
		if(requestTO!=null && !CGCollectionUtils.isEmpty(requestTO.getPacketIds())){
			requestTO.setFileData(null);//making sure response will not flow back to central server
			requestTO.setZipFIles(null);//making sure response will not flow back to central server
			LOGGER.debug("####### OutboundBranchDataExtractProcessor::about to initiate URL::setDataTransferFlagToTransferred ::"+statusUpdateUrl +"With PacketOfficeId ["+requestTO.getDataExtctrIdStr()+"]for the RequestId ["+requestTO.getUniqueRequestId()+"]");
			requestTO.setUpdateFlagStatus(BcunConstant.DATA_TRANSFER_STATUS_TRANSFERRED);
			requestUtil.postRequestToServer(statusUpdateUrl, requestTO);
		}
	}
	
	/**
	 * Process central server response
	 * @param inputTO is server response
	 */
	private boolean processResponseData(OutboundBranchDataTO inputTO) {
		if(inputTO == null)
			return false;
		//Preparing file from server response
		return prepareFile(inputTO);
		//inserting data received from central server to branch database
		//insertFileDataIntoDatabase();
	}
	
	/**
	 * Used to prepare file from central server's response
	 * @param inputTO containing response data
	 */
	private boolean prepareFile(OutboundBranchDataTO inputTO) {
		//Getting file location
		boolean isExceptionOccurred=false;
		String requestId=inputTO!=null?inputTO.getUniqueRequestId():null;
		LOGGER.debug("OutboundBranchDataExtractProcessor::prepareFile::START For  the RequestId ["+requestId+"]");
		String centralFileLocation = bcunService.getBaseFileLocation();
		//Getting zip file contents from TO
		List<byte[]> zipFiles =  inputTO.getZipFIles();
		
		//Iterating through zip content
		if(zipFiles != null && !zipFiles.isEmpty()) {
			LOGGER.debug("OutboundBranchDataExtractProcessor::prepareFile:: about to process Total Blobs ["+zipFiles.size()+"] For  the RequestId ["+requestId+"]");
			for(byte[] zipFIle : zipFiles) {
				try {
					//Creating ZIP file location with name
					String fileName = centralFileLocation + File.separator + System.currentTimeMillis() + ".zip" ;
					//Creating ZIP file
					ZipUtility.createLocalZipFile(fileName , zipFIle);
					//extracting ZIP file for XML file
					ZipUtility.unzip(fileName, centralFileLocation);
					//Deleting ZIP file
					boolean isDeleted = bcunService.deleteFile(fileName);
					LOGGER.debug("OutboundBranchDataExtractProcessor::prepareFile::deleting zip file after extracting:: " + isDeleted+"for the RequestId ["+requestId+"]");
				} catch (IOException e) {
					LOGGER.error("OutboundBranchDataExtractProcessor::prepareFile::IOException:: for the RequestId ["+requestId+"]" , e);
					isExceptionOccurred=true;
				}
			}
		}
		
		LOGGER.debug("OutboundBranchDataExtractProcessor::prepareFile::END For  the RequestId ["+requestId+"]");
		return isExceptionOccurred;
	}
	
	
	
	/**
	 * Spring's setter injection
	 * @param requestUtil
	 */
	public void setRequestUtil(HttpRequestUtil requestUtil) {
		this.requestUtil = requestUtil;
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
