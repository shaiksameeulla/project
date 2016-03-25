/*1.rectification for Query Error 1, added delete st, so no duplicated will be found */
delete from ff_dim_geography;

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


/*2.rectification for Error 2 and Eror 3: Since table ff_fct_customer already present, delete data and insert fresh data*/
Dlete from ff_fct_customer ;

Insert into  ff_fct_customer 
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


/*3.rectification for Error 4: removed ****************589797 from comment*/

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


/*4.rectification for Error 5: added semicolon at end of query */
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
		  

