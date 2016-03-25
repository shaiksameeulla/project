DROP PROCEDURE IF EXISTS sp_insert_fact_data;
CREATE PROCEDURE `sp_insert_fact_data`()
BEGIN

DELETE FROM ff_fct_book_to_bill where IFNULL(BOOKING_DATE,str_to_date('01/01/1950','%d/%m/%Y')) > DATE_SUB(CURDATE(),INTERVAL 4 MONTH);
commit;
INSERT INTO ff_fct_book_to_bill
SELECT bo.BOOKING_ID,
       bo.CONSG_NUMBER,
       bo.BOOKING_DATE,
       bo.BOOKING_TYPE,
       bo.BOOKING_OFF,
       bo.CUSTOMER as CUSTOMER_BO,
       bo.NO_OF_PIECES,
       bo.DLV_DATE_TIME,
       bo.CUST_REF_NO,
       bo.PROCESS_NO,
       bo.PAYMENT_TYPE,
       bo.UPDTAED_PROCESS_FROM,
       bo.PICKUP_RUNSHEET_NO,
       bo.WEIGHT_CAPTURED_MODE,
       bo.PAPER_WORK_REF_NO,
       bo.PINCODE_DLV_TIME_MAP_ID,
       bo.STATUS,
       bo.INSURED_BY as INSURED_BY_BO,
       bo.SHIPPED_TO_CODE,
       c.CONSG_ID,
       c.CONSG_NO,
       c.ORG_OFF,
       c.DEST_PINCODE,
       c.NO_OF_PCS,
       c.CONSG_TYPE,
       c.PRICE,
       c.PRODUCT,
       c.ACTUAL_WEIGHT,
       c.VOL_WEIGHT,
       c.FINAL_WEIGHT,
       c.CONTENT,
       c.PAPER_WORK,
       c.INSURED_BY as INSURED_BY_C,
       c.INSURANCE_POLICY_NO,
       c.REF_NO,
       c.HEIGHT,
       c.LENGTH,
       c.BREATH,
       c.RECEIVED_DATE,
       c.DLV_DATE_TIME AS DLV_DATE_TIME_C,
       c.CONSG_STATUS,
       c.DECLARED_VALUE,
       c.BILLING_STATUS,
       c.CHANGED_AFTER_BILLING_WEIGHT_DEST,
       c.CHANGED_AFTER_NEW_RATE_COMPONENT,
      
       c.CUSTOMER AS CUSTOMER_C,
       c.RTH_RTO_REASON,
       c.TOPAY_AMT,
       c.SPL_CHG,
       c.COD_AMT,
       c.LC_AMT,
       c.RATE_TYPE,
       c.OTHERS_CN_CONTENT,
       c.STOP_DELIVERY,
       c.STOP_DELIVERY_REASON,
       c.STOP_DELV_DATE,
       c.MIS_ROUTED,
       c.IS_EXCESS_CONSG,
       cr.CONSIGNMENT_RATE_ID CONSIGNMENT_RATE_ID_CR,
       cr.CONSIGNMENT_ID,
       cr.RATE_CALCULATED_FOR,
       cr.FUEL_SURCHARGE,
       cr.RISK_SURCHARGE,
       cr.TO_PAY_CHARGE,
       cr.COD_CHARGES,
       cr.PARCEL_HANDLING_CHARGE,
       cr.AIRPORT_HANDLING_CHARGE,
       cr.DOCUMENT_HANDLING_CHARGE,
       cr.RTO_DISCOUNT,
       cr.OTHER_OR_SPECIAL_CHARGES,
       cr.DISCOUNT,
       cr.SERVICE_TAX,
       cr.EDUCATION_CESS,
       cr.HIGHER_EDUCATION_CES,
       cr.STATE_TAX,
       cr.SURCHARGE_ON_STATE_TAX,
       cr.OCTROI,
       cr.SERVICE_CHARGE_ON_OCTROI,
       cr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE,
       cr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
       cr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE,
       cr.TOTAL_WITHOUT_TAX,
       cr.GRAND_TOTAL_INCLUDING_TAX,
       cr.LC_CHARGE,
       cr.SLAB_RATE,
       cr.FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE,
       cr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE,
       cr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE,
       cr.COD_AMOUNT,
       cr.BILLED,
       bc.BILLING_CONSIGNMENT_ID,
       bc.CONSG_ID AS CONSG_ID_BC,
       bc.CONSG_NO AS CONSG_NO_BC,
       bc.ORIGIN_OFFICE,
       bc.ACTUAL_WEIGHT AS ACTUAL_WEIGHT_BC,
       bc.VOL_WEIGHT AS VOL_WEIGHT_BC,
       bc.FINAL_WEIGHT AS FINAL_WEIGHT_BC,
       bc.UPDATED_PROCESS_FROM,
       bc.DLV_DATE_TIME AS DLV_DATE_TIME_BC,
       c.CONSIGNOR,
       c.CONSIGNEE,
       bc.CONSG_STATUS AS CONSG_STATUS_BC,
       bc.SUMMARY,
       bc.INVOICE,
       bc.SCOPE,
       bc.CONSIGNMENT_COPY,
       bc.LC_BANK_NAME,
       bc.CUSTOMER AS CUSTOMER_BC,
       bc.BILLING_STATUS AS BILLING_STATUS_BC,
       bc.CHANGED_AFTER_BILLING_WEIGHT_DEST
          AS CHANGED_AFTER_BILLING_WEIGHT_DEST_BC,
       bc.CHANGED_AFTER_NEW_RATE_COMPONENT
          AS CHANGED_AFTER_NEW_RATE_COMPONENT_BC,
       bc.DELTA_TRANSFER,
       bcr.BILLING_CONSIGNMENT_RATE_ID as BILLING_CONSIGNMENT_RATE_ID_BCR,
       bcr.CONSIGNMENT_RATE_ID,
       bcr.BILLING_CONSIGNMENT,
       bcr.CONSIGNMENT,
       bcr.RATE_CALCULATED_FOR AS RATE_CALCULATED_FOR_BCR,
       bcr.FINAL_SLAB_RATE AS FINAL_SLAB_RATE_BCR,
       bcr.FUEL_SURCHARGE AS FUEL_SURCHARGE_BCR,
       bcr.RISK_SURCHARGE AS RISK_SURCHARGE_BCR,
       bcr.TO_PAY_CHARGE AS TO_PAY_CHARGE_BCR,
       bcr.COD_CHARGES AS COD_CHARGES_BCR,
       bcr.PARCEL_HANDLING_CHARGE AS PARCEL_HANDLING_CHARGE_BCR,
       bcr.AIRPORT_HANDLING_CHARGE AS AIRPORT_HANDLING_CHARGE_BCR,
       bcr.DOCUMENT_HANDLING_CHARGE AS DOCUMENT_HANDLING_CHARGE_BCR,
       bcr.RTO_DISCOUNT AS RTO_DISCOUNT_BCR,
       bcr.OTHER_OR_SPECIAL_CHARGES AS OTHER_OR_SPECIAL_CHARGES_BCR,
       bcr.DISCOUNT AS DISCOUNT_BCR,
       bcr.SERVICE_TAX AS SERVICE_TAX_BCR,
       bcr.EDUCATION_CESS AS EDUCATION_CESS_BCR,
       bcr.HIGHER_EDUCATION_CES AS HIGHER_EDUCATION_CES_BCR,
       bcr.STATE_TAX AS STATE_TAX_BCR,
       bcr.SURCHARGE_ON_STATE_TAX AS SURCHARGE_ON_STATE_TAX_BCR,
       bcr.OCTROI AS OCTROI_BCR,
       bcr.SERVICE_CHARGE_ON_OCTROI AS SERVICE_CHARGE_ON_OCTROI_BCR,
       bcr.SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE
          AS SERVICE_TAX_ON_OCTROI_SERVICE_CHARGE_BCR,
       bcr.EDU_CESS_ON_OCTROI_SERVICE_CHARGE
          AS EDU_CESS_ON_OCTROI_SERVICE_CHARGE_BCR,
       bcr.HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE
          AS HIGHER_EDU_CESS_ON_OCTROI_SERVICE_CHARGE_BCR,
       bcr.TOTAL_WITHOUT_TAX AS TOTAL_WITHOUT_TAX_BCR,
       bcr.GRAND_TOTAL_INCLUDING_TAX AS GRAND_TOTAL_INCLUDING_TAX_BCR,
       bcr.LC_CHARGE AS LC_CHARGE_BCR,
       bcr.FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE
          AS FINAL_SLAB_RATE_ADDED_TO_RISK_SURCHARGE_BCR,
       bcr.LC_AMOUNT AS LC_AMOUNT_BCR,
       bcr.OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE
          AS OCTROI_SALES_TAX_ON_OCTROI_SERVICE_CHARGE_BCR,
       bcr.OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE
          AS OCTROI_SURCHARGE_ON_STATE_TAX_ON_OCTROI_SERVICE_CHARGE_BCR,
       bi.INVOICE_ID,
       bi.INVOICE_NUMBER,
       bi.FINANCIAL_PRODUCT_ID,
       bi.PRODUCT_ID,
       bi.BILLING_OFFICE_ID,
       bi.FROM_DATE,
       bi.TO_DATE,
       bi.NO_OF_PICKUPS,
       bi.FREIGHT,
       bi.RISK_SURCHARGE AS RISK_SURCHARGE_BI,
       bi.DOC_HANDLING_CHARGE,
       bi.PARCAL_HANDLING_CHARGE,
       bi.AIRPORT_HANDLING_CHARGE AS AIRPORT_HANDLING_CHARGE_BI,
       bi.OTHER_CHARGES,
       bi.TOTAL,
       bi.FUEL_SURCHARGE AS FUEL_SURCHARGE_BI,
       bi.SERVICE_TAX AS SERVICE_TAX_BI,
       bi.EDUCATION_CESS AS EDUCATION_CESS_BI,
       bi.HIGHER_EDUCATION_CESS,
       bi.STATE_TAX AS STATE_TAX_BI,
       bi.SURCHARGE_ON_STATE_TAX AS SURCHARGE_ON_STATE_TAX_BI,
       bi.GRAND_TOTAL,
       bi.GRAND_TOTAL_ROUNDED_OFF,
       bi.BILL_GENERATED,
       bi.BILL_STATUS,
       bi.SHIP_TO_CODE,
       bi.CUSTOMER AS CUSTOMER_BI,
       NOW() as LAST_UPDT_DATE,
       c.EVENT_DATE,
       bo.CUST_VENDOR,
       bo.BOOKING_PAYMENT_ID,
       bo.INSURANCE_CHG,
       cr.FINAL_SLAB_RATE
  FROM ff_f_booking bo                                                
       JOIN ff_f_consignment c
          ON bo.CONSG_NUMBER = c.CONSG_NO                             
       LEFT JOIN ff_f_consignment_rate cr
          ON cr.CONSIGNMENT_ID = c.CONSG_ID                           
       LEFT JOIN ff_f_billing_consignment bc
          ON bc.consg_id = c.consg_id                                 
       LEFT JOIN ff_f_billing_consignment_rate bcr
          ON bcr.billing_consignment = bc.billing_consignment_id      
       LEFT JOIN ff_f_bill bi
          ON bi.INVOICE_ID = bc.INVOICE                               
  where IFNULL(bo.BOOKING_DATE,str_to_date('01/01/1950','%d/%m/%Y')) > DATE_SUB(CURDATE(),INTERVAL 4 MONTH);
  commit;



DELETE FROM ff_fct_lead_to_contract where IFNULL(CUST_CREATED_DATE,str_to_date('01/01/1950','%d/%m/%Y')) > DATE_SUB(CURDATE(),INTERVAL 4 MONTH);
insert into ff_fct_lead_to_contract
SELECT c.CUST_CREATED_DATE,
       c.CUST_UPDATED_DATE,
       c.N_CUST_SKEY,
       dg.N_GEO_SKEY,
       dp.N_PROD_SKEY,
       l.LEAD_ID,
       l.LEAD_NUMBER,
       l.LEAD_STATUS_CODE,
       lc.LEAD_COMPETITOR_ID,
       lc.COMPETITOR_ID,
       lc.POTENTIAL,
       lc.EXPECTED_VOLUME,
       rc.RATE_CONTRACT_ID,
       rc.RATE_QUOTATION,
       rc.VALID_FROM_DATE,
       rc.VALID_TO_DATE,
       rc.BILLING_CONTRACT_TYPE,
       rc.TYPE_OF_BILLING,
       rc.MODE_OF_BILLING,
       rc.BILLING_CYCLE,
       rc.PAYMENT_TERM,
       rc.OCTRAI_BOURNE_BY,
       rc.CONTRACT_FOR,
       rc.RATE_CONTRACT_NUMBER,
       rc.RATE_CONTRACT_TYPE,
       rc.CONTRACT_STATUS,
       rc.RATE_CONTRACT_ORIGINATED_FROM,
       rc.CONTRACT_RENEWED,
       rq.RATE_QUOTATION_ID,
       rq.RATE_QUOTATION_NUMBER,
       rq.STATUS,
       rq.RATE_QUOTATION_TYPE,
       rq.RATE_QUOTATION_ORIGINATED_FROM_TYPE,
       rq.RATE_QUOTATION_ORIGINATED_FROM,
       rq.QUOTATION_USED_FOR,
       rqpch.RATE_QUOTATION_PRODUCT_CATEGORY_HEADER_ID,
       rqpch.VOB_SLAB,
       rqpch.MINIMUM_CHARGEABLE_WEIGHT_ID,
       rqfc.RATE_QUOTATION_FIXED_CHARGES_ID,
       rqfc.COMPONENT_VALUE,
       rqrto.RATE_QUOTATION_RTO_CHARGES_ID,
       NOW() as LAST_UPDT_DATE
  FROM ff_dim_customer c                                               
       LEFT JOIN ff_dim_geography dg on c.SALES_OFFICE_CODE=dg.OFFICE_CODE
       LEFT JOIN ff_f_lead l
          ON c.legacy_customer_code = l.LEAD_ID                        
       LEFT JOIN ff_f_lead_competitor lc
          ON l.lead_id = lc.lead_id                                    
       LEFT JOIN ff_d_rate_contract rc
          ON     rc.RATE_CONTRACT_NUMBER = c.CONTRACT_NO               
             AND rc.customer = c.CUSTOMER_ID
       LEFT JOIN ff_f_rate_quotation rq
          ON rc.rate_quotation = rq.rate_quotation_id                  
       LEFT JOIN ff_f_rate_quotation_product_category_header rqpch
          ON rqpch.RATE_QUOTATION = rq.RATE_QUOTATION_ID               
       LEFT JOIN ff_dim_product dp
          ON rqpch.RATE_PRODUCT_CATEGORY = dp.RATE_PRODUCT_CATEGORY_ID 
       LEFT JOIN ff_f_rate_quotation_fixed_charges rqfc
          ON rqfc.RATE_QUOTATION = rq.RATE_QUOTATION_ID                
       LEFT JOIN ff_f_rate_quotation_rto_charges rqrto
          ON rqrto.rate_quotation = rq.rate_quotation_id              
  where IFNULL(CUST_CREATED_DATE,str_to_date('01/01/1950','%d/%m/%Y')) > DATE_SUB(CURDATE(),INTERVAL 4 MONTH);
commit;
	
END;
