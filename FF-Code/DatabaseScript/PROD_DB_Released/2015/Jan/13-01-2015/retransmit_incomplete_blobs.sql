/*
As per FFCL request, time period has been changed from 2 days to 10 days.
*/
UPDATE ff_d_outbound_office_packet SET TRANSFER_STATUS='N'        
WHERE TRANSFER_STATUS='I' AND PROCESSED_DATE < DATE_SUB(NOW(),INTERVAL 1 HOUR) and PROCESSED_DATE >=DATE_SUB(NOW(),INTERVAL 10 day);