/*
 * @author mohammes
 */
package com.capgemini.lbs.framework.constants;

// TODO: Auto-generated Javadoc

public interface SplitModelConstant {

	/** The I p_ config. */
	String IP_CONFIG = "ipconfig /all";

	/** The I f_ confi g_ fo r_ linux. */
	String IF_CONFIG_FOR_LINUX = "ifconfig -a";

	/** The PHYSICA l_ addres s_ pattern. */
	String PHYSICAL_ADDRESS_PATTERN = ".*Physical Address.*: (.*)";

	/** The PHYSICA l_ addres s_ patter n_ fo r_ linux. */
	String PHYSICAL_ADDRESS_PATTERN_FOR_LINUX = ".*Link encap:Ethernet  HWaddr.* (.*)";

	/** The JSESSIO n_ id. */
	String JSESSION_ID = "JSessionId";

	/** The JSESSIONI d_ cookie. */
	String JSESSIONID_COOKIE = "JSESSIONID";

	/** The JSON. */
	String JSON = "JSON";

	/** The RESPONS e_ fro m_ server. */
	String RESPONSE_FROM_SERVER = "responseFromServer";

	/** The RESPONS e_ object. */
	String RESPONSE_OBJECT = "responseObject";

	/** The R e_ loggin. */
	String RE_LOGGIN = "Re-LoggIn";

	/** The USE r_ name. */
	String USER_NAME = "user.name";

	/** The USE r_ information. */
	String USER_INFORMATION = "userInfo";
	
	String USER_BRANCH_CODE = "branchCode";

	/** The MA c_ address. */
	String MAC_ADDRESS = "macAddress";

	/** The USE r_ status. */
	String USER_STATUS = "userStatusCookie";

	/** The USE r_ logge d_ in. */
	String USER_LOGGED_IN = "UserIsloggedIn";

	/** The U n_ authorise d_ user. */
	String UN_AUTHORISED_USER = "UnAuthorisedUser";
	
	String AUTHORISED_USER = "AuthorisedUser";

	/** The SPLI t_ mode l_ properties. */
	String SPLIT_MODEL_PROPERTIES = "splitmodel";

	/** The HTTP. */
	String HTTP = "http://";

	/** The COLON. */
	String COLON = ":";

	/** The LOGI n_ request. */
	String LOGIN_REQUEST = "/login.ht";
	String BCUN_LOGIN_SERVLET="/bcunLoginServlet.ht";

	/** The REMOT e_ server. */
	String REMOTE_SERVER = "REMOTE_SERVER";

	/** The HTT p_ port. */
	String HTTP_PORT = "HTTP_PORT";

	/** The QUESTIO n_ mark. */
	String QUESTION_MARK = "?";

	/** The EQUA l_ operator. */
	String EQUAL_OPERATOR = "=";

	/** The AN d_ operator. */
	String AND_OPERATOR = "&";

	/** The DOMAI n_ name. */
	String DOMAIN_NAME = "DOMAIN_NAME";

	/** The WE b_ domai n_ name. */
	String WEB_DOMAIN_NAME = "WEB_DOMAIN_NAME";

	/** The REMOT e_ servic e_ facade. */
	String REMOTE_SERVICE_FACADE = "/remoteServiceFacade.ht";
	
	
	String DATA_EXTRACTOR_SERVICE_FACADE = "/centralDataExtractorServlet.ht";

	/** The WELCOM e_ page. */
	String WELCOME_PAGE = "/Welcome.do";

	/** The PROX y_ port. */
	String PROXY_PORT = "PROXY_PORT";

	/** The PROX y_ server. */
	String PROXY_SERVER = "PROXY_SERVER";

	/** The OBJEC t_ type. */
	String OBJECT_TYPE = "objectType";
	
	String JSON_OBJECT_TYPE = "jsonobjectType";

	/** The WE b_ request. */
	String WEB_REQUEST = "webRequest";

	/** The BUSINES s_ exception. */
	String BUSINESS_EXCEPTION = "businessException";

	/** The BAC k_ slash. */
	String BACK_SLASH = "\\\\";

	/** The BLAN k_ string. */
	String BLANK_STRING = "";

	/** The STAC k_ trace. */
	String STACK_TRACE = "stackTrace";

	/** The CAUSE. */
	String CAUSE = "cause";

	/** The CONSIGNMEN t_ no t_ valid. */
	String CONSIGNMENT_NOT_VALID = "CN_NOT_VALID";

	/** The CONSIGNMEN t_ no t_ active. */
	String CONSIGNMENT_NOT_ACTIVE = "CN_NOT_ACTIVE";

	/** The WINDO w_ o s_ name. */
	String WINDOW_OS_NAME = "Window";

	/** The BAS e_ list. */
	String BASE_LIST = "baseList";
	
	String JSON_CHILD_OBJECT = "jsonChildObject";

	/** The M q_ facade. */
	String MQ_FACADE = "mqFacade";

	/** The ERRO r_ messag e_ fo r_ writin g_ i n_ mq. */
	String ERROR_MESSAGE_FOR_WRITING_IN_MQ = "Error while writing in MQ";
	
	/** The NO t_ writte n_ t o_ central. */
	String NOT_WRITTEN_TO_CENTRAL = "N";
	
	/** The WRITTE n_ t o_ central. */
	String WRITTEN_TO_CENTRAL = "Y";
	
	String CLIENT_PHYSICAL_NOADDRESS = "No ARP Entries Found";
	String IS_TWO_WAYWRITE_ENABLED= "IS_TWO_WAYWRITE_ENABLED";
	String FR_MAX_FETCH_SIZE_DBSYNC="FR_MAX_FETCH_SIZE_DBSYNC";
	String DP_MAX_FETCH_SIZE_DBSYNC="DP_MAX_FETCH_SIZE_DBSYNC";
	
	String TWO_WRITE_REMOTE_SERVLET_REQUEST = "/TwoWayWriteHTTPServlet.ht";
	
	
}
