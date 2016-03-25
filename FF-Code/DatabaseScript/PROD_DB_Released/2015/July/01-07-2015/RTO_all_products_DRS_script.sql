use CorpUDAAN;
update  ff_d_configurable_params set param_value='L,T,M,B,P,A,C,S,D,Q,Z,N,CM',DT_TO_BRANCH='N' where param_name ='RTO_COD_DRS';
update ff_d_app_scrn set DT_TO_BRANCH='N',SCREEN_CODE='RTOP',SCREEN_NAME='RTO DRS Preparation', SCREEN_DESCRIPTION='RTO DRS Preparation' WHERE SCREEN_ID=25;
update ff_d_app_scrn set DT_TO_BRANCH='N',SCREEN_CODE='RTOU',SCREEN_NAME='Update RTO DRS', SCREEN_DESCRIPTION='Update RTO DRS' WHERE SCREEN_ID=195;

