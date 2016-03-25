create index idx_first_name on ff_d_employee(FIRST_NAME);

create index idx_last_name on ff_d_employee(LAST_NAME);

create index idx_sales_person on ff_dim_customer(SALES_PERSON);

create index IDX_TRANSACTION_NUMBER on ff_f_heldup(TRANSACTION_NUMBER);

create index idx_dim_geography_pc_region on ff_dim_geography_pc(REGION_ID);
      
create index idx_dim_geography_pc_city on ff_dim_geography_pc(CITY_ID);
      
create index idx_dim_geography_rho on ff_dim_geography(REPORTING_RHO);
      
create index idx_dim_geography_region on ff_dim_geography(REGION_ID);

CREATE UNIQUE INDEX idx_sector_name on ff_d_sector(SECTOR_NAME);

CREATE INDEX idx_dim_geography_zone_code on ff_dim_geography(ZONE_CODE);
      
create index idx_fct_book_to_bill_org_off on ff_fct_book_to_bill(ORG_OFF);
            
ALTER TABLE ff_fct_book_to_bill ADD CONSTRAINT fk_fct_book_to_bill_product
FOREIGN KEY (PRODUCT) REFERENCES ff_d_product(product_id);  



      


