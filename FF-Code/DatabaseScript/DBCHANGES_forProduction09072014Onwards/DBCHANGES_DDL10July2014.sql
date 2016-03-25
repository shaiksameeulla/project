/*DATE:10.07/2014*/
/*Himal :Table added in corpUDAAN, CorpUDAAN_CG*/

CREATE TABLE ff_f_delivery_commission_calculation (                                                                                                            
DELIVERY_COMM_ID int(11) NOT NULL AUTO_INCREMENT,                                                                                                            
EMPLOYEE_ID int(11) DEFAULT NULL,                                                                                                                            
PRODUCT_GROUP enum('CC','PR','CD','OT') DEFAULT NULL COMMENT 'CC - Credit Card ( C ),PR - Priority ( P ),CD - COD ( L ),OT - Rest Others',                   
CALCULATED_FOR_PERIOD date DEFAULT NULL COMMENT 'Stores the first day of the month for which commissions got calculated',                                    
DELIVERED_COUNT int(5) DEFAULT NULL,                                                                                                                         
DELIVERED_DAY_1 int(5) DEFAULT NULL,                                                                                                                         
DELIVERED_DAY_2 int(5) DEFAULT NULL,                                                                                                                         
DELIVERED_DAY_3 int(5) DEFAULT NULL,                                                                                                                         
DELIVERED_DAY_4_BEYOND int(5) DEFAULT NULL,                                                                                                                  
TRANS_CREATED_DATE datetime DEFAULT NULL COMMENT 'Record created date',                                                                                      
PRIMARY KEY (DELIVERY_COMM_ID),                                                                                                                              
KEY FK_delivery_commission_calculation1 (EMPLOYEE_ID),                                                                                                     
CONSTRAINT FK_delivery_commission_calculation1 FOREIGN KEY (EMPLOYEE_ID) REFERENCES ff_d_employee (EMPLOYEE_ID) ON DELETE NO ACTION ON UPDATE CASCADE ) ;

/*Table structure change for Reports table */

drop table ff_fct_lead_to_contract;

CREATE TABLE ff_fct_lead_to_contract (                                                                                                                                                                                           
MIS_DATE date NOT NULL,                                                                                                                                                                                                        
N_CUST_SKEY int(11) NOT NULL,                                                                                                                                                                                                  
N_GEO_SKEY int(11) DEFAULT NULL,                                                                                                                                                                                               
N_PROD_SKEY int(11) DEFAULT NULL,                                                                                                                                                                                              
LEAD_ID int(11) DEFAULT NULL,                                                                                                                                                                                                  
LEAD_NUMBER varchar(20) DEFAULT NULL,                                                                                                                                                                                          
LEAD_STATUS_CODE varchar(10) DEFAULT NULL,                                                                                                                                                                                     
LEAD_COMPETITOR_ID int(11) DEFAULT NULL,                                                                                                                                                                                       
COMPETITOR_ID int(11) DEFAULT NULL,                                                                                                                                                                                            
POTENTIAL double DEFAULT NULL,                                                                                                                                                                                                 
EXPECTED_VOLUME double DEFAULT NULL,                                                                                                                                                                                           
RATE_CONTRACT_ID int(11) DEFAULT NULL,                                                                                                                                                                                         
RATE_QUOTATION int(11) DEFAULT NULL,                                                                                                                                                                                           
VALID_FROM_DATE datetime DEFAULT NULL,                                                                                                                                                                                         
VALID_TO_DATE datetime DEFAULT NULL,                                                                                                                                                                                           
BILLING_CONTRACT_TYPE char(1) DEFAULT NULL,                                                                                                                                                                                    
TYPE_OF_BILLING char(4) DEFAULT NULL,                                                                                                                                                                                          
MODE_OF_BILLING char(1) DEFAULT NULL,                                                                                                                                                                                          
BILLING_CYCLE char(1) DEFAULT NULL,                                                                                                                                                                                            
PAYMENT_TERM char(4) DEFAULT NULL,                                                                                                                                                                                             
OCTRAI_BOURNE_BY char(2) DEFAULT NULL,                                                                                                                                                                                         
CONTRACT_FOR char(1) DEFAULT NULL,                                                                                                                                                                                             
RATE_CONTRACT_NUMBER varchar(12) DEFAULT NULL,                                                                                                                                                                                 
RATE_CONTRACT_TYPE char(1) DEFAULT NULL,                                                                                                                                                                                       
CONTRACT_STATUS char(1) DEFAULT NULL,                                                                                                                                                                                          
RATE_CONTRACT_ORIGINATED_FROM int(11) DEFAULT NULL,                                                                                                                                                                            
CONTRACT_RENEWED char(1) DEFAULT NULL,                                                                                                                                                                                         
RATE_QUOTATION_ID int(11) DEFAULT NULL,                                                                                                                                                                                        
RATE_QUOTATION_NUMBER varchar(12) DEFAULT NULL,                                                                                                                                                                                
STATUS char(1) DEFAULT NULL,                                                                                                                                                                                                   
RATE_QUOTATION_TYPE char(1) DEFAULT NULL,                                                                                                                                                                                      
RATE_QUOTATION_ORIGINATED_FROM_TYPE char(1) DEFAULT NULL,                                                                                                                                                                      
RATE_QUOTATION_ORIGINATED_FROM int(11) DEFAULT NULL,                                                                                                                                                                           
QUOTATION_USED_FOR char(1) DEFAULT NULL,                                                                                                                                                                                       
RATE_QUOTATION_PRODUCT_CATEGORY_HEADER_ID int(11) DEFAULT NULL,                                                                                                                                                                
VOB_SLAB int(11) DEFAULT NULL,                                                                                                                                                                                                 
MINIMUM_CHARGEABLE_WEIGHT_ID int(11) DEFAULT NULL,                                                                                                                                                                             
RATE_QUOTATION_FIXED_CHARGES_ID int(11) DEFAULT NULL,                                                                                                                                                                          
COMPONENT_VALUE double(10,2) DEFAULT NULL,                                                                                                                                                                                     
RATE_QUOTATION_RTO_CHARGES_ID int(11) DEFAULT NULL,                                                                                                                                                                            
UNIQUE KEY idx_flc (MIS_DATE,N_CUST_SKEY,N_GEO_SKEY,N_PROD_SKEY,RATE_QUOTATION_ID,RATE_QUOTATION_PRODUCT_CATEGORY_HEADER_ID,RATE_QUOTATION_FIXED_CHARGES_ID,RATE_QUOTATION_RTO_CHARGES_ID,RATE_CONTRACT_ID)) ;
