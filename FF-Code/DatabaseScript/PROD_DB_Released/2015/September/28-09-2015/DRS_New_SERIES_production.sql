use corpUDAAN;
update ff_d_configurable_params set param_value= concat (param_value,',I'), dt_to_branch='N' where param_name ='NORMAL_PRIORITY_DOX_DRS';
update ff_d_configurable_params set param_value= concat (param_value,',I,X'), dt_to_branch='N' where param_name ='NORMAL_PRIORITY_PPX_DRS';
update ff_d_configurable_params set param_value= concat (param_value,',X'), dt_to_branch='N' where param_name ='CC_Q_SERIES_DOX_DRS';