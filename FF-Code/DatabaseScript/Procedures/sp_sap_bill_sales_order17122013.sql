DROP PROCEDURE IF EXISTS sp_sap_bill_sales_order;
CREATE PROCEDURE sp_sap_bill_sales_order()
BEGIN
  DECLARE rowcount int(11) DEFAULT 0;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';
  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    SHOW ERRORS;
    ROLLBACK;
  END;
  DECLARE EXIT HANDLER FOR no_such_table
  BEGIN
    SHOW ERRORS;
    ROLLBACK;
  END;
START TRANSACTION;
INSERT INTO ff_f_bill(
        INVOICE_NUMBER,
   SHIP_TO_CODE,
   PRODUCT_ID,
   BILLING_OFFICE_ID,
   FROM_DATE,
   TO_DATE,
   NO_OF_PICKUPS,
   FREIGHT,
   FUEL_SURCHARGE,
   RISK_SURCHARGE,
   PARCAL_HANDLING_CHARGE,
   AIRPORT_HANDLING_CHARGE,
   DOC_HANDLING_CHARGE,
   OTHER_CHARGES,
   TOTAL,
   SERVICE_TAX,
   EDUCATION_CESS,
   HIGHER_EDUCATION_CESS,
   STATE_TAX,
   SURCHARGE_ON_STATE_TAX,
   GRAND_TOTAL,
   GRAND_TOTAL_ROUNDED_OFF,
   BILL_GENERATED,
   CREATED_BY,
   CREATED_DATE,
   UPDATE_BY,
   UPDATE_DATE,
   BILL_STATUS)
SELECT
  sbso.BILL_NUMBER AS InvoiceNumber,
  summary.SHIP_TO_CODE AS ShipToCode,
  cndata.ProductId AS ProductId,
  case 
  when CUSTOMER_TYPE_CODE = 'WI' then null
  when CUSTOMER_TYPE_CODE = 'PR' then null
  when CUSTOMER_TYPE_CODE = 'TP' then null
  when CUSTOMER_TYPE_CODE = 'CR' 
    or CUSTOMER_TYPE_CODE = 'CC' 
    or CUSTOMER_TYPE_CODE = 'LC' 
    or CUSTOMER_TYPE_CODE = 'CD'
    or CUSTOMER_TYPE_CODE = 'GV' then
    case
    when rc.TYPE_OF_BILLING = 'DBDP' 
      or rc.TYPE_OF_BILLING = 'DBCP' then summary.PICKUP_OFFICE_ID
    when rc.TYPE_OF_BILLING = 'CBCP' then pdc_ship.OFFICE_ID
    end
  when CUSTOMER_TYPE_CODE = 'RL' then pdc_rl.OFFICE_ID
  when CUSTOMER_TYPE_CODE = 'BA' or CUSTOMER_TYPE_CODE = 'BV' then CUST_OFFICE_MAPPED_TO
  when CUSTOMER_TYPE_CODE = 'FR' then summary.PICKUP_OFFICE_ID
  when CUSTOMER_TYPE_CODE = 'AC' then CUST_OFFICE_MAPPED_TO
  when CUSTOMER_TYPE_CODE = 'IC' then null
  when CUSTOMER_TYPE_CODE = 'OC' then null
  else null
  end as billingOffice,
  min(FromDate) AS FromDate,
  min(ToDate) AS ToDate,
  count(NoOfPickups) AS NoOfPickups,
  sum(Freight) AS Freight,
  sum(FuelSurcharge) AS FuelSurcharge,
  sum(RiskSurcharge) AS RiskSurcharge,
  sum(ParcelHandlingCharge) AS ParcelHandlingCharge,
  sum(AirportHandlingCharge) AS AirportHandlingCharge,
  sum(DocHandlingCharge) AS DocHandlingCharge,
  sum(OtherCharges) AS OtherCharges,
  sum(TotalCharges) AS TotalCharges,
  sum(ServiceTax) AS ServiceTax,
  sum(EducationCess) AS EducationCess,
  sum(HigherEducationCess) AS HigherEducationCess,
  sum(StateTax) AS StateTax,
  sum(SurchargeOnStateTax) AS SurchargeOnStateTax,
  sum(GrandTotal) AS GrandTotal,
  sum(GrandTotalRounded) AS GrandTotalRounded,
  'Y' AS BillGenerated,
  1 AS CREATED_BY,
  curdate() AS CREATED_DATE,
  NULL AS UPDATE_BY,
  NULL AS UPDATE_DATE,
  'O' AS BillStatus
FROM
  (SELECT
     consignment.SUMMARY AS SummaryId,
     prod.PRODUCT_ID AS ProductId,
     (booking.BOOKING_DATE) AS FromDate,
     (booking.BOOKING_DATE) AS ToDate,
     (consignment.CONSG_NO) AS NoOfPickups,
     (if(booking_rates.FINAL_SLAB_RATE IS NULL, 0, booking_rates.FINAL_SLAB_RATE)) AS Freight,
     (if(booking_rates.FUEL_SURCHARGE IS NULL, 0, booking_rates.FUEL_SURCHARGE)) AS FuelSurcharge,
     (if(booking_rates.RISK_SURCHARGE IS NULL, 0, booking_rates.RISK_SURCHARGE)) AS RiskSurcharge,
     (if(booking_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, booking_rates.PARCEL_HANDLING_CHARGE)) AS ParcelHandlingCharge,
     (if(booking_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, booking_rates.AIRPORT_HANDLING_CHARGE)) AS AirportHandlingCharge,
     (if(booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, booking_rates.DOCUMENT_HANDLING_CHARGE)) AS DocHandlingCharge,
     CASE
       WHEN (custtype.CUSTOMER_TYPE_CODE != 'RL' AND
             (prod.CONSG_SERIES = 'D' OR
              prod.CONSG_SERIES = 'L'))
       THEN
           if(booking_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, booking_rates.PARCEL_HANDLING_CHARGE)
         + if(booking_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, booking_rates.AIRPORT_HANDLING_CHARGE)
         + if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
       ELSE
         CASE
           WHEN consignment.CONSG_STATUS != 'R'
           THEN
               if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
             + if(booking_rates.OCTROI IS NULL, 0, booking_rates.OCTROI)
             + if(booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, booking_rates.SERVICE_CHARGE_ON_OCTROI)
           WHEN consignment.CONSG_STATUS = 'R'
           THEN
               if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
             + IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) = 0, if(booking_rates.TO_PAY_CHARGE IS NULL, 0, booking_rates.TO_PAY_CHARGE), 0)
             + if(booking_rates.OCTROI IS NULL, 0, booking_rates.OCTROI)
             + if(booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, booking_rates.SERVICE_CHARGE_ON_OCTROI)
             - IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) > 0, if(booking_rates.COD_CHARGES IS NULL, 0, booking_rates.COD_CHARGES), 0)
             + if(rto_rates.FINAL_SLAB_RATE IS NULL, 0, rto_rates.FINAL_SLAB_RATE)
             + if(rto_rates.FUEL_SURCHARGE IS NULL, 0, rto_rates.FUEL_SURCHARGE)
             + if(rto_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, rto_rates.PARCEL_HANDLING_CHARGE)
             + if(rto_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, rto_rates.AIRPORT_HANDLING_CHARGE)
             + if(rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, rto_rates.DOCUMENT_HANDLING_CHARGE)
             + if(rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, rto_rates.OTHER_OR_SPECIAL_CHARGES)
             + IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) = 0, if(rto_rates.TO_PAY_CHARGE IS NULL, 0, rto_rates.TO_PAY_CHARGE), 0)
             + if(rto_rates.OCTROI IS NULL, 0, rto_rates.OCTROI)
             + if(rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, rto_rates.SERVICE_CHARGE_ON_OCTROI)
             - IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) > 0, if(rto_rates.COD_CHARGES IS NULL, 0, rto_rates.COD_CHARGES), 0)
         ELSE
             0
         END
     END
       AS OtherCharges,
     CASE
       WHEN (custtype.CUSTOMER_TYPE_CODE != 'RL' AND
             (prod.CONSG_SERIES = 'D' OR
              prod.CONSG_SERIES = 'L'))
       THEN
               if(booking_rates.LC_AMOUNT IS NULL, 0, booking_rates.LC_AMOUNT)
             + if(booking_rates.FINAL_SLAB_RATE IS NULL, 0, booking_rates.FINAL_SLAB_RATE)
             + if(booking_rates.LC_CHARGE IS NULL, 0, booking_rates.LC_CHARGE)
             + if(rto_rates.FINAL_SLAB_RATE IS NULL, 0, rto_rates.FINAL_SLAB_RATE)
             + if(rto_rates.FUEL_SURCHARGE IS NULL, 0, rto_rates.FUEL_SURCHARGE)
             + if(rto_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, rto_rates.PARCEL_HANDLING_CHARGE)
             + if(rto_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, rto_rates.AIRPORT_HANDLING_CHARGE)
             + if(rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, rto_rates.DOCUMENT_HANDLING_CHARGE)
             + if(rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, rto_rates.OTHER_OR_SPECIAL_CHARGES)
             + if(booking_rates.RISK_SURCHARGE IS NULL, 0, booking_rates.RISK_SURCHARGE)
             + if(booking_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, booking_rates.PARCEL_HANDLING_CHARGE)
             + if(booking_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, booking_rates.AIRPORT_HANDLING_CHARGE)
             + if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
       ELSE
         CASE
           WHEN consignment.CONSG_STATUS != 'R'
           THEN
                     if(booking_rates.FINAL_SLAB_RATE IS NULL, 0, booking_rates.FINAL_SLAB_RATE)
                   + if(booking_rates.RISK_SURCHARGE IS NULL, 0, booking_rates.RISK_SURCHARGE)
                   + if(booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, booking_rates.DOCUMENT_HANDLING_CHARGE)
                   + if(booking_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, booking_rates.PARCEL_HANDLING_CHARGE)
                   + if(booking_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, booking_rates.AIRPORT_HANDLING_CHARGE)
                   + if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
                   + if(booking_rates.OCTROI IS NULL, 0, booking_rates.OCTROI)
                   + if(booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, booking_rates.SERVICE_CHARGE_ON_OCTROI)
           WHEN consignment.CONSG_STATUS = 'R'
           THEN
                     if(booking_rates.FINAL_SLAB_RATE IS NULL, 0, booking_rates.FINAL_SLAB_RATE)
                   + if(booking_rates.RISK_SURCHARGE IS NULL, 0, booking_rates.RISK_SURCHARGE)
                   + if(booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, booking_rates.DOCUMENT_HANDLING_CHARGE)
                   + if(booking_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, booking_rates.PARCEL_HANDLING_CHARGE)
                   + if(booking_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, booking_rates.AIRPORT_HANDLING_CHARGE)
                   + if(booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, booking_rates.OTHER_OR_SPECIAL_CHARGES)
                   + IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) = 0, if(booking_rates.TO_PAY_CHARGE IS NULL, 0, booking_rates.TO_PAY_CHARGE), 0)
                   + if(booking_rates.OCTROI IS NULL, 0, booking_rates.OCTROI)
                   + if(booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, booking_rates.SERVICE_CHARGE_ON_OCTROI)
                   - IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) > 0, if(booking_rates.COD_CHARGES IS NULL, 0, booking_rates.COD_CHARGES), 0)
                   + if(rto_rates.FINAL_SLAB_RATE IS NULL, 0, rto_rates.FINAL_SLAB_RATE)
                   + if(rto_rates.FUEL_SURCHARGE IS NULL, 0, rto_rates.FUEL_SURCHARGE)
                   + if(rto_rates.PARCEL_HANDLING_CHARGE IS NULL, 0, rto_rates.PARCEL_HANDLING_CHARGE)
                   + if(rto_rates.AIRPORT_HANDLING_CHARGE IS NULL, 0, rto_rates.AIRPORT_HANDLING_CHARGE)
                   + if(rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL, 0, rto_rates.DOCUMENT_HANDLING_CHARGE)
                   + if(rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL, 0, rto_rates.OTHER_OR_SPECIAL_CHARGES)
                   + IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) = 0, if(rto_rates.TO_PAY_CHARGE IS NULL, 0, rto_rates.TO_PAY_CHARGE), 0)
                   + if(rto_rates.OCTROI IS NULL, 0, rto_rates.OCTROI)
                   + if(rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL, 0, rto_rates.SERVICE_CHARGE_ON_OCTROI)
                   - IF(prod.CONSG_SERIES = 'T' AND if(booking_rates.COD_AMOUNT IS NULL, 0, booking_rates.COD_AMOUNT) > 0, if(rto_rates.COD_CHARGES IS NULL, 0, rto_rates.COD_CHARGES), 0)
           ELSE
             0
         END
     END
       AS TotalCharges,
     (if(booking_rates.SERVICE_TAX IS NULL, 0, booking_rates.SERVICE_TAX)) AS ServiceTax,
     (if(booking_rates.EDUCATION_CESS IS NULL, 0, booking_rates.EDUCATION_CESS)) AS EducationCess,
     (if(booking_rates.HIGHER_EDUCATION_CES IS NULL, 0, booking_rates.HIGHER_EDUCATION_CES)) AS HigherEducationCess,
     (if(booking_rates.STATE_TAX IS NULL, 0, booking_rates.STATE_TAX)) AS StateTax,
     (if(booking_rates.SURCHARGE_ON_STATE_TAX IS NULL, 0, booking_rates.SURCHARGE_ON_STATE_TAX)) AS SurchargeOnStateTax,
     (if(booking_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL, 0, booking_rates.GRAND_TOTAL_INCLUDING_TAX)) AS GrandTotal,
     (if(booking_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL, 0, booking_rates.GRAND_TOTAL_INCLUDING_TAX)) AS GrandTotalRounded,
     'Y' AS BillGenerated,
     1 AS CREATED_BY,
     curdate() AS CREATED_DATE,
     NULL AS UPDATE_BY,
     NULL AS UPDATE_DATE,
     'O' AS BillStatus,
     consignment.ORIGIN_OFFICE,
     customer.CUSTOMER_ID,
     custtype.CUSTOMER_TYPE_CODE,
     customer.OFFICE_MAPPED_TO as CUST_OFFICE_MAPPED_TO
   FROM
      ff_f_billing_consignment consignment
      JOIN ff_f_billing_consignment_rate booking_rates
       ON (booking_rates.BILLING_CONSIGNMENT =
             consignment.BILLING_CONSIGNMENT_ID AND
           booking_rates.RATE_CALCULATED_FOR = 'B')
      LEFT JOIN ff_f_billing_consignment_rate rto_rates
       ON (rto_rates.BILLING_CONSIGNMENT = consignment.BILLING_CONSIGNMENT_ID AND
           rto_rates.RATE_CALCULATED_FOR = 'R')
      JOIN ff_f_booking booking ON consignment.CONSG_NO = booking.CONSG_NUMBER
      LEFT JOIN ff_d_customer customer ON customer.CUSTOMER_ID = booking.CUSTOMER
      LEFT JOIN ff_d_customer_type custtype
       ON custtype.CUSTOMER_TYPE_ID = customer.CUSTOMER_TYPE
      JOIN ff_d_product prod ON consignment.PRODUCT = prod.PRODUCT_ID) AS cndata
  JOIN ff_f_billing_consignment_summary summary
    ON summary.SUMMARY_ID = cndata.SummaryId
  JOIN ff_f_sap_bill_sales_order sbso ON summary.SUMMARY_ID = sbso.SUMMARY_ID
  -- get contract    
  LEFT JOIN ff_d_pickup_delivery_contract pdc
    ON (cndata.ORIGIN_OFFICE = pdc.OFFICE_ID AND cndata.CUSTOMER_ID = pdc.CUSTOMER_ID)
  LEFT JOIN ff_d_pickup_delivery_location pdl
    ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
  LEFT JOIN ff_d_contract_payment_billing_location cpbl
    ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
  LEFT JOIN ff_d_rate_contract rc
    ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
  -- get billing location from contract data
  LEFT JOIN ff_d_contract_payment_billing_location cpbl_ship
    on (cpbl_ship.SHIPPED_TO_CODE = summary.SHIP_TO_CODE and cpbl_ship.LOCATION_OPERATION_TYPE in ('B','BP'))
  left join ff_d_pickup_delivery_location pdl_ship
    on pdl_ship.LOCATION_ID = cpbl_ship.PICKUP_DELIVERY_LOCATION
  left join ff_d_pickup_delivery_contract pdc_ship
    on pdc_ship.CONTRACT_ID = pdl_ship.CONTRACT_ID
  -- get billing and pickup location from contract data for RL customer
  left join ff_d_pickup_delivery_contract pdc_rl
    on pdc_rl.CUSTOMER_ID = cndata.CUSTOMER_ID
  left join ff_d_pickup_delivery_location pdl_rl
    on pdl_rl.CONTRACT_ID = pdc_rl.CONTRACT_ID
  left join ff_d_contract_payment_billing_location cpbl_rl
    on (cpbl_rl.PICKUP_DELIVERY_LOCATION = pdl_rl.LOCATION_ID and cpbl_rl.LOCATION_OPERATION_TYPE = 'BP')
GROUP BY sbso.BILL_NUMBER,
  summary.PRODUCT_CODE,
  billingOffice
;
    
    SELECT
      row_count()
    INTO
      rowcount;
    
    IF rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=213;
      rollback;
    end if;
    update ff_f_billing_consignment_summary bcs
    join ff_f_sap_bill_sales_order sbso on (sbso.SUMMARY_ID = bcs.SUMMARY_ID)
    JOIN ff_f_bill b on (b.INVOICE_NUMBER = sbso.BILL_NUMBER)
    SET bcs.INVOICE = b.INVOICE_ID, bcs.SALES_ORDER = sbso.SALES_ORDER_NUMBER
    WHERE sbso.SUMMARY_ID = bcs.SUMMARY_ID
    and b.BILLING_OFFICE_ID = bcs.PICKUP_OFFICE_ID
    and b.SHIP_TO_CODE = bcs.SHIP_TO_CODE;
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=214;
      rollback;
    end if;
    update ff_f_billing_consignment bc
    join ff_f_billing_consignment_summary ffbcs on ffbcs.summary_id = bc.summary
    set bc.invoice = ffbcs.INVOICE;
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=215;
      rollback;
    end if;
    UPDATE ff_f_sap_bill_sales_order sbso
    JOIN ff_f_billing_consignment bc on (sbso.SUMMARY_ID = bc.SUMMARY)
    SET sbso.DT_SAP_INBOUND = 'C' , sbso.SAP_TIMESTAMP = curdate()
    WHERE sbso.SUMMARY_ID = bc.SUMMARY
    AND sbso.DT_SAP_INBOUND = 'N';
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=213;
      rollback;
    else
      SELECT 'Executed Successfully sap bill sales order procedure, committing';
      commit;
    end if;
END;
