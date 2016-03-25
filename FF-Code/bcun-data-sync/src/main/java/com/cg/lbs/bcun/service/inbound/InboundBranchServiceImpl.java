package com.cg.lbs.bcun.service.inbound;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.AbstractBcunDatasyncServiceImpl;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.utility.InboundPropertyReader;

/**
 * @author mohammal
 * Jan 15, 2013
 * Provides in bound specific implementation of abstract function of BCUNService
 * 
 */
public class InboundBranchServiceImpl extends AbstractBcunDatasyncServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundBranchServiceImpl.class);
	
	@Override
	public String getBaseFileLocation() {
		
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.branch.inbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.branch.inbound.file.location");
		}
		FileUtils.createDirectory(fileLocation);
		LOGGER.trace("InboundBranchServiceImpl::getProcessFileLocation::configured in boud location is: " + fileLocation);
		return fileLocation;
	}

	/*@Override
	public String getErrorFileLocation() {
		// linux.central.inbound.error.file.location
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.central.inbound.error.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.central.inbound.error.file.location");
		}
		return fileLocation;
	}
	*/
	@Override
	public String getModeOfOpration() {
		// bcun.operation.mode
		return bcunProperties.getProperty("bcun.operation.mode");
	}

	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps() {
		return InboundPropertyReader.getInboundConfigProperty();
	}
	
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps(
			String processName) {
		return InboundPropertyReader.getInBoundPropertyListByProcess(processName);
	}

	@Override
	public void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			saveOrUpdateTransferedEntity(baseDO);
		}
	}

	@Override
	public String prepareInboundBranchData() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("InboundBranchServiceImpl :: prepareInboundBranchData :: START" );
		String baseLocation=getBaseFileLocation();
		List<String> fileList=null;
		//step 1: prepare .zip file
		//step2 : move files to PR folder 
		//step3 : move .zip file to manual Extraction folder.
		//step 4: return .Zip location
		String currentSysTime=DateUtil.getCurrentTimeInString();
		File bcunFile=new File(baseLocation);
		String rootFolder=bcunFile.getParent();

		String zipFileLocation=rootFolder+File.separator+"manual"+File.separator+"Inbound"+currentSysTime+".zip";

		try {
			fileList=createZipFileForManualProcess(baseLocation,zipFileLocation);
			
			if(CGCollectionUtils.isEmpty(fileList)){
				ExceptionUtil.prepareBusinessException(GlobalErrorCodeConstants.BUSINESS_ERROR_INBOUND);
			}
			for(String tobeMoved:fileList){
				try {
					renameFileByManual(tobeMoved, baseLocation, "pr");
				} catch (Exception e) {
					LOGGER.error("InboundBranchServiceImpl :: prepareInboundBranchData :: Exception for File renaming (ignore)",e);
					continue;
				}
			}
				
			
		}catch (CGBusinessException cgb){
			LOGGER.error("InboundBranchServiceImpl :: prepareInboundBranchData ::CGBusiness Exception  ",cgb);
			throw cgb;
		}catch (Exception e){
			LOGGER.error("InboundBranchServiceImpl :: prepareInboundBranchData :: Exception  ",e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("InboundBranchServiceImpl :: prepareInboundBranchData :: ENDs with Zip file Location :[" +zipFileLocation+"]");
		return zipFileLocation;
	}

	
	public  List<String> createZipFileForManualProcess(String baseLocation,String fileName)throws IOException,CGBusinessException {
		String[] files =null;
		LOGGER.debug("ZipUtility : createZipFile() : START");
		List<String> fileList=null;
		int BUFFER = 802400;
		byte buffer[] = new byte[BUFFER];

		// Check to see if the directory exists
		File unzipFolder = new File(baseLocation);
		if (unzipFolder.isDirectory()) {
			boolean directoryStatus=true;
			File file= new File(fileName);
			File fileParent= new File(file.getParent());
			if(!fileParent.exists()){
				directoryStatus=fileParent.mkdirs();
			}
			if(directoryStatus){
				FileOutputStream fouts =null;
				ZipOutputStream zout=null;
				 try {
					// get a list of files from current directory
						files = getAllFilesNames(baseLocation);
						
						if(StringUtil.isEmpty(files)){
							return null;
						}else{
							fileList=new ArrayList<String>(files.length);
							 LOGGER.info(" Total No of files to be processed: " + files.length +" & Total no of Available Files :" +com.capgemini.lbs.framework.utils.FileUtils.getTotalNoOfFiles(new File(baseLocation)));
						}
						
					fouts = new FileOutputStream(file);
					 zout = new ZipOutputStream(
							new BufferedOutputStream(fouts));

					
					//StringBuilder fileNameList =new StringBuilder();
					 for (String fileNameStr :files) {
						 File fileNameToProcess=new File(baseLocation+File.separator+fileNameStr);
						 if(fileNameToProcess.isDirectory()){
							 continue;
						 }
						 LOGGER.debug("Adding File...: " + fileNameStr);
						 LOGGER.debug("createZipFile for the file  ["+fileNameStr +"]");
						 //fileNameList.append(files[i].getName());
						 FileInputStream fis=null;
						 try {
							 fis = new FileInputStream(fileNameToProcess);
						 } catch (Exception e) {
							 LOGGER.error("createZipFile for the file & continuing manual process ",e);
							 continue;
						 }
						 fileList.add(fileNameStr);
						 ZipEntry entry = new ZipEntry(fileNameStr);
						 zout.putNextEntry(entry);
						 int count;
						 while ((count = fis.read(buffer)) != -1) {
							 zout.write(buffer, 0, count);
						 }

						 zout.closeEntry();
						 fis.close();
					 }
				}finally{
					if(zout!=null){
					zout.close();
					}
					if(fouts!=null){
					fouts.close();
					}
				}
				

			}
		}else{
			ExceptionUtil.prepareBusinessException(GlobalErrorCodeConstants.BUSIENSS_VIOLATION_FILE_ERROR);
		}
		LOGGER.debug("ZipUtility : createZipFile() : END");
		return fileList;

	}
	
	

}
