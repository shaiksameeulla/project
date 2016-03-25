/**
 * 
 */
package com.ff.universe.drs.constant;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.universe.constant.UdaanCommonConstants;

// TODO: Auto-generated Javadoc
/**
 * The Interface UniversalDeliveryContants.
 * 
 * @author mohammes Purpose: this Constant file holds all global constants,Config params which
 *         are Universally applicable across the module/project(s)
 */
public interface UniversalDeliveryContants {

	/** The qry consg status from delivery. */
	String QRY_CONSG_STATUS_FROM_DELIVERY = "getConsgStatusFromDelivery";

	String QRY_IS_CONSG_EXIST_IN_DELIVERY_BY_PARENT_CN="isConsgExistInDeliveryByParentCn";
	/** The qry get consignment id by consg no. */
	String QRY_GET_CONSIGNMENT_ID_BY_CONSG_NO = "getConsignmentIdByConsgNo";

	/** The qry dox consg frm bkng. */
	String QRY_DOX_CONSG_FRM_BKNG = "getBookedConsgDtlsByConsgNumber";
	
	String QRY_PPX_CONSG_FRM_BKNG="getBookedConsgDtlsByConsgNumberForPpx";

	/** The qry dox manifest consgn dtls for drs. */
	String QRY_DOX_MANIFEST_CONSGN_DTLS_FOR_DRS = "getManifestConsgnDtlsForDrs";
	
	String QRY_CN_DTLS_FOR_THIRD_PARTY_MANIFEST_DTLS_PARENT_CN="getManifestConsgnDtlsForThirdPartyManifestDrsPatentCn";
	String QRY_CN_DTLS_FOR_THIRD_PARTY_MANIFEST_DTLS_CHILD_CN="getManifestConsgnDtlsForThirdPartyManifestDrsChildCn";
	
	String QRY_PPX_MANIFEST_CONSGN_FOR_DRS="getMnfstChildConsgnDtlsForDrsForPPX";
	/** The qry dox manifest consgn dtls for drs. */
	String QRY_DOX_MANIFEST_COMAIL_DTLS_FOR_DRS = "getInManifestedComailDtls";
	
	/** The qry is comail valid. */
	String QRY_IS_COMAIL_VALID = "isComailNumberValid";
	
	/** The qry latest date consgn number. */
	String QRY_LATEST_DATE_CONSGN_NUMBER = "getLatestDateForConsgnNumber";
	
	/** The qry for attempt numebr. */
	String QRY_FOR_ATTEMPT_NUMEBR="getConsignmentCount";
	
	String QRY_FOR_CONG_PRICING_DTLS = "getCongPricingDtls";
	
	String QRY_FOR_CHILD_CONG_PRICING_DTLS = "getChildCongPricingDtls";
	
	String QRY_FOR_PARENT_CONSG_DTLS = "getParentConsgDtlsForDrs";
	
	@Deprecated
	String QRY_GET_CONSIGNMENT_TYPE_FOR_CONSG="getConsignmentTypeForConsg";
	
	String QRY_GET_CONSIGNMENT_TYPE_FOR_PARENT_CONSG="getConsignmentTypeForParentConsg";
	
	String QRY_GET_CONSIGNMENT_TYPE_FOR_CHILD_CONSG="getConsignmentTypeForChildConsg";
	
	String QRY_GET_CONSIGNMENT_STATUS_FROM_CONSG="getConsignmentStatusFromConsg";
	
	String QRY_FOR_CHILD_CONSG_DTLS = "getChildConsgDtlsForDrs";
	
	String QRY_FOR_IS_CONSG_HAVING_CHILD_CNS="isConsgHavingChildCns";
	
	String QRY_FOR_MANFST_DATE_FOR_PARENT_CN="manifetedDateForConsgForParentCn";
	String QRY_FOR_MANFST_DATE_FOR_PARENT_CN_TP_MANIFEST="manifetedDateForConsgForParentCnTPManifest";
	
	String QRY_FOR_MANFST_DATE_FOR_CHILD_CN="manifetedDateForConsgForChildCn";
	
	String  QRY_FOR_MANFST_DATE_FOR_CHILD_CN_TP_MANIFEST="manifetedDateForConsgForChildCnTPManifest";
	
	String QRY_FOR_MANFST_DATE_FOR_COMAIL ="getInManifestedComailDate";
	
	String QRY_FOR_IS_CHILD_CNS="isChildConsg";
	
	String QRY_FOR_OCTROI_AMOUNT_BY_PARENT_CN="getOctroiAmountForConsignment";
	
	String QRY_FOR_OCTROI_AMOUNT_BY_CHILD_CN="getOctroiAmountForConsignmentForChildCn";
	
	String QRY_FOR_IS_COLLECTION_ALREADY_POSTED="IsCollectionDtlsAlreadyPostedByDrsForCn";

	/** The qry param consg. */
	String QRY_PARAM_CONSG = "consignmentNumber";
	String QRY_PARAM_MNFST_NUMBER = "manifestNo";
	
	
	/** The qry param process code. */
	String QRY_PARAM_PROCESS_CODE = "processCode";

	/** The qry param record status. */
	String QRY_PARAM_RECORD_STATUS = "recordStatus";

	/** The qry param logged in office id. */
	String QRY_PARAM_LOGGED_IN_OFFICE_ID = "loggedInOfficeId";

	/** The qry param employee id. */
	String QRY_PARAM_EMPLOYEE_ID = "employeeId";
	
	String QRY_GET_LOGGED_IN_MANIFESTED_NUMBER_FOR_CHILD_CN="getLoggedInManifestedNumberForChildCn";
	String QRY_GET_OUT_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_CHILD_CN_TP_MANIFEST="getManifestedNumberByLoggedInOfficeForChildCnTPManifest";
	
	String QRY_GET_LOGGED_IN_MANIFESTED_NUMBER_FOR_PARENT_CONSG="getLoggedInManifestedNumberForParentConsg";
	
	
	String QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_PARENT_CONSG_TP_MANIFEST="getManifestedNumberByLoggedinOfficeForParentConsgTPManifest";
	
	String QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_PARENT_CONSG_TP_MANIFEST_FOR_MANUAL_DRS="getManifestedNumberByLoggedinOfficeForParentConsgTPManifestForManualDRS";
	
	String QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_CHILD_CONSG_TP_MANIFEST_FOR_MANUAL_DRS = "getManifestedNumberByLoggedInOfficeForChildCnTPManifestForManualDrs";
	
	String QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOGIN_OFFICE="getManifestedTypeForConsgByLoggedInOffice";
	String QRY_GET_MANIFESTED_TYPE_FOR_CONSG_BY_LOG_IN_OFFICE_FOR_CHILD="getManifestedTypeForConsgByLoggedInOfficeForChild";
	

	// #######CONSIGNMENT STATUS############START
	/** The DELIVERY status delivered. */
	String DELIVERY_STATUS_DELIVERED = CommonConstants.DELIVERY_STATUS_DELIVERED;

	/** The DELIVERY status out delivery. */
	String DELIVERY_STATUS_OUT_DELIVERY = CommonConstants.DELIVERY_STATUS_OUT_DELIVERY;

	/** The DELIVERY status PENDING/NON DELIVERED. */
	String DELIVERY_STATUS_PENDING = CommonConstants.DELIVERY_STATUS_PENDING;

	String DELIVERY_STATUS_DESCRIPTION_DELIVERED = "Delivered";

	/** The DELIVERY status out delivery. */
	String DELIVERY_STATUS_DESCRIPTION_OUT_DELIVERY = "Out For Delivery";

	/** The DELIVERY status PENDING/NON DELIVERED. */
	String DELIVERY_STATUS_DESCRIPTION_PENDING = "Pending";
	String RTO_COD_SCREEN_CODE = "RTOCOD";
	
	// #######CONSIGNMENT STATUS############END

	// #######Record STATUS############START
	/** The record status active. */
	String RECORD_STATUS_ACTIVE = "A";

	/** The record status in active. */
	String RECORD_STATUS_IN_ACTIVE = "I";

	// #######Record STATUS############END

	// #######Delivery Type############START
	/** The delivery type home. */
	String DELIVERY_TYPE_HOME = "H";

	/** The delivery type office. */
	String DELIVERY_TYPE_OFFICE = "O";

	/** The delivery type no delivery. */
	String DELIVERY_TYPE_NO_DELIVERY = "N";
	
	String COMPANY_SEAL_AND_SIGN = "CSS";

	// #######Delivery Type############END

	// #######DRS-Config_params Constants START############

	/** The np dox drs config params series. */
	String NP_DOX_DRS_CONFIG_PARAMS_SERIES = "NORMAL_PRIORITY_DOX_DRS";

	/** The np ppx drs config params series. */
	String NP_PPX_DRS_CONFIG_PARAMS_SERIES = "NORMAL_PRIORITY_PPX_DRS";

	/** The ccq drs config params series. */
	String CCQ_DRS_CONFIG_PARAMS_SERIES = "CC_Q_SERIES_DOX_DRS";

	/** The cod lc dox drs config params series. */
	String COD_LC_DOX_DRS_CONFIG_PARAMS_SERIES = "COD_LC_TO_PAY_DOX_DRS";

	/** The cod lc ppx drs config params series. */
	String COD_LC_PPX_DRS_CONFIG_PARAMS_SERIES = "COD_LC_TO_PAY_PPX_DRS";

	/** The rto cod drs config params series. */
	String RTO_COD_DRS_CONFIG_PARAMS_SERIES = CommonConstants.RTO_COD_DRS_CONFIG_PARAMS_SERIES;

	/** The manual drs config params series. */
	String MANUAL_DRS_CONFIG_PARAMS_SERIES = "THIRD_PARTY_MANUAL_DRS";
	
	
	/** The drs missed card reason. */
	String DRS_MISSED_CARD_REASON = "DRS_MISSED_CARD_REASON";

	//for Max cn allowed per screen
	/** The n p dox drs grid max cn. */
	String N_P_DOX_DRS_GRID_MAX_CN = "N_P_DOX_DRS_GRID_MAX_CN";
	
	/** The n p ppx drs grid max cn. */
	String N_P_PPX_DRS_GRID_MAX_CN = "N_P_PPX_DRS_GRID_MAX_CN";
	
	/** The cc q series dox drs grid max cn. */
	String CC_Q_SERIES_DOX_DRS_GRID_MAX_CN = "CC_Q_SERIES_DOX_DRS_GRID_MAX_CN";
	
	/** The cod lc to pay dox drs grid max cn. */
	String COD_LC_TO_PAY_DOX_DRS_GRID_MAX_CN = "COD_LC_TO_PAY_DOX_DRS_GRID_MAX_CN";
	
	/** The cod lc to pay ppx drs grid max cn. */
	String COD_LC_TO_PAY_PPX_DRS_GRID_MAX_CN = "COD_LC_TO_PAY_PPX_DRS_GRID_MAX_CN";
	
	/** The rto cod drs grid max cn. */
	String RTO_COD_DRS_GRID_MAX_CN = "RTO_COD_DRS_GRID_MAX_CN";
	
	/** The bulk pending branch drs grid max cn. */
	String BULK_PENDING_BRANCH_DRS_GRID_MAX_CN = "BULK_PENDING_BRANCH_DRS_GRID_MAX_CN";
	
	/** The bulk pending hub drs grid max cn. */
	String BULK_PENDING_HUB_DRS_GRID_MAX_CN = "BULK_PENDING_HUB_DRS_GRID_MAX_CN";

	

	//for Max cn allowed per screen for Print
	
	String N_P_DOX_DRS_PRINT_MAX_CN = "N_P_DOX_DRS_PRINT_MAX_CN";
	String N_P_PPX_DRS_PRINT_MAX_CN = "N_P_PPX_DRS_PRINT_MAX_CN";
	String CC_Q_SERIES_DOX_DRS_PRINT_MAX_CN = "CC_Q_SERIES_DOX_DRS_PRINT_MAX_CN";
	String COD_LC_TO_PAY_DOX_DRS_PRINT_MAX_CN = "COD_LC_TO_PAY_DOX_DRS_PRINT_MAX_CN";
	String COD_LC_TO_PAY_PPX_DRS_PRINT_MAX_CN = "COD_LC_TO_PAY_PPX_DRS_PRINT_MAX_CN";
	String RTO_COD_DRS_PRINT_MAX_CN = "RTO_COD_DRS_PRINT_MAX_CN";

	// #######DRS-Config_params Constants END############
	/** The qry param delivery status. */
	String QRY_PARAM_DELIVERY_STATUS = "deliveryStatus";
	
	String QRY_PARAM_CN_DELIVERED_STATUS="cnDeliveryStatus";
	
	String QRY_PARAM_DRS_DELIVERED_STATUS="drsDeliveryStatus";
	
	String QRY_PARAM_DRS_STATUS="drsStatus";

	/** The consg origin off id. */
	String CONSG_ORIGIN_OFF_ID = "orgOffId";
	
	String QRY_PARAM_UPDATED_BY="updatedBy";
	
	/** The qry param fs in time. */
	String QRY_PARAM_FS_IN_TIME="fsInTime";
	
	String QRY_PARAM_DELIVERY_DTL_ID="deliveryDetailId";

	String QRY_PARAM_CONSG_ID="consgId";

	/** The drs discarded yes. */
	String DRS_DISCARDED_YES = "Y";

	/** The drs discarded no. */
	String DRS_DISCARDED_NO = "N";

	/** The qry get delivered cn details. */
	String QRY_GET_DELIVERED_CN_DTLS = "getDeliveredConsignmentDtls";
	
	/** The drs consigment. */
	String DRS_CONSIGMENT="Consignment";
	
	/** The drs comail. */
	String DRS_COMAIL="Co-mail";
	String DRS_MANIFEST_TYPE="Third party manifest";
	String DRS_FOR_MANIFEST_NO="for Manifest NO: ";
	
	
	/** The drs parent consg type. these are only useful for PPX consg types */
	String DRS_PARENT_CONSG_TYPE="P";
	
	/** The drs child consg type.these are only useful for PPX consg types */
	String DRS_CHILD_CONSG_TYPE="C";
	
	/** The qry param consg. */
	String QRY_PARAM_DATE = "currentDate";
	
	/** The qry get delivered cn details. */
	String QRY_GET_ALL_DELIVERED_CN_DTLS_BY_DATE = "getAllDeliverdConsgDtlsByDate";
	
	String QRY_DRS_DTLS_BY_STATUS="getDrsDtlsByConsgStatus";
	
	String QRY_DRS_DTLS_CHILD_CN_BY_STATUS="getDrsDtlsByChildConsgStatus";
	
	String QRY_UPDATE_CONSG="updateConsgDtlsByConsg";
	
	String QRY_PARAM_DLV_TIME="deliveryDateTime";

	String QRY_GET_DRS_DTLS_BY_CONSG_NO = "getDrsDtlsByConsgNo";
	
	String QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION = "getDrsDtlsForCollectionIntegration";
	
	String QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION_FOR_MISC_CHARGES="getDrsDtlsForCollectionIntegrationForMiscCharges";
	String QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION_FOR_BA_AMOUNT="getDrsDtlsForCollectionIntegrationForBaAmount";
	
	String QRY_GET_CN_STATUS_FROM_CONSG_FOR_PARENT = "getConsignmentStatusFromConsgForParent";
	
	String QRY_GET_CN_STATUS_FROM_CONSG_FOR_CHILD = "getConsignmentStatusFromConsgForChild";
	
	String QRY_GET_DRS_DTLS_FOR_EXPENSE_TYPE_COLLECTOIN = "getDrsDtlsForExpenseTypeCollectoin";
	
	
	String PARAM_CONSG_NO = "consgNo";
	
	String DELIVERY_CUSTOMER_TYPE="";
	
	String DELIVERY_BA_TYPE="BV";
	
	String DELIVERY_FRANCHISEE_TYPE="FR";
	
	String DELIVERY_CO_COURIER_TYPE=UdaanCommonConstants.VENDOR_TYPE_CO_COURIER;
	
	String QRY_PARAM_YESTERDAY_DATE="yesterdayDate";
	String QRY_PARAM_TODAY_DATE="todayDate";
	
	String QRY_PARAM_COLLECTION_STATUS="collectionStatus";
	
	String COLLECTION_STATUS_FLAG_YES="Y";
	String COLLECTION_STATUS_FLAG_NO="N";
	
	String QRY_UPDATE_DRS_COLLECTION="updateDrsDtlsForCollection";
	
	String QRY_UPDATE_ALL_CHILD_CN_FOR_COLLECTION="updateDrsDtlsByParentCnForCollection";
	
	String QRY_PARAM_FROM_DATE="fromDate";
	
	String QRY_PARAM_TO_DATE="toDate";
	String QRY_PARAM_SCREEN_CODE="drsScreenCode";
	
}
