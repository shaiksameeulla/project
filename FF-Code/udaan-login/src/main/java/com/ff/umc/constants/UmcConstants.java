package com.ff.umc.constants;

public interface UmcConstants {
	// login

	public static final String LOGIN_SERVICE = "loginService";
	public static final String STRING_BLANK = "";
	public static final String CHANGEPASSWORD = "changepassword";
	public static final String FORGOTPASSWORD = "forgotpassword";
	public static final String SUCCESS = "success";
	public static final String USER_INFO = "user";
	public static final String FAILURE = "failure";
	public static final String WELCOME = "welcome";
	public static final String LOGEIN_USER = "user";
	public static final String GENERATE_RANDOM_PASSWORD = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";
	public static final String SHA = "SHA";
	public static final String UTF_8 = "UTF-8";

	public static final String SHOW_ENTER_USERID = "showEnterUserID";

	public static final String FLAG_Y = "Y";
	public static final String FLAG_N = "N";
	public static final String FLAG_C = "C";
	public static final String FLAG_E = "E";
	public static final String SUCCESS_FLAG = "successFlag";
	public static final Integer MAX_LOGIN_ATTEMPTS = 3;
	public static final Integer MAX_NOT_LOGGED_IN_DAYS = 15;
	public static final String EMPLOYEEINFO = "employee";
	public static final String CUSTOMERINFO = "customer";
	public static final String LOGIN = "login";
	String CONFIG_ITEMS = "configItems";
	public static final String PASSWORD_LENGTH = "8";

	String NEW_PASSWORD_MESSAGE = "The New Password is ";
	String NEW_PASSWORD_EMAIL_SUBJECT = "New Password Details";
	String UDAAN_CONFIG = "udaan-web";

	String CONFIGURABLE_PARAM_QUERRY = "getConfigurableParmByName";
	String PARAM_NAME = "paramName";
	String HYPHEN = "-";
	String SLASH_CONST = "/";
	String EMPTY_STRING = "";
	// login

	// role

	String ADD_USER_ROLES = "addUserRoles";
	String USER_ROLES_ACTIVE = "A";
	String USER_ROLES_INACTIVE = "I";
	String USER_ROLES_ASSIGNED = "ASSIGNED";
	String USER_ROLES_NOT_ASSIGNED = "NOT_ASSIGNED";
	String CREATE_CUSTOMER_LOGIN = "customerLogin";
	String CREATE_EMPLOYEE_LOGIN = "employeeLogin";

	String APPL_SCREENS = "applScreens";
	String USER_ROLES = "userRoles";
	String ROLE_TYPE = "roleType";
	String NOROLES = "NOROLES";
	String USER_ROLE_ID = "userRoleId";
	String NO_USER_RIGHTS = "NOUSERRIGHTS";
	String QRY_UPDATE_USER_ROLES = "updateUserRole";
	String STATUS = "status";
	String USER_ROLE_IDS = "roleIds";
	String ROLE_ID = "roleId";
	String CUST_TYPE_ID = "custTypeId";
	String OFFIC_ID = "officeId";
	String ROLE_ID_1 = "rolesTypeDO.roleId";
	String SRC_ACCESS_BOTH = "B";
	String QRY_ACCESS_USER_SCREENS = "getAccessibleApplscreens";
	String ACCESSIBLE = "accessible";
	String QRY_IS_ROLE_ASSIGNED_TO_USER = "isRoleAssigned";
	String QRY_DELETE_APPL_RIGHTS = "deleteApplRights";
	String ASSIGN_USER_ROLES = "assignUserRoles";
	String REGION_LIST = "regionTOs";
	String REGION_ID = "regionId";
	String CITY_LIST = "cityTOs";
	String FETCH_APP_RIGHTS = "fetchAppRights";

	// Login
	String QRY_GET_USER_BY_USERNAME = "getUserbyUserName";

	String USER_NAME = "userName";
	String WELCOME_USERNAME = "welcomeUserName";
	String QRY_GET_PASSWORD_BY_USERID = "getPasswordByUserId";
	String USER_ID = "userId";
	String QRY_GET_LAST_LOGIN_LOGOUT_RECORD = "getLastLoginLogoutRecord";
	String QRY_GET_EMP_USERDO_BY_USERID = "getEmpUserDObyUserId";
	String QRY_GET_CUST_USERDO_BY_USERID = "getCustUserDObyUserId";
	String QRY_GET_USER_ROLES = "getUserRoles";
	String QRY_GET_USER_SCREEN = "getUserScreen";
	String QRY_GET_LAST_TWO_PASSWORD_BY_USERID = "getLastTwoPasswordByUserId";
	String QRY_GET_PASSWORD_RECORDS = "getPasswordRecords";
	String QRY_GET_OFFICE_BY_EMPID = "getOfficeByEmpId";
	String QRY_GET_OFFICE_BY_CUSTID = "getOfficeByCustId";
	String EMPLOYEE_ID = "employeeId";
	// UMC
	String QRY_GETOFFICENAME_BYOFFICEID = "getOfficeNameByOfficeId";
	String QRY_UPDATE_USER_STATUS = "updateUserStatus";
	String QRY_GETCUST_BYCUSTNAME = "getCustDOByCustName";
	String QRY_GETCUSTTYPE_DESC_BYCUSTTYPEID = "getCustTypeDescByCustTypeId";
	String USER_ACTIVE = "active";
	String USER_INACTIVE = "de-active";
	String CUST_NAME = "custName";
	String CUST_CODE = "custCode";
	String CUSTOMER_ID = "customerId";
	String QRY_USER_RECORD = "getUserRecord";
	String QRY_CUST_EMAIL_ID = "getCustEmailID";
	String QRY_EMP_EMAIL_ID = "getEmpEmailID";
	String USER_CODE = "userCode";
	String PARAM_USER_CODE = "user.userName";
	String PARAM_USER_TYPE = "user.userType";
	String INVALID_USER = "INVALID_USER";
	String SHOW_CUST_DETAILS = "showCustDetails";
	String EMP_NAME = "empName";
	String FIRST_NAME = "fName";
	String LAST_NAME = "lName";
	String QRY_GETEMP_BYNAME = "getEmpDOByName";
	String QRY_GETEMP_BYCODE = "getEmpDOByCode";
	String QRY_CURRENT_PASSWD = "get_current_paswd";
	String QRY_GETCUSTUSER_BYCUSTID = "getCustUserDObyCustId";
	String QRY_GET_USER_BY_USERID = "getUserbyUserId";
	String SRC_ACCESS_TO_FFCL = "E";
	String SRC_ACCESS_TO_CUSTOMER = "C";
	String QRY_GET_RIGHTS_ID = "getUserRightsId";
	String QRY_GET_OFFICES_BY_TYPE = "getOfficeByType";
	String PARAM_OFF_TYPE = "offType";
	String QRY_GET_OFFICES_BY_CITY = "getOfficeByCity";
	String PARAM_CITY_ID = "cityId";
	String MAPPING_TYPE = "mappingType";
	String MAPPED_AREA = "A";
	String MAPPED_RHO = "R";
	String QRY_GET_USER_BY_USERNAME_TYPE = "getUserbyUserNameType";
	String PARAM_USER_TYPE_1 = "userType";
	String QRY_USER_OFFICE_RIGHT_ID = "getUserOfficeRightsId";
	String OFFICE_ID = "officeId";
	String QRY_GETEMPUSER_BYEMPID = "getEmpUserDObyEmpId";
	String QRY_GETUSER_BYEMPID = "getUserListByEmpId";
	String CUSTOMER_LIST = "customersList";
	String UDAAN_MENUS = "udaanMenus";
	String MENU_LINK = "menuLink";
	String USER_TYPE_FFCL = "E";
	String USER_TYPE_CUSTOMER = "C";
	String EMPLOYEE_LIST = "employeesList";
	String RESET_PASWD = "resetPassword";
	String QRY_UPDATE_EMP_EMAIL = "updateEmailbyEmpId";
	String QRY_UPDATE_CUST_EMAIL = "updateEmailbyCustId";
	String QRY_GET_USER_BY_ACTIVE_USERNAME = "getUserbyActiveUserName";
	String QRY_PARAM_ACTIVE = "active";
	String QRY_PARAM_VALUE_ACTIVE = "Y";
	String QRY_UPDATE_LOGOUT_DATE = "updateLogoutDate";
	String QRY_PARAM_LOGOUT_DATE = "logOutDate";
	String QRY_PARAM_LOG_IN_OUT_ID = "logInOutId";
	String PARAM_EMP_CODE = "empCode";
	String QRY_GET_CUSTOMER_BY_CITY_ID = "getCustomersByCityIdForUMC";
	String INVALID_EMAIL_ID = "Invalid Email Address";
	String USER_ID_STATUS = "userStatus";
	String ACTIVATED_SUCCESSFULLY = "activated successfully";
	String DEACTIVATED_SUCCESSFULLY = "deactivated successfully";

	String REGIONAL_OFFICE_LIST = "regOfficeList";
	String APPL_SCREEN_LIST = "applScreensList";
	String OFFICE_CODE = "O";
	String STATION_CODE = "S";
	String REGIONAL_OFFICE_CODE = "C";
	String REGIONAL_OFFICES = "regionalOffices";
	String CITIES = "cities";
	String IN_ACTIVE = "INACTIVE";
	String EMPLOYEE_CODE = "E";
	String PARAM_ALL = "ALL";
	String USER_UNLOCKED = "userUnlocked";
	String LOGIN_ATTEMPTS_ZERO = "loginAttempsZero";
	String ZERO = "0";
	String CUSTOMER_DETAILS_IN_SESSION = "CUSTOMER_DETAILS_SESSION";

	// Added by Narasimha for silent login
	String UDAAN_CONFIG_ADMIN_SILENT_LOGIN_URL = "UDAAN_CONFIG_ADMIN_SILENT_LOGIN_URL";
	String UDAAN_WEB_SILENT_LOGIN_URL = "UDAAN_WEB_SILENT_LOGIN_URL";
	String UDAAN_CONFIG_ADMIN_PORT = "UDAAN_CONFIG_ADMIN_PORT";
	String UDAAN_WEB_PORT = "UDAAN_WEB_PORT";
	String HTTP = "http://";
	String UDAAN_CONSIG_ADMIN_IP_ADDRESS = "UDAAN_CONSIG_ADMIN_IP_ADDRESS";
	String UDAAN_WEB_IP_ADDRESS = "UDAAN_WEB_IP_ADDRESS";
	String APP_NAME_CONFIG_ADMIN = "udaan-config-admin";
	String APP_NAME_WEB = "udaan-web";
	String APP_NAME_CENTRALISED = "centralized";

	String CUST_SAVED_SUCCESSFULLY = "Customer saved successfully";
	String CUST_NOT_SAVED_SUCCESSFULLY = "Customer not saved successfully";
	String PASWD_RESET_SUCCESSFULLY = "Password reset successfully";
	String UNABLE_TO_RESET = "Unable to reset password";
	String EMP_USER_CREATED_SUCCESSFULLY = "Employee User created successfully";
	String EMP_USER_NOT_CREATED_SUCCESSFULLY = "Employee User could not be created ";
	String EMP_MAIL_MODIFIED_SUCCESSFULLY = "Employee mail modified successfully";

	String REGIONAL_OFC = "Regional Office";
	String ASSIGN_SCREEN = "Assign Screen";
	String ASSIGN_SAVE = "save";
	String ASSIGN_UPDATE = "update";
	String ASSIGN_USER = "User";
	String ASSIGN_OFFICE = "Office";
	String ASSIGN_STATION = "Station";
	String ASSIGN_LOGIN_ID = "Login Id";

	String DT_TO_BRANCH = "dtToBranch";

	// added by Kamal for silent login to udaan report

	String MODULE_UDAAN_REPORT = "Report";
	String MODULE_UDAAN_CENTRAL_DOWNLOAD = "DOWNLOAD";
	String APP_NAME_UDAAN_REPORT = "udaan-report";
	String APP_NAME_UDAAN_CENTRAL = "udaan-central-server";
	String UDAAN_REPORT_IP_ADDRESS = "UDAAN_REPORT_IP_ADDRESS";
	String UDAAN_REPORT_PORT = "UDAAN_REPORT_PORT";
	String UDAAN_REPORT_SILENT_LOGIN_URL = "UDAAN_REPORT_SILENT_LOGIN_URL";
	String SILENT_LOGIN = "silentLogin";

	String UDAAN_BUILD = "UDAAN_BUILD";

	String UDAAN_CENTRAL_SERVER_DOWNLOAD_URL = "UDAAN_ CENTRAL_SERVER_DOWNLOAD_URL";

	String UDAAN_CENTRAL_SERVER_PORT = "UDAAN_CENTRAL_SERVER_PORT";

	String UDAAN_CENTRAL_SERVER_ID_ADDRESS = "UDAAN_CENTRAL_SERVER_ID_ADDRESS";

	//
	public static final String QRY_GET_SCREEN_IDS_FOR_CONFIG_ADMIN = "getConfigAdminAppRightsForMenu";
	public static final String QRY_GET_SCREEN_IDS_FOR_UDAAN_WEB = "getWebAppRightsForMenu";
	public static final String QRY_GET_SCREEN_IDS_FOR_UDAAN_REPORT = "getReportAppRightsForMenu";

}
