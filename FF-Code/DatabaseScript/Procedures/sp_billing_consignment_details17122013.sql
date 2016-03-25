DROP PROCEDURE IF EXISTS sp_billing_consignment_details;
CREATE PROCEDURE sp_billing_consignment_details(invoice_number int(11))
BEGIN
      SELECT bk.BOOKING_DATE,
             bc.CONSG_NO,
             ct.CONSIGNMENT_CODE,
             city.CITY_NAME,
             bc.FINAL_WEIGHT,
             max(if(bcr.RATE_COMPONENT = 1, bcr.CALCULATED_VALUE, 0))
                AS FreightCharges,
             max(if(bcr.RATE_COMPONENT = 3, bcr.CALCULATED_VALUE, 0))
                AS RishSurCharge,
             max(if(bcr.RATE_COMPONENT = 8, bcr.CALCULATED_VALUE, 0))
                AS DocHandlingCharge,
             max(if(bcr.RATE_COMPONENT = 6, bcr.CALCULATED_VALUE, 0))
                AS ParcelHandlingCharge,
             max(if(bcr.RATE_COMPONENT = 7, bcr.CALCULATED_VALUE, 0))
                AS AirportHandlingCharge,
               max(if(bcr.RATE_COMPONENT = 10, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 17, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 18, bcr.CALCULATED_VALUE, 0))
                AS OtherCharges,
               max(if(bcr.RATE_COMPONENT = 1, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 3, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 8, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 6, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 7, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 10, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 17, bcr.CALCULATED_VALUE, 0))
             + max(if(bcr.RATE_COMPONENT = 18, bcr.CALCULATED_VALUE, 0))
                AS Total,
             po.OFFICE_NAME AS SalesOffice,
             c.BUSINESS_NAME AS Customer,
             b.INVOICE_NUMBER AS InvoiceNumber
        FROM ff_f_billing_consignment bc
             JOIN ff_f_billing_consignment_rate bcr
                ON bcr.BILLING_CONSIGNMENT = bc.BILLING_CONSIGNMENT_ID
             JOIN ff_f_booking bk
                ON bk.CONSG_NUMBER = bc.CONSG_NO
             JOIN ff_d_consignment_type ct
                ON ct.CONSIGNMENT_TYPE_ID = bc.CONSG_TYPE
             JOIN ff_d_pincode pin
                ON pin.PINCODE_ID = bk.DEST_PINCODE
             JOIN ff_d_city city
                ON city.CITY_ID = pin.CITY_ID
             JOIN ff_f_bill b
                ON b.INVOICE_ID = bc.INVOICE
             JOIN ff_d_office po
                ON po.OFFICE_ID = b.PICKUP_OFFICE_ID
             JOIN ff_d_contract_payment_billing_location cpbl
                ON b.CONTRACT_PAYMENT_BILLING_LOCATION =
                      cpbl.CONTRACT_PAYMENT_BILLING_LOCATION_ID
             JOIN ff_d_rate_contract rc
                ON rc.RATE_CONTRACT_ID = cpbl.RATE_CONTRACT
             JOIN ff_d_customer c
                ON c.CUSTOMER_ID = rc.CUSTOMER
       WHERE bc.INVOICE = invoice_number
      GROUP BY bc.BILLING_CONSIGNMENT_ID;
   END;
