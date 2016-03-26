package com.ff.web.manifest.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;

/**
 * The Interface ManifestConstants.
 */
public interface ManifestConstants {

	String QRY_COMAIL_DETAILS = "getComailDetails";

	/** The qry in manifest details. */
	String QRY_IN_MANIFEST_DETAILS = "getManifestDetails";

	String QRY_MANIFEST_DETAILS = "getInManifestDtls";

	/** The qry get manifest by no process consg type. */
	String QRY_GET_MANIFEST_BY_NO_PROCESS_CONSG_TYPE = "getManifestByNoProcessConsgType";

	/** The qry get manifest by no process type. */
	String QRY_GET_MANIFEST_BY_NO_PROCESS_TYPE = "getManifestByNoProcessType";

	/** The qry get embedded manifest dtls by embedded id. */
	String QRY_GET_EMBEDDED_MANIFEST_DTLS_BY_EMBEDDED_ID = "getEmbeddedManifestDtlsByEmbeddedId";

	/** The qry get manifest id. */
	String QRY_GET_MANIFEST_ID = "getManifestId";

	/** The qry get manifest id by embedded id and mf no. */
	String QRY_GET_MANIFEST_ID_BY_EMBEDDED_ID_AND_MF_NO = "getManifestIdByEmbeddedIdAndMfNo";

	/** The qry get less manifest numbers. */
	String QRY_GET_LESS_MANIFEST_NUMBERS = "getLessManifestNumbers";

	/** The qry get embeded manifest dtls by loginoffice. */
	String QRY_GET_EMBEDED_MANIFEST_DTLS_BY_LOGINOFFICE = "getManifestedDtls";

	String QRY_GET_MANIFESTED_DTLS_BY_MANIFESTNO_STATUS_PROCESSCODE = "getManifestedDtlsByManifestNoStatusProcessCode";

	String QRY_GET_IN_MANIFESTED_DTLS_4_BPL_OUT_MANIFESTNO_LOGIN_OFFICEID = "getInManifestedDtls4BPLOutManifestNoLoginOfficeId";

	String QRY_GET_COMAIL_COUNT_BY_MANIFESTNO = "getComailCountByManifestNo";

	String QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO_AND_STATUS = "getManifestDtlsByManifestNoAndStatus";

	String QRY_GET_DIRECT_MANIFEST_DTLS_BY_MANIFEST_NO_AND_STATUS = "getDirectManifestDtlsByManifestNoAndStatus";

	String QRY_IS_MANIFEST_NUM_OUT_MANIFESTED = "isManifestNumOutManifested";

	String QRY_IS_OUT_MANIFEST_DESTN_ID = "isOutManifestDestnId";

	/** The params. */
	String params[] = { "manifestNumber", "processCode", "updateProcessCode" };

	/** The manifest type out. */
	String MANIFEST_TYPE_OUT = CommonConstants.MANIFEST_TYPE_OUT;

	/** The manifest type in. */
	String MANIFEST_TYPE_IN = CommonConstants.MANIFEST_TYPE_IN;

	/** The manifest type rto. */
	String MANIFEST_TYPE_RTO = CommonConstants.MANIFEST_TYPE_RTO;

	/** The manifest type out. */
	String MANIFEST_STATUS_CODE = CommonConstants.MANIFEST_STATUS_CODE;
	/** The branch misroute. */
	String BRANCH_MISROUTE = "B";

	/** The origin misroute. */
	String ORIGIN_MISROUTE = "H";

	/** The manifest type pod. */
	String MANIFEST_TYPE_POD = "P";

	/** The manifest direction out. */
	String MANIFEST_DIRECTION_OUT = "O";

	String MANIFEST_DIRECTION_IN = "I";

	/** The proudct series code. */
	String PROUDCT_SERIES_CODE = "C";

	/** The third party type cc. */
	String THIRD_PARTY_TYPE_CC = "CC";

	/** The third party type ba. */
	String THIRD_PARTY_TYPE_BA = "BA";

	/** The third party type fr. */
	String THIRD_PARTY_TYPE_FR = "FR";

	/** The type consignment. */
	String TYPE_CONSIGNMENT = "Consignment";

	/** The type manifest. */
	String TYPE_MANIFEST = "Manifest";

	/** The type comail. */
	String TYPE_COMAIL = "CoMail";

	/** The manifest no. */
	String MANIFEST_NO = "manifestNo";

	/** The process code. */
	String PROCESS_CODE = "processCode";

	/** The update process code. */
	String UPDATE_PROCESS_CODE = "updateProcessCode";

	/** The manifest type. */
	String MANIFEST_TYPE = "manifestType";

	String MANIFEST_STATUS = "manifestStatus";

	/** The login office id. */
	String LOGIN_OFFICE_ID = "loginOfficeId";

	String OFFICE = "office";
	String LOGGED_IN_CITY = "loggedincity";
	String LOGGED_IN_REPORTING_HUB = "loggedInReportingHUB";
	String OFFICE_CODE_PROCESS = "officeCodeProcess";
	String STATIONTOs = "stationTOs";
	String LOGIN_CITY_CODE = "loginCityCode";
	String MANIFEST_DIRECTION = "ManifestDirection";
	String REGIONTOs = "regionTOs";
	String REGION_ID = "regionId";
	// Configurable Parameters
	/** The opkt dox max cns allowed. */
	String OPKT_DOX_MAX_CNS_ALLOWED = "OPKT_DOX_MAX_CNS_ALLOWED";

	/** The opkt dox max co mails allowed. */
	String OPKT_DOX_MAX_CO_MAILS_ALLOWED = "OPKT_DOX_MAX_CO_MAILS_ALLOWED";

	/** The co mail start with. */
	String CO_MAIL_START_WITH = "CO_MAIL_START_WITH";

	/** The brom dox max cns allowed. */
	String BROM_DOX_MAX_CNS_ALLOWED = "BROM_DOX_MAX_CNS_ALLOWED";

	/** The brom dox max co mails allowed. */
	String BROM_DOX_MAX_CO_MAILS_ALLOWED = "BROM_DOX_MAX_CO_MAILS_ALLOWED";

	/** The opkt pax max cns allowed. */
	String OPKT_PAX_MAX_CNS_ALLOWED = "OPKT_PAX_MAX_CNS_ALLOWED";

	/** The opkt pax max weight allowed. */
	String OPKT_PAX_MAX_WEIGHT_ALLOWED = "OPKT_PAX_MAX_WEIGHT_ALLOWED";

	/** The opkt pax max tollrence allowed. */
	String OPKT_PAX_MAX_TOLLRENCE_ALLOWED = "OPKT_PAX_MAX_TOLLRENCE_ALLOWED";

	/** The tpom dox max cns allowed. */
	String TPOM_DOX_MAX_CNS_ALLOWED = "TPOM_DOX_MAX_CNS_ALLOWED";

	/** The tpom dox max weight allowed. */
	String TPOM_DOX_MAX_WEIGHT_ALLOWED = "TPOM_DOX_MAX_WEIGHT_ALLOWED";

	/** The tpom dox max tollrence allowed. */
	String TPOM_DOX_MAX_TOLLRENCE_ALLOWED = "TPOM_DOX_MAX_TOLLRENCE_ALLOWED";

	/** The bpl dox max cns allowed. */
	String BPL_DOX_MAX_CNS_ALLOWED = "BPL_DOX_MAX_CNS_ALLOWED";

	/** The bpl dox max weight allowed. */
	String BPL_DOX_MAX_WEIGHT_ALLOWED = "BPL_DOX_MAX_WEIGHT_ALLOWED";

	/** The bpl dox max tollrence allowed. */
	String BPL_DOX_MAX_TOLLRENCE_ALLOWED = "BPL_DOX_ MAX_TOLLRENCE_ALLOWED";

	/** The brom pax max cns allowed. */
	String BROM_PAX_MAX_CNS_ALLOWED = "BROM_PAX_MAX_CNS_ALLOWED";

	/** The brom pax max weight allowed. */
	String BROM_PAX_MAX_WEIGHT_ALLOWED = "BROM_PAX_MAX_WEIGHT_ALLOWED";

	/** The brom pax max tollrence allowed. */
	String BROM_PAX_MAX_TOLLRENCE_ALLOWED = "BROM_PAX_MAX_TOLLRENCE_ALLOWED";

	/** The bpl brom max cns allowed. */
	String BPL_BROM_MAX_CNS_ALLOWED = "BPL_BROM_MAX_CNS_ALLOWED";

	/** The bpl brom max weight allowed. */
	String BPL_BROM_MAX_WEIGHT_ALLOWED = "BPL_BROM_MAX_WEIGHT_ALLOWED";

	/** The bpl brom max tollrence allowed. */
	String BPL_BROM_MAX_TOLLRENCE_ALLOWED = "BPL_BROM_MAX_TOLLRENCE_ALLOWED";

	/** The mbpl max manifest allowed. */
	String MBPL_MAX_MANIFEST_ALLOWED = "MBPL_MAX_MANIFEST_ALLOWED";

	/** The mbpl max weight allowed. */
	String MBPL_MAX_WEIGHT_ALLOWED = "MBPL_MAX_WEIGHT_ALLOWED";

	/** The mbpl max tollrence allowed. */
	String MBPL_MAX_TOLLRENCE_ALLOWED = "MBPL_MAX_TOLLRENCE_ALLOWED";

	/** The pure. */
	String PURE = "P";

	/** The trans. */
	String TRANS = "T";

	/** The tp bpl max cns allowed. */
	String TP_BPL_MAX_CNS_ALLOWED = "TP_BPL_MAX_CNS_ALLOWED";

	/** The tp bpl max weight allowed. */
	String TP_BPL_MAX_WEIGHT_ALLOWED = "TP_BPL_MAX_WEIGHT_ALLOWED";

	/** The tp bpl max tolerance allowed. */
	String TP_BPL_MAX_TOLERANCE_ALLOWED = "TP_BPL_MAX_TOLERANCE_ALLOWED";

	/** The consg type dox. */
	String CONSG_TYPE_DOX = "DOCUMENT";

	/** The consg type parcel. */
	String CONSG_TYPE_PARCEL = "PPX";

	/** The url print bpl out manifest dox. */
	String URL_PRINT_BPL_OUT_MANIFEST_DOX = "printBplOutManifestDox";

	String URL_PRINT_MISROUTE = "printMisroute";

	/** The url print branch outmanifest parcel. */
	String URL_PRINT_BRANCH_OUTMANIFEST_PARCEL = "printBranchOutManifestParcel";

	/** The url print branch outmanifest dox. */
	String URL_PRINT_BRANCH_OUTMANIFEST_DOX = "printBranchOutManifestDox";

	String URL_PRINT_BPL_BRANCH_OUTMANIFEST = "printBplBranchOutManifest";

	String URL_PRINT_OUTMANIFEST_DOX = "printOutManifestDox";

	String URL_PRINT_OUTMANIFEST_PARCEL = "printOutManifestParcel";

	String URL_PRINT_MBPL_OUTMANIFEST = "printMbplOutManifest";

	String URL_PRINT_THIRDPARTY_BPL = "printThirdPartyBpl";

	String URL_PRINT_THIRDPARTY_DOX = "printThirdPartyDox";

	// ####MISROUTE TYPE
	/** The consignment. */
	String CONSIGNMENT = "C";

	/** The packet. */
	String PACKET = "P";

	/** The bag. */
	String BAG = "B";

	/** The master bag. */
	String MASTER_BAG = "M";

	/** The Branch misroute. */
	String BranchMisroute = "Branch Misroute";

	/** The Origin misroute. */
	String OriginMisroute = "Origin Misroute";

	/** The Branch code. */
	String BranchCode = "Branch Code :";

	/** The Hub code. */
	String HubCode = "Hub Code :";

	String PARTY_TYPE_BA = "S";
	String PARTY_TYPE_CC = "R";
	String PARTY_TYPE_FR = "F";

	String OPEN_MANIFEST = "O";
	String SAVE_MANIFEST_ACTION = "save";
	String CLOSE_MANIFEST_ACTION = "close";

	/** Fetch profile for manifest */
	String FETCH_PROFILE_MANIFEST_DOX = "manifest-dox";
	String FETCH_PROFILE_OUT_MANIFEST_DOX = "manifest-out-dox";
	String FETCH_PROFILE_MANIFEST_PARCEL = "manifest-parcel";
	String FETCH_PROFILE_MANIFEST_EMBEDDED_IN = "manifest-embedded-in";
	String FETCH_PROFILE_PARCEL_EMBEDDED_IN_MANIFEST = "manifest-parcel-embedded-in";

	String QRY_GET_CONSIGNMENT_BY_CONSG_NO = "getConsignmentByConsgNo";
	String QRY_GET_MANIFESTS_BY_MANIFEST_NOS = "getManifestsByManifestNos";
	String QRY_GET_DIRECT_MANIFESTS_BY_MANIFESTNOS = "getDirectManifestsByManifestNos";
	String QRY_GET_BOOKING_CONSIGNMENT = "getBookingConsignment";
	String PARAM_CONSG_NO = "consgNo";
	String PARAM_CONSG_NOS = "consgNos";
	String PARAM_MANIFEST_NOS = "manifestNos";
	String PARAM_OFFICE_ID = "officeId";
	String PARAM_MANIFEST_PROCESS_TYPE = "manifestProcessType";
	String PARAM_MANIFEST_DIRECTION = "manifestDirection";

	String PARAM_MANIFEST_STATUS = "manifestStatus";

	String QRY_GET_MISROUTE_MANIFESTS_BY_MANIFEST_NOS = "getMisrouteByManifestNos";

	String ACTION_SAVE = "S";
	String ACTION_SEARCH = "R";

	String MASTER_BAG_MANIFEST_TYPE = "Master Bag";
	String BAG_MANIFEST_TYPE = "Bag";
	String PACKET_MANIFEST_TYPE = "Packet";
	String CONSIGNMENT_MANIFEST_TYPE = "Consignment";

	String PICKUP_PROCESS_CODE = "UPPU";

	String MISROUTE_TYPE = "misrouteType";

	String MANIFEST_CONSG_RATE_DTLS = "MANIFEST_CONSG_RATE_DTLS";
	String PARAM_CONSG_TYPE_PPX = "CONSG_TYPE_PPX";
	String PARAM_CONSIGNOR = "CONSIGNOR";
	String PARAM_INSURED_BY_TYPE_FFCL = "INSURED_BY_FFCL";
	String PARAM_INSURED_BY_TYPE_CONSIGNOR = "INSURED_BY_CONSIGNOR";
	String PARAM_CONSG_TYPE_DOX = "CONSG_TYPE_DOX";

	String PARAM_IN_MANIFEST_REMARKS_LIST = "inManifestRemarkList";

	String QRY_GET_PICKUP_RUNSHEET_HEADER_BY_CONSIGNMENT = "getPickupRunsheetHeaderByConsignmentNo";

	String PICKUP_RUNSHEET_HEADER_STATUS_CLOSED = "C";

	String EXCESS_MANIFEST = "E";

}
