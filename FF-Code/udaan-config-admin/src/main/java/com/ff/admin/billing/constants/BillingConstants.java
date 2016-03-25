package com.ff.admin.billing.constants;

/**
 * The Interface BillingConstants.
 * 
 * @author narmdr
 */
public interface BillingConstants {

    String SUCCESS = "Success";
    String BILLING_INVOICE_RUNSHEET_NO = "BILLING_INVOICE_RUNSHEET_NO";
    String BILLING_INVOICE_RUNSHEET_NO_START_CODE = "I";

    String FAILURE = "Failure";

    String QRY_GET_RUN_SHEET_DTLS_BY_INVOICE_RUN_SHEET_NO = "getInvoiceRunSheetDtlsByInvoiceRunSheetNo";

    String INVOICE_RUNSHEET_NUMBER = "invoiceRunSheetNumber";

    String PICKUP_BOYS_LIST = "pickupBoysList";

    String USER_INFO = "user";

    String REGION_TO = "regionTo";
    String PRODUCT_TO = "productTo";

    String QRY_CHECK_INVOICE_NO_EXISTS = "checkInvoiceNoExists";

    String INVOICE_RUNSHEET_STATUS = "INVOICE_RUNSHEET_STATUS";

    String RUNSHEET_STATUS = "runSheetStatus";

    String STATIONARY_TYPE = "STATIONARY_TYPE";
    String STATIONERY_TYPES = "stationeryTypes";
    String WARNING_MESSAGE = "warning";
    String URL_CUSTOMER_BILL_PRINT = "customerBillPrint";
    String BILLING_STATUS = "billingStatus";
    String BILLING_STATUS_TBB = "TBB";
    String QRY_GET_CONSIGNMENT_FOR_RATE = "getConsignmentForRate";
    String QRY_GET_LIMIT_FOR_BILLING_BATCH = "getConfigurableValueForBilling";
    String QRY_GET_TOTAL_CN_COUNTFOR_RATE = "getTotalCountConsignmentForRate";

    String REGIONS_LIST = "regionsList";
    String QRY_GET_PRODUCT_BY_ID = "getProductByProductId";
    String PRODUCT_ID = "productId";
    String INVOICE_RUNSHEET_LIST = "invoiceRunsheetList";
    String BILLING_STATUS_RTB = "consignmentbillingStatus";
    String RTB_STATUS = "RTB";
    String QRY_UPDATE_BILLING_STATUS = "updateConsignmentBillingStatus";
    String CONSIGNMENT_NO = "consgNos";
    String BILLING_LIMIT = "paramName";
    String BILLING_CONSOLIDATION_PROC = "callBillingConsolidation";
    String BILLING_STOCK_CONSOLIDATION_PROC = "callBillingStockConsolidation";
    String ELIGIBLE_CN_FOR_BILLING = "";
    String ELIGIBLE_CN_COUNT_FOR_BILLING = "";
    String CUSTOMER_LIST = "customerList";
    String CUSTMODIFICATION_LIST="custmodificationList";
    String BILLING_OFFICE_ID = "officeIds";
    String CITY_ID ="cityId";
    String CITIES_NOT_FOUND = "Q007";
    String ERROR_MSG_CUSTM019 = "Consignment modification will not be allowed for Cash Booking(M&P series),Emotional Bond(E) and FOC booking(N&S series)";
    String NO_CUST_DETAILS_ON_PRODUCT = "Product not configured for Customer";
    String NOINSURENCEMAPPING ="NOINSURENCEMAPPING";
    //bulk consignment modification constants
    String REGION_DTLS_NOT_EXIST = "PM002";
    String PROD_NOT_CONTRACT_BY_CUST ="CUSTM022";
    String NO_CONSG_DETAILS_FOUND ="CUSTM023";
    String CHECK_CONSG_RANGE ="CUSTM024";
    String CONSG_SEIRES_NOT_MATCH ="CUSTM026";
    String ERROR_MSG ="ERROR_MSG";
    String MULTIPLE_TYPE_CN_SELECTION ="M";
    String LIST_OF_FAILED_CNS ="ListOfErrorCns";
    String LIST_OF_ERROR_DESC ="ListOfErrorDetails";
    String HYPHEN_SPACE ="- ";
    
    
    String GET_REBILL_CONSIGNMENT_DATA=
    		"SELECT ffc.CONSG_ID AS consgId,"+"\r\n"+
    		"       ffc.CONSG_NO AS consgNo,"+"\r\n"+
    		"       ffc.CONSG_STATUS consgStatus,"+"\r\n"+
    		"       ffb.BOOKING_OFF AS operaOffice,"+"\r\n"+
    		"       CASE"+"\r\n"+
    		"          WHEN ffbc.FINAL_WEIGHT IS NULL THEN ffc.FINAL_WEIGHT"+"\r\n"+
    		"          ELSE ffbc.FINAL_WEIGHT"+"\r\n"+
    		"       END"+"\r\n"+
    		"          AS finalWt,"+"\r\n"+
    		"       ffc.CUSTOMER AS customer,"+"\r\n"+
    		"       ffct.CONSIGNMENT_CODE AS consgCode,"+"\r\n"+
    		"       ffdp.PINCODE AS destPincode,"+"\r\n"+
    		"       ffdprod.PRODUCT_CODE AS productCode,"+"\r\n"+
    		"       ffdi.INSURED_BY_CODE AS insuredCode,"+"\r\n"+
    		"       ffc.DISCOUNT AS discount,"+"\r\n"+
    		"       ffc.TOPAY_AMT AS topayAmt,"+"\r\n"+
    		"       ffc.SPL_CHG AS splChg,"+"\r\n"+
    		"       ffc.COD_AMT AS codAmt,"+"\r\n"+
    		"       ffc.LC_AMT AS lcAmt,"+"\r\n"+
    		"       ffc.SERVICED_ON AS serviceOn,"+"\r\n"+
    		"       ffc.DECLARED_VALUE AS declareValue,"+"\r\n"+
    		"       ffc.EB_PREFEENCES_CODES AS ebPrefCode,"+"\r\n"+
    		"       ffc.RATE_TYPE AS rateType,"+"\r\n"+
    		"       ffc.EVENT_DATE AS eventDate,"+"\r\n"+
    		"       ffb.BOOKING_DATE AS bookDate,"+"\r\n"+
    		"       ffbc.BILLING_CONSIGNMENT_ID AS billingConsignmentId,"+"\r\n"+
    		"       CASE WHEN ffbc.INVOICE IS NULL THEN 'N' -- rate calculate as per current date and update values in ff_f_consignment_rate table with BILLED=N & update ff_f_consignment BS = TRB & CAWD = Y"+"\r\n"+
    		"                                              ELSE 'Y' -- rate calculate as per current date and store values in re-billing detail table"+"\r\n"+
    		"                                                      END AS bill_generated,"+"\r\n"+
    		"       CASE WHEN ffcrb.CONSIGNMENT_RATE_ID IS NULL THEN 'N' ELSE 'Y' END"+"\r\n"+
    		"          AS booking_Rate,"+"\r\n"+
    		"       CASE WHEN ffcrr.CONSIGNMENT_RATE_ID IS NULL THEN 'N' ELSE 'Y' END"+"\r\n"+
    		"          AS rto_Rate"+"\r\n"+
    		"  FROM ff_f_consignment ffc"+"\r\n"+
    		"       JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"+"\r\n"+
    		"       JOIN ff_d_consignment_type ffct"+"\r\n"+
    		"          ON ffct.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"+"\r\n"+
    		"       JOIN ff_d_pincode ffdp ON ffdp.PINCODE_ID = ffc.DEST_PINCODE"+"\r\n"+
    		"       JOIN ff_d_product ffdprod ON ffdprod.PRODUCT_ID = ffc.PRODUCT"+"\r\n"+
    		"       LEFT JOIN ff_d_insured_by ffdi ON ffdi.INSURED_BY_ID = ffc.INSURED_BY"+"\r\n"+
    		"       LEFT JOIN ff_f_billing_consignment ffbc"+"\r\n"+
    		"          ON ffbc.CONSG_ID = ffc.CONSG_ID"+"\r\n"+
    		"       JOIN ff_f_consignment_rate ffcrb"+"\r\n"+
    		"          ON (    ffcrb.CONSIGNMENT_ID = ffc.CONSG_ID"+"\r\n"+
    		"              AND ffcrb.RATE_CALCULATED_FOR = 'B')"+"\r\n"+
    		"       LEFT JOIN ff_f_consignment_rate ffcrr"+"\r\n"+
    		"          ON (    ffcrr.CONSIGNMENT_ID = ffc.CONSG_ID"+"\r\n"+
    		"              AND ffcrr.RATE_CALCULATED_FOR = 'R')"+"\r\n"+
    		" WHERE     date(ffb.BOOKING_DATE) BETWEEN :startDate AND :endDate"+"\r\n"+
    		"       AND ffc.CUSTOMER = :customer";
    
  String GET_REBILLING_CONSIGNMENT_RATE="select "
                                +"  bcr.CONSIGNMENT_RATE_ID as consignmentRateId," 
                                +"   bcr.CONSIGNMENT as consignmentId,"
                                +"  'O' as contractFor," 
                                +"   bcr.RATE_CALCULATED_FOR as rateCalculated,"
                                +"   sum(bcr.FINAL_SLAB_RATE) as finalSlabRate,"
                                +"   sum(bcr.FUEL_SURCHARGE) as fuelSurcharge,"
                                +"   sum(bcr.RISK_SURCHARGE) as riskSurcharge,"
                                +"   sum(bcr.TO_PAY_CHARGE) as tOPayCharge,"
                                +"   sum(bcr.COD_CHARGES) as codCharges,"
                                +"   sum(bcr.PARCEL_HANDLING_CHARGE) as parcelHandlingCharge,"
                                +"   sum(bcr.AIRPORT_HANDLING_CHARGE) as airportHandlingCharge,"
                                +"   sum(bcr.DOCUMENT_HANDLING_CHARGE) as documentHandlingCharge,"
                                +"   sum(bcr.RTO_DISCOUNT) as rtoDiscount,"
                                +"   sum(bcr.OTHER_OR_SPECIAL_CHARGES) as otherOrSpecialCharge,"
                                +"   sum(bcr.DISCOUNT) as discount,"
                                +"   sum(bcr.SERVICE_TAX) as serviceTax,"
                                +"   sum(bcr.EDUCATION_CESS) as educationCess,"
                                +"   sum(bcr.HIGHER_EDUCATION_CES) as higherEducationCess,"
                                +"   sum(bcr.STATE_TAX) as  stateTax,"
                                +"   sum(bcr.SURCHARGE_ON_STATE_TAX) as surchargeOnStateTax,"
                                +"   sum(bcr.OCTROI) as octroi,"
                                +"   sum(bcr.SERVICE_CHARGE_ON_OCTROI) as octroiServiceCharge,"
                                +"   sum(bcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE) as serviceTaxOnOctroiServiceCharge,"
                                +"   sum(bcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE) as eduCessOnOctroiServiceCharge,"
                                +"   sum(bcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE) as higherEduCessOnOctroiServiceCharge,"
                                +"   sum(bcr.TOTAL_WITHOUT_TAX) as totalWithoutTax,"
                                +"   sum(bcr.GRAND_TOTAL_INCLUDING_TAX) as grandTotalIncludingTax,"
                                +"   sum(bcr.LC_CHARGE) as lcCharge,"
                                +"   sum(bcr.DECLARED_VALUE) as declaredValue,"
                                +"   sum(bcr.SLAB_RATE) as slabRate,"
                                +"   sum(bcr.FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE) as finalSlabRateAddedToRiskSurcharge,"
                                +"   sum(bcr.LC_AMOUNT) as lcAmount,"
                                +"   sum(bcr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE) as stateTaxOnOctroiServiceCharge,"
                                +"   sum(bcr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE) as surchargeOnStateTaxOnoctroiServiceCharge,"
                                +"   sum(bcr.COD_AMOUNT) as codAmount"
                              /*  +"   NOW() AS createdDate,"
                                +"   1 AS createdBy,"
                                +"   NULL AS updatedDate,"
                                +"   NULL AS updatedBy"*/
                                +"   from ff_f_billing_consignment_rate bcr" 
                                +"   where  bcr.CONSIGNMENT=:consignment"
                                +"   group by bcr.RATE_CALCULATED_FOR";
    
    String REBILLING_NO="RE_BILLING_NO";
    String RE_BILLING_NO_START_CODE = "R";
    String REBILLING_NUMBER_DETAILS_SAVED="REB001";
    String REBILLING_DATA_NOT_FOUND="REB002";
    String QRY_GET_REBILLS_DETAILS="getRebillingDetails";
    String CUSTOMER_ID="customerId";
    String BRANCH_ID="branchId";
    String START_DATE="startDate";
    String END_DATE="endDate";
    String QRY_GET_REBILL_JOB_DETAILS="getRebillJobDetails";
    String QRY_UPDATE_REBILLING_STATUS = "updateConsignmentReBillingStatus";
    String TRB_STATUS = "TRB";
    String CAWD="changeWtDest";
    String CAWDYES="Y";
    String REBILLING_HEADERNO="rebillHeaderNo";
    String QRY_UPDATE_REBILLINGHEADER_STATUS="updateRebillingHeader";
    String QRY_TOTAL_REBILLCNS="getTotalRebillCnCount";
    String QRY_OldContractFor="getTotalOldContractCount";
    String QRY_NewContractFor="getTotalNewContractCount";
    
    String CUST_MODIFICATION_SUCCESS = "success";
    String CUST_MODIFICATION_WELCOME = "welcome";
    String CUST_MODIFICATION_ONLY_ALLOWED_AT_RHO ="CUSTM020";
    String NON_INVOICE="NONINVOICE";
    String NO_PRODUCT_SERIES ="NO_PRODUCT_SERIES";
    String CUST_MODIFICATION_ON_RHO_USER ="RHO_USER";
    
    String CUST_MODIFICATION_VALIDATION_DETAIS = "SELECT ffc.CONSG_NO AS consignmentNo,"
    		+" IF (ffc.CONSG_STATUS IN ('R','D'),'Y','N') AS isConsgDelivered,"
    		+" ffc.BILLING_STATUS AS billingStatus,"
    		+" CASE"
    		+" WHEN ffb.CONSG_NUMBER IS NULL"
    		+" THEN 'N'"
    		+" WHEN (bkgType.BOOKING_TYPE IN ('CS', 'EB', 'FC') AND (fdrcc.CUSTOMER_CATEGORY_CODE IS NULL OR fdrcc.CUSTOMER_CATEGORY_CODE <> 'ACC'))"
    		+" THEN 'NO_PRODUCT_SERIES'"
    		+" ELSE 'Y'"
    		+" END"
    		+" AS bookDetails,"
    		+" ffb.BOOKING_DATE AS bkgDate,"
    		+" IFNULL(ffc.IS_EXCESS_CONSG, 'N') AS isExcessConsg,"
    		+" IF(cnCust.CUSTOMER_ID IS NULL, 'N', 'Y') AS custCheck,"
    		+" IF(expense.EXPENSE_ID IS NULL, 'N', 'Y') AS expenseCheck,"
    		+" IF(collection.COLLECTION_ID IS NULL, 'N', 'Y') AS collectionCheck,"
    		+" IF(liaEntry.CONSIGNMENT_ID IS NULL, 'N', 'Y') AS liabilityCheck,"
    		+" IF(sapLiabEntry.CONSG_ID IS NULL, 'N', 'Y') AS liabilitySapCheck,"
    		+" cnCust.CUSTOMER_ID AS custId,"
    		+" cnCust.CUSTOMER_CODE AS custCode,"
    		+" IF(fdrcc.CUSTOMER_CATEGORY_CODE IN ('BA','ACC'), cnCust.CUSTOMER_CODE, IFNULL(ffb.SHIPPED_TO_CODE, fdcpbl.SHIPPED_TO_CODE)) AS shipToCode,"
    		+" cnCust.BUSINESS_NAME AS custName,"
    		+" custType.CUSTOMER_TYPE_CODE AS custTypeCode,"
    		+" ffc.FINAL_WEIGHT AS totalConsignmentWeight,"
    		+" IF(cnType.CONSIGNMENT_CODE = 'PPX', ffc.DECLARED_VALUE, NULL) AS declaredvalue,"
    		+" ffc.ORG_OFF AS officeId,"
    		+" bkgOfc.CITY_ID AS cityId"
    		+" FROM ff_f_consignment ffc"
    		+" LEFT JOIN ff_f_booking ffb ON ffb.CONSG_NUMBER = ffc.CONSG_NO"
    		+" LEFT JOIN ff_d_customer cnCust ON cnCust.CUSTOMER_ID = ffc.CUSTOMER"
    		+" LEFT JOIN ff_d_customer_type custType ON custType.CUSTOMER_TYPE_ID = cnCust.CUSTOMER_TYPE"
    		+" LEFT JOIN ff_d_booking_type bkgType ON ffb.BOOKING_TYPE = bkgType.BOOKING_TYPE_ID"
    		+" LEFT JOIN ff_d_office bkgOfc ON bkgOfc.OFFICE_ID = IFNULL(ffb.BOOKING_OFF, ffc.ORG_OFF)"
    		+" LEFT JOIN ff_d_consignment_type cnType ON ffc.CONSG_TYPE = cnType.CONSIGNMENT_TYPE_ID"

    		+" LEFT JOIN ff_d_rate_customer_category fdrcc ON cnCust.CUSTOMER_CATEGORY = fdrcc.RATE_CUSTOMER_CATEGORY_ID"
    		+" LEFT JOIN ff_d_pickup_delivery_contract fdpdc ON fdpdc.CUSTOMER_ID = cnCust.CUSTOMER_ID"
    		+" LEFT JOIN ff_d_pickup_delivery_location fdpdl ON fdpdl.CONTRACT_ID = fdpdc.CONTRACT_ID"
    		+" LEFT JOIN ff_d_contract_payment_billing_location fdcpbl ON fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"

    		+" LEFT JOIN ff_f_sap_liability_entries sapLiabEntry ON (sapLiabEntry.CONSG_ID = ffc.CONSG_ID"
    		+" 			AND sapLiabEntry.DT_SAP_OUTBOUND = 'C' AND sapLiabEntry.IS_CONSUMED = 'Y')"
    		+" LEFT JOIN ff_f_liability_entries liaEntry ON liaEntry.CONSIGNMENT_ID = ffc.CONSG_ID"
//    		+" -- Commented as suggested by Gouri and Somesh"
//    		+" -- LEFT JOIN ff_f_liability_payment liaPay ON (liaPay.LIABILITY_ID = liaEntry.LIABILITY_ID AND liaPay.DT_SAP_OUTBOUND = 'C')"
    		+" LEFT JOIN ff_f_collection_entries collentry ON collentry.CONSIGNMENT_ID = ffc.CONSG_ID"
    		+" LEFT JOIN ff_f_collection collection ON (collentry.COLLECTION_ID = collection.COLLECTION_ID AND collection.DT_SAP_OUTBOUND = 'C')"
    		+" LEFT JOIN ff_f_expense_entries expEntry ON expEntry.CONSG_ID = ffc.CONSG_ID"
    		+" LEFT JOIN ff_f_expense expense ON (expEntry.EXPENSE_ID = expense.EXPENSE_ID AND expense.DT_SAP_OUTBOUND = 'C')"
    		+" WHERE  ffc.CONSG_NO IN (:consgNo)"
    		+" GROUP BY ffc.CONSG_NO";
    
    String QRY_UPDATE_CUSTMODIFICATION_CONSG="updateConsgForCustModification";
    String QRY_UPDATE_CUSTMODIFICATION_BOOK="updateBookForCustModification";
    String CONSG_ID="consgId";
    String CONSG_NO="consgNo";
    String UPDATED_DATE="updatedDate";
	String UPDATED_BY="updatedBy";
	String CUST_ID="custId";
	String IS_EXCESS_CONSG="isExcessConsg";
	String CUST_DO="custDO";
	String QRY_GET_ALL_BRANCHES_UNDER_REPORTING_HUB="getAllBranchesUnderRepotingHub";
	String OFFICE_ID="officeId";
    String QRY_SHIPPEDTOCODE_NEW_CUSTOMER="getShipToCodeForCustModification";
    //String QRY_BOOKINGTYPE_NEW_CUSTCODE="getcustomerTypeCodeByCustTypeId";
    String NEW_CUSTCODE="custCode";
    String QRY_PARAM_TRANSACTION_STATUS = "transactionStatus";
    String TRANSACTION_STATUS = "A";
    String SHIPPED_TO_CODE="shippedToCode";
    String ACTUAL_WEIGHT="actualWeight";
    String FINAL_WEIGHT="finalWeight";
    String RATE_TYPE="rateType";
    String UPDATESAPLIABILITY_FORCUSTMODIFICATION="updateCustModificationflagForBilling";
    
    String NEW_CUST_CODE="custCode";
//    String QRY_GET_ALL_OFFICES_UNDER_REPORTING_HUB="getAllOfficesUnderReportingHub";
    
    String GET_NON_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH = 
    		"select"+"\r\n"+
    				" fdc1.CUSTOMER_ID as customerId, fdc1.CONTRACT_NO as contractNo, fdc1.CUSTOMER_CODE as customerCode, fdc1.BUSINESS_NAME as businessName, fdc1.CUSTOMER_CODE as customerCode1"+"\r\n"+
    				"  from"+"\r\n"+
    				"    ff_d_office fdo1"+"\r\n"+
    				"join ff_d_customer fdc1 on fdc1.OFFICE_MAPPED_TO = fdo1.OFFICE_ID"+"\r\n"+
    				"join ff_d_customer_type fdct1 on fdct1.CUSTOMER_TYPE_ID ="+"\r\n"+
    				"   fdc1.CUSTOMER_TYPE"+"\r\n"+
    				"   join ff_d_product_customer_type_map fdpctm1"+"\r\n"+
    				"   on fdpctm1.CUSTOMER_TYPE_ID = fdct1.CUSTOMER_TYPE_ID"+"\r\n"+
    				"   join ff_d_product fdp1 on fdp1.PRODUCT_ID = fdpctm1.PRODUCT_ID"+"\r\n"+
    				"   join ff_d_financial_product_series_customer_type_map fdfpsctm1"+"\r\n"+
    				"   on (fdfpsctm1.PRODUCT = fdp1.PRODUCT_ID and"+"\r\n"+
    				"   fdfpsctm1.CUSTOMER_TYPE = fdct1.CUSTOMER_TYPE_ID)"+"\r\n"+
    				"   join ff_d_financial_product fffp1"+"\r\n"+
    				"   on fffp1.FINANCIAL_PRODUCT_ID = fdfpsctm1.FINANCIAL_PRODUCT"+"\r\n"+
    				"  where"+"\r\n"+
    				"    fdo1.OFFICE_ID in (:billingOfficeId) and"+"\r\n"+
    				"    fffp1.FINANCIAL_PRODUCT_ID = :financialProductId and"+"\r\n"+
    				"    fdct1.CUSTOMER_TYPE_CODE in ('BA',"+"\r\n"+
    				"                                 'BV',"+"\r\n"+
    				"                                  'AC') and fffp1.STATUS='A' and fdfpsctm1.STATUS='A' and fdc1.CUR_STATUS = 'A'"+"\r\n"+
    				"";


    	String GET_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH = 
    			" select"+"\r\n"+
    					"			  fdc.CUSTOMER_ID as customerId, fdc.CONTRACT_NO as contractNo, fdc.CUSTOMER_CODE as customerCode, fdc.BUSINESS_NAME as businessName, fdcpbl.SHIPPED_TO_CODE as shippedToCode"+"\r\n"+
    					"			from"+"\r\n"+
    					"			  ff_d_office fdo"+"\r\n"+
    					"			  join ff_d_pickup_delivery_contract fdpdc on fdo.OFFICE_ID = fdpdc.OFFICE_ID"+"\r\n"+
    					"			  join ff_d_pickup_delivery_location fdpdl"+"\r\n"+
    					"			    on fdpdc.CONTRACT_ID = fdpdl.CONTRACT_ID"+"\r\n"+
    					"			  join ff_d_contract_payment_billing_location fdcpbl"+"\r\n"+
    					"			    on fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"+"\r\n"+
    					"			  join ff_d_rate_contract fdrc on fdrc.RATE_CONTRACT_ID = fdcpbl.RATE_CONTRACT"+"\r\n"+
    					"			  join ff_d_customer fdc on fdc.CUSTOMER_ID = fdpdc.CUSTOMER_ID"+"\r\n"+
    					"			  join ff_d_customer_type fdct on fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
    					"			  join ff_d_product_customer_type_map fdpctm"+"\r\n"+
    					"			    on fdpctm.CUSTOMER_TYPE_ID = fdct.CUSTOMER_TYPE_ID"+"\r\n"+
    					"			  join ff_d_product fdp on fdp.PRODUCT_ID = fdpctm.PRODUCT_ID"+"\r\n"+
    					"			  join ff_d_financial_product_series_customer_type_map fdfpsctm"+"\r\n"+
    					"			    on (fdfpsctm.PRODUCT = fdp.PRODUCT_ID and"+"\r\n"+
    					"			        fdfpsctm.CUSTOMER_TYPE = fdct.CUSTOMER_TYPE_ID)"+"\r\n"+
    					"			  join ff_d_financial_product fffp"+"\r\n"+
    					"			    on fffp.FINANCIAL_PRODUCT_ID = fdfpsctm.FINANCIAL_PRODUCT"+"\r\n"+
    					"			where"+"\r\n"+
    					"			  fdo.OFFICE_ID in (:billingOfficeId) and fdc.CUR_STATUS = 'A' and"+"\r\n"+
    					"			  fffp.FINANCIAL_PRODUCT_ID = :financialProductId and fffp.STATUS='A' and fdfpsctm.STATUS='A' and"+"\r\n"+
    					"			  ((fdcpbl.LOCATION_OPERATION_TYPE in ('B',"+"\r\n"+
    					"			                                       'BP') and"+"\r\n"+
    					"			    fdct.CUSTOMER_TYPE_CODE in ('CR',"+"\r\n"+
    					"			                                'CC',"+"\r\n"+
    					"			                                'LC',"+"\r\n"+
    					"			                                'CD',"+"\r\n"+
    					"			                                'GV',"+"\r\n"+
    					"			                                'FR') and"+"\r\n"+
    					"			    fdrc.TYPE_OF_BILLING = 'CBCP') OR"+"\r\n"+
    					"			   (fdcpbl.LOCATION_OPERATION_TYPE in ('B',"+"\r\n"+
    					"			                                       'BP') and"+"\r\n"+
    					"			    fdct.CUSTOMER_TYPE_CODE in ('CR',"+"\r\n"+
    					"			                                'CC',"+"\r\n"+
    					"			                                'LC',"+"\r\n"+
    					"			                                'CD',"+"\r\n"+
    					"			                                'GV',"+"\r\n"+
    					"			                                'FR') and"+"\r\n"+
    					"			    fdrc.TYPE_OF_BILLING in ('DBDP',"+"\r\n"+
    					"			                             'DBCP')) OR"+"\r\n"+
    					"			   (fdcpbl.LOCATION_OPERATION_TYPE = 'BP' and"+"\r\n"+
    					"			    fdct.CUSTOMER_TYPE_CODE = 'RL'))"+"\r\n"+
    					"			group by"+"\r\n"+
    					"			  fdo.OFFICE_ID,"+"\r\n"+
    					"			  fdpdc.CUSTOMER_ID,"+"\r\n"+
    					"			  fdcpbl.SHIPPED_TO_CODE ORDER BY fdc.CUSTOMER_CODE ";

    		String GET_CUSTOMERS_BY_BILLING_BRANCH =  
    				"select "+"\r\n"+
    				"		fdc.CUSTOMER_ID as customerId, fdc.CONTRACT_NO as contractNo, fdc.CUSTOMER_CODE as customerCode, fdc.BUSINESS_NAME as businessName, fdcpbl.SHIPPED_TO_CODE as shippedToCode"+"\r\n"+
    				"		from ff_d_office fdo"+"\r\n"+
    				"		join ff_d_pickup_delivery_contract fdpdc on fdo.OFFICE_ID = fdpdc.OFFICE_ID"+"\r\n"+
    				"		join ff_d_pickup_delivery_location fdpdl on fdpdc.CONTRACT_ID = fdpdl.CONTRACT_ID"+"\r\n"+
    				"		join ff_d_contract_payment_billing_location fdcpbl on fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"+"\r\n"+
    				"		join ff_d_rate_contract fdrc on fdrc.RATE_CONTRACT_ID = fdcpbl.RATE_CONTRACT"+"\r\n"+
    				"		join ff_d_customer fdc on fdc.CUSTOMER_ID = fdpdc.CUSTOMER_ID"+"\r\n"+
    				"		join ff_d_customer_type fdct on fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"+"\r\n"+
    				"		where fdo.OFFICE_ID in (:billingOffice)"+"\r\n"+
    				"		and ((fdcpbl.LOCATION_OPERATION_TYPE in ('B', 'BP')"+"\r\n"+
    				"		    and fdct.CUSTOMER_TYPE_CODE in ('CR','CC','LC','CD','GV','FR')"+"\r\n"+
    				"		    and fdrc.TYPE_OF_BILLING = 'CBCP')"+"\r\n"+
    				"		    OR"+"\r\n"+
    				"		    (fdcpbl.LOCATION_OPERATION_TYPE in ('P', 'BP')"+"\r\n"+
    				"		    and fdct.CUSTOMER_TYPE_CODE in ('CR','CC','LC','CD','GV','FR')"+"\r\n"+
    				"		    and fdrc.TYPE_OF_BILLING in ('DBDP', 'DBCP'))"+"\r\n"+
    				"		    OR"+"\r\n"+
    				"		    (fdcpbl.LOCATION_OPERATION_TYPE = 'BP'"+"\r\n"+
    				"		    and fdct.CUSTOMER_TYPE_CODE = 'RL'))"+"\r\n"+
    				"		group by fdo.OFFICE_ID, fdpdc.CUSTOMER_ID, fdcpbl.SHIPPED_TO_CODE ORDER BY fdc.CUSTOMER_CODE ";



	String GET_CUSTOMERS_BY_BILLING_BRANCH_UNION = 
			"select"+"\r\n"+
			"		 fdc1.CUSTOMER_ID as customerId, fdc1.CONTRACT_NO as contractNo, fdc1.CUSTOMER_CODE as customerCode, fdc1.BUSINESS_NAME as businessName, fdc1.CUSTOMER_CODE as customerCode1"+"\r\n"+
			"		from ff_d_office fdo1"+"\r\n"+
			"		join ff_d_customer fdc1 on fdc1.OFFICE_MAPPED_TO = fdo1.OFFICE_ID"+"\r\n"+
			"		join ff_d_customer_type fdct1 on fdct1.CUSTOMER_TYPE_ID = fdc1.CUSTOMER_TYPE"+"\r\n"+
			"		where fdct1.CUSTOMER_TYPE_CODE in ('BA', 'BV', 'AC') "+"\r\n"+
			"		and fdo1.OFFICE_ID in (:billingOffice)";
    			
	String QRY_BOOKINGTYPE_NEW_CUSTCODE = "select customertype.CUSTOMER_TYPE_CODE as customerTypeCode from ff_d_customer customer " +
			"	left join ff_d_customer_type customertype on customer.customer_type= customertype.customer_type_id " +
			"	where customer.customer_code =:custCode";
	
    String GET_CUSTOMERS_BY_CONTRACT_BRANCHES =" SELECT finalResult.CUSTOMER_ID as customerId," 
			+"	finalResult.CONTRACT_NO as contractNo," 
			+"	finalResult.CUSTOMER_CODE as customerCode,"
			+"	finalResult.BUSINESS_NAME as businessName,"
			+"	finalResult.shippedToCodeTemp as shippedToCode"
			+"	FROM"
			+"	("
			/* contracted customers */
			+"	SELECT"
			+"	fdc.*,"
			+"	fdcpbl.SHIPPED_TO_CODE as shippedToCodeTemp"
			+"	FROM ff_d_office fdo"
			+"	JOIN ff_d_pickup_delivery_contract fdpdc ON fdo.OFFICE_ID =fdpdc.OFFICE_ID"
			+"	JOIN ff_d_pickup_delivery_location fdpdl"
			+"	ON fdpdc.CONTRACT_ID = fdpdl.CONTRACT_ID"
			+"	JOIN ff_d_contract_payment_billing_location fdcpbl"
			+"	ON fdcpbl.PICKUP_DELIVERY_LOCATION = fdpdl.LOCATION_ID"
			+"	JOIN ff_d_rate_contract fdrc ON fdrc.RATE_CONTRACT_ID =fdcpbl.RATE_CONTRACT"
			+"	JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = fdpdc.CUSTOMER_ID"
			+"	JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+"	WHERE"
			+"	fdo.OFFICE_ID IN (:officeIds) AND fdct.CUSTOMER_TYPE_CODE IN ('CR',"
			+"  'CC',"
			+"  'LC',"
			+"	'CD',"
			+"	'GV',"
			+"	'FR') AND"
			+"	fdc.CUR_STATUS = 'A'"
			+"	GROUP BY"
			+"	fdo.OFFICE_ID,"
			+"	fdpdc.CUSTOMER_ID,"
			+"	fdcpbl.SHIPPED_TO_CODE"
			+"	UNION"
			/* non contracted customers AC */
			+"	SELECT"
			+"	fdc.*,"
			+"	fdc.customer_code as shippedToCodeTemp"
			+"	FROM ff_d_office fdo"
			+"	JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"
			+"	JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+"	JOIN ff_d_product_customer_type_map fdpctm1"
			+"	ON fdpctm1.CUSTOMER_TYPE_ID = fdct.CUSTOMER_TYPE_ID"
			+"	WHERE"
			+"	fdo.OFFICE_ID IN (:officeIds) AND"
			+"	fdct.CUSTOMER_TYPE_CODE IN ('AC') AND"
			+"	fdc.CUR_STATUS = 'A'"
			+"	UNION"
			/* non contracted customers BA, BV */
			+"	SELECT"
			+"	fdc.*,"
			+"	fdc.customer_code as shippedToCodeTemp"
			+"	FROM ff_d_office fdo"
			+"	JOIN ff_d_customer fdc ON fdc.OFFICE_MAPPED_TO = fdo.OFFICE_ID"
			+"	JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+"	JOIN ff_d_city fdcity ON fdcity.CITY_ID = fdo.CITY_ID"
			+"	WHERE"
			+"	fdcity.CITY_ID IN (:cityId) AND"
			+"	fdct.CUSTOMER_TYPE_CODE IN ('BA','BV') AND"
			+"	fdc.CUR_STATUS = 'A'"
			+"	UNION"
			/* reverse logistics customer */
			+"	SELECT"
			+"	fdc.*,"
			+"	fdc.customer_code as shippedToCodeTemp"
			+"	FROM ff_d_customer fdc"
			+"	JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+"	WHERE"
			+"	fdct.CUSTOMER_TYPE_CODE = 'RL' AND"
			+"	fdc.CUR_STATUS = 'A') as finalResult ORDER BY finalResult.BUSINESS_NAME";
    
	String CN_MODIFICATION_SAVED = "CNMOD01";
	String CN_MODIFICATION_FAILED = "CNMOD02";		
	String ALL_CONSIGNMENTS_MODIFIED_SUCCESSFULLY = "CNMOD03";
	String PARTIALLY_BULK_CN_MODICATION_SAVED = "CNMOD04";
	String INVALID_CONSIGNMENT_FORMAT = "Invalid Consignment Format";
	String PARTIAL_SUCCESS_FLAG = "PARTIAL_SUCCESS";
	String CONSIGNMENT_TYPES = "ConsgTypeTOs";
	String ACC_CUSTOMERS_NOT_ALLOWED_SERIES="CNMOD05";
	String ALL_BULK_CN_MODICATION_FAILED = "CNMOD06";
	String CN_MODIFICATION_ALLOWED_AT_CORPORATE_RHO_OFFICE = "CNMOD001";
	String PLEASE_ENTER_ATLEAST_ONE_CONSIGNMENT = "CNMOD07";
	String CN_MODIFICATION_NOT_ALLOWED_EXCESS_PRIORITY_PRODUCTS = "CNMOD08";
	String GET_OLD_APPLICABLE_CONTRACT_COUNT = 
			"SELECT CASE"+"\r\n"+
					"          WHEN    fdcc.CUSTOMER_CATEGORY_CODE = 'CRDT'"+"\r\n"+
					"               OR fdcc.CUSTOMER_CATEGORY_CODE = 'FR'"+"\r\n"+
					"          THEN"+"\r\n"+
					"             (SELECT count(1)"+"\r\n"+
					"                FROM ff_d_rate_contract"+"\r\n"+
					"               WHERE     CUSTOMER = fdc.CUSTOMER_ID"+"\r\n"+
					"                     AND VALID_FROM_DATE <=:contractValidFromDate"+"\r\n"+
					"                     AND CONTRACT_STATUS = 'A')"+"\r\n"+
					"          WHEN (fdcc.CUSTOMER_CATEGORY_CODE = 'BA')"+"\r\n"+
					"          THEN"+"\r\n"+
					"             (SELECT count(1)"+"\r\n"+
					"                FROM ff_f_rate_ba_rate_header rbh"+"\r\n"+
					"                     JOIN ff_d_office bo ON rbh.CITY = bo.CITY_ID"+"\r\n"+
					"               WHERE     bo.OFFICE_ID =:baOffice"+"\r\n"+
					"                     AND rbh.FROM_DATE <=:baValidFromDate"+"\r\n"+
					"                     AND rbh.HEADER_STATUS = 'A'"+"\r\n"+
					"                     AND rbh.CUSTOMER_TYPE = fdc.CUSTOMER_TYPE)"+"\r\n"+
					"          WHEN (fdcc.CUSTOMER_CATEGORY_CODE = 'ACC')"+"\r\n"+
					"          THEN"+"\r\n"+
					"             (SELECT count(1)"+"\r\n"+
					"                FROM ff_f_rate_cash_rate_header crh"+"\r\n"+
					"                     JOIN ff_d_office o ON crh.REGION = o.MAPPED_TO_REGION"+"\r\n"+
					"               WHERE     o.OFFICE_ID =:cashOffice"+"\r\n"+
					"                     AND crh.FROM_DATE <=:cashValidFromDate"+"\r\n"+
					"                     AND crh.HEADER_STATUS = 'A')"+"\r\n"+
					"       END"+"\r\n"+
					"  FROM ff_d_customer fdc"+"\r\n"+
					"       JOIN ff_d_rate_customer_category fdcc"+"\r\n"+
					"          ON fdcc.RATE_CUSTOMER_CATEGORY_ID = fdc.CUSTOMER_CATEGORY"+"\r\n"+
					" WHERE fdc.CUSTOMER_ID =:customerId";

}
