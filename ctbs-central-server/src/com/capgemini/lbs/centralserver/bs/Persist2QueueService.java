package com.capgemini.lbs.centralserver.bs;

import java.io.File;
import java.io.IOException;

import net.sf.json.JSONObject;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface Persist2QueueService.
 */
@Deprecated
public interface Persist2QueueService {
	
	/**
	 * Gets the files.
	 *
	 * @param path the path
	 * @return the files
	 */
	public String[] getFiles(String path);
	
	/**
	 * Gets the json from file.
	 *
	 * @param file the file
	 * @return the json from file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JSONObject getJsonFromFile(File file) throws IOException;
	
	/**
	 * Gets the base to from json.
	 *
	 * @param jsonObject the json object
	 * @return the base to from json
	 * @throws Exception the exception
	 */
	public CGBaseTO getBaseTOFromJson(JSONObject jsonObject) throws CGSystemException, CGBusinessException;
	
	/**
	 * Write2 queue.
	 *
	 * @param baseTO the base to
	 * @return true, if successful
	 */
	public boolean write2Queue(CGBaseTO baseTO);
	
	/**
	 * Handle persist process.
	 *
	 * @param activeFileLocation the active file location
	 * @throws Exception the exception
	 */
	public void handlePersistProcess(String activeFileLocation) throws CGSystemException, CGBusinessException; 
	
}
