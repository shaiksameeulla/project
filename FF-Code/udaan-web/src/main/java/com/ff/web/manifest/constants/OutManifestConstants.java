package com.ff.web.manifest.constants;

import com.ff.universe.manifest.constant.ManifestUniversalConstants;

public interface OutManifestConstants {

	String SUCCESS = "Success";

	String OUT_MANIFEST = "OutManifest";
	String BPL_OUT_MANIFEST = "bplOutManifest";
	String MBPL_OUT_MANIFEST = "mbplOutManifest";
	String BRANCH_MANIFEST = "branchManifest";
	String THIRD_PARTY_MANIFEST = "thirdPartyManifest";
	String BPL_OUT_MANIFEST_TYPE_PURE = "P";
	String BPL_OUT_MANIFEST_TYPE_TRANSSHIPMENT = "T";
	String BPL_BRANCH_OUT_MANIFEST = "bplBranchOutManifest";

	String CONSG_TYPE_DOX = "DOCUMENT";
	String CONSG_TYPE_PARCEL = "PARCEL";
	String BPL_MANIFEST = "bpl";
	String MANIFEST_NO = "manifestNo";
	String MISROUTE_NO = "misroutNo";
	String MANIFEST_ID = "manifestId";
	String MANIFEST_EMBEDDED_ID = "manifestEmbeddedId";
	String MAIN_PROCESS_CODE = "mainProcessCode";
	String PROCESS_CODE = "processCode";
	String MANIFEST_TYPE_TRANSHIPMENT = "TRANSHIPMENT";
	String MANIFEST_TYPE_TRANSHIPMENT_CODE = "TRANS";
	String MANIFEST_TYPE_PURE = "PURE";
	String SERVICED_CITY = "servicedCity";
	String TRANSHIPMENT_CITY = "transCity";
	String SEARCH_CLICKED = "clkOnSearch";
	// manifest status
	String OPEN = "O";
	String CLOSE = "C";
	String STATUS = "status";

	String PROCESS_CODE_OBDX = "OBDX";
	String PROCESS_CODE_OBPC = "OBPC";
	String PROCESS_CODE_BOUT = "BOUT";
	String PROCESS_CODE_OMBG = "OMBG";
	String PROCESS_CODE_TPBP = "TPBP";
	String PROCESS_CODE_TPDX = "TPDX";
	String PROCESS_CODE_OPKT = "OPKT";

	String OFFICE_TYPE_CODE_HO = "HO";
	String OFFICE_TYPE_CODE_BO = "BO";

	// Query Names
	String QRY_DELETE_CONSIGNMENT = "deleteConsignment";
	String QRY_GET_MANIFEST_NO_COUNT = "getManifestNoCount";
	String QRY_IS_CONSGNMENT_NO_MANIFESTED = "isConsgnNoManifested";
	String QRY_IS_CONSGNMENT_NO_MANIFESTED_FOR_BRANCH = "isConsgnNoManifestedForBranchManifest";
	String QRY_IS_CONSGNMENT_NO_MANIFESTED_FOR_THIRDPARTY = "isConsgnNoManifestedForThirdParty";
	String QRY_IS_CONSGNMENT_NO_IN_MANIFESTED = "isConsgnNoInManifested";
	String QRY_IS_MANIFEST_EMBEDDED_IN = "isManifestEmbeddedIn";
	String QRY_IS_MANIFEST_EMBEDDED_ID_OF_TYPE_IN = "isManifestEmbeddedOfTypeIn";
	String QRY_GET_LOAD_NO = "getLoadNo";
	String QRY_GET_BPL_DTLS_BY_BPL_NO = "getBPLDtlsByBPLNo";
	String QRY_GET_EMBEDED_MANIFEST_DTLS = "getEmbededManifestDtls";
	String QRY_IS_MANIFESTED = "isManifested";
	String QRY_DELETE_COMAIL = "deleteComail";
	String QRY_DELETE_MANIFEST = "deleteManifest";
	String QRY_GET_MANIFEST_PRODUCT_MAP_DTLS = "getManifestProductMapDtls";
	String QRY_GET_MANIFESTPROCESS_DTLS_FOR_COMAIL = "getManifestProcessDtlsforComail";
	String QRY_GET_UNIQUE_MANIFEST = "getUniqueManifest";
	String QRY_GET_UNIQUE_MANIFEST_FOR_CREATION = "getUniqueManifestForCreation";
	String QRY_IS_CONSGNMENT_NO_MISROUTE = "isConsgnNoMisroute";
	String QRY_IS_VALIDATE_SCANED_MANIFEST_NO = "isValidateScanedManifestNo";

	// Query Param Names
	String CONSIGNMENT_NO = "consignmentNo";
	String CONSIGNMENT_ID = "consignmentId";
	String LOGIN_OFFICE_ID = "loginOfficeId";
	String LOGIN_CITY_ID = "loginCityId";
	String PROCESS_ID = "processId";
	String GRID_SCANNED_NO = "gridScannedNo";
	String COMAIL_NO = "comailNo";
	String COMAIL_ID = "comailId";

	String SCANNED_PRODUCT = "scannedProduct";
	String CONSIGNMENT_TYPE = "consignmentType";
	String MANIFEST_OPEN_TYPE = "manifestOpenType";
	String OFFICE_TYPE = "officeType";
	String MANIFEST_STATUS_OPEN = "O";
	String MANIFEST_STATUS_CLOSE = "C";
	String PARAM_MANIFEST_PROCESS_CODE = "manifestProcessCode";
	String PARAM_OPERATING_OFFICE = "operatingOffice";

	// Manifest Direction
	String MANIFEST_DIRECTION = "manifestDirection";
	String SCANNED_TYPE_C = "C";
	String SCANNED_TYPE_M = "M";
	String MANIFEST_TYPE = "manifestType";
	String MANIFEST_SCAN_LEVEL_HEADER = "H";
	String MANIFEST_SCAN_LEVEL_GRID = "G";
	String BAG_LOCK_NO = ManifestUniversalConstants.BAG_LOCK_NO;

	// Out manifest dox constants
	String REGION_TOS = "regionTOs";
	String OFFICE_TYPE_TOS = "officeTypeTOs";
	String COMAIL_START_SERIES = "coMailStartSeries";
	String CONSIGNMENT_NUMBER = "consgNumber";
	String OFFICE_ID = "officeId";
	String CITY_ID = "cityId";
	// Out manifest parcel constants
	String MANIFEST_TYPE_LIST = "manifestTypeTOList";
	String OFFICE_TYPE_LIST = "officeTypeList";
	String MAX_TOLERANCE_ALLOWED = "maxToleranceAllowed";
	String REGION_ID = "regionId";
	String SERIES_TYPE = "seriesType";
	String IS_THIRDPARTY_SCREEN = "isThirdPartyScreen";
	String ALLOWED_CONSG_MANIFESTED_TYPE = "allowedConsgManifestedType";
	String CONSG_SERIES_D = "D";
	String CONSG_SERIES_L = "L";
	String CONSG_SERIES_T = "T";
	String PARAM_IS_MANIFEST_NO_CHECK_REQ = "isManifestNoCheckReq";
	String ORIGIN_OFFICE_ID = "originOfficeId";

	String MANIFEST_PROCESS_CODE = "manifestProcessCode";
	String PARAM_HEADER_MANIFEST_NO = "headerManifestNo";

	String ERROR_OFFICE_TYPE = "PPX0006";

	String ERROR_MANIFEST_TYPE = "PPX0007";

	String VALIDATE_GRID = "ERROROMD001";

	String MANIFEST_PROCESS_CODE_FOR_RTO = "manifestprocessCodeForRTO";

	String PROCESS_ID_FOR_RTO = "19";

	/** Third Party Constants START */
	String PARAM_THIRD_PARTY_NAME = "thirdPartyName";
	String PARAM_THIRD_PARTY_TYPE = "thirdPartyType";
	/** Third Party Constants END */

	String PROCESS_CODE_DSPT = "DSPT";

	
	
}
