/* The below script is used to insert the correct contract number against the required customer Ids in ff_d_customer table & also re-trigger the same to SAP */
use CorpUDAAN;
update ff_d_customer set contract_no = 'CM9910001473', dt_sap_outbound = 'N' where customer_id = 1473;
update ff_d_customer set contract_no = 'CB9910026038', dt_sap_outbound = 'N' where customer_id = 26070;
update ff_d_customer set contract_no = 'CM9910027114', dt_sap_outbound = 'N' where customer_id = 27148;
update ff_d_customer set contract_no = 'CO9910049544', dt_sap_outbound = 'N' where customer_id = 51819;
update ff_d_customer set contract_no = 'CP9910055267', dt_sap_outbound = 'N' where customer_id = 57521;
update ff_d_customer set contract_no = 'CI9910070931', dt_sap_outbound = 'N' where customer_id = 73882;
