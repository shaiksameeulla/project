package com.ff.web.manifest.inmanifest.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;

// TODO: Auto-generated Javadoc
/**
 * The Interface InManifestConstants.
 */
public interface InManifestConstants {
	
	/** The get consg manifested details. */
	String IS_CONSG_MANIFESTED = "isConsgManifested";
	
	/** The get consg manifested details. */
	String GET_CONSG_MANIFESTED_DETAILS = "getConsgManifestedDetails";
	
	/** The get manifested details. */
	String GET_MANIFESTED_DETAILS = "getManifestedDetails";
	
	String GET_CONSIGNMENT = "getConsignment";
	
	/** The not recieved. */
	String NOT_RECIEVED = "N";
	
	/** The success. */
	String 	SUCCESS = "Success";
	
	/** The region tos. */
	String REGION_TOS = "regionTOs";
	
	/** The region id. */
	String REGION_ID="regionId";
	
	/** The manifest type. */
	String MANIFEST_TYPE = "manifestType";

	String PROCESS_CODE_BPL_DOX = CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_IN_MANIFEST_DOX;
	
	String PROCESS_CODE_BPL_PARCEL = CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL;
	
	/** The updated process code. */
	String UPDATED_PROCESS_CODE = "RCVE";
	
	/** The process code bpl. */
	String PROCESS_CODE_BPL = "IBDX,IBPC";
	
	String PROCESS_CODE_IN_OGM = "OPKT,BOUT,OBDX";
	
	/** The process code bpl ogm. */
	String PROCESS_CODE_BPL_OGM = "OBDX,OBPC";

	String OUT_BPL_DOX_PROCESS_CODE = CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_BRANCH_OUT_MANIFEST
			+ CommonConstants.COMMA 
			+ CommonConstants.PROCESS_DISPATCH;

	String OUT_BPL_DOX_UPDATE_PROCESS_CODE = CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_BRANCH_OUT_MANIFEST
			+ CommonConstants.COMMA 
			+ CommonConstants.PROCESS_DISPATCH;

	String OUT_BPL_PARCEL_PROCESS_CODE = CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_BRANCH_OUT_MANIFEST
			+ CommonConstants.COMMA 
			+ CommonConstants.PROCESS_DISPATCH;

	String OUT_BPL_PARCEL_UPDATE_PROCESS_CODE = CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG
			+ CommonConstants.COMMA
			+ CommonConstants.PROCESS_BRANCH_OUT_MANIFEST
			+ CommonConstants.COMMA 
			+ CommonConstants.PROCESS_DISPATCH;

	String OUT_MASTER_BAG_PROCESS_CODE = CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG
			+ CommonConstants.COMMA 
			+ CommonConstants.PROCESS_DISPATCH;
	
	/** The manifest type in. */
	String MANIFEST_TYPE_IN ="I";
	
	/** The manifest no. */
	String MANIFEST_NO ="ManifestNumber";
	
	/** The login office id. */
	String LOGIN_OFFICE_ID ="LoginOfficeId";
	
	/** The manifest direction in. */
	String MANIFEST_DIRECTION_IN ="I";
	
	/** The qry less manifest numbers. */
	String QRY_LESS_MANIFEST_NUMBERS = "getLessManifestNumbers";
	
	/** The qry get in manifest process dtls. */
	String QRY_GET_IN_MANIFEST_PROCESS_DTLS = "getInManifestProcessDtls";
	
	/** The params. */
	String params="";
	
	/** The qry get in manifest enbedded dtls. */
	String QRY_GET_IN_MANIFEST_ENBEDDED_DTLS = "getInEmbededManifestDtls";

	String QRY_GET_MANIFEST_DTLS_BY_CONSG_NO_OPERATING_OFFICE = "getManifestDtlsByConsgNoOperatingOffice";
	String QRY_GET_BOOKING_OFFICE_ID = "getBookingOfficeId";
	
	/** The manifest number. */
	String MANIFEST_NUMBER = "manifestNo";

	String PARAM_MANIFEST_ID = "manifestId";
	String PARAM_REMARKS = "remarks";
	
	
	/** The manifest weight. */
	String MANIFEST_WEIGHT = "manifestWeight";
	
	/** The manifest embedded in. */
	String MANIFEST_EMBEDDED_IN = "manifestEmbeddedIn";
	
	/** The manifest id. */
	String MANIFEST_ID = "manifestId";
	
	/** The radio frequency no. */
	String RADIO_FREQUENCY_NO = "rfIdNo";
	
	/** The radio frequency id. */
	String RADIO_FREQUENCY_ID = "rfId";
	
	/** The manifest process code. */
	String MANIFEST_PROCESS_CODE = "manifestProcessCode";
	
	/** The process id. */
	String PROCESS_ID ="processId";
	
	/** The origin office id. */
	String ORIGIN_OFFICE_ID = "originOfficeId";
	
	/** The dest office id. */
	String DEST_OFFICE_ID = "destOffcId";
	
	/** The manifest type id. */
	String MANIFEST_TYPE_ID = "manifestType";
	
	/** The manifest direction. */
	String MANIFEST_DIRECTION = "manifestDirection";
	
	/** The updated process id. */
	String UPDATED_PROCESS_ID = "updatedProcessId";
	
	/** The manifest ids. */
	String MANIFEST_IDS = "manifestIds";
	
	/** The consignment no. */
	String CONSIGNMENT_NO = "consignmentNo";
	
	/** The manifest status. */
	String MANIFEST_STATUS = "manifestStatus";
	
	/** The none. */
	String NONE = "None";
	
	/** The not received code. */
	String NOT_RECEIVED_CODE = "N";
	
	/** The received code. */
	String RECEIVED_CODE = "R";
	
	/** The less baggage. */
	String LESS_BAGGAGE = "L";
	
	/** The excess baggage. */
	String EXCESS_BAGGAGE = "E";
			

	//error code constants
	/** The in bpl dox details saved. */
	String IN_BPL_DOX_DETAILS_SAVED = "IMBPL001";
	
	/** The in bpl dox details saved less. */
	String IN_BPL_DOX_DETAILS_SAVED_LESS = "IMBPL002";
	
	/** The in bpl dox details saved excess. */
	String IN_BPL_DOX_DETAILS_SAVED_EXCESS = "IMBPL003";

	/** The in bpl dox details saved less excess. */
	String IN_BPL_DOX_DETAILS_SAVED_LESS_EXCESS = "IMBPL004";
	
	/** The error in saving in bpl dox details. */
	String ERROR_IN_SAVING_IN_BPL_DOX_DETAILS = "IMBPL005";
	
	/** The in bpl parcel details saved. */
	String IN_BPL_PARCEL_DETAILS_SAVED = "IMBPL006";
	
	/** The in bpl parcel details saved less. */
	String IN_BPL_PARCEL_DETAILS_SAVED_LESS = "IMBPL007";
	
	/** The in bpl parcel details saved excess. */
	String IN_BPL_PARCEL_DETAILS_SAVED_EXCESS = "IMBPL008";
	
	/** The in bpl parcel details saved less excess. */
	String IN_BPL_PARCEL_DETAILS_SAVED_LESS_EXCESS = "IMBPL009";
	
	/** The error in saving in bpl parcel details. */
	String ERROR_IN_SAVING_IN_BPL_PARCEL_DETAILS = "IMBPL010";		
	
	/** The only parcel type consignment allowed. */
	String ONLY_PARCEL_TYPE_CONSIGNMENT_ALLOWED = "IMBPL011";

	String CONSIGNMENT_NO_ALREADY_IN_MANIFESTED = "IMBPL012";	
	
	String MANIFEST_NUMBER_ALREADY_IN_MANIFESTED = "IMBPL013";

	String ONLY_PARCEL_TYPE_BPL_ALLOWED = "IMBPL016";
	String ONLY_DOX_TYPE_BPL_ALLOWED = "IMBPL017";
	String IN_BPL_PROCESS_DID_NOT_HAPPEN = "IMBPL018";
	String MANIFEST_NUMBER_NOT_YET_RECEIVED = "IMBPL019";
	String MANIFEST_NUMBER_ALREADY_OUT_MANIFESTED = "IMBPL020";
	
	/** The in mbpl dox details saved less. */
	String IN_MBPL_DOX_DETAILS_SAVED_LESS = "M027";
	
	/** The in mbpl dox details saved excess. */
	String IN_MBPL_DOX_DETAILS_SAVED_EXCESS = "M028";
	
	/** The in mbpl dox details saved less excess. */
	String IN_MBPL_DOX_DETAILS_SAVED_LESS_EXCESS = "M029";
	
	/** The in ogm dox details saved less. */
	String IN_OGM_DOX_DETAILS_SAVED_LESS = "M030";
	
	/** The in ogm dox details saved excess. */
	String IN_OGM_DOX_DETAILS_SAVED_EXCESS = "M031";
	
	/** The in ogm dox details saved less excess. */
	String IN_OGM_DOX_DETAILS_SAVED_LESS_EXCESS = "M032";
	
	/** The in ogm dox details saved. */
	String IN_OGM_DOX_DETAILS_SAVED = "M033";
	
	/** The in mbpl dox details saved. */
	String IN_MBPL_DOX_DETAILS_SAVED = "M034";
	
	/** The error in saving in ogm dox details. */
	String ERROR_IN_SAVING_IN_OGM_DOX_DETAILS = "M035";
	
	/** The error in saving in mbpl details. */
	String ERROR_IN_SAVING_IN_MBPL_DETAILS = "M036";
	
	
	/** The param declared value. */
	String BPL_ALREADY_EXSITS = "M037";
	
	/** The consignment already exsits. */
	String CONSIGNMENT_ALREADY_EXSITS = "M038";
	
	/** The consignment already exsits. */
	String MANIFEST_ALREADY_EXSITS = "M039";	

	String ONLY_DOX_TYPE_CONSIGNMENT_ALLOWED = "M020";	

	String COMAIL_NO_ALREADY_IN_MANIFESTED = "IMBPL014";
	
	String HEADER_MANIFEST_NUMBER_ALREADY_INMANIFESTED = "IMBPL015";
	//param
	/** The param declared value. */
	String PARAM_DECLARED_VALUE = "declaredvalue";
	
	/** The param cod amt. */
	String PARAM_COD_AMT = "codAmt";
	
	/** The param to pay chg. */
	String PARAM_TO_PAY_CHG = "topayChg";
	
	/** The param price id. */
	String PARAM_PRICE_ID = "priceId";
	
	//query
	/** The qry get manifest consignment dtls by manifest id. */
	String QRY_GET_MANIFEST_CONSIGNMENT_DTLS_BY_MANIFEST_ID = "getManifestConsignmentDtlsByManifestId";
	
	/** The qry get less consg numbers. */
	String QRY_GET_LESS_CONSG_NUMBERS = "getLessConsgNumbers";
	
	/** The qry get consg manifest id by manifest id and consg no. */
	String QRY_GET_CONSG_MANIFEST_ID_BY_MANIFEST_ID_AND_CONSG_NO = "getConsgManifestIdByManifestIdAndConsgNo";
	
	/** The qry update cn pricing details. */
	String QRY_UPDATE_CN_PRICING_DETAILS = "updateCnPricingDetails";
	
	String QRY_UPDATE_MANIFEST_REMARKS_BY_ID = "updateManifestRemarksById";
	
	/** The qry get consignment details. */
	String QRY_GET_CONSIGNMENT_DETAILS = "getConsignmentDetails";
	
	/** The qry get consignment id by consg no. */
	String QRY_GET_CONSIGNMENT_ID_BY_CONSG_NO = "getConsignmentIdByConsgNo";
	
	/** The qry get manifest header. */
	String QRY_GET_MANIFEST_HEADER = "getManifestHeader";
	
	/** The qry get manifest numbers by embedded id. */
	String QRY_GET_MANIFEST_NUMBERS_BY_EMBEDDED_ID = "getManifestNumbersByEmbeddedId";
	
	/** The qry get consg numbers by manifest id. */
	String QRY_GET_CONSG_NUMBERS_BY_MANIFEST_ID = "getConsgNumbersByManifestId";
	
	/** The qry get manifest id by manifest no operating office. */
	String QRY_GET_MANIFEST_ID_BY_MANIFEST_NO_OPERATING_OFFICE = "getManifestIdByManifestNoOperatingOffice";
	
	String QRY_IS_CONSG_NUM_MANIFESTED = "isConsgNumManifested";
	String QRY_IS_COMAIL_NUM_MANIFESTED = "isComailNumManifested";	
	String QRY_IS_MANIFEST_NUM_IN_MANIFESTED = "isManifestNumInManifested";
	String QRY_IS_MANIFEST_HEADER_IN_MANIFESTED = "isManifestHeaderInManifested";
	String QRY_GET_COMAIL_ID_BY_NO = "getComailIdByNo";	
	String QRY_GET_MANIFEST_ID_BY_MANIFEST_NO_ORIGIN_TYPE="getManifestIdByManifestNoOriginType";
	String QRY_IS_BPL_DOX_PARCEL = "isBplDoxParcel";
	String QRY_IS_MANIFEST_OUT_MANIFESTED = "isManifestOutManifested";
	
	String QRY_GET_MNFST_OPEN_TYPE_AND_BPL_MNFST_TYPE="getMnfstOpenTypeAndBplMnfstType";
	
	String PARAMS = "consgNum,processCode";
	
	String DOCUMENT_TYPE = CommonConstants.CONSIGNMENT_TYPE_DOCUMENT;
	
	String PARCEL_TYPE = CommonConstants.CONSIGNMENT_TYPE_PARCEL;
	
	String URL_PRINT_IN_MBPL="printInMbpl";
	
	String URL_PRINT_IN_OGM="printOgmDox";
	
	String Fetch_Profile_Manifest_Embedded = "manifest-embedded-in";
	String Fetch_Profile_Manifest_Parcel = "manifest-parcel";
	String Fetch_Profile_Manifest_Dox = "manifest-dox";
	String USER_INFO = "user";
	String MAX_CN_ALLOWED_FOR_IN_DOX="MAX_CN_ALLOWED_FOR_IN_DOX";
	String MANIFEST_OPEN_TYPE_KEY_NAME="manifestOpenType";
	String BPL_MANIFEST_TYPE_KEY_NAME="bplManifestType";
}
