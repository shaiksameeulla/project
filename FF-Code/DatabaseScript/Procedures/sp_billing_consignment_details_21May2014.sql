DROP PROCEDURE IF EXISTS sp_billing_consignment_details;
CREATE PROCEDURE `sp_billing_consignment_details`(invoice_number int(11))
BEGIN
-- Procedure Name - sp_billing_consignment_details
-- Created By  - Kaustubh
-- Modified By - Kaustubh
-- Modified Date - 21 MAY 2014
  SELECT
    consignments.*,
    concat(
      (@rownum := @rownum + 1),
      '')
      AS rowNumber,
    concat(
      (CASE
         WHEN (@rownum % 40) = 0 THEN @pagenum := @pagenum + 1
         ELSE @pagenum
       END),
      '')
      AS pageNumber
  FROM
    (SELECT
       bc.BILLING_CONSIGNMENT_ID AS billingConsignmentId,
       bc.CONSG_ID AS consgId,
       bc.CONSG_NO AS consgNo,
       po.OFFICE_NAME AS salesOfficeName,
       c.BUSINESS_NAME AS businessName,
       b.INVOICE_NUMBER AS invoiceNumber,
       date_format(
         bk.BOOKING_DATE,
         '%e-%b')
         AS bookingDate,
       bc.CONSG_NO AS consignmentNumber,
       CASE ct.CONSIGNMENT_CODE WHEN 'DOX' THEN 'D' WHEN 'PPX' THEN 'P' END
         AS consignmentType,
       origincity.CITY_NAME AS originCityName,
       originoffice.OFFICE_NAME AS originOfficeName,
       bk.CUST_REF_NO AS customerReferenceNumber,
       destcity.CITY_NAME AS destinationCityName,
       bc.FINAL_WEIGHT AS finalWeight,
       bc.CHANGED_AFTER_BILLING_WEIGHT_DEST AS weightModified,
       bc.CHANGED_AFTER_NEW_RATE_COMPONENT AS newRateAdded,
       /* LC Amount */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
              prod.CONSG_SERIES = 'D'
         THEN
           (if(
              booking_rates.LC_AMOUNT IS NULL,
              0,
              booking_rates.LC_AMOUNT) -
            if(
              rto_rates.LC_AMOUNT IS NULL,
              0,
              rto_rates.LC_AMOUNT))
         ELSE
           0
       END
         AS lcAmount,
       /* COD Amount */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
              prod.CONSG_SERIES = 'L'
         THEN
           (if(
              booking_rates.COD_AMOUNT IS NULL,
              0,
              booking_rates.COD_AMOUNT) -
            if(
              rto_rates.COD_AMOUNT IS NULL,
              0,
              rto_rates.COD_AMOUNT))
         ELSE
           0
       END
         AS codAmount,
       /* Freight Charges */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           (if(
              booking_rates.FINAL_SLAB_RATE IS NULL,
              0,
              booking_rates.FINAL_SLAB_RATE) +
            if(
              rto_rates.FINAL_SLAB_RATE IS NULL,
              0,
              rto_rates.FINAL_SLAB_RATE))
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               (if(
                  booking_rates.FINAL_SLAB_RATE IS NULL,
                  0,
                  booking_rates.FINAL_SLAB_RATE) +
                if(
                  rto_rates.FINAL_SLAB_RATE IS NULL,
                  0,
                  rto_rates.FINAL_SLAB_RATE))
             ELSE
               if(
                 booking_rates.FINAL_SLAB_RATE IS NULL,
                 0,
                 booking_rates.FINAL_SLAB_RATE)
           END
       END
         AS finalSlabRate,
       /* LC Charge */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
              prod.CONSG_SERIES = 'D'
         THEN
           (if(
              booking_rates.LC_CHARGE IS NULL,
              0,
              booking_rates.LC_CHARGE) -
            if(
              rto_rates.LC_CHARGE IS NULL,
              0,
              rto_rates.LC_CHARGE))
         ELSE
           0
       END
         AS lcCharge,
       /* COD Charges */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
              prod.CONSG_SERIES = 'L'
         THEN
           (if(
              booking_rates.COD_CHARGES IS NULL,
              0,
              booking_rates.COD_CHARGES) -
            if(
              rto_rates.COD_CHARGES IS NULL,
              0,
              rto_rates.COD_CHARGES))
         ELSE
           0
       END
         AS codCharge,
       /* RTO Charges */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE != 'RL' AND
              (prod.CONSG_SERIES = 'D' OR
               prod.CONSG_SERIES = 'L')
         THEN
           if(
             rto_rates.FINAL_SLAB_RATE IS NULL,
             0,
             rto_rates.FINAL_SLAB_RATE)
         ELSE
           0
       END
         AS rtoCharge,
       /* Risk Surharge */
       (if(
          booking_rates.RISK_SURCHARGE IS NULL,
          0,
          booking_rates.RISK_SURCHARGE) +
        if(
          rto_rates.RISK_SURCHARGE IS NULL,
          0,
          rto_rates.RISK_SURCHARGE))
         AS riskSurCharge,
       /* Document Handling Charge */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           (if(
              booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
              0,
              booking_rates.DOCUMENT_HANDLING_CHARGE) +
            if(
              rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
              0,
              rto_rates.DOCUMENT_HANDLING_CHARGE))
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               (if(
                  booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                  0,
                  booking_rates.DOCUMENT_HANDLING_CHARGE) +
                if(
                  rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                  0,
                  rto_rates.DOCUMENT_HANDLING_CHARGE))
             ELSE
               0
           END
       END
         AS documentHandlingCharge,
       /* Parcel Handling Charge */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           (if(
              booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
              0,
              booking_rates.PARCEL_HANDLING_CHARGE) +
            if(
              rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
              0,
              rto_rates.PARCEL_HANDLING_CHARGE))
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               (if(
                  booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
                  0,
                  booking_rates.PARCEL_HANDLING_CHARGE) +
                if(
                  rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
                  0,
                  rto_rates.PARCEL_HANDLING_CHARGE))
             ELSE
               0
           END
       END
         AS parcelHandlingCharge,
       /* Airport Handling Charge */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           (if(
              booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
              0,
              booking_rates.AIRPORT_HANDLING_CHARGE) +
            if(
              rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
              0,
              rto_rates.AIRPORT_HANDLING_CHARGE))
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               (if(
                  booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                  0,
                  booking_rates.AIRPORT_HANDLING_CHARGE) +
                if(
                  rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                  0,
                  rto_rates.AIRPORT_HANDLING_CHARGE))
             ELSE
               0
           END
       END
         AS airportHandlingCharge,
       /* Other Charges */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           /* Reverse Logistics Customer */
           +if(
              booking_rates.TO_PAY_CHARGE IS NULL,
              0,
              booking_rates.TO_PAY_CHARGE) -
           if(
             rto_rates.TO_PAY_CHARGE IS NULL,
             0,
             rto_rates.TO_PAY_CHARGE) +
           if(
             booking_rates.COD_CHARGES IS NULL,
             0,
             booking_rates.COD_CHARGES) -
           if(
             rto_rates.COD_CHARGES IS NULL,
             0,
             rto_rates.COD_CHARGES) +
           if(
             booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
             0,
             booking_rates.OTHER_OR_SPECIAL_CHARGES) +
           if(
             rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
             0,
             rto_rates.OTHER_OR_SPECIAL_CHARGES) +
           if(
             booking_rates.OCTROI IS NULL,
             0,
             booking_rates.OCTROI) +
           if(
             rto_rates.OCTROI IS NULL,
             0,
             rto_rates.OCTROI) +
           if(
             booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
             0,
             booking_rates.SERVICE_CHARGE_ON_OCTROI) +
           if(
             rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
             0,
             rto_rates.SERVICE_CHARGE_ON_OCTROI) +
           if(
             booking_rates.LC_CHARGE IS NULL,
             0,
             booking_rates.LC_CHARGE) -
           if(
             rto_rates.LC_CHARGE IS NULL,
             0,
             rto_rates.LC_CHARGE)
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               /* Not COD and Not LC */
               +if(
                  booking_rates.TO_PAY_CHARGE IS NULL,
                  0,
                  booking_rates.TO_PAY_CHARGE) -
               if(
                 rto_rates.TO_PAY_CHARGE IS NULL,
                 0,
                 rto_rates.TO_PAY_CHARGE) +
               if(
                 booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 booking_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 rto_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 booking_rates.OCTROI IS NULL,
                 0,
                 booking_rates.OCTROI) +
               if(
                 rto_rates.OCTROI IS NULL,
                 0,
                 rto_rates.OCTROI) +
               if(
                 booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 booking_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 rto_rates.SERVICE_CHARGE_ON_OCTROI)
             ELSE
               /* COD or LC */
               +if(
                  booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
                  0,
                  booking_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.DOCUMENT_HANDLING_CHARGE) +
               if(
                 rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.DOCUMENT_HANDLING_CHARGE) +
               if(
                 booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 booking_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 rto_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 booking_rates.OCTROI IS NULL,
                 0,
                 booking_rates.OCTROI) +
               if(
                 rto_rates.OCTROI IS NULL,
                 0,
                 rto_rates.OCTROI) +
               if(
                 booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 booking_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 rto_rates.SERVICE_CHARGE_ON_OCTROI)
           END
       END
         AS otherCharges,
       /* Total Charges */
       CASE
         WHEN custtype.CUSTOMER_TYPE_CODE = 'RL'
         THEN
           /* Reverse Logistics Customer */
           +if(
              booking_rates.TO_PAY_CHARGE IS NULL,
              0,
              booking_rates.TO_PAY_CHARGE) -
           if(
             rto_rates.TO_PAY_CHARGE IS NULL,
             0,
             rto_rates.TO_PAY_CHARGE) +
           if(
             booking_rates.COD_CHARGES IS NULL,
             0,
             booking_rates.COD_CHARGES) -
           if(
             rto_rates.COD_CHARGES IS NULL,
             0,
             rto_rates.COD_CHARGES) +
           if(
             booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
             0,
             booking_rates.OTHER_OR_SPECIAL_CHARGES) +
           if(
             rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
             0,
             rto_rates.OTHER_OR_SPECIAL_CHARGES) +
           if(
             booking_rates.OCTROI IS NULL,
             0,
             booking_rates.OCTROI) +
           if(
             rto_rates.OCTROI IS NULL,
             0,
             rto_rates.OCTROI) +
           if(
             booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
             0,
             booking_rates.SERVICE_CHARGE_ON_OCTROI) +
           if(
             rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
             0,
             rto_rates.SERVICE_CHARGE_ON_OCTROI) +
           if(
             booking_rates.LC_CHARGE IS NULL,
             0,
             booking_rates.LC_CHARGE) -
           if(
             rto_rates.LC_CHARGE IS NULL,
             0,
             rto_rates.LC_CHARGE) +
           if(
             booking_rates.FINAL_SLAB_RATE IS NULL,
             0,
             booking_rates.FINAL_SLAB_RATE) +
           if(
             rto_rates.FINAL_SLAB_RATE IS NULL,
             0,
             rto_rates.FINAL_SLAB_RATE) +
           if(
             booking_rates.RISK_SURCHARGE IS NULL,
             0,
             booking_rates.RISK_SURCHARGE) +
           if(
             rto_rates.RISK_SURCHARGE IS NULL,
             0,
             rto_rates.RISK_SURCHARGE) +
           if(
             booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
             0,
             booking_rates.PARCEL_HANDLING_CHARGE) +
           if(
             rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
             0,
             rto_rates.PARCEL_HANDLING_CHARGE) +
           if(
             booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
             0,
             booking_rates.AIRPORT_HANDLING_CHARGE) +
           if(
             rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
             0,
             rto_rates.AIRPORT_HANDLING_CHARGE) +
           if(
             booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
             0,
             booking_rates.DOCUMENT_HANDLING_CHARGE) +
           if(
             rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
             0,
             rto_rates.DOCUMENT_HANDLING_CHARGE)
         ELSE
           CASE
             WHEN prod.CONSG_SERIES != 'D' AND
                  prod.CONSG_SERIES != 'L'
             THEN
               /* Not COD and Not LC */
               +if(
                  booking_rates.TO_PAY_CHARGE IS NULL,
                  0,
                  booking_rates.TO_PAY_CHARGE) -
               if(
                 rto_rates.TO_PAY_CHARGE IS NULL,
                 0,
                 rto_rates.TO_PAY_CHARGE) +
               if(
                 booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 booking_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 rto_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 booking_rates.OCTROI IS NULL,
                 0,
                 booking_rates.OCTROI) +
               if(
                 rto_rates.OCTROI IS NULL,
                 0,
                 rto_rates.OCTROI) +
               if(
                 booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 booking_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 rto_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 booking_rates.FINAL_SLAB_RATE IS NULL,
                 0,
                 booking_rates.FINAL_SLAB_RATE) +
               if(
                 rto_rates.FINAL_SLAB_RATE IS NULL,
                 0,
                 rto_rates.FINAL_SLAB_RATE) +
               if(
                 booking_rates.RISK_SURCHARGE IS NULL,
                 0,
                 booking_rates.RISK_SURCHARGE) +
               if(
                 rto_rates.RISK_SURCHARGE IS NULL,
                 0,
                 rto_rates.RISK_SURCHARGE) +
               if(
                 booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.DOCUMENT_HANDLING_CHARGE) +
               if(
                 rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.DOCUMENT_HANDLING_CHARGE)
             ELSE
               /* COD or LC */
               +if(
                  booking_rates.PARCEL_HANDLING_CHARGE IS NULL,
                  0,
                  booking_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 rto_rates.PARCEL_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.PARCEL_HANDLING_CHARGE) +
               if(
                 booking_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 rto_rates.AIRPORT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.AIRPORT_HANDLING_CHARGE) +
               if(
                 booking_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 booking_rates.DOCUMENT_HANDLING_CHARGE) +
               if(
                 rto_rates.DOCUMENT_HANDLING_CHARGE IS NULL,
                 0,
                 rto_rates.DOCUMENT_HANDLING_CHARGE) +
               if(
                 booking_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 booking_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 rto_rates.OTHER_OR_SPECIAL_CHARGES IS NULL,
                 0,
                 rto_rates.OTHER_OR_SPECIAL_CHARGES) +
               if(
                 booking_rates.OCTROI IS NULL,
                 0,
                 booking_rates.OCTROI) +
               if(
                 rto_rates.OCTROI IS NULL,
                 0,
                 rto_rates.OCTROI) +
               if(
                 booking_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 booking_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 rto_rates.SERVICE_CHARGE_ON_OCTROI IS NULL,
                 0,
                 rto_rates.SERVICE_CHARGE_ON_OCTROI) +
               if(
                 booking_rates.FINAL_SLAB_RATE IS NULL,
                 0,
                 booking_rates.FINAL_SLAB_RATE) +
               if(
                 rto_rates.FINAL_SLAB_RATE IS NULL,
                 0,
                 rto_rates.FINAL_SLAB_RATE) +
               if(
                 booking_rates.RISK_SURCHARGE IS NULL,
                 0,
                 booking_rates.RISK_SURCHARGE) +
               if(
                 rto_rates.RISK_SURCHARGE IS NULL,
                 0,
                 rto_rates.RISK_SURCHARGE) +
               if(
                 booking_rates.COD_CHARGES IS NULL,
                 0,
                 booking_rates.COD_CHARGES) -
               if(
                 rto_rates.COD_CHARGES IS NULL,
                 0,
                 rto_rates.COD_CHARGES) +
               if(
                 booking_rates.LC_CHARGE IS NULL,
                 0,
                 booking_rates.LC_CHARGE) -
               if(
                 rto_rates.LC_CHARGE IS NULL,
                 0,
                 rto_rates.LC_CHARGE)
           END
       END
         AS totalCharges,
       vendor.VENDOR_CODE AS vendorCode,
       date_format(
         consg.DLV_DATE_TIME,
         '%e-%b')
         AS deliveryDate,
       bc.DELTA_TRANSFER AS deltaTransfer
     FROM
       /* get consignment details*/
       ff_f_billing_consignment bc
       JOIN ff_d_consignment_type ct ON ct.CONSIGNMENT_TYPE_ID = bc.CONSG_TYPE
       JOIN ff_f_consignment consg ON consg.CONSG_ID = bc.CONSG_ID
       /* get bill*/
       JOIN ff_f_bill b ON b.INVOICE_ID = bc.INVOICE
       /* get customer*/
       JOIN ff_f_booking bk ON bk.CONSG_NUMBER = bc.CONSG_NO
       LEFT JOIN ff_d_customer c ON c.CUSTOMER_ID = bk.CUSTOMER
       LEFT JOIN ff_d_customer_type custtype
         ON custtype.customer_type_id = c.CUSTOMER_TYPE
       /* get contract*/
       LEFT JOIN ff_d_pickup_delivery_contract pdc
         ON (bc.ORIGIN_OFFICE = pdc.OFFICE_ID AND
             c.CUSTOMER_ID = pdc.CONTRACT_ID)
       LEFT JOIN ff_d_pickup_delivery_location pdl
         ON pdc.CONTRACT_ID = pdl.CONTRACT_ID
       LEFT JOIN ff_d_contract_payment_billing_location cpbl
         ON cpbl.PICKUP_DELIVERY_LOCATION = pdl.LOCATION_ID
       LEFT JOIN ff_d_rate_contract rc
         ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
       /* get consignment booking and rto rates*/
       JOIN ff_f_billing_consignment_rate booking_rates
         ON (booking_rates.BILLING_CONSIGNMENT = bc.BILLING_CONSIGNMENT_ID AND
             booking_rates.RATE_CALCULATED_FOR = 'B')
       LEFT JOIN ff_f_billing_consignment_rate rto_rates
         ON (rto_rates.BILLING_CONSIGNMENT = bc.BILLING_CONSIGNMENT_ID AND
             rto_rates.RATE_CALCULATED_FOR = 'R')
       /* get other details*/
       JOIN ff_d_pincode destpin ON destpin.PINCODE_ID = bk.DEST_PINCODE
       JOIN ff_d_city destcity ON destcity.CITY_ID = destpin.CITY_ID
       JOIN ff_d_office po ON po.OFFICE_ID = b.BILLING_OFFICE_ID
       JOIN ff_d_office originoffice ON originoffice.office_id = bc.ORIGIN_OFFICE
       JOIN ff_d_city origincity ON origincity.CITY_ID = originoffice.CITY_ID
       JOIN ff_d_product prod ON prod.PRODUCT_ID = consg.PRODUCT
       LEFT JOIN ff_d_vendor vendor ON vendor.vendor_id = bk.CUST_VENDOR
     WHERE
       bc.INVOICE = invoice_number
     GROUP BY
       bc.BILLING_CONSIGNMENT_ID
     ORDER BY
       po.OFFICE_NAME,
       bc.CONSG_NO) consignments
    JOIN (SELECT
            @rownum := 0,
            @pagenum := 0) AS numbers;
END;
