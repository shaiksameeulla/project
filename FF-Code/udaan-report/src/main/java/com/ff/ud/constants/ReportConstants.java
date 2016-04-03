package com.ff.ud.constants;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReportConstants {

	public static Properties result = null;
	public static ResourceBundle rb = null;

	
	public static final String REPORT_NAME="reportName";
	public static final String VAR_REPORT_URL = "reportURL";
	public static final String DATABASE_NAME_MASTER = "CorpUDAAN.";
	
	
	public static final String REPORT_PROPERTIES_FILE_NAME = "report";
	public static final String COMMON_REPORT_SERVICE = "commonReportService";
	public static final String PRODUCT_TO = "productTo";
	public static final String REGION_DO = "regionDo";
	public static final String CITY_DO = "CityDo";
	public static final String SUCCESS_FORWARD = "success";
	public static final String WRAPPER_REPORT_TO= "wrapperReportTO";

	
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
