/*Kaustubh - Error is occurring in “sp_scd_ff_dim_customer” while populating customer dimension due to difference in definition of EMAIL column in below tables:
ff_d_customer  EMAIL varchar(100)  and ff_dim_customer  EMAIL varchar(50) */

Use CorpUDAAN;

ALTER TABLE ff_dim_customer
MODIFY  EMAIL VARCHAR(100) DEFAULT NULL;

