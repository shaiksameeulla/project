
package com.capgemini.lbs.framework.bs;

import java.io.File;
import java.io.IOException;

import net.sf.json.JSONObject;

import com.capgemini.lbs.framework.to.CGBaseTO;
/**
 * @author anwar
 * 
 */
public interface Persist2QueueService {
	/**
	 * @param path
	 * @return
	 */
	public String[] getFiles(String path);
	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public JSONObject getJsonFromFile(File file) throws IOException;
	
	/**
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	public CGBaseTO getBaseTOFromJson(JSONObject jsonObject);
	
	/**
	 * @param baseTO
	 * @return
	 */
	public boolean write2Queue(CGBaseTO baseTO);
	
	/**
	 * @param baseLocation
	 * @throws Exception
	 */
	public void handlePersistProcess(); 
	
	/**
	 * @param file
	 * @param baseLocation
	 * @param processedFolder
	 * @return
	 */
	public boolean renameFile(File file, String baseLocation, String processedFolder);
	
	/**
	 * @return
	 */
	public String getFileLocation() ;
}
