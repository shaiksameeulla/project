
package com.capgemini.lbs.framework.constants;

/**
 * @author mohammes
 *
 */
public interface FrameworkConstants {


	String DD_MM_YYYY_HYPHEN_FORMAT = "dd-MM-yyyy";
	String DDMMYYYY_SLASH_FORMAT = "dd/MM/yyyy";
	String YYYY_MM_DD_HYPHEN_FORMAT = "yyyy-MM-dd";
	String DDMMYYYY_FORMAT = "ddMMyyyy";
	String YYYY_MM_DD_FORMAT = "yyyyMMdd";

	String DDMMYYYYHHMM_HYPHEN_FORMAT = "dd-MM-yyyy HH:mm";
	String DDMMYYYYHHMM_SLASH_FORMAT = "dd/MM/yyyy HH:mm";
	String DDMMYYYYHHMMSS_SLASH_FORMAT = "dd/MM/yyyy HH:mm:ss";
	String DDMMYYYYHHMMSS_HYPHEN_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
	String YYYY_MM_DD_HHMMSS_HYPHEN_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	String DD_MM_FORMAT = "ddMM";
	/** The CHARACTE r_ tilde. */
	String CHARACTER_TILDE = "~";

	/** The CHARACTE r_ hash. */
	String CHARACTER_HASH = "#";

	/** The EMPT y_ string. */
	String EMPTY_STRING = "";
	String EMPTY_STRING_WITH_SPACE = " ";

	/** The FORWAR d_ slash. */
	String FORWARD_SLASH = "/";

	/** The OPENIN g_ curl y_ brace. */
	String OPENING_CURLY_BRACE = "{";

	/** The CLOSIN g_ curl y_ brace. */
	String CLOSING_CURLY_BRACE = "}";

	/** The CHARACTE r_ colon. */
	String CHARACTER_COLON = ":";

	/** The CHARACTE r_ hyphen. */
	String CHARACTER_HYPHEN = "-";

	/** The CHARACTE r_ comma. */
	String CHARACTER_COMMA = ",";

	/** The OPENIN g_ inne r_ qoutes. */
	String OPENING_INNER_QOUTES = "\"";

	/** The CLOSIN g_ inne r_ qoutes. */
	String CLOSING_INNER_QOUTES = "\"";

	/** The CHARACTE r_ dot. */
	String CHARACTER_DOT = ".";

	String NULL_CONSTANT = "null";

	String CHARACTER_UNDERSCORE = "_";

	String FILE_PROCESSED = "PROCESSED";

	String DIR_PROCESSED = "PR";

	String SINGLE_QUOTE = "'";
	
	/** The error message. */
	String ERROR_MESSAGE = "error";
	
	/** The warning message. */
	String WARNING_MESSAGE = "warning";
	
	/** The info message. */
	String INFO_MESSAGE = "info";
	
	/*String WARNING_MESSAGE = "warning"; */ 
	/*String INFO_MESSAGE = "info";*/
	
	String FILE_NOT_FOUND = "FILE_NOT_FOUND";
	String LOGGED_IN_CITY = "CITY_TO";
	
	String MIME_TYPE_TEXT_JAVA_SCRIPT="text/javascript";
	/** The CHARACTE r_ dot. */
	String MIME_TYPE_TEXT_XML="text/xml";
	
	String STRING_YES="YES";
	String STRING_NO="NO";
	
	String ENUM_YES="Y";
	String ENUM_NO="N";
	
	String ENUM_DAY="D";
	String ENUM_MONTH="M";
	String ENUM_YEAR="Y";
	/** The CHARACTE percentile. */
	String CHARACTER_PERCENTILE = "%";
	
	/** The ERRO r_ ms g_ pro p_ fil e_ name. */
	String ERROR_MSG_PROP_FILE_NAME = "UdaanWebErrorMessageResources";
	String UNIVERALS_MSG_PROP_FILE_NAME = "UniversalMessageResources";
	String FRAMEWORK_MSG_PROP_FILE_NAME = "frameworkErrorMessages";
	
	String RATE_MSG_PROP_FILE_NAME = "RateCalculationMessageResources_en_US";
	
	/** The default error bundle key. */
	String DEFAULT_ERROR_BUNDLE_KEY="errorBundle";
	
	/** The universal message bundle key. */
	String UNIVERSAL_MESSAGE_BUNDLE_KEY="universalResourceBundle";
	
	/** The rate message bundle key. */
	String RATE_MESSAGE_BUNDLE_KEY="rateResourceBundle";
	
	/** The framweork message bundle key. */
	String FRAMWEORK_MESSAGE_BUNDLE_KEY="frameworkResourceBundle";
	
	String CLIENT_USER_FROM_EMAIL_ID="udaansupport@firstflight.net";
	
	String CONFIG_PARAM_FOR_FROM_EMAIL_ID="FROM_EMAIL_ID";
	
	String APP_NAME_WEB = "udaan-web";
	String APP_NAME_CENTRALISED = "centralized";
	
	String XML_EXTENSION = ".xml";
	 String ZIP_EXTENSION = ".zip";
	/** The success flag. */
	String SUCCESS_FLAG = "SUCCESS";
	
	String REQ_PARAM_FOR_RELOAD_URL="reloadurl";
	
	/** The mysql medium blob size. */
	int MYSQL_MEDIUM_BLOB_SIZE=1048576;

	/** The error flag. */
	String ERROR_FLAG = "ERROR";
	
	/** The errors starts with. For Universal Property constants */
	String ERRORS_STARTS_WITH="ERR";
	
	String SEQUENCE_NUMBER_NOT_GENERATED="CGS010";
	
	/** The data truncation error code. */
	int DATA_TRUNCATION_ERROR_CODE=1265;
	
	
	/** The data out of range error code. */
	int DATA_OUT_OF_RANGE_ERROR_CODE=1264;
	
	/** The foreign key error code. */
	int FOREIGN_KEY_ERROR_CODE=1452;
	
	/** The duplicate constraint error code. */
	int DUPLICATE_CONSTRAINT_ERROR_CODE=1062;
	int NOT_NULL_CONSTRAINT_ERROR_CODE=1048;
	int DATA_TRUNCATION_TOO_LONG_VALUE_ERROR_CODE=1406;
	
	//Report dependancy removal from config admin
	String HTTP = "http://";
	String UDAAN_REPORT_IP_ADDRESS = "UDAAN_REPORT_IP_ADDRESS";
	String UDAAN_REPORT_PORT = "UDAAN_REPORT_PORT";
	String APP_NAME_UDAAN_REPORT = "udaan-report";
	
	int FFCL_ADMIN_USER_ID=1;
	
	int FFCL_RE_BILLING_USER_ID=3;
	
	/** The bcun file identifier inbound. */
	String BCUN_FILE_IDENTIFIER_INBOUND = "-Inbound-";
	
	/** The bcun file identifier outbound. */
	String BCUN_FILE_IDENTIFIER_OUTBOUND = "-Outbound-";
	
	String BCUN_FILE_IDENTIFIER_INBOUND_KEYWORD = "Inbound";
	
}
