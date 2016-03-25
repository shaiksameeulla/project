package com.cg.lbs.bcun.service.inbound;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.BcunCentralAuthenticationUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.SimpleFTPClient;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;

/**
 * @author mohammal
 * Jan 15, 2013
 * Used to process the in bound data from the branch office.
 */
public class InboundBranchFTPFileProcessor {

	/**
	 *  Log the message of the process.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(InboundBranchFTPFileProcessor.class);
	
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

	/**
	 * Starting points to setup the process
	 */
	public void fileUploadProcess() throws HttpException, ClassNotFoundException, IOException
	{
		LOGGER.debug("InboundBranchFTPFileProcessor::proceedDatasync::start");

		//fetching for mode of operation
		String modeOfOpration = bcunService.getModeOfOpration();
		LOGGER.debug("InboundBranchFTPFileProcessor::proceedDatasync::modeOfOpration::" + modeOfOpration);
		//checking mode of operation for FTP
		if(modeOfOpration == null || modeOfOpration.equals(BcunConstant.FTP_BCUN_OPRATION_MODE)) {
			try {
				LOGGER.debug("InboundBranchFTPFileProcessor::proceedDatasync::starting file upload....");
				uploadFilesToCentralServer();
				LOGGER.debug("InboundBranchFTPFileProcessor::proceedDatasync::file upload completed....");
			} catch (Exception ex) {
				LOGGER.error("InboundBranchFTPFileProcessor::proceedDatasync::error while uploading files::" ,ex);
			}
		} 
		LOGGER.debug("InboundBranchFTPFileProcessor::processDatasync::end");
	}




	/**
	 * Used to upload files to the central server.
	 * Central server FTP server information is stored in 
	 * BCUN.properties and FTP.properties file.
	 * @throws IOException 
	 */
	private void uploadFilesToCentralServer() throws IOException {
		LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::start");
		//Getting location of the file from where server will upload to central location.
		String baseLocation = bcunService.getBaseFileLocation();
		//Getting all the files which all are has to be uploaded
		//bcunService.getAllFilesNames(baseLocation);
		if(baseLocation != null && !baseLocation.isEmpty()) {
			//Creating base directory of configured location

			File baseDir = new File(baseLocation);
			//Checking whether location is directory or not
			if(baseDir.isDirectory()) {
				//Listing all the files available for transfer to central server
				String[] availableFiles = baseDir.list(FileUtils.bcunInboundFileUploadFilter());// 
				Integer totalNoOfFiles=FileUtils.getTotalNoOfFiles(baseDir);
				
				baseDir = null;

				if(availableFiles != null && availableFiles.length > 0 && BcunCentralAuthenticationUtil.getBcunCentralAuthenticationStatus()) {
					LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: no of files to be processed :["+availableFiles.length+"] And total no of :["+totalNoOfFiles+"]");
					//Connecting and login into FTP server
					boolean isConnected = connectNLogin(); 
					//Checking if connected
					if(isConnected) {
						setFtpConnectionParams();
						File f1 = null;
						for(String fileName : availableFiles) {
							boolean isExceptionOccurred=false;
							boolean isFileTransmissionError=false;
							File zipFile = null;
							try {
								String filePath = baseLocation + File.separator + fileName;
								String zipFilName =FilenameUtils.removeExtension(fileName)+".zi";
								String zipPath = baseLocation + File.separator +zipFilName;
								LOGGER.debug("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: fullfileName :["+filePath+"]");
								f1 = new File(filePath);
								
								if(f1.isDirectory())
									continue;
								isExceptionOccurred = prepareZipFile(fileName,
										filePath, zipPath);
								zipFile= new File(zipPath);
								long zipFileLength=zipFile.length();
								LOGGER.debug("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: ZIP File created with size :" +zipFileLength +" Exception status:"+isExceptionOccurred);
								if(!isExceptionOccurred && zipFileLength>0){
									//Uploading file
									boolean isTransmitted=ftpCLient.uploadFile(zipPath, zipFilName);

									if(isTransmitted){
										
										LOGGER.debug("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: full upload completed waiting for reply code.");
										int replycode = ftpCLient.getReplyCode();
										String replyString=ftpCLient.getReplyString();
										LOGGER.debug("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::  FTP server Replycode  :["+replycode+"] and ReplyString() :["+replyString+"]  for fileName :["+fileName+"] upload.");

										if(FTPReply.isPositiveCompletion(replycode)) {
											boolean isRenamed =false;
											LOGGER.debug("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: Replycode :["+replycode+"] and ReplyString() :["+replyString+"] fileName :["+fileName+"]");
											FTPFile[] f=ftpCLient.listFiles(zipFilName);
											if(f!=null && f.length==1){
												FTPFile ftpFile=f[0];
												long size= ftpFile.getSize();
												f1 = new File(zipPath);
												if(f1.length() == size && size >0){
													LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: size :"+size);
													if(ftpCLient.rename(zipFilName, zipFilName+"p")){
														isRenamed = bcunService.renameFile(fileName, baseLocation, "pr");
														try{
															f1.delete();
														}catch(Exception e){
															LOGGER.error("InboundBranchFTPFileProcessor :: File Deletion Exception",e);
														}
													}
												}else{
													LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: uploaded file Size & Local file size is mismatching hence file renaming is not performing");
												}

											}
											LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::file moved to pr location ? ::" +isRenamed +" replycode :["+replycode+"] ReplyString() :["+replyString+"] for the File Name :["+fileName+"]");
										}else{
											LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::File can not be moved to PR due to improper write to central with replycode ["+replycode+"]"+" for the File Name :["+fileName+"]");
										}
									}else{
										LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::File has not uploaded to Central due To File transmission error  and it's flag["+isTransmitted+"] for the File Name :["+fileName+"]");
									}
								}else{
									LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::local ZIP file creation ERROR for the File Name :["+fileName+"] hence file not transferred");
								}
							} catch (FTPConnectionClosedException e) {
								isFileTransmissionError=true;
								LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::FTPConnectionClosedException:: for the File Name :["+fileName+"]" , e);
							}catch(CopyStreamException copyException){
								isFileTransmissionError=true;
								LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::CopyStreamException(due to The process cannot access the file because another process has locked a portion of the file) for the File Name :["+fileName+"]" , copyException);
							}catch (IOException e) {
								isFileTransmissionError=true;
								LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::IOException:: for the File Name :["+fileName+"]" , e);
							}catch (Exception e) {
								isFileTransmissionError=true;
								LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::Exception:: for the File Name :["+fileName+"]" , e);
							}
							if(isFileTransmissionError){
								StringBuilder erorLogs= new StringBuilder();
								erorLogs.append("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::file transmission error hence deleting .ZI file:: for the File Name :[");
								erorLogs.append(fileName);
								erorLogs.append("] and zi file deletion status :");
								erorLogs.append((zipFile!=null?zipFile.delete():"Not deleted"));
								LOGGER.error(erorLogs.toString());
							}
						}

						/*if(ftpCLient.isConnected()){
							LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::Disconnecting FTP");
							//ftpCLient.disconnect();
						}*/
					}else{
						LOGGER.warn("InboundBranchFTPFileProcessor::uploadFilesToCentralServer:: FTP server is not connected(due to invalid Credentials/non available server)");
					}
				}else{
					LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::No File To Transfer through FTP");
				}
			}
		}
		LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::end");
	}




	private boolean prepareZipFile(String fileName, String filePath,
			String zipPath) throws IOException {
		LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::prepareZipFile ::START");
		FileOutputStream fos=null;
		FileInputStream fis=null;
		ZipOutputStream zout=null;
		boolean isExceptionOccured=false;
		try{
		 fos = new FileOutputStream(zipPath);
		
		 fis = new FileInputStream(filePath);
		 zout = new ZipOutputStream(
				new BufferedOutputStream(fos));
		ZipEntry entry = new ZipEntry(fileName);
		zout.putNextEntry(entry);
		int count;
		byte buffer[] = new byte[802400];
		while ((count = fis.read(buffer)) != -1) {
			zout.write(buffer, 0, count);
		}
		zout.closeEntry();
		fis.close();
		zout.close();
		fos.flush();
		
		}catch(Exception e){
			LOGGER.error("InboundBranchFTPFileProcessor::uploadFilesToCentralServer: Error ZIP FILE Creation:["+fileName+"]",e);
			//isExceptionOccurred=true;
			fis=null;
			zout=null;
			isExceptionOccured=true;
		}finally{
			if(fos!=null){
			fos.close();}
		}
		LOGGER.info("InboundBranchFTPFileProcessor::uploadFilesToCentralServer::prepareZipFile ::END with isExceptionOccured : "+isExceptionOccured);
		return isExceptionOccured;
	}




	private void setFtpConnectionParams() throws IOException, SocketException {
		//Setting file type
		ftpCLient.setFileType(FTPClient.BINARY_FILE_TYPE);
		//Setting file transfer mode
		ftpCLient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
		
		ftpCLient.enterLocalActiveMode();
		//ftpCLient.setFileTransferMode(FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE);
		//Setting time out
		//ftpCLient.setDataTimeout(150000);
		ftpCLient.setConnectTimeout(100000);
		ftpCLient.setSoTimeout(180000);
		ftpCLient.setDataTimeout(240000);
	}





	/**
	 * Connect and login to FTP server
	 * @return
	 */
	private boolean connectNLogin()  {
		boolean isConnected = false;
		//getting host name from ftp properties file
		String host = ftpProp.getProperty("central.ftp.host");
		LOGGER.debug("InboundBranchFTPFileProcessor::connectNLogin::host::" + host);
		//getting port name from ftp properties file
		String port = ftpProp.getProperty("central.ftp.port");
		LOGGER.debug("InboundBranchFTPFileProcessor::connectNLogin::port::" + port);
		//getting user name from ftp properties file
		String userName = ftpProp.getProperty("central.ftp.username");
		LOGGER.debug("InboundBranchFTPFileProcessor::connectNLogin::userName::" + userName);
		//getting password from ftp properties file
		String password = ftpProp.getProperty("central.ftp.password");
		LOGGER.debug("InboundBranchFTPFileProcessor::connectNLogin::password::" + password);
		try {
			//looking for connection
			isConnected = ftpCLient.connectAndLogin(host, Integer.parseInt(port), userName, password);
		} catch (NumberFormatException e) {
			LOGGER.error("InboundBranchFTPFileProcessor::connectNLogin::NumberFormatException::" ,e);
		}catch (UnknownHostException e) {
			LOGGER.error("InboundBranchFTPFileProcessor::connectNLogin::UnknownHostException::" ,e);
		}catch (FTPConnectionClosedException e) {
			LOGGER.error("InboundBranchFTPFileProcessor::connectNLogin::FTPConnectionClosedException::" ,e);
		}catch (Exception e) {
			LOGGER.error("InboundBranchFTPFileProcessor::connectNLogin::Exception::" ,e);
		}
		LOGGER.debug("InboundBranchFTPFileProcessor::connectNLogin::isConnected::" + isConnected);
		return isConnected;
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




}
