/**
 * 
 */
package com.capgemini.lbs.framework.utils;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * @author mohammes
 *
 */
public final class FrameworkResourceLoaderUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FrameworkResourceLoaderUtil.class);
	static ResourceBundle universalErrorMsgs = null;
	static ResourceBundle frameworkErrorMsgs = null;
	static ResourceBundle rateErrorMsgs = null;

	static{
		LOGGER.debug("FrameworkResourceLoaderUtil ::: Executing static Block");
		loadProperties();
	}

	/**
	 * 
	 */
	private static void loadProperties() {
		loadUniversalMessages();
		loadSystemMessages();
		loadRateMessages();
	}

	/**
	 * 
	 */
	private static void loadSystemMessages() {
		try {
			frameworkErrorMsgs=ResourceBundle
					.getBundle(FrameworkConstants.FRAMEWORK_MSG_PROP_FILE_NAME);
		} catch (Exception e) {
			LOGGER.error("FrameworkResourceLoaderUtil :::loadProperties  :: SystemErrorproperty Files  ::-->Exception:",e);		
		}
		LOGGER.debug("FrameworkResourceLoaderUtil :::loadProperties  ::properties (Error proprty files) ::-->"+frameworkErrorMsgs!=null?"Loaded":"not loaded");
	}
	
	/**
	 * 
	 */
	private static void loadRateMessages() {
		try {
			rateErrorMsgs=ResourceBundle
					.getBundle(FrameworkConstants.RATE_MSG_PROP_FILE_NAME);
		} catch (Exception e) {
			LOGGER.error("FrameworkResourceLoaderUtil :::loadProperties  :: rateErrorMsgs Files  ::-->Exception:",e);		
		}
		LOGGER.debug("FrameworkResourceLoaderUtil :::loadProperties  ::rateErrorMsgs (Error proprty files) ::-->"+frameworkErrorMsgs!=null?"Loaded":"not loaded");
	}

	/**
	 * 
	 */
	private static void loadUniversalMessages() {
		try {
			universalErrorMsgs = ResourceBundle
					.getBundle(FrameworkConstants.UNIVERALS_MSG_PROP_FILE_NAME);
		} catch (Exception e) {
			LOGGER.error("FrameworkResourceLoaderUtil :::loadProperties  :: universalproperties  ::-->Exception:",e);
		}
		LOGGER.debug("FrameworkResourceLoaderUtil :::loadProperties  ::properties Status(universal) ::-->"+universalErrorMsgs!=null?"Loaded":"not loaded");
	}

	
	
	/**
	 * @return the universalErrorMsgs
	 */
	public static ResourceBundle getUniversalErrorMsgs() {
		return universalErrorMsgs;
	}

	/**
	 * @return the frameworkErrorMsgs
	 */
	public static ResourceBundle getFrameworkErrorMsgs() {
		return frameworkErrorMsgs;
	}

	/**
	 * @return the rateErrorMsgs
	 */
	public static ResourceBundle getRateErrorMsgs() {
		return rateErrorMsgs;
	}

	/**
	 * @param rateErrorMsgs the rateErrorMsgs to set
	 */
	public static void setRateErrorMsgs(ResourceBundle rateErrorMsgs) {
		FrameworkResourceLoaderUtil.rateErrorMsgs = rateErrorMsgs;
	}

	/**
	 * @param universalErrorMsgs the universalErrorMsgs to set
	 */
	public static void setUniversalErrorMsgs(ResourceBundle universalErrorMsgs) {
		FrameworkResourceLoaderUtil.universalErrorMsgs = universalErrorMsgs;
	}

	/**
	 * @param frameworkErrorMsgs the frameworkErrorMsgs to set
	 */
	public static void setFrameworkErrorMsgs(ResourceBundle frameworkErrorMsgs) {
		FrameworkResourceLoaderUtil.frameworkErrorMsgs = frameworkErrorMsgs;
	}

	public static String getAsString(final String keyParam){
		String value=null;
		LOGGER.debug("FrameworkResourceLoaderUtil ::: Executing getAsString");
		if(frameworkErrorMsgs!=null){
			if(frameworkErrorMsgs.containsKey(keyParam)){
				LOGGER.debug("FrameworkResourceLoaderUtil ::FOR frameworkErrorMsgs:: Executing getAsString:: Contains Key "+keyParam);
				value= frameworkErrorMsgs.getString(keyParam);
				
			}
		} 
		if(universalErrorMsgs!=null && StringUtil.isStringEmpty(value)){
			if(universalErrorMsgs.containsKey(keyParam)){
				LOGGER.debug("FrameworkResourceLoaderUtil ::universalErrorMsgs:: Executing getAsString:: Contains Key "+keyParam);
				value= universalErrorMsgs.getString(keyParam);
				
			}
		} 
		if(rateErrorMsgs!=null && StringUtil.isStringEmpty(value)){
			if(rateErrorMsgs.containsKey(keyParam)){
				LOGGER.debug("FrameworkResourceLoaderUtil ::rateErrorMsgs:: Executing getAsString:: Contains Key "+keyParam);
				value= rateErrorMsgs.getString(keyParam);
				
			}
		}
		LOGGER.trace("FrameworkResourceLoaderUtil ::: Executing getAsString:: For Key:[ "+keyParam +" ] value:["+value+"]");
		return value;
	}
	
}
