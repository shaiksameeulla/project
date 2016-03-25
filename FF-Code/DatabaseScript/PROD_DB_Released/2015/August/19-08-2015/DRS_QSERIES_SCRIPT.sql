use CorpUDAAN;
update  ff_d_configurable_params set param_value = concat (param_value ,',Q'),DT_TO_BRANCH='N' where param_name ='NORMAL_PRIORITY_DOX_DRS';