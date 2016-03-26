package com.ff.web.loadmanagement.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;

/**
 * The Interface LoadManagementConstants.
 *
 * @author narmdr
 */
public interface LoadManagementConstants {

	/** The office type id. */
	String OFFICE_TYPE_ID = "officeTypeId";
	
	/** The origin city id. */
	String ORIGIN_CITY_ID = "originCityId";
	
	/** The origin office id. */
	String ORIGIN_OFFICE_ID = "originOfficeId";
	
	/** The dest city id. */
	String DEST_CITY_ID = "destCityId";
	
	/** The city id. */
	String CITY_ID = "cityId";
	
	/** The city code. */
	String CITY_CODE = "cityCode";
	
	/** The route id. */
	String ROUTE_ID = "routeId";
	
	/** The transport mode id. */
	String TRANSPORT_MODE_ID = "transportModeId";
	
	String VENDOR_ID = "vendorId";
	
	/** The service by type id. */
	String SERVICE_BY_TYPE_ID = "serviceByTypeId";
	
	/** The gate pass number. */
	String GATE_PASS_NUMBER = "gatePassNumber";
	
	/** The generate load dispatch no. */
	String GENERATE_LOAD_DISPATCH_NO = "GEN_LOAD_DISPATCH_NO";
	
	/** The generate receive number. */
	String GENERATE_RECEIVE_NUMBER = "RECEIVE_NUMBER";
	
	/** The manifest number. */
	String MANIFEST_NUMBER = "manifestNumber";
	
	/** The logged in office id. */
	String LOGGED_IN_OFFICE_ID = "loggedInOfficeId";
	
	/** The transshipment city id. */
	String TRANSSHIPMENT_CITY_ID = "transshipmentCityId";
	
	/** The serviced city id. */
	String SERVICED_CITY_ID = "servicedCityId";
	
	/** The regional office id. */
	String REGIONAL_OFFICE_ID = "regionalOfficeId";
	
	/** The load movement id. */
	String LOAD_MOVEMENT_ID = "loadMovementId"; 
	
	/** The received against id. */
	String RECEIVED_AGAINST_ID = "receivedAgainstId"; 
	
	/** The local receive type. */
	String LOCAL_RECEIVE_TYPE = "L"; 
	
	/** The outstation receive type. */
	String OUTSTATION_RECEIVE_TYPE = "O"; 
	
	/** The load receive edit bag. */
	String LOAD_RECEIVE_EDIT_BAG = "loadReceiveEditBag";
	
	/** The dest office id. */
	String DEST_OFFICE_ID = "destOfficeId";
	
	/** The receive number. */
	String RECEIVE_NUMBER = "RECEIVE_NUMBER";

	//URL
	/** The url view load dispatch. */
	String URL_VIEW_LOAD_DISPATCH = "viewLoadDispatch";
	
	/** The url view load receive local. */
	String URL_VIEW_LOAD_RECEIVE_LOCAL = "viewLoadReceiveLocal";
	
	/** The url view load receive outstation. */
	String URL_VIEW_LOAD_RECEIVE_OUTSTATION = "viewLoadReceiveOutstation";
	
	/** The url view load receive edit bag. */
	String URL_VIEW_LOAD_RECEIVE_EDIT_BAG = "viewLoadReceiveEditBag";
	
	String URL_PRINT_LOAD_DISPATCH="printPage";
	
	String URL_PRINT_LOAD_RECEIVE_LOCAL ="loadReceiveLocalPrint";
	
	//code
	/** The gate pass number start code. */
	String GATE_PASS_NUMBER_START_CODE = "G";
	
	/** The receive number start code. */
	String RECEIVE_NUMBER_START_CODE = "R";
	
	/** The others vehicle code. */
	String OTHERS_VEHICLE_CODE = "O";
	
	/** The master vehicle code. */
	String MASTER_VEHICLE_CODE = "M";
	
	/** The others code. */
	String OTHERS_CODE = "O";
	
	/** The master code. */
	String MASTER_CODE = "M";
	
	/** The dispatch direction. */
	String DISPATCH_DIRECTION = "D";
	
	/** The receive direction. */
	String RECEIVE_DIRECTION = "R";
	
	/** The road code. */
	String ROAD_CODE = CommonConstants.TRANSPORT_MODE_ROAD_CODE;
	
	/** The not received status. */
	String NOT_RECEIVED_STATUS = "N";
	
	/** The partial receive. */
	String PARTIAL_RECEIVE = "P";
	
	/** The complete receive. */
	String COMPLETE_RECEIVE = "C";
	
	/** The manifest type out. */
	String MANIFEST_TYPE_OUT = "O";
	
	/** The manifest type in. */
	String MANIFEST_TYPE_IN = "I";

	//LoadErrorCodesConstants
	/** The mbpl already dispatched. */
	String MBPL_ALREADY_DISPATCHED = "L000";
	
	/** The not issued to office. */
	String NOT_ISSUED_TO_OFFICE = "L001";
	
	/** The no route. */
	String NO_ROUTE = "L002";
	
	/** The invalid bpl mbpl num. */
	String INVALID_BPL_MBPL_NUM = "L005";
	
	/** The bpl mbpl num updated. */
	String BPL_MBPL_NUM_UPDATED = "L006";
	
	/** The bpl mbpl num does not updated. */
	String BPL_MBPL_NUM_DOES_NOT_UPDATED = "L007";
	
	/** The database issue details not updated. */
	String DATABASE_ISSUE_DETAILS_NOT_UPDATED = "L008";
	
	/** The database issue details not found. */
	String DATABASE_ISSUE_DETAILS_NOT_FOUND = "L010";
	
	/** The receive outstation details saved. */
	String RECEIVE_OUTSTATION_DETAILS_SAVED = "L012";
	
	/** The error in saving load receive outstation details. */
	String ERROR_IN_SAVING_LOAD_RECEIVE_OUTSTATION_DETAILS = "L013";

	String GATEPASS_NUMBER_DETAILS_SAVED = "L015";
	String ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS = "L016";
	String ERROR_IN_LOADING_PAGE = "L017";
	
	
	// param name
	/** The load connected id list param. */
	String LOAD_CONNECTED_ID_LIST_PARAM = "loadConnectedIdList";
	
	/** The manifest number list param. */
	String MANIFEST_NUMBER_LIST_PARAM = "manifestNumberList";
	
	/** The load movement id param. */
	String LOAD_MOVEMENT_ID_PARAM = "loadMovementId";
	
	/** The manifest number param. */
	String MANIFEST_NUMBER_PARAM = "manifestNumber";
	
	// Query Name
	/** The delete load connected by id list. */
	String DELETE_LOAD_CONNECTED_BY_ID_LIST = "deleteLoadConnectedByIdList";
	
	/** The get load connected from dispatch as not received query. */
	String GET_LOAD_CONNECTED_FROM_DISPATCH_AS_NOT_RECEIVED_QUERY = "getLoadConnectedFromDispatchAsNotReceived";
	
	/** The get load connected by manifest no query. */
	String GET_LOAD_CONNECTED_BY_MANIFEST_NO_QUERY = "getLoadConnectedByManifestNo";
	
	/** The get load connected ids as not received query. */
	String GET_LOAD_CONNECTED_IDS_AS_NOT_RECEIVED_QUERY = "getLoadConnectedIdsAsNotReceived";
	String QRY_UPDATE_LOAD_MOVEMENT_DT_TO_CENTRAL = "updateLoadMovementDtToCentral";
	
	
}
