package com.ff.universe.loadmanagement.constant;

import com.capgemini.lbs.framework.constants.CommonConstants;

/**
 * The Interface LoadManagementUniversalConstants.
 */
public interface LoadManagementUniversalConstants {
	
	/** The others vehicle code. */
	String OTHERS_VEHICLE_CODE = "O";
	
	/** The others code. */
	String OTHERS_CODE = "O";
	//String MASTER_VEHICLE_CODE = "M";
	/** The dispatch direction. */
	String DISPATCH_DIRECTION = "D";
	
	/** The receive direction. */
	String RECEIVE_DIRECTION = "R";

	/** The direct code. */
	String DIRECT_CODE = "D";
	
	/** The Employee_ code. */
	String Employee_CODE = "E";
	
	/** The vendor code. */
	String VENDOR_CODE = "V";
	
	/** The road code. */
	String ROAD_CODE = CommonConstants.TRANSPORT_MODE_ROAD_CODE;
	
	/** The air code. */
	String AIR_CODE = CommonConstants.TRANSPORT_MODE_AIR_CODE;
	
	/** The train code. */
	String TRAIN_CODE = CommonConstants.TRANSPORT_MODE_Train_CODE;
	//Manifest Code
	/*String INCOMING_MANIFEST = "I";
	String OUTGOING_MANIFEST = "O";
	String RTO_MANIFEST = "R";*/
	/** The manifest type out. */
	String MANIFEST_TYPE_OUT = "O";
	
	/** The manifest type in. */
	String MANIFEST_TYPE_IN = "I";
	
	/** The manifest type rto. */
	String MANIFEST_TYPE_RTO = "R";
	
	//Load Error Codes Constants
	/** The mbpl already dispatched. */
	String MBPL_ALREADY_DISPATCHED = "L000";
	
	/** The not issued to office. */
	String NOT_ISSUED_TO_OFFICE = "L001";
	
	/** The no route. */
	String NO_ROUTE = "L002";
	
	/** The gate pass number already received. */
	String GATE_PASS_NUMBER_ALREADY_RECEIVED = "L003";
	
	/** The invalid gatepass number. */
	String INVALID_GATEPASS_NUMBER = "L004";
	
	/** The mbpl already received. */
	String MBPL_ALREADY_RECEIVED = "L009";
	
	/** The receive no already exist. */
	String RECEIVE_NO_ALREADY_EXIST = "L011";

	String ERROR_IN_PRINTING_INVALED_GATEPASS_NUMBER = "L014";

	String ONLY_CLOSED_BPL_MBPL_ARE_ALLOWED = "L018";
	String OUTSECTOR_BPL_MBPL_NOT_ALLOWED = "L019";
	String BPL_MBPL_NEITHER_PREPARED_NOR_INMANIFESTED = "L020";
	String BPL_MBPL_NUMBER_ALREADY_IN_MANIFESTED = "L021";
	
	//print
	/** The train number label. */
	String TRAIN_NUMBER_LABEL = "Train Number";
	
	/** The flight number label. */
	String FLIGHT_NUMBER_LABEL ="Flight Number";
	
	/** The vehicle number label. */
	String VEHICLE_NUMBER_LABEL ="Vehicle Number";
	
	String NOT_RECEIVED_LABEL = "Not Received";
	
	String RECEIVE_LABEL = "Received";
	
	String EXCESS_LABEL = "Excess";
	

	/** The not received status. */
	String NOT_RECEIVED_STATUS = "N";
	
	String RECEIVE_STATUS = "R";
	
	String EXCESS_STATUS = "E";
		
	//Param constants for DAO
	/** The gate pass number param. */
	String GATE_PASS_NUMBER_PARAM = "gatePassNumber";
	
	/** The movement direction param. */
	String MOVEMENT_DIRECTION_PARAM = "movementDirection";
	
	/** The regional office id param. */
	String REGIONAL_OFFICE_ID_PARAM = "regionalOfficeId";
	
	/** The manifest number param. */
	String MANIFEST_NUMBER_PARAM = "manifestNumber";
	
	/** The load movement id param. */
	String LOAD_MOVEMENT_ID_PARAM = "loadMovementId";
	
	/** The origin office id param. */
	String ORIGIN_OFFICE_ID_PARAM = "originOfficeId";
	
	/** The logged in office id param. */
	String LOGGED_IN_OFFICE_ID_PARAM = "loggedInOfficeId";
	
	/** The dest office id param. */
	String DEST_OFFICE_ID_PARAM = "destOfficeId";
	
	/** The receive number param. */
	String RECEIVE_NUMBER_PARAM = "receiveNumber";
	
	//Query Name
	/** The get load movement for receive local by dispatch query. */
	String GET_LOAD_MOVEMENT_FOR_RECEIVE_LOCAL_BY_DISPATCH_QUERY = 
			"getLoadMovementForReceiveLocalByDispatch";
	
	/** The get load movement for receive local by receive query. */
	String GET_LOAD_MOVEMENT_FOR_RECEIVE_LOCAL_BY_RECEIVE_QUERY = 
			"getLoadMovementForReceiveLocalByReceive";
	
	/** The GE t_ loa d_ connecte d_4_ receiv e_ loca l_ b y_ receiv e_ manifes t_ n o_ query. */
	String GET_LOAD_CONNECTED_4_RECEIVE_LOCAL_BY_RECEIVE_MANIFEST_NO_QUERY = 
			"getLoadConnected4ReceiveLocalByReceiveManifestNo";
	
	/** The GE t_ loa d_ connecte d_4_ receiv e_ loca l_ b y_ dispatc h_ manifes t_ n o_ query. */
	String GET_LOAD_CONNECTED_4_RECEIVE_LOCAL_BY_DISPATCH_MANIFEST_NO_QUERY = 
			"getLoadConnected4ReceiveLocalByDispatchManifestNo";
	
	/** The GE t_ loa d_ connecte d_4_ receiv e_ outstatio n_ b y_ dispatc h_ manifes t_ n o_ query. */
	String GET_LOAD_CONNECTED_4_RECEIVE_OUTSTATION_BY_DISPATCH_MANIFEST_NO_QUERY = 
			"getLoadConnected4ReceiveOutstationByDispatchManifestNo";

	/** The get load connected for manifest query. */
	String GET_LOAD_CONNECTED_FOR_MANIFEST_QUERY = "getLoadConnectedForManifest";
	
	/** The is manifest received query. */
	String IS_MANIFEST_RECEIVED_QUERY = "isManifestReceived";
	
	/** The is manifest received for outstation query. */
	String IS_MANIFEST_RECEIVED_FOR_OUTSTATION_QUERY = "isManifestReceived4Outstation";
	
	/** The is receive number exist query. */
	String IS_RECEIVE_NUMBER_EXIST_QUERY = "isReceiveNumberExist";

	String BRANCH_TO_BRANCH = "BTB";
	String BRANCH_TO_HUB = "BTH";
	String HUB_TO_BRANCH = "HTB";
	String HUB_TO_HUB = "HTH";
}
