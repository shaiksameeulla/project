/**
 * 
 */
package com.cg.framework.bcun.jnlp;

import java.io.File;

/**
 * @author mohammes
 *
 */
public class FileLocator {
	static String warFileLocation=null;
	public static String getWarFileLocation(){
		String baseDrive=null;
		if(isWindowsOS()) {
			baseDrive = "D:";
		} else {
			baseDrive="";
		}
		warFileLocation=baseDrive+File.separator+"opt"+File.separator+"ud"+File.separator+"udaan"+File.separator+"libs"+File.separator;
		return warFileLocation;
		
	}
	
	public static String getScriptFileLocation(){
		String baseDrive=null;
		if(isWindowsOS()) {
			baseDrive = "D:";
		} else {
			baseDrive="";
		}
		warFileLocation=baseDrive+File.separator+"opt"+File.separator+"ud"+File.separator;
		return warFileLocation;
		
	}
	
	/**
	 * @return
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOs = false;
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().contains("windows")){
			isWindowsOs = true;
		}
		return isWindowsOs;
	}

}
