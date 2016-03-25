package com.capgemini.lbs.framework.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;


/**
 * 
 * @author anttus
 *
 */
public class CGXMLUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(CGXMLUtil.class);

	/**
	 * This method is used to create a filename with a timestamp and a seperator
	 * @param filelocName the name of the file with the extension
	 * @param dateTimeFormat the timestamp format needed for the filename
	 * @param seperator the seperator required between the filename and dateformat
	 * @return
	 * @throws Exception
	 */
	public static String createFileNameWithTimestamp(String filelocName,String dateTimeFormat,String seperator) {
		StringBuffer strFileName = new StringBuffer(filelocName);
		String strDateTimeFormat = seperator + DateUtil.getDDMMYYYYDateString(DateUtil.getCurrentDate(),dateTimeFormat)+System.currentTimeMillis();
		strFileName.insert(filelocName.lastIndexOf(FrameworkConstants.CHARACTER_DOT),strDateTimeFormat );
		return strFileName.toString();
	}


	/**
	 * @param dirLocation
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public static boolean moveFile (String dirLocation, File sourceFile) {
		//Destination directory
		boolean moved = false;
		File destDir = new File (dirLocation);

		//TO CREATE THE DIRESCTORY STRUCTURE 

		if(destDir != null && !destDir.exists()){
			destDir.mkdirs();
		}
		File destination=new File(destDir, sourceFile.getName());

		LOGGER.debug("CGXMLUtil :: MOVE  destDir:"+destDir+":FileName:"+sourceFile.getName());
		moved =  sourceFile.renameTo(destination);
		if(!moved){
			try {
				org.apache.commons.io.FileUtils.moveFile(sourceFile, destination);
			} catch (IOException e) {
				moved=false;
				LOGGER.error("CGXMLUtil :: moveFile###ERROR:"+destDir+":FileName:"+sourceFile.getName(),e);
			}
		}
		LOGGER.debug("CGXMLUtil :: moveFile :: STATUS:"+moved);
		return moved;
	}
	/**
	 * @author mohammes
	 * @param dirLocation:destination directory
	 * @param RandomAccessFile:raf by which data can be read 
	 * @param fileName
	 * 
	 * created Date : 17-feb-2012
	 * @description :method copy the content from RandomAccessFile:raf and saves the content in dirLocation with  fileName
	 * 
	 * 
	 */
	public static boolean copyAndMoveFile(String dirLocation, RandomAccessFile raf ,String fileName){
		//Destination directory
		boolean moved = false;
		FileOutputStream fos=null;
		try {
			File destDir = new File (dirLocation);
			
			//TO CREATE THE DIRESCTORY STRUCTURE 
			
				if(destDir != null && !destDir.exists()){
				destDir.mkdirs();
				}
				raf.seek(0);
			int BUFFER = (int)raf.length();
			byte buffer[] = new byte[BUFFER];
			raf.read(buffer);
			File destFile = new File(destDir,FrameworkConstants.FILE_PROCESSED+fileName);
			 fos = new FileOutputStream(destFile);
			fos.write(buffer);
			moved = true;
		} catch (Exception e) {
			moved = true;
			LOGGER.error("copyAndMoveFile:["+moved+"] for file name ["+fileName+"]",e);
		}
		finally{
			if(fos != null){
					try {
						fos.close();
					} catch (IOException e) {
						LOGGER.error("CGXMLUtil::copyAndMoveFile::error =IOException " ,e);
					}
			}
		}
		
		return moved;
	}

	
	/**
	 * @param dirLocation
	 * @param matchCriteria
	 * @param extension
	 * @return
	 * @throws Exception
	 */
	public static File[] retrieveMatchFileNames (String dirLocation, String matchCriteria,String extension) {
		File destDir = new File(dirLocation);
		FilenameFilter filter = new FileFilter(matchCriteria,extension);
		File[] fileList = destDir.listFiles(filter);
		if (fileList != null) {
		      LOGGER.debug("\nThe " + fileList.length
		          + " matching items in the directory, " + destDir.getName()
		          + ", for match criteria ["+matchCriteria+"] are :");
		      for (File file : fileList) {
		    	  LOGGER.debug(file + " is a "
		                + (file.isDirectory() ? "directory" : "file"));
		      }
		    } else {
		    	LOGGER.debug(destDir.getName() + " is not a directory");
		    }

		return fileList;
	}
	

}
