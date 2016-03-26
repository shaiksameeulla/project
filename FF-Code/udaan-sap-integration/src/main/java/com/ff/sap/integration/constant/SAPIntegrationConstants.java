package com.ff.sap.integration.constant;

/**
 * @author cbhure
 * 
 */
public interface SAPIntegrationConstants {

	// added by Aasma
	String STOCK_REQ_NO = "requisitionNumber";
	String STOCK_RECEIPT_NO = "acknowledgementNumber";
	String STOCK_ISSUE_NO = "stockIssueNumber";
	String STOCK_TRANSFER_NO = "stockTransferNumber";
	String STOCK_RETURN_NO = "returnNumber";
	String COLOADER_TRANSACTION_NUMBER = "transactionNumber";

	// Stock
	String ZERO_DECIMAL_VALUE = "0.0";

	String QRY_PARAM_REQ_DETAILS_FOR_SAP = "getRequisitionDetailsForSAP";

	String QRY_PARAM_REQ_DTLS_FOR_SAP_RHO_EXT = "getRequisitionDtlsForSAPRHOExternal";

	String QRY_PARAM_ISSUE_DETAILS_FOR_SAP = "getStockIssueDetailsForSAP";

	String QRY_PARAM_RECEIPT_DETAILS_FOR_SAP = "getStockReceiptDetailsForSAP";

	String QRY_PARAM_UPDATE_RECEIPT_DTLS_FOR_SAP = "updateStockReceiptDetailsForSAP";

	String QRY_PARAM_UPDATE_REQ_DTLS = "updateRequisitionDetailsForSAP";

	String QRY_PARAM_UPDATE_ISSUE_DTLS = "updateStockIssueDetailsForSAP";

	String QRY_PARAM_GET_STOCK_ISSUE_FROM_STAGING = "getStockIssueDtlsFromSAPIssue";

	String QRY_PARAM_UPDATE_STOCK_ISSUE_IN_STAGING = "updateStockIssueSAPFlag";

	String QRY_PARAM_CANCELLATION_DETAILS_FOR_SAP = "getStockCancellationDetailsForSAP";

	String QRY_PARAM_UPDATE_CANCELLATION_DTLS = "updateStockCancellationDetailsForSAP";

	String QRY_PARAM_GET_STOCK_RETUN_FROM_CSD = "getStockReturnDtlsFromCSD";

	String QRY_PARAM_UPDATE_STOCK_RETUN_CSD = "updateStockReturnDtlsOfCSD";

	String QRY_PARAM_GET_STOCK_RETUN_FROM_STAGING = "getStockReturnDtlsFromSAPRet";

	String QRY_PARAM_UPDATE_STOCK_RETUN_IN_STAGING = "updateStockReturnSAPFlag";

	String QRY_PARAM_GET_STOCK_REQUISITION_FROM_STAGING = "getStockRequisitionDtlsFromSAPReq";

	String QRY_PARAM_UPDATE_STOCK_REQUISITION_IN_STAGING = "updateStockRequisitionDtlsSAPFlag";

	String QRY_PARAM_GET_STOCK_RECEIPT_FROM_STAGING = "getStockReceiptDtlsFromSAPRecipt";

	String QRY_PARAM_UPDATE_STOCK_RECEIPT_IN_STAGING = "updateStockReceiptDtlsSAPFlag";

	String QRY_PARAM_GET_STOCK_CANCEL_FROM_STAGING = "getStockCancelDtlsFromSAPCancel";

	String QRY_PARAM_UPDATE_STOCK_CANCEL_IN_STAGING = "updateStockCancelDtlsSAPFlag";

	String QRY_PARAM_UPDATE_MATERIAL_STATUS = "updateMaterialStatus";

	String QRY_PARAM_GET_STOCK_TRANSFER_FROM_CSD = "getStockTransferDtlsForSAP";

	String QRY_PARAM_UPDATE_TRANSFER_DTLS = "updateStockTransferForCSD";

	String QRY_PARAM_GET_STOCK_TRANSFER_FROM_STAGING = "getStockTransferFromStaging";

	String QRY_PARAM_UPDATE_STOCK_TRANSFER_IN_STAGING = "updateStockTransferSAPFlag";

	String DT_SAP_OUTBOUND = "sapStatus";

	String STATUS_FLAG = "statusFlag";

	String TRANSFER_STATUS = "transferStatus";

	String DT_SAP_INBOUND = "sapStatusInBound";

	String POSITION = "position";

	String SAP_TIMESTAMP = "sapTimestamp";

	String CONSG_NO = "consgNumber";

	String STOCK_REQ_DTLS_ID = "stockRequisitionItemDtlsId";

	String STOCK_ISSUE_ID = "stockIssueId";

	String STOCK_RECEIPT_ID = "stockReceiptId";

	String STOCK_CANCELLED_ID = "stockCancelledId";

	String STOCK_CANCELLED_NO = "cancellationNumber";

	String STOCK_RETURN_ID = "stockReturnId";

	String STOCK_TRANSFER_ID = "stockTransferId";

	String PR_CONSOLIDATED = "Y";

	String STOCK_ISSUE_DTLS_ID = "stockIssueItemDtlsId";

	String QRY_PARAM_COLOADER_DETLS_SAP = "getColoaderAirTrainVehicleDtls";

	String QRY_PARAM_UPDATE_COLOADER_DTLS = "updateColoaderDtlsForSAP";

	String QRY_PARAM_GET_COLOADER_DTLS_FROM_STAGING = "getColoaderDtlsFromStaging";

	String QRY_PARAM_UPDATE_COLOADER_IN_STAGING = "updateColoaderDtlsSAPFlag";

	String QRY_PARAM_UPDATE_COLOADER_INVOICE_NO = "updateColoaderInvoiceNo";

	String COLOADER_INV_NO = "inoviceNo";

	String QRY_PARAM_DEACTIVATE_USER_DTLS = "updateUser";

	String USER_ID = "userId";

	String QRY_PARAM_COCOURIER_DETLS_SAP = "getCoCourierDtls";

	String QRY_PARAM_UPDATE_COCOURIER_DTLS = "updateCocourierDtls";

	String DEL_DTLS_ID = "deliveryDetailId";

	String QRY_PARAM_GET_COCOURIER_DTLS_FROM_STAGING = "getCocourierDtlsFromStaging";

	String QRY_PARAM_UPDATE_COCOURIER_IN_STAGING = "updateCocourierDtlsSAPFlag";

	// Miscellaneous

	String QRY_PARAM_EXPENSE_DETAILS_FOR_SAP = "getExpenseDtlsForExpInterface";

	String QRY_PARAM_UPDATE_EXPENSE_DTLS = "updateExpenseDetailsForSAP";

	String QRY_PARAM_GET_EXPENSE_DETAILS_FROM_STAGING = "getExpenseDtlsFromSAPExp";

	String QRY_PARAM_UPDATE_EXPENSE_DTLS_IN_STAGING = "updateExpenseDetailsSAPFlag";

	String QRY_PARAM_EXPENSE_RHO_OFC = "getExpensesRHOOffice";

	String QRY_PARAM_PINCODE_AGAINST_CONSG_NO = "getPincodeAgainstConsgNo";

	String QRY_PARAM_COLLECTION_DETAILS_FOR_SAP = "getCollectionDtlsForExpInterface";

	String QRY_PARAM_UPDATE_COLLECTION_DTLS = "updateCollectionDetailsForSAP";

	String QRY_PARAM_COLLECTION_DETAILS_FROM_STAGING = "getCollectionDtlsFromSAPCollnStaging";

	String QRY_PARAM_UPDATE_COLLECTION_DETAILS_IN_STAGING = "updateCollectionDtlsSAPFlag";

	String QRY_PARAM_LIABILITY_PAYMENT_DETAILS_FOR_SAP = "getLiabilityPaymentDtlsForInterface";

	String QRY_PARAM_UPDATE_LIABILITY_DTLS = "updateLiabilityDtlsForSAP";

	String GET_COLLECTION_CONSIGNMENT = "getCollectionConsg";

	String GET_CASH_BOOKING_CONSIGNMENT = "getCashBookingConsignment";

	String BOOKING_DATE = "bookingDate";

	String BOOKING_OFFICE = "bookingOfficeId";

	String BOOKING_TYPE_VALUE = "CS";

	String PAYMENT_CODE_VALUE = "CA";

	String TX_CODE_CC = "CC";

	String BOOKING_TYPE = "bookingType";

	String PAYMENT_CODE = "paymentCode";

	String GET_CONSG_ID_AGAINST_NO = "getConsgIdAgainstNo";

	String CONSG_NO_LIST = "consgNoList";

	String CONSG_ID_LIST = "consgID";

	String PREV_DATE = "prevDate";

	String CURRENT_DATE = "currentDate";

	String QRY_GET_ALL_BOOKING_OFFICES_OF_CURRENT_DAY = "getAllBookingOfficesOfCurrDate";

	String QRY_PARAM_UPDATE_CASH_BOOKING_RECORDS = "updateCashBookingRecords";

	String BOOKING_START_DATE = "bookingStartDate";

	String BOOKING_END_DATE = "bookingEndDate";

	String QRY_PARAM_GET_CONSG_STATUS_CONSIGNEE = "getConsgStatusConsignee";

	String QRY_PARAM_EXPENSE_DETAILS_COUNT = "getExpenseCount";

	// New COD LC INTERFACE Start
	String GET_BOOKED_COD_LC_CONSG = "getBookedCodLcConsg";

	String GET_PRODUCT_BY_CONSG_SERIES = "getProductByConsgSeries";

	String PRODUCT_ID_LIST = "productIDList";

	String QRY_PARAM_UPDATE_COD_LC_DTLS_FOR_SAP = "updateCODLCEntriesForSAP";

	String CONSG_ID = "consgId";

	String QRY_PARAM_GET_COD_LC_FROM_STAGING = "getCODLCFromStaging";

	String GET_COD_LC_CONSG_STATUS_FROM_STAGING = "getConsgStatusFromStaging";

	String CHK_CONSG_DELIVERED = "isConsgDelivered";

	String QRY_PARAM_UPDATE_CONSG_DELIVERED_STATUS_IN_STAGING = "updateConsgDeliveredStatusInStaging";

	String CONSG_DELIVERED = "consgDelivered";

	String CONSG_NUMBER = "consgNo";

	String QRY_PARAM_UPDATE_CONSG_RTO_STATUS_IN_STAGING = "updateConsgRTOStatusInStaging";

	String RTO_DATE = "rtoDate";

	String RTO_DRS_DATE = "rtoDrsDate";

	String CONSIGNEE_DATE = "consigneeDate";

	String GET_COD_LC_STAGING_CONSG_FOR_RTODRS = "getStagingConsgForRTODRS";

	String QRY_PARAM_UPDATE_CONSG_RTO_DRS_STATUS_IN_STAGING = "updateConsgRTODRSStatusInStaging";

	String QRY_PARAM_UPDATE_CONSIGNEE_DATE_IN_STAGING = "updateConsigneeDate";

	String QRY_PARAM_UPDATE_COD_LC_SAP_STATUS = "updateCODLCStatusFlag";

	String QRY_PARAM_UPDATE_CONSG_COLL_STATUS = "updateConsgCollStatus";

	String COLLECTION_ENTRY_ID = "entryId";
	
	String QRY_PARAM_GET_LIABILITY_ENTRIES_COUNT = "getLiabilityEntriesCount";
	
	String QRY_PARAM_GET_COD_LC_STAGING_COUNT = "getCODLCStagingCount";
	
	String PARAM_SAP_STATUS = "sapStatus";
	
	String PARAM_CONSIGNMENT_NUMBER = "consignmentNumber";
	
	String STATUS_FLAG_M = "M";

	String QRY_PARAM_GET_CONSG_STATUS_DELIVERED = "getConsgStatusDelivered";
	
	String QRY_PARAM_GET_DELIVERY_DETAILS = "getDeliveryDetails";
	
	String QRY_PARAM_GET_CONSG_STATUS_RTO_DRS = "getConsgStatusRTODRS";
	
	String QRY_PARAM_GET_CONSG_STATUS_COUNT_FOR_CONSIGNEE = "getCountOfConsgStatusConsignee";
	
	String QRY_PARAM_GET_COD_LC_DETAILS_BY_CONSG_NO = "getCODLCDtlsByConsgNo";
	
	Integer SAP_USER_ID = 3;
	
	// End

	String QRY_PARAM_UPDATE_LIABILITY_ENTRIES_DTLS = "updateLiabilityEntriesDtlsForSAP";

	String QRY_PARAM_GET_LIABILITY_DETAILS_FROM_STAGING = "getLiabilityDtlsFromStaging";

	String QRY_PARAM_UPDATE_LIABILITY_PAYMENT_IN_STAGING = "updateLiabilityPaymentSAPFlag";

	String EXPENSE_STATUS = "status";

	String EXPENSE_OFC_RHO = "expenseOfficeRho";

	String EXPENSE_ID = "expenseId";

	String SAP_STAGING_ID = "Id";

	String COLOADER_ID = "id";

	String LIABILITY_ID = "liabilityId";

	String COLLECTION_ID = "collectionId";

	String LIABILITY_DTLS_ID = "liabilityDetailId";

	String COLLECTION_STATUS = "status";

	String OFFICE_ID = "officeId";

	String OFFICE_IDS = "officeIds";

	String REPORTING_RHO_ID = "reportingRHOID";

	// SD Interface

	String QRY_PARAM_CONTRACT_DTLS_FOR_SAP = "getContractDtlsForInterface";

	String QRY_PARAM_UPDATE_CONTRACT_DTLS = "updateContractDetailsForSAP";

	String QRY_PARAM_GET_CONTRACT_DETAILS_FROM_STAGING = "getContractDtlsFromStaging";

	String QRY_PARAM_UPDATE_CONTRACT_IN_STAGING = "updateContractDetailsInStaging";

	String QRY_PARAM_UPDATE_CUST_NO_AGAINST_CONTRACT = "updateCustNo";

	String QRY_PARAM_UPDATE_CUST_STATUS_AGAINST_CONTRACT = "updateCustStatus";

	String QRY_PARAM_UPDATE_SHIP_TO_CODE = "updateShipToCode";

	String QRY_PARAM_BILL_SUMMARY_CONSG_DETAILS_FOR_SAP = "getBillConsgSummaryDtlsForSAP";

	String QRY_PARAM_UPDATE_BCS_DTLS_FOR_SAP = "updateBCSDetailsForSAP";

	String QRY_PARAM_UPDATE_INVOICE_NUMBER = "updateInvoiceNumber";

	String BCS_ID = "billingConsignmentSummaryId";

	String QRY_PARAM_GET_BCS_FROM_STAGING = "getBCSFromStaging";

	String QRY_PARAM_UPDATE_BCS_IN_STAGING = "updateBCSInStaging";

	String CONTRACT_ID = "Id";

	String CONTRACT_NO = "contractNo";

	String CUSTOMER_ID = "customerId";

	String CUSTOMER_NO = "customerCode";

	String CURRENT_STATUS = "currentStatus";

	String CONTRACT_STATUS = "contractStatus";

	String RATE_CONTRACT_NO = "rateContractNo";

	String RATE_CONTRACT_ID = "rateContractId";

	String SHIP_TO_CODE = "shippedToCode";

	String QRY_PARAM_UPDATE_SO_NUMBER = "updateSONumber";

	String SO_NUMBER = "salesOrder";

	String INVOICE_NUMBER = "invoiceNumber";

	String INVOICE_ID = "invoiceId";

	String QRY_PARAM_SP_BILL_SALES_ORDER_DTLS = "updateBillSalesorderDtls";

	String QRY_PARAM_UPDATE_INVOICE_STATUS = "updateInvoiceStatus";

	String QRY_PARAM_UPDATE_INVOICE_STATUS_IN_BILL = "updateInvoiceStatusInBill";

	String BILL_STATUS = "billStatus";

	String BILL_NO = "billNo";

	// Common constants

	String EXP_STATUS = "V";

	String COLLN_STATUS = "V";

	String SAP_STATUS = "N";
	String SAP_STATUS_C = "C";
	String SAP_STATUS_I = "I";
	String SAP_STATUS_B = "B";
	String SAP_STATUS_E = "E";

	String DT_TO_BRANCH = "dtToBranch";

	String STATUS = "active";

	String NEW_STATUS = "status";

	String VENDOR_STATUS = "status";

	String EXCEPTION = "exception";

	String EMAILD_ID = "chandrakant.bhure@capgemini.com";

	String PARAM_NAME = "paramName";

	String MAX_CHECK = "SAP_INTGRATION_MAX_DATA_CHECK";
	String MAX_DATA_COUNT = "";

	// Office Master

	String QRY_PARAM_UPDATE_OFC_STATUS = "updateOfficeInBoundStatus";

	String IS_ERROR = "isError";

	String ERR_DESC = "errorDesc";

	// Outstanding Payment Report

	String QRY_PARAM_GET_OUT_STANDING_PAYMENT_DTLS = "getOutStandingPaymentDtls";

	String QRY_PARAM_UPDATE_OUT_STANDING_PAYMENT_DTLS = "updateOutStandingPaymentDtlsFlag";

	// Constant of BILLING CONFIG ADMIN

	String QRY_GET_LIMIT_FOR_BILLING_BATCH = "getConfigurableValueForBilling";

	String BILLING_LIMIT = "paramName";

	String QRY_PARAM_UPDATE_VENDOR_OFFICE_MAPPING = "updateVendorOfficecMapping";

	String UPDATED_BY = "updatedBy";

	String UPDATED_DATE = "updatedDate";

	String ELIGIBLE_CN_COUNT_FOR_BILLING = 
			"SELECT "+"\r\n"+
					"    count(1) as totalCount"+"\r\n"+
					"  FROM "+"\r\n"+
					"    ff_f_consignment ffc"+"\r\n"+
					"    JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"+"\r\n"+
					"    JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"+"\r\n"+
					"    JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"+"\r\n"+
					"    JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"+"\r\n"+
					"    JOIN ff_d_consignment_type fdcnt force index for join (PRIMARY) ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
					"    /* get customer */"+"\r\n"+
					"    LEFT JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffb.CUSTOMER"+"\r\n"+
					"        LEFT JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
					"    /* get rates */"+"\r\n"+
					"    JOIN ff_f_consignment_rate ffcr_booking_rate "+"\r\n"+
					"    ON (ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"+"\r\n"+
					"    LEFT JOIN ff_f_consignment_rate ffcr_rto_rate"+"\r\n"+
					"    ON (ffc.CONSG_ID = ffcr_rto_rate.CONSIGNMENT_ID AND ffcr_rto_rate.RATE_CALCULATED_FOR = 'R')"+"\r\n"+
					"  WHERE "+"\r\n"+
					"      ffc.BILLING_STATUS IN ('TBB','TRB')"+"\r\n"+
					"      AND ("+"\r\n"+
					"    (date(ffc.CREATED_DATE) BETWEEN str_to_date('2014/12/01','%Y/%m/%d') AND str_to_date('2014/12/15','%Y/%m/%d')) AND"+"\r\n"+
					"        /* T Series credit booking consignments which are either delivered or RTO delivered */"+"\r\n"+
					"            ("+"\r\n"+
					"                  fdp.CONSG_SERIES = 'T'  "+"\r\n"+
					"                  AND (ffc.COD_AMT IS NULL OR ffc.COD_AMT = 0)"+"\r\n"+
					"                  AND (fdct.CUSTOMER_TYPE_CODE NOT IN ('BA' ,'BV') OR fdct.CUSTOMER_TYPE_CODE IS NULL)"+"\r\n"+
					"                  -- AND (ffc.BILLING_STATUS = 'TBB' OR ffc.BILLING_STATUS = 'TRB')"+"\r\n"+
					"                  AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"+"\r\n"+
					"                  AND (ffc.CONSG_STATUS = 'D' OR ffc.CONSG_STATUS = 'S')"+"\r\n"+
					"            )"+"\r\n"+
					"            /* Other consignment series fresh/modified/rto bookings */"+"\r\n"+
					"            OR "+"\r\n"+
					"            ("+"\r\n"+
					"                  ("+"\r\n"+
					"                        fdp.CONSG_SERIES != 'T' "+"\r\n"+
					"                        OR (fdp.CONSG_SERIES = 'T' AND ffc.COD_AMT IS NOT NULL AND ffc.COD_AMT != 0)"+"\r\n"+
					"                        OR (fdp.CONSG_SERIES = 'T' AND fdct.CUSTOMER_TYPE_CODE IN ('BA','BV') AND (ffc.COD_AMT IS NULL OR ffc.COD_AMT = 0))"+"\r\n"+
					"                  )"+"\r\n"+
					"                  -- AND (ffc.BILLING_STATUS = 'TBB' OR ffc.BILLING_STATUS = 'TRB')"+"\r\n"+
					"                  AND ( "+"\r\n"+
					"                        ("+"\r\n"+
					"                        ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S'"+"\r\n"+
					"                        AND ("+"\r\n"+
					"                                    /* fresh consignments */"+"\r\n"+
					"                              ("+"\r\n"+
					"                                          ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' "+"\r\n"+
					"                                          AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"+"\r\n"+
					"                                    )"+"\r\n"+
					"                                    /* weight and/or destination modified consignments */"+"\r\n"+
					"                              OR"+"\r\n"+
					"                              ("+"\r\n"+
					"                                          fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC')"+"\r\n"+
					"                                          AND (ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y')"+"\r\n"+
					"                                    )"+"\r\n"+
					"                        )"+"\r\n"+
					"                  )"+"\r\n"+
					"                        /* RTO or RTO Delivered consignments */"+"\r\n"+
					"                        OR"+"\r\n"+
					"                  ("+"\r\n"+
					"                              fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC')"+"\r\n"+
					"                              AND (ffc.CONSG_STATUS = 'R' OR ffc.CONSG_STATUS = 'S')"+"\r\n"+
					"                        )"+"\r\n"+
					"          )"+"\r\n"+
					"            )"+"\r\n"+
					"    )"+"\r\n"+
					"      AND ffc.DEST_PINCODE IS NOT NULL"+"\r\n"+
					"    AND ffb.BOOKING_DATE IS NOT NULL"+"\r\n"+
					"    AND bo.OFFICE_ID IS NOT NULL"+"\r\n"+
					"    AND fdp.PRODUCT_CODE IS NOT NULL"+"\r\n"+
					"    AND ffc.CONSG_STATUS IS NOT NULL"+"\r\n"+
					"    AND ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL"+"\r\n"+
					"      AND ffc.RATE_TYPE IS NOT NULL"+"\r\n"+
					"    AND"+"\r\n"+
					"    ( "+"\r\n"+
					"            (CASE"+"\r\n"+
					"            WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL THEN"+"\r\n"+
					"                  'N'"+"\r\n"+
					"            ELSE"+"\r\n"+
					"                  IF(ffcr_booking_rate.BILLED IS NOT NULL, ffcr_booking_rate.BILLED, 'N')"+"\r\n"+
					"            END = 'N' "+"\r\n"+
					"            )"+"\r\n"+
					"            OR"+"\r\n"+
					"            (CASE"+"\r\n"+
					"            WHEN ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S' THEN"+"\r\n"+
					"                  'NA'  "+"\r\n"+
					"            WHEN ffc.CONSG_STATUS IN ('R','S') AND ffcr_rto_rate.CONSIGNMENT_RATE_ID IS NULL THEN"+"\r\n"+
					"                  'NA'"+"\r\n"+
					"            ELSE"+"\r\n"+
					"                  IF(ffcr_rto_rate.BILLED IS NOT NULL, ffcr_rto_rate.BILLED, 'N')"+"\r\n"+
					"            END = 'N'"+"\r\n"+
					"            )"+"\r\n"+
					"    )"+"\r\n"+
					"    AND"+"\r\n"+
					"    CASE"+"\r\n"+
					"    WHEN (ffb.SHIPPED_TO_CODE IS NOT NULL AND length(ffb.SHIPPED_TO_CODE) > 0) THEN"+"\r\n"+
					"            ffb.SHIPPED_TO_CODE"+"\r\n"+
					"    WHEN (ffb.SHIPPED_TO_CODE IS NULL OR length(ffb.SHIPPED_TO_CODE) = 0) THEN"+"\r\n"+
					"            CASE"+"\r\n"+
					"            WHEN (ffb.CUSTOMER IS NOT NULL AND length(ffb.CUSTOMER) > 0) THEN fdc.CUSTOMER_CODE"+"\r\n"+
					"            WHEN (ffb.CUSTOMER IS NULL OR length(ffb.CUSTOMER) = 0) THEN"+"\r\n"+
					"                  CASE"+"\r\n"+
					"                  WHEN fdbt.BOOKING_TYPE = 'FC' THEN 'FOC'"+"\r\n"+
					"                  ELSE 'CASH'"+"\r\n"+
					"                  END"+"\r\n"+
					"            ELSE 'ELSE 2'"+"\r\n"+
					"            END"+"\r\n"+
					"    ELSE 'ELSE 1'"+"\r\n"+
					"    END NOT IN ('ELSE 1', 'ELSE 2')";

	String ELIGIBLE_CN_FOR_BILLING = 
			"SELECT ffc.*,"+"\r\n"+
			"       fdp.CONSG_SERIES,"+"\r\n"+
			"       NULL AS BOOKING_DATE,"+"\r\n"+
			"       fdp.CONSOLIDATION_WINDOW,"+"\r\n"+
			"       NULL AS BOOKING_TYPE,"+"\r\n"+
			"       fdcnt.CONSIGNMENT_CODE,"+"\r\n"+
			"       NULL AS OFFICE_ID,"+"\r\n"+
			"       fdp.PRODUCT_CODE,"+"\r\n"+
			"       ffcr_booking_rate.CONSIGNMENT_RATE_ID,"+"\r\n"+
			"       fdct.CUSTOMER_TYPE_CODE,"+"\r\n"+
			"       NULL AS BOOKING_TYPE_DESC,"+"\r\n"+
			"       fdp.PRODUCT_NAME,"+"\r\n"+
			"       insuby.INSURED_BY_CODE,"+"\r\n"+
			"       CASE"+"\r\n"+
			"          WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"          THEN"+"\r\n"+
			"             'N'"+"\r\n"+
			"          ELSE"+"\r\n"+
			"             IF(ffcr_booking_rate.BILLED IS NOT NULL,"+"\r\n"+
			"                ffcr_booking_rate.BILLED,"+"\r\n"+
			"                'N')"+"\r\n"+
			"       END"+"\r\n"+
			"          AS BOOKING_RATE_BILLED,"+"\r\n"+
			"       CASE"+"\r\n"+
			"          WHEN ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S'"+"\r\n"+
			"          THEN"+"\r\n"+
			"             'NA'"+"\r\n"+
			"          WHEN     ffc.CONSG_STATUS IN ('R', 'S')"+"\r\n"+
			"               AND ffcr_rto_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"          THEN"+"\r\n"+
			"             'NA'"+"\r\n"+
			"          ELSE"+"\r\n"+
			"             IF(ffcr_rto_rate.BILLED IS NOT NULL, ffcr_rto_rate.BILLED, 'N')"+"\r\n"+
			"       END"+"\r\n"+
			"          AS RTO_RATE_BILLED,"+"\r\n"+
			"       NULL AS SHIP_TO_CODE"+"\r\n"+
			"  FROM ff_f_consignment ffc"+"\r\n"+
			"       JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"+"\r\n"+
			"       --        JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"+"\r\n"+
			"       --        JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"+"\r\n"+
			"       --        JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"+"\r\n"+
			"       JOIN ff_d_consignment_type fdcnt FORCE INDEX for join (PRIMARY)"+"\r\n"+
			"    ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
			"  LEFT JOIN ff_d_insured_by insuby FORCE INDEX for join (PRIMARY)"+"\r\n"+
			"                                  ON insuby.INSURED_BY_ID = ffc.INSURED_BY"+"\r\n"+
			"  /* get customer */"+"\r\n"+
			"  LEFT JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffc.CUSTOMER"+"\r\n"+
			"  LEFT JOIN ff_d_customer_type fdct"+"\r\n"+
			"    ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"  /* get rates */"+"\r\n"+
			"  JOIN ff_f_consignment_rate ffcr_booking_rate"+"\r\n"+
			"    ON (ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND"+"\r\n"+
			"        ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"+"\r\n"+
			"  LEFT JOIN ff_f_consignment_rate ffcr_rto_rate"+"\r\n"+
			"    ON (ffc.CONSG_ID = ffcr_rto_rate.CONSIGNMENT_ID AND"+"\r\n"+
			"        ffcr_rto_rate.RATE_CALCULATED_FOR = 'R')"+"\r\n"+
			"WHERE"+"\r\n"+
			"  ffc.BILLING_STATUS IN ('TBB'"+"\r\n"+
			"                        ,'TRB') AND"+"\r\n"+
			"  (/* T Series credit booking consignments which are either delivered or RTO delivered */"+"\r\n"+
			"   (fdp.CONSG_SERIES = 'T' AND"+"\r\n"+
			"    (ffc.COD_AMT IS NULL OR"+"\r\n"+
			"     ffc.COD_AMT = 0) AND"+"\r\n"+
			"    (fdct.CUSTOMER_TYPE_CODE NOT IN ('BA'"+"\r\n"+
			"                                    ,'BV') OR"+"\r\n"+
			"     fdct.CUSTOMER_TYPE_CODE IS NULL)"+"\r\n"+
			"    AND"+"\r\n"+
			"    (ffc.CONSG_STATUS = 'D' OR"+"\r\n"+
			"     ffc.CONSG_STATUS = 'S')) /* Other consignment series fresh/modified/rto bookings */"+"\r\n"+
			"   OR"+"\r\n"+
			"   ((fdp.CONSG_SERIES != 'T' OR"+"\r\n"+
			"     (fdp.CONSG_SERIES = 'T' AND"+"\r\n"+
			"      ffc.COD_AMT IS NOT NULL AND"+"\r\n"+
			"      ffc.COD_AMT != 0) OR"+"\r\n"+
			"     (fdp.CONSG_SERIES = 'T' AND"+"\r\n"+
			"      fdct.CUSTOMER_TYPE_CODE IN ('BA'"+"\r\n"+
			"                                 ,'BV') AND"+"\r\n"+
			"      (ffc.COD_AMT IS NULL OR"+"\r\n"+
			"       ffc.COD_AMT = 0)))"+"\r\n"+
			"    AND"+"\r\n"+
			"    ((ffc.CONSG_STATUS != 'R' AND"+"\r\n"+
			"      ffc.CONSG_STATUS != 'S' AND"+"\r\n"+
			"      (/* fresh consignments */"+"\r\n"+
			"       (not exists (select 1 from ff_f_billing_consignment ffbc where ffbc.CONSG_ID = ffc.CONSG_ID) AND"+"\r\n"+
			"        ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'"+"\r\n"+
			"                              ) /* weight and/or destination modified consignments */"+"\r\n"+
			"       OR"+"\r\n"+
			"       ("+"\r\n"+
			"  1 =  (SELECT COUNT(1) FROM ff_f_booking b JOIN ff_d_booking_type bt ON bt.BOOKING_TYPE_ID = b.BOOKING_TYPE WHERE b.CONSG_NUMBER = ffc.CONSG_NO AND"+"\r\n"+
			"    bt.BOOKING_TYPE NOT IN ('EB'"+"\r\n"+
			"                                ,'CS' ,'FC')) AND"+"\r\n"+
			"        (exists (select 1 from ff_f_billing_consignment ffbc where ffbc.CONSG_ID = ffc.CONSG_ID) OR"+"\r\n"+
			"         ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'))"+"\r\n"+
			"         )) /* RTO or RTO Delivered consignments */"+"\r\n"+
			"     OR"+"\r\n"+
			"     ((ffc.CONSG_STATUS = 'R' OR"+"\r\n"+
			"       ffc.CONSG_STATUS = 'S') AND"+"\r\n"+
			"     ((not exists (select 1 from ff_f_billing_consignment ffbc where ffbc.CONSG_ID = ffc.CONSG_ID)) OR"+"\r\n"+
			"     (exists (select 1 from ff_f_billing_consignment ffbc where ffbc.CONSG_ID = ffc.CONSG_ID) AND"+"\r\n"+
			"     1 =  (SELECT COUNT(1) FROM ff_f_booking b JOIN ff_d_booking_type bt ON bt.BOOKING_TYPE_ID = b.BOOKING_TYPE WHERE b.CONSG_NUMBER = ffc.CONSG_NO AND"+"\r\n"+
			"    bt.BOOKING_TYPE NOT IN ('EB'"+"\r\n"+
			"                                ,'CS' ,'FC')))))))) AND"+"\r\n"+
			"  ffc.DEST_PINCODE IS NOT NULL AND"+"\r\n"+
			"  fdp.PRODUCT_CODE IS NOT NULL AND"+"\r\n"+
			"  ffc.CONSG_STATUS IS NOT NULL AND"+"\r\n"+
			"  ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL AND"+"\r\n"+
			"  ffc.RATE_TYPE IS NOT NULL AND"+"\r\n"+
			"  ((CASE"+"\r\n"+
			"      WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"      THEN"+"\r\n"+
			"        'N'"+"\r\n"+
			"      ELSE"+"\r\n"+
			"        IF("+"\r\n"+
			"          ffcr_booking_rate.BILLED IS NOT NULL"+"\r\n"+
			"         ,ffcr_booking_rate.BILLED"+"\r\n"+
			"         ,'N')"+"\r\n"+
			"    END = 'N') OR"+"\r\n"+
			"   (CASE"+"\r\n"+
			"      WHEN ffc.CONSG_STATUS != 'R' AND"+"\r\n"+
			"           ffc.CONSG_STATUS != 'S'"+"\r\n"+
			"      THEN"+"\r\n"+
			"        'NA'"+"\r\n"+
			"      WHEN ffc.CONSG_STATUS IN ('R'"+"\r\n"+
			"                               ,'S') AND"+"\r\n"+
			"           ffcr_rto_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"      THEN"+"\r\n"+
			"        'NA'"+"\r\n"+
			"      ELSE"+"\r\n"+
			"        IF("+"\r\n"+
			"          ffcr_rto_rate.BILLED IS NOT NULL"+"\r\n"+
			"         ,ffcr_rto_rate.BILLED"+"\r\n"+
			"         ,'N')"+"\r\n"+
			"    END = 'N'))";

	String QRY_UPDATE_BILLING_STATUS = "updateConsignmentBillingStatus";
	String BILLING_STATUS_RTB = "consignmentbillingStatus";
	String RTB_STATUS = "RTB";
	String CONSIGNMENT_NO = "consgNos";
	String BILLING_CONSOLIDATION_PROC = "callBillingConsolidation";
	String BILLING_STOCK_CONSOLIDATION_PROC = "callBillingStockConsolidation";
	String RRB_STATUS = "RRB";

	String QRY_PARAM_INVOICE = "updateInvoiceStagingStatus";

	String ACC_CUSTOMER = "AC";

	// PickUp Commission Count
	String QRY_PARAM_REQ_DETAILS_FOR_PICKUP_COMMISSION = "getPickUpCommissionDetailsForSAP";

	String QRY_PARAM_PICKUP_DTLS_FROM_STAGING = "getPickUpCommissionCalcultnSAP";

	String QRY_PARAM_UPDATE_PICKUP_COMMISON_DTLS = "updatePickUpCommissioncalculatnInTransactionTable";

	String QRY_PARAM_UPDATE_PICKUP_COMMISON_DTLS_FOR_STAGING = "updatePickUpCommissioncalculatnInStagingTable";

	String COD_LC_CONSG_POSTING_INTERVAL_FIRST = "15";
	String COD_LC_CONSG_POSTING_INTERVAL_SEC = "21";
	String COD_LC_CONSG_POSTING_INTERVAL_FOURTH = "07";
	String FIRST_INTERVAL_BOOKIND_START_DATE = "01";
	String SEC_INTERVAL_BOOKIND_START_DATE = "08";
	String THIRD_INTERVAL_BOOKIND_START_DATE = "16";
	String FOURTH_INTERVAL_BOOKIND_START_DATE = "24";
	String FIRST_INTERVAL_BOOKIND_END_DATE = "07";
	String SEC_INTERVAL_BOOKIND_END_DATE = "15";
	String THIRD_INTERVAL_BOOKIND_END_DATE = "23";

	String QRY_PARAM_UPDATE_COD_LC_CONSG_STAGING_STATUS = "updateCODLCConsgStagingStatus";

	/* SAP PnD Commission Constants. */
	String QRY_GET_SAP_DLV_COMM_DTLS = "getSAPDlvCommissionDtls";

	// Email Template Names
	String STK_CANCELLATION_EMAIL_TEMPLATE_NAME = "stockCancellation.vm";

	String SAP_INTEGRATION_EMAIL_ID = "SAP_INTEGRATION_EMAIL_ID";

	String EXPENSE_NO = "txNumber";

	String COLL_NO = "txnNo";
	
	String BILLING_JOB_MAX_CN = "BILLING_JOB_MAX_CN";
	String BILLING_JOB_MAX_THREAD_LIMIT = "BILLING_JOB_MAX_THREAD_LIMIT";
	String GET_CN_HAVING_NULL_RATE =  
			"SELECT ffc.*,"+"\r\n"+
			"       fdp.CONSG_SERIES,"+"\r\n"+
			"       ffb.BOOKING_DATE,"+"\r\n"+
			"       fdp.CONSOLIDATION_WINDOW,"+"\r\n"+
			"       fdbt.BOOKING_TYPE,"+"\r\n"+
			"       fdcnt.CONSIGNMENT_CODE,"+"\r\n"+
			"       bo.OFFICE_ID,"+"\r\n"+
			"       fdp.PRODUCT_CODE,"+"\r\n"+
			"       ffcr_booking_rate.CONSIGNMENT_RATE_ID,"+"\r\n"+
			"       fdct.CUSTOMER_TYPE_CODE,"+"\r\n"+
			"       fdbt.BOOKING_TYPE_DESC,"+"\r\n"+
			"       fdp.PRODUCT_NAME,"+"\r\n"+
			"       insuby.INSURED_BY_CODE,"+"\r\n"+
			"       CASE"+"\r\n"+
			"          WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"          THEN"+"\r\n"+
			"             'N'"+"\r\n"+
			"          ELSE"+"\r\n"+
			"             IF(ffcr_booking_rate.BILLED IS NOT NULL,"+"\r\n"+
			"                ffcr_booking_rate.BILLED,"+"\r\n"+
			"                'N')"+"\r\n"+
			"       END"+"\r\n"+
			"          AS BOOKING_RATE_BILLED,"+"\r\n"+
			"       NULL AS RTO_RATE_BILLED,"+"\r\n"+
			"       NULL AS SHIP_TO_CODE"+"\r\n"+
			"  FROM ff_f_consignment ffc"+"\r\n"+
			"       JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"+"\r\n"+
			"       JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"+"\r\n"+
			"       JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"+"\r\n"+
			"       JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"+"\r\n"+
			"       JOIN ff_d_consignment_type fdcnt force index (PRIMARY)"+"\r\n"+
			"          ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
			"       LEFT JOIN ff_d_insured_by insuby force index (PRIMARY)"+"\r\n"+
			"          ON insuby.INSURED_BY_ID = ffc.INSURED_BY"+"\r\n"+
			"       /* get customer */"+"\r\n"+
			"       LEFT JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffc.CUSTOMER"+"\r\n"+
			"       LEFT JOIN ff_d_customer_type fdct"+"\r\n"+
			"          ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"       /* get rates */"+"\r\n"+
			"       LEFT JOIN ff_f_consignment_rate ffcr_booking_rate"+"\r\n"+
			"          ON (    ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID"+"\r\n"+
			"              AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"+"\r\n"+
			" WHERE     (   ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"            OR ffcr_booking_rate.GRAND_TOTAL_INCLUDING_TAX IS NULL)"+"\r\n"+
			"       AND ffc.BILLING_STATUS NOT IN ('PFB')"+"\r\n"+
			"       AND ffb.BOOKING_OFF = ffc.ORG_OFF"+"\r\n"+
			"       AND ffc.DEST_PINCODE IS NOT NULL"+"\r\n"+
			"       AND fdp.PRODUCT_CODE IS NOT NULL"+"\r\n"+
			"       AND ffc.CONSG_STATUS IS NOT NULL"+"\r\n"+
			"       AND ffc.RATE_TYPE IS NOT NULL"+"\r\n"+
			"       AND date(ffb.BOOKING_DATE) >= STR_TO_DATE('01/01/2016', '%d/%m/%Y')";
	
	String BA_BILLING_CONSOLIDATION_PROC = "callBABillingConsolidation";
	
	// Business Exceptions
	String ERR_BA_CUST_SHOULD_NOT_HAVE_CONTRACT = "ERR_CUST_001";
	String ERR_INCORRECT_DATA = "ERR_CUST_002";
	String ERR_CONTRACT_NOT_FOUND = "ERR_CUST_003";
	String ERR_CUSTOMER_NOT_FOUND = "ERR_CUST_004";
	String ERR_CONTRACT_CUSTOMER_NUMBER_NULL = "ERR_CUST_005";
	String ERR_NON_CONTRACTUAL_CUSTOMER = "ERR_CUST_006";
	String ERR_CONTRACTUAL_CUSTOMER = "ERR_CUST_007";
	String CREDIT_CUSTOMER_OTHER_THAN_COD = "0001";
	String COD_CUSTOMER = "0002";
	String FRANCHISEE_CUSTOMER = "0004";
	
	String GET_CN_HAVING_RTO_RATE_NULL =  
			"SELECT ffc.*,"+"\r\n"+
			"       fdp.CONSG_SERIES,"+"\r\n"+
			"       ffb.BOOKING_DATE,"+"\r\n"+
			"       fdp.CONSOLIDATION_WINDOW,"+"\r\n"+
			"       fdbt.BOOKING_TYPE,"+"\r\n"+
			"       fdcnt.CONSIGNMENT_CODE,"+"\r\n"+
			"       bo.OFFICE_ID,"+"\r\n"+
			"       fdp.PRODUCT_CODE,"+"\r\n"+
			"       ffcr_booking_rate.CONSIGNMENT_RATE_ID,"+"\r\n"+
			"       fdct.CUSTOMER_TYPE_CODE,"+"\r\n"+
			"       fdbt.BOOKING_TYPE_DESC,"+"\r\n"+
			"       fdp.PRODUCT_NAME,"+"\r\n"+
			"       insuby.INSURED_BY_CODE,"+"\r\n"+
			"       CASE"+"\r\n"+
			"          WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"          THEN"+"\r\n"+
			"             'N'"+"\r\n"+
			"          ELSE"+"\r\n"+
			"             IF(ffcr_booking_rate.BILLED IS NOT NULL,"+"\r\n"+
			"                ffcr_booking_rate.BILLED,"+"\r\n"+
			"                'N')"+"\r\n"+
			"       END"+"\r\n"+
			"          AS BOOKING_RATE_BILLED,"+"\r\n"+
			"       NULL AS RTO_RATE_BILLED,"+"\r\n"+
			"       NULL AS SHIP_TO_CODE"+"\r\n"+
			"  FROM ff_f_consignment ffc"+"\r\n"+
			"       JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"+"\r\n"+
			"       JOIN ff_f_booking ffb ON (ffc.CONSG_NO = ffb.CONSG_NUMBER AND ffb.DT_FROM_OPSMAN != 'R')"+"\r\n"+
			"       JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"+"\r\n"+
			"       JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"+"\r\n"+
			"       JOIN ff_d_consignment_type fdcnt force index (PRIMARY)"+"\r\n"+
			"          ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
			"       JOIN ff_f_consignment_manifested ffcm"+"\r\n"+
			"          ON ffc.CONSG_ID = ffcm.CONSIGNMENT_ID"+"\r\n"+
			"       JOIN ff_f_manifest ffm"+"\r\n"+
			"          ON (ffcm.MANIFEST_ID = ffm.MANIFEST_ID AND ffm.MANIFEST_TYPE = 'R')"+"\r\n"+
			"       LEFT JOIN ff_d_insured_by insuby force index (PRIMARY)"+"\r\n"+
			"          ON insuby.INSURED_BY_ID = ffc.INSURED_BY"+"\r\n"+
			"       /* get customer */"+"\r\n"+
			"       JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffc.CUSTOMER"+"\r\n"+
			"       JOIN ff_d_customer_type fdct"+"\r\n"+
			"          ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
			"       /* get rates */"+"\r\n"+
			"       LEFT JOIN ff_f_consignment_rate ffcr_booking_rate"+"\r\n"+
			"          ON (    ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID"+"\r\n"+
			"              AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'R')"+"\r\n"+
			" WHERE     (   ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL"+"\r\n"+
			"            OR ffcr_booking_rate.GRAND_TOTAL_INCLUDING_TAX IS NULL)"+"\r\n"+
			"       AND ffb.BOOKING_OFF = ffc.ORG_OFF"+"\r\n"+
			"       AND ffc.DEST_PINCODE IS NOT NULL"+"\r\n"+
			"       AND fdp.PRODUCT_CODE IS NOT NULL"+"\r\n"+
			"       AND ffc.CONSG_STATUS IS NOT NULL"+"\r\n"+
			"       AND ffc.RATE_TYPE IS NOT NULL"+"\r\n"+
			"       AND date(ffb.BOOKING_DATE) >= STR_TO_DATE('01/10/2015', '%d/%m/%Y')";

	String QRY_UPDATE_OPSMAN_STATUS =  "updateBookingOpsmanStatus";
	String QRY_GET_STOCK_CONSOLIDATION_DETAILS =  
			"SELECT summary.BOOKING_DATE,"+"\r\n"+
					"       summary.SHIP_TO_CODE,"+"\r\n"+
					"       customer.CUSTOMER_CODE,"+"\r\n"+
					"       summary.DISTRIBUTION_CHANNEL,"+"\r\n"+
					"       ofc.OFFICE_CODE,"+"\r\n"+
					"       summary.SUMMARY_ID,"+"\r\n"+
					"       summary.PRODUCT_CODE,"+"\r\n"+
					"       summary.NO_OF_PICKUPS,"+"\r\n"+
					"       SUM(if(ffbsi.PRICE IS NULL, 0, ffbsi.PRICE)) AS FREIGHT,"+"\r\n"+
					"       0 AS FUEL_SURCHARGE,"+"\r\n"+
					"       0 AS RISK_SURCHARGE,"+"\r\n"+
					"       0 AS PARCEL_HANDLING_CHARGE,"+"\r\n"+
					"       0 AS AIRPORT_HANDLING_CHARGE,"+"\r\n"+
					"       0 AS DOCUMENT_HANDLING_CHARGE,"+"\r\n"+
					"       0 AS COD_CHARGES,"+"\r\n"+
					"       0 AS TO_PAY_CHARGE,"+"\r\n"+
					"       0 AS LC_CHARGE,"+"\r\n"+
					"       0 AS OTHER_CHARGES,"+"\r\n"+
					"       SUM(if(ffbsi.SERVICE_TAX_AMOUNT IS NULL, 0, ffbsi.SERVICE_TAX_AMOUNT))"+"\r\n"+
					"          AS SERVICE_TAX,"+"\r\n"+
					"       SUM("+"\r\n"+
					"          if(ffbsi.EDUCATION_CESS_AMOUNT IS NULL,"+"\r\n"+
					"             0,"+"\r\n"+
					"             ffbsi.EDUCATION_CESS_AMOUNT))"+"\r\n"+
					"          AS EDUCATION_CESS,"+"\r\n"+
					"       SUM("+"\r\n"+
					"          if(ffbsi.HIGHER_EDUCATION_CESS_AMOUNT IS NULL,"+"\r\n"+
					"             0,"+"\r\n"+
					"             ffbsi.HIGHER_EDUCATION_CESS_AMOUNT))"+"\r\n"+
					"          AS SEC_HIGHER_EDU_CESS,"+"\r\n"+
					"       SUM(if(ffbsi.STATE_TAX_AMOUNT IS NULL, 0, ffbsi.STATE_TAX_AMOUNT))"+"\r\n"+
					"          AS STATE_TAX,"+"\r\n"+
					"       SUM("+"\r\n"+
					"          if(ffbsi.SURCHARGE_ON_STATE_TAX_AMOUNT IS NULL,"+"\r\n"+
					"             0,"+"\r\n"+
					"             ffbsi.SURCHARGE_ON_STATE_TAX_AMOUNT))"+"\r\n"+
					"          AS SURCHARGE_ON_STATE_TAX,"+"\r\n"+
					"       SUM("+"\r\n"+
					"          if(ffbsi.GRAND_TOTAL_INCLUDING_TAX IS NULL,"+"\r\n"+
					"             0,"+"\r\n"+
					"             ffbsi.GRAND_TOTAL_INCLUDING_TAX)),"+"\r\n"+
					"       summary.SUMMARY_CATEGORY,"+"\r\n"+
					"       summary.DESTINATION_OFFICE"+"\r\n"+
					"  FROM ff_f_billing_consignment_summary summary"+"\r\n"+
					"       JOIN ff_f_billing_stock_issue ffbsi"+"\r\n"+
					"          ON ffbsi.SUMMARY = summary.SUMMARY_ID"+"\r\n"+
					"       JOIN ff_d_customer customer ON summary.CUSTOMER = customer.CUSTOMER_ID"+"\r\n"+
					"       JOIN ff_d_office ofc ON ofc.office_id = summary.PICKUP_OFFICE_ID"+"\r\n"+
					" WHERE summary.TRANSFER_STATUS = 'N'"+"\r\n"+
					"GROUP BY summary.SUMMARY_ID";
	
	String FIND_APPLICABLE_CONTRACT =  
			"SELECT con.RATE_CONTRACT_ID as rateContractId "+"\r\n"+
					"  FROM ff_f_booking bk"+"\r\n"+
					"       JOIN ff_d_rate_contract con"+"\r\n"+
					"          ON (con.CUSTOMER = bk.customer AND bk.CONSG_NUMBER = ?)"+"\r\n"+
					" WHERE con.RATE_CONTRACT_ID ="+"\r\n"+
					"          (SELECT c.RATE_CONTRACT_ID"+"\r\n"+
					"             FROM ff_d_rate_contract c"+"\r\n"+
					"                  JOIN ff_d_customer cust ON cust.customer_id = c.customer"+"\r\n"+
					"            WHERE     c.CONTRACT_STATUS IN ('A', 'I', 'B' )"+"\r\n"+
					"                  AND cust.customer_id = bk.customer"+"\r\n"+
					"                  AND date(bk.booking_date) >= date(c.VALID_FROM_DATE)"+"\r\n"+
					"           ORDER BY c.VALID_FROM_DATE DESC"+"\r\n"+
					"            LIMIT 1)";
	String SUMMARY_STAGING_INSERTION_PROC = "callSummaryStagingInsertion";

}
