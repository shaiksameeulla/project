DROP PROCEDURE IF EXISTS sp_billing_consolidation;
CREATE PROCEDURE sp_billing_consolidation()
BEGIN
  -- --------------------------------------------
  -- Created by: Kaustubh A. Gajare (37434)
  -- --------------------------------------------
  
  DECLARE _step varchar(150);
  DECLARE summary_rowcount int(11) DEFAULT 0;
  DECLARE consignment_rowcount int(11) DEFAULT 0;
  DECLARE consignment_rate_rowcount int(11) DEFAULT 0;
  DECLARE update_rowcount int(11) DEFAULT 0;
  DECLARE reset_cn_flags_rowcount INT(11) DEFAULT 0;

  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    SHOW ERRORS;
    SELECT _step;
    ROLLBACK;
  END;

  DECLARE EXIT HANDLER FOR no_such_table
  BEGIN
    SHOW ERRORS;
    SELECT _step;
    ROLLBACK;
  END;


  -- -------------------------------------
  -- CONSOLIDATE CONSOGNMENT DATA
  -- -------------------------------------
  START TRANSACTION;

    -- -------------------------------------------
    -- GENERATE CONSIGNMENT SUMMARY
    -- -------------------------------------------
    SET _step = "GENERATE SO SUMMARY FOR CN HAVING BOOKING_STATUS = ?RTB?";
    -- SO SUMMARY
    INSERT INTO ff_f_billing_consignment_summary(
    TRANSACTION_IDENTIFIER,
    SALES_ORDER,
    BOOKING_DATE,
    PRODUCT_CODE,
    PICKUP_OFFICE_ID,
    INVOICE,
    CREATED_BY,
    CREATED_DATE,
    UPDATE_BY,
    UPDATE_DATE,
    NO_OF_PICKUPS,
    TRANSFER_STATUS,
    SAP_TIMESTAMP,
    DISTRIBUTION_CHANNEL,
    SHIP_TO_CODE,
    SUMMARY_TYPE,
    UNIT_OF_MEASUREMENT,
    SUMMARY_CATEGORY,
    DESTINATION_OFFICE,
    VERSION
    )
    -- GENERATE SO SUMMARY FOR COMBINATION OF
    -- BOOKING DATE, BOOKING OFFICE, SHIP TO CODE, DISTRIBUTION CHANNEL AND PRODUCT TYPE;
    SELECT
      TRANSACTION_IDENTIFIER,
      SALES_ORDER,
      BOOKING_DATE,
      PRODUCT_CODE,
      PICKUP_OFFICE_ID,
      INVOICE_ID,
      CREATED_BY,
      CREATED_DATE,
      UPDATE_BY,
      UPDATE_DATE,
      NO_OF_PICKUPS,
      TRANSFER_STATUS,
      SAP_TIMESTAMP,
      DISTRIBUTION_CHANNELS,
      SHIP_TO_CODE,
      SUMMARY_TYPE,
      UNIT_OF_MEASUREMENT,
      SUMMARY_CATEGORY,
      DESTINATION_OFFICE,
      0 AS VERSION
    FROM (SELECT
        NULL AS TRANSACTION_IDENTIFIER,
        NULL AS SALES_ORDER,
        STR_TO_DATE(DATE_FORMAT(bk.BOOKING_DATE, '%d/%m/%Y'), '%d/%m/%Y') AS BOOKING_DATE,
        p.PRODUCT_CODE AS PRODUCT_CODE,
        bo.OFFICE_ID PICKUP_OFFICE_ID,
        NULL AS INVOICE_ID,
        1 AS CREATED_BY,
        NOW() AS CREATED_DATE,
        NULL AS UPDATE_BY,
        NULL AS UPDATE_DATE,
        COUNT(cn.CONSG_ID) AS NO_OF_PICKUPS,
        'N' AS TRANSFER_STATUS,
        NULL AS SAP_TIMESTAMP,
        CASE
          WHEN cnew.DISTRIBUTION_CHANNELS IS NULL THEN '00' ELSE cnew.DISTRIBUTION_CHANNELS
        END AS DISTRIBUTION_CHANNELS, 
        CASE 
        WHEN bk.PICKUP_RUNSHEET_NO IS NOT NULL THEN
          bk.SHIPPED_TO_CODE      
        WHEN bk.PICKUP_RUNSHEET_NO IS NULL THEN
          CASE 
          WHEN bk.CUSTOMER IS NULL THEN 
            CASE
            WHEN bktype.BOOKING_TYPE = 'FC' THEN 'FOC' 
            ELSE 'CASH'
            END
          WHEN bk.CUSTOMER IS NOT NULL THEN 
            CASE
              WHEN cnew.CONTRACT_NO IS NULL THEN cnew.CUSTOMER_CODE 
              ELSE cpbl.SHIPPED_TO_CODE
            END
          END
        END AS SHIP_TO_CODE,
        'CN' AS SUMMARY_TYPE,
        NULL AS UNIT_OF_MEASUREMENT,
        CASE
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          cnew.CUSTOMER_CODE IS NULL THEN 'TPCASH'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'D' THEN 'TPCBD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'S' THEN 'TPCBRD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NOT NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'D' THEN 'PCDCBD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NOT NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'S' THEN 'PCDCBRD' ELSE 'OTHERS'
        END AS SUMMARY_CATEGORY,
        CASE 
          WHEN p.CONSG_SERIES = 'T' AND cn.CONSG_STATUS = 'D' THEN fdo.OFFICE_CODE
          ELSE 'NA'
        END AS DESTINATION_OFFICE
      FROM ff_f_consignment cn
        -- get customer
        JOIN ff_f_booking bk
          ON bk.CONSG_NUMBER = cn.CONSG_NO
        JOIN ff_d_booking_type bktype
          ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
        LEFT JOIN ff_d_customer cnew
          ON cnew.CUSTOMER_ID = bk.CUSTOMER
        LEFT JOIN ff_d_customer_type ctype
          ON ctype.CUSTOMER_TYPE_ID = cnew.CUSTOMER_TYPE
        -- get contract    
        LEFT JOIN ff_d_pickup_delivery_contract pdc
          ON (cn.ORG_OFF = pdc.OFFICE_ID AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
        LEFT JOIN ff_d_pickup_delivery_location pdl
          ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
        LEFT JOIN ff_d_contract_payment_billing_location cpbl
          ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
        LEFT JOIN ff_d_rate_contract rc
          ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
        -- get other details
        JOIN ff_d_product p
          ON p.PRODUCT_ID = cn.PRODUCT
        JOIN ff_d_office bo
          ON bo.OFFICE_ID = bk.BOOKING_OFF
        JOIN ff_d_consignment_type ct
          ON ct.CONSIGNMENT_TYPE_ID = cn.CONSG_TYPE
        JOIN ff_d_pincode dpin
          ON dpin.PINCODE_ID = bk.DEST_PINCODE
        JOIN ff_d_city dcity
          ON dcity.CITY_ID = dpin.CITY_ID
        -- get consignment rate
        LEFT JOIN ff_f_consignment_rate ffcr_booking_rate 
          ON (cn.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')
        -- get delivery details
        LEFT JOIN ff_f_delivery_dtls ffdd 
          ON (cn.CONSG_ID = ffdd.CONSIGNMENT_ID AND ffdd.DELIVERY_STATUS = 'D')
        LEFT JOIN ff_f_delivery ffd ON ffdd.DELIVERY_ID = ffd.DELIVERY_ID
        LEFT JOIN ff_d_office fdo ON ffd.CREATED_OFFICE_ID = fdo.OFFICE_ID
      WHERE cn.BILLING_STATUS = 'RTB' 
--       below conditions are commented as these are now 
--       being handled at Batch job where eligible consignments
--       are selected and marked for consolidation
--       WHERE  
--       (
--         -- T Series credit booking consignments which are either delivered or RTO delivered
--         (p.CONSG_SERIES = 'T' 
--         AND bktype.BOOKING_TYPE = 'CR'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--         AND (cn.CONSG_STATUS = 'D' OR cn.CONSG_STATUS = 'S'))
--         -- T Series cash booking which are not RTO
--         OR 
--         (p.CONSG_SERIES = 'T'
--         AND bktype.BOOKING_TYPE = 'CS'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--         AND (cn.CONSG_STATUS != 'R' OR cn.CONSG_STATUS != 'S'))
--         -- Other consignment series fresh/modified/rto bookings
--         OR 
--         (p.CONSG_SERIES != 'T'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND ( 
--       		(
--       			cn.CONSG_STATUS != 'R' AND cn.CONSG_STATUS != 'S'
--       			AND (
--       				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
--       				AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--               )
--       				OR
--       				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y')
--       			)
--       		)
--       		OR (cn.CONSG_STATUS = 'R' AND cn.CONSG_STATUS = 'S')
--       	))
--       )
--       AND cn.DEST_PINCODE IS NOT NULL
--       AND bk.BOOKING_DATE IS NOT NULL
--       AND bo.OFFICE_ID IS NOT NULL
--       AND p.PRODUCT_CODE IS NOT NULL
--       AND ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL
--       AND cn.CONSG_STATUS IS NOT NULL
      GROUP BY SUMMARY_CATEGORY,  -- Added as per the mail from Somesh dated 21st October 2013
           DATE_FORMAT(bk.BOOKING_DATE, '%d/%m/%Y'),
           PICKUP_OFFICE_ID,
           SHIP_TO_CODE,
           DISTRIBUTION_CHANNELS, -- Added as per discussion on 6th August 2013
           PRODUCT_CODE,
           DESTINATION_OFFICE
--            ,CONSIGNMENT_TYPE_ID, -- Removed as per discussion on 6th August 2013
--            DESTINATION_REGION_ID -- Removed as per discussion on 6th August 2013
    ) AS RESULT
    WHERE RESULT.SHIP_TO_CODE IS NOT NULL;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      summary_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    IF summary_rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Data found for consolidation, rolling back.', MYSQL_ERRNO=210;
      rollback;
    end if;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------
    -- GENERATE BILLING CONSIGNMENTS
    -- -------------------------------------------
    SET _step = "READ ALL BOOKING DATA HAVING BOOKING_STATUS = ?RTB?";
    -- CONSIGNMENT DATA
    INSERT INTO ff_f_billing_consignment(
    CONSG_ID,
    CONSG_NO,
    ORIGIN_OFFICE,
    DEST_PINCODE,
    NO_OF_PCS,
    CONSG_TYPE,
    PRICE,
    PRODUCT,
    ACTUAL_WEIGHT,
    VOL_WEIGHT,
    FINAL_WEIGHT,
    DT_TO_BRANCH,
    DT_TO_CENTRAL,
    DT_FROM_OPSMAN,
    UPDATED_PROCESS_FROM,
    DT_UPDATE_TO_CENTRAL,
    MOBILE_NO,
    CONTENT,
    PAPER_WORK,
    INSURED_BY,
    INSURANCE_POLICY_NO,
    REF_NO,
    HEIGHT,
    LENGTH,
    BREADTH,
    RECEIVED_DATE,
    DLV_DATE_TIME,
    RECV_NAME_OR_COMP_SEAL,
    RECEIVED_STATUS,
    CONSIGNOR,
    CONSIGNEE,
    DT_TO_OPSMAN,
    CREATED_BY,
    CREATED_DATE,
    UPDATE_BY,
    UPDATE_DATE,
    CONSG_STATUS,
    DECLARED_VALUE,
    SUMMARY,
    INVOICE,
    SCOPE,
    CONSIGNMENT_COPY,
    VERSION,
    CUSTOMER
    )
    -- READ ALL BOOKING DATA HAVING BOOKING_STATUS = ?RTB?
    SELECT
      CONSG_ID,
      CONSG_NO,
      ORG_OFF,
      DEST_PINCODE,
      NO_OF_PCS,
      CONSG_TYPE,
      PRICE,
      PRODUCT_ID,
      ACTUAL_WEIGHT,
      VOL_WEIGHT,
      FINAL_WEIGHT,
      DT_TO_BRANCH,
      DT_TO_CENTRAL,
      DT_FROM_OPSMAN,
      UPDTAED_PROCESS_FROM,
      DT_UPDATE_TO_CENTRAL,
      MOBILE_NO,
      CONTENT,
      PAPER_WORK,
      INSURED_BY,
      INSURANCE_POLICY_NO,
      REF_NO,
      HEIGHT,
      LENGTH,
      BREATH,
      RECEIVED_DATE,
      DLV_DATE_TIME,
      RECV_NAME_OR_COMP_SEAL,
      RECEIVED_STATUS,
      CONSIGNOR,
      CONSIGNEE,
      DT_TO_OPSMAN,
      RESULT.CREATED_BY,
      RESULT.CREATED_DATE,
      RESULT.UPDATE_BY,
      RESULT.UPDATE_DATE,
      CONSG_STATUS,
      DECLARED_VALUE,
      cnsum.SUMMARY_ID AS SUMMARY,
      NULL AS INVOICE,
      'BILL' AS SCOPE,
      1 AS CONSIGNMENT_COPY,
      0 AS VERSION,
      CUSTOMER
    FROM (SELECT
        cn.CONSG_ID,
        cn.CONSG_NO,
        cn.ORG_OFF,
        bk.BOOKING_DATE,
        bo.OFFICE_ID,
        CASE 
        WHEN bk.PICKUP_RUNSHEET_NO IS NOT NULL THEN
          bk.SHIPPED_TO_CODE      
        WHEN bk.PICKUP_RUNSHEET_NO IS NULL THEN
          CASE 
          WHEN bk.CUSTOMER IS NULL THEN 
            CASE
            WHEN bktype.BOOKING_TYPE = 'FC' THEN 'FOC' 
            ELSE 'CASH'
            END
          WHEN bk.CUSTOMER IS NOT NULL THEN 
            CASE
              WHEN cnew.CONTRACT_NO IS NULL THEN cnew.CUSTOMER_CODE 
              ELSE cpbl.SHIPPED_TO_CODE
            END
          END
        END AS CN_SHIP_TO_CODE,
        CASE
          WHEN cnew.DISTRIBUTION_CHANNELS IS NULL THEN '00' ELSE cnew.DISTRIBUTION_CHANNELS
        END AS CN_DISTRIBUTION_CHANNELS,
        p.PRODUCT_CODE,
        p.PRODUCT_ID,
        cn.DEST_PINCODE,
        cn.NO_OF_PCS,
        cn.CONSG_TYPE,
        cn.PRICE,
        cn.ACTUAL_WEIGHT,
        cn.VOL_WEIGHT,
        cn.FINAL_WEIGHT,
        cn.DT_TO_BRANCH,
        cn.DT_TO_CENTRAL,
        cn.DT_FROM_OPSMAN,
        cn.UPDTAED_PROCESS_FROM,
        CASE cn.DT_UPDATE_TO_CENTRAL
          WHEN NULL THEN 'N'
        END AS DT_UPDATE_TO_CENTRAL,
        cn.MOBILE_NO,
        cn.CONTENT,
        cn.PAPER_WORK,
        cn.INSURED_BY,
        cn.INSURANCE_POLICY_NO,
        cn.REF_NO,
        cn.HEIGHT,
        cn.LENGTH,
        cn.BREATH,
        cn.RECEIVED_DATE,
        cn.DLV_DATE_TIME,
        cn.RECV_NAME_OR_COMP_SEAL,
        cn.RECEIVED_STATUS,
        cn.CONSIGNOR,
        cn.CONSIGNEE,
        cn.DT_TO_OPSMAN,
        cn.CREATED_BY,
        cn.CREATED_DATE,
        cn.UPDATE_BY,
        cn.UPDATE_DATE,
        cn.CONSG_STATUS,
        cn.DECLARED_VALUE,
        cn.CUSTOMER,
        CASE
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          cnew.CUSTOMER_CODE IS NULL THEN 'TPCASH'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'D' THEN 'TPCBD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'S' THEN 'TPCBRD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NOT NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'D' THEN 'PCDCBD'
          WHEN p.CONSG_SERIES = 'T' AND
          ffcr_booking_rate.COD_AMOUNT IS NOT NULL AND
          ctype.CUSTOMER_TYPE_CODE = 'CR' AND
          cn.CONSG_STATUS = 'S' THEN 'PCDCBRD' ELSE 'OTHERS'
        END AS SUMMARY_CATEGORY,
        CASE 
          WHEN p.CONSG_SERIES = 'T' AND cn.CONSG_STATUS = 'D' THEN fdo.OFFICE_CODE
          ELSE 'NA'
        END AS DESTINATION_OFFICE
      FROM ff_f_consignment cn
        -- get customer
        JOIN ff_f_booking bk
          ON bk.CONSG_NUMBER = cn.CONSG_NO
        JOIN ff_d_booking_type bktype
          ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
        LEFT JOIN ff_d_customer cnew
          ON cnew.CUSTOMER_ID = bk.CUSTOMER
        LEFT JOIN ff_d_customer_type ctype
          ON ctype.CUSTOMER_TYPE_ID = cnew.CUSTOMER_TYPE
        -- get contract
        LEFT JOIN ff_d_pickup_delivery_contract pdc
          ON (cn.ORG_OFF = pdc.OFFICE_ID
          AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
        LEFT JOIN ff_d_pickup_delivery_location pdl
          ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
        LEFT JOIN ff_d_contract_payment_billing_location cpbl
          ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
        LEFT JOIN ff_d_rate_contract rc
          ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
        -- get other details
        JOIN ff_d_product p
          ON p.PRODUCT_ID = cn.PRODUCT
        JOIN ff_d_office bo
          ON bo.OFFICE_ID = bk.BOOKING_OFF
        JOIN ff_d_consignment_type ct
          ON ct.CONSIGNMENT_TYPE_ID = cn.CONSG_TYPE
        JOIN ff_d_pincode dpin
          ON dpin.PINCODE_ID = bk.DEST_PINCODE
        JOIN ff_d_city dcity
          ON dcity.CITY_ID = dpin.CITY_ID
        -- get consignment rate
        LEFT JOIN ff_f_consignment_rate ffcr_booking_rate 
          ON (cn.CONSG_ID = ffcr_booking_rate.CONSIGNMENT_ID AND ffcr_booking_rate.RATE_CALCULATED_FOR = 'B')
        -- get delivery details
        LEFT JOIN ff_f_delivery_dtls ffdd 
          ON (cn.CONSG_ID = ffdd.CONSIGNMENT_ID AND ffdd.DELIVERY_STATUS = 'D')
        LEFT JOIN ff_f_delivery ffd ON ffdd.DELIVERY_ID = ffd.DELIVERY_ID
        LEFT JOIN ff_d_office fdo ON ffd.CREATED_OFFICE_ID = fdo.OFFICE_ID
      WHERE cn.BILLING_STATUS = 'RTB' 
--       below conditions are commented as these are now 
--       being handled at Batch job where eligible consignments
--       are selected and marked for consolidation
--       WHERE  
--       (
--         -- T Series credit booking consignments which are either delivered or RTO delivered
--         (p.CONSG_SERIES = 'T' 
--         AND bktype.BOOKING_TYPE = 'CR'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--         AND (cn.CONSG_STATUS = 'D' OR cn.CONSG_STATUS = 'S'))
--         -- T Series cash booking which are not RTO
--         OR 
--         (p.CONSG_SERIES = 'T'
--         AND bktype.BOOKING_TYPE = 'CS'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--         AND (cn.CONSG_STATUS != 'R' OR cn.CONSG_STATUS != 'S'))
--         -- Other consignment series fresh/modified/rto bookings
--         OR 
--         (p.CONSG_SERIES != 'T'
--         AND cn.BILLING_STATUS = 'TBB'
--         AND ( 
--       		(
--       			cn.CONSG_STATUS != 'R' AND cn.CONSG_STATUS != 'S'
--       			AND (
--       				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
--       				AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--               )
--       				OR
--       				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y')
--       			)
--       		)
--       		OR (cn.CONSG_STATUS = 'R' AND cn.CONSG_STATUS = 'S')
--       	))
--       )
--       AND cn.DEST_PINCODE IS NOT NULL
--       AND bk.BOOKING_DATE IS NOT NULL
--       AND bo.OFFICE_ID IS NOT NULL
--       AND p.PRODUCT_CODE IS NOT NULL
--       AND ffcr_booking_rate.CONSIGNMENT_RATE_ID IS NOT NULL
--       AND cn.CONSG_STATUS IS NOT NULL    
    ) RESULT
      JOIN ff_f_billing_consignment_summary cnsum
        ON cnsum.SUMMARY_CATEGORY = RESULT.SUMMARY_CATEGORY
        AND (DATE_FORMAT(cnsum.BOOKING_DATE, '%d/%m/%Y') =
        DATE_FORMAT(RESULT.BOOKING_DATE, '%d/%m/%Y')
        AND cnsum.PICKUP_OFFICE_ID = RESULT.OFFICE_ID
        AND cnsum.SHIP_TO_CODE = RESULT.CN_SHIP_TO_CODE
        AND cnsum.DISTRIBUTION_CHANNEL =
        RESULT.CN_DISTRIBUTION_CHANNELS
        AND cnsum.PRODUCT_CODE = RESULT.PRODUCT_CODE
        AND DATE_FORMAT(cnsum.CREATED_DATE, '%m/%d/%Y') = DATE_FORMAT(CURDATE(), '%m/%d/%Y')
        AND cnsum.DESTINATION_OFFICE = RESULT.DESTINATION_OFFICE
        AND cnsum.VERSION = 0)
    WHERE RESULT.CN_SHIP_TO_CODE IS NOT NULL;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      consignment_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    IF consignment_rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Data found for consolidation, rolling back', MYSQL_ERRNO=211;
      rollback;
    ELSE
      UPDATE ff_f_billing_consignment_summary ffbcs
        SET ffbcs.VERSION = 1
        WHERE ffbcs.VERSION = 0;
    end if;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------
    -- GENERATE BILLING CONSIGNMENTS RATE DATA
    -- -------------------------------------------
    SET _step = "GET LATEST RATES FOR BOOKING DATA FOR CONSIGNMENTS HAVING BOOKING_STATUS = ?RTB?";
    -- RATES FOR CONSIGNMENT DATA
    INSERT INTO ff_f_billing_consignment_rate (
    CONSIGNMENT_RATE_ID,
    BILLING_CONSIGNMENT,
    CONSIGNMENT,
    RATE_CALCULATED_FOR,
    FINAL_SLAB_RATE,
    FUEL_SURCHARGE,
    RISK_SURCHARGE,
    TO_PAY_CHARGE,
    COD_CHARGES,
    PARCEL_HANDLING_CHARGE,
    AIRPORT_HANDLING_CHARGE,
    DOCUMENT_HANDLING_CHARGE,
    OTHER_OR_SPECIAL_CHARGES,
    SERVICE_TAX,
    EDUCATION_CESS,
    HIGHER_EDUCATION_CES,
    STATE_TAX,
    SURCHARGE_ON_STATE_TAX,
    OCTROI,
    SERVICE_CHARGE_ON_OCTROI,
    SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE,
    EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
    HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
    GRAND_TOTAL_INCLUDING_TAX,
    LC_CHARGE,
    OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE,
    OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE,
    COD_AMOUNT,
    CREATED_BY,
    CREATED_DATE,
    UPDATE_BY,
    UPDATE_DATE,
    VERSION
    )
    -- GET LATEST RATES FOR BOOKING DATA FOR CONSIGNMENTS HAVING BOOKING_STATUS = ?RTB?
    SELECT
      cr.CONSIGNMENT_RATE_ID,
      bc.BILLING_CONSIGNMENT_ID,
      cn.CONSG_ID,
      CASE
        WHEN p.CONSG_SERIES = 'T' AND
        cr.COD_AMOUNT IS NULL AND
        cnew.CUSTOMER_CODE IS NULL THEN cr.RATE_CALCULATED_FOR
        WHEN p.CONSG_SERIES = 'T' AND
        cr.COD_AMOUNT IS NULL AND
        ctype.CUSTOMER_TYPE_CODE = 'CR' AND
        cn.CONSG_STATUS = 'D' THEN 'B'
        WHEN p.CONSG_SERIES = 'T' AND
        cr.COD_AMOUNT IS NULL AND
        ctype.CUSTOMER_TYPE_CODE = 'CR' AND
        cn.CONSG_STATUS = 'S' THEN 'R'
        WHEN p.CONSG_SERIES = 'T' AND
        cr.COD_AMOUNT IS NOT NULL AND
        ctype.CUSTOMER_TYPE_CODE = 'CR' AND
        cn.CONSG_STATUS = 'D' THEN 'B'
        WHEN p.CONSG_SERIES = 'T' AND
        cr.COD_AMOUNT IS NOT NULL AND
        ctype.CUSTOMER_TYPE_CODE = 'CR' AND
        cn.CONSG_STATUS = 'S' THEN 'R' ELSE cr.RATE_CALCULATED_FOR
      END AS RATE_CALCULATED_FOR,
      -- all rate components
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'))
        THEN
          SUM(cr.FINAL_SLAB_RATE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.FINAL_SLAB_RATE - bcr.FINAL_SLAB_RATE)
        ELSE
          0
      END
        AS FINAL_SLAB_RATE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.FUEL_SURCHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.FUEL_SURCHARGE - bcr.FUEL_SURCHARGE)
        ELSE
          0
      END
        AS FUEL_SURCHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.RISK_SURCHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.RISK_SURCHARGE - bcr.RISK_SURCHARGE)
        ELSE
          0
      END
        AS RISK_SURCHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.TO_PAY_CHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.TO_PAY_CHARGE - bcr.TO_PAY_CHARGE)
        ELSE
          0
      END
        AS TO_PAY_CHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.COD_CHARGES)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.COD_CHARGES - bcr.COD_CHARGES)
        ELSE
          0
      END
        AS COD_CHARGES,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.PARCEL_HANDLING_CHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.PARCEL_HANDLING_CHARGE - bcr.PARCEL_HANDLING_CHARGE)
        ELSE
          0
      END
        AS PARCEL_HANDLING_CHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.AIRPORT_HANDLING_CHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.AIRPORT_HANDLING_CHARGE - bcr.AIRPORT_HANDLING_CHARGE)
        ELSE
          0
      END
        AS AIRPORT_HANDLING_CHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.DOCUMENT_HANDLING_CHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.DOCUMENT_HANDLING_CHARGE - bcr.DOCUMENT_HANDLING_CHARGE)
        ELSE
          0
      END
        AS DOCUMENT_HANDLING_CHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.OTHER_OR_SPECIAL_CHARGES)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' AND
             cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
        THEN
          SUM(cr.OTHER_OR_SPECIAL_CHARGES - bcr.OTHER_OR_SPECIAL_CHARGES)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND
             cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(octraiexpenseentry.OTHER_CHARGES IS NULL, 0, octraiexpenseentry.OTHER_CHARGES))
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' AND
             cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          (cr.OTHER_OR_SPECIAL_CHARGES - bcr.OTHER_OR_SPECIAL_CHARGES) +
          SUM(if(octraiexpenseentry.OTHER_CHARGES IS NULL, 0, octraiexpenseentry.OTHER_CHARGES))
        ELSE
          0
      END
        AS OTHER_OR_SPECIAL_CHARGES,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.SERVICE_TAX)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.SERVICE_TAX - bcr.SERVICE_TAX)
        ELSE
          0
      END
        AS SERVICE_TAX,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.EDUCATION_CESS)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.EDUCATION_CESS - bcr.EDUCATION_CESS)
        ELSE
          0
      END
        AS EDUCATION_CESS,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.HIGHER_EDUCATION_CES)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.HIGHER_EDUCATION_CES - bcr.HIGHER_EDUCATION_CES)
        ELSE
          0
      END
        AS HIGHER_EDUCATION_CES,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.STATE_TAX)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.STATE_TAX - bcr.STATE_TAX)
        ELSE
          0
      END
        AS STATE_TAX,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.SURCHARGE_ON_STATE_TAX)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.SURCHARGE_ON_STATE_TAX - bcr.SURCHARGE_ON_STATE_TAX)
        ELSE
          0
      END
        AS SURCHARGE_ON_STATE_TAX,
      CASE
        WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(
                octraiexpenseentry.AMOUNT IS NULL,
                0,
                octraiexpenseentry.AMOUNT))
        ELSE
          0
      END
        AS OCTROI,
      CASE
        WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(
                octraiexpenseentry.SERVICE_CHARGE IS NULL,
                0,
                octraiexpenseentry.SERVICE_CHARGE))
        ELSE
          0
      END
        AS SERVICE_CHARGE_ON_OCTROI,
      CASE
        WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(
                octraiexpenseentry.SERVICE_TAX IS NULL,
                0,
                octraiexpenseentry.SERVICE_TAX))
        ELSE
          0
      END
        AS SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE,
      CASE
        WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(
                octraiexpenseentry.EDUCATION_CESS IS NULL,
                0,
                octraiexpenseentry.EDUCATION_CESS))
        ELSE
          0
      END
        AS EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
      CASE
        WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
        THEN
          SUM(if(
                octraiexpenseentry.HIGHER_EDUCATION_CESS IS NULL,
                0,
                octraiexpenseentry.HIGHER_EDUCATION_CESS))
        ELSE
          0
      END
        AS HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.GRAND_TOTAL_INCLUDING_TAX)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.GRAND_TOTAL_INCLUDING_TAX - bcr.GRAND_TOTAL_INCLUDING_TAX)
        ELSE
          0
      END
        AS GRAND_TOTAL_INCLUDING_TAX,
      CASE
        WHEN (cr.RATE_CALCULATED_FOR = 'R' OR
              (cr.RATE_CALCULATED_FOR = 'B' AND
               cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
               cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
        THEN
          SUM(cr.LC_CHARGE)
        WHEN cr.RATE_CALCULATED_FOR = 'B' AND
             cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y'
        THEN
          SUM(cr.LC_CHARGE - bcr.LC_CHARGE)
        ELSE
          0
      END
        AS LC_CHARGE,
      0 AS OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE,
      0 AS OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE,
      NULL AS COD_AMOUNT,
      NULL AS CREATED_BY,
      NOW() AS CREATED_DATE,
      NULL AS UPDATE_BY,
      NULL AS UPDATE_DATE,
      0 AS VERSION
    FROM ff_f_consignment cn
      -- get customer
      JOIN ff_f_booking bk
        ON bk.CONSG_NUMBER = cn.CONSG_NO
      JOIN ff_d_booking_type bktype
        ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
      LEFT JOIN ff_d_customer cnew
        ON cnew.CUSTOMER_ID = bk.CUSTOMER
      LEFT JOIN ff_d_customer_type ctype
        ON ctype.CUSTOMER_TYPE_ID = cnew.CUSTOMER_TYPE
      -- get contract
      LEFT JOIN ff_d_pickup_delivery_contract pdc
        ON (cn.ORG_OFF = pdc.OFFICE_ID
        AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
      LEFT JOIN ff_d_pickup_delivery_location pdl
        ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
      LEFT JOIN ff_d_contract_payment_billing_location cpbl
        ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
      LEFT JOIN ff_d_rate_contract rc
        ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
      -- get other details
      JOIN ff_d_product p
        ON p.PRODUCT_ID = cn.PRODUCT
      JOIN ff_d_office bo
        ON bo.OFFICE_ID = bk.BOOKING_OFF
      JOIN ff_d_consignment_type ct
        ON ct.CONSIGNMENT_TYPE_ID = cn.CONSG_TYPE
      JOIN ff_d_pincode dpin
        ON dpin.PINCODE_ID = bk.DEST_PINCODE
      JOIN ff_d_city dcity
        ON dcity.CITY_ID = dpin.CITY_ID
      -- get consignment rates
      LEFT JOIN ff_f_consignment_rate cr
        ON cr.CONSIGNMENT_ID = cn.CONSG_ID
      JOIN ff_f_billing_consignment bc
        ON (bc.CONSG_ID = cr.CONSIGNMENT_ID AND bc.VERSION = 0)
      -- get expenses
      LEFT JOIN ff_f_expense_entries octraiexpenseentry
        ON cn.CONSG_ID = octraiexpenseentry.CONSG_ID
      LEFT JOIN ff_f_expense octraiexpense
        ON (octraiexpense.EXPENSE_ID = octraiexpenseentry.EXPENSE_ID AND octraiexpense.EXPENSE_FOR = 'C')
      LEFT JOIN ff_d_gl_master glm
        ON (glm.GL_ID = octraiexpense.BANK_GL_ID AND glm.IS_OCTROI_GL = 'Y')
      -- get already billed component details
      LEFT JOIN (SELECT
          bcr.CONSIGNMENT,
          bcr.RATE_CALCULATED_FOR,
          IF(bcr.FINAL_SLAB_RATE IS NULL, 0, SUM(bcr.FINAL_SLAB_RATE)) AS FINAL_SLAB_RATE,
          IF(bcr.FUEL_SURCHARGE IS NULL, 0, SUM(bcr.FUEL_SURCHARGE)) AS FUEL_SURCHARGE,
          IF(bcr.RISK_SURCHARGE IS NULL, 0, SUM(bcr.RISK_SURCHARGE)) AS RISK_SURCHARGE,
          IF(bcr.TO_PAY_CHARGE IS NULL, 0, SUM(bcr.TO_PAY_CHARGE)) AS TO_PAY_CHARGE,
          IF(bcr.COD_CHARGES IS NULL, 0, SUM(bcr.COD_CHARGES)) AS COD_CHARGES,
          IF(bcr.PARCEL_HANDLING_CHARGE IS NULL, 0, SUM(bcr.PARCEL_HANDLING_CHARGE)) AS PARCEL_HANDLING_CHARGE,
          IF(bcr.AIRPORT_HANDLING_CHARGE IS NULL, 0, SUM(bcr.AIRPORT_HANDLING_CHARGE)) AS AIRPORT_HANDLING_CHARGE,
          IF(bcr.DOCUMENT_HANDLING_CHARGE IS NULL, 0, SUM(bcr.DOCUMENT_HANDLING_CHARGE)) AS DOCUMENT_HANDLING_CHARGE,
          IF(bcr.RTO_DISCOUNT IS NULL, 0, SUM(bcr.RTO_DISCOUNT)) AS RTO_DISCOUNT,
          IF(bcr.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, SUM(bcr.OTHER_OR_SPECIAL_CHARGES)) AS OTHER_OR_SPECIAL_CHARGES,
          IF(bcr.DISCOUNT IS NULL, 0, SUM(bcr.DISCOUNT)) AS DISCOUNT,
          IF(bcr.SERVICE_TAX IS NULL, 0, SUM(bcr.SERVICE_TAX)) AS SERVICE_TAX,
          IF(bcr.EDUCATION_CESS IS NULL, 0, SUM(bcr.EDUCATION_CESS)) AS EDUCATION_CESS,
          IF(bcr.HIGHER_EDUCATION_CES IS NULL, 0, SUM(bcr.HIGHER_EDUCATION_CES)) AS HIGHER_EDUCATION_CES,
          IF(bcr.STATE_TAX IS NULL, 0, SUM(bcr.STATE_TAX)) AS STATE_TAX,
          IF(bcr.SURCHARGE_ON_STATE_TAX IS NULL, 0, SUM(bcr.SURCHARGE_ON_STATE_TAX)) AS SURCHARGE_ON_STATE_TAX,
          IF(bcr.OCTROI IS NULL, 0, SUM(bcr.OCTROI)) AS OCTROI,
          IF(bcr.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, SUM(bcr.SERVICE_CHARGE_ON_OCTROI)) AS SERVICE_CHARGE_ON_OCTROI,
          IF(bcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL, 0, SUM(bcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE)) AS SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE,
          IF(bcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL, 0, SUM(bcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE)) AS EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
          IF(bcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL, 0, SUM(bcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE)) AS HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
          IF(bcr.TOTAL_WITHOUT_TAX IS NULL, 0, SUM(bcr.TOTAL_WITHOUT_TAX)) AS TOTAL_WITHOUT_TAX,
          IF(bcr.GRAND_TOTAL_INCLUDING_TAX IS NULL, 0, SUM(bcr.GRAND_TOTAL_INCLUDING_TAX)) AS GRAND_TOTAL_INCLUDING_TAX,
          IF(bcr.LC_CHARGE IS NULL, 0, SUM(bcr.LC_CHARGE)) AS LC_CHARGE,
          IF(bcr.DECLARED_VALUE IS NULL, 0, SUM(bcr.DECLARED_VALUE)) AS DECLARED_VALUE,
          IF(bcr.SLAB_RATE IS NULL, 0, SUM(bcr.SLAB_RATE)) AS SLAB_RATE,
          IF(bcr.FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE IS NULL, 0, SUM(bcr.FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE)) AS FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE,
          IF(bcr.LC_AMOUNT IS NULL, 0, SUM(bcr.LC_AMOUNT)) AS LC_AMOUNT,
          IF(bcr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL, 0, SUM(bcr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE)) AS OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE,
          IF(bcr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL, 0, SUM(bcr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE)) AS OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE,
          IF(bcr.COD_AMOUNT IS NULL, 0, SUM(bcr.COD_AMOUNT)) AS COD_AMOUNT
        FROM ff_f_billing_consignment_rate bcr
        WHERE bcr.RATE_CALCULATED_FOR = 'B'
        GROUP BY bcr.CONSIGNMENT,
                 bcr.RATE_CALCULATED_FOR) AS bcr
        ON (bcr.CONSIGNMENT = bc.CONSG_ID AND
        bcr.RATE_CALCULATED_FOR = cr.RATE_CALCULATED_FOR)
    WHERE cn.BILLING_STATUS = 'RTB' 
--     below conditions are commented as these are now 
--     being handled at Batch job where eligible consignments
--     are selected and marked for consolidation
--     WHERE  
--     (
--       -- T Series credit booking consignments which are either delivered or RTO delivered
--       (p.CONSG_SERIES = 'T' 
--       AND bktype.BOOKING_TYPE = 'CR'
--       AND cn.BILLING_STATUS = 'TBB'
--       AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--       AND (cn.CONSG_STATUS = 'D' OR cn.CONSG_STATUS = 'S'))
--       -- T Series cash booking which are not RTO
--       OR 
--       (p.CONSG_SERIES = 'T'
--       AND bktype.BOOKING_TYPE = 'CS'
--       AND cn.BILLING_STATUS = 'TBB'
--       AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--       AND (cn.CONSG_STATUS != 'R' OR cn.CONSG_STATUS != 'S'))
--       -- Other consignment series fresh/modified/rto bookings
--       OR 
--       (p.CONSG_SERIES != 'T'
--       AND cn.BILLING_STATUS = 'TBB'
--       AND ( 
--     		(
--     			cn.CONSG_STATUS != 'R' AND cn.CONSG_STATUS != 'S'
--     			AND (
--     				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
--     				AND (bk.BOOKING_DATE <= DATE_SUB(NOW(), INTERVAL p.CONSOLIDATION_WINDOW DAY))
--             )
--     				OR
--     				(cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y')
--     			)
--     		)
--     		OR (cn.CONSG_STATUS = 'R' AND cn.CONSG_STATUS = 'S')
--     	))
--     )
--     AND cn.DEST_PINCODE IS NOT NULL
--     AND bk.BOOKING_DATE IS NOT NULL
--     AND bo.OFFICE_ID IS NOT NULL
--     AND p.PRODUCT_CODE IS NOT NULL
--     AND cn.CONSG_STATUS IS NOT NULL
    GROUP BY cn.CONSG_NO,
             RATE_CALCULATED_FOR;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      consignment_rate_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    if consignment_rate_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Rate Data found, rolling back', MYSQL_ERRNO=212;
      rollback;
    end if;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------------------------------------------------------------    
    UPDATE ff_f_consignment ffc
      JOIN ff_f_billing_consignment ffbc
      ON ffc.CONSG_ID = ffbc.CONSG_ID
      SET ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N', ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N', ffc.BILLING_STATUS = 'BLD'
      WHERE ffbc.VERSION = 0;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      reset_cn_flags_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    if reset_cn_flags_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=213;
      rollback;
    end if;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------------------------------------------------------------    
    UPDATE ff_f_billing_consignment
    SET version = 1
    WHERE version = 0;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      update_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    IF 
      update_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=214;
      rollback;
    END IF;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------------------------------------------------------------    
    UPDATE ff_f_consignment_rate ffcr
      JOIN ff_f_billing_consignment_rate ffbcr
      ON ffcr.CONSIGNMENT_RATE_ID = ffbcr.CONSIGNMENT_RATE_ID
      SET ffcr.BILLED = 'Y'
      WHERE ffbcr.VERSION = 0;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      update_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    IF 
      update_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=215;
      rollback;
    END IF;
    -- -------------------------------------------------------------------------------------------------    
    -- -------------------------------------------------------------------------------------------------    
    UPDATE ff_f_billing_consignment_rate ffbcr
      SET ffbcr.VERSION = 1
      WHERE ffbcr.VERSION = 0;
    -- -------------------------------------------------------------------------------------------------    
    SELECT
      row_count()
    INTO
      update_rowcount;
    -- -------------------------------------------------------------------------------------------------    
    IF 
      update_rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=216;
      rollback;
    ELSEIF
      summary_rowcount > 0 and
            consignment_rowcount > 0 and
            consignment_rate_rowcount > 0 and
            update_rowcount > 0 then
      select 'Consignment Execution Completed, committing - 217';
      commit;
    ELSE
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=218;
      rollback;
    END IF;
    -- -------------------------------------------------------------------------------------------------    

END;
