/**
 * 
 */
package com.ff.universe.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * @author mohammes
 *
 */
public final class ResourceLoaderUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ResourceLoaderUtil.class);
	static ResourceBundle errorMessages = null;

	static{
		LOGGER.debug("ResourceLoaderUtil ::: Executing static Block");
		loadProperties();
	}

	/**
	 * 
	 */
	private static void loadProperties() {
		errorMessages = ResourceBundle
				.getBundle(FrameworkConstants.UNIVERALS_MSG_PROP_FILE_NAME);
		LOGGER.debug("ResourceLoaderUtil :::loadProperties  ::properties Status ::-->"+errorMessages!=null?"Loaded":"not loaded");
	}

	/**
	 * @return the errorMessages
	 */
	public static ResourceBundle getErrorMessages() {
		if(errorMessages ==null){
			loadProperties();
		}
		return errorMessages;
	}

	/**
	 * @param errorMessages the errorMessages to set
	 */
	public static void setErrorMessages(ResourceBundle errorMessages) {
		ResourceLoaderUtil.errorMessages = errorMessages;
	}
	
	public static String getAsString(final String keyParam){
		String value=null;
		LOGGER.debug("ResourceLoaderUtil ::: Executing getAsString");
		if(errorMessages!=null){
			if(errorMessages.containsKey(keyParam)){
				LOGGER.debug("ResourceLoaderUtil ::: Executing getAsString:: Contains Key "+keyParam);
				value= errorMessages.getString(keyParam);
				
			}
		}
		LOGGER.trace("ResourceLoaderUtil ::: Executing getAsString:: For Key:[ "+keyParam +" ] value:["+value+"]");
		return value;
	}
	
}
