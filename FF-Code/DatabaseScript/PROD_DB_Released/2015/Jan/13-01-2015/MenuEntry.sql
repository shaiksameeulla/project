UPDATE ff_d_app_scrn
   SET URL_PARAMS = 'submitName=getPaymentAdviceReport',
       CREATION_DATE = now(),
       UPDATE_DATE = now(),
       DT_TO_BRANCH = 'N'
 WHERE SCREEN_ID = 249 AND APP_NAME = 'centralized';

UPDATE ff_d_app_scrn
   SET URL_NAME = 'payment-adviceReport.do?submitName=getPaymentAdviceReport',
       URL_PARAMS = 'submitName=getPaymentAdviceReport',
       CREATION_DATE = now(),
       UPDATE_DATE = now(),
       DT_TO_BRANCH = 'N'
 WHERE SCREEN_ID = 250 AND APP_NAME = 'udaan-report';