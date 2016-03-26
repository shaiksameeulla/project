/**
 * 
 */
package com.ff.web.drs.common.constants;

import com.ff.universe.drs.constant.UniversalDeliveryContants;

/**
 * @author mohammes
 *Purpose: This constants file holds all Named Query,Query param,Request param related constants,ERROR-RENDERING constants Values.
 */
public interface DrsCommonConstants {

	
	/* ###### Request params  START#######*/

	/** The load number req params. */
	String LOAD_NUMBER_REQ_PARAMS="loadNumberMap";
	
	String CONSIGNEMNT_TYPE_REQ_PARAMS="consTypeMap";

	/** The drs for req params. */
	String DRS_FOR_REQ_PARAMS="drsForMap";
	
	/** The drs party req params. */
	String DRS_PARTY_REQ_PARAMS="partyTypeMap";
	
	/** The drs party req params. */
	String DRS_NON_DLV_REASON_REQ_PARAMS="nonDlvReasonMap";
	/** The drs party req params. */
	String DRS_NON_DLV_REASON_REQ_PARAMS_FOR_BLK="nonDlvReasonMapBlk";

	/** The yp drs req params. */
	String YP_DRS_REQ_PARAMS="ypDrsMap";
	
	/** The delivery type req params. */
	String DELIVERY_TYPE_REQ_PARAMS="dlvTypeMap";
	
	String DELIVERY_STATUS_REQ_PARAMS="dlvStatusMap";
	
	/** The drs seal req params. */
	String DRS_SEAL_REQ_PARAMS="drsSealMap";
	
	/** The drs load type req params. */
	String DRS_LOAD_TYPE_REQ_PARAMS="drsLoadTypeMap";
	
	String DRS_FOR_TYPE = "drsFor";
	String DRS_FOR_TYPE_ID = "Employee/DA Code";
	
	String DRS_TYPE = "drsType";
	String MANUAL_DRS_TYPE_REQ_PARAMS="manualDrsTypeMap";
	
	/** The req param consgnumber. */
	String REQ_PARAM_CONSGNUMBER="consgNumber";
	
	/** The req param drs numebr. */
	String REQ_PARAM_DRS_NUMEBR="drsNumber";
	

	String DLV_FIELD_STAFF_REQ_PARAM="dlvDrsEmpMap";
	

	/** The relations req params. */
	String RELATIONS_REQ_PARAMS = "relationMap";
	
	/** The idproof req params. */
	String ID_PROOF_REQ_PARAMS = "idproofMap";
	
	/** The modeOfPayment req params. */
	String MODE_OF_PAYMENT_REQ_PARAMS = "modeOfPaymentMap";
	

	/* ###### Request params  END#######*/
	
	
	/* ###### ERROR-RENDERING  START#######*/
	/** The dropdown. */
	String DROPDOWN = " Drop Down";
	
	/** The details. */
	String DETAILS = " details";
	
	String CONSIGNMENT_TYPE = " Consignment type";
	
	/** The does not exist. */
	String DOES_NOT_EXIST = " does not exist";
	
	/** The drs for not exist. */
	String DRS_FOR_NOT_EXIST = "DRS-FOR" +DROPDOWN ;
	
	/** The yp drs not exist. */
	String YP_DRS_NOT_EXIST = "YP-DRS" +DROPDOWN  ;
	
	/** The load no not exist. */
	String LOAD_NO_NOT_EXIST = "Load-No" +DROPDOWN  ;
	
	String CN_TYPE_NO_NOT_EXIST = "Consignment Type" +DROPDOWN  ;
	
	String MANUAL_DRS_TYPE_NOT_EXIST = "DRS-TYPE" +DROPDOWN  ;
	
	String PENDING_REASON = "Pending reason";
	
	String PENDING_REASON_NOT_EXIST =PENDING_REASON +DROPDOWN  ;
	
	
	/** The relations not exist. */
	String RELATIONS_NOT_EXIST = "Relations " +DROPDOWN  ;
	
	/** The id proof not exist. */
	String ID_PROOF_NOT_EXIST = "Id-Proof " +DROPDOWN  ;
	
	/** The mode of Details not exist. */
	String MODE_OF_DETAILS_NOT_EXIST = "Mode Of Payment Details " +DROPDOWN  ;
	
	/** The dlv type not exist. */
	String DLV_TYPE_NOT_EXIST = "Delivery Type" +DROPDOWN  ;
	
	String DLV_STATUS_NOT_EXIST = "Delivery Status" +DROPDOWN  ;
	/** The drs seal sign. */
	String DRS_SEAL_SIGN = "DRS Seal & Sign" +DROPDOWN  ;
	
	String DRS_BULK_LOAD_TYPE = "DRS  Type " +DROPDOWN  ;

	/** The resp error msg. */
	String RESP_ERROR_MSG = " Data issue /connectivity issue";
	
	String FIELD_STAFF_NOT_EXIST = "Field-Staff" +DROPDOWN ;
	/** The consignment. */
	String CONSIGNMENT = " Consignment ";
	
	String CONSIGNMENT_ID = " Consignment key";
	
	/** The at line no. */
	String AT_LINE_NO = "at Line : ";
	/** The DRSg. */
	String DRS = " DRS ";
	
	/** The drs generated. */
	String DRS_GENERATED = " generated ";
	
	/** The drs modified. */
	String DRS_MODIFIED = " updated ";
	String CITY = " City ";
	
	/* ###### ERROR-RENDERING  END#######*/
	
	
	/* ###### Query Params START #######*/
	/** The qry param transaction status. */
	String QRY_PARAM_TRANSACTION_STATUS="transactionStatus";

	/** The qry param officeid. */
	String QRY_PARAM_OFFICEID="officeId";
	
	/** The qry param prefix. */
	String QRY_PARAM_PREFIX="prefix";
	
	/** The qry param number length. */
	String QRY_PARAM_NUMBER_LENGTH = "numberLength";
	
	String QRY_PARAM_DISCARD="discard";
	
	String QRY_PARAM_LOAD_NUMBER="loadNumber";
	
	String QRY_PARAM_DT_TO_CENTRAL="dtToCentral";
	
	/** The qry param delivery id. */
	String QRY_PARAM_DELIVERY_ID="deliveryId";
	/** The qry param delivery id. */
	String QRY_PARAM_DELIVERY_DTL_ID=UniversalDeliveryContants.QRY_PARAM_DELIVERY_DTL_ID;
	
	/** The qry param drs number. */
	String QRY_PARAM_DRS_NUMBER="drsNumber";
	String QRY_PARAM_MANIFESTNO="manifestNo";
	
	String QRY_PARAM_FROM_SCREEN_CODE="fromScreenCode";
	/* ###### Query Params END #######*/
	
	
	/* ######NAMED Query NAMES START #######*/
	/** The qry get max drs number. */
	String QRY_GET_MAX_DRS_NUMBER="getMaxDrsNumber";
	
	/** The qry disard delivery header. */
	String QRY_DISARD_DELIVERY_HEADER="discardDeliveryHeader";
	
	/** The qry disard delivery details. */
	String QRY_DISARD_DELIVERY_DETAILS="disardDeliveryDetails";
	
	/** The qry drs details by drs number. */
	String QRY_DRS_DETAILS_BY_DRS_NUMBER="getDrsDetailsByDrsNumber";
	
	String QRY_DRS_DETAILS_BY_DRS_NUMBER_PRINT="getDrsDetailsByDrsNumberForPrint";
	
	String QRY_NAVIGATION_DTLS_BY_DRS_NUMBER="getNavigationDtlsByDrsNumber";
	
	String QRY_GET_THIRD_PARTY_MNFST_DTS_FOR_DRS="getThirdPartyMnfstDtsForDrs";
	String QRY_GET_THIRD_PARTY_MNFST_HEADER_DTS_FOR_DRS="getThirdPartyMnfstDtsHeaderForDrs";
	
	String QRY_GET_CONSIGNMENT_COUNT_OF_MANIFEST_BY_MNFST_NO="getConsignmentCountOfManifestByMnfstNo";
	
	String QRY_IS_PAYMENT_DETAILS_CAPTURED_BY_CN="isPaymentDetailsCapturedByCn";
	
	/** The qry dlv field staff. */
	String QRY_DLV_FIELD_STAFF="getDeliveryEmployess";
	
	String QRY_GET_DRS_FOR_DTLS_BY_DATE="getDrsForDtlsByDate";
	
	String QRY_DRS_BY_OFFICE_AND_EMPLOYEE="getDrsByOfficeAndEmployee";
	
	String QRY_DRS_BY_OFFICE_AND_BA="getDrsByOfficeAndBA";
	
	String QRY_DRS_BY_OFFICE_AND_CC="getDrsByOfficeAndCC";
	
	String QRY_DRS_BY_OFFICE_AND_FR="getDrsByOfficeAndFR";
	
	
	String QRY_DRS_DTLS_BY_DRS_CONSG_NUMBER="getDrsDtlsByConsgAndDrsNumber";
	
	String QRY_UPDATE_DRS_STATUS="updateDrsStatus";
	
	/** The qry get drs dtls by detailid. */
	String QRY_GET_DRS_DTLS_BY_DETAILID="getDrsDtlsByDetailId";
	
	String QRY_MNFSTED_CONSG_DTLS_FOR_RTO_DRS = "getMnfstParntConsgDtlsForRtoDrs";
	
	String QRY_MNFSTED_CHILD_CONSG_DTLS_FOR_RTO_DRS = "getMnfstChildConsgDtlsForRtoDrs";
	
	String QRY_BOOKING_CUSTOMER_DTLS = "getCustomerDtlsByConsgNumFrmBkng";
	
	String QRY_BOOKING_VENDOR_DTLS = "getVendorDtlsByConsgNumFrmBkng";
	
	String QRY_GET_DRS_STATUS_BY_DRS_NUMBER="getDrsStatusByDrsNumber";
	
	/* ######NAMED Query NAMES END #######*/
	String DRS_MSG_PROP_FILE_NAME = "drsNavigator";
	
	
	

}
