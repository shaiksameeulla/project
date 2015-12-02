/**
 * 
 */
package com.dtdc.bodbadmin.utility;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralDataExtractorConstant.
 *
 * @author nisahoo
 */
public interface CentralDataExtractorConstant {
	
	/* Named Query Names */
	/** The get outgoing manifests for branch query. */
	String GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY = "getOutgoingManifestDtlsForBranch";
	
	/** The get outgoing manifests for branch query param. */
	String GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY_PARAM = "officeCode";
	
	/** The GE t_ outgoin g_ manifest s_ fo r_ branc h_ quer y_ para m1. */
	String GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY_PARAM1 = "officeId";
	
	/** The get booking details by consignments query. */
	String GET_BOOKING_DETAILS_BY_CONSIGNMENTS_QUERY = "getBookingDtlsForMultipleConsginments";
	
	/** The get booking details by consignments query param. */
	String GET_BOOKING_DETAILS_BY_CONSIGNMENTS_QUERY_PARAM = "cnNo";
	
	/** The get frbooking details by cn query. */
	String GET_FRBOOKING_DETAILS_BY_CN_QUERY = "getFranchiseeBookingDetails";
	
	/** The get frbooking details by cn query param. */
	String GET_FRBOOKING_DETAILS_BY_CN_QUERY_PARAM = "consignmentNumber";
	
	/** The get cashbooking details by cn query. */
	String GET_CASHBOOKING_DETAILS_BY_CN_QUERY = "getCashBookingDetails";
	
	/** The get cashbooking details by cn query param. */
	String GET_CASHBOOKING_DETAILS_BY_CN_QUERY_PARAM = "consignmentNumber";
	
	/** The get spclcustbooking details by cn query. */
	String GET_SPCLCUSTBOOKING_DETAILS_BY_CN_QUERY = "getSpclCustomerBookingDetails";
	
	/** The get spclcustbooking details by cn query param. */
	String GET_SPCLCUSTBOOKING_DETAILS_BY_CN_QUERY_PARAM = "consignmentNumber";
	
	/** The get dpbooking details by cn query. */
	String GET_DPBOOKING_DETAILS_BY_CN_QUERY = "getDirectPartyBookingDetails";
	
	/** The get dpbooking details by cn query param. */
	String GET_DPBOOKING_DETAILS_BY_CN_QUERY_PARAM = "consignmentNumber";
	
	/** The xmlfile timestamp format. */
	String XMLFILE_TIMESTAMP_FORMAT = "xmlfile.timestamp.format";
	
	/** The manifest inbound file match. */
	String MANIFEST_INBOUND_FILE_MATCH = "manifest.inbound.xmlfilename.matchcriteria";
	
	/** The booking inbound file match. */
	String BOOKING_INBOUND_FILE_MATCH = "booking.inbound.xmlfilename.matchcriteria";
	
	/** The xmlfile extn. */
	String XMLFILE_EXTN =".xml";
	
	/** The zipfile extn. */
	String ZIPFILE_EXTN =".zip";
	
	/** The booking type franchisee booking. */
	String BOOKING_TYPE_FRANCHISEE_BOOKING = "FRBOOKING";
	
	/** The booking type cash booking. */
	String BOOKING_TYPE_CASH_BOOKING = "CASHBOOKING";
	
	/** The booking type special customer booking. */
	String BOOKING_TYPE_SPECIAL_CUSTOMER_BOOKING = "SPLCUSTBOOKING";
	
	/** The booking type direct party booking. */
	String BOOKING_TYPE_DIRECT_PARTY_BOOKING = "DPBOOKING";
	
	/** The read by local success. */
	String READ_BY_LOCAL_SUCCESS = "Y";
	
	/** The extracted data transit status. */
	String EXTRACTED_DATA_TRANSIT_STATUS = "T";
	
	/** The extracted data read status. */
	String EXTRACTED_DATA_READ_STATUS = "R";
	
	/** The extracted data unread status. */
	String EXTRACTED_DATA_UNREAD_STATUS = "U";
	
	/** The extracted data purge status. */
	String EXTRACTED_DATA_PURGE_STATUS = "P";
	
	
	/** The xml data base dir. */
	String XML_DATA_BASE_DIR = "xml.file.base.location";
	/** The booking filename. */
	String BOOKING_FILENAME = "booking.filename";
	
	String MANIFESTFR_FILENAME = "manifestFR.filename";
	
	/** The manifest filename. */
	String MANIFEST_FILENAME = "manifest.filename";
	
	/** The heldup filename. */
	String HELDUP_FILENAME = "heldUp.filename";
	
	/** The temp zip directory. */
	String TEMP_ZIP_DIRECTORY = "xml.file.temp.location";
	
	/** The underscore. */
	String UNDERSCORE = "_";
	
	/** The goods cancellation filename. */
	String GOODS_CANCELLATION_FILENAME = "goodsCancellation.filename";
	
	/** The goods renewal filename. */
	String GOODS_RENEWAL_FILENAME = "goodsRenewal.filename";
	
	/** The dispatch details filename. */
	String DISPATCH_DETAILS_FILENAME = "dispatchDetails.filename";
	
	String DISPATCHFR_DETAILS_FILENAME = "dispatchDetailsFR.filename";
	
	/** The goods issue filename. */
	String GOODS_ISSUE_FILENAME = "goodsIssue.filename";
	
	/** The max fetch size. */
	String MAX_FETCH_SIZE = "query.maxFetchSize";
	
	/** The max fetch size goods issue. */
	String MAX_FETCH_SIZE_GOODS_ISSUE = "query.maxFetchSize.goodsIssue";
	
	/** The max fetch size goods cancellation. */
	String MAX_FETCH_SIZE_GOODS_CANCELLATION = "query.maxFetchSize.goodsCancellation";
	
	/** The max fetch size goods renewal. */
	String MAX_FETCH_SIZE_GOODS_RENEWAL = "query.maxFetchSize.goodsRenewal";
	
	/** The max fetch size booking. */
	String MAX_FETCH_SIZE_BOOKING = "query.maxFetchSize.booking";
	
	/** The max fetch size heldup release. */
	String MAX_FETCH_SIZE_HELDUP_RELEASE = "query.maxFetchSize.heldupRelease";
	
	/** The max fetch size dispacth. */
	String MAX_FETCH_SIZE_DISPACTH = "query.maxFetchSize.dispatch";
	
	/** The max fetch size manifest. */
	String MAX_FETCH_SIZE_MANIFEST = "query.maxFetchSize.manifest";
	
	/** The max fetch size rto. */
	String MAX_FETCH_SIZE_RTO = "query.maxFetchSize.rto";
	
	/** The get bookingdata by office. */
	String GET_BOOKINGDATA_BY_OFFICE ="getBookingDataForBranchCode";
	
	/** The return to origin filename. */
	String RETURN_TO_ORIGIN_FILENAME = "returnToOrigin.filename";
	
	/** The xml file base multiple location. */
	String XML_FILE_BASE_MULTIPLE_LOCATION = "xml.file.base.multiple.location";
	
	/** The max fetch size delivery. */
	String MAX_FETCH_SIZE_DELIVERY = "query.maxFetchSize.delivery";
	
	/** The delivery details filename. */
	String DELIVERY_DETAILS_FILENAME = "deliveryDetails.filename";
	String GET_USERS_BY_OFFICE ="getUserDataForBranchCode";
	/** The get outgoing manifests for branch query bo db admin. */
	String GET_OUTGOING_MANIFESTS_FOR_BRANCH_QUERY_BO_DB_ADMIN = "getOutgoingManifestDtlsForBranchForBODBAdmin";
	 
 	/** The office code. */
 	String OFFICE_CODE = "officeCode";
	 
 	/** The office id. */
 	String OFFICE_ID = "officeId";
	 
 	/** The goods issue process. */
 	String GOODS_ISSUE_PROCESS="GOODS ISSUE";
	 
 	/** The xml data retrieval for branch. */
 	String  XML_DATA_RETRIEVAL_FOR_BRANCH="xml.file.base.blob.retrival";
	 
	 /** The heldup release process. */
 	String HELDUP_RELEASE_PROCESS="Heldup Release";
	 
 	/** The booking process. */
 	String BOOKING_PROCESS="Booking Release";
	 
 	/** The manifest process. */
 	String MANIFEST_PROCESS="Manifest Release";
	 
 	/** The dispatch process. */
 	String DISPATCH_PROCESS="Dispatch Release";
	 
 	/** The dump url session. */
 	String DUMP_URL_SESSION="DUMP_URL";
	
	 
	 //FOR FR-MODULE
	 
	 /** The read by franchisee. */
 	String READ_BY_FRANCHISEE = "readByFranchisee";
	 
 	/** The qry param goods issue id list. */
 	String QRY_PARAM_GOODS_ISSUE_ID_LIST = "goodsIssueIdList";
	 
 	/** The qry param goods cancel id list. */
 	String QRY_PARAM_GOODS_CANCEL_ID_LIST = "goodsCancelIdList";
	 
 	/** The qry param goods renewal id list. */
 	String QRY_PARAM_GOODS_RENEWAL_ID_LIST = "goodsRenewalIdList";
	 
	 /** The qry update goods issue for fr module. */
 	String QRY_UPDATE_GOODS_ISSUE_FOR_FR_MODULE = "updateReadByFranchiseeForGoodsIssue";
	 
 	/** The qry update goods cancel for fr module. */
 	String QRY_UPDATE_GOODS_CANCEL_FOR_FR_MODULE = "updateReadByFranchiseeForGoodsCancel";
	 
 	/** The qry update goods renewal for fr module. */
 	String QRY_UPDATE_GOODS_RENEWAL_FOR_FR_MODULE = "updateReadByFranchiseeForGoodsRenewal";
	 
	 /** The qry get goods issue dtls for fr module. */
 	String QRY_GET_GOODS_ISSUE_DTLS_FOR_FR_MODULE = "getGoodsIssueDtlsForFrModule";
	 
 	/** The qry get goods renwal dtls for fr module. */
 	String QRY_GET_GOODS_RENWAL_DTLS_FOR_FR_MODULE ="getGoodsRenewalDataByFranchiseeCode";
	 
 	/** The qry get goods cancel dtls for fr module. */
 	String QRY_GET_GOODS_CANCEL_DTLS_FOR_FR_MODULE ="getGoodsCanclDataByFranchiseeCode";
	 
	 /** The franchisee code. */
 	String FRANCHISEE_CODE = "franchiseeCode";
	 
	 /** The goods cancellation process. */
 	String GOODS_CANCELLATION_PROCESS="GOODS CANCELLATION";
	 
 	/** The goods renewal process. */
 	String GOODS_RENEWAL_PROCESS="GOODS RENEWAL";
	 
	 /** The fr module. */
 	String FR_MODULE="FR";
	 String USER_FILENAME="user.filename";		 
	 String SERVICE_CLASS_NAME = "CentralRequestProcessorService";
	 String SERVICE_NAME = "requestProcessorService";
	 String LOAD_DATA_FOR_BRANCH_METHOD = "loadDataForBranch";
	 
	 String GET_BOOKINGDATA_BY_FRANCHISEE ="getBookingDataByFranchiseeCode";
	 	 
	 String GET_MANIFEST_DATA_BY_FRANCHISEE ="getManifestDataByFranchiseeCode";
	 
	 String FR_SYNC_SUCCESS = "Y";
	 
	 String DELIVERY_PROCESS="Delivery Release";
	 
	 String GET_DELIVERYDATA_BY_FRANCHISEE ="getDeliveryDataByFranchiseeCode";
	 
	 String DELIVERY_FR_FILENAME = "deliveryForFR.filename";
	}	
