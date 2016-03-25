/**
 * 
 */
package com.cg.lbs.bcun.utility;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *
 */
public final class InboundBranchIntializerHelper {

	/**
	 *  Log the message of the process.
	 */
	private static Logger logger = LoggerFactory.getLogger(InboundBranchIntializerHelper.class);



	/**
	 * BCUN configuration properties. injected via dependency injection.
	 */
	 static Properties bcunProperties;

	/**
	 * @return the bcunProperties
	 */
	public Properties getBcunProperties() {
		return bcunProperties;
	}

	/**
	 * @param bcunProperties the bcunProperties to set
	 */
	public void setBcunProperties(Properties bcunProperties) {
		this.bcunProperties = bcunProperties;
	}

	public static void branchSetUp(){
		branchFolderCreator();
		branchFolderPermissionInitializer();

	}
	public static void branchFolderCreator(){

		File inboundBranch=null;
		try {
			inboundBranch=getBranchInboundFolder();
			if(inboundBranch!=null && !inboundBranch.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for getBranchInboundFolder:: status "+inboundBranch.mkdirs()); 
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator::### EXCEPTION ##### Folder creation for getBranchInboundFolder:: for the location ["+inboundBranch !=null?inboundBranch.getPath():null,e );
		}



		File inboundBranchPr=null;
		try {
			inboundBranchPr=getBranchInboundPrFolder();
			if(inboundBranchPr!=null && !inboundBranchPr.exists()){
				logger.info("InboundBranchIntializerHelper ::branchFolderCreator::Folder creation for getBranchInboundPrFolder:: Status "+inboundBranchPr.mkdirs() );
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator::### EXCEPTION ##### Folder creation for getBranchInboundPrFolder:: for the location ["+inboundBranchPr !=null?inboundBranchPr.getPath():null,e );
		}
		File outboundBranch=null;
		try {
			outboundBranch=getBranchOutboundFolder();
			if(outboundBranch!=null && !outboundBranch.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for getBranchOutboundFolder:: Status"+outboundBranch.mkdirs());
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator::### EXCEPTION ##### Folder creation for getBranchOutboundFolder:: for the location ["+outboundBranch !=null?outboundBranch.getPath():null,e );
		}
		File outboundBranchPr=null;
		try {
			outboundBranchPr=getBranchOutboundPrFolder();
			if(outboundBranchPr!=null && !outboundBranchPr.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for outboundBranchPr:: for the location  status :"+outboundBranchPr.mkdirs() );
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator:: ### EXCEPTION ##### Folder creation for outboundBranchPr:: for the location ["+outboundBranchPr !=null?outboundBranchPr.getPath():null,e );
		}
		
		File outboundBranchTemp=null;
		try {
			outboundBranchTemp=getBranchOutboundTempFolder();
			if(outboundBranchTemp!=null && !outboundBranchTemp.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for outboundBranchTemp:: for the location  status :"+outboundBranchTemp.mkdirs() );
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator:: ### EXCEPTION ##### Folder creation for outboundBranchTemp:: for the location ["+outboundBranchTemp !=null?outboundBranchTemp.getPath():null,e );
		}
		File outboundBranchError=null;
		try {
			outboundBranchError=getBranchOutboundErrorFolder();
			if(outboundBranchError!=null && !outboundBranchError.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for outboundBranchError:: for the location  status :"+outboundBranchError.mkdirs() );
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator:: ### EXCEPTION ##### Folder creation for outboundBranchError:: for the location ["+outboundBranchError !=null?outboundBranchPr.getPath():null,e );
		}

		File processedInboundBranch=null;
		try {
			processedInboundBranch=getBranchInboundCleanupFolderLocation();
			if(processedInboundBranch!=null && !processedInboundBranch.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for processedInboundBranch:: Status :"+processedInboundBranch.mkdirs());
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator::### EXCEPTION ##### Folder creation for processedInboundBranch:: for the location ["+processedInboundBranch !=null?processedInboundBranch.getPath():null,e );
		}
		File processedOutboundBranch=null;
		try {
			processedOutboundBranch=getBranchOutboundCleanupFolderLocation();
			if(processedOutboundBranch!=null && !processedOutboundBranch.exists()){
				logger.info("InboundBranchIntializerHelper :: branchFolderCreator::Folder creation for processedOutboundBranch:: status :"+processedOutboundBranch.mkdirs());
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderCreator:: ### EXCEPTION ##### Folder creation for processedOutboundBranch:: for the location ["+processedOutboundBranch !=null?processedOutboundBranch.getPath():null,e );
		}
	}
	public static void branchFolderPermissionInitializer(){
		logger.info("InboundBranchIntializerHelper :: branchFolderPermission::Folder Permission## START###");
		File inboundBranchPr=null;
		try {

			inboundBranchPr= new File(getBranchInboundFileLocation());
			if(inboundBranchPr!=null && inboundBranchPr.exists()){
				String cmd="chmod -R 776 "+inboundBranchPr.getParent();
				logger.info("InboundBranchIntializerHelper :: branchFolderPermission::Folder Permission## Command ##"+cmd);
				new ProcessCreator(cmd).start();
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderPermission::### EXCEPTION ##### Folder Permission for the location ["+inboundBranchPr !=null?inboundBranchPr.getParent():null,e );
		}
		File processedLocation=null;
		try {

			processedLocation= new File(getBranchCleanupBaseFolderLocation());
			if(processedLocation!=null && processedLocation.exists()){
				String cmd="chmod -R 776 "+processedLocation;
				logger.info("InboundBranchIntializerHelper :: branchFolderPermission::Folder Permission (for file cleaner)## Command ##"+cmd);
				new ProcessCreator(cmd).start();
			}
		} catch (Exception e) {
			logger.error("InboundBranchIntializerHelper :: branchFolderPermission::### EXCEPTION ##### Folder Permission for the location ["+processedLocation !=null?processedLocation.getPath():null,e );
		}
		logger.info("InboundBranchIntializerHelper :: branchFolderPermission::Folder Permission## END###");
	}


	public static File getBranchInboundPrFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchInboundFileLocation();
			fileLocation=fileLocation+File.separator+"pr";
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchInboundFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchInboundFileLocation();
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchOutboundPrFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchOutboundFileLocation();
			fileLocation=fileLocation+File.separator+"pr";
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchOutboundTempFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchOutboundFileLocation();
			fileLocation=fileLocation+File.separator+"temp";
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchOutboundErrorFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchOutboundFileLocation();
			fileLocation=fileLocation+File.separator+"er";
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchOutboundFolder(){
		String fileLocation=null;
		if(bcunProperties!=null && !bcunProperties.isEmpty()){
			fileLocation = getBranchOutboundFileLocation();
		}
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}



	private static String getBranchInboundFileLocation() {
		String fileLocation;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.branch.inbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.branch.inbound.file.location");
		}
		return fileLocation;
	}
	private static String getBranchOutboundFileLocation() {
		String fileLocation;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.branch.outbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.branch.outbound.file.location");
		}
		return fileLocation;
	}



	private static String getBranchCleanupBaseFolderLocation() {
		String fileLocation;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.processed");
		} else {
			fileLocation = bcunProperties.getProperty("linux.processed");
		}
		return fileLocation;
	}
	public static File getBranchInboundCleanupFolderLocation() {
		String fileLocation=getBranchCleanupBaseFolderLocation();
		String inboundPrfileLocation=getBranchInboundPrFolder().getPath();
		if(inboundPrfileLocation.contains(FrameworkConstants.CHARACTER_COLON)){
			String location[]=inboundPrfileLocation.split(FrameworkConstants.CHARACTER_COLON);
			if(!StringUtil.isEmpty(location) && location.length==2){
				fileLocation =fileLocation+location[1];
			}
		}else{
			fileLocation=fileLocation+getBranchInboundPrFolder();
		}
		logger.debug("InboundBranchIntializerHelper :: getBranchInboundCleanupFolderLocation ::fileLocation : "+fileLocation);
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}
	public static File getBranchOutboundCleanupFolderLocation() {
		String fileLocation=getBranchCleanupBaseFolderLocation();
		String outboundPrfileLocation=getBranchOutboundPrFolder().getPath();

		if(outboundPrfileLocation.contains(FrameworkConstants.CHARACTER_COLON)){
			String location[]=outboundPrfileLocation.split(FrameworkConstants.CHARACTER_COLON);
			if(!StringUtil.isEmpty(location) && location.length==2){
				fileLocation =fileLocation+location[1];
			}
		}else{
			fileLocation= getBranchCleanupBaseFolderLocation()+getBranchOutboundPrFolder();
		}
		logger.debug("InboundBranchIntializerHelper :: getBranchOutboundCleanupFolderLocation ::fileLocation : "+fileLocation);
		return !StringUtil.isStringEmpty(fileLocation)?new File(fileLocation):null;
	}

	public static class ProcessCreator extends Thread{
		String command;
		public ProcessCreator(){

		}
		public ProcessCreator(String command){
			this.command=command;

		}

		public void run(){
			try {
				Runtime.getRuntime().exec(command);
			} catch (Exception e) {
				logger.error("InboundBranchIntializerHelper --->ProcessCreator :: ### EXCEPTION ##### processCreator executing command : "+command,e );
			}
		}
	}

}
