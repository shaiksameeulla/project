DROP PROCEDURE IF EXISTS sp_billing_consolidation_1;
CREATE PROCEDURE sp_billing_consolidation_1()
BEGIN
  
  DECLARE _step varchar(150);
  DECLARE rowcount int(11) DEFAULT 0;

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


  START TRANSACTION;
    
    
    
    

    
    
    
    
    
    
    
    

    

    
    
    
    
    
    
    
    
    
    
    

    
    
    
    SET _step = "GENERATE SO SUMMARY FOR CN HAVING BOOKING_STATUS = ‘RTB’";

    
    INSERT INTO ff_f_billing_consignment_summary(
    TRANSACTION_IDENTIFIER,
    SALES_ORDER,
    BOOKING_DATE,
    PRODUCT_ID,
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
    VALUE_OF_MATERIAL
    )
    
    
    SELECT NULL AS TRANSACTION_IDENTIFIER,
           NULL AS SALES_ORDER,
           STR_TO_DATE(date_format(bk.BOOKING_DATE, '%d/%m/%Y'),'%d/%m/%Y') AS BOOKING_DATE,
           p.PRODUCT_ID AS PRODUCT_ID,
           bo.OFFICE_ID PICKUP_OFFICE_ID,
           NULL AS INVOICE_ID,
           1 AS CREATED_BY,
           curdate() AS CREATED_DATE,
           NULL AS UPDATE_BY,
           NULL AS UPDATE_DATE,
           count(cn.CONSG_ID) AS NO_OF_PICKUPS,
           'N' AS TRANSFER_STATUS,
           NULL AS SAP_TIMESTAMP,
           CASE
           WHEN cnew.DISTRIBUTION_CHANNELS IS NULL THEN '00'
           ELSE cnew.DISTRIBUTION_CHANNELS
           END AS DISTRIBUTION_CHANNELS, 
           CASE
           WHEN bk.CUSTOMER IS NULL THEN
             CASE
             WHEN bktype.BOOKING_TYPE = 'FC' THEN
               'FOC'
             ELSE
               'CASH'
             END
           WHEN bk.CUSTOMER IS NOT NULL THEN
             CASE
             WHEN cnew.CONTRACT_NO IS NULL THEN
               cnew.CUSTOMER_CODE
             ELSE
               cpbl.SHIPPED_TO_CODE
             END
           END AS SHIP_TO_CODE,
           'CN',
           NULL AS VALUE_OF_MATERIAL
      FROM ff_f_consignment cn
           
           JOIN ff_f_booking bk
             ON bk.CONSG_NUMBER = cn.CONSG_NO
           JOIN ff_d_booking_type bktype
             ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
           LEFT JOIN ff_d_customer cnew
             ON cnew.CUSTOMER_ID = bk.CUSTOMER
           
           LEFT JOIN ff_d_pickup_delivery_contract pdc
             ON (cn.ORG_OFF = pdc.OFFICE_ID AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
           LEFT JOIN ff_d_pickup_delivery_location pdl
             ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
           LEFT JOIN ff_d_contract_payment_billing_location cpbl
             ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
           LEFT JOIN ff_d_rate_contract rc
             ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
           
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
          WHERE     cn.BILLING_STATUS = 'RTB'
                AND (   (    cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'
                         AND date_format(bk.BOOKING_DATE, '%d/%m/%Y') <=
                                date_format(DATE_SUB(CURRENT_DATE(), INTERVAL p.CONSOLIDATION_WINDOW DAY), '%d/%m/%Y'))
                     OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
                         AND (cnew) )
                    )
                AND cn.FINAL_WEIGHT IS NOT NULL
                AND cn.DEST_PINCODE IS NOT NULL
         GROUP BY date_format(bk.BOOKING_DATE, '%d/%m/%Y'),
                  PICKUP_OFFICE_ID,
                  SHIP_TO_CODE,
                  DISTRIBUTION_CHANNELS, 
                  PRODUCT_ID;
                  
                  








































    SELECT
      row_count()
    INTO
      rowcount;

    IF rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Data found for consolidation, rolling back.', MYSQL_ERRNO=210;
      rollback;
    end if;

    

    
    
    
    SET _step = "READ ALL BOOKING DATA HAVING BOOKING_STATUS = ‘RTB’";

    
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
    
    SELECT CONSG_ID,
           CONSG_NO,
           ORG_OFF,
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
           result.CREATED_BY,
           result.CREATED_DATE,
           result.UPDATE_BY,
           result.UPDATE_DATE,
           CONSG_STATUS,
           DECLARED_VALUE,
           cnsum.SUMMARY_ID AS SUMMARY,
           NULL AS INVOICE,
           'BILL' AS SCOPE,
           1 AS CONSIGNMENT_COPY,
           0 AS VERSION,
           CUSTOMER
      FROM (SELECT cn.CONSG_ID,
                   cn.CONSG_NO,
                   cn.ORG_OFF,
                   bk.BOOKING_DATE,
                   bo.OFFICE_ID,
                   CASE
                      WHEN bk.CUSTOMER IS NULL
                      THEN
                         CASE
                            WHEN bktype.BOOKING_TYPE = 'FC' THEN 'FOC'
                            ELSE 'CASH'
                         END
                      WHEN bk.CUSTOMER IS NOT NULL
                      THEN
                         CASE
                            WHEN cnew.CONTRACT_NO IS NULL THEN cnew.CUSTOMER_CODE
                            ELSE cpbl.SHIPPED_TO_CODE
                         END
                   END
                      AS CN_SHIP_TO_CODE,
                   CASE
                      WHEN cnew.DISTRIBUTION_CHANNELS IS NULL THEN '00'
                      ELSE cnew.DISTRIBUTION_CHANNELS
                   END
                      AS CN_DISTRIBUTION_CHANNELS,
                   cn.PRODUCT,
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
                   CASE cn.DT_UPDATE_TO_CENTRAL WHEN NULL THEN 'N' END
                      AS DT_UPDATE_TO_CENTRAL,
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
                   cn.CUSTOMER
              FROM ff_f_consignment cn
                   
                   JOIN ff_f_booking bk ON bk.CONSG_NUMBER = cn.CONSG_NO
                   JOIN ff_d_booking_type bktype
                      ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
                   LEFT JOIN ff_d_customer cnew ON cnew.CUSTOMER_ID = bk.CUSTOMER
                   
                   LEFT JOIN ff_d_pickup_delivery_contract pdc
                      ON (    cn.ORG_OFF = pdc.OFFICE_ID
                          AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
                   LEFT JOIN ff_d_pickup_delivery_location pdl
                      ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
                   LEFT JOIN ff_d_contract_payment_billing_location cpbl
                      ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
                   LEFT JOIN ff_d_rate_contract rc
                      ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
                   
                   JOIN ff_d_product p ON p.PRODUCT_ID = cn.PRODUCT
                   JOIN ff_d_office bo ON bo.OFFICE_ID = bk.BOOKING_OFF
                   JOIN ff_d_consignment_type ct
                      ON ct.CONSIGNMENT_TYPE_ID = cn.CONSG_TYPE
                   JOIN ff_d_pincode dpin ON dpin.PINCODE_ID = bk.DEST_PINCODE
                   JOIN ff_d_city dcity ON dcity.CITY_ID = dpin.CITY_ID
             WHERE     cn.BILLING_STATUS = 'RTB'
                   AND (   (    cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'
                            AND date_format(bk.BOOKING_DATE, '%d/%m/%Y') <=
                                   date_format(
                                      DATE_SUB(CURRENT_DATE(), INTERVAL p.CONSOLIDATION_WINDOW DAY),
                                      '%d/%m/%Y'))
                        OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'))
                        AND cn.FINAL_WEIGHT IS NOT NULL
                  AND cn.DEST_PINCODE IS NOT NULL) result
           JOIN ff_f_billing_consignment_summary cnsum
              ON (    date_format(cnsum.BOOKING_DATE, '%d/%m/%Y') =
                         date_format(result.BOOKING_DATE, '%d/%m/%Y')
                  AND cnsum.PICKUP_OFFICE_ID = result.OFFICE_ID
                  AND cnsum.SHIP_TO_CODE = result.CN_SHIP_TO_CODE
                  AND cnsum.DISTRIBUTION_CHANNEL =
                         result.CN_DISTRIBUTION_CHANNELS
                  AND cnsum.PRODUCT_ID = result.PRODUCT);

    SELECT
      row_count()
    INTO
      rowcount;

    IF rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Data found for consolidation, rolling back', MYSQL_ERRNO=211;
      
      rollback;
    end if;


    


    
    
    
    SET _step = "GET LATEST RATES FOR BOOKING DATA FOR CONSIGNMENTS HAVING BOOKING_STATUS = ‘RTB’";

    
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
    UPDATE_DATE
    )
    
    SELECT cr.CONSIGNMENT_RATE_ID,
           bc.BILLING_CONSIGNMENT_ID,
           cn.CONSG_ID,
           cr.RATE_CALCULATED_FOR,
           
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.FINAL_SLAB_RATE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS FINAL_SLAB_RATE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.FUEL_SURCHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS FUEL_SURCHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.RISK_SURCHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS RISK_SURCHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.TO_PAY_CHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS TO_PAY_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.COD_CHARGES
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS COD_CHARGES,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.PARCEL_HANDLING_CHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS PARCEL_HANDLING_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.AIRPORT_HANDLING_CHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS AIRPORT_HANDLING_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.DOCUMENT_HANDLING_CHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS DOCUMENT_HANDLING_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.OTHER_OR_SPECIAL_CHARGES
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.OTHER_CHARGES is null, 0, octraiexpenseentry.OTHER_CHARGES))
              ELSE 0
           END
              AS OTHER_OR_SPECIAL_CHARGES,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.SERVICE_TAX
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS SERVICE_TAX,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.EDUCATION_CESS
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS EDUCATION_CESS,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.HIGHER_EDUCATION_CES
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS HIGHER_EDUCATION_CES,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.STATE_TAX
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS STATE_TAX,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.SURCHARGE_ON_STATE_TAX
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS SURCHARGE_ON_STATE_TAX,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.AMOUNT is null, 0, octraiexpenseentry.AMOUNT))
              ELSE 0
           END
              AS OCTROI,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.SERVICE_CHARGE is null, 0, octraiexpenseentry.SERVICE_CHARGE))
              ELSE 0
           END
              AS SERVICE_CHARGE_ON_OCTROI,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.SERVICE_TAX is null, 0, octraiexpenseentry.SERVICE_TAX))
              ELSE 0
           END
              AS SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.EDUCATION_CESS is null, 0, octraiexpenseentry.EDUCATION_CESS))
              ELSE 0
           END
              AS EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 sum(if(octraiexpenseentry.HIGHER_EDUCATION_CESS is null, 0, octraiexpenseentry.HIGHER_EDUCATION_CESS))
              ELSE 0
           END
              AS HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.GRAND_TOTAL_INCLUDING_TAX
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS GRAND_TOTAL_INCLUDING_TAX,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 cr.LC_CHARGE
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 0
              ELSE 0
           END
              AS LC_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 cr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE
              ELSE 0
           END
              AS OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE,
           CASE
              WHEN (   cr.RATE_CALCULATED_FOR = 'R'
                    OR (    cr.RATE_CALCULATED_FOR = 'B'
                        AND cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                        AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'))
              THEN
                 0
              WHEN cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'
              THEN
                 cr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE
              ELSE 0
           END
              AS OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE,
           NULL AS COD_AMOUNT,
           NULL AS CREATED_BY,
           CURDATE() AS CREATED_DATE,
           NULL AS UPDATE_BY,
           NULL AS UPDATE_DATE
      FROM ff_f_consignment cn
           
           JOIN ff_f_booking bk ON bk.CONSG_NUMBER = cn.CONSG_NO
           JOIN ff_d_booking_type bktype
              ON bk.BOOKING_TYPE = bktype.BOOKING_TYPE_ID
           LEFT JOIN ff_d_customer cnew ON cnew.CUSTOMER_ID = bk.CUSTOMER
           
           LEFT JOIN ff_d_pickup_delivery_contract pdc
              ON (    cn.ORG_OFF = pdc.OFFICE_ID
                  AND cnew.CUSTOMER_ID = pdc.CUSTOMER_ID)
           LEFT JOIN ff_d_pickup_delivery_location pdl
              ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
           LEFT JOIN ff_d_contract_payment_billing_location cpbl
              ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
           LEFT JOIN ff_d_rate_contract rc
              ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
           
           JOIN ff_d_product p ON p.PRODUCT_ID = cn.PRODUCT
           JOIN ff_d_office bo ON bo.OFFICE_ID = bk.BOOKING_OFF
           JOIN ff_d_consignment_type ct
              ON ct.CONSIGNMENT_TYPE_ID = cn.CONSG_TYPE
           JOIN ff_d_pincode dpin ON dpin.PINCODE_ID = bk.DEST_PINCODE
           JOIN ff_d_city dcity ON dcity.CITY_ID = dpin.CITY_ID
           
           LEFT JOIN ff_f_consignment_rate cr ON cr.CONSIGNMENT_ID = cn.CONSG_ID
           JOIN ff_f_billing_consignment bc
              ON (bc.CONSG_ID = cr.CONSIGNMENT_ID AND bc.version = 0)
           
           
           
           LEFT JOIN ff_f_expense_entries octraiexpenseentry
              ON cn.CONSG_ID = octraiexpenseentry.CONSG_ID
           LEFT JOIN ff_f_expense octraiexpense
              ON octraiexpense.EXPENSE_ID = octraiexpenseentry.EXPENSE_ID
           LEFT JOIN ff_d_gl_master glm ON glm.GL_ID = octraiexpense.BANK_GL_ID
     WHERE     cn.BILLING_STATUS = 'RTB'
           AND (   (    cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N'
                    AND cn.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N'
                    AND date_format(bk.BOOKING_DATE, '%d/%m/%Y') <=
                           date_format(
                              DATE_SUB(CURRENT_DATE(),
                                       INTERVAL p.CONSOLIDATION_WINDOW DAY),
                              '%d/%m/%Y'))
                OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y'))
           AND cn.FINAL_WEIGHT IS NOT NULL
           AND cn.DEST_PINCODE IS NOT NULL
           AND octraiexpense.EXPENSE_FOR = 'C'
           AND glm.IS_OCTROI_GL = 'Y'
    GROUP BY cn.CONSG_NO;


    SELECT
      row_count()
    INTO
      rowcount;

    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='No CN Rate Data found, rolling back', MYSQL_ERRNO=212;
      
      rollback;
    end if;

    UPDATE ff_f_billing_consignment
    SET version = 1
    WHERE version = 0;

    
    

    
    
    
  COMMIT;
END;
