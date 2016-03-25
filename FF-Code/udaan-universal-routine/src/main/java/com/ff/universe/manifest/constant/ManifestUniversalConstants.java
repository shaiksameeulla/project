package com.ff.universe.manifest.constant;

/**
 * The Interface ManifestUniversalConstants.
 */
public interface ManifestUniversalConstants {
	// Query Names
	/** The qry get manifest dtls by manifest no. */
	String QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO = "getManifestDtlsByManifestNo";
	
	String QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO_ORIGIN_OFFICE_ID = "getManifestDtlsByManifestNoOriginOffId";
	String QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO_OPERATING_OFFICE = "getManifestDtlsByManifestNoOperatingOffice";
	
	/** The qry update manifest weight. */
	String QRY_UPDATE_MANIFEST_WEIGHT = "updateManifestWeight";
	
	/** The qry update manifest embeddedin dtls. */
	String QRY_UPDATE_MANIFEST_EMBEDDEDIN_DTLS = "updateEnbeddedInDtls";
	
	/** The qry update Was manifest embeddedin dtls. */
	String QRY_UPDATE_MANIFEST_WAS_EMBEDDEDIN_DTLS = "updateWasEmbeddedInDtls";
	
	/** The qry update Was manifest embeddedin dtls. */
	String QRY_DELETE_MANIFEST_MISROUTE= "deleteManifestMisroute";
	
	/** The qry update Was manifest embeddedin dtls. */
	String QRY_GET_REMARKS= "getRemarks";
	
	/** The qry get rfid by rfno. */
	String QRY_GET_RFID_BY_RFNO = "getRfIdByRfNo";
	
	/** The qry get rfno by rfid. */
	String QRY_GET_RFNO_BY_RFID="getRfNoByRfId"; 
	
	/** The qry get manifest process dtls. */
	String QRY_GET_MANIFEST_PROCESS_DTLS = "getManifestProcessDtls";
	
	/** The qry get bpl manifest process dtls. */
	String QRY_GET_BPL_MANIFEST_PROCESS_DTLS = "getBPLManifestProcessDtls";
	
	/** The qry get manifest enbedded in dtls. */
	String QRY_GET_MANIFEST_ENBEDDED_IN_DTLS = "getEmbededManifestDtls";
	
	/** The qry update manifest details. */
	String QRY_UPDATE_MANIFEST_DETAILS = "updateManifestDtls";

	/** The qry is consignment closed. */
	String QRY_IS_CONSIGNMENT_CLOSED = "isConsignmentClosed";
	
	String QRY_GET_COMAIL_COUNT_BY_MANIFESTNO="getComailCountByManifestNo";
	
	String QRY_GET_COMAIL_COUNT_BY_MANIFESTID="getComailCountByManifestId";
	
	String QRY_GET_CONSG_COUNT_BY_MANIFESTID="getConsgCountByManifestId";
	
	String QRY_IS_PARTY_SHIFTED_CONSG = "isPartyShiftedReasonConsg";
	
	String QRY_UPDATE_PROCESS_ID = "updateProcessId";
	
	String QRY_IS_VALIED_BAG_LOCK_NO="isValiedBagLockNo";
	
	String BAG_LOCK_NO="bagLockNo";
	
	// Query Param Names
	/** The manifest no. */
	String MANIFEST_NO = "manifestNo";
	
	/** The manifest weight. */
	String MANIFEST_WEIGHT = "manifestWeight";
	
	/** The manifest embedded in. */
	String MANIFEST_EMBEDDED_IN = "manifestEmbeddedIn";
	
	/** The manifest id. */
	String MANIFEST_ID = "manifestId";

	String OPERATING_OFFICE = "operatingOffice";
		
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
	
	/** The manifest type. */
	String MANIFEST_TYPE = "manifestType";
	
	
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
	
	/** The consignments list. */
	String CONSIGNMENTS_LIST="consignmentsList";

	/** The manifest direction out. */
	String MANIFEST_DIRECTION_OUT = "O";
	
	/** The manifest direction in. */
	String MANIFEST_DIRECTION_IN = "I";
	
	/** The manifest status open. */
	String MANIFEST_STATUS_OPEN = "O";
	
	/** The manifest status closed. */
	String MANIFEST_STATUS_CLOSED = "C";
	
	/** The manifest status closed. */
	String REMARKS = "remarks";

	String CONSIGNMENT_ID = "consgId";
	
	String QRY_GET_MANIFEST_DTLS = "getManifestDtls";
	
	String QRY_GET_MANIFEST_DTLS_IN ="getManifestDtlsForIn";
	
	String OPERATING_LEVEL="operatingLevel";
	
	/** Position in grid */
	String POSITION = "position";
	
	/** Query for updating position by manifestId */
	String QRY_UPDATE_POSITION_BY_MANIFEST_ID = "updatePositionByManifestId";
	
	String QRY_UPDATE_CONSIGNMENT_WEIGHT = "updateConsignmentWeight";
	String PARAM_FINAL_WT = "finalWeight";
	String PARAM_REASON_CODE = "reasonCode";
	String PARTY_SHIFTED_REASON_CODE = "PSD";

	String QRY_UPDATE_REMARKS_MISROUTE = "updateRemarksMisroute";
	String PARAM_VENDOR_CODE="vendorTypeCode";
	String PARAM_REGION_ID="regionId";
	
	String Fetch_Profile_Manifest_Embedded = "manifest-embedded-in";
	String Fetch_Profile_Manifest_Parcel = "manifest-parcel";
	String Fetch_Profile_Manifest_Dox = "manifest-dox";

	String QRY_GET_OUTGOIN_POD_MANIFEST_DTLS = "getManifestDtlsOutgoingPOD";

	String QRY_IS_CONSIGNMENT_CLOSED_FOR_MANIFEST = "isConsignmentClosedForManifest";

	String QRY_GET_NO_OF_ELEMENTS = "getNoOfElementsFromIn";

	String QRY_GET_MANIFEST_ID = "getManifestIdByNo";
	
}
