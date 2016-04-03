package com.ff.ud.constants;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class OpsmanDBFConstant {

	public static Properties result = null;
	public static ResourceBundle rb = null;
	public static final String FILE_PATH_PROPERTIES="filePaths";
	
	//public static final String OPSMAN_DBF_FILE_PATH = getProperties(FILE_PATH_PROPERTIES).getProperty(CommonUtils.getOperatingSytemAlias()+"dbf.path");
	//public static final String FTP_DBF_FILE_PATH = getProperties(FILE_PATH_PROPERTIES).getProperty(CommonUtils.getOperatingSytemAlias()+"ftp.webserver.file.location");
	
	public static final String OFFICE_TYPE_BO = "BO";
	public static final String OFFICE_TYPE_HO = "HO";
	public static final String OFFICE_TYPE_BI = "BI";
	public static final String OFFICE_TYPE_HI = "HI";
	public static final String DATABASE_NAME_MASTER = "CorpUDAAN.";
	public static final String DATABASE_NAME_OPSMAN_UDAAN_INTEGRATION = "CorpUDAAN.";
	
	
	public static Properties getProperties(String propertiesFileName) {
		rb = ResourceBundle.getBundle("properties/"+propertiesFileName, Locale.getDefault ());		
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
