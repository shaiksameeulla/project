package com.cg.lbs.opsmanintg.constant;


/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public interface BcunConstant {
	
	/**
	 * will be used to set default row fetch count
	 */
	int BCUN_DEFAULT_MAX_ROW_FETCH_COUNT = 100;
	/**
	 * used to get object type
	 */
	String OBJECT_TYPE = "objectType";
	/**
	 * used to get JSON object type
	 */
	String JSON_OBJECT_TYPE = "jsonobjectType";
	/**
	 * used to fetch child JSON object
	 */
	String JSON_CHILD_OBJECT = "jsonChildObject";
	/**
	 * repersent child list in JSON object
	 */
	String BASE_LIST = "baseList";
	/**
	 * Represent bcun operating mode as FTP
	 */
	String FTP_BCUN_OPRATION_MODE = "FTP";
	/** The VALIDATIO n_ request. */
	String VALIDATION_REQUEST = "validationRequest";
	
	/** The HTTP. */
	String HTTP = "http://";
	/** The COLON. */
	String COLON = ":";
	/** The JSON. */
	String JSON = "JSON";
	
	/** The drs parent consg type. these are only useful for PPX consg types */

	String DRS_PARENT_CONSG_TYPE="P";


	/** The drs child consg type.these are only useful for PPX consg types */

	String DRS_CHILD_CONSG_TYPE="C";
	
	/** The data transfer status new. */
	String DATA_TRANSFER_STATUS_NEW="N";
	
	/** The data transfer status transferred. */
	String DATA_TRANSFER_STATUS_TRANSFERRED="T";
	
	/** The data transfer status initiated. */
	String DATA_TRANSFER_STATUS_INITIATED="I";
	
	/** The data transfer status manual. */
	String DATA_TRANSFER_STATUS_MANUAL="M";
	
	/** The flag processed. */
	String FLAG_PROCESSED="PR";
	String DATA_ETRACTION_ID ="DATA_ETRACTION_ID";
	
	String DATA_ETRACTION_ID_LIST ="DATA_ETRACTION_ID_LIST";
	
	String YES = "Y";
	String NO = "N";
	/** The Constant AUTH_BRANCH_CODE. */
	String AUTH_BRANCH_CODE = "authBranchCode";
	/** The Constant SYSTEM_USER_CODE. */
	String SYSTEM_USER_CODE = "systemUserCode";
	String OUTBOUND_PROCESS_NAME_STOCK="stock";
	
	/** The outbound process name drs. */
	String OUTBOUND_PROCESS_NAME_DRS = "drs";
	String OUTBOUND_PROCESS_NAME_STOCK_CONTRACT="stockContract";
	String OUTBOUND_PROCESS_NAME_OTHERS ="others";
	String OUTBOUND_PROCESS_NAME_UMC ="umc";
	String OUTBOUND_PROCESS_NAME_PICKUP ="pickup";
	/** The Constant SYSTEM_CODE. */
	String SYSTEM_CODE = "systemCode";
	
	/** The Constant IS_AUTH_SYSTEM_USER_QUERY. */
	String IS_AUTH_SYSTEM_USER_QUERY = "getAuthSystemUserIds";
	
	/** The Constant BCUN_SYSTEM_AUTH_DAO. */
	String BCUN_SYSTEM_AUTH_DAO = "bcunSystemAuthDao";
	
	String FLAG_YES="Yes";
	String OUTBOUND_PROCESS_NAME_MANIFEST ="manifest";
	String OUTBOUND_PROCESS_NAME_CONSIGNMENT ="consignment";
	
	String INBOUND_PROCESS_NAME_DRS="drs";
	String INBOUND_PROCESS_NAME_PICK_UP ="pickup";
	String INBOUND_PROCESS_NAME_UMC ="umc";
	String INBOUND_PROCESS_NAME_BOOKING ="booking";
	String INBOUND_PROCESS_NAME_CONSIGNMENT ="consignment";
	String INBOUND_PROCESS_NAME_MANIFEST ="manifest";
	String INBOUND_PROCESS_NAME_OTHERS ="others";
	
	String OUTBOUND_MASTER_PROCESS_DAILY ="daily";
	String OUTBOUND_MASTER_PROCESS_WEEKLY ="weekly";
	
	String BCUN_PHRASE_INBOUND="Inbound";
	
	String BCUN_PHRASE_OUTBOUND="Outbound";
	
	String DUMP_URL_SESSION="DUMP_URL";

	String BCUN_PROCESS_BY_QUEUE = "bcun.process.byqueue";
	
	String TWO_WAY_WRITE = "TwoWayWrite";
	String TWO_WAY_WRITE_TRANSFER_STATUS = "T";

	String TWO_WRITE_REMOTE_SERVLET_NAME = "TwoWayWriteHTTPServlet.ht";
	
	String JSON_STRING_ENDS_WITH_CHARACTER="}]";
	
	String JSON_STRING_STARTS_WITH_CHARACTER="[{";
}
