
use udaan_gold_bcun;
/*Operational Masters for Rate (Rate meta Data):	*/

set foreign_key_checks=0;
truncate table  	ff_d_account_group_sap	;
truncate table  	ff_d_address	;
truncate table  	ff_d_ba_material_rate_config	;
truncate table  	ff_d_ba_material_rate_details	;
truncate table  	ff_d_cod_charge	;
truncate table  	ff_d_consignment_type_config	;
truncate table  	ff_d_contact	;
truncate table  	ff_d_contract_payment_billing_location	;
truncate table 		ff_d_customer;
truncate table 		ff_d_customer_group;
truncate table  	ff_d_eb_preference_rate	;
truncate table  	ff_d_eb_rate_config	;
truncate table  	ff_d_insurance_mandatory_config	;
truncate table  	ff_d_insured_by	;
truncate table  	ff_d_octroi_charge	;
truncate table 		ff_d_pickup_delivery_contract;
truncate table 		ff_d_pickup_delivery_location;
truncate table 		ff_d_preference_details;
truncate table  	ff_d_rate_bench_mark_header	;
truncate table  	ff_d_rate_bench_mark_matrix	;
truncate table  	ff_d_rate_bench_mark_matrix_header	;
truncate table  	ff_d_rate_bench_mark_product	;
truncate table  	ff_d_rate_component	;
truncate table  	ff_d_rate_component_applicable	;
truncate table  	ff_d_rate_component_calculated	;
truncate table  	ff_d_rate_contract	;
truncate table  	ff_d_rate_contract_spoc_contact_details	;
truncate table  	ff_d_rate_customer	;
truncate table  	ff_d_rate_customer_category	;
truncate table  	ff_d_rate_customer_product_cat_map	;
truncate table  	ff_d_rate_industry_category	;
truncate table  	ff_d_rate_industry_type	;
truncate table  	ff_d_rate_min_chargeable_weight	;
truncate table  	ff_d_rate_prod_prod_cat_map	;
truncate table  	ff_d_rate_product_category	;
truncate table  	ff_d_rate_sectors	;
truncate table  	ff_d_rate_tax_component	;
truncate table  	ff_d_rate_tax_component_applicable	;
truncate table  	ff_d_rate_vob_slabs	;
truncate table  	ff_d_rate_weight_slabs	;
truncate table  	ff_d_region_rate_bench_mark_discount	;
truncate table  	ff_d_risk_surcharge	;
truncate table 		ff_d_sector;
truncate table  	ff_d_vob_slab	;
truncate table  	ff_d_weight_slab	;




/*Transactional Masters for Rate (Created through Application) :	*/

truncate table  	ff_f_rate_ba_additional_charges	;
truncate table  	ff_f_rate_ba_cod_charge	;
truncate table  	ff_f_rate_ba_fixed_charges_config	;
truncate table  	ff_f_rate_ba_product_header	;
truncate table  	ff_f_rate_ba_rate_header	;
truncate table  	ff_f_rate_ba_rto_charge	;
truncate table  	ff_f_rate_ba_slab_rate	;
truncate table  	ff_f_rate_ba_special_destination	;
truncate table  	ff_f_rate_ba_weight_slab	;
truncate table  	ff_f_rate_cash_additional_charges	;
truncate table  	ff_f_rate_cash_cod_charge	;
truncate table  	ff_f_rate_cash_fixed_charges_config	;
truncate table  	ff_f_rate_cash_product_map	;
truncate table  	ff_f_rate_cash_rate_header	;
truncate table  	ff_f_rate_cash_rto_charge	;
truncate table  	ff_f_rate_cash_slab_rate	;
truncate table  	ff_f_rate_cash_special_destination	;
truncate table  	ff_f_rate_cash_weight_slab	;
truncate table  	ff_f_rate_quotation	;
truncate table  	ff_f_rate_quotation_cod_charge	;
truncate table  	ff_f_rate_quotation_fixed_charges	;
truncate table  	ff_f_rate_quotation_fixed_charges_config	;
truncate table  	ff_f_rate_quotation_product_category_header	;
truncate table  	ff_f_rate_quotation_rto_charges	;
truncate table  	ff_f_rate_quotation_slab_rate	;
truncate table  	ff_f_rate_quotation_special_destination	;
truncate table  	ff_f_rate_quotation_weight_slab	;
set foreign_key_checks=1;
