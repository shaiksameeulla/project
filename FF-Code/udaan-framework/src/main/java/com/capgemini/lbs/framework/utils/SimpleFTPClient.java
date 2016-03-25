/* ****************************************************************
 * CTBS+ Project
 * File <SimpleFTPClient.java>
 * Created by : Harichandra Reddy
 * Creation date : 08-Feb-2011
 */
package com.capgemini.lbs.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

 /**
 * SimpleFTPClient - This is a simple wrapper around the Jakarta Commons FTP
 * library. Just added a few convenience methods to the class. 
 * This Java class requires both the Jakarta Commons Net library and the Jakarta 
 * ORO library (available at http://jakarta.apache.org/oro ).
 * @author CSOARES 
 * 
 * @ops.test
 *        author="Hari"
 *        date="08-Feb-2011"
 *        functionality="see unit tests for each methods..."
 *        remainingTest="none"
 */
public class SimpleFTPClient extends FTPClient 
{
    private static final int DEFAULT_BUFFER_SIZE = 8192;
	/**
	 * A convenience method for connecting and logging in.
	 * 
	 * @param host the FTP host address
	 * @param port the FTP port
	 * @param userName the username
	 * @param password the password
	 * @return true if the connection and the log in succeeded
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws FTPConnectionClosedException
	 * @author CSOARES
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="nothing yet TODO"
	 *        remainingTest="all TODO"
	 */
	public boolean connectAndLogin (String host, int port, String userName, String password)
	throws  IOException, UnknownHostException, FTPConnectionClosedException 
	{
		boolean success = false;
		this.setDefaultPort(port); 
		this.connect(host); 
		int reply = this.getReplyCode();
		if (FTPReply.isPositiveCompletion(reply))
		{
			success = this.login(userName, password);
		}
		
		return success;
	}
		
	/**
	 * A convenience method for disconnect.
	 * @throws IOException 
	 * 
	 * @see org.apache.commons.net.SocketClient#disconnect()
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public void disconnect() throws IOException
	{
	    if (this != null && super.isConnected()) 
	    {            
	       super.logout();                
	     // super.disconnect();            
	    }
	}
	
	/**
	 * This method sets ASCII mode for file transfers
	 * 
	 * @return true if succeeded
	 * @throws IOException
	 * @author CSOARES
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public boolean ascii () 
	throws IOException 
	{
		return setFileType(FTP.ASCII_FILE_TYPE);
	}
	
	/**
	 * This method sets Binary mode for file transfers
	 * 
	 * @return true if succeeded
	 * @throws IOException
	 * @author CSOARES
	 * 
	 * @ops.test
	*        author="Hari"
	 *       date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public boolean binary () 
	throws IOException 
	{
		return setFileType(FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * This method downloads a file from the server, and save it to the specified local file
	 * 
	 * @param serverFile complete path to the remote file
	 * @param localFile complete path to the local file
	 * @return true if succeeded
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 * @author CSOARES
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public boolean downloadFile (String serverFile, String localFile)
	throws IOException, FTPConnectionClosedException 
	{
		FileOutputStream out = new FileOutputStream(localFile);
		boolean result = retrieveFile(serverFile, out);
		out.close();
		return result;
	}
	
	
	/**
	 * This method downloads a stream from the server, and save it to the specified outputStream
	 * 
	 * @param serverFile complete path to the remote file
	 * @param outputStream 
	 * @return true if succeeded
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 * @author AMUZET
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public void downloadStream (String serverFile, OutputStream out)
	throws IOException, FTPConnectionClosedException 
	{
	    BufferedInputStream input = new BufferedInputStream(retrieveFileStream(serverFile));
	    BufferedOutputStream output = new BufferedOutputStream(out);
	    byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
	    int nbReadBytes = 0;
		
		while((nbReadBytes = input.read(buffer, 0, buffer.length)) != -1)
		{
		    output.write(buffer, 0, nbReadBytes);
		}
	    output.write(input.read());
	    
	    output.close();
	    input.close();
	}
	
	
	/**
	 * @param remotePath
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 */
	public long getSize(String remotePath, String filename)
	throws IOException, FTPConnectionClosedException
	{
	    FTPFile[] list= this.listFiles(remotePath);
	    if (list != null)
	    {
	        for (int i = 0; i < list.length; i++)
	        {
	            FTPFile file = list[i];
	            if (file.getName().equals(filename))
	                return file.getSize();
	        }
	    }
	    
	    return 0;
	}
	
	/**
	 * This method gets the file list corresponding to the path. 
	 * @param remotePath the remote pathe.
	 * @return the file list as an FTP file array.
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 */
	public FTPFile[] getFileList(String remotePath)
	throws IOException, FTPConnectionClosedException
	{
	   return  this.listFiles(remotePath);
	}
	
	/**
	 * This method uploads a file to the server
	 * 
	 * @param localFile complete path to the local file
	 * @param serverFile complete path to the remote file
	 * @return true if succeeded
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 * @author CSOARES
	 * 
	 * @ops.test
	 *        author="Hari"
	 *        date="08-Feb-2011"
	 *        functionality="ok"
	 *        remainingTest="none"
	 */
	public boolean uploadFile (String localFile, String serverFile) 
	throws IOException, FTPConnectionClosedException 
	{
		boolean result = false;
		FileInputStream in = new FileInputStream(localFile);
		try {
			result = storeFile(serverFile, in);
		} catch (Exception ex) {
			throw ex;
		} finally {
			in.close();
		}
		
		return result;
	}

	/**
	 * @param localFile
	 * @param serverFile
	 * @return
	 * @throws IOException
	 * @throws FTPConnectionClosedException
	 */
	public boolean uploadFile (FileInputStream localFile, String serverFile) 
	throws IOException, FTPConnectionClosedException 
	{
		boolean result = storeFile(serverFile, localFile);
		localFile.close();
		return result;
	}

}
