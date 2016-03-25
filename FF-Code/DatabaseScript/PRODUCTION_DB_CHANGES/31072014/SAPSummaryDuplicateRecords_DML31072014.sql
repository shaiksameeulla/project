-- Query to delete duplicate records
SET SQL_SAFE_UPDATES = 0;
DELETE FROM ff_f_sap_billing_consignment_summary 
WHERE
    ID NOT IN (select id from (SELECT 
        min(ID) as id
    FROM
        ff_f_sap_billing_consignment_summary
    GROUP BY ff_f_sap_billing_consignment_summary.TRANSACTION_NUMBER) as a)
    AND DT_SAP_OUTBOUND != 'C';
SET SQL_SAFE_UPDATES = 1;