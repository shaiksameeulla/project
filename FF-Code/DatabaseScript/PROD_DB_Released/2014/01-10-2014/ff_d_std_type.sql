# Rem insert into ff_d_std_type

delete from ff_d_std_type where ID=277;

insert into ff_d_std_type (id,std_type_code,description,parent_type,type_name,
        cur_status,dt_to_branch) values      
 (296, 'CSETTLE', 'CLAIM SETTLED', 'COMPLAINTS','CLAIM_COMPLAINTS_STATUS','A','N');

insert into ff_d_std_type (id,std_type_code,description,parent_type,type_name,
        cur_status,dt_to_branch) values      
(297, 'CPROG', 'CLAIM IN PROGRESS', 'COMPLAINTS','CLAIM_COMPLAINTS_STATUS','A','N');

insert into ff_d_std_type (id,std_type_code,description,parent_type,type_name,
        cur_status,dt_to_branch) values      
(298, 'CFTC', 'CLAIM FORWARDED TO CORPORATE', 'COMPLAINTS','CLAIM_COMPLAINTS_STATUS','A','N');

COMMIT;