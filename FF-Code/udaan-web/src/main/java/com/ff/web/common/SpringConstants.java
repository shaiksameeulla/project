package com.ff.web.common;

public interface SpringConstants {

	String GLOBAL_SERVICE = "globalService";
	String LOAD_DISPATCH_SERVICE = "loadDispatchService";
	String LOAD_RECEIVE_LOCAL_SERVICE = "loadReceiveLocalService";
	String LOAD_RECEIVE_OUTSTATION_SERVICE = "loadReceiveOutstationService";
	String LOAD_RECEIVE_EDIT_BAG_SERVICE = "loadReceiveEditBagService";
	String LOAD_MANAGEMENT_COMMON_SERVICE = "loadManagementCommonService";
	String LOAD_MANAGEMENT_SERVICE = "loadManagementService";	
	String BOOKING_COMMON_SERVICE = "bookingCommonService";
	String CASH_BOOKING_SERVICE = "cashBookingService";
	String BOOKING_UNIVERSAL_SERVICE = "bookingUniversalService";
	String BA_BOOKING_SERVICE = "baBookingService";
	String CC_BOOKING_SERVICE = "creditCustomerBookingService";
	String EMOTIONAL_BOND_BOOKING_SERVICE = "emotionalBondBookingService";
	String FOC_BOOKING_SERVICE = "focBookingService";
	String BACKDATED_BOOKING_SERVICE = "backdatedBookingService";
	String BULK_BOOKING_SERVICE = "bulkBookingService";
	String BUSINESS_COMMON_SERVICE = "businessCommonService";
	String BOOKING_CONTEXT_SERVICE = "bookingContextService";

	/* START IN MANIFEST */
	String IN_MANIFEST_UNIVERSAL_SERVICE = "inManifestUniversalService";
	String IN_MANIFEST_COMMON_SERVICE = "inManifestCommonService";
	String MBPL_IN_MANIFEST_SERVICE ="inMasterBagManifestService";
	String IN_BAG_MANIFEST_SERVICE = "inBagManifestService";
	String IN_OGM_DOX_SERVICE = "inOgmDoxService";
	
	/* START OUT MANIFEST */
	String OUT_MANIFEST_DOX_SERVICE = "outManifestDoxService";
	String OUT_MANIFEST_UNIVERSAL_SERVICE="outManifestUniversalService";
	String OUT_MANIFEST_COMMON_SERVICE = "outManifestCommonService";
	String OUT_MANIFEST_COMMON_MANIFEST_VALIDATOR = "manifestValidator";
	String OUT_MANIFEST_THIRD_PARTY_DOX = "thirdPartyManifestDoxService";
	String THIRD_PARTY_OUT_MANIFEST_VALIDATOR = "thirdPartyManifestValidator";
	String BPL_OUT_MANIFEST_DOX_SERVICE = "bplOutManifestDoxService";
	String OUT_MANIFEST_PARCEL_SERVICE = "outManifestParcelService";
	String BRANCH_OUT_MANIFEST_DOX_SERVICE = "branchOutManifestDoxService";

	String BPL_BRANCH_OUT_MANIFEST_SERVICE = "bplBranchOutManifestService";
	String OUT_MANIFEST_THIRD_PARTY_BPL = "thirdPartyBPLService";
	String MBPL_OUT_MANIFEST_SERVICE = "mbplOutManifestService";
	String BRANCH_OUT_MANIFEST_PARCEL_SERVICE = "branchOutManifestParcelService";
	String MANIFEST_COMMON_SERVICE = "manifestCommonService";
	/* END OUT MANIFEST */
	String MANIFEST_UNIVERSAL_SERVICE = "manifestUniversalService";
	/* START MISROUTE */
	String MISROUTE_SERVICE = "misrouteService";
	
	/* DRS MODULE BEAN NAME(s) START */
	
	/** The drs common service. */
	String DRS_COMMON_SERVICE = "deliveryCommonService";
	
	/** The drs np dox service. */
	String DRS_NP_DOX_SERVICE = "prepareNormDoxDrsService";
	
	/** The drs np dox service. */
	String DRS_NP_PPX_SERVICE = "prepareNormPpxDrsService";
	
	/** The drs prep rto cod service. */
	String DRS_PREP_RTO_COD_SERVICE = "prepRtoCodDrsService";
	
	/** The drs list service. */
	String DRS_LIST_SERVICE = "listDrsService";
	
	/** The drs cc dox service. */
	String DRS_CC_DOX_SERVICE = "prepareCreditCardService";
	
	/** The drs pending consg service. */
	String DRS_PENDING_CONSG_SERVICE = "pendingDrsService";
	String DRS_MANUAL_SERVICE = "manualDrsService";
	
	/** The drs update np dox service. */
	String DRS_UPDATE_NP_SERVICE="updateNpDrsService";
	
	/** The drs update cc dox service. */
	String DRS_UPDATE_CC_SERVICE="updateCCDrsService";
	
	/** The drs update Cod Lc  service. */
	String DRS_UPDATE_COD_LC_SERVICE="updateCoDLcDrsService";
	
	/** The drs prepare cod lc dox service. */
	String DRS_COD_LC_DOX_SERVICE="prepareCodDoxDrsService";
	
	/** The drs cod lc ppx service. */
	String DRS_COD_LC_PPX_SERVICE="prepareCodPpxDrsService";
	
	String DRS_UPDATE_RTO_SERVICE="updateRtoCodDrsService";
	String DRS_BULK_PENDING_SERVICE = "bulkPendingDrsService";
	
	/* DRS MODULE BEAN NAME(s) END */
	
	String HELD_UP_SERVICE = "heldUpService";
	
	String POD_MANIFEST_COMMON_SERVICE = "podManifestCommonService";
	String POD_MANIFEST_OUTGOING_SERVICE = "outgoingPODManifestService";
	String INCOMING_POD_MANIFEST_SERVICE = "incomingPODManifestService";
	
	/*    START RTH/RTO   */
	String RTH_RTO_MANIFEST_COMMON_SERVICE="rthRtoManifestCommonService";
	String RTH_RTO_VALIDATION_SERVICE = "rthRtoValidationService";
	String RTH_RTO_MANIFEST_PARCEL_SERVICE="rthRtoManifestParcelService";
	String RTH_RTO_MANIFEST_DOX_SERVICE="rthRtoManifestDoxService";
	/*    END RTH/RTO   */
	
	/*    START LEADS   */
	
	String CREATE_LEAD_SERVICE = "createLeadService";
	
	String LEADS_VIEW_SERVICE = "leadsViewService";

	String LEADS_PLANNING_SERVICE="leadsPlanningService";
	
	String LEAD_VALIDATION_SERVICE = "leadValidationService";
	
	String SEND_EMAIL_SERVICE = "sendEmailService";
	
	String SEND_SMS_SERVICE = "sendSMSService";
	
	/*    END LEADS   */
	String COD_RECEIPT_SERVICE="codReceiptService";
	String JOB_SERVICES_SERVICE = "jobServicesService";
	String BATCH_JOB_SERVICE = "jobServicesUniversalService";
	String GEOGRAPHY_COMMON_SERVICE ="geographyCommonService";
}
