/*1.Himal Kansagra changes to be executed*/

alter table ff_f_expense_entries 
add STATE_TAX			double(12, 2)  COMMENT  'State tax on Octroi service charge',
add SURCHARGE_ON_STATE_TAX  	double(12, 2) COMMENT  'Surcharge on state tax on Octroi service charge',
add OCTROI_BOURNE_BY		enum('CE','CO') COMMENT 'CE- CONSINGEE, CO- CONSIGNOR',
modify SERVICE_CHARGE		double(12, 2) 	COMMENT 'Service Charge on Octroi',
modify SERVICE_TAX		double(12, 2)	COMMENT 'Service tax on Octroi service charge',
modify EDUCATION_CESS		double(12, 2)	COMMENT 'Edu. cess on Octroi service charge',
modify HIGHER_EDUCATION_CESS 	double(12, 2)	COMMENT 'Higher Edu. cess on Octroi service charge';


/*2.Chandrakant Bhure */

alter table ff_f_sap_billing_consignment_summary add DESTINATION_OFFICE varchar(20);