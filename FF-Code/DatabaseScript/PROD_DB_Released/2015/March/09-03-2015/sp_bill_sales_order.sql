DROP PROCEDURE IF EXISTS CorpUDAAN.sp_sap_bill_sales_order;
CREATE PROCEDURE CorpUDAAN.`sp_sap_bill_sales_order`()
BEGIN
  
  DECLARE rowcount int(11) DEFAULT 0;
  DECLARE fuel_surcharge_percentage_formula varchar(100) DEFAULT '';
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';
  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE insert_data_into_bill_start_time datetime;
  DECLARE update_ff_f_billing_consignment_summary_start_time datetime;
  DECLARE update_ff_f_billing_consignment_start_time datetime;
  DECLARE update_ff_f_sap_bill_sales_order_start_time datetime;
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
    
    
    
    SELECT
      RATE_COMPNENT_DESC
    INTO
      fuel_surcharge_percentage_formula
    FROM
      ff_d_rate_component
    WHERE
      RATE_COMPONENT_CODE = 'FCPO';
    
    
   SELECT
    CURRENT_TIMESTAMP()
   INTO
    insert_data_into_bill_start_time;
    
    INSERT INTO ff_f_bill(
            INVOICE_NUMBER,
            SHIP_TO_CODE,
            PRODUCT_ID,
            FINANCIAL_PRODUCT_ID,
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
            BILL_STATUS,
            CUSTOMER,
            VERSION,
            FUEL_SURCHARGE_PERCENTAGE,
            FUEL_SURCHARGE_PERCENTAGE_FORMULA,
            BILL_GENERATION_DATE,
            LC_AMOUNT,
            LC_CHARGE,
            COD_AMOUNT,
            COD_CHARGE,
            RTO_CHARGE,
            BILL_TYPE
       )
       SELECT
            sbso.BILL_NUMBER AS InvoiceNumber
           ,summary.SHIP_TO_CODE AS ShipToCode
           ,prod.PRODUCT_ID AS ProductId
           ,fdfp.FINANCIAL_PRODUCT_ID AS FinancialProductId
           ,CASE
              WHEN custtype.CUSTOMER_TYPE_CODE = 'WI'
              THEN
                NULL
              WHEN custtype.CUSTOMER_TYPE_CODE = 'PR'
              THEN
                NULL
              WHEN custtype.CUSTOMER_TYPE_CODE = 'TP'
              THEN
                NULL
              WHEN custtype.CUSTOMER_TYPE_CODE = 'CR' OR
                   custtype.CUSTOMER_TYPE_CODE = 'CC' OR
                   custtype.CUSTOMER_TYPE_CODE = 'LC' OR
                   custtype.CUSTOMER_TYPE_CODE = 'CD' OR
                   custtype.CUSTOMER_TYPE_CODE = 'GV'
              THEN
                CASE
                  WHEN fdrc_ship.TYPE_OF_BILLING = 'DBDP' OR
                       fdrc_ship.TYPE_OF_BILLING = 'DBCP'
                  THEN
                    pdc_ship.OFFICE_ID
                  WHEN fdrc_ship.TYPE_OF_BILLING = 'CBCP'
                  THEN
                    pdc_ship.OFFICE_ID
                END
              WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
              THEN
                pdc_ship.OFFICE_ID
              WHEN custtype.CUSTOMER_TYPE_CODE = 'BA' OR
                   custtype.CUSTOMER_TYPE_CODE = 'BV' OR
                   custtype.CUSTOMER_TYPE_CODE = 'AC'
              THEN
                customer.OFFICE_MAPPED_TO
              WHEN custtype.CUSTOMER_TYPE_CODE = 'FR'
              THEN
                pdc_ship.OFFICE_ID
              WHEN custtype.CUSTOMER_TYPE_CODE = 'IC'
              THEN
                NULL
              WHEN custtype.CUSTOMER_TYPE_CODE = 'OC'
              THEN
                NULL
              ELSE
                NULL
            END
              AS billingOffice
           ,CASE
              WHEN fdrc_ship.BILLING_CYCLE = 'F'
              THEN
                CASE
                  WHEN extract(DAY FROM max(booking.BOOKING_DATE)) <= 15
                  THEN
                    date_sub(
                      max(booking.BOOKING_DATE)
                     ,INTERVAL (extract(DAY FROM max(booking.BOOKING_DATE)) - 1) DAY)
                  WHEN extract(DAY FROM max(booking.BOOKING_DATE)) > 15
                  THEN
                    date_sub(
                      max(booking.BOOKING_DATE)
                     ,INTERVAL (extract(DAY FROM max(booking.BOOKING_DATE)) - 16) DAY)
                END
              WHEN fdrc_ship.BILLING_CYCLE = 'M'
              THEN
                date_sub(
                  max(booking.BOOKING_DATE)
                 ,INTERVAL (extract(DAY FROM max(booking.BOOKING_DATE)) - 1) DAY)
            END
              AS FromDate
           ,CASE
              WHEN fdrc_ship.BILLING_CYCLE = 'F'
              THEN
                CASE
                  WHEN extract(DAY FROM max(booking.BOOKING_DATE)) <= 15
                  THEN
                    date_sub(
                      max(booking.BOOKING_DATE)
                     ,INTERVAL (extract(DAY FROM max(booking.BOOKING_DATE)) - 15) DAY)
                  WHEN extract(DAY FROM max(booking.BOOKING_DATE)) > 15
                  THEN
                    last_day(max(booking.BOOKING_DATE))
                END
              WHEN fdrc_ship.BILLING_CYCLE = 'M'
              THEN
                last_day(max(booking.BOOKING_DATE))
            END
              AS ToDate
           ,count(consignment.CONSG_NO) AS NoOfPickups
          --  consignment.BILLING_CONSIGNMENT_ID
          --  ,consignment.CONSG_NO
          --  ,consignment.SUMMARY
          --  ,sbso.*
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.FINAL_SLAB_RATE IS NULL
                     ,0
                     ,booking_rates.FINAL_SLAB_RATE) +
                    if(
                      rto_rates.FINAL_SLAB_RATE IS NULL
                     ,0
                     ,rto_rates.FINAL_SLAB_RATE)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,booking_rates.FINAL_SLAB_RATE) +
                        if(
                          rto_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,rto_rates.FINAL_SLAB_RATE)
                      ELSE
                        if(
                          booking_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,booking_rates.FINAL_SLAB_RATE)
                    END
                END)
             ,2)
              AS Freight
           ,round(
              sum(
                if(
                  booking_rates.FUEL_SURCHARGE IS NULL
                 ,0
                 ,booking_rates.FUEL_SURCHARGE) +
                if(
                  rto_rates.FUEL_SURCHARGE IS NULL
                 ,0
                 ,rto_rates.FUEL_SURCHARGE))
             ,2)
              AS FuelSurcharge
           ,round(
              sum(
                if(
                  booking_rates.RISK_SURCHARGE IS NULL
                 ,0
                 ,booking_rates.RISK_SURCHARGE) +
                if(
                  rto_rates.RISK_SURCHARGE IS NULL
                 ,0
                 ,rto_rates.RISK_SURCHARGE))
             ,2)
              AS RiskSurcharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.PARCEL_HANDLING_CHARGE) +
                    if(
                      rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.PARCEL_HANDLING_CHARGE)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.PARCEL_HANDLING_CHARGE)
                      ELSE
                        0
                    END
                END)
             ,2)
              AS ParcelHandlingCharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                    if(
                      rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.AIRPORT_HANDLING_CHARGE)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.AIRPORT_HANDLING_CHARGE)
                      ELSE
                        0
                    END
                END)
             ,2)
              AS AirportHandlingCharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                    if(
                      rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.DOCUMENT_HANDLING_CHARGE)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.DOCUMENT_HANDLING_CHARGE)
                      ELSE
                        0
                    END
                END)
             ,2)
              AS DocHandlingCharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                     ,0
                     ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                    if(
                      booking_rates.OCTROI IS NULL
                     ,0
                     ,booking_rates.OCTROI) +
                    if(
                      booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                     ,0
                     ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                    if(
                      booking_rates.TO_PAY_CHARGE IS NULL
                     ,0
                     ,booking_rates.TO_PAY_CHARGE) +
                    if(
                      booking_rates.LC_CHARGE IS NULL
                     ,0
                     ,booking_rates.LC_CHARGE) +
                    if(
                      booking_rates.COD_CHARGES IS NULL
                     ,0
                     ,booking_rates.COD_CHARGES) +
                    if(
                      rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                     ,0
                     ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                    if(
                      rto_rates.OCTROI IS NULL
                     ,0
                     ,rto_rates.OCTROI) +
                    if(
                      rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                     ,0
                     ,rto_rates.SERVICE_CHARGE_ON_OCTROI) -
                    if(
                      rto_rates.TO_PAY_CHARGE IS NULL
                     ,0
                     ,rto_rates.TO_PAY_CHARGE) -
                    if(
                      rto_rates.LC_CHARGE IS NULL
                     ,0
                     ,rto_rates.LC_CHARGE) -
                    if(
                      rto_rates.COD_CHARGES IS NULL
                     ,0
                     ,rto_rates.COD_CHARGES)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          booking_rates.OCTROI IS NULL
                         ,0
                         ,booking_rates.OCTROI) +
                        if(
                          booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                        if(
                          booking_rates.TO_PAY_CHARGE IS NULL
                         ,0
                         ,booking_rates.TO_PAY_CHARGE) +
                        if(
                          rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          rto_rates.OCTROI IS NULL
                         ,0
                         ,rto_rates.OCTROI) +
                        if(
                          rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,rto_rates.SERVICE_CHARGE_ON_OCTROI) -
                        if(
                          rto_rates.TO_PAY_CHARGE IS NULL
                         ,0
                         ,rto_rates.TO_PAY_CHARGE)
                      ELSE
                        +if(
                           booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                          ,0
                          ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          booking_rates.OCTROI IS NULL
                         ,0
                         ,booking_rates.OCTROI) +
                        if(
                          booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                        if(
                          rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          rto_rates.OCTROI IS NULL
                         ,0
                         ,rto_rates.OCTROI) +
                        if(
                          rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,rto_rates.SERVICE_CHARGE_ON_OCTROI)
                    END
                END)
             ,2)
              AS OtherCharges
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
                  THEN
                    if(
                      booking_rates.FINAL_SLAB_RATE IS NULL
                     ,0
                     ,booking_rates.FINAL_SLAB_RATE) +
                    if(
                      booking_rates.RISK_SURCHARGE IS NULL
                     ,0
                     ,booking_rates.RISK_SURCHARGE) +
                    if(
                      booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                    if(
                      booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.PARCEL_HANDLING_CHARGE) +
                    if(
                      booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                     ,0
                     ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                    if(
                      booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                     ,0
                     ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                    if(
                      booking_rates.OCTROI IS NULL
                     ,0
                     ,booking_rates.OCTROI) +
                    if(
                      booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                     ,0
                     ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                    if(
                      booking_rates.TO_PAY_CHARGE IS NULL
                     ,0
                     ,booking_rates.TO_PAY_CHARGE) +
                    if(
                      booking_rates.LC_CHARGE IS NULL
                     ,0
                     ,booking_rates.LC_CHARGE) +
                    if(
                      booking_rates.COD_CHARGES IS NULL
                     ,0
                     ,booking_rates.COD_CHARGES) +
                    if(
                      rto_rates.FINAL_SLAB_RATE IS NULL
                     ,0
                     ,rto_rates.FINAL_SLAB_RATE) +
                    if(
                      rto_rates.RISK_SURCHARGE IS NULL
                     ,0
                     ,rto_rates.RISK_SURCHARGE) +
                    if(
                      rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.DOCUMENT_HANDLING_CHARGE) +
                    if(
                      rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.PARCEL_HANDLING_CHARGE) +
                    if(
                      rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                     ,0
                     ,rto_rates.AIRPORT_HANDLING_CHARGE) +
                    if(
                      rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                     ,0
                     ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                    if(
                      rto_rates.OCTROI IS NULL
                     ,0
                     ,rto_rates.OCTROI) +
                    if(
                      rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                     ,0
                     ,rto_rates.SERVICE_CHARGE_ON_OCTROI) -
                    if(
                      rto_rates.TO_PAY_CHARGE IS NULL
                     ,0
                     ,rto_rates.TO_PAY_CHARGE) -
                    if(
                      rto_rates.LC_CHARGE IS NULL
                     ,0
                     ,rto_rates.LC_CHARGE) -
                    if(
                      rto_rates.COD_CHARGES IS NULL
                     ,0
                     ,rto_rates.COD_CHARGES)
                  ELSE
                    CASE
                      WHEN prod.CONSG_SERIES != 'D' AND
                           prod.CONSG_SERIES != 'L'
                      THEN
                        if(
                          booking_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,booking_rates.FINAL_SLAB_RATE) +
                        if(
                          booking_rates.RISK_SURCHARGE IS NULL
                         ,0
                         ,booking_rates.RISK_SURCHARGE) +
                        if(
                          booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          booking_rates.OCTROI IS NULL
                         ,0
                         ,booking_rates.OCTROI) +
                        if(
                          booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                        if(
                          booking_rates.TO_PAY_CHARGE IS NULL
                         ,0
                         ,booking_rates.TO_PAY_CHARGE) +
                        if(
                          rto_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,rto_rates.FINAL_SLAB_RATE) +
                        if(
                          rto_rates.RISK_SURCHARGE IS NULL
                         ,0
                         ,rto_rates.RISK_SURCHARGE) +
                        if(
                          rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          rto_rates.OCTROI IS NULL
                         ,0
                         ,rto_rates.OCTROI) +
                        if(
                          rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,rto_rates.SERVICE_CHARGE_ON_OCTROI) -
                        if(
                          rto_rates.TO_PAY_CHARGE IS NULL
                         ,0
                         ,rto_rates.TO_PAY_CHARGE)
                      ELSE
                        if(
                          booking_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,booking_rates.FINAL_SLAB_RATE) +
                        if(
                          booking_rates.RISK_SURCHARGE IS NULL
                         ,0
                         ,booking_rates.RISK_SURCHARGE) +
                        if(
                          booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          booking_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          booking_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,booking_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,booking_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          booking_rates.OCTROI IS NULL
                         ,0
                         ,booking_rates.OCTROI) +
                        if(
                          booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,booking_rates.SERVICE_CHARGE_ON_OCTROI) +
                        if(
                          booking_rates.LC_CHARGE IS NULL
                         ,0
                         ,booking_rates.LC_CHARGE) +
                        if(
                          booking_rates.COD_CHARGES IS NULL
                         ,0
                         ,booking_rates.COD_CHARGES) +
                        if(
                          rto_rates.FINAL_SLAB_RATE IS NULL
                         ,0
                         ,rto_rates.FINAL_SLAB_RATE) +
                        if(
                          rto_rates.RISK_SURCHARGE IS NULL
                         ,0
                         ,rto_rates.RISK_SURCHARGE) +
                        if(
                          rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.DOCUMENT_HANDLING_CHARGE) +
                        if(
                          rto_rates.PARCEL_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.PARCEL_HANDLING_CHARGE) +
                        if(
                          rto_rates.AIRPORT_HANDLING_CHARGE IS NULL
                         ,0
                         ,rto_rates.AIRPORT_HANDLING_CHARGE) +
                        if(
                          rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL
                         ,0
                         ,rto_rates.OTHER_OR_SPECIAL_CHARGES) +
                        if(
                          rto_rates.OCTROI IS NULL
                         ,0
                         ,rto_rates.OCTROI) +
                        if(
                          rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL
                         ,0
                         ,rto_rates.SERVICE_CHARGE_ON_OCTROI) -
                        if(
                          rto_rates.LC_CHARGE IS NULL
                         ,0
                         ,rto_rates.LC_CHARGE) -
                        if(
                          rto_rates.COD_CHARGES IS NULL
                         ,0
                         ,rto_rates.COD_CHARGES)
                    END
                END)
             ,2)
              AS TotalCharges
           ,round(
              sum(
                if(
                  booking_rates.SERVICE_TAX IS NULL
                 ,0
                 ,booking_rates.SERVICE_TAX) +
                if(
                  booking_rates.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,booking_rates.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE) +
                if(
                  rto_rates.SERVICE_TAX IS NULL
                 ,0
                 ,rto_rates.SERVICE_TAX) +
                if(
                  rto_rates.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,rto_rates.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE))
             ,2)
              AS ServiceTax
           ,round(
              sum(
                if(
                  booking_rates.EDUCATION_CESS IS NULL
                 ,0
                 ,booking_rates.EDUCATION_CESS) +
                if(
                  booking_rates.EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,booking_rates.EDU_CESS_ON_OCTROI_SERVICE_CHARGE) +
                if(
                  rto_rates.EDUCATION_CESS IS NULL
                 ,0
                 ,rto_rates.EDUCATION_CESS) +
                if(
                  rto_rates.EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,rto_rates.EDU_CESS_ON_OCTROI_SERVICE_CHARGE))
             ,2)
              AS EducationCess
           ,round(
              sum(
                if(
                  booking_rates.HIGHER_EDUCATION_CES IS NULL
                 ,0
                 ,booking_rates.HIGHER_EDUCATION_CES) +
                if(
                  booking_rates.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,booking_rates.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE) +
                if(
                  rto_rates.HIGHER_EDUCATION_CES IS NULL
                 ,0
                 ,rto_rates.HIGHER_EDUCATION_CES) +
                if(
                  rto_rates.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE IS NULL
                 ,0
                 ,rto_rates.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE))
             ,2)
              AS HigherEducationCess
           ,round(
              sum(
                if(
                  booking_rates.STATE_TAX IS NULL
                 ,0
                 ,booking_rates.STATE_TAX) +
                if(
                  rto_rates.STATE_TAX IS NULL
                 ,0
                 ,rto_rates.STATE_TAX))
             ,2)
              AS StateTax
           ,round(
              sum(
                if(
                  booking_rates.SURCHARGE_ON_STATE_TAX IS NULL
                 ,0
                 ,booking_rates.SURCHARGE_ON_STATE_TAX) +
                if(
                  booking_rates.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE
                    IS NULL
                 ,0
                 ,booking_rates.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE) +
                if(
                  rto_rates.SURCHARGE_ON_STATE_TAX IS NULL
                 ,0
                 ,rto_rates.SURCHARGE_ON_STATE_TAX) +
                if(
                  rto_rates.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE
                    IS NULL
                 ,0
                 ,rto_rates.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE))
             ,2)
              AS SurchargeOnStateTax
           ,round(
              sum(
                if(
                  booking_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL
                 ,0
                 ,booking_rates.GRAND_TOTAL_INCLUDING_TAX) +
                if(
                  rto_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL
                 ,0
                 ,rto_rates.GRAND_TOTAL_INCLUDING_TAX))
             ,2)
              AS GrandTotal
           ,round(
              cast(
                sum(
                  if(
                    booking_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL
                   ,0
                   ,booking_rates.GRAND_TOTAL_INCLUDING_TAX) +
                  if(
                    rto_rates.GRAND_TOTAL_INCLUDING_TAX IS NULL
                   ,0
                   ,rto_rates.GRAND_TOTAL_INCLUDING_TAX)) AS decimal(20, 6)))
              AS GrandTotalRounded
           ,'Y' AS BillGenerated
           ,4 AS CREATED_BY
           ,curdate() AS CREATED_DATE
           ,NULL AS UPDATE_BY
           ,NULL AS UPDATE_DATE
           ,'O' AS BillStatus
           ,customer.CUSTOMER_ID
           ,0
           ,getFuelSurchargePercentage(
              fdrcc.CUSTOMER_CATEGORY_CODE
             ,custtype.CUSTOMER_TYPE_CODE
             ,max(booking.BOOKING_DATE)
             ,customer.CUSTOMER_ID
             ,fdr_booking.REGION_ID
             ,fdc_booking.CITY_ID
             ,CASE prod.CONSG_SERIES WHEN 'P' THEN 'Y' ELSE 'N' END)
              AS FuelSurchargePercentage
           ,fuel_surcharge_percentage_formula as FuelSurchargePercentageFormula
           ,now() AS BILL_GENERATION_DATE
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
                       prod.CONSG_SERIES = 'D'
                  THEN
                    (if(
                       booking_rates.LC_AMOUNT IS NULL
                      ,0
                      ,booking_rates.LC_AMOUNT) -
                     if(
                       rto_rates.LC_AMOUNT IS NULL
                      ,0
                      ,rto_rates.LC_AMOUNT))
                  ELSE
                    0
                END)
             ,2)
              AS LCAmount
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
                       prod.CONSG_SERIES = 'D'
                  THEN
                    (if(
                       booking_rates.LC_CHARGE IS NULL
                      ,0
                      ,booking_rates.LC_CHARGE) -
                     if(
                       rto_rates.LC_CHARGE IS NULL
                      ,0
                      ,rto_rates.LC_CHARGE))
                  ELSE
                    0
                END)
             ,2)
              AS LCCharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
                       prod.CONSG_SERIES = 'L'
                  THEN
                    (if(
                       booking_rates.COD_AMOUNT IS NULL
                      ,0
                      ,booking_rates.COD_AMOUNT) -
                     if(
                       rto_rates.COD_AMOUNT IS NULL
                      ,0
                      ,rto_rates.COD_AMOUNT))
                  ELSE
                    0
                END)
             ,2)
              AS CODAmount
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
                       prod.CONSG_SERIES = 'L'
                  THEN
                    (if(
                       booking_rates.COD_CHARGES IS NULL
                      ,0
                      ,booking_rates.COD_CHARGES) -
                     if(
                       rto_rates.COD_CHARGES IS NULL
                      ,0
                      ,rto_rates.COD_CHARGES))
                  ELSE
                    0
                END)
             ,2)
              AS CODCharge
           ,round(
              sum(
                CASE
                  WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
                       (prod.CONSG_SERIES = 'D' OR
                        prod.CONSG_SERIES = 'L')
                  THEN
                    if(
                      rto_rates.FINAL_SLAB_RATE IS NULL
                     ,0
                     ,rto_rates.FINAL_SLAB_RATE)
                  ELSE
                    0
                END)
             ,2)
              AS RTOCharge
           ,CASE
              WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
              THEN
                'RL'
              ELSE
                CASE
                  WHEN prod.CONSG_SERIES != 'D' AND
                       prod.CONSG_SERIES != 'L'
                  THEN
                    'N'
                  WHEN prod.CONSG_SERIES = 'D'
                  THEN
                    'LC'
                  WHEN prod.CONSG_SERIES = 'L'
                  THEN
                    'COD'
                END
            END
              AS BillType
          FROM
            ff_f_billing_consignment consignment
            JOIN ff_f_billing_consignment_rate booking_rates
              ON (booking_rates.BILLING_CONSIGNMENT = consignment.BILLING_CONSIGNMENT_ID AND
                  booking_rates.RATE_CALCULATED_FOR = 'B' AND booking_rates.GRAND_TOTAL_INCLUDING_TAX <> 0)
            LEFT JOIN ff_f_billing_consignment_rate rto_rates
              ON (rto_rates.BILLING_CONSIGNMENT = consignment.BILLING_CONSIGNMENT_ID AND
                  rto_rates.RATE_CALCULATED_FOR = 'R')
            JOIN ff_f_consignment_booking_billing_mapping booking ON consignment.CONSG_ID = booking.CONSG_ID
            JOIN ff_d_office fdo_booking ON fdo_booking.OFFICE_ID = booking.BOOKING_OFF
            JOIN ff_d_city fdc_booking ON fdc_booking.CITY_ID = fdo_booking.CITY_ID
            JOIN ff_d_region fdr_booking
              ON fdr_booking.REGION_ID = fdo_booking.MAPPED_TO_REGION
            LEFT JOIN ff_d_customer customer ON customer.CUSTOMER_ID = booking.CUSTOMER
            LEFT JOIN ff_d_rate_customer_category fdrcc
              ON fdrcc.RATE_CUSTOMER_CATEGORY_ID = customer.CUSTOMER_CATEGORY
            LEFT JOIN ff_d_customer_type custtype
              ON custtype.CUSTOMER_TYPE_ID = customer.CUSTOMER_TYPE
            JOIN ff_d_product prod ON consignment.PRODUCT = prod.PRODUCT_ID
            JOIN ff_d_financial_product_series_customer_type_map fdfpsctm
              ON (fdfpsctm.PRODUCT = prod.PRODUCT_ID AND
                  fdfpsctm.CUSTOMER_TYPE = custtype.CUSTOMER_TYPE_ID)
            JOIN ff_d_financial_product fdfp
              ON fdfp.FINANCIAL_PRODUCT_ID = fdfpsctm.FINANCIAL_PRODUCT
            JOIN ff_f_billing_consignment_summary summary
              ON summary.SUMMARY_ID = consignment.SUMMARY
            JOIN ff_f_sap_bill_sales_order sbso ON summary.SUMMARY_ID = sbso.SUMMARY_ID
            LEFT JOIN (SELECT
                         DISTINCT (SHIPPED_TO_CODE)
                                 ,LOCATION_OPERATION_TYPE
                                 ,PICKUP_DELIVERY_LOCATION
                                 ,RATE_CONTRACT
                                 ,STATUS
                       FROM
                         ff_d_contract_payment_billing_location) AS cpbl_ship
              ON (cpbl_ship.SHIPPED_TO_CODE = summary.SHIP_TO_CODE AND
                  cpbl_ship.LOCATION_OPERATION_TYPE IN ('B'
                                                       ,'BP') AND
                  cpbl_ship.STATUS = 'A')
            LEFT JOIN ff_d_rate_contract fdrc_ship
              ON fdrc_ship.RATE_CONTRACT_ID = cpbl_ship.RATE_CONTRACT
            LEFT JOIN ff_d_pickup_delivery_location pdl_ship
              ON pdl_ship.LOCATION_ID = cpbl_ship.PICKUP_DELIVERY_LOCATION
            LEFT JOIN ff_d_pickup_delivery_contract pdc_ship
              ON pdc_ship.CONTRACT_ID = pdl_ship.CONTRACT_ID
          WHERE
            sbso.DT_SAP_INBOUND = 'N'
          GROUP BY
            sbso.BILL_NUMBER
           ,summary.SHIP_TO_CODE
           ,fdfp.FINANCIAL_PRODUCT_ID
           ,billingOffice
           ,customer.CUSTOMER_ID;
    
    SELECT
      row_count()
    INTO
      rowcount;
   -- select rowcount;
    
    IF rowcount <= 0 THEN
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=213;
      rollback;
    end if;
   
   INSERT INTO
    ff_aud_procedure_execution_stepwise_time(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_sap_bill_sales_order'
     ,'STEP 1 - Insert Data Into Bill'
     ,CURRENT_TIMESTAMP()
     ,insert_data_into_bill_start_time
     ,CURRENT_TIMESTAMP()
     ,null); 
    
    SELECT
      CURRENT_TIMESTAMP()
    INTO
      update_ff_f_billing_consignment_summary_start_time;
    update
      ff_f_billing_consignment_summary ffbcs
      join ff_f_sap_bill_sales_order ffsbso on ffsbso.SUMMARY_ID = ffbcs.SUMMARY_ID
      JOIN ff_f_bill ffb on ffb.INVOICE_NUMBER = ffsbso.BILL_NUMBER
    SET
      ffbcs.INVOICE     = ffb.INVOICE_ID,
      ffbcs.SALES_ORDER = ffsbso.SALES_ORDER_NUMBER
    WHERE
      ffsbso.DT_SAP_INBOUND = 'N' and
      ffb.VERSION = 0;
    
    SELECT
      row_count()
    INTO
      rowcount;
    -- select rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=214;
      rollback;
    end if;
    
   INSERT INTO
    ff_aud_procedure_execution_stepwise_time(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_sap_bill_sales_order'
     ,'STEP 2 - Update Data- ff_f_billing_consignment_summary'
     ,CURRENT_TIMESTAMP()
     ,update_ff_f_billing_consignment_summary_start_time
     ,CURRENT_TIMESTAMP()
     ,null); 
    
    SELECT
      CURRENT_TIMESTAMP()
    INTO
      update_ff_f_billing_consignment_start_time;
    
    update
      ff_f_billing_consignment ffbc
      join ff_f_sap_bill_sales_order ffsbso on ffsbso.SUMMARY_ID = ffbc.SUMMARY
      JOIN ff_f_bill ffb on ffb.INVOICE_NUMBER = ffsbso.BILL_NUMBER
    set
      ffbc.invoice = ffb.INVOICE_ID
    where
      ffsbso.DT_SAP_INBOUND = 'N' and
      ffb.VERSION = 0;
    
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=215;
      rollback;
    end if;
    
  INSERT INTO
    ff_aud_procedure_execution_stepwise_time(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_sap_bill_sales_order'
     ,'STEP 3 - Update Data - ff_f_billing_consignment'
     ,CURRENT_TIMESTAMP()
     ,update_ff_f_billing_consignment_start_time
     ,CURRENT_TIMESTAMP()
     ,null);
    
    SELECT
      CURRENT_TIMESTAMP()
    INTO
      update_ff_f_sap_bill_sales_order_start_time;
      
    UPDATE
      ff_f_sap_bill_sales_order ffsbso
      JOIN ff_f_bill ffb on ffb.INVOICE_NUMBER = ffsbso.BILL_NUMBER
    SET
      ffsbso.DT_SAP_INBOUND = 'C',
      ffsbso.SAP_TIMESTAMP  = curdate()
    WHERE
      ffsbso.DT_SAP_INBOUND = 'N' and
      ffb.VERSION = 0;
    
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=216;
      rollback;
    end if;

    
  INSERT INTO
    ff_aud_procedure_execution_stepwise_time(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_sap_bill_sales_order'
     ,'STEP 4 - Update Data - ff_f_sap_bill_sales_order'
     ,CURRENT_TIMESTAMP()
     ,update_ff_f_sap_bill_sales_order_start_time
     ,CURRENT_TIMESTAMP()
     ,null);
          
    
    
    update
      ff_f_bill ffb
    set
      VERSION = 1
    where
      VERSION = 0;
    
    SELECT
      row_count()
    INTO
      rowcount;
    
    if rowcount <= 0 then
      SIGNAL no_data_found
        SET MESSAGE_TEXT='Problem in execution of sap bill sales order procedure, rolling back', MYSQL_ERRNO=217;
      rollback;
    else
      SELECT 'Executed Successfully sap bill sales order procedure, committing';
      commit;
    end if;
    
    
END;
