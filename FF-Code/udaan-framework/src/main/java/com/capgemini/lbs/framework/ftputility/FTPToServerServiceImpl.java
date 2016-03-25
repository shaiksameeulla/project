package com.capgemini.lbs.framework.ftputility;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.SimpleFTPClient;

/**
 * @author anwar
 * 
 */
public class FTPToServerServiceImpl implements FTPToServerService {
	
	private Properties ftpProp;
	private SimpleFTPClient ftpClient;
	private String fileLocation = null;
	private File ftpDirectory = null;
	
	private static final Logger logger = Logger.getLogger(FTPToServerServiceImpl.class);	
			
	/**
	 * @return
	 */
	public String getFileLocation() {
		return fileLocation;
	}
	/**
	 * @param fileLocation
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	/**
	 * @return
	 */
	public Properties getFtpProp() {
		return ftpProp;
	}
	/**
	 * @param ftpProp
	 */
	public void setFtpProp(Properties ftpProp) {
		this.ftpProp = ftpProp;
	}
	/**
	 * @return
	 */
	public SimpleFTPClient getFtpClient() {
		return ftpClient;
	}
	/**
	 * @param ftpClient
	 */
	public void setFtpClient(SimpleFTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
		
	@Override
	public void executeFTPToServer(boolean isManual) {
		// TODO Auto-generated method stub
		File baseDir = getFtpLocationDir();
		if(baseDir.isDirectory()) {
			String[] filesInBaseDir = baseDir.list();
			if(filesInBaseDir !=null && filesInBaseDir.length > 0) {
				try {
					boolean isConnected = connectNLogin(ftpClient);
					if(isConnected == true) {
						ftpClient.binary();
						ftpClient.enterLocalPassiveMode();
						for(String fileName :filesInBaseDir) {
							String filePath = getFtpFileLocation() + File.separator + fileName;
							try {
								ftpClient.uploadFile(filePath, fileName);
								File f1 = new File(filePath);

								//Archive the file after successful update
								CGXMLUtil.moveFile(ftpProp.getProperty("ctbs.sap.archive.dir"), f1);
								logger.debug("Archive the file ...");								
								
								f1.delete();
								f1 = null;
							} catch (Exception e) {
								logger.error("##### FTPToServerServiceImpl::executeFTPToServer::Exception while uploading file through ftp file: " + filePath);
								logger.error("##### FTPToServerServiceImpl::executeFTPToServer::Exception" ,e);
							}
						}
					}
					else {
						logger.debug("##### FTPToServerServiceImpl::executeFTPToServer:: Unable to connect ftp server [10.74.101.203]" );
					}
				} catch (Exception e) {
					logger.error("##### FTPToServerServiceImpl::executeFTPToServer::Exception:e:getMessage" ,e);
				} finally {
					try {
						ftpClient.logout();
					} catch (IOException e) {
						logger.error("##### FTPToServerServiceImpl::executeFTPToServer::IOException while ftp logout:error" ,e);
					}
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
						logger.error("##### FTPToServerServiceImpl::executeFTPToServer::IOException while ftp logout:error" ,e);
					}
				}
			}
			filesInBaseDir = null;
		} else {
			logger.debug("##### FTPToServerServiceImpl::executeFTPToServer:: Base Location is not a Directory");
		}
	}
	
	/**
	 * @return
	 */
	private String getFtpFileLocation() {
		if(fileLocation == null) {
			String osName = System.getProperty("os.name");			
			if(osName.toLowerCase().contains("windows")){
				fileLocation = ftpProp.getProperty("ctbsweb.ftp.file.location.windows");
			} else {
				fileLocation = ftpProp.getProperty("ctbsweb.ftp.file.location.linux");
			}
			File file = new File(fileLocation);
			if(!file.exists()) {
				file.mkdir();
			}
		}
		return fileLocation;
	}
	/**
	 * @return
	 */
	private File getFtpLocationDir() {
		if(ftpDirectory == null) {
			ftpDirectory = new File(getFtpFileLocation());
		}
		return ftpDirectory;
	}
	
	/**
	 * @param ftp
	 * @return
	 */
	private boolean connectNLogin(SimpleFTPClient ftp) {
		boolean isConnected = false;
		try {
			String hostName = ftpProp.getProperty("hostName");
			String userId = ftpProp.getProperty("UserId");
			String password = ftpProp.getProperty("Password");
			String port = ftpProp.getProperty("PortNo");
			isConnected = ftp.connectAndLogin(hostName, Integer.parseInt(port), userId, password);
		} catch (Exception e) {
			logger.error("#### FTPToServerServiceImpl::connectNLogin::Exception:error:::", e);
		}
		return isConnected;
	}
}