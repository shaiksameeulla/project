/**
 * 
 */
package com.cg.lbs.bcun.service.common;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *  This class uses   the property window.cleanup which is in Bcun.properties
 *  Basically this scheduler moved processed file to one common Folder which is configured in Bcun.properties
 *
 */
public class BcunCleanupFileProcessor {
	/**
	 *  Log the message of the process.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(BcunCleanupFileProcessor.class);

	/**
	 * BCUN configuration properties. injected via dependency injection.
	 */
	protected Properties bcunProperties;

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

	public String getCleanupLocation() {

		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.cleanup");
		} else {
			fileLocation = bcunProperties.getProperty("linux.cleanup");
		}

		LOGGER.debug("BcunCleanupFileProcessor::getCleanupLocation::configured in Bcun.prop : " + fileLocation);
		return fileLocation;
	}

	public String getNewProcessedLocation() {

		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.processed");
		} else {
			fileLocation = bcunProperties.getProperty("linux.processed");
		}

		LOGGER.debug("BcunCleanupFileProcessor::getNewProcessedLocation::configured in Bcun.prop : " + fileLocation);
		return fileLocation;
	}



	public void cleanupProcess() throws HttpException, ClassNotFoundException, IOException {
		String cleanupLocation=getCleanupLocation();
		String processedRoot=getNewProcessedLocation();
		LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess:: START");

		StringTokenizer  cleanupFolders=new StringTokenizer(getCleanupLocation(),CommonConstants.CHARACTER_SEMI_COLON);
		while(cleanupFolders.hasMoreElements()){
			String oldPrLocation=cleanupFolders.nextToken();
			LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess:: Processing with file Location :["+oldPrLocation+"]");
			String newLocation=null;
			if(ApplicatonUtils.isWindowsOS()){
				String location[]=oldPrLocation.split(FrameworkConstants.CHARACTER_COLON);
				if(!StringUtil.isEmpty(location) && location.length==2){
					newLocation =location[0]+FrameworkConstants.CHARACTER_COLON+processedRoot+location[1];
				}
			}else{
				newLocation=getNewProcessedLocation()+oldPrLocation;
			}
			if(!StringUtil.isStringEmpty(newLocation)){
				newLocation = newLocation +File.separator+DateUtil.getDDMMYYYYDateString(DateUtil.getCurrentDate(), FrameworkConstants.YYYY_MM_DD_FORMAT);
			}
			LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess:: processed Files will move to :["+newLocation+"]");
			try {
				//File[] processedFiles=CGXMLUtil.retrieveMatchFileNames(oldPrLocation, BcunConstant.FLAG_PROCESSED, FrameworkConstants.XML_EXTENSION);
				String[] processedFiles=null;
				File baseDir = new File(oldPrLocation);
				if(baseDir.isDirectory()) {
					processedFiles = baseDir.list(com.capgemini.lbs.framework.utils.FileUtils.bcunCleanFilter());
				}
				if(!StringUtil.isEmpty(processedFiles)){
				for(String processedFile:processedFiles){
					try {
						boolean moved=CGXMLUtil.moveFile(newLocation, new File(oldPrLocation+File.separator+processedFile));
						LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess::STATUS:"+moved);
						LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess:: Moved file Name  :["+processedFile+"]: to the location["+newLocation+"]");
					} catch (Exception e) {
						LOGGER.error("BcunCleanupFileProcessor::cleanupProcess:: error Occured while moving file  :["+processedFile+"]: to the location["+newLocation+"] :: Exception...",e);
					}
				}
				
				}

			} catch (Exception e) {
				LOGGER.error("BcunCleanupFileProcessor::cleanupProcess::while processing the Dir :["+oldPrLocation+"] :: Exception...",e);
			}
		}
		LOGGER.debug("BcunCleanupFileProcessor::cleanupProcess:: END");

	}

}
