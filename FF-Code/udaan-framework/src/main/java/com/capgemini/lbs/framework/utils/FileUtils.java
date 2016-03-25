/* ****************************************************************
 * First Flight
 */
package com.capgemini.lbs.framework.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.filefilter.CanWriteFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.file.FileCopyingException;
import com.capgemini.lbs.framework.exception.file.FileDeletingException;
import com.capgemini.lbs.framework.exception.file.FolderCreationException;
import com.capgemini.lbs.framework.exception.file.FolderDeletingException;
import com.capgemini.lbs.framework.exception.file.FolderRenamingException;


/**
 * @author abarudwa
 *
 */
public final  class FileUtils
{
	private static final Logger LOGGER = Logger.getLogger(FileUtils.class);
	// Constants
	private final static int BUFFER_SIZE = 10240;
	public final static String FILE_PATH_PART_SEPARATOR = "/";
	public final static String FILE_NAME_PART_SEPARATOR = "_";
	public final static String PATH_SEPARATOR = "/";
	
	//File.separator
	//File.separatorChar
	public final static String FILENAME_EXTENSION_SEPARATOR = ".";
	
	// Extensions
	public final static String XML_EXTENSION = ".xml";	
	public final static String HTML_EXTENSION = ".html";		
	
	
	
    /**
     * @param folderPath
     * @param replaceIfExisting
     * @throws FolderDeletingException
     * @throws FolderCreationException
     */
    public static void createFolder(String folderPath, boolean replaceIfExisting) throws FolderDeletingException, FolderCreationException
    {
		// Recovery of the folder
	//	File folder = new File(folderPath);
    	if(StringUtil.isStringEmpty(folderPath)){
    		return;
    	}
    	File folder = null;
		try{
			folder = new File(folderPath);
		// Checking if the folder is existing and if it must be replaced 
		if((replaceIfExisting) && (folder.exists()))
		{
			if(!deleteFile(folder))
			{
			    throw new FolderDeletingException();
			}
		}
		
		// Creation of the folder if it is not existing
		if(!folder.exists())
		{
			if(!folder.mkdirs())
			{
				throw new FolderCreationException();
			}
		}
		}finally{
			 if(folder!=null){
			 folder=null;	
			 }
		}
    }
    
    
    /**
     * @param filePath
     * @param replaceIfExisting
     * @throws FileDeletingException
     * @throws IOException
     */
    public static void createFile(String filePath, boolean replaceIfExisting) throws FileDeletingException, IOException
    {
		// Recovery of the folder
		File file = null;
		try{
			 file = new File(filePath);
		// Checking if the folder is existing and if it must be replaced 
		if((replaceIfExisting) && (file.exists()))
		{
			if(!deleteFile(file))
			{
			    throw new FileDeletingException();
			}
		}
		
		// Creation of the folder if it is not existing
		if(!file.exists())
		{
			file.createNewFile();
		}
		}finally{
			if(file!=null){
				file=null;
			}
		}
    }

    
    /**
     * @param oldFolderPath
     * @param newFolderPath
     * @throws FolderRenamingException
     */
    public static void renameFolder(String oldFolderPath, String newFolderPath) throws FolderRenamingException
    {
    	LOGGER.debug("FileUtils ::renameFolder ---->  START");
    	LOGGER.debug("FileUtils ::renameFolder ---->Request  FROM :["+oldFolderPath+"]"+" TO : ["+newFolderPath+"]");
    	File oldFolder = new File(oldFolderPath);
    	File newFolder = new File(newFolderPath);
    	
    	if(oldFolder.exists())
    	{
    		if(!oldFolder.renameTo(newFolder))
    		{
    		    throw new FolderRenamingException();
    		}else{
    			LOGGER.debug("FileUtils ::renameFolder ---->Folder Renamed FROM :["+oldFolderPath+"]"+" TO : ["+newFolderPath+"]");
    		}
    	}
    	
    	LOGGER.debug("FileUtils ::renameFolder ---->  END");
    }
    
    
    
    /**
     * @param filePath
     * @param newExt
     * @return
     * @throws Exception
     */
    public static String changeFilePathExt(String filePath, String newExt) 
    {
        String newFilePath = filePath;
        
        if(filePath!=null)
        {
            int pos = filePath.lastIndexOf(FILENAME_EXTENSION_SEPARATOR);
            if(pos>-1 && pos<filePath.length())
            {
                newFilePath = filePath.substring(0, pos) + newExt;
            }
        }
            
        return newFilePath;
    }
    
   
    public static void moveFolder(String oldFolderPath, String newFolderPath) throws FolderDeletingException, IOException
    {
    	
    	File oldFolder = null;
    	// Recovery of the folders
    	try{
    	oldFolder = new File(oldFolderPath);
    	
    	if(oldFolder.exists())
    	{
    		copyFolder(oldFolderPath, newFolderPath);
    		deleteFolder(oldFolderPath);
    	}
    	}finally{
    		if(oldFolder!=null){
    			oldFolder =null;
    		}
    	}
    	
    }    
    
    
    /**
     * @param folderPath
     * @throws FolderDeletingException
     */
    public static void deleteFolder(String folderPath) throws FolderDeletingException
	{
    	// Recovery of the folder
    	File folder = null;
    	try{
    		 folder = new File(folderPath);
    	if(folder.exists())
    	{
    		if(!deleteFile(folder))
    		{
    		    throw new FolderDeletingException();
    		}
    	}
    	}finally{
    		if(folder!=null){
    			folder =null;
    		}
    	}
	}
    
 
    /**
     * @param filePath
     * @throws FileDeletingException
     */
    public static void deleteFile(String filePath) throws FileDeletingException
	{
    	// Recovery of the file
    	File file = null;
    	try{
    		 file = new File(filePath);
    	if(file.exists())
    	{
    		if(!file.delete())
    		{
    		    throw new FileDeletingException();
    		}
    	}
    	}finally{
    		if(file!=null){
    			file =null;
    		}
    	}
	}
    
   
    
    /**
     * @param srcFilePath
     * @param destFilePath
     * @throws FileCopyingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyFile(String srcFilePath, String destFilePath) throws FileCopyingException, FileNotFoundException, IOException
	{
    	File srcFile = null;
    	FileInputStream srcFileStream = null;
		FileOutputStream destFileStream = null;
		File targetLocation =null;
    	try{
    	srcFile = new File(srcFilePath);
    	if(srcFile.exists())
    	{
			int index = destFilePath.lastIndexOf(PATH_SEPARATOR);
			
			if(index == -1)
			{
				index = destFilePath.lastIndexOf(File.separator);
			}
			
			String destFileFolder = destFilePath.substring(0, index);
			
			if(!isFolderExisting(destFileFolder))
			{
			    targetLocation = new File(destFileFolder);
			    targetLocation.mkdirs();
			    targetLocation=null;
			}

			 srcFileStream = new FileInputStream(new File(srcFilePath));
			 destFileStream = new FileOutputStream(new File(destFilePath));
			
			try
			{
				byte[] buffer = new byte[BUFFER_SIZE];
				int readBytes = 0;
				
				while((readBytes = srcFileStream.read(buffer)) != -1)
				{
					destFileStream.write(buffer, 0, readBytes);
				}
			}
			finally
			{
				destFileStream.close();
				srcFileStream.close();
			}
    	}
    	else
    	{
    	    throw new FileCopyingException();
    	}
    	}finally{
    		if(srcFile!=null){
    			srcFile= null;	
    		}
    		if(destFileStream!=null){
    			destFileStream =null;	
    		}if(srcFileStream!=null){
    			srcFileStream =null;	
    		}
    		
    	}
	}
        
    
    
    /**
     * @param file
     * @return
     */
    private static boolean deleteFile(File file)
    {
    	boolean result = true;
    	File[] childFileList=null;
    	try{
    	if(file.isDirectory())
    	{
	    	 childFileList = file.listFiles();
	    	
	    	if(childFileList != null)
	    	{
		    	for(int i = 0 ; i < childFileList.length ; i++)
		    	{
		    		result &= deleteFile(childFileList[i]);
		    	}
	    	}
    	}
    	
    	result &= file.delete();
    	}finally{
    		if(file!=null){
    		file=null;	
    		}if(childFileList!=null){
    			childFileList=null;	
    		}
    	}
    	return result;
    }
        
    
    /**
     * @param filePath
     * @return
     */
    public static boolean isFileExisting(String filePath)
    {
    	File file = null;
    	try{
    	 file = new File(filePath);
    	return file.exists();
    	}finally{
    		if(file!=null){
    			file=null;	
    		}
    	}
    }
    
        
    /**
     * @param folderPath
     * @return
     */
    public static boolean isFolderExisting(String folderPath)
    {
		File folder = null;
		try{
			 folder = new File(folderPath);
			 return folder.exists();
		}finally{
			if(folder!=null){
			folder=null;	
			}
		}
		
    }
    
        
    /**
     * @param fileName
     * @return
     */
    public static long getSize(String fileName)
    {
    	long size = 0;
    	File file = new File(fileName);
    	
    	if(file.isDirectory())
    	{
    	    File[] files = file.listFiles();
    	    
    		for(int i = 0 ; i < files.length ; i++)
    		{
    			size += getSize(files[i].getAbsolutePath());
    		}
    	}
    	else
    	{
    		size = file.length();
    	}
    	
    	return size;
    }
    
    
    /**
     * @param fileName
     * @return
     */
    public static boolean isDirectoryEmpty(String fileName)
    {
    	boolean empty = true;
    	File file = new File(fileName);
    	
    	if(file.isDirectory())
    	{
    	    File[] files = file.listFiles();
    		int i = 0;
    	    while(i < files.length && empty)
    	    //for(int i = 0 ; i < files.length ; i++)
    		{
    		    empty = isDirectoryEmpty(files[i].getAbsolutePath());
    		    i++;
    		}
    	}
    	else
    	{
    		if(file.length()>0)
    		{
    		    return false;
    		}
    	}
    	
    	return empty;
    }
    
    
    /**
     * @param fileName
     * @return
     */
    public static long getRecursiveNbFiles(String fileName)
    {
    	long nbFiles = 0;
    	File file = new File(fileName);
    	
    	if(file.isDirectory())
    	{
    		File[] files = file.listFiles();

    		for(int i = 0 ; i < files.length ; i++)
    		{
    		    if(files[i].isFile())
    			{
    		    	nbFiles++;
    			}
    		    else if(files[i].isDirectory())
    		    {
    		        nbFiles += getRecursiveNbFiles(files[i].getAbsolutePath());
    		    }
    		}
    		
    		return nbFiles;
    	}   
    	
    	return nbFiles;
    }
        
    
    /**
     * @param fileName
     * @return
     */
    public static long getNbFiles(String fileName)
    {
    	long nbFiles = 0;
    	File file = new File(fileName);
    	
    	if(file.isDirectory())
    	{
    		File[] files = file.listFiles();

    		for(int i = 0 ; i < files.length ; i++)
    		{
    		    if(files[i].isFile())
    			{
    		    	nbFiles++;
    			}
    		}
    		
    		return nbFiles;
    	}    	
    	return nbFiles;
    }
    
        
   
    
        
        
    
	/**
	 * @param sourceLocation
	 * @param targetLocation
	 * @throws IOException
	 */
	private static void copyFolder(File sourceLocation, File targetLocation) throws IOException
	{
		File masterLocation=null;
		File childLocation=null;
		
		InputStream in = null;
		OutputStream out = null;
		
		try{
		//if(sourceLocation.isDirectory())
		if(sourceLocation!=null && sourceLocation.isDirectory())
		{
			if(!targetLocation.exists())
			{
				targetLocation.mkdirs();
			}
			
			String[] children = sourceLocation.list();
			
			for(int i = 0 ; i < children.length ; i++)
			{
				//copyFolder(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
				masterLocation = new File(sourceLocation, children[i]);
				childLocation=new File(targetLocation, children[i]);
				copyFolder(masterLocation,childLocation);
				if(masterLocation!=null){
					masterLocation=null;
		        }if(childLocation!=null){
		        	childLocation=null;
		        }
			}
		}
		else
		{
			 in = new FileInputStream(sourceLocation);
			 out = new FileOutputStream(targetLocation);
			
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len = 0;
			
			while((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
		}
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}	
		
		finally{
			  if(sourceLocation!=null){
		      	sourceLocation =null;	
		      }if(targetLocation!=null){
		      	targetLocation =null;	
		      }if(in!=null){
		      	in=null;
		      }if(out!=null){
		      	out=null;
		      }

		}
	}
		
	
	/**
	 * @param filePathPartList
	 * @return
	 */
	public static String createPath(String[] filePathPartList)
	{
		return createPath(filePathPartList, null, null);
	}
	
		
	/**
	 * @param filePathPartList
	 * @param fileNamePartList
	 * @param extension
	 * @return
	 */
	public static String createPath(String[] filePathPartList, String[] fileNamePartList, String extension)
	{
		StringBuffer path = new StringBuffer();
		
		if(filePathPartList != null)
		{
			for(int i = 0 ; i < filePathPartList.length ; i++)
			{
				if(i != 0)
				{
					path.append(FILE_PATH_PART_SEPARATOR);
				}
				
				path.append(filePathPartList[i]);
			}
		}
		
		if(fileNamePartList != null)
		{
			path.append(FILE_PATH_PART_SEPARATOR);
			
			for(int i = 0 ; i < fileNamePartList.length ; i++)
			{
				if(i != 0)
				{
					path.append(FILE_NAME_PART_SEPARATOR);
				}
				
				path.append(fileNamePartList[i]);
			}
		}
		
		if(extension != null)
		{
			if(!extension.startsWith(FILENAME_EXTENSION_SEPARATOR))
			{
				path.append(FILENAME_EXTENSION_SEPARATOR);
			}
			
			path.append(extension);
		}
		
		return path.toString();
	}
	
	
	/**
	 * @param content
	 * @param filename
	 * @throws IOException
	 */
	public static void saveAs(String content, String filename)
	throws IOException
	{
		File file = new File(filename);
	    FileWriter fileWriter = new FileWriter(file);
	    fileWriter.write(content);
	    fileWriter.close();		
	}
	
	/*public static void saveXmlAs(Document document, String filename)
	throws IOException
	{
		File file = new File(filename);
	    FileWriter fileWriter = new FileWriter(file);	   	    
	    XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	    out.output(document, fileWriter);
	    fileWriter.flush();
	    fileWriter.close();
	}
	
	public static void saveXmlAs(String xml, String filename)
	throws IOException
	{
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		String xmlUTF8 = null;
        try {
            xmlUTF8 = new String(xml.getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e1) {
        }
        StringReader sr = new StringReader(xmlUTF8.trim());
		try {
			// Create a new DOM document
			document = sxb.build(sr);
		} catch(Exception e){
		}
		saveXmlAs(document, filename);
	}
*/
	
	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String fileToString(String filename)
	throws IOException
	{
		File file = new File(filename);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		StringBuffer result = new StringBuffer();
		String line = null;
		while ((line = in.readLine()) != null)
			result.append(line);
		in.close();
		fileReader.close();
		file = null;
		return result.toString();
	}
	
	
	/**
	 * @param path
	 * @throws FolderDeletingException 
	 */
	public static void createDirectory(String path) {
		if (!StringUtil.isStringEmpty(path))
		{
			File folder = new File(path);
			// Creation of the folder if it is not existing
			if(!folder.exists())
				folder.mkdirs();
		}/*else{
		    throw new FolderDeletingException();
		
		}*/
	}
	
	
	/**
	 * @param dirPath
	 * @param ext
	 * @return
	 */
	public static String[] getFileWithExtension(String dirPath, String ext)
	{
		if (!StringUtil.isStringEmpty(dirPath))
		{
			File dir = new File(dirPath);
			if (dir.isDirectory())
			{
				class ExtensionFilter implements FilenameFilter {
				    private String extension;  
				    public ExtensionFilter(String extension) {
				        this.extension = extension;
				    }
				    public boolean accept(File dir, String name) {
				        return (name.endsWith(extension));
				    }
				}
				ExtensionFilter filter = new ExtensionFilter(ext);
				return dir.list(filter);
			}
		}
		return null;
	}

		
    /**
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copyFolder(String source, String target)
    throws IOException
    {
        File sourceLocation = new File(source);
        File targetLocation = new File(target);

        copyFolder(sourceLocation, targetLocation);
       
        if(sourceLocation!=null){
        	sourceLocation=null;
        }if(targetLocation!=null){
        	targetLocation=null;
        }
    }

	
    /**
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) 
    {
        if (dir.isDirectory()) 
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) 
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    System.err.println("Unable to delete "+dir.getAbsolutePath());
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }
    
    
    /**
     * @param dir
     * @return
     */
    public static boolean isEmptyDir(File dir)
    {
    	if (dir.isDirectory()) 
        {
    		String[] children = dir.list();
    		return (children == null || children.length == 0);
        }
    	return false;
    }
    
    /**
     * @param folderPath
     * @return
     */
    public static boolean isEmptyDir(String folderPath)
    {
    	File dir = new File(folderPath);
    	return isEmptyDir(dir);
    }
    
    /**
	 * set the file-pointer offset nbLines before the end of the file.
	 * @param randomFile
	 * @param nbLines
	 * @return long : position of the file-pointer offset nbLines before the end of the file.
	 * @throws IOException
	 */
	public static long positionNLignesBeforeEndOfFile(RandomAccessFile randomFile, int nbLines)throws IOException{
		if(randomFile==null ){
			return -1;
		}
		long position=randomFile.length();
		randomFile.seek(position);
		
		byte code;
		
		 while ((--position > 0) && (nbLines >= 0)) {
		 	randomFile.seek(position);
            code = randomFile.readByte();
            
            if ((code == 13) || (code == 10)) {
            	    position--;
            	    nbLines--;
            } 
		 }
		 return position;
	}
	
	/**
	 * 
	 * @param randomFile
	 * @param chaineToBeFind
	 * @return true if the chaineToBeFind is find in the file
	 * @throws IOException
	 */
	public static boolean SearchInTheFile(RandomAccessFile randomFile, String chaineToBeFind)throws IOException{
	
	int index=-1;
	if(randomFile==null){
		return false;
	}
	String sLine=randomFile.readLine();
	while(sLine!=null&& index<0){
	
	index=sLine.indexOf(chaineToBeFind);
	sLine=randomFile.readLine();
	}
	return index>-1;
	}
	
	/**
	 * @return
	 */
	public static IOFileFilter bcunFileFilter() {
		IOFileFilter filterList= FileFilterUtils.and(CanWriteFileFilter.CAN_WRITE, new SizeFileFilter(1),new SuffixFileFilter(XML_EXTENSION));
		return filterList;
	}
	public static IOFileFilter bcunInboundFileFilter() {
		IOFileFilter filterList = bcunFileFilterByType(FrameworkConstants.BCUN_FILE_IDENTIFIER_INBOUND);
		return filterList;
	}
	public static IOFileFilter bcunInboundFileUploadFilter() {
		IOFileFilter filterList = bcunFileFilterByType(FrameworkConstants.BCUN_FILE_IDENTIFIER_INBOUND_KEYWORD);
		return filterList;
	}


	/**
	 * @return
	 */
	private static IOFileFilter bcunFileFilterByType(String bcunType) {
		IOFileFilter filterList=null;
		if(!StringUtil.isStringEmpty(bcunType)){
		 filterList= FileFilterUtils.and(CanWriteFileFilter.CAN_WRITE, new SizeFileFilter(1),new SuffixFileFilter(XML_EXTENSION),new WildcardFileFilter("*"+bcunType+"*"));
		}else{
			filterList=bcunFileFilter();
		}
		return filterList;
	}
	public static IOFileFilter bcunOutboundFileFilter() {
		IOFileFilter filterList = bcunFileFilterByType(FrameworkConstants.BCUN_FILE_IDENTIFIER_OUTBOUND);
		return filterList;
	}
	public static IOFileFilter bcunFileFilter(String extn) {
		IOFileFilter filterList= FileFilterUtils.and(CanWriteFileFilter.CAN_WRITE, new SizeFileFilter(1),new SuffixFileFilter(extn));
		return filterList;
	}
	public static IOFileFilter bcunCleanFilter() {
		IOFileFilter xmlFilterList= FileFilterUtils.and(CanWriteFileFilter.CAN_WRITE, new SizeFileFilter(1),new PrefixFileFilter(FrameworkConstants.DIR_PROCESSED),new SuffixFileFilter(XML_EXTENSION));
		IOFileFilter zipFilterList= FileFilterUtils.and(new PrefixFileFilter(FrameworkConstants.DIR_PROCESSED),new SuffixFileFilter(FrameworkConstants.ZIP_EXTENSION));
		IOFileFilter filterList=FileFilterUtils.or(xmlFilterList,zipFilterList);
		return filterList;
	}
	
	public static long getTimestampFromFileName(String fileName) {
		int timeStampindex=fileName.lastIndexOf("-");
		int xmlExt=fileName.lastIndexOf(".xml");
		String milliseconds=fileName.substring(timeStampindex+1, xmlExt);
		return StringUtil.convertStringToLong(milliseconds);
	}
	
	public static long getFileCreatedDateTime(String filePath){
		long milliseconds=0;
		File file = new File(filePath);
		BasicFileAttributes attrs=null;
		try {
			attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			LOGGER.error("FileUtils::fileCreatedDateTime::IOException", e);
		}       
       
		if(attrs!=null){
			milliseconds= attrs.creationTime().toMillis();
		}
		return milliseconds;
	}
	public static long getFileLastModifiedDateTime(String filePath){
		long milliseconds=0;
		File file = new File(filePath);
		BasicFileAttributes attrs=null;
		try {
			attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			LOGGER.error("FileUtils::getFileLastModifiedDateTime::IOException", e);
		}       
       
		if(attrs!=null){
			milliseconds= attrs.lastModifiedTime().toMillis();
		}
		return milliseconds;
	}
	public static Integer getTotalNoOfFiles(File baseDir){
		Integer totalFilesSize=0;
		try {
			 totalFilesSize= baseDir.list().length;
		} catch (Exception e) {
			LOGGER.error("FileUtils::getTotalNoOfFiles::Exception", e);
		}       
       
		
		return totalFilesSize;
	}
	public static String[] getAllFilesNames(String location,String filter) {
		String[] fileNames = null;
		//Checking the location is empty or not
		if(!StringUtil.isStringEmpty(location)) {
			//Creating a base directory of provided location
			File baseDir = new File(location);
			if(baseDir.isDirectory()) {
				fileNames = baseDir.list(bcunFileFilterByType(filter));
			}
		}
		return fileNames;
	}
	
	/**
	 * @param args
	 */
	/*public static void main(String[] args)
    {
        try
        {
           // FileUtils.copyFolder("C:/temp/Test Int", "C:/temp/copy");
            FileUtils.createFolder("D:/temp/Test Int", false);
        }
        catch (FolderDeletingException | FolderCreationException e) {
		}
        catch (IOException e)
        {
        }
    }*/
}