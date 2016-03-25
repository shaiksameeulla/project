/* ****************************************************************
 * CTBS+ Project
 * File <FTPUtils.java>
 * Created by : Reddy Harichandra
 * Creation date : 08-FEB-2011.
 */
package com.capgemini.lbs.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.ftp.ConnectionException;
import com.capgemini.lbs.framework.exception.ftp.DownloadException;
import com.capgemini.lbs.framework.exception.ftp.FileNotExistsException;
import com.capgemini.lbs.framework.exception.ftp.UploadException;

/**
 * FTPUtils - this class permits to upload and download files via FTP.
 * 
 * @author Hari
 * 
 */
public class FTPUtils {
	// Properties
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(FTPUtils.class);
	private final static String BUNDLE_FILE_NAME = "ftp";
	private final static ResourceBundle PROPERTIES = ResourceBundle
			.getBundle(BUNDLE_FILE_NAME);

	public final static String PARAMETER_SAP_FTP_HOST = "host.sap_ftp.parameter";
	public final static String PARAMETER_SAP_FTP_PORT = "port.sap_ftp.parameter";
	public final static String PARAMETER_SAP_FTP_PASV = "passive_connection_mode.sap_ftp.parameter";
	public final static String PARAMETER_SAP_FTP_USER = "user.sap_ftp.parameter";
	public final static String PARAMETER_SAP_FTP_PASSWORD = "password.sap_ftp.parameter";
	public final static String PARAMETER_SAP_FTP_DIR_PACKAGES = "package_dir.sap_ftp.parameter";
	public final static String PARAMETER_SAP_END_FILE = "end_file.sap_ftp.parameter";

	public final static String PARAMETER_FTP_HOST = "host.ftp.parameter";
	public final static String PARAMETER_FTP_PORT = "port.ftp.parameter";
	public final static String PARAMETER_FTP_PASV = "passive_connection_mode.ftp.parameter";
	public final static String PARAMETER_FTP_USER = "user.ftp.parameter";
	public final static String PARAMETER_FTP_PASSWORD = "password.ftp.parameter";
	public final static String PARAMETER_FTP_DIR_PACKAGES = "package_dir.ftp.parameter";
	public final static String PARAMETER_FTP_DIR_PUBLISHED_CATALOGUES = "pub_cat_dir.ftp.parameter";
	public final static String PARAMETER_FTP_END_FILE = "end_file.ftp.parameter";

	public final static String PARAMETER_PAPER_FTP_HOST = "host.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_PORT = "port.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_PASV = "passive_connection_mode.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_USER = "user.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_PASSWORD = "password.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_DIR_PACKAGES = "package_dir.paper_ftp.parameter";
	public final static String PARAMETER_PAPER_FTP_END_FILE = "end_file.paper_ftp.parameter";

	public final static String PARAMETER_STAGING_FTP_HOST = "host.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_PORT = "port.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_PASV = "passive_connection_mode.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_USER = "user.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_PASSWORD = "password.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_DIR_PACKAGES = "package_dir.staging_ftp.parameter";
	public final static String PARAMETER_STAGING_FTP_END_FILE = "end_file.staging_ftp.parameter";

	public final static String PARAMETER_ETL_FTP_HOST = "host.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_PORT = "port.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_PASV = "passive_connection_mode.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_USER = "user.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_PASSWORD = "password.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_DIR_PACKAGES = "package_dir.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_DIR_PUBLISHED_CATALOGUES = "pub_cat_dir.etl_ftp.parameter";
	public final static String PARAMETER_ETL_FTP_END_FILE = "end_file.etl_ftp.parameter";

	public final static String PARAMETER_TRANS_FTP_HOST = "host.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_PORT = "port.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_PASV = "passive_connection_mode.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_USER = "user.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_PASSWORD = "password.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_DIR_PACKAGES = "package_dir.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_DIR_PUBLISHED_CATALOGUES = "pub_cat_dir.trans_ftp.parameter";
	public final static String PARAMETER_TRANS_FTP_END_FILE = "end_file.trans_ftp.parameter";

	/**
	 * @param name
	 * @param mandatory
	 * @return
	 * @throws MissingResourceException
	 */
	public static String getPropertyValue(String name, boolean mandatory)
			throws MissingResourceException {
		try {
			return PROPERTIES.getString(name).trim();
		} catch (MissingResourceException e) {
			if (mandatory) {
				throw e;
			}
		}
		return null;
	}

	/**
	 * @param name
	 * @param mandatory
	 * @return
	 * @throws MissingResourceException
	 */
	public static boolean getBooleanPropertyValue(String name, boolean mandatory)
			throws MissingResourceException {
		try {
			return new Boolean((PROPERTIES.getString(name)).trim())
					.booleanValue();
		} catch (MissingResourceException e) {
			if (mandatory) {
				throw e;
			}

			return false;
		}
	}

	/**
	 * @param name
	 * @param mandatory
	 * @return
	 * @throws MissingResourceException
	 */
	public static String getPathPropertyValue(String name, boolean mandatory)
			throws MissingResourceException {
		try {
			return PROPERTIES.getString(name).trim().replace('\\', '/');
		} catch (MissingResourceException e) {
			if (mandatory) {
				throw e;
			}
		}

		return null;
	}

	/**
	 * This method permits to upload a file on a FTP server.
	 * 
	 * @param host
	 *            the FTP IP address
	 * @param username
	 *            the FTP user
	 * @param password
	 *            the FTP password
	 * @param remoteDir
	 *            the FTP directory where the file has to be placed, it must
	 *            exist on the FTP server
	 * @param remoteFileName
	 *            the target name of the file
	 * @param sourceFilePath
	 *            the path & name of the file to upload
	 * @throws Exception
	 * @author CSOARES
	 * 
	 * @ops.test author="CSOARES" date="May 10, 2006" functionality="ok"
	 *           remainingTest="none"
	 */
	public static void uploadFile(String host, int port, boolean passiveMode,
			String username, String password, String remoteDir,
			String remoteFilePath, String sourceFilePath, boolean createOkFile)
			throws UploadException, ConnectionException,
			FTPConnectionClosedException, UnknownHostException, IOException {
		SimpleFTPClient ftp = new SimpleFTPClient();
		if (ftp.connectAndLogin(host, port, username, password)) {
			boolean creation_ok = false;
			boolean change_ok = false;
			try {
				change_ok = ftp.changeWorkingDirectory(remoteDir);
				if (!change_ok) {
					String[] remoteDirList = remoteDir.split("/");

					if (remoteDirList != null && remoteDirList.length > 0) {
						for (int i = 0; i < remoteDirList.length; i++) {
							if (!remoteDirList[i].equals("")) {
								creation_ok = ftp
										.makeDirectory(remoteDirList[i]);
								change_ok = ftp
										.changeWorkingDirectory(remoteDirList[i]);
								Thread.sleep(50);
							}

						}
					} else {
						creation_ok = ftp.makeDirectory(remoteDir);
					}

					if (creation_ok) {
						change_ok = ftp.changeWorkingDirectory(remoteDir);

						if (!change_ok) {
							throw new Exception(
									"Coult not perform the change of folder");
						}
					} else {
						throw new Exception(
								"Could not create the appropriate folder");
					}
				}
				// END-FIX_CODE for BUG_12701 CCY

				// Set data transfer mode
				if (passiveMode) {
					ftp.enterLocalPassiveMode();
				} else {
					ftp.enterLocalActiveMode();
				}

				// Set the file transfer mode to binary
				ftp.binary();

				// Testing if the source file exists
				if (!FileUtils.isFileExisting(sourceFilePath)) {
					throw new FileNotExistsException();
				}

				String remoteFileName = remoteFilePath;
				int index = remoteFileName.indexOf("/");

				while (index != -1) {
					String pathElement = remoteFileName.substring(0, index);
					ftp.makeDirectory(pathElement);
					ftp.changeWorkingDirectory(pathElement);
					remoteFileName = remoteFileName.substring(index + 1,
							remoteFileName.length());
					index = remoteFileName.indexOf("/");
				}

				// Upload the file
				ftp.uploadFile(sourceFilePath, remoteFileName);

				if (createOkFile) {
					try {
						// Upload an empty file indicating that the upload has
						// terminated
						File okFile = new File(FileUtils.changeFilePathExt(
								sourceFilePath, ".ok"));
						okFile.createNewFile();
						ftp.uploadFile(okFile.getAbsolutePath(), FileUtils
								.changeFilePathExt(remoteFileName, ".ok"));
						FileUtils.deleteFile(okFile.getAbsolutePath());
					} catch (Exception e1) {
						LOGGER.debug("error in FTPUtils::uploadFile()::",e1);
					}
				}
			} catch (Exception e) {
				throw new UploadException(ftp.getReplyString());
			} finally {
				ftp.logout();
				ftp.disconnect();
			}
		} else {
			throw new ConnectionException(ftp.getReplyString());
		}
	}

	
	/**
	 * This method permits to upload a file on a FTP server.
	 * 
	 * @param host
	 *            the FTP IP address
	 * @param username
	 *            the FTP user
	 * @param password
	 *            the FTP password
	 * @param remoteDir
	 *            the FTP directory where the file has to be placed, it must
	 *            exist on the FTP server
	 * @param remoteFilename
	 *            the target name of the file
	 * @param sourceFilepath
	 *            the path & name of the file to upload
	 * @throws Exception
	 * @author CSOARES
	 * 
	 * @ops.test author="CSOARES" date="May 10, 2006" functionality="ok"
	 *           remainingTest="none"
	 */
	public static void downloadFile(String host, int port, boolean passiveMode,
			String username, String password, String remoteFileName,
			String remoteDirPath, String destFilePath, boolean toDelete)
			throws DownloadException, ConnectionException,
			FTPConnectionClosedException, UnknownHostException, IOException {
		SimpleFTPClient ftp = new SimpleFTPClient();
		if (ftp.connectAndLogin(host, port, username, password)) {
			try {
				// Set data transfer mode
				if (passiveMode) {
					ftp.enterLocalPassiveMode();
				} else {
					ftp.enterLocalActiveMode();
				}

				// Set the file transfer mode to binary
				ftp.binary();

				if (remoteFileName != null && destFilePath != null) {
					// Download the file
					if (remoteDirPath != null
							&& !ftp.changeWorkingDirectory(remoteDirPath))
						throw new DownloadException(ftp.getReplyString());
					if (!ftp.downloadFile(remoteFileName, destFilePath))
						throw new DownloadException(ftp.getReplyString());
					if (toDelete && !ftp.deleteFile(remoteFileName))
						throw new DownloadException(ftp.getReplyString());
				}
			} catch (Exception e) {
				throw new DownloadException(ftp.getReplyString());
			} finally {
				ftp.logout();
				ftp.disconnect();
			}
		} else {
			throw new ConnectionException(ftp.getReplyString());
		}
	}

	/**
	 * This method permits to upload a file on a FTP server.
	 * 
	 * @param host
	 *            the FTP IP address
	 * @param username
	 *            the FTP user
	 * @param password
	 *            the FTP password
	 * @param remoteDir
	 *            the FTP directory where the file has to be placed, it must
	 *            exist on the FTP server
	 * @param remoteFilename
	 *            the target name of the file
	 * @param output
	 *            stream
	 * @throws Exception
	 * @author CSOARES
	 * 
	 * @ops.test author="AMUZET" date="May 10, 2006" functionality="ok"
	 *           remainingTest="none"
	 */
	public static void downloadStream(String host, int port,
			boolean passiveMode, String username, String password,
			String remoteFilePath, OutputStream out) throws DownloadException,
			ConnectionException, FTPConnectionClosedException,
			UnknownHostException, IOException {
		SimpleFTPClient ftp = new SimpleFTPClient();
		if (ftp.connectAndLogin(host, port, username, password)) {
			try {
				// Set data transfer mode
				if (passiveMode) {
					ftp.enterLocalPassiveMode();
				} else {
					ftp.enterLocalActiveMode();
				}

				// Set the file transfer mode to binary
				ftp.binary();

				// Download the stream and put into out object
				ftp.downloadStream(remoteFilePath, out);
			} catch (Exception e) {
				throw new DownloadException(ftp.getReplyString());
			} finally {
				ftp.logout();
				ftp.disconnect();
			}
		} else {
			throw new ConnectionException(ftp.getReplyString());
		}
	}

	/**
	 * @param host
	 * @param port
	 * @param passiveMode
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param filename
	 * @return
	 * @throws DownloadException
	 * @throws ConnectionException
	 * @throws FTPConnectionClosedException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static long getSize(String host, int port, boolean passiveMode,
			String username, String password, String remotePath, String filename)
			throws DownloadException, ConnectionException,
			FTPConnectionClosedException, UnknownHostException, IOException {
		SimpleFTPClient ftp = new SimpleFTPClient();
		if (ftp.connectAndLogin(host, port, username, password)) {
			try {
				// Set data transfer mode
				if (passiveMode) {
					ftp.enterLocalPassiveMode();
				} else {
					ftp.enterLocalActiveMode();
				}

				// Set the file transfer mode to binary
				ftp.binary();

				//
				return ftp.getSize(remotePath, filename);
			} catch (Exception e) {
				throw new DownloadException(ftp.getReplyString());
			} finally {
				ftp.logout();
				ftp.disconnect();
			}
		}

		throw new ConnectionException(ftp.getReplyString());
	}

	/**
	 * This method gets the file list of the specified path.
	 * 
	 * @param host
	 * @param port
	 * @param passiveMode
	 * @param username
	 * @param password
	 * @param remotePath
	 *            the specified remote path
	 * @return the file list as an FTP file array.
	 * @throws DownloadException
	 * @throws ConnectionException
	 * @throws FTPConnectionClosedException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static FTPFile[] getFileList(String host, int port,
			boolean passiveMode, String username, String password,
			String remotePath) throws DownloadException, ConnectionException,
			FTPConnectionClosedException, UnknownHostException, IOException {
		SimpleFTPClient ftp = new SimpleFTPClient();
		if (ftp.connectAndLogin(host, port, username, password)) {
			try {
				// Set data transfer mode
				if (passiveMode) {
					ftp.enterLocalPassiveMode();
				} else {
					ftp.enterLocalActiveMode();
				}

				// Set the file transfer mode to binary
				ftp.binary();

				// get the file list
				return ftp.getFileList(remotePath);
			} catch (Exception e) {
				throw new DownloadException(ftp.getReplyString());
			} finally {
				ftp.logout();
				ftp.disconnect();
			}
		}

		throw new ConnectionException(ftp.getReplyString());
	}
}