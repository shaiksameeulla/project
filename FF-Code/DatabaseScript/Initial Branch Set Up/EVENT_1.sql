SET GLOBAL event_scheduler = ON;
USE udaan_uat_may14_ver2;

CREATE EVENT
IF NOT EXISTS
`retransmit_incomplete_blobs`                
ON SCHEDULE EVERY 2 HOUR     

DO BEGIN            
-- Update Incomplete blobs transfer to retransfer and do this for  upto 2 days old packets only  
UPDATE ff_d_outbound_office_packet SET TRANSFER_STATUS='N'        WHERE TRANSFER_STATUS='I' AND ( PROCESSED_DATE  <= DATE_SUB(NOW(), INTERVAL 1 HOUR)) and ( PACKET_CREATED_DATE  >= DATE_SUB(NOW(), INTERVAL 2 DAY));           

END;
