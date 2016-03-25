--  1. Add product in product table / ff_d_product
insert into `ff_d_product`(`PRODUCT_ID`,`PRODUCT_CODE`,`PRODUCT_NAME`,`PRODUCT_DESC`,`CONSG_SERIES`,`STATUS`,`PRODUCT_CLASSIFICATION`,`DT_TO_BRANCH`,`PROD_GROUP_ID`,`CONSOLIDATION_WINDOW`) values (16,'PC000018','Prepaid COD','Prepaid COD','X','A','N','N',1,0);
insert into `ff_d_product`(`PRODUCT_ID`,`PRODUCT_CODE`,`PRODUCT_NAME`,`PRODUCT_DESC`,`CONSG_SERIES`,`STATUS`,`PRODUCT_CLASSIFICATION`,`DT_TO_BRANCH`,`PROD_GROUP_ID`,`CONSOLIDATION_WINDOW`) values (17,'PC000017','International in coming','International in coming','I','A','N','N',1,0);

-- 2. Mapping new product with rate product i.e. courier. / ff_d_rate_prod_prod_cat_map 
insert into `ff_d_rate_prod_prod_cat_map`(`RATE_PROD_PROD_CAT_MAP_ID`,`PRODUCT`,`RATE_PRODUCT_CATEGORY`,`DT_TO_BRANCH`) values (16,16,1,'N');
insert into `ff_d_rate_prod_prod_cat_map`(`RATE_PROD_PROD_CAT_MAP_ID`,`PRODUCT`,`RATE_PRODUCT_CATEGORY`,`DT_TO_BRANCH`) values (17,17,1,'N');

-- 3. product customer type mapping / ff_d_product_customer_type_map
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (41,16,4,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (42,16,6,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (43,16,11,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (44,16,12,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (45,16,13,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (46,16,15,'N');

insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (47,17,4,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (48,17,6,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (49,17,11,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (50,17,12,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (51,17,13,'N');
insert into ff_d_product_customer_type_map (`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (52,17,15,'N');

-- 4. Rate component applicable and product mapping / ff_d_rate_component_applicable

-- 5. Booking Type & product mapping / ff_d_booking_type_product_map
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (52,2,16,'A','N');
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (53,5,16,'A','N');
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (54,7,16,'A','N');
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (55,2,17,'A','N');
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (56,5,17,'A','N');
insert into ff_d_booking_type_product_map (`BOOKING_TYPE_PRODUCT_MAP_ID`,`BOOKING_TYPE_ID`,`PRODUCT_ID`,`STATUS`,`DT_TO_BRANCH`) values (57,7,17,'A','N');

-- 6. Product and Customer type mapping

insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (41,16,4,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (42,16,5,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (43,16,6,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (44,16,8,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (45,16,11,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (46,16,15,'N');

insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (47,17,4,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (48,17,5,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (49,17,6,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (50,17,8,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (51,17,11,'N');
insert into `ff_d_product_customer_type_map`(`PRODUCT_CUSTOMER_TYPE_ID`,`PRODUCT_ID`,`CUSTOMER_TYPE_ID`,`DT_TO_BRANCH`) values (52,17,15,'N');

UPDATE ff_d_product SET CONSG_SERIES = DT_TO_BRANCH= 'N' where PRODUCT_ID = 10;



