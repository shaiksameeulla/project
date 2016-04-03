package com.ff.report.common.util;

/**
 * The Interface BillingConstants.
 * 
 * @author narmdr
 */
public interface BillingConstants {

	String NO_BILLS_FOUND = "BILL005";
	String CONSIGNMENT_NOT_FOUND = "TRERR003";
	String NO_REBILLGDR_DATA="REBG001";

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

	String ELIGIBLE_CN_FOR_BILLING = "SELECT"
			+ "  result.*"
			+ "  FROM  "
			+ "  (SELECT ffc.*,"
			+ "    CASE"
			+ "    WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL THEN"
			+ "      'N'"
			+ "    ELSE"
			+ "      IF(ffcr_booking_rate.BILLED IS NOT NULL, ffcr_booking_rate.BILLED, 'N')"
			+ "    END AS BOOKING_RATE_BILLED,"
			+ "    CASE"
			+ "    WHEN ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S' THEN"
			+ "      'NA'  "
			+ "    WHEN ffcr_rto_rate.CONSIGNMENT_RATE_ID IS NULL THEN"
			+ "      'N'"
			+ "    ELSE"
			+ "      IF(ffcr_rto_rate.BILLED IS NOT NULL, ffcr_rto_rate.BILLED, 'N')"
			+ "    END AS RTO_RATE_BILLED,"
			+ "    CASE"
			+ "    WHEN (ffb.SHIPPED_TO_CODE IS NOT NULL AND length(ffb.SHIPPED_TO_CODE) > 0) THEN"
			+ "      ffb.SHIPPED_TO_CODE"
			+ "    WHEN (ffb.SHIPPED_TO_CODE IS NULL OR length(ffb.SHIPPED_TO_CODE) = 0) THEN"
			+ "      CASE"
			+ "      WHEN (ffb.CUSTOMER IS NOT NULL AND length(ffb.CUSTOMER) > 0) THEN fdc.CUSTOMER_CODE" 
			+ "      WHEN (ffb.CUSTOMER IS NULL OR length(ffb.CUSTOMER) = 0) THEN"
			+ "        CASE"
			+ "          WHEN fdbt.BOOKING_TYPE = 'FC' THEN 'FOC'" 
			+ "          ELSE 'CASH'"
			+ "        END"
			+ "      ELSE 'ELSE 2'"
			+ "      END"
			+ "    ELSE 'ELSE 1'"
			+ "    END AS SHIP_TO_CODE"
			+ "    FROM ff_f_consignment ffc"
			+ "    JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"
			+ "    JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"
			+ "    JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"
			+ "    JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"
			+ "    JOIN ff_d_consignment_type fdcnt ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"
			+ "    /* get customer */"
			+ "    LEFT JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffb.CUSTOMER"
			+ "    /* get rates */"
			+ "    JOIN ff_f_consignment_rate ffcr_booking_rate "
			+ "    ON (ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"
			+ "    LEFT JOIN ff_f_consignment_rate ffcr_rto_rate"
			+ "    ON (ffc.CONSG_ID = ffcr_rto_rate.CONSIGNMENT_ID AND ffcr_rto_rate.RATE_CALCULATED_FOR = 'R')"
			+ "    WHERE "
			+ "    ("
			+ "      /* T Series credit booking consignments which are either delivered or RTO delivered */"
			+ "      (fdp.CONSG_SERIES = 'T' "
			+ "      AND ffc.BILLING_STATUS = 'TBB'"
			+ "      AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"
			+ "      AND (ffc.CONSG_STATUS = 'D' OR ffc.CONSG_STATUS = 'S')"
			+ "      )"
			+ "      /* Other consignment series fresh/modified/rto bookings */"
			+ "      OR "
			+ "      ("
			+ "      fdp.CONSG_SERIES != 'T' AND"
			+ "      ffc.BILLING_STATUS = 'TBB'"
			+ "      AND ( "
			+ "        		("
			+ "              /* fresh consignments */"
			+ "        			ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S'"
			+ "        			AND ("
			+ "        				("
			+ "                  ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND "
			+ "                  ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' "
			+ "         				  AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"
			+ "                )"
			+ "                /* weight and/or destination modified consignments */"
			+ "        				OR"
			+ "        				("
			+ "                  fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC') AND fdcnt.CONSIGNMENT_CODE != 'DOX'"
			+ "                  AND ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'"
			+ "                )"
			+ "        			)"
			+ "        		) "
			+ "            /* RTO or RTO Delivered consignments */"
			+ "            OR"
			+ "        		("
			+ "              fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC')"
			+ "              AND ffc.CONSG_STATUS = 'R' AND ffc.CONSG_STATUS = 'S'"
			+ "            )"
			+ "    	    )"
			+ "      )"
			+ "    )"
			+ "    AND ffc.DEST_PINCODE IS NOT NULL"
			+ "    AND ffb.BOOKING_DATE IS NOT NULL"
			+ "    AND bo.OFFICE_ID IS NOT NULL"
			+ "    AND fdp.PRODUCT_CODE IS NOT NULL"
			+ "    AND ffc.CONSG_STATUS IS NOT NULL"
			+ "    AND ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL"
			+ "  ) AS result"
			+ "  WHERE result.SHIP_TO_CODE NOT IN ('ELSE 1', 'ELSE 2')"
			+ "  AND (result.BOOKING_RATE_BILLED = 'N' OR result.RTO_RATE_BILLED = 'N')";

	String ELIGIBLE_CN_COUNT_FOR_BILLING = "SELECT"
			+ "  count(1)"
			+ "  FROM  "
			+ "  (SELECT ffc.*,"
			+ "    CASE"
			+ "    WHEN ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NULL THEN"
			+ "      'N'"
			+ "    ELSE"
			+ "      IF(ffcr_booking_rate.BILLED IS NOT NULL, ffcr_booking_rate.BILLED, 'N')"
			+ "    END AS BOOKING_RATE_BILLED,"
			+ "    CASE"
			+ "    WHEN ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S' THEN"
			+ "      'NA'  "
			+ "    WHEN ffcr_rto_rate.CONSIGNMENT_RATE_ID IS NULL THEN"
			+ "      'N'"
			+ "    ELSE"
			+ "      IF(ffcr_rto_rate.BILLED IS NOT NULL, ffcr_rto_rate.BILLED, 'N')"
			+ "    END AS RTO_RATE_BILLED,"
			+ "    CASE"
			+ "    WHEN (ffb.SHIPPED_TO_CODE IS NOT NULL AND length(ffb.SHIPPED_TO_CODE) > 0) THEN"
			+ "      ffb.SHIPPED_TO_CODE"
			+ "    WHEN (ffb.SHIPPED_TO_CODE IS NULL OR length(ffb.SHIPPED_TO_CODE) = 0) THEN"
			+ "      CASE"
			+ "      WHEN (ffb.CUSTOMER IS NOT NULL AND length(ffb.CUSTOMER) > 0) THEN fdc.CUSTOMER_CODE" 
			+ "      WHEN (ffb.CUSTOMER IS NULL OR length(ffb.CUSTOMER) = 0) THEN"
			+ "        CASE"
			+ "          WHEN fdbt.BOOKING_TYPE = 'FC' THEN 'FOC'" 
			+ "          ELSE 'CASH'"
			+ "        END"
			+ "      ELSE 'ELSE 2'"
			+ "      END"
			+ "    ELSE 'ELSE 1'"
			+ "    END AS SHIP_TO_CODE"
			+ "    FROM ff_f_consignment ffc"
			+ "    JOIN ff_d_product fdp ON ffc.PRODUCT = fdp.PRODUCT_ID"
			+ "    JOIN ff_f_booking ffb ON ffc.CONSG_NO = ffb.CONSG_NUMBER"
			+ "    JOIN ff_d_booking_type fdbt ON ffb.BOOKING_TYPE = fdbt.BOOKING_TYPE_ID"
			+ "    JOIN ff_d_office bo ON bo.OFFICE_ID = ffb.BOOKING_OFF"
			+ "    JOIN ff_d_consignment_type fdcnt ON fdcnt.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE"
			+ "    /* get customer */"
			+ "    LEFT JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = ffb.CUSTOMER"
			+ "    /* get rates */"
			+ "    JOIN ff_f_consignment_rate ffcr_booking_rate "
			+ "    ON (ffc.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')"
			+ "    LEFT JOIN ff_f_consignment_rate ffcr_rto_rate"
			+ "    ON (ffc.CONSG_ID = ffcr_rto_rate.CONSIGNMENT_ID AND ffcr_rto_rate.RATE_CALCULATED_FOR = 'R')"
			+ "    WHERE "
			+ "    ("
			+ "      /* T Series credit booking consignments which are either delivered or RTO delivered */"
			+ "      (fdp.CONSG_SERIES = 'T' "
			+ "      AND ffc.BILLING_STATUS = 'TBB'"
			+ "      AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"
			+ "      AND (ffc.CONSG_STATUS = 'D' OR ffc.CONSG_STATUS = 'S')"
			+ "      )"
			+ "      /* Other consignment series fresh/modified/rto bookings */"
			+ "      OR "
			+ "      ("
			+ "      fdp.CONSG_SERIES != 'T' AND"
			+ "      ffc.BILLING_STATUS = 'TBB'"
			+ "      AND ( "
			+ "        		("
			+ "              /* fresh consignments */"
			+ "        			ffc.CONSG_STATUS != 'R' AND ffc.CONSG_STATUS != 'S'"
			+ "        			AND ("
			+ "        				("
			+ "                  ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND "
			+ "                  ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' "
			+ "         				  AND (ffb.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL fdp.CONSOLIDATION_WINDOW DAY))"
			+ "                )"
			+ "                /* weight and/or destination modified consignments */"
			+ "        				OR"
			+ "        				("
			+ "                  fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC') AND fdcnt.CONSIGNMENT_CODE != 'DOX'"
			+ "                  AND ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'"
			+ "                )"
			+ "        			)"
			+ "        		) "
			+ "            /* RTO or RTO Delivered consignments */"
			+ "            OR"
			+ "        		("
			+ "              fdbt.BOOKING_TYPE NOT IN ('EB', 'CS', 'FC')"
			+ "              AND ffc.CONSG_STATUS = 'R' AND ffc.CONSG_STATUS = 'S'"
			+ "            )"
			+ "    	    )"
			+ "      )"
			+ "    )"
			+ "    AND ffc.DEST_PINCODE IS NOT NULL"
			+ "    AND ffb.BOOKING_DATE IS NOT NULL"
			+ "    AND bo.OFFICE_ID IS NOT NULL"
			+ "    AND fdp.PRODUCT_CODE IS NOT NULL"
			+ "    AND ffc.CONSG_STATUS IS NOT NULL"
			+ "    AND ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL"
			+ "  ) AS result"
			+ "  WHERE result.SHIP_TO_CODE NOT IN ('ELSE 1', 'ELSE 2')"
			+ "  AND (result.BOOKING_RATE_BILLED = 'N' OR result.RTO_RATE_BILLED = 'N')";

	String GET_BILL_DATA_FOR_PRINTING = "  SELECT"
			+ "    ffb.INVOICE_ID as invoiceId,"
			+ "    ffb.INVOICE_NUMBER as invoiceNumber,"
			+ "    ffb.CREATED_DATE as createdDate,"
			+ "    ffb.FROM_DATE as fromDate,"
			+ "    ffb.TO_DATE as toDate,"
			+ "    ffb.NO_OF_PICKUPS as numberOfPickups,"
			+ "    ffb.FREIGHT as freight,"
			+ "    ffb.RISK_SURCHARGE as riskSurcharge,"
			+ "    ffb.DOC_HANDLING_CHARGE as docHandlingCharge,"
			+ "    ffb.PARCAL_HANDLING_CHARGE as parcelHandlingCharge,"
			+ "    ffb.AIRPORT_HANDLING_CHARGE as airportHandlingCharge,"
			+ "    ffb.OTHER_CHARGES as otherCharges,"
			+ "    ffb.TOTAL as total,"
			+ "    ffb.FUEL_SURCHARGE as fuelSurcharge,"
			+ "    ffb.SERVICE_TAX as serviceTax,"
			+ "    ffb.EDUCATION_CESS as educationCess,"
			+ "    ffb.HIGHER_EDUCATION_CESS as higherEducationCess,"
			+ "    ffb.STATE_TAX as stateTax,"
			+ "    ffb.SURCHARGE_ON_STATE_TAX as surchargeOnStateTax,"
			+ "    ffb.GRAND_TOTAL as grandTotal,"
			+ "    ffb.GRAND_TOTAL_ROUNDED_OFF as grandTotalRoundedOff,"
			+ "    fdo_billing.OFFICE_NAME as billingOfficeName,"
			+ "    fdo_billing.PHONE as billingOfficePhone,"
			+ "    fdo_billing_rho.OFFICE_NAME as billingRHOOfficeName,"
			+ "    fdo_billing_rho.ADDRESS_1 as billingRHOOfficeAddress1,"
			+ "    fdo_billing_rho.ADDRESS_2 as billingRHOOfficeAddress2,"
			+ "    fdo_billing_rho.ADDRESS_3 as billingRHOOfficeAddress3,"
			+ "    fdo_billing_rho.PHONE as billingRHOOfficePhone,"
			+ "    fdo_billing_rho.EMAIL as billingRHOOfficeEmail,"
			+ "    fdc_billing_rho.CITY_NAME as rhoCityName,"
			+ "    fdc.CUSTOMER_ID as customerId,"
			+ "    fdc.BUSINESS_NAME as customerBusinessName,"
			+ "    fdc.CUSTOMER_CODE as customerCode,"
			+ "    fda.ADDRESS1 as customerAddress1,"
			+ "    fda.ADDRESS2 as customerAddress2,"
			+ "    fda.ADDRESS3 as customerAddress3,"
			+ "    fdct.CUSTOMER_TYPE_CODE as customerTypeCode,"
			+ "    fdp.PRODUCT_ID as productId,"
			+ "    fdp.CONSG_SERIES as productSeries,"
			+ "    fdfp.FINANCIAL_PRODUCT_ID as financialProductId,"
			+ "    fds_billing.STATE_CODE as stateCode,"
			+ "	   ffb.FUEL_SURCHARGE_PERCENTAGE as fuelSurchargePercentage,"
			+"     ffb.FUEL_SURCHARGE_PERCENTAGE_FORMULA as fuelSurchargePercentageFormula,"
			+"     ffb.UPDATE_DATE as billGenerationDate,"
			+"     ffb.LC_AMOUNT as lcAmount,"
			+"     ffb.LC_CHARGE as lcCharge,"
			+"     ffb.COD_AMOUNT as codAmount,"
			+"     ffb.COD_CHARGE as codCharge,"
			+"     ffb.RTO_CHARGE as rtoCharge,"
			+"     ffb.BILL_TYPE as billType"
			+ "  FROM"
			+ "    ff_f_bill ffb"
			+ "    JOIN ff_d_product fdp ON fdp.PRODUCT_ID = ffb.PRODUCT_ID"
			+ "    join ff_d_rate_prod_prod_cat_map fdrppcm on fdrppcm.PRODUCT = fdp.PRODUCT_ID"
			+ "    join ff_d_customer fdc on fdc.CUSTOMER_ID = ffb.CUSTOMER"
			+ "    join ff_d_customer_type fdct on fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+ "    join ff_d_address fda on fda.ADDRESS_ID = fdc.ADDRESS"
			+ "    join ff_d_office fdcmto on fdcmto.OFFICE_ID = fdc.SALES_OFFICE"
			+ "    /* billing office details */"
			+ "    join ff_d_office fdo_billing on fdo_billing.OFFICE_ID = ffb.BILLING_OFFICE_ID"
			+ "    /* billing office rho details */"
			+ "    join ff_d_office fdo_billing_rho"
			+ "      on fdo_billing_rho.OFFICE_ID = fdo_billing.REPORTING_RHO"
			+ "    join ff_d_city fdc_billing_rho"
			+ "      on fdc_billing_rho.CITY_ID = fdo_billing_rho.CITY_ID"
			+ "    /* financial product */"
			+ "    join ff_d_financial_product fdfp"
			+ "      on fdfp.FINANCIAL_PRODUCT_ID = ffb.FINANCIAL_PRODUCT_ID"
			+ "    /* billing office geography */"
			+ "    join ff_d_city fdc_billing"
			+ "      on fdc_billing.CITY_ID = fdo_billing.CITY_ID"
			+ "    join ff_d_state fds_billing"
			+ "      on fds_billing.STATE_ID = fdc_billing.STATE"
			+ "  WHERE"
			+ "    str_to_date(ffb.BILL_GENERATION_DATE, '%Y-%m-%d') between str_to_date("
			+ "                               :startDate,"
			+ "                               '%d/%m/%Y')"
			+ "                         and str_to_date("
			+ "                               :endDate,"
			+ "                               '%d/%m/%Y') and"
			+ "    ffb.BILLING_OFFICE_ID in (:billingOfficeIds) and"
			+ "    fdfp.FINANCIAL_PRODUCT_ID = :financialProductId and ";

	String GET_CONSIGNMENT_DATA_FOR_PRINTING = "{ call sp_billing_consignment_details (:invoiceId) }";

	String GET_REBILL_CONSIGNMENT_DATA=" select " 
			+ "  ffc.CONSG_ID as consgId,"
			+"   ffc.CONSG_NO as consgNo,"
			+"   ffc.CONSG_STATUS consgStatus,"
			+"   ffc.OPERATING_OFFICE as operaOffice,"
			+"   ffbc.FINAL_WEIGHT as finalWt,"
			+"   ffc.CUSTOMER as customer,"
			+"   ffct.CONSIGNMENT_CODE as consgCode,"
			+"   ffdp.PINCODE as destPincode,"
			+"   ffdprod.PRODUCT_CODE as productCode,"
			+"   ffdi. INSURED_BY_CODE as insuredCode,"
			+"   ffc.DISCOUNT as discount,"
			+"   ffc.TOPAY_AMT as topayAmt,"
			+"   ffc.SPL_CHG as splChg,"
			+"   ffc.COD_AMT as codAmt,"
			+"   ffc.LC_AMT as lcAmt,"
			+"   ffc.SERVICED_ON as serviceOn,"
			+"   ffc.DECLARED_VALUE as declareValue,"
			+"   ffc.EB_PREFEENCES_CODES as ebPrefCode,"
			+"   ffc.RATE_TYPE as rateType,"
			+"   ffc.EVENT_DATE as eventDate,"
			+"   ffb.BOOKING_DATE as bookDate,"
			+"   ffbc.BILLING_CONSIGNMENT_ID as billingConsignmentId,"
			+"   case"
			+"   when ffbc.INVOICE is null then"
			+"   'N'" 
			/*+"   -- rate calculate as per current date and update values in ff_f_consignment_rate table with BILLED=N & update ff_f_consignment BS = TRB & CAWD = Y"*/
			+"   else	'Y' " 
			/* +"   -- rate calculate as per current date and store values in re-billing detail table"*/
			+"   end as bill_generated,"
			+"   case" 
			+"   when ffcrb.CONSIGNMENT_RATE_ID is null then"
			+"   'N'"
			+"   else 'Y'"
			+"   end as booking_Rate,"
			+"   case" 
			+"   when ffcrr.CONSIGNMENT_RATE_ID is null then 'N'"
			+"   else 'Y'"
			+"   end as rto_Rate"
			+"   from" 
			+"   ff_f_consignment ffc"
			+"   join ff_f_booking ffb on ffc.CONSG_NO = ffb.CONSG_NUMBER"
			+"   join ff_d_consignment_type ffct on ffct.CONSIGNMENT_TYPE_ID=ffc.CONSG_TYPE"
			+"   join ff_d_pincode ffdp on ffdp.PINCODE_ID = ffc.DEST_PINCODE"
			+"   join ff_d_product ffdprod on ffdprod.PRODUCT_ID = ffc.PRODUCT"
			+"   left  join ff_d_insured_by ffdi on ffdi.INSURED_BY_ID = ffc.INSURED_BY"
			+"   left  join ff_f_billing_consignment ffbc on ffbc.CONSG_ID = ffc.CONSG_ID"
			+"   join ff_f_consignment_rate ffcrb on (ffcrb.CONSIGNMENT_ID=ffc.CONSG_ID and ffcrb.RATE_CALCULATED_FOR='B')"
			+"   left join ff_f_consignment_rate ffcrr on (ffcrr.CONSIGNMENT_ID=ffc.CONSG_ID and ffcrr.RATE_CALCULATED_FOR='R')"
			+"   where ffb.BOOKING_DATE between :startDate and :endDate " 
			+"   and ffc.CUSTOMER=:customer  and ffc.ORG_OFF=:orgOff";

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


	String QRY_REBILLGDR_DETAILS=" select "
			+"  ffrbh.RE_BILLING_ID as ReBillingID,"
			+"  ffbill.INVOICE_NUMBER as InvoiceNumber,"
			+"  fdc.BUSINESS_NAME as BusinessName,"
			+"  fdo_bill.OFFICE_NAME as OfficeName,"
			+"  date_format(ffb.BOOKING_DATE, '%d-%m-%y') as BookingDate,"
			+"  ffrbc.CONSG_NO as ConsignmentNumber,"
			+"  case fdct.CONSIGNMENT_CODE"
			+"  when 'DOX' then 'D'" 
			+"  when 'PPX' then 'P'" 
			+"  end"
			+"  as consignmentType,"
			+"  fdc_dest.CITY_NAME as CityName,"
			+"  ffrbc.FINAL_WEIGHT as FinalWeight,"
			+"  ffrbcr.CONTRACT_FOR as ContractFor,"
			+"  ffrbcr.RATE_CALCULATED_FOR as RateCalculatedFor,"
			+"  if(ffrbcr.FINAL_SLAB_RATE is null, 0, ffrbcr.FINAL_SLAB_RATE) as FreightCharges,"
			+"  if(ffrbcr.RISK_SURCHARGE is null, 0, ffrbcr.RISK_SURCHARGE) as RiskSurcharge,"
			+"  if(ffrbcr.DOCUMENT_HANDLING_CHARGE is null, 0, ffrbcr.DOCUMENT_HANDLING_CHARGE) as DocumentHandlingCharge,"
			+"  if(ffrbcr.PARCEL_HANDLING_CHARGE is null, 0, ffrbcr.PARCEL_HANDLING_CHARGE) as ParcelHandlingCharge,"
			+"  if(ffrbcr.AIRPORT_HANDLING_CHARGE is null, 0, ffrbcr.AIRPORT_HANDLING_CHARGE) as AirportHandlingCharge,"
			+"  if(ffrbcr.FUEL_SURCHARGE is null, 0, ffrbcr.FUEL_SURCHARGE)"
			+"  + if(ffrbcr.TO_PAY_CHARGE is null, 0, ffrbcr.TO_PAY_CHARGE)"
			+"  + if(ffrbcr.COD_CHARGES is null, 0, ffrbcr.COD_CHARGES)"
			+"  + if(ffrbcr.OTHER_OR_SPECIAL_CHARGES is null, 0, ffrbcr.OTHER_OR_SPECIAL_CHARGES)"
			+"  + if(ffrbcr.DISCOUNT is null, 0, ffrbcr.DISCOUNT)"
			+"  + if(ffrbcr.SERVICE_TAX is null, 0, ffrbcr.SERVICE_TAX)"
			+"  + if(ffrbcr.EDUCATION_CESS is null, 0, ffrbcr.EDUCATION_CESS)"
			+"  + if(ffrbcr.HIGHER_EDUCATION_CES is null, 0, ffrbcr.HIGHER_EDUCATION_CES)"
			+"  + if(ffrbcr.STATE_TAX is null, 0, ffrbcr.STATE_TAX)"
			+"  + if(ffrbcr.SURCHARGE_ON_STATE_TAX is null, 0, ffrbcr.SURCHARGE_ON_STATE_TAX)"
			+"  + if(ffrbcr.OCTROI is null, 0, ffrbcr.OCTROI)"
			+"  + if(ffrbcr.SERVICE_CHARGE_ON_OCTROI is null, 0, ffrbcr.SERVICE_CHARGE_ON_OCTROI)"
			+"  + if(ffrbcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.GRAND_TOTAL_INCLUDING_TAX is null, 0, ffrbcr.GRAND_TOTAL_INCLUDING_TAX)"
			+"  + if(ffrbcr.LC_CHARGE is null, 0, ffrbcr.LC_CHARGE) as OtherCharges,"
			+"  if(ffrbcr.FINAL_SLAB_RATE is null, 0, ffrbcr.FINAL_SLAB_RATE)"
			+"  + if(ffrbcr.RISK_SURCHARGE is null, 0, ffrbcr.RISK_SURCHARGE)"
			+"  + if(ffrbcr.DOCUMENT_HANDLING_CHARGE is null, 0, ffrbcr.DOCUMENT_HANDLING_CHARGE)"
			+"  + if(ffrbcr.PARCEL_HANDLING_CHARGE is null, 0, ffrbcr.PARCEL_HANDLING_CHARGE)"
			+"  + if(ffrbcr.AIRPORT_HANDLING_CHARGE is null, 0, ffrbcr.AIRPORT_HANDLING_CHARGE)"
			+"  + if(ffrbcr.FUEL_SURCHARGE is null, 0, ffrbcr.FUEL_SURCHARGE)"
			+"  + if(ffrbcr.TO_PAY_CHARGE is null, 0, ffrbcr.TO_PAY_CHARGE)"
			+"  + if(ffrbcr.COD_CHARGES is null, 0, ffrbcr.COD_CHARGES)"
			+"  + if(ffrbcr.OTHER_OR_SPECIAL_CHARGES is null, 0, ffrbcr.OTHER_OR_SPECIAL_CHARGES)"
			+"  + if(ffrbcr.DISCOUNT is null, 0, ffrbcr.DISCOUNT)"
			+"  + if(ffrbcr.SERVICE_TAX is null, 0, ffrbcr.SERVICE_TAX)"
			+"  + if(ffrbcr.EDUCATION_CESS is null, 0, ffrbcr.EDUCATION_CESS)"
			+"  + if(ffrbcr.HIGHER_EDUCATION_CES is null, 0, ffrbcr.HIGHER_EDUCATION_CES)"
			+"  + if(ffrbcr.STATE_TAX is null, 0, ffrbcr.STATE_TAX)"
			+"  + if(ffrbcr.SURCHARGE_ON_STATE_TAX is null, 0, ffrbcr.SURCHARGE_ON_STATE_TAX)"
			+"  + if(ffrbcr.OCTROI is null, 0, ffrbcr.OCTROI)"
			+"  + if(ffrbcr.SERVICE_CHARGE_ON_OCTROI is null, 0, ffrbcr.SERVICE_CHARGE_ON_OCTROI)"
			+"  + if(ffrbcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE is null, 0, ffrbcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE)"
			+"  + if(ffrbcr.GRAND_TOTAL_INCLUDING_TAX is null, 0, ffrbcr.GRAND_TOTAL_INCLUDING_TAX)"
			+"  + if(ffrbcr.LC_CHARGE is null, 0, ffrbcr.LC_CHARGE) as TotalCharges"
			+"  from"
			+"  ff_f_re_billing_consignment ffrbc"
			+"  join ff_f_re_billing_consignment_rate ffrbcr"
			+"  on ffrbcr.REBILLING_CONSIGNMENT = ffrbc.REBILLING_CONSIGNMENT_ID"
			+"  join ff_f_booking ffb on ffb.CONSG_NUMBER = ffrbc.CONSG_NO"
			+"  join ff_d_consignment_type fdct"
			+"  on fdct.CONSIGNMENT_TYPE_ID = ffrbc.CONSG_TYPE"
			+"  JOIN ff_d_pincode fdp_dest ON fdp_dest.PINCODE_ID = ffb.DEST_PINCODE"
			+"  JOIN ff_d_city fdc_dest ON fdc_dest.CITY_ID = fdp_dest.CITY_ID"
			+"  join ff_f_billing_consignment ffbc"
			+"  on ffbc.BILLING_CONSIGNMENT_ID = ffrbc.BILLING_CONSIGNMENT"
			+"  join ff_f_bill ffbill on ffbill.INVOICE_ID = ffbc.INVOICE"
			+"  join ff_d_customer fdc on fdc.CUSTOMER_ID = ffbill.CUSTOMER"
			+"  join ff_d_office fdo_bill on fdo_bill.OFFICE_ID = ffbill.BILLING_OFFICE_ID"
			+"  join ff_f_re_billing_header ffrbh ON ffrbh.RE_BILLING_ID = ffrbc.RE_BILLING_ID"
			+"  where ffrbh.RE_BILLING_ID =:reBillId"
			+"  order by"
			+"  ffbill.INVOICE_NUMBER,"
			+"  ffrbc.BILLING_CONSIGNMENT,"
			+"  ffrbcr.RATE_CALCULATED_FOR,"
			+"  ffrbcr.CONTRACT_FOR";

	String CUST_MODIFICATION_SUCCESS = "success";

	String CUST_MODIFICATION_VALIDATION_DETAIS="SELECT"
			+"   CASE WHEN cn.CONSG_NO IS NULL THEN 'N' ELSE 'Y' END AS cnDetails,"
			+"   CASE WHEN bk.CONSG_NUMBER IS NULL THEN 'N' ELSE 'Y' END AS bookDetails,"
			+"   CASE WHEN rate.CONSIGNMENT_ID IS NULL THEN 'N' ELSE 'Y' END AS"
			+"   rateDetails,"
			+"   CASE WHEN cust.CUSTOMER_ID IS NULL THEN 'N' ELSE 'Y' END AS custCheck,"
			+"   CASE WHEN bc.CONSG_NO IS NULL THEN 'Y' ELSE 'N' END AS billCheck,"
			+"   CASE"
			+"   WHEN (SELECT"
			+"   count( expense.EXPENSE_ID)"
			+"   FROM"
			+"   ff_f_expense expense"
			+"   JOIN ff_f_expense_entries exentry"
			+"   ON exentry.EXPENSE_ID = expense.EXPENSE_ID"
			+"   WHERE"
			+"   expense.DT_SAP_OUTBOUND = 'C' AND"
			+"   exentry.CONSG_ID =:consgId) > 0"
			+"   THEN"
			+"   'N'"
			+"   ELSE"
			+"   'Y'"
			+"   END"
			+"   AS expenseCheck,"
			+"  CASE"
			+"  WHEN (SELECT"
			+"  count( collection.COLLECTION_ID)"
			+"  FROM"
			+"   ff_f_collection collection"
			+"  JOIN ff_f_collection_entries collentry"
			+"   ON collection.COLLECTION_ID = collentry.COLLECTION_ID"
			+"   WHERE"
			+"   collection.DT_SAP_OUTBOUND = 'C' AND"
			+"   collentry.CONSIGNMENT_ID =:consgId) > 0"
			+"   THEN"
			+"   'N'"
			+"   ELSE"
			+"   'Y'"
			+"   END"
			+"   AS collectionCheck,"
			+"   CASE"
			+"   WHEN ( select"
			+"   count(liaPay.LIABILITY_ID)"
			+"   FROM ff_f_liability_payment liaPay"
			+"   JOIN ff_f_liability_entries liaEntry"
			+"   ON liaPay.LIABILITY_ID=liaEntry.LIABILITY_ID"
			+"   WHERE"
			+"   liaPay.DT_SAP_OUTBOUND='C' AND"
			+"   liaEntry.CONSIGNMENT_ID=:consgId)> 0"
			+"   THEN"
			+"   'N'"
			+"   ELSE"
			+"   'Y'"
			+"   END"
			+"   AS liabilityCheck,"
			+"   cust.CUSTOMER_ID  AS custId,"
			+"   cust.CUSTOMER_CODE AS custCode,"
			+"   cust.BUSINESS_NAME as custName,"
			+"   custType.CUSTOMER_TYPE_CODE AS custTypeCode"
			+"   FROM"
			+"   ff_f_consignment cn"
			+"   LEFT JOIN ff_f_booking bk ON bk.CONSG_NUMBER = cn.CONSG_NO"
			+"   LEFT JOIN ff_f_consignment_rate rate"
			+"   ON (rate.CONSIGNMENT_ID = cn.CONSG_ID AND"
			+"   rate.RATE_CALCULATED_FOR = 'B')"
			+"   LEFT JOIN ff_d_customer cust ON cust.CUSTOMER_ID = cn.CUSTOMER"
			+"   LEFT JOIN ff_d_customer_type custType ON custType.CUSTOMER_TYPE_ID=cust.CUSTOMER_TYPE"
			+"   LEFT JOIN ff_f_billing_consignment bc ON bc.CONSG_NO = cn.CONSG_NO"
			+"   WHERE"
			+"   cn.CONSG_ID =:consgId"
			+"   order by bc.BILLING_CONSIGNMENT_ID asc limit 1";

	String QRY_UPDATE_CUSTMODIFICATION_CONSG="updateConsgForCustModification";
	String QRY_UPDATE_CUSTMODIFICATION_BOOK="updateBookForCustModification";
	String CONSG_ID="consgId";
	String CONSG_NO="consgNo";
	String UPDATED_DATE="updatedDate";
	String UPDATED_BY="updatedBy";
	String CUST_ID="custId";
	String CUST_DO="custDO";
	String QRY_GET_ALL_BRANCHES_UNDER_REPORTING_HUB="getAllBranchesUnderRepotingHub";
	String OFFICE_ID="officeId";
	String QRY_SHIPPEDTOCODE_NEW_CUSTOMER="getShipToCodeForCustModification";
	String QRY_PARAM_TRANSACTION_STATUS = "transactionStatus";
	String TRANSACTION_STATUS = "A";
	String SHIPPED_TO_CODE="shippedToCode";

	//code added from config admin

	String GET_NON_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH = 
			"select distinct"+"\r\n"+ 
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
					"    fffp1.FINANCIAL_PRODUCT_ID in (:financialProductId) and"+"\r\n"+
					"    fdct1.CUSTOMER_TYPE_CODE in ('BA',"+"\r\n"+
					"                                 'BV',"+"\r\n"+
					"                                  'AC') and fffp1.STATUS='A' and fdfpsctm1.STATUS='A'  order by fdc1.BUSINESS_NAME"+"\r\n"+
					"";


	String GET_CONTRACTED_CUSTOMERS_BY_BILLING_BRANCH = 
			" select DISTINCT"+"\r\n"+
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
					"			  fdo.OFFICE_ID in (:billingOfficeId) and"+"\r\n"+
					"			  fffp.FINANCIAL_PRODUCT_ID in (:financialProductId) and fffp.STATUS='A' and fdfpsctm.STATUS='A' and"+"\r\n"+
					"			  ((fdcpbl.LOCATION_OPERATION_TYPE in ('B',"+"\r\n"+
					"			                                       'BP') or  fdcpbl.LOCATION_OPERATION_TYPE is null and"+"\r\n"+
					"			    fdct.CUSTOMER_TYPE_CODE in ('CR',"+"\r\n"+
					"			                                'CC',"+"\r\n"+
					"			                                'LC',"+"\r\n"+
					"			                                'CD',"+"\r\n"+
					"			                                'GV',"+"\r\n"+
					"			                                'FR') and"+"\r\n"+
					"			    fdrc.TYPE_OF_BILLING = 'CBCP') OR"+"\r\n"+
					"			   (fdcpbl.LOCATION_OPERATION_TYPE in ('B',"+"\r\n"+
					"			                                       'BP') or  fdcpbl.LOCATION_OPERATION_TYPE is null and"+"\r\n"+
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
					"			  fdcpbl.SHIPPED_TO_CODE Order by fdc.BUSINESS_NAME";

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
					"		group by fdo.OFFICE_ID, fdpdc.CUSTOMER_ID, fdcpbl.SHIPPED_TO_CODE";


	String GET_CUSTOMERS_BY_BILLING_BRANCH_UNION = 
			"select"+"\r\n"+
					"		 fdc1.CUSTOMER_ID as customerId, fdc1.CONTRACT_NO as contractNo, fdc1.CUSTOMER_CODE as customerCode, fdc1.BUSINESS_NAME as businessName, fdc1.CUSTOMER_CODE as customerCode1"+"\r\n"+
					"		from ff_d_office fdo1"+"\r\n"+
					"		join ff_d_customer fdc1 on fdc1.OFFICE_MAPPED_TO = fdo1.OFFICE_ID"+"\r\n"+
					"		join ff_d_customer_type fdct1 on fdct1.CUSTOMER_TYPE_ID = fdc1.CUSTOMER_TYPE"+"\r\n"+
					"		where fdct1.CUSTOMER_TYPE_CODE in ('BA', 'BV', 'AC') "+"\r\n"+
					"		and fdo1.OFFICE_ID in (:billingOffice)";

	String GET_CONSIGNMENT_DATA_FOR_BULK_BILL_PRINTING = "{ call sp_bulk_bill_printing_consignment_details (:paramInvoiceId) }";
	
	//Added By Shaheed All customers
	String GET_BILL_DATA_FOR_ALL_CUSTOMERS = "  SELECT"
			+ "    ffb.INVOICE_ID as invoiceId,"
			+ "    ffb.INVOICE_NUMBER as invoiceNumber,"
			+ "    ffb.CREATED_DATE as createdDate,"
			+ "    ffb.FROM_DATE as fromDate,"
			+ "    ffb.TO_DATE as toDate,"
			+ "    ffb.NO_OF_PICKUPS as numberOfPickups,"
			+ "    ffb.FREIGHT as freight,"
			+ "    ffb.RISK_SURCHARGE as riskSurcharge,"
			+ "    ffb.DOC_HANDLING_CHARGE as docHandlingCharge,"
			+ "    ffb.PARCAL_HANDLING_CHARGE as parcelHandlingCharge,"
			+ "    ffb.AIRPORT_HANDLING_CHARGE as airportHandlingCharge,"
			+ "    ffb.OTHER_CHARGES as otherCharges,"
			+ "    ffb.TOTAL as total,"
			+ "    ffb.FUEL_SURCHARGE as fuelSurcharge,"
			+ "    ffb.SERVICE_TAX as serviceTax,"
			+ "    ffb.EDUCATION_CESS as educationCess,"
			+ "    ffb.HIGHER_EDUCATION_CESS as higherEducationCess,"
			+ "    ffb.STATE_TAX as stateTax,"
			+ "    ffb.SURCHARGE_ON_STATE_TAX as surchargeOnStateTax,"
			+ "    ffb.GRAND_TOTAL as grandTotal,"
			+ "    ffb.GRAND_TOTAL_ROUNDED_OFF as grandTotalRoundedOff,"
			+ "    fdo_billing.OFFICE_NAME as billingOfficeName,"
			+ "    fdo_billing.PHONE as billingOfficePhone,"
			+ "    fdo_billing_rho.OFFICE_NAME as billingRHOOfficeName,"
			+ "    fdo_billing_rho.ADDRESS_1 as billingRHOOfficeAddress1,"
			+ "    fdo_billing_rho.ADDRESS_2 as billingRHOOfficeAddress2,"
			+ "    fdo_billing_rho.ADDRESS_3 as billingRHOOfficeAddress3,"
			+ "    fdo_billing_rho.PHONE as billingRHOOfficePhone,"
			+ "    fdo_billing_rho.EMAIL as billingRHOOfficeEmail,"
			+ "    fdc_billing_rho.CITY_NAME as rhoCityName,"
			+ "    fdc.CUSTOMER_ID as customerId,"
			+ "    fdc.BUSINESS_NAME as customerBusinessName,"
			+ "    fdc.CUSTOMER_CODE as customerCode,"
			+ "    fda.ADDRESS1 as customerAddress1,"
			+ "    fda.ADDRESS2 as customerAddress2,"
			+ "    fda.ADDRESS3 as customerAddress3,"
			+ "    fdct.CUSTOMER_TYPE_CODE as customerTypeCode,"
			+ "    fdp.PRODUCT_ID as productId,"
			+ "    fdp.CONSG_SERIES as productSeries,"
			+ "    fdfp.FINANCIAL_PRODUCT_ID as financialProductId,"
			+ "    fds_billing.STATE_CODE as stateCode,"
			+ "	   ffb.FUEL_SURCHARGE_PERCENTAGE as fuelSurchargePercentage,"
			+"     ffb.FUEL_SURCHARGE_PERCENTAGE_FORMULA as fuelSurchargePercentageFormula,"
			+"     ffb.UPDATE_DATE as billGenerationDate,"
			+"     ffb.LC_AMOUNT as lcAmount,"
			+"     ffb.LC_CHARGE as lcCharge,"
			+"     ffb.COD_AMOUNT as codAmount,"
			+"     ffb.COD_CHARGE as codCharge,"
			+"     ffb.RTO_CHARGE as rtoCharge,"
			+"     ffb.BILL_TYPE as billType"
			+ "  FROM"
			+ "    ff_f_bill ffb"
			+ "    JOIN ff_d_product fdp ON fdp.PRODUCT_ID = ffb.PRODUCT_ID"
			+ "    join ff_d_rate_prod_prod_cat_map fdrppcm on fdrppcm.PRODUCT = fdp.PRODUCT_ID"
			+ "    join ff_d_customer fdc on fdc.CUSTOMER_ID = ffb.CUSTOMER"
			+ "    join ff_d_customer_type fdct on fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE"
			+ "    join ff_d_address fda on fda.ADDRESS_ID = fdc.ADDRESS"
			+ "    join ff_d_office fdcmto on fdcmto.OFFICE_ID = fdc.SALES_OFFICE"
			+ "    /* billing office details */"
			+ "    join ff_d_office fdo_billing on fdo_billing.OFFICE_ID = ffb.BILLING_OFFICE_ID"
			+ "    /* billing office rho details */"
			+ "    join ff_d_office fdo_billing_rho"
			+ "      on fdo_billing_rho.OFFICE_ID = fdo_billing.REPORTING_RHO"
			+ "    join ff_d_city fdc_billing_rho"
			+ "      on fdc_billing_rho.CITY_ID = fdo_billing_rho.CITY_ID"
			+ "    /* financial product */"
			+ "    join ff_d_financial_product fdfp"
			+ "      on fdfp.FINANCIAL_PRODUCT_ID = ffb.FINANCIAL_PRODUCT_ID"
			+ "    /* billing office geography */"
			+ "    join ff_d_city fdc_billing"
			+ "      on fdc_billing.CITY_ID = fdo_billing.CITY_ID"
			+ "    join ff_d_state fds_billing"
			+ "      on fds_billing.STATE_ID = fdc_billing.STATE"
			+ "  WHERE"
			+ "    str_to_date(ffb.BILL_GENERATION_DATE, '%Y-%m-%d') between str_to_date("
			+ "                               :startDate,"
			+ "                               '%d/%m/%Y')"
			+ "                         and str_to_date("
			+ "                               :endDate,"
			+ "                               '%d/%m/%Y') and"
			+ "    ffb.BILLING_OFFICE_ID in (:billingOfficeIds) and"
			+ "    fdfp.FINANCIAL_PRODUCT_ID = :financialProductId  ";
}
