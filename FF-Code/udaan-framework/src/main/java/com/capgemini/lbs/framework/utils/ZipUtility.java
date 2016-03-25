package com.capgemini.lbs.framework.utils;

/**
 * 
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * @author nisahoo
 *
 */
public class ZipUtility {
	
private static final Logger LOGGER = Logger.getLogger(ZipUtility.class);
	
	/**
	 * 
	 * @param path: Path where the Zip File is created
	 * @param data: File Data
	 * @throws IOException
	 */
	public static void createLocalZipFile(String path, byte[] data) throws IOException {
		
		LOGGER.debug("ZipUtility : createLocalZipFile() : START at path :["+ path+"]");
		
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			LOGGER.debug("ZipUtility : createLocalZipFile() : Exception ",e); 
		}
		
		LOGGER.debug("ZipUtility : createLocalZipFile() : END at path :["+ path+"]");
	}
	
	/**
	 * @param ZipFile
	 * @param destUnZipFolder
	 * @throws IOException
	 */
	public static void unzip(String ZipFile, String destUnZipFolder)throws IOException {
		LOGGER.debug("ZipUtility : unzip() : START");

		byte[] buffer = new byte[802400];

		// create output directory is not exists
		File folder = new File(destUnZipFolder);
		if (!folder.exists()) {
			LOGGER.debug("Folder creating  for : path ["+folder.getPath() +"]");
			folder.mkdirs();
		}

		// get the zip file content
		ZipInputStream zis = new ZipInputStream(new FileInputStream(ZipFile));
		// get the zipped file list entry
		ZipEntry ze =null;

		while((ze = zis.getNextEntry()) != null)
		{
			String fileName = ze.getName();
			LOGGER.debug("Unziping the  File: ["+fileName +"]");
			File newFile = new File(destUnZipFolder + File.separator + fileName);

			LOGGER.debug("Unzip File : " + newFile.getAbsoluteFile());

			// create all non exists folders
			// else you will hit FileNotFoundException for compressed folder
			File f = new File(newFile.getParent());
			if(f != null && !f.exists()){
				LOGGER.debug("Folder creating  for : path ["+folder.getPath() +"]");
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			
		}

		zis.closeEntry();
		zis.close();

		LOGGER.debug("Unzip Succesful to path :"+destUnZipFolder);
		buffer = null;
		LOGGER.debug("ZipUtility : unzip() : END");
	}
	
	public static void unzipFromFolder(String fromZipFilesLocation, String destUnZipFolder)throws IOException {
		LOGGER.debug("ZipUtility : unzip() : START");

		byte[] buffer = new byte[802400];

		File fromZipFolder = new File(fromZipFilesLocation);
		File files[]=fromZipFolder.listFiles();
		for(File eachZipFile : files){
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(eachZipFile));
			// get the zipped file list entry
			ZipEntry ze =null;

			while((ze = zis.getNextEntry()) != null)
			{
				String fileName = ze.getName();
				LOGGER.debug("Unziping the  File: ["+fileName +"]");
				File newFile = new File(destUnZipFolder + File.separator + fileName);

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

				fos.close();

			}

			zis.closeEntry();
			zis.close();
		}
		LOGGER.debug("Unzip Succesful to path :"+destUnZipFolder);
		buffer = null;
		LOGGER.debug("ZipUtility : unzip() : END");
	}
	
	/**
	 * @param unzipFolderPath
	 * @return
	 * @throws IOException
	 */
	public static byte[] createInMemoryZipFile(String unzipFolderPath)throws IOException {

		LOGGER.debug("ZipUtility : createInMemoryZipFile() : START");

		byte[] inMemoryzipFile = null;
		int BUFFER = 802400;
		byte buffer[] = new byte[BUFFER];

		// Check to see if the directory exists
		File unzipFolder = new File(unzipFolderPath);
		if (unzipFolder.isDirectory()) {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(baos));

			// get a list of files from current directory
			File[] files = unzipFolder.listFiles();
			StringBuilder fileNameList =new StringBuilder();
			for (int i = 0; i < files.length; i++) {

				LOGGER.debug("Adding File...: " + files[i]);
				LOGGER.debug("createInMemoryZipFile for the file  ["+files[i].getName() +"]");
				fileNameList.append(files[i].getName());
				FileInputStream fis = new FileInputStream(files[i]);
				ZipEntry entry = new ZipEntry(files[i].getName());
				zout.putNextEntry(entry);

				int count;
				while ((count = fis.read(buffer)) != -1) {
					zout.write(buffer, 0, count);
				}

				zout.closeEntry();
				fis.close();
			}
			zout.close();
			inMemoryzipFile = baos.toByteArray();
		}
		
		
		

		LOGGER.debug("ZipUtility : createInMemoryZipFile() : END");
		return inMemoryzipFile;

	}
	/**
	 * 
	 * @param path: Path where the Zip File is created
	 * @param data: File Data
	 * @throws IOException
	 */
	public static void createLocalZipFile(String path,String fileName, InputStream fileContent) throws IOException {
		
		LOGGER.debug("ZipUtility : createLocalZipFile() : START at path :["+ path+"]");
		FileOutputStream out =null;
		
		out = new FileOutputStream(new File(path + File.separator
                + fileName));
		try {
			int read = 0;
	        final byte[] bytes = new byte[32768];

	        while ((read = fileContent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
			
			
		} catch (Exception e) {
			LOGGER.debug("ZipUtility : createLocalZipFile() : Exception ",e); 
		}finally{
			if(out!=null){
				out.close();
			}
			if(fileContent!=null){
				fileContent.close();
			}
		}
		
		LOGGER.debug("ZipUtility : createLocalZipFile() : END at path :["+ path+"]");
	}
}
