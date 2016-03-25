package com.ff.universe.tracking.constant;

public interface UniversalTrackingConstants {
	String PARAM_PROCESS_ID = "processId";
	String PARAM_PROCESS_CODE = "processCode";
	String PARAM_OFFICE_CODE = "officeCode";
	
	// Opertaing Levels 
	Integer OPERATING_LEVEL_1 = 10;
	Integer OPERATING_LEVEL_2 = 20;
	Integer OPERATING_LEVEL_3 = 80;
	Integer OPERATING_LEVEL_4 = 100;
	
	Integer OPERATING_LEVEL_TRANSHIPMENT_1 = 30;
	Integer OPERATING_LEVEL_TRANSHIPMENT_INCREMENT_VALUE = 10;
	
	// Query Constants
	String QRY_GETOFFICE_BY_OFFICEID="getOfficeNameByOfficeId";
	String OFFICE_ID = "officeId";
	String TRACKING_RUNNING_NO = "TRACKING_RUNNING_NO";
	String QRY_GETOFFICE_BY_DESTCITYID="getOfficeByDestCityId";
	String PARAMS="officeId,cityId";
	String GET_BOOKING_DETAILS = "getBookingDetails";
	
	//Artifact Type
	String ARTIFACT_CONSIGNMENT = "C";
	String ARTIFACT_MANIFEST = "M";
	
	
	//Tracking Parameter Keys
	String MANIFEST_NUMBER ="manifestNo" ;
	String WEIGHT ="weight" ;
	String BRANCH_OFFICE ="branchOff" ;
	String HUB_OFFICE ="hubOffice" ;
	String CUSTOMER_NAME = "custName";
	String NO_PIECES = "noOfPieces";
	String CITY = "city";
	String CONSIGNMENT_NUMBER ="consgNo" ;
    String DRS_NO = "DRSNo";
    String OTHERS = "others";
    
    String OFFICE_PATH_PRE = "<a href='branchDetails.do?OfficeID=";
    String OFFICE_PATH_SUFFIX = "'>";
    
    String PICKUP_CODE = "UPPU";
    String BOOKING_CODE="BOOK";
    String DISPATCH_CODE="DSPT";
    String RECEIVE_CODE="RCVE";
    
    String INMASTER_CODE="IMBG";
    String INBAGDOX_CODE="IBDX";
    String INBAGPARCEL_CODE="IBPC";
    String INPACKETDOX_CODE="IPKT";
    
    String OUTMASTER_CODE="OMBG";
    String OUTBAGDOX_CODE="OBDX";
    String OUTBAGPARCEL_CODE="OBPC";
    String OUTPACKETDOX_CODE="OPKT";
    
    String DELIVERY_CODE= "DLRS";
    
    String CONSG_NUMBER = "CN";
    String REF_NUMBER = "RN";
    
    String PENDING_CN_PATH = "DRS No.{DRSNo} is {status} due to {reason}";
    String DELIVERED_CN_PATH = "Delivered to {ReceiverName} on {DeliveryDate} at {DeliveryTime} runsheet updated on {updatedDate}";
    String RTO_DELIVERED_CN_PATH = "Delivered on {DeliveryDate} at {DeliveryTime} runsheet updated on {updatedDate}";
    String STOP_DELIVERY_TEMP = "Stop Delivery Request raised from {stopDlvReqOff}";
    String STOP_DELIVERY_PROCESS_NAME ="STOP DELIVERY";
    String DELIVERY_STATUS_DESCRIPTION_RTO_DELIVERED = "RTO Delivered";
    //"DRS No.{DRSNo} is {status}";
    		
    String KEY_1 = "DRSNo";
    String KEY_2 = "status";
    String KEY_3 = "reason";
    String KEY_4 = "weight";
    String KEY_5 = "ReceiverName";
    
    String QRY_GET_CONSIGNMENT_BY_CONSG_NO = "getConsignmentByConsgNo";

    String QRY_GET_USER_BY_USERID = "getUserbyUserId";
    String USER_ID = "userId";
    
    String UPLOAD_CONSIGNMENTS_EXCEL_TO_TRACK = "TRERR012"; 
}
