package com.cg.lbs.bcun.to;

import java.io.Serializable;

/**
 * The Class TwoWayWriteDataContentTO.
 * 
 * @author narmdr
 */
public class TwoWayWriteDataContentTO implements Serializable  {
	
	private static final long serialVersionUID = -3100177865986958784L;
	
	private String[] jsonObjectArrayStr;// 1 - BookingDO Object, 2 - ManifestDO Object 3....
	private String[] doNames;	// 1.Booking DO .
	private String fileName;
		
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the doNames
	 */
	public String[] getDoNames() {
		return doNames;
	}
	/**
	 * @param doNames the doNames to set
	 */
	public void setDoNames(String[] doNames) {
		this.doNames = doNames;
	}
	/**
	 * @return the jsonObjectArrayStr
	 */
	public String[] getJsonObjectArrayStr() {
		return jsonObjectArrayStr;
	}
	/**
	 * @param jsonObjectArrayStr the jsonObjectArrayStr to set
	 */
	public void setJsonObjectArrayStr(String[] jsonObjectArrayStr) {
		this.jsonObjectArrayStr = jsonObjectArrayStr;
	}
}
