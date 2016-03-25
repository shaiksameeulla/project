/**
 * 
 */
package com.ff.admin.constants;

/**
 * @author mohammes
 * 
 */
public interface AdminSpringConstants {

	String STOCK_COMMON_SERVICE = "stockCommonService";
	String STOCK_REQUISTION_SERVICE = "stockRequisitionService";
	String STOCK_ISSUE_SERVICE = "stockIssueService";
	String STOCK_RECEIPT_SERVICE = "stockReceiptService";
	String STOCK_RETURN_SERVICE = "stockReturnService";
	String STOCK_CANCELLATION_SERVICE = "stockCancellationService";
	String STOCK_TRANSFER_SERVICE = "stockTransferService";
	String ROUTE_SERVICED_SERVICE = "routeServicedService";
	
	String ROUTE_SERVICED_COMMON_SERVICE = "routeServicedCommonService";
	
	String AUTO_REQUISITION_SERVICE = "autoRequisitionService";
	String RATE_BENCH_MARK_SERVICE = "rateBenchMarkService";
	String RATE_COMMON_SERVICE = "rateCommonService";
	String BUSINESS_COMMON_SERVICE = "businessCommonService";
	String STOCK_EXCEL_UPLOAD_SERVICE = "stockExcelUploadService";

	String ORGANIZATION_COMMON_SERVICE = "organizationCommonService";

	// Tracking Services
	String CONSIGNMENT_TRACKING_SERVICE = "consignmentTrackingService";
	String BULK_TRACKING_SERVICE = "bulkImportTrackingService";
	String MANIFEST_TRACKING_SERVICE = "manifestTrackingService";
	String GATEPASS_TRACKING_SERVICE = "gatepassTrackingService";
	String MULTIPLE_TRACKING_SERVICE = "multipleTrackingService";

	String VIEW_TRACKING_POPUP = "trackingPopup";
	// Query Constants

	String GET_CHILD_CN_DETAILS = "getChildConsignmentDetails";
	String GET_PROCESS_MAP_DETAILS = "getProcessMapDetails";
	String CONSG_NO = "consgNum";
	String GET_PROCESS_DETAILS = "getProcessDetails";
	String PROCESS_CODE = "processCode";

	String QRY_GET_Type_Name = "getTypeName";
	String TYPENAME_TO = "typeNameTo";

	String QRY_GET_MANIFEST_Type = "getManifestType";
	String Type = "MANIFEST_TRACKING";
	String MANIFEST_NO = "manifestNum";
	String GET_MANIFEST_PROCESS_DETAILS = "getManifestProcessMapDetails";
	String GET_CHILD_MANIFEST_DETAILS = "getChildManifestInformation";
	String MANIFEST_ID = "manifestID";
	String GATEPASS_TYPE = "GATEPASS_TRACKING";
	String GATEPASS_NO = "number";
	String GET_GATEPASS_DETAILS = "getGatepassDetails";

	// Bulk Import Tracking
	String GET_BULK_CONSG_DETAILS = "getBulkConsgDetails";
	String GET_REFNO_DETAILS = "getRefNoDetails";
	String REF_NO = "refNo";
	String INVALID_NUMBER_TYPE = "T001";
	String INVALID_TYPE = "Invalid_type";
	String CONSIGNMENT_EXCEED = "Consignment_exceed";
	String CONSIGNMENT_EXCEED_LIMIT = "Consignment_exceed_limit";

	String CONSG_NUM = "Consignment No";
	String REF_NUM = "Reference No";
	String BOOKING_DATE = "Booking Date & Time";
	String ORIGIN = "Origin";
	String DESTINATION = "Destination";
	String DELIVERY_NUMBER = "DRS Number";
	String DELIVERY_OFFICE = "Delivery Office";
	String THIRD_PARTY_NAME = "Third Party Name";
	String STATUS = "Status";
	String PENDING_REASON = "Pending Reason";
	String DELIVERY_DATE = "Delivery Date";
	String RECEIVER_NAME = "Receiver Name";
	String WEIGHT = "Weight";
	String OGM_NUM = "OGM No.";
	String OGM_DATE = "OGM Date & Time";
	String BPL_NUM = "BPL No.";
	String BPL_DATE = "BPL Date & Time";
	String CD_NUM = "CD No.";
	String CD_DATE = "CD Date & Time";
	String TRANSPORT_NUM = "Transport No.";
	String TRANSPORT_DEPA = "Transport Departure";
	String TRANSPORT_ARR = "Transport Arrival";
	String RECEIVE_DATE = "Receive Date & Time";
	String INMANIFEST_DATE = "In Manifest Date & Time";

	/* Misc. Expense & Collection Services */
	String MEC_COMMON_SERVICE = "mecCommonService";
	String MEC_UNIVERSAL_SERVICE = "mecUniversalService";
	String EXPENSE_ENTRY_SERVICE = "expenseEntryService";
	String LIABILITY_SERVICE = "liabilityPaymentService";
	String CN_COLLECTION_SERVICE = "cnCollectionService";
	String VALIDATE_EXPENSE_SERVICE = "validateExpenseService";
	String PETTY_CASH_REPORT_SERVICE = "pettyCashReportService";

	/* START LEADS */

	String CREATE_LEAD_SERVICE = "createLeadService";
	String LEADS_VIEW_SERVICE = "leadsViewService";
	String LEADS_PLANNING_SERVICE = "leadsPlanningService";
	String LEAD_VALIDATION_SERVICE = "leadValidationService";
	String SEND_EMAIL_SERVICE = "sendEmailService";
	String SEND_SMS_SERVICE = "sendSMSService";
	String LEADS_COMMON_SERVICE = "leadsCommonService";

	/* END LEADS */
	
	/* START ERROR HANDLING */
	String ERROR_HANDLING_SERVICE = "errorHandlingService";
	/* END ERROR HANDLING */

	/* START Billing */
	String INVOICE_RUN_SHEET_PRINTING_SERVICE = "invoiceRunSheetPrintingService";
	String BILLING_COMMON_SERVICE = "billingCommonService";
	String REGION_TO = "regionTo";
	String BILLING_PRINT_SERVICE = "billPrintingService";

	String INVOICE_RUN_SHEET_UPDATE_SERVICE = "invoiceRunSheetUpdateService";

	String RE_BILLING_SERVICE = "reBillingService";

	String RE_BILLING_GDR_SERVICE = "reBillingGDRService";
	
	String CUST_MODIFICATION_SERVICE="custModificationService";
	
	String CN_MODIFICATION_COMMON_SERVICE = "cnModificationCommonService";

	/** The error flag. */
	String ERROR_FLAG = "ERROR";

	/* START Billing */

	String COLOADING_SERVICE = "coloadingService";

	/* START MATER */
	String PINCODE_MASTER_SERVICE = "pincodeMasterService";
	String HOLIDAY_SERVICE = "holidayService";
	/* END MATER */

	String VENDOR_MAPPING_SERVICE = "vendorMappingService";

	/* START COMPLAINTS */
	String COMPLAINTS_BACKLINE_SUMMARY_SERVICE = "complaintsBacklineSummaryService";
	
	String SOLVE_COMPLAINT_SERVICE = "solveComplaintService";
	/* END COMPLAINTS */

	// for Stop Delivery
	String STOP_DELIVERY_SERVICE = "stopDeliveryService";

	/* Complaints START */
	String COMPLAINTS_COMMON_SERVICE = "complaintsCommonService";
	String CRITICAL_COMPLAINT_SERVICE = "criticalComplaintService";
	String CRITICAL_CLAIM_COMPLAINT_SERVICE = "criticalClaimComplaintService";
	/* Complaints END */

	/* START MATER */

	String PAPERWORK_SERVICE = "paperworkService";
	String JOB_SERVICES_SERVICE = "jobServicesService";
	
	/*Bulk Consignment Modification*/
	
	String BULK_CUST_MODIFICATION_SERVICE ="bulkCustModificationService";
	
	String VALIDATE_COLLECTION_SERVICE = "validateCollectionService";
	
}
