use corpUDAAN;
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_CREDIT_CUSTOMER_SERIES';
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_CREDIT_CARD_CUSTOMER_SERIES';
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_RL_CUSTOMER_SERIES';
update ff_d_configurable_params set param_value= concat (param_value,',X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_LC_CUSTOMER_SERIES';
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_EMP_SERIES';
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='STOCK_ISSUE_GOVT_CUSTOMER_SERIES';
