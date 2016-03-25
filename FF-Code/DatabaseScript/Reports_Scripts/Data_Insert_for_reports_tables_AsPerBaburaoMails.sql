/*As per 24/02/2014 mail */

insert into ff_dim_geography
select 
fn_seq_gen('n_geo_skey') as N_GEO_SKEY,NULL,
o.OFFICE_ID,      o.OFFICE_CODE,    o.OFFICE_NAME,    o.REPORTING_RHO,  o.REPORTING_HUB,  
o.PINCODE,  ot.OFFICE_TYPE_ID,      ot.OFFICE_TYPE_CODE,    ot.OFFICE_TYPE_DESC,    
c.CITY_ID,  c.CITY_CODE,      c.CITY_NAME,      
s.STATE_ID, s.STATE_CODE,     s.STATE_NAME,     
r.REGION_ID,      r.REGION_CODE, r.REGION_NAME, 
z.ZONE_ID,  z.ZONE_CODE,      z.ZONE_NAME, null,null, null
from ff_d_office o
join ff_d_office_type ot on ot.office_type_id=o.office_type_id
join ff_d_city c on c.city_id=o.city_id
join ff_d_state s on c.state=s.state_id
join ff_d_region r on c.region=r.region_id
join ff_d_zone z on r.zone=z.zone_id;


ALTER TABLE ff_dim_geography CHANGE N_GEO_SKEY N_GEO_SKEY INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY;


/*COMMENT :In above select MIS_DATE ,START_DATE ,END_DATE   ,LATEST_RECORD columns present in this table not mentioned in mail , hence select modified and null set for these columns*/

Create table ff_fct_customer as
SELECT c.CUSTOMER_ID,
       c.CONTRACT_NO,
       c.CUSTOMER_CODE,
       geo.N_GEO_SKEY,
       c.SALES_PERSON AS EMPLOYEE_ID,
       c.INDUSTRY_CATEGORY AS RATE_INDUSTRY_CATEGORY_ID,
       c.INDUSTRY_TYPE AS RATE_INDUSTRY_TYPE_ID,
       c.DISTRIBUTION_CHANNELS,
       c.GROUP_KEY,
       c.BUSINESS_NAME,
       c.ADDRESS,
       c.PRIMARY_CONTACT,
       c.SECONDARY_CONTACT,
       c.BILLING_CYCLE AS BILLING_CYCLE_C,
       c.PAYEMENT_TERM,
       c.OFFICE_MAPPED_TO,
       c.TAN_NO,
       c.PAN_NO,
       c.CUR_STATUS,
       c.BUSINESS_TYPE,
       c.SAP_TIMESTAMP,
       c.CUSTOMER_TYPE,
       c.DT_SAP_OUTBOUND,
       c.PHONE,
       c.MOBILE,
       c.EMAIL,
       c.CREATED_DATE,
       c.LEGACY_CUSTOMER_CODE,
       cc.RATE_CUSTOMER_CATEGORY_ID,
       cc.CUSTOMER_CATEGORY_CODE,
       cc.CUSTOMER_CATEGORY_NAME,
       ct.CUSTOMER_TYPE_ID,
       ct.CUSTOMER_TYPE_CODE,
       ct.CUSTOMER_TYPE_DESC,
       cg.CUSTOMER_GROUP_ID,
       cg.CUSTOMER_GROUP_CODE,
       cg.CUSTOMER_GROUP_NAME,
       l.LEAD_ID,
       l.CUSTOMER_NAME,
       l.LEAD_NUMBER,
       l.CONTACT_PERSON,
       l.STD_CODE,
       l.PHONE_NO,
       l.MOBILE_NO,
       l.DOOR_NO_BUILDING,
       l.STREET_ROAD,
      l.LOCATION,
       l.CITY,
       l.PINCODE,
       l.DESIGNATION,
       l.INDUSTRY_CATEGORY_CODE,
       l.LEAD_SOURCE_CODE,
       l.SECONDRY_CONTACT,
       l.ASSIGNED_TO,
       l.LEAD_STATUS_CODE,
       lc.LEAD_COMPETITOR_ID,
       lc.PRODUCT_CODE,
       lc.COMPETITOR_ID,
       lc.POTENTIAL,
       lc.EXPECTED_VOLUME,
       rc.RATE_CONTRACT_ID as RATE_CONTRACT_ID_RC,
       rc.RATE_QUOTATION,
       rc.CUSTOMER,
       rc.VALID_FROM_DATE,
       rc.VALID_TO_DATE,
       rc.BILLING_CONTRACT_TYPE,
       rc.TYPE_OF_BILLING,
       rc.MODE_OF_BILLING,
       rc.BILLING_CYCLE AS BILLING_CYCLE_RC,
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
       rq.RATE_CUSTOMER,
       rq.RATE_QUOTATION_ORIGINATED_FROM_TYPE,
       rq.RATE_QUOTATION_ORIGINATED_FROM,
       rq.QUOTATION_USED_FOR,
       rq.RATE_CONTRACT_ID AS RATE_CONTRACT_ID_RQ
       from ff_d_customer c
left join ff_dim_geography geo on geo.OFFICE_ID=c.SALES_OFFICE
left join ff_d_rate_customer_category cc on c.customer_category=cc.rate_customer_category_id
left join ff_d_customer_type ct on c.customer_type=ct.customer_type_id
left join ff_d_customer_group cg on c.group_key=cg.customer_group_id
left join ff_f_lead l on c.legacy_customer_code=l.LEAD_ID
left join ff_f_lead_competitor lc on l.lead_id=lc.lead_id
left join ff_d_rate_contract rc on rc.customer=c.customer_id
left join ff_f_rate_quotation rq on rc.rate_quotation=rq.rate_quotation_id;

Create Unique Index ff_fct_customer on ff_fct_customer (customer_id,rate_contract_id_rq,rate_contract_id_rc);

/*As per 25/02/2014 mail */
INSERT INTO numbers_small VALUES (0),(1),(2),(3),(4),(5),(6),(7),(8),(9);

INSERT INTO numbers
SELECT tenthousands.number * 10000 +thousands.number * 1000 + hundreds.number * 100 + tens.number * 10 + ones.number
FROM numbers_small tenthousands,numbers_small thousands, numbers_small hundreds, numbers_small tens, numbers_small ones
LIMIT 1000000;


INSERT INTO ff_dim_date (n_date_skey, date)
SELECT fn_seq_gen('n_date_skey'), DATE_ADD( '2010-01-01', INTERVAL number DAY )
FROM numbers
WHERE DATE_ADD( '2010-01-01', INTERVAL number DAY ) BETWEEN '2010-01-01' AND '2100-03-31'
ORDER BY number;

UPDATE ff_dim_date SET
day             = DATE_FORMAT( date, "%W" ),
day_of_week     = DAYOFWEEK(date),
day_of_month    = DATE_FORMAT( date, "%d" ),
day_of_year     = DATE_FORMAT( date, "%j" ),
previous_day    = DATE_ADD(date, INTERVAL -1 DAY),
next_day        = DATE_ADD(date, INTERVAL 1 DAY),
weekend         = IF( DATE_FORMAT( date, "%W" ) IN ('Saturday','Sunday'), 'Weekend', 'Weekday'),
week_of_year    = DATE_FORMAT( date, "%V" ),
month           = DATE_FORMAT( date, "%M"),
month_of_year   = DATE_FORMAT( date, "%m"),
quarter_of_year = QUARTER(date),
year            = DATE_FORMAT( date, "%Y" );


/*3*/

INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_geo_skey',now());
INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_cust_skey',now());
INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_prod_skey',now());
INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_item_skey',now());
INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_holiday_skey',now());
INSERT INTO `bi_seq_gen` (`n_seq_no`, `v_application_id`, `d_time_stamp`) VALUES (0, 'n_date_skey',now());

/*As per 25/02/2014 mail */

insert into ff_dim_customer 
SELECT fn_seq_gen('n_cust_skey') as N_CUST_SKEY,null,
       c.CUSTOMER_ID,
       c.CONTRACT_NO,
       c.CUSTOMER_CODE,
       o.office_name AS SALES_OFFICE,
       concat(e.first_name,' ',e.last_name) AS SALES_PERSON,
       ic.industry_category_name AS INDUSTRY_CATEGORY,
       it.industry_type_name AS INDUSTRY_TYPE,
       c.DISTRIBUTION_CHANNELS,
       c.BUSINESS_NAME,
       c.ADDRESS,
       c.PRIMARY_CONTACT,
       c.SECONDARY_CONTACT,
       c.BILLING_CYCLE,
       c.PAYEMENT_TERM,
       c.OFFICE_MAPPED_TO,
       c.TAN_NO,
       c.PAN_NO,
       c.CUR_STATUS,
       c.BUSINESS_TYPE,
       c.SAP_TIMESTAMP,
       c.PHONE,
       c.MOBILE,
       c.EMAIL,
       c.LEGACY_CUSTOMER_CODE,
       cc.RATE_CUSTOMER_CATEGORY_ID,
       cc.CUSTOMER_CATEGORY_CODE,
       cc.CUSTOMER_CATEGORY_NAME,
       ct.CUSTOMER_TYPE_ID,
       ct.CUSTOMER_TYPE_CODE,
       ct.CUSTOMER_TYPE_DESC,
       cg.CUSTOMER_GROUP_ID,
       cg.CUSTOMER_GROUP_CODE,
       cg.CUSTOMER_GROUP_NAME,null,null,null
  FROM ff_d_customer c
       LEFT JOIN ff_d_rate_customer_category cc
          ON c.customer_category = cc.rate_customer_category_id
      LEFT JOIN ff_d_customer_type ct
          ON c.customer_type = ct.customer_type_id
       LEFT JOIN ff_d_customer_group cg
          ON c.group_key = cg.customer_group_id
       LEFT JOIN ff_d_employee e
          ON e.EMPLOYEE_ID = c.SALES_PERSON
       LEFT JOIN ff_d_office o
          ON o.office_id = c.SALES_OFFICE
       LEFT JOIN ff_d_office o1
          ON o1.office_id = c.OFFICE_MAPPED_TO
       LEFT JOIN ff_d_rate_industry_category ic
          ON c.INDUSTRY_CATEGORY = ic.RATE_INDUSTRY_CATEGORY_ID
       LEFT JOIN ff_d_rate_industry_type it
          ON c.INDUSTRY_TYPE = it.rate_industry_type_id;
		  
/*COMMENT :In above select MIS_DATE ,START_DATE ,END_DATE   ,LATEST_RECORD columns present in this table not mentioned in mail , hence select modified and null set for these columns*/
		  
insert into  ff_dim_product 
SELECT fn_seq_gen('n_prod_skey') as N_PROD_SKEY,null,
       p.PRODUCT_ID,
       p.PRODUCT_CODE,
       p.PRODUCT_NAME,
       p.PRODUCT_DESC,
       p.CONSG_SERIES,
       p.PRODUCT_CLASSIFICATION,
       p.CONSOLIDATION_WINDOW,
       pcm.RATE_PROD_PROD_CAT_MAP_ID,
       pc.RATE_PRODUCT_CATEGORY_ID,
       pc.PRODUCT_CATEGORY_CODE,
       pc.PRODUCT_CATEGORY_NAME,
       pc.RATE_PRODUCT_CATEGORY_TYPE,
       pc.CALCULATION_TYPE,
       pc.DISTRIBUTION_CHANNEL,
       pg.PROD_GROUP_ID,
       pg.PROD_GROUP_CODE,
       pg.PROD_GROUP_NAME,null,null,null
       from ff_d_product p
join ff_d_rate_prod_prod_cat_map pcm on p.PRODUCT_ID=pcm.product
join ff_d_rate_product_category pc on pc.rate_product_category_id=pcm.rate_product_category
join ff_d_product_group_srvcblty pg on p.prod_group_id=pg.prod_group_id;
		  
/*COMMENT :In above select MIS_DATE ,START_DATE ,END_DATE   ,LATEST_RECORD columns present in this table not mentioned in mail , hence select modified and null set for these columns*/


insert into  ff_dim_item 
SELECT fn_seq_gen('n_item_skey') as N_ITEM_SKEY,
       i.ITEM_ID,
       i.ITEM_CODE,
       i.ITEM_NAME,
       i.UNIT_OF_MEASURE,
       i.UOM_DESCRIPTION,
       i.ITEM_SERIES,
       i.SERIES_LENGTH,
       i.PURCHASE_DATE,
       i.DESCRIPTION,
       it.ITEM_TYPE_ID,
       it.ITEM_TYPE_CODE,
       it.ITEM_TYPE_NAME,
       it.ITEM_HAS_SERIES
       from ff_d_item i
join ff_d_item_type it on i.item_type_id=it.item_type_id;
		  
		  
		  
/* In mail only tables created  is mentioned for below tables, no table definition (create table st) / query available for below tables in mail communication in 26/02/2014 mail 
ff_fct_lead_to_contract 
ff_fct_book_to_bill 
ff_fct_delivery 
*/		  

/*As per 24/03/2014 mail */

insert into ff_f_sales_target values(2014,'January','B901',200000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','B901',200100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','B901',200200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','B901',200300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','B901',200400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','B901',200500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','B901',200600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','B901',200700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','B901',200800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','B901',200900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','B901',201000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','B901',201100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'January','B023',300000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','B023',300100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','B023',300200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','B023',300300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','B023',300400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','B023',300500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','B023',300600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','B023',300700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','B023',300800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','B023',300900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','B023',301000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','B023',301100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'January','F001',400000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','F001',400100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','F001',400200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','F001',400300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','F001',400400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','F001',400500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','F001',400600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','F001',400700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','F001',400800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','F001',400900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','F001',401000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','F001',401100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'January','F005',500000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','F005',500100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','F005',500200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','F005',500300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','F005',500400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','F005',500500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','F005',500600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','F005',500700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','F005',500800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','F005',500900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','F005',501000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','F005',501100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'January','C991',600000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','C991',600100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','C991',600200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','C991',600300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','C991',600400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','C991',600500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','C991',600600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','C991',600700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','C991',600800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','C991',600900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','C991',601000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','C991',601100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'January','C035',700000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'February','C035',700100,'ABC',curdate());
insert into ff_f_sales_target values(2014,'March','C035',700200,'ABC',curdate());
insert into ff_f_sales_target values(2014,'April','C035',700300,'ABC',curdate());
insert into ff_f_sales_target values(2014,'May','C035',700400,'ABC',curdate());
insert into ff_f_sales_target values(2014,'June','C035',700500,'ABC',curdate());
insert into ff_f_sales_target values(2014,'July','C035',700600,'ABC',curdate());
insert into ff_f_sales_target values(2014,'August','C035',700700,'ABC',curdate());
insert into ff_f_sales_target values(2014,'September','C035',700800,'ABC',curdate());
insert into ff_f_sales_target values(2014,'October','C035',700900,'ABC',curdate());
insert into ff_f_sales_target values(2014,'November','C035',701000,'ABC',curdate());
insert into ff_f_sales_target values(2014,'December','C035',701100,'ABC',curdate());



/*As per 25/03/2014 mail */
insert into ff_d_consignment_status_for_reports values(1,	'DLVD',	'Delivered');
insert into ff_d_consignment_status_for_reports values(2,	'PNWR',	'Pending with Reason');
insert into ff_d_consignment_status_for_reports values(3,	'PNOR',	'Pending without Reason');
insert into ff_d_consignment_status_for_reports values(4,	'EXCN',	'Excess CN');
insert into ff_d_consignment_status_for_reports values(5,	'RTHM',	'RTH Marked');
insert into ff_d_consignment_status_for_reports values(6,	'RTOM',	'RTO Marked');
insert into ff_d_consignment_status_for_reports values(7,	'LOCN',	'Lost Marked');
insert into ff_d_consignment_status_for_reports values(8,	'RECO',	'Return to Consignor');
insert into ff_d_consignment_status_for_reports values(9,	'MRCN',	'Misrouted');


/*As per 26/03/2014 mail */
insert into ff_d_category_for_reports (
     CATEGORY_ID
    ,CATEGORY_CODE
    ,CATEGORY_DESC
  ) VALUES (
     1  
    ,'L'
    ,'Local' 
  );
 insert into ff_d_category_for_reports (
     CATEGORY_ID
    ,CATEGORY_CODE
    ,CATEGORY_DESC) VALUES (
     2
    ,'N'
    ,'National');