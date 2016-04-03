package com.ff.ud.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class CommonUtils {

	public static Properties result = null;
	public static ResourceBundle rb = null;
	
	/**
	 * 	Returns true if operating system is windows
	 * @return 
	 */
	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * 	Returns true if operating system is linux.
	 * @return
	 */
	public static boolean isUnix() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
	
	/**
	 * Returns linux. if operating system is linux
	 * Returns windows. if operating system is windows
	 * 
	 * @return
	 */
	public static String getOperatingSytemAlias() {
		
		String alias="linux.";
		
		if(isWindows()){
			alias="windows.";
		}else if (isUnix()){
			alias="linux.";
		}else {
			alias="linux.";
		}
			return alias;
	}
	
	/**
	 * This appends the zero at the beaning of the  number
	 * @param noOfZeros
	 * @param value
	 * @return the zero appended number in the string
	 */
	public static String appendZeros(int noOfZeros,String value){
		return (String.format("%0"+noOfZeros+"d", Integer.parseInt(value)));
	}
	public static String appendZeros(int noOfZeros,Integer value){
		return (String.format("%0"+noOfZeros+"d", value));
	}
	
	/**
	 * This methods returns of the Properties object representation of the properties file.
	 * @param propertiesFileName : properties file name with the path
	 * @return Properties <code>Properties</code> 
	 * 	 */
	public static Properties getProperties(String propertiesFileName) {

		rb = ResourceBundle.getBundle(propertiesFileName, Locale.getDefault ());		
        result = new Properties ();
        for (Enumeration<String> keys = rb.getKeys (); keys.hasMoreElements ();)
        {
            final String key = keys.nextElement ();
            final String value = rb.getString(key);            
            result.put(key, value);
        }
        return result;
	}
	
	
	
}
